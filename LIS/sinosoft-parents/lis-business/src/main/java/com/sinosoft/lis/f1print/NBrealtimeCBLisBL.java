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
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: 实时承保业绩清单</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: sinosoft</p>
 * @author zy
 * @version 1.0
 * @update  :2007-1-29
 * @ReWrite :liuxs
 * @version :1.1
 */

public class NBrealtimeCBLisBL {
private static Logger logger = Logger.getLogger(NBrealtimeCBLisBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/* 业务处理相关变量 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private String ManageCom = "";
	private String BranchCode = "";
	private String StartDate = "";
	private String EndDate = "";
	private String AccType = "";
	double totalstd = 0;
	double totalprem = 0;

	/** 辅助计算数据 */
	boolean isCom = true;
	SSRS tSSRS = new SSRS();
	String sql = "";
	String tsql = "";
	ExeSQL tExeSQL = null;
	String ManageComName = "";
	String BranchAttrName = "";
	String name = "";

	public NBrealtimeCBLisBL() {
	}

	/**
	 * 本类入口方法,供高层调用
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData))
			return false;

		if (!queryData())
			return false;

		return true;
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
	private boolean getInputData(VData cInputData) {
		ManageCom = (String) cInputData.get(0);
		StartDate = (String) cInputData.get(1);
		BranchCode = (String) cInputData.get(2);
		EndDate = (String) cInputData.get(3);
		AccType = (String) cInputData.get(4); // 01按业务员统计02按险种统计03按保单统计04明细信息
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 5));
		// 添加非空判断
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "BusiBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private boolean queryData() {
		tExeSQL = new ExeSQL();
		ListTable tlistTable = new ListTable();
		tlistTable.setName("NBtask");
		String tPubSQL = "Select a.Managecom As 管理机构代码, (Select Trim(Name) From Lis.Ldcom Where Comcode = a.Managecom) As 管理机构名称,"
				+ " a.Contno As 保单号, a.Prtno As 投保书号, (select sum(p.sumactupaymoney) from lis.ljapayperson p where p.polno = a.polno and p.paytype = 'ZC' and p.paycount = 1) as 规模保费,"
				+ " ((select sum(p.sumactupaymoney)  from lis.ljapayperson p  where p.polno = a.polno  and p.paytype = 'ZC'  and p.paycount = 1)*"
				+ " (case when (select rate from lis.laratestandprem   where riskcode = a.riskcode"
				+ " and trim(year) = (case a.payintv when '0' then '0' else a.payendyear end)"
				+ " and trim(payintv) = trim(a.payintv)"
				+ " and a.cvalidate >= startdate"
				+ " and a.cvalidate <= enddate) is not null then (select rate from lis.laratestandprem   where riskcode = a.riskcode"
				+ " and trim(year) = (case a.payintv when '0' then '0' else a.payendyear end)"
				+ " and trim(payintv) = trim(a.payintv)"
				+ " and a.cvalidate >= startdate"
				+ " and a.cvalidate <= enddate)  else 0 end)) as 标准保费,"
				+ " (case a.Mainpolno when a.Polno then '主险' else '附险' end) As 主附险标记, a.Riskcode As 险种代码,"
				+ "(select riskshortname from lmrisk where riskcode = a.Riskcode) as 险种名称,"
				+ "  a.Payendyear As 缴费年期,"
				+ " (case a.Payintv when '0' then '趸缴' when '12' then '年缴' when '1' then '月缴' end) As 缴费间隔, a.Agentcode As 业务员号,"
				+ " (Select Name From Laagent Where Agentcode = Trim(a.Agentcode)) As 业务员姓名, "
				+ " (select substr(branchseries,1,12) from Lis.Labranchgroup where Agentgroup=trim(a.Agentgroup)) as 区代码, "
				+ "  (select name from Lis.Labranchgroup where Agentgroup=(select substr(branchseries,1,12) from Lis.Labranchgroup where Agentgroup=trim(a.Agentgroup))) as 区名称, "
				+ " (select substr(branchseries,14,12) from Lis.Labranchgroup where Agentgroup=trim(a.Agentgroup)) as 部代码, "
				+ " (select name from Lis.Labranchgroup where Agentgroup=(select substr(branchseries,14,12) from Lis.Labranchgroup where Agentgroup=trim(a.Agentgroup))) as 部名称, "
				+ " a.Agentgroup as 组代码, "
				+ " (select name from Lis.Labranchgroup where Agentgroup=trim(a.Agentgroup)) as 组名称, "
				+ " (select b.polapplydate from lccont b where b.contno=a.contno),"
				+ " (select b.signdate from lccont b where b.contno=a.contno),"
				+ " (select b.customgetpoldate from lccont b where b.contno=a.contno),"
				+ " (select b.getpoldate from lccont b where b.contno=a.contno) "
				+ " From Lis.Lcpol a ";

		try {
			if (AccType.equals("01") || AccType.equals("05")) {
				sql = tPubSQL
						+ " Where 1 = 1 and exists (select 'x' from ljapayperson where polno = a.polno And paytype='ZC' And paycount=1) "
						+ " and  a.signdate >=to_date('"+"?StartDate?"+ "','yyyy-mm-dd')  "
						+ " and  a.riskcode in (select riskcode from lmriskapp where riskprop='I')"
						+ " and  a.Managecom Like concat('" + "?ManageCom?" + "','%')";

				sql += " and exists (select 'x' from ljtempfee c where 1 = 1 ";
				if (AccType.equals("01")) { // 预收交单
					sql += " And c.Paydate Between to_date('"+"?StartDate?"+ "','yyyy-mm-dd') And to_date('"+"?EndDate?"+ "','yyyy-mm-dd') ";
				} else { // 预收到帐
					sql += " and c.Enteraccdate Between to_date('"+"?StartDate?"+ "','yyyy-mm-dd')" + " And to_date('"+"?EndDate?"+ "','yyyy-mm-dd') ";

				}
				sql += "  and c.otherno=a.contno and  c.managecom like concat('" + "?ManageCom?" + "','%') and c.confflag='1' and c.othernotype='6')";

			}
			if (AccType.equals("02") || AccType.equals("03")) {
				sql = tPubSQL + " Where 1 = 1 And a.Managecom Like concat('" + "?ManageCom?" + "','%')" + "  and a.signdate Between to_date('"+"?StartDate?"+ "','yyyy-mm-dd') And to_date('"+"?EndDate?"+ "','yyyy-mm-dd') ";
				if (AccType.equals("02")) // 承保出单
				{
					sql += " and exists (select 'x' from ljapayperson where polno = a.polno And paytype='ZC' And paycount=1)";
				} else // 承保回单
				{
					sql += " and exists "
							+ "(select 'x' from lccont where Customgetpoldate Between to_date('"+"?StartDate?"+ "','yyyy-mm-dd') And to_date('"+"?EndDate?"+ "','yyyy-mm-dd') and contno=a.contno) and exists (select 'x' from ljapayperson where polno = a.polno And paytype='ZC' And paycount=1)";
				}
			}

			if (AccType.equals("04")) {
				sql = tPubSQL + " Where 1 = 1 And a.Managecom Like concat('" + "?ManageCom?" + "','%')" + "  and a.signdate Between to_date('"+"?StartDate?"+ "','yyyy-mm-dd') And to_date('"+"?EndDate?"+ "','yyyy-mm-dd') ";

				sql += " and exists (select 'x' from ljapayperson where polno = a.polno And paytype='ZC' And paycount=1) And Not Exists"
						+ "  (Select 'X'  From Lis.Lpedoritem s "
						+ "  Where s.Contno = a.Contno And s.Edortype In ('CT', 'PT') and s.standbyflag1 ='1' And s.Edorvalidate >= to_date('"+"?StartDate?"+ "','yyyy-mm-dd'))";

			}
			sql += "order by a.agentgroup ";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("StartDate", StartDate);
			sqlbv1.put("EndDate", EndDate);
			sqlbv1.put("ManageCom", ManageCom);
			SSRS aSSRS = tExeSQL.execSQL(sqlbv1);
			int rows = aSSRS.getMaxRow();
			if (rows > 0) {
				for (int i = 1; i <= rows; i++) {
					// if (aSSRS.MaxRow > 0)
					// {
					try {
						String[] cols = new String[24];
						cols[0] = i + "";
						cols[1] = aSSRS.GetText(i, 1);
						cols[2] = aSSRS.GetText(i, 2);
						cols[3] = aSSRS.GetText(i, 3);
						cols[4] = aSSRS.GetText(i, 4);
						cols[5] = aSSRS.GetText(i, 5);
						cols[6] = aSSRS.GetText(i, 6);
						totalprem += Double.parseDouble(cols[5]);
						totalstd += Double.parseDouble(cols[6]);
						cols[7] = aSSRS.GetText(i, 7);
						cols[8] = aSSRS.GetText(i, 8);
						cols[9] = aSSRS.GetText(i, 9);
						cols[10] = aSSRS.GetText(i, 10);
						cols[11] = aSSRS.GetText(i, 11);
						cols[12] = aSSRS.GetText(i, 12);
						cols[13] = aSSRS.GetText(i, 13);
						cols[14] = aSSRS.GetText(i, 14);
						cols[15] = aSSRS.GetText(i, 15);
						cols[16] = aSSRS.GetText(i, 16);
						cols[17] = aSSRS.GetText(i, 17);
						cols[18] = aSSRS.GetText(i, 18);
						cols[19] = aSSRS.GetText(i, 19);
						cols[20] = aSSRS.GetText(i, 20);
						cols[21] = aSSRS.GetText(i, 21);
						cols[22] = aSSRS.GetText(i, 22);
						cols[23] = aSSRS.GetText(i, 23);
						tlistTable.add(cols);
					} catch (Exception ex) {
						buildError("getprintData", "没有需要打印的信息");
						logger.debug("没有需要打印的信息");
						return false;
					}
					// }
				}
			}

			XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
			xmlexport.createDocument("NBrealtimeCBLis.vts", "printer");

			TextTag texttag = new TextTag(); // 新建一个TextTag的实例
			texttag.add("StartDate", StartDate); // 输入统计时间
			texttag.add("EndDate", EndDate); // 输入统计时间
			tsql = "select name from ldcom where comcode ='" + "?ManageCom?" + "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tsql);
			sqlbv2.put("ManageCom", ManageCom);
			ManageComName = tExeSQL.getOneValue(sqlbv2);
			texttag.add("MngCom", ManageComName); // 填报单位
			texttag.add("CurrentDate", PubFun.getCurrentDate());
			texttag.add("sumprem", totalprem); // 填报单位
			texttag.add("sumstd", totalstd); // 填报单位
			texttag.add("Operator", mGlobalInput.Operator); // 填报单位
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			String[] col = new String[1];

			// logger.debug("====="+alistTable.size());
			xmlexport.addListTable(tlistTable, col);

			// xmlexport.outputDocumentToFile("c:\\", "AgentTemp"); //输出xml文档到文件
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "NBrealtimeCBLisBL";
			tError.functionName = "queryData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("end");
		return true;
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "8632";
		tG.Operator = "001";
		VData tVData = new VData();
		tVData.addElement("86210001");
		tVData.addElement("2006-08-01");
		tVData.addElement("");
		tVData.addElement("2006-08-01");
		tVData.addElement("04");
		tVData.addElement(tG);

		NBrealtimeCBLisBL tNBrealtimeCBLisBL = new NBrealtimeCBLisBL();
		if (!tNBrealtimeCBLisBL.submitData(tVData, "")) {
			logger.debug(tNBrealtimeCBLisBL.mErrors.toString());
			logger.debug("fail");
		} else {
			logger.debug("ok");
		}
	}
}
