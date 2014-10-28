package be.nabu.libs.types.properties;

public class PatternProperty extends SimpleProperty<String> {

	public PatternProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "pattern";
	}

}
