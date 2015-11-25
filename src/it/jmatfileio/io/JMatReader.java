package it.jmatfileio.io;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * This class provides basic input stream reading functions, 
 * such as, read bytes. It deals with the big endian/little endian 
 * encoding differences. 
 * @author Donato Pirozzi - donatopirozzi@gmail.com
 */
public class JMatReader {

	private InputStream _is = null;
	private ByteOrder endianEncoding = ByteOrder.BIG_ENDIAN; 
	
	public JMatReader(InputStream is) { this._is = is; }//EndConstructor.

	public ByteOrder getEndianEncoding() { return endianEncoding; }
	public void setEndianEncoding(ByteOrder endianEncoding) { this.endianEncoding = endianEncoding; }

	/*public byte[] readBytes(int numOfBytes) throws IOException {
		if (numOfBytes <= 0) throw new IllegalArgumentException("Number of bytes to read can not be negative or zero.");
		if (numOfBytes % 4 != 0) throw new IllegalArgumentException("Number of bytes must be multiple of 4.");
		
		byte[] bytes = new byte[numOfBytes]; 
		int iNumOfRedBytes = _is.read(bytes); 
		if (iNumOfRedBytes != numOfBytes) {
			String _errMsg = String.format("Number of read bytes [%d] is lesser then expected [%d].", iNumOfRedBytes, numOfBytes);
			throw new IllegalArgumentException(_errMsg);
		}
		
		//Word swapping required. See IEEE Little Endian (PC, 386, 486, DEC Risc).
		if (endianEncoding == ByteOrder.LITTLE_ENDIAN) { 
			
		}
		
		return bytes;
	}//EndMethod.*/
	
	public ByteBuffer readBytes(int numOfBytes) throws IOException {
		if (numOfBytes <= 0) throw new IllegalArgumentException("Number of bytes to read can not be negative or zero.");
		if (numOfBytes % 4 != 0) throw new IllegalArgumentException("Number of bytes must be multiple of 4.");
		
		byte[] bytes = new byte[numOfBytes]; 
		int iNumOfRedBytes = _is.read(bytes); 
		if (iNumOfRedBytes != numOfBytes) {
			String _errMsg = String.format("Number of read bytes [%d] is lesser then expected [%d].", iNumOfRedBytes, numOfBytes);
			throw new IllegalArgumentException(_errMsg);
		}
		
		return ByteBuffer.wrap(bytes).order(endianEncoding);
	}//EndMethod.
	
	public InputStream getInputStream() {
		return _is;
	}
	
}//EndClass.
