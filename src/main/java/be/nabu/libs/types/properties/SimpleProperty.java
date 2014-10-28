package be.nabu.libs.types.properties;

import be.nabu.libs.validator.api.Validator;

abstract public class SimpleProperty<T> extends BaseProperty<T> {

	private Class<T> type;
	
	public SimpleProperty(Class<T> type) {
		this.type = type;
	}
	
	@Override
	public Validator<T> getValidator() {
		return null;
	}

	@Override
	public Class<T> getValueClass() {
		return type;
	}

}
