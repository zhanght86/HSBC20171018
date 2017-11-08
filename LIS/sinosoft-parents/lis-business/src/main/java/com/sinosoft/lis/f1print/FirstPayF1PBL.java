package com.sinosoft.lis.f1print;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCPolSet;
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
 * @author z
 * @version 1.0
 */

public class FirstPayF1PBL {
private static Logger logger = Logger.getLogger(FirstPayF1PBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCAddressSchema mLCAddressSchema = new LCAddressSchema();
	private LCPolSchema mLCPolSchema = new LCPolSchema();

	private double fPremSum = 0;
	private double fJobPremAddSum = 0;
	private double fHealthPremAddSum = 0;
	private double fPrem = 0;
	private double fJobPremAdd = 0;
	private double fHealthPremAdd = 0;
	private String mOperate = "";
	private String CurrentDate = PubFun.getCurrentDate();

	public FirstPayF1PBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mOperate = cOperate;
		try {
			if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			// 得到外部传入的数据，将数据备份到本类中（不管有没有operate,都要执行这一部）
			if (!getInputData(cInputData)) {
				return false;
			}

			if (cOperate.equals("CONFIRM")) {
				mResult.clear();
				// 准备所有要打印的数据
				getPrintData();
			} else if (cOperate.equals("PRINT")) {
				if (!saveData(cInputData)) {
					return false;
				}
			}
			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submitData", ex.toString());
			return false;
		}
	}

