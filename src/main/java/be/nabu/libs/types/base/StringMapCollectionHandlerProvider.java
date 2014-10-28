package be.nabu.libs.types.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
		else
			return (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[1];
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
	public String unmarshalIndex(String index) {
		return index;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<String> getIndexes(Map collection) {
		return collection == null ? new HashSet<String>() : collection.keySet();
	}

}
