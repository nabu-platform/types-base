package be.nabu.libs.types.base;

import be.nabu.libs.types.api.DefinedSimpleType;

/**
 * Simple types are the same as long as they are of the same class (functional singletons)
 */
abstract public class BaseSimpleType<T> extends BaseType<T> implements DefinedSimpleType<T> {

	private Class<T> type;
	
	public BaseSimpleType(Class<T> type) {
		this.type = type;
	}

	@Override
	public Class<T> getInstanceClass() {
		return type;
	}
	
	@Override
	public boolean equals(Object object) {
		return object != null && getClass().equals(object.getClass());
	}
	
	@Override
	public int hashCode() {
		return getInstanceClass().hashCode();
	}
	
	@Override
	public String getId() {
		return getInstanceClass().getName();
	}
}
