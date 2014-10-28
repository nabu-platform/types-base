package be.nabu.libs.types.base;

import be.nabu.libs.types.api.ComplexType;
import be.nabu.libs.types.properties.MaxOccursProperty;
import be.nabu.libs.types.properties.MinOccursProperty;

public class RootElement extends ComplexElementImpl {
	
	public RootElement(ComplexType type) {
		super(type.getName(), type, null);
		getBlockedProperties().add(new MinOccursProperty());
		getBlockedProperties().add(new MaxOccursProperty());
	}
	
	public RootElement(ComplexType type, String name) {
		super(name, type, null);
	}

}
