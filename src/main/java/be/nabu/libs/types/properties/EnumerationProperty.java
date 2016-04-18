package be.nabu.libs.types.properties;

import java.util.List;

public class EnumerationProperty<T> extends SimpleProperty<List<T>> {

	@SuppressWarnings("rawtypes")
	static Class list() {
		return List.class;
	}
	
	@SuppressWarnings("unchecked")
	public EnumerationProperty() {
		super((Class<List<T>>) list());
	}

	@Override
	public String getName() {
		return "enumeration";
	}

}
