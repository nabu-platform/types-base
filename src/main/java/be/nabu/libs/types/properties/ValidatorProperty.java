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

import java.util.List;
import java.util.function.Function;

import be.nabu.libs.validator.api.Validation;
import be.nabu.libs.validator.api.Validator;

// @2023-11-24
// TODO: alternative for schematron: configure a validator on the element(s) which links to a service that must implement the Validator spec (minus getValueClass)
// uncertainty is everything with services/specs/... is much higher up the food chain
public class ValidatorProperty extends BaseProperty<Function<List<Validation<?>>, Object>> {

	private static ValidatorProperty instance = new ValidatorProperty();
	
	public static ValidatorProperty getInstance() {
		return instance;
	}
	
	@Override
	public String getName() {
		return "validator";
	}

	@Override
	public Validator<Function<List<Validation<?>>, Object>> getValidator() {
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Class<Function<List<Validation<?>>, Object>> getValueClass() {
		Class test = Function.class;
		return test;
	}

}
