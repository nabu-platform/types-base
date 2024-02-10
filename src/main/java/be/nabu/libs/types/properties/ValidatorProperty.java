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
