package com.sinosoft.lis.f1print;
import java.util.Vector;

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
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author 路明
 * function :print InputEfficiency or print CheckEfficiency Business Logic layer
 * @version 1.0
 * @date 2006-12-28
 */

public class ReportLisBL {
private static Logger logger = Logger.getLogger(ReportLisBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strMngCom;
	private String strStartDate;
	private String strEndDate;
	private String strPrintDate;
	private String comGrade = "";
	private String strSQL;
	Vector finalAeon = new Vector();
	Vector departmentBin = new Vector();
	Vector groupBin = new Vector();
	Vector tryNewWay = new Vector();
	private int sumBills = 0;
	private int areaNo = 0;
	private int sumBillCom = 0;
	private XmlExport newXmlExport = new XmlExport();
	private int getJ = 1;
	private String strAgentGroup = "";
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mCurrentDate = PubFun.getCurrentDate();// 当前日期

	public ReportLisBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();

		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	/***************************************************************************
	 * Name : getInputData() function :receive data from jsp and check date
	 * check :judge whether startdate begin enddate return :true or false
	 */
	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		strStartDate = (String) cInputData.get(1);
		strEndDate = (String) cInputData.get(2);
		strPrintDate = (String) cInputData.get(3);
		if (strPrintDate == null || strPrintDate.trim().equals("")) {
			strPrintDate = mCurrentDate;
		}
		// strAgentGroup= (String) cInputData.get(2);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	/***************************************************************************
	 * name : buildError function : Prompt error message return :error message
	 */

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "FaultyReportLisBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/***************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData() {
		// 选择临时文件读取目录
		ExeSQL exeSql = new ExeSQL();
		SSRS testSSRS = new SSRS();
		int j_count = 0;
		ListTable alistTable = new ListTable(); // Create a ListTable instance
		alistTable.setName("RISK"); // Appoint to a sepcial flag

		SSRS FenSSRS = new SSRS();
		SSRS ZhongSSRS = new SSRS();
		SSRS YingSSRS = new SSRS();
		// 查询分公司SQL
		String tFenSQL = "";
		// 查询中支SQL
		String tZhongSQL = "";
		// 查询营销服务部SQL
		String tYingSQL = "";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql("select sysvarvalue From ldsysvar where sysvar='DataPrintCombine'");
		testSSRS = exeSql
				.execSQL(sqlbv1);
		String strTemplatePath = testSSRS.GetText(1, 1); // 数据库中的路径-模板路径D:/lis/ui/f1print/NCLtemplate/
		// String strTemplatePath = "D:/lis/ui/f1print/NCLtemplate/";

		ExeSQL searchOneSQL = new ExeSQL();
		SSRS searchOneSsrs = new SSRS();
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql("Select substr(Comgrade,2,1) From ldcom Where comcode='"
				+ "?strMngCom?" + "'");
		sqlbv2.put("strMngCom", strMngCom);
		searchOneSsrs = searchOneSQL
				.execSQL(sqlbv2);
		comGrade = searchOneSsrs.GetText(1, 1);
		if (comGrade.equals("4")) {
			buildError("getprintData", "所选管理机构不能为营销服务部");
			logger.debug("所选管理机构不能为营销服务部");
			return false;
		}
		int errd = searchOneSsrs.getMaxRow();
		if (errd == 0) {
			buildError("getprintData", "机构不存在");
			logger.debug("机构不存在");
			return false;
		}
		/**
		 * @todo 如果录入的机构为总公司，则循环取总公司下的所有分公司。 如果没有选择总公司则将分公司的数量设为1
		 */
		if (strMngCom.equals("86")) {
			tFenSQL = " select comcode,name from ldcom where upcomcode = '"
					+ "?strMngCom?" + "'";
		} else {
			if (strMngCom.length() == 4) {
				tFenSQL = " select comcode,name from ldcom where comcode = '"
						+ "?strMngCom?" + "'";
			} else {
				tFenSQL = " select comcode,name from ldcom where comcode = '"
						+ "?strMngCom1?" + "'";
			}
		}
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tFenSQL);
		sqlbv3.put("strMngCom", strMngCom);
		sqlbv3.put("strMngCom1", strMngCom.substring(0, 4));
		FenSSRS = exeSql.execSQL(sqlbv3);

