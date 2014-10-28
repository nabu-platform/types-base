package be.nabu.libs.types.properties;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import be.nabu.libs.property.api.Enumerated;
import be.nabu.libs.property.api.PropertyWithDefault;
import be.nabu.libs.validator.RangeValidator;
import be.nabu.libs.validator.api.Validator;

abstract public class OccursProperty extends BaseProperty<Integer> implements Enumerated<Integer>, PropertyWithDefault<Integer> {

	private Validator<Integer> validator = new RangeValidator<Integer>(0, true, null, null);
	private Set<Integer> enumerations = new HashSet<Integer>(Arrays.asList(new Integer [] { 0, 1 }));
	
	@Override
	public Validator<Integer> getValidator() {
		return validator;
	}

	@Override
	public Set<Integer> getEnumerations() {
		return enumerations;
	}

	@Override
	public Class<Integer> getValueClass() {
		return Integer.class;
	}
	
	@Override
	public Integer getDefault() {
		return 1;
	}
}
