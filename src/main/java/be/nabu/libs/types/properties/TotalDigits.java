package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;

public class TotalDigits extends SimpleProperty<Integer> implements ComparableProperty<Integer> {

	private static TotalDigits instance = new TotalDigits();
	
	public static TotalDigits getInstance() {
		return instance;
	}
	
	public TotalDigits() {
		super(Integer.class);
	}
	
	@Override
	public String getName() {
		return "totalDigits";
	}

	@Override
	public boolean isSubset(Integer subsetValue, Integer broaderValue) {
		return broaderValue == null || (subsetValue != null && subsetValue < broaderValue);
	}

}