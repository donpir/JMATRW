package it.prz.jmatrw.tests;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;

import it.prz.jmatrw.JMATInfo;
import it.prz.jmatrw.JMATReader;
import junit.framework.TestCase;

public class TC02ReadArraysOfDoubleTestCase extends TestCase {

	public void testVectorRow8() throws IOException {
		//Open the file.
		InputStream fis = TestCase.class.getResourceAsStream("/tc02Compressed/vecRow8.mat");
		assertNotNull(fis);
		
		//Create the reader.
		JMATReader reader = new JMATReader(fis);
		JMATInfo mdata = reader.getInfo();
		assertTrue(mdata.dataNumOfItems == 8);
		assertEquals("vecRow8", mdata.dataName);
		
		AssertNextEqualsTo(reader, -39.08);
		AssertNextEqualsTo(reader, -62.07);
		AssertNextEqualsTo(reader, -61.31);
		AssertNextEqualsTo(reader, 36.44);
		AssertNextEqualsTo(reader, -39.45);
		AssertNextEqualsTo(reader, 8.33);
		AssertNextEqualsTo(reader, -69.83);
		AssertNextEqualsTo(reader, 39.58);
	}//EndTest.
	
	public void testVectorRow8Sum() throws IOException {
		//Open the file.
		InputStream fis = TestCase.class.getResourceAsStream("/tc02Compressed/vecRow8.mat");
		assertNotNull(fis);
		
		//Create the reader.
		JMATReader reader = new JMATReader(fis);
		JMATInfo mdata = reader.getInfo();
		assertTrue(mdata.dataNumOfItems == 8);
		assertEquals("vecRow8", mdata.dataName);
		
		BigDecimal sum = new BigDecimal(0);
		int count = 0;
		while (reader.hasNext()) {
			double value = reader.next();
			BigDecimal bigValue = new BigDecimal(value);
			sum = sum.add(bigValue);
			count++;
		}
		
		assertTrue(count == 8);
		//Here the round is important for the error on precision.
		assertEquals(-187.3900, sum.round(MathContext.DECIMAL32).doubleValue());
	}//EndTest.
	
	public void AssertNextEqualsTo(JMATReader reader, double value) throws IOException {
		assertTrue(reader.hasNext());
		double nextValue = reader.next();
		
		//Rounds the double value.
		/*DecimalFormat decFormat = new DecimalFormat(sformat);
		decFormat.setRoundingMode(RoundingMode.HALF_DOWN);
		String sdouble = decFormat.format(nextValue);
		nextValue = Double.parseDouble(sdouble);*/
		
		assertTrue(nextValue == value);
	}//EndMethod.
	
}//EndClass.
