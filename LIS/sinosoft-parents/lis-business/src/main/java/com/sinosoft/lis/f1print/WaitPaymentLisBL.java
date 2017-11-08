package com.sinosoft.lis.f1print;
import java.util.Vector;

import org.apache.log4j.Logger;

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

public class WaitPaymentLisBL {
private static Logger logger = Logger.getLogger(WaitPaymentLisBL.class);
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
	private String strSaleChnl;

	public WaitPaymentLisBL() {
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
		strSaleChnl = (String) cInputData.get(3);
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
		cError.moduleName = "WaitPaymentLisBL";
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
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tZhongSQL);
			sqlbv2.put("upcomcode", FenSSRS.GetText(i + 1, 1));
			sqlbv2.put("comcode", strMngCom.substring(0, 6));
			ZhongSSRS = exeSql.execSQL(sqlbv2);
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
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql(tYingSQL);
				sqlbv3.put("upcomcode", ZhongSSRS.GetText(j + 1, 1));
				sqlbv3.put("comcode", strMngCom.substring(0, 8));
				YingSSRS = exeSql.execSQL(sqlbv3);
				//
				// strSQL = " select name from ldcom where trim(comcode) =
				// trim(Substr('" +
				// strMngCom + "',1,4))";
				// FenSSRS = exeSql.execSQL(strSQL);
				// String FenCom = "";
				// String ZhongCom = "";
				// if (FenSSRS != null && FenSSRS.getMaxRow() > 0)
				// {
				// FenCom = FenSSRS.GetText(1, 1); //分公司。
				// }
				//
				// SSRS ZhongSSRS = new SSRS();
				// strSQL = "select name from ldcom where comcode='" + strMngCom
				// + "'";
				// ZhongSSRS = exeSql.execSQL(strSQL);
				// ZhongCom = ZhongSSRS.GetText(1, 1);
				//
				// SSRS YingSSRS = new SSRS();
				// strSQL = "select comcode,name from ldcom where upcomcode='" +
				// strMngCom +
				// "'" +
				// " and length(trim(comcode)) ='8'";
				// YingSSRS = exeSql.execSQL(strSQL);

				// ExeSQL comdSQL = new ExeSQL();
				// SSRS comSsrs = comdSQL.execSQL(
				// "Select Name From labranchgroup Where trim(branchattr)='" +
				// strAgentGroup + "'");
				// try
				// {
				// comName = comSsrs.GetText(1, 1);
				// }
				// catch (Exception ex)
				// {
				// buildError("getprintData", "机构编码有误");
				// logger.debug("机构编码有误");
				// return false;
				// }
				// evo1

				// Vector emptyV = new Vector();
				// emptyV = getSearchResult(strAgentGroup, strMngCom);
				// // logger.debug("result " + emptyV);
				// if (emptyV.isEmpty())
				// {
				// buildError("getprintData", "没有可打印的信息");
				// logger.debug("没有可打印的信息");
				// return false;
				//
				// }
				// String input = "";
				// input = getAgentGroup(emptyV);

