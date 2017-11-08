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

public class PayChequeNoLisBL {
private static Logger logger = Logger.getLogger(PayChequeNoLisBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strMngCom;
	private String comName;
	private String strIssueDate;
	private String strSubCom;
	private String str;
	private String strAgentgroup;
	private String strAgentGroup;
	private int departmentNo;
	private String strDepartment;
	private String strSQL;
	private String strPrintDate;

	public PayChequeNoLisBL() {
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
		strIssueDate = (String) cInputData.get(1);
		strAgentGroup = (String) cInputData.get(2);
		strPrintDate = (String) cInputData.get(3);
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
		cError.moduleName = "PayChequeNoLisBL";
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
		ListTable alistTable = new ListTable(); // Create a ListTable instance
		alistTable.setName("RISK"); // Appoint to a sepcial flag
		ExeSQL exeSql = new ExeSQL();

		SSRS FenSSRS = new SSRS();
		SSRS ZhongSSRS = new SSRS();
		SSRS YingSSRS = new SSRS();
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
						+ "?strMngCom1?" + "'";
			}

		}
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tFenSQL);
		sqlbv1.put("strMngCom", strMngCom);
		sqlbv1.put("strMngCom1", strMngCom.substring(0, 4));
		FenSSRS = exeSql.execSQL(sqlbv1);

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
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tFenSQL);
		sqlbv2.put("strMngCom", strMngCom);
		sqlbv2.put("strMngCom1", strMngCom.substring(0, 4));
		FenSSRS = exeSql.execSQL(sqlbv2);

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
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tZhongSQL);
			sqlbv3.put("upcomcode", FenSSRS.GetText(i + 1, 1));
			sqlbv3.put("comcode", strMngCom.substring(0, 6));
			ZhongSSRS = exeSql.execSQL(sqlbv3);
			// 循环中支
			for (int j = 0; j < ZhongSSRS.getMaxRow(); j++) {
				// 中支名称
				String tZhongName = ZhongSSRS.GetText(j + 1, 2);
				if (strMngCom.length() <= 6) {
					tYingSQL = " select comcode,name from ldcom where upcomcode = '"
							+ "?upcomcode?" + "'";
				} else {
					tYingSQL = " select comcode,name from ldcom where comcode = '"
							+ "?comcode?" + "'";
				}
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(tYingSQL);
				sqlbv4.put("upcomcode", ZhongSSRS.GetText(j + 1, 1));
				sqlbv4.put("comcode", strMngCom.substring(0, 8));
				YingSSRS = exeSql.execSQL(sqlbv4);
				// 循环营销部
				for (int k = 0; k < YingSSRS.getMaxRow(); k++) {
					String tYingName = YingSSRS.GetText(k + 1, 2);

					String[] colx = new String[9];
					colx[0] = "录入日期：";
					colx[1] = strIssueDate;
					colx[2] = "   ";
					colx[3] = "   ";
					colx[4] = "打印日期：";
					colx[5] = strPrintDate;
					colx[6] = "   ";
					colx[7] = "   ";
					colx[8] = "   ";
					alistTable.add(colx);

					String[] col1 = new String[9];
					col1[0] = "分公司：";
					col1[1] = tFenName;
					col1[2] = "中心支公司：";
					col1[3] = tZhongName;
					col1[4] = "营业区（营销服务部）：";
					col1[5] = tYingName;
					col1[6] = "   ";
					col1[7] = "   ";
					col1[8] = "   ";
					alistTable.add(col1);

					String[] col2 = new String[9];
					col2[0] = "   ";
					col2[1] = "   ";
					col2[2] = "   ";
					col2[3] = "   ";
					col2[4] = "   ";
					col2[5] = "   ";
					col2[6] = "   ";
					col2[7] = "   ";
					col2[8] = "   ";
					alistTable.add(col2);

					String[] col3 = new String[9];
					col3[0] = "序号";
					col3[1] = "银行";
					col3[2] = "支票号";
					col3[3] = "投保人";
					col3[4] = "被保人";
					col3[5] = "业务员号";
					col3[6] = "业务员";
					col3[7] = "投保单号";
					col3[8] = "金额";
					alistTable.add(col3);

					strSQL = "select (select codename"
							+ " from ldcode"
							+ " where codetype = 'bank'"
							+ " and code = a.bankcode),"
							+ " a.chequeno,"
							+ " (case when a.accname is not null then a.accname  else '' end),"
							+ " (select (case when insuredname is not null then insuredname  else '' end) from lccont where prtno=a.otherno),"
							+ " b.agentcode,"
							+ " (select name from laagent where agentcode = trim(b.agentcode)),"
							+ " a.otherno," + " a.paymoney"
							+ " from ljtempfeeclass a, ljtempfee b"
							+ " where a.paymode = '3' "
							+ " and a.enteraccdate = '3000-01-01'"
							+ " and a.policycom like concat('"
							+ "?policycom?" + "','%')"
							+ " and a.tempfeeno = b.tempfeeno"
							+ " and a.makedate = '" + "?strIssueDate?" + "'";
					logger.debug("strSQL==" + strSQL);
					SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
					sqlbv5.sql(strSQL);
					sqlbv5.put("policycom", YingSSRS.GetText(k + 1, 1));
					sqlbv5.put("strIssueDate", strIssueDate);
					SSRS ssrs = exeSql.execSQL(sqlbv5);

					int i_count = ssrs.getMaxRow();
					// And a.othernotype In ('4','6','7')
					/*
					 * strSQL =" Select
					 * b.Name,a.chequeno,a.appntname,a.accname,c.agentcode,e.Name,a.otherno,
					 * a.paymoney From ljtempfeeclass a, lacom b,ljtempfee
					 * c,laagent e Where a.paymode='3'and a.bankcode=b.agentcom
					 * And c.tempfeeno=a.tempfeeno And
					 * trim(e.agentcode)=trim(c.agentcode) And
					 * a.enteraccdate='3000-1-1' And a.makedate='2005-8-12' And
					 * c.agentgroup In (Select f.agentgroup From labranchgroup
					 * g,labranchgroup f Where f.branchattr Like
					 * trim(g.branchattr) || '%' And g.branchlevel = '03' And
					 * g.agentgroup in(Select agentgroup From labranchgroup
					 * Where trim(branchattr)='0201'And managecom='86320000') ) ";
					 */

					// 将数据装入表单
					for (int l = 1; l <= i_count; l++) {
						String[] cols = new String[9];
						try {
							cols[0] = l + "";
							cols[1] = ssrs.GetText(l, 1);
							cols[2] = ssrs.GetText(l, 2);
							cols[3] = ssrs.GetText(l, 3);
							cols[4] = ssrs.GetText(l, 4);
							cols[5] = ssrs.GetText(l, 5);
							cols[6] = ssrs.GetText(l, 6);
							cols[7] = ssrs.GetText(l, 7);
							cols[8] = ssrs.GetText(l, 8);
						} catch (Exception ex) {
							buildError("getprintData", "抽取数据失败");
							logger.debug("抽取数据失败");
							return false;
						}

						logger.debug("抽取信息成功！！");
						alistTable.add(cols);
					}
					String[] col4 = new String[9];
					col4[0] = "   ";
					col4[1] = "   ";
					col4[2] = "   ";
					col4[3] = "   ";
					col4[4] = "   ";
					col4[5] = "   ";
					col4[6] = "   ";
					col4[7] = "   ";
					col4[8] = "   ";
					alistTable.add(col4);

					String[] col5 = new String[9];
					col5[0] = "   ";
					col5[1] = "   ";
					col5[2] = "   ";
					col5[3] = "   ";
					col5[4] = "   ";
					col5[5] = "   ";
					col5[6] = "   ";
					col5[7] = "   ";
					col5[8] = "   ";
					alistTable.add(col5);

					String[] col6 = new String[9];
					col6[0] = "   ";
					col6[1] = "   ";
					col6[2] = "   ";
					col6[3] = "   ";
					col6[4] = "   ";
					col6[5] = "   ";
					col6[6] = "   ";
					col6[7] = "   ";
					col6[8] = "   ";
					alistTable.add(col6);

				}
			}
		}

		String[] col = new String[1];
		TextTag texttag = new TextTag(); // Create a TextTag instance
		XmlExport xmlexport = new XmlExport(); // Create a XmlExport instance
		xmlexport.createDocument("PayChequeNoLis.vts", "printer"); // appoint
																	// to a
																	// special
																	// output
																	// .vts file

		xmlexport.addListTable(alistTable, col);
		texttag.add("IssueDate", strIssueDate);
		texttag.add("MngCom", comName);
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		// xmlexport.outputDocumentToFile("D:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;

	}

	private Vector getSearchResult(String str, Vector v) {
		String upAgentgroup = str;
		Vector vd = new Vector();
		vd = v;
		String result1 = "";
		int search_count = 0;
		ExeSQL searchOneSQL = new ExeSQL();
		SSRS searchOneSsrs = new SSRS();
		// vd.add(upAgentgroup);
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql("Select agentgroup From labranchgroup Where upbranch='"
				+ "?upAgentgroup?" + "'");
		sqlbv6.put("upAgentgroup", upAgentgroup);
		searchOneSsrs = searchOneSQL.execSQL(sqlbv6);
		search_count = searchOneSsrs.getMaxRow();

		for (int search = 1; search < search_count + 1; search++) {
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql("Select agentgroup From labranchgroup Where upbranch='"
					+ "?upAgentgroup?" + "'");
			sqlbv7.put("upAgentgroup", upAgentgroup);
			searchOneSsrs = searchOneSQL.execSQL(sqlbv7);
			int deb = searchOneSsrs.getMaxRow();
			if (deb == 0) {
				break;
			}

			result1 = searchOneSsrs.GetText(search, 1);
			vd.add(result1);
			this.getSearchResult(result1, vd);

		}
		return vd;
	}

	private Vector getSearchResult(String str1, String str2) {
		String result1 = "";
		int search_count = 0;
		Vector output = new Vector();
		ExeSQL searchOneSQL = new ExeSQL();
		SSRS searchOneSsrs = new SSRS();
		// searchOneSsrs = searchOneSQL.execSQL("Select agentgroup From
		// labranchgroup Where trim(branchattr)='" +
		// str1 + "'And managecom='" + str2 + "'");
		// result1 = searchOneSsrs.GetText(1, 1);

		// searchOneSsrs = searchOneSQL.execSQL("Select c.agentgroup From
		// labranchgroup b,labranchgroup c Where c.branchattr Like
		// trim(b.branchattr) || '%' And b.branchlevel = '03' And b.agentgroup
		// ='"+result1+"' Order By agentgroup");
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql("Select c.agentgroup From labranchgroup b,labranchgroup c Where c.branchattr Like concat(trim(b.branchattr) , '%') And c.managecom='"
				+ "?str2?"
				+ "'And b.branchlevel = '03' And b.agentgroup in(Select agentgroup From labranchgroup Where trim(branchattr)='"
				+ "?str1?"
				+ "'And managecom='"
				+ "?str2?"
				+ "') Order By agentgroup");
		sqlbv8.put("str2", str2);
		sqlbv8.put("str1", str1);
		searchOneSsrs = searchOneSQL.execSQL(sqlbv8);
		search_count = searchOneSsrs.getMaxRow();
		for (int i = 1; i < search_count + 1; i++) {
			result1 = searchOneSsrs.GetText(i, 1);
			output.add(result1);
		}
		return output;
	}

	private String getAgentGroup(Vector v) {

		Vector agent = new Vector();
		agent = v;
		String goOne = " or";
		String goTwo = " c.agentGroup='";
		String goThree = "' ";
		String output = "";
		String value = "";
		if (agent.isEmpty()) {
			return "";
		}
		for (int i = 0; i < agent.size(); i++) {
			value = (String) agent.get(i);
			if (agent.size() == 1) {
				output = goTwo + value + goThree;
				return output;
			}
			if (i == 0) {
				output = goTwo + value;
			} else if (i > 0 && i < agent.size() - 1) {
				output = output + goThree + goOne + goTwo + value;
			} else {
				output = output + goThree + goOne + goTwo + value + goThree;
			}

		}
		// logger.debug("jieguoxianshi"+output);
		return output;
	}

	/*
	 * @function :in order to debug
	 */
	public static void main(String[] args) {
		String tStrMngCom = "86110000";
		String tIssueDate = "2005-8-12";
		String tAgentGroup = "0001";
		String tPrintDate = "2005-08-12";

		VData tVData = new VData();
		tVData.addElement(tStrMngCom);
		tVData.addElement(tIssueDate);
		tVData.addElement(tAgentGroup);
		tVData.addElement(tPrintDate);

		PayChequeNoLisUI tPayChequeNoLisUI = new PayChequeNoLisUI();
		tPayChequeNoLisUI.submitData(tVData, "PRINT");
	}
}
