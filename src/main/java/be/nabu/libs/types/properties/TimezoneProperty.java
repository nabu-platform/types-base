/*
* Copyright (C) 2014 Alexander Verbruggen
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program. If not, see <https://www.gnu.org/licenses/>.
*/

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
