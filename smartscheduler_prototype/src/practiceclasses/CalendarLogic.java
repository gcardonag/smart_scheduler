package practiceclasses;

import java.lang.Integer;
import java.util.Calendar;
/* This class operate only with year,month,day,hour and minutes

*/

public class CalendarLogic {
	int ERROR = 2;
	int SAME = 1;
	int NOT_SAME = 0;

	/****************************** DATE *********************************************/

	/* validate the format of the entry */
	/* year */
	public int convertYear(String pYear) {
		try {
			int year = Integer.parseInt(pYear);
			return year;
		} catch (Exception e) {
			return 0;
		}
	}

	/* month: [1..12] */
	public int convertMonth(String pMonth) {
		try {
			int month = Integer.parseInt(pMonth);
			if (month < 1 || month > 12)
				return 0;
			return month;
		} catch (Exception e) {
			return 0;
		}
	}

	/* day: [1..31] depends of the month and year */
	public int convertDay(String pYear, String pMonth, String pDay) {
		try {
			int year = convertYear(pYear);
			if (year == 0)
				return 0;
			int month = convertMonth(pMonth);
			if (month == 0)
				return 0;
			int day = Integer.parseInt(pDay);
			int daysOfMonth = getDaysOfMonths(pYear, pMonth);
			if (daysOfMonth > 0) {
				if (day >= 1 && day <= daysOfMonth)
					return day;
				return 0;
			}
			return 0;

		} catch (Exception e) {
			return 0;
		}
	}

