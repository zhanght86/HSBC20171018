package com.sinosoft.lis.f1print;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.pubfun.GlobalInput;
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

public class SingleTranNoLisBL {
private static Logger logger = Logger.getLogger(SingleTranNoLisBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strMngCom;
	private String comName;
	private String strIssueDate;
	String str;
	private String strSubCom;
	private String strAgentgroup;
	private String strAgentGroup;
	private int departmentNo;
	private String strDepartment;
	private String strSQL;
	private String strSQL2;
	private String strPrintDate;
	private String strReturnDate;
	private String mListType;
	private GlobalInput mGlobalInput = new GlobalInput();

	public SingleTranNoLisBL() {
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
		strReturnDate = (String) cInputData.get(4);
		mListType = (String) cInputData.get(5);
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
		cError.moduleName = "SingleTranNoLisBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/***************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData() {
		// //选择临时文件读取目录
		// //选择临时文件读取目录
		String tSQL = "";
		ExeSQL exeSql = new ExeSQL();
		SSRS testSSRS = new SSRS();

		// 选择临时文件读取目录
		ListTable alistTable = new ListTable(); // Create a ListTable instance
		alistTable.setName("RISK"); // Appoint to a sepcial flag
		String FenCom = "";
		String tSql = "";

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
					String[] colx = new String[13];
					colx[0] = "划款日期：";
					colx[1] = strIssueDate;
					colx[2] = "    ";
					colx[3] = "打印日期：";
					colx[4] = strPrintDate;
					colx[5] = "    ";
					colx[6] = "    ";
					colx[7] = "    ";
					colx[8] = "    ";
					colx[9] = "    ";
					colx[10] = "    ";
					colx[11] = "    ";
					colx[12] = "    ";
					// alistTable.add(colx);

					String[] col1 = new String[13];
					col1[0] = "分公司：";
					col1[1] = tFenName;
					col1[2] = "   ";
					col1[3] = "中心支公司：   ";
					col1[4] = tZhongName;
					col1[5] = "营业区（营销服务部）";
					col1[6] = tYingName;
					col1[7] = "   ";
					col1[8] = "   ";
					col1[9] = "   ";
					col1[10] = "   ";
					col1[11] = "   ";
					col1[12] = "   ";
					// alistTable.add(col1);

					// String[] col2 = new String[11];
					// col2[0] = " ";
					// col2[1] = " ";
					// col2[2] = " ";
					// col2[3] = " ";
					// col2[4] = " ";
					// col2[5] = " ";
					// col2[6] = " ";
					// col2[7] = " ";
					// col2[8] = " ";
					// col2[9] = " ";
					// col2[10] = " ";
					// alistTable.add(col2);

					String[] col3 = new String[13];
					col3[0] = "序号";
					col3[1] = "录入日期";
					col3[2] = "银行";
					col3[3] = "划款协议书号";
					col3[4] = "投保单号";
					col3[5] = "投保人";
					col3[6] = "业务员号";
					col3[7] = "业务员";
					col3[8] = "划款账号";
					col3[9] = "金额";
					col3[10] = "不成功原因";
					col3[11] = "营业部";
					col3[12] = "营业组";
					// alistTable.add(col3);

					if (mListType != null && mListType.equals("1")) {
						tSQL = "select aa, ab, ac, ad, ae, af, ag, ah, ai, aj,ak"
								+ " from (select (select paydate from ljtempfeeclass where  otherno=a.polno and rownum=1 ) as aa,"
								+ " (select bankname"
								+ " from ldbank"
								+ " where bankcode = a.bankcode and rownum = 1) as ab,"
								+ " a.paycode as ac,"
								+ " a.polno as ad,"
								+ " nvl(a.accname, '') as ae,"
								+ " a.agentcode as af,"
								+ " (select name from laagent where agentcode = trim(a.agentcode)) as ag,"
								+ " a.accno as ah,"
								+ " a.paymoney as ai,"
								+ " nvl((select ldcode1.codename"
								+ " from ldcode1"
								+ " where trim(ldcode1.code1) = a.banksuccflag"
								+ " and ldcode1.code = a.bankcode"
								+ " and codetype = 'bankerror'),'不成功') as aj,"
								+ " (select t.branchcode from laagent t where t.agentcode = trim(a.agentcode)) as ak,"
								+ " row_number() over(partition by a.polno order by modifydate) rn"
								+ " from lyreturnfrombankb a"
								+ " where a.banksuccflag <> '0000'"
								+ "  and a.comcode like '"
								+ YingSSRS.GetText(k + 1, 1)
								+ "%'"
								+ "  and a.senddate='"
								+ strIssueDate
								+ "'"
								+ " and a.dealtype='S'" + " and a.notype = '6'";
						// " and exists (select 'X'" +
						// " from lybanklog b" +
						// " where b.salechnl = '2'" +
						// " and b.operationtype = '6'" +
						// " and b.serialno = a.serialno)";
						if (strReturnDate != null && !strReturnDate.equals("")) {
							tSQL = tSQL + " and a.bankdealdate = '"
									+ strReturnDate + "'";
						}
						tSQL = tSQL + " ) tt" + " where tt.rn = 1";
					}
					if (mListType != null && mListType.equals("2")) {
						tSQL = "select aa, ab, ac, ad, ae, af, ag, ah, ai, aj,ak"
								+ " from (select (select paydate from ljtempfeeclass where  otherno=a.polno and rownum=1 ) as aa,"
								+ " (select bankname"
								+ " from ldbank"
								+ " where bankcode = a.bankcode and rownum = 1) as ab,"
								+ " a.paycode as ac,"
								+ " a.polno as ad,"
								+ " nvl(a.accname, '') as ae,"
								+ " a.agentcode as af,"
								+ " (select name from laagent where agentcode = trim(a.agentcode)) as ag,"
								+ " a.accno as ah,"
								+ " a.paymoney as ai,"
								+ " nvl((select ldcode1.codename"
								+ " from ldcode1"
								+ " where trim(ldcode1.code1) = a.banksuccflag"
								+ " and ldcode1.code = a.bankcode"
								+ " and codetype = 'bankerror'),'不成功') as aj,"
								+ " (select t.branchcode from laagent t where t.agentcode = trim(a.agentcode)) as ak,"
								+ " row_number() over(partition by a.polno order by modifydate) rn"
								+ " from lyreturnfrombankb a"
								+ " where a.banksuccflag <> '0000'"
								+ "  and a.comcode like '"
								+ YingSSRS.GetText(k + 1, 1)
								+ "%'"
								+ "  and a.senddate='"
								+ strIssueDate
								+ "'"
								+ " and a.dealtype='F'" + " and a.notype='4'";
						if (strReturnDate != null && !strReturnDate.equals("")) {
							tSQL = tSQL + " and a.bankdealdate = '"
									+ strReturnDate + "'";
						}
						tSQL = tSQL + " ) tt" + " where tt.rn = 1";

					}
					SSRS ssrs = exeSql.execSQL(tSQL);
					int i_count = ssrs.getMaxRow();
					if (i_count > 0) {
						alistTable.add(colx);
						alistTable.add(col1);
						alistTable.add(col3);
					}
					for (int l = 1; l <= i_count; l++) {
						String[] cols = new String[13];
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
							cols[9] = ssrs.GetText(l, 9);
							cols[10] = ssrs.GetText(l, 10);
							cols[11] = getUpComName(ssrs.GetText(l, 11));
							cols[12] = getComName(ssrs.GetText(l, 11));
						} catch (Exception ex) {
							buildError("getprintData", "抽取数据失败");
							logger.debug("抽取数据失败");
							return false;
						}

						logger.debug("抽取信息成功！！");
						alistTable.add(cols);
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
					// alistTable.add(col4);

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
					// alistTable.add(col5);
					if (i_count > 0) {
						alistTable.add(col4);
						alistTable.add(col5);
					}

				}
			}
		}
		String[] col = new String[1];
		TextTag texttag = new TextTag(); // Create a TextTag instance
		XmlExport xmlexport = new XmlExport(); // Create a XmlExport instance
		if (this.mListType != null && mListType.equals("1")) {
			xmlexport.createDocument("SingleTranNoRLis.vts", "printer"); // appoint
																			// to a
																			// special
																			// output
																			// .vts
																			// file
		} else {
			xmlexport.createDocument("SingleTranNoPLis.vts", "printer");
		}
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

	//
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

	private String getSearchR(String str) {
		ExeSQL searchOneSQL = new ExeSQL();
		SSRS searchOneSsrs = new SSRS();
		// vd.add(upAgentgroup);
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql("Select branchattr From labranchgroup Where agentgroup='"
				+ "?str?" + "' and managecom='" + "?strMngCom?" + "'");
		sqlbv8.put("str", str);
		sqlbv8.put("strMngCom", strMngCom);
		searchOneSsrs = searchOneSQL.execSQL(sqlbv8);
		String result = searchOneSsrs.GetText(1, 1);

		return result;
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
				output = goOne + goTwo + value + goThree;
				return output;
			}
			if (i == 0) {
				output = goOne + goTwo + value;
			} else if (i > 0 && i < agent.size() - 1) {
				output = output + goThree + goOne + goTwo + value;
			} else if (i == agent.size() - 2) {
				output = output + goThree + goOne + goTwo + value;
			} else {
				output = output + goThree + goOne + goTwo + value + goThree;
			}

		}
		// logger.debug("jieguoxianshi"+output);
		return output;
	}

	public String getUpComName(String comcode) {
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupDB ttLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(comcode);
		if (!tLABranchGroupDB.getInfo()) {
			this.buildError("getUpComName", comcode + "机构不存在！");
			return null;
		} else {
			ttLABranchGroupDB.setAgentGroup(tLABranchGroupDB.getUpBranch());
			if (!ttLABranchGroupDB.getInfo()) {
				this.buildError("getUpComName", "在查找comcode所属的营业部时出错！");
				return null;
			} else {
				return ttLABranchGroupDB.getName();
			}
		}
	}

	public String getComName(String comcode) {
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupDB ttLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(comcode);
		if (!tLABranchGroupDB.getInfo()) {
			this.buildError("getUpComName", comcode + "机构不存在！");
			return null;
		} else {
			return tLABranchGroupDB.getName();
		}
	}

	/*
	 * @function :in order to debug
	 */
	public static void main(String[] args) {
		String tStrMngCom = "86110000";
		String tIssueDate = "2005-8-17";
		String tAgentGroup = "0003";
		String tPrintDate = "2005-08-12";

		VData tVData = new VData();
		tVData.addElement(tStrMngCom);
		tVData.addElement(tIssueDate);
		tVData.addElement(tAgentGroup);
		tVData.addElement(tPrintDate);

		SingleTranNoLisUI tSingleTranNoLisUI = new SingleTranNoLisUI();
		tSingleTranNoLisUI.submitData(tVData, "PRINT");
	}
}
