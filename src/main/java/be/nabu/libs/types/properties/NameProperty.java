package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;

public class NameProperty extends SimpleProperty<String> implements ComparableProperty<String> {

	public static final String ANY = "##any";
	public static final String ANY_ATTRIBUTE = "##anyAttribute";
	
	public NameProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "name";
	}

	@Override
	public boolean isSubset(String arg0, String arg1) {
		return true;
	}

}
