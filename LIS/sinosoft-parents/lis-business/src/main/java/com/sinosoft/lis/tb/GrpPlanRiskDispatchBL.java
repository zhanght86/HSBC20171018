package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.util.HashMap;

import com.sinosoft.lis.bl.LCInsuredBL;
import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.LDPlanDB;
import com.sinosoft.lis.db.LDPlanRiskDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDPlanRiskSchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LDPlanRiskSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
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
public class GrpPlanRiskDispatchBL {
private static Logger logger = Logger.getLogger(GrpPlanRiskDispatchBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private MMap mMap = new MMap();

	/** 业务处理相关变量 */
	/** 接受前台传输数据的容器 */
	private TransferData mTransferData = new TransferData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	private VData mSubmitResult = new VData();

	/** 业务数据 */
	private LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
	private LCInsuredBL mLCInsuredBL = new LCInsuredBL();
	private LCContSchema mLCContSchema = new LCContSchema();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	private String mMark = "";
	private String mCurrentData = PubFun.getCurrentDate();

	private HashMap mMainPolMap = new HashMap();

	public GrpPlanRiskDispatchBL() {
	}

	public boolean submitData(VData cInputData, String cOperator) {
		logger.debug("Start GrpPlanRisk SubmitData...");
		this.mInputData = (VData) cInputData.clone();
		if (!getInputData()) {
			return false;
		}
		if (!checkData()) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		prepareOutData();
		return true;
	}

	private boolean getInputData() {
		logger.debug("Start GrpPlanRisk getInputData...");
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput == null) {
			CError tError = new CError();
			tError.moduleName = "CardBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "处理mGlobalInput出错!";
			this.mErrors.addOneError(tError);

		}

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData == null) {
			CError tError = new CError();
			tError.moduleName = "CardBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "处理mTransferData出错!";
			this.mErrors.addOneError(tError);

		}

		mMark = (String) mTransferData.getValueByName("mark");
		if (mMark != null && "card".equals(mMark)) {
			mLCInsuredBL.setSchema((LCInsuredBL) mInputData
					.getObjectByObjectName("LCInsuredBL", 0));
			if (mLCInsuredBL == null) {
				buildError("getInputData", "没有传入被保人信息！");
			}
			mLCInsuredSchema = mLCInsuredBL.getSchema();
		} else {
			mLCInsuredSchema.setSchema((LCInsuredSchema) mInputData
					.getObjectByObjectName("LCInsuredSchema", 0));
			if (mLCInsuredSchema == null) {
				buildError("getInputData", "没有传入被保人信息！");
				return false;
			}
		}

