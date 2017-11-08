package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 核保记事本</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class UWPrepareIssueBL {
private static Logger logger = Logger.getLogger(UWPrepareIssueBL.class);
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
	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mCustomerNo;
	private String mMissionID;
	private String mSubMissionID;
	private String mPrtSeq;

	/** 打印管理表 */
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	/** 问题件表 */
	private LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

	/** 合同数据* */
	private LCContSchema mLCContSchema = new LCContSchema();

	public UWPrepareIssueBL() {

	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		// 进行业务处理
		if (!checkData()) {
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		if (!prepareOutputData()) {
			return false;
		}
		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
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
			tError.moduleName = "UWAutoHealthAfterInitService";
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
			tError.moduleName = "UWAutoHealthAfterInitService";
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
			tError.moduleName = "UWAutoHealthAfterInitService";
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
			tError.moduleName = "UWAutoHealthAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中ContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);

		mPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit);

		if (preparePrint() == false)
			return false;
		if (prepareIssue() == false) {
			return false;
		}
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return 如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		if (mLOPRTManagerSchema != null) {
			mResult.add(mLOPRTManagerSchema);
		}
		if (tLCIssuePolSet.size() != 0) {
			mResult.add(tLCIssuePolSet);
		}
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	// 准备打印队列
	public boolean preparePrint() {

		// 准备打印管理表数据
		mLOPRTManagerSchema.setPrtSeq(mPrtSeq);
		mLOPRTManagerSchema.setOtherNo(mContNo);
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
		mLOPRTManagerSchema.setOldPrtSeq(mPrtSeq);

		return true;
	}

	// 对问题见表状态的更改
	public boolean prepareIssue() {
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		String sql = "select * from lcissuepol where contno = '" + "?contno?"
				+ "'"
				+ " and backobjtype = '3' and (state = '0' or state is null)";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("contno", mContNo);
		tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv);
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

	/**
	 * 对输入数据进行验证
	 */
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
		mLCContSchema = tLCContSet.get(1);
		return true;
	}
}
