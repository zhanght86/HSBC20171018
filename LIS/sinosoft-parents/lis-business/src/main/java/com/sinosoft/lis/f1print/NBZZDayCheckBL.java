package com.sinosoft.lis.f1print;
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
 * @author gaoht
 * @version 1.0
 */

public class NBZZDayCheckBL {
private static Logger logger = Logger.getLogger(NBZZDayCheckBL.class);
	String tManageCom = "";
	String tPolicyCom = "";
	String tEndDay = "";
	String tStartDay = "";
	double day = 0;
	double month = 0;
	String mOperator = "";
	String mPayType = "";

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */

	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	public NBZZDayCheckBL() {
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
		// 付费方式
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

		String msql = "select name from ldcom where comcode = '"
				+ "?mGlobalInput?" + "' ";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(msql);
		sqlbv1.put("mGlobalInput", mGlobalInput.ManageCom);
		ExeSQL tExeSQL = new ExeSQL();
		nSSRS = tExeSQL.execSQL(sqlbv1);
		String bManageComName = nSSRS.GetText(1, 1);

		// 收付机构
		msql = "select name from ldcom where comcode = '" + "?tManageCom?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(msql);
		sqlbv2.put("tManageCom", tManageCom);
		ExeSQL rExeSQL = new ExeSQL();
		nSSRS = rExeSQL.execSQL(sqlbv2);
		String sManageComName = nSSRS.GetText(1, 1);

		// 管理机构
		msql = "select name from ldcom where comcode = '" + "?tPolicyCom?" + "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(msql);
		sqlbv3.put("tPolicyCom", tPolicyCom);
		ExeSQL bExeSQL = new ExeSQL();
		nSSRS = bExeSQL.execSQL(sqlbv3);
		String sPolicyComName = nSSRS.GetText(1, 1);
		ExeSQL yExeSQL = new ExeSQL();

		// 查询收费类型
		msql = "select trim(codename) from ldcode "
				+ " where codetype='paytype' and code='" + "?mPayType?" + "'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(msql);
		sqlbv4.put("mPayType", mPayType);
		rExeSQL = new ExeSQL();
		nSSRS = rExeSQL.execSQL(sqlbv4);
		String PayType = "";
		if (nSSRS.getMaxRow() > 0)
			PayType = nSSRS.GetText(1, 1);

		// 内部转账——应付满期给付——日

		logger.debug("=======================内部转账——应付满期给付===================");

		logger.debug("=========================应付满期给付==========================");
		msql = "select sum(getmoney) from LJFIGet "
				+ " where enteraccdate >= '" + "?tStartDay?" + "' "
				+ "   and enteraccdate <= '" + "?tEndDay?" + "' "
				+ "   and ManageCom like concat('" + "?tManageCom?" + "','%') "
				+ "   and PolicyCom like concat('" + "?tPolicyCom?" + "','%') "
				+ "   and ActuGetNo in ((select ActuGetNo from LJAGetDraw "
				+ " where FeeFinaType = 'EF'))" + " and paymode = '5'";
		if (mPayType != null && !mPayType.equals(""))
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = ljfiget.actugetno and othernotype='"
					+ "?mPayType?" + "')";
		if (mOperator != null && mOperator != "")
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = ljfiget.actugetno and operator='"
					+ "?mOperator?" + "')";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(msql);
		sqlbv5.put("tStartDay", tStartDay);
		sqlbv5.put("tEndDay", tEndDay);
		sqlbv5.put("tManageCom", tManageCom);
		sqlbv5.put("tPolicyCom", tPolicyCom);
		sqlbv5.put("mOperator", mOperator);
		sqlbv5.put("mPayType", mPayType);
		nSSRS = yExeSQL.execSQL(sqlbv5);
		String InTralFinMQDay = nSSRS.GetText(1, 1);
		if (InTralFinMQDay.equals("null"))
			InTralFinMQDay = "0";

		/** @todo 应付年金给付 */
		logger.debug("=========================应付年金给付==========================");
		msql = " select sum(getmoney) from LJFIGet "
				+ "  where enteraccdate >= '" + "?tStartDay?" + "' "
				+ "    and enteraccdate <='" + "?tEndDay?" + "' "
				+ "    and ManageCom like concat('" + "?tManageCom?" + "','%') "
				+ "    and PolicyCom like concat('" + "?tPolicyCom?" + "','%') "
				+ "    and ActuGetNo in (select ActuGetNo from LJAGetDraw "
				+ "  where FeeFinaType = 'YF')" + "    and paymode = '5'";
		if (mPayType != null && !mPayType.equals(""))
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = ljfiget.actugetno and othernotype='"
					+ "?mPayType?" + "')";
		if (mOperator != null && mOperator != "")
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = ljfiget.actugetno and operator='"
					+ "?mOperator?" + "')";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(msql);
		sqlbv6.put("tStartDay", tStartDay);
		sqlbv6.put("tEndDay", tEndDay);
		sqlbv6.put("tManageCom", tManageCom);
		sqlbv6.put("tPolicyCom", tPolicyCom);
		sqlbv6.put("mPayType", mPayType);
		sqlbv6.put("mOperator", mOperator);
		nSSRS = yExeSQL.execSQL(sqlbv6);
		String InTralFinNJDay = nSSRS.GetText(1, 1);
		if (InTralFinNJDay.equals("null"))
			InTralFinNJDay = "0";

		// 内部转账——犹豫期撤单——日
		logger.debug("=======================内部转账——犹豫期撤单===================");
		msql = "select (-1)*sum(getmoney) from LJAGetEndorse "
				+ " where actugetno in (select actugetno from LJFIGet "
				+ " where enteraccdate >= '" + "?tStartDay?" + "' "
				+ "   and enteraccdate <= '" + "?tEndDay?" + "' "
				+ "   and ManageCom like concat('" + "?tManageCom?" + "','%') "
				+ "   and PolicyCom like concat('" + "?tPolicyCom?" + "','%') "
				+ "   and paymode = '5')" + " and FeeFinaType in ('TF','GB')";
		if (mOperator != null && !mOperator.equals("")
				&& mOperator.length() > 0)
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = LJAGetEndorse.actugetno and operator='"
					+ "?mOperator?" + "')";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = LJAGetEndorse.actugetno and othernotype='"
					+ "?mPayType?" + "')";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(msql);
		sqlbv7.put("tStartDay", tStartDay);
		sqlbv7.put("tEndDay", tEndDay);
		sqlbv7.put("tManageCom", tManageCom);
		sqlbv7.put("tPolicyCom", tPolicyCom);
		sqlbv7.put("mOperator", mOperator);
		sqlbv7.put("mPayType", mPayType);
		nSSRS = yExeSQL.execSQL(sqlbv7);
		String InTralFinWTDay1 = nSSRS.GetText(1, 1);
		if (InTralFinWTDay1.equals("null"))
			InTralFinWTDay1 = "0";
		double TF = Double.parseDouble(InTralFinWTDay1);
		msql = "select sum(pay) from LJAGetClaim "
				+ " where FeeFinaType = 'TF' "
				+ "   and actugetno in (select actugetno from LJFIGet "
				+ " where enteraccdate >= '" + "?tStartDay?" + "' "
				+ "   and enteraccdate <= '" + "?tEndDay?" + "' "
				+ "   and ManageCom like concat('" + "?tManageCom?"+ "','%') "
				+ "   and PolicyCom like concat('" + "?tPolicyCom?" + "','%') "
				+ "   and paymode = '5')";
		if (mOperator != null && !mOperator.equals("")
				&& mOperator.length() > 0)
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = LJAGetClaim.actugetno and operator='"
					+ "?mOperator?" + "')";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = LJAGetClaim.actugetno and othernotype='"
					+ "?mPayType?" + "')";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(msql);
		sqlbv8.put("tStartDay", tStartDay);
		sqlbv8.put("tEndDay", tEndDay);
		sqlbv8.put("tManageCom", tManageCom);
		sqlbv8.put("tPolicyCom", tPolicyCom);
		sqlbv8.put("mOperator", mOperator);
		sqlbv8.put("mPayType", mPayType);
		nSSRS = yExeSQL.execSQL(sqlbv8);
		String InClaimDay = nSSRS.GetText(1, 1);
		if (InClaimDay.equals("null"))
			InClaimDay = "0";
		TF = TF + Double.parseDouble(InClaimDay);
		String InTralFinWTDay = String.valueOf(TF);

		// 内部转账——保护质押贷款——日
		logger.debug("=======================内部转账——保护质押贷款===================");
		msql = "select sum(getmoney) from LJFIGet "
				+ " where enteraccdate >= '" + "?tStartDay?" + "' "
				+ "   and enteraccdate <= '" + "?tEndDay?" + "' "
				+ "   and ManageCom like concat('" + "?tManageCom?" + "','%') "
				+ "   and PolicyCom like concat('" + "?tPolicyCom?" + "','%') "
				+ "   and ActuGetNo in (select ActuGetNo from LJAGetEndorse "
				+ " where FeeFinaType = 'DK' and FeeOperationType = 'LN') "
				+ "   and paymode = '5'";
		if (mPayType != null && !mPayType.equals(""))
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = ljfiget.actugetno and othernotype='"
					+ "?mPayType?" + "')";
		if (mOperator != null && mOperator != "")
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = ljfiget.actugetno and operator='"
					+ "?mOperator?" + "')";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(msql);
		sqlbv9.put("tStartDay", tStartDay);
		sqlbv9.put("tEndDay", tEndDay);
		sqlbv9.put("tManageCom", tManageCom);
		sqlbv9.put("tPolicyCom", tPolicyCom);
		sqlbv9.put("mOperator", mOperator);
		sqlbv9.put("mPayType", mPayType);
		nSSRS = yExeSQL.execSQL(sqlbv9);
		String InTralFinDKDay = nSSRS.GetText(1, 1);
		if (InTralFinDKDay.equals("null"))
			InTralFinDKDay = "0";

		// 内部转账——保户红利——日
		logger.debug("=======================内部转账——保户红利===================");
		msql = "select sum(a.paymoney) from ljtempfeeclass a,LJAGetEndorse b "
				+ " where a.ChequeNo = b.actugetno and a.otherno = b.EndorsementNo "
				+ "   and b.FeeFinaType = 'CB' and a.paymode = '5' "
				+ "   and a.EnterAccDate >= '"
				+ "?tStartDay?"
				+ "' "
				+ "   and a.EnterAccDate <= '"
				+ "?tEndDay?"
				+ "' "
				+ "   and a.managecom like concat('"
				+ "?tManageCom?"
				+ "','%') "
				+ "   and a.policycom like concat('"
				+ "?tPolicyCom?"
				+ "','%') "
				+ "   and (a.EnterAccDate is not null or a.EnterAccDate <>'3000-01-01')";
		if (mOperator != null && !mOperator.equals("")
				&& mOperator.length() > 0)
			msql = msql + " and trim(a.Operator) = '" + "?mOperator?" + "'";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql + " and trim(a.Othernotype) = '" + "?mPayType?" + "'";
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql(msql);
		sqlbv10.put("tStartDay", tStartDay);
		sqlbv10.put("tEndDay", tEndDay);
		sqlbv10.put("tManageCom", tManageCom);
		sqlbv10.put("tPolicyCom", tPolicyCom);
		sqlbv10.put("mOperator", mOperator);
		sqlbv10.put("mPayType", mPayType);
		nSSRS = yExeSQL.execSQL(sqlbv10);
		String InTralFinHLDay = nSSRS.GetText(1, 1);
		if (InTralFinHLDay.equals("null"))
			InTralFinHLDay = "0";

		// 内部转账——应付预期续收报费—— 日
		logger.debug("=======================内部转账——应付预期续收报费===================");
		msql = "select sum(a.paymoney) from ljtempfeeclass a, LJAGet b "
				+ " where a.ChequeNo = b.actugetno "
				+ "   and a.paymode = '5' and b.othernotype in ('3') "
				+ "   and a.EnterAccDate >= '"
				+ "?tStartDay?"
				+ "' "
				+ "   and a.EnterAccDate <= '"
				+ "?tEndDay?"
				+ "' "
				+ "   and a.managecom like concat('"
				+ "?tManageCom?"
				+ "','%') "
				+ "   and a.policycom like concat('"
				+ "?tPolicyCom?"
				+ "','%') "
				+ "   and (a.EnterAccDate is not null or a.EnterAccDate <>'3000-01-01')";

		if (mOperator != null && !mOperator.equals("")
				&& mOperator.length() > 0)
			msql = msql + " and trim(a.Operator) = '" + "?mOperator?" + "'";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql + " and trim(a.Othernotype) = '" + "?mPayType?" + "'";
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql(msql);
		sqlbv11.put("tStartDay", tStartDay);
		sqlbv11.put("tEndDay", tEndDay);
		sqlbv11.put("tManageCom", tManageCom);
		sqlbv11.put("tPolicyCom", tPolicyCom);
		sqlbv11.put("mOperator", mOperator);
		sqlbv11.put("mPayType", mPayType);
		nSSRS = yExeSQL.execSQL(sqlbv11);
		String InTralFinYSDay = nSSRS.GetText(1, 1);
		if (InTralFinYSDay.equals("null"))
			InTralFinYSDay = "0";

		// 内部转账——客户预存——日
		logger.debug("=======================内部转账——客户预存===================");
		msql = "select sum(sumgetmoney) from ljaget "
				+ " where actugetno in (select actugetno from ljfiget "
				+ " where enteraccdate >= '" + "?tStartDay?" + "' "
				+ "   and enteraccdate <= '" + "?tEndDay?" + "' "
				+ "   and ManageCom like concat('" + "?tManageCom?" + "','%') "
				+ "   and PolicyCom like concat('" + "?tPolicyCom?" + "','%') "
				+ "   and paymode='5') and othernotype='YC' ";
		if (mOperator != null && !mOperator.equals("")
				&& mOperator.length() > 0)
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = ljaget.actugetno and operator='"
					+ "?mOperator?" + "')";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = ljaget.actugetno and othernotype='"
					+ "?mPayType?" + "')";
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		sqlbv12.sql(msql);
		sqlbv12.put("tStartDay", tStartDay);
		sqlbv12.put("tEndDay", tEndDay);
		sqlbv12.put("tManageCom", tManageCom);
		sqlbv12.put("tPolicyCom", tPolicyCom);
		sqlbv12.put("mOperator", mOperator);
		sqlbv12.put("mPayType", mPayType);
		nSSRS = yExeSQL.execSQL(sqlbv12);
		String InTralFinYCDay = nSSRS.GetText(1, 1);
		if (InTralFinYCDay.equals("null"))
			InTralFinYCDay = "0";

		/*
		 * ================================================= 内部转账——应付退保金——日
		 * 
		 * ================================================
		 */
		logger.debug("=======================内部转账——应付退保金===================");

		double TB = 0;

		// 保全部分
		msql = "select (-1)*sum(getmoney) from LJAGetEndorse a"
				+ " where 1=1 "
				+ " and exists (select actugetno from LJAGetEndorse b where FeeFinaType = 'TB' and actugetno=a.actugetno) "
				+ " and FeeFinaType<>'TF'"
				+ "   and a.ActuGetNo in (select ActuGetNo from ljfiget "
				+ " where enteraccdate >= '" + "?tStartDay?" + "' "
				+ "   and enteraccdate <= '" + "?tEndDay?" + "' "
				+ "   and ManageCom like concat('" + "?tManageCom?" + "','%') "
				+ "   and PolicyCom like concat('" + "?tPolicyCom?" + "','%') "
				+ "   and paymode = '5')";

		if (mOperator != null && !mOperator.equals("")
				&& mOperator.length() > 0)
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = a.actugetno and operator='"
					+ "?mOperator?" + "')";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = a.actugetno and othernotype='"
					+ "?mPayType?" + "')";
		SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
		sqlbv13.sql(msql);
		sqlbv13.put("tStartDay", tStartDay);
		sqlbv13.put("tEndDay", tEndDay);
		sqlbv13.put("tManageCom", tManageCom);
		sqlbv13.put("tPolicyCom", tPolicyCom);
		sqlbv13.put("mOperator", mOperator);
		sqlbv13.put("mPayType", mPayType);
		nSSRS = yExeSQL.execSQL(sqlbv13);
		String InTralFinCTDay1 = nSSRS.GetText(1, 1);
		if (InTralFinCTDay1.equals("null"))
			InTralFinCTDay1 = "0";

		TB = TB + Double.parseDouble(InTralFinCTDay1);

		// 理赔部分
		msql = "select * from LJAGetclaim " + " where FeeFinaType = 'TB' "
				+ "   and ActuGetNo in (select ActuGetNo from ljfiget "
				+ " where enteraccdate >= '" + "?tStartDay?" + "' "
				+ "   and enteraccdate <= '" + "?tEndDay?" + "' "
				+ "   and ManageCom like '" + "?tManageCom?" + "%' "
				+ "   and PolicyCom like '" + "?tPolicyCom?"+ "%' "
				+ "   and paymode = '5')";
		if (mOperator != null && !mOperator.equals("")
				&& mOperator.length() > 0)
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = LJAGetclaim.actugetno and operator='"
					+ "?mOperator?" + "')";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = LJAGetclaim.actugetno and othernotype='"
					+ "?mPayType?" + "')";
		double LPTB = 0;
		logger.debug("退保金理赔===" + msql);
		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		sqlbv14.sql(msql);
		sqlbv14.put("tStartDay", tStartDay);
		sqlbv14.put("tEndDay", tEndDay);
		sqlbv14.put("tManageCom", tManageCom);
		sqlbv14.put("tPolicyCom", tPolicyCom);
		sqlbv14.put("mOperator", mOperator);
		sqlbv14.put("mPayType", mPayType);
		LJAGetClaimSet tLJAGetClaimSet = new LJAGetClaimSet();
		LJAGetClaimDB tLJAGetClaimDB = new LJAGetClaimDB();
		tLJAGetClaimSet = tLJAGetClaimDB.executeQuery(sqlbv14);
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
				SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
				sqlbv15.sql(EFSql);
				sqlbv15.put("actugetno", tLJAGetClaimSchema.getActuGetNo());
				sqlbv15.put("riskcode", tLJAGetClaimSchema.getRiskCode());
				sqlbv15.put("contno", tLJAGetClaimSchema.getContNo());
				LJAGetClaimSet nLJAGetClaimSet = new LJAGetClaimSet();
				LJAGetClaimDB nLJAGetClaimDB = new LJAGetClaimDB();
				nLJAGetClaimSet = nLJAGetClaimDB.executeQuery(sqlbv15);
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
		String InTralFinCTDay = String.valueOf(TB);

		/*
		 * ===========================================================
		 * 内部转账——应付医疗给付——日
		 * 
		 * ============================================================
		 */

		logger.debug("=======================内部转账——应付医疗给付===================");

		msql = "select * from ljagetclaim "
				+ " where FeeFinaType in ('PK') and FeeFinaType <> 'TF' "
				+ "   and substr(SubFeeOperationType,2,2) <> '02' "
				+ "   and exists (select riskcode from lirisktype where risktype1 in ('1', '3') "
				+ "   and trim(riskcode) = substr(LJAGetClaim.riskcode, 3, 3)) "
				+ "   and ActuGetNo in (select ActuGetNo from ljfiget "
				+ " where enteraccdate between '" + "?tStartDay?" + "' and '"
				+ "?tEndDay?" + "' " + "   and ManageCom like concat('" + "?tManageCom?"
				+ "','%') " + "   and PolicyCom like concat('" + "?tPolicyCom?" + "','%') "
				+ "   and paymode = '5')";

		if (mOperator != null && !mOperator.equals("")
				&& mOperator.length() > 0)
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = LJAGetclaim.actugetno and operator='"
					+ "?mOperator?" + "')";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = LJAGetclaim.actugetno and othernotype='"
					+ "?mPayType?" + "')";
		SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
		sqlbv16.sql(msql);
		sqlbv16.put("tStartDay", tStartDay);
		sqlbv16.put("tEndDay", tEndDay);
		sqlbv16.put("tManageCom", tManageCom);
		sqlbv16.put("tPolicyCom", tPolicyCom);
		sqlbv16.put("mOperator", mOperator);
		sqlbv16.put("mPayType", mPayType);
		logger.debug("应付医疗给付:::" + msql);
		tLJAGetClaimSet = new LJAGetClaimSet();
		tLJAGetClaimDB = new LJAGetClaimDB();
		tLJAGetClaimSet = tLJAGetClaimDB.executeQuery(sqlbv16);

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
						YLJF = YLJF + nLJAGetClaimSchema.getPay();
					}
				}
			}
		}

		String HospitalGetDay = String.valueOf(YLJF);

		/*
		 * ========================================= 内部转账——死亡给付——日
		 * ========================================
		 */

		logger.debug("=======================内部转账——死亡给付===================");
		msql = "select * from ljagetclaim where FeeFinaType = 'PK' and FeeFinaType <> 'TF' and substr(SubFeeOperationType,2,2)='02' "
				+ " and exists (select riskcode from lirisktype where risktype1 in ('1', '3') and trim(riskcode) = substr(LJAGetClaim.riskcode, 3, 3))"
				+ " and ActuGetNo in (select ActuGetNo from ljfiget where enteraccdate between '"
				+ "?tStartDay?"
				+ "' and '"
				+ "?tEndDay?"
				+ "' and ManageCom like concat('"
				+ "?tManageCom?"
				+ "','%') and PolicyCom like concat('"
				+ "?tPolicyCom?"
				+ "','%')  and paymode = '5')";

		if (mOperator != null && !mOperator.equals("")
				&& mOperator.length() > 0)
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = LJAGetclaim.actugetno and operator='"
					+ "?mOperator?" + "')";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = LJAGetclaim.actugetno and othernotype='"
					+ "?mPayType?" + "')";
		SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
		sqlbv18.sql(msql);
		sqlbv18.put("tStartDay", tStartDay);
		sqlbv18.put("tEndDay", tEndDay);
		sqlbv18.put("tManageCom", tManageCom);
		sqlbv18.put("tPolicyCom", tPolicyCom);
		sqlbv18.put("mOperator", mOperator);
		sqlbv18.put("mPayType", mPayType);
		logger.debug("应付死亡给付:::" + msql);
		double SWJF = 0;
		tLJAGetClaimSet = new LJAGetClaimSet();
		tLJAGetClaimDB = new LJAGetClaimDB();
		tLJAGetClaimSet = tLJAGetClaimDB.executeQuery(sqlbv18);
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
				SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
				sqlbv19.sql(EFSql);
				sqlbv19.put("actugetno", tLJAGetClaimSchema.getActuGetNo());
				sqlbv19.put("riskcode", tLJAGetClaimSchema.getRiskCode());
				sqlbv19.put("contno", tLJAGetClaimSchema.getContNo());
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
						SWJF = SWJF + nLJAGetClaimSchema.getPay();
					}
				}
			}
		}

		String DeathGetDay = String.valueOf(SWJF);

		// 内部转账——赔款支出——日
		logger.debug("=======================内部转账——赔款支出===================");
		msql = "select * from ljagetclaim "
				+ " where FeeFinaType in ('PK') and FeeFinaType <> 'TF' "
				+ "   and not exists (select riskcode from lirisktype "
				+ " where risktype1 in ('1','3') "
				+ "   and trim(riskcode)=substr(LJAGetClaim.riskcode,3,3))"
				+ "   and ActuGetNo in (select ActuGetNo from ljfiget "
				+ " where enteraccdate between '" + "?tStartDay?" + "' and '"
				+ "?tEndDay?" + "' " + "   and ManageCom like concat('" + "?tManageCom?"
				+ "','%') " + "   and PolicyCom like concat('" + "?tPolicyCom?" + "','%') "
				+ "   and paymode = '5')";
		if (mOperator != null && !mOperator.equals("")
				&& mOperator.length() > 0)
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = LJAGetclaim.actugetno and operator='"
					+ "?mOperator?" + "')";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = LJAGetclaim.actugetno and othernotype='"
					+ "?mPayType?" + "')";
		SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
		sqlbv20.sql(msql);
		sqlbv20.put("tStartDay", tStartDay);
		sqlbv20.put("tEndDay", tEndDay);
		sqlbv20.put("tManageCom", tManageCom);
		sqlbv20.put("tPolicyCom", tPolicyCom);
		sqlbv20.put("mOperator", mOperator);
		sqlbv20.put("mPayType", mPayType);
		logger.debug("应付赔款支出:::" + msql);
		double PKZC = 0;
		tLJAGetClaimSet = new LJAGetClaimSet();
		tLJAGetClaimDB = new LJAGetClaimDB();
		tLJAGetClaimSet = tLJAGetClaimDB.executeQuery(sqlbv20);
		if (tLJAGetClaimSet.size() > 0) {
			for (int i = 1; i <= tLJAGetClaimSet.size(); i++) {
				LJAGetClaimSchema tLJAGetClaimSchema = new LJAGetClaimSchema();
				tLJAGetClaimSchema = tLJAGetClaimSet.get(i);
				PKZC = PKZC + tLJAGetClaimSchema.getPay();
				String EFSql = "select * from LJAGetclaim where FeeFinaType not in ('PK','TF','B') and actugetno='"
						+ "?actugetno?"
						+ "' and riskcode = '"
						+ "?riskcode?" + "'";
				SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
				sqlbv21.sql(EFSql);
				sqlbv21.put("actugetno", tLJAGetClaimSchema.getActuGetNo());
				sqlbv21.put("riskcode", tLJAGetClaimSchema.getRiskCode());
				logger.debug("本次付费除赔款的其他信息查询::::" + EFSql);
				LJAGetClaimSet nLJAGetClaimSet = new LJAGetClaimSet();
				LJAGetClaimDB nLJAGetClaimDB = new LJAGetClaimDB();
				nLJAGetClaimSet = nLJAGetClaimDB.executeQuery(sqlbv21);
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
		String PKGetDay = String.valueOf(PKZC);

		// //内部转账——预付赔款——日
		logger.debug("=======================内部转账——预付赔款===================");
		msql = "select sum(a.paymoney) from ljtempfeeclass a,ljagetclaim b "
				+ " where a.ChequeNo = b.actugetno and a.otherno = b.otherno "
				+ "   and a.paymode = '5' and b.feeoperationtype='B' and b.feefinatype='B' "
				+ "   and a.EnterAccDate >= '"
				+ "?tStartDay?"
				+ "' "
				+ "   and a.EnterAccDate <= '"
				+ "?tEndDay?"
				+ "' "
				+ "   and a.managecom like concat('"
				+ "?tManageCom?"
				+ "','%') "
				+ "   and a.policycom like concat('"
				+ "?tPolicyCom?"
				+ "','%') "
				+ "   and (a.EnterAccDate is not null or a.EnterAccDate <>'3000-01-01')";
		if (mOperator != null && !mOperator.equals("")
				&& mOperator.length() > 0)
			msql = msql + " and trim(a.Operator) = '" + "?mOperator?" + "'";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql + " and trim(a.Othernotype) = '" + "?mPayType?" + "'";
		SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
		sqlbv22.sql(msql);
		sqlbv22.put("tStartDay", tStartDay);
		sqlbv22.put("tEndDay", tEndDay);
		sqlbv22.put("tManageCom", tManageCom);
		sqlbv22.put("tPolicyCom", tPolicyCom);
		sqlbv22.put("mOperator", mOperator);
		sqlbv22.put("mPayType", mPayType);
		nSSRS = yExeSQL.execSQL(sqlbv22);
		String YFGetDay = nSSRS.GetText(1, 1);
		if (YFGetDay.equals("null"))
			YFGetDay = "0";

		// 内部转账——暂收费退费——日
		logger.debug("=======================内部转账——暂收费退费===================");
		msql = "select sum(a.paymoney) from ljtempfeeclass a, LJAGet b "
				+ " where a.ChequeNo = b.actugetno "
				+ "   and a.paymode = '5' and b.othernotype in ('4','8') "
				+ "   and a.EnterAccDate >= '"
				+ "?tStartDay?"
				+ "' "
				+ "   and a.EnterAccDate <= '"
				+ "?tEndDay?"
				+ "' "
				+ "   and a.managecom like concat('"
				+ "?tManageCom?"
				+ "','%') "
				+ "   and a.policycom like concat('"
				+ "?tPolicyCom?"
				+ "','%') "
				+ "   and (a.EnterAccDate is not null or a.EnterAccDate <>'3000-01-01')";

		if (mOperator != null && !mOperator.equals("")
				&& mOperator.length() > 0)
			msql = msql + " and trim(a.Operator) = '" + "?mOperator?" + "'";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql + " and trim(a.Othernotype) = '" + "?mPayType?" + "'";
		SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
		sqlbv23.sql(msql);
		sqlbv23.put("tStartDay", tStartDay);
		sqlbv23.put("tEndDay", tEndDay);
		sqlbv23.put("tManageCom", tManageCom);
		sqlbv23.put("tPolicyCom", tPolicyCom);
		sqlbv23.put("mOperator", mOperator);
		sqlbv23.put("mPayType", mPayType);
		nSSRS = yExeSQL.execSQL(sqlbv23);
		String TempFeeGetDay = nSSRS.GetText(1, 1);
		if (TempFeeGetDay.equals("null"))
			TempFeeGetDay = "0";

		logger.debug("=========================续期冲证回退==========================");
		msql = "select sum(getmoney) from ljfiget "
				+ " where othernotype='9' and enteraccdate >= '" + "?tStartDay?"
				+ "' " + "   and enteraccdate <= '" + "?tEndDay?" + "' "
				+ "   and ManageCom like '" + "?tManageCom?" + "%' "
				+ "   and PolicyCom like '" + "?tPolicyCom?" + "%' "
				+ "   and paymode = '5'";
		if (mOperator != null && !mOperator.equals("")
				&& mOperator.length() > 0)
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = ljfiget.actugetno and operator='"
					+ "?mOperator?" + "')";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql
					+ " and exists (select 1 from ljtempfeeclass where chequeno = ljfiget.actugetno and othernotype='"
					+ "?mPayType?" + "')";
		SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
		sqlbv24.sql(msql);
		sqlbv24.put("tStartDay", tStartDay);
		sqlbv24.put("tEndDay", tEndDay);
		sqlbv24.put("tManageCom", tManageCom);
		sqlbv24.put("tPolicyCom", tPolicyCom);
		sqlbv24.put("mOperator", mOperator);
		sqlbv24.put("mPayType", mPayType);
		nSSRS = yExeSQL.execSQL(sqlbv24);
		String RnGetDay = nSSRS.GetText(1, 1);
		if (RnGetDay.equals("null"))
			RnGetDay = "0";

		// 日总计
		msql = "select sum(paymoney) from ljtempfeeclass "
				+ " where EnterAccDate >= '"
				+ "?tStartDay?"
				+ "' "
				+ "   and EnterAccDate <= '"
				+ "?tEndDay?"
				+ "' "
				+ "   and paymode='5' "
				+ "   and managecom like concat('"
				+ "?tManageCom?"
				+ "','%') "
				+ "   and policycom like concat('"
				+ "?tPolicyCom?"
				+ "','%') "
				+ "   and (EnterAccDate is not null or EnterAccDate <>'3000-01-01')";
		if (mOperator != null && !mOperator.equals("")
				&& mOperator.length() > 0)
			msql = msql + " and trim(Operator) = '" + "?mOperator?" + "'";
		if (mPayType != null && !mPayType.equals("") && mPayType.length() > 0)
			msql = msql + " and trim(Othernotype) = '" + "?mPayType?" + "'";
		SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
		sqlbv25.sql(msql);
		sqlbv25.put("tStartDay", tStartDay);
		sqlbv25.put("tEndDay", tEndDay);
		sqlbv25.put("tManageCom", tManageCom);
		sqlbv25.put("tPolicyCom", tPolicyCom);
		sqlbv25.put("mOperator", mOperator);
		sqlbv25.put("mPayType", mPayType);
		ExeSQL pExeSQL = new ExeSQL();
		nSSRS = pExeSQL.execSQL(sqlbv25);
		String ProvideCorpus = "";
		ProvideCorpus = nSSRS.GetText(1, 1);
		if (ProvideCorpus.equals("null"))
			ProvideCorpus = "0";

		/** ------------------取操作员姓名--------------------------* */
		String OperSql = "select username from lduser where trim(usercode) = '"
				+ "?mGlobalInput?" + "'";
		logger.debug("OperSql===========" + OperSql);
		SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
		sqlbv26.sql(OperSql);
		sqlbv26.put("mGlobalInput", mGlobalInput.Operator);
		ExeSQL oExeSQL = new ExeSQL();
		nSSRS = oExeSQL.execSQL(sqlbv26);
		String mOperator = nSSRS.GetText(1, 1);

		String mDate = tStartDay + "至" + tEndDay;
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("NBZZDayCheck.vts", "PRINT"); // 最好紧接着就初始化xml文档
		texttag.add("ProvideCorpus", ProvideCorpus);
		texttag.add("sManageCom", tManageCom);
		texttag.add("ManageCom", mGlobalInput.ManageCom);
		texttag.add("sManageComName", sManageComName);
		texttag.add("ManageComName", bManageComName);
		texttag.add("sPolicyComName", sPolicyComName);
		texttag.add("CurrentDate", tCurrentDate);
		texttag.add("CurrentTime", tCurrentTime);
		texttag.add("PayType", PayType);
		texttag.add("Date", mDate);
		texttag.add("Operator", mOperator);
		texttag.add("InTralFinMQDay", InTralFinMQDay);

		texttag.add("InTralFinNJDay", InTralFinNJDay);

		texttag.add("InTralFinWTDay", InTralFinWTDay);

		texttag.add("InTralFinDKDay", InTralFinDKDay);

		texttag.add("InTralFinHLDay", InTralFinHLDay);

		texttag.add("InTralFinYSDay", InTralFinYSDay);

		texttag.add("InTralFinYCDay", InTralFinYCDay);

		texttag.add("InTralFinCTDay", InTralFinCTDay);

		texttag.add("HospitalGetDay", HospitalGetDay);

		texttag.add("DeathGetDay", DeathGetDay);

		texttag.add("PKGetDay", PKGetDay);
		texttag.add("RNGetDay", RnGetDay);
		texttag.add("TempFeeGetDay", TempFeeGetDay);

		texttag.add("YFGetDay", YFGetDay);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		// xmlexport.outputDocumentToFile("e:\\", "atest"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}
}
