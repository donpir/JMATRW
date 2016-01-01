/*
 * (C) Copyright 2015 Donato Pirozzi <donatopirozzi@gmail.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 3 which accompanies this distribution (See the COPYING.LESSER
 * file at the top-level directory of this distribution.), and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Donato Pirozzi
 */

package it.prz.jmatrw.io;

import java.io.IOException;
import java.io.InputStream;

import it.prz.jmatrw.JMATInfo;
import it.prz.jmatrw.JMATInfo.DataType;
import it.prz.jmatrw.io.DataElement.DEType;
import it.prz.jmatrw.io.seekable.ISeekable;
import it.prz.jmatrw.io.seekable.Seeker;
import it.prz.jmatrw.matdatatypes.MLArrayTypeClass;
import it.prz.jmatrw.matdatatypes.MLDataType;
import it.prz.jmatrw.matdatatypes.UnknownMLDataTypeException;
import it.prz.jmatrw.utils.ByteArray.ByteArrayOrder;

/**
 * 
 * @author Donato Pirozzi - donatopirozzi@gmail.com
 */
public abstract class JMATParser implements ISeekable {

	/*private enum ReaderStatus {
		UNDEFINED, ERROR, INIT, NEXT_DATA, END
	}*/
	
	protected JMatInputStream _input = null;
	private JMATInfo matinfo = new JMATInfo();
	protected long lByteContentPosition = 0;
	//private ReaderStatus _status = ReaderStatus.INIT;
	
	public JMATParser(InputStream is) { _input = new JMatInputStream(is); }//EndConstructor.
	
	public JMATInfo getInfo() { return matinfo; }
	
	/*
	 * It reads the entire *.mat file content. 
	 * NOTE: this method should not used for huge *.mat files; use instead {@link #init() init},
	 * {@link #next() next}, {@link #hasNext() hasNext} methods. 
	 * @return
	 * @throws IOException
	 */
	/*public JMATData readAll() throws IOException {
		this.init();
		
		if (mldata.dataType == DataType.ARRAY_DOUBLE) 
			readEntireArrayContent(); 
		
		return mldata;
	}//EndMethod.*/
	
	protected JMATInfo init() throws IOException {
		readMATVersion();
		readDataHeader();
		lByteContentPosition = _input.getHeadBytePosition();
		//_status = ReaderStatus.NEXT_DATA;
		
		return matinfo;
	}//EndMethod.
	
	public boolean seek(long lBytePos, Seeker seeker) throws IOException {
		return _input.seek(lBytePos, seeker);
	}//EndMethod.
	
	/**
	 * It read the next element of an array.
	 * @throws IOException 
	 */
	/*@Deprecated
	public Double next() throws IOException {
		if (mldata.dataType != JMATData.DataType.ARRAY_DOUBLE)
			throw new IllegalStateException("Expected an array of doubles.");
		
		ByteArray arrData = _input.readBytes(MLDataType.miDOUBLE.bytes);
				
		if (arrData == null) { //EOF.
			_status = ReaderStatus.END;
			return null;
		}
		
		double value = arrData.getDouble();
		return value;
	}//EndMethod.*/
	
	/*@Deprecated
	public boolean hasNext() throws IOException {
		return (_input.readBytesAvailable() > 0);
	}//EndMethod.*/
		
