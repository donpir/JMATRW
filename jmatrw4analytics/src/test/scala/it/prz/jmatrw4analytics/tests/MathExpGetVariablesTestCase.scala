package it.prz.jmatrw4analytics.tests

import junit.framework.TestCase
import org.junit.Assert._
import it.prz.jmatrw4analytics.mathexp.MathExpEvaluator

class MathExpGetVariablesTestCase extends TestCase {
  
  def testRetrieveVariables() {
    val formula = "Peak > 90";
    val evaluator = new MathExpEvaluator(formula); 
    val vars = evaluator.getSetOfVariables();
    assertTrue(vars.contains("Peak"));
    assertFalse(vars.contains("peak"));
  }//EndTest.
  
}//EndClass.