package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author guanwei
 * @version 1.0
 */

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class RNPrePremInvoiceBL {
private static Logger logger = Logger.getLogger(RNPrePremInvoiceBL.class);
	private String mAppntName = "";
	private String mPayDate = "";
	private String mContNo = "";
	private String mTempFeeNo = "";
	private String mPayMoney = "";
	private String mOperator = "";
	private String mPayer = "";
	private String mIDNo = "";

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	private String mSelString = "";

	// 业务处理相关变量
	/** 全局数据 */

	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();
	private boolean tFlag = false;
	private Object text;

	public RNPrePremInvoiceBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		mResult.clear();

		// 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
		}
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

		mAppntName = (String) mTransferData.getValueByName("AppntName"); // 投保人姓名
		logger.debug("|||||||||AppntName||||||||" + mAppntName);
		mPayDate = (String) mTransferData.getValueByName("PayDate"); // 缴费日期
		logger.debug("|||||||||PayDate||||||||" + mPayDate);
		mContNo = (String) mTransferData.getValueByName("ContNo"); // 保单号
		logger.debug("|||||||||ContNo||||||||" + mContNo);
		mTempFeeNo = (String) mTransferData.getValueByName("TempFeeNo"); // 预存号
		logger.debug("|||||||||TempFeeNo||||||||" + mTempFeeNo);
		mPayMoney = (String) mTransferData.getValueByName("PayMoney"); // 缴费金额
		logger.debug("|||||||||PayMoney||||||||" + mPayMoney);
		mOperator = (String) mTransferData.getValueByName("Operator"); // 操作员
		logger.debug("|||||||||Operator||||||||" + mOperator);
		mPayer = (String) mTransferData.getValueByName("Payer"); // 缴费人
		logger.debug("|||||||||Payer||||||||" + mPayer);
		mIDNo = (String) mTransferData.getValueByName("IDNo"); // 身份证号
		logger.debug("|||||||||IDNo||||||||" + mIDNo);
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
		cError.moduleName = "RNPrePremInvoiceBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 打印处理
	 * 
	 * @return boolean
	 */
	private boolean getPrintData() {

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("RNPrePremInvoice.vts", "PRINT"); // 最好紧接着就初始化xml文档

		FYDate tFYDate = new FYDate(mPayDate);
		String tPayDate = tFYDate.getYear() + "年" + tFYDate.getMonth() + "月"
				+ tFYDate.getDateOfMonth() + "日";
		texttag.add("AppntName", mAppntName);
		texttag.add("PayDate", tPayDate);
		texttag.add("ContNo", mContNo);
		texttag.add("TempFeeNo", mTempFeeNo);
		texttag.add("PayMoneyBig", PubFun.getChnMoney(Double
				.parseDouble(mPayMoney)));
		texttag.add("PayMoney", mPayMoney);
		texttag.add("Operator", mOperator);
		texttag.add("PayPerson", mPayer);
		texttag.add("IDNo", mIDNo);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		mResult.addElement(xmlexport);

		return true;
	}

	private void jbInit() throws Exception {
	}
}
