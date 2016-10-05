package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;

/**
 * A type can have a name (e.g. "user") and a collection name (e.g. "users")
 */
public class CollectionNameProperty extends SimpleProperty<String> implements ComparableProperty<String> {

	private static CollectionNameProperty instance = new CollectionNameProperty();
	
	public static CollectionNameProperty getInstance() {
		return instance;
	}
	
	public CollectionNameProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "collectionName";
	}

	@Override
	public boolean isSubset(String arg0, String arg1) {
		return true;
	}

}
