package com.sinosoft.workflow.cardgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCPENoticeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LAAgentSet;
import com.sinosoft.lis.vschema.LABranchGroupSet;
import com.sinosoft.lis.vschema.LCPENoticeItemSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: 工作流节点任务:新契约发客户合并通知书
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author yaory
 * @version 1.0
 */

public class GrpCustomerPrintAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(GrpCustomerPrintAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	/** 工作流引擎 */
	ActivityOperator mActivityOperator = new ActivityOperator();
	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mCustomerNo;
	private String mMissionID;

	/** 保单表 */
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private String mPrtSeq;

	/** 保全核保主表 */
	private LCCUWMasterSchema mLCCUWMasterSchema = new LCCUWMasterSchema();

	/** 体检资料主表 */
	private LCPENoticeSchema mLCPENoticeSchema = new LCPENoticeSchema();

	/** 体检资料项目表 */
	private LCPENoticeItemSet mLCPENoticeItemSet = new LCPENoticeItemSet();

	/** 打印管理表 */
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	public GrpCustomerPrintAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("进入我的地盘!");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate))
			return false;

		// 校验是否有未打印的通知书
		if (!checkData())
			return false;

		// 进行业务处理
		if (!dealData())
			return false;

		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData())
			return false;

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		logger.debug("Start BL Submit...");

		// mResult.clear();
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 校验保单信息
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mContNo);
		if (!tLCGrpContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "GrpCustomerPrintAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "保单" + mContNo + "信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCGrpContSchema.setSchema(tLCGrpContDB);

		// 校验是否有未打印的体检通知书
		// LCPENoticeDB tLCPENoticeDB = new LCPENoticeDB();
		// tLCPENoticeDB.setContNo(mContNo);
		// tLCPENoticeDB.setCustomerNo(mCustomerNo);
		//
		// if (tLCPENoticeDB.getInfo())
		// {
		// if (tLCPENoticeDB.getPrintFlag().equals("0"))
		// {
		// CError tError = new CError();
		// tError.moduleName = "GrpCustomerPrintAfterInitService";
		// tError.functionName = "checkData";
		// tError.errorMessage = "通知已经录入,但未打印，不能录入新体检资料!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// }

		// //校验保全批单核保主表
		// //校验保单信息
		// LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		// tLCCUWMasterDB.setContNo(mContNo);
		// if (!tLCCUWMasterDB.getInfo())
		// {
		// CError tError = new CError();
		// tError.moduleName = "UWRReportAfterInitService";
		// tError.functionName = "checkData";
		// tError.errorMessage = "保单" + mContNo + "保全批单核保主表信息查询失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		//
		// mLCCUWMasterSchema.setSchema(tLCCUWMasterDB);

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);

		mPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit);

		// 打印队列
		if (preparePrint() == false)
			return false;

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpCustomerPrintAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpCustomerPrintAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpCustomerPrintAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得业务数据
		if (mTransferData == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpCustomerPrintAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mContNo = (String) mTransferData.getValueByName("ContNo");// 团单合同号
		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpCustomerPrintAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中ContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mCustomerNo = (String) mTransferData.getValueByName("CustomerNo");
		if (mCustomerNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpCustomerPrintAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中CustomerNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得业务体检通知数据
		// mLCPENoticeSchema = (LCPENoticeSchema) mTransferData.getValueByName(
		// "LCPENoticeSchema");
		// if (mLCPENoticeSchema == null)
		// {
		// // @@错误处理
		// //this.mErrors.copyAllErrors( tLCContDB.mErrors );
		// CError tError = new CError();
		// tError.moduleName = "GrpCustomerPrintAfterInitService";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "前台传输获得业务体检通知数据失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// 获得业务体检通知对应的体检项目
		// mLCPENoticeItemSet = (LCPENoticeItemSet)
		// mTransferData.getValueByName(
		// "LCPENoticeItemSet");
		// if (mLCPENoticeItemSet == null || mLCPENoticeItemSet.size() == 0)
		// {
		// // @@错误处理
		// //this.mErrors.copyAllErrors( tLCContDB.mErrors );
		// CError tError = new CError();
		// tError.moduleName = "GrpCustomerPrintAfterInitService";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "前台传输获得业务体检通知对应的体检项目数据失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpCustomerPrintAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 准备体检资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareHealth() {

		// //取代理人姓名
		// LAAgentDB tLAAgentDB = new LAAgentDB();
		// tLAAgentDB.setAgentCode(mLCContSchema.getAgentCode());
		// if (!tLAAgentDB.getInfo())
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "GrpCustomerPrintAfterInitService";
		// tError.functionName = "prepareHealth";
		// tError.errorMessage = "取代理人姓名失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		// 取体检人姓名
		// LDPersonDB tLDPersonDB = new LDPersonDB();
		// tLDPersonDB.setCustomerNo(mCustomerNo);
		// if (!tLDPersonDB.getInfo())
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "GrpCustomerPrintAfterInitService";
		// tError.functionName = "prepareHealth";
		// tError.errorMessage = "取被体检客户姓名失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		// mLCPENoticeSchema.setGrpContNo(mLCContSchema.getGrpContNo());
		// mLCPENoticeSchema.setContNo(mLCContSchema.getContNo());
		// mLCPENoticeSchema.setProposalContNo(mLCContSchema.getProposalContNo());
		// mLCPENoticeSchema.setCustomerNo(mCustomerNo);
		// mLCPENoticeSchema.setName(tLDPersonDB.getName());
		// mLCPENoticeSchema.setPrtSeq(mPrtSeq);
		// mLCPENoticeSchema.setPEDate(mLCPENoticeSchema.getPEDate());
		// mLCPENoticeSchema.setPEAddress(mLCPENoticeSchema.getPEAddress());
		// mLCPENoticeSchema.setPrintFlag("0");
		// mLCPENoticeSchema.setAppName(mLCContSchema.getAppntName());
		// mLCPENoticeSchema.setAgentCode(mLCContSchema.getAgentCode());
		// mLCPENoticeSchema.setAgentName(tLAAgentDB.getName());
		// mLCPENoticeSchema.setManageCom(mLCContSchema.getManageCom());
		// mLCPENoticeSchema.setPEBeforeCond(mLCPENoticeSchema.getPEBeforeCond());
		// mLCPENoticeSchema.setOperator(mOperater); //操作员
		// mLCPENoticeSchema.setMakeDate(PubFun.getCurrentDate());
		// mLCPENoticeSchema.setMakeTime(PubFun.getCurrentTime());
		// mLCPENoticeSchema.setModifyDate(PubFun.getCurrentDate());
		// mLCPENoticeSchema.setModifyTime(PubFun.getCurrentTime());
		// mLCPENoticeSchema.setRemark(mLCPENoticeSchema.getRemark());

		// 准备检资料项目信息
		// for (int i = 1; i <= mLCPENoticeItemSet.size(); i++)
		// {
		//
		// mLCPENoticeItemSet.get(i).setGrpContNo(mLCContSchema.getGrpContNo());
		// mLCPENoticeItemSet.get(i).setContNo(mLCContSchema.getContNo());
		// mLCPENoticeItemSet.get(i).setProposalContNo(mLCContSchema.
		// getProposalContNo());
		// mLCPENoticeItemSet.get(i).setPrtSeq(mPrtSeq);
		// //mLCPENoticeItemSet.get(i).setContNo( mContNo );
		// //mLCPENoticeItemSet.get(i).setPEItemCode(); //核保规则编码
		// //mLCPENoticeItemSet.get(i).setPEItemName(); //核保出错信息
		// //mLCPENoticeItemSet.get(i).setCustomerNo(mCustomerNo);
		// mLCPENoticeItemSet.get(i).setModifyDate(PubFun.getCurrentDate());
		// //当前值
		// mLCPENoticeItemSet.get(i).setModifyTime(PubFun.getCurrentTime());
		// //mLCPENoticeItemSet.get(i).setFreePE() ;
		// }
		// 准备核保主表信息
		// mLCCUWMasterSchema.setOperator(mOperater);
		// mLCCUWMasterSchema.setManageCom(mManageCom);
		// mLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
		// mLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		return true;
	}

	/**
	 * 打印信息表
	 * 
	 * @return
	 */
	private boolean preparePrint() {
		// 处于未打印状态的通知书在打印队列中只能有一个
		// 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setCode(PrintManagerBL.CODE_UINT); // 客户合并
		tLOPRTManagerDB.setOtherNo(mContNo);
		tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
		tLOPRTManagerDB.setStandbyFlag1(mCustomerNo);
		tLOPRTManagerDB.setStateFlag("0");

		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();
		if (tLOPRTManagerSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpCustomerPrintAfterInitService";
			tError.functionName = "preparePrint";
			tError.errorMessage = "查询打印管理表信息出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		// tLDSysVarDB.setSysVar("URGEInterval");
		//
		// if (tLDSysVarDB.getInfo() == false) {
		// CError tError = new CError();
		// tError.moduleName = "UWSendPrintBL";
		// tError.functionName = "prepareURGE";
		// tError.errorMessage = "没有描述催发间隔!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// FDate tFDate = new FDate();
		// int tInterval = Integer.parseInt(tLDSysVarDB.getSysVarValue());
		// logger.debug(tInterval);
		//
		// Date tDate = PubFun.calDate(tFDate.getDate(PubFun.getCurrentDate()),
		// tInterval, "D", null);
		// logger.debug(tDate);//取预计催办日期

		// 准备打印管理表数据
		mLOPRTManagerSchema.setPrtSeq(mPrtSeq);
		mLOPRTManagerSchema.setOtherNo(mContNo);
		mLOPRTManagerSchema.setOtherNoType("01"); // 保单号
		mLOPRTManagerSchema.setCode("99"); // 客户合并
		mLOPRTManagerSchema.setManageCom(mLCGrpContSchema.getManageCom());
		mLOPRTManagerSchema.setAgentCode(mLCGrpContSchema.getAgentCode());
		mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
		mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
		// mLOPRTManagerSchema.setExeCom();
		// mLOPRTManagerSchema.setExeOperator();
		mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
		mLOPRTManagerSchema.setStateFlag("0");
		mLOPRTManagerSchema.setPatchFlag("0");
		mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
		// mLOPRTManagerSchema.setDoneDate() ;
		// mLOPRTManagerSchema.setDoneTime();
		mLOPRTManagerSchema.setStandbyFlag1(mCustomerNo); // 被保险人编码
		mLOPRTManagerSchema.setStandbyFlag3(mMissionID);
		mLOPRTManagerSchema.setOldPrtSeq(mPrtSeq);
		mLOPRTManagerSchema.setForMakeDate(PubFun.getCurrentDate());

		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {
		LAAgentDB tLAAgentDB = new LAAgentDB();
		LAAgentSet tLAAgentSet = new LAAgentSet();
		tLAAgentDB.setAgentCode(mLCGrpContSchema.getAgentCode());
		tLAAgentSet = tLAAgentDB.query();
		if (tLAAgentSet == null || tLAAgentSet.size() != 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpCustomerPrintAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人表LAAgent查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLAAgentSet.get(1).getAgentGroup() == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpCustomerPrintAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人表LAAgent中的代理机构数据丢失!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupSet tLABranchGroupSet = new LABranchGroupSet();
		tLABranchGroupDB.setAgentGroup(tLAAgentSet.get(1).getAgentGroup());
		tLABranchGroupSet = tLABranchGroupDB.query();
		if (tLABranchGroupSet == null || tLABranchGroupSet.size() != 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpCustomerPrintAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人展业机构表LABranchGroup查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLABranchGroupSet.get(1).getBranchAttr() == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpCustomerPrintAfterInitService";
			tError.functionName = "prepareTransferData";
			tError.errorMessage = "代理人展业机构表LABranchGroup中展业机构信息丢失!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mTransferData.setNameAndValue("PrtSeq2", mLOPRTManagerSchema
				.getPrtSeq());
		mTransferData.setNameAndValue("OldPrtSeq2", mLOPRTManagerSchema
				.getPrtSeq());
		mTransferData.setNameAndValue("AgentCode", mLCGrpContSchema
				.getAgentCode());
		mTransferData.setNameAndValue("AgentGroup", tLAAgentSet.get(1)
				.getAgentGroup());
		mTransferData.setNameAndValue("BranchAttr", tLABranchGroupSet.get(1)
				.getBranchAttr());
		mTransferData.setNameAndValue("ManageCom", mLCGrpContSchema
				.getManageCom());
		mTransferData.setNameAndValue("PrtNo", mContNo);// 需要准备
		mTransferData.setNameAndValue("GrpContNo", mContNo);
		return true;
	}

	/**
	 * 工作流准备后台提交数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		// //添加体检通知数据
		// map.put(mLCPENoticeSchema, "INSERT");
		//
		// //添加体检项目数据
		// map.put(mLCPENoticeItemSet, "INSERT");

		// 添加体检通知书打印管理表数据
		map.put(mLOPRTManagerSchema, "INSERT");

		// 添加保全批单核保主表通知书打印管理表数据
		// map.put(mLCCUWMasterSchema, "UPDATE");

		mResult.add(map);
		return true;
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
	 * 返回工作流中的Lwfieldmap所描述的值
	 * 
	 * @return TransferData
	 */
	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	/**
	 * 返回错误对象
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		// SysUWNoticeBL sysUWNoticeBL1 = new SysUWNoticeBL();
	}
}
