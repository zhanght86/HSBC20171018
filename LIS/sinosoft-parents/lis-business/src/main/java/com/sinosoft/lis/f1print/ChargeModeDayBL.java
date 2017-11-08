package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author gaoht
 * @version 1.0
 */

public class ChargeModeDayBL {
private static Logger logger = Logger.getLogger(ChargeModeDayBL.class);
	String tManageCom = "";
	String tPolicyCom = "";
	String tEndDay = "";
	String tStartDay = "";
	String mOperator = "";
	String mPayType = "";
	double day = 0;
	double month = 0;

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */

	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	public ChargeModeDayBL() {
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
			logger.debug("打印数据时出错！");
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
		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.Operator = "001";
		mGlobalInput.ComCode = "8621";
		mGlobalInput.ManageCom = "8621";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ManageCom", "8621");
		tTransferData.setNameAndValue("PolicyCom", "8621");
		tTransferData.setNameAndValue("StartDay", "2006-03-06");
		tTransferData.setNameAndValue("EndDay", "2006-03-06");
		// tTransferData.setNameAndValue("Operator","001");
		VData tVData = new VData();
		tVData.addElement(tTransferData);
		tVData.addElement(mGlobalInput);
		ChargeModeDayBL tChargeModeDayBL = new ChargeModeDayBL();
		if (!tChargeModeDayBL.submitData(tVData, "PRINT")) {
			logger.debug("Erro!!!!!!!!!!!!!!!!!!");
		}
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
		// 收付机构
		tManageCom = (String) mTransferData.getValueByName("ManageCom");
		if (tManageCom.equals("") || tManageCom == null) {
			return false;
		}
		// 管理机构
		tPolicyCom = (String) mTransferData.getValueByName("PolicyCom");
		if (tPolicyCom.equals("") || tPolicyCom == null) {
			return false;
		}
		// 终止日期
		tEndDay = (String) mTransferData.getValueByName("EndDay");
		// 起始日期
		tStartDay = (String) mTransferData.getValueByName("StartDay");
		// 操作员
		mOperator = (String) mTransferData.getValueByName("Operator");
		// 收付类型
		mPayType = (String) mTransferData.getValueByName("PayType");

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
		cError.moduleName = "LCPolF1PBL";
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
		SSRS nSSRS = new SSRS();

		String tCurrentDate = PubFun.getCurrentDate();
		String tCurrentTime = PubFun.getCurrentTime();

		// 填报机构
		logger.debug("====================填报机构===================");
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String msql = "select name from ldcom where comcode = '"
				+ "?ManageCom?" + "' ";
		sqlbv1.sql(msql);
		sqlbv1.put("ManageCom", mGlobalInput.ManageCom);
		ExeSQL tExeSQL = new ExeSQL();
		nSSRS = tExeSQL.execSQL(sqlbv1);
		String bManageComName = nSSRS.GetText(1, 1);

		// 收付机构
		logger.debug("====================收付机构===================");
		msql = "select name from ldcom where comcode = '" + "?tManageCom?" + "'";
		sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(msql);
		sqlbv1.put("tManageCom", tManageCom);
		ExeSQL rExeSQL = new ExeSQL();
		nSSRS = rExeSQL.execSQL(sqlbv1);
		String sManageComName = nSSRS.GetText(1, 1);

		// 管理机构
		logger.debug("====================管理机构===================");
		msql = "select name from ldcom where comcode = '" + "?tPolicyCom?" + "'";
		sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(msql);
		sqlbv1.put("tPolicyCom", tPolicyCom);
		ExeSQL bExeSQL = new ExeSQL();
		nSSRS = bExeSQL.execSQL(sqlbv1);
		String sPolicyComName = nSSRS.GetText(1, 1);

		// 查询收费类型
		logger.debug("====================查询收费类型===================");
		msql = "select trim(codename) from ldcode where codetype='paytype' and code='"
				+ "?mPayType?" + "'";
		sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(msql);
		sqlbv1.put("mPayType", mPayType);
		rExeSQL = new ExeSQL();
		nSSRS = rExeSQL.execSQL(sqlbv1);
		String PayType = "";
		if (nSSRS.getMaxRow() > 0)
			PayType = nSSRS.GetText(1, 1);
		// 现金日
		logger.debug("====================现金===================");
		msql = "select sum(paymoney) from ljtempfeeclass "
				+ " where 1=1 and EnterAccDate >= '"
				+ "?tStartDay?"
				+ "' "
				+ "   and EnterAccDate <= '"
				+ "?tEndDay?"
				+ "' "
				+ "   and (EnterAccDate is not null or EnterAccDate <>'3000-01-01') "
				+ "   and managecom like concat('" + "?tManageCom?" + "','%')  "
				+ "   and policycom like concat('" + "?tPolicyCom?" + "','%') "
				+ "   and paymode = '1'";
		if (mOperator != null && !mOperator.equals("")
				&& mOperator.length() > 0)
			msql = msql + " and Operator = '" + "?mOperator?" + "'";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql + " and Othernotype = '" + "?mPayType?" + "'";
		sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(msql);
		sqlbv1.put("tStartDay", tStartDay);
		sqlbv1.put("tEndDay", tEndDay);
		sqlbv1.put("tManageCom", tManageCom);
		sqlbv1.put("tPolicyCom", tPolicyCom);
		sqlbv1.put("mOperator", mOperator);
		sqlbv1.put("mPayType", mPayType);
		ExeSQL xExeSQL = new ExeSQL();
		nSSRS = xExeSQL.execSQL(sqlbv1);
		String XianJinDay = "";
		XianJinDay = nSSRS.GetText(1, 1);
		if (XianJinDay.equals("null"))
			XianJinDay = "0";

		// 现金送款簿日
		logger.debug("====================现金送款簿===================");
		msql = "select sum(paymoney) from ljtempfeeclass "
				+ " where paymode = '2'and EnterAccDate >= '"
				+ "?tStartDay?"
				+ "' "
				+ "   and EnterAccDate <= '"
				+ "?tEndDay?"
				+ "' "
				+ "   and managecom like concat('" + "?tManageCom?" + "','%') "
				+ "   and policycom like concat('" + "?tPolicyCom?" + "','%') "
				+ "   and (EnterAccDate is not null or EnterAccDate <>'3000-01-01')";
		if (mOperator != null && mOperator.length() > 0)
			msql = msql + " and Operator = '" + "?mOperator?" + "'";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0) {
			if (mPayType.equals("04")) {
				msql = msql
						+ " And Exists (Select Tempfeeno From Ljtempfee Where Tempfeeno = Ljtempfeeclass.Tempfeeno And Othernotype = '"
						+ "?mPayType?" + "') ";
			} else {
				msql = msql + " and Othernotype = '" + "?mPayType?" + "'";
			}
		}
		sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(msql);
		sqlbv1.put("tStartDay", tStartDay);
		sqlbv1.put("tEndDay", tEndDay);
		sqlbv1.put("tManageCom", tManageCom);
		sqlbv1.put("tPolicyCom", tPolicyCom);
		sqlbv1.put("mOperator", mOperator);
		sqlbv1.put("mPayType", mPayType);
		