	public static void main(String[] args) {
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));
		// 只赋给schema一个prtseq

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	// 得到返回值
	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "RefuseAppF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	// 准备所有要打印的数据
	private void getPrintData() throws Exception {
		XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
		xmlExport.createDocument("FirstPayNotice_MS.vts", ""); // 最好紧接着就初始化xml文档

		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema); // 将prtseq传给DB，目的查找所有相关信息，然后还要返回给schema
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！

		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all message！

		// 打印时传入的是主险投保单的投保单号
		tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		if (!tLCContDB.getInfo()) {
			mErrors.copyAllErrors(tLCContDB.mErrors);
			throw new Exception("在获取保单信息时出错！");
		}

		String strContNo = tLCContDB.getContNo();

		// 查询打印队列的信息
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();

		if (mLOPRTManagerSchema.getStateFlag() == null) {
			buildError("getprintData", "无效的打印状态");
		} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
			buildError("getprintData", "该打印请求不是在请求状态");
		}

		mLOPRTManagerSchema.setDoneDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setDoneTime(PubFun.getCurrentTime());

		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
		}

		// 设置合计项
		fPremSum = 0;
		fJobPremAddSum = 0;//职业加费
		fHealthPremAddSum = 0;//健康加费

		// 先加入主险投保单信息
		ListTable listTable = new ListTable();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		// 查出所有的险种投保单
		String strsql = "SELECT * FROM LCPol WHERE ContNo = '" + "?strContNo?"
				+ "'" + " order by PolNo";
		sqlbv1.sql(strsql);
		sqlbv1.put("strContNo", strContNo);
		LCPolDB tempLCPolDB = new LCPolDB();

		LCPolSet tLCPolSet = tempLCPolDB.executeQuery(sqlbv1);

		if (tempLCPolDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tempLCPolDB.mErrors);
			throw new Exception("在获取附加险投保单时出错！");
		}
		String[] RiskInfoTitle = new String[8];

		RiskInfoTitle[0] = "RiskName";
		RiskInfoTitle[1] = "Amnt";
		RiskInfoTitle[2] = "PayYears";
		RiskInfoTitle[3] = "PayIntv";	
		RiskInfoTitle[4] = "Prem";
		RiskInfoTitle[5] = "JobPremAdd";
		RiskInfoTitle[6] = "HealthPrem";
		RiskInfoTitle[7] = "PremSum";

		ListTable tListTable = new ListTable();
		String StrRiskInfo[] = null;
		tListTable.setName("RiskInfo");

		for (int nIndex = 0; nIndex < tLCPolSet.size(); nIndex++) {
			mLCPolSchema = tLCPolSet.get(nIndex + 1).getSchema();
			StrRiskInfo = new String[8];

			LMRiskDB tLMRiskDB = new LMRiskDB();
			tLMRiskDB.setRiskCode(mLCPolSchema.getRiskCode());
			if (!tLMRiskDB.getInfo()) {
				mErrors.copyAllErrors(tLMRiskDB.mErrors);
				buildError("outputXML", "在取得主险LMRisk的数据时发生错误");

			}
			StrRiskInfo[0] = tLMRiskDB.getRiskName();

			StrRiskInfo[1] = (new Double(mLCPolSchema.getAmnt())).toString();
			StrRiskInfo[2] = (new Integer(mLCPolSchema.getPayYears()))
					.toString();
			int tPayIntv = mLCPolSchema.getPayIntv();
			if(tPayIntv==-1)
			    StrRiskInfo[3] = "不定期交";
			else if(tPayIntv==0)
			    StrRiskInfo[3] = "趸交";
			else if(tPayIntv==1)
			    StrRiskInfo[3] = "月交";
			else if(tPayIntv==3)
			    StrRiskInfo[3] = "季交";
			else if(tPayIntv==6)
			    StrRiskInfo[3] = "半年交";
			else if(tPayIntv==12)
			    StrRiskInfo[3] = "年交";			
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			String strSQL = "SELECT SUM(Prem) FROM LCPrem WHERE" + " PolNo = '"
					+ "?PolNo?" + "'"
					+ " AND PayPlanCode NOT LIKE '000000%'";
			sqlbv2.sql(strSQL);
			sqlbv2.put("PolNo", mLCPolSchema.getPolNo());
			ExeSQL exeSQL = new ExeSQL();

			SSRS ssrs = exeSQL.execSQL(sqlbv2);

			if (exeSQL.mErrors.needDealError()) {
				mErrors.copyAllErrors(exeSQL.mErrors);
				throw new Exception("取标准保费时出错");
			}

			if (!(ssrs.GetText(1, 1).equals("0")
					|| ssrs.GetText(1, 1).trim().equals("") || ssrs.GetText(1,
					1).equals("null"))) {
				fPrem = Double.parseDouble(ssrs.GetText(1, 1));
			} else {
				fPrem = 0.0;
			}

			StrRiskInfo[4] = new Double(fPrem).toString();
			fPremSum += fPrem;
			// 取加费
			//取职业加费
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			String strSql = "SELECT SUM(Prem) FROM LCPrem WHERE" + " PolNo = '"
					+ "?PolNo?" + "'"
					+ " AND PayPlanCode LIKE '000000%' and AddFeeDirect='02'";
			sqlbv3.sql(strSql);
			sqlbv3.put("PolNo", mLCPolSchema.getPolNo());
			exeSQL = new ExeSQL();

			ssrs = exeSQL.execSQL(sqlbv3);

			if (exeSQL.mErrors.needDealError()) {
				mErrors.copyAllErrors(exeSQL.mErrors);
				throw new Exception("取职业加费时出错");
			}

			if (!(ssrs.GetText(1, 1).equals("0")
					|| ssrs.GetText(1, 1).trim().equals("") || ssrs.GetText(1,
					1).equals("null"))) {
				fJobPremAdd = Double.parseDouble(ssrs.GetText(1, 1));
			} else {
				fJobPremAdd = 0.0;
			}
			StrRiskInfo[5] = new Double(fJobPremAdd).toString();

			fJobPremAddSum += fJobPremAdd;
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			//取健康加费
			strSql = "SELECT SUM(Prem) FROM LCPrem WHERE" + " PolNo = '"
					+ "?PolNo?" + "'"
					+ " AND PayPlanCode LIKE '000000%' and AddFeeDirect='01'";
			sqlbv4.sql(strSql);
			sqlbv4.put("PolNo", mLCPolSchema.getPolNo());
			exeSQL = new ExeSQL();

			ssrs = exeSQL.execSQL(sqlbv4);

			if (exeSQL.mErrors.needDealError()) {
				mErrors.copyAllErrors(exeSQL.mErrors);
				throw new Exception("取健康加费时出错");
			}

			if (!(ssrs.GetText(1, 1).equals("0")
					|| ssrs.GetText(1, 1).trim().equals("") || ssrs.GetText(1,
					1).equals("null"))) {
				fHealthPremAdd = Double.parseDouble(ssrs.GetText(1, 1));
			} else {
				fHealthPremAdd = 0.0;
			}
			StrRiskInfo[6] = new Double(fHealthPremAdd).toString();

			fHealthPremAddSum += fHealthPremAdd;
			double fSum = fJobPremAdd + fHealthPremAdd + fPrem;
			StrRiskInfo[7] = String.valueOf(fSum);
			tListTable.add(StrRiskInfo);
		}

		if (tLCContDB.getPayMode() == "4") {
			xmlExport.addDisplayControl("displaybank");
		} else {
			xmlExport.addDisplayControl("displaymoney");
		}
		xmlExport.addListTable(tListTable, RiskInfoTitle);
		TextTag texttag = new TextTag();

		texttag.add("BarCode1", mLOPRTManagerSchema.getPrtSeq());
		
		texttag.add("AppntName", tLCContDB.getAppntName());
		texttag.add("ContNo", tLCContDB.getContNo());
		texttag.add("Cavidate", tLCContDB.getCValiDate());

		texttag.add("PrtNo", tLCContDB.getPrtNo());
		texttag.add("prtSeq", mLOPRTManagerSchema.getPrtSeq());

		texttag.add("AgentName", getAgentName(tLCContDB.getAgentCode()));
		texttag.add("AgentCode", tLCContDB.getAgentCode());
		texttag.add("ManageCom", getComName(tLCContDB.getManageCom()));
		texttag.add("InsuredName", tLCContDB.getInsuredName());
		texttag.add("PrtSeq", mLOPRTManagerSchema.getPrtSeq());
		texttag.add("LCPol.prtno", tLCContDB.getPrtNo());
		texttag.add("MangeCom", getComName(tLCContDB.getManageCom()));

		texttag.add("Prem", fPremSum);
		texttag.add("JobPremAdd", fJobPremAddSum);
		texttag.add("HealthPremAdd", fHealthPremAddSum);
		texttag.add("PremSum", String.valueOf(fPremSum + fJobPremAddSum + fHealthPremAddSum));

		texttag.add("Bank", getBankName(tLCContDB.getBankCode()));
		texttag.add("BankID", tLCContDB.getBankAccNo());

		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		texttag.add("Today", df.format(new Date()));

		if (texttag.size() > 0) {
			xmlExport.addTextTag(texttag);
		}

		// xmlExport.outputDocumentToFile("e:\\", "firtpay");
		mResult.clear();
		mResult.addElement(xmlExport);

	}

	private boolean saveData(VData mInputData) {

		// 根据印刷号查询打印队列中的纪录
		// mLOPRTManagerSchema
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		mLOPRTManagerSchema.setStateFlag("1");
		mLOPRTManagerSchema.setDoneDate(CurrentDate);
		mLOPRTManagerSchema.setExeOperator(mGlobalInput.Operator);

		tLOPRTManagerDB.setSchema((LOPRTManagerSchema) mInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		mResult.add(mLOPRTManagerSchema);
		mResult.add(tLOPRTManagerDB);
		FirstPayF1PBLS tFirstPayF1PBLS = new FirstPayF1PBLS();
		tFirstPayF1PBLS.submitData(mResult, mOperate);
		if (tFirstPayF1PBLS.mErrors.needDealError()) {
			mErrors.copyAllErrors(tFirstPayF1PBLS.mErrors);
			buildError("saveData", "提交数据库出错！");
			return false;
		}
		return true;

	}

	private String getAgentName(String strAgentCode) throws Exception {
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(strAgentCode);
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			throw new Exception("在取得LAAgent的数据时发生错误");
		}
		return tLAAgentDB.getName();
	}

	private String getComName(String strComCode) throws Exception {
		LDCodeDB tLDCodeDB = new LDCodeDB();

		tLDCodeDB.setCode(strComCode);
		tLDCodeDB.setCodeType("station");

		if (!tLDCodeDB.getInfo()) {
			mErrors.copyAllErrors(tLDCodeDB.mErrors);
			throw new Exception("在取得LDCode的数据时发生错误");
		}
		return tLDCodeDB.getCodeName();
	}

	private String getBankName(String strBankCode) throws Exception {
		LDCodeDB tLDCodeDB = new LDCodeDB();

		tLDCodeDB.setCode(strBankCode);
		tLDCodeDB.setCodeType("bank");
		if (tLDCodeDB.getInfo()) {
			return tLDCodeDB.getCodeName();
		} else {
			return null;
		}
	}

}
