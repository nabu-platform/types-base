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

package be.nabu.libs.types.converters;

import be.nabu.libs.property.ValueUtils;
import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.BaseTypeInstance;
import be.nabu.libs.types.TypeConverterFactory;
import be.nabu.libs.types.api.SimpleType;
import be.nabu.libs.types.api.TypeConverterProvider;
import be.nabu.libs.types.api.Unmarshallable;
import be.nabu.libs.types.properties.ActualTypeProperty;

public class StringToDate implements TypeConverterProvider {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T convert(Object instance, Value<?>[] sourceParameters, Value<?>... targetParameters) {
		if (instance == null || instance.toString().trim().isEmpty())
			return null;
		SimpleType<?> actualType = ValueUtils.getValue(new ActualTypeProperty(), sourceParameters);
		if (actualType != null) {
			instance = ((Unmarshallable<?>) actualType).unmarshal(instance.toString(), sourceParameters);
			if (actualType.equals(getTargetType()))
				return (T) instance;
			else
				return TypeConverterFactory.getInstance().getConverter().convert(instance, new BaseTypeInstance(actualType, sourceParameters), new BaseTypeInstance(getTargetType(), targetParameters));
		}
		else
			return (T) getTargetType().unmarshal(instance.toString(), targetParameters);
	}

	@Override
	public be.nabu.libs.types.simple.String getSourceType() {
		return new be.nabu.libs.types.simple.String();
	}

	@Override
	public be.nabu.libs.types.simple.Date getTargetType() {
		return new be.nabu.libs.types.simple.Date();
	}

}
