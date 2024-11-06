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

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.Element;
import be.nabu.libs.types.api.Type;

public class DynamicElement<T> extends ElementImpl<T> {

	private Element<T> parent;

	public DynamicElement(Element<T> parent, Type type, String name, Value<?>...values) {
		super(name, type, parent.getParent(), values);
		this.parent = parent;
	}
	
	@Override
	public Class<T> getValueClass() {
		return parent.getValueClass();
	}

}
