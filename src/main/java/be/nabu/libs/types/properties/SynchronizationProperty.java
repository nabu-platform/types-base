package be.nabu.libs.types.properties;

import be.nabu.libs.types.base.SynchronizationType;

public class SynchronizationProperty extends SimpleProperty<SynchronizationType> {

	private static SynchronizationProperty instance = new SynchronizationProperty();
	
	public static SynchronizationProperty getInstance() {
		return instance;
	}
	
	public SynchronizationProperty() {
		super(SynchronizationType.class);
	}

	@Override
	public String getName() {
		return "synchronization";
	}
}
