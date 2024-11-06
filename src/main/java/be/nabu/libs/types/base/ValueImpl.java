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

package be.nabu.libs.types.base;

import be.nabu.libs.property.api.ModifiableValue;
import be.nabu.libs.property.api.Property;

public class ValueImpl<T> implements ModifiableValue<T> {

	private Property<T> property;
	private T value;
	
	public ValueImpl(Property<T> property, T value) {
		this.property = property;
		this.value = value;
	}
	
	@Override
	public Property<T> getProperty() {
		return property;
	}

	@Override
	public T getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return getProperty() + " = " + getValue();
	}

	@Override
	public void setValue(T value) {
		this.value = value;
	}
}
