package com.sinosoft.lis.taskservice.crontab;

import org.apache.log4j.Logger;

import java.util.Date;

public class CrontabParser {
	private static Logger logger = Logger.getLogger(CrontabParser.class);

	public static Date calNext(String str, Date currentDate) {
		String[] ss = str.split(" ");
		if (ss.length != 6)
			throw new IllegalArgumentException("应为6列");
		DateModel dm = new DateModel();
		CrontabField minute = new MinuteCrontabField(ss[0]);
		CrontabField hour = new HourCrontabField(ss[1]);
		CrontabField date = new DateCrontabField(ss[2], new DateModel(
				currentDate));
		CrontabField month = new MonthCrontabField(ss[3]);
		CrontabField year = new YearCrontabField(ss[4]);
		CrontabField day = new DayCrontabField(ss[5], currentDate);

		Date max = new Date(year.getMax() - 1900, month.getMax() - 1, date
				.getMax(), hour.getMax(), minute.getMax());
		if (currentDate.getTime() > max.getTime())
			throw new IllegalArgumentException("无新目标");
		dm.setYear(year.calCurrentAvailable(currentDate.getYear() + 1900));
		checkyear(dm);
		if (dm.getYear() > currentDate.getYear() + 1900) {
			dm.setMonth(month.getMin());
			dm.setDate(date.getMin());
			dm.setHour(hour.getMin());
			dm.setMinute(minute.getMin());
		} else {
			dm.setMonth(month.calCurrentAvailable(currentDate.getMonth() + 1));
			checkMonth(dm, currentDate, year, month);
			if (dm.getYear() > currentDate.getYear() + 1900
					|| dm.getMonth() > currentDate.getMonth() + 1) {
				dm.setDate(date.getMin());
				dm.setHour(hour.getMin());
				dm.setMinute(minute.getMin());
			} else {
				if ("*".equals(ss[5])) {
					dm.setDate(date.calCurrentAvailable(currentDate.getDate()));
				} else if ("*".equals(ss[2])) {
					dm.setDate(day.calCurrentAvailable(currentDate.getDay()));
				} else {
					dm.setDate(calDateAndDay(dm, currentDate.getDate(), date,
							day));
				}
				checkDate(dm, currentDate, year, month, date, day);
				if (dm.getYear() > currentDate.getYear() + 1900
						|| dm.getMonth() > currentDate.getMonth() + 1
						|| dm.getDate() > currentDate.getDate()) {
					dm.setHour(hour.getMin());
					dm.setMinute(minute.getMin());
				} else {
					dm
							.setHour(hour.calCurrentAvailable(currentDate
									.getHours()));
					checkHour(dm, currentDate, year, month, date, hour, day);
					if (dm.getYear() > currentDate.getYear() + 1900
							|| dm.getMonth() > currentDate.getMonth() + 1
							|| dm.getDate() > currentDate.getDate()
							|| dm.getHour() > currentDate.getHours()) {
						dm.setMinute(minute.getMin());
					} else {
						dm.setMinute(minute.calNext(currentDate.getMinutes()));
						checkMinite(dm, currentDate, year, month, date, hour,
								minute, day);
					}
				}
			}
		}
		return new Date(dm.getYear() - 1900, dm.getMonth() - 1, dm.getDate(),
				dm.getHour(), dm.getMinute());
	}

	private static void checkMinite(DateModel dm, Date currentDate,
			CrontabField year, CrontabField month, CrontabField date,
			CrontabField hour, CrontabField minute, CrontabField day) {
		if (dm.getMinute() == -1) {
			dm.setMinute(minute.getMin());
			dm.setHour(hour.calNext(currentDate.getHours()));
			checkHour(dm, currentDate, year, month, date, hour, day);
		}

	}

	private static void checkHour(DateModel dm, Date currentDate,
			CrontabField year, CrontabField month, CrontabField date,
			CrontabField hour, CrontabField day) {
		if (dm.getHour() == -1) {
			dm.setHour(hour.getMin());
			if (day.getMax() == 6 && day.getMin() == 0) {//不限星期
				dm.setDate(date.calNext(currentDate.getDate()));
			} else {//限制星期
			    //解决月末最后一天计算错误的问题（因为在checkDate中能够根据限制星期计算出正确的日期，所以当小时引起了日期变化时，直接日期+1然后调用checkDate重算出正确的日期）
//                dm.setDate(calDateAndDay(dm, date
//                        .calNext(currentDate.getDate()), date, day));
	            dm.setDate(day.calCurrentAvailable(new Date(currentDate.getTime()+ 24 * 60 * 60 * 1000).getDay()));
			}
			checkDate(dm, currentDate, year, month, date, day);
		}
	}

	private static void checkDate(DateModel dm, Date currentDate,
			CrontabField year, CrontabField month, CrontabField date,
			CrontabField day) {
		if (dm.getDate() == -1) {
			if (day.getMax() == 6 && day.getMin() == 0) {//不限星期
				dm.setMonth(month.calNext(currentDate.getMonth() + 1));
				((DateCrontabField) date).setCurrentDate(new DateModel(
						currentDate.getYear() + 1900,
						currentDate.getMonth() + 2, 1));
				dm.setDate(date.getMin());
				checkMonth(dm, currentDate, year, month);
			} else {//限制星期
				while (dm.getDate() == -1) {
					dm.setMonth(month.calNext(dm.getMonth()));
					checkMonth(dm, currentDate, year, month);
					((DateCrontabField) date).setCurrentDate(new DateModel(
							currentDate.getYear() + 1900, currentDate
									.getMonth() + 2, 1));
					dm.setDate(calDateAndDay(dm, date.getMin(), date, day));
				}
			}
		}
	}

	private static void checkyear(DateModel yeartarget) {
		if (yeartarget.getYear() == -1)
			throw new IllegalArgumentException("无新目标");
	}

	private static void checkMonth(DateModel dm, Date currentDate,
			CrontabField year, CrontabField month) {
		if (dm.getMonth() == -1) {
			dm.setMonth(month.getMin());
			dm.setYear(year.calNext(dm.getYear()));
			checkyear(dm);
		}
	}

	/**
	 * 根据日期和星期条件，生成指定月中最近的符合日期，无则返回-1
	 * 
	 * @param dm
	 * @param currentDate 目前计算的日期
	 * @param year
	 * @param month
	 * @param date
	 * @param day
	 * @return int
	 */
	private static int calDateAndDay(DateModel dm, int tCurrentDate,
			CrontabField date, CrontabField day) {
		int tDate = date.calCurrentAvailable(tCurrentDate);
		int tMonth = dm.getMonth() - 1;
		Date aDate = new Date(dm.getYear() - 1900, tMonth, tDate);
		int tMaxDate = date.getMax();
		int tMinDate = date.getMin();
		int tMaxDay = day.getMax();
		int tMinDay = day.getMin();
		int tDay = aDate.getDay();
		while (tDate <= tMaxDate && tDate >= tMinDate) {
			if (aDate.getMonth() != tMonth) {
				break;
			}

			if (tDay <= tMaxDay && tDay >= tMinDay) {
				return tDate;
			} else {
				tDate = date.calNext(tDate);
				if (tDate == -1) {
					break;
				}
				aDate.setDate(tDate);
				tDay = aDate.getDay();
			}
		}
		return -1;
	}
}
