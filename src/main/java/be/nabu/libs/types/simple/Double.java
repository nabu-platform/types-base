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
		if (object == null) {
			return null;
		}
		else {
			java.lang.String string = object.toString();
			int index = string.indexOf('.');
			if (index >= 0) {
				// strip trailing 0
				string = string.replaceAll("[0]+$", "");
				if (string.endsWith(".")) {
					// strip the trailing "."
					string = string.substring(0, string.length() - 1);
				}
			}
			if (string.isEmpty()) {
				string = "0";
			}
			return string;
		}
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
