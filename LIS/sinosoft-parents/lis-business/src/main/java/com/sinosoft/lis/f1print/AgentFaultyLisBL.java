package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
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

public class AgentFaultyLisBL {
private static Logger logger = Logger.getLogger(AgentFaultyLisBL.class);
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/*
	 * @define globe variable
	 */
	private String strMngCom;

	private String comName;

	private String strStartIssueDate;

	private String strEndIssueDate;

	private String strSaleChnl;

	private String strSQL;

	private TransferData mTransferData = new TransferData();

	public AgentFaultyLisBL() {
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
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		strMngCom = (String) mTransferData.getValueByName("ManageCom");
		strStartIssueDate = (String) mTransferData
				.getValueByName("StartIssueDate");
		strEndIssueDate = (String) mTransferData.getValueByName("EndIssueDate");
		strSaleChnl = (String) mTransferData.getValueByName("SaleChnl");
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
		cError.moduleName = "AgentFaultyLisBL";
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
		// SSRS testSSRS = new SSRS();
		// testSSRS = exeSql.execSQL(
		// "select sysvarvalue From ldsysvar where sysvar='DataPrintCombine'");
		// String strTemplatePath = testSSRS.GetText(1, 1);
		// //数据库中的路径-模板路径D:/lis/ui/f1print/NCLtemplate/
		// String strTemplatePath = "D:/vsssino/ui/f1print/NCLtemplate/";

		ExeSQL exeSql = new ExeSQL();
		SSRS tSSRS = new SSRS();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String tFenSQL = "";
		String tZhongSQL = "";
		String tFenName = "";
		String tZhongName = "";

		if (strMngCom.equals("86")) {
			tFenName = "总公司";
		} else {
			if (strMngCom.length() == 4) {
				tFenSQL = " select name from ldcom where comcode = '"
						+ "?strMngCom?"+ "'";
				sqlbv.sql(tFenSQL);
				sqlbv.put("strMngCom", strMngCom);
				tSSRS = exeSql.execSQL(sqlbv);
				tFenName = tSSRS.GetText(1, 1);
				tZhongName = "";
			} else {
				tFenSQL = " select comcode,name from ldcom where comcode = '"
						+ "?comcode?" + "'";
				sqlbv.sql(tFenSQL);
				sqlbv.put("comcode", strMngCom.substring(0, 4));
				tSSRS = exeSql.execSQL(sqlbv);
				tFenName = tSSRS.GetText(1, 1);
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				tZhongSQL = " select comcode,name from ldcom where comcode = '"
						+ "?comcode?" + "'";
				sqlbv1.sql(tZhongSQL);
				sqlbv1.put("comcode", strMngCom.substring(0, 6));
//				tSSRS = exeSql.execSQL(tFenSQL);
				tSSRS = exeSql.execSQL(sqlbv1);
				tZhongName = tSSRS.GetText(1, 1);
			}
		}
		TextTag texttag = new TextTag(); // Create a TextTag instance
		XmlExport xmlexport = new XmlExport(); // Create a XmlExport instance
		ExeSQL exeSQL = new ExeSQL();
		SSRS ssrs = new SSRS();
		ListTable alistTable = new ListTable(); // Create a ListTable instance
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		if (strSaleChnl != null && strSaleChnl.equals("1")) {
			strSQL = " select a.proposalcontno,"
					+ " b.issuetype,"
					+ " b.issuecont,"
					+ " a.agentcode,"
					+ " (select name"
					+ " from laagent"
					+ " where laagent.agentcode = trim(a.agentcode)),"
					+ " (select name from ldcom where comcode=a.managecom),"
					+ " (select BranchCode from laagent where laagent.agentcode=trim(a.agentcode)),"
					+ " (select (case b.backobjtype when '1' then '内部' when '2' then '外部' end) from dual)"
					+ " from lccont a, lcissuepol b"
					+ " where a.proposalcontno = b.proposalcontno"
					+ " and a.managecom like concat('" + "?strMngCom?" + "','%')";
			if (strStartIssueDate != null && !strStartIssueDate.equals("")) {
				strSQL = strSQL + " and b.makedate >= '" + "?strStartIssueDate?"
						+ "'";
			}
			if (strEndIssueDate != null && !strEndIssueDate.equals("")) {
				strSQL = strSQL + " and b.makedate <= '" + "?strEndIssueDate?"
						+ "'";
			}
			strSQL = strSQL + " and a.salechnl = '1'";
			sqlbv2.sql(strSQL);
			sqlbv2.put("strMngCom", strMngCom);
			sqlbv2.put("strStartIssueDate", strStartIssueDate);
			sqlbv2.put("strEndIssueDate", strEndIssueDate);

			ssrs = exeSql.execSQL(sqlbv2);

			int i_count = ssrs.getMaxRow();
			if (i_count == 0) {
				logger.debug("没有可打印的信息");
				buildError("getprintData", "没有需要打印的信息");
				return false;
			}
			xmlexport.createDocument("AgentFaultyLis2.vts", "printer"); // appoint
			// to a
			// special
			// output
			// .vts
			// file
			alistTable.setName("RISK"); // Appoint to a sepcial flag

			// 将数据装入表单
			for (int i = 1; i <= i_count; i++) {
				String[] cols = new String[8];

				cols[0] = ssrs.GetText(i, 1);
				cols[1] = ssrs.GetText(i, 2);
				cols[2] = ssrs.GetText(i, 3);
				cols[3] = ssrs.GetText(i, 4);
				cols[4] = ssrs.GetText(i, 5);
				cols[5] = ssrs.GetText(i, 6);
				cols[6] = getUpComName(ssrs.GetText(i, 7));
				cols[7] = ssrs.GetText(i, 8);

				logger.debug("2005-08-09描述表中取出信息！！！！");
				alistTable.add(cols);
			}
			String[] col = new String[1];

			xmlexport.addListTable(alistTable, col);
			texttag.add("MngCom", tFenName);
			texttag.add("ZhongCom", tZhongName);
			texttag.add("TotalQ", i_count);
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
		} else if (strSaleChnl != null && strSaleChnl.equals("3")) {
			strSQL = " select a.proposalcontno,"
					+ " (select lacom.name from lacom where lacom.agentcom = a.agentcom),"
					+ " (select name"
					+ " from laagent"
					+ " where laagent.agentcode = trim(a.agentcode)),"
					+ " a.appntname,"
					+ " a.insuredname,"
					+ " (case when (select d.companyphone from lcaddress d, lcappnt b where b.contno = a.contno and d.customerno = b.appntno and d.addressno = b.addressno) is not null then (select d.companyphone from lcaddress d, lcappnt b where b.contno = a.contno and d.customerno = b.appntno and d.addressno = b.addressno)  else '无' end),"
					+ " b.issuetype,"
					+ " b.issuecont,"
					+ " b.prtseq,"
					+ " (select BranchCode from laagent where laagent.agentcode=trim(a.agentcode)),"
					+ " (select (case b.backobjtype when '1' then '内部' when '2' then '外部' end) from dual)"
					+ " from lccont a, lcissuepol b"
					+ " where a.proposalcontno = b.proposalcontno"
					+ " and a.managecom like concat('" + "?strMngCom?" + "','%')";
			if (strStartIssueDate != null && !strStartIssueDate.equals("")) {
				strSQL = strSQL + " and b.makedate >= '" + "?strStartIssueDate?"
						+ "'";
			}
			if (strEndIssueDate != null && !strEndIssueDate.equals("")) {
				strSQL = strSQL + " and b.makedate <= '" + "?strEndIssueDate?"
						+ "'";
			}
			strSQL = strSQL + " and a.salechnl = '3'";
			strSQL = strSQL + " order by a.managecom,a.agentgroup,a.agentcode";
			sqlbv2.sql(strSQL);
			sqlbv2.put("strMngCom", strMngCom);
			sqlbv2.put("strStartIssueDate", strStartIssueDate);
			sqlbv2.put("strEndIssueDate", strEndIssueDate);
			ssrs = exeSql.execSQL(sqlbv2);
			int i_count = ssrs.getMaxRow();
			if (i_count == 0) {
				logger.debug("没有可打印的信息");
				buildError("getprintData", "没有需要打印的信息");
				return false;
			}
			xmlexport.createDocument("AgentFaultyLis.vts", "printer"); // appoint
			// to a
			// special
			// output
			// .vts
			// file
			alistTable.setName("RISK"); // Appoint to a sepcial flag

			// 将数据装入表单
			for (int i = 1; i <= i_count; i++) {
				String[] cols = new String[13];

				cols[0] = ssrs.GetText(i, 1);
				cols[1] = ssrs.GetText(i, 9);
				cols[2] = ssrs.GetText(i, 2);
				cols[3] = ssrs.GetText(i, 3);
				cols[4] = getUpComName(ssrs.GetText(i, 10));
				cols[5] = ssrs.GetText(i, 4);
				cols[6] = ssrs.GetText(i, 5);
				cols[7] = ssrs.GetText(i, 6);
				cols[8] = ssrs.GetText(i, 7);
				cols[9] = ssrs.GetText(i, 8);
				cols[10] = ssrs.GetText(i, 11);
				;
				cols[11] = "";
				cols[12] = "";
				logger.debug("描述表中取出信息！！！！");
				alistTable.add(cols);
			}
			String[] col = new String[1];

			xmlexport.addListTable(alistTable, col);
			texttag.add("IssueDate", strStartIssueDate);
			texttag.add("MngCom", tFenName);
			texttag.add("ZhongCom", tZhongName);
			texttag.add("TotalQ", i_count);
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
		}

		else {
			logger.debug("没有可打印的信息");
			buildError("getprintData", "没有需要打印的信息");
			return false;
		}

		// xmlexport.outputDocumentToFile("D:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;

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
		String tStrMngCom = "86110000";
		String tIssueDate = "2005-08-09";

		VData tVData = new VData();
		tVData.addElement(tStrMngCom);

		tVData.addElement(tIssueDate);

		AgentFaultyLisUI tAgentFaultyLisUI = new AgentFaultyLisUI();
		tAgentFaultyLisUI.submitData(tVData, "PRINT");
	}
}
