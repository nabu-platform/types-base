package be.nabu.libs.types.properties;

public class RestrictProperty extends SimpleProperty<String> {

	private static RestrictProperty instance = new RestrictProperty();
	
	public static RestrictProperty getInstance() {
		return instance;
	}
	
	public RestrictProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "restrict";
	}

}