				// strSQL =
				// "Select Distinct (a.Missionprop1), a.Missionprop5,
				// b.Riskcode, b.Prem, "
				// +
				// " (Select nvl(Sum(Paymoney), 0) From Ljtempfee e Where
				// e.Otherno = b.Contno),"
				// + " (b.Prem - (Select Nvl(Sum(Paymoney), 0) From Ljtempfee e
				// Where e.Otherno = b.Contno)),"
				// + " c.Agentcode, c.Name, d.Name, b.Makedate, d.Agentgroup "
				// + " From Lwmission a, Lcpol b, Laagent c, Labranchgroup d "
				// + " Where a.Processid = '0000000003' And a.Activityid =
				// '0000001150' And Trim(a.Missionprop1) = Trim(b.Prtno) And "
				// + " Trim(a.Missionprop3) = Trim(c.Agentcode) And
				// Trim(b.Polno) = Trim(b.Mainpolno) And c.Agentgroup =
				// d.Agentgroup And "
				// + " (b.Prem - (Select Nvl(Sum(Paymoney), 0) From Ljtempfee e
				// Where e.Otherno = b.Contno)) > 0 And (" +
				// input + ") "
				// + " Order By Agentgroup ";
				for (int y = 1; y <= YingSSRS.getMaxRow(); y++) {
					String[] col1 = new String[12];
					col1[0] = "分公司：";
					col1[1] = tFenName;
					col1[2] = "中心支公司：";
					col1[3] = tZhongName;
					col1[4] = "营业区（营销服务部）";
					col1[5] = YingSSRS.GetText(y, 2);
					col1[6] = "";
					col1[7] = "";
					col1[8] = "日期：";
					col1[9] = strIssueDate;
					col1[10] = "";
					col1[11] = "";
					alistTable.add(col1);

					String[] col2 = new String[12];
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
					alistTable.add(col2);

					String[] col3 = new String[12];
					col3[0] = "序号";
					col3[1] = "投保单号";
					col3[2] = "投保人";
					col3[3] = "主险";
					col3[4] = "保费合计";
					col3[5] = "实缴保费";
					col3[6] = "应补缴金额";
					col3[7] = "业务号";
					col3[8] = "业务员姓名";
					col3[9] = "营业部";
					col3[10] = "录单日期";
					col3[11] = "交费形式";
					alistTable.add(col3);
					if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
						strSQL = "select aa, ab, ae, ac, ad, ac - ad, af, ag, ah, ai, aj from ("
								+ " select a.missionprop1 as aa,"
								+ " b.appntname as ab,"
								+ " (select riskcode from lcpol where prtno = b.prtno and polno = mainpolno) ae,"
								+ " (select sum(prem) from lcpol where prtno = b.prtno) as ac,"
								+ " (case when (select sum(paymoney) from ljtempfee where enteraccdate is not null and enteraccdate < '3000-01-01' and confflag='0' and otherno = b.prtno) is not null then (select sum(paymoney) from ljtempfee where enteraccdate is not null and enteraccdate < '3000-01-01' and confflag='0' and otherno = b.prtno)  else 0 end) as ad,"
								+ " '',"
								+ " b.agentcode as af,"
								+ " (select f.name from laagent f where f.agentcode = trim(b.agentcode)) as ag,"
								+ " (select g.name from labranchgroup g where g.agentgroup = b.agentgroup) as ah,"
								+ " b.makedate as ai,"
								+ " (case when (select (select codename from ldcode where codetype='paymode' and code=paymode) from ljtempfeeclass where otherno=b.prtno and rownum = 1) is not null then (select (select codename from ldcode where codetype='paymode' and code=paymode) from ljtempfeeclass where otherno=b.prtno and rownum = 1)  else '未交费' end) as aj"
								+ "  from lwmission a, lccont b"
//								+ " where a.Processid = '0000000003'"
//								+ " And a.Activityid = '0000001150'"
								+ " where a.Activityid in (select activityid from lwactivity  where functionid = '10010042')"
								+ " And a.Missionprop1 = Trim(b.Prtno)"
								+ "  And b.managecom like concat('"
								+ "?managecom?"
								+ "','%')"
								+ " and b.salechnl='"
								+ "?strSaleChnl?"
								+ "'"
								+ ") where ac - ad > 0";
					}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
						strSQL = "select aa, ab, ae, ac, ad, ac - ad, af, ag, ah, ai, aj from ("
								+ " select a.missionprop1 as aa,"
								+ " b.appntname as ab,"
								+ " (select riskcode from lcpol where prtno = b.prtno and polno = mainpolno) ae,"
								+ " (select sum(prem) from lcpol where prtno = b.prtno) as ac,"
								+ " (case when (select sum(paymoney) from ljtempfee where enteraccdate is not null and enteraccdate < '3000-01-01' and confflag='0' and otherno = b.prtno) is not null then (select sum(paymoney) from ljtempfee where enteraccdate is not null and enteraccdate < '3000-01-01' and confflag='0' and otherno = b.prtno)  else 0 end) as ad,"
								+ " '',"
								+ " b.agentcode as af,"
								+ " (select f.name from laagent f where f.agentcode = trim(b.agentcode)) as ag,"
								+ " (select g.name from labranchgroup g where g.agentgroup = b.agentgroup) as ah,"
								+ " b.makedate as ai,"
								+ " (case when (select (select codename from ldcode where codetype='paymode' and code=paymode) from ljtempfeeclass where otherno=b.prtno limit 0,1) is not null then (select (select codename from ldcode where codetype='paymode' and code=paymode) from ljtempfeeclass where otherno=b.prtno limit 0,1)  else '未交费' end) as aj"
								+ "  from lwmission a, lccont b"
//								+ " where a.Processid = '0000000003'"
//								+ " And a.Activityid = '0000001150'"
								+ " where a.Activityid in (select activityid from lwactivity  where functionid = '10010042')"
								+ " And a.Missionprop1 = Trim(b.Prtno)"
								+ "  And b.managecom like concat('"
								+ "?managecom?"
								+ "','%')"
								+ " and b.salechnl='"
								+ "?strSaleChnl?"
								+ "'"
								+ ") where ac - ad > 0";
					}
					
					SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
					sqlbv4.sql(strSQL);
					sqlbv4.put("managecom", YingSSRS.GetText(y, 1));
					sqlbv4.put("strSaleChnl", strSaleChnl);
					SSRS ssrs = exeSql.execSQL(sqlbv4);
					int i_count = ssrs.getMaxRow();
					// if (i_count == 0)
					// {
					// logger.debug("没有可打印的信息");
					// buildError("getprintData", "没有需要打印的信息");
					// return false;
					// }

					// 将数据装入表单
					for (int l = 1; l <= i_count; l++) {
						String[] cols = new String[12];
						try {
							cols[0] = l + "";
							cols[1] = ssrs.GetText(l, 1);
							cols[2] = ssrs.GetText(l, 2);
							cols[3] = ssrs.GetText(l, 3);
							cols[4] = ssrs.GetText(l, 4);
							cols[5] = ssrs.GetText(l, 5);
							cols[6] = ssrs.GetText(l, 6);

							if (cols[5].equals("null")) {
								cols[5] = "0";
								cols[6] = ssrs.GetText(l, 4);
							}
							cols[7] = ssrs.GetText(l, 7);
							cols[8] = ssrs.GetText(l, 8);
							cols[9] = ssrs.GetText(l, 9);
							cols[10] = ssrs.GetText(l, 10);
							cols[11] = ssrs.GetText(l, 11);
						} catch (Exception ex) {
							buildError("getprintData", "抽取数据失败");
							logger.debug("抽取数据失败");
							return false;
						}

						logger.debug("抽取信息成功！！");
						alistTable.add(cols);
					}
					String[] col4 = new String[12];
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
					alistTable.add(col4);
					String[] col5 = new String[12];
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
					alistTable.add(col5);
					String[] col6 = new String[12];
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
					alistTable.add(col6);

				}
			}
		}
		String[] col = new String[1];
		TextTag texttag = new TextTag(); // Create a TextTag instance
		XmlExport xmlexport = new XmlExport(); // Create a XmlExport instance
		xmlexport.createDocument("WaitPaymentLis.vts", "printer"); // appoint
																	// to a
																	// special
																	// output
																	// .vts file

		xmlexport.addListTable(alistTable, col);
		texttag.add("IssueDate", strIssueDate);
		texttag.add("MngCom", comName);

		if (strSaleChnl != null && strSaleChnl.equals("1")) {
			texttag.add("ListTitle", "个人业务待收费清单");
		} else if (strSaleChnl != null && strSaleChnl.equals("3")) {
			texttag.add("ListTitle", "银行代理业务待收费清单");
		}

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
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql("Select agentgroup From labranchgroup Where upbranch='"
				+ "?upAgentgroup?" + "'");
		sqlbv5.put("upAgentgroup", upAgentgroup);
		searchOneSsrs = searchOneSQL.execSQL(sqlbv5);
		search_count = searchOneSsrs.getMaxRow();

		for (int search = 1; search < search_count + 1; search++) {
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql("Select agentgroup From labranchgroup Where upbranch='"
					+ "?upAgentgroup?" + "'");
			sqlbv6.put("upAgentgroup", upAgentgroup);
			searchOneSsrs = searchOneSQL.execSQL(sqlbv6);
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
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql("Select c.agentgroup From labranchgroup b,labranchgroup c Where c.branchattr Like concat(trim(b.branchattr) , '%') And c.managecom='"
				+ "?str2?"
				+ "' And b.branchlevel = '03' And b.agentgroup in(Select agentgroup From labranchgroup Where trim(branchattr)='"
				+ "?str1?"
				+ "'And managecom='"
				+ "?str2?"
				+ "') Order By agentgroup");
		sqlbv7.put("str2", str2);
		sqlbv7.put("str1", str1);
		searchOneSsrs = searchOneSQL.execSQL(sqlbv7);
		search_count = searchOneSsrs.getMaxRow();
		for (int i = 1; i < search_count + 1; i++) {
			result1 = searchOneSsrs.GetText(i, 1);
			output.add(result1);
		}
		return output;
	}

	private Vector getOrder(Vector v) {
		Vector output = new Vector();
		String temp = "";

		output = v;
		for (int i = 0; i < output.size() - 2; i++) {
			for (int k = 1 + i; k < output.size() - 1; k++) {
				if (Integer.parseInt(output.get(i).toString()) < Integer
						.parseInt(output.get(k).toString())) {
					continue;
				}

				if (Integer.parseInt(output.get(i).toString()) > Integer
						.parseInt(output.get(k).toString())) {
					temp = output.get(i).toString();
					output.set(i, output.get(k).toString());
					output.set(k, temp);
				}
			}
		}
		logger.debug("newreslt  " + output);
		return output;
	}

	private String getAgentGroup(Vector v) {

		Vector agent = new Vector();
		agent = v;
		String goOne = " or";
		String goTwo = " d.agentGroup='";
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
		String tIssueDate = "2005-08-23";
		String tAgentGroup = "0001";

		VData tVData = new VData();
		tVData.addElement(tStrMngCom);
		tVData.addElement(tIssueDate);
		tVData.addElement(tAgentGroup);

		// WaitPaymentLisBL tWaitPaymentLisBL = new WaitPaymentLisBL();
		// Vector result=tWaitPaymentLisBL.getSearchResult("0201","86320000" );
		// logger.debug("result=="+result);
		WaitPaymentLisUI tWaitPaymentLisUI = new WaitPaymentLisUI();
		tWaitPaymentLisUI.submitData(tVData, "PRINT");
	}
}
