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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import be.nabu.libs.property.api.Enumerated;
import be.nabu.libs.validator.api.Validator;

public class TimeBlockProperty extends BaseProperty<TimeBlock> implements Enumerated<TimeBlock> {
	
	private static TimeBlockProperty instance = new TimeBlockProperty();
	
	public static TimeBlockProperty getInstance() {
		return instance;
	}
	
	private Set<TimeBlock> enumerations = new HashSet<TimeBlock>(Arrays.asList(TimeBlock.values()));
	
	@Override
	public Set<TimeBlock> getEnumerations() {
		return enumerations;
	}

	@Override
	public String getName() {
		return "period";
	}

	@Override
	public Validator<TimeBlock> getValidator() {
		return null;
	}

	@Override
	public Class<TimeBlock> getValueClass() {
		return TimeBlock.class;
	}
}
