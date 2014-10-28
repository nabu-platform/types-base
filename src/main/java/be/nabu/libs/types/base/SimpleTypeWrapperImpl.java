package be.nabu.libs.types.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import be.nabu.libs.types.api.DefinedSimpleType;
import be.nabu.libs.types.api.SimpleTypeWrapper;

public class SimpleTypeWrapperImpl implements SimpleTypeWrapper {

	private static Map<Class<?>, Class<?>> boxedTypes = new HashMap<Class<?>, Class<?>>();
	
	static {
		boxedTypes.put(boolean.class, Boolean.class);
		boxedTypes.put(byte.class, Byte.class);
		boxedTypes.put(short.class, Short.class);
		boxedTypes.put(char.class, Character.class);
		boxedTypes.put(int.class, Integer.class);
		boxedTypes.put(long.class, Long.class);
		boxedTypes.put(float.class, Float.class);
	    boxedTypes.put(double.class, Double.class);
	}
	
	private List<DefinedSimpleType<?>> simpleTypes = new ArrayList<DefinedSimpleType<?>>();
	
	public void addSimpleType(DefinedSimpleType<?> simpleType) {
		simpleTypes.add(simpleType);
	}
	public void removeSimpleType(DefinedSimpleType<?> simpleType) {
		simpleTypes.remove(simpleType);
	}
	
	@SuppressWarnings("rawtypes")
	protected List<DefinedSimpleType<?>> getSimpleTypes() {
		if (simpleTypes.isEmpty()) {
			synchronized(simpleTypes) {
				if (simpleTypes.isEmpty()) {
					ServiceLoader<DefinedSimpleType> serviceLoader = ServiceLoader.load(DefinedSimpleType.class);
					for (DefinedSimpleType provider : serviceLoader)
						simpleTypes.add(provider);
				}
			}
		}
		return simpleTypes;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T> DefinedSimpleType<T> wrap(Class<T> object) {
		if (object.isPrimitive()) {
			object = (Class<T>) boxedTypes.get(object);
		}
		for (DefinedSimpleType simpleType : getSimpleTypes()) {
			if (simpleType.getInstanceClass().isAssignableFrom(object))
				return simpleType;
		}
		// if it's an enumeration, just wrap it
		if (Enum.class.isAssignableFrom(object)) {
			return (DefinedSimpleType<T>) new be.nabu.libs.types.simple.Enum((Class<Enum>) object);
		}
		return null;
	}
}
