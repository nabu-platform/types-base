package be.nabu.libs.types.base;

import java.util.Set;

import be.nabu.libs.property.api.Property;
import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.ComplexContent;
import be.nabu.libs.types.api.ComplexType;
import be.nabu.libs.types.api.Element;
import be.nabu.libs.types.api.ModifiableElement;
import be.nabu.libs.types.api.SimpleType;
import be.nabu.libs.types.api.Type;
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

	@Override
	public void setType(Type type) {
		// we can't simply set a simple type on a complex element
		if (type instanceof SimpleType && getType() instanceof SimpleType) {
			Element<?> element = getType().get(ComplexType.SIMPLE_TYPE_VALUE);
			if (element instanceof ModifiableElement) {
				((ModifiableElement<?>) element).setType(type);
			}
		}
		else {
			super.setType(type);
		}
	}

}