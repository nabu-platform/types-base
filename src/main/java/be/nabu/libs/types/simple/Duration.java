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

import java.text.ParseException;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.Unmarshallable;
import be.nabu.libs.types.base.BaseMarshallableSimpleType;

public class Duration extends BaseMarshallableSimpleType<be.nabu.libs.types.base.Duration> implements Unmarshallable<be.nabu.libs.types.base.Duration> {

	public Duration() {
		super(be.nabu.libs.types.base.Duration.class);
	}

	@Override
	public java.lang.String getName(Value<?>... values) {
		return "duration";
	}

	@Override
	public java.lang.String getNamespace(Value<?>... values) {
		return XML_SCHEMA;
	}

	@Override
	public java.lang.String marshal(be.nabu.libs.types.base.Duration object, Value<?>... values) {
		return object == null ? null : object.toString();
	}

	@Override
	public be.nabu.libs.types.base.Duration unmarshal(java.lang.String content, Value<?>... values) {
		try {
			return content == null ? null : be.nabu.libs.types.base.Duration.parse(content);
		}
		catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
