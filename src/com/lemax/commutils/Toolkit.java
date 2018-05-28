package com.lemax.commutils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
/**
 * 体温单显示类
 * @author key
 *
 */
public class Toolkit {
	private static java.util.Date oldDate;
	private static int seq = 0;
	private static int SIGNID_VERSION = 32;
	private static SimpleDateFormat sdf = new SimpleDateFormat();

	public static final String nullToStr(String str) {
		if (str == null) {
			return "";
		}
		return str.trim();
	}

	public static final String nullToStr(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	public static final String nullToValue(Object obj, String value) {
		if (obj != null) {
			if (obj instanceof String) {
				if (obj.toString().trim().equals("")) {
					return value;
				}
				return obj.toString();
			}

			return value;
		}

		return value;
	}

	public static final double nullToDouble(Object obj, double value) {
		if (obj != null) {
			if (obj instanceof String) {
				if (obj.toString().trim().equals("")) {
					return value;
				}
				return Double.parseDouble(obj.toString());
			}

			return value;
		}

		return value;
	}

	public static final int nullToInt(Object obj, int value) {
		if (obj != null) {
			if (obj instanceof String) {
				if (obj.toString().trim().equals("")) {
					return value;
				}
				return Integer.parseInt(obj.toString());
			}

			return value;
		}

		return value;
	}

	public static String getSignID() {
		java.util.Date curdate = new java.util.Date();

		long mss = curdate.getTime();
		if (oldDate != null) {
			if (!oldDate.equals(curdate)) {
				seq = 0;
				oldDate = curdate;
			}
		} else {
			seq = 0;
			oldDate = curdate;
		}
		seq += 1;
		if (seq >= 10000)
			seq = 0;
		long lid = mss * 10000L + seq;

		if (SIGNID_VERSION == 32) {
			return intTo32(lid);
		}
		return intTo64(lid);
	}

	private static String intTo32(long lnum) {
		char ch;
		String sResult = "";
		while ( lnum > 0L ) {
			long lMod = lnum % 32L;
			lnum /= 32L;
			if (lMod < 10L)
				ch = (char) (int) (48L + lMod);
			else {
				ch = (char) (int) (65L + lMod - 10L);
			}
			sResult = ch + sResult;
		}
		return sResult + "AA";
	}

	private static String intTo64(long lnum) {
		char ch;
		String sResult = "";
		while (lnum > 0L) {
			int lMod = (int) (lnum % 64L);
			lnum /= 64L;
			if (lMod < 10) {
				ch = (char) (48 + lMod);
			} else {
				if (lMod < 36) {
					ch = (char) (65 + lMod - 10);
				} else {
					if (lMod < 62) {
						ch = (char) (97 + lMod - 36);
					} else {
						if (lMod == 62)
							ch = '{';
						else
							ch = '}';
					}
				}
			}
			sResult = ch + sResult;
		}
		return sResult;
	}

	public static String getDateTime(String Format) {
		java.util.Date theDate = new java.util.Date();
		SimpleDateFormat df = new SimpleDateFormat(Format);
		return df.format(theDate);
	}

	public static int getIntervalDays(Date startDate, Date endDate) {
		long startdate = startDate.getTime();
		long enddate = endDate.getTime();
		long interval = enddate - startdate;
		int intervalday = (int) (interval / 86400000L);
		return intervalday;
	}

	public static String formatDateTime(java.util.Date thedate, String formatstring) {
		if (thedate == null)
			return "";
		SimpleDateFormat df = new SimpleDateFormat(formatstring);
		return df.format(thedate);
	}

	public static synchronized Calendar parseCalendarDayFormat(String strDate) {
		String pattern = "yyyy-MM-dd";
		return parseCalendarFormat(strDate, pattern);
	}

	public static synchronized Calendar parseCalendarFormat(String strDate, String pattern) {
		synchronized (sdf) {
			Calendar cal = null;
			sdf.applyPattern(pattern);
			try {
				sdf.parse(strDate);
				cal = sdf.getCalendar();
			} catch (Exception localException) {
			}
			return cal;
		}
	}

	public static synchronized String getDateFormat(Calendar cal, String pattern) {
		return getDateFormat(cal.getTime(), pattern);
	}

	public static synchronized String getDateFormat(java.util.Date date, String pattern) {
		synchronized (sdf) {
			String str = null;
			sdf.applyPattern(pattern);
			str = sdf.format(date);
			return str;
		}
	}

	public static synchronized Calendar parseCalendarW3CFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseCalendarFormat(strDate, pattern);
	}

