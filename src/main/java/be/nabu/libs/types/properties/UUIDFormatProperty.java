package be.nabu.libs.types.properties;

import be.nabu.libs.types.base.UUIDFormat;

// can be used to add a pretty name
public class UUIDFormatProperty extends SimpleProperty<UUIDFormat> {

	private static UUIDFormatProperty instance = new UUIDFormatProperty();
	
	public static UUIDFormatProperty getInstance() {
		return instance;
	}
	
	public UUIDFormatProperty() {
		super(UUIDFormat.class);
	}

	@Override
	public String getName() {
		return "uuidFormat";
	}

}
