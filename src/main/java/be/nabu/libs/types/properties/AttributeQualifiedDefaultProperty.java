package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.PropertyWithDefault;

public class AttributeQualifiedDefaultProperty extends QualifiedProperty implements PropertyWithDefault<Boolean> {
	@Override
	public String getName() {
		return "attributeQualifiedDefault";
	}

	@Override
	public Boolean getDefault() {
		return false;
	}
}