	private int readDataHeader() throws IOException {
		DataElement _dataItem = _input.readDataElementHeader();
		
		if (_dataItem.dataType == MLDataType.miMATRIX) {
			//Read the ArrayFlag Subelement.
			DataElement _arrFlag = _input.readDataElementHeader();
			assert (_arrFlag.dataType == MLDataType.miUINT32);
			assert (_arrFlag.numOfBytesBody == 8); //size is 8 bytes = [miUINT32 * 2]
			
			byte[] bArrFlag = _input.readBytes(MLDataType.miUINT32.bytes).arrayEndian();
			_input.readBytes(MLDataType.miUINT32.bytes); //Undefined 4 bytes in the flag data.
			byte lflag = bArrFlag[2];
			byte lclass = bArrFlag[3];
			MLArrayTypeClass arrayTypeClass = MLArrayTypeClass.dataTypeFromValue(lclass);
			
			//Dimensions Array Subelement.
            DataElement _dimArray = _input.readDataElementHeader();
			assert (_dimArray.dataType == MLDataType.miINT32);
			int numOfArrayDims = _dimArray.numOfBytesBody / 4;
			assert (numOfArrayDims >= 2);//A Matlab array has at least two dimensions.
			if (numOfArrayDims != 2)
				throw new IllegalArgumentException("This version of JMATRW is able to read only one-dimensional and two-dimensional arrays");
			
			//TODO: it works only for array of 2 dimensions.
			int dim1 = (int) _input.readBytes(4).getUInt32();
			int dim2 = (int) _input.readBytes(4).getUInt32();
			
			if (dim1 < 0 || dim2 < 0)
				throw new IllegalArgumentException("The matrix or array is too huge.");
			
			//Array name.
			DataElement _arrName = _input.readDataElementHeader();
			assert (_arrName.dataType == MLDataType.miINT8);
			
			//TODO: probably it can be moved inside the readDataElementHeader()
			if (_arrName.dataElementType == DEType.STANDARD && _arrName.numOfBytesBody % 8 != 0)
				_arrName.numOfBytesBody = (_arrName.numOfBytesBody / 8 + 1) * 8;
						
			byte[] _bArrName = _input.readBytes(_arrName.numOfBytesBody).arrayEndian();
			matinfo.dataName = new StringBuilder(new String(_bArrName)).reverse().toString().trim();
			
			//Read the numbers.
			DataElement _arrCells = _input.readDataElementHeader();
			
			if (dim1 == 1 || dim2 == 1) { //Read the array.
				matinfo.dataType = DataType.ARRAY_DOUBLE;
				if (_arrCells.dataType == MLDataType.miDOUBLE) {
					matinfo.dataNumOfItems = _arrCells.numOfBytesBody / _arrCells.dataType.bytes;
				} else {
					throw new IllegalArgumentException("Sorry, this library reads only array of double.");
				}
				
			} else { //Read the matrix.
				double[][] matrixValues = null;
				if (_arrCells.dataType == MLDataType.miDOUBLE)
					matrixValues = new double[dim1][dim2];
				
				int iRedBytes = 0;
				int idxi = 0;
				int idxj = 0;
				while (iRedBytes < _arrCells.numOfBytesBody) {
					//Read the next number.
					double value = _input.readBytes(_arrCells.dataType.bytes).getDouble();
					matrixValues[idxi][idxj] = value;
					idxi = (idxi + 1) % dim1;
					idxj = idxi == 0 ? (idxj + 1) % dim2 : idxj;
					iRedBytes += _arrCells.dataType.bytes;
				}
				
				matinfo.dataType = DataType.MATRIX_DOUBLE;
				matinfo.dataValue = matrixValues;	
			}
			
		} else if (_dataItem.dataType == MLDataType.miCOMPRESSED) {
			_input.switchToGZipInputStream(_dataItem.numOfBytesBody);
			return readDataHeader();
			
		} else { //Unknown ML data type.
			throw new UnknownMLDataTypeException("Unknown how to process MLDataType " + _dataItem.dataType.description);
		}
		
		//else if (dataType == MLDataType.miUINT32.value) {
			/*int iNumBytesToRead = MLDataType.dataTypeFromValue(dataType).bytes;
			ByteBuffer buffer = _reader.readBytes(iNumBytesToRead);
 			long value = buffer.getInt();
			System.out.printf("Value: %d\n", value);
			return iNumBytesToRead;*/
		//}
	
		return 0;
	}//EndMethod.
	
	/**
	 * It reads the array content and puts the values within 
	 * an array of JMATData. 
	 * @throws IOException
	 */
	/*private void readEntireArrayContent() throws IOException {
		double[] arrValues = new double[(int) mldata.dataNumOfItems];

		int _iCurIdx = 0;
		while (this.hasNext()) {
			Double value = this.next();
			arrValues[_iCurIdx] = value;
			_iCurIdx++;
		}
		
		mldata.dataValue = arrValues;
	}//EndMethod.*/
	
	/**
	 * It reads the mat file header and file version.
	 * The header contains the software which saved the file.
	 * @throws IOException
	 */
	private void readMATVersion() throws IOException {
		//Header Text Field.
		byte[] bArr = _input.readBytes(116).arrayEndian();
		matinfo.header = new String(bArr);
		
		//Header Subsystem Data Offset Field.
		_input.readBytes(8);
		
		//Header Flag Fields.
		bArr = _input.readBytes(4).arrayEndian();
		matinfo.version = bArr[0] << 8 | bArr[1];
		String endianIndicator = new String( new byte[] { bArr[2], bArr[3] }); //TODO: This I think can be replaced with new String();
		if (endianIndicator.equalsIgnoreCase("IM")) _input.setEndianEncoding(ByteArrayOrder.LITTLE_ENDIAN);
	}//EndMethod.
	
	public void close() throws IOException {
		_input.close();
	}//EndMethod.
	
}//EndClass.
