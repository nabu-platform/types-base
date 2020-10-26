package be.nabu.libs.types.properties;

// if you have a foreign key, the foreign name will indicate the field (or fields in the future?) in the target that are a good representative name (e.g. for sorting) 
public class ForeignNameProperty extends SimpleProperty<String> {

	private static ForeignNameProperty instance = new ForeignNameProperty();
	
	public static ForeignNameProperty getInstance() {
		return instance;
	}
	
	public ForeignNameProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "foreignName";
	}

}
