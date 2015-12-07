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
import be.nabu.libs.types.properties.CollectionHandlerProviderProperty;
import be.nabu.libs.types.properties.CommentProperty;
import be.nabu.libs.types.properties.MaxOccursProperty;
import be.nabu.libs.types.properties.MinOccursProperty;
import be.nabu.libs.types.properties.NameProperty;
import be.nabu.libs.types.properties.NamespaceProperty;
import be.nabu.libs.types.properties.NillableProperty;
import be.nabu.libs.validator.NillableValidator;
import be.nabu.libs.validator.OccurenceValidator;
import be.nabu.libs.validator.api.Validator;

abstract public class BaseType<T> implements ModifiableType {
	
	private Type superType;
	
	private Map<Property<?>, Value<?>> properties = new LinkedHashMap<Property<?>, Value<?>>();
	
	@SuppressWarnings("unchecked")
	@Override
	public Validator<T> createValidator(Value<?>...values) {
		return (Validator<T>) new NillableValidator(ValueUtils.getValue(NillableProperty.getInstance(), values));
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
		set.add(NameProperty.getInstance());
		set.add(NamespaceProperty.getInstance());
		set.add(CommentProperty.getInstance());
		set.add(MinOccursProperty.getInstance());
		set.add(MaxOccursProperty.getInstance());
		set.add(NillableProperty.getInstance());
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
