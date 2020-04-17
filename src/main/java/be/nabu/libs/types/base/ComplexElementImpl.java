package be.nabu.libs.types.base;

import java.util.Set;

import be.nabu.libs.property.api.Property;
import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.ComplexContent;
import be.nabu.libs.types.api.ComplexType;
import be.nabu.libs.types.properties.AttributeQualifiedDefaultProperty;
import be.nabu.libs.types.properties.ElementQualifiedDefaultProperty;

public class ComplexElementImpl extends ElementImpl<ComplexContent> {

	public ComplexElementImpl(ComplexType type, ComplexType parent, Value<?>...values) {
		super(type, parent, values);
	}
	
	public ComplexElementImpl(String name, ComplexType type, ComplexType parent, Value<?>...values) {
		super(name, type, parent, values);
	}
	
	@Override
	public ComplexType getType() {
		return (ComplexType) super.getType();
	}

	@Override
	public Set<Property<?>> getSupportedProperties() {
		Set<Property<?>> properties = super.getSupportedProperties();
		properties.add(ElementQualifiedDefaultProperty.getInstance());
		properties.add(AttributeQualifiedDefaultProperty.getInstance());
		properties.removeAll(getBlockedProperties());
		return properties;
	}

	@Override
	public Class<ComplexContent> getValueClass() {
		return ComplexContent.class;
	}
	
}