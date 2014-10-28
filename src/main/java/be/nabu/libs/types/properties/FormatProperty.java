package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;

public class FormatProperty extends SimpleProperty<String> implements ComparableProperty<String> {

	public FormatProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "format";
	}

	/**
	 * The format property does not reflect any property of the actual type but is used for marshalling/unmarshalling
	 * As such it does not matter what the value is
	 * This is especially true for all java.util.Date dates as they always contain all date information in UTC
	 * For this reason country, language and timezone are also not important for the actual data
	 */
	@Override
	public boolean isSubset(String subsetValue, String broaderValue) {
		return true;
	}

}
