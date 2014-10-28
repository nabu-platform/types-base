package be.nabu.libs.types.base;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.ComplexType;
import be.nabu.libs.types.api.SimpleType;

public class SimpleElementImpl<T> extends ElementImpl<T> {

	public SimpleElementImpl(String name, SimpleType<T> simpleType, ComplexType parent, Value<?>...values) {
		super(name, simpleType, parent, values);
	}
	
	public SimpleElementImpl(SimpleType<T> simpleType, ComplexType parent, Value<?>...values) {
		super(simpleType, parent, values);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SimpleType<T> getType() {
		return (SimpleType<T>) super.getType();
	}

	@Override
	public Class<T> getValueClass() {
		return getType().getInstanceClass();
	}
}