		ExeSQL vExeSQL = new ExeSQL();
		nSSRS = vExeSQL.execSQL(sqlbv1);
		String XianJinBenDay = "";
		XianJinBenDay = nSSRS.GetText(1, 1);
		if (XianJinBenDay.equals("null"))
			XianJinBenDay = "0";

		// 支票日
		logger.debug("====================支票===================");
		msql = "select sum(paymoney) from ljtempfeeclass "
				+ " where paymode = '3' and EnterAccDate >= '"
				+ "?tStartDay?"
				+ "' "
				+ "   and EnterAccDate <= '"
				+ "?tEndDay?"
				+ "' "
				+ "   and managecom like concat('"	+ "?tManageCom?"+ "','%') "
				+ "   and policycom like concat('"	+ "?tPolicyCom?"+ "','%') "
				+ "   and (EnterAccDate is not null or EnterAccDate <>'3000-01-01')";
		if (mOperator != null && mOperator != "")
			msql = msql + " and Operator = '" + "?mOperator?" + "'";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql + " and Othernotype = '" + "?mPayType?" + "'";
		sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(msql);
		sqlbv1.put("tStartDay", tStartDay);
		sqlbv1.put("tEndDay", tEndDay);
		sqlbv1.put("tManageCom", tManageCom);
		sqlbv1.put("tPolicyCom", tPolicyCom);
		sqlbv1.put("mOperator", mOperator);
		sqlbv1.put("mPayType", mPayType);
		ExeSQL nExeSQL = new ExeSQL();
		nSSRS = nExeSQL.execSQL(sqlbv1);
		String CheQueDay = "";
		CheQueDay = nSSRS.GetText(1, 1);
		if (CheQueDay.equals("null"))
			CheQueDay = "0";

