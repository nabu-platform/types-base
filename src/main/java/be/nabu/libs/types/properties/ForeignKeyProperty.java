package be.nabu.libs.types.properties;

public class ForeignKeyProperty extends SimpleProperty<String> {

	private static ForeignKeyProperty instance = new ForeignKeyProperty();
	
	public static ForeignKeyProperty getInstance() {
		return instance;
	}
	
	public ForeignKeyProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "foreignKey";
	}

}
