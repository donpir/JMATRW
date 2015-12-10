package it.jmatrw.tests.bytearray.littleendian;

import it.jmatrwio.utils.ByteArray;
import it.jmatrwio.utils.ByteArray.ByteArrayOrder;
import junit.framework.TestCase;

public class ByteArrayGetDoubleTestCase extends TestCase {

	public void testValue1() {
		byte[] buffer = new byte[] { 0, 0, 0, 0, 0, 0, -16, 63 };
		ByteArray ba = ByteArray.wrap(buffer, ByteArrayOrder.LITTLE_ENDIAN);
		double value = ba.getDouble();
		assertEquals(1.0, value);
	}//EndTest.

	public void testValue2() {
		byte[] buffer = new byte[] { 0, 0, 0, 0, 0, 0, 0, 64 };
		ByteArray ba = ByteArray.wrap(buffer, ByteArrayOrder.LITTLE_ENDIAN);
		double value = ba.getDouble();
		assertEquals(2.0, value);
	}//EndTest.
	
	public void testValue3() {
		byte[] buffer = new byte[] { 0, 0, 0, 0, 0, 0, 8, 64 };
		ByteArray ba = ByteArray.wrap(buffer, ByteArrayOrder.LITTLE_ENDIAN);
		double value = ba.getDouble();
		assertEquals(3.0, value);
	}//EndTest.
	
}//EndClass.
