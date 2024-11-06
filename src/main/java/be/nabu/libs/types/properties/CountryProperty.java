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
