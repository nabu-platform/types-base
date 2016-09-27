package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;

public class MinLengthProperty extends SimpleProperty<Integer> implements ComparableProperty<Integer> {

	private static MinLengthProperty instance = new MinLengthProperty();
	
	public static MinLengthProperty getInstance() {
		return instance;
	}
	
	public MinLengthProperty() {
		super(Integer.class);
	}

	@Override
	public String getName() {
		return "minLength";
	}

	@Override
	public boolean isSubset(Integer subsetValue, Integer broaderValue) {
		return broaderValue == null || (subsetValue != null && subsetValue.compareTo(broaderValue) >= 0);
	}
}
