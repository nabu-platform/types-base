package be.nabu.libs.types.converters;

import be.nabu.libs.converter.api.ConverterProvider;
import be.nabu.libs.types.SimpleTypeWrapperFactory;
import be.nabu.libs.types.api.SimpleType;
import be.nabu.libs.types.api.SimpleTypeWrapper;

@SuppressWarnings("rawtypes")
public class StringToSimpleType implements ConverterProvider<String, SimpleType> {

	private SimpleTypeWrapper wrapper = SimpleTypeWrapperFactory.getInstance().getWrapper();
	
	@Override
	public SimpleType convert(String instance) {
		try {
			return instance == null ? null : wrapper.wrap(Class.forName(instance));
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Class<String> getSourceClass() {
		return String.class;
	}

	@Override
	public Class<SimpleType> getTargetClass() {
		return SimpleType.class;
	}
}
