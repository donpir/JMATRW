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

package it.prz.jmatrw;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import it.prz.jmatrw.io.JMATParser;
import it.prz.jmatrw.matdatatypes.MLDataType;
import it.prz.jmatrw.utils.ByteArray;

public class JMATReader extends JMATParser {
		
	public JMATReader(InputStream is) throws IOException {
		super(is);
		this.init();
	}//EndConstructor.
	
	/**
	 * It read the next element of an array.
	 * @throws IOException 
	 */
	public double next() throws IOException {
		if (getInfo().dataType != JMATInfo.DataType.ARRAY_DOUBLE)
			throw new IllegalStateException("Expected an array of doubles.");
		
		MLDataType dt = MLDataType.miDOUBLE;
		ByteArray arrData = _input.readBytes(dt.bytes);
		if (arrData == null) throw new EOFException(); //EOF.

		double value = arrData.getDouble();
		return value;
	}//EndConstructor.
	
	public boolean hasNext() {
		long curBytePos = _input.getHeadBytePosition();
		long startContentBytePos = this.lByteContentPosition;
		long totContentBytes = this.getInfo().dataNumOfItems * MLDataType.miDOUBLE.bytes; 
		
		return (curBytePos < startContentBytePos + totContentBytes);
	}//EndConstructor.

}//EndClass.
