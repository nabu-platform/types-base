package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;

public class MaxLengthProperty extends SimpleProperty<Integer> implements ComparableProperty<Integer> {
	
	private static MaxLengthProperty instance = new MaxLengthProperty();
	
	public static MaxLengthProperty getInstance() {
		return instance;
	}

	public MaxLengthProperty() {
		super(Integer.class);
	}

	@Override
	public String getName() {
		return "maxLength";
	}

	@Override
	public boolean isSubset(Integer subsetValue, Integer broaderValue) {
		return broaderValue == null || (subsetValue != null && subsetValue.compareTo(broaderValue) <= 0);
	}
	
}
