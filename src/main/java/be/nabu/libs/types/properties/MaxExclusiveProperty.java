package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;

public class MaxExclusiveProperty<T extends Comparable<T>> extends SimpleProperty<T> implements ComparableProperty<T> {

	@SuppressWarnings({ "rawtypes" })
	private static Class comparable() {
		return Comparable.class;
	}

	@SuppressWarnings("unchecked")
	public MaxExclusiveProperty() {
		super((Class<T>) comparable());
	}
	
	public MaxExclusiveProperty(Class<T> type) {
		super(type);
	}
	
	@Override
	public String getName() {
		return "maxExclusive";
	}

	@Override
	public boolean isSubset(T subsetValue, T broaderValue) {
		return broaderValue == null || (subsetValue != null && subsetValue.compareTo(broaderValue) <= 0);
	}

}
