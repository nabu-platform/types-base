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

import be.nabu.libs.property.api.PropertyWithDefault;

/**
 * Mostly relevant when marshalling, e.g. a raw string in json marshalling is injected without further encoding assuming it is pre-formatted content
 */
public class RawProperty extends SimpleProperty<Boolean> implements PropertyWithDefault<Boolean> {

	private static RawProperty instance = new RawProperty();
	
	public static RawProperty getInstance() {
		return instance;
	}
	
	public RawProperty() {
		super(Boolean.class);
	}
	
	@Override
	public String getName() {
		return "raw";
	}

	@Override
	public Boolean getDefault() {
		return false;
	}

}