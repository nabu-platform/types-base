package be.nabu.libs.types.properties;

public class EncodingProperty extends SimpleProperty<String> {

	public EncodingProperty() {
		super(String.class);
	}

	@Override
	public String getName() {
		return "encoding";
	}

}
