package it.jmatfileio.utils;

/**
 * 
 * @author Donato Pirozzi - donatopirozzi@gmail.com
 */
public class ByteArray {

	public enum ByteArrayOrder { BIG_ENDIAN, LITTLE_ENDIAN };
	
	private byte[] _buffer = null;
	private ByteArrayOrder _endianEncoding = ByteArrayOrder.BIG_ENDIAN;
	
	private ByteArray(byte[] buffer, ByteArrayOrder byteOrder) {
		this._buffer = buffer;
		this._endianEncoding = byteOrder;
	}//EndConstructor.
	
	public static ByteArray wrap(byte[] buffer, ByteArrayOrder byteOrder) { return new ByteArray(buffer, byteOrder); }//EndMethod.
	
	public long getUInt32() {
		if (_buffer.length > 4)
			throw new IllegalArgumentException("In order to get an int, you need <= 4 bytes.");
	
		long value = 0;
		for (int i=0; i<_buffer.length; i++) {
			long lUlong = ((long) _buffer[i]); //Convert the byte in a long to have enough space for shifts.
			
			long shift = _endianEncoding == ByteArrayOrder.BIG_ENDIAN ? (_buffer.length - i - 1) * 8 : i * 8;  
			value = value | lUlong << shift; //Precedence: first SHIFT (<<) then OR (|)
		}
		return value;
	}//EndMethod.
	
	public long getInt32() {
		return getUInt32();
	}//EndClass.
	
	public byte[] arrayEndian() {
		if (_endianEncoding == ByteArrayOrder.BIG_ENDIAN) return _buffer;
		
		//It swaps
		byte[] destination = new byte[_buffer.length];
		for (int i=0; i<_buffer.length; i++)
			destination[_buffer.length - i - 1] = _buffer[i];
		
		return destination;
	}//EndMethod.
	
}//EndClass.
