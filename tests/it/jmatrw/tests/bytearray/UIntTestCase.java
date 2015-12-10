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

package it.jmatrw.tests.bytearray;

import it.jmatrwio.utils.ByteArray;
import it.jmatrwio.utils.ByteArray.ByteArrayOrder;
import junit.framework.TestCase;

/**
 * 
 * @author Donato Pirozzi - donatopirozzi@gmail.com
 */
public class UIntTestCase extends TestCase {

	/**
	 * 2 BYTES converted to UNSIGNED INT (Big Endian encoding).
	 */
	public void testBigEndian_2bytes_GetUIntA() {
		byte[] bytes = new byte[] { (byte) 0, (byte) 0xA };
		ByteArray buffer = ByteArray.wrap(bytes, ByteArrayOrder.BIG_ENDIAN);
		long iValue = buffer.getUInt32();
		assertEquals(10, iValue);
	}//EndTest.
	
	/**
	 * 2 BYTES converted to UNSIGNED INT (Big Endian encoding).
	 */
	public void testBigEndian_2bytes_GetUIntB() {
		byte[] bytes = new byte[] { (byte) 0xB, (byte) 0xA };
		ByteArray buffer = ByteArray.wrap(bytes, ByteArrayOrder.BIG_ENDIAN);
		long iValue = buffer.getUInt32();
		assertEquals(2826, iValue);
	}//EndTest.
	
	/**
	 * 2 BYTES converted to UNSIGNED INT (Little Endian encoding).
	 */
	public void testLittleEndian_2bytes_GetUIntA() {
		byte[] bytes = new byte[] { (byte) 0, (byte) 0xA };
		ByteArray buffer = ByteArray.wrap(bytes, ByteArrayOrder.LITTLE_ENDIAN);
		long iValue = buffer.getUInt32();
		assertEquals(2560, iValue);
	}//EndTest.
	
	/**
	 * 2 BYTES converted to UNSIGNED INT (Little Endian encoding).
	 */
	public void testLittleEndian_2bytes_GetUIntB() {
		byte[] bytes = new byte[] { (byte) 0xB, (byte) 0xA };
		ByteArray buffer = ByteArray.wrap(bytes, ByteArrayOrder.LITTLE_ENDIAN);
		long iValue = buffer.getUInt32();
		assertEquals(2571, iValue);
	}//EndTest.
	
}//EndClass.
