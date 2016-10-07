package be.nabu.libs.types.properties;

import be.nabu.libs.property.api.ComparableProperty;
import be.nabu.libs.property.api.PropertyWithDefault;

public class UniqueProperty extends SimpleProperty<Boolean> implements PropertyWithDefault<Boolean>, ComparableProperty<Boolean> {

	private static UniqueProperty instance = new UniqueProperty();
	
	public static UniqueProperty getInstance() {
		return instance;
	}
	
	public UniqueProperty() {
		super(Boolean.class);
	}
	
	@Override
	public String getName() {
		return "unique";
	}

	@Override
	public boolean isSubset(Boolean subsetValue, Boolean broaderValue) {
		if (broaderValue == null)
			broaderValue = getDefault();
		if (subsetValue == null)
			subsetValue = getDefault();
		
		// if the broader is unique, it doesn't matter what the subset is
		// however if the broadervalue is false, the subsetvalue must be false as well
		return broaderValue || !subsetValue;
	}

	@Override
	public Boolean getDefault() {
		return true;
	}

}