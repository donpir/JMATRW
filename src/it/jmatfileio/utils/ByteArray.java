package it.jmatfileio.utils;

/**
 * 
 * @author Donato Pirozzi - donatopirozzi@gmail.com
 */
public class ByteArray {

	public enum ByteArrayOrder { BIG_ENDIAN, LITTLE_ENDIAN };
	
	private byte[] _buffer = null;
	private ByteArrayOrder _endianEncoding = ByteArrayOrder.BIG_ENDIAN;
	
	private ByteArray(byte[] buffer) {
		this._buffer = buffer;
	}//EndConstructor.
	
	public static ByteArray wrap(byte[] buffer) { return new ByteArray(buffer); }//EndMethod.
	
	public void setEndianEncoding(ByteArrayOrder order) { this._endianEncoding = order; }
	
}//EndClass.
