package com.socialcrap.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

	public static final String DATE_PATTERN = "dd-MM-yyyy";
	public static final String DB_DATE_PATTERN = "yyyy-MM-dd";
	public static final String DB_TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DOB_DATE_PATTERN = "dd/MM/yyyy";
	public static final String TIMESTAMP_PATTERN = "dd-MM-yyyy HH:mm:ss";
	public static final String TIME_PATTERN_12_HOUR = "hh:mm:ss aa";
	public static final String TIME_PATTERN_24_HOUR = "HH:mm:ss";
	public static final String DATE_TIME_NAME_PATTERN = "EEEE_dd_MMM_YYYY_HH_mm_ss";
	public static final String START_24_HOUR_TIME_PATTERN = "00:00:00";
	public static final String END_24_HOUR_TIME_PATTERN = "23:59:59";
	public static final String START_TIMESTAMP_PATTERN = "yyyy-MM-dd 00:00:00";
	public static final String END_TIMESTAMP_PATTERN = "yyyy-MM-dd 23:59:59";
	public static final String DECORATE_PATTERN = "HH:mm:ss EEEEE dd MMMMM yyyy";

	public static Date parseDate(String date, String format) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String dateToString(Date date) {
		if (date == null) {
			return null;
		}
		return date.toString();
	}

	public static String formatDate(Date date, String format) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}
		return null;
	}

	public static Date modifyTime(Date date, int hours, int minutes, int seconds) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.set(Calendar.HOUR_OF_DAY, hours);
		cal.set(Calendar.MINUTE, minutes);
		cal.set(Calendar.SECOND, seconds);
		return cal.getTime();
	}

	public static Date modifyDate(Date date, int years, int months, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, years);
		c.add(Calendar.MONTH, months);
		c.add(Calendar.DATE, days);
		return c.getTime();
	}

}
