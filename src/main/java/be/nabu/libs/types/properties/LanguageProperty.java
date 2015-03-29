package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;

public class LanguageProperty extends SimpleProperty<String> implements ComparableProperty<String> {

	private static LanguageProperty instance = new LanguageProperty();
	
	public static LanguageProperty getInstance() {
		return instance;
	}
	
	public LanguageProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "language";
	}

	@Override
	public boolean isSubset(String subsetValue, String broaderValue) {
		return true;
	}

}
