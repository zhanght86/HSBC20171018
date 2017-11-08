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
 * function :print InputEfficiency or print CheckEfficiency Business Logic layer
 * @version 1.0
 * @date 2003-04-04
 */

public class DZKCListBL {
private static Logger logger = Logger.getLogger(DZKCListBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strMngCom;

	private String strStartDate;
	private String strEndDate;

	// private String strEndDate;

	public DZKCListBL() {
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
		strStartDate = (String) cInputData.get(1);
		strEndDate = (String) cInputData.get(2);
		logger.debug("strStartDate:" + strStartDate);
		logger.debug("strEndDate:" + strEndDate);
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
		cError.moduleName = "DZKCListBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/***************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData() {
		ListTable alistTable = new ListTable();
		XmlExport xmlexport = new XmlExport(); // Create a XmlExport instance
		xmlexport.createDocument("DZKCList.vts", "printer");
		alistTable.setName("List");
		String t_sql = "";
		logger.debug("开始执行sql操作");
		String ManageCom = "";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		t_sql = "select a.name from ldcom a where comcode ='" + "?strMngCom?" + "'";
		sqlbv.sql(t_sql);
		sqlbv.put("strMngCom", strMngCom);
		ExeSQL tempExeSQL = new ExeSQL();
		SSRS TwoSSRS = new SSRS();
		// TwoSSRS = tempExeSQL.execSQL(sqlbv);
		// ManageCom = TwoSSRS.GetText(1, 1);

		// t_sql = "select a.proposalcontno,a.appntname from lccont a,ljtempfee
		// b where a.proposalcontno=b.otherno and a.managecom='"+strMngCom+"'
		// and b.paydate='"+strFeeDate+"' ";
		// String strSQL="select name,upcomcode from ldcom where
		// comcode='"+strMngCom+"'";
		// ExeSQL exeSQL = new ExeSQL();
		// SSRS ComCode_ssrs = exeSQL.execSQL(strSQL);

		// String UpComCode;
		// String Name1="";
		// String name=ComCode_ssrs.GetText(1, 1);
		// UpComCode = ComCode_ssrs.GetText(1, 2);
		// logger.debug("UpComCode"+UpComCode);
		// String sql="select name from ldcom where comcode='"+UpComCode+"'";
		// ExeSQL exeSQL2 = new ExeSQL();
		// SSRS ComCode2_ssrs = exeSQL2.execSQL(sql);
		// int count1 = ComCode2_ssrs.getMaxRow();
		// if (count1 == 0)
		// {
		// UpComCode = "";
		// }
		// else
		// {
		// Name1 = ComCode2_ssrs.GetText(1, 1);
		// }
		logger.debug("开始执行sql操作");
		// t_sql = "select a.proposalcontno,a.appntname from lccont a,ljtempfee
		// b where a.proposalcontno=b.otherno and a.managecom='"+strMngCom+"'
		// and b.paydate='"+strFeeDate+"' ";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String strSQL = " select distinct (select name from ldcom where comcode = '"
				+ "?strMngCom?"
				+ "'),"
				+ " (select certifyname"
				+ " from lmcertifydes"
				+ " where certifycode = a.certifycode) a2,"
				+ " a.startno,"
				+ " a.endno,"
				+ " a.sumcount,"
				+ " a.makedate,"
				+ " (select username from lduser where usercode = a.operator)"
				+ " from lzcard a"
				+ " where a.sendoutcom = '00'"
				+ " and substr(trim(a.receivecom),2,char_length(trim(a.receivecom))) = '"
				+ "?strMngCom?"
				+ "'"
				+ " and a.makedate between to_date('"+ "?strStartDate?" + "','yyyy-mm-dd') "
				+ " and to_date('"+ "?strEndDate?"+ "','yyyy-mm-dd') "
				+ "order by a2";
		sqlbv1.sql(strSQL);
		sqlbv1.put("strMngCom", strMngCom);
		sqlbv1.put("strStartDate", strStartDate);
		sqlbv1.put("strEndDate", strEndDate);
		ExeSQL exeSQL = new ExeSQL();
		SSRS ssrs = exeSQL.execSQL(sqlbv1);

		// String UpComCode;
		// String Name1="";
		// String name=ComCode_ssrs.GetText(1, 1);
		// UpComCode = ComCode_ssrs.GetText(1, 2);
		// logger.debug("UpComCode"+UpComCode);
		// String sql="select name from ldcom where comcode='"+UpComCode+"'";
		// ExeSQL exeSQL2 = new ExeSQL();
		// SSRS ComCode2_ssrs = exeSQL2.execSQL(sql);
		int count1 = ssrs.getMaxRow();
		if (count1 == 0) {
			CError tError = new CError();
			tError.moduleName = "DZRKListBL";
			tError.functionName = "getDutyGetClmInfo";
			tError.errorMessage = "在该段时间内没有数据！！！";
			this.mErrors.addOneError(tError);
			return false;

		} else {
			for (int i = 1; i <= count1; i++) {
				String[] col = new String[8];
				col[0] = String.valueOf(i);
				col[1] = ssrs.GetText(i, 1);
				col[2] = ssrs.GetText(i, 2);
				col[3] = ssrs.GetText(i, 3);
				col[4] = ssrs.GetText(i, 4);
				col[5] = ssrs.GetText(i, 5);
				col[6] = ssrs.GetText(i, 6);
				col[7] = ssrs.GetText(i, 7);
				alistTable.add(col);
				logger.debug("col[1]" + col[1]);
				logger.debug("col[2]" + col[2]);
				logger.debug("col[3]" + col[3]);
				logger.debug("col[4]" + col[4]);
				logger.debug("col[5]" + col[5]);
				logger.debug("col[6]" + col[6]);
			}
		}

		// }
		String[] cols = new String[8];

		xmlexport.addListTable(alistTable, cols);

		TextTag texttag = new TextTag();

		// logger.debug("您的t_sql的执行结果是"+t_sql);

		// xmlexport.addListTable(alistTable, col);
		texttag.add("Date1", strStartDate);
		texttag.add("Date2", strEndDate);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		// xmlexport.outputDocumentToFile("e:\\", "DZRCList"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;

	}

}
