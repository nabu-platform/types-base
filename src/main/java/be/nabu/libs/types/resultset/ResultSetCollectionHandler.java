package be.nabu.libs.types.resultset;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

import be.nabu.libs.property.ValueUtils;
import be.nabu.libs.types.SimpleTypeWrapperFactory;
import be.nabu.libs.types.TypeUtils;
import be.nabu.libs.types.api.CollectionHandlerProvider;
import be.nabu.libs.types.api.ComplexContent;
import be.nabu.libs.types.api.ComplexType;
import be.nabu.libs.types.api.DefinedSimpleType;
import be.nabu.libs.types.api.Element;
import be.nabu.libs.types.api.ModifiableComplexType;
import be.nabu.libs.types.api.ModifiableComplexTypeGenerator;
import be.nabu.libs.types.api.SimpleType;
import be.nabu.libs.types.base.SimpleElementImpl;
import be.nabu.libs.types.base.ValueImpl;
import be.nabu.libs.types.properties.FormatProperty;
import be.nabu.libs.types.properties.MinOccursProperty;
import be.nabu.libs.types.properties.TimezoneProperty;
import be.nabu.libs.types.utils.DateUtils;
import be.nabu.libs.types.utils.DateUtils.Granularity;

public class ResultSetCollectionHandler implements CollectionHandlerProvider<ResultSet, Integer> {

	private ComplexType type;
	private ModifiableComplexTypeGenerator generator;

	public ResultSetCollectionHandler(ModifiableComplexTypeGenerator generator) {
		this.generator = generator;
	}
	
	public ResultSetCollectionHandler(ComplexType type) {
		this.type = type;
	}
	
