package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.PropertyWithDefault;

public class QualifiedProperty extends SimpleProperty<Boolean> implements PropertyWithDefault<Boolean> {

	public QualifiedProperty() {
		super(Boolean.class);
	}

	@Override
	public String getName() {
		return "qualified";
	}

	@Override
	public Boolean getDefault() {
		return false;
	}

}
