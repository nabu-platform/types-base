package be.nabu.libs.types.simple;

import java.math.BigDecimal;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.base.BaseComparableSimpleType;

/**
 * This should only be used for very big numbers
 * The overhead of big number calculation is rather huge if unnecessary
 * 
 * @author alex
 *
 */
public class Decimal extends BaseComparableSimpleType<BigDecimal> {

	public Decimal() {
		super(BigDecimal.class);
	}

	@Override
	public java.lang.String getName(Value<?>...values) {
		return "decimal";
	}

	@Override
	public java.lang.String marshal(BigDecimal object, Value<?>...values) {
		return object == null ? null : object.toString();
	}

	@Override
	public BigDecimal unmarshal(java.lang.String content, Value<?>...values) {
		return content == null || content.trim().isEmpty() ? null : new BigDecimal(content);
	}

	@Override
	public java.lang.String getNamespace(Value<?>...values) {
		return XML_SCHEMA;
	}
}
