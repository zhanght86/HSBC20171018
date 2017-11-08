package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LMUWDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPGCUWMasterDB;
import com.sinosoft.lis.db.LPGUWMasterDB;
import com.sinosoft.lis.db.LPGUWMasterMainDB;
import com.sinosoft.lis.db.LPGrpContDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorMainDB;
import com.sinosoft.lis.db.LPGrpPolDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LMUWSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPGCUWErrorSchema;
import com.sinosoft.lis.schema.LPGCUWMasterSchema;
import com.sinosoft.lis.schema.LPGCUWSubSchema;
import com.sinosoft.lis.schema.LPGUWErrorSchema;
import com.sinosoft.lis.schema.LPGUWMasterMainSchema;
import com.sinosoft.lis.schema.LPGUWMasterSchema;
import com.sinosoft.lis.schema.LPGUWSubMainSchema;
import com.sinosoft.lis.schema.LPGUWSubSchema;
import com.sinosoft.lis.schema.LPGrpContSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LMUWSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPGCUWErrorSet;
import com.sinosoft.lis.vschema.LPGCUWMasterSet;
import com.sinosoft.lis.vschema.LPGCUWSubSet;
import com.sinosoft.lis.vschema.LPGUWErrorSet;
import com.sinosoft.lis.vschema.LPGUWMasterMainSet;
import com.sinosoft.lis.vschema.LPGUWMasterSet;
import com.sinosoft.lis.vschema.LPGUWSubMainSet;
import com.sinosoft.lis.vschema.LPGUWSubSet;
import com.sinosoft.lis.vschema.LPGrpContSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorMainSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 团单自动核保业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Tjj
 * @ReWrite ZhangRong
 * @version 1.0
 */
public class PGrpEdorAutoUWBL {
private static Logger logger = Logger.getLogger(PGrpEdorAutoUWBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	private String mUWGrade = "A";

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap mUWFinaResult = null;
	private MMap mPrintMap = null;
	private LPGrpEdorMainSchema mLPGrpEdorMainSchema = null;
	private LPGrpEdorMainSet mLPGrpEdorMainSet = null;
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = null;
	private LPGrpEdorItemSet mLPGrpEdorItemSet = null;
	private LPEdorMainSet mLPEdorMainSet = null;

	private LPGrpContSchema mLPGrpContSchema = null;
	private LCGrpContSchema mLCGrpContSchema = null;
	private LPGrpContSet mLPGrpContSet = null;
	private LPGCUWMasterSet mLPGCUWMasterSet = null;
	private LPGCUWSubSet mLPGCUWSubSet = null;
	private LPGCUWErrorSet mLPGCUWErrorSet = null;

	private LPGrpPolSet mLPGrpPolSet = null;
	private LPGUWMasterSet mLPGUWMasterSet = null;
	private LPGUWSubSet mLPGUWSubSet = null;
	private LPGUWErrorSet mLPGUWErrorSet = null;

	private LPGUWMasterMainSet mLPGUWMasterMainSet = null;
	private LPGUWSubMainSet mLPGUWSubMainSet = null;
	String mPayPrintParams = "";
	private TransferData mTransferData = new TransferData();
	boolean isEdorAppLevel = false; // 是否在保全申请级做自动核保的标识

	public PGrpEdorAutoUWBL() {
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

		// 数据准备操作（preparedata())
		if (!prepareData()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}
		if (!isEdorAppLevel) {
			// PubSubmit tSubmit = new PubSubmit();
			//
			// if (!tSubmit.submitData(mResult, "")) //数据提交
			// {
			// // @@错误处理
			// this.mErrors.copyAllErrors(tSubmit.mErrors);
			// CError tError = new CError();
			// tError.moduleName = "PGrpEdorAutoUWBL";
			// tError.functionName = "submitData";
			// tError.errorMessage = "数据提交失败!";
			// this.mErrors.addOneError(tError);
			// return false;
			// }
			// logger.debug("---updateGrpEdorAutoUWData---");
			//
			// mResult.clear();
		}
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("UWFlag", mLPGrpEdorMainSchema
				.getUWState());
		tTransferData.setNameAndValue("UWGrade", mLPGrpEdorMainSchema
				.getUWGrade());
		tTransferData.setNameAndValue("PayPrintParams", mPayPrintParams);
		mResult.add(tTransferData);

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 得到保单号（避免依靠前台)
		mLPGrpEdorMainSchema = (LPGrpEdorMainSchema) cInputData
				.getObjectByObjectName("LPGrpEdorMainSchema", 0);
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData != null
				&& mTransferData.getValueByName("EdorAppLevel") != null
				&& "Y".equals((String) mTransferData
						.getValueByName("EdorAppLevel"))) {
			isEdorAppLevel = true;
		}

		if (mLPGrpEdorMainSchema == null || mGlobalInput == null) {
			mErrors.addOneError(new CError("输入数据不完全！"));
			return false;
		}
		LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
		tLPGrpEdorMainDB.setGrpContNo(mLPGrpEdorMainSchema.getGrpContNo());
		tLPGrpEdorMainDB.setEdorNo(mLPGrpEdorMainSchema.getEdorNo());
		LPGrpEdorMainSet tLPGrpEdorMainSet = tLPGrpEdorMainDB.query();
		if (tLPGrpEdorMainSet == null || tLPGrpEdorMainSet.size() <= 0) {
			mErrors.addOneError(new CError("查询批单"
					+ mLPGrpEdorMainSchema.getEdorNo() + "失败！"));
			return false;
		}
		mLPGrpEdorMainSchema.setSchema(tLPGrpEdorMainSet.get(1));

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		String sql;
		int m;

		mLPGrpEdorMainSet = new LPGrpEdorMainSet();
		mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		mLPGrpEdorItemSet = new LPGrpEdorItemSet();
		mLPEdorMainSet = new LPEdorMainSet();

		mLPGrpContSchema = new LPGrpContSchema();
		mLCGrpContSchema = new LCGrpContSchema();
		mLPGrpContSet = new LPGrpContSet();
		mLPGCUWMasterSet = new LPGCUWMasterSet();
		mLPGCUWSubSet = new LPGCUWSubSet();
		mLPGCUWErrorSet = new LPGCUWErrorSet();

		mLPGrpPolSet = new LPGrpPolSet();
		mLPGUWMasterSet = new LPGUWMasterSet();
		mLPGUWSubSet = new LPGUWSubSet();
		mLPGUWErrorSet = new LPGUWErrorSet();

		mLPGUWMasterMainSet = new LPGUWMasterMainSet();
		mLPGUWSubMainSet = new LPGUWSubMainSet();

		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();

		sql = "select * from LPGrpEdorItem  where (UWFlag in ('0', '5') or UWFlag is null) and ManageCom like '"
				+ mGlobalInput.ManageCom
				+ "%'"
				+ " and GrpContNo='"
				+ mLPGrpEdorMainSchema.getGrpContNo()
				+ "' and EdorNo='"
				+ mLPGrpEdorMainSchema.getEdorNo() + "'"; // 查询该批单下所有的保全项目,(包括未申请确认的)

		logger.debug("-------sql:" + sql);
		mLPGrpEdorItemSet.clear();
		mLPGrpEdorItemSet = tLPGrpEdorItemDB.executeQuery(sql);
		if (mLPGrpEdorItemSet == null) {
			mErrors.addOneError(new CError("查询保全项目失败！"));
			logger.debug("查询保全项目失败！");
			return false;
		}

