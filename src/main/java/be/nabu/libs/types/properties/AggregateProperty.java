package be.nabu.libs.types.properties;

// repurposed for aggregation functions (sum, count, avg,...)
public class AggregateProperty extends SimpleProperty<String> {

	private static AggregateProperty instance = new AggregateProperty();
	
	public static AggregateProperty getInstance() {
		return instance;
	}
	
	public AggregateProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "aggregation";
	}

}
