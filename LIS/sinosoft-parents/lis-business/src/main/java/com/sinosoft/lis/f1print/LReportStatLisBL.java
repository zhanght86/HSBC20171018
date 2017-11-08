package com.sinosoft.lis.f1print;
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
 * modify by zhangxing
 * function :print InputEfficiency or print CheckEfficiency Business Logic layer
 * @version 1.0
 * @date 2003-04-04
 */

public class LReportStatLisBL {
private static Logger logger = Logger.getLogger(LReportStatLisBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strMngCom;
	private String comName;
	private String strStartDate;
	private String strSQL;
	private String strEndDate;
	private String strCode;
	private String strComgrade;

	public LReportStatLisBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("opertate=" + cOperate);
		if (!cOperate.equals("PRINT") && !cOperate.equals("PRINT2")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();

		if (cOperate.equals("PRINT"))
			strCode = "04";
		if (cOperate.equals("PRINT2"))
			strCode = "G04";
		logger.debug("strcode");
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
		strStartDate = (String) cInputData.get(1);
		strEndDate = (String) cInputData.get(2);
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
		cError.moduleName = "LReportStatLisBL";
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

		ExeSQL comdSQL = new ExeSQL();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql("Select Name From ldcom Where comcode='"
				+ "?strMngCom?" + "'");
		sqlbv1.put("strMngCom", strMngCom);
		SSRS comSsrs = comdSQL.execSQL(sqlbv1);
		try {
			comName = comSsrs.GetText(1, 1);

		} catch (Exception ex) {
			buildError("getprintData", "机构编码有误");
			logger.debug("机构编码有误");
			return false;
		}

		// strSQL =
		// " select ManageCom,proposalcontno,name,ReplyDate,rreportfee from
		// lcrreport where 1 = 1 "
		// + " and managecom like '" + strMngCom + "%' "
		// + " and replydate between '" + strStartDate + "' and '" + strEndDate
		// +
		// "' order by ManageCom ";
		if (strMngCom != null && !strMngCom.equals("86")) {
			strSQL = "select (select ldcom.name"
					+ " from ldcom"
					+ " where trim(ldcom.comcode) = substr(b.managecom, 1, 4)),"
					+ " (select ldcom.name"
					+ " from ldcom"
					+ " where trim(ldcom.comcode) = substr(b.managecom, 1, 6)),"
					+ " a.proposalcontno," + " a.name," + " a.ReplyDate,"
					+ " a.rreportfee"
					+ " from lcrreport a, lccont b,loprtmanager c"
					+ " where 1 = 1" + " and b.managecom like concat('" + "?strMngCom?"
					+ "','%')" + "  and c.code='" + "?strCode?" + "'"
					+ " and replydate between to_date('" + "?strStartDate?"	+ "','yyyy-mm-dd')  and to_date('" + "?strEndDate?"	+ "','yyyy-mm-dd')"
					+ " and a.contno = b.proposalcontno "
					+ " and a.prtseq=c.prtseq " + " order by b.ManageCom";
		} else {
			strSQL = "select (select ldcom.name"
					+ " from ldcom"
					+ " where trim(ldcom.comcode) = substr(b.managecom, 1, 4)),"
					+ " ''," + " a.proposalcontno," + " a.name,"
					+ " a.ReplyDate," + " a.rreportfee"
					+ " from lcrreport a, lccont b,loprtmanager c"
					+ " where 1 = 1" + " and b.managecom like concat('" + "?strMngCom?"
					+ "','%')" + "  and c.code='" + "?strCode?" + "'"
					+ " and replydate between to_date('" + "?strStartDate?"	+ "','yyyy-mm-dd')  and to_date('" + "?strEndDate?"	+ "','yyyy-mm-dd')"
					+ " and a.contno = b.proposalcontno "
					+ " and a.prtseq=c.prtseq " + " order by b.ManageCom";
		}
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(strSQL);
		sqlbv2.put("strMngCom", strMngCom);
		sqlbv2.put("strCode", strCode);
		sqlbv2.put("strStartDate", strStartDate);
		sqlbv2.put("strEndDate", strEndDate);
		SSRS ssrs = exeSql.execSQL(sqlbv2);
		int i_count = ssrs.getMaxRow();
		// if (i_count == 0) {
		// logger.debug("没有可打印的信息");
		// buildError("getprintData", "没有需要打印的信息");
		// return false;
		// }
		TextTag texttag = new TextTag(); // Create a TextTag instance
		XmlExport xmlexport = new XmlExport(); // Create a XmlExport instance
		xmlexport.createDocument("LReportStatLis.vts", "printer"); // appoint
																	// to a
																	// special
																	// output
																	// .vts file
		ListTable alistTable = new ListTable(); // Create a ListTable instance
		alistTable.setName("RISK"); // Appoint to a sepcial flag

		double sum = 0;

		// 将数据装入表单
		for (int i = 1; i <= i_count; i++) {
			String[] cols = new String[6];

			cols[0] = ssrs.GetText(i, 1);
			cols[1] = ssrs.GetText(i, 2);
			cols[2] = ssrs.GetText(i, 3);
			cols[3] = ssrs.GetText(i, 4);
			cols[4] = ssrs.GetText(i, 5);
			cols[5] = ssrs.GetText(i, 6);

			if (cols[5].equals("null")) {
				cols[5] = "0";
			}
			logger.debug("2005-08-09描述表中取出信息！！！！");
			sum = sum + Double.parseDouble(cols[5]);
			alistTable.add(cols);
		}
		String[] col2 = new String[6];

		col2[0] = "汇总：";
		col2[1] = "";
		col2[2] = "";
		col2[3] = "";
		col2[4] = "";
		col2[5] = "" + sum;
		alistTable.add(col2);

		String[] col = new String[1];

		xmlexport.addListTable(alistTable, col);
		texttag.add("StartDate", strStartDate);
		texttag.add("EndDate", strEndDate);
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		// xmlexport.outputDocumentToFile("D:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;

	}

	private String getComName(String strComCode) {
		String mSql = "select Name from Ldcom where ComCode = '" + "?strComCode?"
				+ "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(mSql);
		sqlbv3.put("strComCode", strComCode);
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(sqlbv3);

	}

}
