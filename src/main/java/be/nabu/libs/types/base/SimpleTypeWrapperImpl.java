/*
* Copyright (C) 2014 Alexander Verbruggen
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program. If not, see <https://www.gnu.org/licenses/>.
*/

package be.nabu.libs.types.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import be.nabu.libs.types.api.DefinedSimpleType;
import be.nabu.libs.types.api.SimpleTypeWrapper;

public class SimpleTypeWrapperImpl implements SimpleTypeWrapper {

	private static Map<Class<?>, Class<?>> boxedTypes = new HashMap<Class<?>, Class<?>>();
	
	private Map<Class<?>, DefinedSimpleType<?>> resolvedTypes = new HashMap<Class<?>, DefinedSimpleType<?>>();
	
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List<DefinedSimpleType<?>> getSimpleTypes() {
		if (simpleTypes.isEmpty()) {
			synchronized(simpleTypes) {
				if (simpleTypes.isEmpty()) {
					try {
						// let's try this with custom service loading based on a configuration
						Class<?> clazz = getClass().getClassLoader().loadClass("be.nabu.utils.services.ServiceLoader");
						Method declaredMethod = clazz.getDeclaredMethod("load", Class.class);
						simpleTypes.addAll((List<DefinedSimpleType<?>>) declaredMethod.invoke(null, DefinedSimpleType.class));
					}
					catch (ClassNotFoundException e) {
						// ignore, the framework is not present
					}
					catch (NoSuchMethodException e) {
						// corrupt framework?
						throw new RuntimeException(e);
					}
					catch (SecurityException e) {
						throw new RuntimeException(e);
					}
					catch (IllegalAccessException e) {
						// ignore
					}
					catch (InvocationTargetException e) {
						// ignore
					}
					if (simpleTypes.isEmpty()) {
						ServiceLoader<DefinedSimpleType> serviceLoader = ServiceLoader.load(DefinedSimpleType.class);
						for (DefinedSimpleType provider : serviceLoader) {
							simpleTypes.add(provider);
						}
					}
				}
			}
		}
		return simpleTypes;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T> DefinedSimpleType<T> wrap(Class<T> object) {
		if (!resolvedTypes.containsKey(object)) {
			synchronized(resolvedTypes) {
				if (!resolvedTypes.containsKey(object)) {
					if (object.isPrimitive()) {
						object = (Class<T>) boxedTypes.get(object);
					}
					for (DefinedSimpleType simpleType : getSimpleTypes()) {
						if (simpleType.getInstanceClass().isAssignableFrom(object)) {
							resolvedTypes.put(object, simpleType);
							break;
						}
					}
					// if it's an enumeration, just wrap it
					if (!resolvedTypes.containsKey(object) && Enum.class.isAssignableFrom(object)) {
						resolvedTypes.put(object, (DefinedSimpleType<T>) new be.nabu.libs.types.simple.Enum((Class<Enum>) object));
					}
				}
			}
		}
		return (DefinedSimpleType<T>) resolvedTypes.get(object);
	}
	@Override
	public DefinedSimpleType<?> getByName(String name) {
		if (name == null || name.startsWith("[")) {
			return null;
		}
		for (DefinedSimpleType<?> simpleType : getSimpleTypes()) {
			if (name.equals(simpleType.getName())) {
				return simpleType;
			}
		}
		return null;
	}
}
