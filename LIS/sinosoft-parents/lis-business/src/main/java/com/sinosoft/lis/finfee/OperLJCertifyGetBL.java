/**
 * Copyright (c) 2006 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LJCertifyGetAgentSchema;
import com.sinosoft.lis.schema.LJCertifyGetSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:单证保证金管理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author: wl
 * @version 1.04
 */

public class OperLJCertifyGetBL {
private static Logger logger = Logger.getLogger(OperLJCertifyGetBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private String tLimit = "";
	// **生成的暂交费号
	private String tNo = "";
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private MMap map = new MMap();
	private MMap mmap = new MMap();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private LJCertifyGetSchema mLJCertifyGetSchema = new LJCertifyGetSchema();
	private LJCertifyGetAgentSchema mLJCertifyGetAgentSchema = new LJCertifyGetAgentSchema();
	private TransferData mTransferData = new TransferData();
	private String mGetNoticeNo = "";
	private String ManageCom = "";
	private String mName = "";
	private String mIDNo = "";
	private String mAgentCode = "";
	private String mPolicyCom = "";

	public OperLJCertifyGetBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------OperLJCertifyGetBL Begin----------");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 检查数据合法性
		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("insert");
		if (!tPubSubmit.submitData(mInputData, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJCertifyTempFeeBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (1 == 1) {
			mInputData.clear();
			mInputData.add(mmap);
			if (!tPubSubmit.submitData(mInputData, cOperate)) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "LJCertifyTempFeeBL";
				tError.functionName = "PubSubmit.submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

		}

		mInputData = null;
		logger.debug("----------LJCertifyTempFeeBL End----------");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		logger.debug("---------Go To getInputData----------");

		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLJCertifyGetSchema = (LJCertifyGetSchema) mInputData
				.getObjectByObjectName("LJCertifyGetSchema", 0);
		mLJCertifyGetAgentSchema = (LJCertifyGetAgentSchema) mInputData
				.getObjectByObjectName("LJCertifyGetAgentSchema", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mGetNoticeNo = (String) mTransferData.getValueByName("GetNoticeNoQ"); // 暂收费号
		mName = (String) mTransferData.getValueByName("Name"); // 领取人
		mIDNo = (String) mTransferData.getValueByName("IDNo"); // 领取人身份证号
		mAgentCode = (String) mTransferData.getValueByName("AgentCode"); // 工号
		mPolicyCom = (String) mTransferData.getValueByName("PolicyComQ"); // 管理机构

		if (mLJCertifyGetSchema == null) {
			logger.debug("没有得到输入的数据");
			return false;
		}
		return true;
	}

	/**
	 * 校验传入的信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		logger.debug("---------Go To checkData----------");
		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "OperLJCertifyGetBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "OperLJCertifyGetBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输操作符失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mLJCertifyGetSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "OperLJCertifyGetBL";
			tError.functionName = "checkData";
			tError.errorMessage = "接受的Schema信息全部为空!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("---------Go To dealData----------");

		boolean tReturn = true;

		// 进行暂交费信息的录入
		if (mOperate.equals("CertifyGet||INSERT")) {
			ManageCom = mGlobalInput.ManageCom;
			tLimit = PubFun.getNoLimit(ManageCom);
			if (!CertifyTempFeeInsert()) {
				return false;
			}
		}

		return tReturn;
	}

	/**
	 * 进行单证保证金暂收费表的录入
	 * 
	 * @return
	 */
	private boolean CertifyTempFeeInsert() {
		logger.debug("---------Go To CertifyGetInsert()----------");
		String tCheckSQL = "select confflag from ljcertifytempfee where GetNoticeNo ='?mGetNoticeNo?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tCheckSQL);
		sqlbv.put("mGetNoticeNo", mGetNoticeNo);
		ExeSQL checkExeSQL = new ExeSQL();
		String tconfflag = checkExeSQL.getOneValue(sqlbv);
		if (tconfflag.equals("1") || "1".equals(tconfflag) || tconfflag == "1") {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "OperLJCertifyGetBL";
			tError.functionName = "checkData";
			tError.errorMessage = "该笔保证金暂收费已经退费!";
			this.mErrors.addOneError(tError);
			return false;

		}
		tNo = PubFun1.CreateMaxNo("GETNO", tLimit);
		LJCertifyGetSchema tLJCertifyGetSchema = new LJCertifyGetSchema();
		LJCertifyGetAgentSchema tLJCertifyGetAgentSchema = new LJCertifyGetAgentSchema();
		String tLJCertifyTempFeeSql = "select PayMoney from LJCertifyTempFee where GetNoticeNo = '?mGetNoticeNo?'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tLJCertifyTempFeeSql);
		sqlbv1.put("mGetNoticeNo", mGetNoticeNo);
		ExeSQL ttExeSQL = new ExeSQL();
		String tPayMoney = ttExeSQL.getOneValue(sqlbv1);

		tLJCertifyGetSchema.setSchema(mLJCertifyGetSchema);
		tLJCertifyGetAgentSchema.setSchema(tLJCertifyGetAgentSchema);
		tLJCertifyGetAgentSchema.setActuGetNo(tNo);
		tLJCertifyGetAgentSchema.setFeeFinaType("TF");
		if (mAgentCode == null || mAgentCode.equals("")) {
			tLJCertifyGetAgentSchema.setAgentCode("000000");
		} else {
			tLJCertifyGetAgentSchema.setAgentCode(mAgentCode);
		}
		tLJCertifyGetAgentSchema.setConfDate(CurrentDate);
		tLJCertifyGetAgentSchema.setGetMoney(tPayMoney);
		String tLJCertifyTempFeeSQL = "select Name from ljcertifytempfee where GetNoticeNo = '?mGetNoticeNo?'";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tLJCertifyTempFeeSQL);
		sqlbv2.put("mGetNoticeNo", mGetNoticeNo);
		ExeSQL tttExeSQL = new ExeSQL();
		String tAgentName = tttExeSQL.getOneValue(sqlbv2);
		tLJCertifyGetAgentSchema.setAgentName(tAgentName);
		tLJCertifyGetAgentSchema.setMakeDate(CurrentDate);
		tLJCertifyGetAgentSchema.setMakeTime(CurrentTime);
		tLJCertifyGetAgentSchema.setModifyDate(CurrentDate);
		tLJCertifyGetAgentSchema.setModifyTime(CurrentTime);
		tLJCertifyGetAgentSchema.setOperator(mGlobalInput.Operator);
		map.put(tLJCertifyGetAgentSchema, "INSERT");

		// tLJCertifyGetSchema.setPolicyCom(mPolicyCom);
		tLJCertifyGetSchema.setGetMode("1");
		tLJCertifyGetSchema.setActuGetNo(tNo);
		tLJCertifyGetSchema.setSumGetMoney(tPayMoney);
		tLJCertifyGetSchema.setConfDate(CurrentDate);
		tLJCertifyGetSchema.setShouldDate(CurrentDate);
		tLJCertifyGetSchema.setDrawer(tAgentName);
		tLJCertifyTempFeeSQL = "select IDNO from ljcertifytempfee where GetNoticeNo = '?mGetNoticeNo?'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(tLJCertifyTempFeeSQL);
		sqlbv3.put("mGetNoticeNo", mGetNoticeNo);
		tttExeSQL = new ExeSQL();
		tLJCertifyGetSchema.setDrawerID(tttExeSQL
				.getOneValue(sqlbv3));
		tLJCertifyGetSchema.setAgentCode(mAgentCode);
		tLJCertifyGetSchema.setOperator(mGlobalInput.Operator);
		tLJCertifyGetSchema.setMakeDate(CurrentDate);
		tLJCertifyGetSchema.setMakeTime(CurrentTime);
		tLJCertifyGetSchema.setModifyDate(CurrentDate);
		tLJCertifyGetSchema.setModifyTime(CurrentTime);
		tLJCertifyGetSchema.setLastOperator(mGlobalInput.Operator);
		map.put(tLJCertifyGetSchema, "INSERT");
		String tSQLT = " update LJCertifyTempFee set confflag= '1',confdate='?CurrentDate?' where getnoticeno = '?mGetNoticeNo?' ";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(tSQLT);
		sqlbv4.put("CurrentDate", CurrentDate);
		sqlbv4.put("mGetNoticeNo", mGetNoticeNo);
		mmap.put(sqlbv4, "UPDATE");
		return true;

	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "OperLJCertifyGetBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public String getActuGetNO() {
		return tNo;
	}

	/**
	 * 提供返回前台界面的数据
	 * 
	 * @return
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 用于测试
	 * 
	 * @return
	 */
	public static void main(String[] args) {

	}

}
