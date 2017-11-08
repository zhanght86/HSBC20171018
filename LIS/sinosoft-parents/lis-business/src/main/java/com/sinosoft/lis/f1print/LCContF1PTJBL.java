/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1print;
import java.io.File;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;
import org.jdom.Document;

import com.sinosoft.lis.bq.EdorCalZT;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LABranchGroupSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.schema.LDContInvoiceMapSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XMLDatasets;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * ClassName: LCContF1PTJBL
 * </p>
 * <p>
 * Description: 保单xml文件生成，数据准备类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @Database: LIS
 * @CreateDate：2002-11-04
 */
public class LCContF1PTJBL implements PrintService {
private static Logger logger = Logger.getLogger(LCContF1PTJBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private File mFile = null;
	private MMap map = new MMap();
	private MMap map1 = new MMap();

	// 操作流转控制变量
	private String mOperate = "";
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCAppntSchema mLCAppntSchema = new LCAppntSchema();
	private String flag2 = "0"; // 是否具有受益人标志
	private String flag8 = "0";
	private String mainInsuredno = ""; // 主险被保险人
	private String mainRiskcode = "";
	private String insuredName = ""; // 被保险人姓名
	private String insuredSex = ""; // 被保险人性别
	private String insuredIDType = "";
	private String insuredIDNo = "";
	private String insuredRelation = "";
	private String insuredOccupation = "";
	private String insuredRgtAddress = "";
	private String insuredWork = "";
	private String insuredRemark = ""; // 被保险人备注
	private String manageCom = "";
	private String manageComName = "";
	private LCAddressSchema mLCAddressSchema = new LCAddressSchema();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();
	private LABranchGroupSchema mLABranchGroupSchema = new LABranchGroupSchema();
	private LDComSchema mLDComSchema = new LDComSchema();

	// 扫描件集合
	// private VData mEasyScan = new VData();

	private String mTemplatePath = null;
	private String mOutXmlPath = null;
	private XMLDatasets mXMLDatasets = null;
	private String[][] mScanFile = null;
	private XmlExport xmlexport = new XmlExport();
	private XmlExport xmlexportAll = new XmlExport();
	private XmlExport xmlexport1 = new XmlExport();
	// private TextTag mTextTag = new TextTag();
	private TextTag mTextTag1 = new TextTag();
	// private TextTag mTextTag2 = new TextTag();
	private TextTag mTextTag3 = new TextTag();
	private Document myDocument; // add by yaory

	private String startNo = "";// 发票号
	private String prtSeq = "";// 打印批次号

	public LCContF1PTJBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 主程序
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			// 判定打印类型
			if (!cOperate.equals("PRINT") && !cOperate.equals("REPRINT")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}
			// 全局变量赋值
			mOperate = cOperate;
			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}

