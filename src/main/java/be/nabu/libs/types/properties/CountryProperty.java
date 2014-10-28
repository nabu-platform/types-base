package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;

public class CountryProperty extends SimpleProperty<String> implements ComparableProperty<String> {

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
