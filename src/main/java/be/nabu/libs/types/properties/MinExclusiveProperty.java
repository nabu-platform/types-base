package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;

public class MinExclusiveProperty<T extends Comparable<T>> extends SimpleProperty<T> implements ComparableProperty<T> {

	@SuppressWarnings({ "rawtypes" })
	private static Class comparable() {
		return Comparable.class;
	}
	
	@SuppressWarnings("unchecked")
	public MinExclusiveProperty() {
		super((Class<T>) comparable());
	}
	
	@Override
	public String getName() {
		return "minExclusive";
	}

	@Override
	public boolean isSubset(T subsetValue, T broaderValue) {
		return broaderValue == null || (subsetValue != null && subsetValue.compareTo(broaderValue) >= 0);
	}
}
