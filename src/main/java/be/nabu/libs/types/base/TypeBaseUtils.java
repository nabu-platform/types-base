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

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;

import be.nabu.libs.converter.ConverterFactory;
import be.nabu.libs.converter.api.Converter;
import be.nabu.libs.property.ValueUtils;
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
import be.nabu.libs.types.api.ModifiableComplexType;
import be.nabu.libs.types.api.SimpleType;
import be.nabu.libs.types.api.Type;
import be.nabu.libs.types.properties.AllowProperty;
import be.nabu.libs.types.properties.EnricherProperty;
import be.nabu.libs.types.properties.EnumerationProperty;
import be.nabu.libs.types.properties.FormatProperty;
import be.nabu.libs.types.properties.LabelProperty;
import be.nabu.libs.types.properties.NameProperty;
import be.nabu.libs.types.properties.PatternProperty;
import be.nabu.libs.types.properties.RestrictProperty;
import be.nabu.libs.types.utils.KeyValuePairImpl;

public class TypeBaseUtils {
	
	public static String getPrettyName(Type type) {
		String label = ValueUtils.getValue(LabelProperty.getInstance(), type.getProperties());
		return label != null ? label : type.getName();
	}
	
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
	
	public static void merge(ModifiableComplexType into, ComplexType outof) {
		for (Element<?> child : TypeUtils.getAllChildren(outof)) {
			// if we have nothing, just clone it
			Element<?> existing = into.get(child.getName());
			if (existing == null) {
				into.add(clone(child, into));
			}
			// recursive merge
			else if (existing.getType() instanceof ModifiableComplexType && child.getType() instanceof ComplexType) {
				merge((ModifiableComplexType) existing.getType(), (ComplexType) child.getType());
			}
		}
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
	
	
	public static class StubProfile {
		private int maxAmountOfIterations = 20;

		public int getMaxAmountOfIterations() {
			return maxAmountOfIterations;
		}

		public void setMaxAmountOfIterations(int maxAmountOfIterations) {
			this.maxAmountOfIterations = maxAmountOfIterations;
		}
	}
	
	public static Object stub(Type type, StubProfile profile, Value<?>...values) {
		return generateValue(type, profile, values);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Object generateValue(Type type, StubProfile profile, Value<?>...values) {
		if (type.isList(values)) {
			List list = new ArrayList();
			for (int i = 0; i < new Random().nextInt(profile.getMaxAmountOfIterations()); i++) {
				// send without properties, ideally we should only strip maxOccurs....
				list.add(generateValue(type, profile));
			}
			return list;
		}
		else if (type instanceof ComplexType) {
			ComplexContent child = ((ComplexType) type).newInstance();
			for (Element<?> single : TypeUtils.getAllChildren((ComplexType) type)) {
				child.set(single.getName(), generateValue(single.getType(), profile, single.getProperties()));
			}
			return child;
		}
		else if (type instanceof SimpleType) {
			List enumerationValues = (List) ValueUtils.getValue(new EnumerationProperty(), values);
			// if it is enumerated, take a random enumeration value
			if (enumerationValues != null) {
				return enumerationValues.get(new Random().nextInt(enumerationValues.size()));
			}
			else {
				String name = ValueUtils.getValue(NameProperty.getInstance(), values);
				Class<?> instanceClass = ((SimpleType<?>) type).getInstanceClass();
				if (Number.class.isAssignableFrom(instanceClass) || BigInteger.class.isAssignableFrom(instanceClass) || BigDecimal.class.isAssignableFrom(instanceClass)) {
					return new Random().nextDouble();
				}
				else if (java.util.Date.class.isAssignableFrom(instanceClass)) {
					return new Date();
				}
				else if (java.lang.String.class.isAssignableFrom(instanceClass)) {
					return name + "-" + new Random().nextInt();
				}
				else if (java.lang.Boolean.class.isAssignableFrom(instanceClass)) {
					return new Random().nextDouble() >= 0.5;
				}
				else if (byte[].class.isAssignableFrom(instanceClass)) {
					return (name + "-" + new Random().nextInt()).getBytes(Charset.forName("UTF-8"));
				}
				else if (java.io.InputStream.class.isAssignableFrom(instanceClass)) {
					return new ByteArrayInputStream((name + "-" + new Random().nextInt()).getBytes(Charset.forName("UTF-8")));
				}
				else if (UUID.class.isAssignableFrom(instanceClass)) {
					return UUID.randomUUID();
				}
				else if (URI.class.isAssignableFrom(instanceClass)) {
					try {
						return new URI("http://example.com/" + name + "-" + new Random().nextInt());
					}
					catch (URISyntaxException e) {
						throw new RuntimeException(e);
					}
				}
				else if (TimeZone.class.isAssignableFrom(instanceClass)) {
					return TimeZone.getDefault();
				}
				else if (Charset.class.isAssignableFrom(instanceClass)) {
					return Charset.defaultCharset();
				}
			}
		}
		return null;
	}
	
	public static List<String> getRestricted(ComplexType type, Value<?>...properties) {
		if (properties == null || properties.length == 0) {
			properties = type.getProperties();
		}
		List<String> restricted = new ArrayList<String>();
		String restrictedValue = ValueUtils.getValue(RestrictProperty.getInstance(), properties);
		if (restrictedValue != null && !restrictedValue.trim().isEmpty()) {
			restricted.addAll(Arrays.asList(restrictedValue.split("[\\s]*,[\\s]*")));
		}
		String allowedValue = ValueUtils.getValue(AllowProperty.getInstance(), properties);
		if (allowedValue != null && !allowedValue.trim().isEmpty()) {
			// by setting an allow, you implicitly restrict everything that is not explicitly allowed
			List<String> allowed = Arrays.asList(allowedValue.split("[\\s]*,[\\s]*"));
			if (type.getSuperType() instanceof ComplexType) {
				for (Element<?> child : TypeUtils.getAllChildren((ComplexType) type.getSuperType())) {
					if (allowed.indexOf(child.getName()) < 0) {
						restricted.add(child.getName());
					}
				}
			}
		}
		return restricted;
	}
	
	public static boolean isEnriched(Element<?> child) {
		String enricher = ValueUtils.getValue(EnricherProperty.getInstance(), child.getProperties());
		if (enricher != null && !enricher.isEmpty()) {
			return true;
		}
		Type type = child.getType();
		while (type != null) {
			enricher = ValueUtils.getValue(EnricherProperty.getInstance(), type.getProperties());
			if (enricher != null && !enricher.isEmpty()) {
				return true;
			}
			type = type.getSuperType();
		}
		return false;
	}
}
