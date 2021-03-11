package be.nabu.libs.types.properties;

public class LengthProperty extends SimpleProperty<Integer> {
	private static LengthProperty instance;
	
	public static LengthProperty getInstance() {
		if (instance == null) {
			instance = new LengthProperty();
		}
		return instance;
	}
	
	public LengthProperty() {
		super(Integer.class);
	}

	@Override
	public String getName() {
		return "length";
	}

}
