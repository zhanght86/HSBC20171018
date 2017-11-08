package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPENoticeDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.f1printgrp.PrintManagerBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPENoticeItemSchema;
import com.sinosoft.lis.schema.LCPENoticeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCPENoticeItemSet;
import com.sinosoft.lis.vschema.LCPENoticeSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统体检资料录入部分
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
public class UWAutoHealthBL {
private static Logger logger = Logger.getLogger(UWAutoHealthBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private String mManageCom;
	private String mpassflag; // 通过标记
	private int merrcount; // 错误条数

	private FDate fDate = new FDate();

	private String mInsuredNo = "";

	/** 业务处理相关变量 */
	private LCContSet mLCContSet = new LCContSet();

	private LCContSchema mLCContSchema = new LCContSchema();
	private String mPolNo = "";
	private String mOldPolNo = "";

	/** 核保主表 */
	private LCCUWMasterSet mLCUWMasterSet = new LCCUWMasterSet();
	private LCCUWMasterSet mAllLCCUWMasterSet = new LCCUWMasterSet();
	private LCCUWMasterSchema mLCCUWMasterSchema = new LCCUWMasterSchema();

	/** 体检资料主表 */
	private LCPENoticeSet mLCPENoticeSet = new LCPENoticeSet();
	private LCPENoticeSet mAllLCPENoticeSet = new LCPENoticeSet();

	private LCPENoticeSchema mmLCPENoticeSchema = new LCPENoticeSchema();
	/** 体检资料项目表 */
	private LCPENoticeItemSet mLCPENoticeItemSet = new LCPENoticeItemSet();
	private LCPENoticeItemSet mmLCPENoticeItemSet = new LCPENoticeItemSet();
	private LCPENoticeItemSet mAllLCPENoticeItemSet = new LCPENoticeItemSet();
	/** 打印管理表 */
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private LOPRTManagerSet mAllLOPRTManagerSet = new LOPRTManagerSet();

	private GlobalInput mGlobalInput = new GlobalInput();

	public UWAutoHealthBL() {
	}

	String mPrtSeq;

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		int flag = 0; // 判断是不是所有数据都不成功
		int j = 0; // 符合条件数据个数

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		logger.debug("---1---");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;
		logger.debug("---UWAutoHealthBL getInputData---");

		LCContSchema tLCContSchema = new LCContSchema();
		// tLCContSchema = ( LCContSchema )mLCContSet.get( 1 );
		mmLCPENoticeSchema = mLCPENoticeSet.get(1);
		String tProposalNo = mmLCPENoticeSchema.getProposalContNo();
		mOldPolNo = mmLCPENoticeSchema.getProposalContNo();
		mPolNo = mmLCPENoticeSchema.getProposalContNo();
		mInsuredNo = mmLCPENoticeSchema.getCustomerNo();

		// 校验数据是否打印
		if (!checkPrint())
			return false;
		logger.debug("--checkPrintFalse-");
		logger.debug("---UWAutoHealthBL checkData---");
		// 数据操作业务处理
		if (!dealData(tLCContSchema))
			// continue;
			return false;
		else {
			flag = 1;
			j++;

		}

		if (flag == 0) {
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthBL";
			tError.functionName = "submitData";
			tError.errorMessage = "没有自动通过保单!";
			this.mErrors.addOneError(tError);
			return false;
		}

		logger.debug("---UWAutoHealthBL dealData---");
		// 准备给后台的数据
		prepareOutputData();

		logger.debug("---UWAutoHealthBL prepareOutputData---");
		// 数据提交
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWManuAddChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("UWManuAddChkBL Submit OK!");
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(LCContSchema tLCContSchema) {

		if (dealOnePol() == false)
			return false;

		return true;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealOnePol() {

		// 健康信息
		if (prepareHealth() == false)
			return false;

		// 打印队列
		if (print() == false)
			return false;

		LCPENoticeSet tLCPENoticeSet = new LCPENoticeSet();
		tLCPENoticeSet.set(mLCPENoticeSet);
		mAllLCPENoticeSet.add(tLCPENoticeSet);

		LCPENoticeItemSet tLCPENoticeItemSet = new LCPENoticeItemSet();
		tLCPENoticeItemSet.set(mLCPENoticeItemSet);
		mAllLCPENoticeItemSet.add(tLCPENoticeItemSet);

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		logger.debug("------getInputData() beginning--------");
		GlobalInput tGlobalInput = new GlobalInput();
		logger.debug("------getInputData() beginning0000--------");
		tGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		logger.debug("------getInputData() beginning111--------");

		mOperate = tGlobalInput.Operator;
		mManageCom = tGlobalInput.ManageCom;
		logger.debug("ManageCom" + mManageCom);

		mLCPENoticeSet.set((LCPENoticeSet) cInputData.getObjectByObjectName(
				"LCPENoticeSet", 0));
		// logger.debug("LCPENoticeSet");
		mmLCPENoticeItemSet.set((LCPENoticeItemSet) cInputData
				.getObjectByObjectName("LCPENoticeItemSet", 0));
		// logger.debug("LCPENoticeItemSet");
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		// logger.debug("GlobalInput");

		int flag = 0; // 怕判断是不是所有保单都失败
		int j = 0; // 符合条件保单个数

		if (1 > 0) {
			LCContDB tLCContDB = new LCContDB();
			logger.debug("come in if 1");

			// 取被保人客户号
			LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();
			tLCPENoticeSchema = mLCPENoticeSet.get(1);
			logger.debug("get tLCPENoticeSchema");
			// mInsuredNo = tLCPENoticeSchema.getCustomerNo();

			tLCContDB.setContNo(tLCPENoticeSchema.getContNo());
			logger.debug("--BL--Pol--" + tLCPENoticeSchema.getContNo());
			String temp = tLCPENoticeSchema.getContNo();
			logger.debug("temp" + temp);
			mLCContSet = tLCContDB.query();
			logger.debug("come in if 2");
			if (mLCContSet.size() == 0) {
				// @@错误处理
				logger.debug("come in if 3");
				this.mErrors.copyAllErrors(tLCContDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWAutoHealthBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "投保单查询失败!";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				logger.debug("come in if 4");
				mLCContSchema = mLCContSet.get(1);
				flag = 1;
				j++;

			}

		}

		if (flag == 0) {
			logger.debug("come in if 5");
			return false;
		} else {
			logger.debug("come in if 6");
			return true;
		}
	}

	/**
	 * 校验是否打印
	 * 
	 * @return
	 */
	private boolean checkPrint() {
		LCPENoticeDB tLCPENoticeDB = new LCPENoticeDB();
		tLCPENoticeDB.setContNo(mPolNo);
		tLCPENoticeDB.setCustomerNo(mInsuredNo);
		LCPENoticeSet tLCPENoticeSet = tLCPENoticeDB.query();

		if (tLCPENoticeSet.size() == 0) {
			logger.debug("come in if 9");
		} else {
			logger.debug("come in if 7");
			if (tLCPENoticeDB.getPrintFlag() != null
					&& tLCPENoticeDB.getPrintFlag().equals("Y")) {
				CError tError = new CError();
				tError.moduleName = "UWAutoHealthBL";
				tError.functionName = "checkPrint";
				tError.errorMessage = "体检通知已经录入尚未打印，不能录入新体检资料!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		logger.debug("come in if 8");
		return true;
	}

	/**
	 * 打印信息表
	 * 
	 * @return
	 */
	private boolean print() {

		// 处于未打印状态的通知书在打印队列中只能有一个
		// 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setCode(PrintManagerBL.CODE_PE);// 体检
		tLOPRTManagerDB.setOtherNo(mPolNo);
		tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_INDPOL);// 保单号
		tLOPRTManagerDB.setStandbyFlag1(mInsuredNo);
		tLOPRTManagerDB.setStateFlag("0");

		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();
		if (tLOPRTManagerSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthBL";
			tError.functionName = "preparePrint";
			tError.errorMessage = "查询打印管理表信息出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLOPRTManagerSet.size() != 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorUWAutoHealthAfterInitService";
			tError.functionName = "preparePrint";
			tError.errorMessage = "在打印队列中已有一个处于未打印状态的体检通知书!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("URGEInterval");

		if (tLDSysVarDB.getInfo() == false) {
			CError tError = new CError();
			tError.moduleName = "UWSendPrintBL";
			tError.functionName = "prepareURGE";
			tError.errorMessage = "没有描述催发间隔!";
			this.mErrors.addOneError(tError);
			return false;
		}
		FDate tFDate = new FDate();
		int tInterval = Integer.parseInt(tLDSysVarDB.getSysVarValue());
		logger.debug(tInterval);

		Date tDate = PubFun.calDate(tFDate.getDate(PubFun.getCurrentDate()),
				tInterval, "D", null);
		logger.debug(tDate);// 取预计催办日期

		// 准备打印管理表数据
		LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
		logger.debug(mGlobalInput.ComCode);

		logger.debug("mPrtSeq=" + mPrtSeq);
		mLOPRTManagerSchema.setPrtSeq(mPrtSeq);
		mLOPRTManagerSchema.setOtherNo(mPolNo);

		mLOPRTManagerSchema.setStandbyFlag3(mLCContSchema.getGrpContNo());// modify
																			// by
																			// zhangxing
		logger.debug("StandbyFlag3="
				+ mLOPRTManagerSchema.getStandbyFlag3());
		mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL);// 保单号
		mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PE);// 体检
		mLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
		mLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
		mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
		mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);

		mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT);// 前台打印
		mLOPRTManagerSchema.setStateFlag("0");
		mLOPRTManagerSchema.setPatchFlag("0");
		mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());

		mLOPRTManagerSchema.setForMakeDate(tDate);

		mLOPRTManagerSchema.setStandbyFlag1(mInsuredNo);// 被保险人编码

		mLOPRTManagerSet.add(mLOPRTManagerSchema);
		return true;
	}

