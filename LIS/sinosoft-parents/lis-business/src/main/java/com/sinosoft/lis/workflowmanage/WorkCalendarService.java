package com.sinosoft.lis.workflowmanage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDWorkCalendarDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDWorkCalendarSchema;
import com.sinosoft.lis.vschema.LDWorkCalendarSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;

/**
 * 提供工作日历的服务。
 * 
 * @author chenliang
 */
public class WorkCalendarService {

	private WorkCalendarService() {
	}

	private static Logger logger = Logger.getLogger(WorkCalendarService.class);

	public static void main(String[] a) {
		computeWorkTimes1("2009-01-01", "11:00:00", "2009-01-03", "13:00:00");

	}

	/**
	 * 生成指定年份的工作日历。
	 * 
	 * @param year
	 *            指定生成工作日历的年份
	 */
	public static void createWorkCalendar(int year) {
		// 放在WorkDayDefBL中实现了
	}

	/**
	 * 计算两个时刻之间相差的工作时间距
	 * 
	 * @param startTime
	 *            如果fromTime不在工作日历中，则将startTime设置为最近的后一个工作日的开始时刻
	 * @param endTime
	 *            如果endTime不在工作日历中，则将endTime设置为最近的前一个工作日的结束时刻
	 * @return 以分钟计算的两个时刻之间的差
	 */
	public static int computeWorkTimes(String startDate, String startTime,
			String endDate, String endTime) {
		int sumTime = 0;
		// 时间校验
		int interT = calIntervalTime(startDate + " " + startTime, endDate + " "
				+ endTime, "S");
		if (interT < 0) {
			logger.debug("结束时间在开始时间之前");
			return -1;
		}
		String workSql = "select * from LDWorkCalendar where caldate >= '?startDate?' " + " and caldate <= '?endDate?' "
				+ " and DateType='Y' order by caldate";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(workSql);
		sqlbv.put("startDate", startDate);
		sqlbv.put("endDate", endDate);
		LDWorkCalendarSet tLDWorkCalendarSet = new LDWorkCalendarDB()
				.executeQuery(sqlbv);
		if (tLDWorkCalendarSet.size() == 0) {
			logger.debug("没有取到相应工作表");
			return 0;
		}
		String nowDate = "";
		String nowTime = "";
		// 判断是否在工作日 确定开始计算时间和结束时间
		if (isWorkDay(startDate)) {// 如开始日期为工作日
			nowDate = startDate;
		} else {// 如开始日期为非工作日
			nowDate = tLDWorkCalendarSet.get(1).getCalDate();
			nowTime = tLDWorkCalendarSet.get(1).getAmBegin();
		}
		if (isWorkDay(endDate)) { // 如结束日期为工作日
		} else { // 如结束日期为非工作日
			endDate = tLDWorkCalendarSet.get(tLDWorkCalendarSet.size())
					.getCalDate();
			endTime = tLDWorkCalendarSet.get(tLDWorkCalendarSet.size())
					.getPmEnd();
		}
		// 判断是否在工作时间
		String timePlace = isWorkTime(nowDate, startTime);
		if (timePlace.equals("BEFORE")) { // 在上午之前
			nowTime = tLDWorkCalendarSet.get(1).getAmBegin();
		} else if (timePlace.equals("AFTER")) { // 在下午之后
			nowTime = tLDWorkCalendarSet.get(1).getPmEnd();
		} else if (timePlace.equals("MID")) { // 在中午休息时间
			nowTime = tLDWorkCalendarSet.get(1).getPmBegin();
		} else if (timePlace.equals("AM") || timePlace.equals("PM")) { // 在工作时间
			nowTime = startTime;
		}
		timePlace = isWorkTime(nowDate, endTime);
		if (timePlace.equals("BEFORE")) { // 在上午之前
			endTime = tLDWorkCalendarSet.get(1).getAmBegin();
		} else if (timePlace.equals("AFTER")) { // 在下午之后
			endTime = tLDWorkCalendarSet.get(1).getPmEnd();
		} else if (timePlace.equals("MID")) { // 在中午休息时间
			endTime = tLDWorkCalendarSet.get(1).getPmBegin();
		} else if (timePlace.equals("AM") || timePlace.equals("PM")) { // 在工作时间
			// endTime = endTime;
		}
		for (int i = 1; i <= tLDWorkCalendarSet.size(); i++) {
			if (nowDate.equals(endDate)) {// 起始日和终止日在同一天
				// nowTime是否在上午
				int amT = calIntervalTime(nowDate + " " + nowTime, nowDate
						+ " " + tLDWorkCalendarSet.get(i).getAmEnd(), "S");
				if (amT >= 0) {// nowTime在上午
					// 结束时间endTime是否在下午
					int pmT = calIntervalTime(nowDate + " " + endTime, nowDate
							+ " " + tLDWorkCalendarSet.get(i).getAmEnd(), "S");
					if (pmT >= 0) {// endTime在上午
						// 都在上午则直接取差
						sumTime += calIntervalTime(nowDate + " " + nowTime,
								nowDate + " " + endTime, "S");
					} else {// endTime在下午
						// 上午工作时间
						int amTime = calIntervalTime(nowDate + " " + nowTime,
								nowDate + " "
										+ tLDWorkCalendarSet.get(i).getAmEnd(),
								"S");
						// 下午工作时间
						int pmTime = calIntervalTime(nowDate + " "
								+ tLDWorkCalendarSet.get(i).getPmBegin(),
								nowDate + " " + endTime, "S");
						sumTime += (amTime + pmTime);
					}
				} else {// nowTime在下午
					// 下午工作时间
					int pmTime = calIntervalTime(nowDate + " " + nowTime,
							nowDate + " "
									+ tLDWorkCalendarSet.get(i).getPmEnd(), "S");
					sumTime += pmTime;
				}
			} else {// 起始日和终止日不在同一天
				if (nowTime.equals(tLDWorkCalendarSet.get(i).getAmBegin())) {// 不在同一天且从上午开始时间开始计算
					sumTime += Integer.parseInt(tLDWorkCalendarSet.get(i)
							.getWorkTime());
				} else {
					int amT = calIntervalTime(nowDate + " " + nowTime, nowDate
							+ " " + tLDWorkCalendarSet.get(i).getAmEnd(), "S");
					if (amT >= 0) {// nowTime在上午
						// 上午工作时间
						int amTime = calIntervalTime(nowDate + " " + nowTime,
								nowDate + " "
										+ tLDWorkCalendarSet.get(i).getAmEnd(),
								"S");
						// 下午工作时间
						int pmTime = Integer.parseInt(tLDWorkCalendarSet.get(i)
								.getPMWorkTime());
						sumTime += (amTime + pmTime);
					} else {// nowTime在下午
						// 下午工作时间
						int pmTime = calIntervalTime(nowDate + " "
								+ tLDWorkCalendarSet.get(i).getPmBegin(),
								nowDate + " "
										+ tLDWorkCalendarSet.get(i).getPmEnd(),
								"S");
						sumTime += pmTime;
					}
				}
				nowDate = tLDWorkCalendarSet.get(i + 1).getCalDate();// 指定到下一工作日
				nowTime = tLDWorkCalendarSet.get(i + 1).getAmBegin();// 指定到下一工作日上午开始时间
			}
		}
		return sumTime;
	}

