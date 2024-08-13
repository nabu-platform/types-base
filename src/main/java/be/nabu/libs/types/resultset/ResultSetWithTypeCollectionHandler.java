package be.nabu.libs.types.resultset;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.Collection;

import be.nabu.libs.types.TypeUtils;
import be.nabu.libs.types.api.TypedCollectionHandlerProvider;

public class ResultSetWithTypeCollectionHandler implements TypedCollectionHandlerProvider<ResultSetWithType, Integer> {

	@Override
	public ResultSetWithType create(Class<? extends ResultSetWithType> definitionClass, int size) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Class<?> getComponentType(Type type) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public ResultSetWithType set(ResultSetWithType collection, Integer index, Object value) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Object get(ResultSetWithType collection, Integer index) {
		try {
			collection.getSet().absolute(index);
			return ResultSetCollectionHandler.convert(collection.getSet(), collection.getType());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public ResultSetWithType delete(ResultSetWithType collection, Integer index) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Class<ResultSetWithType> getCollectionClass() {
		return ResultSetWithType.class;
	}
	
	@Override
	public Class<Integer> getIndexClass() {
		return Integer.class;
	}
	
	@Override
	public Collection<?> getAsCollection(ResultSetWithType collection) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Iterable<?> getAsIterable(final ResultSetWithType collection) {
		return ResultSetCollectionHandler.newIterable(collection.getSet(), collection.getType(), collection.isNexted());
	}
	
	@Override
	public Collection<Integer> getIndexes(ResultSetWithType collection) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Integer unmarshalIndex(String index, ResultSetWithType collection) {
		return index == null ? null : Integer.parseInt(index);
	}
	
	@Override
	public String marshalIndex(Integer index) {
		return index == null ? null : index.toString();
	}

	@Override
	public boolean isCompatible(ResultSetWithType collection, be.nabu.libs.types.api.Type valueType) {
		return valueType.equals(collection.getType()) || !TypeUtils.getUpcastPath(collection.getType(), valueType).isEmpty();
	}

}
