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

package it.prz.jmatrw.matdatatypes;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Donato Pirozzi - donatopirozzi@gmail.com
 */
public class MLDataType {

	public static final MLDataType miINT8 = new MLDataType(1, 1, "miINT8");
	
	public static final MLDataType miINT32 = new MLDataType(5, 4, "miINT32");
	public static final MLDataType miUINT32 = new MLDataType(6, 4, "miUINT32");
	
	public static final MLDataType miDOUBLE = new MLDataType(9, 8, "miDOUBLE");
	
	public static final MLDataType miMATRIX = new MLDataType(14, 0, "miMATRIX"); 
	
	private static final Map<Integer, MLDataType> dataTypes = new HashMap<Integer, MLDataType>(); 
	public static MLDataType dataTypeFromValue(int index) { return dataTypes.get(index); }
	
	static {
		dataTypes.put(miINT8.value, miINT8);
		dataTypes.put(miINT32.value, miINT32);
		dataTypes.put(miUINT32.value, miUINT32);
		dataTypes.put(miDOUBLE.value, miDOUBLE);
		dataTypes.put(miMATRIX.value, miMATRIX);
	}
	
	public final int value;
	public final int bytes;
	public final String description;
	
	private MLDataType(int value, int bytes, String description) {
		this.value = value;
		this.bytes = bytes;
		this.description = description;
	}//EndConstructor.

	@Override
	public String toString() { return description; }
	
}//EndClass.
