/*
* Copyright (C) 2014 Alexander Verbruggen
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program. If not, see <https://www.gnu.org/licenses/>.
*/

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

	@Override
	public boolean isList(Value<?>...values) {
		return false;
	}

}
