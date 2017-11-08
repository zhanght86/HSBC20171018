package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.db.LPGUWMasterMainDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPGUWMasterMainSchema;
import com.sinosoft.lis.schema.LPGUWSubMainSchema;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.vschema.LPGUWMasterMainSet;
import com.sinosoft.lis.vschema.LPGUWSubMainSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 团体保全人工核保对团险批单下核保结论
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author ZhangRong
 * @version 1.0
 */
public class PGrpEdorManuUWBL {
private static Logger logger = Logger.getLogger(PGrpEdorManuUWBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/**  */
	private String mOperator = "";
	private String mUWFlag = ""; // 核保标志
	private String mUWIdea = ""; // 核保结论
	private String mUWPopedom = ""; // 操作员核保级别
	private String mAppGrade = ""; // 上报级别

	private MMap mMap = null;
	private MMap mUWFinaResult = null;
	private LPGrpEdorMainSet mLPGrpEdorMainSet = null;
	private LPGrpEdorMainSchema mLPGrpEdorMainSchema = null;
	private LPGUWMasterMainSet mLPGUWMasterMainSet = null;
	private LPGUWSubMainSet mLPGUWSubMainSet = null;
	String mFlag;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public PGrpEdorManuUWBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据准备操作（preparedata())
		if (!prepareData()) {
			return false;
		}

		// 用户权限校验
		if (!checkUserGrade()) {
			return false;
		}

		// 数据操作业务处理)
		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();

		if (!tSubmit.submitData(mResult, "")) // 数据提交
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "PGrpEdorTypeManuUWBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);

