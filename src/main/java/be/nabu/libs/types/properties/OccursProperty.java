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
import be.nabu.libs.property.api.PropertyWithDefault;
import be.nabu.libs.validator.RangeValidator;
import be.nabu.libs.validator.api.Validator;

abstract public class OccursProperty extends BaseProperty<Integer> implements Enumerated<Integer>, PropertyWithDefault<Integer> {

	private Validator<Integer> validator = new RangeValidator<Integer>(0, true, null, null);
	private Set<Integer> enumerations = new HashSet<Integer>(Arrays.asList(new Integer [] { 0, 1 }));
	
	@Override
	public Validator<Integer> getValidator() {
		return validator;
	}

	@Override
	public Set<Integer> getEnumerations() {
		return enumerations;
	}

	@Override
	public Class<Integer> getValueClass() {
		return Integer.class;
	}
	
	@Override
	public Integer getDefault() {
		return 1;
	}
}
