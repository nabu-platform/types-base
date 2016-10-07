package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;

/**
 * A type can have a name (e.g. "user") and a collection name (e.g. "users")
 */
public class ForeignKeyProperty extends SimpleProperty<String> implements ComparableProperty<String> {

	private static ForeignKeyProperty instance = new ForeignKeyProperty();
	
	public static ForeignKeyProperty getInstance() {
		return instance;
	}
	
	public ForeignKeyProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "foreignKey";
	}

	@Override
	public boolean isSubset(String arg0, String arg1) {
		return true;
	}

}
