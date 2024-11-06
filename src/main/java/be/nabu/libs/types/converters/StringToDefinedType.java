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
import be.nabu.libs.types.DefinedTypeResolverFactory;
import be.nabu.libs.types.api.DefinedType;
import be.nabu.libs.types.api.DefinedTypeResolver;

public class StringToDefinedType implements ConverterProvider<String, DefinedType> {

	private DefinedTypeResolver resolver;

	public StringToDefinedType() {
		this(DefinedTypeResolverFactory.getInstance().getResolver());
	}
	
	public StringToDefinedType(DefinedTypeResolver resolver) {
		this.resolver = resolver;
	}
	
	@Override
	public DefinedType convert(String instance) {
		return instance == null ? null : resolver.resolve(instance);
	}

	@Override
	public Class<String> getSourceClass() {
		return String.class;
	}

	@Override
	public Class<DefinedType> getTargetClass() {
		return DefinedType.class;
	}
}
