package be.nabu.libs.types.properties;

import be.nabu.libs.types.base.CollectionFormat;

public class CollectionFormatProperty extends SimpleProperty<CollectionFormat> {

	private static CollectionFormatProperty instance = new CollectionFormatProperty();
	
	public static CollectionFormatProperty getInstance() {
		return instance;
	}
	
	public CollectionFormatProperty() {
		super(CollectionFormat.class);
	}

	@Override
	public String getName() {
		return "collectionFormat";
	}
}
