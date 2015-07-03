package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.PropertyWithDefault;

public class ElementQualifiedDefaultProperty extends QualifiedProperty implements PropertyWithDefault<Boolean> {
	
	private static ElementQualifiedDefaultProperty instance = new ElementQualifiedDefaultProperty();
	
	public static ElementQualifiedDefaultProperty getInstance() {
		return instance;
	}
	
	@Override
	public String getName() {
		return "elementQualifiedDefault";
	}

	@Override
	public Boolean getDefault() {
		return false;
	}
}
