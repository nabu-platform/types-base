package be.nabu.libs.types.properties;

import be.nabu.libs.types.api.CollectionHandlerProvider;

/**
 * TODO: not marshallable yet, so it can only be set from xml schema or the likes atm
 */
@SuppressWarnings("rawtypes")
public class CollectionHandlerProviderProperty extends SimpleProperty<CollectionHandlerProvider> {

	public CollectionHandlerProviderProperty() {
		super(CollectionHandlerProvider.class);
	}

	@Override
	public String getName() {
		return "collectionHandler";
	}
}
