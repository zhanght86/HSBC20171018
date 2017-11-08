/***********************************************************************
 * Module:  DimensionofDiseaseCode.java
 * Author:  lei
 * Purpose: Defines the Class DimensionofDiseaseCode
 ***********************************************************************/

package com.sinosoft.claimcalculator.dimension;

import org.apache.log4j.Logger;

import com.sinosoft.claimcalculator.ClaimCalculatorDimension;
import com.sinosoft.lis.db.LLCaseReceiptDB;
import com.sinosoft.lis.vschema.LLCaseReceiptSet;

public class DimensionofDiseaseCode extends ClaimCalculatorDimension {
	private static Logger logger = Logger
			.getLogger(DimensionofDiseaseCode.class);
	private String diseaseCode;

	// @Constructor
	public DimensionofDiseaseCode() {
		setDimensionCode("4");
		setDimensionName("疾病代码维度");
	}

	/**
	 * 对每一个Dimension判断当前理赔是否需要计算累加器，返回true需要计算，返回false不需要计算。
	 * 
	 */
	public boolean ifNeedCalculating() {
		// llcasereceipt.diseasecode，待确认
		if (this.diseaseCode == null || "".equals(this.diseaseCode)) {
			logger.info("累加器疾病代码为空！");
			return false;
		}
		LLCaseReceiptDB tLLCaseReceiptDB = new LLCaseReceiptDB();
		LLCaseReceiptSet tLLCaseReceiptSet = new LLCaseReceiptSet();
		tLLCaseReceiptDB.setRgtNo(this.mLLRegisterSchema.getRgtNo());
		tLLCaseReceiptSet = tLLCaseReceiptDB.query();
		if (tLLCaseReceiptSet == null || tLLCaseReceiptSet.size() <= 0) {
			logger.info("查询该理赔账单信息失败，clmno="
					+ this.mLLRegisterSchema.getRgtNo());
			return false;
		} else {
			for (int i = 1; i <= tLLCaseReceiptSet.size(); i++) {
				// 只要有一个能对应上就可以返回true了。
				if (this.diseaseCode.equals(tLLCaseReceiptSet.get(i)
						.getDiseaseCode())) {
					logger.info("满足疾病代码维度，将受本累加器控制！");
					return true;
				}
			}
		}
		logger.info("不满足疾病代码维度，不受本累加器控制！");
		return false;
	}

	public String getDiseaseCode() {
		return diseaseCode;
	}

	public void setDiseaseCode(String diseaseCode) {
		this.diseaseCode = diseaseCode;
	}

}