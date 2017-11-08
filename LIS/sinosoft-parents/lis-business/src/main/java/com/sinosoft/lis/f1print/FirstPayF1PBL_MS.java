package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;
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
import com.sinosoft.utility.XmlExportNew;

public class FirstPayF1PBL_MS {
private static Logger logger = Logger.getLogger(FirstPayF1PBL_MS.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private double fPremSum = 0;
	private double fPremJobAddSum = 0;
	private double fPremHealthAddSum = 0;
	private double fPremLiveAddSum = 0;
	private double fPremHobbyAddSum = 0;
	private String mOperate = "";
	private String CurrentDate = PubFun.getCurrentDate();
	// 保费保额计算出来后的精确位数
	private String FORMATMODOL = "0.00";
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL);

	public FirstPayF1PBL_MS() {
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
		LCPolDB tLCPolDB = new LCPolDB();
		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);// 将prtseq传给DB，目的查找所有相关信息，然后还要返回给schema
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}
		// 需要判断是否已经打印？！

		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();// get all message！

		// 打印时传入的是主险投保单的投保单号
		tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		if (!tLCContDB.getInfo()) {
			mErrors.copyAllErrors(tLCPolDB.mErrors);
			throw new Exception("在获取合同信息时出错,该保单不存在！");
		}
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String tempSQL_Main = "select * from lcpol where contno='"
				+ "?contno?" + "' order by mainpolno,polno ";
		sqlbv1.sql(tempSQL_Main);
		sqlbv1.put("contno", mLOPRTManagerSchema.getOtherNo());
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet = tLCPolDB.executeQuery(sqlbv1);

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
		// zy 2009-05-13 回复日期
		String replyDate = PubFun.calDate(mLOPRTManagerSchema.getMakeDate(), 30, "D","" );
		String ReplyYear = replyDate.substring(0,4);
		String ReplyMon =  replyDate.substring(5,7);
		String ReplyDay =  replyDate.substring(8,10);

		// 设置合计项
		fPremSum = 0;
		fPremJobAddSum = 0;
		fPremHealthAddSum = 0;
		fPremLiveAddSum = 0;
		fPremHobbyAddSum = 0;

		// 加入投保单信息
		ListTable listTable = new ListTable();
		String[] cols = new String[9];

		for (int nIndex = 0; nIndex < tLCPolSet.size(); nIndex++) {
			cols = new String[9];
			getOneRow(cols, tLCPolSet.get(nIndex + 1));
			listTable.add(cols);
		}

		// 设置列名
		cols = new String[9];

		cols[0] = "RiskName";
		cols[1] = "Amnt";
		cols[2] = "PayEndYear";
		cols[3] = "PayIntv";
		cols[4] = "Prem";
		cols[5] = "JobAddPrem";
		cols[6] = "HealthAddPrem";
		cols[7] = "LiveAddPrem";
		cols[8] = "SumPrem";

		listTable.setName("RiskInfo");
		String tPrintName = "首期交费通知书";
		XmlExportNew xmlExport = new XmlExportNew();// 新建一个XmlExport的实例
		xmlExport.createDocument(tPrintName);//初始化数据存储类
		PrintTool.setBarCode(xmlExport, mLOPRTManagerSchema.getPrtSeq());//条形码
		String uLanguage = PrintTool.getCustomerLanguage(tLCContDB.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage)) 
			xmlExport.setUserLanguage(uLanguage);
		xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言
		
		xmlExport.addListTable(listTable, cols);
		String thead = PrintTool.getHead(tLCContDB.getAppntName(),
				tLCContDB.getAppntSex(), uLanguage);
		TextTag texttag = new TextTag();
		texttag.add("AppntName", thead); // 投保人
		texttag.add("PrtNo", tLCContDB.getContNo());
		texttag.add("AgentName", getAgentName(tLCContDB.getAgentCode()));
		texttag.add("AgentCode", tLCContDB.getAgentCode());
		texttag.add("ManageCom", getComName(tLCContDB.getManageCom()));
		texttag.add("InsuredName", tLCContDB.getInsuredName());
		texttag.add("PrtSeq", mLOPRTManagerSchema.getPrtSeq());
		texttag.add("LCPol.prtno", tLCContDB.getPrtNo());
		//zy 2009-05-13 增加核保
		texttag.add("UWOperator", tLCContDB.getUWOperator());

		texttag.add("Prem", mDecimalFormat.format(fPremSum));
		texttag.add("JobPremAdd", mDecimalFormat.format(fPremJobAddSum));
		texttag.add("HealthPremAdd", mDecimalFormat.format(fPremHealthAddSum));
		texttag.add("LivePremAdd", mDecimalFormat.format(fPremLiveAddSum));
		texttag.add("HobbyPremAdd", mDecimalFormat.format(fPremHobbyAddSum));
		//zy 2009-05-14
		texttag.add("PremSum", mDecimalFormat.format(fPremSum + fPremJobAddSum + fPremHealthAddSum+fPremLiveAddSum));
		
		//zy 2009-05-13 增加回复日期
		texttag.add("ReplyYear", ReplyYear);
		texttag.add("ReplyMon", ReplyMon);
		texttag.add("ReplyDay", ReplyDay);

		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		texttag.add("Today", df.format(new Date()));


		if (texttag.size() > 0)
			xmlExport.addTextTag(texttag);
		

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
		// tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		tLOPRTManagerDB.setSchema((LOPRTManagerSchema) mInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		mResult.add(mLOPRTManagerSchema);
		mResult.add(tLOPRTManagerDB);
		FirstPayF1PBLS_MS tFirstPayF1PBLS = new FirstPayF1PBLS_MS();
		tFirstPayF1PBLS.submitData(mResult, mOperate);
		if (tFirstPayF1PBLS.mErrors.needDealError()) {
			mErrors.copyAllErrors(tFirstPayF1PBLS.mErrors);
			buildError("saveData", "提交数据库出错！");
			return false;
		}
		return true;

		// mResult.add(mLOPRTManagerSchema);
		// Vdata.add(mLOPRTManagerSchema);

		// return true;
	}

	// 下面是一些辅助函数

	private String getRiskName(String strRiskCode) throws Exception {
		LMRiskDB tLMRiskDB = new LMRiskDB();
		tLMRiskDB.setRiskCode(strRiskCode);
		if (!tLMRiskDB.getInfo()) {
			mErrors.copyAllErrors(tLMRiskDB.mErrors);
			throw new Exception("在取得险种LMRisk的数据时发生错误");
		}
		return tLMRiskDB.getRiskName();
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

	private void getOneRow(String[] cols, LCPolSchema aLCPolSchema)
			throws Exception {
		cols[0] = getRiskName(aLCPolSchema.getRiskCode());
		cols[1] = String.valueOf(aLCPolSchema.getAmnt());

		if ((aLCPolSchema.getPayEndYear() == 1000)
				&& aLCPolSchema.getPayEndYearFlag().equals("A")) {
			cols[2] = "终生"; // 交费年期
		} else {
			cols[2] = (new Integer(aLCPolSchema.getPayYears()))
					.toString(); // 交费年期
		}

		String sTemp = "";

		if (aLCPolSchema.getPayIntv() == -1) {
			sTemp = "不定期交费";
		}

		if (aLCPolSchema.getPayIntv() == 0) {
			sTemp = "趸交";
		}

		if (aLCPolSchema.getPayIntv() == 1) {
			sTemp = "月交";
		}

		if (aLCPolSchema.getPayIntv() == 3) {
			sTemp = "季交";
		}

		if (aLCPolSchema.getPayIntv() == 6) {
			sTemp = "半年交";
		}

		if (aLCPolSchema.getPayIntv() == 12) {
			sTemp = "年交";
		}

		cols[3] = sTemp; // 交费方式
		cols[4] = String.valueOf(aLCPolSchema
				.getStandPrem()); // 保费	
		fPremSum = fPremSum + aLCPolSchema.getStandPrem(); // 保费合计		

		SSRS tempSSRS = new SSRS();
		ExeSQL tempExeSQL = new ExeSQL();
		// 险种的职业加费
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
				+ "?PolNo?"
				+ "' and PayPlanCode like '000000%'  and payplantype='02'");
		sqlbv2.put("PolNo", aLCPolSchema.getPolNo());
		tempSSRS = tempExeSQL.execSQL(sqlbv2);

		if (tempSSRS.MaxCol > 0) {
			if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
					.GetText(1, 1).trim().equals(""))) {
				// logger.debug("侯志敏的程序307");
				logger.debug("累计职业加费是"
						+ tempSSRS.GetText(1, 1));
				cols[5] = tempSSRS.GetText(1, 1); // 累计加费
				fPremJobAddSum = fPremJobAddSum
						+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
			} else {
				cols[5] = "0.00";
			}
		}
		
		// 险种的健康加费
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
				+ "?PolNo?"
				+ "' and PayPlanCode like '000000%'  and payplantype='01'");
		sqlbv3.put("PolNo", aLCPolSchema.getPolNo());
		tempSSRS = tempExeSQL.execSQL(sqlbv3);

		if (tempSSRS.MaxCol > 0) {
			if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
					.GetText(1, 1).trim().equals(""))) {
				// logger.debug("侯志敏的程序307");
				
				logger.debug("累计健康加费是"
						+ tempSSRS.GetText(1, 1));
				cols[6] = tempSSRS.GetText(1, 1); // 累计加费
				fPremHealthAddSum = fPremHealthAddSum
						+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
			} else {
				cols[6] = "0.00";
			}
		}
	//zy 2009-5-13 合并居住地和爱好加费为其他加费
		// 险种的居住地加费
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
				+ "?PolNo?"
				+ "' and PayPlanCode like '000000%'  and payplantype not in ('01','02') ");
		sqlbv4.put("PolNo", aLCPolSchema.getPolNo());
		tempSSRS = tempExeSQL.execSQL(sqlbv4);

		if (tempSSRS.MaxCol > 0) {
			if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
					.GetText(1, 1).trim().equals(""))) {
				// logger.debug("侯志敏的程序307");
				
				logger.debug("累计居住地加费是"
						+ tempSSRS.GetText(1, 1));
				cols[7] = tempSSRS.GetText(1, 1); // 累计加费
				fPremLiveAddSum = fPremLiveAddSum
						+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
			} else {
				cols[7] = "0.00";
			}
		}
		cols[8] = String.valueOf(Double.parseDouble(cols[4]) + Double.parseDouble(cols[5])
							+Double.parseDouble(cols[6])+Double.parseDouble(cols[7])); // 保险费合计
		
//		// 险种的爱好加费
//		tempSSRS = tempExeSQL
//				.execSQL("select nvl(sum(Prem),0) from LCPrem where PolNo='"
//						+ aLCPolSchema.getPolNo()
//						+ "' and PayPlanCode like '000000%'  and payplantype='04'");
//
//		if (tempSSRS.MaxCol > 0) {
//			if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
//					.GetText(1, 1).trim().equals(""))) {
//				// logger.debug("侯志敏的程序307");
//				
//				logger.debug("累计爱好加费是"
//						+ tempSSRS.GetText(1, 1));
//				cols[8] = tempSSRS.GetText(1, 1); // 累计加费
//				fPremHobbyAddSum = fPremHobbyAddSum
//						+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
//			} else {
//				cols[8] = "0.00";
//			}
//		}
		
	}
}
