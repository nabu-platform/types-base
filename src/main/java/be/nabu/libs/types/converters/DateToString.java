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
import be.nabu.libs.types.api.Marshallable;
import be.nabu.libs.types.api.SimpleType;
import be.nabu.libs.types.api.Type;
import be.nabu.libs.types.api.TypeConverterProvider;
import be.nabu.libs.types.properties.ActualTypeProperty;

public class DateToString implements TypeConverterProvider {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> T convert(Object instance, Value<?>[] sourceParameters, Value<?>... targetParameters) {
		if (instance == null)
			return null;
		SimpleType<?> actualType = ValueUtils.getValue(ActualTypeProperty.getInstance(), targetParameters);
		// if we have an actual type in the target parameters, we want to use those settings (if applicable) for the conversion
		if (actualType != null) {
			if (actualType.equals(getSourceType()))
				return (T) ((Marshallable) actualType).marshal(instance, targetParameters);
			else {
				instance = TypeConverterFactory.getInstance().getConverter().convert(instance, new BaseTypeInstance(getSourceType(), sourceParameters), new BaseTypeInstance(actualType, targetParameters));
				return (T) ((Marshallable) actualType).marshal(instance, targetParameters);
			}
		}
		// otherwise, the properties in the source date instance are used
		else
			return (T) ((Marshallable) getSourceType()).marshal(instance, sourceParameters);
	}

	@Override
	public Type getSourceType() {
		return new be.nabu.libs.types.simple.Date();
	}

	@Override
	public Type getTargetType() {
		return new be.nabu.libs.types.simple.String();
	}

}
