package be.nabu.libs.types.properties;

public class AllowProperty extends SimpleProperty<String> {

	private static AllowProperty instance = new AllowProperty();
	
	public static AllowProperty getInstance() {
		return instance;
	}
	
	public AllowProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "allow";
	}

}
