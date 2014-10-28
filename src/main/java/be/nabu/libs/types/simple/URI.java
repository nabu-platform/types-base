package be.nabu.libs.types.simple;

import java.net.URISyntaxException;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.MarshalException;
import be.nabu.libs.types.api.Unmarshallable;
import be.nabu.libs.types.base.BaseMarshallableSimpleType;

public class URI extends BaseMarshallableSimpleType<java.net.URI> implements Unmarshallable<java.net.URI> {

	public URI() {
		super(java.net.URI.class);
	}

	@Override
	public java.lang.String getName(Value<?>...values) {
		return "anyURI";
	}

	@Override
	public java.lang.String marshal(java.net.URI object, Value<?>...values) {
		return object == null ? null : object.toString();
	}

	@Override
	public java.net.URI unmarshal(java.lang.String content, Value<?>...values) {
		try {
			return content == null ? null : new java.net.URI(encodeURI(content));
		}
		catch (URISyntaxException e) {
			throw new MarshalException(e);
		}
	}
	
	public static java.lang.String encodeURI(java.lang.String uri) {
		uri = uri.replace(" ", "%20");
		uri = uri.replace("{", "%7B");
		uri = uri.replace("}", "%7D");
		uri = uri.replace("|", "%7C");
		uri = uri.replace("^", "%5E");
		uri = uri.replace("[", "%5B");
		uri = uri.replace("]", "%5D");
		return uri;
	}
	
	public static java.lang.String decode(java.lang.String uri) {
		uri = uri.replace("%20", " ");
		uri = uri.replace("%7B", "{");
		uri = uri.replace("%7D", "}");
		uri = uri.replace("%7C", "|");
		uri = uri.replace("%5E", "^");
		uri = uri.replace("%5B", "[");
		uri = uri.replace("%5D", "]");
		return uri;
	}

	@Override
	public java.lang.String getNamespace(Value<?>...values) {
		return XML_SCHEMA;
	}
}
