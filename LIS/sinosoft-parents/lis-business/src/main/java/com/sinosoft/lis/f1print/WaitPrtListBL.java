package com.sinosoft.lis.f1print;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LABranchGroupDB;
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
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 *
 * <p>
 * Company: Sinosoft
 * </p>
 *
 * @author not attributable
 * @version 6.0
 */

public class WaitPrtListBL {
private static Logger logger = Logger.getLogger(WaitPrtListBL.class);
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/*
	 * @define globe variable
	 */
	private String strMngCom;

	private String comName;

	private String strStartIssueDate;

	private String strEndIssueDate;

	private String strSubCom;

	private String strAgentgroup;

	private String strAgentGroup;

	private int departmentNo;

	private String strDepartment;

	private String strSQL;

	private String strSaleChnl;

	private String strPrtState;

	public WaitPrtListBL() {
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
		strStartIssueDate = (String) cInputData.get(1);
		strAgentGroup = (String) cInputData.get(2);
		strSaleChnl = (String) cInputData.get(3);
		strEndIssueDate = (String) cInputData.get(4);
		strPrtState = (String) cInputData.get(5);

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
		cError.moduleName = "WaiPrtListBL";
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
		// 分公司数量
		int tFenNum = 1;
		// 中支数量
		int tZhongNum = 1;
		// 营销服务部数量
		int tYingNum = 1;
		boolean titleflag = false;

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
		tFenNum = FenSSRS.getMaxRow();
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
			tFenNum = ZhongSSRS.getMaxRow();
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
				// 循环营销部
				for (int k = 0; k < YingSSRS.getMaxRow(); k++) {
					titleflag = false;
					String tYingName = YingSSRS.GetText(k + 1, 2);
					String[] col1 = new String[8];
					col1[0] = "分公司：";
					col1[1] = tFenName;
					col1[2] = "中心支公司：" + tZhongName;
					col1[3] = "营业区（营销服务部）";
					col1[4] = tYingName;
					col1[5] = "     ";
					col1[6] = "发出日期：";
					col1[7] = strStartIssueDate + "至" + strEndIssueDate;

					String[] col3 = new String[9];
					col3[0] = "序号";
					col3[1] = "打印流水号";
					col3[2] = "投保单号";
					col3[3] = "通知书类型";
					col3[4] = "发出日期";
					col3[5] = "投保人";
					col3[6] = "业务员号";
					col3[7] = "业务员姓名";
					col3[8] = "营业部、营业组";

					// *************************************新契约通知书 start
					// ***********************************//
					// *****************************************************************************************//
					strSQL = " select a.prtseq,"
							+ " a.otherno,"
							+ " case"
							+ " when a.code = '03' then"
							+ " '体检通知书'"
							+ " when a.code = '04' then"
							+ " '生调通知书'"
							+ " when a.code = '14' then"
							+ " '业务员通知书'"
							+ " when a.code = '17' then"
							+ " '客户合并通知书'"
							+ " when a.code = '08' then"
							+ " '溢缴退费通知书'"
							+ " when a.code = '09' then"
							+ " '撤保退费通知书'"
							+ "  when a.code = '18' then"
							+ " '逾期通知书'"
							+ " when a.code = '15' then"
							+ " '催缴通知书'"
							+ " when a.code = '16' then"
							+ "  '逾期划款通知书'"
							+ " when a.code = 'BF00' then"
							+ "  '补费通知书'"
							+ " when a.code = 'JB00' then"
							+ "  '直接拒保通知书'"
							+ " else"
							+ " (select codename"
							+ "   from ldcode"
							+ "  where code = a.code"
							+ "   and codetype = 'uwnoticetype')"
							+ " end,"
							+ " a.makedate,"
							+ " b.appntname,"
							+ "  b.agentcode,"
							+ "  (select name from laagent where agentcode = trim(b.agentcode)),"
							+ " (select h.branchcode from laagent h where h.agentcode = trim(b.agentcode))"
							+ " from loprtmanager a, lccont b" + " where 1=1 ";
					if (strStartIssueDate != null
							&& !strStartIssueDate.equals("")) {
						strSQL = strSQL + " and a.makedate >= '"
								+ "?strStartIssueDate?" + "'";
					}
					strSQL = strSQL
							+ " and a.makedate <= '"
							+ "?strEndIssueDate?"
							+ "'"
							+ " and a.otherno = b.prtno"
							+ " and a.stateflag='"
							+ "?strPrtState?"
							+ "'"
							+ " and b.appflag = '0'"
							+ " and b.uwflag!='a'"
							+ " and (b.state is null or (b.state is not null and b.state not in ('1002&&&&', '1003&&&&')))"
							+ " and a.managecom like concat('"
							+ "?managecom?"
							+ "','%')"
							+ " and a.code in"
							+ "  ('00', '03', '04', '06', '14','16','18','17', '81', '82', '83', '84', '86', '85', '87', '88', '89','BF00','JB00')";

					logger.debug("*************************");
					logger.debug("*************************");
					logger.debug("strSaleChnl=" + strSaleChnl);
					logger.debug("*************************");
					logger.debug("*************************");

					if (strSaleChnl != null && !strSaleChnl.equals("")) {
						strSQL = strSQL + " and b.salechnl='" + "?strSaleChnl?"
								+ "'";
					}
					SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
					sqlbv4.sql(strSQL);
					sqlbv4.put("strStartIssueDate", strStartIssueDate);
					sqlbv4.put("strEndIssueDate", strEndIssueDate);
					sqlbv4.put("strPrtState", strPrtState);
					sqlbv4.put("strSaleChnl", strSaleChnl);
					sqlbv4.put("managecom", YingSSRS.GetText(k + 1, 1));
					SSRS ssrs = exeSql.execSQL(sqlbv4);
					int i_count = ssrs.getMaxRow();
					if (i_count != 0) {
						titleflag = true;
						alistTable.add(col1);
						alistTable.add(col3);
					}
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
							cols[8] = getUpComName(ssrs.GetText(l, 8));
						} catch (Exception ex) {
							buildError("getprintData", "新契约通知书 抽取数据失败");
							logger.debug("新契约通知书 抽取数据失败");
							return false;
						}

						logger.debug("新契约通知书 抽取信息成功！！");
						alistTable.add(cols);
					}

