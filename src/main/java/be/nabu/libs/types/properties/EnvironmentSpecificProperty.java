package be.nabu.libs.types.properties;

public class EnvironmentSpecificProperty extends SimpleProperty<Boolean> {

	private static EnvironmentSpecificProperty instance = new EnvironmentSpecificProperty();
	
	public static EnvironmentSpecificProperty getInstance() {
		return instance;
	}
	
	public EnvironmentSpecificProperty() {
		super(Boolean.class);
	}

	@Override
	public String getName() {
		return "environmentSpecific";
	}

}
