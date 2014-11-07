package be.nabu.libs.types.utils;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * There are two problems with xsd:dateTime & xsd:time
 * 		- there can be any number of digits after the seconds which represent precision, but java only supports (by default) up to milliseconds (.net goes to nanoseconds afaik)
 * 		- the timezone that is passed along has a ":" in it which can not be parsed, this should be removed
 * 
 * Format(ISO8601): (-)CCYY-MM-DD'T'HH:MM:SS[(.SSSSS)][(Z|(+|-)hh:mm)]
 * 		Where "Z" indicates "UTC", and any offset can be given with +/-xx:xx (without the Z)
 * 
 * if you state that a timezone has to be in the string, then it is entirely useless to pass along a timezone
 * due to this vague input conflict, this method is not exposed to the public, but is overloaded
 * 
 * Note that the ability to parse a time separately was added later, which is why it seems to be "tacked on later"
 * This may be fixed in future versions, but the public API should remain stable
 * 
 * @param dateTime The xsd datetime
 * @param timezone If no timezone is given (null), then it will either be in local time (if no timezone in xsd timestamp) or in the timezone set by the xsd timestamp. If a timezone is passed along, it will use that timezone ONLY if there is no timezone defined in the date
 * @param forceTimeZoneInString If you do not pass along a timezone, but you set this to true, the parser will throw an error if a timezone is not defined in the string
 * @return
 * @throws ParseException 
 */

public class TimeFormat extends DateFormat {
	
	public TimeFormat() {}
	
	public TimeFormat(Locale locale) {
		this.locale = locale;
	}
	
	private static final long serialVersionUID = -5379635615311146786L;
	
	private Locale locale = Locale.getDefault();
	
	/**
	 * Whether or not you want to force the existence of a timezone in the string. It also regulates the addition of the timezone when formatting
	 */
	private boolean forceTimeZoneInString = false;
	
	/**
	 * Whether or not you want to force the existence of milliseconds when parsing. It also regulates the addition of milliseconds when formatting
	 */
	private boolean forceMilliseconds = false; 
		
	/**
	 * Whether the parsing is done leniently, check SimpleDateFormat for more information
	 */
	private boolean lenient = false;
	
	private TimeZone timezone;
	
