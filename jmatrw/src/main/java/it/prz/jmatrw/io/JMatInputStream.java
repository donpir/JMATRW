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
import java.util.zip.InflaterInputStream;

import it.prz.jmatrw.io.DataElement.DEType;
import it.prz.jmatrw.io.seekable.ISeekable;
import it.prz.jmatrw.io.seekable.Seeker;
import it.prz.jmatrw.matdatatypes.MLDataType;
import it.prz.jmatrw.matdatatypes.UnknownMLDataTypeException;
import it.prz.jmatrw.utils.ByteArray;
import it.prz.jmatrw.utils.ByteArray.ByteArrayOrder;

/**
 * This class provides basic input stream reading functions, 
 * such as, read bytes. It deals with the big endian/little endian 
 * encoding differences. 
 * @author Donato Pirozzi - donatopirozzi@gmail.com
 */
public class JMatInputStream implements ISeekable {

	private InputStream _is = null;
	private ByteArrayOrder endianEncoding = ByteArrayOrder.BIG_ENDIAN;
	
	private long lHeadBytePosition = 0;
	
	/*
	 * Retrieve the number of bytes read until now.
	 * @return Number of total bytes read.
	 */
	public long getHeadBytePosition() { return lHeadBytePosition; }
	
	public JMatInputStream(InputStream is) { 
		this._is = is;
		if (is == null)
			throw new IllegalArgumentException("The InputStream given to the Reader is null.");
	}//EndConstructor.

	public ByteArrayOrder getEndianEncoding() { return endianEncoding; }
	public void setEndianEncoding(ByteArrayOrder endianEncoding) { this.endianEncoding = endianEncoding; }
	
	/**
	 * 
	 * @param numOfBytes
	 * @return An array of bytes, null for end of file.
	 * @throws IOException
	 */
	public ByteArray readBytes(int numOfBytes) throws IOException {
		if (numOfBytes <= 0) throw new IllegalArgumentException("Number of bytes to read can not be negative or zero.");
		if (numOfBytes % 4 != 0) throw new IllegalArgumentException("Number of bytes must be multiple of 4.");
		
		byte[] bytes = new byte[numOfBytes]; 
		int iNumOfRedBytes = _is.read(bytes); 
		
		if (iNumOfRedBytes < 0) {//EOF.
			return null;
		}
		
		if (iNumOfRedBytes != numOfBytes) {
			String _errMsg = String.format("Number of read bytes [%d] is lesser then expected [%d].", iNumOfRedBytes, numOfBytes);
			throw new IllegalArgumentException(_errMsg);
		}
		
		lHeadBytePosition += iNumOfRedBytes; //Updates the number of bytes read.
		
		return ByteArray.wrap(bytes, endianEncoding);
	}//EndMethod.
	
	public DataElement readDataElementHeader() throws IOException {
		DataElement dataElement = new DataElement();
		long iDataType = readBytes(4).getUInt32();
		
		/* Mat file has a standard Data Element and a Small Data Element.
		   Here, these lines are to check if the element is small. From the MATLAB doc:
		   "you can tell if you are processing a small data element by
		   comparing the value of the first 2 bytes of the tag with the value zero (0). If these 2 bytes
		   are not zero, the tag uses the small data element format". */
		boolean bSmallDataElement = (iDataType & 0xff0000) != 0;
		
  		if (bSmallDataElement == true) { //Process Small Element Data.
			//Take bytes 3 and 4 which contain the data type.
			dataElement.dataType = MLDataType.dataTypeFromValue((int) iDataType & 0xffff);
			//Take bytes 1 and 2, that contain the number of bytes to read.
			dataElement.numOfBytesBody = iDataType >> 16; 
			if (dataElement.numOfBytesBody < 4) dataElement.numOfBytesBody = 4;
			//Store the Data Element Type.
			dataElement.dataElementType = DEType.SMALL;
		} else { //Standard Element data.
			dataElement.dataType = MLDataType.dataTypeFromValue((int) iDataType); 
			dataElement.numOfBytesBody = readBytes(4).getUInt32();	
		}
		
		if (dataElement.dataType == null)
			throw new UnknownMLDataTypeException("Unknown Data Type with index " + iDataType);
		
		return dataElement;
	}//EndMethod.
	
	/**
	 * It allows the change of the internal input stream, 
	 * for instance to support a GZipInputStream at some point.
	 * @param is
	 * @throws IOException 
	 */
	public void switchToGZipInputStream() throws IOException {
		_is = new InflaterInputStream(_is);
	}//EndMethod.
	
	public boolean seek(long lBytePos, Seeker seeker) throws IOException {
		boolean success = seeker.seekTo(lBytePos, _is);
		if (success) {
			lHeadBytePosition = lBytePos;
			return true;
		} 
		
		return false;
	}//EndMethod.
	
	/**
	 * Release resources, in particular it closes the InputStream.
	 * @throws IOException
	 */
	public void close() throws IOException {
		if (_is != null)
			_is.close();
	}//EndMethod.
	
}//EndClass.
