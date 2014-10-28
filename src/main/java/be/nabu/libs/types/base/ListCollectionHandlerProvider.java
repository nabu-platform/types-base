package be.nabu.libs.types.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import be.nabu.libs.types.IntegerCollectionProviderBase;

@SuppressWarnings("rawtypes")
public class ListCollectionHandlerProvider extends IntegerCollectionProviderBase<List> {

	public ListCollectionHandlerProvider() {
		super(List.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List set(List collection, Integer index, Object value) {
		if (collection.size() < index) {
			collection.addAll(Arrays.asList(new Object[index + 1 - collection.size()]));
			collection.add(value);
		}
		else if (collection.size() == index) {
			collection.add(value);
		}
		else {
			collection.set(index, value);
		}
		return collection;
	}

	@Override
	public Object get(List collection, Integer index) {
		return collection.get(index);
	}

	@Override
	public Class<List> getCollectionClass() {
		return List.class;
	}

	@Override
	public List delete(List collection, Integer index) {
		collection.remove(index);
		return collection;
	}

	@Override
	public List create(Class<? extends List> definitionClass, int size) {
		if (definitionClass == null || List.class.equals(definitionClass)) {
			return new ArrayList(size);
		}
		else {
			try {
				return definitionClass.newInstance();
			}
			catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			catch (InstantiationException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public Class<?> getComponentType(Type type) {
		if (!(type instanceof ParameterizedType))
			throw new IllegalArgumentException("Raw lists are not supported, you need to add generics");
		else {
			Type result = ((ParameterizedType) type).getActualTypeArguments()[0];
			if (result instanceof ParameterizedType) {
				return (Class<?>) ((ParameterizedType) result).getRawType();
			}
			else if (!Class.class.equals(result.getClass())) {
				throw new IllegalArgumentException("The parameter " + result + " is not a class");
			}
			return (Class<?>) result;
		}
	}

	@Override
	public Collection<?> getAsCollection(List collection) {
		return collection;
	}

	@Override
	public Collection<Integer> getIndexes(List collection) {
		return generateIndexes(collection == null ? 0 : collection.size());
	}

}
