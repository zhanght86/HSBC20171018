package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author guanwei
 * @version 1.0
 */

public class RNPayModeQueryListBL {
private static Logger logger = Logger.getLogger(RNPayModeQueryListBL.class);
	private String mStartDate = "";
	private String mEndDate = "";
	private String mManageCom = "";
	private String mSaleChnl = "";

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */

	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	public RNPayModeQueryListBL() {
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

		mStartDate = (String) mTransferData.getValueByName("StartDate"); // 起始日期
		logger.debug("|||||||||StartDate||||||||" + mStartDate);
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 终止日期
		logger.debug("|||||||||mEndDate||||||||" + mEndDate);
		mManageCom = (String) mTransferData.getValueByName("ManageCom"); // 管理机构
		logger.debug("|||||||||mManageCom||||||||" + mManageCom);
		mSaleChnl = (String) mTransferData.getValueByName("SaleChnl"); // 销售渠道
		logger.debug("|||||||||mSaleChnl||||||||" + mSaleChnl);

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
		cError.moduleName = "RNPayModeQueryListBL";
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
		ListTable tlistTable = new ListTable();
		String[] ContractTitle = new String[6]; // 随意定义的 与显示无关

		String tSql = "select (select Name from LDCom where ComCode = a.ManageCom), "
				+ "a.ManageCom,a.MakeDate "
				+ "  from LJAPayPerson a, LCCont b "
				+ " where a.ContNo = b.ContNo and a.ManageCom like concat('"
				+ "?mManageCom?"
				+ "','%') "
				+ "   and a.MakeDate between '"
				+ "?mStartDate?"
				+ "' and '"
				+ "?mEndDate?"
				+ "' "
				+ "   and b.PayLocation in ('0', '1', '2') "
				+ "   and b.SaleChnl = '"
				+ "?mSaleChnl?"
				+ "' "
				+ " group by a.ManageCom, a.MakeDate "
				+ " order by a.ManageCom, a.MakeDate ";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("mManageCom", mManageCom);
		sqlbv1.put("mStartDate", mStartDate);
		sqlbv1.put("mEndDate", mEndDate);
		sqlbv1.put("mSaleChnl", mSaleChnl);
		tlistTable.setName("list");
		ExeSQL exeSql = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String[] strArr = null;
		tSSRS = exeSql.execSQL(sqlbv1);
		int tBankCount = 0;
		int tOwnCount = 0;
		int tGateCount = 0;
		double tPayMoneyTotal = 0;

		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {

			strArr = new String[6];
			String tManageComName = "";
			String tManageCom = "";
			String tMakeDate = "";
			String tBankNumber = "";
			String tOwnNumber = "";
			String tGateNumber = "";
			String tPayMonty = "";

			// 管理机构名称
			tManageComName = tSSRS.GetText(i, 1);
			strArr[0] = tManageComName;

			// 管理机构
			tManageCom = tSSRS.GetText(i, 2);

			// 生效对应日
			tMakeDate = tSSRS.GetText(i, 3);
			strArr[1] = tMakeDate;

			// 银行划款件数
			SSRS bnSSRS = new SSRS();
			tSql = "select count(distinct a.ContNo) "
					+ "  from LJAPayPerson a,LCCont b "
					+ " where a.ContNo = b.ContNo "
					+ "   and b.PayLocation = '0'  " + "   and a.ManageCom = '"
					+ "?tManageCom?" + "' " + "   and a.MakeDate = '" + "?tMakeDate?"
					+ "' ";
			if (mSaleChnl != null && !mSaleChnl.equals("")
					&& mSaleChnl.length() > 0)
				tSql = tSql + " and b.SaleChnl = '" + "?mSaleChnl?" + "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSql);
			sqlbv2.put("tManageCom", tManageCom);
			sqlbv2.put("tMakeDate", tMakeDate);
			sqlbv2.put("mSaleChnl", mSaleChnl);
			bnSSRS = exeSql.execSQL(sqlbv2);
			tBankNumber = bnSSRS.GetText(1, 1);
			if (bnSSRS.getMaxRow() > 0) {
				strArr[2] = tBankNumber;
				tBankCount = tBankCount + Integer.parseInt(tBankNumber);
			}

			// 自行缴纳件数
			SSRS onSSRS = new SSRS();
			tSql = "select count(distinct a.ContNo) "
					+ "  from LJAPayPerson a,LCCont b "
					+ " where a.ContNo = b.ContNo "
					+ "   and b.PayLocation = '1'  " + "   and a.ManageCom = '"
					+ "?tManageCom?" + "' " + "   and a.MakeDate = '" + "?tMakeDate?"
					+ "' ";
			if (mSaleChnl != null && !mSaleChnl.equals("")
					&& mSaleChnl.length() > 0)
				tSql = tSql + " and b.SaleChnl = '" + "?mSaleChnl?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tSql);
			sqlbv3.put("tManageCom", tManageCom);
			sqlbv3.put("tMakeDate", tMakeDate);
			sqlbv3.put("mSaleChnl", mSaleChnl);
			onSSRS = exeSql.execSQL(sqlbv3);
			tOwnNumber = onSSRS.GetText(1, 1);
			if (onSSRS.getMaxRow() > 0) {
				strArr[3] = tOwnNumber;
				tOwnCount = tOwnCount + Integer.parseInt(tOwnNumber);
			}

			// 上门缴费件数
			SSRS gnSSRS = new SSRS();
			tSql = "select count(distinct a.ContNo) "
					+ "  from LJAPayPerson a,LCCont b "
					+ " where a.ContNo = b.ContNo "
					+ "   and b.PayLocation = '2'  " + "   and a.ManageCom = '"
					+ "?tManageCom?" + "' " + "   and a.MakeDate = '" + "?tMakeDate?"
					+ "' ";
			if (mSaleChnl != null && !mSaleChnl.equals("")
					&& mSaleChnl.length() > 0)
				tSql = tSql + " and b.SaleChnl = '" + "?mSaleChnl?" + "'";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tSql);
			sqlbv4.put("tManageCom", tManageCom);
			sqlbv4.put("tMakeDate", tMakeDate);
			sqlbv4.put("mSaleChnl", mSaleChnl);
			gnSSRS = exeSql.execSQL(sqlbv4);
			tGateNumber = gnSSRS.GetText(1, 1);
			if (gnSSRS.getMaxRow() > 0) {
				strArr[4] = tGateNumber;
				tGateCount = tGateCount + Integer.parseInt(tGateNumber);
			}

			// 缴费金额统计
			SSRS pmSSRS = new SSRS();
			tSql = "select sum(a.SumActuPayMoney) "
					+ "  from LJAPayPerson a,LCCont b "
					+ " where a.ContNo = b.ContNo " + "   and a.ManageCom = '"
					+ "?tManageCom?" + "' " + "   and a.MakeDate = '" + "?tMakeDate?"
					+ "' ";
			if (mSaleChnl != null && !mSaleChnl.equals("")
					&& mSaleChnl.length() > 0)
				tSql = tSql + " and b.SaleChnl = '" + "?mSaleChnl?" + "'";
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(tSql);
			sqlbv5.put("tManageCom", tManageCom);
			sqlbv5.put("tMakeDate", tMakeDate);
			sqlbv5.put("mSaleChnl", mSaleChnl);
			pmSSRS = exeSql.execSQL(sqlbv5);
			tPayMonty = pmSSRS.GetText(1, 1);
			if (pmSSRS.getMaxRow() > 0) {
				strArr[5] = tPayMonty;
				tPayMoneyTotal = tPayMoneyTotal + Double.parseDouble(tPayMonty);
			}
			tlistTable.add(strArr);
		}

