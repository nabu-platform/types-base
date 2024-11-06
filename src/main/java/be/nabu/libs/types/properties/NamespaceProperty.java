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

public class NamespaceProperty extends SimpleProperty<String> implements ComparableProperty<String> {

	private static NamespaceProperty instance = new NamespaceProperty();
	
	public static NamespaceProperty getInstance() {
		return instance;
	}
	
	public static final String DEFAULT_NAMESPACE = "##default";
	
	public NamespaceProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "namespace";
	}

	@Override
	public boolean isSubset(String arg0, String arg1) {
		return true;
	}

}
