/***********************************************************************
 * Module:  ClaimCalculatorTypeDef.java
 * Author:  
 * Purpose: Defines the Class ClaimCalculatorTypeDef
 ***********************************************************************/

package com.sinosoft.claimcalculator;

public class ClaimCalculatorTypeDef {
	/**
	 * 1-限额 2-免赔额 3-计算(公式)
	 * 
	 */
	private String typeCode;
	private String typeName;

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}