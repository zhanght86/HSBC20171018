/***********************************************************************
 * Module:  PaidPerDayCalculator.java
 * Author:  
 * Purpose: 累加要素（类型）：
 * 4-天金额
 ***********************************************************************/

package com.sinosoft.claimcalculator.element;

import org.apache.log4j.Logger;

import com.sinosoft.claimcalculator.ClaimCalculatorElement;
import com.sinosoft.claimcalculator.pubfun.CalculatorPubFun;
import com.sinosoft.lis.schema.LCalculatorTraceSchema;
import com.sinosoft.utility.ExeSQL;

public class PaidPerDayCalculator extends ClaimCalculatorElement {
	private static Logger logger = Logger.getLogger(PaidPerDayCalculator.class);
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
		// 此时的this.mApplyPay已经是被前一个天数累加器调整过的了，可能跟账单金额不同，要取二者中较小的一个
		if (this.mApplyPay > this.mLLClaimDutyFeeSchema.getCalSum()) {
			tCurClaimPaidSum = this.mLLClaimDutyFeeSchema.getCalSum();
		} else {
			tCurClaimPaidSum = this.mApplyPay;
		}
		try {

			//LLClaimCalAutoBL tLLClaimCalAutoBL = new LLClaimCalAutoBL();
			// double tCurClaimDays =
			// tLLClaimCalAutoBL.getSumDayCount(this.mLLClaimDetailSchema);
			double tCurClaimDays = CalculatorPubFun
					.getSumDayCount(mLLClaimDutyFeeSchema);
			LCalculatorTraceSchema tLCalculatorTraceSchema = new LCalculatorTraceSchema();
			if ("1".equals(this.type.getTypeCode())) {
				// 1-限额

				// 天限额累加器只管控制当前账单不超总限额，无需考虑其他已赔或者已计算的历史账单；其他累加限额控制由限额累加器去做

				// 现在this.FactorValue里存的是天限额，tCurClaimDays里存的是天数
				double tSumLimit = this.factorValue * tCurClaimDays;

				double tUsedLimit = 0;

				// 本待赔，取申请金额
				tLCalculatorTraceSchema.setApplyPay(this.mApplyPay);

				// 如果超过限额就按限额赔，否则按申请金额赔：
				if (tCurClaimPaidSum > tSumLimit) {
					// 总待赔
					tLCalculatorTraceSchema.setSumApplyPay(tCurClaimPaidSum);
					// 赔付
					tLCalculatorTraceSchema.setUsedLimit(tSumLimit);
					// 余额
					tLCalculatorTraceSchema.setSumNotPay(tCurClaimPaidSum
							- tSumLimit);
					// 总已赔
					tLCalculatorTraceSchema.setSumPaid(tSumLimit);
					tUsedLimit = tSumLimit;
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
				// 2-免赔额 不应存在这种分支
				return tCurClaimPaidSum;
			} else if ("3".equals(this.type.getTypeCode())) {
				// 3-计算(公式) 不应存在这种分支
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

	/**
	 * 经本次理赔后累加器的要素值（余额）处理。
	 * 
	 */
	public boolean dealFactorValue() {
		return false;
	}

	public int getPaidDays() {
		return paidDays;
	}

	public void setPaidDays(int paidDays) {
		this.paidDays = paidDays;
	}

}