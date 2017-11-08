package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPENoticeDB;
import com.sinosoft.lis.db.LCPENoticeItemDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPENoticeItemSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCPENoticeItemSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lh
 * @version 1.0
 */

public class URGENoticeBL implements PrintService {
private static Logger logger = Logger.getLogger(URGENoticeBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	// 取得的保单号码
	private String mPolNo = "";
	// String InsuredName="";
	// 输入的查询sql语句
	private String msql = "";
	// 取得的延期承保原因
	private String mUWError = "";
	// 取得的代理人编码
	private String mAgentCode = "";
	// 主险名称
	private String mMainRiskName = "";
	private double fPremSum = 0;
	private double fPremAddSum = 0;
	private String ReasonInfo1 = "您无需补交保险费！";
	private String lys_Flag = "0";
	private String lys_Flag_main = "0";
	private String lys_Flag_ab = "0";

	private String FORMATMODOL = "0.00"; // 保费保额计算出来后的精确位数
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL); // 数字转换对象
	/** class object */
	private XmlExport xmlexport = new XmlExport();
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCContSet mLCContSet = new LCContSet();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LCPolSet mLCPolSet = new LCPolSet();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();
	private LMRiskSchema mLMRiskSchema = new LMRiskSchema();
	private LCAddressSchema mLCAddressSchema = new LCAddressSchema();

	public URGENoticeBL() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
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

	public static void main(String[] args) throws Exception {
		String PrtSeq = "86000020040810010333";
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq(PrtSeq);
		logger.debug(PrtSeq);
		GlobalInput tG = new GlobalInput();

		VData tVData = new VData();
		VData mResult = new VData();
		tVData.addElement(tLOPRTManagerSchema);
		URGENoticeBL tURGENoticeBL = new URGENoticeBL();
		tURGENoticeBL.submitData(tVData, "PRINT");
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
		// mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		if (mLOPRTManagerSchema == null) {
			buildError("getInputData", "没有得到足够的信息！");
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

		cError.moduleName = "LCPolF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private boolean getPrintData() {

		// boolean URGENewPayFeeFlag = false; //催办首期交费的判断标志
		// boolean URGEUWFlag=false; //催办核保标记
		// boolean URGEPEFlag=false; //催办体检标记

		// 根据印刷号查询打印队列中的纪录
		String PrtNo = mLOPRTManagerSchema.getPrtSeq();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("getPrintData", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // 保存打印队列数据

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		if (!tLCContDB.getInfo()) {
			mErrors.copyAllErrors(tLCContDB.mErrors);
			buildError("getPrintData", "在取得LCPol的数据时发生错误");
			return false;
		}

		mLCContSchema = tLCContDB.getSchema(); // 保存主险投保单信息

		mAgentCode = mLCContSchema.getAgentCode();
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mAgentCode);
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("getPrintData", "在取得LAAgent的数据时发生错误");
			return false;
		}
		mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息

