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

import java.util.Set;

import be.nabu.libs.property.api.Property;
import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.base.BaseComparableSimpleType;
import be.nabu.libs.types.properties.FractionDigits;
import be.nabu.libs.types.properties.TotalDigits;

public class Float extends BaseComparableSimpleType<java.lang.Float> {

	public Float() {
		super(java.lang.Float.class);
	}

	@Override
	public java.lang.String getName(Value<?>...values) {
		return "float";
	}

	@Override
	public java.lang.String marshal(java.lang.Float object, Value<?>...values) {
		if (object == null) {
			return null;
		}
		else {
			java.lang.String string = object.toString();
			int index = string.indexOf('.');
			if (index >= 0) {
				// strip trailing 0
				string = string.replaceAll("[0]+$", "");
				if (string.endsWith(".")) {
					// strip the trailing "."
					string = string.substring(0, string.length() - 1);
				}
			}
			if (string.isEmpty()) {
				string = "0";
			}
			return string;
		}
	}

	@Override
	public java.lang.Float unmarshal(java.lang.String content, Value<?>...values) {
		return content == null || content.trim().isEmpty() ? null : new java.lang.Float(content);
	}

	@Override
	public java.lang.String getNamespace(Value<?>...values) {
		return XML_SCHEMA;
	}
	
	@Override
	public Set<Property<?>> getSupportedProperties(Value<?>...properties) {
		Set<Property<?>> set = super.getSupportedProperties(properties);
		set.add(FractionDigits.getInstance());
		set.add(TotalDigits.getInstance());
		return set;
	}
}
