package com.sinosoft.lis.f1print;
import java.util.Vector;

import org.apache.log4j.Logger;

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
 * @author 刘岩松
 * function :print InputEfficiency or print CheckEfficiency Business Logic layer
 * @version 1.0
 * @date 2003-04-04
 */

public class AgentTranNoLisBL {
private static Logger logger = Logger.getLogger(AgentTranNoLisBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strMngCom;
	private String comName;
	private String strIssueDate;
	private String strSubCom;
	private String strAgentgroup;
	private String strAgentGroup;
	private int departmentNo;
	private String strDepartment;
	private String strSQL;
	private String strPrintDate;
	private String strReturnDate;

	public AgentTranNoLisBL() {
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

	/**
	 * Name : getInputData() function :receive data from jsp and check date
	 * check :judge whether startdate begin enddate return :true or false
	 */
	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		strIssueDate = (String) cInputData.get(1);
		strPrintDate = (String) cInputData.get(2);
		strReturnDate = (String) cInputData.get(3);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	/**
	 * ***** name : buildError function : Prompt error message return :error
	 * message
	 */

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "AgentTranNoLisBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * ***** name : getPrintData() function :get print data parameter : null
	 * return :true or false
	 */

	private boolean getPrintData() {
		// 选择临时文件读取目录
		ExeSQL exeSql = new ExeSQL();
		// 选择临时文件读取目录
		ListTable alistTable = new ListTable(); // Create a ListTable instance
		alistTable.setName("RISK"); // Appoint to a sepcial flag

		SSRS FenSSRS = new SSRS();
		SSRS ZhongSSRS = new SSRS();
		SSRS YingSSRS = new SSRS();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		// 查询分公司SQL
		String tFenSQL = "";
		// 查询中支SQL
		String tZhongSQL = "";
		// 查询营销服务部SQL
		String tYingSQL = "";

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
						+ "?comcode?" + "'";
			}
		}
		sqlbv.sql(tFenSQL);
		sqlbv.put("strMngCom", strMngCom);
		sqlbv.put("comcode", strMngCom.substring(0, 4));
		FenSSRS = exeSql.execSQL(sqlbv);

		/**
		 * @todo 如果录入的机构为总公司，则循环取总公司下的所有分公司。 如果没有选择总公司则将分公司的数量设为1
		 */
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		if (strMngCom.equals("86")) {
			tFenSQL = " select comcode,name from ldcom where upcomcode = '"
					+ "?strMngCom?" + "'";
		} else {
			if (strMngCom.length() == 4) {
				tFenSQL = " select comcode,name from ldcom where comcode = '"
						+ "?strMngCom?" + "'";
			} else {
				tFenSQL = " select comcode,name from ldcom where comcode = '"
						+ "?comcode?" + "'";
			}

		}
		sqlbv4.sql(tFenSQL);
		sqlbv4.put("strMngCom", strMngCom);
		sqlbv4.put("comcode", strMngCom.substring(0, 4));
		FenSSRS = exeSql.execSQL(sqlbv4);

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
			sqlbv1.sql(tZhongSQL);
			sqlbv1.put("upcomcode", FenSSRS.GetText(i + 1, 1));
			sqlbv1.put("comcode", strMngCom.substring(0, 6));
			ZhongSSRS = exeSql.execSQL(sqlbv1);
			// 循环中支
			for (int j = 0; j < ZhongSSRS.getMaxRow(); j++) {
				// 中支名称
				String tZhongName = ZhongSSRS.GetText(j + 1, 2);
				String[] colx = new String[13];
				colx[0] = "划款日期：";
				colx[1] = strIssueDate;
				colx[2] = "   ";
				colx[3] = "打印日期：";
				colx[4] = strPrintDate;
				colx[5] = "   ";
				colx[6] = "   ";
				colx[7] = "   ";
				colx[8] = "   ";
				colx[9] = "   ";
				colx[10] = "   ";
				colx[11] = "   ";
				colx[12] = "   ";
				alistTable.add(colx);
				String[] col1 = new String[13];
				col1[0] = "分公司：";
				col1[1] = tFenName;
				col1[2] = "中心支公司：";
				col1[3] = tZhongName;
				col1[4] = "   ";
				col1[5] = "   ";
				col1[6] = "   ";
				col1[7] = "   ";
				col1[8] = "   ";
				col1[9] = "   ";
				col1[10] = "   ";
				col1[11] = "   ";
				col1[12] = "   ";
				alistTable.add(col1);
				String[] col2 = new String[13];
				col2[0] = "   ";
				col2[1] = "   ";
				col2[2] = "   ";
				col2[3] = "   ";
				col2[4] = "   ";
				col2[5] = "   ";
				col2[6] = "   ";
				col2[7] = "   ";
				col2[8] = "   ";
				col2[9] = "   ";
				col2[10] = "   ";
				col2[11] = "   ";
				col2[12] = "   ";
				alistTable.add(col2);
				String[] col3 = new String[13];
				col3[0] = "序号";
				col3[1] = "银行";
				col3[2] = "储蓄所";
				col3[3] = "投保单号";
				col3[4] = "投保人";
				col3[5] = "联系电话";
				col3[6] = "保费";
				col3[7] = "失败原因";
				col3[8] = "更正";
				col3[9] = "经办人";
				col3[10] = "险种代码";
				col3[11] = "业务分部";
				col3[12] = "业务员";

				alistTable.add(col3);

				if (strMngCom.length() <= 6) {
					tYingSQL = " select comcode,name from ldcom where upcomcode = '"
							+ "?upcomcode?" + "'";
				} else {
					tYingSQL = " select comcode,name from ldcom where comcode = '"
							+ "?comcode?" + "'";
				}
				sqlbv2.sql(tYingSQL);
				sqlbv2.put("upcomcode", ZhongSSRS.GetText(j + 1, 1));
				sqlbv2.put("comcode", strMngCom.substring(0, 8));
				YingSSRS = exeSql.execSQL(sqlbv2);
				// 循环营销部
				for (int k = 0; k < YingSSRS.getMaxRow(); k++) {
					String tYingName = YingSSRS.GetText(k + 1, 2);
					strSQL = "select aa, ab, ac, ad, ae, af, ag, ah, ai, aj\n"
							+ "  from (select (select bankname from ldbank where bankcode = a.bankcode and rownum = 1) as aa,\n"
							+ "               (select name from lacom aa,lcpol bb where bb.prtno=a.polno and aa.agentcom=bb.agentcom and bb.polno=bb.mainpolno and rownum=1) as ab,\n"
							+ "               a.polno as ac,\n"
							+ "               nvl(a.accname, '') as ad,\n"
							+ "               nvl((select d.companyphone\n"
							+ "                     from lcaddress d, lcappnt b\n"
							+ "                    where b.contno = a.polno\n"
							+ "                      and d.customerno = b.appntno\n"
							+ "                      and d.addressno = b.addressno),\n"
							+ "                   '') as ae,\n"
							+ "               nvl((select ldcode1.codename\n"
							+ "                     from ldcode1\n"
							+ "                    where trim(ldcode1.code1) = a.banksuccflag\n"
							+ "                      and ldcode1.code = a.bankcode\n"
							+ "                      and codetype = 'bankerror'),\n"
							+ "                   '不成功') as af,\n"
							+ "               (select riskcode\n"
							+ "                  from ljtempfee\n"
							+ "                 where tempfeeno = a.paycode\n"
							+ "                   and rownum = 1) as ag,\n"
							+ "               (select aa.name\n"
							+ "                  from labranchgroup aa, labranchgroup bb, laagent cc\n"
							+ "                 where cc.agentcode = trim(a.agentcode)\n"
							+ "                   and aa.agentgroup = bb.upbranch\n"
							+ "                   and bb.agentgroup = cc.branchcode\n"
							+ "                union\n"
							+ "                select aa.name\n"
							+ "                  from labranchgroup aa, laagent cc\n"
							+ "                 where cc.agentcode = trim(a.agentcode)\n"
							+ "                   and aa.agentgroup = cc.branchcode\n"
							+ "                   and aa.branchlevel = '52') as ah,\n"
							+ "               (select name from laagent where agentcode = trim(a.agentcode)) as ai,\n"
							+ "               a.paymoney as aj,"
							+ "               row_number() over(partition by a.polno order by RowID) rn\n"
							+ "          from lyreturnfrombankb a\n"
							+ "         where a.banksuccflag <> '0000'\n"
							+ "           and a.comcode like '"
							+ YingSSRS.GetText(k + 1, 1) + "%'\n"
							+ "              and a.senddate = '" + strIssueDate
							+ "'\n" + "           and a.dealtype = 'S'\n"
							+ "           and a.notype = '7'\n";
					// " and exists (select 'X'\n" +
					// " from lybanklog b\n" +
					// " where b.salechnl = '3'\n" +
					// " and b.operationtype = '6'\n" +
					// " and b.serialno = a.serialno)";
					if (strReturnDate != null && !strReturnDate.equals("")) {
						strSQL = strSQL + " and a.bankdealdate = '"
								+ strReturnDate + "'";
					}
					strSQL = strSQL + ") tt\n"
							+ " where tt.rn = 1 order by ah,ai";

					ExeSQL exeSQL = new ExeSQL();
					SSRS ssrs = exeSql.execSQL(strSQL);
					int i_count = ssrs.getMaxRow();

					// 将数据装入表单
					for (int l = 1; l <= i_count; l++) {
						String[] cols = new String[13];
						try {
							cols[0] = l + "";
							cols[1] = ssrs.GetText(l, 1);
							cols[2] = ssrs.GetText(l, 2);
							cols[3] = ssrs.GetText(l, 3);
							cols[4] = ssrs.GetText(l, 4);
							cols[5] = ssrs.GetText(l, 5);
							cols[6] = ssrs.GetText(l, 10);
							cols[7] = ssrs.GetText(l, 6);
							cols[8] = "    ";
							cols[9] = "    ";
							cols[10] = ssrs.GetText(l, 7);
							cols[11] = ssrs.GetText(l, 8);
							cols[12] = ssrs.GetText(l, 9);

						} catch (Exception ex) {
							buildError("getprintData", "抽取收款数据时出错");
							System.err.println("抽取收款数据时出错");
						}

						logger.debug("2005-08-09描述表中取出信息！！！！");
						alistTable.add(cols);
					}
				}
				String[] col4 = new String[13];
				col4[0] = "   ";
				col4[1] = "   ";
				col4[2] = "   ";
				col4[3] = "   ";
				col4[4] = "   ";
				col4[5] = "   ";
				col4[6] = "   ";
				col4[7] = "   ";
				col4[8] = "   ";
				col4[9] = "   ";
				col4[10] = "   ";
				col4[11] = "   ";
				col4[12] = "   ";
				alistTable.add(col4);

				String[] col5 = new String[13];
				col5[0] = "   ";
				col5[1] = "   ";
				col5[2] = "   ";
				col5[3] = "   ";
				col5[4] = "   ";
				col5[5] = "   ";
				col5[6] = "   ";
				col5[7] = "   ";
				col5[8] = "   ";
				col5[9] = "   ";
				col5[10] = "   ";
				col5[11] = "   ";
				col5[12] = "   ";
				alistTable.add(col5);

				String[] col6 = new String[13];
				col6[0] = "   ";
				col6[1] = "   ";
				col6[2] = "   ";
				col6[3] = "   ";
				col6[4] = "   ";
				col6[5] = "   ";
				col6[6] = "   ";
				col6[7] = "   ";
				col6[8] = "   ";
				col6[9] = "   ";
				col6[10] = "   ";
				col6[11] = "   ";
				col6[12] = "   ";
				alistTable.add(col6);

			}
		}
		String[] col = new String[1];
		TextTag texttag = new TextTag(); // Create a TextTag instance
		XmlExport xmlexport = new XmlExport(); // Create a XmlExport instance
		xmlexport.createDocument("AgentTranNoLis.vts", "printer"); // appoint
																	// to a
																	// special
																	// output
																	// .vts file

