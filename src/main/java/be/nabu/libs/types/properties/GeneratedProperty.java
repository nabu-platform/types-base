package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.PropertyWithDefault;

public class GeneratedProperty extends SimpleProperty<Boolean> implements PropertyWithDefault<Boolean> {

	private static GeneratedProperty instance = new GeneratedProperty();
	
	public static GeneratedProperty getInstance() {
		return instance;
	}
	
	public GeneratedProperty() {
		super(Boolean.class);
	}
	
	@Override
	public String getName() {
		return "generated";
	}

	@Override
	public Boolean getDefault() {
		return false;
	}

}