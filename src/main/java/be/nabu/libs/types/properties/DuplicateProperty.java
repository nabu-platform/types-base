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

// this is (mostly) meant for databases where you use extensions and you want some fields duplicated cross table (e.g. id, created, modified...)
public class DuplicateProperty extends SimpleProperty<String> {

	private static DuplicateProperty instance = new DuplicateProperty();
	
	public static DuplicateProperty getInstance() {
		return instance;
	}
	
	public DuplicateProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "duplicate";
	}

}