		xmlexport.addListTable(alistTable, col);
		texttag.add("IssueDate", strIssueDate);
		texttag.add("MngCom", comName);
		texttag.add("PrintDate", strPrintDate);
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		// xmlexport.outputDocumentToFile("D:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;

	}

	private Vector getSearchResult(String str) {
		Vector vSearch = new Vector();
		String upAgentgroup = str;
		ExeSQL searchOneSQL = new ExeSQL();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql("Select agentgroup From labranchgroup Where upbranch='"
				+ "?upAgentgroup?" + "'");
		sqlbv.put("upAgentgroup", upAgentgroup);
		SSRS searchOneSsrs = searchOneSQL.execSQL(sqlbv);
		// catch
		int search_count = searchOneSsrs.getMaxRow();
		for (int search = 1; search < search_count + 1; search++) {
			if (searchOneSsrs.GetText(search, 1) != null) {
				vSearch.add(getSearchResult(searchOneSsrs.GetText(search, 1)));
			} else {
				break;

			}

		}
		return vSearch;
	}

	/*
	 * @function :in order to debug
	 */
	public static void main(String[] args) {
		String tStrMngCom = "86110000";
		String tIssueDate = "2005-08-28";
		String tPrintDate = "2005-08-28";

		VData tVData = new VData();
		tVData.addElement(tStrMngCom);
		tVData.addElement(tIssueDate);
		tVData.addElement(tPrintDate);

		AgentTranNoLisUI tAgentTranNoLisUI = new AgentTranNoLisUI();
		tAgentTranNoLisUI.submitData(tVData, "PRINT");
	}
}
