package be.nabu.libs.types.properties;

public class LengthProperty extends SimpleProperty<Integer> {

	public LengthProperty() {
		super(Integer.class);
	}

	@Override
	public String getName() {
		return "length";
	}

}