		// 投保人地址和邮编
		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(mLCContSchema.getContNo());
		if (tLCAppntDB.getInfo() == false) {
			mErrors.copyAllErrors(tLCAppntDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}

		LCAddressDB tLCAddressDB = new LCAddressDB();

		tLCAddressDB.setCustomerNo(mLCContSchema.getAppntNo());
		tLCAddressDB.setAddressNo(tLCAppntDB.getAddressNo());
		if (tLCAddressDB.getInfo() == false) {
			mErrors.copyAllErrors(tLCAddressDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}

		mLCAddressSchema = tLCAddressDB.getSchema();

		// 取主险信息
		// LMRiskDB tLMRiskDB = new LMRiskDB();
		// tLMRiskDB.setRiskCode(mLCPolSchema.getRiskCode());
		// if (!tLMRiskDB.getInfo())
		// {
		// mErrors.copyAllErrors(tLMRiskDB.mErrors);
		// buildError("outputXML", "在取得主险LMRisk的数据时发生错误");
		// return false;
		// }
		// mMainRiskName=tLMRiskDB.getRiskName();
		// mLMRiskSchema=tLMRiskDB.getSchema();//保存主险信息

		// 判断打印队列的类型(相关表信息看：寿险业务承保(个单明细))
		if (mLOPRTManagerSchema.getCode().equals("10")) // 首期交费通知书催办通知书
		{
			if (!dealURGENewPay()) {
				buildError("getPrintData", "在处理催办首期交费数据时发生错误");
				return false;
			}
		}
		if (mLOPRTManagerSchema.getCode().equals("11")) // 体检通知书催办通知书
		{
			if (!dealURGEPE()) {
				buildError("getPrintData", "在处理催办体检通知书数据时发生错误");
				return false;
			}
		}
		if (mLOPRTManagerSchema.getCode().equals("12")) // 核保通知书催办通知书
		{
			if (!dealURGEUW()) {
				buildError("getPrintData", "在处理催办核保通知书数据时发生错误");
				return false;
			}
		}
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

	/**
	 * 根据主险保单号码查询主险保单
	 * 
	 * @param tMainPolNo
	 * @return LCPolSchema
	 */
	private LCPolSchema queryMainPol(String tMainPolNo) {
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(tMainPolNo);
		tLCPolDB.setMainPolNo(tMainPolNo);
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet = tLCPolDB.query();
		if (tLCPolSet == null) {
			buildError("queryMainPol", "没有找到主险保单！");
			return null;
		}
		if (tLCPolSet.size() == 0) {
			buildError("queryMainPol", "没有找到主险保单！");
			return null;
		}
		return tLCPolSet.get(1);
	}

	/**
	 * fill out the common data in model
	 * 
	 * @return
	 */
	private boolean commonInfo(TextTag texttag) {
		xmlexport = new XmlExport();
		xmlexport.createDocument("URGENotice.vts", "printer");

		// 生成-年-月-日格式的日期
		// StrTool tSrtTool = new StrTool();
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		String codeType = mLOPRTManagerSchema.getCode();
		String oldCodeType = "";
		if (codeType.equals("10")) {
			oldCodeType = "07";
		}
		if (codeType.equals("11")) {
			oldCodeType = "03";
		}
		if (codeType.equals("12")) {
			oldCodeType = "05";
		}
		// 找到催办的原通知书打印数据（其它号码相同，且StateFlag=3）
		SSRS tRS = new SSRS();
		ExeSQL tES = new ExeSQL();
		String NoticeDate = "";
		String strSql = "select DoneDate from LOPRTManager where OtherNo='"
				+ "?OtherNo?" + "' ";
		strSql = strSql + " and StateFlag='3'";
		strSql = strSql + " and Code='" + "?oldCodeType?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strSql);
		sqlbv1.put("OtherNo", mLOPRTManagerSchema.getOtherNo());
		sqlbv1.put("oldCodeType", oldCodeType);
		tRS = tES.execSQL(sqlbv1);
		if (tES.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tES.mErrors);
			buildError("commonInfo", "打印管理表查询失败！");
			return false;
		}
		if (tRS.MaxRow == 0) {
			// NoticeDate="2003-3-1";
			buildError("commonInfo", "没有找到催办的原通知书的打印数据！");
			return false;
		} else {
			NoticeDate = tRS.GetText(1, 1);
		}

		int interval;
		strSql = "Select * from ldsysvar where sysvar='URGEinteval'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(strSql);
		tRS = tES.execSQL(sqlbv2);
		if (tES.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tES.mErrors);
			buildError("commonInfo", "系统变量表查询失败！");
			return false;
		}
		if (tRS.MaxRow == 0) {
			interval = 30; // default 30 day
		} else {
			interval = Integer.parseInt(tRS.GetText(1, 3));
		}

		FDate tD = new FDate();
		Date newBaseDate = new Date();
		// liuqiang 催办通知书的截至日期应该是当前系统日期加上可配置的一个时间间隔
		// newBaseDate=tD.getDate(NoticeDate); //将字符串转换为日期,后面计算用
		newBaseDate = tD.getDate(PubFun.getCurrentDate()); // 将字符串转换为日期,后面计算用
		if (newBaseDate == null) {
			buildError("commonInfo", "打印管理表中数据打印日期不正确！");
			return false;
		}
		Date AfterDate = PubFun.calDate(newBaseDate, interval, "D", null);
		String NewDate = tD.getString(AfterDate);

		// 模版自上而下的元素
		texttag.add("BarCode1", mLOPRTManagerSchema.getPrtSeq());
		texttag
				.add(
						"BarCodeParam1",
						"BarHeight=30&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		texttag.add("LCPol.AppntName", mLCContSchema.getAppntName()); // 投保人名称
		texttag.add("Post", mLCAddressSchema.getZipCode());// 投保人邮政编码
		texttag.add("Address", mLCAddressSchema.getPostalAddress());// 投保人地址
		texttag.add("LCPol.PolNo", mLCContSchema.getContNo()); // 投保单号
		texttag.add("RiskName", mLMRiskSchema.getRiskName()); // 主险名称
		texttag.add("NoticeDate", NoticeDate);
		texttag.add("NewDate", NewDate);
		// texttag.add("InsuredName",mLCContSchema.getInsuredName()); //被保险人名称
		texttag.add("AppName", mLCContSchema.getAppntName()); // 投保人名称
		texttag.add("LAAgent.Name", mLAAgentSchema.getName()); // 代理人姓名
		texttag.add("LCPol.AgentCode", mLCContSchema.getAgentCode()); // 代理人业务号
		texttag.add("LCPol.ManageCom", getComName(mLCPolSchema.getManageCom())); // 营业机构
		texttag.add("PrtNo", mLOPRTManagerSchema.getPrtSeq()); // 流水号
		texttag.add("LCPol.PrtNo", mLCContSchema.getPrtNo()); // 印刷号
		texttag.add("LCPol.UWCode", mLCContSchema.getUWOperator()); // 核保师代码
		texttag.add("SysDate", SysDate);
		return true;
	}

	/**
	 * 处理催办首期交费
	 * 
	 * @return
	 */
	private boolean dealURGENewPay() {
		TextTag texttag = new TextTag();
		if (!commonInfo(texttag)) {
			return false;
		}

		// 设置合计项
		fPremSum = 0;
		fPremAddSum = 0;

		// 先加入主险投保单信息
		ListTable listTable = new ListTable();
		String[] cols = new String[5];

		// 查出所有的附加险投保单

		String strSQL = "select * from LCPol where contno = '"
				+ "?contno?" + "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(strSQL);
		sqlbv3.put("contno", mLCContSchema.getContNo());
		LCPolDB tempLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = tempLCPolDB.executeQuery(sqlbv3);

		if (tempLCPolDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tempLCPolDB.mErrors);
			buildError("dealURGENewPay", "在取得附险LMRisk的数据时发生错误");
			return false;
		}

		for (int nIndex = 0; nIndex < tLCPolSet.size(); nIndex++) {
			cols = new String[5];

			if (!getOneRow(cols, tLCPolSet.get(nIndex + 1))) {
				return false;
			}

			listTable.add(cols);

		}

		// 设置列名
		cols = new String[5];

		cols[0] = "RiskName";
		cols[1] = "PolNo";
		cols[2] = "Prem";
		cols[3] = "PremAdd";
		cols[4] = "PremSum";

		xmlexport.addDisplayControl("displayfirstpay"); // 模版上的首期交费信息部分的控制标记
		listTable.setName("RiskInfo");
		xmlexport.addListTable(listTable, cols);

		texttag.add("Prem", fPremSum);
		texttag.add("PremAdd", fPremAddSum);
		texttag.add("PremSum", String.valueOf(fPremSum + fPremAddSum));

		xmlexport.addTextTag(texttag);

		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	/**
	 * 处理催办体检通知书
	 * 
	 * @return
	 */
	private boolean dealURGEPE() {

		TextTag texttag = new TextTag();
		if (!commonInfo(texttag)) {
			return false;
		}

		boolean PEFlag = false; // 打印体检件部分的判断标志

		// 2-体检信息
		// 2-1 查询体检主表
		LCPENoticeDB tLCPENoticeDB = new LCPENoticeDB();
		tLCPENoticeDB.setProposalContNo(mLCContSchema.getContNo());
		tLCPENoticeDB.setPrtSeq(mLOPRTManagerSchema.getOldPrtSeq());
		logger.debug(mLOPRTManagerSchema.getOldPrtSeq());
		// tLCPENoticeDB.setName(mLCPolSchema.getInsuredNo());
		if (!tLCPENoticeDB.getInfo()) {
			mErrors.copyAllErrors(tLCPENoticeDB.mErrors);
			buildError("dealURGEUW", "在取得体检通知的数据时发生错误");
			return false;
		}
		String PEDate = tLCPENoticeDB.getPEDate(); // 保存体检日期
		PEDate = PEDate + "-";
		String CheckDate = StrTool.decodeStr(PEDate, "-", 1) + "年"
				+ StrTool.decodeStr(PEDate, "-", 2) + "月"
				+ StrTool.decodeStr(PEDate, "-", 3) + "日";

		String NeedLimosis = "";
		if (tLCPENoticeDB.getPEBeforeCond().equals("1")) // 是否需要空腹
		{
			NeedLimosis = "是";
		} else {
			NeedLimosis = "否";
		}
		String PEAddress = tLCPENoticeDB.getPEAddress(); // 体检地点

		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeDB.setCodeType("hospitalcode");
		tLDCodeDB.setCode(PEAddress);
		if (tLDCodeDB.getInfo()) {
			PEAddress = tLDCodeDB.getCodeName();
		} else // liuqiang 增加else处理
		{
			tLDCodeDB.setCodeType("hospitalcodeuw");
			tLDCodeDB.setCode(PEAddress);
			if (tLDCodeDB.getInfo()) {
				PEAddress = tLDCodeDB.getCodeName();
			} else {
				PEAddress = "该医院代码尚未建立，请确认！";
			}
		}

		// 2-1 查询体检子表
		String[] PETitle = new String[2];
		PETitle[0] = "ID";
		PETitle[1] = "CHECKITEM";
		ListTable tPEListTable = new ListTable();
		String strPE[] = null;
		tPEListTable.setName("CHECKITEM"); // 对应模版体检部分的行对象名
		LCPENoticeItemDB tLCPENoticeItemDB = new LCPENoticeItemDB();
		LCPENoticeItemSet tLCPENoticeItemSet = new LCPENoticeItemSet();
		tLCPENoticeItemDB.setProposalContNo(mLCContSchema.getContNo());
		// tLCPENoticeItemDB.setInsuredNo(mLCPolSchema.getInsuredNo());
		tLCPENoticeItemDB.setFreePE("N");
		tLCPENoticeItemSet.set(tLCPENoticeItemDB.query());
		if (tLCPENoticeItemSet.size() == 0) {
			PEFlag = false;
		} else {
			PEFlag = true;
			LCPENoticeItemSchema tLCPENoticeItemSchema;
			for (int i = 1; i <= tLCPENoticeItemSet.size(); i++) {
				tLCPENoticeItemSchema = new LCPENoticeItemSchema();
				tLCPENoticeItemSchema.setSchema(tLCPENoticeItemSet.get(i));
				strPE = new String[2];
				strPE[0] = tLCPENoticeItemSchema.getPEItemCode(); // 序号
				strPE[1] = tLCPENoticeItemSchema.getPEItemName(); // 序号对应的内容
				tPEListTable.add(strPE);
			}
		}

		// 保存体检信息
		if (PEFlag == true) {
			xmlexport.addDisplayControl("displaycheckbody"); // 模版上的体检信息部分的控制标记
			xmlexport.addListTable(tPEListTable, PETitle); // 保存体检信息
		}

		texttag.add("CheckDate", CheckDate); // 体检日期
		texttag.add("NeedLimosis", NeedLimosis); // 是否需要空腹
		texttag.add("Hospital", PEAddress); // 体检地点
		xmlexport.addTextTag(texttag);

		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	/**
	 * 处理催办核保通知书
	 * 
	 * @return
	 */
	private boolean dealURGEUW() {
		TextTag texttag = new TextTag();
		if (!commonInfo(texttag)) {
			return false;
		}

		boolean ChangePolFlag = false; // 打印保险计划变更标记
		boolean payClientFee = false; // 因保险计划变更，给客户退费标记
		boolean getClientFee = false; // 因保险计划变更，客户需要补交费用标记
		double ChangeFee = 0; // 因保险计划变更，客户需要补交费用或退费
		String ChangeResult = "";
		String ChangePolReason = ""; // 保险计划变更的原因

		boolean QuestionFlag = false; // 打印问题件部分的判断标志
		boolean AddFeeFlag = false; // 打印加费部分的判断标志
		boolean SpecFlag = false; // 打印特别约定部分的判断标志
		double SumPrem = 0; // 合计保费

		double SpecAddFeeSum = 0; // 和保险计划变更一起的客户的加费
		double oldSumPrem = 0; // 合计加费

		String MainRiskName = new String();
		String Sql = new String();
		String tSql = new String();
		ListTable tRiskListTable = new ListTable();
		String[] RiskInfoTitle = new String[6];

		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(mLCContSchema.getContNo());

		LCPolSet tempLCPolSet = tLCPolDB.query();

		for (int nIndex = 0; nIndex < tempLCPolSet.size(); nIndex++) {

			mLCPolSchema = tempLCPolSet.get(nIndex + 1);
			// 1-险种信息：
			// 1.1-取主险信息

			String[] RiskInfo = new String[6];
			tRiskListTable.setName("MAIN"); // 对应模版投保信息部分的行对象名
			RiskInfoTitle[0] = "RiskName"; // 险种名称
			RiskInfoTitle[1] = "Amnt"; // 保险金额
			RiskInfoTitle[2] = "PayYears"; // 缴费年期
			RiskInfoTitle[3] = "PayIntv"; // 缴费方式（间隔）
			RiskInfoTitle[4] = "Prem"; // 保费
			RiskInfoTitle[5] = "AddPrem"; // 加费

			// 1.1-取主险信息
			LMRiskDB tLMRiskDB = new LMRiskDB();
			tLMRiskDB.setRiskCode(tempLCPolSet.get(nIndex + 1).getRiskCode());
			if (!tLMRiskDB.getInfo()) {
				mErrors.copyAllErrors(tLMRiskDB.mErrors);
				buildError("outputXML", "在取得主险LMRisk的数据时发生错误");
				return false;
			}

			RiskInfo[0] = tLMRiskDB.getRiskName(); // 险种名称
			Double fTemp = new Double(tempLCPolSet.get(nIndex + 1).getAmnt());
			int iTemp = 0;
			logger.debug(fTemp);
			RiskInfo[1] = fTemp.toString(); // 保险金额
			if (mLCPolSchema.getPayEndYear() == 1000
					&& mLCPolSchema.getPayEndYearFlag().equals("A")) {
				RiskInfo[2] = "终生"; // 交费年期
			} else {
				RiskInfo[2] = (new Integer(mLCPolSchema.getPayYears()))
						.toString(); // 交费年期
			}
			String sTemp = "";
			if (mLCPolSchema.getPayIntv() == -1) {
				sTemp = "不定期交费";
			}
			if (mLCPolSchema.getPayIntv() == 0) {
				sTemp = "趸交";
			}
			if (mLCPolSchema.getPayIntv() == 1) {
				sTemp = "月交";
			}
			if (mLCPolSchema.getPayIntv() == 3) {
				sTemp = "季交";
			}
			if (mLCPolSchema.getPayIntv() == 6) {
				sTemp = "半年交";
			}
			if (mLCPolSchema.getPayIntv() == 12) {
				sTemp = "年交";
			}
			RiskInfo[3] = sTemp; // 交费方式
			RiskInfo[4] = new Double(mLCPolSchema.getStandPrem()).toString(); // 保费

			SSRS tempSSRS = new SSRS();
			ExeSQL tempExeSQL = new ExeSQL();
			// 取主险投保单加费信息
			tSql = "select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
					+ "?PolNo?"
					+ "' and PayPlanCode like '000000%'";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tSql);
			sqlbv4.put("PolNo", mLCPolSchema.getPolNo());
			logger.debug("Sql:" + tSql);
			tempSSRS = tempExeSQL.execSQL(sqlbv4);
			// 该判断实质上是没用意义的。
			logger.debug("查询的结果是" + tempSSRS.MaxRow);
			if (tempSSRS.MaxCol > 0) {
				if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS.GetText(1,
						1).trim().equals(""))) {
					RiskInfo[5] = tempSSRS.GetText(1, 1); // 累计加费
					logger.debug(tempSSRS.GetText(1, 1));
					SpecAddFeeSum = SpecAddFeeSum
							+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
				}
			}

			tRiskListTable.add(RiskInfo); // 加入主险信息
			SumPrem = SumPrem + mLCPolSchema.getStandPrem(); // 原保费合计
			logger.debug("原保费合计是" + SumPrem);
			// 1.2-取附险信息
			LCPolDB tempLCPolDB = new LCPolDB();
			LCPolSet tLCPolSet = new LCPolSet();
			LCPolSchema tLCPolSchema = new LCPolSchema();

			Sql = "select * from LCPol where MainPolNo='"
					+ "?PolNo?" + "' and PolNo!='"
					+ "?PolNo?" + "'";
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(Sql);
			sqlbv5.put("PolNo", mLCPolSchema.getPolNo());
			logger.debug("附加险的查询方法是" + Sql);
			tLCPolSet = tempLCPolDB.executeQuery(sqlbv5);

			if (tLCPolSet != null) {

				logger.debug("查询的结果是" + tLCPolSet.size());
				mLCPolSet.set(tLCPolSet); // 保存附加险投保单集合,后用
				logger.debug("mlcpolset的值是" + mLCPolSet.size());
				for (int n = 1; n <= tLCPolSet.size(); n++) {

					tLCPolSchema = tLCPolSet.get(n);
					logger.debug("n的值是" + n);
					logger.debug("附加险的保单号码是" + tLCPolSchema.getPolNo());
					logger.debug("主险的保单号码是" + tLCPolSchema.getMainPolNo());
					String lPolNo = tLCPolSchema.getPolNo();
					RiskInfo = new String[6];
					tLMRiskDB = new LMRiskDB();
					tLMRiskDB.setRiskCode(tLCPolSchema.getRiskCode());

					if (!tLMRiskDB.getInfo()) {
						mErrors.copyAllErrors(tLMRiskDB.mErrors);
						buildError("outputXML", "在取得附险LMRisk的数据时发生错误");
						return false;
					}
					logger.debug("附加险的险种名称是" + tLMRiskDB.getRiskName());
					RiskInfo[0] = tLMRiskDB.getRiskName(); // 险种名称
					fTemp = new Double(tLCPolSchema.getAmnt());
					RiskInfo[1] = fTemp.toString(); // 保险金额
					if (tLCPolSchema.getPayEndYear() == 1000
							&& tLCPolSchema.getPayEndYearFlag().equals("A")) {
						RiskInfo[2] = "终生"; // 交费年期
					} else {
						RiskInfo[2] = (new Integer(tLCPolSchema.getPayYears()))
								.toString(); // 交费年期
					}
					sTemp = "";
					if (tLCPolSchema.getPayIntv() == -1) {
						sTemp = "不定期交费";
					}
					if (tLCPolSchema.getPayIntv() == 0) {
						sTemp = "趸交";
					}
					if (tLCPolSchema.getPayIntv() == 1) {
						sTemp = "月交";
					}
					if (tLCPolSchema.getPayIntv() == 3) {
						sTemp = "季交";
					}
					if (tLCPolSchema.getPayIntv() == 6) {
						sTemp = "半年交";
					}
					if (tLCPolSchema.getPayIntv() == 12) {
						sTemp = "年交";
					}
					RiskInfo[3] = sTemp; // 交费方式
					RiskInfo[4] = new Double(tLCPolSchema.getStandPrem())
							.toString(); // 保费
					logger.debug("附加险的标准保费是"
							+ tLCPolSchema.getStandPrem());
					tRiskListTable.add(RiskInfo); // 加入附险信息
					SumPrem = SumPrem + tLCPolSchema.getStandPrem(); // 原保费合计
					// 附险的加费
					Sql = "select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
							+ "?lPolNo?" + "' and PayPlanCode like '000000%'";
					SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
					sqlbv6.sql(Sql);
					sqlbv6.put("lPolNo", lPolNo);
					logger.debug("附加险的加费信息的查询语句Sql:" + Sql);
					tempSSRS = tempExeSQL.execSQL(sqlbv6);
					if (tempSSRS.MaxCol > 0) {
						if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
								.GetText(1, 1).trim().equals(""))) {
							RiskInfo[5] = tempSSRS.GetText(1, 1); // 累计加费
							SpecAddFeeSum = SpecAddFeeSum
									+ Double
											.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
						}
					}
				}
			}
		}
		SpecAddFeeSum = Double
				.parseDouble(mDecimalFormat.format(SpecAddFeeSum)); // 转换计算后的保费(规定的精度)
		SumPrem = Double.parseDouble(mDecimalFormat.format(SumPrem)); // 转换计算后的保费(规定的精度)

		int SN = 0; // 页面排序序号
		String[] QuestionTitle = new String[2];
		QuestionTitle[0] = "ID";
		QuestionTitle[1] = "Question";
		ListTable tQuestionListTable = new ListTable();

		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mLCContSchema.getContNo());
		if (!tLCCUWMasterDB.getInfo()) {
			mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);
			buildError("outputXML", "在取得LCUWMaster的数据时发生错误");
			return false;
		}

		// 保险计划变更标记
		if (tLCCUWMasterDB.getChangePolFlag() != null) {
			if (tLCCUWMasterDB.getChangePolFlag().equals("1")
					|| tLCCUWMasterDB.getChangePolFlag().equals("2")) {
				// 查询暂交费纪录,判断是否补交或退费
				SN = 1;
				ChangePolFlag = true;
				QuestionFlag = false; // 问题件不显示
				double SumTempfee = 0; // 暂交费累积
				ChangePolReason = tLCCUWMasterDB.getChangePolReason(); // 变更原因

				// 查询暂交费纪录,判断是否补交或退费
				// lys---2004-7-27日进行修改
				String tem_sql = "select * from LJTempFee where otherno in ('"
						+ "?PolNo?" + "','"
						+ "?PrtNo?" + "' )";
				logger.debug("查询的语句是" + tem_sql);
				SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
				sqlbv7.sql(tem_sql);
				sqlbv7.put("PolNo", mLCPolSchema.getPolNo());
				sqlbv7.put("PrtNo", mLCPolSchema.getPrtNo());
				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
				LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
				LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
				// tLJTempFeeDB.setOtherNo(mLCPolSchema.getPolNo());

				tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv7); // 根据其它号码类型查询
				if (tLJTempFeeDB.mErrors.needDealError()) {
					mErrors.copyAllErrors(tLJTempFeeDB.mErrors);
					buildError("getPrintData", "在取得LJTempFee的数据时发生错误");
					return false;
				}
				logger.debug("查询的结果是" + tLJTempFeeSet.size());
				// 没有找到暂交费的数据
				if (tLJTempFeeSet.size() == 0) {
					SumTempfee = 0;
				} else {
					tLJTempFeeDB.setTempFeeNo(tLJTempFeeSet.get(1)
							.getTempFeeNo());
					tLJTempFeeSet = tLJTempFeeDB.query(); // 根据暂交费号查询
					for (int n = 1; n <= tLJTempFeeSet.size(); n++) {
						SumTempfee = SumTempfee
								+ tLJTempFeeSet.get(n).getPayMoney();
					}
				}

				ChangeFee = SumTempfee - SumPrem - SpecAddFeeSum; // 正表示给客户退费，负表示客户补交费用
				ChangeFee = Double
						.parseDouble(mDecimalFormat.format(ChangeFee)); // 转换计算后的保费(规定的精度)

				if (ChangeFee >= 0) {
					ChangeResult = "需退还您保费 " + ChangeFee + " 元";
				} else {
					ChangeResult = "请您补交保费 " + (-ChangeFee) + " 元";
				}
			}
		}

		if (ChangePolFlag == false) {
			// 2-问题信息

			String strQue[] = null;
			tQuestionListTable.setName("QUESTION"); // 对应模版问题部分的行对象名

			String q_sql = "";

			q_sql = "select IssueCont from LCIssuePol where BackObjType = '3' and NeedPrint = 'Y' and ProposalContNo  = '"
					+ "?ProposalContNo?" + "'";
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(q_sql);
			sqlbv8.put("ProposalContNo", mLCContSchema.getContNo());
			logger.debug("问题件中所执行的sql是" + q_sql);
			ExeSQL q_exesql = new ExeSQL();
			SSRS q_ssrs = new SSRS();
			q_ssrs = q_exesql.execSQL(sqlbv8);
			if (q_ssrs.getMaxRow() == 0) {
				QuestionFlag = false;
			} else {
				QuestionFlag = true;

				for (int i = 1; i <= q_ssrs.getMaxRow(); i++) {
					strQue = new String[2];
					strQue[0] = (new Integer(i)).toString(); // 序号
					strQue[1] = q_ssrs.GetText(1, i); // 序号对应的内容
					tQuestionListTable.add(strQue);
				}
			}
			SN = q_ssrs.getMaxRow(); // 页面排序序号
		}

		// 3-加费信息
		// 3-1取出加费原因
		/*
		 * LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		 * tLCUWMasterDB.setProposalNo(mLCPolSchema.getProposalNo()); if
		 * (!tLCUWMasterDB.getInfo()) {
		 * mErrors.copyAllErrors(tLCUWMasterDB.mErrors); buildError("outputXML",
		 * "在取得LCUWMaster的数据时发生错误"); return false; }
		 */
		String AddFeeReason = tLCCUWMasterDB.getAddPremReason(); // 得到加费原因,后用
		logger.debug("加费:");
		// 3-2取出加费部分的数据
		/**
		 * 查询出加费的详细信息
		 */
		double SumAddFee = 0;
		double SumAddFeeInfo = 0; // lys最后的合计金额
		ListTable tAddFeeListTable = new ListTable();
		ListTable tAddFeeInfoListTable = new ListTable(); // 加费明细信息

		String[] AddFee = new String[4];
		String AddFeeTitle[] = new String[4];

		String[] AddFeeInfo = new String[2]; // lys显示加费明细信息内容
		String AddFeeInfoTitle[] = new String[2]; // lys显示加费明细的信息内容

		AddFeeTitle[0] = "RiskName"; // 险种名称
		AddFeeTitle[1] = "Amnt"; // 保险金额
		AddFeeTitle[2] = "OriPrem"; // 原保险费
		AddFeeTitle[3] = "AddFee"; // 加费

		AddFeeInfoTitle[0] = "RiskName"; // lys加费明细的险种名称
		AddFeeInfoTitle[1] = "Sum_bujiao"; // lys加费明细信息的补交金额

		tAddFeeListTable.setName("FEE"); // 对应模版加费部分的行对象名
		tAddFeeInfoListTable.setName("FEEINFO"); // lys对应模板的加费明细的行对象名称

		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		// 3-2-1 取主险投保单加费信息
		Sql = "select sum(StandPrem) from LCPrem where PolNo='"
				+ "?PolNo?" + "' and PayPlanCode like '000000%'";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(Sql);
		sqlbv9.put("PolNo", mLCPolSchema.getPolNo());
		logger.debug("Sql:" + Sql);
		tSSRS = tExeSQL.execSQL(sqlbv9);
		if (tSSRS.MaxCol > 0) {
			if (!(tSSRS.GetText(1, 1).equals("0")
					|| tSSRS.GetText(1, 1).trim().equals("") || tSSRS.GetText(
					1, 1).equals("null"))) {
				AddFeeFlag = true; // 加费标记为真
				AddFee = new String[4];

				AddFee[0] = MainRiskName; // 险种名称
				AddFee[1] = (new Double(mLCPolSchema.getAmnt())).toString(); // 保额
				AddFee[2] = (new Double(mLCPolSchema.getStandPrem()))
						.toString(); // 保费
				AddFee[3] = tSSRS.GetText(1, 1); // 累计加费
				tAddFeeListTable.add(AddFee);
				SumAddFee = SumAddFee + Double.parseDouble(tSSRS.GetText(1, 1)); // 加费合计
				oldSumPrem = oldSumPrem + mLCPolSchema.getStandPrem(); // 原保险费

				// lys 添加的查询出该主险的暂收费的金额
				logger.debug("lys您好，现在开始执行您的程序！！！");
				String t_sql = "select sum(paymoney) from LJTempFee where  ( OtherNo = '"
						+ "?OtherNo?"
						+ "' or OtherNo = '"
						+ "?PrtNo?"
						+ "') and RiskCode = '"
						+ "?RiskCode?" + "'";
				logger.debug("lys－您输入的t_sql语句的执行结果是" + t_sql);
				SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
				sqlbv10.sql(t_sql);
				sqlbv10.put("OtherNo", mLCPolSchema.getPolNo());
				sqlbv10.put("PrtNo", mLCPolSchema.getPrtNo());
				sqlbv10.put("RiskCode", mLCPolSchema.getRiskCode());
				SSRS t_ssrs = new SSRS();
				t_ssrs = tExeSQL.execSQL(sqlbv10);
				double temp_fee = 0;
				if (t_ssrs.getMaxRow() == 0) {
					temp_fee = 0;
				} else {
					temp_fee = Double.parseDouble(t_ssrs.GetText(1, 1));
					logger.debug("×××××××paymoney" + temp_fee);
				}
				double temp_fee_sum = Double.parseDouble(AddFee[2])
						+ Double.parseDouble(AddFee[3]) - temp_fee;
				logger.debug("暂交费的值是" + temp_fee_sum);
				if (temp_fee_sum > 0) {
					ReasonInfo1 = "您需要补交的保费金额如下";
					lys_Flag = "1";
					logger.debug("开始执行暂交费》0时的情况");
					logger.debug("原保险费是" + AddFee[2]);
					logger.debug("加费是" + AddFee[3]);
					logger.debug("实际有保户交的费用是" + temp_fee_sum);

					AddFeeInfo = new String[4]; // lys对加费的明细进行附值；
					AddFeeInfo[0] = MainRiskName; // lys对加费的主险进行附值
					AddFeeInfo[1] = (new Double(temp_fee_sum)).toString();

					logger.debug("分保费是" + temp_fee_sum);
					SumAddFeeInfo = SumAddFeeInfo + temp_fee_sum;
					logger.debug("主险的合计是" + SumAddFeeInfo);
					logger.debug("执行到开始判断合计的保费");
					logger.debug("￥￥￥￥合计的保费是" + SumAddFeeInfo);
					tAddFeeInfoListTable.add(AddFeeInfo);
				}
				if (temp_fee_sum <= 0) {
					lys_Flag_main = "1";
					logger.debug("主险中没有要打印的数据");
				}
			}
			logger.debug("2003-04-17--02-31");
		}
		// 3-2-2 取附险投保单信息(利用前面查询得到的附险投保单集合)
		for (int n = 1; n <= mLCPolSet.size(); n++) {
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = mLCPolSet.get(n);
			Sql = "select sum(StandPrem) from LCPrem where PolNo='"
					+ "?PolNo?"
					+ "' and PayPlanCode like '000000%'";
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql(Sql);
			sqlbv11.put("PolNo", tLCPolSchema.getPolNo());
			tSSRS = tExeSQL.execSQL(sqlbv11);
			if (tSSRS.MaxCol > 0) {
				if (!(tSSRS.GetText(1, 1).equals("0") || tSSRS.GetText(1, 1)
						.trim().equals(""))) {
					LMRiskDB tLMRiskDB = new LMRiskDB();
					tLMRiskDB.setRiskCode(tLCPolSchema.getRiskCode());
					if (!tLMRiskDB.getInfo()) {
						mErrors.copyAllErrors(tLMRiskDB.mErrors);
						buildError("outputXML", "在取得LMRisk的数据时发生错误");
						return false;
					}
					AddFeeFlag = true; // 加费标记为真
					AddFee = new String[4];
					AddFee[0] = tLMRiskDB.getRiskName(); // 险种名称
					AddFee[1] = (new Double(tLCPolSchema.getAmnt())).toString(); // 保额
					AddFee[2] = (new Double(tLCPolSchema.getStandPrem()))
							.toString(); // 保费
					AddFee[3] = tSSRS.GetText(1, 1); // 累计加费
					tAddFeeListTable.add(AddFee);

					logger.debug("lys您好，现在开始执行您的附件险的信息！！！");
					String t_sql_f = "select sum(paymoney) from LJTempFee where ( OtherNo = '"
							+ "?OtherNo?"
							+ "' or OtherNo = '"
							+ "?OtherNo?"
							+ "') and RiskCode = '"
							+ "?RiskCode?" + "'";
					SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
					sqlbv12.sql(t_sql_f);
					sqlbv12.put("OtherNo", tLCPolSchema.getPolNo());
					sqlbv12.put("RiskCode", tLCPolSchema.getRiskCode());
					SSRS t_ssrs_f = new SSRS();
					t_ssrs_f = tExeSQL.execSQL(sqlbv12);
					double temp_fee_f = 0;
					if (t_ssrs_f.getMaxRow() == 0) {
						logger.debug("lys附件险的暂交费的记录为空！！");
						temp_fee_f = 0;
					} else {
						logger.debug("lys附件险的暂交费的金额是"
								+ t_ssrs_f.GetText(1, 1));
						temp_fee_f = Double.parseDouble(t_ssrs_f.GetText(1, 1));
					}
					double temp_fee_f_sum = 0;
					temp_fee_f_sum = Double.parseDouble(AddFee[2])
							+ Double.parseDouble(AddFee[3]) - temp_fee_f;
					logger.debug("lys与客户之间发生的现金交易额是" + temp_fee_f_sum);

					if (temp_fee_f_sum > 0) {

						AddFeeInfo = new String[2]; // lys
						AddFeeInfo[0] = tLMRiskDB.getRiskName(); // lys
						AddFeeInfo[1] = (new Double(temp_fee_f_sum)).toString();
						logger.debug("实际要保户交的费用是" + AddFeeInfo[1]);

						tAddFeeInfoListTable.add(AddFeeInfo);
						SumAddFee = SumAddFee
								+ Double.parseDouble(tSSRS.GetText(1, 1)); // 加费合计
						SumAddFeeInfo = SumAddFeeInfo + temp_fee_f_sum;
						oldSumPrem = oldSumPrem + tLCPolSchema.getStandPrem(); // 原保险费
						lys_Flag = "1";
						lys_Flag_main = "0";
						ReasonInfo1 = "您要补交的保险费用如下:";
					}
					if (temp_fee_f_sum <= 0) {
						lys_Flag_ab = "1";
					}
					logger.debug("附件险中没有要补交的数据");
				}
			}
		}
		SumAddFee = Double.parseDouble(mDecimalFormat.format(SumAddFee)); // 转换计算后的保费(规定的精度)
		oldSumPrem = Double.parseDouble(mDecimalFormat.format(oldSumPrem)); // 转换计算后的保费(规定的精度)
		SumAddFeeInfo = Double
				.parseDouble(mDecimalFormat.format(SumAddFeeInfo));
		if (AddFeeFlag) {
			SN = SN + 1; // 序号加1
		}
		// 4-特别约定信息
		// String []SpecTitle=new String[2];
		// SpecTitle[0]= "ID";
		// SpecTitle[1] = "SpecInfo";
		String[] SpecTitle = new String[1];
		SpecTitle[0] = "SpecInfo";
		ListTable tSpecListTable = new ListTable();
		String strSpec[] = null;
		tSpecListTable.setName("SPECINFO"); // 对应模版特别约定部分的行对象名

		String t_sql = "";
		t_sql = "select SpecContent from LCSpec where PolNo= '"
				+ "?PolNo?"
				+ "' order by ModifyDate,ModifyTime desc ";
		SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
		sqlbv13.sql(t_sql);
		sqlbv13.put("PolNo", mLCPolSchema.getPolNo());
		SSRS yssrs = new SSRS();
		ExeSQL yExeSQL = new ExeSQL();
		yssrs = yExeSQL.execSQL(sqlbv13);

		if (yssrs.getMaxRow() == 0) {
			SpecFlag = false;
		} else {
			if (!((yssrs.GetText(1, 1).equals("")) || yssrs.GetText(1, 1) == null)) {
				SpecFlag = true;
				strSpec = new String[1];
				strSpec[0] = yssrs.GetText(1, 1);
				tSpecListTable.add(strSpec);
				logger.debug("特殊预定的内容是" + yssrs.GetText(1, 1));
			}
		}

		xmlexport.addListTable(tRiskListTable, RiskInfoTitle); // 保存险种信息及其标题栏

		if (ChangePolFlag == true) {
			xmlexport.addDisplayControl("displayChangePol"); // 保险计划模版上的保险计划的部分的控标记
		}
		// 保存问题信息
		if (QuestionFlag == true) {
			xmlexport.addDisplayControl("displayquestion"); // 模版上的问题部分的控制标记
			xmlexport.addListTable(tQuestionListTable, QuestionTitle); // 保存问题信息
		}
		// 保存加费信息
		if (ChangePolFlag == false) // 如果不是保险计划变更类型的核保
		{
			if (AddFeeFlag == true) {
				xmlexport.addDisplayControl("displayfee"); // 模版上的加费部分的控制标记
				xmlexport.addListTable(tAddFeeListTable, AddFeeTitle); // 保存问题信息
				if (lys_Flag.equals("1")) {
					xmlexport.addDisplayControl("displayfeeinfo");
					xmlexport.addListTable(tAddFeeInfoListTable,
							AddFeeInfoTitle);
				}
			}
		}
		// 保存特别约定
		if (SpecFlag == true) {
			xmlexport.addDisplayControl("displayspec"); // 模版上的特别约定部分的控制标记
			xmlexport.addListTable(tSpecListTable, SpecTitle); // 保存特别约定信息
		}

		// 取特约原因
		String strSpecReason = tLCCUWMasterDB.getSpecReason();

		if (strSpecReason != null && !strSpecReason.equals("")) {
			xmlexport.addDisplayControl("displayspecreason"); // 显示特约原因
			ListTable ltSpecReason = new ListTable();
			int nMaxCharsInOneLine = 40; // The max number of chars that one
											// line can contain
			int nSpecReasonLen = 0;
			String[] strArr = null;

			ltSpecReason.setName("SPECREASON");
			strSpecReason = "　　因" + strSpecReason + "，作如下特别约定：";
			nSpecReasonLen = strSpecReason.length();
			while (nSpecReasonLen > nMaxCharsInOneLine) {
				strArr = new String[1];
				strArr[0] = strSpecReason.substring(0, nMaxCharsInOneLine);

				strSpecReason = strSpecReason.substring(nMaxCharsInOneLine);
				nSpecReasonLen -= nMaxCharsInOneLine;

				ltSpecReason.add(strArr);
			}

			if (nSpecReasonLen > 0) {
				strArr = new String[1];
				strArr[0] = strSpecReason;

				ltSpecReason.add(strArr);
			}

			strArr = new String[1];
			strArr[0] = "REASON";

			xmlexport.addListTable(ltSpecReason, strArr);
		} // 取特约原因结束

		// texttag.add("SN",SN); //序号
		texttag.add("PremSum", SumPrem); // 合计保费

		if (ChangePolFlag == false) // 如果不是保险计划变更类型的核保
		{
			texttag.add("AddFeeReason", AddFeeReason); // 加费原因
			texttag.add("OldSumPrem", oldSumPrem);
			if (AddFeeFlag == true) {
				texttag.add("ReasonInfo", ReasonInfo1); // 合计加费
			}
			texttag.add("FeeInfoSum", SumAddFeeInfo);
			// 合计加费
		} else {
			// 保险计划变更后添加的元素
			texttag.add("ChangeResult", ChangeResult); // 变更后加费或退费
			texttag.add("ChangePolReason", ChangePolReason); // 变更原因
			texttag.add("SpecAddFeeSum", SpecAddFeeSum); // 加费
		}

		xmlexport.addTextTag(texttag);

		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	private boolean getOneRow(String[] cols, LCPolSchema aLCPolSchema) {
		cols[0] = getRiskName(aLCPolSchema.getRiskCode());
		cols[1] = aLCPolSchema.getPolNo();

		if (cols[0] == null || cols[1] == null) {
			return false;
		}
		// 取标准保费
		String strSQL = "SELECT SUM(Prem) FROM LCPrem WHERE" + " PolNo = '"
				+ "?PolNo?" + "'"
				+ " AND PayPlanCode NOT LIKE '000000%'";

		ExeSQL exeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		sqlbv14.sql(strSQL);
		sqlbv14.put("PolNo", aLCPolSchema.getPolNo());
		SSRS ssrs = exeSQL.execSQL(sqlbv14);

		if (exeSQL.mErrors.needDealError()) {
			mErrors.copyAllErrors(exeSQL.mErrors);
			buildError("getOneRow", "取标准保费时出错");
			return false;
		}

		cols[2] = ssrs.GetText(1, 1);

		fPremSum += Double.parseDouble(cols[2]);

		// 取加费
		strSQL = "SELECT SUM(Prem) FROM LCPrem WHERE" + " PolNo = '"
				+ "?PolNo?" + "'"
				+ " AND PayPlanCode NOT LIKE '000000%'";
		SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
		sqlbv15.sql(strSQL);
		sqlbv15.put("PolNo", aLCPolSchema.getPolNo());
		exeSQL = new ExeSQL();

		ssrs = exeSQL.execSQL(sqlbv15);

		if (exeSQL.mErrors.needDealError()) {
			mErrors.copyAllErrors(exeSQL.mErrors);
			buildError("getOneRow", "取加费时出错");
			return false;
		}

		cols[3] = ssrs.GetText(1, 1);

		fPremAddSum += Double.parseDouble(cols[3]);

		double fSum = Double.parseDouble(cols[2]) + Double.parseDouble(cols[3]);

		cols[4] = String.valueOf(fSum);
		return true;
	}

	private String getRiskName(String strRiskCode) {
		LMRiskDB tLMRiskDB = new LMRiskDB();
		tLMRiskDB.setRiskCode(strRiskCode);
		if (!tLMRiskDB.getInfo()) {
			mErrors.copyAllErrors(tLMRiskDB.mErrors);
			buildError("getOneRow", "在取得主险LMRisk的数据时发生错误");
			return null;
		}
		return tLMRiskDB.getRiskName();
	}
}
