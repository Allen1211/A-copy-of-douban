package com.allen.douban.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 日期与字符串格式化转换,日期相关操作的工具类
 *
 */
public class DateUtil {
	public static String formatYearToDay = "yyyy-MM-dd";
	public static String formatYearToMinute = "yyyy-MM-dd HH:mm";
	public static String formatMonthToMinute = "MM-dd HH:mm";
	public static String formatYearToSecond = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 日期转为字符串，按照特定的的格式
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date,String pattern) {
		if (date == null) {
			return null;
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			return dateFormat.format(date);
		}
	}
	/**
	 * 字符串转为日期，按照特定的格式
	 * @param dateStr
	 * @return
	 */
	public static Date StringToDate(String dateStr,String pattern) {
		if (dateStr == null) {
			return null;
		} else {
			Date date = null;
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
				date = dateFormat.parse(dateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return date;
		}
	}

	/**
	 * 计算日期过了x分钟后的日期
	 * @param date
	 * @param minute
	 * @return	返回String
	 */
	public static String dateAddWithMinute(Date date, int minute) {
		date = new Date(date.getTime() + (long) minute * 60 * 1000);
		return dateToString(date,formatMonthToMinute);

	}
	/**
	 * 计算日期过了x分钟后的日期
	 * @param date
	 * @param minute
	 * @return	返回Date
	 */
	public static Date dateAddWithMinute(int minute, Date date) {
		date = new Date(date.getTime() + (long) minute * 60 * 1000);
		return date;

	}
	/**
	 * 计算日期加上x天的新日期
	 * @param date
	 * @param day
	 * @return	
	 */
	public static String dateAddWithDay(Date date, int day) {
		date = new Date(date.getTime() + (long) day * 24 * 60 * 60 * 1000);
		return dateToString(date,formatYearToDay);
	}
}
