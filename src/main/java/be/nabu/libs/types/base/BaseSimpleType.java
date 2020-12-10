package be.nabu.libs.types.base;

import java.util.Set;

import be.nabu.libs.property.api.Property;
import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.DefinedSimpleType;
import be.nabu.libs.types.properties.AggregateProperty;
import be.nabu.libs.types.properties.CalculationProperty;
import be.nabu.libs.types.properties.ForeignKeyProperty;
import be.nabu.libs.types.properties.ForeignNameProperty;
import be.nabu.libs.types.properties.GeneratedProperty;
import be.nabu.libs.types.properties.PrimaryKeyProperty;

/**
 * Simple types are the same as long as they are of the same class (functional singletons)
 */
abstract public class BaseSimpleType<T> extends BaseType<T> implements DefinedSimpleType<T> {

	private Class<T> type;
	
	public BaseSimpleType(Class<T> type) {
		this.type = type;
	}

	@Override
	public Class<T> getInstanceClass() {
		return type;
	}
	
	@Override
	public boolean equals(Object object) {
		return object != null && getClass().equals(object.getClass());
	}
	
	@Override
	public int hashCode() {
		return getInstanceClass().hashCode();
	}
	
	@Override
	public String getId() {
		return getInstanceClass().getName();
	}
	
	@Override
	public Set<Property<?>> getSupportedProperties(Value<?>...properties) {
		Set<Property<?>> set = super.getSupportedProperties(properties);
		set.add(ForeignKeyProperty.getInstance());
		set.add(ForeignNameProperty.getInstance());
		set.add(PrimaryKeyProperty.getInstance());
		set.add(GeneratedProperty.getInstance());
		set.add(AggregateProperty.getInstance());
		set.add(CalculationProperty.getInstance());
		return set;
	}
}
