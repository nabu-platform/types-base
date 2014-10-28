package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.PropertyWithDefault;

public class AspectProperty extends SimpleProperty<Boolean> implements PropertyWithDefault<Boolean> {

	public AspectProperty() {
		super(Boolean.class);
	}

	@Override
	public String getName() {
		return "aspect";
	}

	@Override
	public Boolean getDefault() {
		return false;
	}

}
