/***********************************************************************
 * Module:  ClaimCalculatorDimension.java
 * Author:  
 * Purpose: Defines the Class ClaimCalculatorDimension
 ***********************************************************************/

package com.sinosoft.claimcalculator;

import com.sinosoft.lis.schema.LLCaseSchema;
import com.sinosoft.lis.schema.LLClaimDetailSchema;
import com.sinosoft.lis.schema.LLClaimDutyFeeSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;

public abstract class ClaimCalculatorDimension {
	private String dimensionCode;
	private String dimensionName;

	protected LLClaimDetailSchema mLLClaimDetailSchema;
	protected LLRegisterSchema mLLRegisterSchema;
	protected LLCaseSchema mLLCaseSchema;
	protected LLClaimDutyFeeSchema mLLClaimDutyFeeSchema;

	public void setLLRegisterSchema(LLRegisterSchema tLLRegisterSchema) {
		this.mLLRegisterSchema = tLLRegisterSchema;
	}
	public void setLLCaseSchema(LLCaseSchema tLLCaseSchema) {
		this.mLLCaseSchema = tLLCaseSchema;
	}
	public void setLLClaimDetailSchema(LLClaimDetailSchema tLLClaimDetailSchema) {
		this.mLLClaimDetailSchema = tLLClaimDetailSchema;
	}

	public void setLLClaimDutyFeeSchema(
			LLClaimDutyFeeSchema tLLClaimDutyFeeSchema) {
		this.mLLClaimDutyFeeSchema = tLLClaimDutyFeeSchema;
	}

	/**
	 * 对每一个Dimension判断当前理赔是否需要计算累加器，返回true需要计算，返回false不需要计算。
	 * 具体的业务逻辑在4个具体的累加器维度对象里实现。
	 * 
	 */
	public abstract boolean ifNeedCalculating();

	/**
	 * @param tDimensionCode
	 */
	public void setDimensionCode(String tDimensionCode) {
		this.dimensionCode = tDimensionCode;
	}

	/**
	 * @param tDimensionCode
	 */
	public String getDimensionCode(String tDimensionCode) {
		return this.dimensionCode;
	}

	/**
	 * @param tDimensionName
	 */
	public void setDimensionName(String tDimensionName) {
		this.dimensionName = tDimensionName;
	}

	public String getDimensionName() {
		return this.dimensionName;
	}

}