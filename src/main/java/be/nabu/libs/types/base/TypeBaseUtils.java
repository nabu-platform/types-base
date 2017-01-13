package be.nabu.libs.types.base;

import java.util.LinkedHashMap;
import java.util.Map;

import be.nabu.libs.converter.ConverterFactory;
import be.nabu.libs.converter.api.Converter;
import be.nabu.libs.types.CollectionHandlerFactory;
import be.nabu.libs.types.ComplexContentWrapperFactory;
import be.nabu.libs.types.TypeUtils;
import be.nabu.libs.types.api.CollectionHandlerProvider;
import be.nabu.libs.types.api.ComplexContent;
import be.nabu.libs.types.api.ComplexType;
import be.nabu.libs.types.api.Element;

public class TypeBaseUtils {
	
	public static Map<String, String> toStringMap(ComplexContent content) {
		if (content == null) {
			return null;
		}
		Map<String, String> current = new LinkedHashMap<String, String>();
		toStringMap(current, null, content);
		return current;
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

}
