package com.like.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;

public class DateUtil {

	@SuppressLint("SimpleDateFormat")
	public static int getYear() {
		long time = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yy");
		Date date = new Date(time);
		String year = format.format(date);
		return Integer.parseInt(year);
	}

	public static int getMonth() {
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		int dow = cal.get(Calendar.DAY_OF_WEEK);
		int dom = cal.get(Calendar.DAY_OF_MONTH);
		int doy = cal.get(Calendar.DAY_OF_YEAR);
		return month;
	}
	
	public static String getDayOfWeekCN(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dow = cal.get(Calendar.DAY_OF_WEEK) - 1;
		String result = null;
		switch (dow) {
		case 1:
			result = "星期一";
			break;
		case 2:
			result = "星期二";
			break;
		case 3:
			result = "星期三";
			break;
		case 4:
			result = "星期四";
			break;
		case 5:
			result = "星期五";
			break;
		case 6:
			result = "星期六";
			break;
		case 7:
			result = "星期天";
			break;
		}
		return result;
	}

	public static int getMonthLastDay(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	public static Date toDate(String year, String month, String day,
			String hour, String min) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault());
			Date date = sdf.parse(year + "-" + month + "-" + day + " " + hour
					+ ":" + min);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean isSameDay(Date day1, Date day2) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
	    String ds1 = sdf.format(day1);
	    String ds2 = sdf.format(day2);
	    if (ds1.equals(ds2)) {
	        return true;
	    } else {
	        return false;
	    }
	}
	
	public static boolean isSameDay(String day1Str, String day2Str) {
		Date date1 = toDate(day1Str, "yyyy-MM-dd");
		Date date2 = toDate(day2Str, "yyyy-MM-dd");
		return isSameDay(date1, date2);
	}
	
	public static Date toDate(String dateStr, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		try {
			Date date = sdf.parse(dateStr);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		String str = format.format(date);
		return str;
	}
	
	public static String toString(Date date, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr, Locale.getDefault());
		String str = sdf.format(date);
		return str;
	}

	public static String formatDate(int year, int month, int day) {
		String formatMonth = month < 10 ? "0" + month : month + "";
		String formatDay = day < 10 ? "0" + day : "" + day;
		return year + "-" + formatMonth + "-" + formatDay;
	}

	public static String formatTime(int hour, int min) {
		String formatHour = hour < 10 ? "0" + hour : "" + hour;
		String formatMin = min < 10 ? "0" + min : "" + min;
		return formatHour + ":" + formatMin;
	}
	
	public static String formatTime(String hour, String min) {
		String formatHour = hour.length()<2?"0"+hour:hour;
		String formatMin = min.length()<2?"0"+min:min;
		return formatHour +":"+formatMin;
	}

}
