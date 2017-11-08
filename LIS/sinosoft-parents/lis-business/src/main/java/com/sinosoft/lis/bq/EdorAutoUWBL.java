package com.sinosoft.lis.bq;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

//import utils.system;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCInsuredRelatedDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.db.LMRiskSortDB;
import com.sinosoft.lis.db.LMUWDB;
import com.sinosoft.lis.db.LPCUWMasterDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPContTempInfoDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPIndUWMasterDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.db.LPInsuredRelatedDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPUWMasterDB;
import com.sinosoft.lis.db.LPUWMasterMainDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LMUWSchema;
import com.sinosoft.lis.schema.LPCUWErrorSchema;
import com.sinosoft.lis.schema.LPCUWMasterSchema;
import com.sinosoft.lis.schema.LPCUWSubSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPContTempInfoSchema;
import com.sinosoft.lis.schema.LPIndUWMasterSchema;
import com.sinosoft.lis.schema.LPIndUWSubSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPUWErrorSchema;
import com.sinosoft.lis.schema.LPUWMasterMainSchema;
import com.sinosoft.lis.schema.LPUWMasterSchema;
import com.sinosoft.lis.schema.LPUWSubMainSchema;
import com.sinosoft.lis.schema.LPUWSubSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCInsuredRelatedSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LMRiskSortSet;
import com.sinosoft.lis.vschema.LMUWSet;
import com.sinosoft.lis.vschema.LPCUWErrorSet;
import com.sinosoft.lis.vschema.LPCUWMasterSet;
import com.sinosoft.lis.vschema.LPCUWSubSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPContTempInfoSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPIndUWErrorSet;
import com.sinosoft.lis.vschema.LPIndUWMasterSet;
import com.sinosoft.lis.vschema.LPIndUWSubSet;
import com.sinosoft.lis.vschema.LPInsuredRelatedSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPUWErrorSet;
import com.sinosoft.lis.vschema.LPUWMasterMainSet;
import com.sinosoft.lis.vschema.LPUWMasterSet;
import com.sinosoft.lis.vschema.LPUWSubMainSet;
import com.sinosoft.lis.vschema.LPUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 个单保全自动核保类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Tjj
 * @ReWrite ZhangRong,FanX
 * @version 1.0
 */
public class EdorAutoUWBL {
private static Logger logger = Logger.getLogger(EdorAutoUWBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 入口标志 */
	private String mContType = "";
	private String mUWGrade = "A";
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap mUWFinaResult = null;
	private MMap mPrintMap = null;
	private LPEdorMainSchema mLPEdorMainSchema = null;
	private LPEdorMainSet mLPEdorMainSet = null;
	private LPEdorItemSchema mLPEdorItemSchema = null;
	private LPEdorItemSet mLPEdorItemSet = null;
	private List mBomList = new ArrayList();
	private Boolean mBomListFlag=false;
	private PrepareBOMBQUWBL mPrepareBOMBQUWBL = new PrepareBOMBQUWBL();
	private LPContSchema mLPContSchema = null;
	private LCContSchema mLCContSchema = null;
	private LCContSet mLCContSet = null;
	private LPContSet mLPContSet = null;
	private LPCUWMasterSet mLPCUWMasterSet = null;
	private LPCUWSubSet mLPCUWSubSet = null;
	private LPCUWErrorSet mLPCUWErrorSet = null;
	private LPContTempInfoSet UPDate_LPContTempInfoSet  = new LPContTempInfoSet();
	
	/**增加被保人核保数据*/
	private LPIndUWMasterSet mLPIndUWMasterSet = new LPIndUWMasterSet();
	private LPIndUWSubSet mLPIndUWSubSet = new LPIndUWSubSet();
	private LPIndUWErrorSet mLPIndUWErrorSet = new LPIndUWErrorSet();

	private LCPolSet mLCPolSet = null;
	private LPPolSet mLPPolSet = null;
	private LPUWMasterSet mLPUWMasterSet = null;
	private LPUWSubSet mLPUWSubSet = null;
	private LPUWErrorSet mLPUWErrorSet = null;

	private LPUWMasterMainSet mLPUWMasterMainSet = null;
	private LPUWSubMainSet mLPUWSubMainSet = null;
	String mFlag;
	String mPayPrintParams = "";
	boolean isEdorAppLevel = false; // 是否在保全申请级做自动核保的标识

	public EdorAutoUWBL() {
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
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		if (!prepareData()) { // 数据准备操作（preparedata())
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		if (!isEdorAppLevel) { // 不是在申请级别做自动核保就分开做数据提交
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(mResult, "")) { // 数据提交
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);

				CError tError = new CError();
				tError.moduleName = "EdorAutoUWBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);

				return false;
			}
			mResult.clear();
		}

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("UWFlag", mLPEdorMainSchema.getUWState());
		tTransferData
				.setNameAndValue("UWGrade", mLPEdorMainSchema.getUWGrade());
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
		mLPEdorMainSchema = (LPEdorMainSchema) cInputData
				.getObjectByObjectName("LPEdorMainSchema", 0);
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mContType = (String) cInputData.getObjectByObjectName("String", 0);
		if (mContType == null || mContType.equals("")) {
			mContType = "I";
		}
		TransferData tTransferData = (TransferData) cInputData
				.getObjectByObjectName("TransferData", 0);
		if (tTransferData != null
				&& tTransferData.getValueByName("EdorAppLevel") != null
				&& "Y".equals((String) tTransferData
						.getValueByName("EdorAppLevel"))) {
			isEdorAppLevel = true;
		}

		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setEdorAcceptNo(mLPEdorMainSchema.getEdorAcceptNo());
		tLPEdorMainDB.setEdorNo(mLPEdorMainSchema.getEdorNo());
		tLPEdorMainDB.setContNo(mLPEdorMainSchema.getContNo());
		mLPEdorMainSet = tLPEdorMainDB.query();
		if (mLPEdorMainSet == null || mLPEdorMainSet.size() != 1) {
			mErrors.copyAllErrors(tLPEdorMainDB.mErrors);
			mErrors.addOneError(new CError("查询批单信息失败！"));
			return false;
		}
		mLPEdorMainSchema = mLPEdorMainSet.get(1);
		
		//增加客户层保全处理，lpconttempinfo.state='2',表示自核已处理完毕
		LPEdorAppDB xLPEdorAppDB = new LPEdorAppDB();
		xLPEdorAppDB.setEdorAcceptNo(mLPEdorMainSchema.getEdorAcceptNo());
		if(!xLPEdorAppDB.getInfo())
		{
			mErrors.copyAllErrors(xLPEdorAppDB.mErrors);
			mErrors.addOneError(new CError("查询保全申请主表信息失败！"));
			return false;
		}
		LPEdorAppSchema xLPEdorAppSchema = new LPEdorAppSchema();
		xLPEdorAppSchema=xLPEdorAppDB.getSchema();
		if(xLPEdorAppSchema.getOtherNoType().equals("1")) //客户层保全
		{
			LPContTempInfoDB  xLPContTempInfoDB = new LPContTempInfoDB();
			LPContTempInfoSchema tLPContTempInfoSchema  = new LPContTempInfoSchema();
			
			xLPContTempInfoDB.setICEdorAcceptNo(mLPEdorMainSchema.getEdorAcceptNo());
			xLPContTempInfoDB.setEdorNo(mLPEdorMainSchema.getEdorNo());
			tLPContTempInfoSchema=xLPContTempInfoDB.query().get(1);
			if(tLPContTempInfoSchema!=null){
				tLPContTempInfoSchema.setState("2");
				tLPContTempInfoSchema.setModifyDate(PubFun.getCurrentDate());
				tLPContTempInfoSchema.setModifyTime(PubFun.getCurrentTime());
				UPDate_LPContTempInfoSet.add(tLPContTempInfoSchema);
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
		mLCContSet = new LCContSet();
		mLPContSet = new LPContSet();
		mLCPolSet = new LCPolSet();
		mLPPolSet = new LPPolSet();
		mLPUWMasterSet = new LPUWMasterSet();
		mLPUWSubSet = new LPUWSubSet();
		mLPUWErrorSet = new LPUWErrorSet();
		mLPCUWMasterSet = new LPCUWMasterSet();
		mLPCUWSubSet = new LPCUWSubSet();
		mLPCUWErrorSet = new LPCUWErrorSet();
		mLPUWMasterMainSet = new LPUWMasterMainSet();
		mLPUWSubMainSet = new LPUWSubMainSet();
		mMap = new MMap();
		mResult.clear();

		String sql = "select * from LPEdorItem where (UWFlag in ('0','5') or UWFlag is null)"
				+ " and EdorAcceptNo = '"
				+ "?EdorAcceptNo?"
				+ "'"
				+ " and EdorNo='"
				+ "?EdorNo?"
				+ "'"
				+ " and ContNo='"
				+ "?ContNo?"
				+ "'"
				+ " order by MakeDate,MakeTime";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(sql);
        sqlbv.put("EdorAcceptNo", mLPEdorMainSchema.getEdorAcceptNo());
        sqlbv.put("EdorNo", mLPEdorMainSchema.getEdorNo());
        sqlbv.put("ContNo", mLPEdorMainSchema.getContNo());
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		mLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv);
		int m = mLPEdorItemSet.size();
		boolean tFlag = true;
		for (int i = 1; i <= m; i++) {
			mLPEdorItemSchema = mLPEdorItemSet.get(i);
			// 未审批通过,未申请确认
			if ((!"a".equals(mLPEdorItemSchema.getEdorState()) && (!"2"
					.equals(mLPEdorItemSchema.getEdorState())))) {
				// @@错误处理
				tFlag = false;
				continue;
			}
		}
		if (!tFlag) {
			CError tError = new CError();
			tError.moduleName = "PGrpEdorAutoUWBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "批单号:" + mLPEdorMainSchema.getEdorNo()
					+ "有个别项目未申请确认或者未审批确认!";
			logger.debug("批单号:" + mLPEdorMainSchema.getEdorNo()
					+ "有个别项目未申请确认或者未审批确认!");
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	private boolean dealData() {
		String aFlag = "9";
		mUWGrade = "A";
		mLPEdorMainSchema.setUWState("9"); // 预设置核保结论为为通过

		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setEdorNo(mLPEdorMainSchema.getEdorNo());
		tLPContDB.setContNo(mLPEdorMainSchema.getContNo());
		LPContSet tLPContSet = tLPContDB.query(); // 从P表获取保全个单信息

		if (tLPContDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPContDB.mErrors);
			mErrors.addOneError(new CError("查询保全个人合同表失败！"));
			return false;
		}
		if (tLPContSet != null && tLPContSet.size() == 1) {
			mLPContSchema = tLPContSet.get(1);
			mLPContSet.add(mLPContSchema);
		} else {
			mLPContSchema = null;
		}
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorMainSchema.getContNo());
		LCContSet tLCContSet = tLCContDB.query(); // 从C表获取个单信息（为后面准备核保主表数据的方便，即使P表中有合同单信息，这里也从C表中获取信息）
		if (tLCContSet == null || tLCContSet.size() != 1) {
			mErrors.copyAllErrors(tLCContDB.mErrors);
			mErrors.addOneError(new CError("查询个人合同表失败！"));
			return false;
		}
		mLCContSchema = tLCContSet.get(1);

		String tEdorTypeNew = "", tEdorTypeOld = "";
		int n = mLPEdorItemSet.size();
		for (int i = 1; i <= n; i++) {
			mLPEdorItemSchema = mLPEdorItemSet.get(i);
			mLPEdorItemSchema.setUWFlag("9"); // 预设置核保结论为为通过
			tEdorTypeNew = mLPEdorItemSchema.getEdorType();


			
			
			aFlag = getAutoUWState(); // 对个单合同下险种进行核保
			if (aFlag.equals("0")) { // 核保过程出现异常
				return false;
			}
			if (!aFlag.equals("9")) {
				mLPEdorItemSchema.setUWFlag("5"); // 设置核保结论
				mLPEdorMainSchema.setUWState("5"); // 如果有一个保全项目自核不通过，则该批单自核不通过
			}

			int j = 0;
			for (j = 1; j < i; j++) {
				if (mLPEdorItemSet.get(j).getEdorType().equals(
						mLPEdorItemSchema.getEdorType())
						&& mLPEdorItemSet.get(j).getContNo().equals(
								mLPEdorItemSchema.getContNo())) { // 如果项目的个单合同已经核保
					break;
				}
			}

			if (j >= i) {
				if (mLPContSchema != null) {
					aFlag = dealLPContUW(mLPContSchema); // 对合同单进行核保（根据P表）

				} else {
					if (mLCContSchema.getUWFlag() == null
							|| mLCContSchema.getUWFlag().equals("")
							|| mLCContSchema.getUWFlag().equals("0")) {
						mLCContSet.add(mLCContSchema);
					}
					if (!tEdorTypeNew.equals(tEdorTypeOld)) {
						aFlag = dealLCContUW(mLCContSchema); // 对合同单进行核保（根据C表）
					}
					tEdorTypeOld = tEdorTypeNew;
				}
				if (aFlag.equals("0")) { // 核保过程出现异常
					return false;
				}
				if (!aFlag.equals("9")) {
					mLPEdorItemSchema.setUWFlag("5"); // 设置核保结论
					mLPEdorMainSchema.setUWState("5"); // 如果合同单保全项目自核不通过，则该批单自核不通过
				}
			}

			mLPEdorItemSchema.setUWDate(PubFun.getCurrentDate());
			mLPEdorItemSchema.setUWTime(PubFun.getCurrentTime());
			mLPEdorItemSchema.setUWOperator(mGlobalInput.Operator);
			mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
			mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
			mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		}

		mLPEdorMainSchema.setUWDate(PubFun.getCurrentDate());
		mLPEdorMainSchema.setUWTime(PubFun.getCurrentTime());
		mLPEdorMainSchema.setUWGrade(mUWGrade);
		mLPEdorMainSchema.setAppGrade(mUWGrade);
		mLPEdorMainSchema.setUWOperator(mGlobalInput.Operator);
		mLPEdorMainSchema.setOperator(mGlobalInput.Operator);
		mLPEdorMainSchema.setModifyDate(PubFun.getCurrentDate());
		mLPEdorMainSchema.setModifyTime(PubFun.getCurrentTime());
		mLPEdorMainSet.add(mLPEdorMainSchema);
		LPUWMasterMainSchema tLPUWMasterMainSchema = prepareEdorUWMasterMain(
				mLPEdorMainSchema, mLCContSchema, mLPEdorMainSchema
						.getUWState(), mUWGrade);
		if (tLPUWMasterMainSchema == null) {
			mErrors.addOneError(new CError("生成保全批单核保主表信息失败！"));
			return false;
		}
		if (prepareEdorUWSubMain(tLPUWMasterMainSchema) == null) {
			mErrors.addOneError(new CError("生成保全批单核保子表信息失败！"));
			return false;
		}

		return true;
	}

	private String getAutoUWState() {
		String tUWState = "9";

		if (mLPEdorItemSchema.getPolNo() != null
				&& !mLPEdorItemSchema.getPolNo().equals("")
				&& !mLPEdorItemSchema.getPolNo().equals("000000")
				&& !mLPEdorItemSchema.getEdorType().equals("NS")) { // 如果批改项目指定险种保单号，则对该险种核保，从LPPol表中获取险种信息
			//add by xiongzh 09-11-2 对于新增附加险(NS)，需要对其lppol中记录逐一进行自核，所以不走该分支，走另一分支
			LPPolDB tLPPolDB = new LPPolDB();
			// tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			// tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
			// tLPPolDB.setContNo(mLPEdorItemSchema.getContNo());
			// tLPPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
			// LPPolSet tLPPolSet = tLPPolDB.query();
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql("select * from LPPol where EdorNo = '"+ "?EdorNo?" + "'"+ " and EdorType = '"+ "?EdorType?"+ "' and ContNo = '"+ "?ContNo?" + "'"+ " and (PolNo = '" + "?PolNo?"+ "' or MainPolNo = '"+ "?MainPolNo?" + "')");
			sqlbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sqlbv.put("EdorType", mLPEdorItemSchema.getEdorType());
			sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
			sqlbv.put("PolNo", mLPEdorItemSchema.getPolNo());
			sqlbv.put("MainPolNo", mLPEdorItemSchema.getPolNo());
			LPPolSet tLPPolSet = tLPPolDB
					.executeQuery(sqlbv);
			
			if (tLPPolSet == null || tLPPolSet.size() != 1) {
				LCPolDB tLCPolDB = new LCPolDB();
				tLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
				tLCPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
				LCPolSet tLCPolSet = tLCPolDB.query();

				if (tLCPolSet != null && tLCPolSet.size() > 0) {
					tUWState = dealLCPolUW(tLCPolSet); // 调用核保函数
				}
			} else {
				LPPolSchema tLPPolSchema = tLPPolSet.get(1);
				tUWState = dealLPPolUW(tLPPolSchema); // 调用核保函数
				mLPPolSet.add(tLPPolSchema);
			}
		} else { // 如果批改项目未指定险种保单号，则对所有合同下险种核保，从LCPol表中获取险种信息
            //add by xiongzh 09-11-2 对于新增附加险(NS)，需要对其lppol中记录逐一进行自核，所以走该分支
			LPPolDB tLPPolDB = new LPPolDB();
			tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPolDB.setContNo(mLPEdorItemSchema.getContNo());
			LPPolSet tLPPolSet = tLPPolDB.query();
			LPPolSchema tLPPolSchema = null;

			if (tLPPolSet != null && tLPPolSet.size() > 0) {
				for (int i = 1; i <= tLPPolSet.size(); i++) {
					tLPPolSchema = tLPPolSet.get(i);
					if (dealLPPolUW(tLPPolSchema).equals("5")) {
						tUWState = "5"; // 调用核保函数
					}
					mLPPolSet.add(tLPPolSchema);
				}
			}
		}

		return tUWState;
	}

	private String dealLPPolUW(LPPolSchema aLPPolSchema) {
		String tUWState = "9";
		String tUWGrade = "A";
		String Sql, tCalCode;
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		LMUWDB tLMUWDB = new LMUWDB();
		LMUWSet tLMUWSet = new LMUWSet();
		LMUWSet tLMUWUnpassSet = new LMUWSet();
		if (mContType != null && mContType.equals("I")) {
			Sql = "select * from LMUW where ( riskcode='"
					+ "?riskcode?" + "' or riskcode = '000000') "
					+ " and RelaPolType='IP'" + " and UWType='"
					+ "?UWType?"
					+ "' order by riskcode ,UWorder"; // 取得个单险种核保规则
			sqlbv.sql(Sql);
			sqlbv.put("riskcode", aLPPolSchema.getRiskCode());
			sqlbv.put("UWType", mLPEdorItemSchema.getEdorType());
		} else if (mContType != null && mContType.equals("G")) {
			Sql = "select * from LMUW where ( riskcode='"
					+ "?riskcode?" + "' or riskcode = '000000') "
					+ " and RelaPolType='GIP'" + " and UWType='"
					+ "?UWType?"
					+ "' order by riskcode ,UWorder"; // 取得团单险种核保规则
			sqlbv.sql(Sql);
			sqlbv.put("riskcode", aLPPolSchema.getRiskCode());
			sqlbv.put("UWType", mLPEdorItemSchema.getEdorType());
		} else {
			CError tError = new CError();
			tError.moduleName = "EdorAutoUWBL";
			tError.functionName = "getAutoUWState";
			tError.errorMessage = "保全自动核保的（团单、个单）类别传输错误!";
			this.mErrors.addOneError(tError);
			return "0";
		}

		BqCalBase tBqCalBase = new BqCalBase();
		tBqCalBase.setOperator(mLPEdorItemSchema.getOperator());
		tBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
		tBqCalBase.setPolNo(aLPPolSchema.getPolNo());
		tBqCalBase.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tBqCalBase.setEdorType(mLPEdorItemSchema.getEdorType());
		tBqCalBase.setAppntNo(aLPPolSchema.getAppntNo());
		int tInterval = PubFun.calInterval(aLPPolSchema.getCValiDate(),
				mLPEdorItemSchema.getEdorValiDate(), "Y"); // 计算时间间隔
		tBqCalBase.setInsuredNo(aLPPolSchema.getInsuredNo());
		tBqCalBase.setEdorValiDate(mLPEdorItemSchema.getEdorValiDate());
		tBqCalBase.setRiskCode(aLPPolSchema.getRiskCode());
		LMRiskSortDB tLMRiskSortDB = new LMRiskSortDB();
		tLMRiskSortDB.setRiskCode(aLPPolSchema.getRiskCode());
		tLMRiskSortDB.setRiskSortType("2");
		LMRiskSortSet tLMRiskSortSet = tLMRiskSortDB.query();
		if (tLMRiskSortSet != null && tLMRiskSortSet.size() > 0) {
			ExeSQL tExeSQL = new ExeSQL();
			try {
				SQLwithBindVariables sbv=new SQLwithBindVariables();
//				sbv.sql("select HEALTHYAMNT2('"+ "?s1?" + "', '"+ "?s2?" + "',  '"+ "?s3?"+ "') from dual");
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
					sbv.sql("select HEALTHYAMNT2('"+ "?s1?" + "', '"+ "?s2?" + "',  '"+ "?s3?"+ "') from dual");
				}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					sbv.sql(" {call HEALTHYAMNT2(?#@d#?,'"+ "?s1?" + "', '"+ "?s2?" + "',  '"+ "?s3?"+ "') }");
				}
				sbv.put("s1", aLPPolSchema.getInsuredNo());
				sbv.put("s2", aLPPolSchema.getRiskCode());
				sbv.put("s3", tLMRiskSortSet.get(1).getRiskSortValue());
				tBqCalBase.setSumDangerAmnt(Double.parseDouble(tExeSQL
						.getOneValue(sbv))); // 风险保额
				//comment by jiaqiangli 2009-03-26 报错，应佟盟要求，注释掉，现有程序没有调用
//				tBqCalBase.setSumInvaliAmnt(Double.parseDouble(tExeSQL
//						.getOneValue("select INVALIDHEALTHYAMNT('"
//								+ aLPPolSchema.getInsuredNo() + "', '"
//								+ aLPPolSchema.getRiskCode() + "',  '"
//								+ tLMRiskSortSet.get(1).getRiskSortValue()
//								+ "') from dual"))); // 失效保单风险保额
			} catch (Exception ex) {
				CError.buildErr(this, "计算风险保额失败！");
			}
		}

		LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
		tLJSGetEndorseDB.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
		tLJSGetEndorseDB.setFeeOperationType(mLPEdorItemSchema.getEdorType());
		LJSGetEndorseSet tLJSGetEndorseSet = tLJSGetEndorseDB.query();
		if (tLJSGetEndorseDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLJSGetEndorseDB.mErrors);
			mErrors.addOneError(new CError("查询批改补退费表失败！"));
			return "0";
		}
		if (tLJSGetEndorseSet != null && tLJSGetEndorseSet.size() > 0) {
			tBqCalBase.setGetMoney(tLJSGetEndorseSet.get(1).getGetMoney()); // 从财务表中获取补退费信息
		}
		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		tLPInsuredDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsuredDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsuredDB.setContNo(aLPPolSchema.getContNo());
		tLPInsuredDB.setInsuredNo(aLPPolSchema.getInsuredNo());
		LPInsuredSet tLPInsuredSet = tLPInsuredDB.query(); // 从P表中获取被保人信息
		if (tLPInsuredDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPInsuredDB.mErrors);
			mErrors.addOneError(new CError("查询保全被保人信息失败!"));
			return "0";
		}
		if (tLPInsuredSet == null || tLPInsuredSet.size() <= 0) { // 如果P表中无被保人信息
			LCInsuredDB tLCInsuredDB = new LCInsuredDB(); // 从C表中获取被保人信息
			tLCInsuredDB.setContNo(aLPPolSchema.getContNo());
			tLCInsuredDB.setInsuredNo(aLPPolSchema.getInsuredNo());
			LCInsuredSet tLCInsuredSet = tLCInsuredDB.query();
			if (tLCInsuredSet == null || tLCInsuredSet.size() <= 0) {
				mErrors.copyAllErrors(tLCInsuredDB.mErrors);
				mErrors.addOneError(new CError("查询被保人信息失败!"));
				return "0";
			}
			// tBqCalBase.setSex(tLCInsuredSet.get(1).getSex());
			// tBqCalBase.setJob(tLCInsuredSet.get(1).getOccupationType());
		} else {
			// tBqCalBase.setSex(tLPInsuredSet.get(1).getSex());
			// tBqCalBase.setJob(tLPInsuredSet.get(1).getOccupationType());
		}
		// ===ADD===zhangtao===2006-01-23=====添加计算要素（第二被保人客户号）=====BGN================================
		String sql = " select * from LPInsuredRelated where polno = " + " '"
				+ "?polno?" + "' " + " and maincustomerno = '"
				+ "?maincustomerno?" + "' and edorno = '"
				+ "?edorno?" + "' " + " and edortype = '"
				+ "?edortype?" + "' ";
		LPInsuredRelatedDB tLPInsuredRelatedDB = new LPInsuredRelatedDB();
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(sql);
		sbv.put("polno", aLPPolSchema.getPolNo());
		sbv.put("maincustomerno", tBqCalBase.getInsuredNo());
		sbv.put("edorno", mLPEdorMainSchema.getEdorNo());
		sbv.put("edortype", mLPEdorItemSchema.getEdorType());
		LPInsuredRelatedSet tLPInsuredRelatedSet = tLPInsuredRelatedDB
				.executeQuery(sbv); // 从P表中获取第二被保人信息
		if (tLPInsuredRelatedDB.mErrors.needDealError()) {
			mErrors.addOneError(new CError("查询保全被保人信息失败!"));
			return "0";
		}
		if (tLPInsuredRelatedSet == null || tLPInsuredRelatedSet.size() <= 0) { // P表中无第二被保人信息
			sql = " select * from LCInsuredRelated where polno = " + " '"
					+ "?polno?" + "' "
					+ " and maincustomerno = '" + "?maincustomerno?"
					+ "' ";
            sbv=new SQLwithBindVariables();
			sbv.sql(sql);
			sbv.put("polno", aLPPolSchema.getPolNo());
			sbv.put("maincustomerno", tBqCalBase.getInsuredNo());
			LCInsuredRelatedDB tLCInsuredRelatedDB = new LCInsuredRelatedDB(); // 从C表中获取
			LCInsuredRelatedSet tLCInsuredRelatedSet = tLCInsuredRelatedDB
					.executeQuery(sbv);
			if (tLCInsuredRelatedSet == null
					|| tLCInsuredRelatedSet.size() <= 0) {
				tBqCalBase.setSecondInsuredNo(""); // 没有也不抱错
			} else {
				tBqCalBase.setSecondInsuredNo(tLCInsuredRelatedSet.get(1)
						.getCustomerNo());
			}
		} else {
			tBqCalBase.setSecondInsuredNo(tLPInsuredRelatedSet.get(1)
					.getCustomerNo());
		}
		// ===ADD===zhangtao===2006-01-23=====添加计算要素（第二被保人客户号）=====END================================

		tLMUWSet = tLMUWDB.executeQuery(sqlbv);

		logger.debug("tLMUWSet size : " + tLMUWSet.size());
		for (int i = 1; i <= tLMUWSet.size(); i++) {
			LMUWSchema tLMUWSchema = tLMUWSet.get(i);

			tCalCode = tLMUWSchema.getCalCode(); // 得到险种编码

			if (!calUWEndorse(tBqCalBase, tCalCode)) { // 核保计算, 如果核保不通过
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

		aLPPolSchema.setUWFlag(tUWState);
		aLPPolSchema.setUWCode(mGlobalInput.Operator);
		aLPPolSchema.setUWDate(PubFun.getCurrentDate());
		aLPPolSchema.setUWTime(PubFun.getCurrentTime());
		aLPPolSchema.setOperator(mGlobalInput.Operator);
		aLPPolSchema.setModifyDate(PubFun.getCurrentDate());
		aLPPolSchema.setModifyTime(PubFun.getCurrentTime());

		LPUWMasterSchema tLPUWMasterSchema = prepareEdorUWMaster(
				mLPEdorItemSchema, aLPPolSchema, tUWState, tUWGrade); // 生成核保主表信息
		if (tLPUWMasterSchema != null) {
			prepareEdorUWSub(tLPUWMasterSchema); // 生成核保子表信息
			prepareEdorUWError(tLPUWMasterSchema, tLMUWUnpassSet); // 生成核保子表信息
		}
		return tUWState;
	}

	private String dealLCPolUW(LCPolSet aLCPolSet) {
		String tUWState = "9";
		String tUWGrade = "A";
		String Sql, tCalCode;
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		LCPolSchema tLCPolSchema = null;
		LMUWDB tLMUWDB = new LMUWDB();
		LMUWSet tLMUWSet = new LMUWSet();
		LMUWSet tLMUWUnpassSet = new LMUWSet();
		int m = aLCPolSet.size();
		for (int j = 1; j <= m; j++) {
			tLMUWSet.clear();
			tLMUWUnpassSet.clear();
			tLCPolSchema = aLCPolSet.get(j);
			if (tLCPolSchema.getUWFlag() == null
					|| tLCPolSchema.getUWFlag().equals("")
					|| tLCPolSchema.getUWFlag().equals("0")) {
				mLCPolSet.add(tLCPolSchema); // 如果未核保过，则置核保标志
			}
			if (mContType != null && mContType.equals("I")) {
				Sql = "select * from LMUW where ( riskcode='"
						+ "?riskcode?"
						+ "' or riskcode = '000000') "
						+ " and RelaPolType='IP'" + " and UWType='"
						+ "?UWType?"
						+ "' order by riskcode ,UWorder"; // 取得个单险种核保规则
				sqlbv.sql(Sql);
				sqlbv.put("riskcode", tLCPolSchema.getRiskCode());
				sqlbv.put("UWType", mLPEdorItemSchema.getEdorType());
			} else if (mContType != null && mContType.equals("G")) {
				Sql = "select * from LMUW where ( riskcode='"
						+ "?riskcode?"
						+ "' or riskcode = '000000') "
						+ " and RelaPolType='GIP'" + " and UWType='"
						+ "?UWType?"
						+ "' order by riskcode ,UWorder"; // 取得团单险种核保规则
				sqlbv.sql(Sql);
				sqlbv.put("riskcode", tLCPolSchema.getRiskCode());
				sqlbv.put("UWType", mLPEdorItemSchema.getEdorType());
			} else {
				CError tError = new CError();
				tError.moduleName = "EdorAutoUWBL";
				tError.functionName = "dealLCPolUW";
				tError.errorMessage = "保全自动核保的（团单、个单）类别传输错误!";
				this.mErrors.addOneError(tError);
				return "0";
			}

			BqCalBase tBqCalBase = new BqCalBase();
			tBqCalBase.setOperator(mLPEdorItemSchema.getOperator());
			tBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
			tBqCalBase.setPolNo(tLCPolSchema.getPolNo());
			tBqCalBase.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tBqCalBase.setEdorType(mLPEdorItemSchema.getEdorType());
			tBqCalBase.setAppntNo(tLCPolSchema.getAppntNo());
			tBqCalBase.setInsuredNo(tLCPolSchema.getInsuredNo());
			tBqCalBase.setEdorValiDate(mLPEdorItemSchema.getEdorValiDate());
			tBqCalBase.setRiskCode(tLCPolSchema.getRiskCode());
			LMRiskSortDB tLMRiskSortDB = new LMRiskSortDB();
			tLMRiskSortDB.setRiskCode(tLCPolSchema.getRiskCode());
			tLMRiskSortDB.setRiskSortType("2");
			LMRiskSortSet tLMRiskSortSet = tLMRiskSortDB.query();
			if (tLMRiskSortSet != null && tLMRiskSortSet.size() > 0) {
				try {
					ExeSQL tExeSQL = new ExeSQL();
					SQLwithBindVariables sbv=new SQLwithBindVariables();
					sbv.sql("select HEALTHYAMNT2('"+ "?s1?" + "', '"+ "?s2?" + "',  '"+ "?s3?"+ "') from dual");
					if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
						sbv.sql("select HEALTHYAMNT2('"+ "?s1?" + "', '"+ "?s2?" + "',  '"+ "?s3?"+ "') from dual");
					}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
						sbv.sql(" { call HEALTHYAMNT2(?#@d#?,'"+ "?s1?" + "', '"+ "?s2?" + "',  '"+ "?s3?"+ "') }");
					}
					sbv.put("s1", tLCPolSchema.getInsuredNo());
					sbv.put("s2", tLCPolSchema.getRiskCode());
					sbv.put("s3", tLMRiskSortSet.get(1).getRiskSortValue());
					tBqCalBase.setSumDangerAmnt(Double.parseDouble(tExeSQL
							.getOneValue(sbv))); // 风险保额
					//comment by jiaqiangli 2009-03-26 报错，应佟盟要求，注释掉，现有程序没有调用
//					tBqCalBase.setSumInvaliAmnt(Double.parseDouble(tExeSQL
//							.getOneValue("select INVALIDHEALTHYAMNT('"
//									+ tLCPolSchema.getInsuredNo() + "', '"
//									+ tLCPolSchema.getRiskCode() + "',  '"
//									+ tLMRiskSortSet.get(1).getRiskSortValue()
//									+ "') from dual"))); // 失效保单风险保额
				} catch (Exception ex) {
					CError.buildErr(this, "计算风险保额失败！");
					// return "0";
				}

			}
			int tInterval = PubFun.calInterval(tLCPolSchema.getCValiDate(),
					mLPEdorItemSchema.getEdorValiDate(), "Y"); // 计算时间间隔

			LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
			tLJSGetEndorseDB.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
			tLJSGetEndorseDB.setFeeOperationType(mLPEdorItemSchema
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
			LPInsuredDB tLPInsuredDB = new LPInsuredDB();
			tLPInsuredDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPInsuredDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPInsuredDB.setContNo(tLCPolSchema.getContNo());
			tLPInsuredDB.setInsuredNo(tLCPolSchema.getInsuredNo());
			LPInsuredSet tLPInsuredSet = tLPInsuredDB.query(); // 从P表中获取被保人信息
			if (tLPInsuredDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLPInsuredDB.mErrors);
				mErrors.addOneError(new CError("查询保全被保人信息失败!"));
				return "0";
			}
			if (tLPInsuredSet == null || tLPInsuredSet.size() <= 0) { // 如果P表中无被保人信息
				LCInsuredDB tLCInsuredDB = new LCInsuredDB(); // 从C表中获取被保人信息
				tLCInsuredDB.setContNo(tLCPolSchema.getContNo());
				tLCInsuredDB.setInsuredNo(tLCPolSchema.getInsuredNo());
				LCInsuredSet tLCInsuredSet = tLCInsuredDB.query();
				if (tLCInsuredSet == null || tLCInsuredSet.size() <= 0) {
					mErrors.copyAllErrors(tLCInsuredDB.mErrors);
					mErrors.addOneError(new CError("查询被保人信息失败!"));
					return "0";
				}
			} else {
			}
			// ===ADD===zhangtao===2006-01-23=====添加计算要素（第二被保人客户号）=====BGN================================
			String sql = " select * from LPInsuredRelated where polno = "
					+ " '" + "?polno?" + "' "
					+ " and maincustomerno = '" + "?maincustomerno?"
					+ "' and edorno = '" + "?edorno?" + "' "
					+ " and edortype = '" + "?edortype?"
					+ "' ";
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(sql);
			sbv.put("polno", tLCPolSchema.getPolNo());
			sbv.put("maincustomerno", tBqCalBase.getInsuredNo());
			sbv.put("edorno", mLPEdorMainSchema.getEdorNo());
			sbv.put("edortype", mLPEdorItemSchema.getEdorType());
			LPInsuredRelatedDB tLPInsuredRelatedDB = new LPInsuredRelatedDB();
			LPInsuredRelatedSet tLPInsuredRelatedSet = tLPInsuredRelatedDB
					.executeQuery(sbv); // 从P表中获取第二被保人信息
			if (tLPInsuredRelatedDB.mErrors.needDealError()) {
				mErrors.addOneError(new CError("查询保全被保人信息失败!"));
				return "0";
			}
			if (tLPInsuredRelatedSet == null
					|| tLPInsuredRelatedSet.size() <= 0) { // P表中无第二被保人信息
				sql = " select * from LCInsuredRelated where polno = " + " '"
						+ "?polno?" + "' "
						+ " and maincustomerno = '" + "?maincustomerno?"
						+ "' ";
				sbv=new SQLwithBindVariables();
				sbv.sql(sql);
				sbv.put("polno", tLCPolSchema.getPolNo());
				sbv.put("maincustomerno", tBqCalBase.getInsuredNo());
				LCInsuredRelatedDB tLCInsuredRelatedDB = new LCInsuredRelatedDB(); // 从C表中获取
				LCInsuredRelatedSet tLCInsuredRelatedSet = tLCInsuredRelatedDB
						.executeQuery(sbv);
				if (tLCInsuredRelatedSet == null
						|| tLCInsuredRelatedSet.size() <= 0) {
					tBqCalBase.setSecondInsuredNo("");
				} else {
					tBqCalBase.setSecondInsuredNo(tLCInsuredRelatedSet.get(1)
							.getCustomerNo());
				}
			} else {
				tBqCalBase.setSecondInsuredNo(tLPInsuredRelatedSet.get(1)
						.getCustomerNo());
			}
			// ===ADD===zhangtao===2006-01-23=====添加计算要素（第二被保人客户号）=====END================================
			tLMUWSet = tLMUWDB.executeQuery(sqlbv);

			logger.debug("tLMUWSet size : " + tLMUWSet.size());
			for (int i = 1; i <= tLMUWSet.size(); i++) {
				LMUWSchema tLMUWSchema = tLMUWSet.get(i);

				tCalCode = tLMUWSchema.getCalCode(); // 得到险种编码

				if (!calUWEndorse(tBqCalBase, tCalCode)) { // 核保计算, 如果核保不通过
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

			tLCPolSchema.setUWFlag(tUWState);
			tLCPolSchema.setUWCode(mGlobalInput.Operator);
			tLCPolSchema.setUWDate(PubFun.getCurrentDate());
			tLCPolSchema.setUWTime(PubFun.getCurrentTime());

			if (tUWGrade != null && tUWGrade.compareTo(mUWGrade) > 0) {
				mUWGrade = tUWGrade; // 保存该保全批单最大核保级别
			}

			LPUWMasterSchema tLPUWMasterSchema = prepareEdorUWMaster(
					mLPEdorItemSchema, tLCPolSchema, tUWState, tUWGrade); // 生成核保主表信息
			if (tLPUWMasterSchema != null) {
				prepareEdorUWSub(tLPUWMasterSchema); // 生成核保子表信息
				prepareEdorUWError(tLPUWMasterSchema, tLMUWUnpassSet); // 生成核保子表信息
			}
		}
		return tUWState;
	}

	private String dealLPContUW(LPContSchema aLPContSchema) {
		String tUWState = "9";
		String tUWGrade = "A";
		String Sql, tCalCode;
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		LMUWDB tLMUWDB = new LMUWDB();
		LMUWSet tLMUWSet = new LMUWSet();
		LMUWSet tLMUWUnpassSet = new LMUWSet();

		if (mContType != null && mContType.equals("I")) {
			Sql = "select * from LMUW where riskcode = '000000' "
					+ " and RelaPolType='IC'" + " and UWType='"
					+ "?UWType?" + "' order by UWorder"; // 取得个单核保规则
			sqlbv.sql(Sql);
			sqlbv.put("UWType", mLPEdorItemSchema.getEdorType());
		} else if (mContType != null && mContType.equals("G")) {
			Sql = "select * from LMUW where riskcode = '000000' "
					+ " and RelaPolType='GIC'" + " and UWType='"
					+ "?UWType?" + "' order by UWorder"; // 取得团单核保规则
			sqlbv.sql(Sql);
			sqlbv.put("UWType", mLPEdorItemSchema.getEdorType());
		} else {
			CError tError = new CError();
			tError.moduleName = "EdorAutoUWBL";
			tError.functionName = "dealLPContUW";
			tError.errorMessage = "保全自动核保的（团单、个单）类别传输错误!";
			this.mErrors.addOneError(tError);
			return "0";
		}

		BqCalBase tBqCalBase = new BqCalBase();
		tBqCalBase.setOperator(mLPEdorItemSchema.getOperator());
		tBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
		tBqCalBase.setGrpContNo(mLPEdorItemSchema.getGrpContNo());
		tBqCalBase.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tBqCalBase.setEdorType(mLPEdorItemSchema.getEdorType());
		tBqCalBase.setAppntNo(aLPContSchema.getAppntNo());
		tBqCalBase.setEdorValiDate(mLPEdorItemSchema.getEdorValiDate());
		int tInterval = PubFun.calInterval(aLPContSchema.getCValiDate(),
				mLPEdorItemSchema.getEdorValiDate(), "Y"); // 计算时间间隔

		LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
		tLJSGetEndorseDB.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
		tLJSGetEndorseDB.setFeeOperationType(mLPEdorItemSchema.getEdorType());
		LJSGetEndorseSet tLJSGetEndorseSet = tLJSGetEndorseDB.query();
		if (tLJSGetEndorseDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLJSGetEndorseDB.mErrors);
			mErrors.addOneError(new CError("查询批改补退费表失败！"));
			return "0";
		}
		if (tLJSGetEndorseSet != null && tLJSGetEndorseSet.size() > 0) {
			tBqCalBase.setGetMoney(tLJSGetEndorseSet.get(1).getGetMoney()); // 从财务表中获取补退费信息
		}
		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		tLPInsuredDB.setEdorNo(mLPEdorMainSchema.getEdorNo());
		tLPInsuredDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsuredDB.setContNo(aLPContSchema.getContNo());
		if ("000000".equals(mLPEdorItemSchema.getInsuredNo())) {
			tLPInsuredDB.setInsuredNo(aLPContSchema.getInsuredNo());
		} else {
			tLPInsuredDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		}
		LPInsuredSet tLPInsuredSet = tLPInsuredDB.query(); // 从P表中获取被保人信息
		if (tLPInsuredDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPInsuredDB.mErrors);
			mErrors.addOneError(new CError("查询保全被保人信息失败!"));
			return "0";
		}
		if (tLPInsuredSet == null || tLPInsuredSet.size() <= 0) { // 如果P表中无被保人信息
			LCInsuredDB tLCInsuredDB = new LCInsuredDB(); // 从C表中获取被保人信息
			tLCInsuredDB.setContNo(aLPContSchema.getContNo());
			if ("000000".equals(mLPEdorItemSchema.getInsuredNo())) {
				tLPInsuredDB.setInsuredNo(aLPContSchema.getInsuredNo());
			} else {
				tLPInsuredDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
			}
			LCInsuredSet tLCInsuredSet = tLCInsuredDB.query();
			if (tLCInsuredSet == null || tLCInsuredSet.size() <= 0) {
				mErrors.copyAllErrors(tLCInsuredDB.mErrors);
				mErrors.addOneError(new CError("查询被保人信息失败!"));
				return "0";
			}
			tBqCalBase.setInsuredNo(tLPInsuredDB.getInsuredNo());
		} else {
			tBqCalBase.setInsuredNo(tLPInsuredDB.getInsuredNo());
		}
		// ===ADD===zhangtao===2006-01-23=====添加计算要素（第二被保人客户号）=====BGN================================
		String sql = "";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
	      sql = " select * from LPInsuredRelated where polno = "
				+ " (select polno from lcpol where polno = mainpolno and contno = '"
				+ "?contno?" + "' and rownum = 1) "
				+ " and maincustomerno = '" + "?maincustomerno?"
				+ "' and edorno = '" + "?edorno?" + "' "
				+ " and edortype = '" + "?edortype?" + "' ";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
		  sql = " select * from LPInsuredRelated where polno = "
				+ " (select polno from lcpol where polno = mainpolno and contno = '"
				+ "?contno?" + "' limit 0,1) "
				+ " and maincustomerno = '" + "?maincustomerno?"
				+ "' and edorno = '" + "?edorno?" + "' "
				+ " and edortype = '" + "?edortype?" + "' ";			
		}
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(sql);
		sbv.put("contno", mLPEdorItemSchema.getContNo());
		sbv.put("maincustomerno", tBqCalBase.getInsuredNo());
		sbv.put("edorno", mLPEdorItemSchema.getEdorType());
		sbv.put("edortype", mLPEdorItemSchema.getEdorType());
		LPInsuredRelatedDB tLPInsuredRelatedDB = new LPInsuredRelatedDB();
		LPInsuredRelatedSet tLPInsuredRelatedSet = tLPInsuredRelatedDB
				.executeQuery(sbv); // 从P表中获取第二被保人信息
		if (tLPInsuredRelatedDB.mErrors.needDealError()) {
			mErrors.addOneError(new CError("查询保全被保人信息失败!"));
			return "0";
		}
		if (tLPInsuredRelatedSet == null || tLPInsuredRelatedSet.size() <= 0) { // P表中无第二被保人信息
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			sql = " select * from LCInsuredRelated where polno = "
					+ " (select polno from lcpol where polno = mainpolno and contno = '"
					+ "?contno?" + "' and rownum = 1) "
					+ " and maincustomerno = '" + "?maincustomerno?"
					+ "' ";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				sql = " select * from LCInsuredRelated where polno = "
						+ " (select polno from lcpol where polno = mainpolno and contno = '"
						+ "?contno?" + "' limit 0,1) "
						+ " and maincustomerno = '" + "?maincustomerno?"
						+ "' ";
			}
			sbv=new SQLwithBindVariables();
			sbv.sql(sql);
			sbv.put("contno", mLPEdorItemSchema.getContNo());
			sbv.put("maincustomerno", tBqCalBase.getInsuredNo());
			LCInsuredRelatedDB tLCInsuredRelatedDB = new LCInsuredRelatedDB(); // 从C表中获取
			LCInsuredRelatedSet tLCInsuredRelatedSet = tLCInsuredRelatedDB
					.executeQuery(sbv);
			if (tLCInsuredRelatedSet == null
					|| tLCInsuredRelatedSet.size() <= 0) {
				tBqCalBase.setSecondInsuredNo("");
			} else {
				tBqCalBase.setSecondInsuredNo(tLCInsuredRelatedSet.get(1)
						.getCustomerNo());
			}
		} else {
			tBqCalBase.setSecondInsuredNo(tLPInsuredRelatedSet.get(1)
					.getCustomerNo());
		}
		// ===ADD===zhangtao===2006-01-23=====添加计算要素（第二被保人客户号）=====END================================
		tLMUWSet = tLMUWDB.executeQuery(sqlbv);

		logger.debug("tLMUWSet size : " + tLMUWSet.size());
		for (int i = 1; i <= tLMUWSet.size(); i++) {
			LMUWSchema tLMUWSchema = tLMUWSet.get(i);

			tCalCode = tLMUWSchema.getCalCode(); // 得到险种编码

			if (!calUWEndorse(tBqCalBase, tCalCode)) { // 核保计算, 如果核保不通过
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

		aLPContSchema.setUWFlag(tUWState);
		aLPContSchema.setUWOperator(mGlobalInput.Operator);
		aLPContSchema.setUWDate(PubFun.getCurrentDate());
		aLPContSchema.setUWTime(PubFun.getCurrentTime());
		// aLPContSchema.setOperator(mGlobalInput.Operator);
		aLPContSchema.setModifyDate(PubFun.getCurrentDate());
		aLPContSchema.setModifyTime(PubFun.getCurrentTime());

		LPCUWMasterSchema tLPCUWMasterSchema = prepareEdorCUWMaster(
				mLPEdorItemSchema, aLPContSchema, tUWState, tUWGrade); // 生成合同核保主表信息
		prepareEdorCUWSub(tLPCUWMasterSchema); // 生成合同核保子表信息
		prepareEdorCUWError(tLPCUWMasterSchema, tLMUWUnpassSet); // 生成合同核保子表信息
		//为核保准备被保人数据，只对个险，团险保持不变			
		//是个险保全，则添加
		if(mLPEdorItemSchema.getGrpContNo()==null || "00000000000000000000".equals(mLPEdorItemSchema.getGrpContNo()))
		{
			LPIndUWMasterSchema tLPIndUWMasterSchema = prepareEdorIndUWMaster(
					mLPEdorItemSchema, aLPContSchema, tUWState, tUWGrade); // 生成被保人核保主表信息
			prepareEdorIndUWSub(tLPIndUWMasterSchema); // 生成被保人核保子表信息
		}
		
		return tUWState;
	}

	private String dealLCContUW(LCContSchema aLCContSchema) {
		String tUWState = "9";
		String tUWGrade = "A";
		String Sql, tCalCode;
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		LMUWDB tLMUWDB = new LMUWDB();
		LMUWSet tLMUWSet = new LMUWSet();
		LMUWSet tLMUWUnpassSet = new LMUWSet();

		if (mContType != null && mContType.equals("I")) {
			Sql = "select * from LMUW where riskcode = '000000' "
					+ " and RelaPolType='IC'" + " and UWType='"
					+ "?UWType?" + "' order by UWorder"; // 取得个单核保规则
			sqlbv.sql(Sql);
			sqlbv.put("UWType", mLPEdorItemSchema.getEdorType());
		} else if (mContType != null && mContType.equals("G")) {
			Sql = "select * from LMUW where riskcode = '000000' "
					+ " and RelaPolType='GIC'" + " and UWType='"
					+ "?UWType?" + "' order by UWorder"; // 取得团单核保规则
			sqlbv.sql(Sql);
			sqlbv.put("UWType", mLPEdorItemSchema.getEdorType());
		} else {
			CError tError = new CError();
			tError.moduleName = "EdorAutoUWBL";
			tError.functionName = "dealLCContUW";
			tError.errorMessage = "保全自动核保的（团单、个单）类别传输错误!";
			this.mErrors.addOneError(tError);
			return "0";
		}

		BqCalBase tBqCalBase = new BqCalBase();
		tBqCalBase.setOperator(mLPEdorItemSchema.getOperator());
		tBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
		tBqCalBase.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tBqCalBase.setEdorType(mLPEdorItemSchema.getEdorType());
		tBqCalBase.setGrpContNo(mLPEdorItemSchema.getGrpContNo());
		tBqCalBase.setAppntNo(aLCContSchema.getAppntNo());
		tBqCalBase.setEdorValiDate(mLPEdorItemSchema.getEdorValiDate());
		int tInterval = PubFun.calInterval(aLCContSchema.getCValiDate(),
				mLPEdorItemSchema.getEdorValiDate(), "Y"); // 计算时间间隔
		LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
		tLJSGetEndorseDB.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
		tLJSGetEndorseDB.setFeeOperationType(mLPEdorItemSchema.getEdorType());
		LJSGetEndorseSet tLJSGetEndorseSet = tLJSGetEndorseDB.query();
		if (tLJSGetEndorseDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLJSGetEndorseDB.mErrors);
			mErrors.addOneError(new CError("查询批改补退费表失败！"));
			return "0";
		}
		if (tLJSGetEndorseSet != null && tLJSGetEndorseSet.size() > 0) {
			tBqCalBase.setGetMoney(tLJSGetEndorseSet.get(1).getGetMoney()); // 从财务表中获取补退费信息
		}
		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		tLPInsuredDB.setEdorNo(mLPEdorMainSchema.getEdorNo());
		tLPInsuredDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsuredDB.setContNo(aLCContSchema.getContNo());
		tLPInsuredDB.setInsuredNo(aLCContSchema.getInsuredNo());
		LPInsuredSet tLPInsuredSet = tLPInsuredDB.query(); // 从P表中获取被保人信息
		if (tLPInsuredDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPInsuredDB.mErrors);
			mErrors.addOneError(new CError("查询保全被保人信息失败!"));
			return "0";
		}
		if (tLPInsuredSet == null || tLPInsuredSet.size() <= 0) { // 如果P表中无被保人信息
			LCInsuredDB tLCInsuredDB = new LCInsuredDB(); // 从C表中获取被保人信息
			tLCInsuredDB.setContNo(aLCContSchema.getContNo());
			tLCInsuredDB.setInsuredNo(aLCContSchema.getInsuredNo());
			LCInsuredSet tLCInsuredSet = tLCInsuredDB.query();
			if (tLCInsuredSet == null || tLCInsuredSet.size() <= 0) {
				mErrors.copyAllErrors(tLCInsuredDB.mErrors);
				mErrors.addOneError(new CError("查询被保人信息失败!"));
				return "0";
			}
			tBqCalBase.setInsuredNo(tLPInsuredDB.getInsuredNo());
			// tBqCalBase.setSex(tLCInsuredSet.get(1).getSex());
			// tBqCalBase.setJob(tLCInsuredSet.get(1).getOccupationType());
		} else {
			tBqCalBase.setInsuredNo(tLPInsuredDB.getInsuredNo());
			// tBqCalBase.setSex(tLPInsuredSet.get(1).getSex());
			// tBqCalBase.setJob(tLPInsuredSet.get(1).getOccupationType());
		}

		// ===ADD===zhangtao===2006-01-23=====添加计算要素（第二被保人客户号）=====BGN================================
		String sql="";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
		sql = " select * from LPInsuredRelated where polno = "
				+ " (select polno from lcpol where polno = mainpolno and contno = '"
				+ "?contno?" + "' and rownum = 1) "
				+ " and maincustomerno = '" + "?maincustomerno?"
				+ "' and edorno = '" + "?edorno?" + "' "
				+ " and edortype = '" + "?edortype?" + "' ";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
		sql = " select * from LPInsuredRelated where polno = "
				+ " (select polno from lcpol where polno = mainpolno and contno = '"
				+ "?contno?" + "' limit 0,1) "
				+ " and maincustomerno = '" + "?maincustomerno?"
				+ "' and edorno = '" + "?edorno?" + "' "
				+ " and edortype = '" + "?edortype?" + "' ";
		}
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(sql);
		sbv.put("contno", mLPEdorItemSchema.getContNo());
		sbv.put("maincustomerno", tBqCalBase.getInsuredNo());
		sbv.put("edorno", mLPEdorMainSchema.getEdorNo());
		sbv.put("edortype", mLPEdorItemSchema.getEdorType());
		LPInsuredRelatedDB tLPInsuredRelatedDB = new LPInsuredRelatedDB();
		LPInsuredRelatedSet tLPInsuredRelatedSet = tLPInsuredRelatedDB
				.executeQuery(sbv); // 从P表中获取第二被保人信息
		if (tLPInsuredRelatedDB.mErrors.needDealError()) {
			mErrors.addOneError(new CError("查询保全被保人信息失败!"));
			return "0";
		}
		if (tLPInsuredRelatedSet == null || tLPInsuredRelatedSet.size() <= 0) { // P表中无第二被保人信息
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			sql = " select * from LCInsuredRelated where polno = "
					+ " (select polno from lcpol where polno = mainpolno and contno = '"
					+ "?contno?" + "' and rownum = 1) "
					+ " and maincustomerno = '" + "?maincustomerno?"
					+ "' ";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			sql = " select * from LCInsuredRelated where polno = "
					+ " (select polno from lcpol where polno = mainpolno and contno = '"
					+ "?contno?" + "' limit 0,1) "
					+ " and maincustomerno = '" + "?maincustomerno?"
					+ "' ";
			}
			sbv=new SQLwithBindVariables();
			sbv.sql(sql);
			sbv.put("contno", mLPEdorItemSchema.getContNo());
			sbv.put("maincustomerno", tBqCalBase.getInsuredNo());
			LCInsuredRelatedDB tLCInsuredRelatedDB = new LCInsuredRelatedDB(); // 从C表中获取
			LCInsuredRelatedSet tLCInsuredRelatedSet = tLCInsuredRelatedDB
					.executeQuery(sbv);
			if (tLCInsuredRelatedSet == null
					|| tLCInsuredRelatedSet.size() <= 0) {
				tBqCalBase.setSecondInsuredNo("");
			} else {
				tBqCalBase.setSecondInsuredNo(tLCInsuredRelatedSet.get(1)
						.getCustomerNo());
			}
		} else {
			tBqCalBase.setSecondInsuredNo(tLPInsuredRelatedSet.get(1)
					.getCustomerNo());
		}
		// ===ADD===zhangtao===2006-01-23=====添加计算要素（第二被保人客户号）=====END================================
		logger.debug("tBqCalBase.getPolNo()" + tBqCalBase.getPolNo());
		logger.debug("tBqCalBase.getEdorNo()" + tBqCalBase.getEdorNo());
		logger.debug("tBqCalBase.getEdorType()"
				+ tBqCalBase.getEdorType());

		tLMUWSet = tLMUWDB.executeQuery(sqlbv);

		logger.debug("tLMUWSet size : " + tLMUWSet.size());
		for (int i = 1; i <= tLMUWSet.size(); i++) {
			LMUWSchema tLMUWSchema = tLMUWSet.get(i);

			tCalCode = tLMUWSchema.getCalCode(); // 得到险种编码

			if (!calUWEndorse(tBqCalBase, tCalCode)) { // 核保计算, 如果核保不通过
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

		aLCContSchema.setUWFlag(tUWState);
		aLCContSchema.setUWOperator(mGlobalInput.Operator);
		aLCContSchema.setUWDate(PubFun.getCurrentDate());
		aLCContSchema.setUWTime(PubFun.getCurrentTime());

		if (tUWGrade != null && tUWGrade.compareTo(mUWGrade) > 0) {
			mUWGrade = tUWGrade; // 保存该保全批单最大核保级别
		}
		LPCUWMasterSchema tLPCUWMasterSchema = prepareEdorCUWMaster(
				mLPEdorItemSchema, aLCContSchema, tUWState, tUWGrade); // 生成合同核保主表信息
		prepareEdorCUWSub(tLPCUWMasterSchema); // 生成合同核保子表信息
		prepareEdorCUWError(tLPCUWMasterSchema, tLMUWUnpassSet); // 生成合同核保子表信息
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
		mCalculator.addBasicFactor("InsuredNo", aBqCalBase.getInsuredNo());
		mCalculator.addBasicFactor("AppntNo", aBqCalBase.getAppntNo());
		mCalculator.addBasicFactor("SumDangerAmnt", aBqCalBase
				.getSumDangerAmnt());
		mCalculator.addBasicFactor("SumInvaliAmnt", aBqCalBase
				.getSumInvaliAmnt());
		mCalculator
				.addBasicFactor("EdorValiDate", aBqCalBase.getEdorValiDate());
		mCalculator.addBasicFactor("RiskCode", aBqCalBase.getRiskCode());
		if (!prepareBOMList(aBqCalBase)) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return false;
		}
		mCalculator.setBOMList(this.mBomList);// 添加BOMList
		String tStr = "";
		tStr = mCalculator.calculate();
		if (tStr == null || tStr.trim().equals("") || tStr.trim().equals("0")) {
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * 为全局变量mBomList赋值，如果已经赋值过，则不再赋值
	 * 
	 * @return
	 */
	private boolean prepareBOMList(BqCalBase aBqCalBase) {
		try {
			if (!this.mBomListFlag) {
				this.mPrepareBOMBQUWBL.setBqCalBase(aBqCalBase);
				this.mBomList = this.mPrepareBOMBQUWBL.dealData(mLPEdorItemSchema);
				this.mBomListFlag = true;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			this.mBomListFlag = false;
			return false;
		}
	}


	private boolean prepareOutputData() {
		mMap.put(mLPEdorMainSet, "UPDATE");
		mMap.put(mLPEdorItemSet, "UPDATE");
		mMap.put(mLCContSet, "UPDATE");
		mMap.put(mLPContSet, "UPDATE");
		mMap.put(mLCPolSet, "UPDATE");
		mMap.put(mLPPolSet, "UPDATE");
		mMap.put(mLPUWMasterSet, "DELETE&INSERT");
		mMap.put(mLPUWSubSet, "INSERT");
		//被保人核保主表
		mMap.put(mLPIndUWMasterSet, "DELETE&INSERT");
		mMap.put(mLPIndUWSubSet, "INSERT");
		mMap.put(mLPUWErrorSet, "INSERT");
		mMap.put(mLPCUWMasterSet, "DELETE&INSERT");
		mMap.put(mLPCUWSubSet, "DELETE&INSERT");
		mMap.put(mLPCUWErrorSet, "DELETE&INSERT");
		mMap.put(mLPUWMasterMainSet, "DELETE&INSERT");
		mMap.put(mLPUWSubMainSet, "INSERT");
		mMap.put(UPDate_LPContTempInfoSet, "UPDATE");
		
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

	// =======创建核保轨迹记录部分=================================BGN====================
	private LPUWMasterSchema prepareEdorUWMaster(
			LPEdorItemSchema aLPEdorItemSchema, LPPolSchema aLPPolSchema,
			String aUWState, String aUWGrade) { // 生成核保主表信息
		int tUWNo = 0;
		LPUWMasterSchema tLPUWMasterSchema = new LPUWMasterSchema();
		for (int j = 1; j <= mLPUWMasterSet.size(); j++) {
			if (aLPEdorItemSchema.getEdorNo().equals(
					mLPUWMasterSet.get(j).getEdorNo())
					&& aLPEdorItemSchema.getEdorType().equals(
							mLPUWMasterSet.get(j).getEdorType())
					&& aLPPolSchema.getPolNo().equals(
							mLPUWMasterSet.get(j).getPolNo())) {
				return null;
			}
		}

		LPUWMasterDB tLPUWMasterDB = new LPUWMasterDB();
		tLPUWMasterDB.setEdorNo(aLPEdorItemSchema.getEdorNo());
		tLPUWMasterDB.setEdorType(aLPEdorItemSchema.getEdorType());
		tLPUWMasterDB.setPolNo(aLPPolSchema.getPolNo());
		LPUWMasterSet tLPUWMasterSet = tLPUWMasterDB.query();
		if (tLPUWMasterSet == null || tLPUWMasterSet.size() <= 0) {
			tUWNo = 1;
			tLPUWMasterSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPUWMasterSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			tLPUWMasterSchema.setGrpContNo(aLPPolSchema.getGrpContNo());
			tLPUWMasterSchema.setContNo(aLPPolSchema.getContNo());
			tLPUWMasterSchema.setProposalContNo(mLCContSchema
					.getProposalContNo());
			tLPUWMasterSchema.setPolNo(aLPPolSchema.getPolNo());
			tLPUWMasterSchema.setProposalNo(aLPPolSchema.getProposalNo());
			tLPUWMasterSchema.setAppntName(aLPPolSchema.getAppntName());
			tLPUWMasterSchema.setAppntNo(aLPPolSchema.getAppntNo());
			tLPUWMasterSchema.setInsuredName(aLPPolSchema.getInsuredName());
			tLPUWMasterSchema.setInsuredNo(aLPPolSchema.getInsuredNo());
			tLPUWMasterSchema.setAgentCode(aLPPolSchema.getAgentCode());
			tLPUWMasterSchema.setAgentGroup(aLPPolSchema.getAgentGroup());
			tLPUWMasterSchema.setAutoUWFlag("1");
			tLPUWMasterSchema.setPassFlag(aUWState);
			tLPUWMasterSchema.setUWGrade(aUWGrade);
			tLPUWMasterSchema.setAppGrade(aUWGrade);
			tLPUWMasterSchema.setPostponeDay("");
			tLPUWMasterSchema.setPostponeDate("");
			tLPUWMasterSchema.setState(aUWState);
			tLPUWMasterSchema.setUWIdea("");
			tLPUWMasterSchema.setUpReportContent("");
			tLPUWMasterSchema.setHealthFlag("0");
			tLPUWMasterSchema.setQuesFlag("0");
			tLPUWMasterSchema.setSpecFlag("0");
			tLPUWMasterSchema.setAddPremFlag("0");
			tLPUWMasterSchema.setAddPremReason("");
			tLPUWMasterSchema.setReportFlag("0");
			tLPUWMasterSchema.setPrintFlag("0");
			tLPUWMasterSchema.setPrintFlag2("0");
			tLPUWMasterSchema.setChangePolFlag("0");
			tLPUWMasterSchema.setChangePolReason("");
			tLPUWMasterSchema.setSpecReason("");
			tLPUWMasterSchema.setManageCom(mLPEdorMainSchema.getManageCom());
			tLPUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLPUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
		} else {
			tUWNo = tLPUWMasterSet.get(1).getUWNo() + 1;
			tLPUWMasterSchema = tLPUWMasterSet.get(1);
			tLPUWMasterSchema.setAutoUWFlag("1");
			tLPUWMasterSchema.setPassFlag(aUWState);
			tLPUWMasterSchema.setUWGrade(aUWGrade);
			tLPUWMasterSchema.setAppGrade(aUWGrade);
		}
		tLPUWMasterSchema.setUWNo(tUWNo);
		tLPUWMasterSchema.setOperator(mGlobalInput.Operator);
		tLPUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLPUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		mLPUWMasterSet.add(tLPUWMasterSchema);
		return tLPUWMasterSchema;
	}

	private LPUWMasterSchema prepareEdorUWMaster(
			LPEdorItemSchema aLPEdorItemSchema, LCPolSchema aLCPolSchema,
			String aUWState, String aUWGrade) { // 生成核保主信息
		int tUWNo = 0;
		LPUWMasterSchema tLPUWMasterSchema = new LPUWMasterSchema();
		for (int j = 1; j <= mLPUWMasterSet.size(); j++) {
			if (aLPEdorItemSchema.getEdorNo().equals(
					mLPUWMasterSet.get(j).getEdorNo())
					&& aLPEdorItemSchema.getEdorType().equals(
							mLPUWMasterSet.get(j).getEdorType())
					&& aLCPolSchema.getPolNo().equals(
							mLPUWMasterSet.get(j).getPolNo())) {
				return null;
			}
		}
		LPUWMasterDB tLPUWMasterDB = new LPUWMasterDB();
		tLPUWMasterDB.setEdorNo(aLPEdorItemSchema.getEdorNo());
		tLPUWMasterDB.setEdorType(aLPEdorItemSchema.getEdorType());
		tLPUWMasterDB.setPolNo(aLCPolSchema.getPolNo());
		LPUWMasterSet tLPUWMasterSet = tLPUWMasterDB.query();
		if (tLPUWMasterSet == null || tLPUWMasterSet.size() <= 0) {
			tUWNo = 1;
			tLPUWMasterSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPUWMasterSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			tLPUWMasterSchema.setGrpContNo(aLCPolSchema.getGrpContNo());
			tLPUWMasterSchema.setContNo(aLCPolSchema.getContNo());
			tLPUWMasterSchema.setProposalContNo(mLCContSchema
					.getProposalContNo());
			tLPUWMasterSchema.setPolNo(aLCPolSchema.getPolNo());
			tLPUWMasterSchema.setProposalNo(aLCPolSchema.getProposalNo());
			tLPUWMasterSchema.setAppntName(aLCPolSchema.getAppntName());
			tLPUWMasterSchema.setAppntNo(aLCPolSchema.getAppntNo());
			tLPUWMasterSchema.setInsuredName(aLCPolSchema.getInsuredName());
			tLPUWMasterSchema.setInsuredNo(aLCPolSchema.getInsuredNo());
			tLPUWMasterSchema.setAgentCode(aLCPolSchema.getAgentCode());
			tLPUWMasterSchema.setAgentGroup(aLCPolSchema.getAgentGroup());
			tLPUWMasterSchema.setAutoUWFlag("1");
			tLPUWMasterSchema.setPassFlag(aUWState);
			tLPUWMasterSchema.setUWGrade(aUWGrade);
			tLPUWMasterSchema.setAppGrade(aUWGrade);
			tLPUWMasterSchema.setPostponeDay("");
			tLPUWMasterSchema.setPostponeDate("");
			tLPUWMasterSchema.setState(aUWState);
			tLPUWMasterSchema.setUWIdea("");
			tLPUWMasterSchema.setUpReportContent("");
			tLPUWMasterSchema.setHealthFlag("0");
			tLPUWMasterSchema.setQuesFlag("0");
			tLPUWMasterSchema.setSpecFlag("0");
			tLPUWMasterSchema.setAddPremFlag("0");
			tLPUWMasterSchema.setAddPremReason("");
			tLPUWMasterSchema.setReportFlag("0");
			tLPUWMasterSchema.setPrintFlag("0");
			tLPUWMasterSchema.setPrintFlag2("0");
			tLPUWMasterSchema.setChangePolFlag("0");
			tLPUWMasterSchema.setChangePolReason("");
			tLPUWMasterSchema.setSpecReason("");
			tLPUWMasterSchema.setManageCom(mLPEdorMainSchema.getManageCom());
			tLPUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLPUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
		} else {
			tUWNo = tLPUWMasterSet.get(1).getUWNo() + 1;
			tLPUWMasterSchema = tLPUWMasterSet.get(1);
			tLPUWMasterSchema.setAutoUWFlag("1");
			tLPUWMasterSchema.setPassFlag(aUWState);
			tLPUWMasterSchema.setUWGrade(aUWGrade);
			tLPUWMasterSchema.setAppGrade(aUWGrade);
		}
		tLPUWMasterSchema.setUWNo(tUWNo);
		tLPUWMasterSchema.setOperator(mGlobalInput.Operator);
		tLPUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLPUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		mLPUWMasterSet.add(tLPUWMasterSchema);
		return tLPUWMasterSchema;
	}

	private LPUWSubSchema prepareEdorUWSub(LPUWMasterSchema aLPUWMasterSchema) { // 生成核保子表
		LPUWSubSchema tLPUWSubSchema = new LPUWSubSchema();

		tLPUWSubSchema.setEdorNo(aLPUWMasterSchema.getEdorNo());
		tLPUWSubSchema.setEdorType(aLPUWMasterSchema.getEdorType());
		tLPUWSubSchema.setGrpContNo(aLPUWMasterSchema.getGrpContNo());
		tLPUWSubSchema.setContNo(aLPUWMasterSchema.getContNo());
		tLPUWSubSchema.setProposalContNo(aLPUWMasterSchema.getProposalContNo());
		tLPUWSubSchema.setPolNo(aLPUWMasterSchema.getPolNo());
		tLPUWSubSchema.setProposalNo(aLPUWMasterSchema.getProposalNo());
		tLPUWSubSchema.setUWNo(aLPUWMasterSchema.getUWNo());
		tLPUWSubSchema.setAppntName(aLPUWMasterSchema.getAppntName());
		tLPUWSubSchema.setAppntNo(aLPUWMasterSchema.getAppntNo());
		tLPUWSubSchema.setInsuredName(aLPUWMasterSchema.getInsuredName());
		tLPUWSubSchema.setInsuredNo(aLPUWMasterSchema.getInsuredNo());
		tLPUWSubSchema.setAgentCode(aLPUWMasterSchema.getAgentCode());
		tLPUWSubSchema.setAgentGroup(aLPUWMasterSchema.getAgentGroup());
		tLPUWSubSchema.setAutoUWFlag(aLPUWMasterSchema.getAutoUWFlag());
		tLPUWSubSchema.setPassFlag(aLPUWMasterSchema.getPassFlag());
		tLPUWSubSchema.setUWGrade(aLPUWMasterSchema.getUWGrade());
		tLPUWSubSchema.setAppGrade(aLPUWMasterSchema.getUWGrade());
		tLPUWSubSchema.setPostponeDay(aLPUWMasterSchema.getPostponeDay());
		tLPUWSubSchema.setPostponeDate(aLPUWMasterSchema.getPostponeDate());
		tLPUWSubSchema.setState(aLPUWMasterSchema.getState());
		tLPUWSubSchema.setUWIdea(aLPUWMasterSchema.getUWIdea());
		tLPUWSubSchema.setUpReportContent(aLPUWMasterSchema
				.getUpReportContent());
		tLPUWSubSchema.setHealthFlag(aLPUWMasterSchema.getHealthFlag());
		tLPUWSubSchema.setQuesFlag(aLPUWMasterSchema.getQuesFlag());
		tLPUWSubSchema.setSpecFlag(aLPUWMasterSchema.getSpecFlag());
		tLPUWSubSchema.setAddPremFlag(aLPUWMasterSchema.getAddPremFlag());
		tLPUWSubSchema.setAddPremReason(aLPUWMasterSchema.getAddPremReason());
		tLPUWSubSchema.setReportFlag(aLPUWMasterSchema.getReportFlag());
		tLPUWSubSchema.setPrintFlag(aLPUWMasterSchema.getPrintFlag());
		tLPUWSubSchema.setPrintFlag2(aLPUWMasterSchema.getPrintFlag2());
		tLPUWSubSchema.setChangePolFlag(aLPUWMasterSchema.getChangePolFlag());
		tLPUWSubSchema.setChangePolReason(aLPUWMasterSchema
				.getChangePolReason());
		tLPUWSubSchema.setSpecReason(aLPUWMasterSchema.getSpecFlag());
		tLPUWSubSchema.setManageCom(aLPUWMasterSchema.getManageCom());
		tLPUWSubSchema.setOperator(mGlobalInput.Operator);
		tLPUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		tLPUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		tLPUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLPUWSubSchema.setMakeTime(PubFun.getCurrentTime());

		mLPUWSubSet.add(tLPUWSubSchema);
		return tLPUWSubSchema;
	}

	private LPUWErrorSet prepareEdorUWError(LPUWMasterSchema aLPUWMasterSchema,
			LMUWSet aLMUWUnpassSet) { // 生成核保错误信息
		LPUWErrorSchema tLPUWErrorSchemaTemp = new LPUWErrorSchema();

		tLPUWErrorSchemaTemp.setEdorNo(aLPUWMasterSchema.getEdorNo());
		tLPUWErrorSchemaTemp.setEdorType(aLPUWMasterSchema.getEdorType());
		tLPUWErrorSchemaTemp.setGrpContNo(aLPUWMasterSchema.getGrpContNo());
		tLPUWErrorSchemaTemp.setContNo(aLPUWMasterSchema.getContNo());
		tLPUWErrorSchemaTemp.setProposalContNo(aLPUWMasterSchema
				.getProposalContNo());
		tLPUWErrorSchemaTemp.setPolNo(aLPUWMasterSchema.getPolNo());
		tLPUWErrorSchemaTemp.setProposalNo(aLPUWMasterSchema.getProposalNo());
		tLPUWErrorSchemaTemp.setUWNo(aLPUWMasterSchema.getUWNo());
		tLPUWErrorSchemaTemp.setAppntName(aLPUWMasterSchema.getAppntName());
		tLPUWErrorSchemaTemp.setAppntNo(aLPUWMasterSchema.getAppntNo());
		tLPUWErrorSchemaTemp.setInsuredName(aLPUWMasterSchema.getInsuredName());
		tLPUWErrorSchemaTemp.setInsuredNo(aLPUWMasterSchema.getInsuredNo());
		tLPUWErrorSchemaTemp.setUWPassFlag(aLPUWMasterSchema.getPassFlag());
		tLPUWErrorSchemaTemp.setUWGrade(aLPUWMasterSchema.getUWGrade());
		tLPUWErrorSchemaTemp.setManageCom(mLPEdorMainSchema.getManageCom());
		tLPUWErrorSchemaTemp.setModifyDate(PubFun.getCurrentDate());
		tLPUWErrorSchemaTemp.setModifyTime(PubFun.getCurrentTime());

		int n = aLMUWUnpassSet.size();
		LMUWSchema tLMUWSchema = null;
		for (int i = 1; i <= n; i++) {
			LPUWErrorSchema tLPUWErrorSchema = new LPUWErrorSchema();
			tLPUWErrorSchema.setSchema(tLPUWErrorSchemaTemp);
			tLMUWSchema = aLMUWUnpassSet.get(i);
			tLPUWErrorSchema.setSerialNo(Integer.toString(i));
			tLPUWErrorSchema.setUWError(tLMUWSchema.getRemark());
			tLPUWErrorSchema.setUWRuleCode(tLMUWSchema.getCalCode());
			mLPUWErrorSet.add(tLPUWErrorSchema);
		}

		return mLPUWErrorSet;
	}

	private LPCUWMasterSchema prepareEdorCUWMaster(
			LPEdorItemSchema aLPEdorItemSchema, LPContSchema aLPContSchema,
			String aUWState, String aUWGrade) { // 生成合同核保主表信息
		int tUWNo = 0;
		LPCUWMasterSchema tLPCUWMasterSchema = new LPCUWMasterSchema();

		LPCUWMasterDB tLPCUWMasterDB = new LPCUWMasterDB();
		tLPCUWMasterDB.setEdorNo(aLPEdorItemSchema.getEdorNo());
		tLPCUWMasterDB.setEdorType(aLPEdorItemSchema.getEdorType());
		tLPCUWMasterDB.setContNo(aLPContSchema.getContNo());
		LPCUWMasterSet tLPCUWMasterSet = tLPCUWMasterDB.query();
		if (tLPCUWMasterSet == null || tLPCUWMasterSet.size() <= 0) {
			tUWNo = 1;

			tLPCUWMasterSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPCUWMasterSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			tLPCUWMasterSchema.setGrpContNo(aLPContSchema.getGrpContNo());
			tLPCUWMasterSchema.setContNo(aLPContSchema.getContNo());
			tLPCUWMasterSchema.setProposalContNo(mLCContSchema
					.getProposalContNo());
			tLPCUWMasterSchema.setAppntName(aLPContSchema.getAppntName());
			tLPCUWMasterSchema.setAppntNo(aLPContSchema.getAppntNo());
			tLPCUWMasterSchema.setInsuredName(aLPContSchema.getInsuredName());
			tLPCUWMasterSchema.setInsuredNo(aLPContSchema.getInsuredNo());
			tLPCUWMasterSchema.setAgentCode(aLPContSchema.getAgentCode());
			tLPCUWMasterSchema.setAgentGroup(aLPContSchema.getAgentGroup());
			tLPCUWMasterSchema.setAutoUWFlag("1");
			tLPCUWMasterSchema.setPassFlag(aUWState);
			tLPCUWMasterSchema.setUWGrade(aUWGrade);
			tLPCUWMasterSchema.setAppGrade(aUWGrade);
			tLPCUWMasterSchema.setPostponeDay("");
			tLPCUWMasterSchema.setPostponeDate("");
			tLPCUWMasterSchema.setState(aUWState);
			tLPCUWMasterSchema.setUWIdea("");
			tLPCUWMasterSchema.setUpReportContent("");
			tLPCUWMasterSchema.setHealthFlag("0");
			tLPCUWMasterSchema.setQuesFlag("0");
			tLPCUWMasterSchema.setSpecFlag("0");
			tLPCUWMasterSchema.setAddPremFlag("0");
			tLPCUWMasterSchema.setAddPremReason("");
			tLPCUWMasterSchema.setReportFlag("0");
			tLPCUWMasterSchema.setPrintFlag("0");
			tLPCUWMasterSchema.setPrintFlag2("0");
			tLPCUWMasterSchema.setChangePolFlag("0");
			tLPCUWMasterSchema.setChangePolReason("");
			tLPCUWMasterSchema.setSpecReason("");
			tLPCUWMasterSchema.setManageCom(mLPEdorMainSchema.getManageCom());
			tLPCUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLPCUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
		} else {
			tUWNo = tLPCUWMasterSet.get(1).getUWNo() + 1;
			tLPCUWMasterSchema = tLPCUWMasterSet.get(1);
			tLPCUWMasterSchema.setAutoUWFlag("1");
			tLPCUWMasterSchema.setPassFlag(aUWState);
			tLPCUWMasterSchema.setUWGrade(aUWGrade);
			tLPCUWMasterSchema.setAppGrade(aUWGrade);
		}
		tLPCUWMasterSchema.setUWNo(tUWNo);
		tLPCUWMasterSchema.setOperator(mGlobalInput.Operator);
		tLPCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLPCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		mLPCUWMasterSet.add(tLPCUWMasterSchema);
		return tLPCUWMasterSchema;
	}
	
	private LPCUWMasterSchema prepareEdorCUWMaster(
			LPEdorItemSchema aLPEdorItemSchema, LCContSchema aLCContSchema,
			String aUWState, String aUWGrade) { // 生成合同核保主信息
		int tUWNo = 0;
		LPCUWMasterSchema tLPCUWMasterSchema = new LPCUWMasterSchema();

		LPCUWMasterDB tLPCUWMasterDB = new LPCUWMasterDB();
		tLPCUWMasterDB.setEdorNo(aLPEdorItemSchema.getEdorNo());
		tLPCUWMasterDB.setEdorType(aLPEdorItemSchema.getEdorType());
		tLPCUWMasterDB.setContNo(aLCContSchema.getContNo());
		LPCUWMasterSet tLPCUWMasterSet = tLPCUWMasterDB.query();
		if (tLPCUWMasterSet == null || tLPCUWMasterSet.size() <= 0) {
			tUWNo = 1;
			tLPCUWMasterSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPCUWMasterSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			tLPCUWMasterSchema.setGrpContNo(aLCContSchema.getGrpContNo());
			tLPCUWMasterSchema.setContNo(aLCContSchema.getContNo());
			tLPCUWMasterSchema.setProposalContNo(mLCContSchema
					.getProposalContNo());
			tLPCUWMasterSchema.setAppntName(aLCContSchema.getAppntName());
			tLPCUWMasterSchema.setAppntNo(aLCContSchema.getAppntNo());
			tLPCUWMasterSchema.setInsuredName(aLCContSchema.getInsuredName());
			tLPCUWMasterSchema.setInsuredNo(aLCContSchema.getInsuredNo());
			tLPCUWMasterSchema.setAgentCode(aLCContSchema.getAgentCode());
			tLPCUWMasterSchema.setAgentGroup(aLCContSchema.getAgentGroup());
			tLPCUWMasterSchema.setAutoUWFlag("1");
			tLPCUWMasterSchema.setPassFlag(aUWState);
			tLPCUWMasterSchema.setUWGrade(aUWGrade);
			tLPCUWMasterSchema.setAppGrade(aUWGrade);
			tLPCUWMasterSchema.setPostponeDay("");
			tLPCUWMasterSchema.setPostponeDate("");
			tLPCUWMasterSchema.setState(aUWState);
			tLPCUWMasterSchema.setUWIdea("");
			tLPCUWMasterSchema.setUpReportContent("");
			tLPCUWMasterSchema.setHealthFlag("0");
			tLPCUWMasterSchema.setQuesFlag("0");
			tLPCUWMasterSchema.setSpecFlag("0");
			tLPCUWMasterSchema.setAddPremFlag("0");
			tLPCUWMasterSchema.setAddPremReason("");
			tLPCUWMasterSchema.setReportFlag("0");
			tLPCUWMasterSchema.setPrintFlag("0");
			tLPCUWMasterSchema.setPrintFlag2("0");
			tLPCUWMasterSchema.setChangePolFlag("0");
			tLPCUWMasterSchema.setChangePolReason("");
			tLPCUWMasterSchema.setSpecReason("");
			tLPCUWMasterSchema.setManageCom(mLPEdorMainSchema.getManageCom());
			tLPCUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLPCUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
		} else {
			tUWNo = tLPCUWMasterSet.get(1).getUWNo() + 1;
			tLPCUWMasterSchema = tLPCUWMasterSet.get(1);
			tLPCUWMasterSchema.setAutoUWFlag("1");
			tLPCUWMasterSchema.setPassFlag(aUWState);
			tLPCUWMasterSchema.setUWGrade(aUWGrade);
			tLPCUWMasterSchema.setAppGrade(aUWGrade);
		}
		tLPCUWMasterSchema.setUWNo(tUWNo);

		tLPCUWMasterSchema.setOperator(mGlobalInput.Operator);
		tLPCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLPCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		mLPCUWMasterSet.add(tLPCUWMasterSchema);
		return tLPCUWMasterSchema;
	}

	private LPCUWSubSchema prepareEdorCUWSub(
			LPCUWMasterSchema aLPCUWMasterSchema) { // 生成合同核保子表
		LPCUWSubSchema tLPCUWSubSchema = new LPCUWSubSchema();

		tLPCUWSubSchema.setEdorNo(aLPCUWMasterSchema.getEdorNo());
		tLPCUWSubSchema.setEdorType(aLPCUWMasterSchema.getEdorType());
		tLPCUWSubSchema.setGrpContNo(aLPCUWMasterSchema.getGrpContNo());
		tLPCUWSubSchema.setContNo(aLPCUWMasterSchema.getContNo());
		tLPCUWSubSchema.setProposalContNo(aLPCUWMasterSchema
				.getProposalContNo());
		tLPCUWSubSchema.setUWNo(aLPCUWMasterSchema.getUWNo());
		tLPCUWSubSchema.setAppntName(aLPCUWMasterSchema.getAppntName());
		tLPCUWSubSchema.setAppntNo(aLPCUWMasterSchema.getAppntNo());
		tLPCUWSubSchema.setInsuredName(aLPCUWMasterSchema.getInsuredName());
		tLPCUWSubSchema.setInsuredNo(aLPCUWMasterSchema.getInsuredNo());
		tLPCUWSubSchema.setAgentCode(aLPCUWMasterSchema.getAgentCode());
		tLPCUWSubSchema.setAgentGroup(aLPCUWMasterSchema.getAgentGroup());
		tLPCUWSubSchema.setAutoUWFlag(aLPCUWMasterSchema.getAutoUWFlag());
		tLPCUWSubSchema.setPassFlag(aLPCUWMasterSchema.getPassFlag());
		tLPCUWSubSchema.setUWGrade(aLPCUWMasterSchema.getUWGrade());
		tLPCUWSubSchema.setAppGrade(aLPCUWMasterSchema.getUWGrade());
		tLPCUWSubSchema.setPostponeDay(aLPCUWMasterSchema.getPostponeDay());
		tLPCUWSubSchema.setPostponeDate(aLPCUWMasterSchema.getPostponeDate());
		tLPCUWSubSchema.setState(aLPCUWMasterSchema.getState());
		tLPCUWSubSchema.setUWIdea(aLPCUWMasterSchema.getUWIdea());
		tLPCUWSubSchema.setUpReportContent(aLPCUWMasterSchema
				.getUpReportContent());
		tLPCUWSubSchema.setHealthFlag(aLPCUWMasterSchema.getHealthFlag());
		tLPCUWSubSchema.setQuesFlag(aLPCUWMasterSchema.getQuesFlag());
		tLPCUWSubSchema.setSpecFlag(aLPCUWMasterSchema.getSpecFlag());
		tLPCUWSubSchema.setAddPremFlag(aLPCUWMasterSchema.getAddPremFlag());
		tLPCUWSubSchema.setAddPremReason(aLPCUWMasterSchema.getAddPremReason());
		tLPCUWSubSchema.setReportFlag(aLPCUWMasterSchema.getReportFlag());
		tLPCUWSubSchema.setPrintFlag(aLPCUWMasterSchema.getPrintFlag());
		tLPCUWSubSchema.setPrintFlag2(aLPCUWMasterSchema.getPrintFlag2());
		tLPCUWSubSchema.setChangePolFlag(aLPCUWMasterSchema.getChangePolFlag());
		tLPCUWSubSchema.setChangePolReason(aLPCUWMasterSchema
				.getChangePolReason());
		tLPCUWSubSchema.setSpecReason(aLPCUWMasterSchema.getSpecFlag());
		tLPCUWSubSchema.setManageCom(aLPCUWMasterSchema.getManageCom());
		tLPCUWSubSchema.setOperator(mGlobalInput.Operator);
		tLPCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		tLPCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		tLPCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLPCUWSubSchema.setMakeTime(PubFun.getCurrentTime());

		mLPCUWSubSet.add(tLPCUWSubSchema);
		return tLPCUWSubSchema;
	}

	private LPCUWErrorSet prepareEdorCUWError(
			LPCUWMasterSchema aLPCUWMasterSchema, LMUWSet aLMUWUnpassSet) { // 生成合同核保错误信息
		LPCUWErrorSchema tLPCUWErrorSchemaTemp = new LPCUWErrorSchema();

		tLPCUWErrorSchemaTemp.setEdorNo(aLPCUWMasterSchema.getEdorNo());
		tLPCUWErrorSchemaTemp.setEdorType(aLPCUWMasterSchema.getEdorType());
		tLPCUWErrorSchemaTemp.setGrpContNo(aLPCUWMasterSchema.getGrpContNo());
		tLPCUWErrorSchemaTemp.setContNo(aLPCUWMasterSchema.getContNo());
		tLPCUWErrorSchemaTemp.setProposalContNo(aLPCUWMasterSchema
				.getProposalContNo());
		tLPCUWErrorSchemaTemp.setUWNo(aLPCUWMasterSchema.getUWNo());
		tLPCUWErrorSchemaTemp.setAppntName(aLPCUWMasterSchema.getAppntName());
		tLPCUWErrorSchemaTemp.setAppntNo(aLPCUWMasterSchema.getAppntNo());
		tLPCUWErrorSchemaTemp.setInsuredName(aLPCUWMasterSchema
				.getInsuredName());
		tLPCUWErrorSchemaTemp.setInsuredNo(aLPCUWMasterSchema.getInsuredNo());
		tLPCUWErrorSchemaTemp.setUWPassFlag(aLPCUWMasterSchema.getPassFlag());
		tLPCUWErrorSchemaTemp.setUWGrade(aLPCUWMasterSchema.getUWGrade());
		tLPCUWErrorSchemaTemp.setManageCom(mLPEdorMainSchema.getManageCom());
		tLPCUWErrorSchemaTemp.setModifyDate(PubFun.getCurrentDate());
		tLPCUWErrorSchemaTemp.setModifyTime(PubFun.getCurrentTime());

		int n = aLMUWUnpassSet.size();
		LMUWSchema tLMUWSchema = null;
		for (int i = 1; i <= n; i++) {
			LPCUWErrorSchema tLPCUWErrorSchema = new LPCUWErrorSchema();
			tLPCUWErrorSchema.setSchema(tLPCUWErrorSchemaTemp);
			tLMUWSchema = aLMUWUnpassSet.get(i);
			tLPCUWErrorSchema.setSerialNo(Integer.toString(i));
			tLPCUWErrorSchema.setUWError(tLMUWSchema.getRemark());
			tLPCUWErrorSchema.setUWRuleCode(tLMUWSchema.getCalCode());
			mLPCUWErrorSet.add(tLPCUWErrorSchema);
		}

		return mLPCUWErrorSet;
	}

	private LPUWMasterMainSchema prepareEdorUWMasterMain(
			LPEdorMainSchema aLPEdorMainSchema, LCContSchema aLCContSchema,
			String aUWState, String aUWGrade) { // 生成批单核保主信息
		int tUWNo = 0;
		LPUWMasterMainSchema tLPUWMasterMainSchema = new LPUWMasterMainSchema();

		LPUWMasterMainDB tLPUWMasterMainDB = new LPUWMasterMainDB();
		tLPUWMasterMainDB.setEdorNo(aLPEdorMainSchema.getEdorNo());
		tLPUWMasterMainDB.setContNo(aLCContSchema.getContNo());
		LPUWMasterMainSet tLPUWMasterMainSet = tLPUWMasterMainDB.query();
		if (tLPUWMasterMainDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPUWMasterMainDB.mErrors);
			mErrors.addOneError(new CError("获取保全批单核保主表信息失败！"));
			return null;
		}
		if (tLPUWMasterMainSet == null || tLPUWMasterMainSet.size() <= 0) {
			tUWNo = 1;
			tLPUWMasterMainSchema.setEdorNo(aLPEdorMainSchema.getEdorNo());
			tLPUWMasterMainSchema.setGrpContNo(aLCContSchema.getGrpContNo());
			tLPUWMasterMainSchema.setContNo(aLCContSchema.getContNo());
			tLPUWMasterMainSchema.setProposalContNo(aLCContSchema
					.getProposalContNo());
			tLPUWMasterMainSchema.setAppntName(aLCContSchema.getAppntName());
			tLPUWMasterMainSchema.setAppntNo(aLCContSchema.getAppntNo());
			tLPUWMasterMainSchema
					.setInsuredName(aLCContSchema.getInsuredName());
			tLPUWMasterMainSchema.setInsuredNo(aLCContSchema.getInsuredNo());
			tLPUWMasterMainSchema.setAgentCode(aLCContSchema.getAgentCode());
			tLPUWMasterMainSchema.setAgentGroup(aLCContSchema.getAgentGroup());
			tLPUWMasterMainSchema.setAutoUWFlag("1");
			tLPUWMasterMainSchema.setPassFlag(aUWState);
			tLPUWMasterMainSchema.setUWGrade(aUWGrade);
			tLPUWMasterMainSchema.setAppGrade(aUWGrade);
			tLPUWMasterMainSchema.setPostponeDay("");
			tLPUWMasterMainSchema.setPostponeDate("");
			tLPUWMasterMainSchema.setState(aUWState);
			tLPUWMasterMainSchema.setUWIdea("");
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
			tLPUWMasterMainSchema = tLPUWMasterMainSet.get(1);
			tLPUWMasterMainSchema.setAutoUWFlag("1");
			tLPUWMasterMainSchema.setPassFlag(aUWState);
			tLPUWMasterMainSchema.setUWGrade(aUWGrade);
			tLPUWMasterMainSchema.setAppGrade(aUWGrade);
		}
		tLPUWMasterMainSchema.setUWNo(tUWNo);

		tLPUWMasterMainSchema.setOperator(mGlobalInput.Operator);
		tLPUWMasterMainSchema.setModifyDate(PubFun.getCurrentDate());
		tLPUWMasterMainSchema.setModifyTime(PubFun.getCurrentTime());

		mLPUWMasterMainSet.add(tLPUWMasterMainSchema);
		return tLPUWMasterMainSchema;
	}

	private LPUWSubMainSchema prepareEdorUWSubMain(
			LPUWMasterMainSchema aLPUWMasterMainSchema) { // 生成合同核保子表
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
	// =======创建核保轨迹记录部分=================================BGN====================

	private LPIndUWMasterSchema prepareEdorIndUWMaster(
			LPEdorItemSchema aLPEdorItemSchema, LPContSchema aLPContSchema,
			String aUWState, String aUWGrade) { // 生成合同核保主表信息
		int tUWNo = 0;
		LPIndUWMasterSchema tLPIndUWMasterSchema = new LPIndUWMasterSchema();

		LPIndUWMasterDB tLPIndUWMasterDB = new LPIndUWMasterDB();
		tLPIndUWMasterDB.setEdorNo(aLPEdorItemSchema.getEdorNo());
		tLPIndUWMasterDB.setEdorType(aLPEdorItemSchema.getEdorType());
		tLPIndUWMasterDB.setContNo(aLPContSchema.getContNo());
		LPIndUWMasterSet tLPIndUWMasterSet = tLPIndUWMasterDB.query();
		if (tLPIndUWMasterSet == null || tLPIndUWMasterSet.size() <= 0) {
			tUWNo = 1;

			tLPIndUWMasterSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPIndUWMasterSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			tLPIndUWMasterSchema.setGrpContNo(aLPContSchema.getGrpContNo());
			tLPIndUWMasterSchema.setContNo(aLPContSchema.getContNo());
			tLPIndUWMasterSchema.setProposalContNo(mLCContSchema
					.getProposalContNo());
			tLPIndUWMasterSchema.setAppntName(aLPContSchema.getAppntName());
			tLPIndUWMasterSchema.setAppntNo(aLPContSchema.getAppntNo());
			tLPIndUWMasterSchema.setInsuredName(aLPContSchema.getInsuredName());
			tLPIndUWMasterSchema.setInsuredNo(aLPContSchema.getInsuredNo());
			tLPIndUWMasterSchema.setAgentCode(aLPContSchema.getAgentCode());
			tLPIndUWMasterSchema.setAgentGroup(aLPContSchema.getAgentGroup());
			tLPIndUWMasterSchema.setAutoUWFlag("1");
			tLPIndUWMasterSchema.setPassFlag(aUWState);
			tLPIndUWMasterSchema.setUWGrade(aUWGrade);
			tLPIndUWMasterSchema.setAppGrade(aUWGrade);
			tLPIndUWMasterSchema.setPostponeDay("");
			tLPIndUWMasterSchema.setPostponeDate("");
			tLPIndUWMasterSchema.setState(aUWState);
			tLPIndUWMasterSchema.setUWIdea("");
			tLPIndUWMasterSchema.setUpReportContent("");
			tLPIndUWMasterSchema.setHealthFlag("0");
			tLPIndUWMasterSchema.setQuesFlag("0");
			tLPIndUWMasterSchema.setSpecFlag("0");
			tLPIndUWMasterSchema.setAddPremFlag("0");
			tLPIndUWMasterSchema.setAddPremReason("");
			tLPIndUWMasterSchema.setReportFlag("0");
			tLPIndUWMasterSchema.setPrintFlag("0");
			tLPIndUWMasterSchema.setPrintFlag2("0");
			tLPIndUWMasterSchema.setChangePolFlag("0");
			tLPIndUWMasterSchema.setChangePolReason("");
			tLPIndUWMasterSchema.setSpecReason("");
			tLPIndUWMasterSchema.setManageCom(mLPEdorMainSchema.getManageCom());
			tLPIndUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLPIndUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
		} else {
			tUWNo = tLPIndUWMasterSet.get(1).getUWNo() + 1;
			tLPIndUWMasterSchema = tLPIndUWMasterSet.get(1);
			tLPIndUWMasterSchema.setAutoUWFlag("1");
			tLPIndUWMasterSchema.setPassFlag(aUWState);
			tLPIndUWMasterSchema.setUWGrade(aUWGrade);
			tLPIndUWMasterSchema.setAppGrade(aUWGrade);
		}
		tLPIndUWMasterSchema.setUWNo(tUWNo);
		tLPIndUWMasterSchema.setOperator(mGlobalInput.Operator);
		tLPIndUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		tLPIndUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		mLPIndUWMasterSet.add(tLPIndUWMasterSchema);
		return tLPIndUWMasterSchema;
	}
	private LPIndUWSubSchema prepareEdorIndUWSub(
			LPIndUWMasterSchema aLPIndUWMasterSchema) { // 生成合同核保子表
		LPIndUWSubSchema tLPIndUWSubSchema = new LPIndUWSubSchema();

		tLPIndUWSubSchema.setEdorNo(aLPIndUWMasterSchema.getEdorNo());
		tLPIndUWSubSchema.setEdorType(aLPIndUWMasterSchema.getEdorType());
		tLPIndUWSubSchema.setGrpContNo(aLPIndUWMasterSchema.getGrpContNo());
		tLPIndUWSubSchema.setContNo(aLPIndUWMasterSchema.getContNo());
		tLPIndUWSubSchema.setProposalContNo(aLPIndUWMasterSchema
				.getProposalContNo());
		tLPIndUWSubSchema.setUWNo(aLPIndUWMasterSchema.getUWNo());
		tLPIndUWSubSchema.setAppntName(aLPIndUWMasterSchema.getAppntName());
		tLPIndUWSubSchema.setAppntNo(aLPIndUWMasterSchema.getAppntNo());
		tLPIndUWSubSchema.setInsuredName(aLPIndUWMasterSchema.getInsuredName());
		tLPIndUWSubSchema.setInsuredNo(aLPIndUWMasterSchema.getInsuredNo());
		tLPIndUWSubSchema.setAgentCode(aLPIndUWMasterSchema.getAgentCode());
		tLPIndUWSubSchema.setAgentGroup(aLPIndUWMasterSchema.getAgentGroup());
		tLPIndUWSubSchema.setAutoUWFlag(aLPIndUWMasterSchema.getAutoUWFlag());
		tLPIndUWSubSchema.setPassFlag(aLPIndUWMasterSchema.getPassFlag());
		tLPIndUWSubSchema.setUWGrade(aLPIndUWMasterSchema.getUWGrade());
		tLPIndUWSubSchema.setAppGrade(aLPIndUWMasterSchema.getUWGrade());
		tLPIndUWSubSchema.setPostponeDay(aLPIndUWMasterSchema.getPostponeDay());
		tLPIndUWSubSchema.setPostponeDate(aLPIndUWMasterSchema.getPostponeDate());
		tLPIndUWSubSchema.setState(aLPIndUWMasterSchema.getState());
		tLPIndUWSubSchema.setUWIdea(aLPIndUWMasterSchema.getUWIdea());
		tLPIndUWSubSchema.setUpReportContent(aLPIndUWMasterSchema
				.getUpReportContent());
		tLPIndUWSubSchema.setHealthFlag(aLPIndUWMasterSchema.getHealthFlag());
		tLPIndUWSubSchema.setQuesFlag(aLPIndUWMasterSchema.getQuesFlag());
		tLPIndUWSubSchema.setSpecFlag(aLPIndUWMasterSchema.getSpecFlag());
		tLPIndUWSubSchema.setAddPremFlag(aLPIndUWMasterSchema.getAddPremFlag());
		tLPIndUWSubSchema.setAddPremReason(aLPIndUWMasterSchema.getAddPremReason());
		tLPIndUWSubSchema.setReportFlag(aLPIndUWMasterSchema.getReportFlag());
		tLPIndUWSubSchema.setPrintFlag(aLPIndUWMasterSchema.getPrintFlag());
		tLPIndUWSubSchema.setPrintFlag2(aLPIndUWMasterSchema.getPrintFlag2());
		tLPIndUWSubSchema.setChangePolFlag(aLPIndUWMasterSchema.getChangePolFlag());
		tLPIndUWSubSchema.setChangePolReason(aLPIndUWMasterSchema
				.getChangePolReason());
		tLPIndUWSubSchema.setSpecReason(aLPIndUWMasterSchema.getSpecFlag());
		tLPIndUWSubSchema.setManageCom(aLPIndUWMasterSchema.getManageCom());
		tLPIndUWSubSchema.setOperator(mGlobalInput.Operator);
		tLPIndUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		tLPIndUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		tLPIndUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLPIndUWSubSchema.setMakeTime(PubFun.getCurrentTime());

		mLPIndUWSubSet.add(tLPIndUWSubSchema);
		return tLPIndUWSubSchema;
	}
	

}
