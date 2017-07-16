package be.nabu.libs.types.simple;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.MarshalException;
import be.nabu.libs.types.api.Unmarshallable;
import be.nabu.libs.types.base.BaseMarshallableSimpleType;

public class Boolean extends BaseMarshallableSimpleType<java.lang.Boolean> implements Unmarshallable<java.lang.Boolean> {

	public Boolean() {
		super(java.lang.Boolean.class);
	}
	
	@Override
	public java.lang.String getName(Value<?>...values) {
		return "boolean";
	}

	@Override
	public java.lang.String marshal(java.lang.Boolean object, Value<?>...values) {
		return object == null ? null : object.toString();
	}

	@Override
	public java.lang.Boolean unmarshal(java.lang.String content, Value<?>...values) {
		// the default boolean parsing in java is (surprisingly) too lenient
		// if you try "new java.lang.Boolean('this is definitely not a boolean')" it will be the value false
		// we want an exception indicating that what you sent is not a valid boolean in any way
		if (content != null && !content.equalsIgnoreCase("true") && !content.equalsIgnoreCase("false")) {
			throw new MarshalException("The string is not a correct boolean value: " + content);
		}
		return content == null ? null : new java.lang.Boolean(content);
	}

	@Override
	public java.lang.String getNamespace(Value<?>...values) {
		return XML_SCHEMA;
	}

}
