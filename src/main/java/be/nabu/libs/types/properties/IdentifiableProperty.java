package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.PropertyWithDefault;

// whether or not the data in this field is identifiable
// this can be interesting to automatically pull identifiable data for reporting or anonimize it
public class IdentifiableProperty extends SimpleProperty<Boolean> implements PropertyWithDefault<Boolean> {

	private static IdentifiableProperty instance = new IdentifiableProperty();
	
	public static IdentifiableProperty getInstance() {
		return instance;
	}
	
	public IdentifiableProperty() {
		super(Boolean.class);
	}

	@Override
	public String getName() {
		return "identifiable";
	}

	@Override
	public Boolean getDefault() {
		return false;
	}

}
