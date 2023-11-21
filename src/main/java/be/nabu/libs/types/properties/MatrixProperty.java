package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;

// In json you can have nested arrays like [[[1]]] which can not be properly handled by the type system
// instead we could do [t1:[t2:[]]]. if we then set "matrix" on both t1 and t2, we will skip the actual name and inject it as part of a matrix

public class MatrixProperty extends SimpleProperty<Boolean> implements ComparableProperty<Boolean> {

	private static MatrixProperty instance = new MatrixProperty();
	
	public static MatrixProperty getInstance() {
		return instance;
	}
	
	public MatrixProperty() {
		super(Boolean.class);
	}

	@Override
	public String getName() {
		return "matrix";
	}

	@Override
	public boolean isSubset(Boolean arg0, Boolean arg1) {
		return true;
	}

}
