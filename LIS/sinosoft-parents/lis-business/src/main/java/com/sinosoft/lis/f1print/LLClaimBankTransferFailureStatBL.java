
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.CodeNameQuery;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.ReportPubFun;
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

public class LLClaimBankTransferFailureStatBL {
private static Logger logger = Logger.getLogger(LLClaimBankTransferFailureStatBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mStartDate = "";
	private String mEndDate = "";
	private String mManageCom = "";

	public LLClaimBankTransferFailureStatBL() {
	}

	/**传输数据的公共方法*/
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			CError.buildErr(this, "不支持的操作字符串");
			return false;
		}

		if (!getInputData(cInputData)) {
			return false;
		}

		if (!getPrintData()) {
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		TransferData tTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		if (tTransferData == null || mGlobalInput == null) {
			CError.buildErr(this, "传入后台的参数缺少！");
			return false;
		}

		mStartDate = (String) tTransferData.getValueByName("StartDate");
		mEndDate = (String) tTransferData.getValueByName("EndDate");
		mManageCom = (String) tTransferData.getValueByName("ManageCom");

		if (mStartDate.equals("") || mEndDate.equals("") || mManageCom.equals("")) {
			CError.buildErr(this, "没有得到足够的查询信息！");
			return false;
		}

		return true;
	}

	private boolean getPrintData() {
		XmlExport xmlexport = new XmlExport(); //新建一个XmlExport的实例
		xmlexport.createDocument("LPBankTransferFailureReport.vts", "");

		ListTable tlistTable = new ListTable();
		tlistTable.setName("BKFail");
    //select a.actugetno,c.rgtno,c.endcasedate,a.agentcom,a.agentcode,a.Drawer
		ExeSQL tExeSQL = new ExeSQL();

		String asql = "select c.managecom,(select name from ldcom where comcode=c.managecom),c.otherno,c.actugetno,c.bankcode,"
				+ "(select bankname from ldbank where bankcode=c.bankcode), "
				+ "c.bankaccno,c.accname,a.standbyflag7,(select Name from llbnfgather q where q.clmno=c.otherno and q.otherno=c.actugetno),a.agentcode,(select name from laagent where agentcode=a.agentcode), "
				+ "(select managecom from laagent where agentcode=a.agentcode),(select laagent.name from laagent,ldcom where comcode=managecom and agentcode=a.agentcode), "
				+ "(select (case when mobile is not null then mobile  else phone end) from laagent where agentcode=a.agentcode) "
				+ "from loprtmanager a,LJaget c where a.code='LP01' and a.stateflag='0' and a.otherno=c.otherno and "
				+ "a.standbyflag1=c.actugetno and c.managecom like concat('"
				+ "?mManageCom?"
				+ "','%') and a.makedate>='"
				+ "?mStartDate?"
				+ "' and a.makedate<='"
				+ "?mEndDate?"
				+ "' ";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(asql);
		sqlbv.put("mManageCom", this.mManageCom);
		sqlbv.put("mStartDate", this.mStartDate);
		sqlbv.put("mEndDate", this.mEndDate);
		SSRS assrs = (SSRS) tExeSQL.execSQL(sqlbv);
		if (tExeSQL.mErrors.needDealError() == true || assrs == null || assrs.getMaxRow() <= 0) {
			CError.buildErr(this, "无法查询得到收付费转账失败清单！");
			return false;
		}

		String[] strArr = null;

		for (int i = 1; i <= assrs.getMaxRow(); i++) {
			strArr = new String[16];
			for (int j = 1; j <= assrs.getMaxCol(); j++) {
				strArr[j-1] = assrs.GetText(i, j);
			}
			tlistTable.add(strArr);
		}
		strArr = new String[16];
		xmlexport.addListTable(tlistTable, strArr);

		TextTag texttag = new TextTag(); //新建一个TextTag的实例
		texttag.add("ManageCom", ReportPubFun.getMngName(mManageCom));
		texttag.add("StartDate", mStartDate);
		texttag.add("EndDate", mEndDate);
		texttag.add("PrintOperator",CodeNameQuery.getOperator(this.mGlobalInput.Operator)) ;
		texttag.add("PrintDate", PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());
		logger.debug("大小" + texttag.size());
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}

		mResult.clear();
		mResult.addElement(xmlexport);

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}
}
