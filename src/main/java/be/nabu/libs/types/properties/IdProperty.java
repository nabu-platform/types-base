package be.nabu.libs.types.properties;

/**
 * Allows setting an id on a type, this could be interesting to set for example a globally unique way of identifying this particular type
 * It was originally introduced to provide backwards compatibility with UML where unique ids were assigned to types for referencing
 */
public class IdProperty extends SimpleProperty<String> {

	private static IdProperty instance = new IdProperty();
	
	public static IdProperty getInstance() {
		return instance;
	}
	
	public IdProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "id";
	}

}
