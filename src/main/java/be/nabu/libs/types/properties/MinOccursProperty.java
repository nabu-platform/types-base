package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;

public class MinOccursProperty extends OccursProperty implements ComparableProperty<Integer> {
	
	private static MinOccursProperty instance = new MinOccursProperty();
	
	public static MinOccursProperty getInstance() {
		return instance;
	}
	
	@Override
	public String getName() {
		return "minOccurs";
	}
	@Override
	public boolean isSubset(Integer subsetValue, Integer broaderValue) {
		return broaderValue == null || (subsetValue != null && subsetValue.compareTo(broaderValue) >= 0);
	}

}
