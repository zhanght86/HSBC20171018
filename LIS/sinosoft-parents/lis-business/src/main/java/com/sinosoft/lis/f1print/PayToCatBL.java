package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJAGetClaimDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LJAGetClaimSchema;
import com.sinosoft.lis.vschema.LJAGetClaimSet;
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
 * @author Ght
 * @version 1.0
 */

public class PayToCatBL {
private static Logger logger = Logger.getLogger(PayToCatBL.class);
	String tManageCom = "";
	String tPolicyCom = "";
	String tEndDay = "";
	String tStartDay = "";
	String mPayMode = "";
	String mOperator = "";

	double day = 0;
	double month = 0;

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	private String mSelString = "";
	private DecimalFormat tf = new DecimalFormat("0.00");
	// 业务处理相关变量
	/** 全局数据 */

	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	private String mBankCode = "";
	private String mBankName = "";

	public PayToCatBL() {
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
		String strOperation = "PRINT";
		GlobalInput tGI = new GlobalInput();
		tGI.ManageCom = "865100";
		tGI.Operator = "001";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ManageCom", "865100");
		tTransferData.setNameAndValue("PolicyCom", "865100");
		tTransferData.setNameAndValue("StartDay", "2005-12-22");
		tTransferData.setNameAndValue("EndDay", "2005-12-22");
		VData tVData = new VData();
		tVData.addElement(tTransferData);
		tVData.addElement(tGI);
		PayToCatUI tPayToCatUI = new PayToCatUI();
		if (!tPayToCatUI.submitData(tVData, strOperation)) {
			logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$");
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
		// mDay = (String[]) cInputData.get(0);
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
		// 管理机构
		tPolicyCom = (String) mTransferData.getValueByName("PolicyCom");
		// 付费方式
		mPayMode = (String) mTransferData.getValueByName("PayMode");
		// 制表员
		mOperator = (String) mTransferData.getValueByName("Operator");
		// 银行编码
		mBankCode = (String) mTransferData.getValueByName("BankCode");
		logger.debug("***********************mBankCode***********************"
						+ mBankCode);
		// 银行名称
		mBankName = (String) mTransferData.getValueByName("BankName");
		if (tManageCom.equals("") || tManageCom == null) {
			return false;
		} else if (tManageCom.length() < mGlobalInput.ManageCom.length()) {
			buildError("getInputData", "输入收付机构时越权操作:"
					+ mGlobalInput.ManageCom.length() + "位登陆机构不能操作"
					+ tManageCom.length() + "位机构下数据");
			return false;
		}
		if (tPolicyCom.equals("") || tPolicyCom == null) {
			return false;
		}

		tEndDay = (String) mTransferData.getValueByName("EndDay");
		tStartDay = (String) mTransferData.getValueByName("StartDay");

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

		String msql = "select name from ldcom where comcode = '"
				+ "?mGlobalInput?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(msql);
		sqlbv1.put("mGlobalInput", mGlobalInput.ManageCom);
		ExeSQL tExeSQL = new ExeSQL();
		nSSRS = tExeSQL.execSQL(sqlbv1);
		String bManageComName = nSSRS.GetText(1, 1);

		// 收付机构名称
		msql = "select name from ldcom where comcode = '" + "?tManageCom?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(msql);
		sqlbv2.put("tManageCom", tManageCom);
		ExeSQL rExeSQL = new ExeSQL();
		nSSRS = rExeSQL.execSQL(sqlbv2);
		String sManageComName = nSSRS.GetText(1, 1);

		// 管理机构名称
		msql = "select name from ldcom where comcode = '" + "?tPolicyCom?" + "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(msql);
		sqlbv3.put("tPolicyCom", tPolicyCom);
		ExeSQL bExeSQL = new ExeSQL();
		nSSRS = bExeSQL.execSQL(sqlbv3);
		String sPolicyComName = nSSRS.GetText(1, 1);

		// 查询付费方式
		String GetType = "无";
		if (mPayMode != null && !mPayMode.equals("")) {
			String y = "select trim(codename) from ldcode "
					+ " where codetype='gettype' and code='" + "?mPayMode?" + "'";
			logger.debug("查询付费方式::::::::::::::" + y);
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(y);
			sqlbv4.put("mPayMode", mPayMode);
			rExeSQL = new ExeSQL();
			nSSRS = rExeSQL.execSQL(sqlbv4);
			if (nSSRS.getMaxRow() > 0)
				GetType = nSSRS.GetText(1, 1);
		}
		String BankName = "";
		// if(mBankCode != null && !mBankCode.equalsIgnoreCase(""))
		// {
		// logger.debug("bankcode="+mBankCode);
		// logger.debug("paymode="+mPayMode);
		// if(mPayMode.equals("A"))
		// {
		// String tLDBankSql =
		// "select trim(codename) from ldcode where codetype = 'bank' and code =
		// '" +
		// mBankCode + "'";
		// ExeSQL tExeSql = new ExeSQL();
		// nSSRS = tExeSql.execSQL(tLDBankSql);
		// BankName = nSSRS.GetText(1, 1);
		// }
		// if(mPayMode.equals("4"))
		// {
		// String ttLDBankSql =
		// "select trim(codename) from ldcode where codetype = 'finbank' and
		// code = '" +
		// mBankCode + "'";
		// ExeSQL ttExeSql = new ExeSQL();
		// nSSRS = ttExeSql.execSQL(ttLDBankSql);
		// BankName = nSSRS.GetText(1, 1);
		// }
		//
		// }

		/** @todo 应付满期给付日 */
		logger.debug("=========================应付满期给付==========================");
		msql = "select sum(getmoney) from LJFIGet "
				+ " where enteraccdate >= '" + "?tStartDay?" + "' "
				+ "   and enteraccdate <= '" + "?tEndDay?" + "' "
				+ "   and ManageCom like concat('" + "?tManageCom?" + "','%') "
				+ "   and PolicyCom like concat('" + "?tPolicyCom?" + "','%') "
				+ "   and ActuGetNo in ((select ActuGetNo from LJAGetDraw "
				+ " where FeeFinaType = 'EF'))" + " and paymode <> '5'";
		if (mPayMode != null && !mPayMode.equals("")) {
			msql = msql + " and paymode='" + "?mPayMode?" + "'";
		}
		// if(mPayMode == "4"||mPayMode=="A")
		// {
		if (mBankCode != null && !mBankCode.equals("")) {
			msql = msql + " and bankcode = '" + "?mBankCode?" + "'";
		}
		// }
		if (mOperator != null && mOperator != "")
			msql = msql + " and trim(Operator) = '" + "?mOperator?" + "'";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(msql);
		sqlbv5.put("tStartDay", tStartDay);
		sqlbv5.put("tEndDay", tEndDay);
		sqlbv5.put("tManageCom", tManageCom);
		sqlbv5.put("tPolicyCom", tPolicyCom);
		sqlbv5.put("mPayMode", mPayMode);
		sqlbv5.put("mBankCode", mBankCode);
		sqlbv5.put("mOperator", mOperator);
		nSSRS = rExeSQL.execSQL(sqlbv5);
		String yfmqd = nSSRS.GetText(1, 1);
		if (yfmqd != null && !yfmqd.equals("") && !yfmqd.equals("null")) {
			day = day + Double.parseDouble(yfmqd);
		}
		if (yfmqd == null || yfmqd.equals("null")) {
			yfmqd = "0";
		}

		/** @todo 应付年金给付 */
		logger.debug("=========================应付年金给付==========================");
		msql = " select sum(getmoney) from LJFIGet "
				+ "  where enteraccdate >= '" + "?tStartDay?" + "' "
				+ "    and enteraccdate <='" + "?tEndDay?" + "' "
				+ "    and ManageCom like concat('" + "?tManageCom?" + "','%') "
				+ "    and PolicyCom like concat('" + "?tPolicyCom?" + "','%') "
				+ "    and ActuGetNo in (select ActuGetNo from LJAGetDraw "
				+ "  where FeeFinaType = 'YF')" + "    and paymode <> '5'";
		if (mPayMode != null && !mPayMode.equals(""))
			msql = msql + " and paymode='" + "?mPayMode?" + "'";
		// if(mPayMode == "4"||mPayMode=="A")
		// {
		if (mBankCode != null && !mBankCode.equals("")) {
			msql = msql + " and bankcode = '" + "?mBankCode?" + "'";
		}
		// }
		if (mOperator != null && mOperator != "")
			msql = msql + " and trim(Operator) = '" + "?mOperator?" + "'";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(msql);
		sqlbv6.put("tStartDay", tStartDay);
		sqlbv6.put("tEndDay", tEndDay);
		sqlbv6.put("tManageCom", tManageCom);
		sqlbv6.put("tPolicyCom", tPolicyCom);
		sqlbv6.put("mPayMode", mPayMode);
		sqlbv6.put("mBankCode", mBankCode);
		sqlbv6.put("mOperator", mOperator);
		nSSRS = rExeSQL.execSQL(sqlbv6);
		String yfnjd = nSSRS.GetText(1, 1);

		if (yfnjd != null && !yfnjd.equals("") && !yfnjd.equals("null")) {
			day = day + Double.parseDouble(yfnjd);
		}
		if (yfnjd == null || yfnjd.equals("null")) {
			yfnjd = "0";
		}

		/** @todo 应付出单后退费 */
		logger.debug("=========================应付出单后退费==========================");

		String tSql = "select actugetno from LJFIGet "
				+ " where enteraccdate >= '" + "?tStartDay?" + "' "
				+ "   and enteraccdate <= '" + "?tEndDay?" + "' "
				+ "   and ManageCom like concat('" + "?tManageCom?" + "','%') "
				+ "   and PolicyCom like concat('" + "?tPolicyCom?" + "','%') "
				+ "   and paymode <> '5'";
		if (mPayMode != null && !mPayMode.equals(""))
			tSql = tSql + " and paymode = '" + "?mPayMode?" + "'";
		if (mBankCode != null && !mBankCode.equals(""))
			tSql = tSql + " and bankcode = '" + "?mBankCode?" + "'";
		if (mOperator != null && mOperator != "")
			tSql = tSql + " and trim(Operator) = '" + "?mOperator?" + "'";

		msql = "select (-1)*sum(getmoney) from LJAGetEndorse "
				+ " where actugetno in (" + tSql + ")"
				+ " and FeeFinaType in ('TF','GB','YF')"
				+ " and LJAGetEndorse.Feeoperationtype not in ('RB') ";// MOd
																		// by
																		// gaoht
																		// 保全工本费回退记入保全回退
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(msql);
		sqlbv7.put("tStartDay", tStartDay);
		sqlbv7.put("tEndDay", tEndDay);
		sqlbv7.put("tManageCom", tManageCom);
		sqlbv7.put("tPolicyCom", tPolicyCom);
		sqlbv7.put("mPayMode", mPayMode);
		sqlbv7.put("mBankCode", mBankCode);
		sqlbv7.put("mOperator", mOperator);
		nSSRS = rExeSQL.execSQL(sqlbv7);
		String yfcdhd1 = nSSRS.GetText(1, 1);
		if (yfcdhd1 == null || yfcdhd1.equals("null")) {
			yfcdhd1 = "0";
		}
		double TF = Double.parseDouble(yfcdhd1);

		msql = "select sum(pay) from LJAGetClaim "
				+ " where FeeFinaType = 'TF' " + "   and actugetno in (" + tSql
				+ ")";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(msql);
		sqlbv8.put("tStartDay", tStartDay);
		sqlbv8.put("tEndDay", tEndDay);
		sqlbv8.put("tManageCom", tManageCom);
		sqlbv8.put("tPolicyCom", tPolicyCom);
		sqlbv8.put("mPayMode", mPayMode);
		sqlbv8.put("mBankCode", mBankCode);
		sqlbv8.put("mOperator", mOperator);
		nSSRS = rExeSQL.execSQL(sqlbv8);
		String yfcdhd2 = nSSRS.GetText(1, 1);
		if (yfcdhd2 == null || yfcdhd2.equals("null")) {
			yfcdhd2 = "0";
		}
		TF = TF + Double.parseDouble(yfcdhd2);

		String yfcdhd = String.valueOf(TF);

		if (TF != 0) {
			day = day + TF;
		}
		if (TF == 0) {
			yfcdhd = "0";
		}

		/** @todo 应付保户质押贷款 */
		logger.debug("=========================应付保户质押贷款==========================");

		msql = "select sum(getmoney) from LJFIGet "
				+ " where enteraccdate >= '" + "?tStartDay?" + "' "
				+ "   and enteraccdate <= '" + "?tEndDay?" + "' "
				+ "   and ManageCom like concat('" + "?tManageCom?" + "','%') "
				+ "   and PolicyCom like concat('" + "?tPolicyCom?" + "','%') "
				+ "   and ActuGetNo in (select ActuGetNo from LJAGetEndorse "
				+ " where FeeFinaType = 'DK' and FeeOperationType = 'LN') "
				+ "   and paymode <> '5'";
		if (mPayMode != null && !mPayMode.equals(""))
			msql = msql + " and paymode = '" + "?mPayMode?" + "'";
		if (mBankCode != null && !mBankCode.equals(""))
			msql = msql + " and bankcode = '" + "?mBankCode?" + "'";
		if (mOperator != null && mOperator != "")
			msql = msql + " and trim(Operator) = '" + "?mOperator?" + "'";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(msql);
		sqlbv9.put("tStartDay", tStartDay);
		sqlbv9.put("tEndDay", tEndDay);
		sqlbv9.put("tManageCom", tManageCom);
		sqlbv9.put("tPolicyCom", tPolicyCom);
		sqlbv9.put("mPayMode", mPayMode);
		sqlbv9.put("mBankCode", mBankCode);
		sqlbv9.put("mOperator", mOperator);
		nSSRS = rExeSQL.execSQL(sqlbv9);
		String yfbhzydd = nSSRS.GetText(1, 1);
		if (yfbhzydd != null && !yfbhzydd.equals("")
				&& !yfbhzydd.equals("null")) {
			day = day + Double.parseDouble(yfbhzydd);
		}
		if (yfbhzydd == null || yfbhzydd.equals("null")) {
			yfbhzydd = "0";
		}

		// /**@todo 应付保户红利 */
		// logger.debug("=========================应付保户红利==========================");
		// double HL = 0;
		// msql =" select sum(getmoney) from LJFIGet where enteraccdate >='" +
		// tStartDay + "' and enteraccdate <='" + tEndDay + "' and ManageCom
		// like '" +
		// tManageCom + "%' and ActuGetNo in (select ActuGetNo from LJABonusGet
		// where FeeFinaType = 'CB')"
		// +" and paymode <> '5'";
		// if (mPayMode!=null&&!mPayMode.equals(""))
		// msql = msql + " and paymode = '"+mPayMode+"'";
		// if (mOperator!=null&&mOperator!="")
		// msql = msql + " and trim(Operator) = '"+mOperator+"'";
		//
		// nSSRS = rExeSQL.execSQL(msql);
		// if (!nSSRS.GetText(1, 1).equals("null"))
		// HL = Double.parseDouble(nSSRS.GetText(1, 1));
		//
		// msql = "select sum(pay) from ljagetclaim where actugetno in (select
		// actugetno from ljfiget where enteraccdate between '"+tStartDay
		// +"' and '"+tEndDay+"' and ManageCom like '"+tManageCom+"%' and
		// paymode<>'5') and feeoperationtype = 'C05'";
		// if (mPayMode!=null&&!mPayMode.equals(""))
		// msql = "select sum(pay) from ljagetclaim where actugetno in (select
		// actugetno from ljfiget where enteraccdate between '"+tStartDay
		// +"' and '"+tEndDay+"' and ManageCom like '"+tManageCom+"%' and
		// paymode<>'5' and paymode='"+mPayMode+"') and feeoperationtype =
		// 'C05'";
		// if (mOperator!=null&&mOperator!="")
		// msql = "select sum(pay) from ljagetclaim where actugetno in (select
		// actugetno from ljfiget where enteraccdate between '"+tStartDay
		// +"' and '"+tEndDay+"' and ManageCom like '"+tManageCom+"%' and
		// paymode<>'5' and Operator='"+mOperator+"') and feeoperationtype =
		// 'C05'";
		// if
		// ((mOperator!=null&&mOperator!="")&&(mPayMode!=null&&!mPayMode.equals("")))
		// msql = "select sum(pay) from ljagetclaim where actugetno in (select
		// actugetno from ljfiget where enteraccdate between '"+tStartDay
		// +"' and '"+tEndDay+"' and ManageCom like '"+tManageCom+"%' and
		// paymode<>'5' and Operator='"+mOperator+"' and paymode='"+mPayMode+"')
		// and feeoperationtype = 'C05'";
		//
		// nSSRS = rExeSQL.execSQL(msql);
		// if (!nSSRS.GetText(1, 1).equals("null"))
		// HL = HL + Double.parseDouble(nSSRS.GetText(1, 1));
		String yfbhhld = "";
		// if (HL>0)
		// yfbhhld = String.valueOf(HL);
		if (yfbhhld != null && !yfbhhld.equals("") && !yfbhhld.equals("null")) {
			day = day + Double.parseDouble(yfbhhld);
		}
		if (yfbhhld == null || yfbhhld.equals("null") || yfbhhld == "") {
			yfbhhld = "0";
		}

		// 应付预收续期保费
		// commented by heyq 需要在增加保单帐户功能后完善
		// msql =" select sum(Pay) from LJAGetClaim where FeeFinaType = 'TF' and
		// GetDate >='" +
		// tStartDay + "' and GetDate <='" + tEndDay + "' and ManageCom like '"
		// +
		// tManageCom + "'%";
		// nSSRS = rExeSQL.execSQL(msql);
		// String yfysxqbfd = nSSRS.GetText(1, 1);
		// if (yfysxqbfd != null && !yfysxqbfd.equals("")&&
		// !yfysxqbfd.equals("null")) {
		// day = day + Double.parseDouble(yfysxqbfd);
		// }
		// if (yfysxqbfd == null || yfysxqbfd.equals("null")) {
		// String yfysxqbfd = "0";
		// }

		// msql = " select sum(Pay) from LJAGetClaim where FeeFinaType = 'TF'
		// and GetDate >=(last_day(Add_months('" +
		// tStartDay + "',-1))+1) and GetDate <='" + tEndDay +
		// "' and ManageCom like '" + tManageCom + "%'";
		// nSSRS = rExeSQL.execSQL(msql);
		// String yfysxqbfm = nSSRS.GetText(1, 1);
		// if (yfysxqbfm != null && !yfysxqbfm.equals("")&&
		// !yfysxqbfm.equals("null")) {
		// month = month + Double.parseDouble(yfysxqbfm);
		// }
		// if (yfysxqbfm == null || yfysxqbfm.equals("null")) {
		// String yfysxqbfm ="0";
		// }

		/** @todo 客户预存 */
		logger.debug("======================客户预存=======================");
		msql = "select sum(sumgetmoney) from ljaget " + " where actugetno in ("
				+ tSql + ") and othernotype='YC' ";
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql(msql);
		sqlbv10.put("tStartDay", tStartDay);
		sqlbv10.put("tEndDay", tEndDay);
		sqlbv10.put("tManageCom", tManageCom);
		sqlbv10.put("tPolicyCom", tPolicyCom);
		sqlbv10.put("mPayMode", mPayMode);
		sqlbv10.put("mBankCode", mBankCode);
		sqlbv10.put("mOperator", mOperator);
		nSSRS = rExeSQL.execSQL(sqlbv10);
		String khycd = nSSRS.GetText(1, 1);
		if (khycd != null && !khycd.equals("") && !khycd.equals("null")) {
			day = day + Double.parseDouble(khycd);
		}

		if (khycd == null || khycd.equals("null")) {
			khycd = "0";
		}

		/** @todo 应付退保金 */
		logger.debug("=========================应付退保金==========================");
		double TB = 0;
		msql = "select (-1)*sum(getmoney) from LJAGetEndorse a"
				+ " where 1=1 "
				+ " and exists (select actugetno from LJAGetEndorse b where FeeFinaType = 'TB' and actugetno=a.actugetno) "
				+ " and FeeFinaType<>'TF'" + "   and a.ActuGetNo in (" + tSql
				+ ")";
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql(msql);
		sqlbv11.put("tStartDay", tStartDay);
		sqlbv11.put("tEndDay", tEndDay);
		sqlbv11.put("tManageCom", tManageCom);
		sqlbv11.put("tPolicyCom", tPolicyCom);
		sqlbv11.put("mPayMode", mPayMode);
		sqlbv11.put("mBankCode", mBankCode);
		sqlbv11.put("mOperator", mOperator);
		nSSRS = rExeSQL.execSQL(sqlbv11);
		String yftbjd = nSSRS.GetText(1, 1);
		if (yftbjd != null && !yftbjd.equals("") && !yftbjd.equals("null")) {
			day = day + Double.parseDouble(yftbjd);
			TB = TB + Double.parseDouble(yftbjd);
		}
		if (yftbjd == null || yftbjd.equals("null")) {
			yftbjd = "0";
		}

		// 退保金理赔
		msql = "select * from LJAGetclaim " + " where FeeFinaType = 'TB' "
				+ "   and ActuGetNo in (" + tSql + ")";
		double LPTB = 0;

		logger.debug("退保金理赔===" + msql);
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		sqlbv12.sql(msql);
		sqlbv12.put("tStartDay", tStartDay);
		sqlbv12.put("tEndDay", tEndDay);
		sqlbv12.put("tManageCom", tManageCom);
		sqlbv12.put("tPolicyCom", tPolicyCom);
		sqlbv12.put("mPayMode", mPayMode);
		sqlbv12.put("mBankCode", mBankCode);
		sqlbv12.put("mOperator", mOperator);
		LJAGetClaimSet tLJAGetClaimSet = new LJAGetClaimSet();
		LJAGetClaimDB tLJAGetClaimDB = new LJAGetClaimDB();
		tLJAGetClaimSet = tLJAGetClaimDB.executeQuery(sqlbv12);
		if (tLJAGetClaimSet.size() > 0) {
			for (int i = 1; i <= tLJAGetClaimSet.size(); i++) {
				LJAGetClaimSchema tLJAGetClaimSchema = new LJAGetClaimSchema();
				tLJAGetClaimSchema = tLJAGetClaimSet.get(i);
				LPTB = LPTB + tLJAGetClaimSchema.getPay();
				String EFSql = "select * from LJAGetclaim where FeeFinaType not in ('PK','TF','TB') and actugetno='"
						+ "?actugetno?"
						+ "' and riskcode = '"
						+ "?riskcode?"
						+ "' and contno = '"
						+ "?contno?" + "'";
				SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
				sqlbv13.sql(EFSql);
				sqlbv13.put("actugetno", tLJAGetClaimSchema.getActuGetNo());
				sqlbv13.put("riskcode", tLJAGetClaimSchema.getRiskCode());
				sqlbv13.put("contno", tLJAGetClaimSchema.getContNo());
				LJAGetClaimSet nLJAGetClaimSet = new LJAGetClaimSet();
				LJAGetClaimDB nLJAGetClaimDB = new LJAGetClaimDB();
				nLJAGetClaimSet = nLJAGetClaimDB.executeQuery(sqlbv13);
				logger.debug("本次付费除赔款的其他信息查询::::" + EFSql);
				if (nLJAGetClaimSet.size() > 0) {
					for (int t = 1; t <= nLJAGetClaimSet.size(); t++) {
						LJAGetClaimSchema nLJAGetClaimSchema = new LJAGetClaimSchema();
						nLJAGetClaimSchema = nLJAGetClaimSet.get(t);
						logger.debug("本次付费存在财务类型:::::::::::::::::"
								+ nLJAGetClaimSchema.getFeeFinaType());
						LPTB = LPTB + nLJAGetClaimSchema.getPay();
					}
				}
			}
		}
		if (LPTB != 0) {
			day = day + LPTB;
			TB = TB + LPTB;
		}
		String TBJ = String.valueOf(TB);

		/** @todo 应付医疗给付 */
		logger.debug("=========================应付医疗给付==========================");
		msql = "select * from ljagetclaim "
				+ " where FeeFinaType in ('PK') and FeeFinaType <> 'TF' "
				+ "   and substr(SubFeeOperationType,2,2) <> '02' "
				+ "   and exists (select riskcode from lirisktype where risktype1 in ('1', '3') "
				+ "   and trim(riskcode) = substr(LJAGetClaim.riskcode, 3, 3)) "
				+ "   and ActuGetNo in (" + tSql + ")";
		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		sqlbv14.sql(msql);
		sqlbv14.put("tStartDay", tStartDay);
		sqlbv14.put("tEndDay", tEndDay);
		sqlbv14.put("tManageCom", tManageCom);
		sqlbv14.put("tPolicyCom", tPolicyCom);
		sqlbv14.put("mPayMode", mPayMode);
		sqlbv14.put("mBankCode", mBankCode);
		sqlbv14.put("mOperator", mOperator);
		logger.debug("应付医疗给付:::" + msql);
		tLJAGetClaimSet = new LJAGetClaimSet();
		tLJAGetClaimDB = new LJAGetClaimDB();
		tLJAGetClaimSet = tLJAGetClaimDB.executeQuery(sqlbv14);

		double YLJF = 0;
		if (tLJAGetClaimSet.size() > 0) {
			for (int i = 1; i <= tLJAGetClaimSet.size(); i++) {
				LJAGetClaimSchema tLJAGetClaimSchema = new LJAGetClaimSchema();
				tLJAGetClaimSchema = tLJAGetClaimSet.get(i);
				YLJF = YLJF + tLJAGetClaimSchema.getPay();
				String EFSql = "select * from LJAGetclaim where FeeFinaType not in ('PK','TF','B') and actugetno='"
						+ "?actugetno?"
						+ "' and riskcode = '"
						+ "?riskcode?"
						+ "' and contno='"
						+ "?contno?" + "'";
				SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
				sqlbv15.sql(EFSql);
				sqlbv15.put("actugetno", tLJAGetClaimSchema.getActuGetNo());
				sqlbv15.put("riskcode", tLJAGetClaimSchema.getRiskCode());
				sqlbv15.put("contno", tLJAGetClaimSchema.getContNo());
				logger.debug("本次付费除赔款的其他信息查询::::" + EFSql);
				LJAGetClaimSet nLJAGetClaimSet = new LJAGetClaimSet();
				LJAGetClaimDB nLJAGetClaimDB = new LJAGetClaimDB();
				nLJAGetClaimSet = nLJAGetClaimDB.executeQuery(sqlbv15);
				if (nLJAGetClaimSet.size() > 0) {
					for (int t = 1; t <= nLJAGetClaimSet.size(); t++) {
						LJAGetClaimSchema nLJAGetClaimSchema = new LJAGetClaimSchema();
						nLJAGetClaimSchema = nLJAGetClaimSet.get(t);
						logger.debug("本次付费存在财务类型:::::::::::::::::"
								+ nLJAGetClaimSchema.getFeeFinaType());
						YLJF = YLJF + nLJAGetClaimSchema.getPay();
					}
				}
			}
		}
		if (YLJF != 0) {
			day = day + YLJF;
		}
		String yfylgfd = String.valueOf(YLJF);

		/** @todo 应付死亡给付 */
		logger.debug("=========================应付死亡给付==========================");

		msql = "select * from ljagetclaim where FeeFinaType = 'PK' and FeeFinaType <> 'TF' and substr(SubFeeOperationType,2,2)='02' "
				+ " and exists (select riskcode from lirisktype where risktype1 in ('1', '3') and trim(riskcode) = substr(LJAGetClaim.riskcode, 3, 3))"
				+ " and ActuGetNo in (" + tSql + ")";
		SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
		sqlbv16.sql(msql);
		sqlbv16.put("tStartDay", tStartDay);
		sqlbv16.put("tEndDay", tEndDay);
		sqlbv16.put("tManageCom", tManageCom);
		sqlbv16.put("tPolicyCom", tPolicyCom);
		sqlbv16.put("mPayMode", mPayMode);
		sqlbv16.put("mBankCode", mBankCode);
		sqlbv16.put("mOperator", mOperator);
		logger.debug("应付死亡给付:::" + msql);
		double SWJF = 0;
		tLJAGetClaimSet = new LJAGetClaimSet();
		tLJAGetClaimDB = new LJAGetClaimDB();
		tLJAGetClaimSet = tLJAGetClaimDB.executeQuery(sqlbv16);
		if (tLJAGetClaimSet.size() > 0) {
			for (int i = 1; i <= tLJAGetClaimSet.size(); i++) {
				LJAGetClaimSchema tLJAGetClaimSchema = new LJAGetClaimSchema();
				tLJAGetClaimSchema = tLJAGetClaimSet.get(i);
				SWJF = SWJF + tLJAGetClaimSchema.getPay();
				String EFSql = "select * from LJAGetclaim where FeeFinaType not in ('PK','TF','B') and actugetno='"
						+ "?actugetno?"
						+ "' and riskcode = '"
						+ "?riskcode?"
						+ "' and contno='"
						+ "?contno?" + "'";
				SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
				sqlbv17.sql(EFSql);
				sqlbv17.put("actugetno", tLJAGetClaimSchema.getActuGetNo());
				sqlbv17.put("riskcode", tLJAGetClaimSchema.getRiskCode());
				sqlbv17.put("contno", tLJAGetClaimSchema.getContNo());
				logger.debug("本次付费除赔款的其他信息查询::::" + EFSql);
				LJAGetClaimSet nLJAGetClaimSet = new LJAGetClaimSet();
				LJAGetClaimDB nLJAGetClaimDB = new LJAGetClaimDB();
				nLJAGetClaimSet = nLJAGetClaimDB.executeQuery(sqlbv17);
				if (nLJAGetClaimSet.size() > 0) {
					for (int t = 1; t <= nLJAGetClaimSet.size(); t++) {
						LJAGetClaimSchema nLJAGetClaimSchema = new LJAGetClaimSchema();
						nLJAGetClaimSchema = nLJAGetClaimSet.get(t);
						logger.debug("本次付费存在财务类型:::::::::::::::::"
								+ nLJAGetClaimSchema.getFeeFinaType());
						SWJF = SWJF + nLJAGetClaimSchema.getPay();
					}
				}
			}
		}

		if (SWJF != 0) {
			day = day + SWJF;
		}
		String yfswgfd = String.valueOf(SWJF);

		/** @todo 应付赔款支出 */
		logger.debug("=========================应付赔款支出==========================");

		msql = "select * from ljagetclaim "
				+ " where FeeFinaType in ('PK') and FeeFinaType <> 'TF' "
				+ "   and not exists (select riskcode from lirisktype "
				+ " where risktype1 in ('1','3') "
				+ "   and trim(riskcode)=substr(LJAGetClaim.riskcode,3,3))"
				+ "   and ActuGetNo in (" + tSql + ")";
		SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
		sqlbv18.sql(msql);
		sqlbv18.put("tStartDay", tStartDay);
		sqlbv18.put("tEndDay", tEndDay);
		sqlbv18.put("tManageCom", tManageCom);
		sqlbv18.put("tPolicyCom", tPolicyCom);
		sqlbv18.put("mPayMode", mPayMode);
		sqlbv18.put("mBankCode", mBankCode);
		sqlbv18.put("mOperator", mOperator);
		logger.debug("应付赔款支出:::" + msql);
		double PKZC = 0;
		tLJAGetClaimSet = new LJAGetClaimSet();
		tLJAGetClaimDB = new LJAGetClaimDB();
		tLJAGetClaimSet = tLJAGetClaimDB.executeQuery(sqlbv18);
		if (tLJAGetClaimSet.size() > 0) {
			for (int i = 1; i <= tLJAGetClaimSet.size(); i++) {
				LJAGetClaimSchema tLJAGetClaimSchema = new LJAGetClaimSchema();
				tLJAGetClaimSchema = tLJAGetClaimSet.get(i);
				PKZC = PKZC + tLJAGetClaimSchema.getPay();
				String EFSql = "select * from LJAGetclaim where FeeFinaType not in ('PK','TF','B') and actugetno='"
						+ "?actugetno?"
						+ "' and riskcode = '"
						+ "?riskcode?" + "'";
				SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
				sqlbv19.sql(EFSql);
				sqlbv19.put("actugetno", tLJAGetClaimSchema.getActuGetNo());
				sqlbv19.put("riskcode", tLJAGetClaimSchema.getRiskCode());
				logger.debug("本次付费除赔款的其他信息查询::::" + EFSql);
				LJAGetClaimSet nLJAGetClaimSet = new LJAGetClaimSet();
				LJAGetClaimDB nLJAGetClaimDB = new LJAGetClaimDB();
				nLJAGetClaimSet = nLJAGetClaimDB.executeQuery(sqlbv19);
				if (nLJAGetClaimSet.size() > 0) {
					for (int t = 1; t <= nLJAGetClaimSet.size(); t++) {
						LJAGetClaimSchema nLJAGetClaimSchema = new LJAGetClaimSchema();
						nLJAGetClaimSchema = nLJAGetClaimSet.get(t);
						logger.debug("本次付费存在财务类型:::::::::::::::::"
								+ nLJAGetClaimSchema.getFeeFinaType());
						PKZC = PKZC + nLJAGetClaimSchema.getPay();
					}
				}
			}
		}

		if (PKZC != 0) {
			day = day + PKZC;
		}
		String yfpkzcd = String.valueOf(PKZC);

		/** @todo 应付出单前退费 */
		logger.debug("=========================应付出单前退费==========================");
		msql = "select sum(getmoney) from LJFIGet "
				+ " where enteraccdate >= '"
				+ "?tStartDay?"
				+ "' "
				+ "   and enteraccdate <= '"
				+ "?tEndDay?"
				+ "' "
				+ "   and ManageCom like concat('"
				+ "?tManageCom?"
				+ "','%') "
				+ "   and PolicyCom like concat('"
				+ "?tPolicyCom?"
				+ "','%') "
				+ "   and (othernotype='4' or othernotype='8') and paymode <> '5'";
		if (mPayMode != null && !mPayMode.equals(""))
			msql = msql + " and paymode='" + "?mPayMode?" + "'";
		if (mBankCode != null && !mBankCode.equals(""))
			msql = msql + " and bankcode = '" + "?mBankCode?" + "'";
		if (mOperator != null && mOperator != "")
			msql = msql + " and trim(Operator) = '" + "?mOperator?" + "'";
		SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
		sqlbv20.sql(msql);
		sqlbv20.put("tStartDay", tStartDay);
		sqlbv20.put("tEndDay", tEndDay);
		sqlbv20.put("tManageCom", tManageCom);
		sqlbv20.put("tPolicyCom", tPolicyCom);
		sqlbv20.put("mPayMode", mPayMode);
		sqlbv20.put("mBankCode", mBankCode);
		sqlbv20.put("mOperator", mOperator);
		nSSRS = rExeSQL.execSQL(sqlbv20);
		String yfcdqtfd = nSSRS.GetText(1, 1);
		if (yfcdqtfd != null && !yfcdqtfd.equals("")
				&& !yfcdqtfd.equals("null")) {
			day = day + Double.parseDouble(yfcdqtfd);
		}
		if (yfcdqtfd == null || yfcdqtfd.equals("null")) {
			yfcdqtfd = "0";
		}

		/** @todo 应付预收续期保费 */
		logger.debug("=========================应付预收续期保费==========================");
		msql = "select sum(getmoney) from LJFIGet "
				+ " where enteraccdate >= '" + "?tStartDay?"+ "' "
				+ "   and enteraccdate <= '" + "?tEndDay?" + "' "
				+ "   and ManageCom like concat('" + "?tManageCom?" + "','%') "
				+ "   and PolicyCom like concat('" + "?tPolicyCom?" + "','%') "
				+ "   and othernotype='3' and paymode <> '5'";
		if (mPayMode != null && !mPayMode.equals(""))
			msql = msql + " and paymode='" + "?mPayMode?" + "'";
		if (mBankCode != null && !mBankCode.equals(""))
			msql = msql + " and bankcode='" + "?mBankCode?" + "'";
		if (mOperator != null && mOperator != "")
			msql = msql + " and trim(Operator) = '" + "?mOperator?" + "'";
		SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
		sqlbv21.sql(msql);
		sqlbv21.put("tStartDay", tStartDay);
		sqlbv21.put("tEndDay", tEndDay);
		sqlbv21.put("tManageCom", tManageCom);
		sqlbv21.put("tPolicyCom", tPolicyCom);
		sqlbv21.put("mPayMode", mPayMode);
		sqlbv21.put("mBankCode", mBankCode);
		sqlbv21.put("mOperator", mOperator);
		nSSRS = rExeSQL.execSQL(sqlbv21);
		String yfysxqbfd = nSSRS.GetText(1, 1);
		if (yfysxqbfd != null && !yfysxqbfd.equals("")
				&& !yfysxqbfd.equals("null")) {
			day = day + Double.parseDouble(yfysxqbfd);
		}
		if (yfysxqbfd == null || yfysxqbfd.equals("null")) {
			yfysxqbfd = "0";
		}

		/** @todo 赔款预付 */
		logger.debug("=========================赔款预付==========================");
		msql = "select sum((select pay from LJAGetClaim where FeeFinaType = 'B' and ActuGetNo=ljfiget.ActuGetNo )) from LJFIGet "
				+ " where enteraccdate >= '"
				+ "?tStartDay?"
				+ "' "
				+ "   and enteraccdate <= '"
				+ "?tEndDay?"
				+ "' "
				+ "   and ManageCom like concat('"
				+ "?tManageCom?"
				+ "','%') "
				+ "   and PolicyCom like concat('"
				+ "?tPolicyCom?"
				+ "','%') "
				+ "   and paymode <> '5'";
		if (mPayMode != null && !mPayMode.equals(""))
			msql = msql + " and paymode='" + "?mPayMode?" + "'";
		if (mBankCode != null && !mBankCode.equals(""))
			msql = msql + " and bankcode='" + "?mBankCode?" + "'";
		if (mOperator != null && mOperator != "")
			msql = msql + " and trim(Operator) = '" + "?mOperator?" + "'";
		SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
		sqlbv22.sql(msql);
		sqlbv22.put("tStartDay", tStartDay);
		sqlbv22.put("tEndDay", tEndDay);
		sqlbv22.put("tManageCom", tManageCom);
		sqlbv22.put("tPolicyCom", tPolicyCom);
		sqlbv22.put("mPayMode", mPayMode);
		sqlbv22.put("mBankCode", mBankCode);
		sqlbv22.put("mOperator", mOperator);
		nSSRS = rExeSQL.execSQL(sqlbv22);
		String pkyfd = nSSRS.GetText(1, 1);
		if (pkyfd != null && !pkyfd.equals("") && !pkyfd.equals("null")) {
			day = day + Double.parseDouble(pkyfd);

		}
		if (pkyfd == null || pkyfd.equals("null")) {
			pkyfd = "0";
		}

		/** @todo 续期冲证回退 */
		logger.debug("=========================续期冲证回退==========================");
		msql = "select sum(getmoney) from ljfiget "
				+ " where othernotype='9' and enteraccdate >= '" + "?tStartDay?"
				+ "' " + "   and enteraccdate <= '" + "?tEndDay?" + "' "
				+ "   and ManageCom like concat('" + "?tManageCom?" + "','%') "
				+ "   and PolicyCom like concat('" + "?tPolicyCom?" + "','%') "
				+ "   and paymode <> '5'";
		if (mPayMode != null && !mPayMode.equals(""))
			msql = msql + " and paymode='" + "?mPayMode?" + "'";
		if (mBankCode != null && !mBankCode.equals(""))
			msql = msql + " and bankcode='" + "?mBankCode?" + "'";
		if (mOperator != null && mOperator != "")
			msql = msql + " and trim(Operator) = '" + "?mOperator?" + "'";
		SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
		sqlbv23.sql(msql);
		sqlbv23.put("tStartDay", tStartDay);
		sqlbv23.put("tEndDay", tEndDay);
		sqlbv23.put("tManageCom", tManageCom);
		sqlbv23.put("tPolicyCom", tPolicyCom);
		sqlbv23.put("mPayMode", mPayMode);
		sqlbv23.put("mBankCode", mBankCode);
		sqlbv23.put("mOperator", mOperator);
		nSSRS = rExeSQL.execSQL(sqlbv23);
		String XQRollBack = nSSRS.GetText(1, 1);
		if (XQRollBack != null && !XQRollBack.equals("")
				&& !XQRollBack.equals("null")) {
			day = day + Double.parseDouble(XQRollBack);

		}
		if (XQRollBack == null || XQRollBack.equals("null")) {
			XQRollBack = "0";
		}
		/** @todo 保全回退 */
		logger.debug("=========================保全回退==========================");

		double BQHT = 0;
		msql = "select sum(getmoney) from ljfiget where othernotype='10' "
				+ "   and enteraccdate >= '"
				+ "?tStartDay?"
				+ "' "
				+ "   and enteraccdate <= '"
				+ "?tEndDay?"
				+ "' "
				+ "   and ManageCom like concat('"
				+ "?tManageCom?"
				+ "','%') "
				+ "   and PolicyCom like concat('"
				+ "?tPolicyCom?"
				+ "','%') "
				+ "   and paymode <> '5' "
				+ "   and ("
				+ " exists (select payno from ljapayperson where paytype='RB' and payno=ljfiget.actugetno)"
				+ " or exists (select contno from ljagetendorse where feeoperationtype = 'RB' and actugetno = ljfiget.actugetno)"
				+ " or exists (select actugetno from LJAGetDraw where feeoperationtype = 'RB' and actugetno = ljfiget.actugetno))";
		if (mPayMode != null && !mPayMode.equals(""))
			msql = msql + " and paymode='" + "?mPayMode?" + "'";
		if (mBankCode != null && !mBankCode.equals(""))
			msql = msql + " and bankcode='" + "?mBankCode?" + "'";
		if (mOperator != null && mOperator != "")
			msql = msql + " and trim(Operator) = '" + "?mOperator?" + "'";
		SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
		sqlbv24.sql(msql);
		sqlbv24.put("tStartDay", tStartDay);
		sqlbv24.put("tEndDay", tEndDay);
		sqlbv24.put("tManageCom", tManageCom);
		sqlbv24.put("tPolicyCom", tPolicyCom);
		sqlbv24.put("mPayMode", mPayMode);
		sqlbv24.put("mBankCode", mBankCode);
		sqlbv24.put("mOperator", mOperator);
		nSSRS = rExeSQL.execSQL(sqlbv24);
		String BQHT1 = nSSRS.GetText(1, 1);
		if (BQHT1 != null && !BQHT1.equals("") && !BQHT1.equals("null")) {
			day = day + Double.parseDouble(BQHT1);
			BQHT = BQHT + Double.parseDouble(BQHT1);
		}
		if (BQHT1 == null || BQHT1.equals("null")) {
			BQHT1 = "0";
		}

		String HHT = String.valueOf(BQHT);

		/** ------------------取操作员姓名--------------------------* */
		String OperSql = "select username from lduser where trim(usercode) = '"
				+ "?mGlobalInput?" + "'";
		logger.debug("OperSql===========" + OperSql);
		SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
		sqlbv25.sql(OperSql);
		sqlbv25.put("mGlobalInput", mGlobalInput.Operator);
		ExeSQL oExeSQL = new ExeSQL();
		nSSRS = oExeSQL.execSQL(sqlbv25);
		String mOperator = nSSRS.GetText(1, 1);

		DecimalFormat df = new DecimalFormat("0.00");
		String mDate = tStartDay + "至" + tEndDay;
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("PayToFeeCat.vts", "PRINT"); // 最好紧接着就初始化xml文档
		texttag.add("yfpkzcd", df.format(Double.parseDouble(yfpkzcd)));
		// texttag.add("yfpkzcm",yfpkzcm );
		texttag.add("yfcdqtfd", df.format(Double.parseDouble(yfcdqtfd)));
		// texttag.add("yfcdqtfm", yfcdqtfm);
		texttag.add("yfswgfd", df.format(Double.parseDouble(yfswgfd)));
		texttag.add("pkyfd", df.format(Double.parseDouble(pkyfd)));

		texttag.add("khycd", df.format(Double.parseDouble(khycd)));
		// texttag.add("khycm", khycm);
		texttag.add("yftbjd", df.format(Double.parseDouble(TBJ)));
		// texttag.add("yftbjm", yftbjm);
		texttag.add("yfylgfd", df.format(Double.parseDouble(yfylgfd)));
		// texttag.add("yfylgfm", yfylgfm);
		texttag.add("yfbhhld", df.format(Double.parseDouble(yfbhhld)));
		// texttag.add("yfbhhlm", yfbhhlm);
		texttag.add("yfysxqbfd", df.format(Double.parseDouble(yfysxqbfd)));
		// texttag.add("yfysxqbfm", yfysxqbfm);
		texttag.add("yfbhzydd", df.format(Double.parseDouble(yfbhzydd)));
		// texttag.add("yfbhzydm", yfbhzydm);
		texttag.add("yfcdhd", df.format(Double.parseDouble(yfcdhd)));
		// texttag.add("yfcdhm", yfcdhm);
		texttag.add("yfmqd", df.format(Double.parseDouble(yfmqd)));
		// texttag.add("yfmqm", yfmqm);
		texttag.add("yfnjd", df.format(Double.parseDouble(yfnjd)));
		// texttag.add("yfnjm", yfnjm);
		texttag.add("XQRollBack", df.format(Double.parseDouble(XQRollBack)));
		texttag.add("BQHT", df.format(Double.parseDouble(HHT)));
		// texttag.add("XQZTF", df.format(Double.parseDouble(XQZTF)));
		texttag.add("hjr", df.format(Double.parseDouble(String.valueOf(day))));
		// texttag.add("hjm", String.valueOf(month));

		texttag.add("sManageCom", tManageCom);
		texttag.add("GetType", GetType);
		texttag.add("BankCode", mBankName);
		texttag.add("ManageCom", mGlobalInput.ManageCom);
		texttag.add("sManageComName", sManageComName);
		texttag.add("ManageComName", bManageComName);
		texttag.add("sPolicyComName", sPolicyComName);
		texttag.add("Operator", mOperator);
		texttag.add("CurrentDate", tCurrentDate);
		texttag.add("CurrentTime", tCurrentTime);
		texttag.add("Date", mDate);
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		// xmlexport.outputDocumentToFile("d:\\", "lis"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}
}
