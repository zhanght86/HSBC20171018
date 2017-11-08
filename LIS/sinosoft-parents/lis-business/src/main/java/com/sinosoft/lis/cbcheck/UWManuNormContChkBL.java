package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LMRiskAppSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class UWManuNormContChkBL {
private static Logger logger = Logger.getLogger(UWManuNormContChkBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperator; // 操作员
	private String mManageCom;

	/** 业务处理相关变量 */
	private String mContType = "";
	private String mGrpContNo = "";
	private String mContNo = "";
	private String mUWFlag = ""; // 核保标志
	private String mRiskCode = "";
	private String mUWIdea = ""; // 核保结论
	private String mUPUWCode = "";
	private String mAppGrade = "";

	private String mpostday; // 延长天数
	private String mvalidate; // 延长天数
	private String mReason = ""; // 加费原因
	private String mSpecReason = ""; // 特约原因
	private String mPosFlag = "";

	private LCContSchema mLCContSchema = new LCContSchema();

	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	private boolean isAll = true;
	private boolean isMainRisk = true;

	public UWManuNormContChkBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!this.getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		prepareOutputData();

		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		String tUWFlag = "";
		mInputData = (VData) cInputData.clone();
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mOperator = mGlobalInput.Operator;
		mManageCom = mGlobalInput.ManageCom;

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		mContType = (String) mTransferData.getValueByName("ContType");
		if (mContType == null || "".equals(mContType)) {
			mContType = "1"; // 默认为个险下核保结论
		}

		mPosFlag = (String) mTransferData.getValueByName("PosFlag");
		if (mPosFlag == null || "".equals(mPosFlag)) {
			mPosFlag = "1";
		}

		mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
		mContNo = (String) mTransferData.getValueByName("ContNo");
		mRiskCode = (String) mTransferData.getValueByName("RiskCode");

		mpostday = (String) mTransferData.getValueByName("PostDay");
		mvalidate = (String) mTransferData.getValueByName("ValiDate");
		mReason = (String) mTransferData.getValueByName("Reason");
		mSpecReason = (String) mTransferData.getValueByName("SpecReason");
		mAppGrade = (String) mTransferData.getValueByName("AppGrade");
		mUPUWCode = (String) mTransferData.getValueByName("UPUWCode");

		// 险种核保结论分两层：
		// 一、集体层险种下核保结论
		// 二、对个单层个别被保人下险种核保结论
		// 传入个单保单号，就是对个单层个别被保人下险种核保结论
		if (mContNo != null && !"".equals(mContNo)) {
			isAll = false;
		}

		tUWFlag = (String) mTransferData.getValueByName("UWFlag");
		mUWIdea = (String) mTransferData.getValueByName("UWIdea");
		if (tUWFlag == null || "".equals(tUWFlag)) {
			buildError("getInputData", "没有传入核保结论标志！");
			return false;
		}

		if (mContType.equals("2")) {
			// 团险有的核保结论，个单层面没有，如果团单层面核保结论为拒保，个单层面也为拒保
			// 否则，为标准承保
			mUWFlag = tUWFlag;
			if (isAll) {
				// 对所有保单下结论，则是集体单下，
				// 如非拒保则核保结论设为9，否则核保结论取传入值（个单保单险种核保结论有多个）
				if (!tUWFlag.equals("1")) {
					mUWFlag = "9";
				}
			}
		} else {
			mUWFlag = tUWFlag;
		}

		return true;
	}

	private boolean checkData() {
		if (mGrpContNo == null || "".equals(mGrpContNo)) {
			buildError("checkData", "没有传入集体保单号！");
			return false;
		}

		if (mRiskCode == null || "".equals(mRiskCode)) {
			buildError("checkData", "没有传入险种编码！");
			return false;
		}

		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		LMRiskAppSet tLMRiskAppSet = new LMRiskAppSet();
		LMRiskAppSchema tLMRiskAppSchema = new LMRiskAppSchema();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql("select * from lmriskapp where riskcode='"
				+ "?riskcode?" + "'");
		sqlbv.put("riskcode", mRiskCode);
		tLMRiskAppSet = tLMRiskAppDB
				.executeQuery(sqlbv);
		if (tLMRiskAppSet == null || tLMRiskAppSet.size() == 0) {
			buildError("checkData", "险种查询失败!");
			return false;
		}
		tLMRiskAppSchema = tLMRiskAppSet.get(1);
		if ("S".equals(tLMRiskAppSchema.getSubRiskFlag())) {
			isMainRisk = false;
		} else {
			isMainRisk = true;
		}

		return true;
	}

	private boolean dealData() {
		if (isAll) {
			// 整单层下核保结论
			if (!prepareAllCont()) {
				return false;
			}
		} else {
			// 对个别个单下核保结论
			if (!prepareSingleCont(mGrpContNo, mContNo)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 在整单层下险种核保结论，修改所有个单层保单核保结论
	 * 
	 * @return boolean
	 */
	private boolean prepareAllCont() {
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String tSQL = "";
		tSQL = "select distinct contno from lcpol where " + "grpcontno='"
				+ "?grpcontno?" + "' and riskcode='" + "?riskcode?" + "'";
		logger.debug("Cont_SQL=" + tSQL);
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSQL);
		sqlbv1.put("grpcontno", mGrpContNo);
		sqlbv1.put("riskcode", mRiskCode);
		tSSRS = tExeSQL.execSQL(sqlbv1);
		if (tSSRS == null || tSSRS.getMaxRow() == 0) {
			buildError("checkData", "个人保单表查询失败！");
			return false;
		}
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			if (!prepareSingleCont(mGrpContNo, tSSRS.GetText(i, 1))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 对一个个单下核保结论：如果个单层对应的险种都已经下了核保结论则对个单下核保结论;如果个单对应险种有未下核保结论的，则不对个单下核保结论
	 * 个单层核保结论取值:1，拒保；4，次标准；9，标准
	 * 如果险种都为拒保，则个单拒保；如果既有拒保又有标准，或其它结论，则下次标准；如果都为标准则下标准
	 * 
	 * @param tGrpContNo
	 *            String
	 * @param tContNo
	 *            String
	 * @return boolean
	 */
	private boolean prepareSingleCont(String tGrpContNo, String tContNo) {
		String tSQL = "";
		LCContDB tLCContDB = new LCContDB();
		LCContSet tLCContSet = new LCContSet();
		LCContSchema tLCContSchema = null;
		tSQL = "select * from lccont where contno = '" + "?contno?"
				+ "' and grpcontno='" + "?grpcontno?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSQL);
		sqlbv2.put("contno", tContNo);
		sqlbv2.put("grpcontno", tGrpContNo);
		tLCContSet = tLCContDB.executeQuery(sqlbv2);
		if (tLCContSet == null || tLCContSet.size() == 0) {
			buildError("checkData", "个人保单表查询失败！");
			return false;
		}

		tLCContSchema = tLCContSet.get(1);
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();

		// 如果是从集体整单下的核保结论且核保结论不是拒保，则查该险种该个单是否已经下了核保结论
		// 如果已经下了，则不再对个单下核保结论
		if (mPosFlag.equals("0") && !mUWFlag.equals("1")) {
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql("select * from LCPol where ContNo = '"
					+ "?ContNo?" + "' and RiskCode = '"
					+ "?RiskCode?" + "'");
			sqlbv3.put("ContNo", tLCContSchema.getContNo());
			sqlbv3.put("RiskCode", mRiskCode);
			tLCPolSet = tLCPolDB
					.executeQuery(sqlbv3);
			if ((tLCPolSet != null) && (tLCPolSet.size() > 0)) {
				if (!"".equals(tLCPolSet.get(1).getUWFlag())
						&& !"0".equals(tLCPolSet.get(1).getUWFlag())
						&& !"5".equals(tLCPolSet.get(1).getUWFlag())
						&& !"z".equalsIgnoreCase(tLCPolSet.get(1).getUWFlag())) {
					return true;
				}
			}
		}
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql("select * from LCPol where ContNo = '"
				+ "?ContNo?" + "' and RiskCode <> '"
				+ "?RiskCode?" + "'");
		sqlbv4.put("ContNo", tLCContSchema.getContNo());
		sqlbv4.put("RiskCode", mRiskCode);
		tLCPolSet = tLCPolDB
				.executeQuery(sqlbv4);
		int n = 0;
		String mContPassFlag = "";
		if ((tLCPolSet == null) || (tLCPolSet.size() < 0)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCCont表中无法查到合同号为" + tContNo + "的保单记录";
			this.mErrors.addOneError(tError);

			return false;
		}

		n = tLCPolSet.size();
		LCPolSchema tLCPolSchema = null;
		mContPassFlag = mUWFlag;
		if (n == 0) {
			if (!mUWFlag.equals("9") && !mUWFlag.equals("1")) {
				mContPassFlag = "4";
			}
		} else {
			if (isMainRisk && "1".equals(mUWFlag)) {
				// 如果此险种为主险且为拒保，则判断是否有其它险种且不是其对应附加险险种下非拒保结论
				// 如果没有，则保单下拒保（主险在下拒保的同时会对其对应附加险下拒保结论）
				// 如果有且其它险种有未下核保结论的，则返回，不对个单下结论
				// 如果有且其它险种已经下了非拒保核保结论，则保单下次标准结论
				int tRs = this.isHaveOhterRisk(tLCContSchema.getContNo(),
						mRiskCode);
				if (tRs == 0) {
					mContPassFlag = "1";
				}
				if (tRs == 1) {
					return true;
				}
				if (tRs == 2) {
					mContPassFlag = "4";
				}
			} else {
				for (int i = 1; i <= n; i++) {
					tLCPolSchema = tLCPolSet.get(i);
					// 如果存在没有核保的险种，则不对保单下核保结论，直接退出
					if (tLCPolSchema.getUWFlag().equals("0")
							|| tLCPolSchema.getUWFlag().equals("5")
							|| tLCPolSchema.getUWFlag().equalsIgnoreCase("z")) {
						return true;
					} else if (tLCPolSchema.getUWFlag().equals("9")
							&& mContPassFlag.equals("9")) {
						mContPassFlag = "9";
					} else if (tLCPolSchema.getUWFlag().equals("9")
							&& !mContPassFlag.equals("9")) {
						mContPassFlag = "4";
					} else if (!tLCPolSchema.getUWFlag().equals("9")
							&& mContPassFlag.equals("9")) {
						mContPassFlag = "4";
					} else if (tLCPolSchema.getUWFlag().equals("1")
							&& !mContPassFlag.equals("1")) {
						mContPassFlag = "4";
					} else if (tLCPolSchema.getUWFlag().equals("1")
							&& mContPassFlag.equals("1")) {
						mContPassFlag = "1";
					} else if (!tLCPolSchema.getUWFlag().equals("9")
							&& !tLCPolSchema.getUWFlag().equals("1")
							&& mContPassFlag.equals("1")) {
						mContPassFlag = "4";
					}
				}
			}
		}
		tLCContSchema.setUWFlag(mContPassFlag);
		tLCContSchema.setUWOperator(mOperator);
		tLCContSchema.setUWDate(PubFun.getCurrentDate());
		tLCContSchema.setUWTime(PubFun.getCurrentTime());
		tLCContSchema.setOperator(mOperator);
		tLCContSchema.setModifyDate(PubFun.getCurrentDate());
		tLCContSchema.setModifyTime(PubFun.getCurrentTime());

		if (mUWFlag.equals("1")) {
			String tPrem = "0";
			ExeSQL tExeSQL = new ExeSQL();
			if (isMainRisk) {
				tSQL = "select sum(prem) from lcpol where grpcontno='"
						+ "?grpcontno?"
						+ "' and mainpolno not in (select polno from lcpol where riskcode='"
						+ "?riskcode?" + "' and contno='" + "?contno?"
						+ "') and uwflag<>'1' and contno='" + "?contno?" + "'";
			} else {
				tSQL = "select sum(prem) from lcpol where grpcontno='"
						+ "?grpcontno?" + "' and riskcode<>'" + "?riskcode?"
						+ "' and uwflag<>'1' and contno='" + "?contno?" + "'";
			}
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(tSQL);
			sqlbv5.put("grpcontno", mGrpContNo);
			sqlbv5.put("riskcode", mRiskCode);
			sqlbv5.put("contno", tContNo);
			tPrem = tExeSQL.getOneValue(sqlbv5);
			if (tPrem == null || "".equals(tPrem)) {
				tPrem = "0";
			}
			tLCContSchema.setPrem(tPrem);
		}

		mMap.put(tLCContSchema, "UPDATE");

		prepareSingleUWMaster(tLCContSchema.getContNo(), mContPassFlag);
		return true;
	}

	private boolean prepareSingleUWMaster(String tContNo, String tUWFlag) {
		int m = 0;
		int n = 0;
		LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(tContNo);
		tLCCUWMasterDB.setProposalContNo(tContNo);

		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
		tLCCUWMasterSet = tLCCUWMasterDB.query();

		if (tLCCUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWMaster表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		n = tLCCUWMasterSet.size();
		logger.debug("该投保单的核保主表记录条数:   " + n);

		if (n == 1) {
			tLCCUWMasterSchema = tLCCUWMasterSet.get(1);
			// 人工核保后uwno加一 add by heyq 2005-1-3
			int uwno = tLCCUWMasterSet.get(1).getUWNo();
			uwno++;
			tLCCUWMasterSchema.setUWNo(uwno);
			tLCCUWMasterSchema.setPassFlag(tUWFlag); // 通过标志
			tLCCUWMasterSchema.setState(tUWFlag);
			tLCCUWMasterSchema.setUWIdea(mUWIdea);
			tLCCUWMasterSchema.setAutoUWFlag("2"); // 1 自动核保 2 人工核保

			// tLCCUWMasterSchema.setUWGrade(mUWPopedom);
			tLCCUWMasterSchema.setAppGrade(mAppGrade);

			if (mUWFlag.equals("6")) {
				tLCCUWMasterSchema.setOperator(mUPUWCode); // 上报核保指定核保师功能的实现借助将当前核保师改为待核保师方式实现
			} else {
				tLCCUWMasterSchema.setOperator(mOperator);
			}
			// 操作员

			tLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

			// 延期
			if (mUWFlag.equals("2")) { // 此代码冗余
				tLCCUWMasterSchema.setPostponeDay(mpostday);
				tLCCUWMasterSchema.setPostponeDate(mvalidate);
			}

			// 条件承保
			if (mUWFlag.equals("3")) {
				tLCCUWMasterSchema.setSpecReason(mSpecReason); // 特约原因
				tLCCUWMasterSchema.setAddPremReason(mReason);
			}

			if (mUWFlag.equals("3")) {
				if (tLCCUWMasterSchema.getPrintFlag().equals("1")) {
					CError tError = new CError();
					tError.moduleName = "UWManuNormGChkBL";
					tError.functionName = "prepareUW";
					tError.errorMessage = "已经发核保通知不可录入!";
					this.mErrors.addOneError(tError);

					return false;
				}

				tLCCUWMasterSchema.setSpecFlag("1");
			}
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWMaster表取数据不唯一!";
			this.mErrors.addOneError(tError);

			return false;
		}

		// 核保轨迹表
		LCCUWSubSchema tLCCUWSubSchema = new LCCUWSubSchema();
		LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		tLCCUWSubDB.setContNo(tContNo);

		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		tLCCUWSubSet = tLCCUWSubDB.query();

		if (tLCCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWSubDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWSub表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		m = tLCCUWSubSet.size();
		logger.debug("subcount=" + m);

		if (m > 0) {
			m++; // 核保次数
			tLCCUWSubSchema = new LCCUWSubSchema();
			tLCCUWSubSchema.setUWNo(m); // 第几次核保
			tLCCUWSubSchema.setContNo(tLCCUWMasterSchema.getContNo());
			tLCCUWSubSchema.setGrpContNo(tLCCUWMasterSchema.getGrpContNo());
			tLCCUWSubSchema.setProposalContNo(tLCCUWMasterSchema
					.getProposalContNo());
			tLCCUWSubSchema.setInsuredNo(tLCCUWMasterSchema.getInsuredNo());
			tLCCUWSubSchema.setInsuredName(tLCCUWMasterSchema.getInsuredName());
			tLCCUWSubSchema.setAppntNo(tLCCUWMasterSchema.getAppntNo());
			tLCCUWSubSchema.setAppntName(tLCCUWMasterSchema.getAppntName());
			tLCCUWSubSchema.setAgentCode(tLCCUWMasterSchema.getAgentCode());
			tLCCUWSubSchema.setAgentGroup(tLCCUWMasterSchema.getAgentGroup());
			tLCCUWSubSchema.setUWGrade(tLCCUWMasterSchema.getUWGrade()); // 核保级别
			tLCCUWSubSchema.setAppGrade(tLCCUWMasterSchema.getAppGrade()); // 申请级别
			tLCCUWSubSchema.setAutoUWFlag(tLCCUWMasterSchema.getAutoUWFlag());
			tLCCUWSubSchema.setState(tLCCUWMasterSchema.getState());
			tLCCUWSubSchema.setPassFlag(tLCCUWMasterSchema.getState());
			tLCCUWSubSchema.setPostponeDay(tLCCUWMasterSchema.getPostponeDay());
			tLCCUWSubSchema.setPostponeDate(tLCCUWMasterSchema
					.getPostponeDate());
			tLCCUWSubSchema.setUpReportContent(tLCCUWMasterSchema
					.getUpReportContent());
			tLCCUWSubSchema.setHealthFlag(tLCCUWMasterSchema.getHealthFlag());
			tLCCUWSubSchema.setSpecFlag(tLCCUWMasterSchema.getSpecFlag());
			tLCCUWSubSchema.setSpecReason(tLCCUWMasterSchema.getSpecReason());
			tLCCUWSubSchema.setQuesFlag(tLCCUWMasterSchema.getQuesFlag());
			tLCCUWSubSchema.setReportFlag(tLCCUWMasterSchema.getReportFlag());
			tLCCUWSubSchema.setChangePolFlag(tLCCUWMasterSchema
					.getChangePolFlag());
			tLCCUWSubSchema.setChangePolReason(tLCCUWMasterSchema
					.getChangePolReason());
			tLCCUWSubSchema.setAddPremReason(tLCCUWMasterSchema
					.getAddPremReason());
			tLCCUWSubSchema.setPrintFlag(tLCCUWMasterSchema.getPrintFlag());
			tLCCUWSubSchema.setPrintFlag2(tLCCUWMasterSchema.getPrintFlag2());
			tLCCUWSubSchema.setUWIdea(tLCCUWMasterSchema.getUWIdea());
			tLCCUWSubSchema.setOperator(tLCCUWMasterSchema.getOperator()); // 操作员
			tLCCUWSubSchema.setManageCom(tLCCUWMasterSchema.getManageCom());
			tLCCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tLCCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tLCCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWSubDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWSub表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		mMap.put(tLCCUWMasterSchema, "DELETE&INSERT");
		mMap.put(tLCCUWSubSchema, "INSERT");
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mResult.clear();
		mResult.add(mMap);
	}

	/**
	 * 返回处理后的结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 校验此保单是否存在其它下非拒保结论、且不是该主险附加险的险种
	 * 
	 * @param cContNo
	 *            String
	 * @param cRiskCode
	 *            String
	 * @return int 不存在其它为非拒保核保结论的险种，返回0；如果存在，且有险种为下核保结论返回1，否则返回2
	 */
	private int isHaveOhterRisk(String cContNo, String cRiskCode) {
		String tResult = "";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql("select uwflag from lcpol a where a.contno='"
				+ "?contno?" + "' and uwflag!='1' and a.mainpolno not in "
				+ "(select polno from lcpol where contno='" + "?contno?"
				+ "' and riskcode='" + "?riskcode?" + "')");
		sqlbv6.put("contno", cContNo);
		sqlbv6.put("riskcode", cRiskCode);
		tSSRS = tExeSQL.execSQL(sqlbv6);
		if (tSSRS == null || tSSRS.getMaxRow() <= 0) {
			return 0;
		}
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			tResult = tSSRS.GetText(i, 1);
			if ("5".equals(tResult) || "z".equalsIgnoreCase(tResult)
					|| "0".equals(tResult)) {
				return 1;
			}
		}
		return 2;
	}

	private void buildError(String mFunctionName, String mMsg) {
		// @@错误处理
		CError tError = new CError();
		tError.moduleName = "UWManuNormContChkBL";
		tError.functionName = mFunctionName;
		tError.errorMessage = mMsg;
		this.mErrors.addOneError(tError);
	}

	public static void main(String[] args) {
		UWManuNormContChkBL uwmanunormcontchkbl = new UWManuNormContChkBL();
		VData tVData = new VData();
		VData tResult = new VData();
		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.ComCode = "8621";
		mGlobalInput.ManageCom = "8621";
		mGlobalInput.Operator = "001";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("GrpContNo", "20061225000002");
		// tTransferData.setNameAndValue("ContNo", "130210000019827");
		tTransferData.setNameAndValue("RiskCode", "00276000");
		tTransferData.setNameAndValue("UWFlag", "9");
		tTransferData.setNameAndValue("UWIdea", "AA");
		tTransferData.setNameAndValue("ContType", "2");
		tTransferData.setNameAndValue("PosFlag", "0");
		tVData.add(tTransferData);
		tVData.add(mGlobalInput);

		if (uwmanunormcontchkbl.submitData(tVData, "")) {
			logger.debug("Succ");
		}
	}

	private void jbInit() throws Exception {
	}
}
