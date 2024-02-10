package be.nabu.libs.types.properties;

public class EnricherProperty extends SimpleProperty<String> {
	
	private static EnricherProperty instance = new EnricherProperty();
	
	public EnricherProperty() {
		super(String.class);
	}
	
	public static EnricherProperty getInstance() {
		return instance;
	}
	
	@Override
	public String getName() {
		return "enricher";
	}

}
