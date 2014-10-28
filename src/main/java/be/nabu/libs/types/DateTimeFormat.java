package be.nabu.libs.types;

import java.util.Locale;

public class DateTimeFormat extends TimeFormat {

	private static final long serialVersionUID = 9169668326351985667L;

	public DateTimeFormat() {
		isDateTime = true;
	}
	
	public DateTimeFormat(Locale locale) {
		super(locale);
		isDateTime = true;
	}
}
