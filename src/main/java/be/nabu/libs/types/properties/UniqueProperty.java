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

public class UniqueProperty extends SimpleProperty<Boolean> implements ComparableProperty<Boolean> {

	private static UniqueProperty instance = new UniqueProperty();
	
	public static UniqueProperty getInstance() {
		return instance;
	}
	
	public UniqueProperty() {
		super(Boolean.class);
	}
	
	@Override
	public String getName() {
		return "unique";
	}

	@Override
	public boolean isSubset(Boolean subsetValue, Boolean broaderValue) {
		if (broaderValue == null) {
			broaderValue = false;
		}
		if (subsetValue == null) {
			subsetValue = false;
		}
		// if the broader is unique, it doesn't matter what the subset is
		// however if the broadervalue is false, the subsetvalue must be false as well
		return broaderValue || !subsetValue;
	}

}