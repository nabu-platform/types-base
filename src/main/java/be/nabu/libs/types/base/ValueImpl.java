package be.nabu.libs.types.base;

import be.nabu.libs.property.api.ModifiableValue;
import be.nabu.libs.property.api.Property;

public class ValueImpl<T> implements ModifiableValue<T> {

	private Property<T> property;
	private T value;
	
	public ValueImpl(Property<T> property, T value) {
		this.property = property;
		this.value = value;
	}
	
	@Override
	public Property<T> getProperty() {
		return property;
	}

	@Override
	public T getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return getProperty() + " = " + getValue();
	}

	@Override
	public void setValue(T value) {
		this.value = value;
	}
}
