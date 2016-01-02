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

package it.prz.jmatrw.io;

import it.prz.jmatrw.JMATInfo;

/**
 * Concrete implementation of the JMATInfo. 
 * @author Donato Pirozzi
 *
 */
class JMATInfoImpl extends JMATInfo {

	public void setSysIsCompressed(boolean iscompressed) { this.sys_iscompressed = iscompressed; } //EndMethod.
	
}//EndClass.
