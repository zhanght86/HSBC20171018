



package com.sinosoft.lis.reinsure.tools;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.reinsure.calculate.manage.RIRecordLog;
import com.sinosoft.lis.schema.RIRecordSchema;
import com.sinosoft.lis.schema.RIRecordTraceSchema;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @zhangbin
 * @version 1.0
 */

public class RIPubFun {
	public CErrors mErrors = new CErrors();
	private String[][] logInfo = { { "00", "再保任务生成" }, { "01", "业务数据提取" },
			{ "02", "数据校验" }, { "03", "再保方案分配" }, { "04", "风险计算" },
			{ "08", "分保数据生成" }, { "10", "初始化参数" }, { "11", "分保计算项计算" },
			{ "98", "删除再保任务" }, { "99", "分保数据处理" } };

	public RIPubFun() {
	}

	/**
	 * 将一大号段分割成若干小号段.
	 * 
	 * @param startNo
	 *            String 起始号
	 * @param endNo
	 *            String 终止号
	 * @param intv
	 *            int 号段长度
	 * @return String[][]
	 * @例: splitSerialNo("N01B000100","N01B000200",50) 返回结果: N01B000100
	 *     N01B000149 N01B000150 N01B000199 N01B000200 N01B000200
	 */
	public String[][] splitSerialNo(String startNo, String endNo, int intv) {
		RISplitSerialNo tRISplitSerialNo = new RISplitSerialNo();
		String[][] segArr = tRISplitSerialNo
				.splitSerialNo(startNo, endNo, intv);
		if (!tRISplitSerialNo.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tRISplitSerialNo.mErrors);
		}
		if (segArr == null) {
			this.mErrors.copyAllErrors(tRISplitSerialNo.mErrors);
		}
		return segArr;
	}

	public boolean recordWFLog(RIWFLogSchema riWFLogSchema, String appPath) {
		RIRecordLog tRIRecordLog = new RIRecordLog();
		VData tVData = new VData();
		tVData.add(riWFLogSchema);
		if (!tRIRecordLog.submitData(tVData, "01")) {
			this.mErrors.copyAllErrors(tRIRecordLog.mErrors);
			return false;
		}
		if (!recordLog(riWFLogSchema, appPath, "01")) {
			return false;
		}
		return true;
	}

	public String getCessRecordSerialNo() {
		RIGetSerialNo tRIGetSerialNo = new RIGetSerialNo();
		String tSerialN0 = tRIGetSerialNo.getSerialNo("01");
		if (tSerialN0 == null) {
			this.mErrors.copyAllErrors(tRIGetSerialNo.mErrors);
		}
		return tSerialN0;
	}

	/**
	 * 
	 * @param riWFLogSchema
	 *            RIWFLogSchema
	 * @param operate
	 *            String 目前operate都是 01
	 * @return boolean
	 */
	public boolean recordLog(RIWFLogSchema riWFLogSchema, String appPath,
			String operate) {
		String logStr = "";
		// if (operate.equals("01")) {
		// for (int i = 0; i < logInfo.length; i++) {
		// if (riWFLogSchema.getNodeState().equals(logInfo[i][0])) {
		// logStr = "累计风险编号为：" + riWFLogSchema.getTaskCode() + "   "
		// + logInfo[i][1] + "完成 ";
		// break;
		// }
		// }
		// }
		// RILog tRILog = new RILog();
		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("LOG", logStr);
		// tTransferData.setNameAndValue("PATH", appPath);
		// VData vData = new VData();
		// vData.add(tTransferData);
		// vData.add(riWFLogSchema);
		//
		// if (!tRILog.submitData(vData, operate)) {
		// this.mErrors.copyAllErrors(tRILog.mErrors);
		// return false;
		// }
		return true;
	}

	/**
	 * 
	 * @param riWFLogSchema
	 *            RIWFLogSchema
	 * @param log
	 *            String
	 * @param operate
	 *            String
	 * @return boolean
	 */
	public boolean recordLog(RIWFLogSchema riWFLogSchema, String log,
			String appPath, String operate) {
		// RILog tRILog = new RILog();
		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("LOG", log);
		// tTransferData.setNameAndValue("PATH", appPath);
		// VData vData = new VData();
		// vData.add(tTransferData);
		// vData.add(riWFLogSchema);
		//
		// if (!tRILog.submitData(vData, operate)) {
		// this.mErrors.copyAllErrors(tRILog.mErrors);
		// return false;
		// }
		return true;
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
	 * 比较两个日期的大小
	 * 
	 * @param if StartDate>EndDate,return fasle else return true
	 * @param compareDate
	 * @return
	 */
	public static boolean checkDate(String StartDate, String EndDate) {
		FDate tFDate = new FDate();
		Date tStateDate = tFDate.getDate(StartDate);
		Date tEndDate = tFDate.getDate(EndDate);
		return (tStateDate.after(tEndDate)) ? false : true;
	}

	public static String[] getDateArray(String startDate, String endDate) {
		if (!checkDate(startDate, endDate)) {
			return null;
		}
		int len = calInterval(startDate, endDate, "D");
		String[] retDate = new String[len + 1];
		retDate[0] = endDate;
		for (int i = 1; i <= len; i++) {
			retDate[i] = PubFun.calDate(retDate[i - 1], -1, "D", null);
		}
		return retDate;
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
			interval *= 12;
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
			sCalendar.set(sYears, sMonths, sDays);
			eCalendar.set(eYears, eMonths, eDays);
			long lInterval = (eCalendar.getTime().getTime() - sCalendar
					.getTime().getTime()) / 86400000;
			interval = (int) lInterval;
		}
		return interval;
	}

	public static boolean isLastDay(String date, String unit) {
		String[] ymd = date.split("-");
		int year = Integer.parseInt(ymd[0]);
		int month = Integer.parseInt(ymd[1]);
		int dayOfMonth = Integer.parseInt(ymd[2]);
		String afterDate = PubFun.calDate(date, 1, "D", null);
		String[] afterymd = afterDate.split("-");
		int afterYear = Integer.parseInt(afterymd[0]);
		int afterMonth = Integer.parseInt(afterymd[1]);
		int afterDayOfMonth = Integer.parseInt(afterymd[2]);

		if (afterMonth != month) {
			if ("Q".equals(unit)) {
				if (afterMonth == 1 || afterMonth == 4 || afterMonth == 7
						|| afterMonth == 10) {
					return true;
				}
				return false;
			} else if ("M".equals(unit)) {
				return true;
			}
		} else {
			return false;
		}
		return false;
	}

	public static void fillDefaultField(Object o, GlobalInput g) {
		Class[] c = new Class[1];
		Method m = null;

		try {
			c[0] = Class.forName("java.lang.String");
		} catch (Exception ex) {
		}
		try {
			m = o.getClass().getMethod("setOperator", c);
		} catch (Exception ex) {
		}
		try {
			m.invoke(o, g.Operator);
		} catch (Exception ex) {
		}
		try {
			m = o.getClass().getMethod("setManageCom", c);
		} catch (Exception ex) {
		}
		try {
			m.invoke(o, g.ManageCom);
		} catch (Exception ex) {
		}
		try {
			m = o.getClass().getMethod("setMakeDate", c);
		} catch (Exception ex) {
		}
		try {
			m.invoke(o, PubFun.getCurrentDate());
		} catch (Exception ex) {
		}
		try {
			m = o.getClass().getMethod("setModifyDate", c);
		} catch (Exception ex) {
		}
		try {
			m.invoke(o, PubFun.getCurrentDate());
		} catch (Exception ex) {
		}
		try {
			m = o.getClass().getMethod("setMakeTime", c);
		} catch (Exception ex) {
		}
		try {
			m.invoke(o, PubFun.getCurrentTime());
		} catch (Exception ex) {
		}
		try {
			m = o.getClass().getMethod("setModifyTime", c);
		} catch (Exception ex) {
		}
		try {
			m.invoke(o, PubFun.getCurrentTime());
		} catch (Exception ex) {
		}
	}

	public static boolean compare(RIRecordSchema riRecordSchema,
			RIRecordTraceSchema riRecordTraceSchema) {
		if (riRecordTraceSchema.getAccumulateDefNO().equals(
				riRecordSchema.getAccumulateDefNO())
				&& riRecordTraceSchema.getOtherNo().equals(
						riRecordSchema.getOtherNo())
				&& riRecordTraceSchema.getContNo().equals(
						riRecordSchema.getContNo())
				&& riRecordTraceSchema.getRIPreceptNo().equals(
						riRecordSchema.getRIPreceptNo())
				&& riRecordTraceSchema.getAreaID().equals(
						riRecordSchema.getAreaID())
				&& riRecordTraceSchema.getRiskCode().equals(
						riRecordSchema.getRiskCode())
				&& riRecordTraceSchema.getDutyCode().equals(
						riRecordSchema.getDutyCode())) {
			return true;
		} else {
			return false;
		}
	}

	public static String[] getMonthBetween(String runDate) {
		String[] retDateArr = new String[2];
		String[] ymd = runDate.split("-");
		int year = Integer.parseInt(ymd[0]);
		int month = Integer.parseInt(ymd[1]);

		String strSQL = "SELECT caldate FROM (select * from LDWorkCalendar a WHERE a.datetype = 'Y' and year='"
				+ year
				+ "' and MONTH(a.caldate)='"
				+ month
				+ "' ORDER BY a.caldate DESC) WHERE rownum=1";
		String tempDate = new ExeSQL().getOneValue(strSQL);

		if (PubFun.calInterval2(runDate, tempDate, "D") == 0) {
			retDateArr[1] = runDate;
		} else {
			return null;
		}

		strSQL = "SELECT caldate FROM (select * from LDWorkCalendar a WHERE a.datetype = 'Y' and year=YEAR('"
				+ PubFun.calDate(runDate, -1, "M", null)
				+ "') and MONTH(a.caldate)= MONTH('"
				+ PubFun.calDate(runDate, -1, "M", null)
				+ "') ORDER BY a.caldate DESC) WHERE rownum=1";
		tempDate = new ExeSQL().getOneValue(strSQL);
		retDateArr[0] = PubFun.calDate(tempDate, 1, "D", null);

		return retDateArr;
	}
	
	public static void main(String[] args) {
	}
}


