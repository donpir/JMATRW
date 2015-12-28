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

package it.prz.jmatrw.tests.bitwise;

import it.prz.jmatrw.utils.BitWise;
import junit.framework.TestCase;

public class BitWiseTestCase extends TestCase {

	public void testAnd() {
		byte[] buffer = new byte[] { 1, 1 };
		BitWise bits = new BitWise(buffer);
		
		BitWise bitsResult = bits.and(new byte[] { 1 });
		assertEquals(2, bitsResult.getLength());
		assertEquals(1, bitsResult.getByte(0));
		assertEquals(0, bitsResult.getByte(1));
	}//EndTest.
	
	public void testSetOnes() {
		//## Starting from ZERO.
		BitWise bits = BitWise.setOnes(0, 5);
		assertEquals(1, bits.getLength());
		byte firstByte = bits.getByte(0);
		assertEquals(31, firstByte);
		
		bits = BitWise.setOnes(0, 8);
		assertEquals(1, bits.getLength());
		firstByte = bits.getByte(0);
		assertEquals(-1, firstByte); //-1 in byte is 0xFF.
		
		bits = BitWise.setOnes(0, 9);
		assertEquals(2, bits.getLength());
		assertEquals(-1, bits.getByte(0)); //-1 in byte is 0xFF.
		assertEquals(1, bits.getByte(1)); //-1 in byte is 0xFF.
		
		//## Starting from ONE.
		bits = BitWise.setOnes(1, 2);
		assertEquals(1, bits.getLength());
		assertEquals(6, bits.getByte(0));
		
		//## Starting from ONE.
		bits = BitWise.setOnes(2, 2);
		assertEquals(1, bits.getLength());
		assertEquals(6, bits.getByte(0));
	}//EndTest.
	
	public void testShift() {
		byte[] buffer = new byte[] { 1 };
		BitWise bits = new BitWise(buffer);
		assertEquals(1, bits.getByte(0)); 
		BitWise result = bits.shiftLeft();
		assertEquals(2, result.getByte(0)); 
		
		buffer = new byte[] { 64 };
		bits = new BitWise(buffer);
		assertEquals(64, bits.getByte(0)); 
		result = bits.shiftLeft();
		assertEquals(-128, result.getByte(0)); //-128 is 0x80.
		
		buffer = new byte[] { 0, -128 };
		bits = new BitWise(buffer);
		assertEquals(2, bits.getLength());
		result = bits.shiftLeft();
		assertEquals(1, result.getByte(0));
		assertEquals(0, result.getByte(1)); 
	}//EndTest.
	
}//EndClass.
