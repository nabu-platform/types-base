/*
* Copyright (C) 2014 Alexander Verbruggen
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program. If not, see <https://www.gnu.org/licenses/>.
*/

package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;

// In json you can have nested arrays like [[[1]]] which can not be properly handled by the type system
// instead we could do [t1:[t2:[]]]. if we then set "matrix" on both t1 and t2, we will skip the actual name and inject it as part of a matrix

public class MatrixProperty extends SimpleProperty<Boolean> implements ComparableProperty<Boolean> {

	private static MatrixProperty instance = new MatrixProperty();
	
	public static MatrixProperty getInstance() {
		return instance;
	}
	
	public MatrixProperty() {
		super(Boolean.class);
	}

	@Override
	public String getName() {
		return "matrix";
	}

	@Override
	public boolean isSubset(Boolean arg0, Boolean arg1) {
		return true;
	}

}
