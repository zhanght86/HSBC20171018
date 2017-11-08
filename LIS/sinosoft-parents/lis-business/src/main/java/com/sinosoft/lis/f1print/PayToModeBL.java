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
 * @author lh
 * @version 1.0
 */

public class PayToModeBL {
private static Logger logger = Logger.getLogger(PayToModeBL.class);
	String tManageCom = "";
	String tPolicyCom = "";
	String tEndDay = "";
	String tStartDay = "";
	double day = 0;

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */

	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	public PayToModeBL() {
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
			logger.debug("w我真的没有查到任何的值，所以现在我打算把我得到的给掉我的传回去");
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
		// 起始日期
		tEndDay = (String) mTransferData.getValueByName("EndDay");
		// 终止日期
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
				+ "?mGlobalInput?" + "' ";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(msql);
		sqlbv1.put("mGlobalInput", mGlobalInput.ManageCom);
		ExeSQL tExeSQL = new ExeSQL();
		nSSRS = tExeSQL.execSQL(sqlbv1);
		String bManageComName = nSSRS.GetText(1, 1);

		// 收付机构
		msql = "select name from ldcom where comcode = '" + "?tManageCom?" + "' ";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(msql);
		sqlbv2.put("tManageCom", tManageCom);
		ExeSQL rExeSQL = new ExeSQL();
		nSSRS = rExeSQL.execSQL(sqlbv2);
		String sManageComName = nSSRS.GetText(1, 1);

		// 管理机构
		msql = "select name from ldcom where comcode = '" + "?tPolicyCom?" + "' ";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(msql);
		sqlbv3.put("tPolicyCom", tPolicyCom);
		ExeSQL bExeSQL = new ExeSQL();
		nSSRS = bExeSQL.execSQL(sqlbv3);
		String sPolicyComName = nSSRS.GetText(1, 1);

		// 现金日
		msql = "select sum(getmoney) from ljfiget " + " where paymode = '1' "
				+ "   and enteraccdate >= '" + "?tStartDay?" + "' "
				+ "   and enteraccdate <= '" + "?tEndDay?" + "' "
				+ "   and ManageCom like concat('" + "?tManageCom?" + "','%') "
				+ "   and PolicyCom like concat('" + "?tPolicyCom?" + "','%')";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(msql);
		sqlbv4.put("tStartDay", tStartDay);
		sqlbv4.put("tEndDay", tEndDay);
		sqlbv4.put("tManageCom", tManageCom);
		sqlbv4.put("tPolicyCom", tPolicyCom);
		nSSRS = rExeSQL.execSQL(sqlbv4);
		String xjd = nSSRS.GetText(1, 1);
		if (xjd != null && !xjd.equals("") && !xjd.equals("null")) {
			day = day + Double.parseDouble(xjd);
		}
		if (xjd == null || xjd.equals("null")) {
			xjd = "0";
		}

		// 现金月
		// msql = " select sum(getmoney) from ljfiget where paymode = '1' and
		// enteraccdate >= (last_day(Add_months('" +
		// tStartDay + "',-1))+1) and enteraccdate <='" + tEndDay +
		// "' and ManageCom like '" + tManageCom + "%'";
		// nSSRS = rExeSQL.execSQL(msql);
		// String xjm = nSSRS.GetText(1, 1);
		// if (xjm != null && !xjm.equals("")&& !xjm.equals("null")) {
		// month = month + Double.parseDouble(xjm);
		// }
		// if (xjm == null || xjm.equals("null")) {
		// xjm ="0";
		// }

		// 现金支票

		msql = "select sum(getmoney) from ljfiget where paymode = '2' "
				+ "   and enteraccdate >= '" + "?tStartDay?" + "' "
				+ "   and enteraccdate <= '" + "?tEndDay?" + "' "
				+ "   and ManageCom like concat('" + "?tManageCom?" + "','%')"
				+ "   and PolicyCom like concat('" + "?tPolicyCom?" + "','%')";
		SQLwithBindVariables sqlbv5= new SQLwithBindVariables();
		sqlbv5.sql(msql);
		sqlbv5.put("tStartDay", tStartDay);
		sqlbv5.put("tEndDay", tEndDay);
		sqlbv5.put("tManageCom", tManageCom);
		sqlbv5.put("tPolicyCom", tPolicyCom);
		nSSRS = rExeSQL.execSQL(sqlbv5);
		String xjzpd = nSSRS.GetText(1, 1);
		if (xjzpd != null && !xjzpd.equals("") && !xjzpd.equals("null")) {
			day = day + Double.parseDouble(xjzpd);
		}
		if (xjzpd == null || xjzpd.equals("null")) {
			xjzpd = "0";
		}

		// msql = " select sum(getmoney) from ljfiget where paymode = '2' and
		// enteraccdate >=(last_day(Add_months('" +
		// tStartDay + "',-1))+1) and enteraccdate <='" + tEndDay +
		// "' and ManageCom like '" + tManageCom + "%'";
		// nSSRS = rExeSQL.execSQL(msql);
		// String xjzpm = nSSRS.GetText(1, 1);
		// if (xjzpm != null && !xjzpm.equals("")&& !xjzpm.equals("null")) {
		// month = month + Double.parseDouble(xjzpm);
		// }
		// if (xjzpm == null || xjzpm.equals("null")) {
		// xjzpm ="0";
		// }

		// 转帐支票

		msql = " select sum(getmoney) from ljfiget where paymode = '3' "
				+ "    and enteraccdate >= '" + "?tStartDay?" + "' "
				+ "    and enteraccdate <= '" + "?tEndDay?" + "' "
				+ "    and ManageCom like concat('" + "?tManageCom?" + "','%')"
				+ "   and PolicyCom like concat('" + "?tPolicyCom?" + "','%')";
		SQLwithBindVariables sqlbv6= new SQLwithBindVariables();
		sqlbv6.sql(msql);
		sqlbv6.put("tStartDay", tStartDay);
		sqlbv6.put("tEndDay", tEndDay);
		sqlbv6.put("tManageCom", tManageCom);
		sqlbv6.put("tPolicyCom", tPolicyCom);
		nSSRS = rExeSQL.execSQL(sqlbv6);
		String zzzpd = nSSRS.GetText(1, 1);
		if (zzzpd != null && !zzzpd.equals("") && !zzzpd.equals("null")) {
			day = day + Double.parseDouble(zzzpd);
		}
		if (zzzpd == null || zzzpd.equals("null")) {
			zzzpd = "0";
		}

		// msql = " select sum(getmoney) from ljfiget where paymode = '3' and
		// enteraccdate >=(last_day(Add_months('" +
		// tStartDay + "',-1))+1) and enteraccdate <='" + tEndDay +
		// "' and ManageCom like '" + tManageCom + "%'";
		// nSSRS = rExeSQL.execSQL(msql);
		// String zzzpm = nSSRS.GetText(1, 1);
		// if (zzzpm != null && !zzzpm.equals("")&& !zzzpm.equals("null")) {
		// month = month + Double.parseDouble(zzzpm);
		// }
		// if (zzzpm == null || zzzpm.equals("null")) {
		// zzzpm ="0";
		// }

		// 网上银行

		msql = "select sum(getmoney) from ljfiget where paymode = '7' "
				+ "   and enteraccdate >= '" + "?tStartDay?" + "' "
				+ "   and enteraccdate <= '" + "?tEndDay?" + "' "
				+ "   and ManageCom like concat('" + "?tManageCom?" + "','%')"
				+ "   and PolicyCom like concat('" + "?tPolicyCom?" + "','%')";
		SQLwithBindVariables sqlbv7= new SQLwithBindVariables();
		sqlbv7.sql(msql);
		sqlbv7.put("tStartDay", tStartDay);
		sqlbv7.put("tEndDay", tEndDay);
		sqlbv7.put("tManageCom", tManageCom);
		sqlbv7.put("tPolicyCom", tPolicyCom);
		nSSRS = rExeSQL.execSQL(sqlbv7);
		String wsyhd = nSSRS.GetText(1, 1);
		if (wsyhd != null && !wsyhd.equals("") && !wsyhd.equals("null")) {
			day = day + Double.parseDouble(wsyhd);
		}
		if (wsyhd == null || wsyhd.equals("null")) {
			wsyhd = "0";
		}

		// msql = " select sum(getmoney) from ljfiget where paymode = '7' and
		// enteraccdate >=(last_day(Add_months('" +
		// tStartDay + "',-1))+1) and enteraccdate <='" + tEndDay +
		// "' and ManageCom like '" + tManageCom + "%'";
		// nSSRS = rExeSQL.execSQL(msql);
		// String wsyhm = nSSRS.GetText(1, 1);
		// if (wsyhm != null && !wsyhm.equals("")&& !wsyhm.equals("null")) {
		// month = month + Double.parseDouble(wsyhm);
		// }
		// if (wsyhm == null || wsyhm.equals("null")) {
		// wsyhm ="0";
		// }

		// pos付款
		msql = "select sum(getmoney) from ljfiget where paymode = '6' "
				+ "   and enteraccdate >= '" + "?tStartDay?" + "' "
				+ "   and enteraccdate <= '" + "?tEndDay?" + "' "
				+ "   and ManageCom like concat('" + "?tManageCom?" + "','%') "
				+ "   and PolicyCom like concat('" + "?tPolicyCom?" + "','%')";
		SQLwithBindVariables sqlbv8= new SQLwithBindVariables();
		sqlbv8.sql(msql);
		sqlbv8.put("tStartDay", tStartDay);
		sqlbv8.put("tEndDay", tEndDay);
		sqlbv8.put("tManageCom", tManageCom);
		sqlbv8.put("tPolicyCom", tPolicyCom);
		nSSRS = rExeSQL.execSQL(sqlbv8);
		String posfkd = nSSRS.GetText(1, 1);
		if (posfkd != null && !posfkd.equals("") && !posfkd.equals("null")) {
			day = day + Double.parseDouble(posfkd);
		}
		if (posfkd == null || posfkd.equals("null")) {
			posfkd = "0";
		}

		// msql = " select sum(getmoney) from ljfiget where paymode = '6' and
		// enteraccdate >=(last_day(Add_months('" +
		// tStartDay + "',-1))+1) and enteraccdate <='" + tEndDay +
		// "' and ManageCom like '" + tManageCom + "%'";
		// nSSRS = rExeSQL.execSQL(msql);
		// String posfkm = nSSRS.GetText(1, 1);
		// if (posfkm != null && !posfkm.equals("")&& !posfkm.equals("null")) {
		// month = month + Double.parseDouble(posfkm);
		// }
		// if (posfkm == null || posfkm.equals("null")) {
		// posfkm ="0";
		// }

		// 银行付款
		msql = " select sum(getmoney) from ljfiget where paymode = '4' "
				+ "   and enteraccdate >= '" + "?tStartDay?" + "' "
				+ "   and enteraccdate <= '" + "?tEndDay?" + "' "
				+ "   and ManageCom like concat('" + "?tManageCom?" + "','%') "
				+ "   and PolicyCom like concat('" + "?tPolicyCom?" + "','%')";
		SQLwithBindVariables sqlbv9= new SQLwithBindVariables();
		sqlbv9.sql(msql);
		sqlbv9.put("tStartDay", tStartDay);
		sqlbv9.put("tEndDay", tEndDay);
		sqlbv9.put("tManageCom", tManageCom);
		sqlbv9.put("tPolicyCom", tPolicyCom);
		nSSRS = rExeSQL.execSQL(sqlbv9);
		String yhdsd = nSSRS.GetText(1, 1);
		if (yhdsd != null && !yhdsd.equals("") && !yhdsd.equals("null")) {
			day = day + Double.parseDouble(yhdsd);
		}
		if (yhdsd == null || yhdsd.equals("null")) {
			yhdsd = "0";
		}

		// 银保通
		msql = " select sum(getmoney) from ljfiget where paymode = 'A' "
				+ "   and enteraccdate >= '" + "?tStartDay?" + "' "
				+ "   and enteraccdate <= '" + "?tEndDay?" + "' "
				+ "   and ManageCom like concat('" + "?tManageCom?" + "','%') "
				+ "   and PolicyCom like concat('" + "?tPolicyCom?" + "','%')";
		SQLwithBindVariables sqlbv10= new SQLwithBindVariables();
		sqlbv10.sql(msql);
		sqlbv10.put("tStartDay", tStartDay);
		sqlbv10.put("tEndDay", tEndDay);
		sqlbv10.put("tManageCom", tManageCom);
		sqlbv10.put("tPolicyCom", tPolicyCom);
		nSSRS = rExeSQL.execSQL(sqlbv10);
		String ybtd = nSSRS.GetText(1, 1);
		if (ybtd != null && !ybtd.equals("") && !ybtd.equals("null")) {
			day = day + Double.parseDouble(ybtd);
		}
		if (ybtd == null || ybtd.equals("null")) {
			ybtd = "0";
		}
		logger.debug("银保通付费合计==========================" + ybtd);

		// msql = " select sum(getmoney) from ljfiget where paymode = '4' and
		// enteraccdate >=(last_day(Add_months('" +
		// tStartDay + "',-1))+1) and enteraccdate <='" + tEndDay +
		// "' and ManageCom like '" + tManageCom + "%'";
		// nSSRS = rExeSQL.execSQL(msql);
		// String yhdsm = nSSRS.GetText(1, 1);
		// if (yhdsm != null && !yhdsm.equals("")&& !yhdsm.equals("null")) {
		// month = month + Double.parseDouble(yhdsm);
		// }
		// if (yhdsm == null || yhdsm.equals("null")) {
		// yhdsm ="0";
		// }

		// //工本费
		//
		// msql = " select sum(getmoney) from ljagetendorse where feefinatype = 'GB' and
		// getdate >= '" +
		// tStartDay + "' and getdate <= '" + tEndDay +
		// "' and managecom like '" + tManageCom + "%'";
		// nSSRS = rExeSQL.execSQL(msql);
		// String bqgbd = nSSRS.GetText(1, 1);
		// if (bqgbd != null && !bqgbd.equals("")&& !bqgbd.equals("null")) {
		// day = day + Double.parseDouble(bqgbd);
		// }
		//
		// if (bqgbd == null || bqgbd.equals("null")) {
		// bqgbd = "0";
		// }
		//
		// msql = " select sum(getmoney) from ljagetendorse where feefinatype = 'GB' and
		// getdate>=(last_day(Add_months('" +
		// tStartDay + "',-1))+1) and getdate<='" + tEndDay +
		// "' and managecom like '" + tManageCom + "%' ";
		// nSSRS = rExeSQL.execSQL(msql);
		// String bqgbm = nSSRS.GetText(1, 1);
		// if (bqgbm != null && !bqgbm.equals("")&& !bqgbm.equals("null")) {
		// month = month + Double.parseDouble(bqgbm);
		// }
		// if (bqgbm == null || bqgbm.equals("null")) {
		// bqgbm = "0";
		// }
		//
		// //保全 保费
		//
		// msql = " select sum(getmoney) from ljagetendorse where feefinatype = 'BF' and
		// getdate >= '" +
		// tStartDay + "' and getdate <= '" + tEndDay +
		// "' and managecom like '" + tManageCom + "%' ";
		// nSSRS = rExeSQL.execSQL(msql);
		// String bqbfd = nSSRS.GetText(1, 1);
		// if (bqbfd != null && !bqbfd.equals("")&& !bqbfd.equals("null")) {
		// day = day + Double.parseDouble(bqbfd);
		// }
		// if (bqbfd == null || bqbfd.equals("null")) {
		// bqbfd = "0";
		// }
		//
		//
		// msql = " select sum(getmoney) from ljagetendorse where feefinatype = 'BF' and
		// getdate>=(last_day(Add_months('" +
		// tStartDay + "',-1))+1) and getdate<='" + tEndDay +
		// "' and managecom like '" + tManageCom + "%' ";
		// nSSRS = rExeSQL.execSQL(msql);
		// String bqbfm = nSSRS.GetText(1, 1);
		// if (bqbfm != null && !bqbfm.equals("")&& !bqbfm.equals("null")) {
		// month = month + Double.parseDouble(bqbfm);
		// }
		// if (bqbfm == null || bqbfm.equals("null")) {
		// bqbfm = "0";
		// }
		//
		//
		// double daylx = 0;
		// double monthlx = 0;
		// //利息
		// msql = " select sum(getmoney) from ljagetendorse where feefinatype = 'LX' and
		// getdate >= '" +
		// tStartDay + "' and getdate <= '" + tEndDay +
		// "' and managecom like '" + tManageCom + "%'";
		// nSSRS = rExeSQL.execSQL(msql);
		// String bqlxd = nSSRS.GetText(1, 1);
		// if (bqlxd != null && !bqlxd.equals("")&& !bqlxd.equals("null")) {
		// day = day + Double.parseDouble(bqlxd);
		// daylx = daylx + Double.parseDouble(bqlxd);
		// }
		// if (bqlxd == null || bqlxd.equals("null")) {
		// bqlxd = "0";
		// }
		//
		//
		// msql = " select sum(getmoney) from ljagetendorse where feefinatype = 'LX' and
		// getdate>=(last_day(Add_months('" +
		// tStartDay + "',-1))+1) and getdate<='" + tEndDay +
		// "' and managecom like '" + tManageCom + "%' ";
		// nSSRS = rExeSQL.execSQL(msql);
		// String bqlxm = nSSRS.GetText(1, 1);
		// if (bqlxm != null && !bqlxm.equals("")&& !bqlxm.equals("null")) {
		// month = month + Double.parseDouble(bqlxm);
		// monthlx = monthlx + Double.parseDouble(bqlxm);
		// }
		// if (bqlxm == null || bqlxm.equals("null")) {
		// bqlxm = "0";
		// }
		//
		// //贷款
		//
		// msql = " select sum(getmoney) from ljagetendorse where feefinatype = 'HK' and
		// getdate >= '" +
		// tStartDay + "' and getdate <= '" + tEndDay +
		// "' and managecom like '" + tManageCom + "%' ";
		// nSSRS = rExeSQL.execSQL(msql);
		// String dkd = nSSRS.GetText(1, 1);
		// if (dkd != null && !dkd.equals("")&& !dkd.equals("null")) {
		// day = day + Double.parseDouble(dkd);
		// daylx = daylx + Double.parseDouble(dkd);
		// }
		// if (dkd == null || dkd.equals("null")) {
		// dkd = "0";
		// }
		//
		//
		// msql = " select sum(getmoney) from ljagetendorse where feefinatype = 'HK' and
		// getdate>=(last_day(Add_months('" +
		// tStartDay + "',-1))+1) and getdate<='" + tEndDay +
		// "' and managecom like '" + tManageCom + "%' ";
		// nSSRS = rExeSQL.execSQL(msql);
		// String dky = nSSRS.GetText(1, 1);
		// if (dky != null && !dky.equals("")&& !dky.equals("null")) {
		// month = month + Double.parseDouble(dky);
		// monthlx = monthlx + Double.parseDouble(dky);
		// }
		// if (dky == null || dky.equals("null")) {
		// dky = "0";
		// }

		/** ------------------取操作员姓名--------------------------* */
		String OperSql = "select username from lduser where trim(usercode) = '"
				+ "?mGlobalInput?" + "'";
		logger.debug("OperSql===========" + OperSql);
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql(OperSql);
		sqlbv11.put("mGlobalInput", mGlobalInput.Operator);
		ExeSQL oExeSQL = new ExeSQL();
		nSSRS = oExeSQL.execSQL(sqlbv11);
		String mOperator = nSSRS.GetText(1, 1);

		String mDate = tStartDay + "至" + tEndDay;
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("PayToFeeMode.vts", "PRINT"); // 最好紧接着就初始化xml文档
		texttag.add("xjd", xjd);
		// texttag.add("xjm",xjm );
		texttag.add("xjzpd", xjzpd);
		// texttag.add("xjzpm", xjzpm);
		texttag.add("zzzpd", zzzpd);
		// texttag.add("zzzpm",zzzpm );
		texttag.add("wsyhd", wsyhd);
		// texttag.add("wsyhm",wsyhm );
		texttag.add("posfkd", posfkd);
		// texttag.add("posfkm",posfkm );
		texttag.add("yhdsd", yhdsd);
		// texttag.add("yhdsm", yhdsm);
		texttag.add("ybtd", ybtd);
		logger.debug("ybtd added in texttag");
		// texttag.add("ybtd", ybtd);
		texttag.add("hjr", String.valueOf(day));
		// texttag.add("hjm", String.valueOf(month));
		texttag.add("sManageCom", tManageCom);
		texttag.add("ManageCom", mGlobalInput.ManageCom);
		texttag.add("sManageComName", sManageComName);
		texttag.add("sPolicyComName", sPolicyComName);
		texttag.add("ManageComName", bManageComName);
		texttag.add("CurrentDate", tCurrentDate);
		texttag.add("CurrentTime", tCurrentTime);
		texttag.add("Operator", mOperator);
		texttag.add("Date", mDate);
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		// xmlexport.outputDocumentToFile("e:\\", "atest"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}
}
