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

package be.nabu.libs.types.base;

import java.util.Set;

import be.nabu.libs.property.api.Property;
import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.DefinedSimpleType;
import be.nabu.libs.types.properties.AggregateProperty;
import be.nabu.libs.types.properties.CalculationProperty;
import be.nabu.libs.types.properties.DefaultValueProperty;
import be.nabu.libs.types.properties.EnvironmentSpecificProperty;
import be.nabu.libs.types.properties.ForeignKeyProperty;
import be.nabu.libs.types.properties.ForeignNameProperty;
import be.nabu.libs.types.properties.GeneratedProperty;
import be.nabu.libs.types.properties.IndexedProperty;
import be.nabu.libs.types.properties.PrimaryKeyProperty;
import be.nabu.libs.types.properties.TranslatableProperty;
import be.nabu.libs.types.properties.UUIDFormatProperty;

/**
 * Simple types are the same as long as they are of the same class (functional singletons)
 */
abstract public class BaseSimpleType<T> extends BaseType<T> implements DefinedSimpleType<T> {

	private Class<T> type;
	
	public BaseSimpleType(Class<T> type) {
		this.type = type;
	}

	@Override
	public Class<T> getInstanceClass() {
		return type;
	}
	
	@Override
	public boolean equals(Object object) {
		return object != null && getClass().equals(object.getClass());
	}
	
	@Override
	public int hashCode() {
		return getInstanceClass().hashCode();
	}
	
	@Override
	public String getId() {
		return getInstanceClass().getName();
	}
	
	@Override
	public Set<Property<?>> getSupportedProperties(Value<?>...properties) {
		Set<Property<?>> set = super.getSupportedProperties(properties);
		set.add(ForeignKeyProperty.getInstance());
		set.add(ForeignNameProperty.getInstance());
		set.add(PrimaryKeyProperty.getInstance());
		set.add(IndexedProperty.getInstance());
		set.add(GeneratedProperty.getInstance());
		set.add(AggregateProperty.getInstance());
		set.add(CalculationProperty.getInstance());
		set.add(UUIDFormatProperty.getInstance());
		set.add(EnvironmentSpecificProperty.getInstance());
		set.add(TranslatableProperty.getInstance());
		set.add(DefaultValueProperty.getInstance());
		return set;
	}
}
