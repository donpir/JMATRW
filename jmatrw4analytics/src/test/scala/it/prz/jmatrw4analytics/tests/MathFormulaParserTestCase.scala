package it.prz.jmatrw4analytics.tests

import junit.framework.TestCase
import org.junit.Assert._
import it.prz.jmatrw4analytics.mathexp.MathParser

class MathFormulaParserTestCase extends TestCase  {
  
   def testVariable() {
       assertTrue(MathParser.parseAll(MathParser.variable, "revenue").successful);
       assertTrue(MathParser.parseAll(MathParser.variable, "costOfRevenue").successful);
       assertFalse(MathParser.parseAll(MathParser.variable, "100").successful);
       assertFalse(MathParser.parseAll(MathParser.variable, ">").successful);
   }//EndTest.
   
   def testConstant() {
       assertFalse(MathParser.parseAll(MathParser.constant, "revenue").successful);
       assertFalse(MathParser.parseAll(MathParser.constant, "costOfRevenue").successful);
       assertTrue(MathParser.parseAll(MathParser.constant, "100").successful);
       assertFalse(MathParser.parseAll(MathParser.constant, ">").successful);
   }//EndTest.
   
   def testOperatorsMulDiv() {
     assertTrue(MathParser.parseAll(MathParser.term, "revenue * 100").successful);
     assertTrue(MathParser.parseAll(MathParser.term, "revenue*100").successful);
     assertTrue(MathParser.parseAll(MathParser.term, "actualValue * interestRate * 100").successful);
     assertTrue(MathParser.parseAll(MathParser.term, "grossProfit / revenue * 100").successful);
     assertFalse(MathParser.parseAll(MathParser.term, "*100").successful);
     assertTrue(MathParser.parseAll(MathParser.term, "revenue").successful);
     assertTrue(MathParser.parseAll(MathParser.term, "100").successful);
     assertFalse(MathParser.parseAll(MathParser.term, "revenue*").successful);
   }
   
   def testOperatorsSumDiff() {
     assertTrue(MathParser.parseAll(MathParser.operand, "revenue + 100").successful);
     assertTrue(MathParser.parseAll(MathParser.operand, "revenue+100").successful);
     assertTrue(MathParser.parseAll(MathParser.operand, "actualValue + interestRate + 100").successful);
     assertTrue(MathParser.parseAll(MathParser.operand, "grossProfit - revenue + 100").successful);
     assertFalse(MathParser.parseAll(MathParser.operand, "+100").successful);
     assertTrue(MathParser.parseAll(MathParser.operand, "revenue").successful);
     assertTrue(MathParser.parseAll(MathParser.operand, "100").successful);
     assertFalse(MathParser.parseAll(MathParser.operand, "revenue+").successful);
   }
   
   def testOperatorsLogic() {
     assertTrue(MathParser.parseAll(MathParser.exp, "revenue > 100").successful);
     assertFalse(MathParser.parseAll(MathParser.exp, "revenue > 100 > 50").successful);
     assertTrue(MathParser.parseAll(MathParser.exp, "revenue < 100").successful);
   }
    
}//EndClass.