package be.nabu.libs.types.simple;

import java.util.TimeZone;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.Unmarshallable;
import be.nabu.libs.types.base.BaseMarshallableSimpleType;

public class Timezone extends BaseMarshallableSimpleType<TimeZone> implements Unmarshallable<TimeZone> {

	public Timezone() {
		super(TimeZone.class);
	}

	@Override
	public java.lang.String getName(Value<?>... arg0) {
		return "timezone";
	}

	@Override
	public java.lang.String getNamespace(Value<?>... arg0) {
		return null;
	}

	@Override
	public java.lang.String marshal(TimeZone arg0, Value<?>... arg1) {
		return arg0 == null ? null : arg0.getID();
	}

	@Override
	public TimeZone unmarshal(java.lang.String content, Value<?>... arg1) {
		return content == null || content.trim().isEmpty() ? null : TimeZone.getTimeZone(content);
	}
}
