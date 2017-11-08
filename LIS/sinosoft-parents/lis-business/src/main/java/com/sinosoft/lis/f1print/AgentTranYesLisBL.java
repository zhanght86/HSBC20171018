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

public class AgentTranYesLisBL {
private static Logger logger = Logger.getLogger(AgentTranYesLisBL.class);
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

	public AgentTranYesLisBL() {
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
		strPrintDate = (String) cInputData.get(2);
		strReturnDate = (String) cInputData.get(3);
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
		cError.moduleName = "AgentTranYesLisBL";
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
				String[] colx = new String[7];
				colx[0] = "划款日期：";
				colx[1] = strIssueDate;
				colx[2] = "   ";
				colx[3] = "打印日期：";
				colx[4] = strPrintDate;
				colx[5] = "   ";
				colx[6] = "   ";
				alistTable.add(colx);

				String[] col1 = new String[7];
				col1[0] = "分公司：";
				col1[1] = tFenName;
				col1[2] = "中心支公司：";
				col1[3] = tZhongName;
				col1[4] = "  ";
				col1[5] = "  ";
				col1[6] = "  ";
				alistTable.add(col1);

				String[] col2 = new String[7];
				col2[0] = "   ";
				col2[1] = "   ";
				col2[2] = "   ";
				col2[3] = "   ";
				col2[4] = "   ";
				col2[5] = "   ";
				col2[6] = "   ";
				alistTable.add(col2);
				String[] col3 = new String[7];
				col3[0] = "序号";
				col3[1] = "录入日期";
				col3[2] = "银行";
				col3[3] = "代办所号";
				col3[4] = "投保单号";
				col3[5] = "投保人";
				col3[6] = "金额";
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
					SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
					 if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
						 strSQL = "select (select paydate from ljtempfeeclass where otherno=a.polno and rownum=1 ),"
									+ " (select bankname from ldbank where bankcode = a.bankcode and rownum = 1),"
									+ " a.comcode,"
									+ " a.polno,"
									+ " (case when a.accname is not null then a.accname  else '' end),"
									+ " a.paymoney"
									+ " from lyreturnfrombankb a"
									+ " where a.banksuccflag = '0000'"
									+ "  and a.comcode like concat('"+ "?comcode?" + "','%')"
									+ "  and a.senddate='"
									+ "?strIssueDate?"
									+ "'"
									+ " and a.dealtype='S'" + " and a.notype = '7'";
							// " and exists (select 'X'" +
							// " from lybanklog b" +
							// " where b.salechnl = '3'" +
							// " and b.operationtype = '6'" +
							// " and b.serialno = a.serialno)";
							if (strReturnDate != null && !strReturnDate.equals("")) {
								strSQL = strSQL + " and a.bankdealdate = '"
										+ "?strReturnDate?" + "'";
							}
				        }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				        	strSQL = "select (select paydate from ljtempfeeclass where otherno=a.polno  limit 0,1 ),"
									+ " (select bankname from ldbank where bankcode = a.bankcode  limit 0,1),"
									+ " a.comcode,"
									+ " a.polno,"
									+ " (case when a.accname is not null then a.accname  else '' end),"
									+ " a.paymoney"
									+ " from lyreturnfrombankb a"
									+ " where a.banksuccflag = '0000'"
									+ "  and a.comcode like concat('"+ "?comcode?" + "','%')"
									+ "  and a.senddate='"
									+ "?strIssueDate?"
									+ "'"
									+ " and a.dealtype='S'" + " and a.notype = '7'";
							// " and exists (select 'X'" +
							// " from lybanklog b" +
							// " where b.salechnl = '3'" +
							// " and b.operationtype = '6'" +
							// " and b.serialno = a.serialno)";
							if (strReturnDate != null && !strReturnDate.equals("")) {
								strSQL = strSQL + " and a.bankdealdate = '"
										+ "?strReturnDate?" + "'";
							}
				        }
					 sqlbv3.sql(strSQL);
					 sqlbv3.put("comcode", YingSSRS.GetText(k + 1, 1));
					 sqlbv3.put("strIssueDate", strIssueDate);
					 sqlbv3.put("strReturnDate", strReturnDate);

					ExeSQL exeSQL = new ExeSQL();
					SSRS ssrs = exeSql.execSQL(sqlbv3);
					int i_count = ssrs.getMaxRow();

					// 将数据装入表单
					for (int l = 1; l <= i_count; l++) {
						String[] cols = new String[7];
						try {
							cols[0] = l + "";
							cols[1] = ssrs.GetText(l, 1);
							cols[2] = ssrs.GetText(l, 2);
							cols[3] = ssrs.GetText(l, 3);
							cols[4] = ssrs.GetText(l, 4);
							cols[5] = ssrs.GetText(l, 5);
							cols[6] = ssrs.GetText(l, 6);
						} catch (Exception ex) {
							buildError("getprintData", "抽取收款数据时出错");
							System.err.println("抽取收款数据时出错");
						}

						logger.debug("2005-08-09描述表中取出信息！！！！");
						alistTable.add(cols);
					}

				}
				String[] col4 = new String[7];
				col4[0] = "   ";
				col4[1] = "   ";
				col4[2] = "   ";
				col4[3] = "   ";
				col4[4] = "   ";
				col4[5] = "   ";
				col4[6] = "   ";
				alistTable.add(col4);

				String[] col5 = new String[7];
				col5[0] = "   ";
				col5[1] = "   ";
				col5[2] = "   ";
				col5[3] = "   ";
				col5[4] = "   ";
				col5[5] = "   ";
				col5[6] = "   ";
				alistTable.add(col5);

				String[] col6 = new String[7];
				col6[0] = "   ";
				col6[1] = "   ";
				col6[2] = "   ";
				col6[3] = "   ";
				col6[4] = "   ";
				col6[5] = "   ";
				col6[6] = "   ";
				alistTable.add(col6);

			}
		}
		String[] col = new String[1];
		TextTag texttag = new TextTag(); // Create a TextTag instance
		XmlExport xmlexport = new XmlExport(); // Create a XmlExport instance
		xmlexport.createDocument("AgentTranYesLis.vts", "printer"); // appoint
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
		String tStrMngCom = "861100";
		String tIssueDate = "2005-08-28";
		String tPrintDate = "2005-08-28";

		VData tVData = new VData();
		tVData.addElement(tStrMngCom);
		tVData.addElement(tIssueDate);
		tVData.addElement(tPrintDate);

		AgentTranYesLisUI tAgentTranYesLisUI = new AgentTranYesLisUI();
		tAgentTranYesLisUI.submitData(tVData, "PRINT");
	}
}
