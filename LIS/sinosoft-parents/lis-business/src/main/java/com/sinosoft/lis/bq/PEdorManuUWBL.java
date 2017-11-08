package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPUWMasterMainDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LDUserSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPUWMasterMainSchema;
import com.sinosoft.lis.schema.LPUWSubMainSchema;
import com.sinosoft.lis.vschema.LDUserSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPUWMasterMainSet;
import com.sinosoft.lis.vschema.LPUWSubMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:个人保全人工核保确认功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Tjj ReWrite ZhangRong
 * @version 1.0
 */
public class PEdorManuUWBL {
private static Logger logger = Logger.getLogger(PEdorManuUWBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private String mManageCom;
	private String mOperator;

	/** 业务数据操作字符串 */
	private String mEdorNo;
	private String mContNo;
	private String mUWState;
	private String mUWIdea;
	private String mAppGrade;
	private String mPostponeDay;
	private String mAppUser; // 上报核保师编码
	private Reflections mReflections = new Reflections();
	private MMap mMap = new MMap();
	private MMap mUWFinaResult = new MMap();
	/**  */
	private LCContSchema mLCContSchema = new LCContSchema();
	private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	private LPUWMasterMainSet mLPUWMasterMainSet = new LPUWMasterMainSet();
	private LPUWSubMainSet mLPUWSubMainSet = new LPUWSubMainSet();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	String mFlag;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public PEdorManuUWBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	public boolean submitData(VData cInputData, String cOperate) {

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据校验操作（checkdata)
		if (!checkData()) {
			return false;
		}
		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 从输入数据中得到所有对象
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		TransferData tTransferData = (TransferData) cInputData
				.getObjectByObjectName("TransferData", 0);
		LPUWMasterMainSchema tLPUWMasterMainSchema = (LPUWMasterMainSchema) tTransferData
				.getValueByName("LPUWMasterMainSchema");
		mInputData = cInputData;
		if (mGlobalInput == null || tTransferData == null
				|| tLPUWMasterMainSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "PEdorManuUWBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "传输数据不完全!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		mManageCom = mGlobalInput.ManageCom;
		if (mOperator == null || mOperator.trim().equals("")
				|| mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorManuUWBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "全局信息不完全!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mEdorNo = (String) tTransferData.getValueByName("EdorNo");
		mContNo = (String) tTransferData.getValueByName("ContNo");
		mUWState = tLPUWMasterMainSchema.getPassFlag();
		mUWIdea = tLPUWMasterMainSchema.getUWIdea();
		mPostponeDay = tLPUWMasterMainSchema.getPostponeDay();
		mAppUser = (String) tTransferData.getValueByName("AppUser");

		if (mEdorNo == null || mEdorNo.equals("") || mContNo == null
				|| mContNo.equals("") || mUWState == null
				|| mUWState.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorManuUWBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "传入核保结论信息不完全!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mUWState.trim().equals("6")
				&& (mAppUser == null || mAppUser.trim().equals(""))) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorManuUWBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中上报核保师编码失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mUWState.trim().equals("2")
				&& (mPostponeDay == null || mPostponeDay.trim().equals(""))) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorManuUWBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中的延期日期数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setEdorNo(mEdorNo);
		tLPEdorMainDB.setContNo(mContNo);
		LPEdorMainSet tLPEdorMainSet = tLPEdorMainDB.query();
		if (tLPEdorMainSet == null || tLPEdorMainSet.size() < 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorManuUWBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "查询保全申请批单信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPEdorMainSchema = tLPEdorMainSet.get(1);

		if (mAppUser != null && !mAppUser.equals("")
				&& mUWState.trim().equals("6")) {
			// 保全核保结论为上报核保,获得上保核保师级别
			LDUserDB tLDUserDB = new LDUserDB();
			LDUserSet tLDUserSet = new LDUserSet();
			LDUserSchema tLDUserSchema = new LDUserSchema();
			tLDUserDB.setUserCode(mAppUser);
			tLDUserSet = tLDUserDB.query();
			if (tLDUserSet == null || tLDUserSet.size() != 1) {
				CError tError = new CError();
				tError.moduleName = "PEdorManuUWBL";
				tError.functionName = "prepareData";
				tError.errorMessage = "上报核保师" + mAppUser + "的信息查询失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLDUserSchema = tLDUserSet.get(1);
			mAppGrade = tLDUserSchema.getEdorPopedom();
		}
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		return checkUserGrade();
	}

	/**
	 * 校验核保级别 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkUserGrade() {
		LDUserDB tLDUserDB = new LDUserDB();
		tLDUserDB.setUserCode(mOperator);
		if (!tLDUserDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "PEdorItemManuUWBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "无此操作员信息，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		String tUWPopedom = tLDUserDB.getUWPopedom();
		if ((tUWPopedom == null) || tUWPopedom.equals("")) {
			CError tError = new CError();
			tError.moduleName = "PEdorItemManuUWBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "操作员无核保权限，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		if (mUWState.equals("6")) {
			return true; // 如果上报，则不需校验核保级别
		}

		String sql = "select * from LPEdorMain where EdorNo = '?mEdorNo?' and AppGrade = (select Max(AppGrade) from LPEdorMain where EdorNo = '?mEdorNo?')";
		logger.debug(sql);
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("mEdorNo", mEdorNo);
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		LPEdorMainSet tLPEdorMainSet = tLPEdorMainDB.executeQuery(sqlbv);

		if (tLPEdorMainSet == null || tLPEdorMainSet.size() <= 0) {
			CError tError = new CError();
			tError.moduleName = "PEdorItemManuUWBL";
			tError.functionName = "checkUWGrade";
			tError.errorMessage = "没有核保信息，不能核保!（操作员：" + mOperator + "）";
			this.mErrors.addOneError(tError);

			return false;
		} else {
			String tAppGrade = tLPEdorMainSet.get(1).getAppGrade();

			if ((tAppGrade == null) || tAppGrade.equals("")) {
				CError tError = new CError();
				tError.moduleName = "PEdorItemManuUWBL";
				tError.functionName = "checkUWGrade";
				tError.errorMessage = "该投保单无核保级别，不能核保!";
				this.mErrors.addOneError(tError);

				return false;
			}

			if (tUWPopedom.compareTo(tAppGrade) < 0) {
				CError tError = new CError();
				tError.moduleName = "PEdorItemManuUWBL";
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
	private boolean dealData() {
		if (mUWState.trim().equals("4") || mUWState.trim().equals("9")) {
			String sql = "select * from LPEdorItem where EdorNo = '?mEdorNo?' and UWFlag <> '4' and UWFlag <> '9'";
			logger.debug(sql);
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("mEdorNo", mEdorNo);
			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv);
			if (tLPEdorItemDB.mErrors.needDealError()) {
				mErrors.addOneError(new CError("查询团单保全项目表数据失败！"));
				return false;
			}
			if (tLPEdorItemSet != null && tLPEdorItemSet.size() > 0) {
				mErrors.addOneError(new CError("保全申请批单" + mEdorNo
						+ "下存在未核保通过的项目，无法核保通过此申请批单！"));
				return false;
			}

			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mContNo);
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				mErrors.addOneError(new CError("查询集体保单数据失败！"));
			}
			mLCContSchema.setSchema(tLCContDB.getSchema());
			// 保全财务合计处理，将批改补退费表数据合计到应收总表/实付总表 //财务数据在复核后生成
			// PEdorUWFinaProduce tPEdorUWFinaProduce = new
			// PEdorUWFinaProduce(mEdorNo);
			// tPEdorUWFinaProduce.setAgentCode(mLCContSchema.getAgentCode());
			// tPEdorUWFinaProduce.setAgentGroup(mLCContSchema.getAgentGroup());
			// tPEdorUWFinaProduce.setLimit(PubFun.getNoLimit(mLPEdorMainSchema.getManageCom()));
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

		mLPEdorMainSchema.setUWState(mUWState);
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

		if (!preparePrint()) {
			mErrors.addOneError(new CError("准备核保打印数据失败！"));
			return false;
		}
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
			tLPUWMasterMainSchema.setAppntName(mLCContSchema.getAppntName());
			tLPUWMasterMainSchema.setAppntNo(mLCContSchema.getAppntNo());
			tLPUWMasterMainSchema
					.setInsuredName(mLCContSchema.getInsuredName());
			tLPUWMasterMainSchema.setInsuredNo(mLCContSchema.getInsuredNo());
			tLPUWMasterMainSchema.setAgentCode(mLCContSchema.getAgentCode());
			tLPUWMasterMainSchema.setAgentGroup(mLCContSchema.getAgentGroup());
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

	/**
	 * 打印信息表
	 * 
	 * @return
	 */
	private boolean preparePrint() {

		// 拒保通知书
		if (mUWState.trim().equals("1")) {
			// 通知书号
			String tLimit = PubFun.getNoLimit(mManageCom);
			String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
			LOPRTManagerSchema tLOPRTManagerSchema;
			tLOPRTManagerSchema = new LOPRTManagerSchema();
			tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
			tLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
			tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
			tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			tLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
			tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT);
			tLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
			tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL);

			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PEdorDECLINE);
			tLOPRTManagerSchema.setOtherNo(mContNo);
			tLOPRTManagerSchema.setStandbyFlag1(mEdorNo);
			tLOPRTManagerSchema.setStateFlag("0");
			mLOPRTManagerSet.add(tLOPRTManagerSchema);
		}

		// 延期通知书
		if (mUWState.trim().equals("2")) {
			String tLimit = PubFun.getNoLimit(mManageCom);
			String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", tLimit); // 产生即付通知书号
			LOPRTManagerSchema tLOPRTManagerSchema;
			tLOPRTManagerSchema = new LOPRTManagerSchema();
			tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
			tLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
			tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
			tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			tLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
			tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT);
			tLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
			tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL);
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PEdorDEFER);
			tLOPRTManagerSchema.setOtherNo(mContNo);
			tLOPRTManagerSchema.setStandbyFlag1(mEdorNo);
			tLOPRTManagerSchema.setStateFlag("0");
			mLOPRTManagerSet.add(tLOPRTManagerSchema);
		}

		return true;
	}

	private boolean prepareOutputData() {
		mMap.put(mLPEdorMainSchema, "UPDATE");
		mMap.put(mLPUWMasterMainSet, "DELETE&INSERT");
		mMap.put(mLPUWSubMainSet, "INSERT");
		mMap.add(mUWFinaResult);
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

}
