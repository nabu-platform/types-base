package be.nabu.libs.types.properties;

public class TokenProperty extends SimpleProperty<Boolean> {

	private static TokenProperty instance = new TokenProperty();
	
	public static TokenProperty getInstance() {
		return instance;
	}
	
	public TokenProperty() {
		super(Boolean.class);
	}
	
	@Override
	public String getName() {
		return "token";
	}

}