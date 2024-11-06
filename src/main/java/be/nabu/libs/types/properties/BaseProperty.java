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

import be.nabu.libs.property.api.Property;

/**
 * Properties that extend this carry no state and as such are equal to other instances of themselves
 * as long as the class type is compatible
 * 
 * @author alex
 *
 * @param <T>
 */
abstract public class BaseProperty<T> implements Property<T> {
	@Override
	public boolean equals(Object object) {
		return getClass().equals(object.getClass())
			&& getValueClass().isAssignableFrom(((Property<?>) object).getValueClass());
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
