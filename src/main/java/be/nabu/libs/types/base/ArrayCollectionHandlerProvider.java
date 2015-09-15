package be.nabu.libs.types.base;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import be.nabu.libs.types.IntegerCollectionProviderBase;

public class ArrayCollectionHandlerProvider extends IntegerCollectionProviderBase<Object[]> {

	private Class<? extends Object[]> arrayClazz;

	public ArrayCollectionHandlerProvider() {
		super(Object[].class);
	}
	
	public ArrayCollectionHandlerProvider(Class<? extends Object[]> arrayClazz) {
		super(Object[].class);
		this.arrayClazz = arrayClazz;
	}

	@Override
	public Object[] set(Object[] collection, Integer index, Object value) {
		if (collection.length <= index + 1)
			collection = Arrays.copyOf(collection, index + 1);
		collection[index] = value;
		return collection;
	}

	@Override
	public Object get(Object[] collection, Integer index) {
		return collection[index];
	}

	@Override
	public Class<Object[]> getCollectionClass() {
		return Object[].class;
	}

	@Override
	public Object[] create(Class<? extends Object[]> definitionClass, int size) {
		if (definitionClass == null) {
			definitionClass = arrayClazz;
		}
		return (Object[]) Array.newInstance(definitionClass.isArray() ? definitionClass.getComponentType() : definitionClass, size);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object[] delete(Object[] collection, Integer index) {
		List list = new ArrayList(Arrays.asList(collection));
		list.remove(index);
		return list.toArray((Object[]) Array.newInstance(collection.getClass().getComponentType(), list.size()));
	}

	@Override
	public Class<?> getComponentType(Type type) {
		// generics array
		if (type instanceof GenericArrayType) {
			Type genericComponentType = ((GenericArrayType) type).getGenericComponentType();
			if (genericComponentType instanceof ParameterizedType) {
				return (Class<?>) ((ParameterizedType) genericComponentType).getRawType();
			}
			else {
				return (Class<?>) genericComponentType;
			}
		}
		else if (!(type instanceof Class))
			throw new IllegalArgumentException("Need to pass along an array");
		else
			return ((Class<?>) type).getComponentType();
	}

	@Override
	public Collection<?> getAsCollection(Object[] collection) {
		return Arrays.asList(collection);
	}

	@Override
	public Collection<Integer> getIndexes(Object[] collection) {
		return generateIndexes(collection == null ? 0 : collection.length);
	}

}
