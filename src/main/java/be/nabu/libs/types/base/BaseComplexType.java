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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import be.nabu.libs.property.ValueUtils;
import be.nabu.libs.property.api.Property;
import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.Element;
import be.nabu.libs.types.api.Group;
import be.nabu.libs.types.api.ModifiableComplexType;
import be.nabu.libs.types.properties.AllowProperty;
import be.nabu.libs.types.properties.AttributeQualifiedDefaultProperty;
import be.nabu.libs.types.properties.CollectionCrudProviderProperty;
import be.nabu.libs.types.properties.DuplicateProperty;
import be.nabu.libs.types.properties.ElementQualifiedDefaultProperty;
import be.nabu.libs.types.properties.NameProperty;
import be.nabu.libs.types.properties.NamespaceProperty;
import be.nabu.libs.types.properties.RestrictProperty;
import be.nabu.libs.validator.api.ValidationMessage;
import be.nabu.libs.validator.api.ValidationMessage.Severity;

public abstract class BaseComplexType<T> extends BaseType<T> implements ModifiableComplexType {
	
	private List<Group> groups = new ArrayList<Group>();
	private Map<String, Element<?>> elements = new LinkedHashMap<String, Element<?>>();
	
	public BaseComplexType() {
		setName("anonymous");
	}
	
	@Override
	public String getName(Value<?>...values) {
		return ValueUtils.getValue(NameProperty.getInstance(), values);
	}

	@Override
	public String getNamespace(Value<?>...values) {
		return ValueUtils.getValue(NamespaceProperty.getInstance(), values);
	}

	@Override
	public Iterator<Element<?>> iterator() {
		return elements.values().iterator();
	}

	@Override
	public Element<?> get(String arg0) {
		return elements.get(arg0);
	}

	@Override
	public Group[] getGroups() {
		return groups.toArray(new Group[groups.size()]);
	}
	
	@Override
	public Boolean isAttributeQualified(Value<?>...values) {
		return ValueUtils.getValue(AttributeQualifiedDefaultProperty.getInstance(), values);
	}

	@Override
	public Boolean isElementQualified(Value<?>...values) {
		return ValueUtils.getValue(ElementQualifiedDefaultProperty.getInstance(), values);
	}

	@Override
	public List<ValidationMessage> add(Element<?> element) {
		List<ValidationMessage> messages = new ArrayList<ValidationMessage>();
		if (elements.containsKey(element.getName())) {
			messages.add(new ValidationMessage(Severity.ERROR, "An element already exists with the name: " + element.getName()));
		}
		else {
			elements.put(element.getName(), element);
		}
		return messages;
	}

	@Override
	public List<ValidationMessage> add(Group group) {
		groups.add(group);
		return new ArrayList<ValidationMessage>();
	}

	@Override
	public void remove(Element<?> name) {
		elements.remove(name.getName());
	}

	@Override
	public void remove(Group group) {
		groups.remove(group);
	}

	@Override
	public void setName(String value) {
		setProperty(new ValueImpl<String>(NameProperty.getInstance(), value));
	}

	@Override
	public void setNamespace(String value) {
		setProperty(new ValueImpl<String>(NamespaceProperty.getInstance(), value));
	}

	@Override
	public Set<Property<?>> getSupportedProperties(Value<?>... properties) {
		Set<Property<?>> supportedProperties = super.getSupportedProperties(properties);
		supportedProperties.add(AllowProperty.getInstance());
		supportedProperties.add(RestrictProperty.getInstance());
		supportedProperties.add(DuplicateProperty.getInstance());
		supportedProperties.add(CollectionCrudProviderProperty.getInstance());
		return supportedProperties;
	}
	
}
