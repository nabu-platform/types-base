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

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
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
	
	public static Map<String, String> regexes = new HashMap<String, String>(); static {
		String timezone = "(?:z|Z|[+-][0-9]{2}:[+-][0-9]{2}|)";
		regexes.put("dateTime", "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}(?:\\.[0-9]+|)" + timezone);
		regexes.put("date", "[0-9]{4}-[0-9]{2}-[0-9]{2}" + timezone);
		regexes.put("time", "[0-9]{2}:[0-9]{2}:[0-9]{2}(?:\\.[0-9]+|)" + timezone);
		regexes.put("gDay", "---[0-9]{2}" + timezone);
		regexes.put("gMonth", "--[0-9]{2}" + timezone);
		regexes.put("gYear", "[+-]*[0-9]{4}" + timezone);
		regexes.put("gMonthDay", "--[0-9]{2}-[0-9]{2}" + timezone);
		regexes.put("gYearMonth", "[0-9]{4}-[0-9]{2}" + timezone);
		regexes.put("yyyy/MM", "[0-9]{4}/[0-9]{2}");
		regexes.put("yyyy/MM/dd", "[0-9]{4}/[0-9]{2}/[0-9]{2}");
		regexes.put("dd/MM/yyyy", "[0-9]{2}/[0-9]{2}/[0-9]{4}");
		regexes.put("HH:mm", "[0-9]{2}:[0-9]{2}");
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
