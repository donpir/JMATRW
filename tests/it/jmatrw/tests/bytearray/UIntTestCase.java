package it.jmatrw.tests.bytearray;

import it.jmatrwio.utils.ByteArray;
import it.jmatrwio.utils.ByteArray.ByteArrayOrder;
import junit.framework.TestCase;

/**
 * 
 * @author Donato Pirozzi - donatopirozzi@gmail.com
 */
public class UIntTestCase extends TestCase {

	/**
	 * 2 BYTES converted to UNSIGNED INT (Big Endian encoding).
	 */
	public void testBigEndian_2bytes_GetUIntA() {
		byte[] bytes = new byte[] { (byte) 0, (byte) 0xA };
		ByteArray buffer = ByteArray.wrap(bytes, ByteArrayOrder.BIG_ENDIAN);
		long iValue = buffer.getUInt32();
		assertEquals(10, iValue);
	}//EndTest.
	
	/**
	 * 2 BYTES converted to UNSIGNED INT (Big Endian encoding).
	 */
	public void testBigEndian_2bytes_GetUIntB() {
		byte[] bytes = new byte[] { (byte) 0xB, (byte) 0xA };
		ByteArray buffer = ByteArray.wrap(bytes, ByteArrayOrder.BIG_ENDIAN);
		long iValue = buffer.getUInt32();
		assertEquals(2826, iValue);
	}//EndTest.
	
	/**
	 * 2 BYTES converted to UNSIGNED INT (Little Endian encoding).
	 */
	public void testLittleEndian_2bytes_GetUIntA() {
		byte[] bytes = new byte[] { (byte) 0, (byte) 0xA };
		ByteArray buffer = ByteArray.wrap(bytes, ByteArrayOrder.LITTLE_ENDIAN);
		long iValue = buffer.getUInt32();
		assertEquals(2560, iValue);
	}//EndTest.
	
	/**
	 * 2 BYTES converted to UNSIGNED INT (Little Endian encoding).
	 */
	public void testLittleEndian_2bytes_GetUIntB() {
		byte[] bytes = new byte[] { (byte) 0xB, (byte) 0xA };
		ByteArray buffer = ByteArray.wrap(bytes, ByteArrayOrder.LITTLE_ENDIAN);
		long iValue = buffer.getUInt32();
		assertEquals(2571, iValue);
	}//EndTest.
	
}//EndClass.
