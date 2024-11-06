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
import be.nabu.libs.types.base.BaseSimpleType;

public class Certificate extends BaseSimpleType<java.security.cert.Certificate> {

	public Certificate() {
		super(java.security.cert.Certificate.class);
	}

	@Override
	public java.lang.String getName(Value<?>... arg0) {
		return "certificate";
	}

	@Override
	public java.lang.String getNamespace(Value<?>... arg0) {
		return null;
	}
}
