package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskComCtrlDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCUrgeLogSchema;
import com.sinosoft.lis.vschema.LCUrgeLogSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LMRiskAppSet;
import com.sinosoft.lis.vschema.LMRiskComCtrlSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;

/**
 * <p>
 * Title:
 * </p>
 * 财务通用方法
 * <p>
 * Description:
 * </p>
 * 将财务通用的方法进行整合
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author Gaoht
 * @version 1.0
 */

public class FinFeePubFun {
private static Logger logger = Logger.getLogger(FinFeePubFun.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类* */
	public CErrors mErrors = new CErrors();

	public FinFeePubFun() {
	}

	/**
	 * 校验单证是否在财务处回收
	 * 
	 * @param String
	 *            CertifyCode 单证编码
	 * @return String Result 1--需要财务回收 0--不需要 Other---Other 可扩展至 （如返回打印回收标志）
	 */

	public static String CertifyTakeBackCheck(String CertifyCode) {
		String Result = "";
		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeDB.setCode(CertifyCode);
		tLDCodeDB.setCodeType("certifyflag");
		tLDCodeDB.getInfo();
		Result = tLDCodeDB.getOtherSign();
		if (Result == null || Result.equals("")) {
			Result = "0";
		}
		return Result;
	}

	/**
	 * 判断暂收费是否部分到帐 供财务记账及退费处理 For Example : 新契约交费 100 其中 现金30 支票70
	 * 则在交费时点100中，30到账，70未到帐。此时撤单只退30
	 * 
	 * @param String
	 *            OthernoType "TempFeeNo":暂收据号 "OtherNo":其他号码
	 * @param String
	 *            No 实际传入号码
	 * @param int
	 *            t when 1 then 返回已到帐金额 else 返回未到帐金额
	 * @return LJTempFeeClassSet tLJTempFeeClassSet
	 */

	public static LJTempFeeClassSet PartEnterAccGet(String OthernoType,
			String No, int t) {
		LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
		String PayModeSql = "select * from ljtempfeeclass where " + OthernoType
				+ " = '?No?'";
		if (t == 1)
			PayModeSql = PayModeSql
					+ " and enteraccdate is not null and enteraccdate <> '3000-1-1' ";
		else
			PayModeSql = PayModeSql
					+ " and (enteraccdate is null or enteraccdate = '3000-1-1') ";

		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(PayModeSql);
		sqlbv.put("No", No);
		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		tLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(sqlbv);
		return tLJTempFeeClassSet;
	}

	/**
	 * 错误日志公共方法 LCUrgeLog 为公用表纪录发生的错误日志
	 * 
	 * @param tErrors
	 *            错误容器
	 * @param OperatorType
	 *            业务类型
	 * @param YWNo
	 *            业务号
	 * @param OtherNo
	 *            其他号码
	 * @return map 提交的map
	 */

	public static MMap ErrorLogSet(CErrors tErrors, String OperatorType,
			String YWNo, String OtherNo) {
		MMap map = new MMap();
		if (tErrors.getErrorCount() > 0) {
			LCUrgeLogSet tLCUrgeLogSet = new LCUrgeLogSet();
			for (int i = 0; i < tErrors.getErrorCount(); i++) {
				LCUrgeLogSchema tLCUrgeLogSchema = new LCUrgeLogSchema();
				tLCUrgeLogSchema.setTempFeeNo(YWNo);
				tLCUrgeLogSchema.setPolNo(OtherNo);
				tLCUrgeLogSchema.setMakeDate(PubFun.getCurrentDate());
				tLCUrgeLogSchema.setMakeTime(PubFun.getCurrentTime());
				tLCUrgeLogSchema.setManageCom(OperatorType);
				tLCUrgeLogSchema
						.setErrorMessage(tErrors.getError(i).errorMessage);
				tLCUrgeLogSet.add(tLCUrgeLogSchema);
			}
			map.put(tLCUrgeLogSet, "DELETE&INSERT");
		}
		return map;
	}

	/**
	 * 校验收费险种地域限制
	 * 
	 * @param tErrors
	 *            错误容器
	 * @param OperatorType
	 *            业务类型
	 * @param YWNo
	 *            业务号
	 * @param OtherNo
	 *            其他号码
	 * @return map 提交的map
	 */
	public static boolean CheckRiskAtCom(String cRiskCOde, String cManageCom,
			String cDate) {
		logger.debug("=============开始险种地域校验=============");
		String tSql = "select * from lmriskapp where 1=1" + " and riskcode='?cRiskCOde?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("cRiskCOde", cRiskCOde);
		logger.debug("地域校验1====================" + tSql);
		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		LMRiskAppSet tLMRiskAppSet = new LMRiskAppSet();
		tLMRiskAppSet = tLMRiskAppDB.executeQuery(sqlbv);
		String tBuff = "";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		
		if (tLMRiskAppSet.size() == 0) {
			return true;
		}
		if (cManageCom.length() == 2) {

		} else if (cManageCom.length() == 4) {
			tBuff = " and comcode in ('?cManageCom?')";
			sqlbv1.put("cManageCom", cManageCom);
		} else if (cManageCom.length() == 6) {
			String c1 = cManageCom.substring(0, 4);
			tBuff = " and comcode in ('?comcode?')";
			ArrayList<String> strArr=new ArrayList<String>();
			strArr.add(cManageCom);
			strArr.add(c1);
			sqlbv1.put("comcode", strArr);
		} else if (cManageCom.length() == 8) {
			String c1 = cManageCom.substring(0, 4);
			String c2 = cManageCom.substring(0, 6);
			tBuff = " and comcode in ('?comcode?')";
			ArrayList<String> strArr=new ArrayList<String>();
			strArr.add(cManageCom);
			strArr.add(c1);
			strArr.add(c2);
			sqlbv1.put("comcode", strArr);
		} else {
			return false;
		}
		// 查询分类区ldcomgrptocom
		tSql = "select * from LMRiskComCtrl where 1=1"
				+ " and riskcode = '?cRiskCOde?'"
				+ " and  StartDate <= '?cDate?'"
				// 险种定义终止日期从零时计算，所以ENDDATA必须大于
				+ " and (EndDate > '?cDate?' or EndDate is null)"
				+ " and trim(managecomgrp) in (select trim(comgrpcode) from ldcomgrptocom where 1=1 "
				+ tBuff + ")";
		logger.debug("地域校验2====================" + tSql);
		sqlbv1.sql(tSql);
		sqlbv1.put("cRiskCOde", cRiskCOde);
		sqlbv1.put("cDate", cDate);
		
		LMRiskComCtrlDB tLMRiskComCtrlDB = new LMRiskComCtrlDB();
		LMRiskComCtrlSet tLMRiskComCtrlSet = new LMRiskComCtrlSet();
		tLMRiskComCtrlSet = tLMRiskComCtrlDB.executeQuery(sqlbv1);
		if (tLMRiskComCtrlSet.size() == 0) {
			return false;
		}
		return true;
	}

	public static Date calOFDate(Date baseDate, int interval, String unit,
			Date validDate) {
//		Date returnDate = null;
//
//		GregorianCalendar mCalendar = new GregorianCalendar();
//		// 设置起始日期格式
//		mCalendar.setTime(baseDate);
//		if (unit.equals("Y")) {
//			mCalendar.add(Calendar.YEAR, interval);
//		}
//		if (unit.equals("M")) {
//			// 执行月份增减
//			mCalendar.add(Calendar.MONTH, interval);
//		}
//		if (unit.equals("D")) {
//			mCalendar.add(Calendar.DATE, interval);
//		}
//
//		if (compareDate != null) {
//			GregorianCalendar cCalendar = new GregorianCalendar();
//			// 设置坐标日期
//			cCalendar.setTime(compareDate);
//
//			int mYears = mCalendar.get(Calendar.YEAR);
//			int mMonths = mCalendar.get(Calendar.MONTH);
//			int cMonths = cCalendar.get(Calendar.MONTH);
//			int cDays = cCalendar.get(Calendar.DATE);
//
//			if (unit.equals("Y")) {
//				cCalendar.set(mYears, cMonths, cDays);
//				if (mMonths < cCalendar.get(Calendar.MONTH)) {
//					cCalendar.set(mYears, mMonths + 1, 0);
//				}
//				if (cCalendar.before(mCalendar)) {
//					mCalendar.set(mYears + 1, cMonths, cDays);
//					returnDate = mCalendar.getTime();
//				} else {
//					returnDate = cCalendar.getTime();
//				}
//			}
//			if (unit.equals("M")) {
//				cCalendar.set(mYears, mMonths, cDays);
//
//				if (mMonths < cCalendar.get(Calendar.MONTH)) {
//					// 取当前月的最后一天日期
//					cCalendar.set(mYears, mMonths + 1, 0);
//				}
//				if (cCalendar.before(mCalendar)) {
//					mCalendar.set(mYears, mMonths + 1, cDays);
//					returnDate = mCalendar.getTime();
//				} else {
//					returnDate = cCalendar.getTime();
//				}
//			}
//			if (unit.equals("D")) {
//				returnDate = mCalendar.getTime();
//			}
//		} else {
//			returnDate = mCalendar.getTime();
//		}
//
//		return returnDate;
		
		GregorianCalendar mCalendar = new GregorianCalendar();
        mCalendar.setTime(baseDate);

        if (unit.equals("Y"))
                        mCalendar.add(Calendar.MONTH, interval * 12);
        if (unit.equals("M"))
                        mCalendar.add(Calendar.MONTH, interval);
        if (unit.equals("D"))
                        mCalendar.add(Calendar.DATE, interval);

        if (validDate != null) {
                        if (unit.equals("M") || unit.equals("Y")) {
                                        GregorianCalendar cCalendar = new GregorianCalendar();
                                        cCalendar.setTime(validDate);
                                        if (mCalendar.get(Calendar.DATE) < cCalendar.get(Calendar.DATE)) {
                                        	mCalendar.set(Calendar.DATE,
                                        			mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) > cCalendar.get(Calendar.DATE) ? 
                                        					cCalendar.get(Calendar.DATE): 
                                        						mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                                        }
                        }
        }
        return mCalendar.getTime();
        /* 算法核心思想：
         * comment by jiaqiangli 2008-11-01
		 * //A = baseDate //B = baseDate + interval //C = validate
		 * //YYYY属性代表日期YYYY-MM-DD中的YYYY //MM属性代表日期YYYY-MM-DD中的MM
		 * //DD属性代表日期YYYY-MM-DD中的DD if (B. DD< C. DD) { //这里隐含的逻辑是B. DD一定<= C.DD，
		 * 因为Java中的mCalendar.add方法相当于对月份做增减，日取原日期中的日与目标月的最大值之间的较小者，换言之不会超过目标月的最后一天。如2008-01-31加一个月后为2008-02-29日。
		 * return B.YYYY || B.MM || min(B.MM的最后一天,C.DD);
		 * //经过前面的add运算，年月两个属性都已经固定下来，但是日要取目标月的最后一天与保单生效日期的日中两者的较小者，例如保单生效日是2007-01-30日，第一个交费后的paytodate为2008-02-28，第二次计算paytodate时，中间的目标日期为2008-02-28+1个月=2008-03-28，3月的最后一天为31，而保单生效日为30，故此时的paytodate为2008-30-30。 }
		 */
	}
	
	/*函数重载 Date VS String*/
	/*calOFDate函数还有一个简单算法：每次都切换到cvalidate进行日期的增减*/
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
}