	public static java.util.Date strToDate(String strDay) {
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			java.util.Date tmp = myFmt.parse(strDay);
			return tmp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final int strToInt(String s) {
		return strToInt(s, 0);
	}

	public static final int strToInt(String s, int i) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException nfe) {
		}
		return i;
	}

	public static void main(String[] args) {
	}

	public static String arrayToSqlStr(String[] array) {
		String s = new String("");
		if (array == null)
			return s;
		if (array.length == 0) {
			return s;
		}
		for (int i = 0; i < array.length; ++i) {
			s = s + "'" + array[i] + "',";
		}
		if (s.length() > 0) {
			s = s.substring(0, s.length() - 1);
		}

		return s;
	}

	public static String listToSqlStr(ArrayList<?> list) {
		String s = new String("");
		if (list == null)
			return s;
		if (list.size() == 0) {
			return s;
		}
		for (int i = 0; i < list.size(); ++i) {
			s = s + "'" + list.get(i) + "',";
		}
		if (s.length() > 0) {
			s = s.substring(0, s.length() - 1);
		}

		return s;
	}

	public static String[] listToArray(ArrayList<?> list) {
		if (list == null) {
			return null;
		}
		String[] returnValue = new String[list.size()];
		for (int i = 0; i < list.size(); ++i) {
			returnValue[i] = ((String) list.get(i));
		}
		return returnValue;
	}

	public static ArrayList<String> arrayToList(String[] array) {
		ArrayList<String> returnValue = new ArrayList<String>();
		if (array != null) {
			for (int i = 0; i < array.length; ++i) {
				returnValue.add(array[i]);
			}
		}

		return returnValue;
	}

	public static String formatPrefix(Object obj, int len, String defalutValue) {
		if ((len <= 0) || (defalutValue == null)) {
			return null;
		}
		String returnValue = "";
		if (obj == null) {
			for (int i = 0; i < len; ++i) {
				returnValue = returnValue + defalutValue;
			}
		} else if (obj.toString().length() < len) {
			String tempStr = "";
			for (int i = 0; i < len - obj.toString().length(); ++i) {
				tempStr = tempStr + defalutValue;
			}
			returnValue = tempStr + obj.toString();
		} else {
			return obj.toString();
		}

		if (returnValue.length() > len) {
			returnValue = returnValue.substring(returnValue.length() - len, returnValue.length());
		}

		return returnValue;
	}

	public static String getWeekDayByDate(String strdate) {
		String[] dayNames = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		java.util.Date date = new java.util.Date();
		try {
			date = sdfInput.parse(strdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.setTime(date);
		int dayOfWeek = calendar.get(7) - 1;
		if (dayOfWeek < 0)
			dayOfWeek = 0;
		return dayNames[dayOfWeek];
	}

	public static String getCurrentDayAndWeekDay(String format) {
		String strdateReturn = getDateTime(format);
		String strdate = getDateTime("yyyy-MM-dd");
		String[] dayNames = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		java.util.Date date = new java.util.Date();
		try {
			date = sdfInput.parse(strdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.setTime(date);
		int dayOfWeek = calendar.get(7) - 1;
		if (dayOfWeek < 0)
			dayOfWeek = 0;
		return strdateReturn + "  " + dayNames[dayOfWeek];
	}

	public static java.util.Date now() {
		return Calendar.getInstance().getTime();
	}

	public static String dateToStr(java.util.Date date, String format, Locale locale) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
		return sdf.format(date);
	}

	public static String dateToStr(java.util.Date date, String format) {
		return dateToStr(date, format, Locale.getDefault());
	}

	public static String dateToStr(java.util.Date date) {
		return dateToStr(date, "yyyy-MM-dd HH:MM:ss.SSS");
	}

	public static String dateToStr() {
		return dateToStr(now());
	}

	public static String joinWithComma(String[] array, boolean bool) {
		if ((array == null) || (array.length == 0)) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		String[] arrayOfString = array;
		int j = array.length;
		for (int i = 0; i < j; ++i) {
			String s = arrayOfString[i];
			s = s.trim();
			if (bool) {
				sb.append(",").append(s);
			} else if (!"".equals(s)) {
				sb.append(",").append(s);
			}
		}

		if (sb.charAt(0) == ',') {
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}
}