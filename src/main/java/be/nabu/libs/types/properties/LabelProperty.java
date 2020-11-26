package be.nabu.libs.types.properties;

// can be used to add a pretty name
public class LabelProperty extends SimpleProperty<String> {

	private static LabelProperty instance = new LabelProperty();
	
	public static LabelProperty getInstance() {
		return instance;
	}
	
	public LabelProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "label";
	}

}
