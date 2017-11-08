package com.sinosoft.lis.f1print;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.pubfun.PubFun;
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

public class ContReturnNoLisBL {
private static Logger logger = Logger.getLogger(ContReturnNoLisBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strMngCom;
	private String comName;
	private String strStartDate;
	private String strSubCom;
	private String strAgentgroup;
	private String strAgentGroup;
	private int departmentNo;
	private String strDepartment;
	private String strSQL;
	private String strEndDate;
	private String strSaleChnl;

	public ContReturnNoLisBL() {
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
		strStartDate = (String) cInputData.get(1);
		strEndDate = (String) cInputData.get(2);
		strSaleChnl = (String) cInputData.get(3);

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
		cError.moduleName = "ContReturnNoLisBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * ***** name : getPrintData() function :get print data parameter : null
	 * return :true or false
	 */

	private boolean getPrintData() {
		ListTable ListTable = new ListTable();
		ListTable.setName("RISK");

		String[] Title = new String[13];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		Title[5] = "";
		Title[6] = "";
		Title[7] = "";
		Title[8] = "";
		Title[9] = "";
		Title[10] = "";
		Title[11] = "";
		Title[12] = "";
		String FenCom = "";
		String tSql = "";

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
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
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
		sqlbv.sql(tFenSQL);
		sqlbv.put("strMngCom", strMngCom);
		sqlbv.put("strMngCom1", strMngCom.substring(0, 4));
		FenSSRS = exeSql.execSQL(sqlbv);

		/**
		 * @todo 循环分公司
		 */
		for (int i = 0; i < FenSSRS.getMaxRow(); i++) {
			// 取得分公司名称
			String tFenName = FenSSRS.GetText(i + 1, 2);
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			if (strMngCom.length() <= 4) {
				tZhongSQL = " select comcode,name from ldcom where upcomcode = '"
						+ "?upcomcode?" + "'";
			} else {
				tZhongSQL = " select comcode,name from ldcom where comcode = '"
						+ "?comcode?" + "'";
			}
			sqlbv4.sql(tZhongSQL);
			sqlbv4.put("upcomcode", FenSSRS.GetText(i + 1, 1));
			sqlbv4.put("comcode", strMngCom.substring(0, 6));
			ZhongSSRS = exeSql.execSQL(sqlbv4);
			// 循环中支
			for (int j = 0; j < ZhongSSRS.getMaxRow(); j++) {
				// 中支名称
				String tZhongName = ZhongSSRS.GetText(j + 1, 2);
				SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
				if (strMngCom.length() <= 6) {
					tYingSQL = " select comcode,name from ldcom where upcomcode = '"
							+ "?upcomcode?" + "'";
				} else {
					tYingSQL = " select comcode,name from ldcom where comcode = '"
							+ "?comcode?" + "'";
				}
				sqlbv6.sql(tYingSQL);
				sqlbv6.put("upcomcode", ZhongSSRS.GetText(j + 1, 1));
				sqlbv6.put("comcode", strMngCom.substring(0, 8));
				YingSSRS = exeSql.execSQL(sqlbv6);
				// 循环营销部
				for (int k = 0; k < YingSSRS.getMaxRow(); k++) {
					String tYingName = YingSSRS.GetText(k + 1, 2);
					String[] str1 = new String[13];
					str1[0] = "分公司:";
					str1[1] = tFenName;
					str1[2] = "中心支公司:";
					str1[3] = tZhongName;
					str1[4] = "营业区（营销服务部）：";
					str1[5] = tYingName;
					str1[6] = "";
					str1[7] = "";
					str1[8] = "";
					str1[9] = "";
					str1[10] = "";
					str1[11] = "";
					str1[12] = "";

					logger.debug("FenCom" + FenCom);
					String[] str2 = new String[13]; // 空行
					str2[0] = "出单日期：";
					str2[1] = strStartDate;
					str2[2] = "至";
					str2[3] = strEndDate;
					str2[4] = "打印日期：";
					str2[5] = PubFun.getCurrentDate();
					str2[6] = "  ";
					str2[7] = "  ";
					str2[8] = "  ";
					str2[9] = "  ";
					str2[10] = "  ";
					str2[11] = "  ";
					str2[12] = "  ";

					String[] str3 = new String[13];
					str3[0] = "序号";
					str3[1] = "话务员号";
					str3[2] = "话务员姓名";
					str3[3] = "直销部组";

					str3[4] = "外务员号";
					str3[5] = "外务员姓名";
					str3[6] = "直销部组";

					str3[7] = "保单号";
					str3[8] = "投保人姓名";
					str3[9] = "险种代码";
					str3[10] = "签单日期";
					str3[11] = "  ";
					str3[12] = "  ";

					// ListTable.add(str3);

					// tSql =
					// "Select b.agentcode,b.Name,c.Name,a.contno,
					// a.appntname,a.signdate "
					// + " From lccont a, laagent b,labranchgroup c"
					// +
					// " Where trim(a.agentcode)=trim(b.agentcode) And
					// b.agentgroup=c.agentgroup "
					// + " And a.customgetpoldate Is Null And a.signdate
					// Between('" +
					// strStartDate + "') And ('" + strEndDate + "') "
					// + " and a.managecom='" + YingSSRS.GetText(k + 1, 1) +
					// "' and a.salechnl='" + strSaleChnl + "'";
					SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
					if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
						tSql = " Select (select agentcode from laagent where agentcode in (select agentcode from lacommisiondetail where grpcontno = a.contno) and branchtype = '5' and branchtype2 = '01' and rownum = '1' ),"
								+ " (select name from laagent where agentcode in (select agentcode from lacommisiondetail where grpcontno = a.contno) and branchtype = '5' and branchtype2 = '01' and rownum = '1' ),"
								+ " (select r.branchcode from laagent r where r.agentcode in (select agentcode from lacommisiondetail where grpcontno = a.contno) and branchtype = '5' and branchtype2 = '01' and rownum = '1' ),"

								+ " (select agentcode from laagent where agentcode in (select agentcode from lacommisiondetail where grpcontno = a.contno) and branchtype = '5' and branchtype2 = '02' and rownum = '1' ),"
								+ " (select name from laagent where agentcode in (select agentcode from lacommisiondetail where grpcontno = a.contno) and branchtype = '5' and branchtype2 = '02' and rownum = '1' ),"
								+ " (select r.branchcode from laagent r where r.agentcode in (select agentcode from lacommisiondetail where grpcontno = a.contno) and branchtype = '5' and branchtype2 = '02' and rownum = '1' ),"
								+ " a.contno,"
								+ " a.appntname,"
								+ " (select riskcode"
								+ " from lcpol"
								+ " where lcpol.contno = a.contno"
								+ " and polno = mainpolno) as riskcode,"
								+ " a.signdate"
								+ " From lccont a, ldcontinvoicemap d"
								+ " Where 1 = 1"
								+ " and a.contno=d.contno"
								+ " And a.customgetpoldate Is Null"
								+ " and a.managecom = '"
								+ "?managecom?"
								+ "'"
								// + " and a.salechnl = '" + strSaleChnl + "'"
								+ " and d.makedate between '"
								+ "?strStartDate?"
								+ "' and "
								+ " '"
								+ "?strEndDate?"
								+ "'"
								+ " and d.batchno = (select max(batchno) "
								+ " from ldcontinvoicemap f "
								+ " where f.contno = a.contno"
								+ " and f.opertype = '1')";

						if (strSaleChnl != null && !strSaleChnl.equals("")) {
							if (strSaleChnl.equals("1")) {
								tSql = tSql
										+ " and exists (select 'x' from lmriskapp m "
										+ " where m.riskcode = (select r.riskcode from lcpol r where r.contno = a.contno and r.polno = r.mainpolno and rownum = 1) and m.riskprop = 'I'"
										+ " and m.poltype = 'P')";
							}
							if (strSaleChnl.equals("2")) {
								tSql = tSql
										+ " and exists (select 'x' from lmriskapp m "
										+ " where m.riskcode = (select r.riskcode from lcpol r where r.contno = a.contno and r.polno = r.mainpolno and rownum = 1) and m.riskprop = 'G'"
										+ " and m.poltype = 'P')";
							}
							if (strSaleChnl.equals("3")) {
								tSql = tSql
										+ " and exists (select 'x' from lmriskapp m "
										+ " where m.riskcode = (select r.riskcode from lcpol r where r.contno = a.contno and r.polno = r.mainpolno and rownum = 1) and m.riskprop = 'Y'"
										+ " and m.poltype = 'P')";
							}
							if (strSaleChnl.equals("5")) {
								tSql = tSql
										+ " and exists (select 'x' from lmriskapp m "
										+ " where m.riskcode = (select r.riskcode from lcpol r where r.contno = a.contno and r.polno = r.mainpolno and rownum = 1) "
										+ " and m.riskprop = 'T')";
							}
						}
					}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					tSql = " Select (select agentcode from laagent where agentcode in (select agentcode from lacommisiondetail where grpcontno = a.contno) and branchtype = '5' and branchtype2 = '01'  limit 0,1 ),"
							+ " (select name from laagent where agentcode in (select agentcode from lacommisiondetail where grpcontno = a.contno) and branchtype = '5' and branchtype2 = '01'  limit 0,1 ),"
							+ " (select r.branchcode from laagent r where r.agentcode in (select agentcode from lacommisiondetail where grpcontno = a.contno) and branchtype = '5' and branchtype2 = '01'  limit 0,1 ),"

							+ " (select agentcode from laagent where agentcode in (select agentcode from lacommisiondetail where grpcontno = a.contno) and branchtype = '5' and branchtype2 = '02'  limit 0,1 ),"
							+ " (select name from laagent where agentcode in (select agentcode from lacommisiondetail where grpcontno = a.contno) and branchtype = '5' and branchtype2 = '02'  limit 0,1 ),"
							+ " (select r.branchcode from laagent r where r.agentcode in (select agentcode from lacommisiondetail where grpcontno = a.contno) and branchtype = '5' and branchtype2 = '02'  limit 0,1 ),"
							+ " a.contno,"
							+ " a.appntname,"
							+ " (select riskcode"
							+ " from lcpol"
							+ " where lcpol.contno = a.contno"
							+ " and polno = mainpolno) as riskcode,"
							+ " a.signdate"
							+ " From lccont a, ldcontinvoicemap d"
							+ " Where 1 = 1"
							+ " and a.contno=d.contno"
							+ " And a.customgetpoldate Is Null"
							+ " and a.managecom = '"
							+ "?managecom?"
							+ "'"
							// + " and a.salechnl = '" + strSaleChnl + "'"
							+ " and d.makedate between '"
							+ "?strStartDate?"
							+ "' and "
							+ " '"
							+ "?strEndDate?"
							+ "'"
							+ " and d.batchno = (select max(batchno) "
							+ " from ldcontinvoicemap f "
							+ " where f.contno = a.contno"
							+ " and f.opertype = '1')";

					if (strSaleChnl != null && !strSaleChnl.equals("")) {
						if (strSaleChnl.equals("1")) {
							tSql = tSql
									+ " and exists (select 'x' from lmriskapp m "
									+ " where m.riskcode = (select r.riskcode from lcpol r where r.contno = a.contno and r.polno = r.mainpolno  limit 0,1) and m.riskprop = 'I'"
									+ " and m.poltype = 'P')";
						}
						if (strSaleChnl.equals("2")) {
							tSql = tSql
									+ " and exists (select 'x' from lmriskapp m "
									+ " where m.riskcode = (select r.riskcode from lcpol r where r.contno = a.contno and r.polno = r.mainpolno  limit 0,1) and m.riskprop = 'G'"
									+ " and m.poltype = 'P')";
						}
						if (strSaleChnl.equals("3")) {
							tSql = tSql
									+ " and exists (select 'x' from lmriskapp m "
									+ " where m.riskcode = (select r.riskcode from lcpol r where r.contno = a.contno and r.polno = r.mainpolno  limit 0,1) and m.riskprop = 'Y'"
									+ " and m.poltype = 'P')";
						}
						if (strSaleChnl.equals("5")) {
							tSql = tSql
									+ " and exists (select 'x' from lmriskapp m "
									+ " where m.riskcode = (select r.riskcode from lcpol r where r.contno = a.contno and r.polno = r.mainpolno  limit 0,1) "
									+ " and m.riskprop = 'T')";
						}
					}
					}
					tSql = tSql + " order by riskcode desc";

					logger.debug("tSql" + tSql);
					sqlbv8.sql(tSql);
					sqlbv8.put("managecom", YingSSRS.GetText(k + 1, 1));
					sqlbv8.put("strStartDate", strStartDate);
					sqlbv8.put("strEndDate", strEndDate);
					
					SSRS temp2SSRS = new SSRS();
					temp2SSRS = exeSql.execSQL(sqlbv8);

					if (temp2SSRS != null && temp2SSRS.getMaxRow() > 0
							&& temp2SSRS.getMaxCol() > 0) {
						ListTable.add(str1);
						ListTable.add(str2);
						ListTable.add(str3);
						logger.debug("temp2SSRS.getMaxRow()"
								+ temp2SSRS.getMaxRow());
						for (int l = 1; l <= temp2SSRS.getMaxRow(); l++) {
							String[] stra = new String[13];
							stra[0] = l + "";
							stra[1] = temp2SSRS.GetText(l, 1);
							stra[2] = temp2SSRS.GetText(l, 2);
							stra[3] = getUpComName(temp2SSRS.GetText(l, 3));
							stra[4] = temp2SSRS.GetText(l, 4);
							stra[5] = temp2SSRS.GetText(l, 5);
							stra[6] = getUpComName(temp2SSRS.GetText(l, 6));
							stra[7] = temp2SSRS.GetText(l, 7);
							stra[8] = temp2SSRS.GetText(l, 8);
							stra[9] = temp2SSRS.GetText(l, 9);
							stra[10] = temp2SSRS.GetText(l, 10);
							stra[11] = "  ";
							stra[12] = "  ";
							logger.debug(" stra[0] " + stra[0]);
							logger.debug(" stra[1] " + stra[1]);
							logger.debug(" stra[2] " + stra[2]);
							logger.debug(" stra[3] " + stra[3]);
							ListTable.add(stra);
						}
					}

					// else
					// {
					// logger.debug("没有可打印的信息");
					// buildError("getprintData", "没有需要打印的信息");
					// return false;
					// }

					String[] str5 = new String[13]; // 空行
					str5[0] = "  ";
					str5[1] = "  ";
					str5[2] = "  ";
					str5[3] = "  ";
					str5[4] = "  ";
					str5[5] = "  ";
					str5[6] = "  ";
					str5[7] = "  ";
					str5[8] = "  ";
					str5[9] = "  ";
					str5[10] = "  ";
					str5[11] = "  ";
					str5[12] = "  ";

					String[] str6 = new String[13]; // 空行
					str6[0] = "  ";
					str6[1] = "  ";
					str6[2] = "  ";
					str6[3] = "  ";
					str6[4] = "  ";
					str6[5] = "  ";
					str6[6] = "  ";
					str6[7] = "  ";
					str6[8] = "  ";
					str6[9] = "  ";
					str5[10] = "  ";
					str5[11] = "  ";
					str5[12] = "  ";

					if (temp2SSRS.getMaxRow() > 0) {
						ListTable.add(str5);
						ListTable.add(str6);
					}
				}
			}
		}

		TextTag texttag = new TextTag(); // Create a TextTag instance
		XmlExport xmlexport = new XmlExport(); // Create a XmlExport instance
		xmlexport.createDocument("ContReturnNoLis.vts", "printer"); // appoint
																	// to a
																	// special
																	// output
																	// .vts file
		ListTable alistTable = new ListTable(); // Create a ListTable instance
		alistTable.setName("RISK"); // Appoint to a sepcial flag

		// 将数据装入表单
		if (strSaleChnl != null && strSaleChnl.equals("1")) {
			texttag.add("ListTitle", "个人业务保单未回单清单");
		} else if (strSaleChnl != null && strSaleChnl.equals("1")) {
			texttag.add("ListTitle", "银行代理业务保单未回单清单");
		} else {
			texttag.add("ListTitle", "保单未回单清单");
		}

		xmlexport.addTextTag(texttag);
		xmlexport.addListTable(ListTable, Title);

		// xmlexport.outputDocumentToFile("D:\\", "testHZM1"); //输出xml文档到文件
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
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql("Select agentgroup From labranchgroup Where upbranch='"
				+ "?upAgentgroup?" + "'");
		sqlbv9.put("upAgentgroup", upAgentgroup);
		searchOneSsrs = searchOneSQL.execSQL(sqlbv9);
		search_count = searchOneSsrs.getMaxRow();

		for (int search = 1; search < search_count + 1; search++) {
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql("Select agentgroup From labranchgroup Where upbranch='"
					+ "?upAgentgroup?" + "'");
			sqlbv10.put("upAgentgroup", upAgentgroup);
			searchOneSsrs = searchOneSQL.execSQL(sqlbv10);
			result1 = searchOneSsrs.GetText(search, 1);
			vd.add(result1);
			this.getSearchResult(result1, vd);

		}
		return vd;
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

	/*
	 * @function :in order to debug
	 */
	public static void main(String[] args) {
		String tStrMngCom = "86320000";
		String tIssueDate = "2005-12-07";

		String tPrintDate = "2005-12-07";

		VData tVData = new VData();
		tVData.addElement(tStrMngCom);
		tVData.addElement(tIssueDate);

		tVData.addElement(tPrintDate);
		tVData.addElement("1");

		ContReturnNoLisUI tContReturnNoLisUI = new ContReturnNoLisUI();
		tContReturnNoLisUI.submitData(tVData, "PRINT");
	}
}
