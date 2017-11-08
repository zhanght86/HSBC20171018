package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author lizhuo
 * @version 1.0
 */
public class UWIssuePolPrintBL {
private static Logger logger = Logger.getLogger(UWIssuePolPrintBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 传出数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 额外传递的参数 */
	private LCContSchema mLCContSchema = new LCContSchema();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mCustomerNo;
	private String mMissionID;
	private String mSubMissionID;
	private String mPrtSeq;
	private String ComCode;

	/** 打印管理表 */
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();

	/** 问题件表 */
	private LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

	/** 合同数据* */
	public UWIssuePolPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 进行业务处理
		if (!checkData()) {
			return false;
		}
		// 准备打印
		if (!preparePrint()) {
			return false;
		}
		// 进行业务处理
		if (!prepareOutputData()) {
			return false;
		}
		// PubSubmit tPubSubmit = new PubSubmit();
		// if (!tPubSubmit.submitData(mResult, ""))
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tPubSubmit.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "UWAutoChkBL";
		// tError.functionName = "submitData";
		// tError.errorMessage = "数据提交失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		//
		logger.debug("Start UWIssuePolPrintBL Submit...");

		logger.debug("UWIssuePolPrintBL Submit OK!");

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	private boolean getInputData(VData cInputData) {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLCContSchema = (LCContSchema) mInputData.getObjectByObjectName(
					"LCContSchema", 0);
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();

			CError tError = new CError();
			tError.moduleName = "UWNotePadBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);

			return false;
		}
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWIssuePolPrint";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		ComCode = mGlobalInput.ComCode;

		/*
		 * mManageCom = mGlobalInput.ManageCom; if (mManageCom == null ||
		 * mManageCom.trim().equals("")) { // @@错误处理
		 * //this.mErrors.copyAllErrors( tLCContDB.mErrors ); CError tError =
		 * new CError(); tError.moduleName = "UWIssuePolPrint";
		 * tError.functionName = "getInputData"; tError.errorMessage =
		 * "前台传输全局公共数据ManageCom失败!"; this.mErrors.addOneError(tError); return
		 * false; }
		 */

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWIssuePolPrint";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得业务数据
		if (mLCContSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWIssuePolPrint";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 获取ContNo
		mContNo = mLCContSchema.getContNo();
		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWIssuePolPrint";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中ContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		/*
		 * mMissionID = (String) mTransferData.getValueByName("MissionID"); if
		 * (mMissionID == null) { // @@错误处理 //this.mErrors.copyAllErrors(
		 * tLCContDB.mErrors ); CError tError = new CError(); tError.moduleName =
		 * "UWIssuePolPrint"; tError.functionName = "getInputData";
		 * tError.errorMessage = "前台传输业务数据中MissionID失败!";
		 * this.mErrors.addOneError(tError); return false; } mSubMissionID =
		 * (String) mTransferData.getValueByName("SubMissionID"); if (mMissionID ==
		 * null) { // @@错误处理 //this.mErrors.copyAllErrors( tLCContDB.mErrors );
		 * CError tError = new CError(); tError.moduleName = "UWIssuePolPrint";
		 * tError.functionName = "getInputData"; tError.errorMessage =
		 * "前台传输业务数据中MissionID失败!"; this.mErrors.addOneError(tError); return
		 * false; }
		 */
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return 如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		logger.debug(mLOPRTManagerSet.size());
		MMap map = new MMap();

		map.put(mLOPRTManagerSet, "INSERT");
		map.put(tLCIssuePolSet, "UPDATE");
		// logger.debug(tLCIssuePolSet.size());
		// LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();
		// for(int i=1;i <= tLCIssuePolSet.size();i++){
		// tLCIssuePolSchema = tLCIssuePolSet.get(i);
		//
		// }
		mResult.add(map);
		mResult.add(mLOPRTManagerSet);
		this.mResult.add(this.tLCIssuePolSet);

		return true;
	}

	// 准备打印队列
	public boolean preparePrint() {
		// tongmeng 2007-10-18 modify
		// 增加对外部问题件的发送给业务员的处理
		String i_sql = "select IssueType from lcissuepol where contno = '"
				+ "?contno?"
				+ "'"
				// + " and backobjtype = '2' and (state = '0' or state is
				// null)";
				+ " and backobjtype in ('2','3') and (state = '0' or state is null)";
		logger.debug("UWIssuePolPrintBL i_sql:" + i_sql);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(i_sql);
		sqlbv.put("contno", mContNo);
		ExeSQL i_exesql = new ExeSQL();
		SSRS i_ssrs = new SSRS();
		i_ssrs = i_exesql.execSQL(sqlbv);
		int issue = i_ssrs.getMaxRow();
		if (issue == 0) {
			CError tError = new CError();
			tError.moduleName = "UWIssuePolPrint";
			tError.functionName = "perpareOutputData";
			tError.errorMessage = "此ContNo无符合要求的IssueType!";
			this.mErrors.addOneError(tError);
			return false;
		}
		int m = 0;
		for (int j = 1; j <= issue; j++) {
			if (i_ssrs.GetText(j, 1).equals("99")) {
				m = m + 1;
			}
		}
		int n = 0;
		n = issue - m;
		String no22PrtSeq = new String();
		String PrtSeq22[] = null;
		PrtSeq22 = new String[m];
		// IssueType非22
		if (n != 0) {
			String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
			mPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit);
			no22PrtSeq = mPrtSeq;
			logger.debug(mPrtSeq);
			// 准备打印管理表数据
			mLOPRTManagerSchema.setPrtSeq(mPrtSeq);
			mLOPRTManagerSchema.setOtherNo(mContNo);
			logger.debug("mContNo");
			mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
			mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_ISSUE); // 问题件
			mLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
			mLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
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
			mLOPRTManagerSchema.setCode("14");
			mLOPRTManagerSchema.setOldPrtSeq(mPrtSeq);

