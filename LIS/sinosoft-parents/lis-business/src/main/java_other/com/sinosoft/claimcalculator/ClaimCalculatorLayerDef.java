/***********************************************************************
 * Module:  ClaimCalculatorLayerDef.java
 * Author:  
 * Purpose: Defines the Class ClaimCalculatorLayerDef
 ***********************************************************************/

package com.sinosoft.claimcalculator;

public class ClaimCalculatorLayerDef {
	/**
	 * 1.保障责任（给付责任） 2.责任 3.连生被保人 4.产品 5.被保人（多被保人-家庭单的情况） 6.保单
	 * 7.个人（个险系统累加器只包含以上7中层级） 8.计划 9.团单
	 * 
	 */
	private int layerCode;
	private String layerName;

	public int getLayerCode() {
		return layerCode;
	}

	public void setLayerCode(int layerCode) {
		this.layerCode = layerCode;
	}

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

}