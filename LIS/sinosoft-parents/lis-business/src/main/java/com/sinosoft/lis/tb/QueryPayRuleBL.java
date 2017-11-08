package com.sinosoft.lis.tb;
import java.text.DecimalFormat;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPayRuleFactoryDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMDutyPayRelaDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPayRuleFactorySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPayRuleFactorySet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LMDutyPayRelaSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Lis_New
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Caihy
 * @version 1.0
 */

public class QueryPayRuleBL {
private static Logger logger = Logger.getLogger(QueryPayRuleBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	private String mInsured = "";

	/** 接受前台传输数据的容器 */
	private TransferData mTransferData = new TransferData();

	private LCPayRuleFactorySchema mLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
	private LCPayRuleFactorySet mLCPayRuleFactorySet = new LCPayRuleFactorySet();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();

	/* 转换精确位数的对象 */
	private String FORMATMODOL = "0.00"; // 保费保额计算出来后的精确位数
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL); // 数字转换对象

	public QueryPayRuleBL() {
	}

	public boolean submitData(VData cInputData) {
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("after getInputData");
		if (!queryPayRule()) {
			return false;
		}
		logger.debug("after queryPayRule");
		return true;
	}

	private boolean getInputData(VData cInputData) {
		// mTransferData = (TransferData)cInputData.getObjectByObjectName(
		// "TransferData", 0);
		mLCPayRuleFactorySchema = (LCPayRuleFactorySchema) cInputData
				.getObjectByObjectName("LCPayRuleFactorySchema", 0);
		mLCPolSchema = (LCPolSchema) cInputData.getObjectByObjectName(
				"LCPolSchema", 0);
		mLCInsuredSchema = (LCInsuredSchema) cInputData.getObjectByObjectName(
				"LCInsuredSchema", 0);
		mInsured = mLCPolSchema.getInsuredNo();
		// mInsured = (String) mTransferData.getValueByName("InsuredNo");
		logger.debug("mInsured++++" + mLCPolSchema.getInsuredNo());
		return true;
	}

