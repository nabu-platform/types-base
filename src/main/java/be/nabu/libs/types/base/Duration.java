package be.nabu.libs.types.base;

import java.text.ParseException;
import java.time.Period;

public class Duration implements Comparable<Duration> {
	private boolean negative;
	private int years, months, days, hours, minutes;
	private double seconds;

	public int getYears() {
		return years;
	}
	public void setYears(int years) {
		this.years = years;
	}
	public int getMonths() {
		return months;
	}
	public void setMonths(int months) {
		this.months = months;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	public double getSeconds() {
		return seconds;
	}
	public void setSeconds(double seconds) {
		this.seconds = seconds;
	}
	
	public boolean isNegative() {
		return negative;
	}
	public void setNegative(boolean negative) {
		this.negative = negative;
	}
	// P2Y6M5DT12H35M30S
	public static Duration parse(String value) throws ParseException {
		if (value == null) {
			return null;
		}
		Duration duration = new Duration();
		if (value.startsWith("-P")) {
			duration.setNegative(true);
			value = value.substring(2);
		}
		else if (!value.startsWith("P")) {
			throw new ParseException("Duration did not start with a P: " + value, 0);
		}
		else {
			value = value.substring(1);
		}
		
		String[] parts = value.split("T");
		
		// the first part is years/months/days
		int index = parts[0].indexOf('Y');
		if (index >= 0) {
			duration.setYears(Integer.parseInt(parts[0].substring(0, index)));
			parts[0] = parts[0].substring(index + 1);
		}
		index = parts[0].indexOf('M');
		if (index >= 0) {
			duration.setMonths(Integer.parseInt(parts[0].substring(0, index)));
			parts[0] = parts[0].substring(index + 1);
		}
		index = parts[0].indexOf('D');
		if (index >= 0) {
			duration.setDays(Integer.parseInt(parts[0].substring(0, index)));
			parts[0] = parts[0].substring(index + 1);
		}
		// the second part (if any) is hours/minutes/seconds
		// seconds can be decimal!!
		if (parts.length > 1) {
			index = parts[1].indexOf('H');
			if (index >= 0) {
				duration.setHours(Integer.parseInt(parts[1].substring(0, index)));
				parts[1] = parts[1].substring(index + 1);
			}
			index = parts[1].indexOf('M');
			if (index >= 0) {
				duration.setMinutes(Integer.parseInt(parts[1].substring(0, index)));
				parts[1] = parts[1].substring(index + 1);
			}
			index = parts[1].indexOf('S');
			if (index >= 0) {
				duration.setSeconds(Double.parseDouble(parts[1].substring(0, index)));
				parts[1] = parts[1].substring(index + 1);
			}
		}
		return duration;
	}
	
	@Override
	public String toString() {
		return marshal(true, true);
	}
	private String marshal(boolean includeDate, boolean includeTime) {
		StringBuilder builder = new StringBuilder();
		if (negative) {
			builder.append("-");
		}
		builder.append("P");
		if (includeDate && hasDate()) {
			if (years != 0) {
				builder.append(years).append("Y");
			}
			if (months != 0) {
				builder.append(months).append("M");
			}
			if (days != 0) {
				builder.append(days).append("D");
			}
		}
		if (includeTime && hasTime()) {
			builder.append("T");
			if (hours != 0) {
				builder.append(hours).append("H");
			}
			if (minutes != 0) {
				builder.append(minutes).append("M");
			}
			if (seconds != 0) {
				if (Math.round(seconds) == seconds) {
					builder.append(Math.round(seconds)).append("S");
				}
				else {
					builder.append(seconds).append("S");
				}
			}
		}
		return builder.toString();
	}

	public boolean hasDate() {
		return years != 0 || months != 0 || days != 0;
	}
	public boolean hasTime() {
		return hours != 0 || minutes != 0 || seconds != 0;
	}
	public Period toJavaPeriod() {
		return hasDate() ? Period.parse(marshal(true, false)) : null;
	}
	public java.time.Duration toJavaDuration() {
		return hasTime() ? java.time.Duration.parse(marshal(false, true)) : null;
	}
	// not public because it is not accurate (due to months)
	// but it can be used to compare durations
	private long toSeconds() {
		long result = 0;
		if (seconds != 0) {
			result += seconds;
		}
		if (minutes != 0) {
			result += minutes * 60;
		}
		if (hours != 0) {
			result += hours * 60 * 60;
		}
		if (days != 0) {
			result += days * 24 * 60 * 60;
		}
		if (months != 0) {
			result += months * 30 * 24 * 60 * 60;
		}
		if (years != 0) {
			result += years * 365 * 24 * 60 * 60;
		}
		if (negative) {
			result *= -1;
		}
		return result;
	}
	@Override
	public int compareTo(Duration o) {
		return (int) (toSeconds() - o.toSeconds());
	}
	@Override
	public boolean equals(Object o) {
		return o instanceof Duration && ((Duration) o).toString().equals(toString());
	}
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
