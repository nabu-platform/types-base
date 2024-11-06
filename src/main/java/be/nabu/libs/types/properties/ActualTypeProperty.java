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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;

import be.nabu.libs.property.api.Enumerated;
import be.nabu.libs.types.api.DefinedSimpleType;
import be.nabu.libs.types.api.SimpleType;
import be.nabu.libs.validator.api.ValidationMessage;
import be.nabu.libs.validator.api.ValidationMessage.Severity;
import be.nabu.libs.validator.api.Validator;

public class ActualTypeProperty extends BaseProperty<SimpleType<?>> implements Enumerated<SimpleType<?>> {

	private static ActualTypeProperty instance = new ActualTypeProperty();
	
	public static ActualTypeProperty getInstance() {
		return instance;
	}
	
	private volatile static List<SimpleType<?>> availableSimpleTypes = new ArrayList<SimpleType<?>>();
	
	@Override
	public String getName() {
		return "actualType";
	}

	@SuppressWarnings("rawtypes")
	public List<SimpleType<?>> getAvailableSimpleTypes() {
		// this is currently limited to SPI because it is only used by the developer
		// we may need to update this if it becomes necessary
		if (availableSimpleTypes.isEmpty()) {
			synchronized(availableSimpleTypes) {
				if (availableSimpleTypes.isEmpty()) {
					ServiceLoader<DefinedSimpleType> serviceLoader = ServiceLoader.load(DefinedSimpleType.class);
					for (SimpleType simpleType : serviceLoader) {
						availableSimpleTypes.add(simpleType);
					}
					Collections.sort(availableSimpleTypes, new Comparator<SimpleType>() {
						@Override
						public int compare(SimpleType arg0, SimpleType arg1) {
							return arg0.getName().toLowerCase().compareTo(arg1.getName().toLowerCase());
						}
					});
				}
			}
		}
		return availableSimpleTypes;
	}
	
	private class ValidateActualType implements Validator<SimpleType<?>> {
		@Override
		public List<ValidationMessage> validate(SimpleType<?> instance) {
			List<ValidationMessage> messages = new ArrayList<ValidationMessage>();
			if (instance == null)
				messages.add(new ValidationMessage(Severity.WARNING, "The actual type is null"));
			else if (!getAvailableSimpleTypes().contains(instance))
				messages.add(new ValidationMessage(Severity.ERROR, "The type '" + instance + "' can not be embedded in a string"));
			return messages;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Class<SimpleType<?>> getValueClass() {
			return ActualTypeProperty.this.getValueClass();
		}
	}

	@Override
	public Validator<SimpleType<?>> getValidator() {
		return new ValidateActualType();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getValueClass() {
		return SimpleType.class;
	}
	
	public void addSimpleType(SimpleType<?> simpleType) {
		availableSimpleTypes.add(simpleType);
	}
	public void removeSimpleType(SimpleType<?> simpleType) {
		availableSimpleTypes.remove(simpleType);
	}

	@Override
	public Set<SimpleType<?>> getEnumerations() {
		return new LinkedHashSet<SimpleType<?>>(getAvailableSimpleTypes());
	}
}
