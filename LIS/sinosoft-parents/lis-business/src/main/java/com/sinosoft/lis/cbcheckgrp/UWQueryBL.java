package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LMDutyChooseDB;
import com.sinosoft.lis.db.LMDutyCtrlDB;
import com.sinosoft.lis.db.LMDutyDB;
import com.sinosoft.lis.db.LMDutyGetAliveDB;
import com.sinosoft.lis.db.LMDutyGetClmDB;
import com.sinosoft.lis.db.LMDutyGetDB;
import com.sinosoft.lis.db.LMDutyPayDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.schema.LCAppntIndSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LMDutyChooseSchema;
import com.sinosoft.lis.schema.LMDutyCtrlSchema;
import com.sinosoft.lis.schema.LMDutyGetAliveSchema;
import com.sinosoft.lis.schema.LMDutyGetClmSchema;
import com.sinosoft.lis.schema.LMDutyGetSchema;
import com.sinosoft.lis.schema.LMDutyPaySchema;
import com.sinosoft.lis.schema.LMDutySchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LMDutyChooseSet;
import com.sinosoft.lis.vschema.LMDutyCtrlSet;
import com.sinosoft.lis.vschema.LMDutyGetAliveSet;
import com.sinosoft.lis.vschema.LMDutyGetClmSet;
import com.sinosoft.lis.vschema.LMDutyGetSet;
import com.sinosoft.lis.vschema.LMDutyPaySet;
import com.sinosoft.lis.vschema.LMDutySet;
import com.sinosoft.lis.vschema.LMRiskSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ChgData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统保单查询部分
 * </p>
 * <p>
 * Description: 逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WHN
 * @version 1.0
 */
