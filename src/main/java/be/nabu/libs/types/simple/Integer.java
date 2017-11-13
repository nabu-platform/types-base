package be.nabu.libs.types.simple;

import java.math.BigInteger;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.base.BaseComparableSimpleType;

/**
 * This should only be used for very big numbers
 * The overhead of big number calculation is rather huge if unnecessary
 * 
 * @author alex
 *
 */
public class Integer extends BaseComparableSimpleType<BigInteger> {

	public Integer() {
		super(BigInteger.class);
	}

	@Override
	public java.lang.String getName(Value<?>...values) {
		return "integer";
	}

	@Override
	public java.lang.String marshal(BigInteger object, Value<?>...values) {
		return object == null ? null : object.toString();
	}

	@Override
	public BigInteger unmarshal(java.lang.String content, Value<?>...values) {
		return content == null || content.trim().isEmpty() ? null : new BigInteger(content);
	}
	
	@Override
	public java.lang.String getNamespace(Value<?>...values) {
		return XML_SCHEMA;
	}

}
