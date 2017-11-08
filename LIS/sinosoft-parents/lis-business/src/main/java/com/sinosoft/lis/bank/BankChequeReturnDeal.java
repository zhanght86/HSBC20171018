package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 工行代打发票返回处理
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author Ght
 * @version 1.0
 */
public class BankChequeReturnDeal {
private static Logger logger = Logger.getLogger(BankChequeReturnDeal.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	public BankChequeReturnDeal() {
	}

	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private String tCurrentDate = PubFun.getCurrentDate();
	private String tCurrentTime = PubFun.getCurrentTime();
	// private String mStartDate = "";
	// private String mEndDate = "";
	private String mBankCode = "";
	private SQLwithBindVariables mLOPrtManagerSql ;
	private SQLwithBindVariables mLYBankBillSql ;
	private SQLwithBindVariables mDelLYBankUploadSql ;
	private SQLwithBindVariables mLYBankUploadBSql ;

	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 数据校验
		if (!checkData()) {
			return false;
		}
		logger.debug("----------------Start  dealData...");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("-----------------dealData successful!");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("-------------------Start  Submit...");
		// 提交数据

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "DifSetBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("成功！！！return true");
		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		logger.debug("End Get GlobalInput");

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mBankCode = (String) mTransferData.getValueByName("BankCode");
		logger.debug("End Get BankCode,BankCode:" + mBankCode);
		if (mBankCode == null || mBankCode.equals("")) {
			logger.debug("=====没有得到银行代码=======");
			buildError("getInputData", "没有得到银行代码！");
			return false;
		}
		logger.debug("End Get InputData");
		return true;
	}

	private boolean checkData() {
		if (mBankCode == null || mBankCode.equals("")) {
			buildError("checkData", "没有得到银行代码！");
			return false;
		}
		return true;
	}

	private boolean dealData() {
		String tSql = "select * from LYBankUpload where trim(bankcode)='?mBankCode?'";
		logger.debug("查询LYBankUpload：" + tSql);
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql("delete from LYBankUpload where trim(bankcode) = '?mBankCode?'");
		sqlbv2.put("mBankCode", mBankCode);
		mDelLYBankUploadSql = sqlbv2;
		logger.debug("删除LYBankUpload：" + mDelLYBankUploadSql);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql("select Bankprintcount from LYBankBill where SerialNo in "
				+ "(select SerialNo from LYBankUpload "
				+ " where BankCode = '?mBankCode?')");
		sqlbv.put("mBankCode", mBankCode);
		tSSRS = tExeSQL
				.execSQL(sqlbv);
		String tBankCountPrint = tSSRS.GetText(1, 1);
		logger.debug("tBankCountPrint" + tBankCountPrint);
		if (tBankCountPrint == null || tBankCountPrint.equals("")
				|| tBankCountPrint.equals("0")) {
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql("update LYBankBill set BankPrintCount = 1 , ReturnFromBankDate = '?tCurrentDate?'"
					+ "where Serialno in (select Serialno from LYBankUpload "
					+ " where trim(BankCode) = '?mBankCode?')");
			sqlbv1.put("tCurrentDate", tCurrentDate);
			sqlbv1.put("mBankCode", mBankCode);
			mLYBankBillSql = sqlbv1;
		} else {
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql("update LYBankBill set Bankprintcount = Bankprintcount + 1 "
					+ "where Serialno in (select Serialno from LYBankUpload "
					+ " where trim(BankCode) = '?mBankCode?')");
			sqlbv1.put("mBankCode", mBankCode);
			mLYBankBillSql = sqlbv1;
		}
		logger.debug("更新LYBankBill:" + mLYBankBillSql);
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql("insert into LYBankUploadB " + tSql);
		sqlbv3.put("mBankCode", mBankCode);
		mLYBankUploadBSql = sqlbv3;
		logger.debug("插入LYBankUploadB：" + mLYBankUploadBSql);
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql("update LOPrtManager set stateflag='1' "
				+ "where code='32' and StandbyFlag1 in "
				+ "(select getnoticeno from LYBankBill "
				+ " where serialno in (select SerialNo from LYBankUpload "
				+ " where BankCode = '?mBankCode?'))");
		sqlbv4.put("mBankCode", mBankCode);
		mLOPrtManagerSql = sqlbv4;
		logger.debug("更新LOPrtManager:" + mLOPrtManagerSql);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();
		map.put(mLYBankBillSql, "UPDATE");
		map.put(mLYBankUploadBSql, "INSERT");
		map.put(mLOPrtManagerSql, "UPDATE");
		map.put(mDelLYBankUploadSql, "DELETE");
		mResult.add(map);
		return true;
	}

	/**
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "BankChequeReturnDeal";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		VData tVData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("BankCode", "2100010");

		tVData.add(tGlobalInput);
		tVData.add(tTransferData);
		BankChequeReturnDeal tBankChequeReturnDeal = new BankChequeReturnDeal();
		if (!tBankChequeReturnDeal.submitData(tVData, "")) {
			logger.debug("no");
		} else {
			logger.debug("ok");
		}

	}
}
