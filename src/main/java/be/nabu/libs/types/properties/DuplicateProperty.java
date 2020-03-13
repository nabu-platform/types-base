package be.nabu.libs.types.properties;

// this is (mostly) meant for databases where you use extensions and you want some fields duplicated cross table (e.g. id, created, modified...)
public class DuplicateProperty extends SimpleProperty<String> {

	private static DuplicateProperty instance = new DuplicateProperty();
	
	public static DuplicateProperty getInstance() {
		return instance;
	}
	
	public DuplicateProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "duplicate";
	}

}
