package be.nabu.libs.types.utils;

public class DateUtils {
	/**
	 * The granularity that this format has, useful for database interaction
	 */
	public enum Granularity {
		DATE,
		TIME,
		TIMESTAMP
	}

	public static Granularity getGranularity(String format) {
		if (format.equalsIgnoreCase("time")) {
			return Granularity.TIME;
		}
		else if (format.equalsIgnoreCase("dateTime")) {
			return Granularity.TIMESTAMP;
		}
		else if (format.equalsIgnoreCase("date") || format.equalsIgnoreCase("gMonth") || format.equalsIgnoreCase("gDay") || format.equalsIgnoreCase("gMonthDay") || format.equalsIgnoreCase("gYear") || format.equalsIgnoreCase("gYearMonth")) {
			return Granularity.DATE;
		}
		else {
			// need to expand this to less used patterns
			boolean hasDate = format.contains("y") || format.contains("M") || format.contains("d");
			boolean hasTime = format.contains("H") || format.contains("m") || format.contains("s") || format.contains("S");
			if (hasDate && hasTime) {
				return Granularity.TIMESTAMP;
			}
			else if (hasDate) {
				return Granularity.DATE;
			}
			else if (hasTime) {
				return Granularity.TIME;
			}
			else {
				throw new IllegalArgumentException("Not a recognizable date format: " + format);
			}
		}
	}
	
}
