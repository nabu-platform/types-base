package be.nabu.libs.types.properties;

// you can do a calculation on the field itself _or_ (in the future) other fields
public class CalculationProperty extends SimpleProperty<String> {

	private static CalculationProperty instance = new CalculationProperty();
	
	public static CalculationProperty getInstance() {
		return instance;
	}
	
	public CalculationProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "calculation";
	}

}
