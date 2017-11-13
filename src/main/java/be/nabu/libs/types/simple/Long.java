package be.nabu.libs.types.simple;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.base.BaseComparableSimpleType;

public class Long extends BaseComparableSimpleType<java.lang.Long> {
	
	public Long() {
		super(java.lang.Long.class);
	}

	@Override
	public java.lang.String getName(Value<?>...values) {
		return "long";
	}

	@Override
	public java.lang.String marshal(java.lang.Long object, Value<?>...values) {
		return object == null ? null : object.toString();
	}

	@Override
	public java.lang.Long unmarshal(java.lang.String content, Value<?>...values) {
		return content == null || content.trim().isEmpty() ? null : new java.lang.Long(content);
	}

	@Override
	public java.lang.String getNamespace(Value<?>...values) {
		return XML_SCHEMA;
	}
}
