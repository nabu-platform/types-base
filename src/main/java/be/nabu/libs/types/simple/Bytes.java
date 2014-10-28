package be.nabu.libs.types.simple;

import java.io.IOException;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.Unmarshallable;
import be.nabu.libs.types.base.BaseMarshallableSimpleType;
import be.nabu.utils.codec.TranscoderUtils;
import be.nabu.utils.codec.impl.Base64Decoder;
import be.nabu.utils.codec.impl.Base64Encoder;
import be.nabu.utils.io.IOUtils;

public class Bytes extends BaseMarshallableSimpleType<byte[]> implements Unmarshallable<byte[]> {

	public Bytes() {
		super(byte[].class);
	}

	@Override
	public java.lang.String marshal(byte[] object, Value<?>...values) {
		try {
			return object == null
				? null
				: new java.lang.String(IOUtils.toBytes(TranscoderUtils.transcodeBytes(IOUtils.wrap(object, true), new Base64Encoder())), "ASCII");
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte[] unmarshal(java.lang.String content, Value<?>...values) {
		try {
			return content == null
				? null
				: IOUtils.toBytes(TranscoderUtils.transcodeBytes(IOUtils.wrap(content.getBytes("ASCII"), true), new Base64Decoder()));
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public java.lang.String getName(Value<?>...values) {
		return "base64Binary";
	}

	@Override
	public java.lang.String getNamespace(Value<?>...values) {
		return XML_SCHEMA;
	}

}
