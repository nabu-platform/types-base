package be.nabu.libs.types.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import be.nabu.libs.converter.ConverterFactory;
import be.nabu.libs.converter.api.Converter;
import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.BaseTypeInstance;
import be.nabu.libs.types.CollectionHandlerFactory;
import be.nabu.libs.types.ComplexContentWrapperFactory;
import be.nabu.libs.types.TypeUtils;
import be.nabu.libs.types.api.CollectionHandlerProvider;
import be.nabu.libs.types.api.ComplexContent;
import be.nabu.libs.types.api.ComplexType;
import be.nabu.libs.types.api.Element;
import be.nabu.libs.types.api.KeyValuePair;
import be.nabu.libs.types.api.SimpleType;
import be.nabu.libs.types.properties.FormatProperty;
import be.nabu.libs.types.properties.PatternProperty;
import be.nabu.libs.types.utils.KeyValuePairImpl;

public class TypeBaseUtils {
	
	public static Map<String, String> toStringMap(ComplexContent content) {
		if (content == null) {
			return null;
		}
		Map<String, String> current = new LinkedHashMap<String, String>();
		toStringMap(current, null, content);
		return current;
	}
	
	public static List<KeyValuePair> toProperties(Map<String, String> map) {
		List<KeyValuePair> pairs = new ArrayList<KeyValuePair>();
		for (String key : map.keySet()) {
			pairs.add(new KeyValuePairImpl(key, map.get(key)));
		}
		return pairs;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void toStringMap(Map<String, String> map, String path, ComplexContent content) {
		Converter converter = ConverterFactory.getInstance().getConverter();
		for (Element<?> element : TypeUtils.getAllChildren(content.getType())) {
			String childPath = (path == null ? "" : path + "/") + element.getName();
			Object value = content.get(element.getName());
			if (value != null) {
				if (element.getType().isList(element.getProperties())) {
					CollectionHandlerProvider handler = CollectionHandlerFactory.getInstance().getHandler().getHandler(value.getClass());
					for (Object index : handler.getIndexes(value)) {
						String indexAsString = converter.convert(index, String.class);
						Object single = handler.get(value, index);
						putSingle(map, converter, element, single, childPath + "[" + indexAsString + "]");
					}
				}
				else {
					putSingle(map, converter, element, value, childPath);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static void putSingle(Map<String, String> map, Converter converter, Element<?> element, Object value, String childPath) {
		if (value == null) {
			map.put(childPath, null);
		}
		else {
			if (element.getType() instanceof ComplexType) {
				if (!(value instanceof ComplexContent)) {
					value = ComplexContentWrapperFactory.getInstance().getWrapper().wrap(value);
				}
				toStringMap(map, childPath, (ComplexContent) value);
			}
			else {
				String singleAsString = value == null ? null : converter.convert(value, String.class);
				map.put(childPath, singleAsString);
			}
		}
	}

	public static Map<String, String> getRegexes(Iterable<Element<?>> iterable) {
		Map<String, String> regexes = new HashMap<String, String>();
		if (iterable != null) {
			for (Element<?> element : iterable) {
				Value<String> patternProperty = element.getProperty(PatternProperty.getInstance());
				Value<String> formatProperty = element.getProperty(FormatProperty.getInstance());
				if (patternProperty != null && patternProperty.getValue() != null) {
					regexes.put(element.getName(), patternProperty.getValue());
				}
				else if (formatProperty != null && formatProperty.getValue() != null && FormatProperty.regexes.containsKey(formatProperty.getValue())) {
					regexes.put(element.getName(), FormatProperty.regexes.get(formatProperty.getValue()));
				}
				else if (element.getType() instanceof SimpleType) {
					Class<?> clazz = ((SimpleType<?>) element.getType()).getInstanceClass();
					if (Number.class.isAssignableFrom(clazz)) {
						regexes.put(element.getName(), "[0-9.]+");
					}
					else if (UUID.class.isAssignableFrom(clazz)) {
						regexes.put(element.getName(), "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}|[0-9a-fA-F]{32}");
					}
				}
			}
		}
		return regexes;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Element<?> clone(Element<?> element, ComplexType parent) {
		// make sure we take into account the maintain default values (necessary for wsdl shizzle)
		// once again: default values for properties were a bad idea...
		if (element.getType() instanceof ComplexType) {
			ComplexElementImpl cloned = new ComplexElementImpl(element.getName(), (ComplexType) element.getType(), parent);
			if (element instanceof BaseTypeInstance) {
				cloned.setMaintainDefaultValues(((BaseTypeInstance) element).isMaintainDefaultValues());
			}
			cloned.setProperty(element.getProperties());
			return cloned;
		}
		else {
			SimpleElementImpl cloned = new SimpleElementImpl(element.getName(), (SimpleType) element.getType(), parent);
			if (element instanceof BaseTypeInstance) {
				cloned.setMaintainDefaultValues(((BaseTypeInstance) element).isMaintainDefaultValues());
			}
			cloned.setProperty(element.getProperties());
			return cloned;
		}
	}
}
