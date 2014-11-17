package be.nabu.libs.types.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List<CollectionHandlerProvider<?, ?>> getHandlers() {
		if (handlers.isEmpty()) {
			try {
				// let's try this with custom service loading based on a configuration
				Class<?> clazz = getClass().getClassLoader().loadClass("be.nabu.utils.services.ServiceLoader");
				Method declaredMethod = clazz.getDeclaredMethod("load", Class.class);
				handlers.addAll((List<CollectionHandlerProvider<?, ?>>) declaredMethod.invoke(null, CollectionHandlerProvider.class));
			}
			catch (ClassNotFoundException e) {
				// ignore, the framework is not present
			}
			catch (NoSuchMethodException e) {
				// corrupt framework?
				throw new RuntimeException(e);
			}
			catch (SecurityException e) {
				throw new RuntimeException(e);
			}
			catch (IllegalAccessException e) {
				// ignore
			}
			catch (InvocationTargetException e) {
				// ignore
			}
			if (handlers.isEmpty()) {
				ServiceLoader<CollectionHandlerProvider> serviceLoader = ServiceLoader.load(CollectionHandlerProvider.class);
				for (CollectionHandlerProvider provider : serviceLoader) {
					handlers.add(provider);
				}
			}
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
