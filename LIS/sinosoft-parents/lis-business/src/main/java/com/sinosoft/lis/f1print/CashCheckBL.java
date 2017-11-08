package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
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
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lh
 * @version 1.0
 */

public class CashCheckBL {
private static Logger logger = Logger.getLogger(CashCheckBL.class);
	String tManageCom = "";
	String tEndDay = "";
	String tStartDay = "";
	double day = 0;
	double month = 0;

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	private String mSelString = "";

	// 业务处理相关变量
	/** 全局数据 */

	private GlobalInput mGlobalInput = new GlobalInput();
	// private LCPolSchema mLCPolSchema = new LCPolSchema();
	// private LJSPaySchema mLJSPaySchema = new LJSPaySchema();
	// private LOPRTManagerSchema mLOPRTManagerSchema = new
	// LOPRTManagerSchema();
	// private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	// private VData mLOPRTManagerSchemas = new VData();
	// private int mSchemaNum = 0;

	private TransferData mTransferData = new TransferData();

	public CashCheckBL() {
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
		// mDay = (String[]) cInputData.get(0);
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		tManageCom = (String) mTransferData.getValueByName("ManageCom");
		if (tManageCom.equals("") || tManageCom == null) {
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
		SSRS tSSRS = new SSRS();
		SSRS nSSRS = new SSRS();

		// String PayFlag = request.getParameter("FinDayType");
		// String tBranchAttr = request.getParameter("BranchAttr")+"%";
		String tCurrentDate = PubFun.getCurrentDate();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String msql = "select name from ldcom where comcode = '"
				+ "?comcode?"+ "'";
		sqlbv.sql(msql);
		sqlbv.put("comcode", mGlobalInput.ManageCom);
		ExeSQL tExeSQL = new ExeSQL();
		nSSRS = tExeSQL.execSQL(sqlbv);
		String bManageComName = nSSRS.GetText(1, 1);

		msql = "select name from ldcom where comcode = '" + "?tManageCom?" + "'";
		sqlbv.sql(msql);
		sqlbv.put("tManageCom", tManageCom);
		ExeSQL rExeSQL = new ExeSQL();
		nSSRS = rExeSQL.execSQL(sqlbv);

		String sManageComName = nSSRS.GetText(1, 1);
		// 现金
		// msql =" select sum(getmoney) from ljfiget where paymode = '1' and
		// ConfDate >='" +
		// tStartDay + "' and ConfDate <='" + tEndDay + "' and ManageCom = '" +
		// tManageCom + "'";
		// nSSRS = rExeSQL.execSQL(msql);
		// String xjd = nSSRS.GetText(1, 1);
		// if (xjd != null && !xjd.equals("")&& !xjd.equals("null")) {
		// day = day + Double.parseDouble(xjd);
		//
		// }
		// if (xjd == null || xjd.equals("null")) {
		// xjd ="0";
		// }
		//
		//
		//
		// msql = " select sum(getmoney) from ljfiget where paymode = '1' and
		// ConfDate >= (last_day(Add_months('" +
		// tStartDay + "',-1))+1) and ConfDate <='" + tEndDay +
		// "' and ManageCom = '" + tManageCom + "'";
		// nSSRS = rExeSQL.execSQL(msql);
		// String xjm = nSSRS.GetText(1, 1);
		// if (xjm != null && !xjm.equals("")&& !xjm.equals("null")) {
		// month = month + Double.parseDouble(xjm);
		// }
		// if (xjm == null || xjm.equals("null")) {
		// xjm ="0";
		// }
		//
		//
		// //现金支票
		//
		// msql =
		// " select sum(getmoney) from ljfiget where paymode = '3' and ConfDate
		// >='" +
		// tStartDay + "' and ConfDate <='" + tEndDay + "' and ManageCom = '" +
		// tManageCom + "'";
		// nSSRS = rExeSQL.execSQL(msql);
		// String xjzpd = nSSRS.GetText(1, 1);
		// if (xjzpd != null && !xjzpd.equals("")&& !xjzpd.equals("null")) {
		// day = day + Double.parseDouble(xjzpd);
		// }
		// if (xjzpd == null || xjzpd.equals("null")) {
		// xjzpd ="0";
		// }
		//
		//
		// msql = " select sum(getmoney) from ljfiget where paymode = '3' and
		// ConfDate >=(last_day(Add_months('" +
		// tStartDay + "',-1))+1) and ConfDate <='" + tEndDay +
		// "' and ManageCom = '" + tManageCom + "'";
		// nSSRS = rExeSQL.execSQL(msql);
		// String xjzpm = nSSRS.GetText(1, 1);
		// if (xjzpm != null && !xjzpm.equals("")&& !xjzpm.equals("null")) {
		// month = month + Double.parseDouble(xjzpm);
		// }
		// if (xjzpm == null || xjzpm.equals("null")) {
		// xjzpm ="0";
		// }
		//
		//
		// //转帐支票
		//
		// msql =
		// " select sum(getmoney) from ljfiget where paymode = '4' and ConfDate
		// >='" +
		// tStartDay + "' and ConfDate <='" + tEndDay + "' and ManageCom = '" +
		// tManageCom + "'";
		// nSSRS = rExeSQL.execSQL(msql);
		// String zzzpd = nSSRS.GetText(1, 1);
		// if (zzzpd != null && !zzzpd.equals("")&& !zzzpd.equals("null")) {
		// day = day + Double.parseDouble(zzzpd);
		// }
		// if (zzzpd == null || zzzpd.equals("null")) {
		// zzzpd ="0";
		// }
		//
		//
		// msql = " select sum(getmoney) from ljfiget where paymode = '4' and
		// ConfDate >=(last_day(Add_months('" +
		// tStartDay + "',-1))+1) and ConfDate <='" + tEndDay +
		// "' and ManageCom = '" + tManageCom + "'";
		// nSSRS = rExeSQL.execSQL(msql);
		// String zzzpm = nSSRS.GetText(1, 1);
		// if (zzzpm != null && !zzzpm.equals("")&& !zzzpm.equals("null")) {
		// month = month + Double.parseDouble(zzzpm);
		// }
		// if (zzzpm == null || zzzpm.equals("null")) {
		// zzzpm ="0";
		// }
		//
		//
		// //网上银行
		//
		// logger.debug("======================================我这执行了一个大的数字");
		//
		// msql =
		// " select sum(getmoney) from ljfiget where paymode = '9' and ConfDate
		// >='" +
		// tStartDay + "' and ConfDate <='" + tEndDay + "' and ManageCom = '" +
		// tManageCom + "'";
		// nSSRS = rExeSQL.execSQL(msql);
		// String wsyhd = nSSRS.GetText(1, 1);
		// if (wsyhd != null && !wsyhd.equals("")&& !wsyhd.equals("null")) {
		// day = day + Double.parseDouble(wsyhd);
		// }
		// if (wsyhd == null || wsyhd.equals("null")) {
		// wsyhd ="0";
		// }
		//
		//
		//
		// msql = " select sum(getmoney) from ljfiget where paymode = '9' and
		// ConfDate >=(last_day(Add_months('" +
		// tStartDay + "',-1))+1) and ConfDate <='" + tEndDay +
		// "' and ManageCom = '" + tManageCom + "'";
		// nSSRS = rExeSQL.execSQL(msql);
		// String wsyhm = nSSRS.GetText(1, 1);
		// if (wsyhm != null && !wsyhm.equals("")&& !wsyhm.equals("null")) {
		// month = month + Double.parseDouble(wsyhm);
		// }
		// if (wsyhm == null || wsyhm.equals("null")) {
		// wsyhm ="0";
		// }
		//
		// //pos付款
		//
		//
		// msql =
		// " select sum(getmoney) from ljfiget where paymode = '6' and ConfDate
		// >='" +
		// tStartDay + "' and ConfDate <='" + tEndDay + "' and ManageCom = '" +
		// tManageCom + "'";
		// nSSRS = rExeSQL.execSQL(msql);
		// String posfkd = nSSRS.GetText(1, 1);
		// if (posfkd != null && !posfkd.equals("")&& !posfkd.equals("null")) {
		// day = day + Double.parseDouble(posfkd);
		// }
		// if (posfkd == null || posfkd.equals("null")) {
		// posfkd ="0";
		// }
		//
		//
		// msql = " select sum(getmoney) from ljfiget where paymode = '6' and
		// ConfDate >=(last_day(Add_months('" +
		// tStartDay + "',-1))+1) and ConfDate <='" + tEndDay +
		// "' and ManageCom = '" + tManageCom + "'";
		// nSSRS = rExeSQL.execSQL(msql);
		// String posfkm = nSSRS.GetText(1, 1);
		// if (posfkm != null && !posfkm.equals("")&& !posfkm.equals("null")) {
		// month = month + Double.parseDouble(posfkm);
		// }
		// if (posfkm == null || posfkm.equals("null")) {
		// posfkm ="0";
		// }
		//
		// //银行付款
		// msql =" select sum(getmoney) from ljfiget where paymode = '7' and
		// ConfDate >='" +
		// tStartDay + "' and ConfDate <='" + tEndDay + "' and ManageCom = '" +
		// tManageCom + "'";
		// nSSRS = rExeSQL.execSQL(msql);
		// String yhdsd = nSSRS.GetText(1, 1);
		// if (yhdsd != null && !yhdsd.equals("")&& !yhdsd.equals("null")) {
		// day = day + Double.parseDouble(yhdsd);
		// }
		// if (yhdsd == null || yhdsd.equals("null")) {
		// yhdsd ="0";
		// }
		//
		//
		// msql = " select sum(getmoney) from ljfiget where paymode = '7' and
		// ConfDate >=(last_day(Add_months('" +
		// tStartDay + "',-1))+1) and ConfDate <='" + tEndDay +
		// "' and ManageCom = '" + tManageCom + "'";
		// nSSRS = rExeSQL.execSQL(msql);
		// String yhdsm = nSSRS.GetText(1, 1);
		// if (yhdsm != null && !yhdsm.equals("")&& !yhdsm.equals("null")) {
		// month = month + Double.parseDouble(yhdsm);
		// }
		// if (yhdsm == null || yhdsm.equals("null")) {
		// yhdsm ="0";
		// }
		//

		// //工本费
		//
		// msql = " select sum(getmoney) from ljagetendorse where feefinatype =
		// 'GB' and getdate >= '" +
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
		// msql = " select sum(getmoney) from ljagetendorse where feefinatype =
		// 'GB' and getdate>=(last_day(Add_months('" +
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
		// msql = " select sum(getmoney) from ljagetendorse where feefinatype =
		// 'BF' and getdate >= '" +
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
		// msql = " select sum(getmoney) from ljagetendorse where feefinatype =
		// 'BF' and getdate>=(last_day(Add_months('" +
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
		// msql = " select sum(getmoney) from ljagetendorse where feefinatype =
		// 'LX' and getdate >= '" +
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
		// msql = " select sum(getmoney) from ljagetendorse where feefinatype =
		// 'LX' and getdate>=(last_day(Add_months('" +
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
		// msql = " select sum(getmoney) from ljagetendorse where feefinatype =
		// 'HK' and getdate >= '" +
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
		// msql = " select sum(getmoney) from ljagetendorse where feefinatype =
		// 'HK' and getdate>=(last_day(Add_months('" +
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
		msql = "select  sum(LJFIget.GetMoney) from LJFIget,ldcode "
				+ "where LJFIget.Othernotype = ldcode.code  and LJFIget.ConfDate >= '"
				+ "?tStartDay?" + "' and  LJFIget.ConfDate <= '" + "?tEndDay?"
				+ "' and  LJFIget.PolicyCom like concat('" + "?tManageCom?" + "','%')";
		sqlbv.sql(msql);
		sqlbv.put("tStartDay", tStartDay);
		sqlbv.put("tEndDay", tEndDay);
		sqlbv.put("tManageCom", tManageCom);
		nSSRS = rExeSQL.execSQL(sqlbv);
		String hjr = nSSRS.GetText(1, 1);
		// if (hjr != null && !dky.equals("")&& !hjr.equals("null")) {
		// month = month + Double.parseDouble(hjr);
		// monthlx = monthlx + Double.parseDouble(dky);
		// }
		if (hjr == null || hjr.equals("null")) {
			hjr = "0";
		}

		msql = " select  ldcode.codename, sum(LJFIget.GetMoney) from LJFIget,ldcode "
				+ " where  LJFIget.Othernotype = ldcode.code and ldcode.codetype = 'othernotype'  and LJFIget.ConfDate >= '"
				+ "?tStartDay?"
				+ "' and  LJFIget.ConfDate <= '"
				+ "?tEndDay?"
				+ "' and  LJFIget.PolicyCom like concat('"+ "?tManageCom?"+ "','%')  group by ldcode.codename";
		sqlbv.sql(msql);
		sqlbv.put("tStartDay", tStartDay);
		sqlbv.put("tEndDay", tEndDay);
		sqlbv.put("tManageCom", tManageCom);
		nSSRS = rExeSQL.execSQL(sqlbv);
		ListTable tlistTable = new ListTable();
		String strArr[] = null;
		tlistTable.setName("BILL");
		for (int i = 1; i <= nSSRS.MaxRow; i++) {
			strArr = new String[2];
			for (int j = 1; j <= nSSRS.MaxCol; j++) {
				strArr[j - 1] = nSSRS.GetText(i, j);

			}
			tlistTable.add(strArr);
		}

		String mDate = tStartDay + "至" + tEndDay;
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("CashCheck.vts", "PRINT"); // 最好紧接着就初始化xml文档
		// texttag.add("xjd",xjd );
		// texttag.add("xjm",xjm );
		// texttag.add("xjzpd",xjzpd );
		// texttag.add("xjzpm", xjzpm);
		// texttag.add("zzzpd",zzzpd );
		// texttag.add("zzzpm",zzzpm );
		// texttag.add("wsyhd",wsyhd );
		// texttag.add("wsyhm",wsyhm );
		// texttag.add("posfkd", posfkd);
		// texttag.add("posfkm",posfkm );
		// texttag.add("yhdsd",yhdsd );
		// texttag.add("yhdsm", yhdsm);
		texttag.add("hjr", hjr);
		// texttag.add("hjm", String.valueOf(month));
		texttag.add("sManageCom", tManageCom);
		texttag.add("ManageCom", mGlobalInput.ManageCom);
		texttag.add("sManageComName", sManageComName);
		texttag.add("ManageComName", bManageComName);
		texttag.add("CurrentDate", tCurrentDate);
		texttag.add("Date", mDate);
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		if (tlistTable.size() > 0) {
			xmlexport.addListTable(tlistTable, strArr);
		}
		// xmlexport.outputDocumentToFile("e:\\", "atest"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}
}
