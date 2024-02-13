package be.nabu.libs.types.properties;

public class PersisterProperty extends SimpleProperty<String> {
	
	private static PersisterProperty instance = new PersisterProperty();
	
	public PersisterProperty() {
		super(String.class);
	}
	
	public static PersisterProperty getInstance() {
		return instance;
	}
	
	@Override
	public String getName() {
		return "persister";
	}

}
