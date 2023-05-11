package be.nabu.libs.types.simple;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.base.BaseSimpleType;

public class Key extends BaseSimpleType<java.security.Key> {

	public Key() {
		super(java.security.Key.class);
	}

	@Override
	public java.lang.String getName(Value<?>... arg0) {
		return "key";
	}

	@Override
	public java.lang.String getNamespace(Value<?>... arg0) {
		return null;
	}
}
