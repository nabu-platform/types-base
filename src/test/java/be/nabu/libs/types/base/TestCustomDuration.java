package be.nabu.libs.types.base;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class TestCustomDuration extends TestCase {
	public void testDuration() throws ParseException {
		List<String> durations = Arrays.asList("P2Y6M5DT12H35M30S", "P1DT2H", "P20M", "PT20M", "P20M", "-P60D", "PT1M30.5S");
		for (String duration : durations) {
			Duration parsed = Duration.parse(duration);
			assertEquals(duration, parsed.toString());
		}
	}
}
