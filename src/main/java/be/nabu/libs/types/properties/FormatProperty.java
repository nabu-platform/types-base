package be.nabu.libs.types.properties;

import java.util.LinkedHashSet;
import java.util.Set;

import be.nabu.libs.property.api.ComparableProperty;
import be.nabu.libs.property.api.Enumerated;

public class FormatProperty extends SimpleProperty<String> implements ComparableProperty<String>, Enumerated<String> {

	private static FormatProperty instance = new FormatProperty();

	private static Set<String> available; static {
		available = new LinkedHashSet<String>();
		available.add("dateTime");
		available.add("date");
		available.add("time");
		available.add("gDay");
		available.add("gMonth");
		available.add("gYear");
		available.add("gMonthDay");
		available.add("gYearMonth");
		available.add("yyyy/MM");
		available.add("yyyy/MM/dd");
		available.add("dd/MM/yyyy");
		available.add("HH:mm");
	}
	
	public static FormatProperty getInstance() {
		return instance;
	}
	
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

	@Override
	public Set<String> getEnumerations() {
		return available;
	}
	
}
