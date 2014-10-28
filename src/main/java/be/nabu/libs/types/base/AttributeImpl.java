package be.nabu.libs.types.base;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.Attribute;
import be.nabu.libs.types.api.ComplexType;
import be.nabu.libs.types.api.SimpleType;

public class AttributeImpl<T> extends SimpleElementImpl<T> implements Attribute<T> {

	public AttributeImpl(String name, SimpleType<T> simpleType, ComplexType parent, Value<?>...values) {
		super(name, simpleType, parent, values);
	}

	public AttributeImpl(SimpleType<T> simpleType, ComplexType parent, Value<?>...values) {
		super(simpleType, parent, values);
	}
}
