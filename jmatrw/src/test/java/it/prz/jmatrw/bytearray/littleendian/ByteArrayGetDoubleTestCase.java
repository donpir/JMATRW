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

package it.prz.jmatrw.bytearray.littleendian;

import it.prz.jmatrw.utils.ByteArray;
import it.prz.jmatrw.utils.ByteArray.ByteArrayOrder;
import junit.framework.TestCase;

public class ByteArrayGetDoubleTestCase extends TestCase {

	public void testValue1() {
		byte[] buffer = new byte[] { 0, 0, 0, 0, 0, 0, -16, 63 };
		ByteArray ba = ByteArray.wrap(buffer, ByteArrayOrder.LITTLE_ENDIAN);
		double value = ba.getDouble();
		assertEquals(1.0, value);
	}//EndTest.

	public void testValue2() {
		byte[] buffer = new byte[] { 0, 0, 0, 0, 0, 0, 0, 64 };
		ByteArray ba = ByteArray.wrap(buffer, ByteArrayOrder.LITTLE_ENDIAN);
		double value = ba.getDouble();
		assertEquals(2.0, value);
	}//EndTest.
	
	public void testValue3() {
		byte[] buffer = new byte[] { 0, 0, 0, 0, 0, 0, 8, 64 };
		ByteArray ba = ByteArray.wrap(buffer, ByteArrayOrder.LITTLE_ENDIAN);
		double value = ba.getDouble();
		assertEquals(3.0, value);
	}//EndTest.
	
}//EndClass.