			mLOPRTManagerSet.add(mLOPRTManagerSchema);
		}

		if (m != 0) {
			for (int j = 1; j <= m; j++) {
				String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
				mPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit);
				logger.debug(mPrtSeq);
				PrtSeq22[j - 1] = mPrtSeq;
				LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
				// 准备打印管理表数据
				tLOPRTManagerSchema.setPrtSeq(mPrtSeq);
				tLOPRTManagerSchema.setOtherNo(mContNo);
				tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
				tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_ISSUE); // 问题件
				tLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
				tLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
				tLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
				tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
				// mLOPRTManagerSchema.setExeCom();
				// mLOPRTManagerSchema.setExeOperator();
				tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
				tLOPRTManagerSchema.setStateFlag("0");
				tLOPRTManagerSchema.setPatchFlag("0");
				tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
				tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
				// mLOPRTManagerSchema.setDoneDate() ;
				// mLOPRTManagerSchema.setDoneTime();
				tLOPRTManagerSchema.setStandbyFlag1(mCustomerNo); // 被保险人编码
				tLOPRTManagerSchema.setStandbyFlag3(mMissionID);
				tLOPRTManagerSchema.setCode("17");
				tLOPRTManagerSchema.setOldPrtSeq(mPrtSeq);

				mLOPRTManagerSet.add(tLOPRTManagerSchema);
			}
		}
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		logger.debug(i_sql);
		// tongmeng 2007-10-18 modify
		// 增加对发放给业务员的问题件的处理
		String m_sql = "select * from lcissuepol where contno = '"
				+ "?contno?"
				+ "'"
				// + " and backobjtype = '2' and (state = '0' or state is
				// null)";
				+ " and backobjtype in ('2','3') and (state = '0' or state is null)";
		logger.debug("UWIssuePolPrintBL m_sql:" + m_sql);
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(m_sql);
		sqlbv1.put("contno", mContNo);
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv1);
		if (tLCIssuePolSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "UWPewpareIssueBL";
			tError.functionName = "PrintIssue";
			tError.errorMessage = "无需要发放的问题件! ";
			this.mErrors.addOneError(tError);
			return false;
		}
		int j = 0;
		for (int i = 1; i <= issue; i++) {
			logger.debug(tLCIssuePolSet.get(i).getIssueType());
			if (tLCIssuePolSet.get(i).getIssueType().equals("99")) {
				tLCIssuePolSet.get(i).setPrtSeq(PrtSeq22[j]);
				j++;
			} else {
				tLCIssuePolSet.get(i).setPrtSeq(no22PrtSeq);
			}
			tLCIssuePolSet.get(i).setState("1");
		}
		return true;
	}

	// 对问题见表状态的更改
	public boolean prepareIssue() {
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		// tongmeng 2007-10-18 modify
		// 增加对发放给业务员的处理
		String sql = "select * from lcissuepol where contno = '"
				+ "?contno?"
				+ "'"
				// + " and backobjtype = '2' and (state = '0' or state is
				// null)";
				+ " and backobjtype in ('2','3') and (state = '0' or state is null)";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(sql);
		sqlbv2.put("contno", mContNo);
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv2);
		if (tLCIssuePolSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "UWPewpareIssueBL";
			tError.functionName = "PrintIssue";
			tError.errorMessage = "无需要发放的问题件! ";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= tLCIssuePolSet.size(); i++) {
			tLCIssuePolSet.get(1).setPrtSeq(mPrtSeq);
			tLCIssuePolSet.get(i).setState("1");
		}
		return true;
	}

	public boolean checkData() {
		LCContDB tLCContDB = new LCContDB();
		LCContSet tLCContSet = new LCContSet();
		tLCContDB.setContNo(mContNo);
		tLCContSet = tLCContDB.query();
		if (tLCContSet.size() != 1) {
			CError tError = new CError();
			tError.moduleName = "UWPewpareIssueBL";
			tError.functionName = "CheckData";
			tError.errorMessage = "数据验证出错! ";
			this.mErrors.addOneError(tError);
		}
		// mLCContSchema = tLCContSet.get(1);
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LCContF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) throws Exception {
		UWIssuePolPrintBL tUWIssualPolPrintBL = new UWIssuePolPrintBL();
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ComCode = "86";

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo("130110000000340");
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema = tLCContDB.getSchema();

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tLCContSchema);

		tUWIssualPolPrintBL.submitData(tVData, "");
	}
}
