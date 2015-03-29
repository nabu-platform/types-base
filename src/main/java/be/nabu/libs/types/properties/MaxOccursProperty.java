package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;

public class MaxOccursProperty extends OccursProperty implements ComparableProperty<Integer> {
	
	private static MaxOccursProperty instance = new MaxOccursProperty();
	
	public static MaxOccursProperty getInstance() {
		return instance;
	}
	
	@Override
	public String getName() {
		return "maxOccurs";
	}
	
	@Override
	public boolean isSubset(Integer subsetValue, Integer broaderValue) {
		return broaderValue == null || (subsetValue != null && subsetValue.compareTo(broaderValue) <= 0);
	}
}