		m = mLPGrpEdorItemSet.size();
		for (int i = 1; i <= m; i++) {
			tLPGrpEdorItemSchema = mLPGrpEdorItemSet.get(i);
			if (!"2".equals(tLPGrpEdorItemSchema.getEdorState())) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PGrpEdorAutoUWBL";
				tError.functionName = "prepareData";
				tError.errorMessage = "批单号:" + mLPGrpEdorMainSchema.getEdorNo()
						+ "有个别项目未申请确认!";
				logger.debug("批单号:" + mLPGrpEdorMainSchema.getEdorNo()
						+ "有个别项目未申请确认!");
				this.mErrors.addOneError(tError);
				return false;
			}
			/*
			 * if (i == 1) { mLPGrpEdorMainSet.add(tLPGrpEdorItemSchema);
			 * tEdorNo = tLPGrpEdorItemSchema.getEdorNo();
			 * 
			 * LPGrpEdorMainSet iLPGrpEdorMainSet = new LPGrpEdorMainSet();
			 * LPGrpEdorMainDB iLPGrpEdorMainDB = new LPGrpEdorMainDB();
			 * iLPGrpEdorMainDB.setEdorNo(tEdorNo);
			 * iLPGrpEdorMainDB.setEdorState("1"); iLPGrpEdorMainSet =
			 * iLPGrpEdorMainDB.query(); if (iLPGrpEdorMainSet.size() > 0) { //
			 * @@错误处理 CError tError = new CError(); tError.moduleName =
			 * "PGrpEdorAutoUWBL"; tError.functionName = "prepareData";
			 * tError.errorMessage = "批单号:" + tEdorNo + "有个别项目未申请确认!";
			 * this.mErrors.addOneError(tError); return false; } } else { if
			 * (tLPGrpEdorItemSchema.getEdorNo().equals(tEdorNo)) { continue; }
			 * mLPGrpEdorMainSet.add(tLPGrpEdorItemSchema); tEdorNo =
			 * tLPGrpEdorItemSchema.getEdorNo();
			 * 
			 * LPGrpEdorMainSet iLPGrpEdorMainSet = new LPGrpEdorMainSet();
			 * LPGrpEdorMainDB iLPGrpEdorMainDB = new LPGrpEdorMainDB();
			 * iLPGrpEdorMainDB.setEdorNo(tEdorNo);
			 * iLPGrpEdorMainDB.setEdorState("1"); iLPGrpEdorMainSet =
			 * iLPGrpEdorMainDB.query(); if (iLPGrpEdorMainSet.size() > 0) { //
			 * @@错误处理 CError tError = new CError(); tError.moduleName =
			 * "PGrpEdorAutoUWBL"; tError.functionName = "prepareData";
			 * tError.errorMessage = "批单号:" + tEdorNo + "有个别项目未申请确认!";
			 * this.mErrors.addOneError(tError); return false; } }
			 */
		}

		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setEdorNo(mLPGrpEdorMainSchema.getEdorNo());
		mLPEdorMainSet = tLPEdorMainDB.query(); // 查询团单下进行保全的个单
		if (tLPEdorMainDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorMainDB.mErrors);
			mErrors.addOneError(new CError("查询团险下个单保全批改表失败！"));
			return false;
		}

		return true;
	}

	private boolean dealData() {
		LPEdorMainSchema tLPEdorMainSchema = null;
		VData tInputData = new VData();
		VData tContUWResult = null;
		TransferData tContUWTransData = null;
		String tUWState = "9";
		mLPGrpEdorMainSchema.setUWState("9"); // 预设置核保结论为通过

		EdorAutoUWBL tEdorAutoUWBL = null;
		int n = mLPEdorMainSet.size();
		int i = 1;
		for (i = 1; i <= n; i++) {
			tEdorAutoUWBL = new EdorAutoUWBL();
			tLPEdorMainSchema = mLPEdorMainSet.get(i);
			tInputData.clear();
			tInputData.addElement("G");
			tInputData.addElement(mGlobalInput);
			tInputData.addElement(tLPEdorMainSchema);
			tInputData.addElement(mTransferData);

			if (!tEdorAutoUWBL.submitData(tInputData, "")) // 对团单下个单进行核保
			{
				mErrors.copyAllErrors(tEdorAutoUWBL.mErrors);
				mErrors.addOneError(new CError("团体保单下个人保单"
						+ tLPEdorMainSchema.getContNo() + "核保失败！"));
				return false;
			} else {
				tContUWResult = tEdorAutoUWBL.getResult();
				if (tContUWResult == null
						|| tContUWResult.getObjectByObjectName("TransferData",
								0) == null) {
					mErrors.addOneError(new CError("团体保单下个人保单核保结果为空！"));
					return false;
				}
				tContUWTransData = (TransferData) tContUWResult
						.getObjectByObjectName("TransferData", 0);
				if (!((String) tContUWTransData.getValueByName("UWFlag"))
						.equals("9")) {
					mLPGrpEdorMainSchema.setUWState("5"); // 团单下个单核保未通过，则团单核保不通过
					if ((String) tContUWTransData.getValueByName("UWGrade") != null
							&& !((String) tContUWTransData
									.getValueByName("UWGrade")).equals("")
							&& mUWGrade.compareTo((String) tContUWTransData
									.getValueByName("UWGrade")) < 0) {
						mUWGrade = (String) tContUWTransData
								.getValueByName("UWGrade"); // 获得核保级别
					}
				}
				if (isEdorAppLevel
						&& tContUWResult.getObjectByObjectName("MMap", 0) != null) {
					mMap.add((MMap) tContUWResult.getObjectByObjectName("MMap",
							0));
				}
			}
		}

		LPGrpContDB tLPGrpContDB = new LPGrpContDB();
		tLPGrpContDB.setEdorNo(mLPGrpEdorMainSchema.getEdorNo());
		tLPGrpContDB.setGrpContNo(mLPGrpEdorMainSchema.getGrpContNo());
		LPGrpContSet tLPGrpContSet = tLPGrpContDB.query(); // 从P表获取保全团体合同单信息

		if (tLPGrpContDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPGrpContDB.mErrors);
			mErrors.addOneError(new CError("查询保全个人合同表失败！"));
			return false;
		}
		if (tLPGrpContSet != null && tLPGrpContSet.size() == 1) {
			mLPGrpContSchema = tLPGrpContSet.get(1);
			mLPGrpContSet.add(mLPGrpContSchema);
		} else {
			mLPGrpContSchema = null;
		}

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorMainSchema.getGrpContNo());
		LCGrpContSet tLCGrpContSet = tLCGrpContDB.query(); // 从C表获取团体合同单信息
		if (tLCGrpContSet == null || tLCGrpContSet.size() != 1) {
			mErrors.copyAllErrors(tLCGrpContDB.mErrors);
			mErrors.addOneError(new CError("查询个人合同表失败！"));
			return false;
		}
		mLCGrpContSchema = tLCGrpContSet.get(1);

		n = mLPGrpEdorItemSet.size();
		for (i = 1; i <= n; i++) {
			mLPGrpEdorItemSchema = mLPGrpEdorItemSet.get(i);
			mLPGrpEdorItemSchema.setUWFlag("9"); // 预设置核保结论为通过

			tUWState = getAutoUWState(); // 对团险合同单合同下险种进行核保
			if (tUWState.equals("0")) // 核保过程出现异常
			{
				return false;
			}
			if (!tUWState.equals("9")) {
				mLPGrpEdorItemSchema.setUWFlag("5"); // 如果有一个保全项目自核不通过，则该项目自核不通过
				mLPGrpEdorMainSchema.setUWState("5"); // 如果有一个保全项目自核不通过，则该批单自核不通过
			}

			if (mLPGrpContSchema != null) {
				tUWState = dealLPGrpContUW(mLPGrpContSchema); // 对合同单进行核保（根据P表）
			} else {
				tUWState = dealLCGrpContUW(mLCGrpContSchema); // 对合同单进行核保（根据C表）
			}
			if (tUWState.equals("0")) // 核保过程出现异常
			{
				return false;
			}
			if (!tUWState.equals("9")) {
				mLPGrpEdorItemSchema.setUWFlag("5"); // 如果合同单保全项目自核不通过，则该项目自核不通过
				mLPGrpEdorMainSchema.setUWState("5"); // 如果合同单保全项目自核不通过，则该批单自核不通过
			}

			tUWState = getPUWState(mLPGrpEdorItemSchema); // 查询该项目下团单下个单的核保结果
			if (tUWState.equals("0")) // 核保过程出现异常
			{
				return false;
			}
			if (!tUWState.equals("9")) {
				mLPGrpEdorItemSchema.setUWFlag("5"); // 如果该保全项目下有个单自核不通过，则该项目自核不通过
				mLPGrpEdorMainSchema.setUWState("5"); // 如果该保全项目下有个单自核不通过，则该批单自核不通过
			}

			mLPGrpEdorItemSchema.setUWDate(PubFun.getCurrentDate());
			mLPGrpEdorItemSchema.setUWTime(PubFun.getCurrentTime());
			mLPGrpEdorItemSchema.setUWOperator(mGlobalInput.Operator);
			mLPGrpEdorItemSchema.setOperator(mGlobalInput.Operator);
			mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
			mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		}

		mLPGrpEdorMainSchema.setUWDate(PubFun.getCurrentDate());
		mLPGrpEdorMainSchema.setUWTime(PubFun.getCurrentTime());
		mLPGrpEdorMainSchema.setUWGrade(mUWGrade);
		mLPGrpEdorMainSchema.setAppGrade(mUWGrade);
		mLPGrpEdorMainSchema.setUWOperator(mGlobalInput.Operator);
		mLPGrpEdorMainSchema.setOperator(mGlobalInput.Operator);
		mLPGrpEdorMainSchema.setModifyDate(PubFun.getCurrentDate());
		mLPGrpEdorMainSchema.setModifyTime(PubFun.getCurrentTime());
		mLPGrpEdorMainSet.add(mLPGrpEdorMainSchema);
		LPGUWMasterMainSchema tLPGUWMasterMainSchema = prepareEdorGUWMasterMain(
				mLPGrpEdorMainSchema, mLCGrpContSchema, mLPGrpEdorMainSchema
						.getUWState(), mUWGrade);
		if (tLPGUWMasterMainSchema == null) {
			mErrors.addOneError(new CError("生成保全批单核保主表信息失败！"));
			return false;
		}
		if (prepareEdorGUWSubMain(tLPGUWMasterMainSchema) == null) {
			mErrors.addOneError(new CError("生成保全批单核保子表信息失败！"));
			return false;
		}

		// 生成打印数据
		if (!isEdorAppLevel) {
			if (!getPrintData()) {
				return false;
			}

			// 生成打印收费参数
			if (!getPrintParams()) {
				return false;
			}
		}
		if (mLPGrpEdorMainSchema.getUWState().equals("9")) {
			// 保全财务合计处理，将批改补退费表数据合计到应收总表/实付总表
			if (!isEdorAppLevel) {
				// PEdorUWFinaProduce tPEdorUWFinaProduce = new
				// PEdorUWFinaProduce(
				// mLPGrpEdorMainSchema.getEdorNo());
				// tPEdorUWFinaProduce.setAgentCode(mLCGrpContSchema.getAgentCode());
				// tPEdorUWFinaProduce.setAgentGroup(mLCGrpContSchema.
				// getAgentGroup());
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
				// mUWFinaResult = (MMap) ((VData) tPEdorUWFinaProduce.
				// getResult()).
				// getObjectByObjectName("MMap", 0);
				// }
			}

		}

		return true;
	}

	private String getAutoUWState() {
		String tUWState = "9";

		LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();
		tLPGrpPolDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpPolDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		LPGrpPolSet tLPGrpPolSet = tLPGrpPolDB.query();

		if (tLPGrpPolSet != null && tLPGrpPolSet.size() > 0) { // 如果批改项目指定了对应的险种保单，则对所有团体合同下险种核保，从LCGrpPol表中获取险种信息
			tUWState = dealLPGrpPolUW(tLPGrpPolSet); // 调用团体险种核保函数
			mLPGrpPolSet.add(tLPGrpPolSet);
		} else { // 如果批改项目未指定对应的险种保单，则对所有团体合同下险种核保，从LCGrpPol表中获取险种信息
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
			LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();

			if (tLCGrpPolSet != null && tLCGrpPolSet.size() > 0) {
				tUWState = dealLCGrpPolUW(tLCGrpPolSet); // 调用团体险种核保函数
			}
		}
		return tUWState;
	}

	private String dealLPGrpPolUW(LPGrpPolSet aLPGrpPolSet) {
		String tUWState = "9";
		String tUWGrade = "A";
		String Sql, tCalCode;
		LPGrpPolSchema tLPGrpPolSchema = null;
		LMUWDB tLMUWDB = new LMUWDB();
		LMUWSet tLMUWSet = new LMUWSet();
		LMUWSet tLMUWUnpassSet = new LMUWSet();
		int m = aLPGrpPolSet.size();
		for (int j = 1; j <= m; j++) {
			tLMUWSet.clear();
			tLMUWUnpassSet.clear();
			tLPGrpPolSchema = aLPGrpPolSet.get(j);
			Sql = "select * from LMUW where ( riskcode='"
					+ tLPGrpPolSchema.getRiskCode()
					+ "' or riskcode = '000000') " + " and RelaPolType='GP'"
					+ " and UWType='" + mLPGrpEdorItemSchema.getEdorType()
					+ "' order by riskcode ,UWorder"; // 取得个单险种核保规则

			logger.debug("-----sql:" + Sql);
			tLMUWSet = tLMUWDB.executeQuery(Sql);

			logger.debug("tLMUWSet size : " + tLMUWSet.size());
			for (int i = 1; i <= tLMUWSet.size(); i++) {
				BqCalBase tBqCalBase = new BqCalBase();
				LMUWSchema tLMUWSchema = tLMUWSet.get(i);

				tCalCode = tLMUWSchema.getCalCode(); // 得到险种编码

				tBqCalBase.setOperator(mLPGrpEdorItemSchema.getOperator());
				tBqCalBase.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
				tBqCalBase.setGrpPolNo(tLPGrpPolSchema.getGrpPolNo());
				tBqCalBase.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
				tBqCalBase.setEdorType(mLPGrpEdorItemSchema.getEdorType());
				tBqCalBase.setEdorTypeCal(mLPGrpEdorItemSchema.getEdorTypeCal());
				int tInterval = PubFun.calInterval(tLPGrpPolSchema
						.getCValiDate(),
						mLPGrpEdorItemSchema.getEdorValiDate(), "Y"); // 计算时间间隔
				// tBqCalBase.setInterval(tInterval);
				// tBqCalBase.setMult(tLPGrpPolSchema.getMult());
				// tBqCalBase.setPrem(tLPGrpPolSchema.getPrem());
				// tBqCalBase.setPayIntv(tLPGrpPolSchema.getPayIntv());
				// tBqCalBase.setGet(tLPGrpPolSchema.getAmnt());

				LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
				tLJSGetEndorseDB.setEndorsementNo(mLPGrpEdorItemSchema
						.getEdorNo());
				tLJSGetEndorseDB.setFeeOperationType(mLPGrpEdorItemSchema
						.getEdorType());
				LJSGetEndorseSet tLJSGetEndorseSet = tLJSGetEndorseDB.query();
				if (tLJSGetEndorseDB.mErrors.needDealError()) {
					mErrors.copyAllErrors(tLJSGetEndorseDB.mErrors);
					mErrors.addOneError(new CError("查询批改补退费表失败！"));
					return "0";
				}
				if (tLJSGetEndorseSet != null && tLJSGetEndorseSet.size() > 0) {
					tBqCalBase.setGetMoney(tLJSGetEndorseSet.get(1)
							.getGetMoney()); // 从财务表中获取补退费信息
				}

				if (!calUWEndorse(tBqCalBase, tCalCode)) // 核保计算
				{ // 如果核保不通过
					tLMUWUnpassSet.add(tLMUWSchema);
					if (tUWGrade == null || tUWGrade.equals("")) {
						tUWGrade = tLMUWSchema.getUWGrade();
					} else {
						if (tUWGrade != null
								&& tLMUWSchema.getUWGrade() != null
								&& tUWGrade.compareTo(tLMUWSchema.getUWGrade()) < 0) {
							tUWGrade = tLMUWSchema.getUWGrade(); // 保存当前保全项目的最大核保级别
						}
					}

					tUWState = "5";
				}
			}
			if (tUWGrade != null && tUWGrade.compareTo(mUWGrade) > 0) {
				mUWGrade = tUWGrade; // 保存该保全批单最大核保级别
			}

			tLPGrpPolSchema.setUWFlag(tUWState);
			tLPGrpPolSchema.setUWOperator(mGlobalInput.Operator);
			tLPGrpPolSchema.setUWDate(PubFun.getCurrentDate());
			tLPGrpPolSchema.setUWTime(PubFun.getCurrentTime());
			tLPGrpPolSchema.setOperator(mGlobalInput.Operator);
			tLPGrpPolSchema.setModifyDate(PubFun.getCurrentDate());
			tLPGrpPolSchema.setModifyTime(PubFun.getCurrentTime());

			LPGUWMasterSchema tLPGUWMasterSchema = prepareEdorGUWMaster(
					mLPGrpEdorItemSchema, tLPGrpPolSchema, tUWState, tUWGrade); // 生成团险核保主表信息
			prepareEdorGUWSub(tLPGUWMasterSchema); // 生成团险核保子表信息
			prepareEdorGUWError(tLPGUWMasterSchema, tLMUWUnpassSet); // 生成团险核保子表信息
		}
		return tUWState;
	}

	private String dealLCGrpPolUW(LCGrpPolSet aLCGrpPolSet) {
		String tUWState = "9";
		String tUWGrade = "A";
		String Sql, tCalCode;
		LCGrpPolSchema tLCGrpPolSchema = null;
		LMUWDB tLMUWDB = new LMUWDB();
		LMUWSet tLMUWSet = new LMUWSet();
		LMUWSet tLMUWUnpassSet = new LMUWSet();
		int m = aLCGrpPolSet.size();
		for (int j = 1; j <= m; j++) {
			tLMUWSet.clear();
			tLMUWUnpassSet.clear();
			tLCGrpPolSchema = aLCGrpPolSet.get(j);
			Sql = "select * from LMUW where ( riskcode='"
					+ tLCGrpPolSchema.getRiskCode()
					+ "' or riskcode = '000000') " + " and RelaPolType='GP'"
					+ " and UWType='" + mLPGrpEdorItemSchema.getEdorType()
					+ "' order by riskcode ,UWorder"; // 取得个单险种核保规则

			logger.debug("-----sql:" + Sql);
			tLMUWSet = tLMUWDB.executeQuery(Sql);

			logger.debug("tLMUWSet size : " + tLMUWSet.size());
			for (int i = 1; i <= tLMUWSet.size(); i++) {
				BqCalBase tBqCalBase = new BqCalBase();
				LMUWSchema tLMUWSchema = tLMUWSet.get(i);

				tCalCode = tLMUWSchema.getCalCode(); // 得到险种编码

				tBqCalBase.setOperator(mLPGrpEdorItemSchema.getOperator());
				tBqCalBase.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
				tBqCalBase.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
				tBqCalBase.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
				tBqCalBase.setEdorType(mLPGrpEdorItemSchema.getEdorType());
				tBqCalBase.setEdorTypeCal(mLPGrpEdorItemSchema.getEdorTypeCal());
				int tInterval = PubFun.calInterval(tLCGrpPolSchema
						.getCValiDate(),
						mLPGrpEdorItemSchema.getEdorValiDate(), "Y"); // 计算时间间隔
				// tBqCalBase.setInterval(tInterval);
				// tBqCalBase.setMult(tLCGrpPolSchema.getMult());
				// tBqCalBase.setPrem(tLCGrpPolSchema.getPrem());
				// tBqCalBase.setPayIntv(tLCGrpPolSchema.getPayIntv());
				// tBqCalBase.setGet(tLCGrpPolSchema.getAmnt());

				LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
				tLJSGetEndorseDB.setEndorsementNo(mLPGrpEdorItemSchema
						.getEdorNo());
				tLJSGetEndorseDB.setFeeOperationType(mLPGrpEdorItemSchema
						.getEdorType());
				LJSGetEndorseSet tLJSGetEndorseSet = tLJSGetEndorseDB.query();
				if (tLJSGetEndorseDB.mErrors.needDealError()) {
					mErrors.copyAllErrors(tLJSGetEndorseDB.mErrors);
					mErrors.addOneError(new CError("查询批改补退费表失败！"));
					return "0";
				}
				if (tLJSGetEndorseSet != null && tLJSGetEndorseSet.size() > 0) {
					tBqCalBase.setGetMoney(tLJSGetEndorseSet.get(1)
							.getGetMoney()); // 从财务表中获取补退费信息
				}

				if (!calUWEndorse(tBqCalBase, tCalCode)) // 核保计算
				{ // 如果核保不通过
					tLMUWUnpassSet.add(tLMUWSchema);
					if (tUWGrade == null || tUWGrade.equals("")) {
						tUWGrade = tLMUWSchema.getUWGrade();
					} else {
						if (tUWGrade != null
								&& tLMUWSchema.getUWGrade() != null
								&& tUWGrade.compareTo(tLMUWSchema.getUWGrade()) < 0) {
							tUWGrade = tLMUWSchema.getUWGrade(); // 保存当前保全项目的最大核保级别
						}
					}

					tUWState = "5";
				}
			}
			if (tUWGrade != null && tUWGrade.compareTo(mUWGrade) > 0) {
				mUWGrade = tUWGrade; // 保存该保全批单最大核保级别
			}
			LPGUWMasterSchema tLPGUWMasterSchema = prepareEdorGUWMaster(
					mLPGrpEdorItemSchema, tLCGrpPolSchema, tUWState, tUWGrade); // 生成核保主表信息
			prepareEdorGUWSub(tLPGUWMasterSchema); // 生成核保子表信息
			prepareEdorGUWError(tLPGUWMasterSchema, tLMUWUnpassSet); // 生成核保子表信息
		}
		return tUWState;
	}

	private String dealLPGrpContUW(LPGrpContSchema aLPGrpContSchema) {
		String tUWState = "9";
		String tUWGrade = "A";
		String Sql, tCalCode;
		LMUWDB tLMUWDB = new LMUWDB();
		LMUWSet tLMUWSet = new LMUWSet();
		LMUWSet tLMUWUnpassSet = new LMUWSet();

		Sql = "select * from LMUW where riskcode = '000000' "
				+ " and RelaPolType='GC'" + " and UWType='"
				+ mLPGrpEdorItemSchema.getEdorType() + "' order by UWorder"; // 取得团单核保规则

		logger.debug("-----sql:" + Sql);
		tLMUWSet = tLMUWDB.executeQuery(Sql);

		logger.debug("tLMUWSet size : " + tLMUWSet.size());
		for (int i = 1; i <= tLMUWSet.size(); i++) {
			BqCalBase tBqCalBase = new BqCalBase();
			LMUWSchema tLMUWSchema = tLMUWSet.get(i);

			tCalCode = tLMUWSchema.getCalCode(); // 得到险种编码

			tBqCalBase.setOperator(mLPGrpEdorItemSchema.getOperator());
			tBqCalBase.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
			tBqCalBase.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tBqCalBase.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tBqCalBase.setEdorTypeCal(mLPGrpEdorItemSchema.getEdorTypeCal());
			int tInterval = PubFun.calInterval(aLPGrpContSchema.getCValiDate(),
					mLPGrpEdorItemSchema.getEdorValiDate(), "Y"); // 计算时间间隔
			// tBqCalBase.setInterval(tInterval);
			// tBqCalBase.setMult(aLPGrpContSchema.getMult());
			// tBqCalBase.setPrem(aLPGrpContSchema.getPrem());
			// tBqCalBase.setPayIntv(aLPGrpContSchema.getPayIntv());
			// tBqCalBase.setGet(aLPGrpContSchema.getAmnt());

			LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
			tLJSGetEndorseDB.setEndorsementNo(mLPGrpEdorItemSchema.getEdorNo());
			tLJSGetEndorseDB.setFeeOperationType(mLPGrpEdorItemSchema
					.getEdorType());
			LJSGetEndorseSet tLJSGetEndorseSet = tLJSGetEndorseDB.query();
			if (tLJSGetEndorseDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLJSGetEndorseDB.mErrors);
				mErrors.addOneError(new CError("查询批改补退费表失败！"));
				return "0";
			}
			if (tLJSGetEndorseSet != null && tLJSGetEndorseSet.size() > 0) {
				tBqCalBase.setGetMoney(tLJSGetEndorseSet.get(1).getGetMoney()); // 从财务表中获取补退费信息
			}

			if (!calUWEndorse(tBqCalBase, tCalCode)) // 核保计算
			{ // 如果核保不通过
				tLMUWUnpassSet.add(tLMUWSchema);
				if (tUWGrade == null || tUWGrade.equals("")) {
					tUWGrade = tLMUWSchema.getUWGrade();
				} else {
					if (tUWGrade != null && tLMUWSchema.getUWGrade() != null
							&& tUWGrade.compareTo(tLMUWSchema.getUWGrade()) < 0) {
						tUWGrade = tLMUWSchema.getUWGrade(); // 保存当前保全项目的最大核保级别
					}
				}

				tUWState = "5";
			}
		}

		aLPGrpContSchema.setUWFlag(tUWState);
		aLPGrpContSchema.setUWOperator(mGlobalInput.Operator);
		aLPGrpContSchema.setUWDate(PubFun.getCurrentDate());
		aLPGrpContSchema.setUWTime(PubFun.getCurrentTime());
		aLPGrpContSchema.setOperator(mGlobalInput.Operator);
		aLPGrpContSchema.setModifyDate(PubFun.getCurrentDate());
		aLPGrpContSchema.setModifyTime(PubFun.getCurrentTime());

		if (tUWGrade != null && tUWGrade.compareTo(mUWGrade) > 0) {
			mUWGrade = tUWGrade; // 保存该保全批单最大核保级别
		}
		LPGCUWMasterSchema tLPGCUWMasterSchema = prepareEdorGCUWMaster(
				mLPGrpEdorItemSchema, aLPGrpContSchema, tUWState, tUWGrade); // 生成团体合同核保主表信息
		prepareEdorGCUWSub(tLPGCUWMasterSchema); // 生成团体合同核保子表信息
		prepareEdorGCUWError(tLPGCUWMasterSchema, tLMUWUnpassSet); // 生成团体合同核保子表信息
		return tUWState;
	}

	private String dealLCGrpContUW(LCGrpContSchema aLCGrpContSchema) {
		String tUWState = "9";
		String tUWGrade = "A";
		String Sql, tCalCode;
		LMUWDB tLMUWDB = new LMUWDB();
		LMUWSet tLMUWSet = new LMUWSet();
		LMUWSet tLMUWUnpassSet = new LMUWSet();

		Sql = "select * from LMUW where riskcode = '000000' "
				+ " and RelaPolType='GC'" + " and UWType='"
				+ mLPGrpEdorItemSchema.getEdorType() + "' order by UWorder"; // 取得个单核保规则

		logger.debug("-----sql:" + Sql);
		tLMUWSet = tLMUWDB.executeQuery(Sql);

		logger.debug("tLMUWSet size : " + tLMUWSet.size());
		for (int i = 1; i <= tLMUWSet.size(); i++) {
			BqCalBase tBqCalBase = new BqCalBase();
			LMUWSchema tLMUWSchema = tLMUWSet.get(i);

			tCalCode = tLMUWSchema.getCalCode(); // 得到险种编码

			tBqCalBase.setOperator(mLPGrpEdorItemSchema.getOperator());
			tBqCalBase.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
			tBqCalBase.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tBqCalBase.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tBqCalBase.setEdorTypeCal(mLPGrpEdorItemSchema.getEdorTypeCal());
			int tInterval = PubFun.calInterval(aLCGrpContSchema.getCValiDate(),
					mLPGrpEdorItemSchema.getEdorValiDate(), "Y"); // 计算时间间隔
			// tBqCalBase.setInterval(tInterval);
			// tBqCalBase.setMult(aLCGrpContSchema.getMult());
			// tBqCalBase.setPrem(aLCGrpContSchema.getPrem());
			// tBqCalBase.setPayIntv(aLCGrpContSchema.getPayIntv());
			// tBqCalBase.setGet(aLCGrpContSchema.getAmnt());

			LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
			tLJSGetEndorseDB.setEndorsementNo(mLPGrpEdorItemSchema.getEdorNo());
			tLJSGetEndorseDB.setFeeOperationType(mLPGrpEdorItemSchema
					.getEdorType());
			LJSGetEndorseSet tLJSGetEndorseSet = tLJSGetEndorseDB.query();
			if (tLJSGetEndorseDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLJSGetEndorseDB.mErrors);
				mErrors.addOneError(new CError("查询批改补退费表失败！"));
				return "0";
			}
			if (tLJSGetEndorseSet != null && tLJSGetEndorseSet.size() > 0) {
				tBqCalBase.setGetMoney(tLJSGetEndorseSet.get(1).getGetMoney()); // 从财务表中获取补退费信息
			}

			if (!calUWEndorse(tBqCalBase, tCalCode)) // 核保计算
			{ // 如果核保不通过
				tLMUWUnpassSet.add(tLMUWSchema);
				if (tUWGrade == null || tUWGrade.equals("")) {
					tUWGrade = tLMUWSchema.getUWGrade();
				} else {
					if (tUWGrade != null && tLMUWSchema.getUWGrade() != null
							&& tUWGrade.compareTo(tLMUWSchema.getUWGrade()) < 0) {
						tUWGrade = tLMUWSchema.getUWGrade(); // 保存当前保全项目的最大核保级别
					}
				}

				tUWState = "5";
			}
		}
		if (tUWGrade != null && tUWGrade.compareTo(mUWGrade) > 0) {
			mUWGrade = tUWGrade; // 保存该保全批单最大核保级别
		}
		LPGCUWMasterSchema tLPGCUWMasterSchema = prepareEdorGCUWMaster(
				mLPGrpEdorItemSchema, aLCGrpContSchema, tUWState, tUWGrade); // 生成团体合同核保主表信息
		prepareEdorGCUWSub(tLPGCUWMasterSchema); // 生成团体合同核保子表信息
		prepareEdorGCUWError(tLPGCUWMasterSchema, tLMUWUnpassSet); // 生成团体合同核保子表信息
		return tUWState;
	}

	private String getPUWState(LPGrpEdorItemSchema aLPGrpEdorItemSchema) {
		String tUWState = "9";
		String sql = "select * from LPEdorItem where EdorNo = '"
				+ aLPGrpEdorItemSchema.getEdorNo() + "'" + " and EdorType = '"
				+ aLPGrpEdorItemSchema.getEdorType() + "'"
				+ " and UWFlag <> '9'";
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.executeQuery(sql);
		if (tLPEdorItemDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorItemDB.mErrors);
			mErrors.addOneError(new CError("查询团单下个单核保结论错误！"));
			tUWState = "0";
		}
		if (tLPEdorItemSet != null && tLPEdorItemSet.size() > 0) {
			// tUWState = "5";
		}
		return tUWState;
	}

	/**
	 * 判断自动核保是否通过
	 * 
	 * @return
	 */
	private boolean calUWEndorse(BqCalBase aBqCalBase, String aCalCode) {
		Calculator mCalculator = new Calculator();
		mCalculator.setCalCode(aCalCode);
		// 增加基本要素
		mCalculator.addBasicFactor("Interval", aBqCalBase.getInterval());
		mCalculator.addBasicFactor("GetMoney", aBqCalBase.getGetMoney());
		mCalculator.addBasicFactor("Get", aBqCalBase.getGet());
		mCalculator.addBasicFactor("Mult", aBqCalBase.getMult());
		mCalculator.addBasicFactor("Prem", aBqCalBase.getPrem());
		mCalculator.addBasicFactor("PayIntv", aBqCalBase.getPayIntv());
		mCalculator.addBasicFactor("GetIntv", aBqCalBase.getGetIntv());
		mCalculator.addBasicFactor("AppAge", aBqCalBase.getAppAge());
		mCalculator.addBasicFactor("Sex", aBqCalBase.getSex());
		mCalculator.addBasicFactor("Job", aBqCalBase.getJob());
		mCalculator.addBasicFactor("PayEndYear", aBqCalBase.getPayEndYear());
		mCalculator.addBasicFactor("PayEndYearFlag", aBqCalBase
				.getPayEndYearFlag());
		mCalculator.addBasicFactor("GetStartDate", "");
		mCalculator.addBasicFactor("GetYear", aBqCalBase.getGetYear());
		mCalculator.addBasicFactor("Years", aBqCalBase.getYears());
		mCalculator.addBasicFactor("Grp", "");
		mCalculator.addBasicFactor("EdorTypeCal", aBqCalBase.getEdorTypeCal());
		mCalculator.addBasicFactor("GetFlag", "");
		mCalculator.addBasicFactor("CValiDate", "");
		mCalculator.addBasicFactor("Count", aBqCalBase.getCount());
		mCalculator.addBasicFactor("FirstPayDate", "");
		mCalculator.addBasicFactor("AddRate", aBqCalBase.getAddRate());
		mCalculator.addBasicFactor("GDuty", aBqCalBase.getGDuty());
		mCalculator.addBasicFactor("EdorNo", aBqCalBase.getEdorNo());
		mCalculator.addBasicFactor("EdorType", aBqCalBase.getEdorType());
		mCalculator.addBasicFactor("GrpContNo", aBqCalBase.getGrpContNo());
		mCalculator.addBasicFactor("GrpPolNo", aBqCalBase.getGrpPolNo());
		mCalculator.addBasicFactor("ContNo", aBqCalBase.getContNo());
		mCalculator.addBasicFactor("PolNo", aBqCalBase.getPolNo());

		String tStr = "";
		tStr = mCalculator.calculate();
		if (tStr == null || tStr.trim().equals("") || tStr.trim().equals("0")) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * //集体单处理核保信息 public boolean saveGrpEdorUW(LPGrpEdorMainSchema
	 * aLPGrpEdorMainSchema) { LPGrpEdorMainSet tLPGrpEdorMainSet = new
	 * LPGrpEdorMainSet(); PGrpEdorAutoUWBLS tPGrpEdorAutoUWBLS = new
	 * PGrpEdorAutoUWBLS();
	 * 
	 * LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
	 * tLPGrpEdorMainDB.setEdorNo(aLPGrpEdorMainSchema.getEdorNo());
	 * tLPGrpEdorMainDB.setGrpContNo(aLPGrpEdorMainSchema.getGrpContNo());
	 * logger.debug(aLPGrpEdorMainSchema.getEdorNo());
	 * logger.debug(aLPGrpEdorMainSchema.getGrpContNo());
	 * 
	 * tLPGrpEdorMainSet = tLPGrpEdorMainDB.query();
	 * logger.debug("---size:" + tLPGrpEdorMainSet.size()); if
	 * (tLPGrpEdorMainSet.size() > 0) { VData tInputData = new VData();
	 * 
	 * for (int j = 1; j <= tLPGrpEdorMainSet.size(); j++) { LPGrpEdorMainSchema
	 * tLPGrpEdorMainSchema = new LPGrpEdorMainSchema(); tLPGrpEdorMainSchema =
	 * tLPGrpEdorMainSet.get(j); this.getGrpAutoUWState(tLPGrpEdorMainSchema);
	 * //logger.debug(mGlobalInput.Operator); tInputData.clear();
	 * tInputData.addElement(tLPGrpEdorMainSchema);
	 * tInputData.addElement(mLPGUWErrorSet);
	 * tInputData.addElement(mLPGUWSubSchema);
	 * tInputData.addElement(mLPGUWMasterSchema);
	 * tInputData.addElement(mGlobalInput); if (mLPGUWErrorSet.size() > 0) {
	 * this.setUWFlag("5"); }
	 * 
	 * logger.debug("--------------------j:" + j); if
	 * (!tPGrpEdorAutoUWBLS.submitData(tInputData, this.getOperate())) { return
	 * false; } logger.debug("---size:" + tLPGrpEdorMainSet.size()); } }
	 * return true; }
	 */

	private LPGUWMasterSchema prepareEdorGUWMaster(
			LPGrpEdorItemSchema aLPGrpEdorItemSchema,
			LPGrpPolSchema aLPGrpPolSchema, String aUWState, String aUWGrade) // 生成核保主表信息
	{
		int tUWNo = 0;
		LPGUWMasterSchema tLPGUWMasterSchema = new LPGUWMasterSchema();

		LPGUWMasterDB tLPGUWMasterDB = new LPGUWMasterDB();
		tLPGUWMasterDB.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
		tLPGUWMasterDB.setEdorType(aLPGrpEdorItemSchema.getEdorType());
		tLPGUWMasterDB.setGrpPolNo(aLPGrpPolSchema.getGrpPolNo());
		LPGUWMasterSet tLPGUWMasterSet = tLPGUWMasterDB.query();
		if (tLPGUWMasterSet == null || tLPGUWMasterSet.size() <= 0) {
			tUWNo = 1;
			tLPGUWMasterSchema.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
			tLPGUWMasterSchema.setEdorType(aLPGrpEdorItemSchema.getEdorType());
			tLPGUWMasterSchema
					.setGrpContNo(aLPGrpEdorItemSchema.getGrpContNo());
			tLPGUWMasterSchema.setProposalGrpContNo(mLCGrpContSchema
					.getProposalGrpContNo());
			tLPGUWMasterSchema.setGrpPolNo(aLPGrpPolSchema.getGrpPolNo());
			tLPGUWMasterSchema.setGrpProposalNo(aLPGrpPolSchema
					.getGrpProposalNo());
			tLPGUWMasterSchema.setAutoUWFlag("1");
			tLPGUWMasterSchema.setPassFlag(aUWState);
			tLPGUWMasterSchema.setUWGrade(aUWGrade);
			tLPGUWMasterSchema.setAppGrade(aUWGrade);
			tLPGUWMasterSchema.setPostponeDay("");
			tLPGUWMasterSchema.setPostponeDate("");
			tLPGUWMasterSchema.setState(aUWState);
			tLPGUWMasterSchema.setUWIdea("");
			tLPGUWMasterSchema.setUpReportContent("");
			tLPGUWMasterSchema.setHealthFlag("0");
			tLPGUWMasterSchema.setQuesFlag("0");
			tLPGUWMasterSchema.setSpecFlag("0");
			tLPGUWMasterSchema.setAddPremFlag("0");
			tLPGUWMasterSchema.setAddPremReason("");
			tLPGUWMasterSchema.setReportFlag("0");
			tLPGUWMasterSchema.setPrintFlag("0");
			tLPGUWMasterSchema.setPrintFlag2("0");
			tLPGUWMasterSchema.setChangePolFlag("0");
			tLPGUWMasterSchema.setChangePolReason("");
			tLPGUWMasterSchema.setSpecReason("");
			tLPGUWMasterSchema
					.setManageCom(aLPGrpEdorItemSchema.getManageCom());
			tLPGUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLPGUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
		} else {
			tUWNo = tLPGUWMasterSet.get(1).getUWNo() + 1;
			tLPGUWMasterSchema = tLPGUWMasterSet.get(1);
			tLPGUWMasterSchema.setAutoUWFlag("1");
			tLPGUWMasterSchema.setPassFlag(aUWState);
			tLPGUWMasterSchema.setUWGrade(aUWGrade);
			tLPGUWMasterSchema.setAppGrade(aUWGrade);
		}
		tLPGUWMasterSchema.setUWNo(tUWNo);
		tLPGUWMasterSchema.setAppntName(aLPGrpPolSchema.getGrpName());
		tLPGUWMasterSchema.setAppntNo(aLPGrpPolSchema.getCustomerNo());
		tLPGUWMasterSchema.setAgentCode(aLPGrpPolSchema.getAgentCode());
		tLPGUWMasterSchema.setAgentGroup(aLPGrpPolSchema.getAgentGroup());
		tLPGUWMasterSchema.setOperator(mGlobalInput.Operator);
		tLPGUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLPGUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		mLPGUWMasterSet.add(tLPGUWMasterSchema);
		return tLPGUWMasterSchema;
	}

	private LPGUWMasterSchema prepareEdorGUWMaster(
			LPGrpEdorItemSchema aLPGrpEdorItemSchema,
			LCGrpPolSchema aLCGrpPolSchema, String aUWState, String aUWGrade) // 生成核保主信息
	{
		int tUWNo = 0;
		LPGUWMasterSchema tLPGUWMasterSchema = new LPGUWMasterSchema();

		LPGUWMasterDB tLPGUWMasterDB = new LPGUWMasterDB();
		tLPGUWMasterDB.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
		tLPGUWMasterDB.setEdorType(aLPGrpEdorItemSchema.getEdorType());
		tLPGUWMasterDB.setGrpPolNo(aLCGrpPolSchema.getGrpPolNo());
		LPGUWMasterSet tLPGUWMasterSet = tLPGUWMasterDB.query();
		if (tLPGUWMasterSet == null || tLPGUWMasterSet.size() <= 0) {
			tUWNo = 1;
			tLPGUWMasterSchema.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
			tLPGUWMasterSchema.setEdorType(aLPGrpEdorItemSchema.getEdorType());
			tLPGUWMasterSchema
					.setGrpContNo(aLPGrpEdorItemSchema.getGrpContNo());
			tLPGUWMasterSchema.setProposalGrpContNo(mLCGrpContSchema
					.getProposalGrpContNo());
			tLPGUWMasterSchema.setGrpPolNo(aLCGrpPolSchema.getGrpPolNo());
			tLPGUWMasterSchema.setGrpProposalNo(aLCGrpPolSchema
					.getGrpProposalNo());
			tLPGUWMasterSchema.setAutoUWFlag("1");
			tLPGUWMasterSchema.setPassFlag(aUWState);
			tLPGUWMasterSchema.setUWGrade(aUWGrade);
			tLPGUWMasterSchema.setAppGrade(aUWGrade);
			tLPGUWMasterSchema.setPostponeDay("");
			tLPGUWMasterSchema.setPostponeDate("");
			tLPGUWMasterSchema.setState(aUWState);
			tLPGUWMasterSchema.setUWIdea("");
			tLPGUWMasterSchema.setUpReportContent("");
			tLPGUWMasterSchema.setHealthFlag("0");
			tLPGUWMasterSchema.setQuesFlag("0");
			tLPGUWMasterSchema.setSpecFlag("0");
			tLPGUWMasterSchema.setAddPremFlag("0");
			tLPGUWMasterSchema.setAddPremReason("");
			tLPGUWMasterSchema.setReportFlag("0");
			tLPGUWMasterSchema.setPrintFlag("0");
			tLPGUWMasterSchema.setPrintFlag2("0");
			tLPGUWMasterSchema.setChangePolFlag("0");
			tLPGUWMasterSchema.setChangePolReason("");
			tLPGUWMasterSchema.setSpecReason("");
			tLPGUWMasterSchema
					.setManageCom(aLPGrpEdorItemSchema.getManageCom());
			tLPGUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLPGUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
		} else {
			tUWNo = tLPGUWMasterSet.get(1).getUWNo() + 1;
			tLPGUWMasterSchema = tLPGUWMasterSet.get(1);
			tLPGUWMasterSchema.setAutoUWFlag("1");
			tLPGUWMasterSchema.setPassFlag(aUWState);
			tLPGUWMasterSchema.setUWGrade(aUWGrade);
			tLPGUWMasterSchema.setAppGrade(aUWGrade);
		}
		tLPGUWMasterSchema.setUWNo(tUWNo);
		tLPGUWMasterSchema.setAppntName(aLCGrpPolSchema.getGrpName());
		tLPGUWMasterSchema.setAppntNo(aLCGrpPolSchema.getCustomerNo());
		tLPGUWMasterSchema.setAgentCode(aLCGrpPolSchema.getAgentCode());
		tLPGUWMasterSchema.setAgentGroup(aLCGrpPolSchema.getAgentGroup());
		tLPGUWMasterSchema.setOperator(mGlobalInput.Operator);
		tLPGUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLPGUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		mLPGUWMasterSet.add(tLPGUWMasterSchema);
		return tLPGUWMasterSchema;
	}

	private LPGUWSubSchema prepareEdorGUWSub(
			LPGUWMasterSchema aLPGUWMasterSchema) // 生成核保子表
	{
		LPGUWSubSchema tLPGUWSubSchema = new LPGUWSubSchema();

		tLPGUWSubSchema.setEdorNo(aLPGUWMasterSchema.getEdorNo());
		tLPGUWSubSchema.setEdorType(aLPGUWMasterSchema.getEdorType());
		tLPGUWSubSchema.setGrpContNo(aLPGUWMasterSchema.getGrpContNo());
		tLPGUWSubSchema.setProposalGrpContNo(aLPGUWMasterSchema
				.getProposalGrpContNo());
		tLPGUWSubSchema.setGrpPolNo(aLPGUWMasterSchema.getGrpPolNo());
		tLPGUWSubSchema.setGrpProposalNo(aLPGUWMasterSchema.getGrpProposalNo());
		tLPGUWSubSchema.setUWNo(aLPGUWMasterSchema.getUWNo());
		tLPGUWSubSchema.setAppntName(aLPGUWMasterSchema.getAppntName());
		tLPGUWSubSchema.setAppntNo(aLPGUWMasterSchema.getAppntNo());
		tLPGUWSubSchema.setInsuredName(aLPGUWMasterSchema.getInsuredName());
		tLPGUWSubSchema.setInsuredNo(aLPGUWMasterSchema.getInsuredNo());
		tLPGUWSubSchema.setAgentCode(aLPGUWMasterSchema.getAgentCode());
		tLPGUWSubSchema.setAgentGroup(aLPGUWMasterSchema.getAgentGroup());
		tLPGUWSubSchema.setAutoUWFlag(aLPGUWMasterSchema.getAutoUWFlag());
		tLPGUWSubSchema.setPassFlag(aLPGUWMasterSchema.getPassFlag());
		tLPGUWSubSchema.setUWGrade(aLPGUWMasterSchema.getUWGrade());
		tLPGUWSubSchema.setAppGrade(aLPGUWMasterSchema.getUWGrade());
		tLPGUWSubSchema.setPostponeDay(aLPGUWMasterSchema.getPostponeDay());
		tLPGUWSubSchema.setPostponeDate(aLPGUWMasterSchema.getPostponeDate());
		tLPGUWSubSchema.setState(aLPGUWMasterSchema.getState());
		tLPGUWSubSchema.setUWIdea(aLPGUWMasterSchema.getUWIdea());
		tLPGUWSubSchema.setUpReportContent(aLPGUWMasterSchema
				.getUpReportContent());
		tLPGUWSubSchema.setHealthFlag(aLPGUWMasterSchema.getHealthFlag());
		tLPGUWSubSchema.setQuesFlag(aLPGUWMasterSchema.getQuesFlag());
		tLPGUWSubSchema.setSpecFlag(aLPGUWMasterSchema.getSpecFlag());
		tLPGUWSubSchema.setAddPremFlag(aLPGUWMasterSchema.getAddPremFlag());
		tLPGUWSubSchema.setAddPremReason(aLPGUWMasterSchema.getAddPremReason());
		tLPGUWSubSchema.setReportFlag(aLPGUWMasterSchema.getReportFlag());
		tLPGUWSubSchema.setPrintFlag(aLPGUWMasterSchema.getPrintFlag());
		tLPGUWSubSchema.setPrintFlag2(aLPGUWMasterSchema.getPrintFlag2());
		tLPGUWSubSchema.setChangePolFlag(aLPGUWMasterSchema.getChangePolFlag());
		tLPGUWSubSchema.setChangePolReason(aLPGUWMasterSchema
				.getChangePolReason());
		tLPGUWSubSchema.setSpecReason(aLPGUWMasterSchema.getSpecFlag());
		tLPGUWSubSchema.setManageCom(aLPGUWMasterSchema.getManageCom());
		tLPGUWSubSchema.setOperator(mGlobalInput.Operator);
		tLPGUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		tLPGUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		tLPGUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLPGUWSubSchema.setMakeTime(PubFun.getCurrentTime());

		mLPGUWSubSet.add(tLPGUWSubSchema);
		return tLPGUWSubSchema;
	}

	private LPGUWErrorSet prepareEdorGUWError(
			LPGUWMasterSchema aLPGUWMasterSchema, LMUWSet aLMUWUnpassSet) // 生成核保错误信息
	{
		LPGUWErrorSchema tLPGUWErrorSchemaTemp = new LPGUWErrorSchema();

		tLPGUWErrorSchemaTemp.setEdorNo(aLPGUWMasterSchema.getEdorNo());
		tLPGUWErrorSchemaTemp.setEdorType(aLPGUWMasterSchema.getEdorType());
		tLPGUWErrorSchemaTemp.setGrpContNo(aLPGUWMasterSchema.getGrpContNo());
		tLPGUWErrorSchemaTemp.setProposalGrpContNo(aLPGUWMasterSchema
				.getProposalGrpContNo());
		tLPGUWErrorSchemaTemp.setGrpPolNo(aLPGUWMasterSchema.getGrpPolNo());
		tLPGUWErrorSchemaTemp.setGrpProposalNo(aLPGUWMasterSchema
				.getGrpProposalNo());
		tLPGUWErrorSchemaTemp.setUWNo(aLPGUWMasterSchema.getUWNo());
		tLPGUWErrorSchemaTemp.setUWPassFlag(aLPGUWMasterSchema.getPassFlag());
		tLPGUWErrorSchemaTemp.setUWGrade(aLPGUWMasterSchema.getUWGrade());
		tLPGUWErrorSchemaTemp.setManageCom(aLPGUWMasterSchema.getManageCom());
		tLPGUWErrorSchemaTemp.setModifyDate(PubFun.getCurrentDate());
		tLPGUWErrorSchemaTemp.setModifyTime(PubFun.getCurrentTime());

		int n = aLMUWUnpassSet.size();
		LMUWSchema tLMUWSchema = null;
		for (int i = 1; i <= n; i++) {
			LPGUWErrorSchema tLPGUWErrorSchema = new LPGUWErrorSchema();
			tLPGUWErrorSchema.setSchema(tLPGUWErrorSchemaTemp);
			tLMUWSchema = aLMUWUnpassSet.get(i);
			tLPGUWErrorSchema.setSerialNo(Integer.toString(i));
			tLPGUWErrorSchema.setUWError(tLMUWSchema.getRemark());
			tLPGUWErrorSchema.setUWRuleCode(tLMUWSchema.getCalCode());
			mLPGUWErrorSet.add(tLPGUWErrorSchema);
		}

		return mLPGUWErrorSet;
	}

	private LPGCUWMasterSchema prepareEdorGCUWMaster(
			LPGrpEdorItemSchema aLPGrpEdorItemSchema,
			LPGrpContSchema aLPGrpContSchema, String aUWState, String aUWGrade) // 生成团体合同核保主表信息
	{
		int tUWNo = 0;
		LPGCUWMasterSchema tLPGCUWMasterSchema = new LPGCUWMasterSchema();

		LPGCUWMasterDB tLPGCUWMasterDB = new LPGCUWMasterDB();
		tLPGCUWMasterDB.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
		tLPGCUWMasterDB.setEdorType(aLPGrpEdorItemSchema.getEdorType());
		tLPGCUWMasterDB.setGrpContNo(aLPGrpContSchema.getGrpContNo());
		LPGCUWMasterSet tLPGCUWMasterSet = tLPGCUWMasterDB.query();
		if (tLPGCUWMasterSet == null || tLPGCUWMasterSet.size() <= 0) {
			tUWNo = 1;

			tLPGCUWMasterSchema.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
			tLPGCUWMasterSchema.setEdorType(aLPGrpEdorItemSchema.getEdorType());
			tLPGCUWMasterSchema.setGrpContNo(aLPGrpContSchema.getGrpContNo());
			tLPGCUWMasterSchema.setProposalGrpContNo(aLPGrpContSchema
					.getProposalGrpContNo());
			tLPGCUWMasterSchema.setAutoUWFlag("1");
			tLPGCUWMasterSchema.setPassFlag(aUWState);
			tLPGCUWMasterSchema.setUWGrade(aUWGrade);
			tLPGCUWMasterSchema.setAppGrade(aUWGrade);
			tLPGCUWMasterSchema.setPostponeDay("");
			tLPGCUWMasterSchema.setPostponeDate("");
			tLPGCUWMasterSchema.setState(aUWState);
			tLPGCUWMasterSchema.setUWIdea("");
			tLPGCUWMasterSchema.setUpReportContent("");
			tLPGCUWMasterSchema.setHealthFlag("0");
			tLPGCUWMasterSchema.setQuesFlag("0");
			tLPGCUWMasterSchema.setSpecFlag("0");
			tLPGCUWMasterSchema.setAddPremFlag("0");
			tLPGCUWMasterSchema.setAddPremReason("");
			tLPGCUWMasterSchema.setReportFlag("0");
			tLPGCUWMasterSchema.setPrintFlag("0");
			tLPGCUWMasterSchema.setPrintFlag2("0");
			tLPGCUWMasterSchema.setChangePolFlag("0");
			tLPGCUWMasterSchema.setChangePolReason("");
			tLPGCUWMasterSchema.setSpecReason("");
			tLPGCUWMasterSchema.setManageCom(aLPGrpEdorItemSchema
					.getManageCom());
			tLPGCUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLPGCUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
		} else {
			tUWNo = tLPGCUWMasterSet.get(1).getUWNo() + 1;
			tLPGCUWMasterSchema = tLPGCUWMasterSet.get(1);
			tLPGCUWMasterSchema.setAutoUWFlag("1");
			tLPGCUWMasterSchema.setPassFlag(aUWState);
			tLPGCUWMasterSchema.setUWGrade(aUWGrade);
			tLPGCUWMasterSchema.setAppGrade(aUWGrade);
		}
		tLPGCUWMasterSchema.setUWNo(tUWNo);
		tLPGCUWMasterSchema.setAppntName(aLPGrpContSchema.getGrpName());
		tLPGCUWMasterSchema.setAppntNo(aLPGrpContSchema.getAppntNo());
		tLPGCUWMasterSchema.setAgentCode(aLPGrpContSchema.getAgentCode());
		tLPGCUWMasterSchema.setAgentGroup(aLPGrpContSchema.getAgentGroup());
		tLPGCUWMasterSchema.setOperator(mGlobalInput.Operator);
		tLPGCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLPGCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		mLPGCUWMasterSet.add(tLPGCUWMasterSchema);
		return tLPGCUWMasterSchema;
	}

	private LPGCUWMasterSchema prepareEdorGCUWMaster(
			LPGrpEdorItemSchema aLPGrpEdorItemSchema,
			LCGrpContSchema aLCGrpContSchema, String aUWState, String aUWGrade) // 生成团体合同核保主信息
	{
		int tUWNo = 0;
		LPGCUWMasterSchema tLPGCUWMasterSchema = new LPGCUWMasterSchema();

		LPGCUWMasterDB tLPGCUWMasterDB = new LPGCUWMasterDB();
		tLPGCUWMasterDB.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
		tLPGCUWMasterDB.setEdorType(aLPGrpEdorItemSchema.getEdorType());
		tLPGCUWMasterDB.setGrpContNo(aLCGrpContSchema.getGrpContNo());
		LPGCUWMasterSet tLPGCUWMasterSet = tLPGCUWMasterDB.query();
		if (tLPGCUWMasterSet == null || tLPGCUWMasterSet.size() <= 0) {
			tUWNo = 1;
			tLPGCUWMasterSchema.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
			tLPGCUWMasterSchema.setEdorType(aLPGrpEdorItemSchema.getEdorType());
			tLPGCUWMasterSchema.setGrpContNo(aLCGrpContSchema.getGrpContNo());
			tLPGCUWMasterSchema.setProposalGrpContNo(aLCGrpContSchema
					.getProposalGrpContNo());
			tLPGCUWMasterSchema.setAutoUWFlag("1");
			tLPGCUWMasterSchema.setPassFlag(aUWState);
			tLPGCUWMasterSchema.setUWGrade(aUWGrade);
			tLPGCUWMasterSchema.setAppGrade(aUWGrade);
			tLPGCUWMasterSchema.setPostponeDay("");
			tLPGCUWMasterSchema.setPostponeDate("");
			tLPGCUWMasterSchema.setState(aUWState);
			tLPGCUWMasterSchema.setUWIdea("");
			tLPGCUWMasterSchema.setUpReportContent("");
			tLPGCUWMasterSchema.setHealthFlag("0");
			tLPGCUWMasterSchema.setQuesFlag("0");
			tLPGCUWMasterSchema.setSpecFlag("0");
			tLPGCUWMasterSchema.setAddPremFlag("0");
			tLPGCUWMasterSchema.setAddPremReason("");
			tLPGCUWMasterSchema.setReportFlag("0");
			tLPGCUWMasterSchema.setPrintFlag("0");
			tLPGCUWMasterSchema.setPrintFlag2("0");
			tLPGCUWMasterSchema.setChangePolFlag("0");
			tLPGCUWMasterSchema.setChangePolReason("");
			tLPGCUWMasterSchema.setSpecReason("");
			tLPGCUWMasterSchema.setManageCom(aLPGrpEdorItemSchema
					.getManageCom());
			tLPGCUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLPGCUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
		} else {
			tUWNo = tLPGCUWMasterSet.get(1).getUWNo() + 1;
			tLPGCUWMasterSchema = tLPGCUWMasterSet.get(1);
			tLPGCUWMasterSchema.setAutoUWFlag("1");
			tLPGCUWMasterSchema.setPassFlag(aUWState);
			tLPGCUWMasterSchema.setUWGrade(aUWGrade);
			tLPGCUWMasterSchema.setAppGrade(aUWGrade);
		}
		tLPGCUWMasterSchema.setUWNo(tUWNo);
		tLPGCUWMasterSchema.setAppntName(aLCGrpContSchema.getGrpName());
		tLPGCUWMasterSchema.setAppntNo(aLCGrpContSchema.getAppntNo());
		tLPGCUWMasterSchema.setAgentCode(aLCGrpContSchema.getAgentCode());
		tLPGCUWMasterSchema.setAgentGroup(aLCGrpContSchema.getAgentGroup());
		tLPGCUWMasterSchema.setOperator(mGlobalInput.Operator);
		tLPGCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLPGCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		mLPGCUWMasterSet.add(tLPGCUWMasterSchema);
		return tLPGCUWMasterSchema;
	}

	private LPGCUWSubSchema prepareEdorGCUWSub(
			LPGCUWMasterSchema aLPGCUWMasterSchema) // 生成合同核保子表
	{
		LPGCUWSubSchema tLPGCUWSubSchema = new LPGCUWSubSchema();

		tLPGCUWSubSchema.setEdorNo(aLPGCUWMasterSchema.getEdorNo());
		tLPGCUWSubSchema.setEdorType(aLPGCUWMasterSchema.getEdorType());
		tLPGCUWSubSchema.setGrpContNo(aLPGCUWMasterSchema.getGrpContNo());
		tLPGCUWSubSchema.setProposalGrpContNo(aLPGCUWMasterSchema
				.getProposalGrpContNo());
		tLPGCUWSubSchema.setUWNo(aLPGCUWMasterSchema.getUWNo());
		tLPGCUWSubSchema.setAppntName(aLPGCUWMasterSchema.getAppntName());
		tLPGCUWSubSchema.setAppntNo(aLPGCUWMasterSchema.getAppntNo());
		tLPGCUWSubSchema.setInsuredName(aLPGCUWMasterSchema.getInsuredName());
		tLPGCUWSubSchema.setInsuredNo(aLPGCUWMasterSchema.getInsuredNo());
		tLPGCUWSubSchema.setAgentCode(aLPGCUWMasterSchema.getAgentCode());
		tLPGCUWSubSchema.setAgentGroup(aLPGCUWMasterSchema.getAgentGroup());
		tLPGCUWSubSchema.setAutoUWFlag(aLPGCUWMasterSchema.getAutoUWFlag());
		tLPGCUWSubSchema.setPassFlag(aLPGCUWMasterSchema.getPassFlag());
		tLPGCUWSubSchema.setUWGrade(aLPGCUWMasterSchema.getUWGrade());
		tLPGCUWSubSchema.setAppGrade(aLPGCUWMasterSchema.getUWGrade());
		tLPGCUWSubSchema.setPostponeDay(aLPGCUWMasterSchema.getPostponeDay());
		tLPGCUWSubSchema.setPostponeDate(aLPGCUWMasterSchema.getPostponeDate());
		tLPGCUWSubSchema.setState(aLPGCUWMasterSchema.getState());
		tLPGCUWSubSchema.setUWIdea(aLPGCUWMasterSchema.getUWIdea());
		tLPGCUWSubSchema.setUpReportContent(aLPGCUWMasterSchema
				.getUpReportContent());
		tLPGCUWSubSchema.setHealthFlag(aLPGCUWMasterSchema.getHealthFlag());
		tLPGCUWSubSchema.setQuesFlag(aLPGCUWMasterSchema.getQuesFlag());
		tLPGCUWSubSchema.setSpecFlag(aLPGCUWMasterSchema.getSpecFlag());
		tLPGCUWSubSchema.setAddPremFlag(aLPGCUWMasterSchema.getAddPremFlag());
		tLPGCUWSubSchema.setAddPremReason(aLPGCUWMasterSchema
				.getAddPremReason());
		tLPGCUWSubSchema.setReportFlag(aLPGCUWMasterSchema.getReportFlag());
		tLPGCUWSubSchema.setPrintFlag(aLPGCUWMasterSchema.getPrintFlag());
		tLPGCUWSubSchema.setPrintFlag2(aLPGCUWMasterSchema.getPrintFlag2());
		tLPGCUWSubSchema.setChangePolFlag(aLPGCUWMasterSchema
				.getChangePolFlag());
		tLPGCUWSubSchema.setChangePolReason(aLPGCUWMasterSchema
				.getChangePolReason());
		tLPGCUWSubSchema.setSpecReason(aLPGCUWMasterSchema.getSpecFlag());
		tLPGCUWSubSchema.setManageCom(aLPGCUWMasterSchema.getManageCom());
		tLPGCUWSubSchema.setOperator(mGlobalInput.Operator);
		tLPGCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		tLPGCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		tLPGCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLPGCUWSubSchema.setMakeTime(PubFun.getCurrentTime());

		mLPGCUWSubSet.add(tLPGCUWSubSchema);
		return tLPGCUWSubSchema;
	}

	private LPGCUWErrorSet prepareEdorGCUWError(
			LPGCUWMasterSchema aLPGCUWMasterSchema, LMUWSet aLMUWUnpassSet) // 生成合同核保错误信息
	{
		LPGCUWErrorSchema tLPGCUWErrorSchemaTemp = new LPGCUWErrorSchema();

		tLPGCUWErrorSchemaTemp.setEdorNo(aLPGCUWMasterSchema.getEdorNo());
		tLPGCUWErrorSchemaTemp.setEdorType(aLPGCUWMasterSchema.getEdorType());
		tLPGCUWErrorSchemaTemp.setGrpContNo(aLPGCUWMasterSchema.getGrpContNo());
		tLPGCUWErrorSchemaTemp.setProposalGrpContNo(aLPGCUWMasterSchema
				.getProposalGrpContNo());
		tLPGCUWErrorSchemaTemp.setUWNo(aLPGCUWMasterSchema.getUWNo());
		tLPGCUWErrorSchemaTemp.setAppntName(aLPGCUWMasterSchema.getAppntName());
		tLPGCUWErrorSchemaTemp.setAppntNo(aLPGCUWMasterSchema.getAppntNo());
		tLPGCUWErrorSchemaTemp.setInsuredName(aLPGCUWMasterSchema
				.getInsuredName());
		tLPGCUWErrorSchemaTemp.setInsuredNo(aLPGCUWMasterSchema.getInsuredNo());
		tLPGCUWErrorSchemaTemp.setUWPassFlag(aLPGCUWMasterSchema.getPassFlag());
		tLPGCUWErrorSchemaTemp.setUWGrade(aLPGCUWMasterSchema.getUWGrade());
		tLPGCUWErrorSchemaTemp.setManageCom(aLPGCUWMasterSchema.getManageCom());
		tLPGCUWErrorSchemaTemp.setModifyDate(PubFun.getCurrentDate());
		tLPGCUWErrorSchemaTemp.setModifyTime(PubFun.getCurrentTime());

		int n = aLMUWUnpassSet.size();
		LMUWSchema tLMUWSchema = null;
		for (int i = 1; i <= n; i++) {
			LPGCUWErrorSchema tLPGCUWErrorSchema = new LPGCUWErrorSchema();
			tLPGCUWErrorSchema.setSchema(tLPGCUWErrorSchemaTemp);
			tLMUWSchema = aLMUWUnpassSet.get(i);
			tLPGCUWErrorSchema.setSerialNo(Integer.toString(i));
			tLPGCUWErrorSchema.setUWError(tLMUWSchema.getRemark());
			tLPGCUWErrorSchema.setUWRuleCode(tLMUWSchema.getCalCode());
			mLPGCUWErrorSet.add(tLPGCUWErrorSchema);
		}

		return mLPGCUWErrorSet;
	}

	private LPGUWMasterMainSchema prepareEdorGUWMasterMain(
			LPGrpEdorMainSchema aLPGrpEdorMainSchema,
			LCGrpContSchema aLCGrpContSchema, String aUWState, String aUWGrade) // 生成批单核保主信息
	{
		int tUWNo = 0;
		LPGUWMasterMainSchema tLPGUWMasterMainSchema = new LPGUWMasterMainSchema();

		LPGUWMasterMainDB tLPGUWMasterMainDB = new LPGUWMasterMainDB();
		tLPGUWMasterMainDB.setEdorNo(aLPGrpEdorMainSchema.getEdorNo());
		tLPGUWMasterMainDB.setGrpContNo(aLCGrpContSchema.getGrpContNo());
		LPGUWMasterMainSet tLPGUWMasterMainSet = tLPGUWMasterMainDB.query();
		if (tLPGUWMasterMainSet == null || tLPGUWMasterMainSet.size() <= 0) {
			tUWNo = 1;
			tLPGUWMasterMainSchema.setEdorNo(aLPGrpEdorMainSchema.getEdorNo());
			tLPGUWMasterMainSchema
					.setGrpContNo(aLCGrpContSchema.getGrpContNo());
			tLPGUWMasterMainSchema.setProposalGrpContNo(aLCGrpContSchema
					.getProposalGrpContNo());
			tLPGUWMasterMainSchema.setAppntNo(aLCGrpContSchema.getAppntNo());
			tLPGUWMasterMainSchema
					.setAgentCode(aLCGrpContSchema.getAgentCode());
			tLPGUWMasterMainSchema.setAgentGroup(aLCGrpContSchema
					.getAgentGroup());
			tLPGUWMasterMainSchema.setAutoUWFlag("1");
			tLPGUWMasterMainSchema.setPassFlag(aUWState);
			tLPGUWMasterMainSchema.setUWGrade(aUWGrade);
			tLPGUWMasterMainSchema.setAppGrade(aUWGrade);
			tLPGUWMasterMainSchema.setPostponeDay("");
			tLPGUWMasterMainSchema.setPostponeDate("");
			tLPGUWMasterMainSchema.setState(aUWState);
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
			tLPGUWMasterMainSchema = tLPGUWMasterMainSet.get(1);
			tLPGUWMasterMainSchema.setAutoUWFlag("1");
			tLPGUWMasterMainSchema.setPassFlag(aUWState);
			tLPGUWMasterMainSchema.setUWGrade(aUWGrade);
			tLPGUWMasterMainSchema.setAppGrade(aUWGrade);
		}
		tLPGUWMasterMainSchema.setUWNo(tUWNo);

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

	/**
	 * 生成打印数据
	 * 
	 * @return
	 */
	private boolean getPrintData() {
		// remark by PQ
		try {
			logger.debug("start 生成打印数据...");
			VData tVData = new VData();
			// LPGrpEdorMainSchema tLPGrpEdorMainSchema = new
			// LPGrpEdorMainSchema();
			// tLPGrpEdorMainSchema.setGrpContNo(mLPGrpEdorMainSchema.getGrpContNo());
			tVData.addElement(mLPGrpEdorMainSchema);
			tVData.addElement(mGlobalInput);

			PrtGrpEndorsementBL tGrpPrtEndorsementBL = new PrtGrpEndorsementBL(
					mLPGrpEdorMainSchema.getEdorNo());
			if (!tGrpPrtEndorsementBL.submitData(tVData, "")) {
				mErrors.copyAllErrors(tGrpPrtEndorsementBL.mErrors);
				return false;
			}
			tVData = (VData) tGrpPrtEndorsementBL.getResult();
			mPrintMap = (MMap) tVData.getObjectByObjectName("MMap", 0);
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "生成打印数据失败");
			return false;
		}

		return true;
	}

	/**
	 * 查询收费打印参数
	 * 
	 * @return
	 */
	private boolean getPrintParams() {
		// 判断是否有保全交费产生，如果有，取得相应的打印交费表需要的参数
		logger.debug("TJS数据提取**************");
		String prtOtherNo = mLPGrpEdorMainSchema.getEdorNo();
		String strSql = "select * from ljspay where otherno = '" + prtOtherNo
				+ "' and othernotype='3'";
		logger.debug("strSql :" + strSql);

		LJSPayDB tPayDB = new LJSPayDB();
		LJSPaySet tPaySet = tPayDB.executeQuery(strSql);
		if (tPaySet != null && tPaySet.size() == 1) {
			mPayPrintParams = prtOtherNo;
		}
		return true;
	}

	private boolean prepareOutputData() {
		mMap.put(mLPGrpEdorMainSet, "UPDATE");
		mMap.put(mLPGrpEdorItemSet, "UPDATE");
		mMap.put(mLPGrpContSet, "UPDATE");
		mMap.put(mLPGrpPolSet, "UPDATE");
		mMap.put(mLPGUWMasterSet, "DELETE&INSERT");
		mMap.put(mLPGUWSubSet, "INSERT");
		mMap.put(mLPGUWErrorSet, "INSERT");
		mMap.put(mLPGCUWMasterSet, "DELETE&INSERT");
		mMap.put(mLPGCUWSubSet, "INSERT");
		mMap.put(mLPGCUWErrorSet, "INSERT");
		mMap.put(mLPGUWMasterMainSet, "DELETE&INSERT");
		mMap.put(mLPGUWSubMainSet, "INSERT");
		if (mUWFinaResult != null) {
			mMap.add(mUWFinaResult);
		}
		if (mPrintMap != null) {
			mMap.add(mPrintMap);
		}
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	public static void main(String[] args) {
		VData tInputData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		PGrpEdorAutoUWBL aPGrpEdorAutoUWBL = new PGrpEdorAutoUWBL();
		LPGrpEdorMainSchema tLPGrpEdorMainSchema = new LPGrpEdorMainSchema();

		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";

		tLPGrpEdorMainSchema.setEdorNo("430110000000230");

		tInputData.addElement(tLPGrpEdorMainSchema);
		tInputData.addElement(tGlobalInput);

		aPGrpEdorAutoUWBL.submitData(tInputData, "");
		logger.debug(aPGrpEdorAutoUWBL.mErrors.getErrContent());
	}

}
