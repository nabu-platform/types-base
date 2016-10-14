package be.nabu.libs.types.properties;

public class PrimaryKeyProperty extends SimpleProperty<Boolean> {

	private static PrimaryKeyProperty instance = new PrimaryKeyProperty();
	
	public static PrimaryKeyProperty getInstance() {
		return instance;
	}
	
	public PrimaryKeyProperty() {
		super(Boolean.class);
	}

	@Override
	public String getName() {
		return "primaryKey";
	}

}
