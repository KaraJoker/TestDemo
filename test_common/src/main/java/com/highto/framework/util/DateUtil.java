package com.highto.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {

	private static Map<String, String> nextDayMap = new HashMap<String, String>();

	private static Map<String, Long> timeMap = new HashMap<String, Long>();

	private static Map<Long, String> dateMap = new HashMap<Long, String>();

	static {
		// 计算10年的下一天日期,缓存起来
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		for (int i = 1; i < 3650; i++) {
			String current = dateformat.format(c.getTime());
			c.add(Calendar.DATE, 1);
			String next = dateformat.format(c.getTime());
			nextDayMap.put(current, next);
		}
		// 计算10年的字符串转换成long时间，缓存起来
		c = Calendar.getInstance();
		for (int i = 1; i < 3650; i++) {
			String current = dateformat.format(c.getTime());
			try {
				long time = dateformat.parse(current).getTime();
				timeMap.put(current, time);
				dateMap.put(time, current);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			c.add(Calendar.DATE, 1);
		}
	}

	public static Date getTimeFromNow(int field, int amount) {
		Calendar c = Calendar.getInstance();
		c.add(field, amount);
		return c.getTime();
	}

	public static Date getTimeFrom(Date base, int field, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(base);
		c.add(field, amount);
		return c.getTime();
	}

	/**
	 * 整形到日，把小时，分，秒,毫秒全部清零。
	 * 
	 * @param date
	 * @return
	 */
	public static Date trimToDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static String fastGetNextDayStr(String todayStr) {
		return nextDayMap.get(todayStr);
	}

	public static Long fastGetTime(String timeStr) {
		return timeMap.get(timeStr);
	}

	public static String fastGetDate(Long time) {
		return dateMap.get(time);
	}

	/**
	 * 距离现在的天数。正数代表将来的时间
	 * 
	 * @param deadline
	 * 
	 * @return
	 */
	public static int getDaysFromNow(long deadline) {
		Date now = trimToDate(new Date());
		return (int) ((deadline - now.getTime()) / (24 * 60 * 60 * 1000));
	}

}
