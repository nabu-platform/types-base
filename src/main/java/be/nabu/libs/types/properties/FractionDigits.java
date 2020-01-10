package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;

public class FractionDigits extends SimpleProperty<Integer> implements ComparableProperty<Integer> {

	private static FractionDigits instance = new FractionDigits();
	
	public static FractionDigits getInstance() {
		return instance;
	}
	
	public FractionDigits() {
		super(Integer.class);
	}
	
	@Override
	public String getName() {
		return "fractionDigits";
	}

	@Override
	public boolean isSubset(Integer subsetValue, Integer broaderValue) {
		return broaderValue == null || (subsetValue != null && subsetValue < broaderValue);
	}

}