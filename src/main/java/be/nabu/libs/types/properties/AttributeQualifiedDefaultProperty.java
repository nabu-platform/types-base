package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.PropertyWithDefault;

public class AttributeQualifiedDefaultProperty extends QualifiedProperty implements PropertyWithDefault<Boolean> {
	
	private static AttributeQualifiedDefaultProperty instance = new AttributeQualifiedDefaultProperty();
	
	public static AttributeQualifiedDefaultProperty getInstance() {
		return instance;
	}
	
	@Override
	public String getName() {
		return "attributeQualifiedDefault";
	}

	@Override
	public Boolean getDefault() {
		return false;
	}
}
