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

// if you have a foreign key, the foreign name will indicate the field (or fields in the future?) in the target that are a good representative name (e.g. for sorting) 
public class ForeignNameProperty extends SimpleProperty<String> {

	private static ForeignNameProperty instance = new ForeignNameProperty();
	
	public static ForeignNameProperty getInstance() {
		return instance;
	}
	
	public ForeignNameProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "foreignName";
	}

}
