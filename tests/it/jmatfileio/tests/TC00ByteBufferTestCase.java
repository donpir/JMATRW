package it.jmatfileio.tests;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import junit.framework.TestCase;

/**
 * 
 * @author Donato Pirozzi - donatopirozzi@gmail.com
 */
public class TC00ByteBufferTestCase extends TestCase {

	public void testByteOrder() {
		byte[] bytes = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7 };
		
		ByteBuffer bb = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN);
		System.out.printf("expected: %d, actual: %d, Equals: %b\n", bb.get(7), 7, bb.get(7) == 7 );
		System.out.printf("expected: %d, actual: %d, Equals: %b\n", bb.get(3), 3, bb.get(3) == 3);
		System.out.printf("Integer: %d\n", bb.getInt());
		
		bb = bb.order(ByteOrder.LITTLE_ENDIAN);
		System.out.printf("expected: %d, actual: %d, Equals: %b\n", bb.get(7), 7, bb.get(7) == 7);
		System.out.printf("expected: %d, actual: %d, Equals: %b\n", bb.get(3), 3, bb.get(3) == 3);
		System.out.printf("Integer: %d\n", bb.getInt());
	}//EndTest.
	
}//EndClass.
