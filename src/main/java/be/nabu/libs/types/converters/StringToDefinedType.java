package be.nabu.libs.types.converters;

import be.nabu.libs.converter.api.ConverterProvider;
import be.nabu.libs.types.DefinedTypeResolverFactory;
import be.nabu.libs.types.api.DefinedType;
import be.nabu.libs.types.api.DefinedTypeResolver;

public class StringToDefinedType implements ConverterProvider<String, DefinedType> {

	private DefinedTypeResolver resolver;

	public StringToDefinedType() {
		this(DefinedTypeResolverFactory.getInstance().getResolver());
	}
	
	public StringToDefinedType(DefinedTypeResolver resolver) {
		this.resolver = resolver;
	}
	
	@Override
	public DefinedType convert(String instance) {
		return instance == null ? null : resolver.resolve(instance);
	}

	@Override
	public Class<String> getSourceClass() {
		return String.class;
	}

	@Override
	public Class<DefinedType> getTargetClass() {
		return DefinedType.class;
	}
}
