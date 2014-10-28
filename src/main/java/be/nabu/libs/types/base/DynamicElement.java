package be.nabu.libs.types.base;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.Element;
import be.nabu.libs.types.api.Type;

public class DynamicElement<T> extends ElementImpl<T> {

	private Element<T> parent;

	public DynamicElement(Element<T> parent, Type type, String name, Value<?>...values) {
		super(name, type, parent.getParent(), values);
		this.parent = parent;
	}
	
	@Override
	public Class<T> getValueClass() {
		return parent.getValueClass();
	}

}
