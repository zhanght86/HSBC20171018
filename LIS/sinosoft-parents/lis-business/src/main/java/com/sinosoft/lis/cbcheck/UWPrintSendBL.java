package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;

/**
 * <p>
 * Title: 发通知书
 * </p>
 * <p>
 * Description: 发通知书
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author ZhangRong
 * @version 1.0
 */
public class UWPrintSendBL {
private static Logger logger = Logger.getLogger(UWPrintSendBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	public UWPrintSendBL() {
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，
	 */

	public LOPRTManagerSchema preparePrint(String tContNo, String tCode,
			GlobalInput tGlobalInput) {
		// 处于未打印状态的通知书在打印队列中只能有一个
		// 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setCode(tCode); // 体检
		tLOPRTManagerDB.setOtherNo(tContNo);
		tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号

		tLOPRTManagerDB.setStateFlag("0");

		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();
		if (tLOPRTManagerSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthBL";
			tError.functionName = "preparePrint";
			tError.errorMessage = "查询打印管理表信息出错!";
			this.mErrors.addOneError(tError);

		}

		if (tLOPRTManagerSet.size() != 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorUWAutoHealthAfterInitService";
			tError.functionName = "preparePrint";
			tError.errorMessage = "在打印队列中已有一个处于未打印状态的体检通知书!";
			this.mErrors.addOneError(tError);

		}

		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("URGEInterval");

		if (tLDSysVarDB.getInfo() == false) {
			CError tError = new CError();
			tError.moduleName = "UWSendPrintBL";
			tError.functionName = "prepareURGE";
			tError.errorMessage = "没有描述催发间隔!";
			this.mErrors.addOneError(tError);

		}
		FDate tFDate = new FDate();
		int tInterval = Integer.parseInt(tLDSysVarDB.getSysVarValue());
		logger.debug(tInterval);

		Date tDate = PubFun.calDate(tFDate.getDate(PubFun.getCurrentDate()),
				tInterval, "D", null);
		logger.debug(tDate); // 取预计催办日期

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(tContNo);
		if (!tLCContDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "LCContDB";
			tError.functionName = "LCContDB";
			tError.errorMessage = "查询合同信息错误!";
			this.mErrors.addOneError(tError);

		}

		// 准备打印管理表数据
		LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
		logger.debug(tGlobalInput.ComCode);
		String strNoLimit = PubFun.getNoLimit(tGlobalInput.ComCode);

		String mPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit);
		mLOPRTManagerSchema.setPrtSeq(mPrtSeq);
		mLOPRTManagerSchema.setOtherNo(tContNo);

		mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
		mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_UWCHANG); // 承保计划变更
		mLOPRTManagerSchema.setManageCom(tGlobalInput.ManageCom);
		mLOPRTManagerSchema.setAgentCode(tLCContDB.getAgentCode());
		mLOPRTManagerSchema.setReqCom(tGlobalInput.ComCode);
		mLOPRTManagerSchema.setReqOperator(tGlobalInput.Operator);

		mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
		mLOPRTManagerSchema.setStateFlag("0");
		mLOPRTManagerSchema.setPatchFlag("0");
		mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());

		mLOPRTManagerSchema.setForMakeDate(tDate);

		return mLOPRTManagerSchema;

	}

}