			return false;
		}
		logger.debug("---updateData PGrpEdorTypeManuUWBL LK---");

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mLPGrpEdorMainSchema = (LPGrpEdorMainSchema) cInputData
				.getObjectByObjectName("LPGrpEdorMainSchema", 0);
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mLPGrpEdorMainSchema == null || mLPGrpEdorMainSchema == null
				|| mGlobalInput == null) {
			mErrors.addOneError(new CError("输入信息不完整！"));
			return false;
		}

		mUWFlag = mLPGrpEdorMainSchema.getUWState();
		if (mUWFlag == null || mUWFlag.equals("")) {
			mErrors.addOneError(new CError("输入信息不完整！"));
			return false;
		}
		mOperator = mGlobalInput.Operator;
		return true;
	}

	/**
	 * 校验核保级别 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkUserGrade() {
		LDUserDB tLDUserDB = new LDUserDB();
		tLDUserDB.setUserCode(mOperator);
		logger.debug("mOperator" + mOperator);

		if (!tLDUserDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "PGrpEdorTypeManuUWBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "无此操作员信息，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		String tUWPopedom = tLDUserDB.getUWPopedom();
		logger.debug("tUWPopedom" + tUWPopedom);

		if ((tUWPopedom == null) || tUWPopedom.equals("")) {
			CError tError = new CError();
			tError.moduleName = "PGrpEdorTypeManuUWBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "操作员无核保权限，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		String sql = "select * from LPGrpEdorMain where EdorNo = '"
				+ mLPGrpEdorMainSchema.getEdorNo()
				+ "' and AppGrade = (select Max(AppGrade) from LPGrpEdorMain where EdorNo = '"
				+ mLPGrpEdorMainSchema.getEdorNo() + "')";
		logger.debug(sql);
		LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
		LPGrpEdorMainSet tLPGrpEdorMainSet = tLPGrpEdorMainDB.executeQuery(sql);

		if (tLPGrpEdorMainSet == null || tLPGrpEdorMainSet.size() <= 0) {
			CError tError = new CError();
			tError.moduleName = "PGrpEdorTypeManuUWBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "没有核保信息，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		} else {
			mAppGrade = tLPGrpEdorMainSet.get(1).getAppGrade();

			if ((mAppGrade == null) || mAppGrade.equals("")) {
				CError tError = new CError();
				tError.moduleName = "PGrpEdorTypeManuUWBL";
				tError.functionName = "checkUWGrade";
				tError.errorMessage = "该投保单无核保级别，不能核保!";
				this.mErrors.addOneError(tError);

				return false;
			}

			if (tUWPopedom.compareTo(mAppGrade) < 0) {
				CError tError = new CError();
				tError.moduleName = "PGrpEdorTypeManuUWBL";
				tError.functionName = "checkUWGrade";
				tError.errorMessage = "已经提交上级核保，不能核保!（操作员：" + mOperator + "）";
				this.mErrors.addOneError(tError);

				return false;
			}
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		mLPGrpEdorMainSet = new LPGrpEdorMainSet();
		mLPGUWMasterMainSet = new LPGUWMasterMainSet();
		mLPGUWSubMainSet = new LPGUWSubMainSet();

		String sql = "select * from LPGrpEdorMain where EdorNo='"
				+ mLPGrpEdorMainSchema.getEdorNo() + "'"
				+ " order by MakeDate,MakeTime";
		logger.debug("-------sql:" + sql);

		LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
		mLPGrpEdorMainSet = tLPGrpEdorMainDB.executeQuery(sql);
		if (mLPGrpEdorMainSet == null || mLPGrpEdorMainSet.size() != 1) {
			mErrors.addOneError(new CError("查询保全批改表数据失败！"));
			return false;
		}
		return true;
	}

	/**
	 * 下核保结论
	 */
	private boolean dealData() {
		mLPGrpEdorMainSchema = mLPGrpEdorMainSet.get(1);

		String sql = "";
		if (mUWFlag.equals("4") || mUWFlag.equals("9")) {
			sql = "select * from LPGrpEdorItem where EdorNo = '"
					+ mLPGrpEdorMainSchema.getEdorNo()
					+ "' and UWFlag <> '4' and UWFlag <> '9'";
			logger.debug(sql);
			LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
			LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB
					.executeQuery(sql);
			if (tLPGrpEdorItemDB.mErrors.needDealError()) {
				mErrors.addOneError(new CError("查询团单保全项目表数据失败！"));
				return false;
			}
			if (tLPGrpEdorItemSet != null && tLPGrpEdorItemSet.size() > 0) {
				mErrors.addOneError(new CError("保全申请批单"
						+ mLPGrpEdorMainSchema.getEdorNo()
						+ "下存在未核保通过的项目，无法核保通过此申请批单！"));
				return false;
			}

			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			tLCGrpContDB.setGrpContNo(mLPGrpEdorMainSchema.getGrpContNo());
			if (!tLCGrpContDB.getInfo()) {
				mErrors.copyAllErrors(tLCGrpContDB.mErrors);
				mErrors.addOneError(new CError("查询集体保单数据失败！"));
			}
			// 保全财务合计处理，将批改补退费表数据合计到应收总表/实付总表 //财务数据在复核后生成
			// PEdorUWFinaProduce tPEdorUWFinaProduce = new PEdorUWFinaProduce(
			// mLPGrpEdorMainSchema.getEdorNo());
			// tPEdorUWFinaProduce.setAgentCode(tLCGrpContDB.getAgentCode());
			// tPEdorUWFinaProduce.setAgentGroup(tLCGrpContDB.getAgentGroup());
			// tPEdorUWFinaProduce.setLimit(PubFun.getNoLimit(
			// mLPGrpEdorMainSchema.getManageCom()));
			// tPEdorUWFinaProduce.setOperator(mGlobalInput.Operator);
			// if (!tPEdorUWFinaProduce.submitData())
			// {
			// return false;
			// }
			// if (tPEdorUWFinaProduce.getResult() == null)
			// {
			// return false;
			// }
			// else
			// {
			// mUWFinaResult = (MMap) ((VData) tPEdorUWFinaProduce.getResult()).
			// getObjectByObjectName("MMap", 0);
			// }
		}
		if (mUWFlag.equals("1")) {
			// sql = "select * from LPEdorItem where EdorNo = '" +
			// mLPGrpEdorMainSchema.getEdorNo() +
			// "' and UWFlag <> '1'";
			// LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
			// LPGrpEdorItemSet tLPGrpEdorItemSet =
			// tLPGrpEdorItemDB.executeQuery(
			// sql);
			// if (tLPGrpEdorItemDB.mErrors.needDealError())
			// {
			// mErrors.addOneError(new CError("查询团单保全项目表数据失败！"));
			// return false;
			// }
			// if (tLPGrpEdorItemSet != null && tLPGrpEdorItemSet.size() > 0)
			// {
			// mErrors.addOneError(new CError("保全申请批单" +
			// mLPGrpEdorMainSchema.getEdorNo() +
			// "下存在未核保拒绝的项目，无法核保拒绝此申请批单！"));
			// return false;
			// } //comment by zhangrong 即使各项目均通过，也允许整单拒绝
		}

		mLPGrpEdorMainSchema.setUWState(mUWFlag);
		mLPGrpEdorMainSchema.setUWDate(PubFun.getCurrentDate());
		mLPGrpEdorMainSchema.setUWTime(PubFun.getCurrentTime());
		mLPGrpEdorMainSchema.setUWOperator(mGlobalInput.Operator);
		mLPGrpEdorMainSchema.setOperator(mOperator);
		mLPGrpEdorMainSchema.setModifyDate(PubFun.getCurrentDate());
		mLPGrpEdorMainSchema.setModifyTime(PubFun.getCurrentTime());
		LPGUWMasterMainSchema tLPGUWMasterMainSchema = prepareEdorGUWMasterMain(mLPGrpEdorMainSchema);
		if (tLPGUWMasterMainSchema == null) {
			mErrors.addOneError(new CError("生成保全批单核保主表信息失败！"));
			return false;
		}
		if (prepareEdorGUWSubMain(tLPGUWMasterMainSchema) == null) {
			mErrors.addOneError(new CError("生成保全批单核保子表信息失败！"));
			return false;
		}

		return true;
	}

	private LPGUWMasterMainSchema prepareEdorGUWMasterMain(
			LPGrpEdorMainSchema aLPGrpEdorMainSchema) // 生成批单核保主信息
	{
		int tUWNo = 0;
		LPGUWMasterMainSchema tLPGUWMasterMainSchema = new LPGUWMasterMainSchema();

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(tLPGUWMasterMainSchema.getGrpContNo());
		if (!tLCGrpContDB.getInfo()) {
			mErrors.copyAllErrors(tLCGrpContDB.mErrors);
			mErrors.addOneError("获取团体合同保单信息失败！");
			return null;
		}
		LPGUWMasterMainDB tLPGUWMasterMainDB = new LPGUWMasterMainDB();
		tLPGUWMasterMainDB.setEdorNo(aLPGrpEdorMainSchema.getEdorNo());
		tLPGUWMasterMainDB.setGrpContNo(tLCGrpContDB.getGrpContNo());
		LPGUWMasterMainSet tLPGUWMasterMainSet = tLPGUWMasterMainDB.query();
		if (tLPGUWMasterMainSet == null || tLPGUWMasterMainSet.size() <= 0) {
			tUWNo = 1;
			tLPGUWMasterMainSchema.setEdorNo(aLPGrpEdorMainSchema.getEdorNo());
			tLPGUWMasterMainSchema.setGrpContNo(tLCGrpContDB.getGrpContNo());
			tLPGUWMasterMainSchema.setProposalGrpContNo(tLCGrpContDB
					.getProposalGrpContNo());
			tLPGUWMasterMainSchema.setAppntNo(tLCGrpContDB.getAppntNo());
			tLPGUWMasterMainSchema.setAgentCode(tLCGrpContDB.getAgentCode());
			tLPGUWMasterMainSchema.setAgentGroup(tLCGrpContDB.getAgentGroup());
			tLPGUWMasterMainSchema.setPostponeDay("");
			tLPGUWMasterMainSchema.setPostponeDate("");
			tLPGUWMasterMainSchema.setUWIdea("");
			tLPGUWMasterMainSchema.setUpReportContent("");
			tLPGUWMasterMainSchema.setHealthFlag("0");
			tLPGUWMasterMainSchema.setQuesFlag("0");
			tLPGUWMasterMainSchema.setSpecFlag("0");
			tLPGUWMasterMainSchema.setAddPremFlag("0");
			tLPGUWMasterMainSchema.setAddPremReason("");
			tLPGUWMasterMainSchema.setReportFlag("0");
			tLPGUWMasterMainSchema.setPrintFlag("0");
			tLPGUWMasterMainSchema.setPrintFlag2("0");
			tLPGUWMasterMainSchema.setChangePolFlag("0");
			tLPGUWMasterMainSchema.setChangePolReason("");
			tLPGUWMasterMainSchema.setSpecReason("");
			tLPGUWMasterMainSchema.setManageCom(mLPGrpEdorMainSchema
					.getManageCom());
			tLPGUWMasterMainSchema.setMakeDate(PubFun.getCurrentDate());
			tLPGUWMasterMainSchema.setMakeTime(PubFun.getCurrentTime());
		} else {
			tUWNo = tLPGUWMasterMainSet.get(1).getUWNo() + 1;
			tLPGUWMasterMainSchema.setSchema(tLPGUWMasterMainSet.get(1));
		}
		tLPGUWMasterMainSchema.setUWNo(tUWNo);

		tLPGUWMasterMainSchema.setAutoUWFlag("2");
		tLPGUWMasterMainSchema.setPassFlag(mUWFlag);
		tLPGUWMasterMainSchema.setState(mUWFlag);
		tLPGUWMasterMainSchema.setUWIdea(mUWIdea);
		tLPGUWMasterMainSchema.setOperator(mGlobalInput.Operator);
		tLPGUWMasterMainSchema.setModifyDate(PubFun.getCurrentDate());
		tLPGUWMasterMainSchema.setModifyTime(PubFun.getCurrentTime());

		mLPGUWMasterMainSet.add(tLPGUWMasterMainSchema);
		return tLPGUWMasterMainSchema;
	}

	private LPGUWSubMainSchema prepareEdorGUWSubMain(
			LPGUWMasterMainSchema aLPGUWMasterMainSchema) // 生成合同核保子表
	{
		LPGUWSubMainSchema tLPGUWSubMainSchema = new LPGUWSubMainSchema();

		tLPGUWSubMainSchema.setEdorNo(aLPGUWMasterMainSchema.getEdorNo());
		tLPGUWSubMainSchema.setGrpContNo(aLPGUWMasterMainSchema.getGrpContNo());
		tLPGUWSubMainSchema.setProposalGrpContNo(aLPGUWMasterMainSchema
				.getProposalGrpContNo());
		tLPGUWSubMainSchema.setUWNo(aLPGUWMasterMainSchema.getUWNo());
		tLPGUWSubMainSchema.setAppntName(aLPGUWMasterMainSchema.getAppntName());
		tLPGUWSubMainSchema.setAppntNo(aLPGUWMasterMainSchema.getAppntNo());
		tLPGUWSubMainSchema.setInsuredName(aLPGUWMasterMainSchema
				.getInsuredName());
		tLPGUWSubMainSchema.setInsuredNo(aLPGUWMasterMainSchema.getInsuredNo());
		tLPGUWSubMainSchema.setAgentCode(aLPGUWMasterMainSchema.getAgentCode());
		tLPGUWSubMainSchema.setAgentGroup(aLPGUWMasterMainSchema
				.getAgentGroup());
		tLPGUWSubMainSchema.setAutoUWFlag(aLPGUWMasterMainSchema
				.getAutoUWFlag());
		tLPGUWSubMainSchema.setPassFlag(aLPGUWMasterMainSchema.getPassFlag());
		tLPGUWSubMainSchema.setUWGrade(aLPGUWMasterMainSchema.getUWGrade());
		tLPGUWSubMainSchema.setAppGrade(aLPGUWMasterMainSchema.getUWGrade());
		tLPGUWSubMainSchema.setPostponeDay(aLPGUWMasterMainSchema
				.getPostponeDay());
		tLPGUWSubMainSchema.setPostponeDate(aLPGUWMasterMainSchema
				.getPostponeDate());
		tLPGUWSubMainSchema.setState(aLPGUWMasterMainSchema.getState());
		tLPGUWSubMainSchema.setUWIdea(aLPGUWMasterMainSchema.getUWIdea());
		tLPGUWSubMainSchema.setUpReportContent(aLPGUWMasterMainSchema
				.getUpReportContent());
		tLPGUWSubMainSchema.setHealthFlag(aLPGUWMasterMainSchema
				.getHealthFlag());
		tLPGUWSubMainSchema.setQuesFlag(aLPGUWMasterMainSchema.getQuesFlag());
		tLPGUWSubMainSchema.setSpecFlag(aLPGUWMasterMainSchema.getSpecFlag());
		tLPGUWSubMainSchema.setAddPremFlag(aLPGUWMasterMainSchema
				.getAddPremFlag());
		tLPGUWSubMainSchema.setAddPremReason(aLPGUWMasterMainSchema
				.getAddPremReason());
		tLPGUWSubMainSchema.setReportFlag(aLPGUWMasterMainSchema
				.getReportFlag());
		tLPGUWSubMainSchema.setPrintFlag(aLPGUWMasterMainSchema.getPrintFlag());
		tLPGUWSubMainSchema.setPrintFlag2(aLPGUWMasterMainSchema
				.getPrintFlag2());
		tLPGUWSubMainSchema.setChangePolFlag(aLPGUWMasterMainSchema
				.getChangePolFlag());
		tLPGUWSubMainSchema.setChangePolReason(aLPGUWMasterMainSchema
				.getChangePolReason());
		tLPGUWSubMainSchema.setSpecReason(aLPGUWMasterMainSchema.getSpecFlag());
		tLPGUWSubMainSchema.setManageCom(aLPGUWMasterMainSchema.getManageCom());
		tLPGUWSubMainSchema.setOperator(mGlobalInput.Operator);
		tLPGUWSubMainSchema.setModifyDate(PubFun.getCurrentDate());
		tLPGUWSubMainSchema.setModifyTime(PubFun.getCurrentTime());
		tLPGUWSubMainSchema.setMakeDate(PubFun.getCurrentDate());
		tLPGUWSubMainSchema.setMakeTime(PubFun.getCurrentTime());

		mLPGUWSubMainSet.add(tLPGUWSubMainSchema);
		return tLPGUWSubMainSchema;
	}

	private boolean prepareOutputData() {
		mMap = new MMap();
		mResult.clear();
		mMap.put(mLPGrpEdorMainSet, "UPDATE");
		mMap.put(mLPGUWMasterMainSet, "DELETE&INSERT");
		mMap.put(mLPGUWSubMainSet, "INSERT");
		mMap.put(mUWFinaResult, "DELETE&INSERT");
		mResult.add(mMap);
		return true;
	}

}
