package be.nabu.libs.types.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.Type;
import be.nabu.libs.types.api.Unmarshallable;
import be.nabu.libs.types.base.BaseMarshallableSimpleType;
import be.nabu.libs.types.base.ValueImpl;
import be.nabu.libs.types.properties.EnumerationProperty;

@SuppressWarnings("rawtypes")
public class Enum extends BaseMarshallableSimpleType<java.lang.Enum> implements Unmarshallable<java.lang.Enum> {

	private List<Value<?>> properties;
	
	public Enum(java.lang.Class<java.lang.Enum> type) {
		super(type);
	}

	@Override
	public Type getSuperType() {
		return new String();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Value<?>[] getProperties() {
		if (properties == null) {
			properties = new ArrayList<Value<?>>(Arrays.asList(super.getProperties()));
			List<java.lang.String> values = new ArrayList<java.lang.String>();
			for (java.lang.Enum enumValue : getInstanceClass().getEnumConstants()) {
				values.add(enumValue.toString());
			}
			properties.add(new ValueImpl<List>(new EnumerationProperty(), values));
		}
		return properties.toArray(new Value<?>[0]);
	}
	
	@Override
	public java.lang.String getName(Value<?>...values) {
		java.lang.String name = getInstanceClass().getSimpleName();
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}

	@Override
	public java.lang.String getNamespace(Value<?>...values) {
		return getInstanceClass().getName().replaceAll("\\.[^.+]$", "");
	}

	@Override
	public java.lang.String marshal(java.lang.Enum object, Value<?>... values) {
		return object == null ? null : object.toString();
	}

	@Override
	public java.lang.Enum unmarshal(java.lang.String content, Value<?>... values) {
		return content == null || content.trim().isEmpty() ? null : getValue(content, getInstanceClass().getEnumConstants());
	}

	private java.lang.Enum getValue(java.lang.String value, java.lang.Enum...enums) {
		for (java.lang.Enum possible : enums) {
			if (possible.toString().equals(value)) {
				return possible;
			}
		}
		throw new IllegalArgumentException("There is no enum constant with the value " + value);
	}
}