					strSQL = " select distinct a.prtseq,"
							+ " a.otherno,"
							+ " case"
							+ " when a.code = '03' then"
							+ " '体检通知书'"
							+ " when a.code = '04' then"
							+ " '生调通知书'"
							+ " when a.code = '14' then"
							+ " '业务员通知书'"
							+ " when a.code = '17' then"
							+ " '客户合并通知书'"
							+ " when a.code = '08' then"
							+ " '溢缴退费通知书'"
							+ " when a.code = '09' then"
							+ " '撤保退费通知书'"
							+ "  when a.code = '18' then"
							+ " '逾期通知书'"
							+ " when a.code = '15' then"
							+ " '催缴通知书'"
							+ " when a.code = '16' then"
							+ "  '逾期划款通知书'"
							+ " else"
							+ " (select codename"
							+ "   from ldcode"
							+ "  where code = a.code"
							+ "   and codetype = 'uwnoticetype')"
							+ " end,"
							+ " a.makedate,"
							+ " '',"
							+ "  b.agentcode,"
							+ "  (select name from laagent where agentcode = trim(b.agentcode)),"
							+ " (select h.branchcode from laagent h where h.agentcode = trim(b.agentcode))"
							+ " from loprtmanager a, ljtempfee b"
							+ " where 1 = 1";
					if (strStartIssueDate != null
							&& !strStartIssueDate.equals("")) {
						strSQL = strSQL + " and a.makedate >= '"
								+ "?strStartIssueDate?" + "'";
					}
					strSQL = strSQL
							+ " and a.makedate <= '"
							+ "?strEndIssueDate?"
							+ "'"
							+ " and a.otherno = b.otherno"
							+ " and a.stateflag='"
							+ "?strPrtState?"
							+ "'"
							+ " and a.managecom like concat('"
							+ "?managecom?"
							+ "','%')"
							+ " and a.code in ('15')"
							+ " and not exists(select 'x' from lcpol p where p.contno = a.otherno and p.uwflag = 'a')"
							+ " and not exists(select 'x' from lccont t where t.contno = a.otherno and t.state = '1002&&&&')"
							+ " and not exists(select 'x' from lccont tt where tt.contno = a.otherno and tt.state = '1003&&&&')";

