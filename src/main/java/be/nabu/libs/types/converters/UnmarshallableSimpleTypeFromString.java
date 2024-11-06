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

import be.nabu.libs.converter.api.PrioritizedConverter;
import be.nabu.libs.types.SimpleTypeWrapperFactory;
import be.nabu.libs.types.api.DefinedSimpleType;
import be.nabu.libs.types.api.SimpleTypeWrapper;
import be.nabu.libs.types.api.Unmarshallable;

public class UnmarshallableSimpleTypeFromString implements PrioritizedConverter {

	private SimpleTypeWrapper wrapper = SimpleTypeWrapperFactory.getInstance().getWrapper();
	
	@Override
	public boolean canConvert(Class<?> arg0, Class<?> arg1) {
		if (String.class.equals(arg0)) {
			DefinedSimpleType<?> wrap = wrapper.wrap(arg1);
			return wrap instanceof Unmarshallable;
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> T convert(Object arg0, Class<T> arg1) {
		if (arg0 instanceof String) {
			DefinedSimpleType<?> wrap = wrapper.wrap(arg1);
			if (wrap instanceof Unmarshallable) {
				Object unmarshal = ((Unmarshallable) wrap).unmarshal((String) arg0);
				if (unmarshal != null && arg1.isAssignableFrom(unmarshal.getClass())) {
					return (T) unmarshal;
				}
			}
		}
		return null;
	}

	@Override
	public int getPriority() {
		return -1;
	}
}
