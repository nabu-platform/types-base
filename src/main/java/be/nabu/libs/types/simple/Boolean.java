package be.nabu.libs.types.simple;

import be.nabu.libs.property.api.Value;
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
		// update: due to priorities of converters and multiple string > boolean paths (in some setups) with equal priority
		// it becomes too hard to only make this path more strict as it may lead to seemingly "random" failures depending on converter order
//		if (content != null && !content.equalsIgnoreCase("true") && !content.equalsIgnoreCase("false")) {
//			throw new MarshalException("The string is not a correct boolean value: " + content);
//		}
		
		// keep in sync with be.nabu.libs.converter.base.providers.StringToBoolean for consistent results cross environment
		if (content == null) {
			return null;
		}
		else if (content.matches("[0-9]+")) {
			return java.lang.Integer.parseInt(content) >= 1;
		}
		else {
			return java.lang.Boolean.parseBoolean(content);
		}
	}

	@Override
	public java.lang.String getNamespace(Value<?>...values) {
		return XML_SCHEMA;
	}

}