public class UWQueryBL {
private static Logger logger = Logger.getLogger(UWQueryBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	/** 保单 */
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LCPolSet mLCPolSet = new LCPolSet();
	/** 投保人 */
	private LCAppntIndSchema mLCAppntIndSchema = new LCAppntIndSchema();
	/** 被保人 */
	private LCInsuredSet mLCInsuredSet = new LCInsuredSet();
	/** 责任 */
	private LCDutySet mLCDutySet = new LCDutySet();
	/** 受益人 */
	private LCBnfSet mLCBnfSet = new LCBnfSet();
	/** 告知信息 */
	private LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
	/** 特别约定 */
	private LCSpecSet mLCSpecSet = new LCSpecSet();
	/** 核保轨迹 */
	private LCUWSubSet mLCUWSubSet = new LCUWSubSet();

	/** 责任代码字符串 */
	public String mDutyCodeString = "";

	/** 返回字符串 */
	public String mResultString = "";

	public UWQueryBL() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("---getInputData---");

		// 进行业务处理

		if (cOperate.equals("QUERY||LCUWSUB")) {
			if (!queryDetail()) {
				return false;
			}
			logger.debug("---queryDetail---");
		}

		prepareOutputData();

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 保单查询条件
		mLCPolSchema.setSchema((LCPolSchema) cInputData.getObjectByObjectName(
				"LCPolSchema", 0));

		if (mLCPolSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalQueryBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "请输入查询条件!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 查询符合条件的保单 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean queryData() {
		LCPolDB tLCPolDB = mLCPolSchema.getDB();
		mLCPolSet = tLCPolDB.query();
		if (tLCPolDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "保单查询失败!";
			this.mErrors.addOneError(tError);
			mLCPolSet.clear();
			return false;
		}
		if (mLCPolSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalQueryBL";
			tError.functionName = "queryData";
			tError.errorMessage = "未找到相关数据!";
			this.mErrors.addOneError(tError);
			mLCPolSet.clear();
			return false;
		}
		mResult.clear();
		mResult.add(mLCPolSet);

		return true;
	}

	/**
	 * 查询保单明细信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean queryDetail() {
		// 核保轨迹信息
		LCUWSubDB mLCUWSubDB = new LCUWSubDB();
		mLCUWSubDB.setPolNo(mLCPolSchema.getPolNo());
		mLCUWSubSet.clear();
		mLCUWSubSet = mLCUWSubDB.query();
		if (mLCUWSubDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(mLCUWSubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWQueryBL";
			tError.functionName = "queryDetail";
			tError.errorMessage = "核保轨迹查询失败!";
			this.mErrors.addOneError(tError);
			mLCSpecSet.clear();
			return false;
		}
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		try {
			mResult.add(mLCPolSchema);
			mResult.add(mLCAppntIndSchema);
			mResult.add(mLCInsuredSet);
			mResult.add(mLCDutySet);
			mResult.add(mLCBnfSet);
			mResult.add(mLCCustomerImpartSet);
			mResult.add(mLCSpecSet);
			mResult.add(mLCUWSubSet);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWQueryBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 查询符合条件的该险种的可以选择的责任 输出：如果查询数据时发生错误则返回false,否则返回true
	 */
	private boolean queryChooseDuty() {
		mResult.clear();
		// 查询责任描述
		if (!queryChooseDuty_Duty()) {
			return false;
		}
		// 查询责任关系表
		if (!queryChooseDuty_DutyChoose()) {
			return false;
		}
		// 查询责任交费表
		if (!queryChooseDuty_DutyPay()) {
			return false;
		}
		// 查询责任给付总表
		if (!queryChooseDuty_DutyGet()) {
			return false;
		}
		// 查询责任给付（生存领取表）
		if (!queryChooseDuty_DutyGetAlive()) {
			return false;
		}
		// 查询责任给付（案件领取表）
		if (!queryChooseDuty_DutyGetClm()) {
			return false;
		}
		// 查询交费、给付、责任项是否可以修改表
		if (!queryChooseDuty_DutyCtrl()) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @return true：表示查询责任成功，false：表示查询责任失败
	 */
	private boolean queryChooseDuty_Duty() {
		// 责任表
		String tSql = "";
		tSql = "select * from lmduty where dutycode in(select dutycode from lmriskduty where riskcode='"
				+ mLCPolSchema.getRiskCode()
				+ "' and riskver='"
				+ mLCPolSchema.getRiskVersion() + "')";
		LMDutyDB tLMDutyDB = new LMDutyDB();
		LMDutySet tLMDutySet = new LMDutySet();
		tLMDutySet = tLMDutyDB.executeQuery(tSql);
		if (tLMDutyDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMDutyDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWQueryBL";
			tError.functionName = "queryChooseDuty";
			tError.errorMessage = "责任选择查询失败!";
			this.mErrors.addOneError(tError);
			tLMDutySet.clear();
			return false;
		}
		if (tLMDutySet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWQueryBL";
			tError.functionName = "queryChooseDuty";
			tError.errorMessage = "未找到相关数据!";
			this.mErrors.addOneError(tError);
			tLMDutySet.clear();
			return false;
		}
		int i, iMax;
		LMDutySchema tLMDutySchema = new LMDutySchema();
		String tResultStr = "";
		iMax = tLMDutySet.size();
		tResultStr = "0|";
		mDutyCodeString = "";
		for (i = 1; i <= iMax; i++) {
			tResultStr += "^";
			tLMDutySchema = tLMDutySet.get(i);
			tResultStr += tLMDutySchema.getDutyCode() + "|";
			tResultStr += tLMDutySchema.getDutyName() + "|";
			tResultStr += ChgData.chgData(tLMDutySchema.getPayEndYear()) + "|";
			tResultStr += tLMDutySchema.getPayEndYearFlag() + "|";
			tResultStr += tLMDutySchema.getPayEndDateCalRef() + "|";
			tResultStr += tLMDutySchema.getPayEndDateCalMode() + "|";
			tResultStr += ChgData.chgData(tLMDutySchema.getGetYear()) + "|";
			tResultStr += tLMDutySchema.getGetYearFlag() + "|";
			tResultStr += ChgData.chgData(tLMDutySchema.getInsuYear()) + "|";
			tResultStr += tLMDutySchema.getInsuYearFlag() + "|";
			tResultStr += ChgData.chgData(tLMDutySchema.getAcciYear()) + "|";
			tResultStr += tLMDutySchema.getAcciYearFlag() + "|";
			tResultStr += tLMDutySchema.getCalMode();
			mDutyCodeString += tLMDutySchema.getDutyCode() + ",";
		}
		if (!mDutyCodeString.equals("")) {
			// 去掉最后一个分号
			mDutyCodeString = mDutyCodeString.substring(0, mDutyCodeString
					.length() - 1);
		}
		mResult.add(tResultStr);
		return true;
	}

	/**
	 * 
	 * @return true：表示查询责任关联表成功，false：表示查询责任关联表失败
	 */
	private boolean queryChooseDuty_DutyChoose() {
		// 责任关联表
		String tSql = "";
		tSql = "select * from lmdutychoose where dutycode in(select dutycode from lmriskduty where riskcode='"
				+ mLCPolSchema.getRiskCode()
				+ "' and riskver='"
				+ mLCPolSchema.getRiskVersion() + "')";
		LMDutyChooseDB tLMDutyChooseDB = new LMDutyChooseDB();
		LMDutyChooseSet tLMDutyChooseSet = new LMDutyChooseSet();
		tLMDutyChooseSet = tLMDutyChooseDB.executeQuery(tSql);
		if (tLMDutyChooseDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMDutyChooseDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWQueryBL";
			tError.functionName = "queryChooseDuty";
			tError.errorMessage = "责任选择查询失败!";
			this.mErrors.addOneError(tError);
			tLMDutyChooseSet.clear();
			return false;
		}
		int i, iMax;
		LMDutyChooseSchema tLMDutyChooseSchema = new LMDutyChooseSchema();
		String tResultStr = "";
		iMax = tLMDutyChooseSet.size();
		tResultStr = "0|";
		for (i = 1; i <= iMax; i++) {
			tResultStr += "^";
			tLMDutyChooseSchema = tLMDutyChooseSet.get(i);
			tResultStr += tLMDutyChooseSchema.getRiskCode() + "|";
			tResultStr += tLMDutyChooseSchema.getRiskVer() + "|";
			tResultStr += tLMDutyChooseSchema.getDutyCode() + "|";
			tResultStr += tLMDutyChooseSchema.getDutyName() + "|";
			tResultStr += tLMDutyChooseSchema.getRelaDutyCode() + "|";
			tResultStr += tLMDutyChooseSchema.getRelaDutyName() + "|";
			tResultStr += tLMDutyChooseSchema.getRelation();
		}
		mResult.add(tResultStr);
		return true;
	}

	/**
	 * 
	 * @return true：表示查询责任缴费表成功，false：表示责任缴费表失败
	 */
	private boolean queryChooseDuty_DutyPay() {
		// 责任缴费表
		String tSql = "";
		tSql = "select * from lmDutyPay where PayPlanCode in (select PayPlanCode from LMDutyPayRela where  dutycode in(select dutycode from lmriskduty where riskcode='"
				+ mLCPolSchema.getRiskCode()
				+ "' and riskver='"
				+ mLCPolSchema.getRiskVersion() + "'))";
		LMDutyPayDB tLMDutyPayDB = new LMDutyPayDB();
		LMDutyPaySet tLMDutyPaySet = new LMDutyPaySet();
		tLMDutyPaySet = tLMDutyPayDB.executeQuery(tSql);
		if (tLMDutyPayDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMDutyPayDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWQueryBL";
			tError.functionName = "queryChooseDuty";
			tError.errorMessage = "责任选择查询失败!";
			this.mErrors.addOneError(tError);
			tLMDutyPaySet.clear();
			return false;
		}
		if (tLMDutyPaySet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalQueryBL";
			tError.functionName = "queryChooseDuty";
			tError.errorMessage = "未找到相关数据!";
			this.mErrors.addOneError(tError);
			tLMDutyPaySet.clear();
			return false;
		}
		int i, iMax;
		LMDutyPaySchema tLMDutyPaySchema = new LMDutyPaySchema();
		String tResultStr = "";
		iMax = tLMDutyPaySet.size();
		tResultStr = "0|";
		for (i = 1; i <= iMax; i++) {
			tResultStr += "^";
			tLMDutyPaySchema = tLMDutyPaySet.get(i);
			tResultStr += tLMDutyPaySchema.getPayPlanCode() + "|";
			tResultStr += tLMDutyPaySchema.getPayPlanName() + "|";
			tResultStr += tLMDutyPaySchema.getType() + "|";
			tResultStr += tLMDutyPaySchema.getPayIntv() + "|";
			tResultStr += tLMDutyPaySchema.getPayEndYearFlag() + "|";
			tResultStr += tLMDutyPaySchema.getPayEndYear() + "|";
			tResultStr += tLMDutyPaySchema.getPayEndDateCalRef() + "|";
			tResultStr += tLMDutyPaySchema.getPayEndDateCalMode() + "|";
			tResultStr += tLMDutyPaySchema.getDefaultVal() + "|";
			tResultStr += tLMDutyPaySchema.getCalCode() + "|";
			tResultStr += tLMDutyPaySchema.getCnterCalCode() + "|";
			tResultStr += tLMDutyPaySchema.getOthCalCode() + "|";
			tResultStr += tLMDutyPaySchema.getRate() + "|";
			tResultStr += tLMDutyPaySchema.getMinPay() + "|";
			tResultStr += tLMDutyPaySchema.getAssuYield() + "|";
			tResultStr += tLMDutyPaySchema.getFeeRate() + "|";
			tResultStr += tLMDutyPaySchema.getPayToDateCalMode() + "|";
			tResultStr += tLMDutyPaySchema.getUrgePayFlag() + "|";
			tResultStr += tLMDutyPaySchema.getPayLackFlag() + "|";
			tResultStr += tLMDutyPaySchema.getPayOverFlag() + "|";
			tResultStr += tLMDutyPaySchema.getPayOverDeal() + "|";
			tResultStr += tLMDutyPaySchema.getAvoidPayFlag() + "|";
			tResultStr += tLMDutyPaySchema.getGracePeriod() + "|";
			tResultStr += tLMDutyPaySchema.getPubFlag();
		}
		mResult.add(tResultStr);
		return true;
	}

	/**
	 * 
	 * @return true：表示查询责任给付表成功，false：表示责任给付表失败
	 */
	private boolean queryChooseDuty_DutyGet() {
		// 责任给付表
		String tSql = "";
		tSql = "select * from lmDutyGet where GetDutyCode in (select GetDutyCode from LMDutyGetRela where  dutycode in(select dutycode from lmriskduty where riskcode='"
				+ mLCPolSchema.getRiskCode()
				+ "' and riskver='"
				+ mLCPolSchema.getRiskVersion() + "'))";
		LMDutyGetDB tLMDutyGetDB = new LMDutyGetDB();
		LMDutyGetSet tLMDutyGetSet = new LMDutyGetSet();
		tLMDutyGetSet = tLMDutyGetDB.executeQuery(tSql);
		if (tLMDutyGetDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMDutyGetDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWQueryBL";
			tError.functionName = "queryChooseDuty";
			tError.errorMessage = "责任选择查询失败!";
			this.mErrors.addOneError(tError);
			tLMDutyGetSet.clear();
			return false;
		}
		if (tLMDutyGetSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalQueryBL";
			tError.functionName = "queryChooseDuty";
			tError.errorMessage = "未找到相关数据!";
			this.mErrors.addOneError(tError);
			tLMDutyGetSet.clear();
			return false;
		}
		int i, iMax;
		LMDutyGetSchema tLMDutyGetSchema = new LMDutyGetSchema();
		String tResultStr = "";
		iMax = tLMDutyGetSet.size();
		tResultStr = "0|";
		for (i = 1; i <= iMax; i++) {
			tResultStr += "^";
			tLMDutyGetSchema = tLMDutyGetSet.get(i);
			tResultStr += tLMDutyGetSchema.getGetDutyCode() + "|";
			tResultStr += tLMDutyGetSchema.getGetDutyName() + "|";
			// tResultStr+=tLMDutyGetSchema.getGetDutyKind()+"|";
			tResultStr += tLMDutyGetSchema.getGetIntv() + "|";
			tResultStr += tLMDutyGetSchema.getDefaultVal() + "|";
			tResultStr += tLMDutyGetSchema.getCalCode() + "|";
			tResultStr += tLMDutyGetSchema.getCnterCalCode() + "|";
			tResultStr += tLMDutyGetSchema.getOthCalCode() + "|";
			tResultStr += tLMDutyGetSchema.getGetYear() + "|";
			tResultStr += tLMDutyGetSchema.getGetYearFlag() + "|";
			tResultStr += tLMDutyGetSchema.getStartDateCalRef() + "|";
			tResultStr += tLMDutyGetSchema.getStartDateCalMode() + "|";
			tResultStr += tLMDutyGetSchema.getMinGetStartPeriod() + "|";
			tResultStr += tLMDutyGetSchema.getGetEndPeriod() + "|";
			tResultStr += tLMDutyGetSchema.getGetEndUnit() + "|";
			tResultStr += tLMDutyGetSchema.getEndDateCalRef() + "|";
			tResultStr += tLMDutyGetSchema.getEndDateCalMode() + "|";
			tResultStr += tLMDutyGetSchema.getMaxGetEndPeriod() + "|";
			tResultStr += tLMDutyGetSchema.getAddFlag() + "|";
			tResultStr += tLMDutyGetSchema.getSexRelaFlag() + "|";
			tResultStr += tLMDutyGetSchema.getUnitAppRelaFlag() + "|";
			tResultStr += tLMDutyGetSchema.getAddAmntFlag() + "|";
			tResultStr += tLMDutyGetSchema.getDiscntFlag() + "|";
			tResultStr += tLMDutyGetSchema.getInterestFlag() + "|";
			tResultStr += tLMDutyGetSchema.getShareFlag() + "|";
			tResultStr += tLMDutyGetSchema.getInpFlag() + "|";
			tResultStr += tLMDutyGetSchema.getBnfFlag() + "|";
			tResultStr += tLMDutyGetSchema.getUrgeGetFlag() + "|";
			tResultStr += tLMDutyGetSchema.getDeadValiFlag() + "|";
			tResultStr += tLMDutyGetSchema.getGetInitFlag() + "|";
			tResultStr += tLMDutyGetSchema.getGetLimit() + "|";
			tResultStr += tLMDutyGetSchema.getMaxGet() + "|";
			tResultStr += tLMDutyGetSchema.getGetRate();
		}
		mResult.add(tResultStr);
		return true;
	}

	/**
	 * 
	 * @return true：表示查询责任给付表成功，false：表示责任给付表失败
	 */
	private boolean queryChooseDuty_DutyGetAlive() {
		// 责任给付表
		String tSql = "";
		tSql = "select * from lmDutyGetAlive where GetDutyCode in (select GetDutyCode from LMDutyGetRela where  dutycode in(select dutycode from lmriskduty where riskcode='"
				+ mLCPolSchema.getRiskCode()
				+ "' and riskver='"
				+ mLCPolSchema.getRiskVersion() + "'))";
		LMDutyGetAliveDB tLMDutyGetAliveDB = new LMDutyGetAliveDB();
		LMDutyGetAliveSet tLMDutyGetAliveSet = new LMDutyGetAliveSet();
		tLMDutyGetAliveSet = tLMDutyGetAliveDB.executeQuery(tSql);
		if (tLMDutyGetAliveDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMDutyGetAliveDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWQueryBL";
			tError.functionName = "queryChooseDuty_DutyGetAlive";
			tError.errorMessage = "责任选择查询失败!";
			this.mErrors.addOneError(tError);
			tLMDutyGetAliveSet.clear();
			return false;
		}
		/*
		 * if (tLMDutyGetAliveSet.size() == 0) { // @@错误处理 CError tError = new
		 * CError(); tError.moduleName = "ProposalQueryBL"; tError.functionName =
		 * "queryChooseDuty_DutyGetAlive"; tError.errorMessage = "未找到相关数据!";
		 * this.mErrors.addOneError(tError); tLMDutyGetAliveSet.clear(); return
		 * false; }
		 */
		int i, iMax;
		LMDutyGetAliveSchema tLMDutyGetAliveSchema = new LMDutyGetAliveSchema();
		String tResultStr = "";
		iMax = tLMDutyGetAliveSet.size();
		tResultStr = "0|";
		for (i = 1; i <= iMax; i++) {
			tResultStr += "^";
			tLMDutyGetAliveSchema = tLMDutyGetAliveSet.get(i);
			tResultStr += tLMDutyGetAliveSchema.getGetDutyCode() + "|";
			tResultStr += tLMDutyGetAliveSchema.getGetDutyName() + "|";
			tResultStr += tLMDutyGetAliveSchema.getGetDutyKind() + "|";
			// tResultStr+=tLMDutyGetAliveSchema.getType()+"|";
			tResultStr += tLMDutyGetAliveSchema.getGetIntv() + "|";
			tResultStr += tLMDutyGetAliveSchema.getDefaultVal() + "|";
			tResultStr += tLMDutyGetAliveSchema.getCalCode() + "|";
			tResultStr += tLMDutyGetAliveSchema.getCnterCalCode() + "|";
			tResultStr += tLMDutyGetAliveSchema.getOthCalCode() + "|";
			tResultStr += tLMDutyGetAliveSchema.getGetStartPeriod() + "|";
			tResultStr += tLMDutyGetAliveSchema.getGetStartUnit() + "|";
			tResultStr += tLMDutyGetAliveSchema.getStartDateCalRef() + "|";
			tResultStr += tLMDutyGetAliveSchema.getStartDateCalMode() + "|";
			tResultStr += tLMDutyGetAliveSchema.getMinGetStartPeriod() + "|";
			tResultStr += tLMDutyGetAliveSchema.getGetEndPeriod() + "|";
			tResultStr += tLMDutyGetAliveSchema.getGetEndUnit() + "|";
			tResultStr += tLMDutyGetAliveSchema.getEndDateCalRef() + "|";
			tResultStr += tLMDutyGetAliveSchema.getEndDateCalMode() + "|";
			tResultStr += tLMDutyGetAliveSchema.getMaxGetEndPeriod() + "|";
			tResultStr += tLMDutyGetAliveSchema.getAddFlag() + "|";
			tResultStr += tLMDutyGetAliveSchema.getAddIntv() + "|";
			tResultStr += tLMDutyGetAliveSchema.getAddStartPeriod() + "|";
			tResultStr += tLMDutyGetAliveSchema.getAddStartUnit() + "|";
			tResultStr += tLMDutyGetAliveSchema.getAddEndPeriod() + "|";
			tResultStr += tLMDutyGetAliveSchema.getAddEndUnit() + "|";
			tResultStr += tLMDutyGetAliveSchema.getAddType() + "|";
			tResultStr += tLMDutyGetAliveSchema.getAddValue() + "|";
			tResultStr += tLMDutyGetAliveSchema.getMaxGetCount() + "|";
			tResultStr += tLMDutyGetAliveSchema.getAfterGet() + "|";
			tResultStr += tLMDutyGetAliveSchema.getGetActionType() + "|";
			tResultStr += tLMDutyGetAliveSchema.getUrgeGetFlag() + "|";
			tResultStr += tLMDutyGetAliveSchema.getDiscntFlag() + "|";
			tResultStr += tLMDutyGetAliveSchema.getGetCond();
		}
		mResult.add(tResultStr);
		return true;
	}

	/**
	 * 
	 * @return true：表示查询责任给付表成功，false：表示责任给付表失败
	 */
	private boolean queryChooseDuty_DutyGetClm() {
		// 责任给付表
		String tSql = "";
		tSql = "select * from lmDutyGetClm where GetDutyCode in (select GetDutyCode from LMDutyGetRela where  dutycode in(select dutycode from lmriskduty where riskcode='"
				+ mLCPolSchema.getRiskCode()
				+ "' and riskver='"
				+ mLCPolSchema.getRiskVersion() + "'))";
		LMDutyGetClmDB tLMDutyGetClmDB = new LMDutyGetClmDB();
		LMDutyGetClmSet tLMDutyGetClmSet = new LMDutyGetClmSet();
		tLMDutyGetClmSet = tLMDutyGetClmDB.executeQuery(tSql);
		if (tLMDutyGetClmDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMDutyGetClmDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWQueryBL";
			tError.functionName = "queryChooseDuty_DutyGetClm";
			tError.errorMessage = "责任选择查询失败!";
			this.mErrors.addOneError(tError);
			tLMDutyGetClmSet.clear();
			return false;
		}
		/*
		 * if (tLMDutyGetClmSet.size() == 0) { // @@错误处理 CError tError = new
		 * CError(); tError.moduleName = "ProposalQueryBL"; tError.functionName =
		 * "queryChooseDuty_DutyGetClm"; tError.errorMessage = "未找到相关数据!";
		 * this.mErrors.addOneError(tError); tLMDutyGetClmSet.clear(); return
		 * false; }
		 */
		int i, iMax;
		LMDutyGetClmSchema tLMDutyGetClmSchema = new LMDutyGetClmSchema();
		String tResultStr = "";
		iMax = tLMDutyGetClmSet.size();
		tResultStr = "0|";
		for (i = 1; i <= iMax; i++) {
			tResultStr += "^";
			tLMDutyGetClmSchema = tLMDutyGetClmSet.get(i);
			tResultStr += tLMDutyGetClmSchema.getGetDutyCode() + "|";
			tResultStr += tLMDutyGetClmSchema.getGetDutyName() + "|";
			tResultStr += tLMDutyGetClmSchema.getGetDutyKind() + "|";
			// tResultStr+=tLMDutyGetClmSchema.getType()+"|";
			tResultStr += tLMDutyGetClmSchema.getDefaultVal() + "|";
			tResultStr += tLMDutyGetClmSchema.getCalCode() + "|";
			tResultStr += tLMDutyGetClmSchema.getCnterCalCode() + "|";
			tResultStr += tLMDutyGetClmSchema.getOthCalCode() + "|";
			tResultStr += tLMDutyGetClmSchema.getInpFlag() + "|";
			tResultStr += tLMDutyGetClmSchema.getStatType() + "|";
			tResultStr += tLMDutyGetClmSchema.getMinGet() + "|";
			tResultStr += tLMDutyGetClmSchema.getAfterGet() + "|";
			tResultStr += tLMDutyGetClmSchema.getMaxGet() + "|";
			tResultStr += tLMDutyGetClmSchema.getClaimRate() + "|";
			tResultStr += tLMDutyGetClmSchema.getClmDayLmt() + "|";
			tResultStr += tLMDutyGetClmSchema.getSumClmDayLmt() + "|";
			tResultStr += tLMDutyGetClmSchema.getDeductible() + "|";
			tResultStr += tLMDutyGetClmSchema.getDeDuctDay() + "|";
			tResultStr += tLMDutyGetClmSchema.getObsPeriod() + "|";
			tResultStr += tLMDutyGetClmSchema.getDeadValiFlag() + "|";
			tResultStr += tLMDutyGetClmSchema.getDeadToPValueFlag();
		}
		mResult.add(tResultStr);
		return true;
	}

	/**
	 * 
	 * @return true：表示查询责任录入控制表成功，false：表示责任录入控制表失败
	 */
	private boolean queryChooseDuty_DutyCtrl() {
		// 责任录入控制表
		String tSql = "";
		tSql = "select * from lmDutyCtrl where DutyCode in (select dutycode from lmriskduty where riskcode='"
				+ mLCPolSchema.getRiskCode()
				+ "' and riskver='"
				+ mLCPolSchema.getRiskVersion() + "')";
		LMDutyCtrlDB tLMDutyCtrlDB = new LMDutyCtrlDB();
		LMDutyCtrlSet tLMDutyCtrlSet = new LMDutyCtrlSet();
		tLMDutyCtrlSet = tLMDutyCtrlDB.executeQuery(tSql);
		if (tLMDutyCtrlDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMDutyCtrlDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWQueryBL";
			tError.functionName = "queryChooseDuty_DutyCtrl";
			tError.errorMessage = "责任选择查询失败!";
			this.mErrors.addOneError(tError);
			tLMDutyCtrlSet.clear();
			return false;
		}
		int i, iMax;
		LMDutyCtrlSchema tLMDutyCtrlSchema = new LMDutyCtrlSchema();
		String tResultStr = "";
		iMax = tLMDutyCtrlSet.size();
		tResultStr = "0|";
		for (i = 1; i <= iMax; i++) {
			tResultStr += "^";
			tLMDutyCtrlSchema = tLMDutyCtrlSet.get(i);
			tResultStr += tLMDutyCtrlSchema.getDutyCode() + "|";
			tResultStr += tLMDutyCtrlSchema.getOtherCode() + "|";
			tResultStr += tLMDutyCtrlSchema.getFieldName() + "|";
			tResultStr += tLMDutyCtrlSchema.getInpFlag() + "|";
			tResultStr += tLMDutyCtrlSchema.getCtrlType() + "|";
		}
		mResult.add(tResultStr);
		return true;
	}

	/**
	 * 
	 * @return true：表示查询险种描述表成功，false：表示险种描述表失败
	 */
	private boolean queryRisk() {
		// 险种描述表397
		mResultString = "";
		String tSql = "";
		tSql = "select * from lmRisk where riskcode='"
				+ mLCPolSchema.getRiskCode() + "' and riskver='"
				+ mLCPolSchema.getRiskVersion() + "'";
		LMRiskDB tLMRiskDB = new LMRiskDB();
		LMRiskSet tLMRiskSet = new LMRiskSet();
		tLMRiskSet = tLMRiskDB.executeQuery(tSql);
		if (tLMRiskDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMRiskDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWQueryBL";
			tError.functionName = "queryChooseDuty_Risk";
			tError.errorMessage = "责任选择查询失败!";
			this.mErrors.addOneError(tError);
			tLMRiskSet.clear();
			return false;
		}
		if (tLMRiskSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWQueryBL";
			tError.functionName = "queryChooseDuty_Risk";
			tError.errorMessage = "未找到相关数据!";
			this.mErrors.addOneError(tError);
			tLMRiskSet.clear();
			return false;
		}
		int i, iMax;
		LMRiskSchema tLMRiskSchema = new LMRiskSchema();
		String tResultStr = "";
		iMax = tLMRiskSet.size();
		tResultStr = "0|";
		for (i = 1; i <= iMax; i++) {
			tResultStr += "^";
			tLMRiskSchema = tLMRiskSet.get(i);
			tResultStr += tLMRiskSchema.getRiskCode() + "|";
			tResultStr += tLMRiskSchema.getRiskVer() + "|";
			tResultStr += tLMRiskSchema.getRiskName() + "|";
			tResultStr += tLMRiskSchema.getChoDutyFlag() + "|";
			tResultStr += tLMRiskSchema.getCPayFlag() + "|";
			tResultStr += tLMRiskSchema.getGetFlag() + "|";
			tResultStr += tLMRiskSchema.getEdorFlag() + "|";
			tResultStr += tLMRiskSchema.getRnewFlag() + "|";
			tResultStr += tLMRiskSchema.getUWFlag() + "|";
			tResultStr += tLMRiskSchema.getRinsFlag() + "|";
			tResultStr += tLMRiskSchema.getInsuAccFlag();
		}
		mResultString = tResultStr;
		return true;
	}

}
