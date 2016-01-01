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

package it.prz.jmatrw.tests;

import java.io.IOException;
import java.io.InputStream;

import it.prz.jmatrw.JMATInfo;
import it.prz.jmatrw.JMATInfo.DataType;
import it.prz.jmatrw.JMATReader;
import junit.framework.TestCase;

/**
 * 
 * @author Donato Pirozzi - donatopirozzi@gmail.com
 */
public class TC01ReadArrayOfDoubleTestCase extends TestCase {

	/*
	 * example01_array.mat contains: x = [1, 3, 2]
	 * @throws IOException
	 */
	public void testExample01Array() throws IOException {
		//Open the file.
		InputStream fis = TestCase.class.getResourceAsStream("/tc01NoCompression/example01_array.mat");
		assertNotNull(fis);
		
		//Create the reader.
		JMATReader reader = new JMATReader(fis);
		JMATInfo matdata = reader.getInfo();
		
		//Check file version.
		assertTrue(matdata.header.contains("MATLAB"));
		assertTrue(matdata.version == 1);
		
		//Check file content.
		assertTrue(matdata.dataType == DataType.ARRAY_DOUBLE);
		assertEquals("x", matdata.dataName);
		
		//It reads the values.
		assertTrue(reader.hasNext());
		assertEquals(1.0, reader.next());
		
		assertTrue(reader.hasNext());
		assertEquals(3.0, reader.next());
		
		assertTrue(reader.hasNext());
		assertEquals(2.0, reader.next());
	}//EndTest.
	
	public void testExample01ArraySum() throws IOException {
		//Open the file.
		InputStream fis = TestCase.class.getResourceAsStream("/tc01NoCompression/example01_array.mat");
		assertNotNull(fis);
		
		//Create the reader.
		JMATReader reader = new JMATReader(fis);
		JMATInfo matdata = reader.getInfo();
		
		//Check file version.
		assertTrue(matdata.header.contains("MATLAB"));
		assertTrue(matdata.version == 1);
		
		//Check file content.
		assertTrue(matdata.dataType == DataType.ARRAY_DOUBLE);
		assertEquals("x", matdata.dataName);
		
		//It reads the values.
		int count = 0;
		double sum = 0;
		while (reader.hasNext()) {
			double value = reader.next();
			sum += value;
			count++;
		}
		
		assertTrue(count == 3);
		assertTrue(sum == 6);
	}//EndTest.
	
	/*
	 * A is a 3x3 matrix.
	 * A = [1, 1, 2; 3, 5, 8; 13, 21, 34]
	 */
	
	public void testExample03Matrix() throws IOException {
		//Open the file.
		InputStream fis = TestCase.class.getResourceAsStream("/tc01NoCompression/example02_matrix.mat");
		assertNotNull(fis);
		
		//Create the reader.
		JMATReader reader = new JMATReader(fis);
		JMATInfo matdata = reader.getInfo();
		
		//Check file version.
		assertTrue(matdata.header.contains("MATLAB"));
		assertTrue(matdata.version == 1);
		
		//Check file content.
		assertTrue(matdata.dataType == DataType.MATRIX_DOUBLE);
		assertEquals("A", matdata.dataName);
				
		//Read the entire matrix.
		double[][] arrDouble = (double[][]) matdata.dataValue;
		assertTrue(arrDouble.length == 3);
		assertTrue(arrDouble[0][0] == 1.0);
		assertTrue(arrDouble[0][1] == 1.0);
		assertTrue(arrDouble[0][2] == 2.0);
	}//EndTest.
	
}//EndTestCase.
