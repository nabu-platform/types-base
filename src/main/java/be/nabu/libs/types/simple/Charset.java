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
import be.nabu.libs.types.api.Unmarshallable;
import be.nabu.libs.types.base.BaseMarshallableSimpleType;

public class Charset extends BaseMarshallableSimpleType<java.nio.charset.Charset> implements Unmarshallable<java.nio.charset.Charset> {

	public Charset() {
		super(java.nio.charset.Charset.class);
	}

	@Override
	public java.lang.String getName(Value<?>... arg0) {
		return "charset";
	}

	@Override
	public java.lang.String getNamespace(Value<?>... arg0) {
		return null;
	}

	@Override
	public java.lang.String marshal(java.nio.charset.Charset arg0, Value<?>... arg1) {
		return arg0 == null ? null : arg0.name();
	}

	@Override
	public java.nio.charset.Charset unmarshal(java.lang.String content, Value<?>... arg1) {
		return content == null || content.trim().isEmpty() ? null : java.nio.charset.Charset.forName(content);
	}
}
