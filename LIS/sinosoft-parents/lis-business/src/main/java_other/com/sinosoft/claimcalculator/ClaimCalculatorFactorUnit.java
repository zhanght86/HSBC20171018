/***********************************************************************
 * Module:  ClaimCalculatorFactorUnit.java
 * Author:  
 * Purpose: Defines the Class ClaimCalculatorFactorUnit
 ***********************************************************************/

package com.sinosoft.claimcalculator;

public class ClaimCalculatorFactorUnit {
	private String factorUnitCode;
	/*
	 * 要素单位代码 1-金额（元） 2-天 3-次 4-小时
	 */
	private String factorUnitName;

	public String getFactorUnitCode() {
		return factorUnitCode;
	}

	public void setFactorUnitCode(String factorUnitCode) {
		this.factorUnitCode = factorUnitCode;
	}

	public String getFactorUnitName() {
		return factorUnitName;
	}

	public void setFactorUnitName(String factorUnitName) {
		this.factorUnitName = factorUnitName;
	}

}