package be.nabu.libs.types.converters;

import be.nabu.libs.converter.api.PrioritizedConverter;
import be.nabu.libs.types.SimpleTypeWrapperFactory;
import be.nabu.libs.types.api.DefinedSimpleType;
import be.nabu.libs.types.api.Marshallable;
import be.nabu.libs.types.api.SimpleTypeWrapper;

public class MarshallableSimpleTypeToString implements PrioritizedConverter {

	private SimpleTypeWrapper wrapper = SimpleTypeWrapperFactory.getInstance().getWrapper();
	
	@Override
	public boolean canConvert(Class<?> arg0, Class<?> arg1) {
		if (String.class.equals(arg1)) {
			DefinedSimpleType<?> wrap = wrapper.wrap(arg0);
			return wrap instanceof Marshallable;
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> T convert(Object arg0, Class<T> arg1) {
		if (arg0 != null && String.class.equals(arg1)) {
			DefinedSimpleType<?> wrap = wrapper.wrap(arg0.getClass());
			if (wrap instanceof Marshallable) {
				return (T) ((Marshallable) wrap).marshal(arg0);
			}
		}
		return null;
	}

	@Override
	public int getPriority() {
		return -1;
	}

}