		// 银行转账日
		logger.debug("====================银行转账===================");
		msql = "select sum(paymoney) from ljtempfeeclass "
				+ " where paymode = '4' and EnterAccDate >= '"
				+ "?tStartDay?"
				+ "' "
				+ "   and EnterAccDate <= '"
				+ "?tEndDay?"
				+ "' "
				+ "   and managecom like concat('"	+ "?tManageCom?"+ "','%') "
				+ "   and policycom like concat('"	+ "?tPolicyCom?"+ "','%') "
				+ "   and (EnterAccDate is not null or EnterAccDate <>'3000-01-01')";
		if (mOperator != null && mOperator != "")
			msql = msql + " and Operator = '" + "?mOperator?" + "'";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql + " and Othernotype = '" + "?mPayType?" + "'";
		sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(msql);
		sqlbv1.put("tStartDay", tStartDay);
		sqlbv1.put("tEndDay", tEndDay);
		sqlbv1.put("tManageCom", tManageCom);
		sqlbv1.put("tPolicyCom", tPolicyCom);
		sqlbv1.put("mOperator", mOperator);
		sqlbv1.put("mPayType", mPayType);
		ExeSQL sExeSQL = new ExeSQL();
		nSSRS = sExeSQL.execSQL(sqlbv1);
		String BankPayDay = "";
		BankPayDay = nSSRS.GetText(1, 1);
		if (BankPayDay.equals("null"))
			BankPayDay = "0";

		// 内部转帐
		logger.debug("====================内部转帐===================");
		msql = "select sum(paymoney) from ljtempfeeclass "
				+ " where paymode = '5' and EnterAccDate >= '"
				+ "?tStartDay?"
				+ "' "
				+ "   and EnterAccDate <= '"
				+ "?tEndDay?"
				+ "' "
				+ "   and managecom like concat('"+ "?tManageCom?"+ "','%') "
				+ "   and policycom like concat('"+ "?tPolicyCom?"+ "','%') "
				+ "   and (EnterAccDate is not null or EnterAccDate <>'3000-01-01')";
		if (mOperator != null && mOperator != "")
			msql = msql + " and Operator = '" + "?mOperator?" + "'";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql + " and Othernotype = '" + "?mPayType?" + "'";
		sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(msql);
		sqlbv1.put("tStartDay", tStartDay);
		sqlbv1.put("tEndDay", tEndDay);
		sqlbv1.put("tManageCom", tManageCom);
		sqlbv1.put("tPolicyCom", tPolicyCom);
		sqlbv1.put("mOperator", mOperator);
		sqlbv1.put("mPayType", mPayType);
		ExeSQL wExeSQL = new ExeSQL();
		nSSRS = wExeSQL.execSQL(sqlbv1);
		String NBZZDay = "";
		NBZZDay = nSSRS.GetText(1, 1);
		if (NBZZDay.equals("null"))
			NBZZDay = "0";

		// 银行代扣日
		logger.debug("====================银行代扣===================");
		msql = "select sum(paymoney) from ljtempfeeclass "
				+ " where paymode = '7' and EnterAccDate >= '"
				+ "?tStartDay?"
				+ "' "
				+ "   and EnterAccDate <= '"
				+ "?tEndDay?"
				+ "' "
				+ "   and managecom like concat('"+ "?tManageCom?"+ "','%') "
				+ "   and policycom like concat('"+ "?tPolicyCom?"+ "','%') "
				+ "   and (EnterAccDate is not null or EnterAccDate <>'3000-01-01')";
		if (mOperator != null && mOperator != "")
			msql = msql + " and Operator = '" + "?mOperator?" + "'";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql + " and Othernotype = '" + "?mPayType?" + "'";
		sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(msql);
		sqlbv1.put("tStartDay", tStartDay);
		sqlbv1.put("tEndDay", tEndDay);
		sqlbv1.put("tManageCom", tManageCom);
		sqlbv1.put("tPolicyCom", tPolicyCom);
		sqlbv1.put("mOperator", mOperator);
		sqlbv1.put("mPayType", mPayType);
		ExeSQL fExeSQL = new ExeSQL();
		nSSRS = fExeSQL.execSQL(sqlbv1);
		String BankToDay = "";
		BankToDay = nSSRS.GetText(1, 1);
		if (BankToDay.equals("null"))
			BankToDay = "0";

