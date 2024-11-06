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

// whether or not the data in this field is identifiable
// this can be interesting to automatically pull identifiable data for reporting or anonimize it
public class TranslatableProperty extends SimpleProperty<Boolean> implements PropertyWithDefault<Boolean> {

	private static TranslatableProperty instance = new TranslatableProperty();
	
	public static TranslatableProperty getInstance() {
		return instance;
	}
	
	public TranslatableProperty() {
		super(Boolean.class);
	}

	@Override
	public String getName() {
		return "translatable";
	}

	@Override
	public Boolean getDefault() {
		return false;
	}

}
