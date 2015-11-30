package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.PropertyWithDefault;

public class ValidateProperty extends SimpleProperty<Boolean> implements PropertyWithDefault<Boolean> {

	private static ValidateProperty instance = new ValidateProperty();
	
	public static ValidateProperty getInstance() {
		return instance;
	}
	
	public ValidateProperty() {
		super(Boolean.class);
	}
	
	@Override
	public String getName() {
		return "validate";
	}

	@Override
	public Boolean getDefault() {
		return false;
	}
}