		// 网上银行日
		logger.debug("====================网上银行===================");
		msql = "select sum(paymoney) from ljtempfeeclass "
				+ " where paymode = '9' and EnterAccDate >= '"
				+ "?tStartDay?"
				+ "' "
				+ "   and EnterAccDate <= '"
				+ "?tEndDay?"
				+ "' "
				+ "   and managecom like concat('"+ "?tManageCom?"+ "','%') "
				+ "   and policycom like concat('"+ "?tPolicyCom?"+ "','%') "
				+ "   and (EnterAccDate is not null or EnterAccDate <>'3000-01-01')";
		if (mOperator != null && mOperator != "")
			msql = msql + " and Operator = '" + "?mOperator?" + "'";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql + " and Othernotype = '" + "?mPayType?" + "'";
		sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(msql);
		sqlbv1.put("tStartDay", tStartDay);
		sqlbv1.put("tEndDay", tEndDay);
		sqlbv1.put("tManageCom", tManageCom);
		sqlbv1.put("tPolicyCom", tPolicyCom);
		sqlbv1.put("mOperator", mOperator);
		sqlbv1.put("mPayType", mPayType);
		ExeSQL hExeSQL = new ExeSQL();
		nSSRS = hExeSQL.execSQL(sqlbv1);
		String NetBankDay = "";
		NetBankDay = nSSRS.GetText(1, 1);
		if (NetBankDay.equals("null"))
			NetBankDay = "0";

		// POS收款日
		logger.debug("====================POS收款===================");
		msql = "select sum(paymoney) from ljtempfeeclass "
				+ " where paymode = '6' and EnterAccDate >= '"
				+ "?tStartDay?"
				+ "' "
				+ "   and EnterAccDate <= '"
				+ "?tEndDay?"
				+ "' "
				+ "   and managecom like concat('"+ "?tManageCom?" + "','%') "
				+ "   and policycom like concat('"+ "?tPolicyCom?" + "','%') "
				+ "   and (EnterAccDate is not null or EnterAccDate <>'3000-01-01')";
		if (mOperator != null && mOperator != "")
			msql = msql + " and Operator = '" + "?mOperator?" + "'";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql + " and Othernotype = '" + "?mPayType?" + "'";
		sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(msql);
		sqlbv1.put("tStartDay", tStartDay);
		sqlbv1.put("tEndDay", tEndDay);
		sqlbv1.put("tManageCom", tManageCom);
		sqlbv1.put("tPolicyCom", tPolicyCom);
		sqlbv1.put("mOperator", mOperator);
		sqlbv1.put("mPayType", mPayType);
		ExeSQL kExeSQL = new ExeSQL();
		nSSRS = kExeSQL.execSQL(sqlbv1);
		String PosPayDay = "";
		PosPayDay = nSSRS.GetText(1, 1);
		if (PosPayDay.equals("null"))
			PosPayDay = "0";

