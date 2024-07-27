package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.PropertyWithDefault;

// whether or not the data in this field is identifiable
// this can be interesting to automatically pull identifiable data for reporting or anonimize it
public class SecretProperty extends SimpleProperty<Boolean> implements PropertyWithDefault<Boolean> {

	private static SecretProperty instance = new SecretProperty();
	
	public static SecretProperty getInstance() {
		return instance;
	}
	
	public SecretProperty() {
		super(Boolean.class);
	}

	@Override
	public String getName() {
		return "secret";
	}

	@Override
	public Boolean getDefault() {
		return false;
	}

}
