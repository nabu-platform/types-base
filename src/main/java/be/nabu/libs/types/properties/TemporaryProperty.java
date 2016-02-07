package be.nabu.libs.types.properties;

public class TemporaryProperty extends SimpleProperty<Boolean> {

	private static TemporaryProperty instance = new TemporaryProperty();
	
	public static TemporaryProperty getInstance() {
		return instance;
	}
	
	public TemporaryProperty() {
		super(Boolean.class);
	}

	@Override
	public String getName() {
		return "temporary";
	}

}
