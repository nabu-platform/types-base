package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;

public class NamespaceProperty extends SimpleProperty<String> implements ComparableProperty<String> {

	public NamespaceProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "namespace";
	}

	@Override
	public boolean isSubset(String arg0, String arg1) {
		return true;
	}

}
