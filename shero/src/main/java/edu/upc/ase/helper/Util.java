package edu.upc.ase.helper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class Util {

	/**
	 * Given a start and an end date, this method identifies all dates between
	 * start and end, and puts them into a list (incl. start and end). The
	 * output is a complete list of dates within the specified period.
	 * 
	 */
	public static List<Date> getPeriodDates(Date start, Date end) {
		List<Date> period = new ArrayList<Date>();

		// add the beginning date
		period.add(start);

		// get calendar to manipulate and retrieve new dates
		Calendar currDate = Calendar.getInstance();
		currDate.setTime(start);
		Calendar rangeEnd = Calendar.getInstance();
		rangeEnd.setTime(end);

		// add all dates between start and end to list (excl. start, incl. end)
		while (currDate.before(rangeEnd)) {
			// increase date by 24 hours (one day)
			currDate.add(Calendar.HOUR_OF_DAY, 24);
			Date newDate = currDate.getTime();
			// add new day to period list
			period.add(newDate);
		}

		return period;
	}

}
