/***********************************************************************
 * Module:  FactorTypeDef.java
 * Author:  
 * Purpose: Defines the Class FactorTypeDef
 ***********************************************************************/

package com.sinosoft.claimcalculator;

public class FactorTypeDef {
	/**
	 * 1-金额 2-天数 3-次数 4-天金额 5-次金额
	 */
	private String factorTypeCode;
	private String factorTypeName;

	public String getFactorTypeCode() {
		return factorTypeCode;
	}

	public void setFactorTypeCode(String factorTypeCode) {
		this.factorTypeCode = factorTypeCode;
	}

	public String getFactorTypeName() {
		return factorTypeName;
	}

	public void setFactorTypeName(String factorTypeName) {
		this.factorTypeName = factorTypeName;
	}

}