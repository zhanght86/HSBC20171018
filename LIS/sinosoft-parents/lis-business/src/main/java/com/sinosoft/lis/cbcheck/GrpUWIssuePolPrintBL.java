package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpIssuePolDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpIssuePolSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
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
public class GrpUWIssuePolPrintBL {
private static Logger logger = Logger.getLogger(GrpUWIssuePolPrintBL.class);
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
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mGrpContNo;
	private String mCustomerNo;
	private String mMissionID;
	private String mSubMissionID;
	private String mPrtSeq;
	private String ComCode;

	/** 打印管理表 */
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();

	/** 问题件表 */
	private LCGrpIssuePolSet tLCGrpIssuePolSet = new LCGrpIssuePolSet();

	/** 合同数据* */
	public GrpUWIssuePolPrintBL() {
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

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	private boolean getInputData(VData cInputData) {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLCGrpContSchema = (LCGrpContSchema) mInputData
					.getObjectByObjectName("LCGrpContSchema", 0);
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
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
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
		 * //this.mErrors.copyAllErrors( tLCGrpContDB.mErrors ); CError tError =
		 * new CError(); tError.moduleName = "UWIssuePolPrint";
		 * tError.functionName = "getInputData"; tError.errorMessage =
		 * "前台传输全局公共数据ManageCom失败!"; this.mErrors.addOneError(tError); return
		 * false; }
		 */

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWIssuePolPrint";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得业务数据
		if (mLCGrpContSchema == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWIssuePolPrint";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 获取ContNo
		mGrpContNo = mLCGrpContSchema.getGrpContNo();
		if (mGrpContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
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
		 * tLCGrpContDB.mErrors ); CError tError = new CError();
		 * tError.moduleName = "UWIssuePolPrint"; tError.functionName =
		 * "getInputData"; tError.errorMessage = "前台传输业务数据中MissionID失败!";
		 * this.mErrors.addOneError(tError); return false; } mSubMissionID =
		 * (String) mTransferData.getValueByName("SubMissionID"); if (mMissionID ==
		 * null) { // @@错误处理 //this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
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
		map.put(tLCGrpIssuePolSet, "UPDATE");
		logger.debug(tLCGrpIssuePolSet.size());
		mResult.add(map);
		mResult.add(mLOPRTManagerSet);

		return true;
	}

	// 准备打印队列
	public boolean preparePrint() {
		String i_sql = "select IssueType from LCGrpIssuePol where Grpcontno = '"
				+ mGrpContNo
				+ "'"
				+ " and backobjtype = '2' and (state = '0' or state is null)";
		ExeSQL i_exesql = new ExeSQL();
		SSRS i_ssrs = new SSRS();
		i_ssrs = i_exesql.execSQL(i_sql);
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
			if (i_ssrs.GetText(j, 1).equals("22")) {
				m = m + 1;
			}
		}
		logger.debug("m:" + m);
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
			mLOPRTManagerSchema.setOtherNo(mGrpContNo);
			logger.debug("mGrpContNo");
			mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_GRPPOL); // 保单号
			mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_GRP_QUEST); // 问题件
			mLOPRTManagerSchema.setManageCom(mLCGrpContSchema.getManageCom());
			mLOPRTManagerSchema.setAgentCode(mLCGrpContSchema.getAgentCode());
			mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
			mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);

			mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
			mLOPRTManagerSchema.setStateFlag("0");
			mLOPRTManagerSchema.setPatchFlag("0");
			mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());

			mLOPRTManagerSchema.setStandbyFlag1(mCustomerNo); // 被保险人编码
			mLOPRTManagerSchema.setStandbyFlag3(mMissionID);
			mLOPRTManagerSchema.setCode("54");
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
				tLOPRTManagerSchema.setOtherNo(mGrpContNo);
				tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_GRPPOL); // 保单号
				tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_GRP_UINT); // 问题件
				tLOPRTManagerSchema.setManageCom(mLCGrpContSchema
						.getManageCom());
				tLOPRTManagerSchema.setAgentCode(mLCGrpContSchema
						.getAgentCode());
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
				tLOPRTManagerSchema.setCode("56");
				tLOPRTManagerSchema.setOldPrtSeq(mPrtSeq);

				mLOPRTManagerSet.add(tLOPRTManagerSchema);
			}
		}
		LCGrpIssuePolDB tLCGrpIssuePolDB = new LCGrpIssuePolDB();
		logger.debug(i_sql);
		String m_sql = "select * from LCGrpIssuePol where Grpcontno = '"
				+ mGrpContNo + "'"
				+ " and backobjtype = '2' and (state = '0' or state is null)";
		tLCGrpIssuePolSet = tLCGrpIssuePolDB.executeQuery(m_sql);
		if (tLCGrpIssuePolSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "UWPewpareIssueBL";
			tError.functionName = "PrintIssue";
			tError.errorMessage = "无需要发放的问题件! ";
			this.mErrors.addOneError(tError);
			return false;
		}
		int j = 0;
		for (int i = 1; i <= issue; i++) {
			logger.debug(tLCGrpIssuePolSet.get(i).getIssueType());
			if (tLCGrpIssuePolSet.get(i).getIssueType().equals("22")) {
				tLCGrpIssuePolSet.get(i).setPrtSeq(PrtSeq22[j]);
				j++;
			} else {
				tLCGrpIssuePolSet.get(i).setPrtSeq(no22PrtSeq);
			}
			tLCGrpIssuePolSet.get(i).setState("1");
		}
		return true;
	}

	// 对问题见表状态的更改
	public boolean prepareIssue() {
		LCGrpIssuePolDB tLCGrpIssuePolDB = new LCGrpIssuePolDB();
		String sql = "select * from lcissuepol where Grpcontno = '"
				+ mGrpContNo + "'"
				+ " and backobjtype = '2' and (state = '0' or state is null)";
		tLCGrpIssuePolSet = tLCGrpIssuePolDB.executeQuery(sql);
		if (tLCGrpIssuePolSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "UWPewpareIssueBL";
			tError.functionName = "PrintIssue";
			tError.errorMessage = "无需要发放的问题件! ";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= tLCGrpIssuePolSet.size(); i++) {
			tLCGrpIssuePolSet.get(1).setPrtSeq(mPrtSeq);
			tLCGrpIssuePolSet.get(i).setState("1");
		}
		return true;
	}

	public boolean checkData() {
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		tLCGrpContDB.setGrpContNo(mGrpContNo);
		tLCGrpContSet = tLCGrpContDB.query();
		if (tLCGrpContSet.size() != 1) {
			CError tError = new CError();
			tError.moduleName = "UWPewpareIssueBL";
			tError.functionName = "CheckData";
			tError.errorMessage = "数据验证出错! ";
			this.mErrors.addOneError(tError);
		}
		// mLCGrpContSchema = tLCGrpContSet.get(1);
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LCGrpContF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) throws Exception {
		GrpUWIssuePolPrintBL tGrpUWIssuePolPrintBL = new GrpUWIssuePolPrintBL();
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ComCode = "86";

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo("140110000000696");
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		tLCGrpContSchema = tLCGrpContDB.getSchema();

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tLCGrpContSchema);

		tGrpUWIssuePolPrintBL.submitData(tVData, "");
	}
}
