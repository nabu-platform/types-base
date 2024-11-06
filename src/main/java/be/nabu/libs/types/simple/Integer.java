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

import java.math.BigInteger;

import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.base.BaseComparableSimpleType;

/**
 * This should only be used for very big numbers
 * The overhead of big number calculation is rather huge if unnecessary
 * 
 * @author alex
 *
 */
public class Integer extends BaseComparableSimpleType<BigInteger> {

	public Integer() {
		super(BigInteger.class);
	}

	@Override
	public java.lang.String getName(Value<?>...values) {
		return "integer";
	}

	@Override
	public java.lang.String marshal(BigInteger object, Value<?>...values) {
		return object == null ? null : object.toString();
	}

	@Override
	public BigInteger unmarshal(java.lang.String content, Value<?>...values) {
		return content == null || content.trim().isEmpty() ? null : new BigInteger(content);
	}
	
	@Override
	public java.lang.String getNamespace(Value<?>...values) {
		return XML_SCHEMA;
	}

}
