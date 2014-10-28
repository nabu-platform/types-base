package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.Property;

/**
 * Properties that extend this carry no state and as such are equal to other instances of themselves
 * as long as the class type is compatible
 * 
 * @author alex
 *
 * @param <T>
 */
abstract public class BaseProperty<T> implements Property<T> {
	@Override
	public boolean equals(Object object) {
		return getClass().equals(object.getClass())
			&& getValueClass().isAssignableFrom(((Property<?>) object).getValueClass());
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
