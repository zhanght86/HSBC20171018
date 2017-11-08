package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDPlanDutyParamDB;
import com.sinosoft.lis.db.LMRiskDutyDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LDPlanRiskSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LDPlanDutyParamSet;
import com.sinosoft.lis.vschema.LMRiskDutySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class LDPlanQryBL {
private static Logger logger = Logger.getLogger(LDPlanQryBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();
	private TransferData tTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 全局数据 */

	/** 业务处理相关变量 */
	private LDPlanRiskSchema mLDPlanRiskSchema = new LDPlanRiskSchema();
	private LDPlanDutyParamSet mLDPlanDutyParamSet = new LDPlanDutyParamSet();
	private LDPlanDutyParamSet mDefualtLDPlanDutyParamSet = new LDPlanDutyParamSet();

	private LCDutySchema oneDuty = new LCDutySchema();
	private LCDutySet mLCDutySet = new LCDutySet();
	private String mGetDutyKind = "";
	private String mPubAmntFlag = ""; // 公共保额导入标记
	private String mMult = "";
	private String mCValidate = "";

	/**
	 * sql绑定变量类
	 */
	private SQLwithBindVariables sqlbv= new SQLwithBindVariables();
	public LDPlanQryBL() {
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
		if (!getInputData()) {
			return false;
		}
		logger.debug("---getInputData---");

		// 校验传入的数据
		if (!checkData()) {
			return false;
		}

		// 根据业务逻辑对数据进行处理

		if (this.queryData() == false) {
			return false;
		}

		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();

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
			// 全局变量
			// mGlobalInput.setSchema( (GlobalInput)
			// mInputData.getObjectByObjectName(
			// "GlobalInput", 0));
			//
			// mTransferData = (TransferData) mInputData.getObjectByObjectName(
			// "mTransferData", 0);
			// 保单险种保险计划
			mLDPlanRiskSchema.setSchema((LDPlanRiskSchema) mInputData
					.getObjectByObjectName("LDPlanRiskSchema", 0));
			tTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			if (tTransferData != null) {
				mPubAmntFlag = (String) tTransferData
						.getValueByName("PubAmntFlag");
				mMult = (String) tTransferData.getValueByName("Mult");
				logger.debug("mMult=" + mMult);
				mCValidate = (String) tTransferData.getValueByName("CValidate");
			}
			return true;
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "ProposalBL";
			tError.functionName = "checkData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			return false;

		}

	}

	/**
	 * 校验传入的数据
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean checkData() {
		if (mLDPlanRiskSchema.getRiskCode() == null
				|| mLDPlanRiskSchema.getRiskCode().equals("")
				|| mLDPlanRiskSchema.getContPlanCode() == null
				|| mLDPlanRiskSchema.getContPlanCode().equals("")
				|| mLDPlanRiskSchema.getMainRiskCode() == null
				|| mLDPlanRiskSchema.getMainRiskCode().equals("")) {
			CError tError = new CError();
			tError.moduleName = "ContPlanQryBL";
			tError.functionName = "checkData";
			tError.errorMessage = "未获得查询保险计划必要的信息!";
			this.mErrors.addOneError(tError);
		}
		return true;
	}

	/**
	 * 根据传入的团单号码，险种计划编码，险种编码，查询险种计划和默认值中该险种下的所有信息， 准备到责任集合中.
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean queryData() {
		LDPlanDutyParamDB tLDPlanDutyParamDB = new LDPlanDutyParamDB();
		tLDPlanDutyParamDB.setContPlanCode(mLDPlanRiskSchema.getContPlanCode());
		tLDPlanDutyParamDB.setRiskCode(mLDPlanRiskSchema.getRiskCode());
		tLDPlanDutyParamDB.setMainRiskCode(mLDPlanRiskSchema.getMainRiskCode());
		//update by cxq 修改绑定变量
		sqlbv = new SQLwithBindVariables();
		String sql = "select * from LDPlanDutyParam where ContPlanCode='?ContPlanCode?' and RiskCode='?RiskCode?' and MainRiskCode='?MainRiskCode?'"
				+ " and PlanType in ('0','3')";
		sqlbv.sql(sql);
		sqlbv.put("ContPlanCode", mLDPlanRiskSchema.getContPlanCode());
		sqlbv.put("RiskCode", mLDPlanRiskSchema.getRiskCode());
		sqlbv.put("MainRiskCode", mLDPlanRiskSchema.getMainRiskCode());
		mLDPlanDutyParamSet = tLDPlanDutyParamDB.executeQuery(sqlbv);
		if (tLDPlanDutyParamDB.mErrors.needDealError()) {
			CError tError = new CError();
			tError.moduleName = "ContPlanQryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "查询保险计划要素时失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (!mLDPlanRiskSchema.getContPlanCode().equals("00")) {
			// 如果不是默认值，还需要查出默认值要素
			//update by cxq 修改绑定变量
			sqlbv = new SQLwithBindVariables();
			String calFactorSql = "select CalFactor from LDPlanDutyParam "
					+ "where ContPlanCode='?ContPlanCode?' and Riskcode='?RiskCode?' and MainRiskCode='?MainRiskCode?'"
					+ " and PlanType in ('0','3')";
			String dutycodeSql = "select distinct dutycode from LDPlanDutyParam "
					+ "where ContPlanCode='?ContPlanCode?' and Riskcode='?RiskCode?' and MainRiskCode='?MainRiskCode?'"
					+ " and PlanType in ('0','3')";

			String dutySQL = "Select * from LDPlanDutyParam where "
					+ "Riskcode='?RiskCode?' and ContPlanCode ='00' and CalFactor not in " + "("
					+ calFactorSql + ") and dutycode in " + "(" + dutycodeSql
					+ ")" + " and PlanType in ('0','3')";
			sqlbv.sql(dutySQL);
			sqlbv.put("ContPlanCode", mLDPlanRiskSchema.getContPlanCode());
			sqlbv.put("RiskCode", mLDPlanRiskSchema.getRiskCode());
			sqlbv.put("MainRiskCode", mLDPlanRiskSchema.getMainRiskCode());
			logger.debug("dutySQL" + sqlbv.sql());

			mDefualtLDPlanDutyParamSet = tLDPlanDutyParamDB
					.executeQuery(sqlbv);
			mLDPlanDutyParamSet.add(mDefualtLDPlanDutyParamSet);

		}
		// 根据险种的责任准备责任信息
		LMRiskDutyDB mLMRiskDutyDB = new LMRiskDutyDB();
		mLMRiskDutyDB.setRiskCode(mLDPlanRiskSchema.getRiskCode());
		if (mPubAmntFlag != null && mPubAmntFlag != ""
				&& mPubAmntFlag.equals("Y")) {
			mLMRiskDutyDB.setSpecFlag("A"); // 如果是公共保额导入，则只查询公共保额责任的默认值
		} else {
			mLMRiskDutyDB.setSpecFlag("N"); // 2005-11-24TPA险过滤掉公共帐户对应的责任
		}

		LMRiskDutySet mLMRiskDutySet = new LMRiskDutySet();
		mLMRiskDutySet = mLMRiskDutyDB.query();
		if (mLMRiskDutyDB.mErrors.needDealError() || mLMRiskDutySet == null
				|| mLMRiskDutySet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "ContPlanQryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "查询险种责任失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mLMRiskDutySet.size() == 1) {
			oneDuty.setDutyCode(mLMRiskDutySet.get(1).getDutyCode());
			// LMDutyDB mLMDutyDB=new LMDutyDB();
			// mLMDutyDB.setDutyCode(mLMRiskDutySet.get(1).getDutyCode());
			// mLMDutyDB.getInfo();
			// oneDuty.setOperator(mLMDutyDB.getDutyName());//将险种代码放到
			if (!transPlanToDuty(oneDuty)) {
				CError tError = new CError();
				tError.moduleName = "ContPlanQryBL";
				tError.functionName = "queryData";
				tError.errorMessage = "从保险计划向责任赋值失败!";
				this.mErrors.addOneError(tError);
				return false;

			}
			oneDuty.setMult(mMult);
		} else {
			for (int i = 1; i <= mLMRiskDutySet.size(); i++) {
				LCDutySchema tempLCDutySchema = new LCDutySchema();
				tempLCDutySchema.setDutyCode(mLMRiskDutySet.get(i)
						.getDutyCode());
				if (!transPlanToDuty(tempLCDutySchema)) {
					CError tError = new CError();
					tError.moduleName = "ContPlanQryBL";
					tError.functionName = "queryData";
					tError.errorMessage = "从保险计划向责任赋值失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				tempLCDutySchema.setMult(mMult);
				if (!tempLCDutySchema.getDutyCode().equals("")) {
					mLCDutySet.add(tempLCDutySchema);
				}

			}

		}

		return true;
	}

	/**
	 * 根据查询出来的保险计划和默认值中的要素赋值到责任中，如果该责任在计划和默认值中 都没有描述，则不准备该责任
	 * 
	 * @param: tLCDutySchema
	 * @return: boolean
	 */
	private boolean transPlanToDuty(LCDutySchema tLCDutySchema) {
		// Class dutyClaass = tLCDutySchema.getClass();
		String dutycode = tLCDutySchema.getDutyCode();
		// Field[] dutyField = dutyClaass.getDeclaredFields();
		// AccessibleObject.setAccessible(dutyField, true);
		// 判断该责任是否有值标记
		boolean dutyFlag = false;
		for (int i = 1; i <= mLDPlanDutyParamSet.size(); i++) {
			String CalFactor = mLDPlanDutyParamSet.get(i).getCalFactor();

			String CalFactorValue = mLDPlanDutyParamSet.get(i)
					.getCalFactorValue();
			String CalDutyCode = mLDPlanDutyParamSet.get(i).getDutyCode();

			/** Add by zhangxing 根据终止日期计算保险期间 */
			if (CalFactor.equals("InsuYear") && (CalFactorValue == null)) {
				//update by cxq 修改绑定变量
				sqlbv = new SQLwithBindVariables();
				String tSql = " select CalFactorValue from LDPlanDutyParam where 1=1 "
						+ " and riskcode = '?RiskCode?'and contplancode = '00'"
						+ " and MainRiskCode = '?MainRiskCode?'"
						+ " and dutycode = '?CalDutyCode?'"
						+ " and CalFactor = 'EndDate'";
				sqlbv.sql(tSql);
				sqlbv.put("RiskCode", mLDPlanDutyParamSet.get(i).getRiskCode());
				sqlbv.put("MainRiskCode", mLDPlanDutyParamSet.get(i).getMainRiskCode());
				sqlbv.put("CalDutyCode", CalDutyCode);
				ExeSQL tExeSQL = new ExeSQL();
				String EndData = tExeSQL.getOneValue(sqlbv);
				
				//update by cxq 修改绑定变量
				sqlbv = new SQLwithBindVariables();
				String ttSql = " select CalFactorValue from LDPlanDutyParam where 1=1 "
						+ " and riskcode = '?RiskCode?'and contplancode = '00'"
						+ " and MainRiskCode = '?MainRiskCode?'"
						+ " and dutycode = '?CalDutyCode?'"
						+ " and CalFactor = 'InsuYearFlag'";
				sqlbv.sql(ttSql);
				sqlbv.put("RiskCode", mLDPlanDutyParamSet.get(i).getRiskCode());
				sqlbv.put("MainRiskCode", mLDPlanDutyParamSet.get(i).getMainRiskCode());
				sqlbv.put("CalDutyCode", CalDutyCode);
				
				String InsuYearFlag = tExeSQL.getOneValue(sqlbv);
				String PolApplyDate = mCValidate;
				int InsuYear = PubFun.calInterval(PolApplyDate, EndData,
						InsuYearFlag);

				CalFactor = "InsuYear";
				CalFactorValue = Integer.toString(InsuYear);
			}

			// logger.debug("dutyCode:"+dutycode+"
			// caldutycode:"+CalDutyCode+" calfactor:"+CalFactor+"
			// value:"+CalFactorValue);
			if (dutycode.trim().equals(CalDutyCode.trim())) {
				if (tLCDutySchema.getFieldIndex(CalFactor) == -1) {
					mTransferData.setNameAndValue(CalFactor, CalFactorValue);
				} else {
					tLCDutySchema.setV(CalFactor, CalFactorValue);
					dutyFlag = true;
				}
			}
		}
		if (!dutyFlag) {
			tLCDutySchema.setDutyCode("");
		}
		// 当计算规则为统一费率时，不将费率带到界面，在计算中重新到保险计划要素中取
		if (StrTool.compareString(tLCDutySchema.getCalRule(), "1")) {
			tLCDutySchema.setFloatRate(0.0);
		}
		return true;
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
		mResult.add(this.mTransferData);
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

	public static void main(String[] args) {
		LDPlanQryBL ldplanqrybl = new LDPlanQryBL();
	}
}