		/**
		 * @todo 循环分公司
		 */

		for (int i = 0; i < FenSSRS.getMaxRow(); i++) {
			// 取得分公司名称
			String tFenName = FenSSRS.GetText(i + 1, 2);
			if (strMngCom.length() <= 4) {
				tZhongSQL = " select comcode,name from ldcom where upcomcode = '"
						+ "?upcomcode?" + "'";
			} else {
				tZhongSQL = " select comcode,name from ldcom where comcode = '"
						+ "?comcode?" + "'";
			}
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tZhongSQL);
			sqlbv4.put("upcomcode", FenSSRS.GetText(i + 1, 1));
			sqlbv4.put("comcode", strMngCom.substring(0, 6));
			ZhongSSRS = exeSql.execSQL(sqlbv4);

			// 循环中支
			for (int j = 0; j < ZhongSSRS.getMaxRow(); j++) {
				// 中支名称
				String tZhongName = ZhongSSRS.GetText(j + 1, 2);
				// if (strMngCom.length() <= 6)
				// {
				// tYingSQL =
				// " select comcode,name from ldcom where upcomcode = '" +
				// ZhongSSRS.GetText(j + 1, 1) + "'";
				// }
				// else
				// {
				// tYingSQL =
				// " select comcode,name from ldcom where comcode = '" +
				// strMngCom.substring(0, 8) + "'";
				// }
				// YingSSRS = exeSql.execSQL(tYingSQL);
				// 循环营销部
				// for (int k = 0; k < YingSSRS.getMaxRow(); k++)
				// {
				// String tYingName = YingSSRS.GetText(k + 1, 2);

				// comGrade = searchOneSsrs.GetText(1, 1);
				// if (comGrade.equals("1") || comGrade.equals("4"))
				// {
				// buildError("getprintData", "只能打印分公司或者中心支公司的清单");
				// logger.debug("只能打印分公司或者中心支公司的清单");
				// return false;
				// }
				strSQL = "Select b.BatchNo,b.MakeDate,a.GrpContNo,a.GrpName,a.agentcode,c.name,"
						+ " (Select Name from labranchgroup"
						+ " where agentgroup=c.agentgroup)"
						+ " From LCGrpCont a,ldcontinvoicemap b,laagent c "
						+ " Where (a.PrintCount = 1 or a.PrintCount = 2 or a.PrintCount = 3)"
						+ " And b.contno=a.grpcontno"
						+ " And trim(a.agentcode)=trim(c.agentcode)"
						+ " And a.ManageCom like concat('"
						+ "?agentcode?"
						+ "','%')"
						+ " And b.MakeDate between '"
						+ "?strStartDate?"
						+ "' and '"
						+ "?strEndDate?"
						+ "'"
						+ " Order By b.MakeDate,b.BatchNo";
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(strSQL);
				sqlbv5.put("agentcode", ZhongSSRS.GetText(j + 1, 1));
				sqlbv5.put("strStartDate", strStartDate);
				sqlbv5.put("strEndDate", strEndDate);
				ExeSQL exeSQL = new ExeSQL();
				SSRS ssrs = exeSql.execSQL(sqlbv5);
				int i_count = ssrs.getMaxRow();

				if (i_count <= 0) {
					logger.debug("管理机构'" + ZhongSSRS.GetText(j + 1, 1)
							+ "'没有可打印的数据");
					continue;
				}
				// ////////////////////////////////////////////////////////
				String[] colx = new String[8];
				colx[0] = "统计日期:";
				colx[1] = strStartDate + "至";
				colx[2] = strEndDate;
				colx[3] = "    ";
				colx[4] = "    ";
				colx[5] = "    ";
				colx[6] = "打印日期:";
				colx[7] = strPrintDate;
				alistTable.add(colx);

				String[] col1 = new String[8];
				col1[0] = "分公司：";
				col1[1] = tFenName;
				col1[2] = "   ";
				col1[3] = "   ";
				col1[4] = "   ";
				col1[5] = "   ";
				col1[6] = "中心支公司:";
				col1[7] = tZhongName + "  ";
				alistTable.add(col1);

				String[] col2 = new String[8];
				col2[0] = "序号";
				col2[1] = "打印批次号";
				col2[2] = "保单打印日期";
				col2[3] = "保单号";
				col2[4] = "投保单位";
				col2[5] = "业务员号";
				col2[6] = "业务员";
				col2[7] = "业务分部";
				alistTable.add(col2);

				for (int l = 1; l <= i_count; l++) {
					String[] cols = new String[8];
					try {
						cols[0] = l + "";
						cols[1] = ssrs.GetText(l, 1);
						cols[2] = ssrs.GetText(l, 2);
						cols[3] = ssrs.GetText(l, 3);
						cols[4] = ssrs.GetText(l, 4);
						cols[5] = ssrs.GetText(l, 5);
						cols[6] = ssrs.GetText(l, 6);
						cols[7] = ssrs.GetText(l, 7);
					} catch (Exception ex) {
						buildError("getprintData", "抽取数据失败");
						logger.debug("抽取数据失败");
						return false;
					}

					logger.debug("抽取信息成功！！");
					alistTable.add(cols);
				}

				String[] col3 = new String[8];
				col3[0] = "   ";
				col3[1] = "   ";
				col3[2] = "   ";
				col3[3] = "   ";
				col3[4] = "   ";
				col3[5] = "   ";
				col3[6] = "   ";
				col3[7] = "   ";
				alistTable.add(col3);

				j_count = j_count + i_count;
				String[] col4 = new String[8];
				col4[0] = "件数小计:";
				col4[1] = "" + i_count;
				col4[2] = "   ";
				col4[3] = "   ";
				col4[4] = "   ";
				col4[5] = "   ";
				col4[6] = "   ";
				col4[7] = "   ";
				alistTable.add(col4);

				String[] co15 = new String[8];
				co15[0] = "   ";
				co15[1] = "   ";
				co15[2] = "   ";
				co15[3] = "   ";
				co15[4] = "   ";
				co15[5] = "   ";
				co15[6] = "   ";
				co15[7] = "   ";
				alistTable.add(co15);

				// }
			}
		}
		if (j_count == 0) {
			buildError("tSSRSError", "对不起，没有查询结果。所选管理机构在对应日期没有数据！");
			return false;
		}

		String[] col = new String[1];
		TextTag texttag = new TextTag(); // Create a TextTag
		// instance
		XmlExport xmlexport = new XmlExport(); // Create a
		// XmlExport
		// instance
		xmlexport.createDocument("ReportLis.vts", "printer"); // appoint
		// to a
		// special
		// output
		// .vts file
		xmlexport.addListTable(alistTable, col);
		texttag.add("EndDate", strEndDate);
		texttag.add("StartDate", strStartDate);
		texttag.add("MngCom", strMngCom);
		texttag.add("PrintDate", strPrintDate);
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		xmlexport.outputDocumentToFile("D:\\", "testHZM"); // 输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);

		return true;

	}

	/*
	 * @function :in order to debug
	 */
	public static void main(String[] args) {
		String tStrMngCom = "8632";
		// String tIssueDate = "2005-09-09";
		// String tAgentGroup = "0005";

		VData tVData = new VData();
		tVData.addElement(tStrMngCom);

		// tVData.addElement(tAgentGroup);

		ReportLisUI tReportLisUI = new ReportLisUI();
		tReportLisUI.submitData(tVData, "PRINT");
	}
}
