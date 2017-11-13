package be.nabu.libs.types.simple;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.Type;
import be.nabu.libs.types.api.Unmarshallable;
import be.nabu.libs.types.base.BaseMarshallableSimpleType;

@SuppressWarnings("rawtypes")
public class Class extends BaseMarshallableSimpleType<java.lang.Class> implements Unmarshallable<java.lang.Class> {

	public Class() {
		super(java.lang.Class.class);
	}
	
	@Override
	public Type getSuperType() {
		return new String();
	}

	@Override
	public java.lang.String getName(Value<?>... values) {
		return "class";
	}

	@Override
	public java.lang.String getNamespace(Value<?>... values) {
		return getInstanceClass().getName().replaceAll("\\.[^.]+$", "");
	}

	@Override
	public java.lang.String marshal(java.lang.Class object, Value<?>...values) {
		return object == null ? null : object.getName();
	}

	@Override
	public java.lang.Class unmarshal(java.lang.String content, Value<?>...values) {
		try {
			return content == null || content.trim().isEmpty() ? null : java.lang.Class.forName(content);
		}
		catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
	}
}

