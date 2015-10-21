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
		return object == null ? null : object.toString().replace("-", "");
	}

	@Override
	public java.util.UUID unmarshal(java.lang.String content, Value<?>... values) {
		// an unformatted UUID, add formatting to enable parsing
		if (content != null && content.indexOf('-') < 0) {
			content = content.substring(0, 8) + "-" + content.substring(8, 12) + "-" + content.substring(12, 16) + "-" + content.substring(16, 20) + "-" + content.substring(20);
		}
		return content == null ? null : java.util.UUID.fromString(content);
	}

}