		// 统计机构
		SSRS nSSRS = new SSRS();
		tSql = "select Name from LDCom where ComCode='" + "?mManageCom?" + "'";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(tSql);
		sqlbv6.put("mManageCom", mManageCom);
		nSSRS = exeSql.execSQL(sqlbv6);
		String tManageName = nSSRS.GetText(1, 1);

		// 销售渠道
		SSRS cSSRS = new SSRS();
		tSql = "select CodeName from LDCode where Code='" + "?mSaleChnl?" + "'"
				+ "and CodeType = 'salechnl '";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(tSql);
		sqlbv7.put("mSaleChnl", mSaleChnl);
		cSSRS = exeSql.execSQL(sqlbv7);
		String tSaleChnl = cSSRS.GetText(1, 1);

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("RNPayModeQueryList.vts", "PRINT"); // 最好紧接着就初始化xml文档

		texttag.add("BankCount", tBankCount);
		texttag.add("OwnCount", tOwnCount);
		texttag.add("GateCount", tGateCount);
		texttag.add("PayMoneyTotal", tPayMoneyTotal);
		texttag.add("ManageName", tManageName);
		texttag.add("SaleChnl", tSaleChnl);
		texttag.add("StartDate", mStartDate);
		texttag.add("EndDate", mEndDate);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		xmlexport.addListTable(tlistTable, ContractTitle);
		mResult.addElement(xmlexport);

		return true;

	}

	private void jbInit() throws Exception {
	}
}
