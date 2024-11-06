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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import be.nabu.libs.types.api.CollectionHandlerProvider;

@SuppressWarnings("rawtypes")
public class StringMapCollectionHandlerProvider<V> implements CollectionHandlerProvider<Map, String> {

	@Override
	public Map create(Class<? extends Map> arg0, int initialCapacity) {
		return new LinkedHashMap(initialCapacity);
	}

	@Override
	public Map delete(Map map, String index) {
		map.remove(index);
		return map;
	}

	@Override
	public Object get(Map map, String index) {
		return map == null ? null : map.get(index);
	}

	@Override
	public Collection<?> getAsCollection(Map map) {
		return map.values();
	}

	@Override
	public Class<Map> getCollectionClass() {
		return Map.class;
	}

	@Override
	public Class<?> getComponentType(Type type) {
		if (!(type instanceof ParameterizedType))
			throw new IllegalArgumentException("Raw maps are not supported, you need to add generics");
		else {
			Type result = ((ParameterizedType) type).getActualTypeArguments()[1];
			if (result instanceof ParameterizedType) {
				return (Class<?>) ((ParameterizedType) result).getRawType();
			}
			// using a generic parameter
			else if (result instanceof TypeVariable) {
				return Object.class;
			}
			else if (!Class.class.equals(result.getClass())) {
				throw new IllegalArgumentException("The parameter " + result + " (of type " + result.getClass() + ") is not a class");
			}
			return (Class<?>) result;
		}
	}

	@Override
	public String marshalIndex(String index) {
		return index;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map set(Map map, String index, Object value) {
		map.put(index, value);
		return map;
	}

	@Override
	public String unmarshalIndex(String index, Map collection) {
		return index;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<String> getIndexes(Map collection) {
		return collection == null ? new HashSet<String>() : collection.keySet();
	}

	@Override
	public Class<String> getIndexClass() {
		return String.class;
	}
	
}
