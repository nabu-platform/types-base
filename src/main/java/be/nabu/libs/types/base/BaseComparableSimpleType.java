package be.nabu.libs.types.base;

import java.util.Set;

import be.nabu.libs.property.ValueUtils;
import be.nabu.libs.property.api.Property;
import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.Unmarshallable;
import be.nabu.libs.types.properties.MaxExclusiveProperty;
import be.nabu.libs.types.properties.MaxInclusiveProperty;
import be.nabu.libs.types.properties.MinExclusiveProperty;
import be.nabu.libs.types.properties.MinInclusiveProperty;
import be.nabu.libs.validator.MultipleValidator;
import be.nabu.libs.validator.RangeValidator;
import be.nabu.libs.validator.api.Validator;

/**
 * Abstract base for all number-related types
 */
abstract public class BaseComparableSimpleType<T extends Comparable<T>> extends BaseMarshallableSimpleType<T> implements Unmarshallable<T> {

	public BaseComparableSimpleType(Class<T> type) {
		super(type);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Validator<T> createValidator(Value<?>...values) {
		T min = null, max = null;
		Boolean isMinInclusive = null, isMaxInclusive = null;
		
		T maxInclusive = ValueUtils.getValue(new MaxInclusiveProperty<T>(), values);
		T minInclusive = ValueUtils.getValue(new MinInclusiveProperty<T>(), values);
		T maxExclusive = ValueUtils.getValue(new MaxExclusiveProperty<T>(), values);
		T minExclusive = ValueUtils.getValue(new MinExclusiveProperty<T>(), values);
		if (maxInclusive != null) {
			max = maxInclusive;
			isMaxInclusive = true;
		}
		else if (maxExclusive != null) {
			max = maxExclusive;
			isMaxInclusive = false;
		}
		if (minInclusive != null) {
			min = minInclusive;
			isMinInclusive = true;
		}
		else if (minExclusive != null) {
			min = minExclusive;
			isMinInclusive = false;
		}
		return new MultipleValidator<T>(
			
			super.createValidator(values),
			
			new RangeValidator<T>(
				min,
				isMinInclusive,
				max,
				isMaxInclusive
			)
		);
	}

	@Override
	public Set<Property<?>> getSupportedProperties(Value<?>...properties) {
		Set<Property<?>> set = super.getSupportedProperties(properties);
		set.add(new MaxExclusiveProperty<T>());
		set.add(new MaxInclusiveProperty<T>());
		set.add(new MinExclusiveProperty<T>());
		set.add(new MinInclusiveProperty<T>());
		return set;
	}
	
}
