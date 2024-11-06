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

import java.util.Set;

import be.nabu.libs.property.api.Property;
import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.ComplexContent;
import be.nabu.libs.types.api.ComplexType;
import be.nabu.libs.types.api.Element;
import be.nabu.libs.types.api.ModifiableElement;
import be.nabu.libs.types.api.SimpleType;
import be.nabu.libs.types.api.Type;
import be.nabu.libs.types.properties.AttributeQualifiedDefaultProperty;
import be.nabu.libs.types.properties.ElementQualifiedDefaultProperty;

public class ComplexElementImpl extends ElementImpl<ComplexContent> {

	public ComplexElementImpl(ComplexType type, ComplexType parent, Value<?>...values) {
		super(type, parent, values);
	}
	
	public ComplexElementImpl(String name, ComplexType type, ComplexType parent, Value<?>...values) {
		super(name, type, parent, values);
	}
	
	@Override
	public ComplexType getType() {
		return (ComplexType) super.getType();
	}

	@Override
	public Set<Property<?>> getSupportedProperties() {
		Set<Property<?>> properties = super.getSupportedProperties();
		properties.add(ElementQualifiedDefaultProperty.getInstance());
		properties.add(AttributeQualifiedDefaultProperty.getInstance());
		properties.removeAll(getBlockedProperties());
		return properties;
	}

	@Override
	public Class<ComplexContent> getValueClass() {
		return ComplexContent.class;
	}

	@Override
	public void setType(Type type) {
		// we can't simply set a simple type on a complex element
		if (type instanceof SimpleType && getType() instanceof SimpleType) {
			Element<?> element = getType().get(ComplexType.SIMPLE_TYPE_VALUE);
			if (element instanceof ModifiableElement) {
				((ModifiableElement<?>) element).setType(type);
			}
		}
		else {
			super.setType(type);
		}
	}

}