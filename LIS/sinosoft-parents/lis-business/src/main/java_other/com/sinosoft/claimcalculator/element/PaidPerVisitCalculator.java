/***********************************************************************
 * Module:  PaidPerVisitCalculator.java
 * Author:  
 * Purpose: 累加要素（类型）：
 * 5-次金额
 ***********************************************************************/

package com.sinosoft.claimcalculator.element;

import org.apache.log4j.Logger;

import com.sinosoft.claimcalculator.ClaimCalculatorElement;
import com.sinosoft.lis.schema.LCalculatorTraceSchema;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;

public class PaidPerVisitCalculator extends ClaimCalculatorElement {
	private static Logger logger = Logger
			.getLogger(PaidPerVisitCalculator.class);
	private int paidDays;

	/**
	 * 得到经累加器控制后的理赔赔付金额。
	 * 
	 */
	public double getClaimCalculationResultDetail() {
		/*
		 * 天金额累加器的控制要领： 1.累加器顺序描述时必须控制：天金额累加器的计算一定是紧随着一个天数累加器的
		 * 2.由于前一个天数累加器计算时只看天数、无视账单金额，本累加器的输入金额应该=min(天数累加器计算结果金额,账单申请金额)
		 * 3.当输入金额/天数>本累加器规定的天限额时，返回计算结果为天限额*天数；否则直接返回输入金额 ——次金额累加器的控制与上雷同
		 */

		// 本次理赔申请金额：即累加器控制前的理赔金额
		double tCurClaimPaidSum = 0;
		// 此时的this.mApplyPay已经是被前一个次数累加器调整过的了，可能跟账单金额不同，要取二者中较小的一个
		if (this.mApplyPay > this.mLLClaimDutyFeeSchema.getCalSum()) {
			tCurClaimPaidSum = this.mLLClaimDutyFeeSchema.getCalSum();
		} else {
			tCurClaimPaidSum = this.mApplyPay;
		}
		try {

			LCalculatorTraceSchema tLCalculatorTraceSchema = new LCalculatorTraceSchema();
			if ("1".equals(this.type.getTypeCode())) {
				// 1-限额

				// 次限额累加器只管控制当前账单不超限额，无需考虑其他已赔或者已计算的历史账单；其他累加限额控制由限额累加器去做

				// 现在this.FactorValue里存的是次限额

				double tUsedLimit = 0;

				// 本待赔，取申请金额
				tLCalculatorTraceSchema.setApplyPay(this.mApplyPay);
				
				//modify by wsp 2016-04-07
				//添加次数/天数，赔付金额大于每次/每天金额上限*次数/天数时，赔付每次/每天金额上限*次数/天数
				int tCount = 1;
				if("5".equals(this.factorType.getFactorTypeCode())){
					tCount = this.mLLClaimDutyFeeSchema.getTimes();
				}else if("4".equals(this.factorType.getFactorTypeCode())){
					tCount = this.mLLClaimDutyFeeSchema.getDayCount();
				}

				// 如果超过限额就按限额赔，否则按申请金额赔：
				if (tCurClaimPaidSum > this.factorValue*tCount) {
					// 总待赔
					tLCalculatorTraceSchema.setSumApplyPay(tCurClaimPaidSum);
					// 赔付
					tLCalculatorTraceSchema.setUsedLimit(this.factorValue*tCount);
					// 余额
					tLCalculatorTraceSchema.setSumNotPay(tCurClaimPaidSum
							- this.factorValue*tCount);
					// 总已赔
					tLCalculatorTraceSchema.setSumPaid(this.factorValue*tCount);
					tUsedLimit = this.factorValue*tCount;
				} else {
					// 总待赔
					tLCalculatorTraceSchema.setSumApplyPay(tCurClaimPaidSum);
					// 赔付
					tLCalculatorTraceSchema.setUsedLimit(tCurClaimPaidSum);
					// 余额
					tLCalculatorTraceSchema.setSumNotPay(0);
					// 总已赔
					tLCalculatorTraceSchema.setSumPaid(tCurClaimPaidSum);
					tUsedLimit = tCurClaimPaidSum;
				}

				// 保存累加器轨迹
				if (!dealCalculatorTraceNew(tLCalculatorTraceSchema)) {
					tUsedLimit = 0;
				}
				return tUsedLimit;
			} else if ("2".equals(this.type.getTypeCode())) {
				// 2-免赔额 不应有这种分支
				return tCurClaimPaidSum;
			} else if ("3".equals(this.type.getTypeCode())) {
				// 3-计算(公式) 不应有这种分支
				return tCurClaimPaidSum;
			} else {
				logger.info("PaidSumCalculator中传入了系统不支持的累加器类型，代码="
						+ this.type.getTypeCode());
				return 0;
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			return 0;
		}

	}

	// 获取本次理赔之前的累加器天数
	// ——这个方法不用了 20150506
	public double getRemainedDays() {
		// 累加器的原始值是this.tRemainedLimint，本方法要获取本保单累加器之前的轨迹，再从该原始值中扣除

		double tRemainedDays = 0;
		String tSql = "select sum(usedlimit) from LCalculatorTrace";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		String tWherePart = getTraceQryWherePart();
		tSql += tWherePart;

		logger.info(tSql);
		if (tSql.indexOf(" and ") == -1) {
			return 0;
		} else {
			ExeSQL mExeSQL = new ExeSQL();
			String tRetStr = mExeSQL.getOneValue(sqlbv);
			if (tRetStr == null || "".equals(tRetStr)) {
				tRetStr = "0";
			}
			tRemainedDays = this.factorValue - Double.valueOf(tRetStr);
			return tRemainedDays;
		}
	}

	// 获取本次理赔申请天数
	// 方法是用ClmNo对LLCaseReceipt.daycount这个字段求Sum，目前看来这是最合理的方法
	// ——这个方法不用了 20150506
	public double getCurClaimDays() {
		double tCurDays = 0;
		String tSql = "select (case when sum(daycount) is null then 0 else sum(daycount) end) from LLCaseReceipt where clmno='" 
				+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("clmno", this.mLLClaimDetailSchema.getClmNo());
		ExeSQL mExeSQL = new ExeSQL();
		tCurDays = Double.valueOf(mExeSQL.getOneValue(sqlbv1));
		return tCurDays;
	}

	/**
	 * 经本次理赔后累加器的要素值（余额）处理。
	 * 
	 * @pdOid 71f36c5d-cd50-42c7-b9f9-4d58cf3c2695
	 */
	public boolean dealFactorValue() {
		// TODO: implement
		return false;
	}

	public int getPaidDays() {
		return paidDays;
	}

	public void setPaidDays(int paidDays) {
		this.paidDays = paidDays;
	}

}