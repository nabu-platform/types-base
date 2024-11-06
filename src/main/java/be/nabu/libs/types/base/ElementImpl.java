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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import be.nabu.libs.property.ValueUtils;
import be.nabu.libs.property.api.Property;
import be.nabu.libs.property.api.PropertyChangeListener;
import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.BaseTypeInstance;
import be.nabu.libs.types.CollectionHandlerFactory;
import be.nabu.libs.types.api.CollectionHandlerProvider;
import be.nabu.libs.types.api.ComplexType;
import be.nabu.libs.types.api.ElementWithPropertyListener;
import be.nabu.libs.types.api.ModifiableElement;
import be.nabu.libs.types.api.Type;
import be.nabu.libs.types.properties.NameProperty;
import be.nabu.libs.types.properties.NamespaceProperty;
import be.nabu.libs.types.properties.QualifiedProperty;
import be.nabu.libs.validator.api.Validation;
import be.nabu.libs.validator.api.Validator;

abstract public class ElementImpl<T> extends BaseTypeInstance implements ModifiableElement<T>, ElementWithPropertyListener<T> {

	private Set<Property<?>> blockedProperties = new HashSet<Property<?>>();
	
	private ComplexType parent;
	
	private Map<String, List<PropertyChangeListener<?>>> listeners = new HashMap<String, List<PropertyChangeListener<?>>>();
	
	/**
	 * Cached values for quick access
	 */
	private String name, namespace;
	
	public ElementImpl(Type type, ComplexType parent, Value<?>...values) {
		super(type);
		this.parent = parent;
		setProperty(values);
	}
	
	public ElementImpl(String name, Type type, ComplexType parent, Value<?>...values) {
		super(type);
		this.parent = parent;
		setProperty(values);
		setProperty(new ValueImpl<String>(NameProperty.getInstance(), name));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<? extends Validation<?>> validate(T instance) {
		CollectionHandlerProvider<T, ?> provider = (CollectionHandlerProvider<T, ?>) CollectionHandlerFactory.getInstance().getHandler().getHandler(instance.getClass());
		// if there is a provider for it, it is a collection
		if (provider != null) {
			Validator<Collection<?>> collectionValidator = getType().createCollectionValidator(getProperties());
			Collection<?> collection = provider.getAsCollection(instance);
			List<Validation<?>> messages = new ArrayList<Validation<?>>(collectionValidator.validate(collection));
			Validator validator = getType().createValidator(getProperties());
			for (Object item : collection)
				messages.addAll(validator.validate(item));
			return messages;
		}
		else {
			Validator<T> validator = (Validator<T>) getType().createValidator(getProperties());
			return validator.validate(instance);
		}
	}

	@Override
	public ComplexType getParent() {
		return parent;
	}
	
	@Override
	public Set<Property<?>> getSupportedProperties() {
		Set<Property<?>> properties = getType().getSupportedProperties(getProperties());
		properties.add(QualifiedProperty.getInstance());
		properties.add(NameProperty.getInstance());
		properties.add(NamespaceProperty.getInstance());
		properties.removeAll(blockedProperties);
		return properties;
	}
	
	public Set<Property<?>> getBlockedProperties() {
		return blockedProperties;
	}
	
	@Override
	public String getName() {
		if (name == null) {
			name = ValueUtils.getValue(NameProperty.getInstance(), getProperties());
			if (name == null) {
				name = getType().getName(getProperties());
			}
		}
		return name;
	}
	@Override
	public String getNamespace() {
		if (namespace == null) {
			namespace = ValueUtils.getValue(NamespaceProperty.getInstance(), getProperties());
			if (namespace == null && getParent() != null) {
				namespace = getParent().getNamespace();
			}
		}
		return namespace;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setProperty(Value<?>... values) {
		// check for listeners and inform them of the changes
		if (values != null && values.length > 0) {
			for (Value<?> value : values) {
				List<PropertyChangeListener<?>> list = listeners.get(value.getProperty().getName());
				if (list != null && !list.isEmpty()) {
					for (PropertyChangeListener listener : list) {
						listener.changed(value.getProperty(), getProperty(value.getProperty()), value);
					}
				}
			}
		}
		// reset cache
		name = null;
		namespace = null;
		super.setProperty(values);
	}
	
	@Override
	public void setParent(ComplexType parent) {
		this.parent = parent;
	}

	@Override
	public <P> void registerPropertyListener(Property<P> property, PropertyChangeListener<P> listener) {
		if (!listeners.containsKey(property.getName())) {
			listeners.put(property.getName(), new ArrayList<PropertyChangeListener<?>>());
		}
		List<PropertyChangeListener<?>> list = listeners.get(property.getName());
		list.add(listener);
	}

	@Override
	public <P> void unregisterPropertyListener(Property<P> property, PropertyChangeListener<P> listener) {
		if (listeners.containsKey(property.getName())) {
			List<PropertyChangeListener<?>> list = listeners.get(property.getName());
			if (list != null) {
				list.remove(listener);
			}
		}
	}
	
}
