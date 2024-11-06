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

public class ForeignKeyProperty extends SimpleProperty<String> {

	private static ForeignKeyProperty instance = new ForeignKeyProperty();
	
	public static ForeignKeyProperty getInstance() {
		return instance;
	}
	
	public ForeignKeyProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "foreignKey";
	}

}
