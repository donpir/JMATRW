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
	
	
	/*public void testLittleEndian_2bytes_GetUInt() {
		byte[] bytes = new byte[] { (byte) 0xA, (byte) 0 };
		ByteArray buffer = ByteArray.wrap(bytes, ByteArrayOrder.LITTLE_ENDIAN);
		long iValue = buffer.getUInt();
		assertEquals(2826, iValue);
	}*/
	
	
	
	/*public void testLittleEndian_GetUInt2Bytes() {
		byte[] bytes = new byte[] { (byte) 0x90, (byte) 0xAB, (byte) 0x12, (byte) 0xCD };
		ByteArray buffer = ByteArray.wrap(bytes, ByteArrayOrder.LITTLE_ENDIAN);
		long iValue = buffer.getUInt();
		assertEquals(3440552848l, iValue);
	}//EndTest.*/
	
	/*public void testBigEndian_GetInt2Bytes() {
		byte[] bytes = new byte[] { (byte) 0x90, (byte) 0xAB  };
		
		long _iFirst = (long) bytes[0];
		long iFirst = (_iFirst << 8);
		long iSecond = (((long) bytes[1]));
		
		long iValue = iFirst | iSecond;
		System.out.println(iValue);
		//ByteArray buffer = ByteArray.wrap(bytes, ByteArrayOrder.BIG_ENDIAN);
		//long iValue = buffer.getInt();
		//assertEquals(37035, iValue);
	}//EndTest.*/
	
	/*public void testBigEndian_GetUInt2Bytes() {
		byte b0 = 0xA;
		byte b1 = 0xB;
		long mylong0 = b0;
		long mylong1 = b1;
		long sh = mylong0 << 8;
		
		long iValue = sh | mylong1;
		
		System.out.println("");
	}*/
	
	
	
	/*public void testLittleEndian_GetInt4Bytes() {
		byte[] bytes = new byte[] { (byte) 0x90, (byte) 0xAB, (byte) 0x12, (byte) 0xCD };
		ByteArray buffer = ByteArray.wrap(bytes, ByteArrayOrder.LITTLE_ENDIAN);
		long iValue = buffer.getInt();
		assertEquals(3440552848l, iValue);
	}//EndTest.
	
	public void testBigEndian_GetInt4Bytes() {
		byte[] bytes = new byte[] { (byte) 0x90, (byte) 0xAB, (byte) 0x12, (byte) 0xCD };
		ByteArray buffer = ByteArray.wrap(bytes, ByteArrayOrder.BIG_ENDIAN);
		long iValue = buffer.getInt();
		assertEquals(2427130573l, iValue);
	}//EndTest.*/
	
	/*public void testByteOrder() {
		byte[] bytes = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7 };
		
		ByteBuffer bb = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN);
		System.out.printf("expected: %d, actual: %d, Equals: %b\n", bb.get(7), 7, bb.get(7) == 7 );
		System.out.printf("expected: %d, actual: %d, Equals: %b\n", bb.get(3), 3, bb.get(3) == 3);
		System.out.printf("Integer: %d\n", bb.getInt());
		
		bb.order(ByteOrder.LITTLE_ENDIAN).flip();
		System.out.printf("expected: %d, actual: %d, Equals: %b\n", bb.get(7), 7, bb.get(7) == 7);
		System.out.printf("expected: %d, actual: %d, Equals: %b\n", bb.get(3), 3, bb.get(3) == 3);
		System.out.printf("Integer: %d\n", bb.getInt());
	}//EndTest.*/
	
}//EndClass.
