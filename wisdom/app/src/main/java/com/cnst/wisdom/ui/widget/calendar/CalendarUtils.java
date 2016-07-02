package com.cnst.wisdom.ui.widget.calendar;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 闰年月算法
 * @author Vincent Lee
 */
public class CalendarUtils {

	// 系统当前时间
	private static int sys_year;
	private static int sys_month;
	private static int sys_day;
	private static LunarCalendar lc = null;
	private static boolean isLeapyear = false; // 是否为闰年
	private static int daysOfMonth = 0; // 某月的天数
	private static int dayOfWeek = 0; // 具体某一天是星期几
	private static int lastDaysOfMonth = 0; // 上一个月的总天数
	private static String leapMonth = ""; // 闰哪一个月
	private static String cyclical = ""; // 天干地支
	private static String animalsYear = "";
	public final static int SAMEDAY = 0;
	public final static int BAFORE = -1;
	public final static int AFTER = 1;

	public static ArrayList<Calendar> getCalendar(Calendar calendar){
		calendar.set(Calendar.DAY_OF_MONTH,1);
		lc = new LunarCalendar();
		sys_year = calendar.get(Calendar.YEAR);
		sys_month = calendar.get(Calendar.MONTH);
		sys_day = calendar.get(Calendar.DAY_OF_MONTH);
		isLeapyear = isLeapYear(sys_year); // 是否为闰年
		daysOfMonth = getDaysOfMonth(isLeapyear, sys_month+1); // 某月的总天数
//		Calendar[] calendars = new Calendar[daysOfMonth];
		ArrayList<Calendar> calendars = new ArrayList();//本月所有日期
		dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;//某日是周几
		for(int i = 0; i<dayOfWeek; i++)
		{
			calendars.add(null);//占据本月第一天前面的位置
		}
//		lastDaysOfMonth = getDaysOfMonth(isLeapyear, sys_month); // 上一个月的总天数
		setAnimalsYear(lc.animalsYear(sys_year));
		setLeapMonth(lc.leapMonth == 0 ? "" : String.valueOf(lc.leapMonth));
		setCyclical(lc.cyclical(sys_year));
		getWeek(calendars);
		return calendars;
	}

//	public static int getDayOfWeek()
//	{
//		return dayOfWeek;
//	}

	public static boolean compareTo(Calendar calendar,Calendar anotherCalendar) {
		if(calendar==null||anotherCalendar==null){
			return false;
		}
		return calendar.get(Calendar.DAY_OF_MONTH)==anotherCalendar.get(Calendar.DAY_OF_MONTH)
				&&calendar.get(Calendar.MONTH)==anotherCalendar.get(Calendar.MONTH)
				&&calendar.get(Calendar.YEAR)==anotherCalendar.get(Calendar.YEAR);
	}
	public static boolean isAfter(Calendar calendar,Calendar anotherCalendar) {
		if(calendar.get(Calendar.YEAR)>anotherCalendar.get(Calendar.YEAR)){
			return true;
		}
		if(calendar.get(Calendar.MONTH)>anotherCalendar.get(Calendar.MONTH)){
			return true;
		}
		if(calendar.get(Calendar.DAY_OF_MONTH)>anotherCalendar.get(Calendar.DAY_OF_MONTH)){
			return true;
		}
		return false;
	}

	// 将一个月中的每一天的值添加入数组dayNuber中
	private static void getWeek(ArrayList<Calendar> calendars) {
		// 得到当前月的所有日程日期(这些日期需要标记)
		for (int i = 0; i < daysOfMonth; i++) {
			int year = sys_year;
			int month = sys_month;
//			int day = i-dayOfWeek+1;
//			if (day<0) { // 前一个月
//				day = lastDaysOfMonth +day;
//				month--;
//				if(month<0){
//					month=11;
//					year --;
//				}
//			} else if (day>=daysOfMonth) { // 下一个月
//				day = day-daysOfMonth;
//				month++;
//				if(month>11){
//					month=0;
//					year++;
//				}
//			}
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, i+1);
			calendars.add(calendar);

		}
	}
	public static void matchScheduleDate(int year, int month, int day) {

	}

	//生肖
	public static String getAnimalsYear() {
		return animalsYear;
	}

	public static void setAnimalsYear(String animalsYear) {
		CalendarUtils.animalsYear = animalsYear;
	}

	public static String getLeapMonth() {
		return leapMonth;
	}

	public static void setLeapMonth(String leapMonth) {
		CalendarUtils.leapMonth = leapMonth;
	}

	public static String getCyclical() {
		return cyclical;
	}

	public static void setCyclical(String cyclical) {
		CalendarUtils.cyclical = cyclical;
	}

	// 判断是否为闰年  其简单计算方法:1。能被4整除而不能被100整除。（如2004年就是闰年,1800年不是。）2。能被400整除。（如2000年是闰年）
	public static boolean isLeapYear(int year) {
		if (year % 100 == 0 && year % 400 == 0) {
			return true;
		} else if (year % 100 != 0 && year % 4 == 0) {
			return true;
		}
		return false;
	}

	// 得到某月有多少天数
	public static int getDaysOfMonth(boolean isLeapyear, int month) {
		int days = 0;
		switch (month) {
		case 0:
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
		case 13:
			days = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			days = 30;
			break;
		case 2:
			if (isLeapyear) {
				days = 29;
			} else {
				days = 28;
			}

		}
		return days;
	}

	// 指定某年中的某月的第一天是星期几
	public static int getWeekdayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1);
		dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return dayOfWeek;
	}

}
