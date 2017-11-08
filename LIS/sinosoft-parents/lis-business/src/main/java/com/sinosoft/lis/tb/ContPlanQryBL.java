package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContPlanDutyParamDB;
import com.sinosoft.lis.db.LMRiskDutyDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContPlanRiskSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.vschema.LCContPlanDutyParamSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LMRiskDutySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: BL层业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @version 1.0
 * @date 2004-12-01
 */
public class ContPlanQryBL {
private static Logger logger = Logger.getLogger(ContPlanQryBL.class);
	public static void main(String args[]) {
		
		LCContPlanRiskSchema mmLCContPlanRiskSchema = new LCContPlanRiskSchema();
		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.ManageCom = "86";
		mGlobalInput.Operator = "001";
		mmLCContPlanRiskSchema.setContPlanCode("00");
		mmLCContPlanRiskSchema.setProposalGrpContNo("65487965423165");
		mmLCContPlanRiskSchema.setRiskCode("00152000");
		mmLCContPlanRiskSchema.setMainRiskCode("00152000");
		ContPlanQryBL tContPlanQryBL = new ContPlanQryBL();
		VData cInputData = new VData();
		cInputData.add(mmLCContPlanRiskSchema);
		cInputData.add(mGlobalInput);
		tContPlanQryBL.submitData(cInputData, "");
		VData mResult = new VData();
		mResult = tContPlanQryBL.getResult();
	}

	private LCContPlanDutyParamSet mDefualtLCContPlanDutyParamSet = new LCContPlanDutyParamSet();
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	private String mGetDutyKind = "";

	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 全局数据 */

	private LCContPlanDutyParamSet mLCContPlanDutyParamSet = new LCContPlanDutyParamSet();
	/** 业务处理相关变量 */
	private LCContPlanRiskSchema mLCContPlanRiskSchema = new LCContPlanRiskSchema();
	private LCDutySet mLCDutySet = new LCDutySet();

	/** 数据操作字符串 */
	private String mOperate;
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();

	private LCDutySchema oneDuty = new LCDutySchema();

	// @Constructor
	public ContPlanQryBL() {
	}

	/**
	 * 校验传入的数据
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean checkData() {
		if (mLCContPlanRiskSchema.getProposalGrpContNo() == null
				|| mLCContPlanRiskSchema.getProposalGrpContNo().equals("")
				|| mLCContPlanRiskSchema.getRiskCode() == null
				|| mLCContPlanRiskSchema.getRiskCode().equals("")
				|| mLCContPlanRiskSchema.getContPlanCode() == null
				|| mLCContPlanRiskSchema.getContPlanCode().equals("")
				|| mLCContPlanRiskSchema.getMainRiskCode() == null
				|| mLCContPlanRiskSchema.getMainRiskCode().equals("")) {
			CError tError = new CError();
			tError.moduleName = "ContPlanQryBL";
			tError.functionName = "checkData";
			tError.errorMessage = "未获得查询保险计划必要的信息!";
			this.mErrors.addOneError(tError);
		}
		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			// 保单险种保险计划
			mLCContPlanRiskSchema.setSchema((LCContPlanRiskSchema) mInputData
					.getObjectByObjectName("LCContPlanRiskSchema", 0));
			return true;
		} catch (Exception ex) {
			CError.buildErr(this,ex.toString());
			return false;

		}

	}

	/**
	 * 取得操作结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public TransferData getTransferData() {
		return mTransferData;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: void
	 */
	private void prepareOutputData() {

		mInputData.clear();

		mResult.clear();
		if (mLCDutySet.size() > 0) {
			logger.debug(mLCDutySet.encode());
			mResult.add(mLCDutySet);
		} else {
			logger.debug(oneDuty.encode());
			mResult.add(oneDuty);
			// mResult.add(mGetDutyKind);

		}
	}

