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

package it.prz.jmatrw.utils;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.BitSet;

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
			throw new IllegalArgumentException("In order to get an int you need <= 4 bytes.");
	
		long value = 0;
		for (int i=0; i<_buffer.length; i++) {
			long lUlong = ((long) _buffer[i]); //Convert the byte in a long to have enough space for shifts.
			
			long shift = _endianEncoding == ByteArrayOrder.BIG_ENDIAN ? (_buffer.length - i - 1) * 8 : i * 8;  
			value = value | lUlong << shift; //Precedence: first SHIFT (<<) then OR (|)
		}
		return value;
	}//EndMethod.
	
	public double getDouble() {
		if (_buffer.length != 8)
			throw new IllegalArgumentException("In order to get a double you meed exactly 8 bytes.");
		
		//We use the ByteBuffer class utility.
		double dvalue = ByteBuffer.wrap(this._buffer).order(ByteOrder.LITTLE_ENDIAN).getDouble();
		if (_buffer.length == 8) return dvalue;
		
		//This number is on 64bits encoded with IEEE754 double format.
		byte[] bIEEE754Double = this.arrayEndian();
				
		
		//It is a bit.
		byte bSign = (byte) (bIEEE754Double[0] & 0x80); //0x80 = (10000000)
		
		//Store sequence of bits in a big integer to perform operations.
		BitWise bits = new BitWise(bIEEE754Double);
		
		//Extract the Mantissa bits.
		BitWise maskMantissa = BitWise.setOnes(12, 52);
		BitWise bitsMantissa = bits.and(maskMantissa);
		
		//Extract the Exponent bits.
		BitWise maskExponent = BitWise.setOnes(1, 11);
		BitWise bitsExponent = bits.and(maskExponent);
		bitsExponent = bitsExponent.shiftLeft();
		
		
		
		long bMantissa =  bIEEE754Double[7] 
				       |  bIEEE754Double[6] << 8 
				       |  bIEEE754Double[5] << 16 
					   |  bIEEE754Double[4] << 24 
					   |  bIEEE754Double[3] << 32
					   |  bIEEE754Double[2] << 40
					   | (bIEEE754Double[1] & 0x0F) << 44;
	
		long bT1 =  (bIEEE754Double[1] & 0xF0) >> 4;
		long bT2 = (bIEEE754Double[0] & 0x7F) << 4;
		
		
		long bExponent = (bIEEE754Double[1] & 0xF0) >> 4 
				       | (bIEEE754Double[0] & 0x7F) << 4; //0x7F = (01111111)
		bExponent = bExponent - 1023;
		
		double lMantissa = 0;
		for (int i=1; i<=52; i++) {
			//Get the i-simo bit of the Mantissa.
			long bidx = 1 << (52 - i);
			long bi = bMantissa & bidx;
			
			//Divide the bit bi for 2^-i. It means bi shifted to right by i.
			long div = (long) Math.pow(2, i);
			div = div == 0 ? 1 : div;
			long ltmp = bi / div;
			
			lMantissa += ltmp;
		}
		
		double value = (1 + lMantissa) * (long) Math.pow(2, bExponent);
		
		
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