					if (strSaleChnl != null && !strSaleChnl.equals("")) {
						strSQL = strSQL + " and b.salechnl='" + "?strSaleChnl?"
								+ "'";
					}
					SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
					sqlbv5.sql(strSQL);
					sqlbv5.put("strStartIssueDate", strStartIssueDate);
					sqlbv5.put("strEndIssueDate", strEndIssueDate);
					sqlbv5.put("strPrtState", strPrtState);
					sqlbv5.put("managecom", YingSSRS.GetText(k + 1, 1));
					sqlbv5.put("strSaleChnl", strSaleChnl);
					SSRS ssrs8 = exeSql.execSQL(sqlbv5);
					int i_count8 = ssrs8.getMaxRow();
					logger.debug("i_count8=" + i_count8);

					if (titleflag == false && i_count8 != 0) {
						titleflag = true;
						alistTable.add(col1);
						alistTable.add(col3);
					}
					// 将数据装入表单
					for (int l = 1; l <= i_count8; l++) {
						String[] cols = new String[9];
						try {
							cols[0] = i_count + l + "";
							cols[1] = ssrs8.GetText(l, 1);
							cols[2] = ssrs8.GetText(l, 2);
							cols[3] = ssrs8.GetText(l, 3);
							cols[4] = ssrs8.GetText(l, 4);
							cols[5] = ssrs8.GetText(l, 5);
							cols[6] = ssrs8.GetText(l, 6);
							cols[7] = ssrs8.GetText(l, 7);
							cols[8] = getUpComName(ssrs8.GetText(l, 8));
						} catch (Exception ex) {
							buildError("getprintData", "新契约通知书 抽取数据失败");
							logger.debug("新契约通知书 抽取数据失败");
							return false;
						}

						logger.debug("新契约通知书 抽取信息成功！！");
						alistTable.add(cols);
					}

					// *****************************************************************************************//
					// *************************************新契约通知书 end
					// *************************************//

					// *************************************理赔通知书 start
					// ************************************//
					// ****************************************************************************************//
					strSQL = " select a.prtseq,"
							+ " a.otherno,"
							+ " (select codename"
							+ "   from ldcode"
							+ "  where code = a.code"
							+ "   and codetype = 'lluwnotice'),"
							+ " a.makedate,"
							+ " b.appntname,"
							+ "  b.agentcode,"
							+ "  (select name from laagent where agentcode = trim(b.agentcode)),"
							+ " (select name from labranchgroup where agentgroup = b.agentgroup)"
							+ " from loprtmanager a, lccont b" + " where 1=1 ";
					if (strStartIssueDate != null
							&& !strStartIssueDate.equals("")) {
						strSQL = strSQL + " and a.makedate >= '"
								+ "?strStartIssueDate?" + "'";
					}
					strSQL = strSQL + " and a.makedate <= '" + "?strEndIssueDate?"
							+ "'" + " and a.otherno = b.contno"
							+ " and a.stateflag='" + "?strPrtState?" + "'"
							+ " and a.managecom like concat('"
							+ "?managecom?" + "','%')"
							+ " and a.code in"
							+ " ('LP03', 'LP81', 'LP89' , 'LP88')";