	/**
	 * 根据传入的团单号码，险种计划编码，险种编码，查询险种计划和默认值中该险种下的所有信息， 准备到责任集合中.
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean queryData() {
		LCContPlanDutyParamDB tLCContPlanDutyParamDB = new LCContPlanDutyParamDB();
		tLCContPlanDutyParamDB.setContPlanCode(mLCContPlanRiskSchema
				.getContPlanCode());
		tLCContPlanDutyParamDB.setProposalGrpContNo(mLCContPlanRiskSchema
				.getProposalGrpContNo());
		tLCContPlanDutyParamDB.setRiskCode(mLCContPlanRiskSchema.getRiskCode());
		tLCContPlanDutyParamDB.setMainRiskCode(mLCContPlanRiskSchema
				.getMainRiskCode());
		String sql = "select * from LCContPlanDutyParam where ContPlanCode='"
				+ "?ContPlanCode?" + "' and GrpContNo='"
				+ "?GrpContNo?"
				+ "' and RiskCode='" + "?RiskCode?"
				+ "' and MainRiskCode='"
				+ "?MainRiskCode?" + "'"
				+ " and PlanType in ('0','3')";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("ContPlanCode", mLCContPlanRiskSchema.getContPlanCode());
		sqlbv.put("GrpContNo", mLCContPlanRiskSchema.getProposalGrpContNo());
		sqlbv.put("RiskCode", mLCContPlanRiskSchema.getRiskCode());
		sqlbv.put("MainRiskCode", mLCContPlanRiskSchema.getMainRiskCode());
		mLCContPlanDutyParamSet = tLCContPlanDutyParamDB.executeQuery(sqlbv);
		if (tLCContPlanDutyParamDB.mErrors.needDealError()) {
			CError.buildErr(this,"查询保险计划要素时失败!");
			return false;
		}

		// if (!mLCContPlanRiskSchema.getContPlanCode().equals("00"))
		// {//yaorywln
		// 如果不是默认值，还需要查出默认值要素
//		String calFactorSql = "select CalFactor from LCContPlanDutyParam "
//				+ "where ContPlanCode='"
//				+ mLCContPlanRiskSchema.getContPlanCode()
//				+ "' and ProposalGrpContNo='"
//				+ mLCContPlanRiskSchema.getProposalGrpContNo()
//				+ "' and Riskcode='" + mLCContPlanRiskSchema.getRiskCode()
//				+ "' and MainRiskCode='"
//				+ mLCContPlanRiskSchema.getMainRiskCode() + "'"
//				+ " and PlanType in ('0','3')";
//		String dutycodeSql = "select distinct dutycode from LCContPlanDutyParam "
//				+ "where ContPlanCode='"
//				+ mLCContPlanRiskSchema.getContPlanCode()
//				+ "' and ProposalGrpContNo='"
//				+ mLCContPlanRiskSchema.getProposalGrpContNo()
//				+ "' and Riskcode='"
//				+ mLCContPlanRiskSchema.getRiskCode()
//				+ "' and MainRiskCode='"
//				+ mLCContPlanRiskSchema.getMainRiskCode()
//				+ "'"
//				+ " and PlanType in ('0','3')";

		String dutySQL = "Select * from LCContPlanDutyParam where "
				+ "ProposalGrpContNo='"
				+ "?ProposalGrpContNo?"
				+ "' and Riskcode='" + "?Riskcode?"
				+ "' and ContPlanCode ='"
				+ "?ContPlanCode?" + "'";
		// and CalFactor not in "
		// + "(" + calFactorSql + ") and dutycode in "
		// + "(" + dutycodeSql + ")"
		// +" and PlanType in ('0','3')"

		logger.debug("dutySQL" + dutySQL);
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(dutySQL);
		sqlbv1.put("ProposalGrpContNo", mLCContPlanRiskSchema.getProposalGrpContNo());
		sqlbv1.put("RiskCode", mLCContPlanRiskSchema.getRiskCode());
		sqlbv1.put("ContPlanCode", mLCContPlanRiskSchema.getContPlanCode());
		mDefualtLCContPlanDutyParamSet = tLCContPlanDutyParamDB
				.executeQuery(sqlbv1);
		mLCContPlanDutyParamSet.add(mDefualtLCContPlanDutyParamSet);

		// }
		// 根据险种的责任准备责任信息
		LMRiskDutyDB mLMRiskDutyDB = new LMRiskDutyDB();
		mLMRiskDutyDB.setRiskCode(mLCContPlanRiskSchema.getRiskCode());
		LMRiskDutySet mLMRiskDutySet = new LMRiskDutySet();
		mLMRiskDutySet = mLMRiskDutyDB.query();
		if (mLMRiskDutyDB.mErrors.needDealError()) {
			CError.buildErr(this,"查询险种责任失败!");
			return false;
		}
		if (mLMRiskDutySet.size() == 1) {
			oneDuty.setDutyCode(mLMRiskDutySet.get(1).getDutyCode());
			if (!transPlanToDuty(oneDuty)) {
				CError.buildErr(this,"从保险计划向责任赋值失败!");
				return false;

			}
		} else {
			for (int i = 1; i <= mLMRiskDutySet.size(); i++) {
				LCDutySchema tempLCDutySchema = new LCDutySchema();
				tempLCDutySchema.setDutyCode(mLMRiskDutySet.get(i)
						.getDutyCode());
				if (!transPlanToDuty(tempLCDutySchema)) {
					CError.buildErr(this,"从保险计划向责任赋值失败!");
					return false;
				}
				if (!tempLCDutySchema.getDutyCode().equals(""))
					mLCDutySet.add(tempLCDutySchema);
			}
		}
		return true;
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		logger.debug("now in ContPlanQryBL submit");
		// 将外部传入的数据分解到本类的属性中，准备处理
		if (!getInputData())
			return false;
		logger.debug("---getInputData---");

		// 校验传入的数据
		if (!checkData())
			return false;

		// 根据业务逻辑对数据进行处理

		if (this.queryData() == false)
			return false;

		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();

		return true;
	}

	/**
	 * 根据查询出来的保险计划和默认值中的要素赋值到责任中，如果该责任在计划和默认值中 都没有描述，则不准备该责任
	 * 
	 * @param: tLCDutySchema
	 * @return: boolean
	 */
	private boolean transPlanToDuty(LCDutySchema tLCDutySchema) {
		String dutycode = tLCDutySchema.getDutyCode();

		// 判断该责任是否有值标记
		boolean dutyFlag = false;
		for (int i = 1; i <= mLCContPlanDutyParamSet.size(); i++) {
			String CalFactor = mLCContPlanDutyParamSet.get(i).getCalFactor();

			String CalFactorValue = mLCContPlanDutyParamSet.get(i)
					.getCalFactorValue();
			String CalDutyCode = mLCContPlanDutyParamSet.get(i).getDutyCode();

			/** Add by zhangxing 根据终止日期计算保险期间 */
			if (CalFactor.equals("InsuYear") && (CalFactorValue == null)) {
				String tSql = " select CalFactorValue from LCContPlanDutyParam where 1=1 "
						+ " and GrpContNo = '"
						+ "?GrpContNo?"
						+ "'"
						+ " and riskcode = '"
						+ "?riskcode?"
						+ "'and contplancode = '00'"
						+ " and MainRiskCode = '"
						+ "?MainRiskCode?"
						+ "'"
						+ " and dutycode = '"
						+ "?dutycode?"
						+ "'"
						+ " and CalFactor = 'EndDate'";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(tSql);
				sqlbv2.put("GrpContNo", mLCContPlanDutyParamSet.get(i).getProposalGrpContNo());
				sqlbv2.put("riskcode", mLCContPlanDutyParamSet.get(i).getRiskCode());
				sqlbv2.put("MainRiskCode", mLCContPlanDutyParamSet.get(i).getMainRiskCode());
				sqlbv2.put("dutycode", CalDutyCode);

				String ttSql = " select CalFactorValue from LCContPlanDutyParam where 1=1 "
						+ " and GrpContNo = '"
						+ "?GrpContNo?"
						+ "'"
						+ " and riskcode = '"
						+ "?riskcode?"
						+ "'and contplancode = '00'"
						+ " and MainRiskCode = '"
						+ "?MainRiskCode?"
						+ "'"
						+ " and dutycode = '"
						+ "?dutycode?"
						+ "'"
						+ " and CalFactor = 'InsuYearFlag'";
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql(ttSql);
				sqlbv3.put("GrpContNo", mLCContPlanDutyParamSet.get(i).getProposalGrpContNo());
				sqlbv3.put("riskcode", mLCContPlanDutyParamSet.get(i).getRiskCode());
				sqlbv3.put("MainRiskCode", mLCContPlanDutyParamSet.get(i).getMainRiskCode());
				sqlbv3.put("dutycode", CalDutyCode);
				
				String aSql = "select polapplydate from lcgrpcont where grpcontno = '"
						+ "?grpcontno?" + "'";
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(aSql);
				sqlbv4.put("grpcontno", mLCContPlanDutyParamSet.get(i).getGrpContNo());
				
				ExeSQL tExeSQL = new ExeSQL();
				String EndData = tExeSQL.getOneValue(sqlbv2);
				String InsuYearFlag = tExeSQL.getOneValue(sqlbv3);
				String PolApplyDate = tExeSQL.getOneValue(sqlbv4);
				int InsuYear = PubFun.calInterval(PolApplyDate, EndData,
						InsuYearFlag);

				CalFactor = "InsuYear";
				CalFactorValue = Integer.toString(InsuYear);
			}
			if (dutycode.trim().equals(CalDutyCode.trim())) {
				if (tLCDutySchema.getFieldIndex(CalFactor) == -1) {

					mTransferData.setNameAndValue(CalFactor, CalFactorValue);

				} else {
					tLCDutySchema.setV(CalFactor, CalFactorValue);
					dutyFlag = true;
				}
			}
		}
		if (!dutyFlag)
			tLCDutySchema.setDutyCode("");
		// 当计算规则为统一费率时，不将费率带到界面，在计算中重新到保险计划要素中取
		// if (StrTool.compareString(tLCDutySchema.getCalRule(), "1"))
		// {
		// tLCDutySchema.setFloatRate(0.0);
		// }
		return true;
	}

}
