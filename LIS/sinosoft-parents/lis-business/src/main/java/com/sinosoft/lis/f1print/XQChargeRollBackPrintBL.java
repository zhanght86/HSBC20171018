package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class XQChargeRollBackPrintBL {
private static Logger logger = Logger.getLogger(XQChargeRollBackPrintBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private GlobalInput tGI = null;
	private String mContNo = "";
	// private String mGetnoticeNo = "";
	private TransferData tTransferData = new TransferData();

	public XQChargeRollBackPrintBL() {

	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("Start new printbillrightbl");
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}
		logger.debug("Start new printbillrightbl");
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		TransferData tTD = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		tGI = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0); // lh加

		if (tTD == null || tGI == null) {
			this.buildError("getInputData", "提取输入数据不足！");
			return false;
		}
		mContNo = (String) tTD.getValueByName("ContNo");
		if (mContNo.equals("null") || mContNo.equals("")) {
			this.buildError("getInputData", "保单信息不全，请联系管理员！");
			return false;
		}

		// mGetnoticeNo = (String) tTD.getValueByName("GetnoticeNo");
		// if(mGetnoticeNo.equals("null") || mGetnoticeNo.equals("") )
		// {
		// this.buildError("getInputData", "通知书号信息不全，请联系管理员！");
		// return false;
		// }
		logger.debug("========缴费通知书号=========");
		return true;

	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "NewPrintBillRightBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private boolean getPrintData() {

		String tSql = "select * from ljaget where othernotype = '9' and otherno = '"
				+ "?mContNo?" + " ' and enteraccdate is null ";
		logger.debug(tSql);
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("mContNo", mContNo);
		LJAGetSchema tLJAGetSchema = new LJAGetSchema();
		LJAGetDB tLJAGetDB = new LJAGetDB();
		LJAGetSet tLJAGetSet = new LJAGetSet();
		tLJAGetSet = tLJAGetDB.executeQuery(sqlbv1);
		if (tLJAGetSet.size() == 0) {
			this.buildError("getInputData", "查无此信息，请确认此保单是否是今天回退！");
			return true;
		}
		tLJAGetSchema = tLJAGetSet.get(1);
		FYDate tFYDate = new FYDate(tLJAGetSchema.getSchema().getMakeDate());// 得到年月日分开的时间
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSql = " select name from laagent where agentcode = '"
				+ "?agentcode?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("agentcode", tLJAGetSchema.getAgentCode());
		tSSRS = tExeSQL.execSQL(sqlbv2);
		String tAgentName = "";
		if (tSSRS.MaxRow != 0) {
			tAgentName = tSSRS.GetText(1, 1);
		}

		/* 区部组* */
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql("select (select name from labranchgroup where managecom ='"
				+ "?managecom?"
				+ "' and branchattr = (select Substr(branchattr,1,4) from labranchgroup where agentgroup = (select branchcode from laagent where agentcode ='"
				+ "?agentcode?"
				+ "' ))), "
				+ " (select name from labranchgroup where managecom ='"
				+ "?managecom?"
				+ "' and branchattr = (select Substr(branchattr,1,7) from labranchgroup where agentgroup = (select branchcode from laagent where agentcode ='"
				+ "?agentcode?"
				+ "' ))), "
				+ "  ((select name from labranchgroup where managecom ='"
				+ "?managecom?"
				+ "' and agentgroup = (select branchcode from laagent where agentcode ='"
				+ "?agentcode?" + "' ))) from dual ");
		sqlbv3.put("managecom", tLJAGetSchema.getManageCom());
		sqlbv3.put("agentcode", tLJAGetSchema.getAgentCode());
		tSSRS = tExeSQL.execSQL(sqlbv3);
		String tAgentManage = tSSRS.GetText(1, 1) + tSSRS.GetText(1, 2)
				+ tSSRS.GetText(1, 3);
		/* 客户姓名* */
		String tAppntName = "";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql("select appntname from lccont where contno = '"
				+ "?mContNo?" + "'");
		sqlbv4.put("mContNo", mContNo);
		tSSRS = tExeSQL.execSQL(sqlbv4);
		if (tSSRS.MaxRow != 0) {
			tAppntName = tSSRS.GetText(1, 1);
		}
		/* 退费机构* */
		String tt = tLJAGetSchema.getManageCom().substring(0, 6);
		String tfManageCom = "";
		tSql = "select name from ldcom where comcode = '" + "?tt?" + "'";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tSql);
		sqlbv5.put("tt", tt);
		tSSRS = tExeSQL.execSQL(sqlbv5);

		if (tSSRS.MaxRow > 0) {
			tfManageCom = tSSRS.GetText(1, 1);
		}
		/* 如果银行交费，设置银行信息* */
		String BankAccNameInfo = "";
		String FeeMode = "";
		String BankAccNoInfo = "";
		String BankInfo = "";
		if (tLJAGetSchema.getPayMode() != null
				&& !tLJAGetSchema.getPayMode().equals("null")
				&& tLJAGetSchema.getPayMode().equals("4")) {
			FeeMode = "银行划款";
			tSql = "select bankname from ldbank where bankcode = '"
					+ "?bankcode?" + "'";
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(tSql);
			sqlbv6.put("bankcode", tLJAGetSchema.getBankCode());
			tSSRS = tExeSQL.execSQL(sqlbv6);
			BankInfo = "开户行：" + BankInfo;
			BankAccNameInfo = "开户名：" + tAppntName;
			BankAccNoInfo = "帐号：" + tLJAGetSchema.getBankAccNo();
		} else {
			FeeMode = "现金";
		}

		String MainRiskIN = "";
		String MainRiskMoney = "";
		tSql = "select sum(sumactupaymoney) from ljapayperson a, lcpol b where a.contno = '"
				+ "?contno?"
				+ "' "
				+ " and a.getnoticeno = '"
				+ "?getnoticeno?"
				+ "' and b.polno = b.mainpolno and a.polno = b.polno "
				+ " and sumactupaymoney > 0 ";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(tSql);
		sqlbv7.put("contno", tLJAGetSchema.getOtherNo());
		sqlbv7.put("getnoticeno", tLJAGetSchema.getGetNoticeNo());
		tSSRS = tExeSQL.execSQL(sqlbv7);
		if (tSSRS.MaxRow > 0) {
			MainRiskMoney = tSSRS.GetText(1, 1);
			tSql = "select b.riskshortname from lcpol a , lmrisk b where a.mainpolno = a.polno  "
					+ " and a.riskcode = b.riskcode and a.contno = '"
					+ "?contno?" + "'";
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(tSql);
			sqlbv8.put("contno", tLJAGetSchema.getOtherNo());
			tSSRS = tExeSQL.execSQL(sqlbv8);
			MainRiskIN = tSSRS.GetText(1, 1);
		}
		SSRS tESSRS = new SSRS();
		SSRS tMSSRS = new SSRS();
		String ExtendINFO = "";
		tSql = "select a.riskcode from ljapayperson a, lcpol b where a.contno = '"
				+ "?contno?"
				+ "' "
				+ " and a.getnoticeno = '"
				+ "?getnoticeno?"
				+ "' and b.polno <> b.mainpolno and a.polno = b.polno "
				+ " and sumactupaymoney > 0   group by a.riskcode ";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(tSql);
		sqlbv9.put("contno", tLJAGetSchema.getOtherNo());
		sqlbv9.put("getnoticeno", tLJAGetSchema.getGetNoticeNo());
		tSSRS = tExeSQL.execSQL(sqlbv9);
		if (tSSRS.MaxRow > 0) {
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
				sqlbv10.sql("select riskshortname from lmrisk where riskcode = '"
						+ "?riskcode?" + "'");
				sqlbv10.put("riskcode", tSSRS.GetText(i, 1));
				tESSRS = tExeSQL.execSQL(sqlbv10);
				ExtendINFO += tESSRS.GetText(1, 1) + " ";

				tSql = "select sum(sumactupaymoney) from ljapayperson a, lcpol b where a.contno = '"
						+ "?contno?"
						+ "' "
						+ " and  a.getnoticeno = '"
						+ "?getnoticeno?"
						+ "' and a.riskcode = '"
						+ "?riskcode?"
						+ "' "
						+ " and b.polno <> b.mainpolno and a.polno = b.polno and a. sumactupaymoney > 0 ";
				SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
				sqlbv11.sql(tSql);
				sqlbv11.put("contno", tLJAGetSchema.getOtherNo());
				sqlbv11.put("getnoticeno", tLJAGetSchema.getGetNoticeNo());
				sqlbv11.put("riskcode", tSSRS.GetText(i, 1));
				tMSSRS = tExeSQL.execSQL(sqlbv11);
				ExtendINFO += "金额：" + tMSSRS.GetText(1, 1) + "  ";

			}
		}

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		texttag.add("GetNoticeNo", tLJAGetSchema.getActuGetNo());
		texttag.add("AppntNameIN", tAppntName);
		texttag.add("UserYear", tFYDate.getYear());
		texttag.add("UserMonth", tFYDate.getMonth());
		texttag.add("UserDay", tFYDate.getDateOfMonth());
		texttag.add("MContNo", mContNo);

		texttag.add("MainRiskIN", MainRiskIN);
		texttag.add("MainRiskMoney", MainRiskMoney);
		texttag.add("ExtendINFO", ExtendINFO);
		texttag.add("SumMoney", new DecimalFormat("0.00").format(tLJAGetSchema
				.getSumGetMoney()));
		texttag.add("ChinaSumMoney", PubFun.getChnMoney(tLJAGetSchema
				.getSumGetMoney()));
		texttag.add("FeeMode", FeeMode);
		texttag.add("BankAccNoInfo", BankAccNoInfo);
		texttag.add("BankInfo", BankInfo);
		texttag.add("BankAccNameInfo", BankAccNameInfo);
		texttag.add("OperatOr", tLJAGetSchema.getOperator());
		texttag.add("ManageComInfo", tfManageCom);
		texttag.add("printDate", PubFun.getCurrentDate());
		texttag.add("AgentName", tAgentName);
		texttag.add("AgentCode", tLJAGetSchema.getAgentCode());
		texttag.add("AgentGroup", tAgentManage);
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("XQRollBackInfromation.vts", "PRINT"); // 最好紧接着就初始化xml文档
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);

		}

		// xmlexport.outputDocumentToFile("e:\\", "ExtendInvoice"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);

		return true;

	}

	public static void main(String arg[])

	{
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo", "NJ020022341000647");
		GlobalInput tGI = new GlobalInput();
		tVData.addElement(tGI);
		tVData.addElement(tTransferData);

		XQChargeRollBackPrintUI tPubUI = new XQChargeRollBackPrintUI();

		if (!tPubUI.submitData(tVData, "PRINT")) {
			logger.debug("shibai");
		} else {
			logger.debug("cehng");
		}

	}

}
