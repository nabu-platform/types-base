package be.nabu.libs.types.simple;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.base.BaseComparableSimpleType;

public class Short extends BaseComparableSimpleType<java.lang.Short> {

	public Short() {
		super(java.lang.Short.class);
	}

	@Override
	public java.lang.String getName(Value<?>...values) {
		return "short";
	}

	@Override
	public java.lang.String marshal(java.lang.Short object, Value<?>...values) {
		return object == null ? null : object.toString();
	}

	@Override
	public java.lang.Short unmarshal(java.lang.String content, Value<?>...values) {
		return content == null ? null : new java.lang.Short(content);
	}
	
	@Override
	public java.lang.String getNamespace(Value<?>...values) {
		return XML_SCHEMA;
	}
}