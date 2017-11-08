/***********************************************************************
 * Module:  DimensionofFeeCode.java
 * Author:  
 * Purpose: Defines the Class DimensionofFeeCode
 ***********************************************************************/

package com.sinosoft.claimcalculator.dimension;

import org.apache.log4j.Logger;

import com.sinosoft.claimcalculator.ClaimCalculatorDimension;

public class DimensionofFeeCode extends ClaimCalculatorDimension {
	private static Logger logger = Logger.getLogger(DimensionofFeeCode.class);
	private String feeCode;

	// @Constructor
	public DimensionofFeeCode() {
		setDimensionCode("3");
		setDimensionName("账单费用类型维度");
	}

	/**
	 * 对每一个Dimension判断当前理赔是否需要计算累加器，返回true需要计算，返回false不需要计算。
	 * 
	 */
	public boolean ifNeedCalculating() {
		// TODO: implement
		if (this.feeCode == null || "".equals(this.feeCode)) {
			logger.info("累加器费用类型代码为空！");
			return false;
		}
		int tLengthOfFeeCode = this.feeCode.length();

		if (this.mLLClaimDutyFeeSchema != null
				&& this.mLLClaimDutyFeeSchema.getDutyFeeCode() != null) {
			if (this.feeCode.equals(this.mLLClaimDutyFeeSchema.getDutyFeeCode()
					.substring(0, tLengthOfFeeCode))) {
				logger.info("满足账单费用类型维度，将受本累加器控制！");
				return true;
			}
		}
		logger.info("不满足账单费用类型维度，不受本累加器控制！");
		return false;
	}

	public String getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

}