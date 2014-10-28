package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;

public class CommentProperty extends SimpleProperty<String> implements ComparableProperty<String> {

	public CommentProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "comment";
	}

	@Override
	public boolean isSubset(String arg0, String arg1) {
		return true;
	}

}
