package be.nabu.libs.types.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import be.nabu.libs.types.IntegerCollectionProviderBase;

/**
 * Note: not all collections have guaranteed orderings. It is partly up to the developer to know this.
 * For a set, the code will return a LinkedHashSet to maintain order for as long as possible but if someone specifically uses a HashSet, all index access is random.
 */
@SuppressWarnings("rawtypes")
public class GenericCollectionHandlerProvider extends IntegerCollectionProviderBase<Collection> {

	public GenericCollectionHandlerProvider() {
		super(Collection.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection set(Collection collection, Integer index, Object value) {
		if (collection.size() < index) {
			collection.addAll(Arrays.asList(new Object[index + 1 - collection.size()]));
			collection.add(value);
		}
		else if (collection.size() == index) {
			collection.add(value);
		}
		else if (collection instanceof List) {
			((List) collection).set(index, value);
		}
		else {
			// wrap it in an index-accessible list
			List list = new ArrayList(collection);
			list.set(index, value);
			try {
				Constructor<? extends Collection> constructor = collection.getClass().getConstructor(Collection.class);
				if (constructor == null) {
					throw new RuntimeException("Could not find a constructor to reconstruct the collection of type " + collection.getClass().getName());
				}
				collection = constructor.newInstance(list);
			}
			catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			catch (InstantiationException e) {
				throw new RuntimeException(e);
			}
			catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			}
			catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			}
			catch (NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		}
		return collection;
	}

	@Override
	public Object get(Collection collection, Integer index) {
		int i = 0;
		Iterator iterator = collection.iterator();
		while (iterator.hasNext() && i < index) {
			iterator.next();
			i++;
		}
		return i == index ? iterator.next() : null;
	}

	@Override
	public Class<Collection> getCollectionClass() {
		return Collection.class;
	}

	@Override
	public Collection delete(Collection collection, Integer index) {
		int i = 0;
		Iterator iterator = collection.iterator();
		while (iterator.hasNext()) {
			if (i == index) {
				iterator.remove();
				break;
			}
		}
		return collection;
	}

	@Override
	public Collection create(Class<? extends Collection> definitionClass, int size) {
		// if you want _any_ collection, just return a list
		if (definitionClass == null || List.class.isAssignableFrom(definitionClass) || Collection.class.equals(definitionClass)) {
			return new ArrayList(size);
		}
		else if (SortedSet.class.isAssignableFrom(definitionClass)) {
			return new TreeSet();
		}
		// check interfaces
		else if (Set.class.isAssignableFrom(definitionClass)) {
			return new LinkedHashSet();
		}
		else if (Deque.class.isAssignableFrom(definitionClass) || Queue.class.isAssignableFrom(definitionClass)) {
			return new ArrayDeque();
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
		if (!(type instanceof ParameterizedType)) {
			throw new IllegalArgumentException("Raw collections are not supported, you need to add generics to: " + type);
		}
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
	public Collection<?> getAsCollection(Collection collection) {
		return collection;
	}

	@Override
	public Collection<Integer> getIndexes(Collection collection) {
		return generateIndexes(collection == null ? 0 : collection.size());
	}
}
