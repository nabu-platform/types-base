package be.nabu.libs.types.base;

import java.lang.reflect.Type;
import java.sql.Array;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

import be.nabu.libs.types.IntegerCollectionProviderBase;

public class JDBCArrayCollectionHandlerProvider extends IntegerCollectionProviderBase<Array> {

	public JDBCArrayCollectionHandlerProvider() {
		super(Array.class);
	}

	@Override
	public Array create(Class<? extends Array> definitionClass, int size) {
		throw new UnsupportedOperationException("Can not instantiate the generic jdbc array interface");
	}

	@Override
	public Class<?> getComponentType(Type type) {
		// there is actually a get base type thing in the Array instance
		// but we don't have the intsance here and still need to translate it from JDBC
		return Object.class;
	}

	@Override
	public Array set(Array collection, Integer index, Object value) {
		throw new UnsupportedOperationException("The jdbc array interface is read-only");
	}

	@Override
	public Object get(Array collection, Integer index) {
		try {
			// this throws org.postgresql.util.PSQLException: The array index is out of range: 0
			//return collection == null ? null : ((Object[]) collection.getArray((long) index, 1))[0];
			return collection == null ? null : ((Object[]) collection.getArray())[index];
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Array delete(Array collection, Integer index) {
		throw new UnsupportedOperationException("The jdbc array interface is read-only");
	}

	@Override
	public Collection<?> getAsCollection(Array collection) {
		try {
			return Arrays.asList((Object[]) collection.getArray());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection<Integer> getIndexes(Array collection) {
		try {
			return generateIndexes(collection == null ? 0 : ((Object[]) collection.getArray()).length);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
