package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;

public class CountryProperty extends SimpleProperty<String> implements ComparableProperty<String> {

	private static CountryProperty instance = new CountryProperty();
	
	public static CountryProperty getInstance() {
		return instance;
	}
	
	public CountryProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "country";
	}

	@Override
	public boolean isSubset(String subsetValue, String broaderValue) {
		return true;
	}

}
