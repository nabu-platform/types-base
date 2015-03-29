package be.nabu.libs.types.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import be.nabu.libs.property.api.ComparableProperty;
import be.nabu.libs.property.api.Enumerated;
import be.nabu.libs.property.api.PropertyWithDefault;

public class TimezoneProperty extends SimpleProperty<TimeZone> implements PropertyWithDefault<TimeZone>, ComparableProperty<TimeZone>, Enumerated<TimeZone> {

	private static TimezoneProperty instance = new TimezoneProperty();
	
	private static Set<TimeZone> available; static {
		available = new LinkedHashSet<TimeZone>();
		List<String> availableIDs = new ArrayList<String>(Arrays.asList(TimeZone.getAvailableIDs()));
		Collections.sort(availableIDs);
		for (String id : availableIDs) {
			available.add(TimeZone.getTimeZone(id));
		}
	}
	
	public static TimezoneProperty getInstance() {
		return instance;
	}
	
	public TimezoneProperty() {
		super(TimeZone.class);
	}

	@Override
	public String getName() {
		return "timezone";
	}

	@Override
	public TimeZone getDefault() {
		return TimeZone.getDefault();
	}

	@Override
	public boolean isSubset(TimeZone subsetValue, TimeZone broaderValue) {
		return true;
	}

	@Override
	public Set<TimeZone> getEnumerations() {
		return available;
	}

}
