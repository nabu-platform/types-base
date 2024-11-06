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

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import be.nabu.libs.property.ValueUtils;
import be.nabu.libs.property.api.Property;
import be.nabu.libs.property.api.PropertyWithDefault;
import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.CollectionHandlerProvider;
import be.nabu.libs.types.api.ModifiableType;
import be.nabu.libs.types.api.Type;
import be.nabu.libs.types.properties.AliasProperty;
import be.nabu.libs.types.properties.CollectionHandlerProviderProperty;
import be.nabu.libs.types.properties.CollectionNameProperty;
import be.nabu.libs.types.properties.CommentProperty;
import be.nabu.libs.types.properties.DynamicNameProperty;
import be.nabu.libs.types.properties.EnricherProperty;
import be.nabu.libs.types.properties.IdentifiableProperty;
import be.nabu.libs.types.properties.LabelProperty;
import be.nabu.libs.types.properties.MatrixProperty;
import be.nabu.libs.types.properties.MaxOccursProperty;
import be.nabu.libs.types.properties.MinOccursProperty;
import be.nabu.libs.types.properties.NameProperty;
import be.nabu.libs.types.properties.NamespaceProperty;
import be.nabu.libs.types.properties.NillableProperty;
import be.nabu.libs.types.properties.PersisterProperty;
import be.nabu.libs.types.properties.ScopeProperty;
import be.nabu.libs.types.properties.SecretProperty;
import be.nabu.libs.types.properties.UniqueProperty;
import be.nabu.libs.validator.NillableValidator;
import be.nabu.libs.validator.OccurenceValidator;
import be.nabu.libs.validator.api.Validator;

abstract public class BaseType<T> implements ModifiableType {
	
	private Type superType;
	
	private Map<Property<?>, Value<?>> properties = new LinkedHashMap<Property<?>, Value<?>>();
	
	@SuppressWarnings("unchecked")
	@Override
	public Validator<T> createValidator(Value<?>...values) {
		Boolean nillable = null, optional = false;
		for (Value<?> value : values) {
			if (value.getProperty().equals(NillableProperty.getInstance())) {
				nillable = (Boolean) value.getValue();
			}
			else if (value.getProperty().equals(MinOccursProperty.getInstance())) {
				Integer minOccurs = (Integer) value.getValue();
				optional = minOccurs != null && minOccurs == 0;
			}
		}
		return (Validator<T>) new NillableValidator(nillable == null ? optional : nillable);
	}

	@Override
	public Validator<Collection<?>> createCollectionValidator(Value<?>...values) {
		return new OccurenceValidator<Collection<?>>(
			ValueUtils.getValue(MinOccursProperty.getInstance(), values), 
			ValueUtils.getValue(MaxOccursProperty.getInstance(), values)
		);
	}

	@Override
	public Set<Property<?>> getSupportedProperties(Value<?>...properties) {
		Set<Property<?>> set = new LinkedHashSet<Property<?>>();
		set.add(AliasProperty.getInstance());
		set.add(NameProperty.getInstance());
		set.add(NamespaceProperty.getInstance());
		set.add(CommentProperty.getInstance());
		set.add(MinOccursProperty.getInstance());
		set.add(MaxOccursProperty.getInstance());
		set.add(NillableProperty.getInstance());
		set.add(CollectionNameProperty.getInstance());
		set.add(UniqueProperty.getInstance());
		set.add(ScopeProperty.getInstance());
		set.add(IdentifiableProperty.getInstance());
		set.add(SecretProperty.getInstance());
		set.add(LabelProperty.getInstance());
		set.add(DynamicNameProperty.getInstance());
		set.add(MatrixProperty.getInstance());
		set.add(EnricherProperty.getInstance());
		set.add(PersisterProperty.getInstance());
		// add any properties already set
		set.addAll(this.properties.keySet());
		return set;
	}

	@Override
	public boolean isList(Value<?>...values) {
		Integer maxOccurs = ValueUtils.getValue(MaxOccursProperty.getInstance(), values);
		CollectionHandlerProvider<?, ?> collectionHandler = ValueUtils.getValue(CollectionHandlerProviderProperty.getInstance(), values);
		return collectionHandler != null || (maxOccurs != null && (maxOccurs == 0 || maxOccurs > 1));
	}

	@Override
	public Type getSuperType() {
		return superType;
	}

	protected void setSuperType(Type superType) {
		this.superType = superType;
	}

	@Override
	public void setProperty(Value<?>...values) {
		for (Value<?> value : values) {
			if (value.getProperty() instanceof PropertyWithDefault && value.getValue() != null && value.getValue().equals(((PropertyWithDefault<?>) value.getProperty()).getDefault())) {
				properties.remove(value.getProperty());
			}
			else {
				properties.put(value.getProperty(), value);
			}
		}
	}

	@Override
	public Value<?>[] getProperties() {
		return properties.values().toArray(new Value<?>[properties.size()]);
	}
}
