package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDSysVarSchema;
import com.sinosoft.lis.vschema.LYBankBillSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 银行代打发票
 * </p>
 * 
 * <p>
 * Description: 银行代打发票生成数据库文件模块
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author : guanwei
 * 
 * @version 1.0
 */

public class RNProxyInvoiceSendToBankBL {
private static Logger logger = Logger.getLogger(RNProxyInvoiceSendToBankBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	int ErrCount = 0;
	/** 业务处理相关变量 */
	private String mStartDate = "";
	private String mEndDate = "";
	private String mBankCode = "";
	/** 直接与数据库进行连接的变量 */
	private Connection conn = null;
	private Statement st = null;
	private ResultSet rs = null;
	String FileName = "";
	String FilePath = "";
	String TempFileName = "";
	private RNProxyInvoiceSendToBankBLF mRNProxyInvoiceSendToBankBLF = new RNProxyInvoiceSendToBankBLF();
	int temp = 0;

	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();
	private boolean tFlag = false;
	private Object text;

	public RNProxyInvoiceSendToBankBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate)
			throws SQLException {
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		if (!cOperate.equals("INVOICE")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("---End getInputData---");
		mResult.clear();

		// 准备所有要生成的数据
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");
		mResult.add(TempFileName);
		logger.debug("---FileName:" + FileName + "---");
		return true;
	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mStartDate = (String) mTransferData.getValueByName("StartDate"); // 起始日期
		logger.debug("|||||||||StartDate||||||||" + mStartDate);
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 终止日期
		logger.debug("|||||||||mEndDate||||||||" + mEndDate);
		mBankCode = (String) mTransferData.getValueByName("BankCode"); // 银行代码
		logger.debug("|||||||||mBankCode||||||||" + mBankCode);
		return true;
	}

	/**
	 * 获取返回信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
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
		cError.moduleName = "RNProxyInvoiceSendToBankBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private String getFilePath() {
		LDSysVarSchema tLDSysVarSchema = new LDSysVarSchema();
		tLDSysVarSchema.setSysVar("SendToBankFilePath");
		logger.debug("SendToBankFilePath");
		tLDSysVarSchema = tLDSysVarSchema.getDB().query().get(1);
		return tLDSysVarSchema.getSysVarValue();
	}

	/**
	 * 查询得到要发送银行的数据
	 * 
	 * @return boolean
	 */
	private boolean dealData() throws SQLException {

		// 构建文件名，未加后缀名
		// ExeSQL exeSql = new ExeSQL();
		// String bSql = "select BankName from LDBank where BankCode = '" +
		// mBankCode + "'";
		// SSRS tSSRS = exeSql.execSQL(bSql);
		// String tBankName = tSSRS.GetText(1,1);
		FileName = "xh_inv_"
				+ // tBankName + "_" +
				PubFun.getCurrentDate().substring(0, 4)
				+ PubFun.getCurrentDate().substring(5, 7);
		// 构建文件名，添加后缀名
		TempFileName = FileName + ".z";
		logger.debug("FileName return:" + TempFileName);
		FileName = getFilePath() + FileName;
		logger.debug("FileName:" + FileName);
		mTransferData.setNameAndValue("FileName", FileName);

		String tSql = "select * from LYBankBill where BankCode = '" + "?a1?"
				+ "' and BankSuccDate between '" + "?a2?" + "'and '"
				+ "?a3?" + "' "
				+ " and BankPrintCount is null or BankPrintCount < '1'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("a1", mBankCode);
		sqlbv1.put("a2", mStartDate);
		sqlbv1.put("a3", mEndDate);
		logger.debug("tSql=" + tSql);
		// LYBankBillDB tLYBankBillDB = new LYBankBillDB();
		LYBankBillSet tLYBankBillSet = new LYBankBillSet();
		// tLYBankBillSet = tLYBankBillDB.executeQuery(tSql);

		RSWrapper rsWrapper = new RSWrapper();
		if (!rsWrapper.prepareData(tLYBankBillSet, sqlbv1)) {
			this.mErrors.copyAllErrors(rsWrapper.mErrors);
			logger.debug(rsWrapper.mErrors.getFirstError());
			return false;
		}

		/** ********初始化调用生成BLF的类,传入一些基本值,并生成文件头********** */
		mRNProxyInvoiceSendToBankBLF.getInputData(mTransferData, mGlobalInput);
		mRNProxyInvoiceSendToBankBLF.WriterFileHead();
		do {
			logger.debug("Start getData....");
			rsWrapper.getData();
			logger.debug("5000的数据取数");
			dealOneBantchData(tLYBankBillSet);
		} while (tLYBankBillSet.size() > 0);
		rsWrapper.close();
		/** *再次调用BLF中的方法 关闭输出流,并开始生成解析xml,生成文本文件 ** */
		mRNProxyInvoiceSendToBankBLF.WriterFileEnd();
		// 转换xml
		if (!mRNProxyInvoiceSendToBankBLF.xmlTransform()) {
			return false;
		}
		if (ErrCount > 0) {
			CError tError = new CError();
			tError.moduleName = "RNProxyInvoiceSendToBankBL";
			tError.functionName = "saveData";
			tError.errorMessage = "以上错误导致部分数据添加失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return 如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean dealOneBantchData(LYBankBillSet tLYBankBillSet)
			throws SQLException {
		// 给BLF输入需要生成xml的值 return void
		mRNProxyInvoiceSendToBankBLF.setLYBankBillSet(tLYBankBillSet);

		// 生成本次输入数据xml的文件 return boolean
		if (!mRNProxyInvoiceSendToBankBLF.WriterFileBody()) {
			this.mErrors.copyAllErrors(mRNProxyInvoiceSendToBankBLF.mErrors);
			return false;
		}
		return true;
	}

	private void jbInit() throws Exception {
	}
}
