package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCRReportDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.f1printgrp.PrintManagerBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCRReportItemSchema;
import com.sinosoft.lis.schema.LCRReportSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCRReportItemSet;
import com.sinosoft.lis.vschema.LCRReportSet;
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
public class UWRReportBL {
private static Logger logger = Logger.getLogger(UWRReportBL.class);
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
	private String mCalCode; // 计算编码
	private String mUser;
	private FDate fDate = new FDate();
	private double mValue;
	private String mInsuredNo = "";

	/** 业务处理相关变量 */
	private LCContSet mLCContSet = new LCContSet();

	private LCContSchema mLCContSchema = new LCContSchema();
	private String mPolNo = "";
	private String mOldPolNo = "";
	private String mContNo = "";

	/** 体检资料主表 */
	private LCRReportSet mLCRReportSet = new LCRReportSet();
	private LCRReportSet mAllLCRReportSet = new LCRReportSet();

	private LCRReportSchema mmLCRReportSchema = new LCRReportSchema();
	/** 体检资料项目表 */
	private LCRReportItemSet mLCRReportItemSet = new LCRReportItemSet();
	private LCRReportItemSet mmLCRReportItemSet = new LCRReportItemSet();
	private LCRReportItemSet mAllLCRReportItemSet = new LCRReportItemSet();
	/** 打印管理表 */
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private LOPRTManagerSet mAllLOPRTManagerSet = new LOPRTManagerSet();

	private GlobalInput mGlobalInput = new GlobalInput();

	public UWRReportBL() {
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
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("---UWAutoHealthBL getInputData---");

		LCContSchema tLCContSchema = new LCContSchema();
		// tLCContSchema = ( LCContSchema )mLCContSet.get( 1 );
		mmLCRReportSchema = mLCRReportSet.get(1);
		String tProposalNo = mmLCRReportSchema.getProposalContNo();
		mOldPolNo = mmLCRReportSchema.getProposalContNo();
		mPolNo = mmLCRReportSchema.getProposalContNo();
		mInsuredNo = mmLCRReportSchema.getCustomerNo();
		mContNo = mmLCRReportSchema.getContNo();

		// 校验数据是否打印
		if (!CheckReply()) {
			return false;
		}

		logger.debug("---UWAutoHealthBL checkData---");
		// 数据操作业务处理
		if (!dealData(tLCContSchema)) {
			// continue;
			return false;
		} else {
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

		PubSubmit tSubmit = new PubSubmit();

		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "GrpUWAutoChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);

			return false;
		}
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData(LCContSchema tLCContSchema) {

		if (dealOnePol() == false) {
			return false;
		}

		return true;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealOnePol() {

		// 健康信息
		if (prepareHealth() == false) {
			return false;
		}

		// 打印队列
		if (print() == false) {
			return false;
		}

		LCRReportSet tLCRReportSet = new LCRReportSet();
		tLCRReportSet.set(mLCRReportSet);
		mAllLCRReportSet.add(tLCRReportSet);

		LCRReportItemSet tLCRReportItemSet = new LCRReportItemSet();
		tLCRReportItemSet.set(mLCRReportItemSet);
		mAllLCRReportItemSet.add(tLCRReportItemSet);

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		GlobalInput tGlobalInput = new GlobalInput();

		tGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		mOperate = tGlobalInput.Operator;
		mManageCom = tGlobalInput.ManageCom;
		logger.debug(mManageCom);
		// mmLCContSet.set((LCContSet)cInputData.getObjectByObjectName("LCContSet",0));

		// logger.debug("LCContSet="+mmLCContSet.get(1).getContNo());
		mLCRReportSet.set((LCRReportSet) cInputData.getObjectByObjectName(
				"LCRReportSet", 0));
		mmLCRReportItemSet.set((LCRReportItemSet) cInputData
				.getObjectByObjectName("LCRReportItemSet", 0));
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		// int n = mmLCContSet.size();
		int flag = 0; // 怕判断是不是所有保单都失败
		int j = 0; // 符合条件保单个数

		if (1 > 0) {
			LCContDB tLCContDB = new LCContDB();
			// LCContSchema tLCContSchema = mmLCContSet.get(1);

			// 取被保人客户号
			LCRReportSchema tLCRReportSchema = new LCRReportSchema();
			tLCRReportSchema = mLCRReportSet.get(1);
			mInsuredNo = tLCRReportSchema.getCustomerNo();

			tLCContDB.setContNo(tLCRReportSchema.getContNo());
			logger.debug("--BL--Pol--" + tLCRReportSchema.getContNo());
			String temp = tLCRReportSchema.getContNo();
			logger.debug("temp" + temp);
			mLCContSet = tLCContDB.query();
			if (mLCContSet.size() == 0) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCContDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWAutoHealthBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "投保单查询失败!";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				mLCContSchema = mLCContSet.get(1);
				flag = 1;
				j++;
				// tLCContSchema.setSchema( tLCContDB );
				// mLCContSet.add(tLCContSchema);
			}

		}

		if (flag == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 校验是否打印
	 * 
	 * @return
	 */
	private boolean CheckReply() {
		String tsql = "select * from LCRReport where ContNo = '" + mContNo
				+ "' and ProposalContNo ='" + mPolNo + "' and ReplyFlag = '0'";

		logger.debug("sql:" + tsql);

		LCRReportDB tLCRReportDB = new LCRReportDB();
		LCRReportSet tLCRReportSet = new LCRReportSet();

		tLCRReportSet = tLCRReportDB.executeQuery(tsql);

		if (tLCRReportSet.size() == 0) {
		} else {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWRReportBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "上次生存调查报告尚未回复,不能录入!";
			this.mErrors.addOneError(tError);

			return false;
		}

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

		tLOPRTManagerDB.setCode(PrintManagerBL.CODE_MEET); // 契调
		tLOPRTManagerDB.setOtherNo(mPolNo);
		tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
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

		// 准备打印管理表数据
		LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
		logger.debug(mGlobalInput.ComCode);

		logger.debug("mPrtSeq=" + mPrtSeq);
		mLOPRTManagerSchema.setPrtSeq(mPrtSeq);
		mLOPRTManagerSchema.setOtherNo(mLCContSchema.getContNo());

		mLOPRTManagerSchema.setStandbyFlag3(mLCContSchema.getGrpContNo()); // modify
																			// by
																			// zhangxing
		logger.debug("StandbyFlag3="
				+ mLOPRTManagerSchema.getStandbyFlag3());
		mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL); // 保单号
		mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_MEET); // 契调
		mLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
		mLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
		mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
		mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);

		mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
		mLOPRTManagerSchema.setStateFlag("0");
		mLOPRTManagerSchema.setPatchFlag("0");
		mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());

