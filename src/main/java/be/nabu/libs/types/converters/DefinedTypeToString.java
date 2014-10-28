package be.nabu.libs.types.converters;

import be.nabu.libs.converter.api.ConverterProvider;
import be.nabu.libs.types.api.DefinedType;

public class DefinedTypeToString implements ConverterProvider<DefinedType, String> {

	@Override
	public String convert(DefinedType instance) {
		return instance == null ? null : instance.getId();
	}

	@Override
	public Class<DefinedType> getSourceClass() {
		return DefinedType.class;
	}

	@Override
	public Class<String> getTargetClass() {
		return String.class;
	}

}
