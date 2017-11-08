package com.sinosoft.lis.pubfun;

import org.apache.log4j.Logger;

import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;

/**
 * <p>Title：保费计算</p>
 * 
 * <p>Description：计算标准保费</p>
 * 
 * <p>Copyright：Copyright (c) 2012</p>
 * 
 * <p>Company: Sinosoft</p>
 * 
 * @author 杨治纲
 * @version 8.0
 */
public class CalPremBL {
	
	private static Logger logger = Logger.getLogger(CalPremBL.class);
	
	public CalPremBL() {
		
	}
	
	/**
	 * 计算标准保费
	 * @param tTransferData
	 * @return
	 */
	public double calStandardPrem(TransferData tTransferData, String[][] tWeightedArr) {
		
		double tStandardPrem = 0.0;
		
		if (tTransferData==null) {
			return tStandardPrem;
		}
		
		String tRiskCode = (String)tTransferData.getValueByName("RiskCode");
		String tDutyCode = (String)tTransferData.getValueByName("DutyCode");
		String tPayPlanCode = (String)tTransferData.getValueByName("PayPlanCode");
		if (tRiskCode==null || "".equals(tRiskCode) || tDutyCode==null || "".equals(tDutyCode)) {
			return tStandardPrem;
		}
		
		//免赔天数DeductibleDays计算保费时需转换为免赔额Deductibles
		String tDeductibleDays = (String)tTransferData.getValueByName("DeductibleDays");
		if (tDeductibleDays!=null && !"".equals(tDeductibleDays)) {
			tTransferData.setNameAndValue("Deductibles", tDeductibleDays);
		}
		
		StringBuffer tStrBuff = new StringBuffer();
		tStrBuff = new StringBuffer();
		tStrBuff.append("select d.calsql ");
		tStrBuff.append("from lmriskduty a, lmdutypayrela b, lmdutypay c, lmcalmode d ");
		tStrBuff.append("where a.dutycode=b.dutycode and b.payplancode=c.payplancode and c.calcode=d.calcode ");
		tStrBuff.append("and a.riskcode='"+ "?tRiskCode?" +"' and b.dutycode='"+ "?tDutyCode?" +"' ");
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql("select count(1) from lmdutypayrela where dutycode='"+ "?d1?" +"'");
		sqlbv1.put("d1", tDutyCode);
		ExeSQL tExeSQL = new ExeSQL();
		int tPayPlanCodeCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv1));
		if (tPayPlanCodeCount==0) {
			
			return tStandardPrem;
		} else if (tPayPlanCodeCount>1) {
			
			if (tPayPlanCode==null || "".equals(tPayPlanCode)) {
				return tStandardPrem;
			}
			
			tStrBuff.append("and c.payplancode='"+ "?tPayPlanCode?" +"'");
		}
		SQLwithBindVariables sqlbva = new SQLwithBindVariables();
		sqlbva.sql(tStrBuff.toString());
		sqlbva.put("tRiskCode", tRiskCode);
		sqlbva.put("tDutyCode", tDutyCode);
		sqlbva.put("tPayPlanCode", tPayPlanCode);
		String tCalSQL = tExeSQL.getOneValue(sqlbva);
		if (tCalSQL==null || "".equals(tCalSQL)) {
			return tStandardPrem;
		}
		
		//处理加权问题
		if (tWeightedArr==null || tWeightedArr.length==0) {
			tStandardPrem = calPrem(tTransferData, tCalSQL);
		} else {
			
			int count = 0;
			
			String[][] tTempWeightedArr = new String[tWeightedArr.length][3];
			for (int i=0;i<tWeightedArr.length;i++) {
				
				int index = tCalSQL.indexOf(tWeightedArr[i][0]);
				if (index!=-1) {
					tTempWeightedArr[count++] = tWeightedArr[i];
				}
			}
			
			logger.debug("count=====" + count);
			
			if (count==0) {
				tStandardPrem = calPrem(tTransferData, tCalSQL);
			} else {
				
				String[][] tFinalWeightedArr = new String[count][3];
				for (int i=0;i<count;i++) {
					tFinalWeightedArr[i] = tTempWeightedArr[i];
				}
				
				tStandardPrem = calWeightedPrem(tTransferData, tCalSQL, tFinalWeightedArr);
			}
		}
		
		return tStandardPrem;
	}
	
	/**
	 * 处理加权问题
	 * @param tTransferData
	 * @param tCalSQL
	 * @param tWeightedArr
	 * @return
	 */
	private double calWeightedPrem(TransferData tTransferData, String tCalSQL, String[][] tWeightedArr) {
		
		String tFactor = tWeightedArr[0][0];
		String[] tFactorValueArr = tWeightedArr[0][1].split(",");
		String[] tFactorWeightArr = tWeightedArr[0][2].split(",");
		
		logger.debug("Factor=====" + tFactor);
		
		double tPremSum = 0.0;
		double tWeightSum = 0.0;
		
		for (int i=0;i<tFactorValueArr.length;i++) {
			
			if (Double.parseDouble(tFactorWeightArr[i])<0) {
				tFactorWeightArr[i] = "0";
			}
			
			tTransferData.removeByName(tFactor);
			tTransferData.setNameAndValue(tFactor, tFactorValueArr[i]);
			
			double tPrem = 0.0;
			double tWeight = Double.parseDouble(tFactorWeightArr[i]);
			
			if (tWeightedArr.length==1) {
				tPrem = calPrem(tTransferData, tCalSQL);
			} else {
				
				String[][] tTempWeightedArr = new String[tWeightedArr.length-1][];
				for(int j=1;j<tWeightedArr.length;j++){
					tTempWeightedArr[j-1]=tWeightedArr[j];
				}
				
				tPrem = calWeightedPrem(tTransferData, tCalSQL, tTempWeightedArr);
			}
			
			tPremSum = Arith.add(tPremSum, Arith.mul(tPrem, tWeight));
			tWeightSum = Arith.add(tWeightSum, tWeight);
		}
		
		double tStandardPrem = Arith.div(tPremSum, tWeightSum, 8);
		
		return tStandardPrem;
	}
	
	/**
	 * 通过因子代入SQL计算
	 * @param tTransferData
	 * @param tCalSQL
	 * @return
	 */
	private double calPrem(TransferData tTransferData, String tCalSQL) {
		
		double tStandardPrem = 0.0;
		
		PubCalculator tPubCalculator = new PubCalculator();
		tPubCalculator.addBasicFactor(tTransferData);
		tPubCalculator.setCalSql(tCalSQL);
		String tResult = tPubCalculator.calculate();
		if (tResult!=null && !"".equals(tResult)) {
			tStandardPrem = Double.parseDouble(tResult);
		}
		
		return tStandardPrem;
	}
}