	public int getDaysOfMonths(String pYear, String pMonth) {
		try {
			int year = convertYear(pYear);
			if (year == 0)
				return 0;
			int month = convertMonth(pMonth);
			if (month == 0)
				return 0;
			/* january, march, may, july, august,october, december */
			if (month == 1 || month == 3 || month == 5 || month == 7
					|| month == 8 || month == 10 || month == 12)
				return 31;
			/* april, june,september,november */
			if (month == 4 || month == 6 || month == 9 || month == 11)
				return 30;
			/* february */
			if (month == 2 && isLeap(pYear))
				return 29;
			if (month == 2 && !isLeap(pYear))
				return 28;
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	public boolean isLeap(String pYear) {
		try {
			int year = convertYear(pYear);
			if (year == 0)
				return false;
			/* divisible per 4 but not divisible per 100 */
			if (year % 4 == 0 && year % 100 != 0)
				return true;
			return false;

		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * validate if is the same date(day/month/year) OUTPUT: 0 not is the same
	 * date 1 is the same date 2 error
	 */
	public int isSameDate(String year1, String month1, String day1,
			String year2, String month2, String day2) {
		// convert is correct
		int nYear1 = convertYear(year1);
		int nYear2 = convertYear(year2);
		int nMonth1 = convertMonth(month1);
		int nMonth2 = convertMonth(month2);
		int nDay1 = convertDay(year1, month1, day1);
		int nDay2 = convertDay(year2, month2, day2);
		if (nYear1 == 0 || nYear2 == 0 || nMonth1 == 0 || nMonth2 == 0
				|| nDay1 == 0 || nDay2 == 0)
			return ERROR;
		if (nYear1 == nYear2 && nMonth1 == nMonth2 && nDay1 == nDay2)
			return SAME;
		return NOT_SAME;
	}

	/*
	 * validate if is the date dd_1/mm_1/yyyy_1 > dd_2/mm_2/yyyy_2 OUTPUT: 0
	 * dd_1/mm_1/yyyy_1 IS BEFORE dd_2/mm_2/yyyy_2 1 dd_1/mm_1/yyyy_1 AFTER
	 * dd_2/mm_2/yyyy_2 2 error
	 */
	public int isBeforeAfterDate(String year1, String month1, String day1,
			String year2, String month2, String day2) {
		int BEFORE = 0;
		int AFTER = 1;
		// convert is correct
		int nYear1 = convertYear(year1);
		int nYear2 = convertYear(year2);
		int nMonth1 = convertMonth(month1);
		int nMonth2 = convertMonth(month2);
		int nDay1 = convertDay(year1, month1, day1);
		int nDay2 = convertDay(year2, month2, day2);
		if (nYear1 == 0 || nYear2 == 0 || nMonth1 == 0 || nMonth2 == 0
				|| nDay1 == 0 || nDay2 == 0)
			return ERROR;

		if (nYear1 < nYear2)
			return BEFORE;
		if (nYear1 > nYear2)
			return AFTER;
		if (nMonth1 < nMonth2)
			return BEFORE;
		if (nMonth1 > nMonth2)
			return AFTER;
		if (nDay1 < nDay2)
			return BEFORE;
		if (nDay1 > nDay2)
			return AFTER;

		return ERROR;
	}

	/* add days to a date : return a date with the format : dd/mm/yyyy */
	public String addDays(String year, String month, String day, int daysToAdd) {
		String date = format(year, month, day);
		for (int i = 0; i < daysToAdd; i++) {
			date = format(year, month, day);
			if (date.equals("ERROR"))
				return "ERROR";
			date = addDay(year, month, day);
			year = getYearFromFormat(date);
			month = getMonthFromFormat(date);
			day = getDayFromFormat(date);
		}
		return date;
	}

	/* add day to a date : return a date with the format : dd/mm/yyyy */
	public String addDay(String year, String month, String day) {
		int nYear = convertYear(year);
		int nMonth = convertMonth(month);
		int nDay = convertDay(year, month, day);
		if (nYear == 0 || nMonth == 0 || nDay == 0)
			return "ERROR";
		/* depends of the month and day */
		/* last day of the year */
		if (nMonth == 12 && getDaysOfMonths(year, month) == nDay) {
			nYear++;
			nMonth = 1;
			nDay = 1;
		} else {
			/* last day of the month */
			if (getDaysOfMonths(year, month) == nDay) {
				nMonth++;
				nDay = 1;
			} else {
				nDay++;
			}
		}
		return format(nYear, nMonth, nDay);

	}

	public String format(String strYear, String strMonth, String strDay) {
		if (strYear.length() != 4)
			return "ERROR";
		if (strMonth.length() == 1)
			strMonth = "0" + strMonth;
		if (strMonth.length() != 2)
			return "ERROR";
		if (strDay.length() == 1)
			strDay = "0" + strDay;
		if (strDay.length() != 2)
			return "ERROR";
		return strDay + "/" + strMonth + "/" + strYear;
	}

	public String format(int year, int month, int day) {
		String strYear = year + "";
		if (strYear.length() != 4)
			return "ERROR";
		String strMonth = month + "";
		if (strMonth.length() == 1)
			strMonth = "0" + strMonth;
		if (strMonth.length() != 2)
			return "ERROR";
		String strDay = day + "";
		if (strDay.length() == 1)
			strDay = "0" + strDay;
		if (strDay.length() != 2)
			return "ERROR";
		return strDay + "/" + strMonth + "/" + strYear;
	}

	// return yyyy from dd/mm/yyyy hh:mm
	public String getYearFromFormat(String datetime) {
		String date = datetime.substring(0, 10);
		// if(date.length() == 10){
		String year = date.substring(6, date.length());
		int nYear = convertYear(year);
		if (nYear == 0)
			return "ERROR";
		return year;
		// }
		// return "ERROR";
	}

	// return mm from dd/mm/yyyy hh:mm
	public String getMonthFromFormat(String datetime) {
		String date = datetime.substring(0, 10);
		// if(date.length() == 10){
		String month = date.substring(3, 5);
		int nMonth = convertMonth(month);
		if (nMonth == 0)
			return "ERROR";
		return month;
		// }
		// return "ERROR";
	}

	// return dd from dd/mm/yyyy hh:mm
	public String getDayFromFormat(String datetime) {
		String date = datetime.substring(0, 10);
		// if(date.length() == 10){
		String year = getYearFromFormat(date);
		String month = getMonthFromFormat(date);
		String day = date.substring(0, 2);
		int nDay = convertDay(year, month, day);
		if (nDay == 0)
			return "ERROR";
		return day;
		// }
		// return "ERROR";
	}

	public int getNumberWeekday(String year, String month, String day) {
		// convert is correct
		int nYear = convertYear(year);
		int nMonth = convertMonth(month);
		int nDay = convertDay(year, month, day);
		if (nYear == 0 || nMonth == 0 || nDay == 0)
			return ERROR;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, nDay);
		cal.set(Calendar.MONTH, nMonth - 1);
		cal.set(Calendar.YEAR, nYear);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/****************************** TIME *********************************************/
	/* validate the format of the entry */
	/* hour: [0..23] */
	public int convertHour(String pHour) {
		try {
			int hour = Integer.parseInt(pHour);
			return hour % 24;
		} catch (Exception e) {
			return 0;
		}
	}

	/* minute: [0..59] */
	public int convertMinute(String pMinute) {
		try {
			int minute = Integer.parseInt(pMinute);
			return minute % 60;
		} catch (Exception e) {
			return 0;
		}
	}

	/*
	 * input : dd/mm/yyyy output: dd/mm/yyyy hh:mm
	 */
	public String insertTime(String date, String hour, String mins) {
		String dateTime = date + " " + format(hour, mins);
		return dateTime;
	}

	/*
	 * validate if is the same time(day/month/year) OUTPUT: 0 not is the same
	 * time 1 is the same time 2 error
	 */
	public int isSameTime(String hour1, String minute1, String hour2,
			String minute2) {
		// convert is correct
		int nHour1 = convertHour(hour1);
		int nMin1 = convertMinute(minute1);
		int nHour2 = convertHour(hour2);
		int nMin2 = convertMinute(minute2);
		if (nHour1 == 0 || nHour2 == 0 || nMin1 == 0 || nMin2 == 0)
			return ERROR;
		if (nHour1 == nHour2 && nMin1 == nMin2)
			return SAME;
		return NOT_SAME;
	}

	/*
	 * validate if is the time hh_1:min_1>hh_2:min_2 valid to use in the same
	 * DATE OUTPUT: 0 hh_1:min_1 IS BEFORE hh_2:min_2 1 hh_1:min_1 IS AFTER
	 * hh_2:min_2 2 error
	 */
	public int isBeforeAfterTime(String hour1, String minute1, String hour2,
			String minute2) {
		int BEFORE = 0;
		int AFTER = 1;
		// convert is correct
		int nHour1 = convertHour(hour1);
		int nMin1 = convertMinute(minute1);
		int nHour2 = convertHour(hour2);
		int nMin2 = convertMinute(minute2);
		if (nHour1 < nHour2)
			return BEFORE;
		if (nHour1 > nHour2)
			return AFTER;
		if (nMin1 < nMin2)
			return BEFORE;
		if (nMin1 > nMin2)
			return AFTER;
		return ERROR;
	}

	/* return hh:mm */
	public String format(String strHour, String strMinutes) {
		if (strHour.length() == 1)
			strHour = "0" + strHour;
		if (strHour.length() != 2)
			return "ERROR";
		if (strMinutes.length() == 1)
			strMinutes = "0" + strMinutes;
		if (strMinutes.length() != 2)
			return "ERROR";
		return strHour + ":" + strMinutes;
	}

	/* return hh:mm */
	public String format(int hour, int minutes) {
		String strHour = hour + "";
		if (strHour.length() == 1)
			strHour = "0" + strHour;
		if (strHour.length() != 2)
			return "ERROR";
		String strMinutes = minutes + "";
		if (strMinutes.length() == 1)
			strMinutes = "0" + strMinutes;
		if (strMinutes.length() != 2)
			return "ERROR";
		return strHour + ":" + strMinutes;
	}

	// return hh from dd/mm/yyyy hh:mm
	public String getHourFromFormat(String datetime) {

		if (datetime.length() >= 16) {
			String date = datetime.substring(11, 16);

			// if(date.length() == 5){
			String hour = date.substring(0, 2);
			int nHour = convertHour(hour);
			return hour;
		}
		return "ERROR";
	}

	// return mm from dd/mm/yyyy hh:mm
	public String getMinutesFromFormat(String datetime) {
		if (datetime.length() >= 16) {
			String date = datetime.substring(11, 16);
			// if(date.length() == 5){
			String minutes = date.substring(3, 5);
			int nMinutes = convertMinute(minutes);
			return minutes;
		}
		return "ERROR";
	}

	/****************************** DATE - TIME *********************************************/
	/*
	 * validate if is the datetime dd_1/mm_1/yyyy_1 hh_1:min_1 >
	 * dd_2/mm_2/yyyy_2 hh_2:min_2 OUTPUT: 0 dd_1/mm_1/yyyy_1 hh_1:min_1 IS
	 * BEFORE dd_2/mm_2/yyyy_2 hh_2:min_2 1 dd_1/mm_1/yyyy_1 hh_1:min_1 AFTER
	 * dd_2/mm_2/yyyy_2 hh_2:min_2 2 error
	 */
	public int isBeforeAfterDateTime(String year1, String month1, String day1,
			String hour1, String minute1, String year2, String month2,
			String day2, String hour2, String minute2) {
		int BEFORE = 0;
		int AFTER = 1;
		if (isBeforeAfterDate(year1, month1, day1, year2, month2, day2) == 0)
			return BEFORE;
		if (isBeforeAfterDate(year1, month1, day1, year2, month2, day2) == 1)
			return AFTER;
		if (!hour1.equals("") && !hour2.equals("") && !minute1.equals("")
				&& !minute2.equals("")) {
			if (isBeforeAfterTime(hour1, minute1, hour2, minute2) == 0)
				return BEFORE;
			if (isBeforeAfterTime(hour1, minute1, hour2, minute2) == 1)
				return AFTER;
		}
		return ERROR;
	}

	public String format(String strYear, String strMonth, String strDay,
			String strHour, String strMinutes) {
		if (strYear.length() != 4)
			return "ERROR";
		if (strMonth.length() == 1)
			strMonth = "0" + strMonth;
		if (strMonth.length() != 2)
			return "ERROR";
		if (strDay.length() == 1)
			strDay = "0" + strDay;
		if (strDay.length() != 2)
			return "ERROR";
		if (strHour.length() == 1)
			strHour = "0" + strHour;
		if (strHour.length() != 2)
			return "ERROR";
		if (strMinutes.length() == 1)
			strMinutes = "0" + strMinutes;
		if (strMinutes.length() != 2)
			return "ERROR";
		return strDay + "/" + strMonth + "/" + strYear + " " + strHour + ":"
				+ strMinutes;
	}

}