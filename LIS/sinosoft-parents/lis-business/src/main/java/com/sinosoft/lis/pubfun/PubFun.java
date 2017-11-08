/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.pubfun;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LMRiskPayDB;
import com.sinosoft.lis.vschema.LMRiskPaySet;
import com.sinosoft.lis.workflowmanage.WorkCalendarService;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.JdbcUrl;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.SchemaSet;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:业务系统的公共业务处理函数 该类包含所有业务处理中的公共函数，和以前系统中的funpub.4gl
 * 文件相对应。在这个类中，所有的函数都采用Static的类型，所有需要的数据都是 通过参数传入的，在本类中不采用通过属性传递数据的方法。
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author YT
 * @version 1.0
 */
public class PubFun {
	private static Logger logger = Logger.getLogger(PubFun.class);

	public PubFun() {
	}

	/**
	 * 判断是否为闰年 XinYQ added on 2006-09-25
	 */
	public static boolean isLeapYear(int nYear) {
		boolean ResultLeap = false;
		ResultLeap = (nYear % 400 == 0) | (nYear % 100 != 0) & (nYear % 4 == 0);
		return ResultLeap;
	}

	/**
	 * 计算日期的函数 author: HST 参照日期指当按照年月进行日期的计算的时候，参考的日期，如下例，结果返回2002-03-31
	 * <p>
	 * <b>Example: </b>
	 * <p>
	 * <p>
	 * FDate tD=new FDate();
	 * <p>
	 * <p>
	 * Date baseDate =new Date();
	 * <p>
	 * <p>
	 * baseDate=tD.getDate("2000-02-29");
	 * <p>
	 * <p>
	 * Date comDate =new Date();
	 * <p>
	 * <p>
	 * comDate=tD.getDate("1999-12-31");
	 * <p>
	 * <p>
	 * int inteval=1;
	 * <p>
	 * <p>
	 * String tUnit="M";
	 * <p>
	 * <p>
	 * Date tDate =new Date();
	 * <p>
	 * <p>
	 * tDate=PubFun.calDate(baseDate,inteval,tUnit,comDate);
	 * <p>
	 * <p>
	 * logger.debug(tDate.toString());
	 * <p>
	 * 
	 * @param baseDate
	 *            起始日期
	 * @param interval
	 *            时间间隔
	 * @param unit
	 *            时间间隔单位
	 * @param compareDate
	 *            参照日期
	 * @return Date类型变量
	 */
	public static Date calDate(Date baseDate, int interval, String unit,
			Date compareDate) {
		Date returnDate = null;

		GregorianCalendar tBaseCalendar = new GregorianCalendar();
		tBaseCalendar.setTime(baseDate);

		if (unit.equals("Y")) {
			tBaseCalendar.add(Calendar.YEAR, interval);
		}
		if (unit.equals("M")) {
			tBaseCalendar.add(Calendar.MONTH, interval);
		}
		if (unit.equals("D")) {
			tBaseCalendar.add(Calendar.DATE, interval);
		}

		if (compareDate != null) {
			GregorianCalendar tCompCalendar = new GregorianCalendar();
			tCompCalendar.setTime(compareDate);
			int nBaseYears = tBaseCalendar.get(Calendar.YEAR);
			int nBaseMonths = tBaseCalendar.get(Calendar.MONTH);
			int nCompMonths = tCompCalendar.get(Calendar.MONTH);
			int nCompDays = tCompCalendar.get(Calendar.DATE);
			int oldDays = tCompCalendar.get(Calendar.DATE);

			if (unit.equals("Y")) {
				/*
				 * 目前来看，compareDate非空的情况下unit一定是Y，这样确保新日期的月和日是不变的，但是此时还需要考虑一种情况，
				 * 如果旧日期是闰年2-29，例如2008-2-29，而新的年份为非闰年，例如2038，拼在一起为2038-2-29，对于此种数据，
				 * tCompCalendar.set(nBaseYears, nCompMonths,
				 * nCompDays)将会自动将其置为2038-3-1，为了避免出现此问题， 我们需要特殊处理nCompDays
				 */
				// 特殊处理1，如果旧日期为润年2-29，新年份nBaseYears为非闰年，日nCompDays只能为28
				if (1 == nCompMonths && 29 == oldDays
						&& !isLeapYear(nBaseYears)) {
					nCompDays = 28;
				}
				tCompCalendar.set(nBaseYears, nCompMonths, nCompDays);
				if (tCompCalendar.before(tBaseCalendar)) {
					// 特殊处理2，如果旧日期为润年2-29，新年份nBaseYears+1为非闰年，日nCompDays只能为28
					if (1 == nCompMonths && 29 == oldDays
							&& !isLeapYear(nBaseYears + 1)) {
						nCompDays = 28;
					}
					// 特殊处理3，如果旧日期为润年2-29，新年份nBaseYears为非闰年，日nCompDays在特殊处理1中改为28
					// 但是如果nBaseYears+1为闰年，需要还原nCompDays，实际上就是29。
					if (1 == nCompMonths && 29 == oldDays
							&& isLeapYear(nBaseYears + 1)) {
						nCompDays = oldDays;
					}
					tBaseCalendar.set(nBaseYears + 1, nCompMonths, nCompDays);
					returnDate = tBaseCalendar.getTime();
				} else {
					returnDate = tCompCalendar.getTime();
				}
			}
			if (unit.equals("M")) {
				tCompCalendar.set(nBaseYears, nBaseMonths, nCompDays);
				if (tCompCalendar.before(tBaseCalendar)) {
					tBaseCalendar.set(nBaseYears, nBaseMonths + 1, nCompDays);
					returnDate = tBaseCalendar.getTime();
				} else {
					returnDate = tCompCalendar.getTime();
				}
			}
			if (unit.equals("D")) {
				returnDate = tBaseCalendar.getTime();
			}
			tCompCalendar = null;
		} else {
			returnDate = tBaseCalendar.getTime();

			// XinYQ added on 2006-09-25 : 修正闰年闰月和月底天数,和Oracle保持一致
			// GregorianCalendar tLeapCalendar = new GregorianCalendar();
			// tLeapCalendar.setTime(returnDate);
			// int arrComnYearEndDate[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30,
			// 31, 30, 31 };
			// int arrLeapYearEndDate[] = { 31, 29, 31, 30, 31, 30, 31, 31, 30,
			// 31, 30, 31 };
			// int nOldYear = 1900 + baseDate.getYear();
			// int nOldMonth = baseDate.getMonth();
			// int nOldDate = baseDate.getDate();
			// int nNewYear = tLeapCalendar.get(Calendar.YEAR);
			// int nNewMonth = tLeapCalendar.get(Calendar.MONTH);
			//
			// if ((isLeapYear(nOldYear) && nOldDate ==
			// arrLeapYearEndDate[nOldMonth])
			// || (!isLeapYear(nOldYear) && nOldDate ==
			// arrComnYearEndDate[nOldMonth])) {
			// if (unit != null
			// && (unit.equalsIgnoreCase("Y") || unit
			// .equalsIgnoreCase("M"))) {
			// if (isLeapYear(nNewYear)) {
			// returnDate.setDate(arrLeapYearEndDate[nNewMonth]);
			// } else {
			// returnDate.setDate(arrComnYearEndDate[nNewMonth]);
			// }
			// }
			// }
			// tLeapCalendar = null;
		}
		tBaseCalendar = null;

		return returnDate;
	}

	/*
	 * 新增续保lcduty.enddate,lcprem.payenddate特殊算法 续期年交件或者续保件算催收算交至日期也可能需要类似操作
	 * 当需要将保单交至日期由2-28变回2-29时调用此方法 @param baseDate 起始日期 @param interval 时间间隔
	 * @param unit 时间间隔单位 @param compareDate 参照日期,此处肯定为空 @return Date类型变量
	 */
	public static Date calxbEndDate(Date baseDate, int interval, String unit,
			Date compareDate) {
		Date returnDate = null;

		GregorianCalendar tBaseCalendar = new GregorianCalendar();
		tBaseCalendar.setTime(baseDate);

		if (unit.equals("Y")) {
			tBaseCalendar.add(Calendar.YEAR, interval);
		}
		if (unit.equals("M")) {
			tBaseCalendar.add(Calendar.MONTH, interval);
		}
		if (unit.equals("D")) {
			tBaseCalendar.add(Calendar.DATE, interval);
		}

		returnDate = tBaseCalendar.getTime();

		// XinYQ added on 2006-09-25 : 修正闰年闰月和月底天数,和Oracle保持一致
		GregorianCalendar tLeapCalendar = new GregorianCalendar();
		tLeapCalendar.setTime(returnDate);
		int arrComnYearEndDate[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
				30, 31 };
		int arrLeapYearEndDate[] = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31,
				30, 31 };
		int nOldYear = 1900 + baseDate.getYear();
		int nOldMonth = baseDate.getMonth();
		int nOldDate = baseDate.getDate();
		int nNewYear = tLeapCalendar.get(Calendar.YEAR);
		int nNewMonth = tLeapCalendar.get(Calendar.MONTH);

		if ((isLeapYear(nOldYear) && nOldDate == arrLeapYearEndDate[nOldMonth])
				|| (!isLeapYear(nOldYear) && nOldDate == arrComnYearEndDate[nOldMonth])) {
			if (unit != null
					&& (unit.equalsIgnoreCase("Y") || unit
							.equalsIgnoreCase("M"))) {
				if (isLeapYear(nNewYear)) {
					returnDate.setDate(arrLeapYearEndDate[nNewMonth]);
				} else {
					returnDate.setDate(arrComnYearEndDate[nNewMonth]);
				}
			}
		}
		tLeapCalendar = null;
		tBaseCalendar = null;

