package it.jmatfileio.io;
import java.io.IOException;
import java.io.InputStream;

import it.jmatfileio.DataElement;
import it.jmatfileio.matdatatypes.MLDataType;
import it.jmatfileio.matdatatypes.UnknownMLDataTypeException;
import it.jmatfileio.utils.ByteArray;
import it.jmatfileio.utils.ByteArray.ByteArrayOrder;

/**
 * This class provides basic input stream reading functions, 
 * such as, read bytes. It deals with the big endian/little endian 
 * encoding differences. 
 * @author Donato Pirozzi - donatopirozzi@gmail.com
 */
public class JMatReader {

	private InputStream _is = null;
	private ByteArrayOrder endianEncoding = ByteArrayOrder.BIG_ENDIAN; 
	
	public JMatReader(InputStream is) { this._is = is; }//EndConstructor.

	public ByteArrayOrder getEndianEncoding() { return endianEncoding; }
	public void setEndianEncoding(ByteArrayOrder endianEncoding) { this.endianEncoding = endianEncoding; }

	public ByteArray readBytes(int numOfBytes) throws IOException {
		if (numOfBytes <= 0) throw new IllegalArgumentException("Number of bytes to read can not be negative or zero.");
		if (numOfBytes % 4 != 0) throw new IllegalArgumentException("Number of bytes must be multiple of 4.");
		
		byte[] bytes = new byte[numOfBytes]; 
		int iNumOfRedBytes = _is.read(bytes); 
		if (iNumOfRedBytes != numOfBytes) {
			String _errMsg = String.format("Number of read bytes [%d] is lesser then expected [%d].", iNumOfRedBytes, numOfBytes);
			throw new IllegalArgumentException(_errMsg);
		}
		
		return ByteArray.wrap(bytes, endianEncoding);
	}//EndMethod.
	
	public DataElement readDataElementHeader() throws IOException {
		DataElement dataElement = new DataElement();
		int iDataType = (int) readBytes(4).getUInt32();
		dataElement.dataType = MLDataType.dataTypeFromValue(iDataType); 
		dataElement.numOfBytesBody = (int) readBytes(4).getUInt32();
		
		if (dataElement.dataType == null)
			throw new UnknownMLDataTypeException("Unknown Data Type with index " + iDataType);
		
		return dataElement;
	}//EndMethod.
	
	public InputStream getInputStream() { return _is; }
	
}//EndClass.
