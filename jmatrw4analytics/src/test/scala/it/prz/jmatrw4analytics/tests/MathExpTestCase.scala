package it.prz.jmatrw4analytics.tests

import org.junit.Assert._
import org.junit.Assert.assertTrue
import it.prz.jmatrw4analytics.mathexp.MathParser
import junit.framework.TestCase
import it.prz.jmatrw4analytics.mathexp.MathExpEvaluator
import it.prz.jmatrw4analytics.mathexp.model.DTBool

class MathExpTestCase extends TestCase {
    
   def testLogicGreaterThan() {
     val formula = "Peak > 90";
     val variables = Map[String, Double]("Peak" -> 100);
     
     val expres = new MathExpEvaluator(formula).evaluate(variables);
     expres match {
       case res : DTBool => assertTrue(res.value == true);
       case _ => fail();
     }
   }//EndTest.
   
   def testMathExp01() {
     val formula = "grossProfit / revenue * 100 > 50";
     val variables = Map[String, Double]("revenue" -> 100, "grossProfit" -> 100);
     
     val expres = new MathExpEvaluator(formula).evaluate(variables);
     expres match {
       case res : DTBool => assertTrue(res.value)
       case _ => fail();
     }
   }//EndTest.
   
    def testMathExp02() {
     val formula = "grossProfit / revenue * 100 > 50";
     val variables = Map[String, Double]("revenue" -> 100, "grossProfit" -> 1);
     
     val expres = new MathExpEvaluator(formula).evaluate(variables);
     expres match {
       case res : DTBool => assertFalse(res.value)
       case _ => fail();
     }
   }//EndTest.
    
    def testFormulaWithConstValues() {
     val filterFormula : String = "Peak > mean - 5 * stddev";
     val variables = Map[String, Double]("Peak" -> 10, "mean" -> 10, "stddev" -> 0);
     
     val expres = new MathExpEvaluator(filterFormula).evaluate(variables);
     expres match {
       case res : DTBool => assertFalse(res.value)
       case _ => fail();
     }
   }
    
   /*def testFormulaWithConstValues() {
     val filterFormula : String = "Peak > mean - 4,7 * stddev";
     val variables = Map[String, Double]("Peak" -> 10, "mean" -> 10, "stddev" -> 0);
     
     val expres = new MathExpEvaluator(filterFormula).evaluate(variables);
     expres match {
       case res : DTBool => assertFalse(res.value)
       case _ => fail();
     }
   }*/
   
}//EndClass