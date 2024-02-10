package be.nabu.libs.types.converters;

import be.nabu.libs.converter.api.ConverterProvider;
import be.nabu.libs.types.base.Duration;

public class DurationToLong implements ConverterProvider<Duration, Long> {

	@Override
	public Long convert(Duration instance) {
		return instance == null ? null : instance.toMilliSeconds();
	}

	@Override
	public Class<Duration> getSourceClass() {
		return Duration.class;
	}

	@Override
	public Class<Long> getTargetClass() {
		return Long.class;
	}

}
