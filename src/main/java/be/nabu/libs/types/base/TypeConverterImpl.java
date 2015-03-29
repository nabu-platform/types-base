package be.nabu.libs.types.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import be.nabu.libs.converter.ConverterFactory;
import be.nabu.libs.converter.api.Converter;
import be.nabu.libs.types.api.SimpleType;
import be.nabu.libs.types.api.TypeConverter;
import be.nabu.libs.types.api.TypeConverterProvider;
import be.nabu.libs.types.api.TypeInstance;

/**
 * If no specific simple type converter is found, it falls back to the classic converter
 * 
 * @author alex
 *
 */
public class TypeConverterImpl implements TypeConverter {

	private Converter converter;
	private List<TypeConverterProvider> providers = new ArrayList<TypeConverterProvider>();
	
	public void setConverter(Converter converter) {
		this.converter = converter;
	}
	public void unsetConverter(Converter converter) {
		this.converter = null;
	}
	
	public Converter getConverter() {
		if (converter == null)
			converter = ConverterFactory.getInstance().getConverter();
		return converter;
	}
	
	public void addTypeConverter(TypeConverterProvider provider) {
		providers.add(provider);
	}
	
	public void removeTypeConverter(TypeConverterProvider provider) {
		providers.remove(provider);
	}
	
	@SuppressWarnings("unchecked")
	protected List<TypeConverterProvider> getProviders() {
		if (providers.isEmpty()) {
			try {
				// let's try this with custom service loading based on a configuration
				Class<?> clazz = getClass().getClassLoader().loadClass("be.nabu.utils.services.ServiceLoader");
				Method declaredMethod = clazz.getDeclaredMethod("load", Class.class);
				providers.addAll((List<TypeConverterProvider>) declaredMethod.invoke(null, TypeConverterProvider.class));
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
			if (providers.isEmpty()) {
				ServiceLoader<TypeConverterProvider> serviceLoader = ServiceLoader.load(TypeConverterProvider.class);
				for (TypeConverterProvider provider : serviceLoader) {
					providers.add(provider);
				}
			}
		}
		return providers;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> T convert(Object instance, TypeInstance from, TypeInstance to) {
		if (from.getType().equals(to.getType())) {
			return (T) instance;
		}
		TypeConverterProvider bestProvider = getProvider(from, to);
		if (bestProvider != null)
			return (T) bestProvider.convert(instance, from.getProperties(), to.getProperties());
		// no type provider found, check if you are doing a simple conversion, in that case we can try default conversion
		else if (from.getType() instanceof SimpleType && to.getType() instanceof SimpleType)
			return (T) getConverter().convert(instance, ((SimpleType) to.getType()).getInstanceClass());
		else
			return null;
	}
	
	@Override
	public boolean canConvert(TypeInstance from, TypeInstance to) {
		boolean canConvert = from.getType().equals(to.getType()) ? true : getProvider(from, to) != null;
		// if we can't do advanced type conversion, perhaps we can do basic conversion
		if (!canConvert && from.getType() instanceof SimpleType && to.getType() instanceof SimpleType)
			return getConverter().canConvert(((SimpleType<?>) from.getType()).getInstanceClass(), ((SimpleType<?>) to.getType()).getInstanceClass());
		return canConvert;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private TypeConverterProvider getProvider(TypeInstance from, TypeInstance to) {
		TypeConverterProvider bestProvider = null;
		for (TypeConverterProvider provider : getProviders()) {
			// if there is a provider that performs the exact conversion you are looking for, use that
			if (provider.getSourceType().equals(from.getType()) && provider.getTargetType().equals(to.getType())) {
				bestProvider = provider;
				break;
			}
			// if you are converting from simpletype to simpletype, the provider may be broader, for example if you want to convert to "MyObject" and your provider generates an "MySpecificObject" extension, it would do
			// don't break though because a more specific one would be better
			else if (from.getType() instanceof SimpleType && provider.getSourceType() instanceof SimpleType 
				&& to.getType() instanceof SimpleType && provider.getTargetType() instanceof SimpleType
				&& ((SimpleType) provider.getSourceType()).getInstanceClass().isAssignableFrom(((SimpleType<?>) from.getType()).getInstanceClass()) 
				&& ((SimpleType) to.getType()).getInstanceClass().isAssignableFrom(((SimpleType) provider.getTargetType()).getInstanceClass()))
					bestProvider = provider;
		}
		return bestProvider;
	}
}
