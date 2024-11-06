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

public class MaxExclusiveProperty<T extends Comparable<T>> extends SimpleProperty<T> implements ComparableProperty<T> {

	@SuppressWarnings({ "rawtypes" })
	private static Class comparable() {
		return Comparable.class;
	}

	@SuppressWarnings("unchecked")
	public MaxExclusiveProperty() {
		super((Class<T>) comparable());
	}
	
	public MaxExclusiveProperty(Class<T> type) {
		super(type);
	}
	
	@Override
	public String getName() {
		return "maxExclusive";
	}

	@Override
	public boolean isSubset(T subsetValue, T broaderValue) {
		return broaderValue == null || (subsetValue != null && subsetValue.compareTo(broaderValue) <= 0);
	}

}
