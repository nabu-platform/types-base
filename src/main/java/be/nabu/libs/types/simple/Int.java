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

package be.nabu.libs.types.simple;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.base.BaseComparableSimpleType;

public class Int extends BaseComparableSimpleType<java.lang.Integer> {
	
	public Int() {
		super(java.lang.Integer.class);
	}

	@Override
	public java.lang.String getName(Value<?>...values) {
		return "int";
	}

	@Override
	public java.lang.String marshal(java.lang.Integer object, Value<?>...values) {
		return object == null ? null : object.toString();
	}

	@Override
	public java.lang.Integer unmarshal(java.lang.String content, Value<?>...values) {
		return content == null || content.trim().isEmpty() ? null : new java.lang.Integer(content);
	}

	@Override
	public java.lang.String getNamespace(Value<?>...values) {
		return XML_SCHEMA;
	}
}
