package be.nabu.libs.types.properties;

import java.util.TimeZone;

import be.nabu.libs.property.api.ComparableProperty;
import be.nabu.libs.property.api.PropertyWithDefault;

public class TimezoneProperty extends SimpleProperty<TimeZone> implements PropertyWithDefault<TimeZone>, ComparableProperty<TimeZone> {

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

}
