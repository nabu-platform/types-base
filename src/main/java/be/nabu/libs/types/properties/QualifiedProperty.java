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

public class QualifiedProperty extends SimpleProperty<Boolean> implements PropertyWithDefault<Boolean> {

	private static QualifiedProperty instance = new QualifiedProperty();
	
	public static QualifiedProperty getInstance() {
		return instance;
	}
	
	public QualifiedProperty() {
		super(Boolean.class);
	}

	@Override
	public String getName() {
		return "qualified";
	}

	@Override
	public Boolean getDefault() {
		return false;
	}

}
