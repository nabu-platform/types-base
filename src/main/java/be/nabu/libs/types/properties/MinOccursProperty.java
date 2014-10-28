package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;

public class MinOccursProperty extends OccursProperty implements ComparableProperty<Integer> {
	@Override
	public String getName() {
		return "minOccurs";
	}
	@Override
	public boolean isSubset(Integer subsetValue, Integer broaderValue) {
		return broaderValue == null || (subsetValue != null && subsetValue.compareTo(broaderValue) >= 0);
	}

}
