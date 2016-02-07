package be.nabu.libs.types.properties;


public class HiddenProperty extends SimpleProperty<Boolean> {

	private static HiddenProperty instance = new HiddenProperty();
	
	public static HiddenProperty getInstance() {
		return instance;
	}

	public HiddenProperty() {
		super(Boolean.class);
	}

	@Override
	public String getName() {
		return "hidden";
	}

}