	public static int computeWorkTimes1(String startDate, String startTime,
			String endDate, String endTime) {
		int sumTime = 0;
		// 时间校验
		int interT = calIntervalTime(startDate + " " + startTime, endDate + " "
				+ endTime, "S");
		if (interT < 0) {
			logger.debug("结束时间在开始时间之前");
			return -1;
		}
		String workSql = "select * from TDWorkCalendar where caldate >= '?startDate?' " + " and caldate <= '?endDate?' "
				+ " and DateType='Y' order by caldate";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(workSql);
		sqlbv1.put("startDate", startDate);
		sqlbv1.put("endDate", endDate);
		LDWorkCalendarSet tLDWorkCalendarSet = new LDWorkCalendarDB()
				.executeQuery(sqlbv1);
		if (tLDWorkCalendarSet.size() == 0) {
			logger.debug("没有取到相应工作表");
			return 0;
		}
		String nowDate = "";
		String nowTime = "";
		// 判断是否在工作日 确定开始计算时间和结束时间
		if (tLDWorkCalendarSet.get(1).getCalDate().equals(startDate)) {// 如开始日期为工作日
			nowDate = startDate;
			// 判断是否在工作时间
			String timePlace = isWorkTime(nowDate, startTime);
			if (timePlace.equals("BEFORE")) { // 在上午之前
				nowTime = tLDWorkCalendarSet.get(1).getAmBegin();
			} else if (timePlace.equals("AFTER")) { // 在下午之后
				nowTime = tLDWorkCalendarSet.get(1).getPmEnd();
			} else if (timePlace.equals("MID")) { // 在中午休息时间
				nowTime = tLDWorkCalendarSet.get(1).getPmBegin();
			} else if (timePlace.equals("AM") || timePlace.equals("PM")) { // 在工作时间
				nowTime = startTime;
			}
		} else {// 如开始日期为非工作日
			nowDate = tLDWorkCalendarSet.get(1).getCalDate();
			nowTime = tLDWorkCalendarSet.get(1).getAmBegin();
		}
		if (tLDWorkCalendarSet.get(tLDWorkCalendarSet.size()).getCalDate()
				.equals(endDate)) { // 如结束日期为工作日
			String timePlace = isWorkTime(endDate, endTime);
			if (timePlace.equals("BEFORE")) { // 在上午之前
				endTime = tLDWorkCalendarSet.get(tLDWorkCalendarSet.size())
						.getAmBegin();
			} else if (timePlace.equals("AFTER")) { // 在下午之后
				endTime = tLDWorkCalendarSet.get(tLDWorkCalendarSet.size())
						.getPmEnd();
			} else if (timePlace.equals("MID")) { // 在中午休息时间
				endTime = tLDWorkCalendarSet.get(tLDWorkCalendarSet.size())
						.getPmBegin();
			} else if (timePlace.equals("AM") || timePlace.equals("PM")) { // 在工作时间
				// endTime = endTime;
			}
		} else { // 如结束日期为非工作日
			endDate = tLDWorkCalendarSet.get(tLDWorkCalendarSet.size())
					.getCalDate();
			endTime = tLDWorkCalendarSet.get(tLDWorkCalendarSet.size())
					.getPmEnd();
		}
		for (int i = 1; i <= tLDWorkCalendarSet.size(); i++) {
			if (nowDate.equals(endDate)) {// 起始日和终止日在同一天
				// nowTime是否在上午
				int amT = calIntervalTime(nowDate + " " + nowTime, nowDate
						+ " " + tLDWorkCalendarSet.get(i).getAmEnd(), "S");
				if (amT >= 0) {// nowTime在上午
					// 结束时间endTime是否在下午
					int pmT = calIntervalTime(nowDate + " " + endTime, nowDate
							+ " " + tLDWorkCalendarSet.get(i).getAmEnd(), "S");
					if (pmT >= 0) {// endTime在上午
						// 都在上午则直接取差
						sumTime += calIntervalTime(nowDate + " " + nowTime,
								nowDate + " " + endTime, "S");
					} else {// endTime在下午
						// 上午工作时间
						int amTime = calIntervalTime(nowDate + " " + nowTime,
								nowDate + " "
										+ tLDWorkCalendarSet.get(i).getAmEnd(),
								"S");
						// 下午工作时间
						int pmTime = calIntervalTime(nowDate + " "
								+ tLDWorkCalendarSet.get(i).getPmBegin(),
								nowDate + " " + endTime, "S");
						sumTime += (amTime + pmTime);
					}
				} else {// nowTime在下午
					// 下午工作时间
					int pmTime = calIntervalTime(nowDate + " " + nowTime,
							nowDate + " "
									+ tLDWorkCalendarSet.get(i).getPmEnd(), "S");
					sumTime += pmTime;
				}
			} else {// 起始日和终止日不在同一天
				if (nowTime.equals(tLDWorkCalendarSet.get(i).getAmBegin())) {// 不在同一天且从上午开始时间开始计算
					sumTime += Integer.parseInt(tLDWorkCalendarSet.get(i)
							.getWorkTime());
				} else {
					int amT = calIntervalTime(nowDate + " " + nowTime, nowDate
							+ " " + tLDWorkCalendarSet.get(i).getAmEnd(), "S");
					if (amT >= 0) {// nowTime在上午
						// 上午工作时间
						int amTime = calIntervalTime(nowDate + " " + nowTime,
								nowDate + " "
										+ tLDWorkCalendarSet.get(i).getAmEnd(),
								"S");
						// 下午工作时间
						int pmTime = Integer.parseInt(tLDWorkCalendarSet.get(i)
								.getPMWorkTime());
						sumTime += (amTime + pmTime);
					} else {// nowTime在下午
						// 下午工作时间
						int pmTime = calIntervalTime(nowDate + " "
								+ tLDWorkCalendarSet.get(i).getPmBegin(),
								nowDate + " "
										+ tLDWorkCalendarSet.get(i).getPmEnd(),
								"S");
						sumTime += pmTime;
					}
				}
				nowDate = tLDWorkCalendarSet.get(i + 1).getCalDate();// 指定到下一工作日
				nowTime = tLDWorkCalendarSet.get(i + 1).getAmBegin();// 指定到下一工作日上午开始时间
			}
		}
		return sumTime;
	}

