/***********************************************************************
 * Module:  DimensionofInsuranceTime.java
 * Author:  
 * Purpose: Defines the Class DimensionofInsuranceTime
 ***********************************************************************/

package com.sinosoft.claimcalculator.dimension;

import org.apache.log4j.Logger;

import com.sinosoft.claimcalculator.ClaimCalculatorDimension;

public class DimensionofInsuranceTime extends ClaimCalculatorDimension {
	private static Logger logger = Logger
			.getLogger(DimensionofInsuranceTime.class);
	private int validPeriod;
	/**
	 * Y-年 S-季 M-月 W-周 D-天
	 * 
	 */
	private String validPeriodUnit;

	// @Constructor
	public DimensionofInsuranceTime() {
		setDimensionCode("2");
		setDimensionName("保单保险年期时间维度");
	}

	/**
	 * 对每一个Dimension判断当前理赔是否需要计算累加器，返回true需要计算，返回false不需要计算。
	 */
	public boolean ifNeedCalculating() {

		// TODO: implement
		String tCValidate = this.mLLClaimDetailSchema.getCValiDate();
		//xuyunpeng change 一律改为出险日期
		String tAccidentDate = this.mLLCaseSchema.getAccDate();
		if (tAccidentDate == null || "".equals(tAccidentDate)) {
			logger.info("出险日期为空！");
			return false;
		}
		if (tCValidate == null || "".equals(tCValidate)) {
			logger.info("保险起期为空！");
			return false;
		}
		if (this.validPeriod <= 0) {
			logger.info("累加器有效期设定错误！");
			return false;
		}
		if (this.validPeriodUnit == null || "".equals(this.validPeriodUnit)) {
			logger.info("累加器有效期时间单位设定错误！");
			return false;
		}
		// 保险年期维度现在仅用于累加器的“年赔付额”类的控制，即以保单每年的生效对应日为起点对该保单年度理赔限额控制
		// 因此，在ifNeedCalculating方法中暂不做以下日期控制——只要以上描述数据没有错误就直接返回true
		logger.info("满足保单保险年期时间维度，将受本累加器控制！");
		return true;
		
	}

	public int getValidPeriod() {
		return validPeriod;
	}

	public void setValidPeriod(int validPeriod) {
		this.validPeriod = validPeriod;
	}

	public String getValidPeriodUnit() {
		return validPeriodUnit;
	}

	public void setValidPeriodUnit(String validPeriodUnit) {
		this.validPeriodUnit = validPeriodUnit;
	}

}