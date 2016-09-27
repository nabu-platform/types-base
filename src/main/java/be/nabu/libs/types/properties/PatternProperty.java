package be.nabu.libs.types.properties;

public class PatternProperty extends SimpleProperty<String> {

	private static PatternProperty instance = new PatternProperty();
	
	public static PatternProperty getInstance() {
		return instance;
	}
	
	public PatternProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "pattern";
	}

}
