package be.nabu.libs.types.base;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import be.nabu.libs.types.api.CollectionHandler;
import be.nabu.libs.types.api.CollectionHandlerProvider;

public class CollectionHandlerImpl implements CollectionHandler {

	private List<CollectionHandlerProvider<?, ?>> handlers = new ArrayList<CollectionHandlerProvider<?, ?>>();
	
	public void addCollectionHandler(CollectionHandlerProvider<?, ?> handler) {
		handlers.add(handler);
	}
	public void removeCollectionHandler(CollectionHandlerProvider<?, ?> handler) {
		handlers.remove(handler);
	}
	
	@SuppressWarnings("rawtypes")
	protected List<CollectionHandlerProvider<?, ?>> getHandlers() {
		if (handlers.isEmpty()) {
			ServiceLoader<CollectionHandlerProvider> serviceLoader = ServiceLoader.load(CollectionHandlerProvider.class);
			for (CollectionHandlerProvider provider : serviceLoader)
				handlers.add(provider);
		}
		return handlers;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T, V> CollectionHandlerProvider<T, V> getHandler(Class<T> clazz) {
		for (CollectionHandlerProvider handler : getHandlers()) {
			if (handler.getCollectionClass().isAssignableFrom(clazz))
				return handler;
		}
		return null;
	}

}
