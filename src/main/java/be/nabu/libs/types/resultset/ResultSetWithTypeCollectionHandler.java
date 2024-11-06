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
