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

package it.jmatrw.tests;

import java.io.IOException;
import java.io.InputStream;

import it.jmatrw.JMATData;
import it.jmatrw.JMATReader;
import it.jmatrw.JMATData.DataType;
import junit.framework.TestCase;

/**
 * 
 * @author Donato Pirozzi - donatopirozzi@gmail.com
 */
public class TC01ReadArrayTestCase extends TestCase {

	/*
	 * example01_array.mat contains: x = [1, 3, 2]
	 * @throws IOException
	 */
		
	public void testExample01Array() throws IOException {
		//Open the file.
		InputStream fis = TestCase.class.getResourceAsStream("/basicexamples/example01_array.mat");
		assertNotNull(fis);
		
		//Create the reader.
		JMATReader controller = new JMATReader(fis);
		JMATData matdata = controller.read();
		
		//Check file version.
		assertTrue(matdata.header.contains("MATLAB"));
		assertTrue(matdata.version == 1);
		
		//Check file content.
		assertTrue(matdata.dataType == DataType.ARRAY_DOUBLE);
		assertEquals("x", matdata.dataName);
		
		double[] arrDouble = (double[]) matdata.dataValue;
		assertTrue(arrDouble.length == 3);
		assertTrue(arrDouble[0] == 1.0);
		assertTrue(arrDouble[1] == 3.0);
		assertTrue(arrDouble[2] == 2.0);
	}//EndTest.
	
	/*
	 * A is a 3x3 matrix.
	 * A = [1, 1, 2; 3, 5, 8; 13, 21, 34]
	 */
	
	public void testExample02Matrix() throws IOException {
		//Open the file.
		InputStream fis = TestCase.class.getResourceAsStream("/basicexamples/example02_matrix.mat");
		assertNotNull(fis);
		
		//Create the reader.
		JMATReader controller = new JMATReader(fis);
		JMATData matdata = controller.read();
		
		//Check file version.
		assertTrue(matdata.header.contains("MATLAB"));
		assertTrue(matdata.version == 1);
		
		//Check file content.
		assertTrue(matdata.dataType == DataType.MATRIX_DOUBLE);
		assertEquals("A", matdata.dataName);
		
		double[][] arrDouble = (double[][]) matdata.dataValue;
		assertTrue(arrDouble.length == 3);
		assertTrue(arrDouble[0][0] == 1.0);
		assertTrue(arrDouble[0][1] == 1.0);
		assertTrue(arrDouble[0][2] == 2.0);
	}//EndTest.
	
}//EndTestCase.
