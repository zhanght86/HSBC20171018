package com.sinosoft.easyscan5.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import com.sinosoft.easyscan5.common.Constants;



/*
 * 时间转换类
 * 
 * 
 */
public class FDate
{    
    

	/**
	 * 得到当前系统日期 
	 * 
	 * @return 当前日期的格式字符串,日期格式为"yyyy-MM-dd"
	 */
	public static String getCurrentDate() {
		String pattern = Constants.DATE_FORMAT;
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}
	
	/**
	 * 得到当前系统时间 
	 * 
	 * @return 当前时间的格式字符串，时间格式为"HH:mm:ss"
	 */
	public static String getCurrentTime() {
		String pattern = Constants.TIME_FORMAT;
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}
	
	
	/**
	 * 得到当前系统日期时间 
	 * 
	 * @return 当前时间的格式字符串，时间格式为"yyyy-MM-dd HH:mm:ss"
	 */
	public static String getCurrentDateTime() {
		String pattern = Constants.DATE_TIME_FORMAT;
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}
	/**
	 * 得到当前系统日期时间 
	 * 
	 * @return 当前时间的格式字符串，时间格式为"yyyy-MM-dd HH:mm:ss"
	 * @throws ParseException 
	 */
	public static Date getCurrentDateAndTime() throws ParseException {
		String tString = getCurrentDateTime();
		Date tDate = formatYMDHMSToDate(tString);
		return tDate;
	}
    /**
	 * 日期字符(必须是"yyyy-MM-dd HH:mm:ss"格式)串转成Date
	 * 
	 * 说明："yyyy-MM-dd"格式字符串会报错.
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date formatYMDHMSToDate(String dateStr) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		return  format.parse(dateStr);
	}
	/**
	 * 日期字符(可以是"yyyy-MM-dd"格式，也可以是"yyyy-MM-dd  HH:mm:ss")串转成Date
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date formatYMDToDate(String dateStr) {
		SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
		Date initDate = null;
		try {
			initDate = format.parse(dateStr);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return initDate;
	}
	/**
	 *  日期字符("yyyy-MM-dd HH:mm:ss.SSS")串转成Date
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date formatYMDHMSSToDate(String dateStr) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return  format.parse(dateStr);
	}
	/**
	 * 日期转字符串格式 yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date){
		DateFormat format1 = new SimpleDateFormat(Constants.DATE_FORMAT);
        return format1.format(date);

	}
	/**
	 * 日期转字符串格式 yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date){
		DateFormat format1 = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        return format1.format(date);

	}
	/**
	 * 日期转字符串格式 yyyy-MM-dd HH:mm:ss,SSS
	 * @param date
	 * @return
	 */
	public static String formatDateTimeMS(Date date){
		String timeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS") .format(date);
        return timeStr;
	}
	/**
	 * 日期转字符串格式 yyyy-MM-dd HH:mm:ss,SSS
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static String formatTimeMS(Date date) throws ParseException {
		String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS") .format(date);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date initDate = null;
		Calendar Cld = Calendar.getInstance();
		initDate = format.parse(dateStr);
		String hmssStr = Cld.get(Calendar.HOUR_OF_DAY) + ":" 
				+ Cld.get(Calendar.MINUTE) + ":" + Cld.get(Calendar.SECOND) 
				+ "." + Cld.get(Calendar.MILLISECOND);
		return hmssStr;
	}
	/**
	 * 日期转字符串格式
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date,String pattern){
		DateFormat format1 = new SimpleDateFormat(pattern);
        return format1.format(date);
	}	
	/**
	 * 计算日期加上年之后的日期
	 * @param sourceDate
	 * @param year
	 * @return
	 */
	public static Date addYear(Date sourceDate,int year) {
		Calendar calendar =Calendar.getInstance();
		calendar.setTime(sourceDate);
		calendar.add(Calendar.YEAR, year);
		return calendar.getTime();
	}
	/**
	 * 计算日期加上月份之后的日期
	 * @param sourceDate
	 * @param month
	 * @return
	 */
	public static Date addMonth(Date sourceDate,int month) {
		Calendar calendar =Calendar.getInstance();
		calendar.setTime(sourceDate);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}	
	
	
	
	/**
	 * 计算日期加上日之后的日期
	 * @param sourceDate
	 * @param day
	 * @return
	 */
	public static Date addDay(Date sourceDate,int day) {
		Calendar calendar =Calendar.getInstance();
		calendar.setTime(sourceDate);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}	
	/**
	 * 计算日期加上小时之后的日期
	 * @param sourceDate
	 * @param hour
	 * @return
	 */
	public static Date addHour(Date sourceDate,int hour) {
		Calendar calendar =Calendar.getInstance();
		calendar.setTime(sourceDate);
		calendar.add(Calendar.HOUR, hour);
		return calendar.getTime();
	}
	
	/**
	 * 计算日期加上分钟之后的日期
	 * @param sourceDate
	 * @param minute
	 * @return
	 */
	public static Date addMinute(Date sourceDate,int minute) {
		Calendar calendar =Calendar.getInstance();
		calendar.setTime(sourceDate);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}
	
	/**
	 * 计算日期加上秒之后的日期
	 * @param sourceDate
	 * @param second
	 * @return
	 */
	public static Date addSecond(Date sourceDate,int second) {
		Calendar calendar =Calendar.getInstance();
		calendar.setTime(sourceDate);
		calendar.add(Calendar.SECOND, second);
		return calendar.getTime();
	}	
	
 

}
