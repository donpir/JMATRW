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

import java.util.Arrays;

public class BitWise {

	private byte[] _buffer = null;
	
	public BitWise(byte[] buffer) { this._buffer = buffer; }
	
	public byte getByte(int idx) { return _buffer[idx]; }//EndMethod.
	
	public long getLength() { return _buffer.length; }//EndMethod.
	
	protected byte[] getBuffer() { return _buffer; }//EndMethod.
	
	/**
	 * NOTE: do not change the internal and the input BitWise parameter. 
	 * @param operand
	 * @return
	 */
	public BitWise and(BitWise operand) { return and(operand.getBuffer()); }
	
	/**
	 * 
	 * NOTE: it does not change the input array and internal array.
	 * @param operand Do not change the parameter array.
	 * @return
	 */
	public BitWise and(byte[] operand) {
		int maxLength = Math.max(_buffer.length, operand.length);
		int minLength = Math.min(_buffer.length, operand.length);
		byte[] buf = new byte[maxLength];

		for (int i=0; i<minLength; i++)
			buf[i] = (byte) (_buffer[i] & operand[i]);
		
		return new BitWise(buf);
	}//EndMethos.
	
	public static BitWise setOnes(int startBit, int numOfBits) {
		int length = (int) Math.ceil( (double) (startBit + numOfBits) / 8 );
		byte[] buf = new byte[length];
		
		for (int i=startBit; i<startBit+numOfBits; i++) {
			int idxByte = i / 8;
			int shift = i % 8;
			buf[idxByte] = (byte) (buf[idxByte] | 1 << shift); 			
		}
		
		return new BitWise(buf);
	}//EndMethod.
	
	public BitWise shiftLeft(int shift) {
		BitWise cur = this;
		while (shift >0) {
			cur = cur.shiftLeft();
			shift--;
		}
		return cur;
	}//EndMethod.
	
	public BitWise shiftLeft() {
		byte[] buf = Arrays.copyOf(_buffer, _buffer.length);
		
		buf[0] <<= 1;	
		for (int i=1; i<buf.length; i++) {
			byte btransfer = (byte) (buf[i] >> 7 & 1);
			buf[i-1] |= btransfer; 
			buf[i] <<= 1;
		}
		
		return new BitWise(buf);
	}//EndMethod.
	
}//EndClass.