	/**
	 * 准备体检资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareHealth() {
		int tuwno = 0;
		// 取险种名称

		// 取代理人姓名
		/*
		 * LAAgentDB tLAAgentDB = new LAAgentDB();
		 * tLAAgentDB.setAgentCode(mLCContSchema.getAgentCode());
		 * if(!tLAAgentDB.getInfo()) { // @@错误处理 CError tError = new CError();
		 * tError.moduleName = "UWAutoHealthBL"; tError.functionName =
		 * "prepareHealth"; tError.errorMessage = "取代理人姓名失败!"; this.mErrors
		 * .addOneError(tError) ; return false; } //
		 */
		LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();
		tLCPENoticeSchema = mLCPENoticeSet.get(1);
		// mInsuredNo = tLCPENoticeSchema.getCustomerNo();

		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
		String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit);
		mPrtSeq = tPrtSeq;
		tLCPENoticeSchema.setPrtSeq(mPrtSeq);
		logger.debug("mPrtSeq=" + mPrtSeq);
		// tLCPENoticeSchema.setRiskName(tLMRiskDB.getRiskName());
		/*
		 * Lis5.3 upgrade get
		 * tLCPENoticeSchema.setInsuredName(tLCInsuredDB.getName());
		 */
		tLCPENoticeSchema.setGrpContNo(mLCContSchema.getGrpContNo());
		tLCPENoticeSchema.setName(mInsuredNo);
		tLCPENoticeSchema.setPrintFlag("Y");
		tLCPENoticeSchema.setAppName(mLCContSchema.getAppntName());
		tLCPENoticeSchema.setAgentCode(mLCContSchema.getAgentCode());
		// tLCPENoticeSchema.setAgentName(tLAAgentDB.getName());
		tLCPENoticeSchema.setManageCom(mLCContSchema.getManageCom());

		tLCPENoticeSchema.setPEBeforeCond(mmLCPENoticeSchema.getPEBeforeCond());
		tLCPENoticeSchema.setOperator(mOperate); // 操作员
		tLCPENoticeSchema.setMakeDate(PubFun.getCurrentDate());
		tLCPENoticeSchema.setMakeTime(PubFun.getCurrentTime());
		tLCPENoticeSchema.setModifyDate(PubFun.getCurrentDate());
		tLCPENoticeSchema.setModifyTime(PubFun.getCurrentTime());
		tLCPENoticeSchema.setRemark(mmLCPENoticeSchema.getRemark());

		mLCPENoticeSet.clear();
		mLCPENoticeSet.add(tLCPENoticeSchema);

		// 取体检资料项目信息
		mLCPENoticeItemSet.clear();
		merrcount = mmLCPENoticeItemSet.size();
		if (merrcount > 0) {
			for (int i = 1; i <= merrcount; i++) {
				// 取出错信息
				LCPENoticeItemSchema tLCPENoticeItemSchema = new LCPENoticeItemSchema();
				tLCPENoticeItemSchema = mmLCPENoticeItemSet.get(i);
				// 生成流水号
				String tserialno = "" + i;

				tLCPENoticeItemSchema.setProposalContNo(mPolNo);
				tLCPENoticeItemSchema.setContNo(mPolNo);
				tLCPENoticeItemSchema.setPrtSeq(mPrtSeq);
				// 由于先没有该字段，先把它注释掉，用到的时候再看（张星）
				// tLCPENoticeItemSchemasetPEItemCode(tLCPENoticeItemSchema.getPEItemCode());
				// //核保规则编码
				tLCPENoticeItemSchema.setPEItemName(tLCPENoticeItemSchema
						.getPEItemName()); // 核保出错信息
				// tLCPENoticeItemSchema.setCustomerNo(mInsuredNo);
				tLCPENoticeItemSchema.setModifyDate(PubFun.getCurrentDate()); // 当前值
				tLCPENoticeItemSchema.setModifyTime(PubFun.getCurrentTime());

				LCPENoticeItemSchema ttLCPENoticeItemSchema = new LCPENoticeItemSchema();
				ttLCPENoticeItemSchema.setSchema(tLCPENoticeItemSchema);

				mLCPENoticeItemSet.add(ttLCPENoticeItemSchema);
			}
		}

		// 核保主表信息
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mPolNo);

		if (tLCCUWMasterDB.getInfo() == false) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthBL";
			tError.functionName = "prepareHealth";
			tError.errorMessage = "无核保主表信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mLCCUWMasterSchema = tLCCUWMasterDB.getSchema();

		// 校验是否已经发放核保通知书
		if (mLCCUWMasterSchema.getPrintFlag().equals("Y")) {
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthBL";
			tError.functionName = "prepareHealth";
			tError.errorMessage = "已经发核保通知不可录入!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCCUWMasterSchema.setHealthFlag("1");

		logger.debug(" end prepareHealth()");

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareOutputData() {

		MMap map = new MMap();

		map.put(mAllLCPENoticeSet, "INSERT");
		map.put(mAllLCPENoticeItemSet, "INSERT");
		map.put(mLCCUWMasterSchema, "UPDATE");
		map.put(mLOPRTManagerSet, "INSERT");

		mResult.add(map);

		return true;

	}

}
