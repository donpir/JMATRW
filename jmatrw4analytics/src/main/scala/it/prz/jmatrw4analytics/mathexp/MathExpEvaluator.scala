package it.prz.jmatrw4analytics.mathexp

import it.prz.jmatrw4analytics.mathexp.model.ExpLogicOperator
import it.prz.jmatrw4analytics.mathexp.model.ExpOperator
import it.prz.jmatrw4analytics.mathexp.model.DTAny
import it.prz.jmatrw4analytics.mathexp.model.ExpMathOperator
import it.prz.jmatrw4analytics.mathexp.model.ExpSymbol
import it.prz.jmatrw4analytics.mathexp.model.DTNumber
import it.prz.jmatrw4analytics.mathexp.model.Var
import it.prz.jmatrw4analytics.mathexp.model.Const

sealed abstract class ExprValue;
case class DoubleExprValue(value:Double) extends ExprValue;
case class BoolExprValue(value:Boolean) extends ExprValue;

class MathExpEvaluator {
   
   var exproot : ExpSymbol = null;
   var vars : Set[String] = null;
   
   /**
    * It takes in input the mathematical expression.
    */
   def this(sexpression: String) {
     this();
     val parsedExp = MathParser.parseAll(MathParser.exp, sexpression);
     if (parsedExp.successful == false)
       throw new IllegalArgumentException
     exproot = parsedExp.get;
     
     var vars = collection.mutable.Set[String]()
     this._buildListOfVariables(exproot, vars);
     this.vars = vars.toSet;
   }//EndConstructor.
   
   def getSetOfVariables() : Set[String] = {
     return vars;
   }//EndFunction.
  
   /**
    * This method retrieves the variables used with the expression.
    * It navigates the syntactic tree and extracts all the variable names.
    */
   private def _buildListOfVariables(exp : ExpSymbol, varSet : collection.mutable.Set[String]) {
       exp match {
         case sym : Const => ;
         case sym : Var => varSet += sym.name;
         case sym : ExpOperator => {
           _buildListOfVariables(sym.x, varSet);
           _buildListOfVariables(sym.y, varSet);
         }
         case _ => throw new IllegalArgumentException;
       }
   }//EndFunction.
   
   def evaluate(varTable : Map[String, Double]) : DTAny = {
     return evaluate(exproot, varTable);  
   }
   
   def evaluate(exp : ExpSymbol, varTable : Map[String, Double]) : DTAny = {
     exp match {
       case sym : Const => return new DTNumber(sym.value);
       case sym : Var => return new DTNumber(varTable.find( p => p._1.equals(sym.name)).get._2);
       case sym : ExpOperator => {
         val num1 = evaluate(sym.x, varTable);
         val num2 = evaluate(sym.y, varTable);
         (num1, num2, sym) match {
           case (num1:DTNumber, num2:DTNumber, op:ExpMathOperator)  => op.eval(num1, num2);
           case (num1:DTNumber, num2:DTNumber, op:ExpLogicOperator) => op.eval(num1, num2);
           case _ => throw new IllegalArgumentException
         }
       }
       case _  => throw new IllegalArgumentException;
     }
   }//EndMethod.
   
}//EndClass.