		mLOPRTManagerSchema.setStandbyFlag1(mInsuredNo); // 被保险人编码

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
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mLCContSchema.getAgentCode());
		if (!tLAAgentDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthBL";
			tError.functionName = "prepareHealth";
			tError.errorMessage = "取代理人姓名失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LCRReportSchema tLCRReportSchema = new LCRReportSchema();
		tLCRReportSchema = mLCRReportSet.get(1);
		mInsuredNo = tLCRReportSchema.getCustomerNo();

		// 取被保人姓名
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();

		tLCInsuredDB.setInsuredNo(mInsuredNo);

		tLCInsuredDB.setContNo(mLCContSchema.getContNo());

		if (!tLCInsuredDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthBL";
			tError.functionName = "prepareHealth";
			tError.errorMessage = "取被保人姓名失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
		String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit);
		mPrtSeq = tPrtSeq;
		tLCRReportSchema.setPrtSeq(mPrtSeq);
		logger.debug("mPrtSeq=" + mPrtSeq);

		tLCRReportSchema.setGrpContNo(mLCContSchema.getGrpContNo());
		tLCRReportSchema.setName(tLCInsuredDB.getName());
		tLCRReportSchema.setReplyFlag("0");
		tLCRReportSchema.setAppntName(mLCContSchema.getAppntName());
		// tLCRReportSchema.setAgentCode(mLCContSchema.getAgentCode());
		// tLCRReportSchema.setAgentName(tLAAgentDB.getName());
		tLCRReportSchema.setManageCom(mLCContSchema.getManageCom());

		// tLCRReportSchema.setPEBeforeCond(mmLCRReportSchema.getPEBeforeCond());
		tLCRReportSchema.setOperator(mOperate); // 操作员
		tLCRReportSchema.setMakeDate(PubFun.getCurrentDate());
		tLCRReportSchema.setMakeTime(PubFun.getCurrentTime());
		tLCRReportSchema.setModifyDate(PubFun.getCurrentDate());
		tLCRReportSchema.setModifyTime(PubFun.getCurrentTime());

		mLCRReportSet.clear();
		mLCRReportSet.add(tLCRReportSchema);

		// 取契调资料项目信息
		mLCRReportItemSet.clear();
		merrcount = mmLCRReportItemSet.size();
		if (merrcount > 0) {
			for (int i = 1; i <= merrcount; i++) {
				// 取出错信息
				LCRReportItemSchema tLCRReportItemSchema = new LCRReportItemSchema();
				tLCRReportItemSchema = mmLCRReportItemSet.get(i);
				// 生成流水号
				String tserialno = "" + i;
				tLCRReportItemSchema.setGrpContNo(mLCContSchema.getGrpContNo());
				tLCRReportItemSchema.setProposalContNo(mPolNo);
				tLCRReportItemSchema.setContNo(mLCContSchema.getContNo());
				tLCRReportItemSchema.setPrtSeq(mPrtSeq);
				// 由于先没有该字段，先把它注释掉，用到的时候再看（张星）
				// tLCRReportItemSchemasetPEItemCode(tLCRReportItemSchema.getPEItemCode());
				// //核保规则编码
				tLCRReportItemSchema.setRReportItemName(tLCRReportItemSchema
						.getRReportItemName()); // 核保出错信息
				// tLCRReportItemSchema.setCustomerNo(mInsuredNo);
				tLCRReportItemSchema.setModifyDate(PubFun.getCurrentDate()); // 当前值
				tLCRReportItemSchema.setModifyTime(PubFun.getCurrentTime());

				LCRReportItemSchema ttLCRReportItemSchema = new LCRReportItemSchema();
				ttLCRReportItemSchema.setSchema(tLCRReportItemSchema);

				mLCRReportItemSet.add(ttLCRReportItemSchema);
			}
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {

		mResult.clear();
		MMap map = new MMap();

		map.put(mAllLCRReportSet, "INSERT");
		map.put(mAllLCRReportItemSet, "INSERT");
		map.put(mLOPRTManagerSet, "INSERT");

		mResult.add(map);

	}

}
