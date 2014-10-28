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
		return (Validator<T>) new NillableValidator(ValueUtils.getValue(new NillableProperty(), values));
	}

	@Override
	public Validator<Collection<?>> createCollectionValidator(Value<?>...values) {
		return new OccurenceValidator<Collection<?>>(
			ValueUtils.getValue(new MinOccursProperty(), values), 
			ValueUtils.getValue(new MaxOccursProperty(), values)
		);
	}

	@Override
	public Set<Property<?>> getSupportedProperties(Value<?>...properties) {
		Set<Property<?>> set = new LinkedHashSet<Property<?>>();
		set.add(new NameProperty());
		set.add(new NamespaceProperty());
		set.add(new CommentProperty());
		set.add(new MinOccursProperty());
		set.add(new MaxOccursProperty());
		set.add(new NillableProperty());
		return set;
	}

	@Override
	public boolean isList(Value<?>...values) {
		Integer maxOccurs = ValueUtils.getValue(new MaxOccursProperty(), values);
		CollectionHandlerProvider<?, ?> collectionHandler = ValueUtils.getValue(new CollectionHandlerProviderProperty(), values);
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