	/**
	 * Determines whether or not it is a date time
	 */
	protected boolean isDateTime = false;

	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition pos) {
		String dateString = formatDate(date);
		toAppendTo.append(dateString);
		return toAppendTo;
	}

	@Override
	public Date parseObject(String source) throws ParseException {
		return parse(source);
	}

	@Override
	public Date parseObject(String source, ParsePosition pos) {
		// substring only the important bits
		source = source.substring(pos.getIndex());
		try {
			return parse(source);
		}
		catch (ParseException e) {
			pos.setErrorIndex(pos.getIndex());
			return null;
		}
	}
	
	@Override
	public Date parse(String date) throws ParseException {
		// the base format we will use to parse, if it's part of a dateTime, add that to the format
		String format = isDateTime ? "yyyy-MM-dd'T'HH:mm:ss" : "HH:mm:ss";
		
		// the base regex, as per http://www.w3.org/TR/xmlschema11-2/#dateTime (more or less) 
		String regex = isDateTime ? "-?(?:[1-9][0-9]{3,}|0[0-9]{3})-(?:0[1-9]|1[0-2])-(?:0[1-9]|[12][0-9]|3[01])T" : "";
		// add the time bit
		regex += "(?:[01][0-9]|2[0-4]):(?:[0-5][0-9]):(?:[0-5][0-9])";
		
		// do an initial match
		if (!date.matches("^" + regex + ".*$"))
			throw new ParseException("The dateTime '" + date + "' did not conform to the date format outlined in http://www.w3.org/TR/xmlschema11-2/#dateTime", 0);
		
		// check for the leading "-" sign, neither simpledateformat nor calendar seem to support it in java
		if (isDateTime && date.substring(0, 1).equals("-"))
			throw new ParseException("The dateTime '" + date + "' had a negative year, this is currently not supported", 0);
		
		// check the millisecond precision and update our regex/format accordingly
		
		// 3 or more (max 3 is allowed by java)
		if (date.matches(regex + "\\.[0-9]{3}[0-9]*.*")) {
			// remove the overflow, it is simply ignored
			date = date.replaceAll("^(" + regex + "\\.[0-9]{3})(?:[0-9]*|)(.*)", "$1$2");
			// append the format & regex
			format += ".SSS";
			regex += "\\.[0-9]{3}";
		}
		// 2
		else if (date.matches(regex + "\\.[0-9]{2}.*")) {
			// update format & regex			
			format += ".SS";
			regex += "\\.[0-9]{2}";
		}
		// 1
		else if (date.matches(regex + "\\.[0-9]{1}.*")) {
			// update format & regex			
			format += ".S";
			regex += "\\.[0-9]{1}";
		}
		// the dot separator without milliseconds is not allowed
		else if (date.matches(regex + "\\..*"))
			throw new ParseException("A dot separator was found for milliseconds in '" + date + "', but no millisecond value was found", 0);
		// if no milliseconds were found, but you enforced it, an error is thrown
		else if (forceMilliseconds)
			throw new ParseException("No milliseconds were found in '" + date + "', but it was required. This option can be toggled", 0);
			
		// if none, the format & regex are correct

		// "Time zones that aren't specified are considered undetermined" ~ this usually means local time
		// replace trailing z/Z with +00:00 (z/Z indicates UTC time)
		date = date.replaceAll("^(" + regex + ")(?:z|Z)$", "$1+00:00");
		
		// check if there is a timezone attached to it (in format +/-xx:xx), if so, we have to update our parsing format & dateTime
		if (date.matches("^" + regex + "(\\+|-)[0-9]{2}:[0-9]{2}$")) {
			format += "Z";
			date = date.replaceAll("(" + regex + "(?:\\+|-)[0-9]{2})(?::|)([0-9]{2})$", "$1$2");
			regex += "(?:\\+|-)[0-9]{4}";
		}
		// if no timezone is defined, nor given along and the force is set, throw an exception
		else if (forceTimeZoneInString)
			throw new ParseException("No timezone was found in the string '" + date + "' and it was required", 0);

		// do a final check against the regex, see that there is no "extra" garbage
		if (!date.matches("^" + regex + "$"))
			throw new ParseException("The date '" + date + "' does not conform to '" + format + "'", 0);
		
		DateFormat formatter = new SimpleDateFormat(format, getLocale());
		
		// this sets the timezone for the formatter
		formatter.setTimeZone(getTimeZone());
		
		// no lenient formatting
		formatter.setLenient(lenient);

		// parse it
		return formatter.parse(date);
	}

	@Override
	public TimeZone getTimeZone() {
		return timezone == null ? TimeZone.getDefault() : timezone;
	}

	@Override
	public void setTimeZone(TimeZone timezone) {
		this.timezone = timezone;
	}

	protected String formatDate(Date date) {		
		String format = isDateTime ? "yyyy-MM-dd'T'HH:mm:ss" : "HH:mm:ss";
		
		if (forceMilliseconds)
			format += ".SSS";
		
		// add the timezone if requested
		if (forceTimeZoneInString)
			format += "Z";
		
		DateFormat formatter = new SimpleDateFormat(format);
		// set the timezone
		formatter.setTimeZone(getTimeZone());
		
		String formatted = formatter.format(date);
		
		// the timezone is expressed in "+-xxxx" and should become "+-xx:xx"
		if (forceMilliseconds)
			formatted = formatted.replaceAll("^(.*)(\\+|-)([0-9]{2})([0-9]{2})$", "$1$2$3:$4");
		
		return formatted;
	}

	public Locale getLocale() {
		return locale == null ? Locale.getDefault() : locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public boolean isForceTimeZoneInString() {
		return forceTimeZoneInString;
	}

	public void setForceTimeZoneInString(boolean forceTimeZoneInString) {
		this.forceTimeZoneInString = forceTimeZoneInString;
	}

	public boolean isForceMilliseconds() {
		return forceMilliseconds;
	}

	public void setForceMilliseconds(boolean forceMilliseconds) {
		this.forceMilliseconds = forceMilliseconds;
	}

	public boolean isLenient() {
		return lenient;
	}

	public void setLenient(boolean lenient) {
		this.lenient = lenient;
	}

	@Override
	public Date parse(String source, ParsePosition pos) {
		try {
			return parse(source);
		}
		catch (ParseException e) {
			pos.setErrorIndex(pos.getIndex());
			return null;
		}
	}
}