					if (strSaleChnl != null && !strSaleChnl.equals("")) {
						strSQL = strSQL + " and b.salechnl='" + "?strSaleChnl?"
								+ "'";
					}
					SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
					sqlbv6.sql(strSQL);
					sqlbv6.put("strStartIssueDate", strStartIssueDate);
					sqlbv6.put("strEndIssueDate", strEndIssueDate);
					sqlbv6.put("strPrtState", strPrtState);
					sqlbv6.put("managecom", YingSSRS.GetText(k + 1, 1));
					sqlbv6.put("strSaleChnl", strSaleChnl);
					SSRS ssrs2 = exeSql.execSQL(sqlbv6);
					int i_count2 = ssrs2.getMaxRow();

					if (titleflag == false && i_count2 != 0) {
						titleflag = true;
						alistTable.add(col1);
						alistTable.add(col3);
					}
					// 将数据装入表单
					for (int l = 1; l <= i_count2; l++) {
						String[] cols = new String[9];
						try {
							cols[0] = i_count + i_count8 + l + "";
							cols[1] = ssrs2.GetText(l, 1);
							cols[2] = ssrs2.GetText(l, 2);
							cols[3] = ssrs2.GetText(l, 3);
							cols[4] = ssrs2.GetText(l, 4);
							cols[5] = ssrs2.GetText(l, 5);
							cols[6] = ssrs2.GetText(l, 6);
							cols[7] = ssrs2.GetText(l, 7);
							cols[8] = ssrs2.GetText(l, 8);
						} catch (Exception ex) {
							buildError("getprintData", "理赔通知书 抽取数据失败");
							logger.debug("理赔通知书 抽取数据失败");
							return false;
						}

						logger.debug("理赔通知书 抽取信息成功！！");
						alistTable.add(cols);
					}
					// *****************************************************************************************//
					// *************************************理赔通知书 end
					// **************************************//

					// *************************************保全通知书 start
					// ************************************//
					// ****************************************************************************************//
					strSQL = " select a.prtseq,"
							+ " a.otherno,"
							+ " (select codename"
							+ "   from ldcode"
							+ "  where code = a.code"
							+ "   and codetype = 'bquw'),"
							+ " a.makedate,"
							+ " b.appntname,"
							+ "  b.agentcode,"
							+ "  (select name from laagent where agentcode = trim(b.agentcode)),"
							+ " (select h.branchcode from laagent h where h.agentcode = trim(b.agentcode))"
							+ " from loprtmanager a, lccont b" + " where 1=1 ";
					if (strStartIssueDate != null
							&& !strStartIssueDate.equals("")) {
						strSQL = strSQL + " and a.makedate >= '"
								+ "?strStartIssueDate?" + "'";
					}
					strSQL = strSQL
							+ " and a.makedate <= '"
							+ "?strEndIssueDate?"
							+ "'"
							+ " and a.otherno = b.contno"
							+ " and a.stateflag='"
							+ "?strPrtState?"
							+ "'"
							+ " and a.managecom like concat('"
							+ "?managecom?"
							+ "','%')"
							+ " and a.code in"
							+ " ('BQ80','BQ81','BQ82','BQ84','BQ85','BQ86','BQ87','BQ88','BQ89','BQ90')";

					if (strSaleChnl != null && !strSaleChnl.equals("")) {
						strSQL = strSQL + " and b.salechnl='" + "?strSaleChnl?"
								+ "'";
					}
					SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
					sqlbv7.sql(strSQL);
					sqlbv7.put("strStartIssueDate", strStartIssueDate);
					sqlbv7.put("strEndIssueDate", strEndIssueDate);
					sqlbv7.put("strPrtState", strPrtState);
					sqlbv7.put("managecom", YingSSRS.GetText(k + 1, 1));
					sqlbv7.put("strSaleChnl", strSaleChnl);
					SSRS ssrs3 = exeSql.execSQL(sqlbv7);
					int i_count3 = ssrs3.getMaxRow();

