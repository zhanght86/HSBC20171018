package com.sinosoft.lis.f1print;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
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

public class ZhixiaoBL {
private static Logger logger = Logger.getLogger(ZhixiaoBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strMngCom;
	private String comName;
	private String strIssueDate;
	private String str;
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
		cError.moduleName = "ZhixiaoBL";
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
		String tSQL = "";
		ExeSQL exeSql = new ExeSQL();
		SSRS testSSRS = new SSRS();

		// 选择临时文件读取目录
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
						+ "?strMngCom?" + "'";
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
					String[] colx = new String[8];
					colx[0] = "划款日期：";
					colx[1] = strIssueDate;
					colx[2] = "    ";
					colx[3] = "打印日期：";
					colx[4] = strPrintDate;
					colx[5] = "    ";
					colx[6] = "    ";
					colx[7] = "    ";
					// alistTable.add(colx);

					String[] col1 = new String[8];
					col1[0] = "分公司：";
					col1[1] = tFenName;
					col1[2] = "中心支公司：";
					col1[3] = tZhongName;
					col1[4] = "营业区（营销服务部）：";
					col1[5] = tYingName;
					col1[6] = "  ";
					col1[7] = "  ";
					// alistTable.add(col1);

					// String[] col2 = new String[8];
					// col2[0] = " ";
					// col2[1] = " ";
					// col2[2] = " ";
					// col2[3] = " ";
					// col2[4] = " ";
					// col2[5] = " ";
					// col2[6] = " ";
					// col2[7] = " ";
					// alistTable.add(col2);

					String[] col3 = new String[8];
					col3[0] = "序号";
					col3[1] = "录入日期";
					col3[2] = "银行";
					col3[3] = "协议书号";
					col3[4] = "投保单号";
					col3[5] = "投保人";
					col3[6] = "划款账号";
					col3[7] = "金额";
					// alistTable.add(col3);

					// ///////////////////////////////////////////////////////////
					// 根据界面的选择“收款”、“付款”
					// ///////////////////////////////////////////////////////////
					if (this.mListType != null && mListType.equals("1")) {
						if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
							tSQL = "select (select paydate from ljtempfeeclass where  otherno=a.polno and rownum=1 ),"
									+ " (select bankname from ldbank where bankcode = a.bankcode),"
									+ " a.paycode,"
									+ " a.polno,"
									+ " (case when a.accname is not null then a.accname  else '' end),"
									+ " a.accno,"
									+ " a.paymoney"
									+ " from lyreturnfrombankb a"
									+ " where a.banksuccflag = '0000'"
									+ "  and a.comcode like concat('"
									+ "?comcode?"
									+ "','%')"
									+ "  and a.senddate='"
									+ "?strIssueDate?"
									+ "'"
									+ " and a.dealtype='S'"
									+ " and exists (select 'X'"
									+ "  from lybanklog b"
									+ " where (b.operationtype = '6' or b.operationtype = 'X')"
									+ " and b.serialno = a.serialno)";
						}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
							tSQL = "select (select paydate from ljtempfeeclass where  otherno=a.polno limit 0,1 ),"
									+ " (select bankname from ldbank where bankcode = a.bankcode),"
									+ " a.paycode,"
									+ " a.polno,"
									+ " (case when a.accname is not null then a.accname  else '' end),"
									+ " a.accno,"
									+ " a.paymoney"
									+ " from lyreturnfrombankb a"
									+ " where a.banksuccflag = '0000'"
									+ "  and a.comcode like concat('"
									+ "?comcode?"
									+ "','%')"
									+ "  and a.senddate='"
									+ "?strIssueDate?"
									+ "'"
									+ " and a.dealtype='S'"
									+ " and exists (select 'X'"
									+ "  from lybanklog b"
									+ " where (b.operationtype = '6' or b.operationtype = 'X')"
									+ " and b.serialno = a.serialno)";
						}
						if (strReturnDate != null && !strReturnDate.equals("")) {
							tSQL = tSQL + " and a.bankdealdate = '"
									+ "?strReturnDate?" + "'";
						}
						tSQL = tSQL
								+ "and exists(select 'x' from ljtempfee p where p.otherno = a.polno "
								+ " and p.riskcode in "
								+ "(select riskcode from lmriskapp where riskprop = 'T'))";
					}
					if (this.mListType != null && mListType.equals("2")) {
						if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
							tSQL = "select (select paydate from ljtempfeeclass where  otherno=a.polno and rownum=1 ),"
									+ " (select bankname from ldbank where bankcode = a.bankcode),"
									+ " a.paycode,"
									+ " a.polno,"
									+ " nvl(a.accname,''),"
									+ " a.accno,"
									+ " a.paymoney"
									+ " from lyreturnfrombankb a"
									+ " where a.banksuccflag = '0000'"
									+ "  and a.comcode like concat('"
									+ "?comcode?"
									+ "','%')"
									+ "  and a.senddate='"
									+ "?strIssueDate?"
									+ "'"
									+ " and a.dealtype='F'" + " and a.notype='4'";
						}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
							tSQL = "select (select paydate from ljtempfeeclass where  otherno=a.polno limit 0,1 ),"
									+ " (select bankname from ldbank where bankcode = a.bankcode),"
									+ " a.paycode,"
									+ " a.polno,"
									+ " CASE WHEN a.accname IS NOT NULL THEN a.accname ELSE '' END ,"
									+ " a.accno,"
									+ " a.paymoney"
									+ " from lyreturnfrombankb a"
									+ " where a.banksuccflag = '0000'"
									+ "  and a.comcode like concat('"
									+ "?comcode?"
									+ "','%')"
									+ "  and a.senddate='"
									+ "?strIssueDate?"
									+ "'"
									+ " and a.dealtype='F'" + " and a.notype='4'";
						}
						if (strReturnDate != null && !strReturnDate.equals("")) {
							tSQL = tSQL + " and a.bankdealdate = '"
									+ "?strReturnDate?" + "'";
						}

						tSQL = tSQL
								+ "and exists(select 'x' from ljtempfee p where p.otherno = a.polno "
								+ " and p.riskcode in "
								+ "(select riskcodecode from lmriskapp where riskprop = 'T'))";
					}
					SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
					sqlbv5.sql(tSQL);
					sqlbv5.put("comcode", YingSSRS.GetText(k + 1, 1));
					sqlbv5.put("strIssueDate", strIssueDate);
					sqlbv5.put("strReturnDate", strReturnDate);
					SSRS ssrs = exeSql.execSQL(sqlbv5);
					if (ssrs.getMaxRow() <= 0) {
						logger.debug("管理机构'" + YingSSRS.GetText(k + 1, 1)
								+ "'在'" + strIssueDate + "'没有数据");
					}
					int i_count = ssrs.getMaxRow();
					if (i_count > 0) {
						alistTable.add(colx);
						alistTable.add(col1);
						alistTable.add(col3);
					}
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
					String[] col4 = new String[8];
					col4[0] = "   ";
					col4[1] = "   ";
					col4[2] = "   ";
					col4[3] = "   ";
					col4[4] = "   ";
					col4[5] = "   ";
					col4[6] = "   ";
					col4[7] = "   ";
					// alistTable.add(col4);

					String[] col5 = new String[8];
					col5[0] = "   ";
					col5[1] = "   ";
					col5[2] = "   ";
					col5[3] = "   ";
					col5[4] = "   ";
					col5[5] = "   ";
					col5[6] = "   ";
					col5[7] = "   ";
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
		if (this.mListType == null || this.mListType == "") {
			buildError("tSSRSError", "对不起，没有查询结果。所选管理机构在对应日期没有数据！");
			return false;
		} else if (this.mListType.equals("1")) {
			xmlexport.createDocument("Zhixiao.vts", "printer"); // appoint to a
																// special
																// output .vts
																// file
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

	/*
	 * @function :in order to debug
	 */
	public static void main(String[] args) {
		String tStrMngCom = "8621";
		String tIssueDate = "2006-04-20";
		String tAgentGroup = "0001";
		String tPrintDate = "2006-04-20";
		String tReturnDate = "2006-04-21";
		String tLisType = "1";

		VData tVData = new VData();
		tVData.addElement(tStrMngCom);
		tVData.addElement(tIssueDate);
		tVData.addElement(tAgentGroup);
		tVData.addElement(tPrintDate);
		tVData.addElement(tReturnDate);
		tVData.addElement(tLisType);

		ZhixiaoUI tZhixiaoUI = new ZhixiaoUI();
		tZhixiaoUI.submitData(tVData, "PRINT");
	}
}
