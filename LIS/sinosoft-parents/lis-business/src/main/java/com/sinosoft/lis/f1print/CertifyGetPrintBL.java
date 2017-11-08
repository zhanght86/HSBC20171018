//CertifyGetPrintBL.java

package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LJCertifyGetDB;
import com.sinosoft.lis.db.LJCertifyPayDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LJCertifyGetSchema;
import com.sinosoft.lis.schema.LJCertifyPaySchema;
import com.sinosoft.lis.vschema.LJCertifyGetAgentSet;
import com.sinosoft.lis.vschema.LJCertifyGetSet;
import com.sinosoft.lis.vschema.LJCertifyPaySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class CertifyGetPrintBL {
private static Logger logger = Logger.getLogger(CertifyGetPrintBL.class);

	private String mAgentCode = "";
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mOperator = "";;
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private String mKmoney;
	private double mSmoney = 0;
	private double mGetMoney = 0;
	private String mNo = "";
	public CErrors mErrors = new CErrors();
	private String mDistict, mDepartment, mBranchCode;
	private LAAgentDB mLAAgentDB = new LAAgentDB();
	private VData mResult = new VData();
	private LJCertifyGetSchema mLJCertifyGetSchema = new LJCertifyGetSchema();
	private LJCertifyGetAgentSet mLJCertifyGetAgentSet = new LJCertifyGetAgentSet();

	public CertifyGetPrintBL() {

	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 获得业务数据；
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 数据效验
		if (!checkDate()) {
			return false;
		}
		// 获得打印数据
		if (!dealData()) {
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		TransferData tTransferData = (TransferData) cInputData
				.getObjectByObjectName("TransferData", 0);
		mAgentCode = (String) tTransferData.getValueByName("AgentCode");
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);

		return true;
	}

	private boolean checkDate() {
		LJCertifyGetSet tLJCertifyGetSet = new LJCertifyGetSet();
		LJCertifyGetDB tLJCertifyGetDB = new LJCertifyGetDB();
		tLJCertifyGetDB.setAgentCode(mAgentCode);
		tLJCertifyGetSet = tLJCertifyGetDB.query();

		mLAAgentDB.setAgentCode(mAgentCode);
		if (!mLAAgentDB.getInfo()) {
			CError.buildErr(this, "未找到业务员信息");
			return false;
		}
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String tSql = "select outworkdate from laagent where outworkdate is not null and agentcode='"
				+ "?mAgentCode?" + "'";
		sqlbv1.sql(tSql);
		sqlbv1.put("mAgentCode", mAgentCode);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv1);
		if (tSSRS.getMaxRow() == 0) {
			CError.buildErr(this, "业务员并未离职");
			return false;
		}
		return true;
	}

	private boolean dealData() {
		// 计算离职日期
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		String tSql = "select to_char(outworkdate,'YYYY'), to_char(outworkdate,'MM'),to_char(outworkdate,'DD'),sex from laagent where outworkdate is not null and agentcode='"
				+ "?mAgentCode?" + "'";
		sqlbv2.sql(tSql);
		sqlbv2.put("mAgentCode", mAgentCode);
		String tYear = "";
		String tMonth = "";
		String tDay = "";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv2);
		if (tSSRS.getMaxRow() == 0) {
			CError.buildErr(this, "业务员并未离职");
			return false;
		}
		tYear = tSSRS.GetText(1, 1);
		tMonth = tSSRS.GetText(1, 2);
		tDay = tSSRS.GetText(1, 3);

		String tSex = tSSRS.GetText(1, 4);
		if (tSex != null) {
			if (tSex.equals("1")) {
				tSex = "女士";
			} else if (tSex.equals("0")) {
				tSex = "先生";
			} else {
				tSex = "";
			}
		} else {
			tSex = "";
		}
		String tAgentCode = mAgentCode;
		LJCertifyPaySet tLJCertifyPaySet = new LJCertifyPaySet();
		LJCertifyPayDB tLJCertifyPayDB = new LJCertifyPayDB();
		tLJCertifyPayDB.setAgentCode(tAgentCode);
		tLJCertifyPaySet = tLJCertifyPayDB.query();
		LJCertifyPaySchema tLJCertifyPaySchema = tLJCertifyPaySet.get(1);

		String tAgentName = tLJCertifyPaySchema.getAgentName();
		logger.debug("代理人姓名:" + tAgentName);
		logger.debug("工号：" + mAgentCode);

		String tIDNo = tLJCertifyPaySchema.getCertifyNo();
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		String ttSql = "select IDType from LJCertifyTempFee where trim(IDNo) = '"
				+ "?tIDNo?" + "'";
		sqlbv3.sql(ttSql);
		sqlbv3.put("tIDNo", tIDNo);
		ExeSQL ttExeSQL = new ExeSQL();
		SSRS ttSSRS = new SSRS();
		ttSSRS = ttExeSQL.execSQL(sqlbv3);

		String tIDType = ttSSRS.GetText(1, 1);
		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeDB.setCodeType("idtype");
		tLDCodeDB.setCode(tIDType);
		if (tLDCodeDB.getInfo())
			tIDType = tLDCodeDB.getCodeName();
		logger.debug("证件号码：" + tIDNo);
		logger.debug("证件类型：" + tIDType);

		double tActuPayMoney = tLJCertifyPaySchema.getActuPayMoney();
		logger.debug("入单时缴纳金额：" + tActuPayMoney);
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		String Ksql = "select -sum(getmoney) from ljcertifygetagent where agentcode='"
				+ "?mAgentCode?" + "' and feefinatype in ('KK','HK')";
		sqlbv4.sql(Ksql);
		sqlbv4.put("mAgentCode", mAgentCode);
		tExeSQL = new ExeSQL();
		tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv4);
		mKmoney = tSSRS.GetText(1, 1);
		logger.debug("扣款金额=====================" + mKmoney);
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		String mySql = "select SumGetMoney,ActuGetNo from LJCertifyGet where agentcode = '"
				+ "?mAgentCode?" + "'";
		sqlbv5.sql(mySql);
		sqlbv5.put("mAgentCode", mAgentCode);
		ExeSQL myExeSQL = new ExeSQL();
		SSRS mySSRS = new SSRS();
		mySSRS = myExeSQL.execSQL(sqlbv5);

		String tSumGetMoney = mySSRS.GetText(1, 1);
		String tActuGetNo = mySSRS.GetText(1, 2);
		logger.debug("通知书号:" + tActuGetNo);
		logger.debug("实付金额：" + tSumGetMoney);
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		String OperSql = "select username from lduser where trim(usercode) = '"
				+ "?Operator?" + "'";
		sqlbv6.sql(OperSql);
		sqlbv6.put("Operator", mGlobalInput.Operator);
		logger.debug("OperSql===========" + OperSql);

		ExeSQL oExeSQL = new ExeSQL();
		ttSSRS = new SSRS();
		ttSSRS = oExeSQL.execSQL(sqlbv6);
		mOperator = ttSSRS.GetText(1, 1);
		// 准备打印：

		XmlExport xmlexport = new XmlExport();
		// 最好紧接着就初始化xml文档
		xmlexport.createDocument("LJCertifyGetPrint.vts", "PRINT");

		TextTag texttag = new TextTag();
		texttag.add("Year", tYear);
		texttag.add("Month", tMonth);
		texttag.add("Date", tDay);
		texttag.add("AgentName", tAgentName);
		texttag.add("AgentCode", mAgentCode);
		texttag.add("Sex", tSex);
		texttag.add("IDNo", tIDNo);
		texttag.add("IDType", tIDType);
		texttag.add("ActuPayMoney", tActuPayMoney);
		texttag.add("Kmoney", mKmoney);
		texttag.add("SumGetMoney", tSumGetMoney);
		texttag.add("ActuGetNo", tActuGetNo);
		texttag.add("CDate", CurrentDate);
		texttag.add("Operator", mOperator);
		xmlexport.addTextTag(texttag);
		mResult.addElement(xmlexport);
		return true;
	}

}
