package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.PropertyWithDefault;

// whether or not the data in this field is identifiable
// this can be interesting to automatically pull identifiable data for reporting or anonimize it
public class TranslatableProperty extends SimpleProperty<Boolean> implements PropertyWithDefault<Boolean> {

	private static TranslatableProperty instance = new TranslatableProperty();
	
	public static TranslatableProperty getInstance() {
		return instance;
	}
	
	public TranslatableProperty() {
		super(Boolean.class);
	}

	@Override
	public String getName() {
		return "translatable";
	}

	@Override
	public Boolean getDefault() {
		return false;
	}

}