			// 正常打印处理
			if (mOperate.equals("PRINT")) {
				// 新建一个xml对象
				mXMLDatasets = new XMLDatasets();
				mXMLDatasets.createDocument();
				mResult.clear();
				// 数据准备
				if (!getPrintData()) {
					return false;
				}
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submit", ex.getMessage());
			return false;
		}
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean 如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 获取数据的时候不需要区分打印模式
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLCContSchema.setSchema((LCContSchema) cInputData
				.getObjectByObjectName("LCContSchema", 0));
		TransferData tTD = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (tTD == null) {
			buildError("getInputData", "传入数据不足！");
			return false;
		}
		prtSeq = (String) tTD.getValueByName("ContPrtSeq");
		startNo = (String) tTD.getValueByName("StartNo");
		mOutXmlPath = (String) tTD.getValueByName("RealPath");
		flag8 = (String) tTD.getValueByName("Individ");
		if (flag8 == null || flag8.trim().equals(""))
			flag8 = "0";
		return true;
	}

	/**
	 * 返回信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 出错处理
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LCContF1PTJBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 正常打印处理
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	private boolean getPrintData() throws Exception {
		// 个单Schema
		LCContSchema tLCContSchema = null;
		// 原则上一次只打印一个个单，当然可以一次多打
		tLCContSchema = this.mLCContSchema;

		// 取得该个单下的所有投保险种信息
		LCPolSet tLCPolSet = getRiskList(tLCContSchema.getContNo());

		// 正常打印流程，目前只关心这个
		if (!getPolDataSet(tLCPolSet)) {
			return false;
		}

		// 将准备好的数据，放入mResult对象，传递给BLS进行后台操作
		this.mLCContSchema.setPrintCount(1);
		this.mLCContSchema.setModifyDate(PubFun.getCurrentDate());
		this.mLCContSchema.setModifyTime(PubFun.getCurrentTime());
		mResult.addElement(xmlexport);

		// 更新LCGrpCont表数据
		String mContNo = mLCContSchema.getContNo();
		LDContInvoiceMapSchema tLDMapSchema = new LDContInvoiceMapSchema();
		tLDMapSchema.setBatchNo(prtSeq);
		tLDMapSchema.setContNo(mContNo);
		tLDMapSchema.setInvoiceNo(startNo);
		tLDMapSchema.setMakeDate(PubFun.getCurrentDate());
		tLDMapSchema.setMakeTime(PubFun.getCurrentTime());
		tLDMapSchema.setModifyDate(PubFun.getCurrentDate());
		tLDMapSchema.setModifyTime(PubFun.getCurrentTime());
		String insSql = "insert into ldcontinvoicemap (contno,invoiceno,batchno,makedate,maketime,modifydate,modifytime,operator,opertype)"
				+ " values('"
				+"?mContNo?"
				+ "','"
				+ "?startNo?"
				+ "','"
				+ "?prtSeq?"
				+ "','"
				+"?date?"
				+ "',"
				+ "'"
				+ "?time?"
				+ "','"
				+"?date?"
				+ "','"
				+ "?time?"
				+ "','"
				+ "?Operator?" + "','1')";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(insSql);
		sqlbv1.put("mContNo", mContNo);
		sqlbv1.put("startNo", startNo);
		sqlbv1.put("prtSeq", prtSeq);
		sqlbv1.put("date", PubFun.getCurrentDate());
		sqlbv1.put("time", PubFun.getCurrentTime());
		sqlbv1.put("Operator", mGlobalInput.Operator);
		map.put(sqlbv1, "INSERT");
		// PubSubmit tPubSubmit = new PubSubmit();
		// VData tInputData = new VData();
		// tInputData.add(map);
		// tPubSubmit.submitData(tInputData,"");
		mResult.add(map);

		return true;
	}

	/**
	 * 正常打印流程
	 * 
	 * @param cLCPolSet
	 *            LCPolSet
	 * @return boolean
	 * @throws Exception
	 */
	private boolean getPolDataSet(LCPolSet cLCPolSet) throws Exception {
		// xml对象
		EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);
		String sumprem = "";
		// 查询个单合同表信息
		LCContDB tLCContDB = new LCContDB();
		// 根据合同号查询
		tLCContDB.setContNo(this.mLCContSchema.getContNo());
		logger.debug("个单合同号======" + this.mLCContSchema.getContNo());
		// 如果查询失败，无论是查询为空，还是真正的查询失败，则返回
		if (!tLCContDB.getInfo()) {
			buildError("getInfo", "查询个单合同信息失败！");
			return false;
		}
		this.mLCContSchema = tLCContDB.getSchema();
		insuredRemark = mLCContSchema.getRemark();
		// /////////以下发票信息//////////////
		manageCom = mLCContSchema.getManageCom().substring(0, 4); // 管理机构前四位

		String currentDate = PubFun.getCurrentDate();
		String isGrp = "";
		if (mLCContSchema.getContType().equals("1")) {
			isGrp = "0";
		} else {
			isGrp = "1";
		}
		String agentcode = mLCContSchema.getAgentCode();

		ExeSQL tExeSQL = new ExeSQL();
		String tSql = "select name,branchtype from laagent where agentcode='"
				+ "?agentcode?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("agentcode", agentcode);
		SSRS pSSRS = new SSRS();
		String agentname = "";
		String department = "";
		String branchtype = "";
		pSSRS = tExeSQL.execSQL(sqlbv2);
		if (pSSRS.MaxRow == 0) {
			buildError("getInfo", "查询代理人信息失败！");
			return false;
		} else {
			agentname = pSSRS.GetText(1, 1);
			branchtype = pSSRS.GetText(1, 2);
		}
		if (branchtype != null && branchtype.trim().equals("3")) {
			tSql = "select a.name From LABranchGroup a,laagent c where "
					+ " a.agentgroup = c.agentgroup and c.agentcode = '"
					+ "?agentcode?" + "'";
		} else {
			tSql = "select a.name From LABranchGroup a,labranchgroup b,laagent c where a.agentgroup= b.upbranch"
					+ " and b.agentgroup = c.branchcode and c.agentcode = '"
					+ "?agentcode?" + "'";
		}
		tSql = "select name From Ldcom where comcode='"
				+ "?comcode?" + "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSql);
		sqlbv3.put("agentcode", agentcode);
		sqlbv3.put("comcode", mLCContSchema.getManageCom());
		pSSRS = tExeSQL.execSQL(sqlbv3);
		if (pSSRS.MaxRow == 0) {
			buildError("getInfo", "查询机构信息失败！");
			return false;
		} else {
			manageComName = pSSRS.GetText(1, 1);
		}

		// 查询投保人信息
		LCAppntDB tLCAppntDB = new LCAppntDB();
		// 根据合同号查询
		tLCAppntDB.setContNo(tLCContDB.getContNo());
		// 如果查询失败，无论是查询为空，还是真正的查询失败，则返回
		if (!tLCAppntDB.getInfo()) {
			buildError("getInfo", "查询个单投保人信息失败！");
			return false;
		}
		this.mLCAppntSchema = tLCAppntDB.getSchema();

		// 查询polno
		String ppsql = "", signDateSql = "";

		ExeSQL exeSql = new ExeSQL();
		SSRS tSSRS = new SSRS(); // 临时的，谁偶可以用
		SSRS xSSRS = new SSRS(); // 基本险专用
		SSRS mSSRS = new SSRS(); // 连带被保险人专用
		SSRS bSSRS = new SSRS(); // 受益人专用
		SSRS signSSRS = new SSRS(); // 受益人专用

		String salechnl = ""; // 险种类型

		ppsql = "select a.polno,a.insuredno,a.riskcode,a.InsuYear,a.InsuYearFlag,floor(Months_between(a.CValiDate,a.InsuredBirthday)/12),a.payintv,a.payyears,concat(trim(a.standbyflag1),'%'),case b.mngcom when 'B' then '3' when 'I' then '1' when 'G' then '2' when 'T' then '5' end from lcpol a,lmriskapp b where a.contno='"
				+"?contno?"
				+ "' and a.polno=a.mainpolno and a.appflag = '1' and b.riskcode = a.riskcode";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(ppsql);
		sqlbv4.put("contno", tLCContDB.getContNo());
		tSSRS = exeSql.execSQL(sqlbv4);
		if (tSSRS.MaxRow == 0) {
			buildError("getInfo", "查询个单险种信息失败！");
			return false;
		} else {
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql("select * from lcpol where contno='"
					+"?contno?"
					+ "' and polno=mainpolno and appflag = '1'");
			sqlbv5.put("contno", tLCContDB.getContNo());
			xSSRS = exeSql.execSQL(sqlbv5);
		}
		salechnl = tSSRS.GetText(1, 10);

		// 定义主险LCPOL的POLNO
		String mainPolno = tSSRS.GetText(1, 1);
		mainInsuredno = tSSRS.GetText(1, 2); // 主险被保险人
		mainRiskcode = tSSRS.GetText(1, 3);

		ListTable tListTableURL = null;
		String strURL[] = null;
		String strURLTitle[] = null;
		ppsql = "select (select b.serverport from es_server_info b where b.hostname = a.hostname),a.picpath , a.pagename from es_doc_pages a where a.docid in(select docid from es_doc_relation ";
		ppsql += "where bussnotype = '11' and busstype = 'TB'  and subtype in ('UA001','UA006','UA081','UA013','UN001','UN002','UN015','UN017','UN018','UN019','UN020','UN021','UN022','UN016') ";
		ppsql += "and bussno = '" +"?bussno?"
				+ "') order by char_Length(a.pagename),a.pagename";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(ppsql);
		sqlbv6.put("bussno", mLCContSchema.getProposalContNo());
		tSSRS = exeSql.execSQL(sqlbv6);
		if (tSSRS.MaxRow > 0) {
			tListTableURL = new ListTable();
			tListTableURL.setName("URL"); // 对应模版部分的行对象名
			strURLTitle = new String[1];
			strURLTitle[0] = "影像路径";
			String hostname = "";
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				strURL = new String[1];
				hostname = tSSRS.GetText(i, 1);
				if (hostname.indexOf("/") == -1)
					hostname += "/";
				strURL[0] = "http://" + hostname + tSSRS.GetText(i, 2)
						+ tSSRS.GetText(i, 3) + ".tif";
				tListTableURL.add(strURL);
			}
		}

		// 查询被保险人信息
		ppsql = "Select a.Name, a.Sex, a.Idtype, a.Idno, b.Codename,a.OccupationCode,c.postaladdress ,a.Relationtoappnt ";
		ppsql += "From Lcinsured a, Ldcode b,lcaddress c ";
		ppsql += " where a.Contno='"
				+"?Contno?"
				+ "' and sequenceno='1' And a.Relationtoappnt = b.Code And b.Codetype = 'relation'";
		ppsql += " and c.customerno = a.insuredno and c.addressno = a.addressno";
		// 根据合同号查询
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(ppsql);
		sqlbv7.put("Contno", mLCContSchema.getContNo());
		mSSRS = exeSql.execSQL(sqlbv7);
		if (mSSRS.MaxRow > 0) {
			insuredName = mSSRS.GetText(1, 1);
			insuredSex = mSSRS.GetText(1, 2);
			insuredIDType = mSSRS.GetText(1, 3);
			insuredIDNo = mSSRS.GetText(1, 4);
			insuredRelation = mSSRS.GetText(1, 5);
			insuredOccupation = mSSRS.GetText(1, 6);
			insuredRgtAddress = mSSRS.GetText(1, 7);
		}
		// 被保险人职业
		SSRS ldSSRS = new SSRS(); //
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql("Select occupationname From ldoccupation Where  occupationcode='"
				+ "?insuredOccupation?" + "'");
		sqlbv8.put("insuredOccupation", insuredOccupation);
		ldSSRS = exeSql.execSQL(sqlbv8);
		if (ldSSRS.MaxRow == 0) {
			logger.debug("查询被保险人职业信息失败！");
			insuredWork = "";
		} else {
			insuredWork = ldSSRS.GetText(1, 1);
		}

		// 出单日期
		signDateSql = "Select LCCont.SignDate From lccont Where contno='"
				+ "?contno?" + "'";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(signDateSql);
		sqlbv9.put("contno", tLCContDB.getContNo());
		signSSRS = exeSql.execSQL(sqlbv9);
		if (signSSRS.MaxRow == 0) {
			logger.debug("查询出单日期信息失败！");
		}

		// 查询受益人
		ppsql = "select a.Name,c.codeName,a.IDNo,a.BnfGrade,a.BnfLot,b.name from lcbnf a,lcinsured b ,ldcode c  where a.contno='"
				+"?contno?"
				+ "' and a.polno='"
				+ "?mainPolno?"
				+ "' and a.insuredno='"
				+ "?mainInsuredno?"
				+ "' and a.insuredno = b.insuredno and b.contno = '"
				+ "?contno?"
				+ "' And a.RelationToInsured=c.code And c.Codetype = 'relation'";
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql(ppsql);
		sqlbv10.put("contno", tLCContDB.getContNo());
		sqlbv10.put("mainPolno", mainPolno);
		sqlbv10.put("mainInsuredno", mainInsuredno);
		bSSRS = exeSql.execSQL(sqlbv10);
		if (bSSRS.MaxRow == 0) {
			logger.debug("查询受益人信息失败！");
			flag2 = "1";
		}

		// xmlexportAll.createDocuments("",mGlobalInput);
		// xmlexportAll.setTemplateName("TJdbtJxk.vts");
		xmlexport.createDocument("TJdbtJxk.vts", "");
		xmlexport.addControlItem("CONTROL", "RiskNo", mainRiskcode);
		xmlexport.addControlItem("CONTROL", "IsGrp", isGrp);

		TextTag mTextTag = new TextTag();
		// 投保人
		mTextTag.add("LCCont.ContNo", mLCAppntSchema.getContNo());// NO.
		// mTextTag.add("CardNo", ???????????);//卡号
		mTextTag.add("LCCont.AppntName", mLCAppntSchema.getAppntName());
		mTextTag.add("LCInsured.RelationToMainInsured", insuredRelation);// ??????????????????????????
		mTextTag.add("LCCont.AppntIDNo", (mLCAppntSchema.getIDType()
				.equals("9") ? mLCAppntSchema.getAppntBirthday()
				: mLCAppntSchema.getIDNo()));// ////////////////
		logger.debug("投保人的证件号码====" + mLCAppntSchema.getIDNo());

		// 被保险人
		mTextTag.add("LCCont.InsuredName", insuredName);
		insuredSex = getSex(insuredSex); // 被保险人性别insuAge
		mTextTag.add("LCCont.InsuredSex", insuredSex);
		mTextTag.add("LCCont.InsuredIDNo",
				(insuredIDType.equals("9") ? mLCContSchema.getInsuredBirthday()
						: insuredIDNo));
		mTextTag.add("LCInsured.OccupationType", insuredWork);// ??????????????????????????????????????
		mTextTag.add("LCInsured.RgtAddress", insuredRgtAddress);// ??????????????????????????????????????

		// 受益人
		if (!flag2.equals("1")) {
			mTextTag.add("Lcbnf.Name", bSSRS.GetText(1, 1));
			mTextTag.add("Ldcode.Codename", bSSRS.GetText(1, 2));
			mTextTag.add("Lcbnf.Idno", bSSRS.GetText(1, 3));

		} else {
			logger.debug("没有主险的受益人信息！");
			mTextTag.add("Lcbnf.Name", "法定继承人");
			mTextTag.add("Ldcode.Codename", "");
			mTextTag.add("Lcbnf.Idno", "");
		}
		// if (mTextTag.size() > 0)
		// {
		// xmlexport.addTextTag(mTextTag);
		// //mTextTag=null;
		// }

		// 基本险
		String tInsureAmnt = PubFun.getChnMoney(
				parseFloat(xSSRS.GetText(1, 59))).replaceAll("元整", "份");
		mTextTag.add("InsureAmnt", tInsureAmnt);
		mTextTag.add("CPrem", PubFun.getChnMoney(parseFloat(xSSRS
				.GetText(1, 61))));// ///////////////////////////
		mTextTag.add("LCCont.Prem", xSSRS.GetText(1, 61));// //////////////////////////////////
		mTextTag.add("InsureTerm", getInsureTerm(xSSRS.GetText(1, 39), xSSRS
				.GetText(1, 31), xSSRS.GetText(1, 46)));// ///////////////

		mTextTag.add("LCCont.SignDate", StrTool.getChnDate(PubFun
				.getCurrentDate()));
		if (mTextTag.size() > 0) {
			xmlexport.addTextTag(mTextTag);
			// mTextTag1=null;
		}
		TextTag mTextTag2 = new TextTag();
		mTextTag2.add("Invoice", " ");
		xmlexport.addTextTag(mTextTag2);
		String CurrentTime = PubFun.getCurrentDate();
		String SysYear = CurrentTime.substring(0, 4);
		String SysMonth = CurrentTime.substring(5, 7);
		String SysDay = CurrentTime.substring(8, 10);
		xmlexport.addControlItem("Invoice", "InvoiceCode", "");// 发票代码
		xmlexport.addControlItem("Invoice", "InvoiceNo", startNo);
		xmlexport.addControlItem("Invoice", "SysYear", SysYear);
		xmlexport.addControlItem("Invoice", "SysMonth", SysMonth);
		xmlexport.addControlItem("Invoice", "SysDay", SysDay);
		xmlexport.addControlItem("Invoice", "LCCont.AppntName", mLCAppntSchema
				.getAppntName());
		xmlexport.addControlItem("Invoice", "CPrem", PubFun
				.getChnMoney(parseFloat(xSSRS.GetText(1, 61))));// ///////////////////////////
		xmlexport.addControlItem("Invoice", "LAAgent.Name", agentname);
		xmlexport.addControlItem("Invoice", "LAAgent.AgentCode", agentcode);

		// 扫描影像
		if (tListTableURL != null) {
			logger.debug("扫描影像++++++++++++++++ADD!");
			xmlexport.addListTable(tListTableURL, strURLTitle);
			xmlexport.addDisplayControl("displayURL");
		}

		// xmlexportAll.addDataSet(xmlexportAll.getDocument().getRootElement(),
		// xmlexport.getDocument().getRootElement());
		//
		// xmlexportAll.addDataSet(xmlexportAll.getDocument().getRootElement(),
		// xmlexport.getDocument().getRootElement());
		// xmlexportAll.addDataSet(xmlexportAll.getDocument().getRootElement(),
		// xmlexport.getDocument().getRootElement());
		// xmlexportAll.addDataSet(xmlexportAll.getDocument().getRootElement(),
		// xmlexport.getDocument().getRootElement());
		// //mTextTag2=null;
		//
		// /**xmlexport将所得数据流入下面目录下的一XML文件中*/
		// xmlexportAll.outputDocumentToFile("d:\\",
		// "TJdbtJxk"+PubFun.getCurrentTime().toString().replaceAll(":","_"));
		/** 将xmlexport添加到mResult中，以供以后调用 */
		mResult.addElement(xmlexport);
		logger.debug("end");

		return true;
	}

	private String getIdtype(String s) {
		String returnValue = "";
		if (s.trim().equals("9"))
			return returnValue;
		ExeSQL exeSql = new ExeSQL();
		SSRS tSSRS = new SSRS();
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql("select codename from ldcode where code='" + "?s?"
				+ "' and codetype='idtype'");
		sqlbv11.put("s", s);
		tSSRS = exeSql.execSQL(sqlbv11);
		if (tSSRS.MaxRow == 0) {
			buildError("submitData", "没有查询到对应的证件类型！");

		} else {
			returnValue = tSSRS.GetText(1, 1) + "：";
		}

		return returnValue;
	}

	private String getInsureTerm(String s1, String s2, String s3) {
		String returnValue = "";
		String tDate = s2;
		// String date3 = PubFun.calDate(s2,Integer.parseInt(s1),"Y","");
		// date3 = PubFun.calDate(s2, -1, "D", "");
		tDate = StrTool.getChnDate(tDate);
		if (!s3.equals("1000")) {
			// returnValue = PubFun.calDate(s2, Integer.parseInt(s1), "Y", "");
			returnValue = PubFun.calDate(s1, -1, "D", "");
			returnValue = StrTool.getChnDate(returnValue);
			returnValue = tDate + "至" + returnValue;
		} else {
			returnValue = tDate + "至 终身";
		}
		return returnValue;

	}

	private String getAddress(String s) {
		String returnValue = "";
		ExeSQL exeSql = new ExeSQL();
		SSRS tSSRS = new SSRS();
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		sqlbv12.sql("select address from ldcom where comcode = (select upcomcode from ldcom where comcode ='"
				+ "?s?" + "')");
		sqlbv12.put("s", s);
		tSSRS = exeSql.execSQL(sqlbv12);
		if (tSSRS.MaxRow == 0) {
			// buildError("submitData", "没有查询到对应的数据！");

		} else {
			returnValue = tSSRS.GetText(1, 1);
		}

		return returnValue;

	}

	private String getSex(String s) {
		if (s == null || s.trim().equals(""))
			return "";
		String returnValue = "";
		ExeSQL exeSql = new ExeSQL();
		SSRS tSSRS = new SSRS();
		SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
		sqlbv13.sql("select codename from ldcode where code='" + "?s?"
				+ "' and codetype='sex'");
		sqlbv13.put("s", s);
		tSSRS = exeSql.execSQL(sqlbv13);
		if (tSSRS.MaxRow == 0) {
			// buildError("submitData", "没有查询到对应的数据！");

		} else {
			returnValue = tSSRS.GetText(1, 1);
		}

		return returnValue;

	}

	/**
	 * 取得个单下的全部LCPol表数据
	 * 
	 * @param cContNo
	 *            String
	 * @return LCPolSet
	 * @throws Exception
	 */
	private static LCPolSet getRiskList(String cContNo) throws Exception {
		// 查询返回容器
		LCPolSet tLCPolSet = new LCPolSet();
		LCPolDB tLCPolDB = new LCPolDB();
		// 根据个单合同查询个单险种明细信息，对家庭单而言，会发现lcpol比较多，需要如何处理？？？
		tLCPolDB.setContNo(cContNo);
		tLCPolDB.setAppFlag("1");
		tLCPolSet = tLCPolDB.query();
		// 返回个单险种集合
		return tLCPolSet;
	}

	private float parseFloat(String s) {
		float f1 = 0;
		String tmp = "";
		String s1 = "";
		for (int i = 0; i < s.length(); i++) {
			s1 = s.substring(i, i + 1);
			if (s1.equals("0") || s1.equals("1") || s1.equals("2")
					|| s1.equals("3") || s1.equals("4") || s1.equals("5")
					|| s1.equals("6") || s1.equals("7") || s1.equals("8")
					|| s1.equals("9") || s1.equals(".")) {
				tmp = tmp + s1;
			} else if (tmp.length() > 0) {
				break;
			}
		}
		f1 = Float.parseFloat(tmp);
		// logger.debug("tmp:"+tmp+" f1:"+f1);
		return f1;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 格式化浮点型数据
	 * 
	 * @param dValue
	 *            double
	 * @return String
	 */
	private static String format(double dValue) {
		return new DecimalFormat("0.00").format(dValue);
	}

	public static void main(String[] args) {
		VData tVData = new VData();
		GlobalInput tg = new GlobalInput();
		tg.ManageCom = "86";
		tg.ComCode = "86";
		tg.Operator = "001";
		TransferData tTD = new TransferData();
		tTD.setNameAndValue("ContPrtSeq", "111");
		tTD.setNameAndValue("StartNo", "1212");
		tTD.setNameAndValue("RealPath", "a");
		LCContSchema tlc = new LCContSchema();
		tlc.setContNo("000006740947");
		tVData.add(tg);
		tVData.add(tlc);
		tVData.add(tTD);
		LCContF1PTJBL tLCContF1PTJBL = new LCContF1PTJBL();
		if (!tLCContF1PTJBL.submitData(tVData, "PRINT")) {
			logger.debug("Wrong!");
		} else {
			logger.debug("OK!");
		}
		// if(!tLCContF1PTJBL.submitData(tVData, "PRINT"))
		// {
		// logger.debug("Wrong!");
		// }
		// else
		// {
		// logger.debug("OK!");
		// }

		XmlExport xmlexportAll = new XmlExport();
		xmlexportAll.createDocuments("", tg);
		xmlexportAll.setTemplateName("TJdbtJxk.vts");
		VData mResult = tLCContF1PTJBL.getResult();
		XmlExport xmlexport = (XmlExport) mResult.getObjectByObjectName(
				"XmlExport", 0);
		// VData mResult1=tLCContF1PTJBL.getResult();
		// XmlExport xmlexport1=(XmlExport)
		// mResult1.getObjectByObjectName("XmlExport",0);
		xmlexportAll.addDataSet(xmlexportAll.getDocument().getRootElement(),
				xmlexport.getDocument().getRootElement());
		// xmlexportAll.addDataSet(xmlexportAll.getDocument().getRootElement(),
		// xmlexport1.getDocument().getRootElement());

		// /**xmlexport将所得数据流入下面目录下的一XML文件中*/
		// xmlexportAll.outputDocumentToFile("d:\\",
		// "TJdbtJxk"+PubFun.getCurrentTime().toString().replaceAll(":","_"));

	}

	private void jbInit() throws Exception {
	}

}
