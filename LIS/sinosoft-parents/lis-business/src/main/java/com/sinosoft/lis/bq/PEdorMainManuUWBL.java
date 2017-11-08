package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPUWMasterMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPCUWMasterSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPUWMasterMainSchema;
import com.sinosoft.lis.schema.LPUWSubMainSchema;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPUWMasterMainSet;
import com.sinosoft.lis.vschema.LPUWSubMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全个单人工核保结论处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author FanX
 * @version 1.0
 */
public class PEdorMainManuUWBL {
private static Logger logger = Logger.getLogger(PEdorMainManuUWBL.class);
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
	private String mPostponeDay = ""; // 延迟时间

	private MMap mMap = null;
	private LPEdorMainSet mLPEdorMainSet = null;
	private LPEdorMainSchema mLPEdorMainSchema = null;
	private LPEdorItemSet mLPEdorItemSet = null;
	private LPContSet mLPContSet = null;
	private LCContSchema mLCContSchema = new LCContSchema();
	private LPPolSet mLPPolSet = null;
	private LPUWMasterMainSet mLPUWMasterMainSet = new LPUWMasterMainSet();
	private LPUWSubMainSet mLPUWSubMainSet = new LPUWSubMainSet();

	String mFlag;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public PEdorMainManuUWBL() {
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
			tError.moduleName = "PEdorMainManuUWBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);

			return false;
		}
		logger.debug("---updateData PEdorMainManuUWBL LK---");

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mLPEdorMainSchema = (LPEdorMainSchema) cInputData
				.getObjectByObjectName("LPEdorMainSchema", 0);
		LPCUWMasterSchema tLPCUWMasterSchema = (LPCUWMasterSchema) cInputData
				.getObjectByObjectName("LPCUWMasterSchema", 0);
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mLPEdorMainSchema == null || tLPCUWMasterSchema == null
				|| mGlobalInput == null) {
			mErrors.addOneError(new CError("输入信息不完整！"));
			return false;
		}

		mUWFlag = tLPCUWMasterSchema.getPassFlag();
		mUWIdea = tLPCUWMasterSchema.getUWIdea();
		mPostponeDay = tLPCUWMasterSchema.getPostponeDay();
		if (mUWFlag == null || mUWFlag.equals("")) {
			mUWFlag = tLPCUWMasterSchema.getPassFlag();
			if (mUWFlag == null || mUWFlag.equals("")) {
				mErrors.addOneError(new CError("输入信息不完整！"));
				return false;
			}
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
		if (!tLDUserDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "PEdorMainManuUWBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "无此操作员信息，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		String tUWPopedom = tLDUserDB.getUWPopedom();
		if ((tUWPopedom == null) || tUWPopedom.equals("")) {
			CError tError = new CError();
			tError.moduleName = "PEdorMainManuUWBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "操作员无核保权限，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		if (mLPEdorMainSet == null || mLPEdorMainSet.size() <= 0) {
			return true;
		}

		if (mUWFlag.equals("6")) {
			return true; // 如果上报，则不需校验核保级别
		}

		String sql = "select * from LPEdorMain where EdorNo = '?EdorNo?' and AppGrade = (select Max(AppGrade) from LPEdorMain where EdorNo = '?EdorNo?')";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(sql);
		sbv.put("EdorNo", mLPEdorMainSchema.getEdorNo());
		logger.debug(sql);
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		LPEdorMainSet tLPEdorMainSet = tLPEdorMainDB.executeQuery(sbv);

		if (tLPEdorMainSet == null || tLPEdorMainSet.size() <= 0) {
			CError tError = new CError();
			tError.moduleName = "PEdorMainManuUWBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "没有核保信息，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		} else {
			mAppGrade = tLPEdorMainSet.get(1).getAppGrade();

			if ((mAppGrade == null) || mAppGrade.equals("")) {
				CError tError = new CError();
				tError.moduleName = "PEdorMainManuUWBL";
				tError.functionName = "checkUWGrade";
				tError.errorMessage = "该投保单无核保级别，不能核保!";
				this.mErrors.addOneError(tError);

				return false;
			}

			if (tUWPopedom.compareTo(mAppGrade) < 0) {
				CError tError = new CError();
				tError.moduleName = "PEdorMainManuUWBL";
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
		mLPEdorMainSet = new LPEdorMainSet();
		mLPEdorItemSet = new LPEdorItemSet();
		mLPContSet = new LPContSet();
		mMap = new MMap();
		mResult.clear();

		String sql = "select * from LPEdorItem where UWFlag in ('5','1')"
				+ " and EdorNo='?EdorNo?'"
				+ " order by MakeDate,MakeTime";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(sql);
		sbv.put("EdorNo", mLPEdorMainSchema.getEdorNo());

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		mLPEdorItemSet = tLPEdorItemDB.executeQuery(sbv);
		if (mLPEdorItemSet != null && mLPEdorItemSet.size() > 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorMainManuUWBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "批单号:" + mLPEdorMainSchema.getEdorNo()
					+ "有个别项目未核保通过!";
			logger.debug("批单号:" + mLPEdorMainSchema.getEdorNo()
					+ "有个别项目未核保通过!");
			this.mErrors.addOneError(tError);
			return false;

		}

		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		sql = "select * from LPEdorMain where UWState in ('5','1')"
				+ " and EdorAcceptNo='?EdorAcceptNo?'" + " and EdorNo='?EdorNo?'"
				+ " order by MakeDate,MakeTime";
		sbv=new SQLwithBindVariables();
		sbv.sql(sql);
		sbv.put("EdorAcceptNo", mLPEdorMainSchema.getEdorAcceptNo());
		sbv.put("EdorNo", mLPEdorMainSchema.getEdorNo());
		mLPEdorMainSet = tLPEdorMainDB.executeQuery(sbv);

		return true;
	}

	/**
	 * 下核保结论
	 */
	private boolean dealData() {
		if (mLPEdorMainSet == null || mLPEdorMainSet.size() <= 0) {
			return true;
		}
		int n = mLPEdorMainSet.size(); // 应该只有一条
		for (int i = 1; i <= n; i++) {
			mLPEdorMainSchema = mLPEdorMainSet.get(i);
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mLPEdorMainSchema.getContNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				mErrors.addOneError(new CError("查询合同数据失败！"));
			}
			mLCContSchema.setSchema(tLCContDB.getSchema());// 用于prepareEdorUWMasterMain和prepareEdorUWSubMain函数

			LPContDB tLPContDB = new LPContDB();
			tLPContDB.setEdorNo(mLPEdorMainSchema.getEdorNo());
			tLPContDB.setContNo(mLPEdorMainSchema.getContNo());
			LPContSet tLPContSet = tLPContDB.query(); // 从P表获取保全个单信息
			if (tLPContDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLPContDB.mErrors);
				mErrors.addOneError(new CError("查询保全个人合同表失败！"));
				return false;
			}

			LPContSchema tLPContSchema = null;
			if (tLPContSet != null && tLPContSet.size() == 1) {
				tLPContSchema = tLPContSet.get(1);
				if (!setContUWState(tLPContSchema)) // 对合同单下核保结论
				{
					return false;
				}
			}

			mLPEdorMainSchema.setUWState(mUWFlag);
			mLPEdorMainSchema.setUWDate(PubFun.getCurrentDate());
			mLPEdorMainSchema.setUWTime(PubFun.getCurrentTime());
			mLPEdorMainSchema.setUWOperator(mGlobalInput.Operator);
			mLPEdorMainSchema.setOperator(mOperator);
			mLPEdorMainSchema.setModifyDate(PubFun.getCurrentDate());
			mLPEdorMainSchema.setModifyTime(PubFun.getCurrentTime());
			LPUWMasterMainSchema tLPUWMasterMainSchema = prepareEdorUWMasterMain(mLPEdorMainSchema);
			if (tLPUWMasterMainSchema == null) {
				mErrors.addOneError(new CError("生成保全批单核保主表信息失败！"));
				return false;
			}
			if (prepareEdorUWSubMain(tLPUWMasterMainSchema) == null) {
				mErrors.addOneError(new CError("生成保全批单核保子表信息失败！"));
				return false;
			}

		}

		return true;
	}

	private boolean setContUWState(LPContSchema aLPContSchema) {
		if (aLPContSchema == null) {
			return false;
		}
		aLPContSchema.setUWFlag(mUWFlag);
		aLPContSchema.setUWOperator(mOperator);
		aLPContSchema.setUWDate(PubFun.getCurrentDate());
		aLPContSchema.setUWTime(PubFun.getCurrentTime());
		// aLPContSchema.setOperator(mOperator);
		aLPContSchema.setModifyDate(PubFun.getCurrentDate());
		aLPContSchema.setModifyTime(PubFun.getCurrentTime());
		mLPContSet.add(aLPContSchema);

		return true;
	}

	private LPUWMasterMainSchema prepareEdorUWMasterMain(
			LPEdorMainSchema aLPEdorMainSchema) // 生成批单核保主信息
	{
		int tUWNo = 0;
		LPUWMasterMainSchema tLPUWMasterMainSchema = new LPUWMasterMainSchema();

		LPUWMasterMainDB tLPUWMasterMainDB = new LPUWMasterMainDB();
		tLPUWMasterMainDB.setEdorNo(aLPEdorMainSchema.getEdorNo());
		tLPUWMasterMainDB.setContNo(mLCContSchema.getContNo());
		LPUWMasterMainSet tLPUWMasterMainSet = tLPUWMasterMainDB.query();
		if (tLPUWMasterMainDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPUWMasterMainDB.mErrors);
			mErrors.addOneError(new CError("获取保全批单核保主表信息失败！"));
			return null;
		}
		if (tLPUWMasterMainSet == null || tLPUWMasterMainSet.size() <= 0) {
			tUWNo = 1;
			tLPUWMasterMainSchema.setEdorNo(aLPEdorMainSchema.getEdorNo());
			tLPUWMasterMainSchema.setGrpContNo(mLCContSchema.getGrpContNo());
			tLPUWMasterMainSchema.setContNo(mLCContSchema.getContNo());
			tLPUWMasterMainSchema.setProposalContNo(mLCContSchema
					.getProposalContNo());
			tLPUWMasterMainSchema.setUpReportContent("");
			tLPUWMasterMainSchema.setHealthFlag("0");
			tLPUWMasterMainSchema.setQuesFlag("0");
			tLPUWMasterMainSchema.setSpecFlag("0");
			tLPUWMasterMainSchema.setAddPremFlag("0");
			tLPUWMasterMainSchema.setAddPremReason("");
			tLPUWMasterMainSchema.setReportFlag("0");
			tLPUWMasterMainSchema.setPrintFlag("0");
			tLPUWMasterMainSchema.setPrintFlag2("0");
			tLPUWMasterMainSchema.setChangePolFlag("0");
			tLPUWMasterMainSchema.setChangePolReason("");
			tLPUWMasterMainSchema.setSpecReason("");
			tLPUWMasterMainSchema
					.setManageCom(mLPEdorMainSchema.getManageCom());
			tLPUWMasterMainSchema.setMakeDate(PubFun.getCurrentDate());
			tLPUWMasterMainSchema.setMakeTime(PubFun.getCurrentTime());
		} else {
			tUWNo = tLPUWMasterMainSet.get(1).getUWNo() + 1;
			tLPUWMasterMainSchema.setSchema(tLPUWMasterMainSet.get(1));
		}
		tLPUWMasterMainSchema.setUWNo(tUWNo);
		tLPUWMasterMainSchema.setAppntName(mLCContSchema.getAppntName());
		tLPUWMasterMainSchema.setAppntNo(mLCContSchema.getAppntNo());
		tLPUWMasterMainSchema.setInsuredName(mLCContSchema.getInsuredName());
		tLPUWMasterMainSchema.setInsuredNo(mLCContSchema.getInsuredNo());
		tLPUWMasterMainSchema.setAgentCode(mLCContSchema.getAgentCode());
		tLPUWMasterMainSchema.setAgentGroup(mLCContSchema.getAgentGroup());
		tLPUWMasterMainSchema.setAutoUWFlag("2");
		tLPUWMasterMainSchema.setPassFlag(mLPEdorMainSchema.getUWState());
		tLPUWMasterMainSchema.setPostponeDay(mPostponeDay);
		tLPUWMasterMainSchema.setState(mLPEdorMainSchema.getUWState());
		tLPUWMasterMainSchema.setUWIdea(mUWIdea);
		tLPUWMasterMainSchema.setUWGrade(mLPEdorMainSchema.getUWGrade());
		tLPUWMasterMainSchema.setAppGrade(mLPEdorMainSchema.getAppGrade());
		tLPUWMasterMainSchema.setOperator(mGlobalInput.Operator);
		tLPUWMasterMainSchema.setModifyDate(PubFun.getCurrentDate());
		tLPUWMasterMainSchema.setModifyTime(PubFun.getCurrentTime());

		mLPUWMasterMainSet.add(tLPUWMasterMainSchema);
		return tLPUWMasterMainSchema;
	}

	private LPUWSubMainSchema prepareEdorUWSubMain(
			LPUWMasterMainSchema aLPUWMasterMainSchema) // 生成合同核保子表
	{
		LPUWSubMainSchema tLPUWSubMainSchema = new LPUWSubMainSchema();

		tLPUWSubMainSchema.setEdorNo(aLPUWMasterMainSchema.getEdorNo());
		tLPUWSubMainSchema.setGrpContNo(aLPUWMasterMainSchema.getGrpContNo());
		tLPUWSubMainSchema.setContNo(aLPUWMasterMainSchema.getContNo());
		tLPUWSubMainSchema.setProposalContNo(aLPUWMasterMainSchema
				.getProposalContNo());
		tLPUWSubMainSchema.setUWNo(aLPUWMasterMainSchema.getUWNo());
		tLPUWSubMainSchema.setAppntName(aLPUWMasterMainSchema.getAppntName());
		tLPUWSubMainSchema.setAppntNo(aLPUWMasterMainSchema.getAppntNo());
		tLPUWSubMainSchema.setInsuredName(aLPUWMasterMainSchema
				.getInsuredName());
		tLPUWSubMainSchema.setInsuredNo(aLPUWMasterMainSchema.getInsuredNo());
		tLPUWSubMainSchema.setAgentCode(aLPUWMasterMainSchema.getAgentCode());
		tLPUWSubMainSchema.setAgentGroup(aLPUWMasterMainSchema.getAgentGroup());
		tLPUWSubMainSchema.setAutoUWFlag(aLPUWMasterMainSchema.getAutoUWFlag());
		tLPUWSubMainSchema.setPassFlag(aLPUWMasterMainSchema.getPassFlag());
		tLPUWSubMainSchema.setUWGrade(aLPUWMasterMainSchema.getUWGrade());
		tLPUWSubMainSchema.setAppGrade(aLPUWMasterMainSchema.getUWGrade());
		tLPUWSubMainSchema.setPostponeDay(aLPUWMasterMainSchema
				.getPostponeDay());
		tLPUWSubMainSchema.setPostponeDate(aLPUWMasterMainSchema
				.getPostponeDate());
		tLPUWSubMainSchema.setState(aLPUWMasterMainSchema.getState());
		tLPUWSubMainSchema.setUWIdea(aLPUWMasterMainSchema.getUWIdea());
		tLPUWSubMainSchema.setUpReportContent(aLPUWMasterMainSchema
				.getUpReportContent());
		tLPUWSubMainSchema.setHealthFlag(aLPUWMasterMainSchema.getHealthFlag());
		tLPUWSubMainSchema.setQuesFlag(aLPUWMasterMainSchema.getQuesFlag());
		tLPUWSubMainSchema.setSpecFlag(aLPUWMasterMainSchema.getSpecFlag());
		tLPUWSubMainSchema.setAddPremFlag(aLPUWMasterMainSchema
				.getAddPremFlag());
		tLPUWSubMainSchema.setAddPremReason(aLPUWMasterMainSchema
				.getAddPremReason());
		tLPUWSubMainSchema.setReportFlag(aLPUWMasterMainSchema.getReportFlag());
		tLPUWSubMainSchema.setPrintFlag(aLPUWMasterMainSchema.getPrintFlag());
		tLPUWSubMainSchema.setPrintFlag2(aLPUWMasterMainSchema.getPrintFlag2());
		tLPUWSubMainSchema.setChangePolFlag(aLPUWMasterMainSchema
				.getChangePolFlag());
		tLPUWSubMainSchema.setChangePolReason(aLPUWMasterMainSchema
				.getChangePolReason());
		tLPUWSubMainSchema.setSpecReason(aLPUWMasterMainSchema.getSpecFlag());
		tLPUWSubMainSchema.setManageCom(aLPUWMasterMainSchema.getManageCom());
		tLPUWSubMainSchema.setOperator(mGlobalInput.Operator);
		tLPUWSubMainSchema.setModifyDate(PubFun.getCurrentDate());
		tLPUWSubMainSchema.setModifyTime(PubFun.getCurrentTime());
		tLPUWSubMainSchema.setMakeDate(PubFun.getCurrentDate());
		tLPUWSubMainSchema.setMakeTime(PubFun.getCurrentTime());

		mLPUWSubMainSet.add(tLPUWSubMainSchema);
		return tLPUWSubMainSchema;
	}

	private boolean prepareOutputData() {
		mMap.put(mLPEdorMainSet, "UPDATE");
		mMap.put(mLPContSet, "UPDATE");
		mMap.put(mLPUWMasterMainSet, "DELETE&INSERT");
		mMap.put(mLPUWSubMainSet, "INSERT");
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

}
