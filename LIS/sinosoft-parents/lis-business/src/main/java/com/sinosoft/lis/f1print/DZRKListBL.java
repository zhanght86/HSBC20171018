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

public class DZRKListBL {
private static Logger logger = Logger.getLogger(DZRKListBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strMngCom;

	private String strStartDate;
	private String strEndDate;

	// private String strEndDate;

	public DZRKListBL() {
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
		cError.moduleName = "DZRKListBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/***************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData() {
		XmlExport xmlexport = new XmlExport(); // Create a XmlExport instance
		xmlexport.createDocument("DZRKList.vts", "printer");
		ListTable alistTable = new ListTable();
		alistTable.setName("List");
		String t_sql = "";
		logger.debug("开始执行sql操作");
		// t_sql = "select a.proposalcontno,a.appntname from lccont a,ljtempfee
		// b where a.proposalcontno=b.otherno and a.managecom='"+strMngCom+"'
		// and b.paydate='"+strFeeDate+"' ";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String strSQL = "select distinct a.name,b.certifyname,c.startno,c.endno,c.sumcount,c.makedate,(select username from lduser where trim(usercode)=trim(c.operator)) from ldcom a,lmcertifydes b,lzcardtrack c "
				+ "where trim(a.comcode)='"
				+ "?strMngCom?"
				+ "' and trim(b.certifycode)=trim(c.certifycode) and substr(trim(c.receivecom),2,char_length(trim(c.receivecom)))='"
				+ "?strMngCom?"
				+ "' and c.makedate between '"
				+ "?strStartDate?"
				+ "' and '" + "?strEndDate?" + "'";
		sqlbv.sql(strSQL);
		sqlbv.put("strMngCom", strMngCom);
		sqlbv.put("strStartDate", strStartDate);
		sqlbv.put("strEndDate", strEndDate);
		ExeSQL exeSQL = new ExeSQL();
		SSRS ssrs = exeSQL.execSQL(sqlbv);

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
		// xmlexport.outputDocumentToFile("e:\\","DZRKList");//输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

}
