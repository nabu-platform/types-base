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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import be.nabu.libs.property.ValueUtils;
import be.nabu.libs.property.api.Property;
import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.api.MarshalException;
import be.nabu.libs.types.api.Unmarshallable;
import be.nabu.libs.types.base.BaseComparableSimpleType;
import be.nabu.libs.types.properties.CountryProperty;
import be.nabu.libs.types.properties.FormatProperty;
import be.nabu.libs.types.properties.LanguageProperty;
import be.nabu.libs.types.properties.TimeBlockProperty;
import be.nabu.libs.types.properties.TimezoneProperty;
import be.nabu.libs.types.utils.DateTimeFormat;
import be.nabu.libs.types.utils.TimeFormat;
import be.nabu.libs.types.validators.TimeBlockValidator;
import be.nabu.libs.validator.MultipleValidator;
import be.nabu.libs.validator.api.Validator;

public class Date extends BaseComparableSimpleType<java.util.Date> implements Unmarshallable<java.util.Date> {
	
	public Date() {
		super(java.util.Date.class);
	}

	public static enum XSDFormat {
		DATE_TIME("dateTime", null),
		TIME("time", null),
		DATE("date", "yyyy-MM-dd"),
		DAY("gDay", "---dd"),
		MONTH("gMonth", "--MM"),
		MONTH_DAY("gMonthDay", "--MM-dd"),
		YEAR("gYear", "yyyy"),
		YEAR_MONTH("gYearMonth", "yyyy-MM"),
		;
		
		private java.lang.String name, format;
		
		private XSDFormat(java.lang.String name, java.lang.String format) {
			this.name = name;
			this.format = format;
		}
		public java.lang.String getFormat() {
			return format;
		}
		public java.lang.String getName() {
			return name;
		}
		public static XSDFormat getXSDFormat(java.lang.String name) {
			for (XSDFormat format : values()) {
				if (format.name.equals(name))
					return format;
			}
			return null;
		}
	}

	@Override
	public java.lang.String getName(Value<?>...values) {
		java.lang.String format = ValueUtils.getValue(FormatProperty.getInstance(), values);
		XSDFormat xsdFormat = XSDFormat.getXSDFormat(format);
		if (xsdFormat != null)
			return xsdFormat.getName();
		// the default format is dateTime
		else if (format == null)
			return "dateTime";
		// if it is not one of the fixed xsd formats, just make it a string
		// can optionally set a regex on it
		else
			return "string";
	}

	
	@Override
	public Set<Property<?>> getSupportedProperties(Value<?>... values) {
		Set<Property<?>> properties = super.getSupportedProperties(values);
		properties.add(new FormatProperty());
		properties.add(new TimezoneProperty());
		properties.add(new LanguageProperty());
		properties.add(new CountryProperty());
		properties.add(new TimeBlockProperty());
		return properties;
	}

	private DateFormat createFormatter(java.lang.String guessedFormat, Value<?>...values) {
		DateFormat dateFormatter = null;
		java.lang.String format = ValueUtils.getValue(new FormatProperty(), values);
		if (format == null) {
			if (guessedFormat != null) {
				format = guessedFormat;
			}
			else {
				format = "dateTime";
			}
		}
		java.lang.String country = ValueUtils.getValue(new CountryProperty(), values);
		java.lang.String language = ValueUtils.getValue(new LanguageProperty(), values);
		TimeZone timezone = ValueUtils.getValue(new TimezoneProperty(), values);
		XSDFormat xsdFormat = XSDFormat.getXSDFormat(format);
		
		if (XSDFormat.DATE_TIME.equals(xsdFormat)) {
			if (language != null)
				dateFormatter = new DateTimeFormat(country == null ? new Locale(language) : new Locale(language, country));
			else
				dateFormatter = new DateTimeFormat();
		}
		else if (XSDFormat.TIME.equals(xsdFormat)) {
			if (language != null)
				dateFormatter = new TimeFormat(country == null ? new Locale(language) : new Locale(language, country));
			else
				dateFormatter = new TimeFormat();
		}
		else {
			// still an xsd format but it can be handled by a default formatter
			if (xsdFormat != null)
				format = xsdFormat.getFormat();
			
			if (language != null)
				dateFormatter = new SimpleDateFormat(format, country == null ? new Locale(language) : new Locale(language, country));
			else
				dateFormatter = new SimpleDateFormat(format);
		}
		if (timezone != null)
			dateFormatter.setTimeZone(timezone);
		return dateFormatter;
	}

	@Override
	public java.lang.String marshal(java.util.Date object, Value<?>...values) {
		DateFormat dateFormatter = createFormatter(null, values);
		if (dateFormatter instanceof TimeFormat) {
			((TimeFormat) dateFormatter).setForceTimeZoneInString(true);
			((TimeFormat) dateFormatter).setForceMilliseconds(true);
		}
		// need me a special parser
		return dateFormatter.format(object);
	}

	private java.lang.String guessFormat(java.lang.String date) {
		java.lang.String format = null;
		if (date != null) {
			if (date.matches("[0-9]{4}")) {
				format = "yyyy";
			}
			else if (date.matches("[0-9]{4}-[0-9]{2}")) {
				format = "yyyy-MM";
			}
			else if (date.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
				format = "yyyy-MM-dd";
			}
		}
		return format;
	}
	
	@Override
	public java.util.Date unmarshal(java.lang.String content, Value<?>...values) {
		if (content == null || content.trim().isEmpty()) {
			return null;
		}
		DateFormat dateFormatter = createFormatter(guessFormat(content), values);
		try {
			if (dateFormatter instanceof TimeFormat) {
				((TimeFormat) dateFormatter).setForceTimeZoneInString(false);
				((TimeFormat) dateFormatter).setForceMilliseconds(false);
				((TimeFormat) dateFormatter).setLenient(false);
			}
			return dateFormatter.parse(content);
		}
		catch (ParseException e) {
			throw new MarshalException(e);
		}
	}

	@Override
	public java.lang.String getNamespace(Value<?>...values) {
		return XML_SCHEMA;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Validator<java.util.Date> createValidator(Value<?>...values) {
		return new MultipleValidator(
			super.createValidator(values),
			new TimeBlockValidator(createFormatter(null, values), ValueUtils.getValue(new TimeBlockProperty(), values))
		);
	}
	
}
