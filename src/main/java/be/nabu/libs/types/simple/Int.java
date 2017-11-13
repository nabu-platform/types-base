package be.nabu.libs.types.simple;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.base.BaseComparableSimpleType;

public class Int extends BaseComparableSimpleType<java.lang.Integer> {
	
	public Int() {
		super(java.lang.Integer.class);
	}

	@Override
	public java.lang.String getName(Value<?>...values) {
		return "int";
	}

	@Override
	public java.lang.String marshal(java.lang.Integer object, Value<?>...values) {
		return object == null ? null : object.toString();
	}

	@Override
	public java.lang.Integer unmarshal(java.lang.String content, Value<?>...values) {
		return content == null || content.trim().isEmpty() ? null : new java.lang.Integer(content);
	}

	@Override
	public java.lang.String getNamespace(Value<?>...values) {
		return XML_SCHEMA;
	}
}
