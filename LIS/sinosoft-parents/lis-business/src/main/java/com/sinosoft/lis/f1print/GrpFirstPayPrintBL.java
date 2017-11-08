package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lh
 * @version 1.0
 */

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LMDutyDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCContPlanDutyParamSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class GrpFirstPayPrintBL implements PrintService {
private static Logger logger = Logger.getLogger(GrpFirstPayPrintBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	// 取得的保单号码
	private String mContNo = "";

	// 输入的查询sql语句
	private String msql = "";
	// 取得的延期承保原因
	private String mUWError = "";
	// 取得的代理人编码
	private String mAgentCode = "";
	private String mGrpContNo = "";

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LCGrpContSet mLCGrpContSet = new LCGrpContSet();
	private LCContPlanDutyParamSchema mLCContPlanDutyParamSchema = new LCContPlanDutyParamSchema();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();

	public GrpFirstPayPrintBL() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		mResult.clear();

		// 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
		}

		return true;
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
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));
		if (mLOPRTManagerSchema == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		if (mLOPRTManagerSchema.getPrtSeq() == null) {
			buildError("getInputData", "没有得到足够的信息:印刷号不能为空！");
			return false;
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "LCContF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private boolean getPrintData() {

		// 根据印刷号查询打印队列中的纪录
		String PrtNo = mLOPRTManagerSchema.getPrtSeq(); // 打印流水号

		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();

		boolean PEFlag = false; // 打印体检件部分的判断标志
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLOPRTManagerSchema.getOtherNo());
		int m, i;
		if (!tLCGrpContDB.getInfo()) {
			mErrors.copyAllErrors(tLCGrpContDB.mErrors);
			buildError("outputXML", "在取得LCCont的数据时发生错误");
			return false;
		}
		mLCGrpContSchema = tLCGrpContDB.getSchema();

		mAgentCode = mLCGrpContSchema.getAgentCode();
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mAgentCode);
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LAAgent的数据时发生错误");
			return false;
		}
		mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
		// 险种信息
		mGrpContNo = mLCGrpContSchema.getGrpContNo();
		// 2-1 查询体检子表
		String[] RiskTitle = new String[11];
		RiskTitle[0] = "PlanCode";
		RiskTitle[1] = "asset";
		RiskTitle[2] = "peopeles";
		RiskTitle[3] = "RiskCode";
		RiskTitle[4] = "RiskName";
		RiskTitle[5] = "DutyCode";
		RiskTitle[6] = "DutyName";
		RiskTitle[7] = "Mult";
		RiskTitle[8] = "GetLimit";
		RiskTitle[9] = "GetRate";

		RiskTitle[10] = "Prem";
		ListTable tRiskInfoTable = new ListTable();
		tRiskInfoTable.setName("RiskInfo");
		String strRiskInfo[] = null;

		String Sql = "select distinct contplancode ,ContPlanName,riskcode ,dutycode from LCContPlanDutyParam where GrpContNo='"
				+ mLCGrpContSchema.getGrpContNo()
				+ "' and contplancode <> '00'";
		ExeSQL q_exesql = new ExeSQL();
		SSRS s_ssrs = new SSRS();
		s_ssrs = q_exesql.execSQL(Sql);
		for (int Index = 1; Index <= s_ssrs.getMaxRow(); Index++) {
			PEFlag = true;

			LMRiskDB tLMRiskDB = new LMRiskDB();
			tLMRiskDB.setRiskCode(s_ssrs.GetText(Index, 3));
			if (!tLMRiskDB.getInfo()) {
				mErrors.copyAllErrors(tLMRiskDB.mErrors);
				buildError("outputXML", "在取得LMRisk的数据时发生错误");
				return false;
			}

			LMDutyDB tLMDutyDB = new LMDutyDB();
			tLMDutyDB.setDutyCode(s_ssrs.GetText(Index, 4));
			if (!tLMDutyDB.getInfo()) {
				mErrors.copyAllErrors(tLMDutyDB.mErrors);
				buildError("outputXML", "在取得LMDutyDB的数据时发生错误");
				return false;
			}

			String sql1 = " select sum(prem) from LCDuty a,LCInsured b where a.ContNo = b.ContNo "
					+ " and rtrim(b.ContPlanCode) = '"
					+ s_ssrs.GetText(Index, 1)
					+ "'"
					+ " and rtrim(a.DutyCode) = '"
					+ s_ssrs.GetText(Index, 4)
					+ "' and grpContNo = '"
					+ mLCGrpContSchema.getGrpContNo()
					+ "'";
			String spl2 = " select count(*) from LCDuty a,LCInsured b where a.ContNo = b.ContNo "
					+ " and rtrim(b.ContPlanCode) = '"
					+ s_ssrs.GetText(Index, 1)
					+ "' "
					+ " and rtrim(a.DutyCode) = '"
					+ s_ssrs.GetText(Index, 4)
					+ "' and grpContNo = '"
					+ mLCGrpContSchema.getGrpContNo()
					+ "' ";

			String spl3 = " select amnt,getlimit,GetRate from LCDuty a,LCInsured b where a.ContNo = b.ContNo "
					+ " and rtrim(b.ContPlanCode) = '"
					+ s_ssrs.GetText(Index, 1)
					+ "' "
					+ " and rtrim(a.DutyCode) = '"
					+ s_ssrs.GetText(Index, 4)
					+ "' and grpContNo = '"
					+ mLCGrpContSchema.getGrpContNo()
					+ "' ";

			SSRS q_ssrs = new SSRS();
			SSRS t_ssrs = new SSRS();
			SSRS n_ssrs = new SSRS();
			q_ssrs = q_exesql.execSQL(sql1);
			t_ssrs = q_exesql.execSQL(spl2);
			n_ssrs = q_exesql.execSQL(spl3);

			strRiskInfo = new String[11];

			strRiskInfo[0] = s_ssrs.GetText(Index, 1);
			strRiskInfo[1] = s_ssrs.GetText(Index, 2);
			strRiskInfo[2] = t_ssrs.GetText(1, 1);
			strRiskInfo[3] = s_ssrs.GetText(Index, 3);

			strRiskInfo[4] = tLMRiskDB.getRiskName();
			strRiskInfo[5] = s_ssrs.GetText(Index, 4);

			strRiskInfo[6] = tLMDutyDB.getDutyName();
			strRiskInfo[7] = new Double(n_ssrs.GetText(1, 1)).toString();
			strRiskInfo[8] = new Double(n_ssrs.GetText(1, 2)).toString();

			strRiskInfo[9] = new Double(n_ssrs.GetText(1, 3)).toString();

			strRiskInfo[10] = q_ssrs.GetText(1, 1);

			tRiskInfoTable.add(strRiskInfo);

		}

		// 其它模版上单独不成块的信息
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("GrpFirstPay.vts", "printer"); // 最好紧接着就初始化xml文档
		// 生成-年-月-日格式的日期

		StrTool tSrtTool = new StrTool();
		String SysDate = tSrtTool.getYear() + "年" + tSrtTool.getMonth() + "月"
				+ tSrtTool.getDay() + "日";

		// 模版自上而下的元素

		texttag.add("BarCode1", mLOPRTManagerSchema.getPrtSeq());
		texttag
				.add(
						"BarCodeParam1",
						"BarHeight=30&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		texttag.add("LCCont.AppntName", mLCGrpContSchema.getGrpName()); // 投保人名称
		texttag.add("LCCont.ContNo", mLCGrpContSchema.getGrpContNo()); // 投保单号

		texttag.add("Bank", getBankName(mLCGrpContSchema.getBankCode()));
		texttag.add("BankID", mLCGrpContSchema.getBankAccNo());
		texttag.add("BankName", mLCGrpContSchema.getAccName());

		texttag.add("LAAgent.Name", mLAAgentSchema.getName()); // 代理人姓名
		texttag.add("LCCont.AgentCode", mLCGrpContSchema.getAgentCode()); // 代理人业务号
		texttag.add("LCCont.ManageCom", getComName(mLCGrpContSchema
				.getManageCom())); // 营业机构
		texttag.add("PrtNo", PrtNo); // 刘水号
		texttag.add("LCCont.PrtNo", mLCGrpContSchema.getPrtNo()); // 印刷号
		texttag.add("SysDate", SysDate);// 当前日期

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		// 保存体检信息
		if (PEFlag == true) {
			xmlexport.addListTable(tRiskInfoTable, RiskTitle); // 保存体检信息
		}

		// xmlexport.outputDocumentToFile("e:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);

		return true;
	}

	/**
	 * 得到通过机构代码得到机构名称
	 * 
	 * @param strComCode
	 * @return
	 * @throws Exception
	 */
	private String getComName(String strComCode) {
		LDCodeDB tLDCodeDB = new LDCodeDB();

		tLDCodeDB.setCode(strComCode);
		tLDCodeDB.setCodeType("station");

		if (!tLDCodeDB.getInfo()) {
			mErrors.copyAllErrors(tLDCodeDB.mErrors);
			return "";
		}
		return tLDCodeDB.getCodeName();
	}

	private String getBankName(String strBankCode)

	{
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
