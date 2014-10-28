package be.nabu.libs.types.converters;

import be.nabu.libs.property.ValueUtils;
import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.BaseTypeInstance;
import be.nabu.libs.types.TypeConverterFactory;
import be.nabu.libs.types.api.Marshallable;
import be.nabu.libs.types.api.SimpleType;
import be.nabu.libs.types.api.Type;
import be.nabu.libs.types.api.TypeConverterProvider;
import be.nabu.libs.types.properties.ActualTypeProperty;

public class DateToString implements TypeConverterProvider {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> T convert(Object instance, Value<?>[] sourceParameters, Value<?>... targetParameters) {
		if (instance == null)
			return null;
		SimpleType<?> actualType = ValueUtils.getValue(new ActualTypeProperty(), targetParameters);
		if (actualType != null) {
			if (actualType.equals(getSourceType()))
				return (T) ((Marshallable) actualType).marshal(instance, sourceParameters);
			else {
				instance = TypeConverterFactory.getInstance().getConverter().convert(instance, new BaseTypeInstance(getSourceType(), targetParameters), new BaseTypeInstance(actualType, sourceParameters));
				return (T) ((Marshallable) actualType).marshal(instance, sourceParameters);
			}
		}
		else
			return (T) ((Marshallable) getSourceType()).marshal(instance, targetParameters);
	}

	@Override
	public Type getSourceType() {
		return new be.nabu.libs.types.simple.Date();
	}

	@Override
	public Type getTargetType() {
		return new be.nabu.libs.types.simple.String();
	}

}