		return returnDate;
	}

	/**
	 * 通过起始日期和终止日期计算以时间间隔单位为计量标准的时间间隔，忽略闰年
	 * <p>
	 * <b>Example: </b>
	 * <p>
	 * <p>
	 * calInterval3("2004-2-29", "2006-2-28", "Y") returns
	 * 1，calInterval3("2004-2-29", "2006-3-1", "Y") returns 2
	 * <p>
	 * 
	 * @param startDate
	 *            起始日期，(String,格式："YYYY-MM-DD")
	 * @param endDate
	 *            终止日期，(String,格式："YYYY-MM-DD")
	 * @param unit
	 *            时间间隔单位，可用值("Y"--年 "M"--月 "D"--日)
	 * @return 时间间隔,整形变量int
	 */
	public static int calInterval3(String cstartDate, String cendDate,
			String unit) {
		FDate fDate = new FDate();
		Date startDate = fDate.getDate(cstartDate);
		Date endDate = fDate.getDate(cendDate);
		if (fDate.mErrors.needDealError())
			return 0;

		int interval = 0;

		GregorianCalendar sCalendar = new GregorianCalendar();
		sCalendar.setTime(startDate);
		int sYears = sCalendar.get(Calendar.YEAR);
		int sMonths = sCalendar.get(Calendar.MONTH);
		int sDays = sCalendar.get(Calendar.DAY_OF_MONTH);
		int sDaysOfYear = sCalendar.get(Calendar.DAY_OF_YEAR);

		GregorianCalendar eCalendar = new GregorianCalendar();
		eCalendar.setTime(endDate);
		int eYears = eCalendar.get(Calendar.YEAR);
		int eMonths = eCalendar.get(Calendar.MONTH);
		int eDays = eCalendar.get(Calendar.DAY_OF_MONTH);
		int eDaysOfYear = eCalendar.get(Calendar.DAY_OF_YEAR);

		if (StrTool.cTrim(unit).equals("Y")) {
			interval = eYears - sYears;

			if (eMonths < sMonths) {
				interval--;
			} else {
				if (eMonths == sMonths && eDays < sDays) {
					interval--; // 不考虑有闰年的情况
				}
			}
		}
		if (StrTool.cTrim(unit).equals("M")) {
			interval = eYears - sYears;
			interval = interval * 12;
			interval = eMonths - sMonths + interval;

			if (eDays < sDays) {
				interval--;
				// eDays如果是月末，则认为是满一个月
				int maxDate = eCalendar.getActualMaximum(Calendar.DATE);
				if (eDays == maxDate) {
					interval++;
				}
			}
		}
		if (StrTool.cTrim(unit).equals("D")) {
			interval = eYears - sYears;
			interval = interval * 365;

			interval = eDaysOfYear - sDaysOfYear + interval;

			// 处理润年
			int n = 0;
			eYears--;// 不用考虑终止年的闰年问题
			int i = sYears % 4;
			sYears -= (i == 0) ? 4 : i;
			n += (eYears - sYears) / 4;// 取得指定时间间隔内闰年个数
			// end modify

			interval += n;
		}
		return interval;
	}

	/**
	 * 通过起始日期和终止日期计算以时间间隔单位为计量标准的时间间隔，忽略闰年
	 * <p>
	 * <b>Example:calInterval3("2004-2-29", "2006-2-28", "Y") returns
	 * 1，calInterval("2004-2-29", "2006-3-1", "Y") returns 2 </b>
	 * <p>
	 * <p>
	 * 参照calInterval3(String cstartDate, String cendDate, String
	 * unit)，前两个变量改为日期型即可
	 * <p>
	 * 
	 * @param startDate
	 *            起始日期，Date变量
	 * @param endDate
	 *            终止日期，Date变量
	 * @param unit
	 *            时间间隔单位，可用值("Y"--年 "M"--月 "D"--日)
	 * @return 时间间隔,整形变量int
	 */
	public static int calInterval3(Date startDate, Date endDate, String unit) {
		int interval = 0;

		GregorianCalendar sCalendar = new GregorianCalendar();
		sCalendar.setTime(startDate);
		int sYears = sCalendar.get(Calendar.YEAR);
		int sMonths = sCalendar.get(Calendar.MONTH);
		int sDays = sCalendar.get(Calendar.DAY_OF_MONTH);
		int sDaysOfYear = sCalendar.get(Calendar.DAY_OF_YEAR);

		GregorianCalendar eCalendar = new GregorianCalendar();
		eCalendar.setTime(endDate);
		int eYears = eCalendar.get(Calendar.YEAR);
		int eMonths = eCalendar.get(Calendar.MONTH);
		int eDays = eCalendar.get(Calendar.DAY_OF_MONTH);
		int eDaysOfYear = eCalendar.get(Calendar.DAY_OF_YEAR);

		if (unit.equals("Y")) {
			interval = eYears - sYears;
			if (eMonths < sMonths) {
				interval--;
			} else {
				if (eMonths == sMonths && eDays < sDays) {
					interval--;
				}
			}
		}
		if (unit.equals("M")) {
			interval = eYears - sYears;
			interval = interval * 12;

			interval = eMonths - sMonths + interval;
			if (eDays < sDays) {
				interval--;
				// eDays如果是月末，则认为是满一个月
				int maxDate = eCalendar.getActualMaximum(Calendar.DATE);
				if (eDays == maxDate) {
					interval++;
				}
			}
		}
		if (unit.equals("D")) {
			interval = eYears - sYears;
			interval = interval * 365;
			interval = eDaysOfYear - sDaysOfYear + interval;

			// 处理润年
			int n = 0;
			eYears--;
			if (eYears > sYears) {
				int i = sYears % 4;
				if (i == 0) {
					sYears++;
					n++;
				}
				int j = (eYears) % 4;
				if (j == 0) {
					eYears--;
					n++;
				}
				n += (eYears - sYears) / 4;
			}
			if (eYears == sYears) {
				int i = sYears % 4;
				if (i == 0)
					n++;
			}
			interval += n;
		}
		return interval;
	}

	/**
	 * 重载计算日期，参数见楼上，add by Minim
	 * 
	 * @param baseDate
	 *            String
	 * @param interval
	 *            int
	 * @param unit
	 *            String
	 * @param compareDate
	 *            String
	 * @return String
	 */
	public static String calDate(String baseDate, int interval, String unit,
			String compareDate) {
		try {
			FDate tFDate = new FDate();
			Date bDate = tFDate.getDate(baseDate);
			Date cDate = tFDate.getDate(compareDate);
			return tFDate.getString(calDate(bDate, interval, unit, cDate));
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/*
	 * comment by jiaqiangli 2008-11-03 calOFDate两个方法请用FinFeePubFun对应的方法
	 * 为使程序调用的统一性与一致性 PubFun的这两个方法不再使用
	 */
	// public static String calOFDate(String baseDate, int interval, String
	// unit,
	// String compareDate) {
	// try {
	// FDate tFDate = new FDate();
	// Date bDate = tFDate.getDate(baseDate);
	// Date cDate = tFDate.getDate(compareDate);
	// return tFDate.getString(calOFDate(bDate, interval, unit, cDate));
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// return null;
	// }
	// }
	// public static Date calOFDate(Date baseDate, int interval, String unit,
	// Date compareDate) {
	// Date returnDate = null;
	//
	// GregorianCalendar mCalendar = new GregorianCalendar();
	// // 设置起始日期格式
	// mCalendar.setTime(baseDate);
	// if (unit.equals("Y")) {
	// mCalendar.add(Calendar.YEAR, interval);
	// }
	// if (unit.equals("M")) {
	// // 执行月份增减
	// mCalendar.add(Calendar.MONTH, interval);
	// }
	// if (unit.equals("D")) {
	// mCalendar.add(Calendar.DATE, interval);
	// }
	//
	// if (compareDate != null) {
	// GregorianCalendar cCalendar = new GregorianCalendar();
	// // 设置坐标日期
	// cCalendar.setTime(compareDate);
	//
	// int mYears = mCalendar.get(Calendar.YEAR);
	// int mMonths = mCalendar.get(Calendar.MONTH);
	// int cMonths = cCalendar.get(Calendar.MONTH);
	// int cDays = cCalendar.get(Calendar.DATE);
	//
	// if (unit.equals("Y")) {
	// cCalendar.set(mYears, cMonths, cDays);
	// if (mMonths < cCalendar.get(Calendar.MONTH)) {
	// cCalendar.set(mYears, mMonths + 1, 0);
	// }
	// if (cCalendar.before(mCalendar)) {
	// mCalendar.set(mYears + 1, cMonths, cDays);
	// returnDate = mCalendar.getTime();
	// } else {
	// returnDate = cCalendar.getTime();
	// }
	// }
	// if (unit.equals("M")) {
	// cCalendar.set(mYears, mMonths, cDays);
	//
	// if (mMonths < cCalendar.get(Calendar.MONTH)) {
	// // 取当前月的最后一天日期
	// cCalendar.set(mYears, mMonths + 1, 0);
	// }
	// if (cCalendar.before(mCalendar)) {
	// mCalendar.set(mYears, mMonths + 1, cDays);
	// returnDate = mCalendar.getTime();
	// } else {
	// returnDate = cCalendar.getTime();
	// }
	// }
	// if (unit.equals("D")) {
	// returnDate = mCalendar.getTime();
	// }
	// } else {
	// returnDate = mCalendar.getTime();
	// }
	//
	// return returnDate;
	// }
	/**
	 * 生成付费方式，add by GaoHT
	 * 
	 * @param SSRS
	 *            nSSRS
	 * @return String 按收费方式生成该笔退费的退费方式
	 */

	public static String PayModeTransform(SSRS nSSRS) {
		try {
			int Cash = 0; // 现金
			int Cheque = 0; // 支票
			int Bank = 0; // 银行划款
			int Pos = 0; // Pos收款
			String tOutPayMode = "";
			for (int i = 1; i <= nSSRS.getMaxRow(); i++) {
				String tPayMode = nSSRS.GetText(i, 1);
				if (tPayMode.equals("3") || tPayMode.equals("4")) {
					Cheque++;
				} else if (tPayMode.equals("6")) {
					Pos++;
				} else if (tPayMode.equals("7")) {
					Bank++;
				} else {
					Cash++;
				}
			}
			logger.debug("临时付费方式中 现金:::::::::（" + Cash + "） 笔");
			logger.debug("临时付费方式中 支票:::::::::（" + Cheque + "） 笔");
			logger.debug("临时付费方式中 POS:::::::::（" + Pos + "） 笔");
			logger.debug("临时付费方式中 代收:::::::::（" + Bank + "） 笔");
			logger
					.debug("---------------------根据优先级决定付费方式--------------------");

			if (Cheque > 0) // 支票优先级最高
			{
				tOutPayMode = "3";
			} else if (Bank > 0) // 银行代付其次
			{
				tOutPayMode = "4";
			} else if (Cash > 0) // 其他为现金
			{
				tOutPayMode = "1";
			} else if (Pos > 0) // 只有一个pos
			{
				tOutPayMode = "6";
			}

			return tOutPayMode;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 功能:得到某个月的最后一天 日期格式：yyyyMMdd 指定年月最大天数
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 最大天数
	 */

	public static int getActualMaximum(int year, int month) {
		java.util.GregorianCalendar date = new java.util.GregorianCalendar(
				year, month - 1, 1);
		return (date.getActualMaximum(Calendar.DATE));
	}

	/**
	 * 校验字符串必须是数字
	 * 
	 * @param: String
	 * @return: boolean
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern
				.compile("([\\+\\-])?([0-9])+(.[0-9])?([0-9])*");
		Matcher isNum = pattern.matcher(str);

		if (!isNum.matches()) {
			return false;
		}

		return true;
	}

	/**
	 * 功能:得到某时间单位,在某个时间段内往后或往前推某个时间段的日期 日期格式：yyyy--MM--dd unit:Y,M,D
	 * acount:当为正数时,则计算的是需要往后推的时间数,当为负数时,则计算的是需要往前推的时间数,
	 * 但是往后推的天数是不包括baseDate的,比如传入的("2008-05-01","D",10),则会得到"2008-05-11",即在0501的基础上增加了10天,
	 * 如果需要要包括baseDate,则需要将baseDate-1再传入,即要得到2008-05-10,则传入的是(PubFun.newCalDate(PubFun.getCurrentDate(),"D",-1),"D",10),先八baseDate往后推一天
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 最大天数
	 */
	public static String newCalDate(String baseDate, String unit, int aconunt) {
		Calendar c = Calendar.getInstance();// 获得一个日历的实例
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(baseDate);// 初始日期
		} catch (Exception e) {
			logger.debug("得到指定日期后推多少天出现异常,异常信息:" + e.toString());
		}
		c.setTime(date);// 设置日历时间

		// 在日历的日期按照给定的日期单位增加一个间隔;
		if (unit.equals("Y"))
			c.add(Calendar.YEAR, aconunt);
		else if (unit.equals("M"))
			c.add(Calendar.MONTH, aconunt);
		else if (unit.equals("D"))
			c.add(Calendar.DATE, aconunt);
		else
			c.add(Calendar.DATE, aconunt);// 如果传错单位,默认日期单位是天

		logger.debug(sdf.format(c.getTime()));

		return sdf.format(c.getTime());
	}

	/**
	 * 比较两个日期的大小
	 * 
	 * @param if
	 *            StartDate>EndDate,return fasle else return true
	 * @param compareDate
	 * @return
	 */
	public static boolean checkDate(String StartDate, String EndDate) {
		String flag = "";
		FDate tFDate = new FDate();
		Date tStateDate = tFDate.getDate(StartDate);
		Date tEndDate = tFDate.getDate(EndDate);

		if (tStateDate.after(tEndDate)) {
			flag = "0";
			logger.debug("开始日期晚于结束日期！！！！");

			return false;
		} else {
			flag = "1";
			logger.debug("开始日期早于结束日期！！！");

			return true;
		}
	}

	/**
	 * 比较两个具体日期+具体时间的大小,YY-MM-DD HH-
	 * 
	 * @param date1
	 * @param date2
	 * @if date1<date2 return true
	 * @else return false author zz
	 */
	public static boolean isDateBefore(String date1, String date2)
			throws java.text.ParseException {

		DateFormat df = DateFormat.getDateTimeInstance();
		// 如果只是时间的比较，这里应该是DateFormat.getTimeInstance();
		// 只是日期的比较则是getDateInstance();
		return df.parse(date1).before(df.parse(date2));

	}

	/**
	 * 通过起始日期和终止日期计算以时间间隔单位为计量标准的时间间隔 author: HST
	 * <p>
	 * <b>Example: </b>
	 * <p>
	 * <p>
	 * 参照calInterval(String cstartDate, String cendDate, String
	 * unit)，前两个变量改为日期型即可
	 * <p>
	 * 
	 * @param startDate
	 *            起始日期，Date变量
	 * @param endDate
	 *            终止日期，Date变量
	 * @param unit
	 *            时间间隔单位，可用值("Y"--年 "M"--月 "D"--日)
	 * @return 时间间隔,整形变量int
	 */
	public static int calInterval(Date startDate, Date endDate, String unit) {
		int interval = 0;

		GregorianCalendar sCalendar = new GregorianCalendar();
		sCalendar.setTime(startDate);
		int sYears = sCalendar.get(Calendar.YEAR);
		int sMonths = sCalendar.get(Calendar.MONTH);
		int sDays = sCalendar.get(Calendar.DAY_OF_MONTH);

		GregorianCalendar eCalendar = new GregorianCalendar();
		eCalendar.setTime(endDate);
		int eYears = eCalendar.get(Calendar.YEAR);
		int eMonths = eCalendar.get(Calendar.MONTH);
		int eDays = eCalendar.get(Calendar.DAY_OF_MONTH);

		if (unit.equals("Y")) {
			interval = eYears - sYears;
			if (eMonths < sMonths) {
				interval--;
			} else {
				if (eMonths == sMonths && eDays < sDays) {
					interval--;
					if (eMonths == 1) { // 如果同是2月，校验润年问题
						if ((sYears % 4) == 0 && (eYears % 4) != 0) { // 如果起始年是润年，终止年不是润年
							if (eDays == 28) { // 如果终止年不是润年，且2月的最后一天28日，那么补一
								interval++;
							}
						}
					}
				}
			}
		}
		if (unit.equals("M")) {
			interval = eYears - sYears;
			// interval = interval * 12;
			interval *= 12;

			// interval = eMonths - sMonths + interval;
			interval += eMonths - sMonths;
			if (eDays < sDays) {
				interval--;
				// eDays如果是月末，则认为是满一个月
				int maxDate = eCalendar.getActualMaximum(Calendar.DATE);
				if (eDays == maxDate) {
					interval++;
				}
			}
		}
		if (unit.equals("D")) {
			// ====del===liuxs===2006-09-09=====修改日期间隔计算漏洞================BGN=========
			// interval = eYears - sYears;
			// // interval = interval * 365;
			// interval *= 365;
			// // interval = eDaysOfYear - sDaysOfYear + interval;
			// interval += eDaysOfYear - sDaysOfYear;
			//
			// // 处理润年
			// int n = 0;
			// eYears--;
			// if (eYears > sYears)
			// {
			// int i = sYears % 4;
			// if (i == 0)
			// {
			// sYears++;
			// n++;
			// }
			// int j = (eYears) % 4;
			// if (j == 0)
			// {
			// eYears--;
			// n++;
			// }
			// n += (eYears - sYears) / 4;
			// }
			// if (eYears == sYears)
			// {
			// int i = sYears % 4;
			// if (i == 0)
			// {
			// n++;
			// }
			// }
			// interval += n;
			// ====del===liuxs===2006-09-09=====修改日期间隔计算漏洞================END=========
			// ====add===liuxs===2006-09-09=====修改日期间隔计算漏洞================BGN=========
			sCalendar.set(sYears, sMonths, sDays);
			eCalendar.set(eYears, eMonths, eDays);
			long lInterval = (eCalendar.getTime().getTime() - sCalendar
					.getTime().getTime()) / 86400000;
			interval = (int) lInterval;
			// ====add===liuxs===2006-09-09=====修改日期间隔计算漏洞================END=========
		}
		return interval;
	}

	/**
	 * 通过起始日期和终止日期计算以时间间隔单位为计量标准的时间间隔，舍弃法 author: HST
	 * 起始日期，(String,格式："YYYY-MM-DD")
	 * 
	 * @param cstartDate
	 *            String 终止日期，(String,格式："YYYY-MM-DD")
	 * @param cendDate
	 *            String 时间间隔单位，可用值("Y"--年 "M"--月 "D"--日)
	 * @param unit
	 *            String 时间间隔,整形变量int
	 * @return int
	 */
	public static int calInterval(String cstartDate, String cendDate,
			String unit) {
		FDate fDate = new FDate();
		Date startDate = fDate.getDate(cstartDate);
		Date endDate = fDate.getDate(cendDate);
		if (fDate.mErrors.needDealError()) {
			return 0;
		}

		int interval = 0;

		GregorianCalendar sCalendar = new GregorianCalendar();
		sCalendar.setTime(startDate);
		int sYears = sCalendar.get(Calendar.YEAR);
		int sMonths = sCalendar.get(Calendar.MONTH);
		int sDays = sCalendar.get(Calendar.DAY_OF_MONTH);

		GregorianCalendar eCalendar = new GregorianCalendar();
		eCalendar.setTime(endDate);
		int eYears = eCalendar.get(Calendar.YEAR);
		int eMonths = eCalendar.get(Calendar.MONTH);
		int eDays = eCalendar.get(Calendar.DAY_OF_MONTH);

		if (StrTool.cTrim(unit).equals("Y")) {
			interval = eYears - sYears;

			if (eMonths < sMonths) {
				interval--;
			} else {
				if (eMonths == sMonths && eDays < sDays) {
					interval--;
					if (eMonths == 1) { // 如果同是2月，校验润年问题
						if ((sYears % 4) == 0 && (eYears % 4) != 0) { // 如果起始年是润年，终止年不是润年
							if (eDays == 28) { // 如果终止年不是润年，且2月的最后一天28日，那么补一
								interval++;
							}
						}
					}
				}
			}
		}
		if (StrTool.cTrim(unit).equals("M")) {
			interval = eYears - sYears;
			// interval = interval * 12;
			interval *= 12;
			// interval = eMonths - sMonths + interval;
			interval += eMonths - sMonths;

			if (eDays < sDays) {
				interval--;
				// eDays如果是月末，则认为是满一个月
				int maxDate = eCalendar.getActualMaximum(Calendar.DATE);
				if (eDays == maxDate) {
					interval++;
				}
			}
		}
		if (StrTool.cTrim(unit).equals("D")) {
			// ====del===liuxs===2006-09-09=====修改日期间隔计算漏洞================BGN=========
			// interval = eYears - sYears;
			// // interval = interval * 365;
			// interval *= 365;
			// // interval = eDaysOfYear - sDaysOfYear + interval;
			// interval += eDaysOfYear - sDaysOfYear;
			//
			// // 处理润年
			// int n = 0;
			// eYears--;
			// if (eYears > sYears)
			// {
			// int i = sYears % 4;
			// if (i == 0)
			// {
			// sYears++;
			// n++;
			// }
			// int j = (eYears) % 4;
			// if (j == 0)
			// {
			// eYears--;
			// n++;
			// }
			// n += (eYears - sYears) / 4;
			// }
			// if (eYears == sYears)
			// {
			// int i = sYears % 4;
			// if (i == 0)
			// {
			// n++;
			// }
			// }
			// interval += n;
			// ====del===liuxs===2006-09-09=====修改日期间隔计算漏洞================END=========
			// ====add===liuxs===2006-09-09=====修改日期间隔计算漏洞================BGN=========
			sCalendar.set(sYears, sMonths, sDays);
			eCalendar.set(eYears, eMonths, eDays);
			long lInterval = (eCalendar.getTime().getTime() - sCalendar
					.getTime().getTime()) / 86400000;
			interval = (int) lInterval;
			// ====add===liuxs===2006-09-09=====修改日期间隔计算漏洞================END=========

		}
		return interval;
	}

	/**
	 * 通过起始日期和终止日期计算以时间间隔单位为计量标准的时间间隔，约进法 author: YangZhao，Minim
	 * 起始日期，(String,格式："YYYY-MM-DD")
	 * 
	 * @param cstartDate
	 *            String 终止日期，(String,格式："YYYY-MM-DD")
	 * @param cendDate
	 *            String 时间间隔单位，可用值("Y"--年 "M"--月 "D"--日)
	 * @param unit
	 *            String 时间间隔,整形变量int
	 * @return int
	 */
	public static int calInterval2(String cstartDate, String cendDate,
			String unit) {
		FDate fDate = new FDate();
		Date startDate = fDate.getDate(cstartDate);
		Date endDate = fDate.getDate(cendDate);
		if (fDate.mErrors.needDealError()) {
			return 0;
		}

		int interval = 0;

		GregorianCalendar sCalendar = new GregorianCalendar();
		sCalendar.setTime(startDate);
		int sYears = sCalendar.get(Calendar.YEAR);
		int sMonths = sCalendar.get(Calendar.MONTH);
		int sDays = sCalendar.get(Calendar.DAY_OF_MONTH);

		GregorianCalendar eCalendar = new GregorianCalendar();
		eCalendar.setTime(endDate);
		int eYears = eCalendar.get(Calendar.YEAR);
		int eMonths = eCalendar.get(Calendar.MONTH);
		int eDays = eCalendar.get(Calendar.DAY_OF_MONTH);

		if (StrTool.cTrim(unit).equals("Y")) {
			interval = eYears - sYears;

			if (eMonths > sMonths) {
				interval++;
			} else {
				if (eMonths == sMonths && eDays > sDays) {
					interval++;
					if (eMonths == 1) { // 如果同是2月，校验润年问题
						if ((sYears % 4) == 0 && (eYears % 4) != 0) { // 如果起始年是润年，终止年不是润年
							if (eDays == 28) { // 如果终止年不是润年，且2月的最后一天28日，那么减一
								interval--;
							}
						}
					}
				}
			}
		}
		if (StrTool.cTrim(unit).equals("M")) {
			interval = eYears - sYears;
			// interval = interval * 12;
			interval *= 12;
			// interval = eMonths - sMonths + interval;
			interval += eMonths - sMonths;

			if (eDays > sDays) {
				interval++;
				// sDays 和 eDays如果均是月末，则认为是满一个月 //modified by zhangrong
				int maxsDate = sCalendar.getActualMaximum(Calendar.DATE);
				int maxeDate = eCalendar.getActualMaximum(Calendar.DATE);
				if (sDays == maxsDate && eDays == maxeDate) {
					interval--;
				}
			}
		}
		if (StrTool.cTrim(unit).equals("D")) {
			// ====del===liuxs===2006-09-09=====修改日期间隔计算漏洞================BGN=========
			// interval = eYears - sYears;
			// // interval = interval * 365;
			// interval *= 365;
			// // interval = eDaysOfYear - sDaysOfYear + interval;
			// interval += eDaysOfYear - sDaysOfYear;
			//
			// // 处理润年
			// int n = 0;
			// eYears--;
			// if (eYears > sYears)
			// {
			// int i = sYears % 4;
			// if (i == 0)
			// {
			// sYears++;
			// n++;
			// }
			// int j = (eYears) % 4;
			// if (j == 0)
			// {
			// eYears--;
			// n++;
			// }
			// n += (eYears - sYears) / 4;
			// }
			// if (eYears == sYears)
			// {
			// int i = sYears % 4;
			// if (i == 0)
			// {
			// n++;
			// }
			// }
			// interval += n;
			// ====del===liuxs===2006-09-09=====修改日期间隔计算漏洞================END=========
			// ====add===liuxs===2006-09-09=====修改日期间隔计算漏洞================BGN=========
			sCalendar.set(sYears, sMonths, sDays);
			eCalendar.set(eYears, eMonths, eDays);
			long lInterval = (eCalendar.getTime().getTime() - sCalendar
					.getTime().getTime()) / 86400000; // 24*60*60*1000
			// 一天对应的毫秒数
			interval = (int) lInterval;
			// ====add===liuxs===2006-09-09=====修改日期间隔计算漏洞================END=========
		}
		return interval;
	}

	/**
	 * 通过传入的日期可以得到所在月的第一天和最后一天的日期 author: LH 日期，(String,格式："YYYY-MM-DD")
	 * 
	 * @param tDate
	 *            String 本月开始和结束日期，返回String[2]
	 * @return String[]
	 */
	public static String[] calFLDate(String tDate) {
		String MonDate[] = new String[2];
		FDate fDate = new FDate();
		Date CurDate = fDate.getDate(tDate);
		GregorianCalendar mCalendar = new GregorianCalendar();
		mCalendar.setTime(CurDate);
		int Years = mCalendar.get(Calendar.YEAR);
		int Months = mCalendar.get(Calendar.MONTH);
		int FirstDay = mCalendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		int LastDay = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		mCalendar.set(Years, Months, FirstDay);
		MonDate[0] = fDate.getString(mCalendar.getTime());
		mCalendar.set(Years, Months, LastDay);
		MonDate[1] = fDate.getString(mCalendar.getTime());
		return MonDate;
	}

	/**
	 * 通过传入的日期可以得到宽限期止期(包括延长宽限期) 起始日期，(String,格式："YYYY-MM-DD")
	 * 
	 * @param startDate
	 *            String
	 * @param strRiskCode
	 *            String
	 * @return String
	 */
	public static String calLapseDate(String startDate, String strRiskCode) {
		String returnDate = "";
		// Date tLapseDate = null;
		FDate tFDate = new FDate();
		int nDates;
		int nExtendLapseDates;

		// 检查输入数据完整性
		if ((startDate == null) || startDate.trim().equals("")) {
			logger.debug("没有起始日期,计算宽限止期失败!");
			return returnDate;
		}

		// 获取险种交费失效描述
		LMRiskPayDB tLMRiskPayDB = new LMRiskPayDB();
		tLMRiskPayDB.setRiskCode(strRiskCode);
		LMRiskPaySet tLMRiskPaySet = tLMRiskPayDB.query();

		if (tLMRiskPaySet.size() > 0) {
			if ((tLMRiskPaySet.get(1).getGracePeriodUnit() == null)
					|| tLMRiskPaySet.get(1).getGracePeriodUnit().equals("")) {
				// 设置宽限期为默认值
				logger.debug("缺少险种交费失效描述!按默认值计算");
				nDates = 60;
				returnDate = calDate(startDate, nDates, "D", null);
			} else {
				// 取得指定宽限期
				nDates = tLMRiskPaySet.get(1).getGracePeriod();
				returnDate = calDate(startDate, nDates, tLMRiskPaySet.get(1)
						.getGracePeriodUnit(), null);
				// jdk1.4自带的方法，根据－拆分字符串到数组
				// String[] tDate = returnDate.split("-");
				// 按月进位，舍弃日精度
				// 增加空值判断 2005-8-5 周磊
				if (tLMRiskPaySet.get(1).getGraceDateCalMode() != null) {
					if (tLMRiskPaySet.get(1).getGraceDateCalMode().equals("1")) {
						// tLapseDate = tFDate.getDate(returnDate);
						// tLapseDate.setMonth(tLapseDate.getMonth() + 1);
						// tLapseDate.setDate(1);
						// returnDate = tFDate.getString(tLapseDate);

						// 对日期的操作，最好使用Calendar方法
						GregorianCalendar tCalendar = new GregorianCalendar();
						tCalendar.setTime(tFDate.getDate(returnDate));
						// 月份进位，舍弃日精度
						tCalendar.set(tCalendar.get(Calendar.YEAR), tCalendar
								.get(Calendar.MONTH) + 1, 1);
						returnDate = tFDate.getString(tCalendar.getTime());
					}

					// 按年进位，只舍弃了日精度，不舍弃月精度
					if (tLMRiskPaySet.get(1).getGraceDateCalMode().equals("2")) {
						// tLapseDate = tFDate.getDate(returnDate);
						// tLapseDate.setYear(tLapseDate.getYear() + 1);
						// tLapseDate.setDate(1);
						// returnDate = tFDate.getString(tLapseDate);

						// 对日期的操作，最好使用Calendar方法
						GregorianCalendar tCalendar = new GregorianCalendar();
						tCalendar.setTime(tFDate.getDate(returnDate));
						// 年份进位，舍弃日精度，不舍弃月精度
						tCalendar.set(tCalendar.get(Calendar.YEAR) + 1,
								tCalendar.get(Calendar.MONTH), 1);
						returnDate = tFDate.getString(tCalendar.getTime());
					}
				}
			}
		} else {
			// 设置宽限期为默认值
			logger.debug("没有险种交费失效描述!按默认值计算");
			nDates = 60;
			returnDate = calDate(startDate, nDates, "D", null);
		}

		// 取得宽限期延长期
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("ExtendLapseDates");
		if (!tLDSysVarDB.getInfo()) {
			nExtendLapseDates = 0;
		} else {
			nExtendLapseDates = Integer.parseInt(tLDSysVarDB.getSchema()
					.getSysVarValue());
			returnDate = calDate(returnDate, nExtendLapseDates, "D", null);
		}

		return returnDate;
	}
	
	/**
	 * 比较两个日期大小
	 * @param date1
	 * @param date2
	 * @return 如果date1>date2返回1，如果date1<date2返回-1，否则返回0
	 */
	public static int compareToDate(String date1, String date2) {
		
		FDate tFDate = new FDate();
		Date tDate1 = tFDate.getDate(date1);
		Date tDate2 = tFDate.getDate(date2);
		
		return tDate1.compareTo(tDate2);
	}
	
	/**
	 * 得到默认的JDBCUrl
	 * 
	 * @return JDBCUrl
	 */
	public static JdbcUrl getDefaultUrl() {
		JdbcUrl tUrl = new JdbcUrl();
		return tUrl;
	}

	/**
	 * /** 身份证转换函数(15位转换为18位)
	 * <p>
	 * <b>Example: </b>
	 * <p>
	 * <p>
	 * SecurNum("412722851105") returns "412722198511051012"
	 * <p>
	 * 
	 * @param tStr
	 *            源字符串
	 * @return 字符串
	 */
	public static String TransID(String tStrID) {

		String tStrIDtmp = "";
		String tStrIDnew = "";
		String tChkLetter = "10X98765432";
		int j, k, a, w, r1 = 0;
		int w1 = 1;
		tStrIDtmp = tStrID.substring(0, 6) + "19" + tStrID.substring(6, 15);
		for (int i = 17; i >= 1; i--) {
			a = Integer.parseInt(tStrIDtmp.substring(i - 1, i));
			w1 = 2 * w1;
			w = w1 % 11;
			r1 = r1 + w * a;
		}
		j = r1 % 11;
		if (j >= 0 && j <= 10) {
			j = j + 1;
			tStrIDnew = tStrIDtmp + tChkLetter.substring(j - 1, j);
		} else {
			tStrIDnew = tStrIDtmp + "E";
		}
		return tStrIDnew;
	}

	/**
	 * 将字符串补数,将sourString的<br>
	 * 后面</br>用cChar补足cLen长度的字符串,如果字符串超长，则不做处理
	 * <p>
	 * <b>Example: </b>
	 * <p>
	 * <p>
	 * RCh("Minim", "0", 10) returns "Minim00000"
	 * <p>
	 * 
	 * @param sourString
	 *            源字符串
	 * @param cChar
	 *            补数用的字符
	 * @param cLen
	 *            字符串的目标长度
	 * @return 字符串
	 */
	public static String RCh(String sourString, String cChar, int cLen) {
		int tLen = sourString.length();
		int i, iMax;
		String tReturn = "";
		if (tLen >= cLen) {
			return sourString;
		}
		iMax = cLen - tLen;
		for (i = 0; i < iMax; i++) {
			tReturn += cChar;
		}
		tReturn = sourString.trim() + tReturn.trim();
		return tReturn;
	}

	/**
	 * 将字符串补数,将sourString的<br>
	 * 前面</br>用cChar补足cLen长度的字符串,如果字符串超长，则不做处理
	 * <p>
	 * <b>Example: </b>
	 * <p>
	 * <p>
	 * LCh("Minim", "0", 10) returns "00000Minim"
	 * <p>
	 * 
	 * @param sourString
	 *            源字符串
	 * @param cChar
	 *            补数用的字符
	 * @param cLen
	 *            字符串的目标长度
	 * @return 字符串
	 */
	public static String LCh(String sourString, String cChar, int cLen) {
		int tLen = sourString.length();
		int i, iMax;
		String tReturn = "";
		if (tLen >= cLen) {
			return sourString;
		}
		iMax = cLen - tLen;
		for (i = 0; i < iMax; i++) {
			tReturn += cChar;
		}
		tReturn = tReturn.trim() + sourString.trim();
		return tReturn;
	}

	/**
	 * 比较获取两天中较后的一天
	 * 
	 * @param date1
	 *            String
	 * @param date2
	 *            String
	 * @return String
	 */
	public static String getLaterDate(String date1, String date2) {
		try {
			date1 = StrTool.cTrim(date1);
			date2 = StrTool.cTrim(date2);
			if (date1.equals("")) {
				return date2;
			}
			if (date2.equals("")) {
				return date1;
			}
			FDate fd = new FDate();
			Date d1 = fd.getDate(date1);
			Date d2 = fd.getDate(date2);
			if (d1.after(d2)) {
				return date1;
			}
			return date2;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * 比较获取两天中较早的一天
	 * 
	 * @param date1
	 *            String
	 * @param date2
	 *            String
	 * @return String
	 */
	public static String getBeforeDate(String date1, String date2) {
		try {
			date1 = StrTool.cTrim(date1);
			date2 = StrTool.cTrim(date2);
			if (date1.equals("")) {
				return date2;
			}
			if (date2.equals("")) {
				return date1;
			}
			FDate fd = new FDate();
			Date d1 = fd.getDate(date1);
			Date d2 = fd.getDate(date2);
			if (d1.before(d2)) {
				return date1;
			}
			return date2;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * 得到当前系统日期 author: YT
	 * 
	 * @return 当前日期的格式字符串,日期格式为"yyyy-MM-dd"
	 */
	public static String getCurrentDate() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}

	/**
	 * 得到当前系统时间 author: YT
	 * 
	 * @return 当前时间的格式字符串，时间格式为"HH:mm:ss"
	 */
	public static String getCurrentTime() {
		String pattern = "HH:mm:ss";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}

	/**
	 * 得到流水号前导 author: YT
	 * 
	 * @param comCode
	 *            机构代码
	 * @return 流水号的前导字符串
	 */
	public static String getNoLimit(String comCode) {
		comCode = comCode.trim();
		int tLen = comCode.length();
		if (tLen > 6) {
			comCode = comCode.substring(0, 6);
		}
		if (tLen < 6) {
			comCode = RCh(comCode, "0", 6);
		}
		String tString = "";
		tString = comCode + getCurrentDate().substring(0, 4);
		return tString;
	}

	/**
	 * picc获取管理机构，截取管理代码的第3-6位（二级机构+三级机构） 再加上日期编码的两位年两位月日日 052203
	 * 
	 * @param comCode
	 *            String
	 * @return String
	 */
	public static String getPiccNoLimit(String comCode) {
		comCode = comCode.trim();
		logger.debug("comCode :" + comCode);
		int tLen = comCode.length();
		if (tLen == 8) {
			comCode = comCode.substring(2, 6);
		}
		if (tLen == 4) {
			comCode = comCode.substring(2, 4) + "00";
		}
		logger.debug("SubComCode :" + comCode);
		String tString = "";
		tString = comCode + getCurrentDate().substring(2, 4)
				+ getCurrentDate().substring(5, 7)
				+ getCurrentDate().substring(8, 10);
		logger.debug("PubFun getPiccNoLimit : " + tString);
		return tString;
	}

	/**
	 * 该函数得到c_Str中的第c_i个以c_Split分割的字符串
	 * 
	 * @param c_Str
	 *            目标字符串
	 * @param c_i
	 *            位置
	 * @param c_Split
	 *            分割符
	 * @return 如果发生异常，则返回空
	 */
	public static String getTenLengNo(String tNo) {
		String tRetrunValue = "";
		int tLength = tNo.length();
		if (tLength < 12) {
			int t = 12 - tLength;
			String tempAdd = "";
			for (int i = 1; i <= t; i++) {
				tempAdd = tempAdd + "0";
			}
			tRetrunValue = tempAdd + tNo;
		}
		return tRetrunValue;
	}

	/**
	 * 该函数得到c_Str中的第c_i个以c_Split分割的字符串
	 * 
	 * @param c_Str
	 *            目标字符串
	 * @param c_i
	 *            位置
	 * @param c_Split
	 *            分割符
	 * @return 如果发生异常，则返回空
	 */
	public static String getStr(String c_Str, int c_i, String c_Split) {
		String t_Str1 = "", t_Str2 = "", t_strOld = "";
		int i = 0, i_Start = 0;
		// int j_End = 0;
		t_Str1 = c_Str;
		t_Str2 = c_Split;
		i = 0;
		try {
			while (i < c_i) {
				i_Start = t_Str1.indexOf(t_Str2, 0);
				if (i_Start >= 0) {
					i += 1;
					t_strOld = t_Str1;
					t_Str1 = t_Str1.substring(i_Start + t_Str2.length(), t_Str1
							.length());
				} else {
					if (i != c_i - 1) {
						t_Str1 = "";
					}
					break;
				}
			}

			if (i_Start >= 0) {
				t_Str1 = t_strOld.substring(0, i_Start);
			}
		} catch (Exception ex) {
			t_Str1 = "";
		}
		return t_Str1;
	}

	/**
	 * 把数字金额转换为中文大写金额 author: HST
	 * 
	 * @param money
	 *            数字金额(double)
	 * @return 中文大写金额(String)
	 */
	public static String getChnMoney(double money) {
		String ChnMoney = "";
		String s0 = "";

		// 在原来版本的程序中，getChnMoney(585.30)得到的数据是585.29。

		if (money == 0.0) {
			ChnMoney = "人民币零元整";
			return ChnMoney;
		}

		if (money < 0) {
			s0 = "负";
			money *= (-1);
		}

		String sMoney = new DecimalFormat("0").format(money * 100);

		int nLen = sMoney.length();
		String sInteger;
		String sDot;
		if (nLen < 2) {
			// add by JL at 2004-9-14
			sInteger = "";
			if (nLen == 1) {
				sDot = "0" + sMoney.substring(nLen - 1, nLen);
			} else {
				sDot = "0";
			}
		} else {
			sInteger = sMoney.substring(0, nLen - 2);
			sDot = sMoney.substring(nLen - 2, nLen);
		}

		String sFormatStr = PubFun.formatStr(sInteger);

		String s1 = PubFun.getChnM(sFormatStr.substring(0, 4), "亿");

		String s2 = PubFun.getChnM(sFormatStr.substring(4, 8), "万");

		String s3 = PubFun.getChnM(sFormatStr.substring(8, 12), "");

		String s4 = PubFun.getDotM(sDot);

		if (s1.length() > 0 && s1.substring(0, 1).equals("0")) {
			s1 = s1.substring(1, s1.length());
		}
		if (s1.length() > 0
				&& s1.substring(s1.length() - 1, s1.length()).equals("0")
				&& s2.length() > 0 && s2.substring(0, 1).equals("0")) {
			s1 = s1.substring(0, s1.length() - 1);
		}
		if (s2.length() > 0
				&& s2.substring(s2.length() - 1, s2.length()).equals("0")
				&& s3.length() > 0 && s3.substring(0, 1).equals("0")) {
			s2 = s2.substring(0, s2.length() - 1);
		}
		if (s4.equals("00")) {
			s4 = "";
			if (s3.length() > 0
					&& s3.substring(s3.length() - 1, s3.length()).equals("0")) {
				s3 = s3.substring(0, s3.length() - 1);
			}
		}
		if (s3.length() > 0
				&& s3.substring(s3.length() - 1, s3.length()).equals("0")
				&& s4.length() > 0 && s4.substring(0, 1).equals("0")) {
			s3 = s3.substring(0, s3.length() - 1);
		}
		if (s4.length() > 0
				&& s4.substring(s4.length() - 1, s4.length()).equals("0")) {
			s4 = s4.substring(0, s4.length() - 1);
		}
		if (s3.equals("0")) {
			s3 = "";
			s4 = "0" + s4;
		}

		ChnMoney = s0 + s1 + s2 + s3 + "元" + s4;
		if (ChnMoney.substring(0, 1).equals("0")) {
			ChnMoney = ChnMoney.substring(1, ChnMoney.length());
		}
		for (int i = 0; i < ChnMoney.length(); i++) {
			if (ChnMoney.substring(i, i + 1).equals("0")) {
				ChnMoney = ChnMoney.substring(0, i) + "零"
						+ ChnMoney.substring(i + 1, ChnMoney.length());
			}
		}

		if (sDot.substring(1, 2).equals("0")) {
			ChnMoney += "整";
		}

		return "人民币" + ChnMoney;
	}

	/**
	 * 得到money的角分信息
	 * 
	 * @param sIn
	 *            String
	 * @return String
	 */
	private static String getDotM(String sIn) {
		String sMoney = "";
		if (!sIn.substring(0, 1).equals("0")) {
			sMoney += getNum(sIn.substring(0, 1)) + "角";
		} else {
			sMoney += "0";
		}
		if (!sIn.substring(1, 2).equals("0")) {
			sMoney += getNum(sIn.substring(1, 2)) + "分";
		} else {
			sMoney += "0";
		}

		return sMoney;
	}

	/**
	 * 添加仟、佰、拾等单位信息
	 * 
	 * @param strUnit
	 *            String
	 * @param digit
	 *            String
	 * @return String
	 */
	private static String getChnM(String strUnit, String digit) {
		String sMoney = "";
		boolean flag = false;

		if (strUnit.equals("0000")) {
			sMoney += "0";
			return sMoney;
		}
		if (!strUnit.substring(0, 1).equals("0")) {
			sMoney += getNum(strUnit.substring(0, 1)) + "仟";
		} else {
			sMoney += "0";
			flag = true;
		}
		if (!strUnit.substring(1, 2).equals("0")) {
			sMoney += getNum(strUnit.substring(1, 2)) + "佰";
			flag = false;
		} else {
			if (!flag) {
				sMoney += "0";
				flag = true;
			}
		}
		if (!strUnit.substring(2, 3).equals("0")) {
			sMoney += getNum(strUnit.substring(2, 3)) + "拾";
			flag = false;
		} else {
			if (!flag) {
				sMoney += "0";
				flag = true;
			}
		}
		if (!strUnit.substring(3, 4).equals("0")) {
			sMoney += getNum(strUnit.substring(3, 4));
		} else {
			if (!flag) {
				sMoney += "0";
				flag = true;
			}
		}

		if (sMoney.substring(sMoney.length() - 1, sMoney.length()).equals("0")) {
			sMoney = sMoney.substring(0, sMoney.length() - 1) + digit.trim()
					+ "0";
		} else {
			sMoney += digit.trim();
		}
		return sMoney;
	}

	/**
	 * 格式化字符
	 * 
	 * @param sIn
	 *            String
	 * @return String
	 */
	private static String formatStr(String sIn) {
		int n = sIn.length();
		String sOut = sIn;
		// int i = n % 4;

		for (int k = 1; k <= 12 - n; k++) {
			sOut = "0" + sOut;
		}
		return sOut;
	}

	/**
	 * 获取阿拉伯数字和中文数字的对应关系
	 * 
	 * @param value
	 *            String
	 * @return String
	 */
	private static String getNum(String value) {
		String sNum = "";
		Integer I = new Integer(value);
		int iValue = I.intValue();
		switch (iValue) {
		case 0:
			sNum = "零";
			break;
		case 1:
			sNum = "壹";
			break;
		case 2:
			sNum = "贰";
			break;
		case 3:
			sNum = "叁";
			break;
		case 4:
			sNum = "肆";
			break;
		case 5:
			sNum = "伍";
			break;
		case 6:
			sNum = "陆";
			break;
		case 7:
			sNum = "柒";
			break;
		case 8:
			sNum = "捌";
			break;
		case 9:
			sNum = "玖";
			break;
		}
		return sNum;
	}

	/**
	 * 如果一个字符串数字中小数点后全为零，则去掉小数点及零
	 * 
	 * @param Value
	 *            String
	 * @return String
	 */
	public static String getInt(String Value) {
		if (Value == null) {
			return null;
		}
		String result = "";
		boolean mflag = true;
		int m = 0;
		m = Value.lastIndexOf(".");
		if (m == -1) {
			result = Value;
		} else {
			for (int i = m + 1; i <= Value.length() - 1; i++) {
				if (Value.charAt(i) != '0') {
					result = Value;
					mflag = false;
					break;
				}
			}
			if (mflag) {
				result = Value.substring(0, m);
			}
		}
		return result;
	}

	/**
	 * 得到近似值
	 * 
	 * @param aValue
	 *            double
	 * @return double
	 */
	public static double getApproximation(double aValue) {
		if (java.lang.Math.abs(aValue) <= 0.01) {
			aValue = 0;
		}
		return aValue;
	}

	/**
	 * 根据输入标记为间隔符号，拆分字符串
	 * 
	 * @param strMain
	 *            String
	 * @param strDelimiters
	 *            String 失败返回NULL
	 * @return String[]
	 */
	public static String[] split(String strMain, String strDelimiters) {
		int i;
		int intIndex = 0; // 记录分隔符位置，以取出子串
		Vector vResult = new Vector(); // 存储子串的数组
		String strSub = ""; // 存放子串的中间变量

		strMain = strMain.trim();

		// 若主字符串比分隔符串还要短的话,则返回空字符串
		if (strMain.length() <= strDelimiters.length()) {
			logger.debug("分隔符串长度大于等于主字符串长度，不能进行拆分！");
			return null;
		}

		// 取出第一个分隔符在主串中的位置
		intIndex = strMain.indexOf(strDelimiters);

		// 在主串中找不到分隔符
		if (intIndex == -1) {
			String[] arrResult = { strMain };
			return arrResult;
		}

		// 分割主串到数组中
		while (intIndex != -1) {
			strSub = strMain.substring(0, intIndex);
			if (intIndex != 0) {
				vResult.add(strSub);
			} else {
				// break;
				vResult.add("");
			}

			strMain = strMain.substring(intIndex + strDelimiters.length())
					.trim();
			intIndex = strMain.indexOf(strDelimiters);
		}

		// 如果最末不是分隔符，取最后的字符串
		// if (!strMain.equals("") && strMain != null)
		if (!strMain.equals("")) {
			vResult.add(strMain);
		}

		String[] arrResult = new String[vResult.size()];
		for (i = 0; i < vResult.size(); i++) {
			arrResult[i] = (String) vResult.get(i);
		}

		return arrResult;
	}

	/**
	 * 设置数字精度 需要格式化的数据
	 * 
	 * @param value
	 *            float 精度描述（0.00表示精确到小数点后两位）
	 * @param precision
	 *            String
	 * @return double
	 */
	public static double setPrecision(float value, String precision) {
		return Float.parseFloat(new DecimalFormat(precision).format(value));
	}

	/**
	 * 设置数字精度 需要格式化的数据
	 * 
	 * @param value
	 *            double 精度描述（0.00表示精确到小数点后两位）
	 * @param precision
	 *            String
	 * @return double
	 */
	public static double setPrecision(double value, String precision) {
		return Double.parseDouble(new DecimalFormat(precision).format(value));
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果 add by jiaqiangli 2008-10-30 lis65程序四舍五入调用方法
	 *         或者调用Arith.round方法
	 */
	public static double round(double v, int scale) {

		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 将源 Schema 对象中的数据拷贝至目标 Schema 对象中 保全 C、P 互换时经常用到
	 * 
	 * @param Schema
	 * @param Schema
	 */
	public static void copySchema(Schema destSchema, Schema srcSchema) {
		if (destSchema != null && srcSchema != null) {
			try {
				Reflections tReflections = new Reflections();
				tReflections.transFields(destSchema, srcSchema);
				tReflections = null;
			} catch (Exception ex) {
				logger.debug("\t@> PubFun.copySchema() : Reflections 拷贝数据失败！");
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 把schemaset对象拷贝一份返回
	 * 
	 * @param srcSet
	 *            SchemaSet
	 * @return SchemaSet
	 */
	public static SchemaSet copySchemaSet(SchemaSet srcSet) {
		try {
			if (srcSet != null && srcSet.size() > 0) {
				if (srcSet.getObj(1) == null) {
					return null;
				}
				Class cls = srcSet.getClass();
				Schema schema = (Schema) srcSet.getObj(1).getClass()
						.newInstance();
				SchemaSet obj = (SchemaSet) cls.newInstance();
				obj.add(schema);
				Reflections tReflections = new Reflections();
				tReflections.transFields(obj, srcSet);
				tReflections = null;
				return obj;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 互换LP和LC表数据
	 * 
	 * @param source
	 *            Schema
	 * @param object
	 *            Schema
	 * @return boolean
	 */
	public static boolean exchangeSchema(Schema source, Schema object) {
		try {
			// 把LP表的数据传递到LC表
			Reflections tReflections = new Reflections();
			tReflections.transFields(object, source);

			// 获取一个数据库连接DB
			Method m = object.getClass().getMethod("getDB", null);
			Schema schemaDB = (Schema) m.invoke(object, null);
			// 因为LP表与LC表只有EdorNo和EdorType两个关键字的差别，所以可以唯一获取LC表对应记录
			m = schemaDB.getClass().getMethod("getInfo", null);
			m.invoke(schemaDB, null);
			m = schemaDB.getClass().getMethod("getSchema", null);
			object = (Schema) m.invoke(schemaDB, null);

			// 把LC表数据备份到临时表
			m = object.getClass().getMethod("getSchema", null);
			Schema tSchema = (Schema) m.invoke(object, null);

			// 互换LP和LC表数据
			tReflections.transFields(object, source);
			tReflections.transFields(source, tSchema);

			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 生成更新的sql列表
	 * 
	 * @param tables
	 *            String[]
	 * @param condition
	 *            String
	 * @param wherepart
	 *            String
	 * @return Vector
	 */
	public static Vector formUpdateSql(String[] tables, String condition,
			String wherepart) {
		Vector sqlVec = new Vector();
		for (int i = 0; i < tables.length; i++) {
			sqlVec.add("update " + tables[i] + " set " + condition + " where "
					+ wherepart);
		}
		return sqlVec;
	}

	/**
	 * 将账号前的0去掉
	 * 
	 * @param sIn
	 *            String
	 * @return String
	 */
	public static String DeleteZero(String sIn) {
		int n = sIn.length();
		String sOut = sIn;

		while (sOut.substring(0, 1).equals("0") && n > 1) {
			sOut = sOut.substring(1, n);
			n = sOut.length();
			logger.debug(sOut);
		}

		if (sOut.equals("0")) {
			return "";
		} else {
			return sOut;
		}
	}

	public static String CreateSeq(String seq) {
		String tt = null;
		ExeSQL tExeSQL = new ExeSQL();
		try {
			if ("ORACLE".equalsIgnoreCase(SysConst.DBTYPE)) {
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql("select " + seq + ".nextval from dual");
				//sqlbv1.put("seq", seq);
				tt = tExeSQL.getOneValue(sqlbv1);
			} else {
				SysMaxNoMinSheng maxNo = new SysMaxNoMinSheng();
				tt = maxNo.CreateMaxNo(seq, 1);
			}
		} catch (Exception ex) {
			logger.debug(ex.toString());
		}
		return tt;
	}

	public static String CreateSeq(String seq, int len) {
		String tt = CreateSeq(seq);
		tt = PubFun.LCh(tt, "0", len);
		return tt;
	}
	
	/**
	 * 转换JavaScript解析不了的特殊字符
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String changForJavaScript(String s) {
		char[] arr = s.toCharArray();
		s = "";
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == '"' || arr[i] == '\'') {
				s = s + "\\";
			}
			// modify by zzm
			if (arr[i] == '\n') {
				s = s + "\\n";
				continue;
			}
			if (arr[i] == '\r')
				continue;
			if (arr[i] == '\\')
				s = s + "\\";

			s = s + arr[i];
		}

		return s;
	}

	/**
	 * 转换JavaScript解析不了的特殊字符
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String changForHTML(String s) {
		char[] arr = s.toCharArray();
		s = "";

		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == '"' || arr[i] == '\'') {
				s = s + "\\";
			}

			if (arr[i] == '\n') {
				s = s + "<br>";
				continue;
			}
			if (arr[i] == '\r')
				continue;

			s = s + arr[i];
		}

		return s;
	}

	public static String getClassFileName(Object o) {
		String fileName = o.getClass().getName();
		fileName = fileName.substring(fileName.lastIndexOf(".") + 1);
		return fileName;
	}

	public static void out(Object o, String s) {
		logger.debug(PubFun.getClassFileName(o) + " : " + s);
	}

	/**
	 * 计算保单年度
	 * 
	 * @param cstartDate
	 *            String
	 * @param cendDate
	 *            String
	 * @return int
	 */
	public static int calPolYear(String cstartDate, String cendDate) {
		FDate fDate = new FDate();
		Date startDate = fDate.getDate(cstartDate);
		Date endDate = fDate.getDate(cendDate);
		if (fDate.mErrors.needDealError()) {
			return 0;
		}

		int interval = 0;

		GregorianCalendar sCalendar = new GregorianCalendar();
		sCalendar.setTime(startDate);
		int sYears = sCalendar.get(Calendar.YEAR);
		// int sMonths = sCalendar.get(Calendar.MONTH);
		// int sDays = sCalendar.get(Calendar.DAY_OF_MONTH);
		int sDaysOfYear = sCalendar.get(Calendar.DAY_OF_YEAR);

		GregorianCalendar eCalendar = new GregorianCalendar();
		eCalendar.setTime(endDate);
		int eYears = eCalendar.get(Calendar.YEAR);
		// int eMonths = eCalendar.get(Calendar.MONTH);
		// int eDays = eCalendar.get(Calendar.DAY_OF_MONTH);
		int eDaysOfYear = eCalendar.get(Calendar.DAY_OF_YEAR);

		interval = eYears - sYears;
		// interval = interval * 365;
		interval *= 365;
		// interval = eDaysOfYear - sDaysOfYear + interval;
		interval += eDaysOfYear - sDaysOfYear;

		// 处理润年
		int n = 0;
		eYears--;
		if (eYears > sYears) {
			int i = sYears % 4;
			if (i == 0) {
				sYears++;
				n++;
			}
			int j = (eYears) % 4;
			if (j == 0) {
				eYears--;
				n++;
			}
			n += (eYears - sYears) / 4;
		}
		if (eYears == sYears) {
			int i = sYears % 4;
			if (i == 0) {
				n++;
			}
		}
		interval += n;

		int x = 365;
		int PolYear = 1;
		while (x < interval) {
			// x = x + 365;
			x += 365;
			// PolYear = PolYear + 1;
			PolYear += 1;
		}

		return PolYear;
	}

	/**
	 * tongmeng 2009-02-09 add 校验是否自动运行停止
	 * 
	 * @param tLimitTime
	 *            限制时间
	 * @return true 可以执行 false 不执行
	 */
	public static boolean checkAutoRunControl(String tLimitTime) {
		boolean tRes = true;
		String tCurrDate = PubFun.getCurrentDate();
		String tCurrTime = PubFun.getCurrentTime();
		if (tLimitTime == null || tLimitTime.trim().equals("")) {
			// 如果没有传时间,使用LDSysvar中的描述数据
			LDSysVarDB tLDSysVarDB = new LDSysVarDB();
			tLDSysVarDB.setSysVar("AutoRunLimitTime");
			if (!tLDSysVarDB.getInfo()) {
				// 查询不出来,默认认为00:00:00
				tLimitTime = "00:00:00";
			} else {
				if (tLDSysVarDB.getSysVarValue() != null
						&& !tLDSysVarDB.equals("")) {
					tLimitTime = tLDSysVarDB.getSysVarValue();
				} else {
					tLimitTime = "00:00:00";
				}
			}
		}
//		"Oracle：select decode(count(prtno),0,'0','1') from laagentascription 
//		改造为：select (case count(prtno) when 0 then '0' else '1' end)from laagentascription "

		String tSQL = "select (case count(*) when 0 then '0' else '1' end) from dual where concat(concat('"
				+ "?tCurrDate?" + "',' '),'" + "?tCurrTime?" + "')>=concat(concat('" + "?tCurrDate1?"
				+ "',' '),'" + "?tLimitTime?" + "' )";
		String tValue = "";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tSQL);
		sqlbv2.put("tCurrDate", tCurrDate);
		sqlbv2.put("tCurrTime", tCurrTime);
		sqlbv2.put("tCurrDate1", tCurrDate);
		sqlbv2.put("tLimitTime", tLimitTime);
		ExeSQL tExeSQL = new ExeSQL();
		tValue = tExeSQL.getOneValue(sqlbv2);
		if (tValue == null || tValue.equals("")
				|| (tValue != null && Integer.parseInt(tValue) < 1)) {
			// 小于0,不限制
			tRes = true;
		} else {
			tRes = false;
		}
		return tRes;
	}

	/**
	 * 通过身份证号号获取生日日期
	 * 
	 * @param IdNo
	 *            String
	 * @return String
	 */
	public static String getBirthdayFromId(String IdNo) {
		String tIdNo = StrTool.cTrim(IdNo);
		String birthday = "";
		if (tIdNo.length() != 15 && tIdNo.length() != 18) {
			return "";
		}
		if (tIdNo.length() == 18) {
			birthday = tIdNo.substring(6, 14);
			birthday = birthday.substring(0, 4) + "-"
					+ birthday.substring(4, 6) + "-" + birthday.substring(6);
		}
		if (tIdNo.length() == 15) {
			birthday = tIdNo.substring(6, 12);
			birthday = birthday.substring(0, 2) + "-"
					+ birthday.substring(2, 4) + "-" + birthday.substring(4);
			birthday = "19" + birthday;
		}
		return birthday;

	}

	/**
	 * 通过身份证号获取性别
	 * 
	 * @param IdNo
	 *            String
	 * @return String
	 */
	public static String getSexFromId(String IdNo) {
		String tIdNo = StrTool.cTrim(IdNo);
		if (tIdNo.length() != 15 && tIdNo.length() != 18) {
			return "";
		}
		String sex = "";
		if (tIdNo.length() == 15) {
			sex = tIdNo.substring(14, 15);
		} else {
			sex = tIdNo.substring(16, 17);
		}
		try {
			int iSex = Integer.parseInt(sex);
			// iSex = iSex % 2;
			iSex %= 2;
			if (iSex == 0) {
				return "1";
			}
			if (iSex == 1) {
				return "0";
			}
		} catch (Exception ex) {
			return "";
		}
		return "";
	}

	/**
	 * 用户页面权限判断
	 * 
	 * @param cGlobalInput
	 *            GlobalInput
	 * @param RunScript
	 *            String
	 * @return boolean
	 */
	public static boolean canIDo(GlobalInput cGlobalInput, String RunScript,
			String pagesign) {
		String Operator = cGlobalInput.Operator;
		// String ComCode = cGlobalInput.ComCode;
		// String ManageCom = cGlobalInput.ManageCom;
		// 通过用户编码查询用户页面权限集合,NodeSign = 2为用户页面权限菜单标志
		String sqlStr = "select count(1) from LDMenu ";
		sqlStr = sqlStr + "where RunScript like concat(concat('%','?RunScript?'),'%') ";
		// "where NodeSign = '2' and RunScript = '" + RunScript + "' ";
		if (pagesign.equals("page"))
			sqlStr = sqlStr
					+ "and parentnodecode in ( select distinct NodeCode from LDMenuGrpToMenu ";
		if (pagesign.equals("menu"))
			sqlStr = sqlStr
					+ "and NodeCode in ( select distinct NodeCode from LDMenuGrpToMenu ";
		sqlStr = sqlStr
				+ "where MenuGrpCode in ( select distinct MenuGrpCode from LDMenuGrp ";
		sqlStr = sqlStr
				+ "where MenuGrpCode in (select distinct MenuGrpCode from LDUserToMenuGrp where UserCode = '";
		sqlStr = sqlStr + "?Operator?";
		sqlStr = sqlStr + "') ) ) ";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sqlStr);
		sqlbv3.put("RunScript", RunScript);
		sqlbv3.put("Operator", Operator);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv3);
		if (tSSRS != null) {
			String tt[] = tSSRS.getRowData(1);
			if (tt[0].equals("0")) {
				return false;
			}
		}
		// logger.debug("Yes can do");
		return true;
	}

	/**
	 * 校验录入的管理机构是否是操作员登陆机构同级或下级，参数为空返回false
	 * 
	 * @param cGlobalInput
	 *            GlobalInput
	 * @param RunScript
	 *            String
	 * @return boolean
	 */
	public static boolean checkManageCom(String InputManageCom,
			String UserManageCom) {
		if (InputManageCom == null || InputManageCom.trim().equals("")
				|| UserManageCom == null || UserManageCom.trim().equals(""))
			return false;
		/*"Oracle：select nvl(sum(prem),0) from ljtempfee_lmriskapp
		改造为：select (case when sum(prem) is not null then sum(prem)  else 0 end)  from ljtempfee_lmriskapp;"
*/		
		
		String sqlcheck = "select (case when (select 'Y' from ldsysvar where sysvar = 'onerow' and trim("
				+ "?InputManageCom?"
				+ ") like concat(trim("
				+ "?UserManageCom?"
				+ "),'%')) is null then 'N' else (select 'Y' from ldsysvar where sysvar = 'onerow' and trim("
				+ "?InputManageCom?"
				+ ") like concat(trim("
				+ "?UserManageCom?"
				+ "),'%')) end) from ldsysvar where sysvar = 'onerow'";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(sqlcheck);
		sqlbv4.put("InputManageCom", InputManageCom);
		sqlbv4.put("UserManageCom", UserManageCom);
		ExeSQL tExeSQL = new ExeSQL();
		String result = tExeSQL.getOneValue(sqlbv4);
		if (result.equals("Y"))
			return true;
		else
			return false;
	}

	/**
	 * 用户页面权限判断
	 * 
	 * @param cGlobalInput
	 *            GlobalInput
	 * @param RunScript
	 *            String
	 * @return boolean
	 */
	public static boolean canIDo(GlobalInput cGlobalInput, String RunScript) {
		String Operator = cGlobalInput.Operator;
		// String ComCode = cGlobalInput.ComCode;
		// String ManageCom = cGlobalInput.ManageCom;
		// 通过用户编码查询用户页面权限集合,NodeSign = 2为用户页面权限菜单标志
		String sqlStr = "select * from LDMenu ";
		sqlStr = sqlStr + "where NodeSign = 2 and RunScript = '" + "?RunScript?"
				+ "' ";
		sqlStr = sqlStr
				+ "and NodeCode in ( select NodeCode from LDMenuGrpToMenu ";
		sqlStr = sqlStr
				+ " where MenuGrpCode in ( select MenuGrpCode from LDMenuGrp ";
		sqlStr = sqlStr
				+ " where MenuGrpCode in (select MenuGrpCode from LDUserToMenuGrp where UserCode = '";
		sqlStr = sqlStr + "?Operator?";
		sqlStr = sqlStr + "') ) ) order by nodeorder";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(sqlStr);
		sqlbv5.put("RunScript", RunScript);
		sqlbv5.put("Operator", Operator);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv5);

		if (tSSRS == null || tSSRS.equals("")) {
			return false;
		}
		return true;
	}

	/***************************************************************************
	 * 本函数功能：用于作各种自定义判断校验。前提是 必须将校验的SQL字串已经存入 LMCalMode表里 所需参数：
	 * CalCode----------获取校验SQL的代码; TransferData-----包含作校验时校验字串所需参数
	 * 返回参数：True--校验成功[校验字串返回值为“1”] ；False---校验失败；
	 * 例：判断业务员（代理人）是否有销售资格,代理人代码为：02281075 校验字串: select case salequaf when 'N'
	 * then '0' else '1' end from laagent where agentcode='?AgentCode?' 需要传入的参数:
	 * (1)、CalCode=“AgSale”； (2)、代理人代码<AgentCode>,则需将代理人代码放入 TransferData 调用方法;
	 * String tCalCode ="AgSale"; //根据字串从 lmcalmode 里获取 校验的SQL字串 TransferData
	 * tTransferData = new TransferData();
	 * tTransferData.setNameAndValue("AgentCode","02281075");//校验字串所需参数
	 * if(PubFun.userDefinedCheck(tCalCode,tTransferData)==false) {
	 * logger.debug("校验失败"); return false; }
	 **************************************************************************/
	public static boolean userDefinedCheck(String mCalCode,
			TransferData mTransferData) {
		//
		String tCalCode = ""; // 获取传入的 CalCode 代码，用于得到校验的SQL语句
		TransferData tTransferData = new TransferData();
		tTransferData = mTransferData; // 获取校验所需参数<>
		// 判断传入代码的是否正确
		if (mCalCode == null || mCalCode.equals("")) {
			return false;
		} else {
			tCalCode = mCalCode.trim();
		}
		// 判断传入TransferData<包含校验所需参数>是否正确
		if (tTransferData == null) {
			return false;
		}
		// 以下调用 Calculator 类，作校验判断
		Calculator mCalculator = new Calculator();
		mCalculator.setCalCode(tCalCode);
		Vector tVector = new Vector();
		tVector = tTransferData.getValueNames();
		if (tVector.size() > 0) {
			// 获得要素名称并赋值到Calculator类中
			String mValue = "";
			for (int i = 0; i < tVector.size(); i++) {
				String tFactorName = new String();
				String tFactorValue = new String();
				tFactorName = (String) tVector.get(i);
				tFactorValue = (String) tTransferData
						.getValueByName(tFactorName);
				mCalculator.addBasicFactor(tFactorName, tFactorValue);
			}
			mValue = mCalculator.calculate();
			if (mValue != null && mValue.equals("1")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 取得sequenceno的值
	 * 
	 * @param type
	 *            String 序列号类型
	 * @return String 序列号,如果出错返回"-1"
	 */
	public static String getSeqNo(String type) {
		String ret = "-1";

		if ("ORACLE".equalsIgnoreCase(SysConst.DBTYPE)) {
			ExeSQL es = new ExeSQL();
			SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
			sqlbv6.sql("select " + type + ".nextval from dual");
			//sqlbv6.put("type", type);
			ret = es.getOneValue(sqlbv6);
		} else {
			SysMaxNoMinSheng maxNo = new SysMaxNoMinSheng();
			ret = maxNo.CreateMaxNo(type, 1);
		}
		return ret;
	}

	/**
	 * java中得到应用的路径.
	 * 
	 */

	public static String GetRealPathForJava() {
		String Result;
		int tMarkCount;

		String str = ClassLoader.getSystemResource("").toString();

		tMarkCount = str.length() - str.replaceAll("/", "").length();

		ExeSQL tExeSQL = new ExeSQL();
		String tstartindexSQL = "select instr4('" + "?str?" + "','/',1,1) from dual";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(tstartindexSQL);
		sqlbv6.put("str", str);
		int tstartindex = Integer.parseInt(tExeSQL.getOneValue(sqlbv6));

		// 前推两层目录,classess,WEB_INF
		tMarkCount = tMarkCount - 2;
		String tendindexSQL = "select instr4('" + "?str?" + "','/',1,?tMarkCount?) from dual";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(tendindexSQL);
		sqlbv7.put("str", str);
		sqlbv7.put("tMarkCount", tMarkCount);
		int tendindex = Integer.parseInt(tExeSQL.getOneValue(sqlbv7));

		Result = str.substring(tstartindex - 1, tendindex);
		return (Result);
	}

	/*
	 * 界面录入起始日期、起始时间、结束日期、结束时间，返回查询SQL ColDateName 查询表的日期字段名称 ColTimeName
	 * 查询表的时间字段名称 StartDate 界面录入的开始日期 StartTime 界面录入的开始时间 EndDate 界面录入的结束日期
	 * EndTime 界面录入的结束时间
	 */
	public static String DateTimeBetween(String ColDateName,
			String ColTimeName, String StartDate, String StartTime,
			String EndDate, String EndTime) {
		String tReturnSQL = "";
		if (StartDate == null || EndDate == null || StartDate.equals("")
				|| EndDate.equals("")) {
		} else {
			if (StartDate.equals(EndDate))
				tReturnSQL = " AND (" + ColDateName + "='" + StartDate
						+ "' AND " + ColTimeName + ">='" + StartTime + "' AND "
						+ ColTimeName + "<='" + EndTime + "') ";
			else
				tReturnSQL = " AND ((" + ColDateName + "='" + StartDate
						+ "' AND " + ColTimeName + ">='" + StartTime + "') OR "
						+ "(" + ColDateName + "='" + EndDate + "' AND "
						+ ColTimeName + "<='" + EndTime + "') OR " + "("
						+ ColDateName + ">'" + StartDate + "' AND "
						+ ColDateName + "<'" + EndDate + "')) ";
		}

		return tReturnSQL;
	}

	// 银行账号校验过滤
	// 1）根据银行编码BankCode查询银行信息表ldbank的银行类型标志BankType和银行机构Comcode；
	// 2）如果BankType为“GSYH”，如果银行账号BankAccno存在“*”字符，则返回的银行账号信息为原账号过滤“*”之后的账号信息；
	// 3）如果BankType为“NYYH”且Comcode的前四位为“8613”，如果银行账号BankAccno开头字符为“50-”字符，则返回的银行账号信息为原账号截取掉“50-”之后的账号信息。
	public static String getBankAccNo(String BankCode, String BankAccNo) {
		String strSql = "select a.BankType, a.comcode from ldbank a where a.bankcode='"
				+ "?BankCode?" + "'";
		SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
		sqlbv8.sql(strSql);
		sqlbv8.put("BankCode", BankCode);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv8);
		if (tSSRS == null || tSSRS.equals("")) {
			return BankAccNo;
		}

		if ("GSYH".equals(tSSRS.GetText(1, 1))) {
			if (BankAccNo.indexOf("*") != -1) {
				logger.debug("您输入的银行账号" + BankAccNo + "中存在[*]字符，去除之");
				return StrTool.replace(BankAccNo, "*", "");
			}
		} else if ("NYYH".equals(tSSRS.GetText(1, 1))
				&& tSSRS.GetText(1, 2) != null
				&& tSSRS.GetText(1, 2).startsWith("8613")) {
			if (BankAccNo != null && BankAccNo.startsWith("50-")) {
				logger.debug("您输入的银行账号" + BankAccNo + "不允许[50-]开头，去除之");
				return BankAccNo.substring(3);
			}
		}

		return BankAccNo;
	}

	/**
	 * 获取传入日期后的度量纬日期
	 * 
	 * @param cDate
	 *            String 传入的日期
	 * @param cInterval
	 *            int 日期的间隔
	 * @param cUnit
	 *            String 日期计算的纬度
	 * @return String
	 */
	public static String getLastDate(String cDate, int cInterval, String cUnit) {
		// 返回日期的格式化样式
		String tFormatDate = "yyyy-MM-dd";
		// 创建格式化对象
		SimpleDateFormat tSimpleDateFormat = new SimpleDateFormat(tFormatDate);
		// 转换日期字符串为日期类型
		FDate tFDate = new FDate();
		Date CurDate = tFDate.getDate(cDate);

		GregorianCalendar tCalendar = new GregorianCalendar();
		// 将传入的日期置入
		tCalendar.setTime(CurDate);

		if (cUnit.equals("D")) {
			// 取出日信息，需要同时取日月
			int tDay = tCalendar.get(Calendar.DAY_OF_MONTH);
			// 将日加上间隔，置回
			tCalendar.set(Calendar.DAY_OF_MONTH, tDay + cInterval);
			// 返回
			return tSimpleDateFormat.format(tCalendar.getTime());
		} else if (cUnit.equals("M")) {
			// 获取月份信息
			int tMonth = tCalendar.get(Calendar.MONTH);
			// 将月份加上间隔，置回
			tCalendar.set(Calendar.MONTH, tMonth + cInterval);
			// 返回
			return tSimpleDateFormat.format(tCalendar.getTime());
		} else if (cUnit.equals("Y")) {
			// 获取年信息
			int tYear = tCalendar.get(Calendar.YEAR);
			// 将年加上间隔，置回
			tCalendar.set(Calendar.YEAR, tYear + cInterval);
			// 返回
			return tSimpleDateFormat.format(tCalendar.getTime());
		} else {
			return "";
		}
	}

	/**
	 * 计算投保年龄,对于生日单生日减一 起始日期，(String,格式："YYYY-MM-DD")
	 * 
	 * @param cstartDate
	 *            String 终止日期，(String,格式："YYYY-MM-DD")
	 * @param cendDate
	 *            String 时间间隔单位，可用值("Y"--年 "M"--月 "D"--日)
	 * @param unit
	 *            String 时间间隔,整形变量int
	 * @return int
	 */
	public static int calAppAge(String cstartDate, String cendDate, String unit) {
		FDate fDate = new FDate();
		Date startDate = fDate.getDate(cstartDate);
		Date endDate = fDate.getDate(cendDate);
		if (fDate.mErrors.needDealError()) {
			return 0;
		}

		int interval = 0;

		GregorianCalendar sCalendar = new GregorianCalendar();
		sCalendar.setTime(startDate);
		int sYears = sCalendar.get(Calendar.YEAR);
		int sMonths = sCalendar.get(Calendar.MONTH);
		int sDays = sCalendar.get(Calendar.DAY_OF_MONTH);

		GregorianCalendar eCalendar = new GregorianCalendar();
		eCalendar.setTime(endDate);
		int eYears = eCalendar.get(Calendar.YEAR);
		int eMonths = eCalendar.get(Calendar.MONTH);
		int eDays = eCalendar.get(Calendar.DAY_OF_MONTH);

		if (StrTool.cTrim(unit).equals("Y")) {
			interval = eYears - sYears;

			if (eMonths < sMonths) {
				interval--;
			} else {
				if (eMonths == sMonths && eDays <= sDays) {
					interval--;
				}
			}
		}
		if (StrTool.cTrim(unit).equals("M")) {
			interval = eYears - sYears;
			interval *= 12;
			interval += eMonths - sMonths;

			if (eDays < sDays) {
				interval--;
				int maxDate = eCalendar.getActualMaximum(Calendar.DATE);
				if (eDays == maxDate) {
					interval++;
				}
			}
		}
		if (StrTool.cTrim(unit).equals("D")) {

			sCalendar.set(sYears, sMonths, sDays);
			eCalendar.set(eYears, eMonths, eDays);
			long lInterval = (eCalendar.getTime().getTime() - sCalendar
					.getTime().getTime()) / 86400000;
			interval = (int) lInterval;

		}
		return interval;
	}

	public static String calOFDate(String baseDate, int interval, String unit,
			String compareDate) {
		try {
			FDate tFDate = new FDate();
			Date bDate = tFDate.getDate(baseDate);
			Date cDate = tFDate.getDate(compareDate);
			return tFDate.getString(calOFDate(bDate, interval, unit, cDate));
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static Date calOFDate(Date baseDate, int interval, String unit,
			Date compareDate) {
		Date returnDate = null;

		GregorianCalendar mCalendar = new GregorianCalendar();
		// 设置起始日期格式
		mCalendar.setTime(baseDate);
		if (unit.equals("Y")) {
			mCalendar.add(Calendar.YEAR, interval);
		}
		if (unit.equals("M")) {
			// 执行月份增减
			mCalendar.add(Calendar.MONTH, interval);
		}
		if (unit.equals("D")) {
			mCalendar.add(Calendar.DATE, interval);
		}

		if (compareDate != null) {
			GregorianCalendar cCalendar = new GregorianCalendar();
			// 设置坐标日期
			cCalendar.setTime(compareDate);

			int mYears = mCalendar.get(Calendar.YEAR);
			int mMonths = mCalendar.get(Calendar.MONTH);
			int cMonths = cCalendar.get(Calendar.MONTH);
			int cDays = cCalendar.get(Calendar.DATE);

			if (unit.equals("Y")) {
				cCalendar.set(mYears, cMonths, cDays);
				if (mMonths < cCalendar.get(Calendar.MONTH)) {
					cCalendar.set(mYears, mMonths + 1, 0);
				}
				if (cCalendar.before(mCalendar)) {
					mCalendar.set(mYears + 1, cMonths, cDays);
					returnDate = mCalendar.getTime();
				} else {
					returnDate = cCalendar.getTime();
				}
			}
			if (unit.equals("M")) {
				cCalendar.set(mYears, mMonths, cDays);

				if (mMonths < cCalendar.get(Calendar.MONTH)) {
					// 取当前月的最后一天日期
					cCalendar.set(mYears, mMonths + 1, 0);
				}
				if (cCalendar.before(mCalendar)) {
					mCalendar.set(mYears, mMonths + 1, cDays);
					returnDate = mCalendar.getTime();
				} else {
					returnDate = cCalendar.getTime();
				}
			}
			if (unit.equals("D")) {
				returnDate = mCalendar.getTime();
			}
		} else {
			returnDate = mCalendar.getTime();
		}

		return returnDate;
	}

	/**
	 * 日志组件-业务轨迹监控-非后台批次调用
	 * 
	 * @param SubjectID
	 * @param ItemID
	 */
	public static void logTrack(String SubjectID, String KeyNo, String LogDes) {
		if (SubjectID != null && !SubjectID.equals(""))
			LogProcessor.log("02", SubjectID, "", KeyNo, LogDes, "null");
	}

	/**
	 * 日志组件-业务轨迹监控-后台批次调用接口 LogID[0]为SubjectID="TASK"+LDTASK.TASKCODE
	 * LogID[1]为LDTASKRUNLOG.SERIALNO
	 * 
	 */
	public static void logTrack(GlobalInput o, String KeyNo, String LogDes) {
		if (o != null && o.LogID[0] != null && !o.LogID[0].equals("")
				&& o.LogID[1] != null && !o.LogID[1].equals(""))
			LogProcessor.log("02", o.LogID[0], o.LogID[1], KeyNo, LogDes,
					"null");
	}

	/**
	 * 日志组件-业务结果监控-非后台批次调用
	 * 
	 * @param SubjectID
	 * @param ItemID
	 */
	public static void logResult(String SubjectID, String KeyNo, String LogDes) {
		if (SubjectID != null && !SubjectID.equals(""))
			LogProcessor.log("03", SubjectID, "", KeyNo, LogDes, "null");
	}

	/**
	 * 日志组件-业务结果监控-后台批次调用接口 LogID[0]为SubjectID="TASK"+LDTASK.TASKCODE
	 * LogID[1]为LDTASKRUNLOG.SERIALNO
	 * 
	 */
	public static void logResult(GlobalInput o, String KeyNo, String LogDes) {
		if (o != null && o.LogID[0] != null && !o.LogID[0].equals("")
				&& o.LogID[1] != null && !o.LogID[1].equals(""))
			LogProcessor.log("03", o.LogID[0], o.LogID[1], KeyNo, LogDes,
					"null");
	}

	/**
	 * 日志组件-业务状态监控-非后台批次调用
	 * 
	 * @param State
	 * @param KeyType
	 * @param KeyNo
	 */

	public static void logState(String SubjectID, String KeyNo, String LogDes,
			String State) {
		if (SubjectID != null && !SubjectID.equals(""))
			LogProcessor.log("01", SubjectID, "", KeyNo, LogDes, State);
	}

	/**
	 * 日志组件-业务状态监控-后台批次调用接口 LogID[0]为SubjectID="TASK"+LDTASK.TASKCODE
	 * LogID[1]为LDTASKRUNLOG.SERIALNO
	 * 
	 */
	public static void logState(GlobalInput o, String KeyNo, String LogDes,
			String State) {
		if (o != null && o.LogID[0] != null && !o.LogID[0].equals("")
				&& o.LogID[1] != null && !o.LogID[1].equals(""))
			LogProcessor
					.log("01", o.LogID[0], o.LogID[1], KeyNo, LogDes, State);
	}

	// capacity
	/**
	 * 日志组件-业务性能监控-非后台批次调用
	 * 
	 * @param State
	 * @param KeyType
	 * @param KeyNo
	 */

	public static void logPerformance(String SubjectID, String KeyNo,
			String LogDes, String State) {
		if (SubjectID != null && !SubjectID.equals(""))
			LogProcessor.log("04", SubjectID, "", KeyNo, LogDes, State);
	}

	/**
	 * 日志组件-业务性能监控-后台批次调用接口 LogID[0]为SubjectID="TASK"+LDTASK.TASKCODE
	 * LogID[1]为LDTASKRUNLOG.SERIALNO
	 * 
	 */
	public static void logPerformance(GlobalInput o, String KeyNo,
			String LogDes, String State) {
		if (o != null && o.LogID[0] != null && !o.LogID[0].equals("")
				&& o.LogID[1] != null && !o.LogID[1].equals(""))
			LogProcessor
					.log("04", o.LogID[0], o.LogID[1], KeyNo, LogDes, State);
	}

	public static boolean writeToFile(String tFileName, ArrayList tContentList) {
		OutputStreamWriter outS = null;
		BufferedWriter bw = null;
		String tSQL = "select sysvarvalue from ldsysvar where sysvar='ProductOutPath' ";
		SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
		sqlbv9.sql(tSQL);
		ExeSQL tExeSQL = new ExeSQL();
		String tFilePath = tExeSQL.getOneValue(sqlbv9);
		if (tFilePath == null || tFilePath.equals("")) {
			return false;
		}
		// logger.debug(ClassLoader.getSystemResource("/"));
		File f = new File(tFilePath + tFileName);
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(f), "UTF-8"));

			for (int i = 0; i < tContentList.size(); i++) {
				String tText = (String) tContentList.get(i);
				bw.write(tText);
				bw.newLine();
			}

			// os2.write("测试");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// outS.close();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	// 产品脚本发布路径，相对路径
	public static String getProductDeployPath() {
		String sql = "select concat(concat(sysvarvalue,to_char(now(),'yyyyMMdd')),'/') from ldsysvar where sysvar='ProductDeployPath'";
		SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
		sqlbv9.sql(sql);
		ExeSQL tExeSQL = new ExeSQL();
		String path = tExeSQL.getOneValue(sqlbv9);
		// 默认路径为:/productdef/deployscript/
		if (path == null || path.equals(""))
			return "productdef/deployscript/";
		return path;
	}

	public static String getClassPath() {
		String path = "";
		// try {
		// path = Thread.currentThread().getContextClassLoader().getResource( 
		// if (path.endsWith("WEB-INF/classes/")
		// || path.endsWith("WEB-INF\\classes\\")) {
		// path = path.substring(0, path.length() - 16);
		// path = path.replaceAll("\\\\", "/");
		// path = path.replaceAll("//", "/");
		// }
		// } catch (Exception ex) {}
		// 介于WebSphere的路径问题，目前使用配置形式。
		String sql = "select sysvarvalue from ldsysvar where sysvar='ProductOutPath'";
		SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
		sqlbv10.sql(sql);
		ExeSQL tExeSQL = new ExeSQL();
		path = tExeSQL.getOneValue(sqlbv10);
		// 默认路径为:/productdef/deployscript/
		if (path == null)
			return "/";
		return path;
	}

	private static SimpleDateFormat hhmmss = new SimpleDateFormat("HHmmss");

	public static String getScripName(String Type, String env, String name) {
		if (Type == null || "".equals(Type) || env == null || "".equals(env)
				|| name == null || "".equals(name))
			return "";
		int ran = (int) (Math.random() * 100);
		String NAME = Type + "_" + env + "_" + name + "_"
				+ hhmmss.format(new Date()) + ran + ".sql";
		return NAME;

	}

	public static boolean ProductWriteToFile(String tFileName,
			ArrayList tContentList) {
		BufferedWriter bw = null;
		/*
		 * String tSQL = "select sysvarvalue from ldsysvar where
		 * sysvar='ProductDeployPath' "; ExeSQL tExeSQL = new ExeSQL(); String
		 * tFilePath = tExeSQL.getOneValue(tSQL);
		 */
		String tFilePath = getProductDeployPath();
		if (tFilePath == null || tFilePath.equals("")) {
			return false;
		}
		String pathr = getClassPath();
		tFilePath = pathr + tFilePath;
		tFilePath = tFilePath.replaceAll("\\\\", "/");
		tFilePath = tFilePath.replaceAll("//", "/");
		if (!tFilePath.endsWith("/"))
			tFilePath += "/";
		File f1 = new File(tFilePath);
		if (!f1.exists())
			f1.mkdirs();
		File f = new File(tFilePath + tFileName);
		int k = 1;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(f), "UTF-8"));

			for (int i = 0; i < tContentList.size(); i++, k++) {
				String tText = (String) tContentList.get(i);
				bw.write(tText);
				bw.newLine();
				if (k % 100 == 0) {
					// bw.write("commit;");
					bw.newLine();
				}
			}

			// os2.write("测试");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// outS.close();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
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
		return WorkCalendarService.isWorkDay(tDate, tType);
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
		return WorkCalendarService.WorkDaysLater(tDate, days, tType);
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
		return WorkCalendarService.WorkDayCount(tBeginDate, tEndDate, tType);
	}
	
	
    public static String getSeverRealPath() {
        int index = PubFun.class.getResource("").toString().lastIndexOf(
                "WEB-INF");
        String path = "";
        try {
            path = java.net.URLDecoder.decode(PubFun.class.getResource("").toString().substring(0,
                    index).replace("file:/", ""), "UTF-8");

            if (!path.subSequence(1, 2).equals(":")) {
                path = "/" + path;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return path;
    }

    /**
     * 过滤不安全的文件路径
     * @param path
     * @return
     */
    public static String getSafetyPath(String path){
    	String safePath = "";
    	safePath = path;
    	if(safePath.contains("..\\"))
    		safePath = safePath.replace("..\\", "");
		if(safePath.contains("../"))
			safePath = safePath.replace("../", "");
		if(safePath.contains("./"))
			safePath = safePath.replace("./", "");
    	return safePath;
    }

    
    public static String getSafetySQLParam(String sqlParam){
    	String safeSQLParam = "";
    	safeSQLParam = sqlParam;
    	
    	//safeSQLParam = StringEscapeUtils.escapeSql(safeSQLParam);
    	
    	String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"  
    			   + "(\\b(select|update|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";  
		
		Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		Matcher match= pattern.matcher(safeSQLParam.toLowerCase());
		if(match.find()){
			return null;
		}
    	
    	return safeSQLParam;
    }

	/**
	 * 向控制台輸出信息（同logger.debug()）
	 * 
	 * @param message
	 *            輸出信息
	 */
	public static String buildLog(Object message) {
		int size = 10;
		int count = 0;
		String[] classnames = new String[size];
		String[] functionnames = new String[size];
		String[] rownumbers = new String[size];
		Throwable mThrowable = new Throwable();
		StackTraceElement[] stackElements = mThrowable.getStackTrace();
		for (int i = 0; i < stackElements.length && i < size; i++) {
			classnames[i] = stackElements[i].getClassName();
			functionnames[i] = stackElements[i].getMethodName() + "()";
			rownumbers[i] = ":" + stackElements[i].getLineNumber();
			if (!classnames[i].startsWith("com.sinosoft")) {
				classnames[i] = classnames[i].substring(classnames[i]
						.lastIndexOf(".") + 1);
				classnames[i] = classnames[i].replaceAll("_jsp", "")
						.replaceAll("_", "") + ".jsp";
				if (functionnames[i].equals("_jspService()")) {
					functionnames[i] = "";
				}
				rownumbers[i] = "";
				count++;
				break;
			} else {
				classnames[i] = classnames[i].substring(classnames[i]
						.lastIndexOf(".") + 1) + ".java";
			}
			count++;
		}
		StringBuffer mStringBuffer = new StringBuffer();
		for (int i = count - 1; i > 0; i--) {
			if (i == count - 1) {
				mStringBuffer.append("Process: ["
						+ classnames[i] + rownumbers[i] + "] "
						+ functionnames[i]);
			} else {
				mStringBuffer.append(" --> [" + classnames[i] + rownumbers[i]
						+ "] " + functionnames[i]);
			}
		}

		return message.toString() + "\n\t" + mStringBuffer.toString();
	}
	/**
	 * 主函数，测试用
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		// String t="1112000*-";
		// logger.debug(t.toUpperCase());
		// PubFun tPunFun = new PubFun();
		// String test = tPunFun.calLapseDate("2004-02-02", "111502");
		// logger.debug(PubFun.calInterval("2004-01-01", "2005-01-01", "Y"));
		// logger.debug(PubFun.DateTimeBetween("makedate","maketime","2004-01-01",
		// "00:00:00", "2004-01-01","11:00:00"));
		// GlobalInput G = new GlobalInput();
		// G.LogID = "6377";
		// PubFun.logTrack(G, "010000", "0000000");
		// PubFun tPunFun = new PubFun();
		// tPunFun.isNumeric("100"+"200");
		// ArrayList t = new ArrayList();
		//		
		// t.add("213123");
		// t.add("中午");
		// t.add("Endgi");
		// PubFun.writeToFile("test.txt", t);
		// LDCodeDB tLDCodeDB = new LDCodeDB();
		// tLDCodeDB.setCodeType("111test");
		// tLDCodeDB.setCode(null);
		// tLDCodeDB.query();
//		PubFun.isWorkDay("2012-7-15", "2");
//		PubFun.WorkDaysLater("2012-7-18", 8, "2");
//		PubFun.WorkDayCount("2012-7-17", "2012-8-13", "2");
		
		logger.debug(PubFun.getSafetySQLParam(" 1 and ManageCom like #86431300#||#%# "));
	}
}
