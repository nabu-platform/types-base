package be.nabu.libs.types.simple;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.base.BaseComparableSimpleType;

public class Float extends BaseComparableSimpleType<java.lang.Float> {

	public Float() {
		super(java.lang.Float.class);
	}

	@Override
	public java.lang.String getName(Value<?>...values) {
		return "float";
	}

	@Override
	public java.lang.String marshal(java.lang.Float object, Value<?>...values) {
		return object == null ? null : object.toString().replaceAll("[.0]+$", "");
	}

	@Override
	public java.lang.Float unmarshal(java.lang.String content, Value<?>...values) {
		return content == null ? null : new java.lang.Float(content);
	}

	@Override
	public java.lang.String getNamespace(Value<?>...values) {
		return XML_SCHEMA;
	}
}
