package be.nabu.libs.types.simple;

import be.nabu.libs.property.ValueUtils;
import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.Unmarshallable;
import be.nabu.libs.types.base.BaseMarshallableSimpleType;
import be.nabu.libs.types.base.UUIDFormat;
import be.nabu.libs.types.base.ValueImpl;
import be.nabu.libs.types.properties.PatternProperty;
import be.nabu.libs.types.properties.UUIDFormatProperty;

public class UUID extends BaseMarshallableSimpleType<java.util.UUID> implements Unmarshallable<java.util.UUID> {

	public UUID() {
		super(java.util.UUID.class);
		// 6cac990b-5f6b-4d44-8a77-e8a5438afd56
		setProperty(new ValueImpl<java.lang.String>(PatternProperty.getInstance(), "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}|[0-9a-fA-F]{32}"));
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
		UUIDFormat value = ValueUtils.getValue(UUIDFormatProperty.getInstance(), values);
		if (value != null && value.equals(UUIDFormat.DASHES)) {
			return object == null ? null : object.toString();
		}
		else {
			return object == null ? null : object.toString().replace("-", "");
		}
	}

	@Override
	public java.util.UUID unmarshal(java.lang.String content, Value<?>... values) {
		// an unformatted UUID, add formatting to enable parsing
		if (content != null && !content.trim().isEmpty() && content.indexOf('-') < 0) {
			if (content.length() != 32) {
				throw new IllegalArgumentException("The value is not a valid UUID: " + content);
			}
			content = content.substring(0, 8) + "-" + content.substring(8, 12) + "-" + content.substring(12, 16) + "-" + content.substring(16, 20) + "-" + content.substring(20);
		}
		return content == null || content.trim().isEmpty() ? null : java.util.UUID.fromString(content);
	}

}
