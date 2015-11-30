package be.nabu.libs.types.simple;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.base.BaseComparableSimpleType;

/**
 * +/-0 are not the same for doubles & floats in xsd?
 * @author alex
 *
 */
public class Double extends BaseComparableSimpleType<java.lang.Double> {

	public Double() {
		super(java.lang.Double.class);
	}

	@Override
	public java.lang.String getName(Value<?>...values) {
		return "double";
	}

	@Override
	public java.lang.String marshal(java.lang.Double object, Value<?>...values) {
		return object == null ? null : object.toString().replaceAll("[.0]+$", "");
	}

	@Override
	public java.lang.Double unmarshal(java.lang.String content, Value<?>...values) {
		return content == null ? null : new java.lang.Double(content);
	}
	
	@Override
	public java.lang.String getNamespace(Value<?>...values) {
		return XML_SCHEMA;
	}
}
