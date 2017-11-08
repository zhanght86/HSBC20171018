package com.sinosoft.lis.f1print;
import java.util.Enumeration;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LJFIGetDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LJFIGetSchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.vschema.LJFIGetSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LMRiskAppSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author hezy lys
 * @version 1.0
 */

public class FinDayCheckModeBL {
private static Logger logger = Logger.getLogger(FinDayCheckModeBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	// 取得的时间
	private String mDay[] = null;
	// 业务处理相关变量
	private String mPayMode;
	private String mBankOrCheckNo;
	private LMRiskAppSet mLMRiskAppSet;
	private String mPayModeName = "";

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public FinDayCheckModeBL() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINTGET") && !cOperate.equals("PRINTPAY")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}
		if (!getInputData(cInputData))
			return false;
		mResult.clear();
		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		this.mLMRiskAppSet = tLMRiskAppDB.query();
		// 准备所有要打印的数据
		if (cOperate.equals("PRINTGET")) // 打印付费
		{
			if (!getPrintDataGet()) {
				return false;
			}
		}

		if (cOperate.equals("PRINTPAY")) // 打印暂收费
		{
			if (!getPrintDataPay()) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "8611";
		VData vData = new VData();
		String[] tDay = new String[2];
		tDay[0] = "2006-07-20";
		tDay[1] = "2006-07-20";
		vData.addElement(tDay);
		vData.addElement(tG);

		FinDayCheckModeBL tF = new FinDayCheckModeBL();
		tF.submitData(vData, "PRINTPAY");
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) // 打印付费
	{
		// 全局变量
		mDay = (String[]) cInputData.get(0);
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "FinDayCheckBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private boolean getPrintDataPay() {
		XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
		xmlexport.createDocument("FinDayCheckModePay.vts", "printer");// 最好紧接着就初始化xml文档
		String strArr[] = null;

		// add by hezy 加载数据
		FinDayTool tFinDayTool = new FinDayTool();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String tSql = "select * from LJTempFeeClass b where ConfMakeDate>='"
				+ "?date1?"
				+ "' and ConfMakeDate<='"
				+ "?date2?"
				+ "' and ManageCom like concat('"
				+ "?ManageCom?"
				+ "','%') and (paymode <> '4' or (paymode = '4' "
				+ "and exists(select 1 from ldbank where bankcode = b.bankcode "
				+ "and rpad(trim(comcode),6,'00') = substr(b.managecom,1,6))))";
		logger.debug(tSql);
		sqlbv1.sql(tSql);
		sqlbv1.put("date1", mDay[0]);
		sqlbv1.put("date2", mDay[1]);
		sqlbv1.put("ManageCom", mGlobalInput.ManageCom);
		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();// -寿险业务-暂收分类表
		LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB
				.executeQuery(sqlbv1);
		for (int i = 1; i <= tLJTempFeeClassSet.size(); i++) {
			this.initCommon(); // 初始化成员变量
			LJTempFeeClassSchema tLJTempFeeClassSchema = tLJTempFeeClassSet
					.get(i);
			String tRow[] = new String[2];
			String tColumn[] = new String[0];
			tRow[0] = tLJTempFeeClassSchema.getPayMode(); // 方式
			int tPayMode = Integer.parseInt(tLJTempFeeClassSchema.getPayMode());
			String tAddColumn = "";
			switch (tPayMode) {
			case 1: // 现金
				tAddColumn = "$1";
				break;
			case 2: // 现金支票
				tAddColumn = tLJTempFeeClassSchema.getChequeNo();
				break;
			case 3: // 转账支票
				tAddColumn = tLJTempFeeClassSchema.getChequeNo();
				break;
			case 4: // 银行转账
				tAddColumn = tLJTempFeeClassSchema.getBankCode();
				break;
			case 5: // 内部转账
				tAddColumn = "$5";
				break;
			case 6: // 银行托收
				tAddColumn = tLJTempFeeClassSchema.getBankCode();
				break;
			case 7: // 信用卡
				tAddColumn = "$7";
				break;
			case 8: // 其他
				tAddColumn = "$8";
				break;
			}

			tRow[1] = tAddColumn; // 银行名称或支票号码
			FinCheckKey tF = new FinCheckKey(tRow, tColumn);
			String tPayMoney = String.valueOf(tLJTempFeeClassSchema
					.getPayMoney());
			tFinDayTool.enterBasicInfo(tF, tPayMoney);
		}
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		tSql = "select * from LJTempFeeClass b where ConfMakeDate>='"
				+ "?date1?"
				+ "' and ConfMakeDate<='"
				+ "?date2?"
				+ "' and ManageCom like concat('"
				+ "?ManageCom?"
				+ "','%') and paymode = '4' "
				+ "and exists(select 1 from ldbank where bankcode = b.bankcode "
				+ "and rpad(trim(comcode),6,'00') <> substr(b.managecom,1,6))";
		sqlbv2.sql(tSql);
		sqlbv2.put("date1", mDay[0]);
		sqlbv2.put("date2", mDay[1]);
		sqlbv2.put("ManageCom", mGlobalInput.ManageCom);
		tLJTempFeeClassDB = new LJTempFeeClassDB();
		tLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(sqlbv2);
		for (int i = 1; i <= tLJTempFeeClassSet.size(); i++) {
			this.initCommon(); // 初始化成员变量
			LJTempFeeClassSchema tLJTempFeeClassSchema = tLJTempFeeClassSet
					.get(i);
			String tRow[] = new String[2];
			String tColumn[] = new String[0];
			tRow[0] = "x"; // 方式
			tRow[1] = tLJTempFeeClassSchema.getBankCode();
			FinCheckKey tF = new FinCheckKey(tRow, tColumn);
			String tPayMoney = String.valueOf(tLJTempFeeClassSchema
					.getPayMoney());
			tFinDayTool.enterBasicInfo(tF, tPayMoney);
		}

		// add by he
		ListTable tlistTable = new ListTable();
		tlistTable.setName("RISK1");

		for (int k = 1; k <= 9; k++) {
			// add by he 求每个险类的小计
			String tttRow[] = new String[2];
			String tttColumn[] = new String[0];
			tttRow[0] = (k == 9) ? "x" : String.valueOf(k);
			FinCheckKey tttFinCheckKey = new FinCheckKey(tttRow, tttColumn);
			String tTotalsum = tFinDayTool.getTotalValue(tttFinCheckKey);
			if (Double.parseDouble(tTotalsum) == 0)
				continue;
			this.mPayModeName = (k == 9) ? "集中批收" : FinDayPub.getPayModeName(k);
			// add by he
			String ttRow[] = new String[2];
			String ttColumn[] = new String[0];

			Vector tv = tFinDayTool.getAllRiskCode((k == 9) ? "x" : String
					.valueOf(k));
			Enumeration e = tv.elements();
			while (e.hasMoreElements()) {
				String tBankOrCheckNoCode = (String) e.nextElement();
				ttRow[0] = (k == 9) ? "x" : String.valueOf(k);
				ttRow[1] = tBankOrCheckNoCode;
				FinCheckKey ttFinCheckKey = new FinCheckKey(ttRow, ttColumn);
				String tRiskMoney = tFinDayTool.getCheckValue(ttFinCheckKey);
				if (Double.parseDouble(tRiskMoney) == 0)
					continue;

				strArr = new String[4];
				strArr[0] = this.mPayModeName;
				strArr[1] = getBankName(tBankOrCheckNoCode);
				strArr[2] = tRiskMoney;
				strArr[3] = "";
				tlistTable.add(strArr);
			}
		}
		strArr = new String[4];
		strArr[0] = "Risk";
		strArr[1] = "BankCodeOrCheckNo";
		strArr[2] = "Money";
		strArr[3] = "Count";
		xmlexport.addListTable(tlistTable, strArr);
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		String nsql = "select Name from LDCom where ComCode='"
				+ "?ManageCom?" + "'";
		sqlbv3.sql(nsql);
		sqlbv3.put("ManageCom", mGlobalInput.ManageCom);
		ExeSQL nExeSQL = new ExeSQL();
		SSRS nSSRS = new SSRS();
		nSSRS = nExeSQL.execSQL(sqlbv3);
		String manageCom = nSSRS.GetText(1, 1);
		TextTag texttag = new TextTag();// 新建一个TextTag的实例
		texttag.add("StartDate", mDay[0]);
		texttag.add("EndDate", mDay[1]);
		texttag.add("ManageCom", manageCom);

		if (texttag.size() > 0)
			xmlexport.addTextTag(texttag);
		// xmlexport.outputDocumentToFile("e:\\","abccc.xls");//输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	private boolean getPrintDataGet() {
		XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
		xmlexport.createDocument("FinDayCheckModeGet.vts", "printer");// 最好紧接着就初始化xml文档
		String strArr[] = null;

		// add by hezy 加载数据
		FinDayTool tFinDayTool = new FinDayTool();
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		String tSql = "select * from LJFIGet b where ConfDate>='"
				+ "?date1?"
				+ "' and ConfDate<='"
				+ "?date2?"
				+ "' and ManageCom like concat('"
				+ "?ManageCom?"
				+ "','%')  and ManageCom like concat('"
				+ "?ManageCom?"
				+ "','%') and (paymode <> '4' or (paymode = '4' "
				+ "and exists(select 1 from ldbank where bankcode = b.bankcode "
				+ "and rpad(trim(comcode),6,'00') = substr(b.managecom,1,6))))";
		logger.debug(tSql);
		sqlbv4.sql(tSql);
		sqlbv4.put("date1", mDay[0]);
		sqlbv4.put("date2", mDay[1]);
		sqlbv4.put("ManageCom", mGlobalInput.ManageCom);
		LJFIGetDB tLJFIGetDB = new LJFIGetDB();// -寿险业务-付费总表
		LJFIGetSet tLJFIGetSet = tLJFIGetDB.executeQuery(sqlbv4);
		for (int i = 1; i <= tLJFIGetSet.size(); i++) {
			this.initCommon(); // 初始化成员变量
			LJFIGetSchema tLJFIGetSchema = tLJFIGetSet.get(i);
			String tRow[] = new String[2];
			String tColumn[] = new String[0];
			tRow[0] = tLJFIGetSchema.getPayMode(); // 方式
			int tPayMode = Integer.parseInt(tLJFIGetSchema.getPayMode());
			String tAddColumn = "";
			switch (tPayMode) {
			case 1: // 现金
				tAddColumn = "$1";
				break;
			case 2: // 现金支票
				tAddColumn = tLJFIGetSchema.getChequeNo();
				break;
			case 3: // 转账支票
				tAddColumn = tLJFIGetSchema.getChequeNo();
				break;
			case 4: // 银行转账
				tAddColumn = tLJFIGetSchema.getBankCode();
				break;
			case 5: // 内部转账
				tAddColumn = "$5";
				break;
			case 6: // 银行托收
				tAddColumn = tLJFIGetSchema.getBankCode();
				break;
			case 7: // 信用卡
				tAddColumn = "$7";
				break;
			case 8: // 其他
				tAddColumn = "$8";
				break;
			}
			tRow[1] = tAddColumn; // 银行名称或支票号码
			FinCheckKey tF = new FinCheckKey(tRow, tColumn);
			String tPayMoney = String.valueOf(tLJFIGetSchema.getGetMoney());
			tFinDayTool.enterBasicInfo(tF, tPayMoney);
		}
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		tSql = "select * from LJFIGet b where ConfDate>='"
				+ "?date1?"
				+ "' and ConfDate<='"
				+ "?date2?"
				+ "' and ManageCom like concat('"
				+ "?ManageCom?"
				+ "','%')  and ManageCom like concat('"
				+ "?ManageCom?"
				+ "','%') and paymode = '4' "
				+ "and exists(select 1 from ldbank where bankcode = b.bankcode "
				+ "and rpad(trim(comcode),6,'00') <> substr(b.managecom,1,6))";
		logger.debug(tSql);
		sqlbv5.sql(tSql);
		sqlbv5.put("date1", mDay[0]);
		sqlbv5.put("date2", mDay[1]);
		sqlbv5.put("ManageCom", mGlobalInput.ManageCom);
		tLJFIGetDB = new LJFIGetDB();// -寿险业务-付费总表
		tLJFIGetSet = tLJFIGetDB.executeQuery(sqlbv5);
		for (int i = 1; i <= tLJFIGetSet.size(); i++) {
			this.initCommon(); // 初始化成员变量
			LJFIGetSchema tLJFIGetSchema = tLJFIGetSet.get(i);
			String tRow[] = new String[2];
			String tColumn[] = new String[0];
			tRow[0] = "x"; // 方式
			tRow[1] = tLJFIGetSchema.getBankCode(); // 银行名称或支票号码
			FinCheckKey tF = new FinCheckKey(tRow, tColumn);
			String tPayMoney = String.valueOf(tLJFIGetSchema.getGetMoney());
			tFinDayTool.enterBasicInfo(tF, tPayMoney);
		}

		// add by he
		ListTable tlistTable = new ListTable();
		tlistTable.setName("RISK1");

		for (int k = 1; k <= 9; k++) {
			// add by he 求每个险类的小计
			String tttRow[] = new String[2];
			String tttColumn[] = new String[0];
			tttRow[0] = (k == 9) ? "x" : String.valueOf(k);
			FinCheckKey tttFinCheckKey = new FinCheckKey(tttRow, tttColumn);
			String tTotalsum = tFinDayTool.getTotalValue(tttFinCheckKey);
			if (Double.parseDouble(tTotalsum) == 0)
				continue;
			this.mPayModeName = (k == 9) ? "集中批付" : FinDayPub.getPayModeName(k);
			// add by he
			String ttRow[] = new String[2];
			String ttColumn[] = new String[0];

			Vector tv = tFinDayTool.getAllRiskCode((k == 9) ? "x" : String
					.valueOf(k));
			Enumeration e = tv.elements();
			while (e.hasMoreElements()) {
				String tBankOrCheckNoCode = (String) e.nextElement();
				ttRow[0] = (k == 9) ? "x" : String.valueOf(k);
				ttRow[1] = tBankOrCheckNoCode;
				FinCheckKey ttFinCheckKey = new FinCheckKey(ttRow, ttColumn);
				String tRiskMoney = tFinDayTool.getCheckValue(ttFinCheckKey);
				if (Double.parseDouble(tRiskMoney) == 0)
					continue;

				strArr = new String[4];
				strArr[0] = this.mPayModeName;
				strArr[1] = getBankName(tBankOrCheckNoCode);
				strArr[2] = tRiskMoney;
				strArr[3] = "";
				tlistTable.add(strArr);
			}
		}
		strArr = new String[4];
		strArr[0] = "Risk";
		strArr[1] = "BankCodeOrCheckNo";
		strArr[2] = "Money";
		strArr[3] = "Count";
		xmlexport.addListTable(tlistTable, strArr);
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		String nsql = "select Name from LDCom where ComCode='"
				+ "?ManageCom?" + "'";
		sqlbv6.sql(nsql);
		sqlbv6.put("ManageCom", mGlobalInput.ManageCom);
		ExeSQL nExeSQL = new ExeSQL();
		SSRS nSSRS = new SSRS();
		nSSRS = nExeSQL.execSQL(sqlbv6);
		String manageCom = nSSRS.GetText(1, 1);
		TextTag texttag = new TextTag();// 新建一个TextTag的实例
		texttag.add("StartDate", mDay[0]);
		texttag.add("EndDate", mDay[1]);
		texttag.add("ManageCom", manageCom);

		if (texttag.size() > 0)
			xmlexport.addTextTag(texttag);
		// xmlexport.outputDocumentToFile("e:\\","核保岗位日结清单");//输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	private void initCommon() {
		this.mPayMode = "";
		this.mBankOrCheckNo = "";
		this.mPayModeName = "";
	}

	private String getRiskName(String riskcode) {
		String tRet = "";
		for (int i = 1; i <= this.mLMRiskAppSet.size(); i++) {
			LMRiskAppSchema tLMRiskAppSchema = mLMRiskAppSet.get(i);
			if (tLMRiskAppSchema.getRiskCode().equals(riskcode)) {
				tRet = tLMRiskAppSchema.getRiskName();
			}
		}
		return tRet;
	}

	private String getBankName(String tBankCode) {
		if (tBankCode == null)
			return "";
		if (tBankCode.length() == 4) {
			LDCodeDB tLDCodeDB = new LDCodeDB();
			tLDCodeDB.setCodeType("bank");
			tLDCodeDB.setCode(tBankCode);
			if (tLDCodeDB.getInfo()) {
				return tLDCodeDB.getCodeName();
			}
		}
		if (tBankCode.indexOf("$") != -1) {
			return "";
		}
		return tBankCode;
	}
}
