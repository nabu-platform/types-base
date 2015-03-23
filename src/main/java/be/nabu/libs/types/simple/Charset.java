package be.nabu.libs.types.simple;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.Unmarshallable;
import be.nabu.libs.types.base.BaseMarshallableSimpleType;

public class Charset extends BaseMarshallableSimpleType<java.nio.charset.Charset> implements Unmarshallable<java.nio.charset.Charset> {

	public Charset() {
		super(java.nio.charset.Charset.class);
	}

	@Override
	public java.lang.String getName(Value<?>... arg0) {
		return "charset";
	}

	@Override
	public java.lang.String getNamespace(Value<?>... arg0) {
		return null;
	}

	@Override
	public java.lang.String marshal(java.nio.charset.Charset arg0, Value<?>... arg1) {
		return arg0 == null ? null : arg0.name();
	}

	@Override
	public java.nio.charset.Charset unmarshal(java.lang.String arg0, Value<?>... arg1) {
		return arg0 == null ? null : java.nio.charset.Charset.forName(arg0);
	}
}
