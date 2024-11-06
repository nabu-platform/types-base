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

/**
 * Allows setting an id on a type, this could be interesting to set for example a globally unique way of identifying this particular type
 * It was originally introduced to provide backwards compatibility with UML where unique ids were assigned to types for referencing
 */
public class IdProperty extends SimpleProperty<String> {

	private static IdProperty instance = new IdProperty();
	
	public static IdProperty getInstance() {
		return instance;
	}
	
	public IdProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "id";
	}

}
