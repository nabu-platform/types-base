package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;

/**
 * A type can have a name (e.g. "user") and a collection name (e.g. "users")
 */
public class CollectionCrudProviderProperty extends SimpleProperty<String> implements ComparableProperty<String> {

	private static CollectionCrudProviderProperty instance = new CollectionCrudProviderProperty();
	
	public static CollectionCrudProviderProperty getInstance() {
		return instance;
	}
	
	public CollectionCrudProviderProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "collectionCrudProvider";
	}

	@Override
	public boolean isSubset(String arg0, String arg1) {
		return true;
	}

}
