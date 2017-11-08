package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class PrintCustomerBillBPBL implements PrintService {
private static Logger logger = Logger.getLogger(PrintCustomerBillBPBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
	// private VData vData = new VData();
	private TransferData mTransferData = new TransferData();
	String mChannelType = null;
	String mManageCom = null;
	String mStartDate = null;
	String mEndDate = null;
	String mAccType = null;

	public PrintCustomerBillBPBL() {
	}

	/**
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("cOperate:" + cOperate);
		if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")
				&& !cOperate.equals("BATCHCONFIRM")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		boolean isSuccess = true;
		XmlExport tXmlExport = new XmlExport();
		VData tResult = null;
		XmlExport txmlExport1 = null;
		ExeSQL exeSql = new ExeSQL();
		SSRS testSSRS = null;
		String sql = "select a.CustomerNo,b.InsuAccNo from ldperson a ,lccustomeracc b , lccont c where a.customerno=b.customerno  and a.customerno=c.appntno "
				+ " and c.SaleChnl= '"
				+ "?mChannelType?"
				+ "' and c.ManageCom like concat('" + "?mManageCom?" + "','%')";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("mChannelType", mChannelType);
		sqlbv.put("mManageCom", mManageCom);
		testSSRS = exeSql.execSQL(sqlbv);

		if (testSSRS.MaxRow == 0) {
			buildError("submitData",
					"tPrintCustomerBillBL发生错误，要打印的客户帐户对帐清单没有数据!");
			return false;
		}
		for (int j = 1; j <= testSSRS.MaxRow; j++) {
			String tCustomerNo = testSSRS.GetText(j, 1);
			String tInsuAccNo = testSSRS.GetText(j, 2);
			VData vData = new VData();
			vData.add(mAccType);
			vData.add(tInsuAccNo);
			vData.add(tCustomerNo);
			vData.add(mStartDate);
			vData.add(mEndDate);

			PrintCustomerBillBL tPrintCustomerBillBL = new PrintCustomerBillBL();

			if (!tPrintCustomerBillBL.submitData(vData, "PRINT")) {
				buildError("submitData", "tPrintCustomerBillBL 客户代码:"
						+ tCustomerNo + "发生错误，但是没有提供详细的出错信息");
				isSuccess = false;
				break;
			}
			tResult = tPrintCustomerBillBL.getResult();
			txmlExport1 = (XmlExport) tResult.getObjectByObjectName(
					"XmlExport", 0);
			tXmlExport.createDocuments("printer", mGlobalInput);
			tXmlExport.setTemplateName(tXmlExport.getDocument()
					.getRootElement(), txmlExport1.getDocument().getRootElement());
			tXmlExport.addDataSet(tXmlExport.getDocument().getRootElement(),
					txmlExport1.getDocument().getRootElement());

		}
		mResult.add(tXmlExport);

		return isSuccess;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mGlobalInput == null || mTransferData == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		mChannelType = (String) mTransferData.getValueByName("ChannelType");
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		mStartDate = (String) mTransferData.getValueByName("StartDate");
		mEndDate = (String) mTransferData.getValueByName("EndDate");
		mAccType = (String) mTransferData.getValueByName("AccType");

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "PrintCustomerBillBPBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

}