	private boolean queryPayRule() {
		LCPayRuleFactoryDB tLCPayRuleFactoryDB = new LCPayRuleFactoryDB();
		tLCPayRuleFactoryDB.setSchema(mLCPayRuleFactorySchema);
		LCPayRuleFactorySet tLCPayRuleFactorySet = new LCPayRuleFactorySet();
		tLCPayRuleFactorySet = tLCPayRuleFactoryDB.query();
		logger.debug("size:" + tLCPayRuleFactorySet.size());

		// 工作年限：系统中应记录员工的入司日期，工作年限=本次操作的业务生效日－入司日期；
		// 计划参与年限＝本次操作日期的业务生效日－该员工保单生效的日期
		// 本次（缴费）操作的业务生效日：承保缴费＝承保生效日，
		// 续期缴费＝续期缴费生效日（定期续期缴费取应缴费日期，不定期则为财务缴费的次日）。
		Vector tVector = getOpValiDate();
		String tOpValiDate = tVector.get(0).toString();
		String tCValiDate = tVector.get(1).toString();
		logger.debug("tOpValiDate:" + tOpValiDate);
		logger.debug("tCValiDate:" + tCValiDate);
		LCPremSet tLCPremSet = new LCPremSet();

		LCPayRuleFactorySchema tLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
		String tFactoryname = "";
		String tOtherNo = "";
		// String iFactoryname="";
		String tCalCode = "";

		for (int i = 1; i <= tLCPayRuleFactorySet.size(); i++) {
			tLCPayRuleFactorySchema = tLCPayRuleFactorySet.get(i);
			tFactoryname = tLCPayRuleFactorySchema.getFactoryName();
			tOtherNo = tLCPayRuleFactorySchema.getOtherNo();
			String strGrpContNo = tLCPayRuleFactorySchema.getGrpContNo();
			String strPayRuleCode = tLCPayRuleFactorySchema.getPayRuleCode();
			String tInerSerialNo = tLCPayRuleFactorySchema.getInerSerialNo();
			tCalCode = tLCPayRuleFactorySchema.getFactoryCode(); // 取得算法编码
			String tFactoryValue = "";
			PayRuleFee tCalculator = new PayRuleFee();
			if (tFactoryname.equals("ServiceYear")) { // 服务年限
				String tJoinCompanyDate = mLCInsuredSchema.getJoinCompanyDate();
				// 舍弃法算保单生效时员工的服务年限
				String tServiceYear = Integer.toString(PubFun.calInterval(
						tJoinCompanyDate, tOpValiDate, "Y"));
				logger.debug("serviceYear:" + tServiceYear
						+ "  OpValiDate:" + tOpValiDate + "JoinCompanyDate"
						+ tJoinCompanyDate);
				tCalculator.addBasicFactor(tFactoryname, tServiceYear);
				tFactoryValue = tServiceYear;
			} else { // JoinYear参加计划年限
				// 舍弃法算保单生效时员工的计划参与年限
				String tJoinYear = Integer.toString(PubFun.calInterval(
						tCValiDate, tOpValiDate, "Y"));
				tCalculator.addBasicFactor(tFactoryname, tJoinYear);
				tFactoryValue = tJoinYear;
			}
			tCalculator.addBasicFactor("InsuredNo", mInsured);
			tCalculator.addBasicFactor("ContNo", strGrpContNo);
			tCalculator = this.setParamsForDisk(tCalculator);
			tCalculator.testmCalFactors1();
			tCalculator.setCalCode(tCalCode);
			String tRate = tCalculator.calculateEx(strPayRuleCode,
					strGrpContNo, tFactoryname, tFactoryValue, tOtherNo,
					tInerSerialNo);
			double tSalary = mLCInsuredSchema.getSalary();
			logger.debug("tRate:" + tRate);
			if (!tRate.equals("0")) {
				LCPremSchema tLCPremSchema = new LCPremSchema();
				tLCPremSchema.setPayPlanCode(tLCPayRuleFactorySchema
						.getOtherNo());
				LMDutyPayRelaDB tLMDutyPayRelaDB = new LMDutyPayRelaDB();
				tLMDutyPayRelaDB.setPayPlanCode(tLCPremSchema.getPayPlanCode());
				LMDutyPayRelaSet tLMDutyPayRelaSet = new LMDutyPayRelaSet();
				tLMDutyPayRelaSet = tLMDutyPayRelaDB.query();
				tLCPremSchema.setDutyCode(tLMDutyPayRelaSet.get(1)
						.getDutyCode());
				// 交费编码
				// String tPrem =
				// mDecimalFormat.format(Double.parseDouble(tRate) * tSalary);
				// logger.debug("tPrem=" + tPrem);
				// tLCPremSchema.setPrem(tPrem);
				tLCPremSchema.setPrem(tRate);
				tLCPremSet.add(tLCPremSchema);
			}
		}
		if (tLCPremSet.size() > 0) {
			for (int i = 0; i < tLCPremSet.size(); i++) {
				logger.debug("tLCPremSchema.prem="
						+ tLCPremSet.get(i + 1).getPrem());
			}
		}
		mResult.add(tLCPremSet);
		return true;
	}

