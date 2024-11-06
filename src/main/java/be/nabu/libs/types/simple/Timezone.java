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

import java.util.TimeZone;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.Unmarshallable;
import be.nabu.libs.types.base.BaseMarshallableSimpleType;

public class Timezone extends BaseMarshallableSimpleType<TimeZone> implements Unmarshallable<TimeZone> {

	public Timezone() {
		super(TimeZone.class);
	}

	@Override
	public java.lang.String getName(Value<?>... arg0) {
		return "timezone";
	}

	@Override
	public java.lang.String getNamespace(Value<?>... arg0) {
		return null;
	}

	@Override
	public java.lang.String marshal(TimeZone arg0, Value<?>... arg1) {
		return arg0 == null ? null : arg0.getID();
	}

	@Override
	public TimeZone unmarshal(java.lang.String content, Value<?>... arg1) {
		return content == null || content.trim().isEmpty() ? null : TimeZone.getTimeZone(content);
	}
}
