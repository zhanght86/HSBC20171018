/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.claimcalculator.pubfun;

import java.text.ParseException;
import java.util.Date;
import org.apache.log4j.Logger;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.schema.LLClaimDutyFeeSchema;

public class CalculatorPubFun {
	private static Logger logger = Logger.getLogger(CalculatorPubFun.class);

	public CalculatorPubFun() {
	}

	/**
	 * 计算日期currentDate 在StartDate与EndDate之间
	 * 
	 * @param
	 * @Example "2012-02-23"在"2012-02-23"之间"2012-02-23"
	 * @param compareDate
	 * @return
	 */
	public static boolean isBetweenDate(String currentDate, String StartDate,
			String EndDate) {
		try {
			FDate tFDate = new FDate();
			Date tStateDate = tFDate.getDate(StartDate);
			Date tEndDate = tFDate.getDate(EndDate);
			Date tCurrentDate = tFDate.getDate(currentDate);
			if (!tStateDate.after(tEndDate)) {
				if ((!tCurrentDate.before(tStateDate))
						&& (!tCurrentDate.after(tEndDate))) {
					return true;
				} else {
					return false;
				}
			} else {
				logger.info("开始日期晚于结束日期！！！！");
				return false;
			}
		} catch (Exception e) {
			logger.info("传入的日期格式错误！！！！");
			return false;
		}

	}

	/*
	 * 从传入的账单记录中获取住院天数
	 */
	public static int getSumDayCount(LLClaimDutyFeeSchema tLLClaimDutyFeeSchema) {
		if (tLLClaimDutyFeeSchema == null) {
			return 0;
		} else {
			return tLLClaimDutyFeeSchema.getDayCount();
		}
	}

	/**
	 * 主函数，测试用
	 * 
	 * @param args
	 *            String[]
	 * @throws ParseException
	 */
	public static void main(String[] args) {

	}

}