package be.nabu.libs.types.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import be.nabu.libs.property.ValueUtils;
import be.nabu.libs.property.api.Property;
import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.Marshallable;
import be.nabu.libs.types.properties.EnumerationProperty;
import be.nabu.libs.types.properties.LengthProperty;
import be.nabu.libs.types.properties.MaxLengthProperty;
import be.nabu.libs.types.properties.MinLengthProperty;
import be.nabu.libs.types.properties.PatternProperty;
import be.nabu.libs.validator.EnumerationValidator;
import be.nabu.libs.validator.LengthValidator;
import be.nabu.libs.validator.MultipleValidator;
import be.nabu.libs.validator.PatternValidator;
import be.nabu.libs.validator.api.ValidationMessage;
import be.nabu.libs.validator.api.Validator;

abstract public class BaseMarshallableSimpleType<T> extends BaseSimpleType<T> implements Marshallable<T> {

	public BaseMarshallableSimpleType(Class<T> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Validator<T> createValidator(Value<?>... values) {
		Integer length = ValueUtils.getValue(new LengthProperty(), values);
		LengthValidator lengthValidator = length == null
			? new LengthValidator(ValueUtils.getValue(new MinLengthProperty(), values), ValueUtils.getValue(new MaxLengthProperty(), values))
			: new LengthValidator(length, length);
		
		Validator<T> validator = new MultipleValidator<T>(
			super.createValidator(values),
			new StringValidator(new PatternValidator(ValueUtils.getValue(new PatternProperty(), values))),
			new StringValidator(lengthValidator)
		);
		
		List<T> enumerationValues = ValueUtils.getValue(new EnumerationProperty<T>(), values);
		if (enumerationValues != null) {
			validator = new MultipleValidator<T>(
				validator,
				new EnumerationValidator<T>(true, enumerationValues)
			);
		}
		
		return validator;
	}
	
	@Override
	public Set<Property<?>> getSupportedProperties(Value<?>...values) {
		Set<Property<?>> properties = super.getSupportedProperties(values);
		properties.add(new PatternProperty());
		properties.add(new MinLengthProperty());
		properties.add(new MaxLengthProperty());
		properties.add(new LengthProperty());
		return properties;
	}
	
	class StringValidator implements Validator<T> {

		@SuppressWarnings("rawtypes")
		private Validator stringValidator;
		
		private Value<?> [] values;
		
		public StringValidator(Validator<? extends CharSequence> stringValidator, Value<?>... values) {
			this.stringValidator = stringValidator;
			this.values = values;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<ValidationMessage> validate(T instance) {
			return instance == null ? new ArrayList<ValidationMessage>() : stringValidator.validate(BaseMarshallableSimpleType.this.marshal(instance, values));
		}

		@Override
		public Class<T> getValueClass() {
			return getInstanceClass();
		}
		
	}
}
