package be.nabu.libs.types.converters;

import be.nabu.libs.converter.api.ConverterProvider;
import be.nabu.libs.types.api.DefinedSimpleType;
import be.nabu.libs.types.api.SimpleType;

@SuppressWarnings("rawtypes")
public class SimpleTypeToString implements ConverterProvider<SimpleType, String> {
	
	@Override
	public String convert(SimpleType instance) {
		if (instance == null) {
			return null;
		}
		else if (instance instanceof DefinedSimpleType) {
			return ((DefinedSimpleType) instance).getId();
		}
		else {
			return instance.getInstanceClass().getName();
		}
	}

	@Override
	public Class<SimpleType> getSourceClass() {
		return SimpleType.class;
	}

	@Override
	public Class<String> getTargetClass() {
		return String.class;
	}

}
