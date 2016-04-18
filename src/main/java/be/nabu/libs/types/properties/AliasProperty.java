package be.nabu.libs.types.properties;

public class AliasProperty extends SimpleProperty<String> {

	private static AliasProperty instance = new AliasProperty();
	
	public static AliasProperty getInstance() {
		return instance;
	}
	
	public AliasProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "alias";
	}

}
