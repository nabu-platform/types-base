package be.nabu.libs.types.simple;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.base.BaseSimpleType;

public class Certificate extends BaseSimpleType<java.security.cert.Certificate> {

	public Certificate() {
		super(java.security.cert.Certificate.class);
	}

	@Override
	public java.lang.String getName(Value<?>... arg0) {
		return "certificate";
	}

	@Override
	public java.lang.String getNamespace(Value<?>... arg0) {
		return null;
	}
}
