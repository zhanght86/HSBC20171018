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
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 *
 * @author 刘岩松 function :print InputEfficiency or print CheckEfficiency Business
 *         Logic layer
 * @version 1.0
 * @date 2003-04-04
 */

public class ContReturnLisBL {
private static Logger logger = Logger.getLogger(ContReturnLisBL.class);
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

	// private String strAgentGroup;
	private int departmentNo;

	private String strDepartment;

	private String strSQL;

	private String strEndDate;

	private String strSaleChnl;

	private String strStatType;

	public ContReturnLisBL() {
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
	 * ************************************************************************
	 * Name : getInputData() function :receive data from jsp and check date
	 * check :judge whether startdate begin enddate return :true or false
	 */
	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		strStartDate = (String) cInputData.get(1);
		// strAgentGroup= (String) cInputData.get(2);
		strEndDate = (String) cInputData.get(2);
		strSaleChnl = (String) cInputData.get(3);
		strStatType = (String) cInputData.get(4);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	/**
	 * ************************************************************************
	 * name : buildError function : Prompt error message return :error message
	 */

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "ContReturnLisBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * ************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData() {
		int tResultCount = 0;
		ListTable ListTable = new ListTable();
		ListTable.setName("RISK");
		String[] Title = new String[11];
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
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
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
		sqlbv1.sql(tFenSQL);
		sqlbv1.put("strMngCom", strMngCom);
		sqlbv1.put("strMngCom1", strMngCom.substring(0, 4));
		FenSSRS = exeSql.execSQL(sqlbv1);

		/**
		 * @todo 循环分公司
		 */
		for (int i = 0; i < FenSSRS.getMaxRow(); i++) {
			// 取得分公司名称
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			String tFenName = FenSSRS.GetText(i + 1, 2);
			if (strMngCom.length() <= 4) {
				tZhongSQL = " select comcode,name from ldcom where upcomcode = '" + "?upcomcode?" + "'";
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
					String[] str1 = new String[11];
					str1[0] = "分公司:";
					str1[1] = tFenName;
					str1[2] = "中心支公司:";
					str1[3] = tZhongName;
					str1[4] = "营业区（营销服务部）：";
					str1[5] = tYingName;
					if (strStatType != null && strStatType.equals("1")) {
						str1[6] = "出单日期：";
					} else if (strStatType != null && strStatType.equals("2")) {
						str1[6] = "回单日期：";
					}
					str1[7] = strStartDate + "至" + strEndDate;
					str1[8] = "打印日期：";
					str1[9] = PubFun.getCurrentDate();

					logger.debug("FenCom" + FenCom);
					String[] str2 = new String[11]; // 空行
					str2[0] = "  ";
					str2[1] = "  ";
					str2[2] = "  ";
					str2[3] = "  ";
					str2[4] = "  ";
					str2[5] = "  ";
					str2[6] = "  ";
					str2[7] = "  ";
					str2[8] = "  ";
					str2[9] = "  ";
					str2[10] = "  ";

					String[] str3 = new String[11];
					str3[0] = "序号";
					str3[1] = "业务员号";
					str3[2] = "业务员姓名";
					str3[3] = "营业部";
					str3[4] = "保单号";
					str3[5] = "投保人姓名";
					str3[6] = "险种代码";
					str3[7] = "出单日期";
					str3[8] = "签收日期";
					str3[9] = "回单日期";
					str3[10] = "操作员";

					// ListTable.add(str3);

					// tSql = "Select b.agentcode,b.Name,c.Name,a.contno,
					// a.appntname,e.makedate,a.customgetpoldate,d.makedate "
					// + " From lccont a, laagent b,labranchgroup c,lzsyscertify
					// d"
					// + " Where trim(a.agentcode)=trim(b.agentcode) And
					// b.agentgroup=c.agentgroup And a.contno=d.certifyno"
					// + " And d.certifycode='9995' And d.makedate Between('" +
					// strStartDate + "') And ('" + strEndDate + "') and
					// a.managecom='" +
					// YingSSRS.GetText(k+1, 1) + "' and a.SaleChnl='" +
					// strSaleChnl + "'";
					/**
					 * @todo 根据选择的统计类型stattype不同统计不同的时间段 1-根据出单日期统计 2-根据回单日期统计
					 */
					SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
					if (strStatType != null && strStatType.equals("1")) {
						tSql = "Select Substr(d.receivecom, 2, 8),(select b.Name from laagent b where b.agentcode = Substr(d.receivecom, 2, 8)),(select cnm.BranchCode from laagent cnm where cnm.agentcode = Substr(d.receivecom, 2, 8)),d.certifyno,(select appntname from lccont where contno = rpad(d.certifyno, lislen('lccont', 'contno'), ' ')),(select riskcode from lcpol where lcpol.contno = rpad(d.certifyno, lislen('lccont', 'contno'), ' ') and lcpol.polno = lcpol.mainpolno) as riskcode,cc.md as md,d.takebackdate,d.makedate,d.operator From lzsyscertify d,(select contno, max(makedate) as md from ldcontinvoicemap f Where 1 = 1 and f.opertype = '1' and makedate between  '"
								+ "?strStartDate?"
								+ "' and '"
								+ "?strEndDate?"
								+ "' group by contno) cc Where trim(d.certifycode) = '9995' and Substr(d.sendoutcom, 2, 8) = '"
								+ "?sendoutcom?"
								+ "' and cc.contno = rpad(d.certifyno, lislen('lccont', 'contno'), ' ') and exists (select 'x' from lccont where contno = rpad(d.certifyno, lislen('lccont', 'contno'), ' ')and SaleChnl = '"
								+ "?strSaleChnl?"
								+ "') order by d.makedate, d.operator, d.maketime";
					} else if (strStatType != null && strStatType.equals("2")) {
						tSql = "Select Substr(d.receivecom, 2, 8) as a,(select b.Name from laagent b where b.agentcode = Substr(d.receivecom, 2, 8)) as b,(select c. BranchCode from laagent c where c.agentcode = Substr(d.receivecom, 2, 8)) as c,d.certifyno as d,(select a.appntname from lccont a where a.contno = rpad(d.certifyno, lislen('lccont', 'contno'), ' ')) as e,(select riskcode from lcpol where lcpol.contno = rpad(d.certifyno, lislen('lccont', 'contno'), ' ') and lcpol.polno = lcpol.mainpolno) as riskcode,(select e.makedate from ldcontinvoicemap e where e.rowid =(select min(rowid) from ldcontinvoicemap g where g.contno = rpad(d.certifyno, lislen('lccont', 'contno'), ' ') and g.opertype = '1' and g.makedate = (select max(makedate) from ldcontinvoicemap f where f.contno = rpad(d.certifyno,lislen('lccont', 'contno'),' ') and f.opertype = '1'))) as makedate,d.takebackdate as f,d.makedate as bb,d.operator as bbb From lzsyscertify d Where 1 = 1 And trim(d.certifycode) = '9995' and d.makedate between '"
								+ "?strStartDate?"
								+ "' and '"
								+ "?strEndDate?"
								+ "' and Substr(d.sendoutcom, 2, 8) = '"
								+ "?sendoutcom?"
								+ "' and exists(select 'x' from lccont where contno = rpad(d.certifyno, lislen('lccont', 'contno'), ' ') and SaleChnl = '"
								+ "?strSaleChnl?" + "') order by bb,bbb";
					}
					sqlbv8.sql(tSql);
					sqlbv8.put("strStartDate", strStartDate);
					sqlbv8.put("strEndDate", strEndDate);
					sqlbv8.put("sendoutcom", YingSSRS.GetText(k + 1, 1));
					sqlbv8.put("strSaleChnl", strSaleChnl);
					logger.debug("tSql" + tSql);

					SSRS temp2SSRS = new SSRS();
					temp2SSRS = exeSql.execSQL(sqlbv8);

					if (temp2SSRS != null && temp2SSRS.getMaxRow() > 0
							&& temp2SSRS.getMaxCol() > 0) {
						ListTable.add(str1);
						ListTable.add(str2);
						ListTable.add(str3);
						tResultCount = tResultCount + 1;
						logger.debug("temp2SSRS.getMaxRow()"
								+ temp2SSRS.getMaxRow());
						for (int l = 1; l <= temp2SSRS.getMaxRow(); l++) {
							String[] stra = new String[11];
							stra[0] = l + "";
							stra[1] = temp2SSRS.GetText(l, 1);
							stra[2] = temp2SSRS.GetText(l, 2);
							stra[3] = getUpComName(temp2SSRS.GetText(l, 3));
							stra[4] = temp2SSRS.GetText(l, 4);
							stra[5] = temp2SSRS.GetText(l, 5);
							stra[6] = temp2SSRS.GetText(l, 6);
							stra[7] = temp2SSRS.GetText(l, 7);
							stra[8] = temp2SSRS.GetText(l, 8);
							stra[9] = temp2SSRS.GetText(l, 9);
							stra[10] = temp2SSRS.GetText(l, 10);
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

					String[] str5 = new String[11]; // 空行
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
					String[] str6 = new String[11]; // 空行
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
					str6[10] = "  ";

					if (temp2SSRS.getMaxRow() > 0) {
						ListTable.add(str5);
						ListTable.add(str6);
					}
				}
			}
		}

		if (tResultCount == 0) {
			CError tError = new CError();
			tError.moduleName = "ContReturnLisBL";
			tError.functionName = "getPrintData";
			tError.errorMessage = "在该查询条件内没有打印信息，请确认查询条件是否正确！！！";
			this.mErrors.addOneError(tError);
			return false;
		}

		TextTag texttag = new TextTag(); // Create a TextTag instance
		XmlExport xmlexport = new XmlExport(); // Create a XmlExport instance
		xmlexport.createDocument("ContReturnLis.vts", "printer"); // appoint
																	// to
		// a special
		// output .vts
		// file
		ListTable alistTable = new ListTable(); // Create a ListTable instance
		alistTable.setName("RISK"); // Appoint to a sepcial flag

		// 将数据装入表单
		if (strSaleChnl != null && strSaleChnl.equals("1")) {
			texttag.add("ListTitle", "个人业务保单回单清单");
		} else if (strSaleChnl != null && strSaleChnl.equals("3")) {
			texttag.add("ListTitle", "银行代理业务保单回单清单");
		} else {
			texttag.add("ListTitle", "保单回单清单");
		}
		xmlexport.addTextTag(texttag);

		xmlexport.addListTable(ListTable, Title);

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
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql("Select agentgroup From labranchgroup Where upbranch='"
				+ "?upAgentgroup?" + "'");
		sqlbv10.put("upAgentgroup", upAgentgroup);
		searchOneSsrs = searchOneSQL.execSQL(sqlbv10);
		search_count = searchOneSsrs.getMaxRow();

		for (int search = 1; search < search_count + 1; search++) {
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql("Select agentgroup From labranchgroup Where upbranch='"
					+ "?upAgentgroup?" + "'");
			sqlbv11.put("upAgentgroup", upAgentgroup);
			searchOneSsrs = searchOneSQL.execSQL(sqlbv11);
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
		String tStrMngCom = "86";
		String tIssueDate = "2006-04-01";
		// String tAgentGroup = "0003";
		String tPrintDate = "2006-04-05";
		String tSaleChnl = "1";
		String tStatType = "1";

		VData tVData = new VData();
		tVData.addElement(tStrMngCom);
		tVData.addElement(tIssueDate);
		// tVData.addElement(tAgentGroup);
		tVData.addElement(tPrintDate);
		tVData.addElement(tSaleChnl);
		tVData.addElement(tStatType);

		ContReturnLisUI tContReturnLisUI = new ContReturnLisUI();
		tContReturnLisUI.submitData(tVData, "PRINT");
	}
}
