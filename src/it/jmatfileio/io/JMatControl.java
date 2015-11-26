package it.jmatfileio.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import it.jmatfileio.JMATData;
import it.jmatfileio.matdatatypes.MLArrayTypeClass;
import it.jmatfileio.matdatatypes.MLDataType;
import it.jmatfileio.utils.ByteArray.ByteArrayOrder;

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
			//Array Flags format.
			_reader.readDataElementHeader();
			byte[] bArrFlag = _reader.readBytes(MLDataType.miUINT32.bytes).arrayEndian();
			_reader.readBytes(MLDataType.miUINT32.bytes); //Undefined 4 bytes in the flag data.
			byte lflag = bArrFlag[2];
			byte lclass = bArrFlag[3];
			MLArrayTypeClass arrayTypeClass = MLArrayTypeClass.dataTypeFromValue(lclass);
			
			//Dimensions Array Subelement.
			_reader.readBytes(MLDataType.miINT32.bytes).getInt32();
			
			//Array name.
			byte[] arrName =_reader.readBytes(MLDataType.miINT8.bytes).arrayEndian();
			String sArrayName = new String(arrName);
			
			System.out.println("Class" + lclass);
			
			//long lFlag = _reader.readBytes(MLDataType.miUINT32.bytes).array();
			//long lClass = _reader.readBytes(MLDataType.miUINT32.bytes).getLong();
			
			/*ByteBuffer bArrFlag = _reader.readBytes(MLDataType.miUINT32.bytes).flip().array();
			byte bflags = bArrFlag.get;
			int iClass = bArrFlag.get(3);*/
			
			/*byte[] bArrFlag = new byte[8]; 
			_reader.getInputStream().read(bArrFlag);
			
			byte[] bArrFlags = _reader.readBytes(MLDataType.miUINT32.bytes * 2).array();
			byte bflags = bArrFlags[2];
			int iClass = bArrFlags[3];*/
			
			//System.out.printf("iClass " + lClass);
			
			/*int iNumBytesRed = 0;
			while (iNumBytesRed < iNumOfBytes) {
				iNumBytesRed += readElementData();
			}//EndWhile.
			
			return iNumBytesRed;*/
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
