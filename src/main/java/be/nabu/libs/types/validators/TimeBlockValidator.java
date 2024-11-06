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

package be.nabu.libs.types.validators;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import be.nabu.libs.types.properties.TimeBlock;
import be.nabu.libs.validator.api.ValidationMessage;
import be.nabu.libs.validator.api.Validator;
import be.nabu.libs.validator.api.ValidationMessage.Severity;

public class TimeBlockValidator implements Validator<Date> {

	private DateFormat formatter;
	private TimeBlock timeBlock;
	
	public TimeBlockValidator(DateFormat formatter, TimeBlock timeBlock) {
		this.formatter = formatter;
		this.timeBlock = timeBlock;
	}
	
	@Override
	public List<ValidationMessage> validate(Date instance) {
		List<ValidationMessage> messages = new ArrayList<ValidationMessage>();
		// don't validate
		if (timeBlock == null) {
			return messages;
		}
		if (instance == null)
			messages.add(new ValidationMessage(Severity.WARNING, "The date is null, its timeblock can not be validated"));
		else {
			try {
				Date currentDate = formatter.parse(formatter.format(new Date()));
				Date dateToCompare = formatter.parse(formatter.format(instance));
				if (timeBlock == TimeBlock.PAST && !dateToCompare.before(currentDate))
					messages.add(new ValidationMessage(Severity.ERROR, "The date is not in the past"));
				else if (timeBlock == TimeBlock.FUTURE && !dateToCompare.after(currentDate))
					messages.add(new ValidationMessage(Severity.ERROR, "The date is not in the future"));
				if (timeBlock == TimeBlock.PRESENT && dateToCompare.equals(currentDate))
					messages.add(new ValidationMessage(Severity.ERROR, "The date is not in the present"));
			}
			catch (ParseException e) {
				messages.add(new ValidationMessage(Severity.ERROR, "Could not parse the date: " + e.getMessage()));
			}
		}
		return messages;
	}

	@Override
	public Class<Date> getValueClass() {
		return Date.class;
	}

}
