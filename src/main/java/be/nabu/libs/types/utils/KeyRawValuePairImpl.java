package be.nabu.libs.types.utils;

import be.nabu.libs.types.api.KeyRawValuePair;

public class KeyRawValuePairImpl implements KeyRawValuePair {

	private String key;
	private Object value;
	
	public KeyRawValuePairImpl() {
		// auto construct
	}

	public KeyRawValuePairImpl(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
}
