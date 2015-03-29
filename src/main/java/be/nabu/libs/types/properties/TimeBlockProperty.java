package be.nabu.libs.types.properties;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import be.nabu.libs.property.api.Enumerated;
import be.nabu.libs.validator.api.Validator;

public class TimeBlockProperty extends BaseProperty<TimeBlock> implements Enumerated<TimeBlock> {
	
	private static TimeBlockProperty instance = new TimeBlockProperty();
	
	public static TimeBlockProperty getInstance() {
		return instance;
	}
	
	private Set<TimeBlock> enumerations = new HashSet<TimeBlock>(Arrays.asList(TimeBlock.values()));
	
	@Override
	public Set<TimeBlock> getEnumerations() {
		return enumerations;
	}

	@Override
	public String getName() {
		return "period";
	}

	@Override
	public Validator<TimeBlock> getValidator() {
		return null;
	}

	@Override
	public Class<TimeBlock> getValueClass() {
		return TimeBlock.class;
	}
}
