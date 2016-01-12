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

class MathFormulaEvaluator {
   
   var exproot : ExpSymbol = null;
   
   def this(sexpression: String) {
     this();
     val parsedExp = MathParser.parseAll(MathParser.exp, sexpression);
     if (parsedExp.successful == false)
       throw new IllegalArgumentException
     exproot = parsedExp.get;
   }
  
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