package be.nabu.libs.types.simple;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.Unmarshallable;
import be.nabu.libs.types.base.BaseMarshallableSimpleType;

public class Boolean extends BaseMarshallableSimpleType<java.lang.Boolean> implements Unmarshallable<java.lang.Boolean> {

	public Boolean() {
		super(java.lang.Boolean.class);
	}
	
	@Override
	public java.lang.String getName(Value<?>...values) {
		return "boolean";
	}

	@Override
	public java.lang.String marshal(java.lang.Boolean object, Value<?>...values) {
		return object == null ? null : object.toString();
	}

	@Override
	public java.lang.Boolean unmarshal(java.lang.String content, Value<?>...values) {
		return content == null ? null : new java.lang.Boolean(content);
	}

	@Override
	public java.lang.String getNamespace(Value<?>...values) {
		return XML_SCHEMA;
	}

}
