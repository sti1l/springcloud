package com.realz.dao.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期相关工具类
 * @author yinlijun
 * @since  2016/01/05
 */
public class TimeUtil {
	
	/**
	 * 获取当前时间精确到天的日期字符串yyyy-MM-dd
	 * @return
	 */
	public static String getDateStrToDay() {
		Date date = new Date();
		return formatDateToDay(date);
	}

	/**
	 * 获取当前时间精确到天的日期字符串yyyyMMdd
	 * @return
	 */
	public static String getDateStrToDayNoSeparator() {
		Date date = new Date();
		return formatDateToDayNoSeparator(date);
	}

	/**
	 * 获取当前时间精确到秒的字符串yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getDateStrToSec() {
		Date date = new Date();
		return formatDateToSecond(date);
	}
	
	/**
	 * 获取当前时间精确到秒的字符串yyyyMMddHHmmss
	 * @return
	 */
	public static String getDateStrToSecNoSeparator() {
		Date date = new Date();
		return formatDateToSecNoSeparator(date);
	}
	
	/**
	 * 获取当前时间精确到毫秒的字符串yyyy-MM-dd HH:mm:ss.sss
	 * @return
	 */
	public static String getDateStrToMill() {
		Date date = new Date();
		return formatDateToMill(date);
	}
	
	/**
	 * 获取当前时间精确到毫秒的字符串yyyyMMddHHmmsssss
	 * @return
	 */
	public static String getDateStrToMillNoSeparator() {
		Date date = new Date();
		return formatDateToMillNoSeparator(date);
	}

	/**
	 * 格式化日期yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String formatDateToDay(Date date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}	

	/**
	 * 格式化日期yyyyMMdd
	 * @param date
	 * @return
	 */
	public static String formatDateToDayNoSeparator(Date date) {
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(date);
	}

	/**
	 * 格式化日期yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String formatDateToSecond(Date date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	
	/**
	 * 格式化日期yyyyMMddHHmmss
	 * @param date
	 * @return
	 */
	public static String formatDateToSecNoSeparator(Date date) {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(date);
	}
	
	/**
	 * 根据特定的格式，格式化日期
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date,String format) {
		DateFormat dateformat = new SimpleDateFormat(format);
		return dateformat.format(date);
	}
	
	/**
	 * 格式化日期yyyy-MM-dd HH:mm:ss.sss
	 * @param date
	 * @return
	 */
	public static String formatDateToMill(Date date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return format.format(date);
	}
	
	/**
	 * 格式化日期yyyyMMddHHmmsssss
	 * @param date
	 * @return
	 */
	public static String formatDateToMillNoSeparator(Date date) {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return format.format(date);
	}
	
	/**
	 * 验证传参日期和当前日期的比较，精确到日,-1表示小于当前天数，0表示和当前是同一天，1表示大于当前天数
	 * @param date
	 * @return
	 */
	public static Integer compareDateToCurdateByday(Date date) {
		//对传参日期进行格式化，精确到天
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		//构建当前日期，并进行格式化，精确到天
		Calendar curCalendar = GregorianCalendar.getInstance();
		curCalendar.set(Calendar.HOUR_OF_DAY, 0);
		curCalendar.set(Calendar.MINUTE, 0);
		curCalendar.set(Calendar.SECOND, 0);
		curCalendar.set(Calendar.MILLISECOND, 0);
		
		//传参日期和当前日期都精确到天后进行比较
		Long l = calendar.getTime().getTime() - curCalendar.getTime().getTime();
		Integer b;
		if (l == 0) {
			b = 0;
		} else if (l > 0) {
			b = 1;
		} else {
			b = -1;
		}
			
		return b;
	}
	/**
	 * 以Timestamp的形式获取当前时间
	 * @return
	 */
	public static Timestamp getDateToTimestamp(){
		return new Timestamp(new Date().getTime());
	}
	/**
	 * 根据当前日期追加年、月、日
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Timestamp getDateToTimestatmp(int year,int month,int day){
		 Calendar calendar = Calendar.getInstance();        
		 Date date = new Date(System.currentTimeMillis());        
		 calendar.setTime(date);     
		 calendar.add(Calendar.YEAR, year);  
		 calendar.add(Calendar.MONTH, month);
         calendar.add(Calendar.DATE, day);
		 date = calendar.getTime();       
		 return new Timestamp(date.getTime());
	}

	/**
	 * 根据当前日期追加年、月、日、时、分、秒
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Timestamp getDateToTimestatmp(int year,int month,int day,int hour,int minute, int second){
		Calendar calendar = Calendar.getInstance();        
		Date date = new Date(System.currentTimeMillis());        
		calendar.setTime(date);     
		calendar.add(Calendar.YEAR, year);  
		calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.DATE, day);
        calendar.add(Calendar.HOUR_OF_DAY, hour);  
		calendar.add(Calendar.MINUTE, minute);
        calendar.add(Calendar.SECOND, second);
		date = calendar.getTime();
		return new Timestamp(date.getTime());
	}
	
	/**
	 * 比较两个日期的大小，-1表示date1小于date2,0表示date1等于date2，1表示date1大于date2
	 * @param date1
	 * @param date2
	 * @param formatStr 
	 * @return
	 * @throws ParseException
	 */
	public static Integer compareDate1ToDate2(String date1, String date2, String formatStr) throws ParseException {
		//传参日期和当前日期都精确到天后进行比较
		Long l = parseDate(date1, formatStr).getTime() - parseDate(date2, formatStr).getTime();
		Integer b;
		if (l == 0) {
			b = 0;
		} else if (l > 0) {
			b = 1;
		} else {
			b = -1;
		}
			
		return b;
	}
	
	/**
	 * 把日期字符串格式化成对应的日期对象
	 * @param dateStr
	 * @param formatStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String dateStr, String formatStr) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
		return simpleDateFormat.parse(dateStr);
	}
	
	/**
	 * 把日期字符串格式化成对应的日期对象,并对对应的field进行改变
	 * @param dateStr 日期字符串
	 * @param formatStr 格式化字符串的表达式
	 * @param field  需要更改的field
	 * @param amount 改变量
	 * @return
	 * @throws ParseException
	 */
	public static Date addDate(String dateStr, String formatStr , int field, int amount) throws ParseException {
		Date date = parseDate(dateStr, formatStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}
	
	/**
	 * 格式化日期
	 * @param date
	 * @param farmat
	 * @return
	 */
	public static String formatDateToDay(Date date,String farmat) {
		DateFormat format = new SimpleDateFormat(farmat);
		return format.format(date);
	}
	
	
	public static String getTwoDay(){
		
		Date date=new Date();//取时间
	    Calendar calendar = new GregorianCalendar();
	    calendar.setTime(date);
	    calendar.add(calendar.DATE,1);
	    date=calendar.getTime(); 
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    String dateString = formatter.format(date);
		return dateString;
	}
	
	
	
}
