package be.nabu.libs.types.properties;

public class IndexedProperty extends SimpleProperty<Boolean> {

	private static IndexedProperty instance = new IndexedProperty();
	
	public static IndexedProperty getInstance() {
		return instance;
	}
	
	public IndexedProperty() {
		super(Boolean.class);
	}

	@Override
	public String getName() {
		return "indexed";
	}

}
