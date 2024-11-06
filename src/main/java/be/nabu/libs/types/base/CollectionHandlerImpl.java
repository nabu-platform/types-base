/*
* Copyright (C) 2014 Alexander Verbruggen
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program. If not, see <https://www.gnu.org/licenses/>.
*/

package be.nabu.libs.types.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import be.nabu.libs.types.api.CollectionHandler;
import be.nabu.libs.types.api.CollectionHandlerProvider;

public class CollectionHandlerImpl implements CollectionHandler {

	private List<CollectionHandlerProvider<?, ?>> handlers = new ArrayList<CollectionHandlerProvider<?, ?>>();
	
	private Map<Class<?>, CollectionHandlerProvider<?, ?>> providers = new HashMap<Class<?>, CollectionHandlerProvider<?, ?>>();
	
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
		if (!providers.containsKey(clazz)) {
			synchronized(providers) {
				if (!providers.containsKey(clazz)) {
					CollectionHandlerProvider current = null;
					for (CollectionHandlerProvider handler : getHandlers()) {
						if (handler.getCollectionClass().isAssignableFrom(clazz)) {
							if (current == null || current.getCollectionClass().isAssignableFrom(handler.getCollectionClass())) {
								current = handler;
							}
						}
					}
					providers.put(clazz, current);
				}
			}
		}
		return (CollectionHandlerProvider<T, V>) providers.get(clazz);
	}

}