	@Override
	public ResultSet create(Class<? extends ResultSet> definitionClass, int size) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Class<?> getComponentType(Type type) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResultSet set(ResultSet collection, Integer index, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object get(ResultSet collection, Integer index) {
		try {
			collection.absolute(index);
			return convert(collection, type);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ResultSet delete(ResultSet collection, Integer index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Class<ResultSet> getCollectionClass() {
		return ResultSet.class;
	}

	@Override
	public Class<Integer> getIndexClass() {
		return Integer.class;
	}

	@Override
	public Collection<?> getAsCollection(ResultSet collection) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Iterable<?> getAsIterable(final ResultSet collection) {
		final ComplexType type = this.type == null ? newType(collection, generator) : this.type;
		return newIterable(collection, type, false);
	}

	@SuppressWarnings("rawtypes")
	public static Iterable<?> newIterable(final ResultSet collection, final ComplexType type, final boolean initialNext) {
		return new Iterable() {
			@Override
			public Iterator iterator() {
				final boolean isNext;
				try {
					// if we are not in forward only mode and we have moved beyond the zeroth or first row (depending on the initial next value), let's reset
					if (collection.getType() != ResultSet.TYPE_FORWARD_ONLY && ((initialNext && collection.getRow() > 1) || (!initialNext && collection.getRow() > 0))) {
						collection.first();
						isNext = true;
					}
					else {
						isNext = initialNext;
					}
				}
				catch (SQLException e) {
					throw new RuntimeException(e);
				}
				return new Iterator() {
					private boolean next = isNext;
					@Override
					public boolean hasNext() {
						if (!next) {
							try {
								next = collection.next();
							}
							catch (SQLException e) {
								throw new RuntimeException(e);
							}
						}
						return next;
					}
					@Override
					public Object next() {
						if (hasNext()) {
							ComplexContent convert = convert(collection, type);
							next = false;
							return convert;
						}
						return null;
					}
				};
			}
		};
	}

	@Override
	public Collection<Integer> getIndexes(ResultSet collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Integer unmarshalIndex(String index) {
		return index == null ? null : Integer.parseInt(index);
	}

	@Override
	public String marshalIndex(Integer index) {
		return index == null ? null : index.toString();
	}

	public static ComplexContent convert(ResultSet executeQuery, ComplexType resultType) {
		ComplexContent result = resultType.newInstance();
		int column = 1;
		Element<?> keyValueChild = null;
		for (Element<?> child : TypeUtils.getAllChildren(resultType)) {
			if (child.getType() instanceof SimpleType) {
				try {
					SimpleType<?> simpleType = (SimpleType<?>) child.getType();
					Object value;
					if (Date.class.isAssignableFrom(simpleType.getInstanceClass())) {
						String format = ValueUtils.getValue(FormatProperty.getInstance(), child.getProperties());
						Granularity granularity = format == null ? Granularity.TIMESTAMP : DateUtils.getGranularity(format);
						TimeZone timezone = ValueUtils.getValue(TimezoneProperty.getInstance(), child.getProperties());
						Calendar calendar = Calendar.getInstance(timezone);
						switch(granularity) {
							case DATE:
								value = executeQuery.getDate(column++, calendar);
							break;
							case TIME:
								value = executeQuery.getTime(column++, calendar);
							break;
							case TIMESTAMP:
								value = executeQuery.getTimestamp(column++, calendar);
							break;
							default:
								throw new IllegalArgumentException("Unknown date granularity: " + granularity);
						}
					}
					else {
						value = executeQuery.getObject(column++);
					}
					// conversion should be handled by the result instance
					result.set(child.getName(), value);
				}
				catch (Exception e) {
					throw new RuntimeException("Could not set value for field: " + child.getName(), e);
				}
			}
			// a list of key value pairs
			else if (child.getType() instanceof ComplexType && child.getType().isList(child.getProperties()) && ((ComplexType) child.getType()).get("key") != null && ((ComplexType) child.getType()).get("value") != null) {
				keyValueChild = child;
			}
		}
		if (keyValueChild != null) {
			try {
				ResultSetMetaData metaData = executeQuery.getMetaData();
				int columnCount = metaData.getColumnCount();
				if (column < columnCount) {
					for (int i = column; i < columnCount; i++) {
						Object value = executeQuery.getObject(i);
						if (value != null) {
							String key = metaData.getColumnLabel(i);
							result.set(keyValueChild.getName() + "[" + (i - column) + "]/key", key);
							result.set(keyValueChild.getName() + "[" + (i - column) + "]/value", value);
						}
					}
				}
			}
			catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ComplexType newType(ResultSet set, ModifiableComplexTypeGenerator generator) {
		try {
			ModifiableComplexType structure = generator.newComplexType();
			ResultSetMetaData metaData = set.getMetaData();
			for (int i = 0; i < metaData.getColumnCount(); i++) {
				String columnClassName = metaData.getColumnClassName(i);
				Class<?> loadClass = Thread.currentThread().getContextClassLoader().loadClass(columnClassName);
				DefinedSimpleType<?> wrap = SimpleTypeWrapperFactory.getInstance().getWrapper().wrap(loadClass);
				if (wrap == null) {
					wrap = SimpleTypeWrapperFactory.getInstance().getWrapper().wrap(String.class);
				}
				structure.add(new SimpleElementImpl(camelCaseCharacter(metaData.getColumnLabel(i), '_'), wrap, structure, new ValueImpl<Integer>(MinOccursProperty.getInstance(), 0)));
			}
			return structure;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String camelCaseCharacter(String name, char character) {
		StringBuilder builder = new StringBuilder();
		int index = -1;
		int lastIndex = 0;
		boolean first = true;
		while ((index = name.indexOf(character, index + 1)) > lastIndex) {
			String substring = name.substring(lastIndex, index);
			if (substring.isEmpty()) {
				continue;
			}
			else if (first) {
				builder.append(substring);
				first = false;
			}
			else {
				builder.append(substring.substring(0, 1).toUpperCase() + substring.substring(1));
			}
			lastIndex = index + 1;
		}
		if (lastIndex < name.length() - 1) {
			if (first) {
				builder.append(name.substring(lastIndex));
			}
			else {
				builder.append(name.substring(lastIndex, lastIndex + 1).toUpperCase()).append(name.substring(lastIndex + 1));
			}
		}
		return builder.toString();
	}
}