					if (titleflag == false && i_count3 != 0) {
						titleflag = true;
						alistTable.add(col1);
						alistTable.add(col3);
					}
					// 将数据装入表单
					for (int l = 1; l <= i_count3; l++) {
						String[] cols = new String[9];
						try {
							cols[0] = i_count + i_count8 + l + "";
							cols[1] = ssrs3.GetText(l, 1);
							cols[2] = ssrs3.GetText(l, 2);
							cols[3] = ssrs3.GetText(l, 3);
							cols[4] = ssrs3.GetText(l, 4);
							cols[5] = ssrs3.GetText(l, 5);
							cols[6] = ssrs3.GetText(l, 6);
							cols[7] = ssrs3.GetText(l, 7);
							cols[8] = getUpComName(ssrs3.GetText(l, 8));
						} catch (Exception ex) {
							buildError("getprintData", "保全通知书 抽取数据失败");
							logger.debug("保全通知书 抽取数据失败");
							return false;
						}

						logger.debug("保全通知书 抽取信息成功！！");
						alistTable.add(cols);
					}
					// *****************************************************************************************//
					// *************************************保全通知书 end
					// **************************************//

					if (i_count != 0 || i_count8 != 0 || i_count3 != 0) {
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
					}
				}
			}
		}

		String[] col = new String[1];
		TextTag texttag = new TextTag(); // Create a TextTag instance
		XmlExport xmlexport = new XmlExport(); // Create a XmlExport instance
		xmlexport.createDocument("WaitPrtList.vts", "printer"); // appoint to a
		// special
		// output .vts
		// file

		xmlexport.addListTable(alistTable, col);
		String type = "";
		String title = "";
		if (strSaleChnl.equals("1")) {
			type = "个人";
		} else if (strSaleChnl.equals("3")) {
			type = "银行代理";
		} else {
			type = "";
		}
		if (strPrtState.equals("0")) {
			title = "待打印通知书清单";
		}
		if (strPrtState.equals("1")) {
			title = "已打印未回收通知书清单";
		}
		texttag.add("TITLE", title);
		texttag.add("TYPE", type);
		texttag.add("IssueDate", strStartIssueDate);
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
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql("Select agentgroup From labranchgroup Where upbranch='"
				+ "?upAgentgroup?" + "'");
		sqlbv8.put("upAgentgroup", upAgentgroup);
		searchOneSsrs = searchOneSQL.execSQL(sqlbv8);
		search_count = searchOneSsrs.getMaxRow();

		for (int search = 1; search < search_count + 1; search++) {
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql("Select agentgroup From labranchgroup Where upbranch='"
					+ "?upAgentgroup?" + "'");
			sqlbv9.put("upAgentgroup", upAgentgroup);
			searchOneSsrs = searchOneSQL.execSQL(sqlbv9);
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
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql("Select c.agentgroup From labranchgroup b,labranchgroup c Where c.branchattr Like concat(trim(b.branchattr) , '%') And c.managecom='"
				+ "?str2?"
				+ "' And b.branchlevel = '03' And b.agentgroup in(Select agentgroup From labranchgroup Where trim(branchattr)='"
				+ "?str1?"
				+ "'And managecom='"
				+ "?str2?"
				+ "') Order By agentgroup");
		sqlbv10.put("str2", str2);
		sqlbv10.put("str1", str1);
		searchOneSsrs = searchOneSQL.execSQL(sqlbv10);
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

	public static void main(String[] args) {
		WaitPrtListUI waitprtlistbl = new WaitPrtListUI();
		VData tVData = new VData();
		tVData.add("8621");
		tVData.add("2007-05-01");
		tVData.add("0");
		tVData.add("1");
		tVData.add("2007-06-26");
		tVData.add("0");

		waitprtlistbl.submitData(tVData, "PRINT");

	}
}
