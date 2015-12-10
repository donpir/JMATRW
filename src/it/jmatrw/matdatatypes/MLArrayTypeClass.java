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

package it.jmatrw.matdatatypes;

import java.util.HashMap;
import java.util.Map;

public class MLArrayTypeClass {

	public static final MLArrayTypeClass mxDOUBLE_CLASS = new MLArrayTypeClass(6, 8); //8 bytes.
	
	private static final Map<Integer, MLArrayTypeClass> dataTypes = new HashMap<Integer, MLArrayTypeClass>(); 
	public static MLArrayTypeClass dataTypeFromValue(int index) { return dataTypes.get(index); }
	
	static {
		dataTypes.put(mxDOUBLE_CLASS.value, mxDOUBLE_CLASS);
	}
	
	public final int value;
	public final int bytes;
	
	private MLArrayTypeClass(int value, int bytes) {
		this.value = value;
		this.bytes = bytes;
	}//EndConstructor.
	
}//EndClass.
