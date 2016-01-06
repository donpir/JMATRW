package it.prz.jmatrw.bytearray.littleendian;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import it.prz.jmatrw.utils.ByteArray;
import it.prz.jmatrw.utils.ByteArray.ByteArrayOrder;
import junit.framework.TestCase;

public class ByteArrayGetUInt32TestCase extends TestCase {

	public void testValue57600() {
		byte[] buffer = new byte[] {0, -31, 0, 0};
		ByteArray ba = ByteArray.wrap(buffer, ByteArrayOrder.LITTLE_ENDIAN);
		long value = ba.getUInt32();
		
		value = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getInt();
		assertTrue(value > 0);
	}//EndTest.
	
}//EndClass.
