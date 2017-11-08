package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
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

public class DSDZListBL {
private static Logger logger = Logger.getLogger(DSDZListBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strStartDate = "";
	private String strEndDate = "";
	private String mManageCom = "";
	private String mAgentCom = "";
	private String mCertifyCode = "";
	private int sumSendCount = 0;
	private int sumNormalCount = 0;
	private int sumCancelCount = 0;
	private int sumLostCount = 0;
	private float sumRate = 0.0f;
	private TransferData mTransferData = new TransferData();
	private GlobalInput mGlobalInput = new GlobalInput();

	public DSDZListBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("Opertate=" + cOperate);
		if (!cOperate.equals("PRINT") && !cOperate.equals("PRINT2")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();
		if (cOperate.equals("PRINT")) {
			if (!getPrintData()) {
				return false;
			}
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
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		mAgentCom = (String) mTransferData.getValueByName("AgentCom");
		mCertifyCode = (String) mTransferData.getValueByName("CertifyCode");
		strStartDate = (String) mTransferData.getValueByName("StartDate");
		strEndDate = (String) mTransferData.getValueByName("EndDate");
		if (mManageCom.equals("")) {
			mManageCom = mGlobalInput.ManageCom;

		}
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
		cError.moduleName = "DSDZListBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/***************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData() {
		XmlExport xmlexport = new XmlExport();
		xmlexport.createDocument("DSDZList.vts", "printer"); // appoint to a
																// special
																// output .vts
																// file
		String[] cols = new String[3];

		ListTable tlistTable = new ListTable();
		tlistTable.setName("List");
		String t_sql = "";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		t_sql = "  select d.managecom,d.agentcom,b.certifyname, "
				+ "(select sum(sumcount) from lzcardtrack where stateflag='0' and certifycode=b.certifycode and receivecom=concat('E',d.agentcom) ), "
				+ "(select sum(sumcount) from lzcard where stateflag='1' and certifycode=b.certifycode and sendoutcom=concat('E',d.agentcom)), "
				+ "(select sum(sumcount) from lzcard where stateflag='2' and certifycode=b.certifycode and sendoutcom=concat('E',d.agentcom)), "
				+ "(select sum(sumcount) from lzcard where stateflag='5' and certifycode=b.certifycode and sendoutcom=concat('E',d.agentcom)) "
				+ "from lmcertifydes b,lacom d "
				+ " where exists (select 'x' from lzcard c where b.certifycode = c.certifycode "
				+ " and concat('E',d.agentcom) = c.sendoutcom "
				+ " and c.makedate between to_date('" + "?strStartDate?"+ "','yyyy-mm-dd')  and to_date('" + "?strEndDate?" + "','yyyy-mm-dd') )  and d.managecom like concat('"+ "?mManageCom?" + "','%')";
		if (mAgentCom != null && !mAgentCom.equals("")) {
			t_sql += "and d.agentcom = '" + "?mAgentCom?" + "'";
		}
		if (mCertifyCode != null && !mCertifyCode.equals("")) {
			t_sql += "and b.certifycode = '" + "?mCertifyCode?" + "'";
		}
		logger.debug("开始执行sql操作" + t_sql);
		sqlbv.sql(t_sql);
		sqlbv.put("strStartDate", strStartDate);
		sqlbv.put("strEndDate", strEndDate);
		sqlbv.put("mManageCom", mManageCom);
		sqlbv.put("mAgentCom", mAgentCom);
		sqlbv.put("mCertifyCode", mCertifyCode);
		ExeSQL exeSQL1 = new ExeSQL();
		SSRS ssrs1 = exeSQL1.execSQL(sqlbv);
		int i_count1 = ssrs1.getMaxRow();
		logger.debug("i_count1的值" + i_count1);
		if (i_count1 != 0) {
			for (int i = 1; i <= i_count1; i++) {
				float rate;
				logger.debug(ssrs1.GetText(1, 1));
				String[] col = new String[8];
				col[0] = ssrs1.GetText(i, 1);
				col[1] = ssrs1.GetText(i, 2);
				col[2] = ssrs1.GetText(i, 3);
				col[3] = ssrs1.GetText(i, 4);
				col[4] = ssrs1.GetText(i, 5);
				col[5] = ssrs1.GetText(i, 6);
				col[6] = ssrs1.GetText(i, 7);
				for (int j = 0; j < 7; j++) {
					if (col[j].equals("null")) {
						col[j] = "" + 0;
					}
				}
				if (col[3].equals("null") || col[3].equals("0")) {
					col[7] = "";
				} else {
					rate = Float.parseFloat(col[4]) / Float.parseFloat(col[3]);
					sumRate += rate;
					col[7] = "" + rate;
				}
				sumSendCount += Integer.parseInt(col[3]);
				sumNormalCount += Integer.parseInt(col[4]);
				sumCancelCount += Integer.parseInt(col[5]);
				sumLostCount += Integer.parseInt(col[6]);
				tlistTable.add(col);
			}
		}

		if (i_count1 == 0) {
			CError tError = new CError();
			tError.moduleName = "StandardSxStaticBL";
			tError.functionName = "getDutyGetClmInfo";
			tError.errorMessage = "在该时间段内没有打印信息！！！";
			this.mErrors.addOneError(tError);
			return false;

		}
		logger.debug("sumRate=" + sumRate);
		xmlexport.addListTable(tlistTable, cols);
		TextTag texttag = new TextTag();
		texttag.add("sumSendCount", sumSendCount);
		texttag.add("sumNormalCount", sumNormalCount);
		texttag.add("sumCancelCount", sumCancelCount);
		texttag.add("sumLostCount", sumLostCount);
		texttag.add("Date3", PubFun.getCurrentDate());
		texttag.add("Date1", strStartDate);
		texttag.add("Date2", strEndDate);
		texttag.add("sumRate", sumRate);
		xmlexport.addTextTag(texttag);
		mResult.clear();
		mResult.addElement(xmlexport);

		return true;
	}

}
