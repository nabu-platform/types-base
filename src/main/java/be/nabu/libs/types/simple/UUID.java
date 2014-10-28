package be.nabu.libs.types.simple;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.Unmarshallable;
import be.nabu.libs.types.base.BaseMarshallableSimpleType;

public class UUID extends BaseMarshallableSimpleType<java.util.UUID> implements Unmarshallable<java.util.UUID> {

	public UUID() {
		super(java.util.UUID.class);
	}

	@Override
	public java.lang.String getName(Value<?>... values) {
		return "uuid";
	}

	@Override
	public java.lang.String getNamespace(Value<?>... values) {
		return null;
	}

	@Override
	public java.lang.String marshal(java.util.UUID object, Value<?>... values) {
		return object == null ? null : object.toString();
	}

	@Override
	public java.util.UUID unmarshal(java.lang.String content, Value<?>... values) {
		return content == null ? null : java.util.UUID.fromString(content);
	}

}
