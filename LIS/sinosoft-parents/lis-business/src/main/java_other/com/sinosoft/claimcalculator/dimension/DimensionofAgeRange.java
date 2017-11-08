/***********************************************************************
 * Module:  DimensionofAgeRange.java
 * Author:  lei
 * Purpose: Defines the Class DimensionofAgeRange
 ***********************************************************************/

package com.sinosoft.claimcalculator.dimension;

import org.apache.log4j.Logger;

import com.sinosoft.claimcalculator.ClaimCalculatorDimension;
import com.sinosoft.lis.pubfun.PubFun;

public class DimensionofAgeRange extends ClaimCalculatorDimension {
	private static Logger logger = Logger
			.getLogger(DimensionofAgeRange.class);
	private int validStartAge;
	private int validEndtAge;

	// @Constructor
	public DimensionofAgeRange() {
		setDimensionCode("5");
		setDimensionName("年龄范围维度");
	}

	/**
	 * 对每一个Dimension判断当前理赔是否需要计算累加器，返回true需要计算，返回false不需要计算。
	 * 
	 */
	public boolean ifNeedCalculating() {
		String tAccidentDate = this.mLLCaseSchema.getInHospitalDate();
		if (tAccidentDate == null || "".equals(tAccidentDate)) {
			logger.info("出险日期为空！");
			return false;
		}
		int tAge = PubFun.calInterval(this.mLLCaseSchema.getCustBirthday(), 
				tAccidentDate, "Y");
		if (this.validStartAge < 0 || this.validEndtAge<0) {
			logger.info("累加器年龄范围设定错误！");
			return false;
		}
		if (validStartAge<validEndtAge && tAge>=validStartAge && tAge<validEndtAge) {
			logger.info("满足年龄范围维度，将受本累加器控制！");
			return true;
		} else {
			logger.info("不满足年龄范围维度，不受本累加器控制！");
			return false;
		}
	}

	public int getValidStartAge() {
		return validStartAge;
	}

	public void setValidStartAge(int validStartAge) {
		this.validStartAge = validStartAge;
	}
	
	public int getValidEndAge() {
		return validEndtAge;
	}

	public void setValidEndAge(int validEndtAge) {
		this.validEndtAge = validEndtAge;
	}

}