		mLCContSchema.setSchema((LCContSchema) mInputData
				.getObjectByObjectName("LCContSchema", 0));
		if (mLCContSchema == null) {
			buildError("getInputData", "没有传入合同信息！");
			return false;
		}
		logger.debug("Start end getInputData...");
		return true;
	}

	private boolean checkData() {
		logger.debug("产品组合录入-GrpPlanRiskDispatch->checkData-beg");
		/**
		 * @todo 暂时先不加校验，等流程开发完后再加时间校验
		 */

		// String tContPlanCode = mLCInsuredSchema.getContPlanCode();
		// ExeSQL tExecSQL = new ExeSQL();
		// String tResult = "";
		// tResult = tExecSQL.getOneValue(
		// "select 'X' from ldplan where "
		// + "contplancode='" + tContPlanCode + "' "
		// + "and StartDate<= to_date('" +
		// mCurrentData + "','yyyy-mm-dd') "
		// + " and (EndDate >= to_date('" +
		// mCurrentData +
		// "','yyyy-mm-dd') or EndDate is null)");
		// logger.debug("tResult=" + tResult);
		// if (tResult == null || "".equals(tResult)) {
		// buildError("getInputData", tContPlanCode + "不是本机构的在销产品组合！");
		// return false;
		// }
		// logger.debug("产品组合录入-GrpPlanRiskDispatch->checkData-end");
		return true;
	}

	private boolean dealData() {
		logger.debug("Start dealData...");
		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		tLCInsuredSchema = mLCInsuredSchema.getSchema();

		LDPlanDB tLDPlanDB = new LDPlanDB();
		tLDPlanDB.setContPlanCode(tLCInsuredSchema.getContPlanCode());

		LDPlanRiskDB tLDPlanRiskDB = new LDPlanRiskDB();
		LDPlanRiskSet tLDPlanRiskSet = new LDPlanRiskSet();
		tLDPlanRiskDB.setContPlanCode(tLCInsuredSchema.getContPlanCode());
		tLDPlanRiskSet = tLDPlanRiskDB.query();

		// 对保险险种计划排序，确保主险在前面
		LDPlanRiskSet mainPlanRiskSet = new LDPlanRiskSet();
		LDPlanRiskSet subPlanRiskSet = new LDPlanRiskSet();
		LDPlanRiskSchema tPlanRiskSchema = null;
		for (int t = 1; t <= tLDPlanRiskSet.size(); t++) {
			tPlanRiskSchema = tLDPlanRiskSet.get(t);
			if (tPlanRiskSchema.getRiskCode().equals(
					tPlanRiskSchema.getMainRiskCode())) {
				mainPlanRiskSet.add(tPlanRiskSchema);
			} else {
				subPlanRiskSet.add(tPlanRiskSchema);
			}

		}
		mainPlanRiskSet.add(subPlanRiskSet);

		// 循环每个险种，进行险种处理
		for (int i = 1; i <= mainPlanRiskSet.size(); i++) {
			LDPlanRiskSchema ttLDPlanRiskSchema = mainPlanRiskSet.get(i);
			VData tData = preparePlanRiskData(tLCInsuredSchema, mLCContSchema,
					ttLDPlanRiskSchema);
			if (tData == null) {
				CError tError = new CError();
				tError.moduleName = "ContInsuredBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据处理失败ContInsuredBL-->dealData!";
				this.mErrors.addOneError(tError);
				return false;
			}
			LCPolSchema tLCPolSchema = null;
			LCPolBL tLCPolBL = null;
			LCDutySet tLCDutySet = null;
			TransferData tTransferData = null;
			tLCPolSchema = (LCPolSchema) mInputData.getObjectByObjectName(
					"LCPolSchema", 0);
			tLCPolBL = (LCPolBL) mInputData.getObjectByObjectName("LCPolBL", 0);
			tLCDutySet = (LCDutySet) mInputData.getObjectByObjectName(
					"LCDutySet", 0);
			tTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			if (tTransferData != null) {
				mInputData.remove(tTransferData); // 移除原来存在的数据结构，添加新的数据传输结构
			}
			if (tLCPolSchema != null) {
				mInputData.remove(tLCPolSchema); // 移除原来存在的数据结构，添加新的数据传输结构
			}
			if (tLCPolBL != null) {
				mInputData.remove(tLCPolBL); // 移除原来存在的数据结构，添加新的数据传输结构
			}
			if (tLCDutySet != null) {
				mInputData.remove(tLCDutySet); // 移除原来存在的数据结构，添加新的数据传输结构
			}

			tLCPolSchema = (LCPolSchema) tData.getObjectByObjectName(
					"LCPolSchema", 0);
			tLCPolBL = (LCPolBL) tData.getObjectByObjectName("LCPolBL", 0);
			tLCDutySet = (LCDutySet) tData
					.getObjectByObjectName("LCDutySet", 0);
			tTransferData = (TransferData) tData.getObjectByObjectName(
					"TransferData", 0);
			mInputData.add(tTransferData);
			mInputData.add(tLCPolSchema);
			mInputData.add(tLCPolBL);
			mInputData.add(tLCDutySet);
			String poperator = "INSERT||PROPOSAL";
			ProposalBL tProposalBL = new ProposalBL();
			tProposalBL.PrepareSubmitData(mInputData, poperator);
			if (tProposalBL.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tProposalBL.mErrors);
				return false;
			} else {
				if (mMark != null && "card".equals(mMark)) {
					mMap.add((MMap) tProposalBL.getCardResult()
							.getObjectByObjectName("MMap", 0));
				} else {
					mSubmitResult = tProposalBL.getSubmitResult();
				}
				VData pData = tProposalBL.getResult();
				LCPolSchema rPolSchema = (LCPolSchema) pData
						.getObjectByObjectName("LCPolSchema", 0);
				cachePolInfo(rPolSchema.getRiskCode(), rPolSchema);
			}

		}
		logger.debug("End dealData...");
		return true;
	}

	public VData formatLCPol(LCInsuredSchema insuredSchema,
			LDPlanRiskSchema tLDPlanRiskSchema, LCContSchema contSchema) {
		VData tReturnData = new VData();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		String strRiskCode = tLDPlanRiskSchema.getRiskCode();

		tLCPolSchema.setPrtNo(contSchema.getPrtNo());
		tLCPolSchema.setCValiDate(contSchema.getPolApplyDate());
		// 校验险种是否存在，能不能挂在其指定主险中
		if ("1".equals(tLCPolSchema.getSpecifyValiDate())
				&& tLCPolSchema.getCValiDate() != null) {
			// 如果磁盘倒入指定生效日期，且生效日期字段有值,那么就用 生效日期字段的值
		} else {
			logger.debug("====" + mTransferData.getValueByName("BQFlag"));
			if (mTransferData.getValueByName("BQFlag") != null
					&& !mTransferData.getValueByName("BQFlag").equals("")
					&& !mTransferData.getValueByName("BQFlag").equals("null"))

			{
				String EdorValiDate = (String) mTransferData
						.getValueByName("EdorValiDate");
				tLCPolSchema.setCValiDate(EdorValiDate);
			}

		}
		logger.debug("Format");
		tLCPolSchema.setManageCom(contSchema.getManageCom());
		tLCPolSchema.setSaleChnl(contSchema.getSaleChnl());
		tLCPolSchema.setAgentCom(contSchema.getAgentCom());
		tLCPolSchema.setAgentCode(contSchema.getAgentCode());
		tLCPolSchema.setAgentGroup(contSchema.getAgentGroup());
		tLCPolSchema.setAgentCode1(contSchema.getAgentCode1());
		tLCPolSchema.setGrpContNo(contSchema.getGrpContNo());
		// tLCPolSchema.set
		tLCPolSchema.setContType("1");
		// tLCPolSchema.setPolTypeFlag("2");
		tLCPolSchema.setPolTypeFlag(contSchema.getPolType());
		tLCPolSchema.setInsuredPeoples(contSchema.getPeoples());
		tLCPolSchema.setRiskCode(strRiskCode);

		logger.debug("Format2");

		tLCPolSchema.setContNo(insuredSchema.getContNo().trim());
		tLCPolSchema.setInsuredSex(insuredSchema.getSex().trim());
		tLCPolSchema.setInsuredBirthday(insuredSchema.getBirthday());
		tLCPolSchema.setInsuredName(insuredSchema.getName().trim());

		logger.debug("Format3");

		tLCPolSchema.setInsuredNo(insuredSchema.getInsuredNo());
		logger.debug("Forma4t");
		logger.debug("----------------====validate=====----"
				+ tLCPolSchema.getCValiDate());
		tLCPolSchema.setAgentType(contSchema.getAgentType());
		tLCPolSchema.setAppntName(contSchema.getAppntName());
		tLCPolSchema.setAppntNo(contSchema.getAppntNo());
		tLCPolSchema.setPolApplyDate(contSchema.getPolApplyDate());
		tLCPolSchema.setInterestDifFlag("0");
		tLCPolSchema.setMult((String) mTransferData.getValueByName("Mult"));
		logger.debug("Forma5t");
		tReturnData.add(tLCPolSchema);
		return tReturnData;
	}

	/*
	 * 保险计划分险种准备提交给ProposalBL的数据 add by jixf 20051119
	 */
	private VData preparePlanRiskData(LCInsuredSchema insuredSchema,
			LCContSchema contSchema, LDPlanRiskSchema tLDPlanRiskSchema) {
		VData tNewVData = new VData();
		MMap tmpMap = new MMap();
		String tMainRiskCode = tLDPlanRiskSchema.getMainRiskCode();
		String strRiskCode = tLDPlanRiskSchema.getRiskCode();
		LCPolBL tMainPolBL = new LCPolBL();
		LCPolSchema tLCPolSchema = new LCPolSchema();

		// 填充所有保单信息
		VData polData = formatLCPol(insuredSchema, tLDPlanRiskSchema,
				contSchema);
		if (polData == null) {
			CError tError = new CError();
			tError.moduleName = "ContInsuredBL";
			tError.functionName = "submitData";
			tError.errorMessage = "mformatLCPol中有问题";
			this.mErrors.addOneError(tError);
		}

		MMap polRelaMap = (MMap) polData.getObjectByObjectName("MMap", 0);
		tLCPolSchema = (LCPolSchema) polData.getObjectByObjectName(
				"LCPolSchema", 0);
		tLCPolSchema.setInsuredNo(insuredSchema.getInsuredNo());

		if (tMainRiskCode != null && !"".equals(tMainRiskCode)
				&& !tMainRiskCode.equals(strRiskCode)) {
			LCPolSchema mainPolSchema = (LCPolSchema) this.mMainPolMap
					.get(tMainRiskCode);
			if (mainPolSchema == null) {
				CError.buildErr(this, "险种" + strRiskCode + "查找主险保单失败");
				return null;
			}
			tMainPolBL.setSchema(mainPolSchema);
			tLCPolSchema.setMainPolNo(tMainPolBL.getPolNo());
		} else {
			tMainPolBL.setSchema(tLCPolSchema);
		}

		if (polRelaMap != null && polRelaMap.keySet().size() > 0) {
			tmpMap.add(polRelaMap);
		}

		// 责任信息查询和生成
		LDPlanQryBL tLDPlanQryBL = new LDPlanQryBL();
		VData tVData = new VData();
		TransferData ttTransferData = new TransferData();
		tLDPlanRiskSchema.setContPlanCode(insuredSchema.getContPlanCode()); // yaory

		ttTransferData.setNameAndValue("CValidate", contSchema
				.getPolApplyDate());
		ttTransferData.setNameAndValue("Mult", (String) mTransferData
				.getValueByName("Mult"));

		tVData.add(tLDPlanRiskSchema);
		tVData.add(ttTransferData);
		boolean b = tLDPlanQryBL.submitData(tVData, "");
		if (!b || tLDPlanQryBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLDPlanQryBL.mErrors);
			return null;
		}
		tVData = tLDPlanQryBL.getResult();
		TransferData tTransferData = new TransferData();
		tTransferData = (TransferData) tVData.getObjectByObjectName(
				"TransferData", 0);
		LCDutySet tDutySet = (LCDutySet) tVData.getObjectByObjectName(
				"LCDutySet", 0);
		if (tDutySet == null) {
			LCDutySchema tDutySchema = (LCDutySchema) tVData
					.getObjectByObjectName("LCDutySchema", 0);
			if (tDutySchema == null) {
				// CError.buildErr(this, "查询计划要约出错:责任信息不能为空");
				CError tError = new CError();
				tError.moduleName = "ContInsuredBL";
				tError.functionName = "submitData";
				tError.errorMessage = "mformatLCPol中有问题";
				this.mErrors.addOneError(tError);

				return null;
			}
			setPolInfoByDuty(tLCPolSchema, tDutySchema);
			tDutySet = new LCDutySet();
			tDutySet.add(tDutySchema);
		}
		if (tDutySet == null || tDutySet.size() <= 0) {
			// CError.buildErr(this, strRiskCode + "要约信息生成错误出错");
			CError tError = new CError();
			tError.moduleName = "ContInsuredBL";
			tError.functionName = "submitData";
			tError.errorMessage = "mformatLCPol中有问题";
			this.mErrors.addOneError(tError);

			return null;
		}
		tNewVData.add(tDutySet);

		// 加入对应险种的集体投保单信息,险种承保描述信息
		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		tLMRiskAppDB.setRiskCode(tLDPlanRiskSchema.getRiskCode());
		LMRiskAppSchema tLMRiskAppSchema = tLMRiskAppDB.getSchema();
		if (tLMRiskAppSchema == null) {
			CError tError = new CError();
			tError.moduleName = "ContInsuredBL";
			tError.functionName = "submitData";
			tError.errorMessage = "mformatLCPol中有问题";
			this.mErrors.addOneError(tError);
			return null;
		}
		LMRiskDB tLMRiskDB = new LMRiskDB();
		tLMRiskDB.setRiskCode(tLDPlanRiskSchema.getRiskCode());
		LMRiskSchema tLMRiskSchema = new LMRiskSchema();
		tLMRiskSchema = tLMRiskDB.getSchema();

		if (tLMRiskSchema == null) {
			// buildError("prepareContPlanData", strRiskCode +
			// "险种对应的险种承保描述没有找到!");
			CError tError = new CError();
			tError.moduleName = "ContInsuredBL";
			tError.functionName = "submitData";
			tError.errorMessage = "mformatLCPol中有问题";
			this.mErrors.addOneError(tError);

			return null;
		}

		tTransferData.setNameAndValue("GrpImport", "1");

		tTransferData.setNameAndValue("SavePolType", "0"); // 保全保存标记，默认为0，标识非保全
		tTransferData.setNameAndValue("mark", mTransferData
				.getValueByName("mark"));

		tTransferData.setNameAndValue("ContNo", mTransferData
				.getValueByName("ContNo"));
		tTransferData.setNameAndValue("FamilyType", "0"); // 家庭单标记
		tTransferData.setNameAndValue("ContType", "1"); // 团单，个人单标记
		tTransferData.setNameAndValue("PolTypeFlag", mTransferData
				.getValueByName("PolTypeFlag")); // 无名单标记
		tTransferData.setNameAndValue("InsuredPeoples", "1"); // 被保险人人数
		tTransferData.setNameAndValue("InsuredAppAge", mTransferData
				.getValueByName("InsuredAppAge")); // 被保险人年龄
		tTransferData.setNameAndValue("SequenceNo", mTransferData
				.getValueByName("SequenceNo")); // 内部客户号
		tTransferData.setNameAndValue("samePersonFlag", mTransferData
				.getValueByName("samePersonFlag")); // 投保人同被保人标志
		tTransferData.setNameAndValue("deleteAccNo", "1");
		tTransferData.setNameAndValue("ChangePlanFlag", "1");
		tTransferData.setNameAndValue("ISPlanRisk", "Y"); // 套餐险种
		tTransferData.setNameAndValue("Mult", mTransferData
				.getValueByName("Mult")); // 套餐险种

		tNewVData.add(tLCPolSchema);
		tNewVData.add(tTransferData);
		tNewVData.add(tMainPolBL);
		tNewVData.add(tmpMap);
		tNewVData.addElement(tTransferData);
		return tNewVData;
	}

	private void setPolInfoByDuty(LCPolSchema tLCPolSchema,
			LCDutySchema dutySchema) {

		tLCPolSchema.setInsuYear(dutySchema.getInsuYear());
		tLCPolSchema.setInsuYearFlag(dutySchema.getInsuYearFlag());
		logger.debug(dutySchema.getInsuYear() + "-"
				+ dutySchema.getInsuYearFlag());
		tLCPolSchema.setPrem(dutySchema.getPrem());
		tLCPolSchema.setAmnt(dutySchema.getAmnt());
		tLCPolSchema.setPayEndYear(dutySchema.getPayEndYear());
		tLCPolSchema.setPayEndYearFlag(dutySchema.getPayEndYearFlag());
		tLCPolSchema.setGetYear(dutySchema.getGetYear());
		tLCPolSchema.setGetYearFlag(dutySchema.getGetYearFlag());
		tLCPolSchema.setAcciYear(dutySchema.getAcciYear());
		tLCPolSchema.setAcciYearFlag(dutySchema.getAcciYearFlag());
		tLCPolSchema.setMult(dutySchema.getMult());
		// 计算方向,在按分数卖的保单，切记算方向为O的时候
		if (dutySchema.getMult() > 0
				&& "O".equals(StrTool.cTrim(dutySchema.getPremToAmnt()))) {
			tLCPolSchema.setPremToAmnt(dutySchema.getPremToAmnt());
		}
		tLCPolSchema.setStandbyFlag1(dutySchema.getStandbyFlag1());
		tLCPolSchema.setStandbyFlag2(dutySchema.getStandbyFlag2());
		tLCPolSchema.setStandbyFlag3(dutySchema.getStandbyFlag3());
	}

	/**
	 * 缓存数据准备成功的险种保单信息
	 * 
	 * @param strID
	 *            String
	 * @param aLCPolSchema
	 *            LCPolSchema
	 */
	public void cachePolInfo(String tRiskCode, LCPolSchema tLCPolSchema) {
		mMainPolMap.put(tRiskCode, tLCPolSchema);
	}

	private void prepareOutData() {
		this.mResult.clear();
		if (mMark != null && "card".equals(mMark)) {
			mResult.add(mMap);
		} else {
			mResult = (VData) this.mSubmitResult.clone();
		}
	}

	public VData getResult() {
		return mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "GrpPlanRiskDispatchBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		GrpPlanRiskDispatchBL grpplanriskdispatchbl = new GrpPlanRiskDispatchBL();
	}
}
