package it.jmatrw.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import it.jmatrw.DataElement;
import it.jmatrw.JMATData;
import it.jmatrw.matdatatypes.MLArrayTypeClass;
import it.jmatrw.matdatatypes.MLDataType;
import it.jmatrwio.utils.ByteArray.ByteArrayOrder;
import junit.framework.Assert;

/**
 * 
 * @author Donato Pirozzi - donatopirozzi@gmail.com
 */
public class JMatControl {

	private JMatReader _reader = null;
	private JMATData mldata = new JMATData();
	
	public JMatControl(InputStream is) { _reader = new JMatReader(is); }//EndConstructor.
	
	public JMATData read() throws IOException {
		readHeader();
		
		readElementData();
		
		return mldata;
	}//EndMethod.
	
	private int readElementData() throws IOException {
		int dataType = (int) _reader.readBytes(4).getUInt32();
		int iNumOfBytes = (int) _reader.readBytes(4).getUInt32();
		
		if (dataType == MLDataType.miMATRIX.value) {
			//Read the ArrayFlag Subelement.
			DataElement _arrFlag = _reader.readDataElementHeader();
			assert (_arrFlag.dataType == MLDataType.miUINT32);
			assert (_arrFlag.numOfBytesBody == 8); //size is 8 bytes = [miUINT32 * 2]
			
			byte[] bArrFlag = _reader.readBytes(MLDataType.miUINT32.bytes).arrayEndian();
			_reader.readBytes(MLDataType.miUINT32.bytes); //Undefined 4 bytes in the flag data.
			byte lflag = bArrFlag[2];
			byte lclass = bArrFlag[3];
			MLArrayTypeClass arrayTypeClass = MLArrayTypeClass.dataTypeFromValue(lclass);
			
			//Dimensions Array Subelement.
            DataElement _dimArray = _reader.readDataElementHeader();
			assert (_dimArray.dataType == MLDataType.miINT32);
			int numOfArrayDims = _dimArray.numOfBytesBody / 4;
			assert (numOfArrayDims >= 2);//A Matlab array has at least two dimensions.
			
			//TODO: it works only for array of 2 dimensions.
			_reader.readBytes(4).getUInt32();
			_reader.readBytes(4).getUInt32();
			
			//Array name.
			DataElement _arrName = _reader.readDataElementHeader();
			assert (_dimArray.dataType == MLDataType.miINT8);
			byte[] _bArrName = _reader.readBytes(_arrName.numOfBytesBody).arrayEndian();
			String s = new String(_bArrName);
			
			//Read the numbers.
			DataElement _arrCells = _reader.readDataElementHeader();
			
			int iRedBytes = 0;
			while (iRedBytes < _arrCells.numOfBytesBody) {
				//Read the next number.
				double value = _reader.readBytes(_arrCells.dataType.bytes).getDouble();
				System.out.println(value);
			}
			
			//TODO: read here the doubles.
			System.out.println();
			
		} else if (dataType == MLDataType.miUINT32.value) {
			/*int iNumBytesToRead = MLDataType.dataTypeFromValue(dataType).bytes;
			ByteBuffer buffer = _reader.readBytes(iNumBytesToRead);
 			long value = buffer.getInt();
			System.out.printf("Value: %d\n", value);
			return iNumBytesToRead;*/
		}
		
		System.out.println();
		return 0;
	}//EndMethod.
	
	private void readHeader() throws IOException {
		//Header Text Field.
		byte[] bArr = _reader.readBytes(116).arrayEndian();
		mldata.header = new String(bArr);
		
		//Header Subsystem Data Offset Field.
		_reader.readBytes(8);
		
		//Header Flag Fields.
		bArr = _reader.readBytes(4).arrayEndian();
		mldata.version = bArr[0] << 8 | bArr[1];
		String endianIndicator = new String( new byte[] { bArr[2], bArr[3] }); //TODO: This I think can be replaced with new String();
		if (endianIndicator.equalsIgnoreCase("IM")) _reader.setEndianEncoding(ByteArrayOrder.LITTLE_ENDIAN);
	}//EndMethod.
	
}//EndClass.