	// 取得本次（缴费）操作的业务生效日
	// 承保缴费＝承保生效日，
	// 续期缴费＝续期缴费生效日（定期续期缴费取应缴费日期，不定期则为财务缴费的次日）。
	private Vector getOpValiDate() {
		String tValiDate = ""; // 本次（缴费）操作的业务生效日
		String tCValiDate = ""; // 保单生效日
		String tAppFlag = mLCPolSchema.getInterestDifFlag(); // 0-承保缴费,1-续期缴费
		String tInsuredNo = mLCPolSchema.getInsuredNo();
		Vector tVector = new Vector();

		logger.debug("AppFlag:" + tAppFlag);
		logger.debug("InsuredNo" + tInsuredNo);
		if ("0".equals(tAppFlag)) { // 承保缴费
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setGrpContNo(mLCPayRuleFactorySchema.getGrpContNo());
			tLCGrpPolDB.setRiskCode(mLCPayRuleFactorySchema.getRiskCode());
			LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
			tLCGrpPolSet = tLCGrpPolDB.query();
			tValiDate = tLCGrpPolSet.get(1).getCValiDate();
			tCValiDate = tLCGrpPolSet.get(1).getCValiDate();

		} else if ("2".equals(tAppFlag)) {// 保全NI
			tCValiDate = mLCPolSchema.getCValiDate();
			tValiDate = mLCPolSchema.getCValiDate();
		} else { // 续期缴费
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setGrpContNo(mLCPayRuleFactorySchema.getGrpContNo());
			tLCPolDB.setRiskCode(mLCPayRuleFactorySchema.getRiskCode());
			tLCPolDB.setInsuredNo(tInsuredNo);
			LCPolSet tLCPolSet = new LCPolSet();
			tLCPolSet = tLCPolDB.query();
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(1);

			if (tLCPolSchema.getPayIntv() == -1) { // 不定期
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				String tSql = "select min(EnterAccDate)+1 days from ljtempfee where"
						+ " otherno = '"
						+ "?otherno?"
						+ "'"
						+ " and confflag = '0'";
				sqlbv.sql(tSql);
				sqlbv.put("otherno", tLCPolSchema.getGrpContNo());
				ExeSQL tExeSQL = new ExeSQL();
				tValiDate = tExeSQL.getOneValue(sqlbv);
				if (tValiDate == null || tValiDate == "") {
					tValiDate = PubFun.getCurrentDate();
				}

			} else { // 定期
				SQLwithBindVariables tsqlbv = new SQLwithBindVariables();
				String tSql = "select max(paydate) from ljspaygrp where"
						+ " grpcontno = '" + "?grpcontno?" + "'";
				tsqlbv.sql(tSql);
				tsqlbv.put("grpcontno", tLCPolSchema.getGrpContNo());
				ExeSQL tExeSQL = new ExeSQL();
				tValiDate = tExeSQL.getOneValue(tsqlbv);
			}
			tCValiDate = tLCPolSchema.getCValiDate();
		}
		tVector.add(tValiDate);
		tVector.add(tCValiDate);
		return tVector;
	}

	public VData getResult() {
		return mResult;
	}

	private PayRuleFee setParamsForDisk(PayRuleFee tCalculator) {
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setGrpContNo(mLCPayRuleFactorySchema.getGrpContNo());
		tLCInsuredDB.setInsuredNo(mLCPolSchema.getInsuredNo());
		int insuredCount = tLCInsuredDB.getCount();
		logger.debug("count:" + insuredCount);
		// 磁盘投保，从磁盘文件中取得工资
		// if(insuredCount==0){
		String tSalary = String.valueOf(mLCInsuredSchema.getSalary());
		tCalculator.addBasicFactor("Salary", tSalary);
		String tPrem = String.valueOf(mLCPolSchema.getPrem());
		tCalculator.addBasicFactor("Prem", tPrem);
		// }
		return tCalculator;
	}

	public static void main(String[] args) {
		LCPayRuleFactorySchema tLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
		tLCPayRuleFactorySchema.setGrpContNo("20060608000002");
		tLCPayRuleFactorySchema.setRiskCode("212403");
		tLCPayRuleFactorySchema.setPayRuleCode("A");

		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema.setInterestDifFlag("0");
		tLCPolSchema.setInsuredNo("0000009080");

		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		// logger.debug("JoinCompanyDate->"+request.getParameter("JoinCompanyDate"));
		tLCInsuredSchema.setJoinCompanyDate("2004-06-01");
		tLCInsuredSchema.setInsuredNo("0000009080");
		tLCInsuredSchema.setSalary("2000");

		VData tVData = new VData();
		tVData.addElement(tLCPayRuleFactorySchema);
		// tVData.addElement(tTransferData);
		tVData.addElement(tLCPolSchema);
		tVData.addElement(tLCInsuredSchema);

		QueryPayRuleBL tQueryPayRuleBL = new QueryPayRuleBL();
		if (!tQueryPayRuleBL.submitData(tVData)) {
			logger.debug(tQueryPayRuleBL.mErrors.getFirstError());
		} else {
			LCPremSet tLCPremSet = new LCPremSet();
			tLCPremSet = (LCPremSet) tQueryPayRuleBL.mResult
					.getObjectByObjectName("LCPremSet", -1);
			for (int i = 0; i < tLCPremSet.size(); i++) {
				String tPayPlanCode = "";
				String tPrem = "";
				tPayPlanCode = tLCPremSet.get(i + 1).getPayPlanCode()
						.toString();
				tPrem = String.valueOf(tLCPremSet.get(i + 1).getPrem());
				logger.debug("paypalncode:" + tPayPlanCode + "rate:"
						+ tPrem);
			}
		}
	}

}
