package be.nabu.libs.types.simple;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import be.nabu.libs.property.ValueUtils;
import be.nabu.libs.property.api.Property;
import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.TypeConverterFactory;
import be.nabu.libs.types.api.SimpleType;
import be.nabu.libs.types.api.TypeConverter;
import be.nabu.libs.types.api.Unmarshallable;
import be.nabu.libs.types.base.BaseMarshallableSimpleType;
import be.nabu.libs.types.properties.ActualTypeProperty;
import be.nabu.libs.validator.api.ValidationMessage;
import be.nabu.libs.validator.api.ValidationMessage.Severity;
import be.nabu.libs.validator.api.Validator;

public class String extends BaseMarshallableSimpleType<java.lang.String> implements Unmarshallable<java.lang.String> {
	
	public String() {
		super(java.lang.String.class);
	}

	private ActualTypeProperty actualTypeProperty = new ActualTypeProperty();
	
	private TypeConverter converter;
	
	private SimpleType<?> actualType;

	public TypeConverter getConverter() {
		if (converter == null)
			converter = TypeConverterFactory.getInstance().getConverter();
		return converter;
	}

	public void setConverter(TypeConverter converter) {
		this.converter = converter;
	}
	
	@Override
	public java.lang.String getName(Value<?>...values) {
		return actualType == null ? "string" : actualType.getName();
	}

	@Override
	public java.lang.String marshal(java.lang.String object, Value<?>...values) {
		return object;
	}

	@Override
	public java.lang.String unmarshal(java.lang.String content, Value<?>...values) {
		return content;
	}

	/**
	 * Only validation occurs against the actual type, in all other aspects it acts as a string
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Validator createValidator(Value<?>...values) {
		SimpleType<?> actualTypeValue = ValueUtils.getValue(actualTypeProperty, values);
		if (actualTypeValue != null)
			return new UnmarshallingValidator(actualTypeValue, values);
		else
			return super.createValidator(values);
	}
	
	@Override
	public Set<Property<?>> getSupportedProperties(Value<?>...values) {
		// the order is important here, we need the actualtype first
		Set<Property<?>> set = new LinkedHashSet<Property<?>>();
		SimpleType<?> actualTypeValue = ValueUtils.getValue(actualTypeProperty, values);
		set.add(actualTypeProperty);
		if (actualTypeValue != null)
			set.addAll(actualTypeValue.getSupportedProperties(values));
		else
			set.addAll(super.getSupportedProperties(values));
		return set;
	}
	
	@Override
	public java.lang.String getNamespace(Value<?>...values) {
		return XML_SCHEMA;
	}
	
	public static class UnmarshallingValidator<T> implements Validator<java.lang.String> {

		private SimpleType<T> simpleType;
		private Value<?> [] values;
		
		public UnmarshallingValidator(SimpleType<T> simpleType, Value<?>...values) {
			this.simpleType = simpleType;
			this.values = values;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public List<ValidationMessage> validate(java.lang.String instance) {
			if (!(simpleType instanceof Unmarshallable))
				return Arrays.asList(new ValidationMessage [] { new ValidationMessage(Severity.ERROR, "The actual type does not support unmarshalling so it can not be wrapped in a string field") });
			else {
				Validator validator = simpleType.createValidator(values);
				return validator.validate(((Unmarshallable<T>) simpleType).unmarshal(instance, values));
			}
		}

		@Override
		public java.lang.Class<java.lang.String> getValueClass() {
			return java.lang.String.class;
		}
		
	}
}