		// 邮政业务日
		logger.debug("====================邮政业务===================");
		msql = "select sum(paymoney) from ljtempfeeclass "
				+ " where paymode = '8' and EnterAccDate >= '"
				+ "?tStartDay?"
				+ "' "
				+ "   and EnterAccDate <= '"
				+ "?tEndDay?"
				+ "' "
				+ "   and managecom like concat('"+ "?tManageCom?"+ "','%') "
				+ "   and policycom like concat('"+ "?tPolicyCom?"+ "','%') "
				+ "   and (EnterAccDate is not null or EnterAccDate <>'3000-01-01')";
		if (mOperator != null && mOperator != "")
			msql = msql + " and Operator = '" + "?mOperator?" + "'";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql + " and Othernotype = '" + "?mPayType?" + "'";
		sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(msql);
		sqlbv1.put("tStartDay", tStartDay);
		sqlbv1.put("tEndDay", tEndDay);
		sqlbv1.put("tManageCom", tManageCom);
		sqlbv1.put("tPolicyCom", tPolicyCom);
		sqlbv1.put("mOperator", mOperator);
		sqlbv1.put("mPayType", mPayType);
		ExeSQL qExeSQL = new ExeSQL();
		nSSRS = qExeSQL.execSQL(sqlbv1);
		String PortPayDay = "";

		PortPayDay = nSSRS.GetText(1, 1);
		if (PortPayDay.equals("null"))
			PortPayDay = "0";

		logger.debug("====================0老系统补录===================");
		msql = "select sum(paymoney) from ljtempfeeclass "
				+ " where paymode = '0' and EnterAccDate >= '"
				+ "?tStartDay?"
				+ "' "
				+ "   and EnterAccDate <= '"
				+ "?tEndDay?"
				+ "' "
				+ "   and managecom like concat('"+ "?tManageCom?"+ "','%') "
				+ "   and policycom like concat('"+ "?tPolicyCom?"+ "','%') "
				+ "   and (EnterAccDate is not null or EnterAccDate <>'3000-01-01')";
		if (mOperator != null && mOperator != "")
			msql = msql + " and Operator = '" + "?mOperator?" + "'";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql + " and Othernotype = '" + "?mPayType?" + "'";
		sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(msql);
		sqlbv1.put("tStartDay", tStartDay);
		sqlbv1.put("tEndDay", tEndDay);
		sqlbv1.put("tManageCom", tManageCom);
		sqlbv1.put("tPolicyCom", tPolicyCom);
		sqlbv1.put("mOperator", mOperator);
		sqlbv1.put("mPayType", mPayType);
		ExeSQL yExeSQL = new ExeSQL();
		nSSRS = yExeSQL.execSQL(sqlbv1);
		String OldpayDay = "";
		OldpayDay = nSSRS.GetText(1, 1);
		if (OldpayDay.equals("null"))
			OldpayDay = "0";

		// 银保通日
		logger.debug("====================银保通===================");
		msql = "select sum(paymoney) from ljtempfeeclass "
				+ " where paymode = 'A' and EnterAccDate >= '"
				+ "?tStartDay?"
				+ "' "
				+ "   and EnterAccDate <= '"
				+ "?tEndDay?"
				+ "' "
				+ "   and managecom like concat('"+ "?tManageCom?"+ "','%') "
				+ "   and policycom like concat('"+ "?tPolicyCom?"+ "','%') "
				+ "   and (EnterAccDate is not null or EnterAccDate <>'3000-01-01')";
		if (mOperator != null && mOperator != "")
			msql = msql + " and Operator = '" + "?mOperator?" + "'";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql + " and Othernotype = '" + "?mPayType?" + "'";
		sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(msql);
		sqlbv1.put("tStartDay", tStartDay);
		sqlbv1.put("tEndDay", tEndDay);
		sqlbv1.put("tManageCom", tManageCom);
		sqlbv1.put("tPolicyCom", tPolicyCom);
		sqlbv1.put("mOperator", mOperator);
		sqlbv1.put("mPayType", mPayType);
		nSSRS = yExeSQL.execSQL(sqlbv1);
		String YinBaoDay = "";
		YinBaoDay = nSSRS.GetText(1, 1);
		if (YinBaoDay.equals("null"))
			YinBaoDay = "0";

