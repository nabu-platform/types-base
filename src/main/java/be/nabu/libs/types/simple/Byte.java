package be.nabu.libs.types.simple;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.base.BaseComparableSimpleType;

public class Byte extends BaseComparableSimpleType<java.lang.Byte> {

	public Byte() {
		super(java.lang.Byte.class);
	}

	@Override
	public java.lang.String getName(Value<?>...values) {
		return "byte";
	}

	@Override
	public java.lang.String marshal(java.lang.Byte object, Value<?>...values) {
		return object == null ? null : object.toString();
	}

	@Override
	public java.lang.Byte unmarshal(java.lang.String content, Value<?>...values) {
		return content == null ? null : new java.lang.Byte(content);
	}
	
	@Override
	public java.lang.String getNamespace(Value<?>...values) {
		return XML_SCHEMA;
	}
}