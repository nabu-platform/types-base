package be.nabu.libs.types.utils;

import be.nabu.libs.types.api.KeyValuePair;

public class KeyValuePairImpl implements KeyValuePair {

	private String key, value;

	@Override
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
