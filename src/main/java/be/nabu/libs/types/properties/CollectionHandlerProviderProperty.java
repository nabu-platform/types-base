package be.nabu.libs.types.properties;

import be.nabu.libs.types.api.CollectionHandlerProvider;

/**
 * TODO: not marshallable yet, so it can only be set from xml schema or the likes atm
 */
@SuppressWarnings("rawtypes")
public class CollectionHandlerProviderProperty extends SimpleProperty<CollectionHandlerProvider> {

	private static CollectionHandlerProviderProperty instance = new CollectionHandlerProviderProperty();
	
	public static CollectionHandlerProviderProperty getInstance() {
		return instance;
	}
	
	public CollectionHandlerProviderProperty() {
		super(CollectionHandlerProvider.class);
	}

	@Override
	public String getName() {
		return "collectionHandler";
	}
}
