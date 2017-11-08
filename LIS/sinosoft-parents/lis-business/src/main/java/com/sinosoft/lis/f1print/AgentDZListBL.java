package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
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
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author 刘岩松
 * function :print InputEfficiency or print CheckEfficiency Business Logic layer
 * @version 1.0
 * @date 2003-04-04
 */

public class AgentDZListBL implements PrintService {
private static Logger logger = Logger.getLogger(AgentDZListBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strMngCom;

	private String strStartDate;
	private String strCardType;
	private String strEndDate;
	// private String strEndDate;
	private String strCardState;
	private TransferData mTransferData = new TransferData();
	private GlobalInput mGlobalInput = new GlobalInput();

	public AgentDZListBL() {
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
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);

		strMngCom = (String) mTransferData.getValueByName("ManageCom");
		strStartDate = (String) mTransferData.getValueByName("StartDate");
		strEndDate = (String) mTransferData.getValueByName("EndDate");
		strCardState = (String) mTransferData.getValueByName("CardState");
		strCardType = (String) mTransferData.getValueByName("CardType");

		logger.debug("strMngCom:" + strMngCom);
		logger.debug("strStartDate:" + strStartDate);
		logger.debug("strEndDate:" + strEndDate);

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public CErrors getErrors() {
		return mErrors; // To change body of implemented methods use File |
						// Settings | File Templates.
	}

	/***************************************************************************
	 * name : buildError function : Prompt error message return :error message
	 */

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "AgentDZListBL";
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
		xmlexport.createDocument("AgentDZList.vts", "printer");
		ListTable alistTable = new ListTable();
		alistTable.setName("List");
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String sql = "select name from ldcom where comcode='" + "?strMngCom?" + "'";
		sqlbv.sql(sql);
		sqlbv.put("strMngCom", strMngCom);
		ExeSQL exeSQL1 = new ExeSQL();
		SSRS ssrs1 = exeSQL1.execSQL(sqlbv);
		String name = ssrs1.GetText(1, 1);
		String ManageComName = "";
		String BranchGroupName = "";
		String AgentGroupName = "";

		if (strMngCom.length() == 2) {
			ManageComName = name;
		}
		if (strMngCom.length() == 4) {
			ManageComName = name;
		}
		if (strMngCom.length() == 6) {
			BranchGroupName = name;
			sql = "select name from ldcom where comcode='"
					+ "?comcode?" + "'";
			sqlbv.sql(sql);
			sqlbv.put("comcode", strMngCom.substring(0, 4));
			ssrs1 = exeSQL1.execSQL(sqlbv);
			ManageComName = ssrs1.GetText(1, 1);
		}
		if (strMngCom.length() == 8) {
			AgentGroupName = name;
			sql = "select name from ldcom where comcode='"
					+ "?comcode?" + "'";
			sqlbv.sql(sql);
			sqlbv.put("comcode", strMngCom.substring(0, 4));
			ssrs1 = exeSQL1.execSQL(sqlbv);
			ManageComName = ssrs1.GetText(1, 1);
			sql = "select name from ldcom where comcode='"
					+ "?comcode?" + "'";
			sqlbv.sql(sql);
			sqlbv.put("comcode", strMngCom.substring(0, 6));
			ssrs1 = exeSQL1.execSQL(sqlbv);
			BranchGroupName = ssrs1.GetText(1, 1);
		}

		String t_sql = "";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		logger.debug("开始执行sql操作");
		// logger.debug(strMngCom);
		logger.debug("strCardType:" + strCardType);
		// 未选状态
		if (strCardState != null && strCardState.equals("")) {
			t_sql = " select distinct b.certifyname,"
					+ " a.name,"
					+ " d.name,"
					+ " d.agentcode,"
					+ " c.startno,"
					+ " c.endno,"
					+ " c.sumcount,"
					+ " c.handledate,"
					+ " (select username from lduser where usercode = c.operator),"
					+ " '未用'"
					+ " from ldcom a, lmcertifydes b, lzcard c, laagent d"
					+ " where trim(a.comcode) = '"
					+ "?strMngCom?"
					+ "'"
					+ " and trim(b.certifycode) = trim(c.certifycode)"
					+ " and substr(trim(c.sendoutcom), 2, char_length(trim(c.sendoutcom))) like "
					+ " concat('"+"?strMngCom?"+"','%')"
					+ " and c.makedate between to_date('"+ "?strStartDate?" + "','yyyy-mm-dd') and to_date('" + "?strEndDate?" + "','yyyy-mm-dd')"
					+ " and d.agentcode ="
					+ " substr(trim(c.receivecom), 2, char_length(trim(c.receivecom)) - 1)"
					+ " and (c.receivecom like 'D%' or c.receivecom like 'E%')";
			if (strCardType != null && !(strCardType.equals(""))) {
				t_sql = t_sql + " and  b.certifycode = '" + "?strCardType?" + "'";
			}
			t_sql = t_sql + " order by c.handledate,c.startno";
		}

		// 查询发放状态
		if (strCardState != null && strCardState.equals("0")) {
			t_sql = " select distinct b.certifyname,"
					+ " a.name,"
					+ " d.name,"
					+ " d.agentcode,"
					+ " c.startno,"
					+ " c.endno,"
					+ " c.sumcount,"
					+ " c.handledate,"
					+ " (select username from lduser where usercode = c.operator),"
					+ " '未用'"
					+ " from ldcom a, lmcertifydes b, lzcard c, laagent d"
					+ " where trim(a.comcode) = '"
					+ "?strMngCom?"
					+ "'"
					+ " and trim(b.certifycode) = trim(c.certifycode)"
					+ " and substr(trim(c.sendoutcom), 2, char_length(trim(c.sendoutcom))) like "
					+ " concat('"+"?strMngCom?"+"','%')"
					+ " and c.makedate between to_date('"+ "?strStartDate?" + "','yyyy-mm-dd') and to_date('" + "?strEndDate?" + "','yyyy-mm-dd')"
					+ " and d.agentcode ="
					+ " substr(trim(c.receivecom), 2, char_length(trim(c.receivecom)) - 1)"
					+ " and (c.receivecom like 'D%' or c.receivecom like 'E%')"
					+ " and c.stateflag='0'";
			if (strCardType != null && !(strCardType.equals(""))) {
				t_sql = t_sql + " and  b.certifycode = '" + "?strCardType?" + "'";
			}
			t_sql = t_sql + " order by c.handledate,c.startno";
		}
		// 查询回收状态
		if (strCardState != null && strCardState.equals("1")) {
			t_sql = " select distinct b.certifyname,"
					+ " a.name,"
					+ " d.name,"
					+ " d.agentcode,"
					+ " c.startno,"
					+ " c.endno,"
					+ " c.sumcount,"
					+ " c.handledate,"
					+ " (select username from lduser where usercode = c.operator),"
					+ " '回收',"
					+ " c.makedate,"
					+ " c.maketime"
					+ " from ldcom a, lmcertifydes b, lzcard c, laagent d"
					+ " where trim(a.comcode) = '"
					+ "?strMngCom?"
					+ "'"
					+ " and trim(b.certifycode) = trim(c.certifycode)"
					+ " and substr(trim(c.receivecom), 2, char_length(trim(c.receivecom))) like "
					+ " concat('"+"?strMngCom?"+"','%')"
					+ " and c.makedate between to_date('"+ "?strStartDate?" + "','yyyy-mm-dd') and to_date('" + "?strEndDate?" + "','yyyy-mm-dd')"
					+ " and d.agentcode ="
					+ " substr(trim(c.sendoutcom), 2, char_length(trim(c.sendoutcom)) - 1)"
					+ " and (c.receivecom like 'A%' or c.receivecom like 'E%')"
					+ " and c.stateflag='1'";
			if (strCardType != null && !(strCardType.equals(""))) {
				t_sql = t_sql + " and  b.certifycode = '" + "?strCardType?" + "'";
			}
			t_sql = t_sql + " order by c.makedate,c.maketime";
		}
		// 查询作废状态
		if (strCardState != null && strCardState.equals("2")) {
			t_sql = " select distinct b.certifyname,"
					+ " a.name,"
					+ " d.name,"
					+ " d.agentcode,"
					+ " c.startno,"
					+ " c.endno,"
					+ " c.sumcount,"
					+ " c.handledate,"
					+ " (select username from lduser where usercode = c.operator),"
					+ " '作废',"
					+ " c.makedate,"
					+ " c.maketime"
					+ " from ldcom a, lmcertifydes b, lzcard c, laagent d"
					+ " where trim(a.comcode) = '"
					+ "?strMngCom?"
					+ "'"
					+ " and trim(b.certifycode) = trim(c.certifycode)"
					+ " and substr(trim(c.receivecom), 2, char_length(trim(c.receivecom))) like "
					+ " concat('"+"?strMngCom?"+"','%')"
					+ " and c.makedate between to_date('"+ "?strStartDate?" + "','yyyy-mm-dd') and to_date('" + "?strEndDate?" + "','yyyy-mm-dd')"
					+ " and d.agentcode ="
					+ " substr(trim(c.sendoutcom), 2, char_length(trim(c.sendoutcom)) - 1)"
					+ " and (c.receivecom like 'A%' or c.receivecom like 'E%')"
					+ " and c.stateflag='2'";
			if (strCardType != null && !(strCardType.equals(""))) {
				t_sql = t_sql + " and  b.certifycode = '" + "?strCardType?" + "'";
			}
			t_sql = t_sql + " order by c.makedate,c.maketime";
		}
		// 查询挂失态
		if (strCardState != null && strCardState.equals("3")) {
			t_sql = " select distinct b.certifyname,"
					+ " a.name,"
					+ " d.name,"
					+ " d.agentcode,"
					+ " c.startno,"
					+ " c.endno,"
					+ " c.sumcount,"
					+ " c.handledate,"
					+ " (select username from lduser where usercode = c.operator),"
					+ " '挂失',"
					+ " c.makedate,"
					+ " c.maketime"
					+ " from ldcom a, lmcertifydes b, lzcard c, laagent d"
					+ " where trim(a.comcode) = '"
					+ "?strMngCom?"
					+ "'"
					+ " and trim(b.certifycode) = trim(c.certifycode)"
					+ " and substr(trim(c.receivecom), 2, char_length(trim(c.receivecom))) like "
					+ " concat('"+"?strMngCom?"+"','%')"
					+ " and c.makedate between to_date('"+ "?strStartDate?" + "','yyyy-mm-dd') and to_date('" + "?strEndDate?" + "','yyyy-mm-dd')"
					+ " and d.agentcode ="
					+ " substr(trim(c.sendoutcom), 2, char_length(trim(c.sendoutcom)) - 1)"
					+ " and (c.receivecom like 'A%' or c.receivecom like 'E%')"
					+ " and c.stateflag='3'";
			if (strCardType != null && !(strCardType.equals(""))) {
				t_sql = t_sql + " and  b.certifycode = '" + "?strCardType?" + "'";
			}
			t_sql = t_sql + " order by c.makedate,c.maketime";
		}
		// 查询核销状态
		if (strCardState != null && strCardState.equals("4")) {
			t_sql = " select distinct b.certifyname,"
					+ " a.name,"
					+ " d.name,"
					+ " d.agentcode,"
					+ " c.startno,"
					+ " c.endno,"
					+ " c.sumcount,"
					+ " c.handledate,"
					+ " (select username from lduser where usercode = c.operator),"
					+ " '核销',"
					+ " c.makedate,"
					+ " c.maketime"
					+ " from ldcom a, lmcertifydes b, lzcard c, laagent d"
					+ " where trim(a.comcode) = '"
					+ "?strMngCom?"
					+ "'"
					+ " and trim(b.certifycode) = trim(c.certifycode)"
					+ " and substr(trim(c.receivecom), 2, char_length(trim(c.receivecom))) like "
					+ " concat('"+"?strMngCom?"+"','%')"
					+ " and c.makedate between to_date('"+ "?strStartDate?" + "','yyyy-mm-dd') and to_date('" + "?strEndDate?" + "','yyyy-mm-dd')"
					+ " and d.agentcode ="
					+ " substr(trim(c.sendoutcom), 2, char_length(trim(c.sendoutcom)) - 1)"
					+ " and (c.receivecom like 'A%' or c.receivecom like 'E%')"
					+ " and c.stateflag='4'";
			if (strCardType != null && !(strCardType.equals(""))) {
				t_sql = t_sql + " and  b.certifycode = '" + "?strCardType?" + "'";
			}
			t_sql = t_sql + " order by c.makedate,c.maketime";
		}
		// 查询遗失状态
		if (strCardState != null && strCardState.equals("5")) {
			t_sql = " select distinct b.certifyname,"
					+ " a.name,"
					+ " d.name,"
					+ " d.agentcode,"
					+ " c.startno,"
					+ " c.endno,"
					+ " c.sumcount,"
					+ " c.handledate,"
					+ " (select username from lduser where usercode = c.operator),"
					+ " '遗失',"
					+ " c.makedate,"
					+ " c.maketime"
					+ " from ldcom a, lmcertifydes b, lzcard c, laagent d"
					+ " where trim(a.comcode) = '"
					+ "?strMngCom?"
					+ "'"
					+ " and trim(b.certifycode) = trim(c.certifycode)"
					+ " and substr(trim(c.receivecom), 2, char_length(trim(c.receivecom))) like "
					+ " concat('"+"?strMngCom?"+"','%')"
					+ " and c.makedate between to_date('"+ "?strStartDate?" + "','yyyy-mm-dd') and to_date('" + "?strEndDate?" + "','yyyy-mm-dd')"
					+ " and d.agentcode ="
					+ " substr(trim(c.sendoutcom), 2, char_length(trim(c.sendoutcom)) - 1)"
					+ " and (c.receivecom like 'A%' or c.receivecom like 'E%')"
					+ " and c.stateflag='5'";
			if (strCardType != null && !(strCardType.equals(""))) {
				t_sql = t_sql + " and  b.certifycode = '" + "?strCardType?" + "'";
			}
			t_sql = t_sql + " order by c.makedate,c.maketime";
		}

		// 查询失效状态
		if (strCardState != null && strCardState.equals("6")) {
			t_sql = " select distinct b.certifyname,"
					+ " a.name,"
					+ " d.name,"
					+ " d.agentcode,"
					+ " c.startno,"
					+ " c.endno,"
					+ " c.sumcount,"
					+ " c.handledate,"
					+ " (select username from lduser where usercode = c.operator),"
					+ " '失效',"
					+ " c.makedate,"
					+ " c.maketime"
					+ " from ldcom a, lmcertifydes b, lzcard c, laagent d"
					+ " where trim(a.comcode) = '"
					+ "?strMngCom?"
					+ "'"
					+ " and trim(b.certifycode) = trim(c.certifycode)"
					+ " and substr(trim(c.receivecom), 2, char_length(trim(c.receivecom))) like "
					+ " concat('"+"?strMngCom?"+"','%')"
					+ " and c.makedate between to_date('"+ "?strStartDate?" + "','yyyy-mm-dd') and to_date('" + "?strEndDate?" + "','yyyy-mm-dd')"
					+ " and d.agentcode ="
					+ " substr(trim(c.sendoutcom), 2, char_length(trim(c.sendoutcom)) - 1)"
					+ " and (c.receivecom like 'A%' or c.receivecom like 'E%')"
					+ " and c.stateflag='6'";
			if (strCardType != null && !(strCardType.equals(""))) {
				t_sql = t_sql + " and  b.certifycode = '" + "?strCardType?" + "'";
			}
			t_sql = t_sql + " order by c.makedate,c.maketime";

		}
		sqlbv1.sql(t_sql);
		sqlbv1.put("strMngCom", strMngCom);
		sqlbv1.put("strStartDate", strStartDate);
		sqlbv1.put("strEndDate", strEndDate);
		sqlbv1.put("strCardType", strCardType);
		logger.debug("sql=" + t_sql);
		// String strSQL="select name,upcomcode from ldcom where
		// comcode='"+strMngCom+"'";
		ExeSQL exeSQL = new ExeSQL();
		SSRS ssrs = exeSQL.execSQL(sqlbv1);

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
				String[] col = new String[11];
				col[0] = String.valueOf(i);
				col[1] = ssrs.GetText(i, 1);
				col[2] = ssrs.GetText(i, 2);
				col[3] = ssrs.GetText(i, 3);
				col[4] = ssrs.GetText(i, 4);
				col[5] = ssrs.GetText(i, 5);
				col[6] = ssrs.GetText(i, 6);
				col[7] = ssrs.GetText(i, 7);
				col[8] = ssrs.GetText(i, 8);
				col[9] = ssrs.GetText(i, 9);
				col[10] = ssrs.GetText(i, 10);

				alistTable.add(col);
				// logger.debug("col[0]" + col[0]);
				// logger.debug("col[1]" + col[1]);
				// logger.debug("col[2]" + col[2]);
				// logger.debug("col[3]" + col[3]);
				// logger.debug("col[4]" + col[4]);
				// logger.debug("col[5]" + col[5]);
				// logger.debug("col[6]" + col[6]);
				// logger.debug("col[7]" + col[7]);
				// logger.debug("col[8]" + col[8]);
				// logger.debug("col[9]" + col[9]);
				// logger.debug("col[10]" + col[10]);

			}
		}
		String[] cols = new String[11];
		xmlexport.addListTable(alistTable, cols);
		TextTag texttag = new TextTag();

		// logger.debug("您的t_sql的执行结果是"+t_sql);

		// xmlexport.addListTable(alistTable, col);
		texttag.add("ManageCom", ManageComName);
		texttag.add("BranchGroup", BranchGroupName);
		texttag.add("AgentGroup", AgentGroupName);
		texttag.add("Date1", strStartDate);
		texttag.add("Date2", strEndDate);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		// xmlexport.outputDocumentToFile("e:\\", "AgentDZList"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

}
