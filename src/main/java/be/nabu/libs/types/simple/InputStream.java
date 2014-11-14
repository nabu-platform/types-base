package be.nabu.libs.types.simple;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.base.BaseSimpleType;

public class InputStream extends BaseSimpleType<java.io.InputStream> {

	public InputStream() {
		super(java.io.InputStream.class);
	}

	@Override
	public java.lang.String getName(Value<?>... arg0) {
		return "inputstream";
	}

	@Override
	public java.lang.String getNamespace(Value<?>... arg0) {
		return null;
	}
}