		// 续期冲正
		logger.debug("====================续期冲正===================");
		msql = "select sum(paymoney) from ljtempfeeclass "
				+ " where paymode = 'C' and EnterAccDate >= '"
				+ "?tStartDay?"
				+ "' "
				+ "   and EnterAccDate <= '"
				+ "?tEndDay?"
				+ "' "
				+ "   and managecom like concat('"+ "?tManageCom?"+ "','%') "
				+ "   and policycom like concat('"+ "?tPolicyCom?"+ "','%') "
				+ "   and (EnterAccDate is not null or EnterAccDate <>'3000-01-01')";
		if (mOperator != null && mOperator != "")
			msql = msql + " and Operator = '" + "?mOperator?" + "'";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql + " and Othernotype = '" + "?mPayType?" + "'";
		sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(msql);
		sqlbv1.put("tStartDay", tStartDay);
		sqlbv1.put("tEndDay", tEndDay);
		sqlbv1.put("tManageCom", tManageCom);
		sqlbv1.put("tPolicyCom", tPolicyCom);
		sqlbv1.put("mOperator", mOperator);
		sqlbv1.put("mPayType", mPayType);
		nSSRS = yExeSQL.execSQL(sqlbv1);
		String RNCZ = "";
		RNCZ = nSSRS.GetText(1, 1);
		if (RNCZ.equals("null"))
			RNCZ = "0";

		// 日总计
		msql = "select sum(paymoney) from ljtempfeeclass "
				+ " where EnterAccDate >= '"
				+ "?tStartDay?"
				+ "' "
				+ "   and EnterAccDate <= '"
				+ "?tEndDay?"
				+ "' "
				+ "   and managecom like concat('"+ "?tManageCom?"+ "','%') "
				+ "   and policycom like concat('"+ "?tPolicyCom?"+ "','%') "
				+ "   and (EnterAccDate is not null or EnterAccDate <>'3000-01-01')";
		if (mOperator != null && mOperator != "")
			msql = msql + " and Operator = '" + "?mOperator?" + "'";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql + " and Othernotype = '" + "?mPayType?" + "'";
		sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(msql);
		sqlbv1.put("tStartDay", tStartDay);
		sqlbv1.put("tEndDay", tEndDay);
		sqlbv1.put("tManageCom", tManageCom);
		sqlbv1.put("tPolicyCom", tPolicyCom);
		sqlbv1.put("mOperator", mOperator);
		sqlbv1.put("mPayType", mPayType);
		ExeSQL pExeSQL = new ExeSQL();
		nSSRS = pExeSQL.execSQL(sqlbv1);
		String ProvideCorpus = "";
		ProvideCorpus = nSSRS.GetText(1, 1);
		if (ProvideCorpus.equals("null"))
			ProvideCorpus = "0";

		/** ------------------取操作员姓名--------------------------* */
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String OperSql = "select username from lduser where trim(usercode) = '"
				+ "?Operator?" + "'";
		sqlbv.sql(OperSql);
		sqlbv.put("Operator", mGlobalInput.Operator);
		logger.debug("OperSql===========" + OperSql);

		ExeSQL oExeSQL = new ExeSQL();
		nSSRS = oExeSQL.execSQL(sqlbv);
		String moperator = nSSRS.GetText(1, 1);

		String mDate = tStartDay + "至" + tEndDay;
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("ChargeModeDay.vts", "PRINT"); // 最好紧接着就初始化xml文档
		texttag.add("ProvideCorpus", ProvideCorpus);
		texttag.add("XianJinDay", XianJinDay);

		texttag.add("XianJinBenDay", XianJinBenDay);

		texttag.add("CheQueDay", CheQueDay);

		texttag.add("NBZZDay", NBZZDay);
		texttag.add("RNCZ", RNCZ);
		texttag.add("BankPayDay", BankPayDay);

		texttag.add("BankToDay", BankToDay);

		texttag.add("NetBankDay", NetBankDay);

		texttag.add("PosPayDay", PosPayDay);

		texttag.add("PortPayDay", PortPayDay);

		texttag.add("PayType", PayType);

		texttag.add("sManageCom", tManageCom);
		texttag.add("ManageCom", mGlobalInput.ManageCom);
		texttag.add("sManageComName", sManageComName);
		texttag.add("sPolicyComName", sPolicyComName);
		texttag.add("ManageComName", bManageComName);
		texttag.add("CurrentDate", tCurrentDate);
		texttag.add("CurrentTime", tCurrentTime);
		texttag.add("Date", mDate);

		texttag.add("OldpayDay", OldpayDay);
		texttag.add("YinBaoDay", YinBaoDay);

		texttag.add("Operator", moperator);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		// xmlexport.outputDocumentToFile("e:\\", "atest"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}
}