	/**
	 * @param startDate
	 *            开始时间 yyyy-MM-dd HH:mm:ss格式
	 * @param endDate
	 *            结束时间 yyyy-MM-dd HH:mm:ss格式
	 * @param unit
	 *            计算间隔 小时H 分钟M 秒S
	 * @return 计算的两个时刻之间的差
	 */
	public static int calIntervalTime(Date startDate, Date endDate, String unit) {
		int interval = 0;
		try {
			Date d1 = startDate;
			Date d2 = endDate;
			long diff = d2.getTime() - d1.getTime();// 精确到毫秒级
			if (unit.equals("H")) {
				interval = (int) diff / (1000 * 60 * 60);// 小时
			} else if (unit.equals("M")) {
				interval = (int) diff / (1000 * 60);// 分
			} else if (unit.equals("S")) {
				interval = (int) diff / 1000;// 秒
			}
		} catch (Exception e) {
		}
		return interval;
	}

	/**
	 * @param startDate
	 *            开始时间 yyyy-MM-dd HH:mm:ss格式字符串
	 * @param endDate
	 *            结束时间 yyyy-MM-dd HH:mm:ss格式字符串
	 * @param unit
	 *            计算间隔 小时H 分钟M 秒S
	 * @return 计算的两个时刻之间的差
	 */
	public static int calIntervalTime(String startDate, String endDate,
			String unit) {
		int interval = 0;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date d1 = df.parse(startDate);
			Date d2 = df.parse(endDate);
			interval = calIntervalTime(d1, d2, unit);
		} catch (Exception e) {
		}
		return interval;
	}

	/**
	 * 判断该时刻是否在工作日的工作时间内
	 * 
	 * @param time
	 * @return
	 */
	public static String isWorkTime(String date, String time) {
		String workSql = "select * from LDWorkCalendar where caldate = '?date?' and DateType='Y' ";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(workSql);
		sqlbv2.put("date", date);
		LDWorkCalendarSet tLDWorkCalendarSet = new LDWorkCalendarDB()
				.executeQuery(sqlbv2);
		logger.debug(workSql);
		if (tLDWorkCalendarSet.size() == 0) {
			return "";
		}
		// 判读是否在上午工作开始时间之前
		int amT = calIntervalTime(date + " " + time, date + " "
				+ tLDWorkCalendarSet.get(1).getAmBegin(), "S");
		if (amT > 0) {// 如果在上午上班之前
			return "BEFORE";
		} else {
			// 判读是否在下午工作结束时间之后
			int pmT = calIntervalTime(date + " "
					+ tLDWorkCalendarSet.get(1).getPmEnd(), date + " " + time,
					"S");
			if (pmT > 0) {
				return "AFTER";
			} else {
				// 判读是否在下午工作开始时间之前，上午下班时间结束 即中午休息时间
				int midT1 = calIntervalTime(date + " "
						+ tLDWorkCalendarSet.get(1).getAmEnd(), date + " "
						+ time, "S");
				int midT2 = calIntervalTime(date + " "
						+ tLDWorkCalendarSet.get(1).getPmBegin(), date + " "
						+ time, "S");
				if (midT1 > 0 && midT2 < 0) {
					return "MID";
				} else if (midT1 <= 0 && midT2 < 0) {// 上午工作时间内
					return "AM";
				} else if (midT1 > 0 && midT2 >= 0) {// 下午工作时间内
					return "PM";
				} else {
					return "";
				}
			}
		}
	}

	/**
	 * 判断该日期是否在工作日内
	 * 
	 * @param //time
	 * @return
	 */
	public static boolean isWorkDay(String date) {
		String workSql = "select * from LDWorkCalendar where caldate = '?date?' and DateType='Y' ";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(workSql);
		sqlbv3.put("date", date);
		LDWorkCalendarSet tLDWorkCalendarSet = new LDWorkCalendarDB()
				.executeQuery(sqlbv3);
		logger.debug(workSql);
		if (tLDWorkCalendarSet.size() == 0) {
			logger.debug("非工作日");
			return false;
		} else {
			logger.debug("工作日");
			return true;
		}
	}

	/**
	 * 获取指定时刻后（前）相聚days工作日后的日期
	 * 
	 * @param specDate
	 *            指定日期。//如果该时刻不在工作时间内，则将specDate设置为最近的前一个工作日
	 * @param days
	 *            如果为正数，则表示向后计算；如果为负数，则表示向前计算
	 * @return
	 */
	public static String getNextWorkDateByDays(String specDate, int days) {
		String returnDate = "";
		String subString = "";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		if (days >= 0) {
			String endDate = PubFun.calDate(specDate, 6, "M", specDate); // 防止数据量过大，只查将来半年的工作表
			subString = " caldate>='?specDate?' and caldate<='?endDate?' ";
			sqlbv4.put("specDate", specDate);
			sqlbv4.put("endDate", endDate);
		} else {
			String endDate = PubFun.calDate(specDate, -6, "M", specDate); // 防止数据量过大，只查将来半年的工作表
			subString = " caldate<='?specDate?' and caldate>='?endDate?' ";
			sqlbv4.put("specDate", specDate);
			sqlbv4.put("endDate", endDate);
		}
		String workSql = "select * from LDWorkCalendar where " + subString
				+ " and DateType='Y' order by caldate";
		sqlbv4.sql(workSql);
		LDWorkCalendarSet tLDWorkCalendarSet = new LDWorkCalendarDB()
				.executeQuery(sqlbv4);
		logger.debug(workSql);
		if (tLDWorkCalendarSet.size() < (Math.abs(days) + 1)) {
			logger.debug("没有找到T" + days + "日的工作日");
			return null;
		}
		if (days >= 0) {
			if (specDate.equals(tLDWorkCalendarSet.get(1).getCalDate())) {
				returnDate = tLDWorkCalendarSet.get(Math.abs(days) + 1)
						.getCalDate();
			} else {
				returnDate = tLDWorkCalendarSet.get(Math.abs(days))
						.getCalDate();
			}
		} else {
			if (specDate.equals(tLDWorkCalendarSet.get(
					tLDWorkCalendarSet.size()).getCalDate())) {
				returnDate = tLDWorkCalendarSet.get(
						tLDWorkCalendarSet.size() - Math.abs(days))
						.getCalDate();
			} else {
				returnDate = tLDWorkCalendarSet.get(
						tLDWorkCalendarSet.size() - (Math.abs(days) + 1))
						.getCalDate();
			}
		}
		return returnDate;
	}

	/**
	 * 获取指定时刻后（前）相聚hours工作小时后的时刻
	 * 
	 * @param specTime
	 *            指定时刻。如果该时刻不在工作时间内，则将specTime设置为最近的前一个工作日的结束时刻
	 * @param //hours
	 *            如果为正数，则表示向后计算；如果为负数，则表示向前计算
	 * @return
	 */
	public static Date getNextWorkTimeByHours(Date specTime, int days) {
		return null;
	}

	/**
	 * 获取指定时刻后（前）相聚minutes工作分钟后的时刻
	 * 
	 * @param specTime
	 *            指定时刻。如果该时刻不在工作时间内，则将specTime设置为最近的前一个工作日的结束时刻
	 * @param minutes
	 *            如果为正数，则表示向后计算；如果为负数，则表示向前计算
	 * @return
	 */
	public static Date getNextWorkTimeByMinutes(Date specTime, int minutes) {
		return null;
	}

	/**
	 * 将日期转为字符串 返回
	 * 
	 * @param tDate
	 *            指定时刻。如果该时刻不在工作时间内，则将specTime设置为最近的前一个工作日的结束时刻
	 * @return
	 */
	public static String transDateToStrings(Date tDate) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(tDate);
	}

	/**
	 * 计算标准结束日期和时间
	 * 
	 * @param startDate
	 *            开始日期
	 * @param startTime
	 *            开始时间
	 * @param timeFlag
	 *            时效类型
	 * @param transitionTime
	 *            时效
	 * @return
	 */
	public static TransferData getStandEndTimes(String startDate,
			String startTime, String timeFlag, String transitionTime) {
		return getStandEndTimes(startDate,startTime,timeFlag,transitionTime,"01");
	}

	/**
	 * 计算标准结束日期和时间
	 * 
	 * @param startDate
	 *            开始日期
	 * @param startTime
	 *            开始时间
	 * @param timeFlag
	 *            时效类型
	 * @param transitionTime
	 *            时效
	 * @param tType
	 *            工作日历类型
	 * @return
	 */
	public static TransferData getStandEndTimes(String startDate,
			String startTime, String timeFlag, String transitionTime,
			String tType) {
		int tHours = 0;
		int tMinutes = 0;
		int tSceconds = 0;
		TransferData returnTransferData = new TransferData();
		// ---------0.判断是否需要计算时效
		// if (tTMPlanSchema.getComputeType().equals("01") &&
		// timeFlag.equals("02"))
		if (false) {
			// 金额法没有T日，无需计算绝对时效
			returnTransferData.setNameAndValue("CalFlag", "N");
			return returnTransferData;
		} else {
			returnTransferData.setNameAndValue("CalFlag", "Y");
		}
		// ---------1.将开始日期和时间定为最近的工作日期和时间-------------
		// 判断是否在工作日 确定开始计算时间和结束时间
		if ("Y".equals(isWorkDay(startDate, tType))) { // 如开始日期为工作日
			// 判断是否在工作时间
			String timePlace = isWorkTime(startDate, startTime);
			LDWorkCalendarDB tLDWorkCalendarDB = new LDWorkCalendarDB();
			tLDWorkCalendarDB.setCalDate(startDate);
			tLDWorkCalendarDB.setDateType("Y");
			tLDWorkCalendarDB.setCalType(tType);
			LDWorkCalendarSet tLDWorkCalendarSet = tLDWorkCalendarDB.query();
			if (tLDWorkCalendarSet.size() == 0) {
				return null;
			}
			if (timePlace.equals("BEFORE")) { // 在上午之前
				startTime = tLDWorkCalendarSet.get(1).getAmBegin();
			} else if (timePlace.equals("AFTER")) { // 在下午之后
				startTime = tLDWorkCalendarSet.get(1).getPmEnd();
			} else if (timePlace.equals("MID")) { // 在中午休息时间
				startTime = tLDWorkCalendarSet.get(1).getPmBegin();
			}
		} else { // 如开始日期为非工作日
			String endDate = PubFun.calDate(startDate, 30, "D", startDate);// 防止数据量过大，只查将来30天的工作表
			String workSql = "select * from LDWorkCalendar where caldate>='?startDate?' and caldate<='?endDate?' and DateType='Y' and CalType ='?tType?' order by caldate";
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql(workSql);
			sqlbv5.put("startDate", startDate);
			sqlbv5.put("endDate", endDate);
			sqlbv5.put("tType", tType);
			LDWorkCalendarSet tLDWorkCalendarSet = new LDWorkCalendarDB()
					.executeQuery(sqlbv5);
			logger.debug(workSql);
			startDate = tLDWorkCalendarSet.get(1).getCalDate();
			startTime = tLDWorkCalendarSet.get(1).getAmBegin();
		}
		// ---------2.开始计算-------------
		if (timeFlag.equals("01")) {// 如果为相对型 transitionTime格式为 天-小时-分钟 Relative
			try {
				String[] times = transitionTime.split("-");
				// tDays = Integer.parseInt(times[0]);
				tHours = Integer.parseInt(times[0]);
				tMinutes = Integer.parseInt(times[1]);
				// tSceconds秒后必须完成
				tSceconds = tHours * 60 * 60 + tMinutes * 60;
				String endDate = PubFun.calDate(startDate, 6, "M", startDate);// 防止数据量过大，只查将来半年的工作表
				String workSql = "select * from LDWorkCalendar where caldate>='?startDate?' and caldate<='?endDate?' and DateType='Y' and CalType = '?tType?' order by caldate";
				SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
				sqlbv6.sql(workSql);
				sqlbv6.put("startDate", startDate);
				sqlbv6.put("endDate", endDate);
				sqlbv6.put("tType", tType);
				LDWorkCalendarSet tLDWorkCalendarSet = new LDWorkCalendarDB()
						.executeQuery(sqlbv6);
				String calTime = startTime;
				@SuppressWarnings("unused")
				String startWorkTime = "";// 上班时间
				String endWorkTime = "";// 下班时间
				String nextStartWorkTime = "";// 下一个上班时间
				String result = "FALSE";
				for (int i = 1; i <= tLDWorkCalendarSet.size(); i++) {
					// 计算上午
					startWorkTime = tLDWorkCalendarSet.get(i).getAmBegin();// 上午上班时间
					endWorkTime = tLDWorkCalendarSet.get(i).getAmEnd();// 上午下班时间
					nextStartWorkTime = tLDWorkCalendarSet.get(i).getPmBegin();
					TransferData tTransferData = calEndRelative(
							tLDWorkCalendarSet.get(i), calTime,
							nextStartWorkTime, endWorkTime, tSceconds);
					result = (String) tTransferData.getValueByName("RESULT");
					calTime = (String) tTransferData.getValueByName("CALTIME");
					tSceconds = Integer.parseInt((String) tTransferData
							.getValueByName("LIMIT"));
					if (result.equals("TRUE")) {
						returnTransferData.setNameAndValue("StandEndDate",
								tLDWorkCalendarSet.get(i).getCalDate());
						returnTransferData.setNameAndValue("StandEndTime",
								calTime);
						return returnTransferData;
					}
					// 计算下午
					endWorkTime = tLDWorkCalendarSet.get(i).getPmEnd();// 上午下班时间
					nextStartWorkTime = tLDWorkCalendarSet.get(i + 1)
							.getAmBegin();// 下一个开始工作时间为下一天的上午开始时间
					TransferData aTransferData = calEndRelative(
							tLDWorkCalendarSet.get(i), calTime,
							nextStartWorkTime, endWorkTime, tSceconds);
					result = (String) aTransferData.getValueByName("RESULT");
					calTime = (String) aTransferData.getValueByName("CALTIME");
					tSceconds = Integer.parseInt((String) aTransferData
							.getValueByName("LIMIT"));
					if (result.equals("TRUE")) {
						returnTransferData.setNameAndValue("StandEndDate",
								tLDWorkCalendarSet.get(i).getCalDate());
						returnTransferData.setNameAndValue("StandEndTime",
								calTime);
						return returnTransferData;
					}
				}
			} catch (Exception ex) {
				logger.debug(ex.toString());
				return null;
			}
		} else if (timeFlag.equals("02")) {// 如果为绝对型 transitionTime格式为 T+(-)N
			// HH:MM Absolutely
		}
		return null;
	}

	/**
	 * 相对时效计算标准结束时间
	 * 
	 * @param calTime
	 *            开始日期
	 * @param nextStartWorkTime
	 *            开始时间
	 * @param endWorkTime
	 *            时效类型
	 * @param limit
	 *            剩余时间
	 * @return
	 */
	public static TransferData calEndRelative(
			LDWorkCalendarSchema tLDWorkCalendarSchema, String calTime,
			String nextStartWorkTime, String endWorkTime, int limit) {
		TransferData tTransferData = new TransferData();
		String calDate = tLDWorkCalendarSchema.getCalDate();
		// ------------------------------加速---------------------------
		if (calTime.equals(tLDWorkCalendarSchema.getAmBegin())) {
			int amTime = Integer
					.parseInt(tLDWorkCalendarSchema.getAMWorkTime());
			if (limit > amTime) {
				limit = limit - amTime;
				calTime = nextStartWorkTime;
				tTransferData.setNameAndValue("RESULT", "FALSE");// 计算未完成--caldate+剩余时效超过本地工作结束时间，需继续计算
				tTransferData.setNameAndValue("CALTIME", calTime);
				tTransferData.setNameAndValue("LIMIT", String.valueOf(limit));
				return tTransferData;
			}
		} else if (calTime.equals(tLDWorkCalendarSchema.getPmBegin())) {
			int pmTime = Integer
					.parseInt(tLDWorkCalendarSchema.getPMWorkTime());
			if (limit > pmTime) {
				limit = limit - pmTime;
				calTime = nextStartWorkTime;
				tTransferData.setNameAndValue("RESULT", "FALSE");// 计算未完成--caldate+剩余时效超过本地工作结束时间，需继续计算
				tTransferData.setNameAndValue("CALTIME", calTime);
				tTransferData.setNameAndValue("LIMIT", String.valueOf(limit));
				return tTransferData;
			}
		}
		// -----------------------------
		int inter = calIntervalTime(calDate + " " + calTime, calDate + " "
				+ endWorkTime, "S");
		if (inter >= 0) {// caldate不在此次工作结束时间内
			if (limit > inter) {
				limit = limit - inter;
				calTime = nextStartWorkTime;
				tTransferData.setNameAndValue("RESULT", "FALSE");// 计算未完成--caldate+剩余时效超过本地工作结束时间，需继续计算
			} else {
				try {
					calTime = calTimes(calTime, limit, "S");
				} catch (ParseException ex) {
				}
				tTransferData.setNameAndValue("RESULT", "TRUE");// 计算完成
			}
		} else {
			tTransferData.setNameAndValue("RESULT", "FALSE");// 计算未完成--caldate不在此次工作结束时间内
		}
		tTransferData.setNameAndValue("CALTIME", calTime);
		tTransferData.setNameAndValue("LIMIT", String.valueOf(limit));
		return tTransferData;
	}

	/**
	 * 计算时间
	 * 
	 * @param startTime
	 *            开始时间 格式: HH:MM:SS
	 * @param interval
	 *            时间间隔
	 * @param unit
	 *            时间间隔单位
	 * @return
	 */
	public static String calTimes(String startTime, int interval, String unit)
			throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date baseDate = df.parse(PubFun.getCurrentDate() + " " + startTime);
		GregorianCalendar mCalendar = new GregorianCalendar();
		mCalendar.setTime(baseDate);
		if (unit.equals("Y")) {
			mCalendar.add(Calendar.HOUR, interval);
		}
		if (unit.equals("M")) {
			mCalendar.add(Calendar.MINUTE, interval);
		}
		if (unit.equals("S")) {
			mCalendar.add(Calendar.SECOND, interval);
		}
		String hour = "00";
		String minute = "00";
		String second = "00";
		if (mCalendar.get(Calendar.HOUR_OF_DAY) < 10) {
			hour = "0" + String.valueOf(mCalendar.get(Calendar.HOUR_OF_DAY));
		} else {
			hour = String.valueOf(mCalendar.get(Calendar.HOUR_OF_DAY));
		}
		if (mCalendar.get(Calendar.MINUTE) < 10) {
			minute = "0" + String.valueOf(mCalendar.get(Calendar.MINUTE));
		} else {
			minute = String.valueOf(mCalendar.get(Calendar.MINUTE));
		}
		if (mCalendar.get(Calendar.SECOND) < 10) {
			second = "0" + String.valueOf(mCalendar.get(Calendar.SECOND));
		} else {
			second = String.valueOf(mCalendar.get(Calendar.SECOND));
		}
		return hour + ":" + minute + ":" + second;
	}

	/**
	 * 工作日判断，根据sql函数workdayjudge判断。
	 * 
	 * @param tDate
	 *            日期字符串
	 * @param tType
	 *            工作日类型（根据）
	 * @return String yes，no，unknown
	 */
	public static String isWorkDay(String tDate, String tType) {
		String result = null;
		ExeSQL tExeSQL = new ExeSQL();
		DateFormat tDateFormat = DateFormat.getDateInstance();
		try {
			if (tType != null || !"".equals(tType)) {
				//tDate = tDateFormat.format(tDateFormat.parse(tDate));// 此句防止错误的日期格式引起sql错误
				SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
				sqlbv7.sql(new StringBuffer(
						"select workdayjudge('").append("?tDate?").append("','")
						.append("?tType?").append("') from dual").toString());
				sqlbv7.put("tDate", tDate);
				sqlbv7.put("tType", tType);
				result = tExeSQL.getOneValue(sqlbv7);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.debug(ex.toString());
		}
		if ("Y".equals(result)) {
			result = "yes";
		} else if ("N".equals(result)) {
			result = "no";
		} else {
			result = "unknown";
		}
		return result;
	}

	/**
	 * 计算输入日期的后n个工作日是多少日期，根据sql函数workdaylater判断
	 * 
	 * @param tDate
	 *            日期字符串
	 * @param days
	 *            经过n个工作日
	 * @param tType
	 *            工作日类型
	 * @return
	 */
	public static String WorkDaysLater(String tDate, int days, String tType) {
		String result = null;
		ExeSQL tExeSQL = new ExeSQL();
		DateFormat tDateFormat = DateFormat.getDateInstance();
		try {
			if (tType != null && !"".equals(tType) && days != 0) {
				tDate = tDateFormat.format(tDateFormat.parse(tDate));// 此句防止错误的日期格式引起sql错误
				SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
				sqlbv8.sql(new StringBuffer(
						"select workdaylater('").append("?tDate?").append("',")
						.append("?days?").append(",'").append(tType).append(
								"') from dual").toString());
				sqlbv8.put("tDate", tDate);
				sqlbv8.put("days", days);
				result = tExeSQL.getOneValue(sqlbv8);// 得到日期+时间格式
				if (result != null && !"".equals(result))
					result = tDateFormat.format(tDateFormat.parse(result));// 恢复日期格式
			}
		} catch (Exception ex) {
			result = "";
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * 计算起止输入日期之间有多少个工作日，根据sql函数workdaycount判断
	 * 
	 * @param tBeginDate
	 *            日期字符串
	 * @param tEndDate
	 *            日期字符串
	 * @param tType
	 *            工作日类型
	 * @return
	 */
	public static int WorkDayCount(String tBeginDate, String tEndDate,
			String tType) {
		int result = 0;
		ExeSQL tExeSQL = new ExeSQL();
		DateFormat tDateFormat = DateFormat.getDateInstance();
		try {
			if (tType != null || !"".equals(tType)) {
				tBeginDate = tDateFormat.format(tDateFormat.parse(tBeginDate));// 此句防止错误的日期格式引起sql错误
				tEndDate = tDateFormat.format(tDateFormat.parse(tEndDate));// 此句防止错误的日期格式引起sql错误
				SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
				sqlbv9.sql(new StringBuffer(
						"select workdaycount('").append("?tBeginDate?").append(
						"','").append("?tEndDate?").append("','").append("?tType?")
						.append("') from dual").toString());
				sqlbv9.put("tBeginDate", tBeginDate);
				sqlbv9.put("tEndDate", tEndDate);
				sqlbv9.put("tType", tType);
				String temp = tExeSQL.getOneValue(sqlbv9);// 得到日期+时间格式
				result = Integer.valueOf(temp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
