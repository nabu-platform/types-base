package be.nabu.libs.types.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import be.nabu.libs.types.api.SimpleType;
import be.nabu.libs.validator.api.ValidationMessage;
import be.nabu.libs.validator.api.ValidationMessage.Severity;
import be.nabu.libs.validator.api.Validator;

public class ActualTypeProperty extends BaseProperty<SimpleType<?>> {

	private List<SimpleType<?>> availableSimpleTypes = new ArrayList<SimpleType<?>>();
	
	@Override
	public String getName() {
		return "actualType";
	}

	@SuppressWarnings("rawtypes")
	public List<SimpleType<?>> getAvailableSimpleTypes() {
		if (availableSimpleTypes.isEmpty()) {
			ServiceLoader<SimpleType> serviceLoader = ServiceLoader.load(SimpleType.class);
			for (SimpleType simpleType : serviceLoader)
				availableSimpleTypes.add(simpleType);
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

}
