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
import be.nabu.libs.types.api.Type;
import be.nabu.libs.types.api.Unmarshallable;
import be.nabu.libs.types.base.BaseMarshallableSimpleType;

@SuppressWarnings("rawtypes")
public class Class extends BaseMarshallableSimpleType<java.lang.Class> implements Unmarshallable<java.lang.Class> {

	public Class() {
		super(java.lang.Class.class);
	}
	
	@Override
	public Type getSuperType() {
		return new String();
	}

	@Override
	public java.lang.String getName(Value<?>... values) {
		return "class";
	}

	@Override
	public java.lang.String getNamespace(Value<?>... values) {
		return getInstanceClass().getName().replaceAll("\\.[^.]+$", "");
	}

	@Override
	public java.lang.String marshal(java.lang.Class object, Value<?>...values) {
		return object == null ? null : object.getName();
	}

	@Override
	public java.lang.Class unmarshal(java.lang.String content, Value<?>...values) {
		try {
			//return content == null || content.trim().isEmpty() ? null : java.lang.Class.forName(content);
			return content == null || content.trim().isEmpty() ? null : Thread.currentThread().getContextClassLoader().loadClass(content);
		}
		catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
	}
}

