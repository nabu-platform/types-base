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

import be.nabu.libs.converter.api.ConverterProvider;
import be.nabu.libs.types.SimpleTypeWrapperFactory;
import be.nabu.libs.types.api.DefinedSimpleType;
import be.nabu.libs.types.api.SimpleType;
import be.nabu.libs.types.api.SimpleTypeWrapper;

@SuppressWarnings("rawtypes")
public class StringToSimpleType implements ConverterProvider<String, SimpleType> {

	private SimpleTypeWrapper wrapper = SimpleTypeWrapperFactory.getInstance().getWrapper();
	
	@Override
	public SimpleType convert(String instance) {
		if (instance == null || instance.trim().isEmpty()) {
			return null;
		}
		DefinedSimpleType<?> simpleType = wrapper.getByName(instance);
		if (simpleType == null) {
			try {
				simpleType = wrapper.wrap(Class.forName(instance));
			}
			catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		return simpleType;
	}

	@Override
	public Class<String> getSourceClass() {
		return String.class;
	}

	@Override
	public Class<SimpleType> getTargetClass() {
		return SimpleType.class;
	}
}
