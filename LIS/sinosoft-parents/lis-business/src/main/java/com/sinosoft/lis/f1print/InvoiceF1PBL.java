/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1print;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.pubfun.EasyScanQueryBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CBlob;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XMLDataList;
import com.sinosoft.utility.XMLDataMine;
import com.sinosoft.utility.XMLDataTag;
import com.sinosoft.utility.XMLDataset;
import com.sinosoft.utility.XMLDatasets;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * ClassName: LCContF1PBL
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
public class InvoiceF1PBL implements PrintService {
private static Logger logger = Logger.getLogger(InvoiceF1PBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private File mFile = null;

	// 操作流转控制变量
	private String mOperate = "";
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCAppntSchema mLCAppntSchema = new LCAppntSchema();
	private String manageCom = "";
	private String mTemplatePath = null;
	private String mOutXmlPath = null;
	private XMLDatasets mXMLDatasets = null;
	private String[][] mScanFile = null;
	private XmlExport xmlexport1 = new XmlExport();
	private TextTag mTextTag1 = new TextTag();
	private TransferData mTransferData = new TransferData();

	public InvoiceF1PBL() {
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
				// 数据准备
				if (!getPrintData()) {
					return false;
				}
			}
			// 重打处理
			if (mOperate.equals("REPRINT")) {
				if (!getRePrintData()) {
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
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		mTemplatePath = "";
		mOutXmlPath = "";
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
		cError.moduleName = "LCContF1PBL";
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
		mResult.add(this.mLCContSchema); // 只更新这个表就好拉
		mResult.add(mXMLDatasets);
		mResult.add(mGlobalInput);
		mResult.add(mOutXmlPath);
		// mResult.add(xmlexport); //add by yaory
		mResult.add(xmlexport1); // add by yaory
		mResult.add(mScanFile);

		// 现在直接在后台将xml流写入到文件，因此可以不需要将xml数据流返回到前台
		// 返回数据容器清空
		// mResult.clear();
		// logger.debug("add inputstream to mResult");
		// 将xml信息放入返回容器
		// mResult.add(mXMLDatasets.getInputStream());
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
		// insuredRemark = mLCContSchema.getRemark();
		// /////////以下发票信息//////////////
		manageCom = mLCContSchema.getManageCom().substring(0, 4); // 管理机构前四位
		String currentDate = PubFun.getCurrentDate();

		String agentcode = mLCContSchema.getAgentCode();

		String agentname = "";
		String department = "";
		String mngCom = "";// 保单销售渠道，“T”时表示直销
		// 发票中原来输出“业务员代码”、“业务员姓名”的地方，对直销要输出“直销员代码”、“直销员姓名”。
		String agentCodeType = "业务员代码";
		String nameType = "业务员姓名";
		// 查询polno
		String ppsql = "";
		ExeSQL exeSql = new ExeSQL();
		SSRS tSSRS = new SSRS(); // 临时的，谁偶可以用
		SSRS xSSRS = new SSRS(); // 基本险专用
		String salechnl = "";
		String mMainPayTerm = "";// 主险缴费期限

		ppsql = "select a.polno,a.insuredno,a.riskcode,a.InsuYear,a.InsuYearFlag,floor(Months_between(a.CValiDate,a.InsuredBirthday)/12),a.payintv,a.payyears,concat(trim(a.standbyflag1),'%'),case b.mngcom when 'B' then '3' when 'I' then '1' when 'G' then '2' when 'T' then '5' end,payendyearflag,payendyear,paymode,cvalidate,payenddate from lcpol a,lmriskapp b where a.contno='"
				+ "?contno?"
				+ "' and a.polno=a.mainpolno and a.appflag = '1' and b.riskcode = a.riskcode";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(ppsql);
		sqlbv1.put("contno", tLCContDB.getContNo());
		tSSRS = exeSql.execSQL(sqlbv1);
		if (tSSRS.MaxRow == 0) {
			buildError("getInfo", "查询个单险种信息失败！");
			return false;
		} else {
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql("select * from lcpol where contno='"
					+ "?contno?"
					+ "' and polno=mainpolno and appflag = '1'");
			sqlbv2.put("contno", tLCContDB.getContNo());
			xSSRS = exeSql.execSQL(sqlbv2);
		}
		salechnl = tSSRS.GetText(1, 10);
		mMainPayTerm = getPayTerm(xSSRS.GetText(1, 12), xSSRS.GetText(1, 31),
				xSSRS.GetText(1, 36), xSSRS.GetText(1, 53), xSSRS
						.GetText(1, 44), xSSRS.GetText(1, 43));
		ListTable tListTableInvoice = new ListTable();
		tListTableInvoice.setName("RISK");
		String invoiceRisk[] = new String[7];
		invoiceRisk[0] = "险种：";
		invoiceRisk[1] = xSSRS.GetText(1, 12) + "  保费";
		invoiceRisk[2] = format(Double.parseDouble(xSSRS.GetText(1, 61)));
		invoiceRisk[3] = xSSRS.GetText(1, 12);
		invoiceRisk[4] = "保费";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql("Select riskshortname From lmrisk Where riskcode='"
				+ "?riskcode?" + "'");
		sqlbv3.put("riskcode", xSSRS.GetText(1, 12));
		invoiceRisk[5] = exeSql.getOneValue(sqlbv3);
		// 交费起止日期根据李\u73A5要求修改，发票上主险缴费起止日期与保单上缴费期限一致
		invoiceRisk[6] = mMainPayTerm;// getPayTerm(tSSRS.GetText(1,14),tSSRS.GetText(1,15));
		String PayTerm = mMainPayTerm;// getChnPayTerm(tSSRS.GetText(1,14),tSSRS.GetText(1,15));
		tListTableInvoice.add(invoiceRisk);

		// 主险名称
		String RNSql = "Select riskname,riskshortname,riskstatname From lmrisk Where riskcode='"
				+ "?riskcode?" + "'";
		SSRS tSSRS1 = new SSRS();
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(RNSql);
		sqlbv4.put("riskcode", tSSRS.GetText(1, 3));
		tSSRS1 = exeSql.execSQL(sqlbv4);
		String maipolName = tSSRS1.GetText(1, 1);
		String maipolName1 = tSSRS1.GetText(1, 2);
		String maipolName2 = tSSRS1.GetText(1, 3);

		// 交费方式
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql("Select codename From ldcode Where codetype='payintv' And code='"
				+ "?code?" + "'");
		sqlbv5.put("code", tSSRS.GetText(1, 7));
		String payIntvName = exeSql.getOneValue(sqlbv5);

		// 收费形式
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql("Select codename From ldcode Where codetype='paymode' And code='"
				+ "?code?" + "'");
		sqlbv6.put("code", tLCContDB.getPayLocation());
		String payStyleName = exeSql.getOneValue(sqlbv6);

		// 交费期间(年/岁)
		String payEnd = "";
		if (tSSRS.GetText(1, 11).equals("Y"))
			payEnd = tSSRS.GetText(1, 12) + "年";
		else if (tSSRS.GetText(1, 11).equals("A"))
			payEnd = tSSRS.GetText(1, 12) + "岁";

		// 收款员
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql("select operator from ljtempfee where OtherNo='"
				+ "?OtherNo?"
				+ "'and tempfeetype='1' and (othernotype = '6' or othernotype = '7' or othernotype = '0') and riskcode='"
				+ "?riskcode?" + "' order by paydate");
		sqlbv7.put("OtherNo", tLCContDB.getContNo());
		sqlbv7.put("riskcode", xSSRS.GetText(1, 12));
		String oper = exeSql.getOneValue(sqlbv7);

		// 营业单位
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql("Select Name From ldcom Where comcode='"
				+ "?comcode?" + "'");
		sqlbv8.put("comcode", mLCContSchema.getManageCom().substring(0, 6));
		String managecomName = exeSql.getOneValue(sqlbv8);

		// 附加险
		ppsql = "select riskcode,prem,cvalidate,payenddate,payintv from lcpol where contno='"
				+ "?contno?"
				+ "' and polno!=mainpolno and appflag = '1'";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(ppsql);
		sqlbv9.put("contno", tLCContDB.getContNo());
		SSRS appSSRS = exeSql.execSQL(sqlbv9);
		for (int i = 1; i <= appSSRS.getMaxRow(); i++) {
			invoiceRisk = new String[7];
			invoiceRisk[0] = "险种：";
			invoiceRisk[1] = appSSRS.GetText(i, 1) + "  保费";
			invoiceRisk[2] = format(Double.parseDouble(appSSRS.GetText(i, 2)));
			invoiceRisk[3] = appSSRS.GetText(i, 1);
			invoiceRisk[4] = "保费";
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql("Select riskshortname From lmrisk Where riskcode='"
					+ "?riskcode?" + "'");
			sqlbv10.put("riskcode", appSSRS.GetText(i, 1));
			invoiceRisk[5] = exeSql.getOneValue(sqlbv10);
			if (appSSRS.GetText(i, 5) != null
					&& appSSRS.GetText(i, 5).trim().equals("0")) {// 根据李\u73A5要求修改，附加险如果是趸交，则其发票上交费起止日期与保单上主险缴费期限一致
				invoiceRisk[6] = mMainPayTerm;
			} else {
				invoiceRisk[6] = getChnPayTerm(appSSRS.GetText(i, 3), appSSRS
						.GetText(i, 4));
			}

			tListTableInvoice.add(invoiceRisk);
		}

		// ////////////回执/////////////

		// ///////////////////////////////////////////////
		// xmlexport1.createDocument("Invoice.vts", "");
		// mTextTag1.add("AreaID", manageCom);
		// mTextTag1.add("SysDate", currentDate);
		// mTextTag1.add("LCCont.AppntName", mLCAppntSchema.getAppntName());
		// mTextTag1.add("CMoney",
		// PubFun.getChnMoney(parseFloat(xSSRS.GetText(1, 61))));
		// mTextTag1.add("Money", xSSRS.GetText(1, 61));
		// mTextTag1.add("LCCont.ContNo", mLCAppntSchema.getContNo());
		// mTextTag1.add("LAAgent.AgentCode", agentcode);
		// mTextTag1.add("LAAgent.Name", agentname);
		// mTextTag1.add("LABranchGroup.Name", department);
		// xmlexport1.addTextTag(mTextTag1);
		SSRS testSSRS = new SSRS();
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql("select sum(prem) from lcpol where contno='"
				+ "?contno?" + "' and appflag = '1'");
		sqlbv11.put("contno", tLCContDB.getContNo());
		testSSRS = exeSql.execSQL(sqlbv11);
		String sumprem = testSSRS.GetText(1, 1);

		xmlexport1.createDocument("Invoice_" + manageCom + ".vts", "");
		String sysDate = PubFun.getCurrentDate();
		String PayDate = "";
		if ((salechnl.trim().equals("1") || salechnl.trim().equals("5"))
				&& tLCContDB.getNewPayMode() != null
				&& (tLCContDB.getNewPayMode().trim().equals("1") || tLCContDB
						.getNewPayMode().trim().equals("3"))) {
			PayDate = tLCContDB.getFirstPayDate();
			// if(!manageCom.equals("8632")) sysDate = PayDate;
		} else {
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.sql("select to_char(EnterAccDate,'yyyy-mm-dd'),paydate from ljtempfee where OtherNo='"
					+ "?OtherNo?"
					+ "' and (othernotype = '6' or othernotype = '7' or othernotype = '0') order by paydate");
			sqlbv12.put("OtherNo", tLCContDB.getContNo());
			testSSRS = exeSql.execSQL(sqlbv12);
			if (testSSRS.MaxRow > 0) {
				// if(!manageCom.equals("8632")) sysDate =
				// testSSRS.GetText(1,1);
				PayDate = testSSRS.GetText(1, 2);
			}
		}
		String tSql = "select name from laagent where agentcode='" + "?agentcode?"
				+ "'";
		SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
		sqlbv13.sql(tSql);
		sqlbv13.put("agentcode", agentcode);
		agentname = exeSql.getOneValue(sqlbv13);
		tSql = "select name From LABranchGroup where agentgroup='"
				+ "?agentgroup?" + "'";
		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		sqlbv14.sql(tSql);
		sqlbv14.put("agentgroup", mLCContSchema.getAgentGroup());
		department = exeSql.getOneValue(sqlbv14);
		SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
		sqlbv15.sql("select address from ldcom where comcode = (select upcomcode from ldcom where comcode ='"
				+ "?comcode?" + "')");
		sqlbv15.put("comcode", mLCContSchema.getManageCom());
		String address = exeSql.getOneValue(sqlbv15);
		if (address == null)
			address = " ";
		tSql = "Select b.mngcom From lccont a,Lmriskapp b,lcpol c Where a.contno='"
				+ "?contno?"
				+ "' And a.contno=c.contno And b.riskcode=c.riskcode And c.Polno = c.Mainpolno";
		SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
		sqlbv16.sql(tSql);
		sqlbv16.put("contno", this.mLCContSchema.getContNo());
		mngCom = exeSql.getOneValue(sqlbv16);
		if ("T".equals(mngCom)) {
			agentCodeType = "直销员代码";
			nameType = "直销员姓名";
		}

		mTextTag1.add("LmRisk.RiskName", maipolName);
		mTextTag1.add("LmRisk.RiskName1", maipolName1);
		mTextTag1.add("LmRisk.RiskName2", maipolName2);
		mTextTag1.add("PayIntv", payIntvName);
		mTextTag1.add("PayEnd", payEnd);
		mTextTag1.add("PayTerm", PayTerm);
		mTextTag1.add("PayMode", payStyleName);
		mTextTag1.add("LjTempFee.Oper", oper);
		if ((StrTool.getChnDate(PayDate)).length() == 11) {
			mTextTag1.add("FeeYear", StrTool.getChnDate(PayDate)
					.substring(0, 4));
			mTextTag1.add("FeeMonth", StrTool.getChnDate(PayDate).substring(5,
					7));
			mTextTag1.add("FeeDay", StrTool.getChnDate(PayDate)
					.substring(8, 10));
		}
		mTextTag1.add("LjTempFee.ManageCom", managecomName);
		mTextTag1.add("AreaID", manageCom);
		mTextTag1.add("SysDate", StrTool.getChnDate(sysDate));
		mTextTag1.add("SysYear", StrTool.getChnDate(sysDate).substring(0, 4));
		mTextTag1.add("SysMonth", StrTool.getChnDate(sysDate).substring(5, 7));
		mTextTag1.add("SysDay", StrTool.getChnDate(sysDate).substring(8, 10));
		mTextTag1.add("PayToDate", StrTool.getChnDate(PayDate));
		mTextTag1.add("LCCont.AppntName", mLCContSchema.getAppntName());
		mTextTag1.add("Money", format(Double.parseDouble(sumprem)));
		mTextTag1.add("CMoney", PubFun.getChnMoney(parseFloat(sumprem)));
		mTextTag1.add("LCCont.ContNo", mLCContSchema.getContNo());
		mTextTag1.add("AgentCodeType", agentCodeType);
		mTextTag1.add("LAAgent.AgentCode", agentcode);
		mTextTag1.add("NameType", nameType);
		mTextTag1.add("LAAgent.Name", agentname);
		mTextTag1.add("LABranchGroup.Name", department);
		mTextTag1.add("Address", address);
		xmlexport1.addTextTag(mTextTag1);
		invoiceRisk = new String[7];
		xmlexport1.addListTable(tListTableInvoice, invoiceRisk);
		return true;
	}

	private String getPayTerm(String riskcode, String s1, String s2, String s4,
			String payendyear, String payendyearflag) {
		String s3 = PubFun.calDate(s2, -1, "D", "");
		s1 = StrTool.getChnDate(s1);
		s3 = StrTool.getChnDate(s3);
		String s5 = s1 + "至" + s3;
		if (riskcode.equals("00614000")) // payendyearflag.equals("A")
		{
			s5 = "缴至" + payendyear + "周岁";
		}
		if (s4.equals("0")) {
			s5 = s1;
		}
		return s5;
	}

	private String getPayTerm(String s1, String s2) {
		s1 = StrTool.getChnDate(s1).replaceAll("年", ".").replaceAll("月", ".")
				.replaceAll("日", "");
		s2 = StrTool.getChnDate(PubFun.calDate(s2, -1, "D", "")).replaceAll(
				"年", ".").replaceAll("月", ".").replaceAll("日", "");

		return s1 + "—" + s2;
	}

	private String getChnPayTerm(String s1, String s2) {
		s1 = StrTool.getChnDate(s1);
		s2 = StrTool.getChnDate(PubFun.calDate(s2, -1, "D", ""));

		return s1 + "至" + s2;
	}

	/**
	 * 查询影印件数据
	 * 
	 * @param cXmlDataset
	 *            XMLDataset
	 * @param cLCContSchema
	 *            LCContSchema
	 * @return boolean
	 * @throws Exception
	 */
	private boolean getScanPic(XMLDataset cXmlDataset,
			LCContSchema cLCContSchema) throws Exception {
		XMLDataList tXmlDataList = new XMLDataList();
		// 标签对象
		tXmlDataList.setDataObjectID("PicFile");
		// 标签中的Header
		tXmlDataList.addColHead("FileUrl");
		tXmlDataList.addColHead("HttpUrl");
		tXmlDataList.addColHead("PageIndex");
		tXmlDataList.buildColHead();

		VData tVData = new VData();
		tVData.add(cLCContSchema.getPrtNo());
		tVData.add("11");
		tVData.add("TB");
		tVData.add("TB1001");
		EasyScanQueryBL tEasyScanQueryBL = new EasyScanQueryBL();
		if (!tEasyScanQueryBL.submitData(tVData, "QUERY||MAIN")) {
			logger.debug(tEasyScanQueryBL.mErrors.getFirstError());
			// buildError("getScanPic", "查询保单影印件出错！");
			// return true;
			// 即便失败也返回true
			// return false;
		} else {
			tVData.clear();
			tVData = tEasyScanQueryBL.getResult();
			// Http信息对象
			VData tUrl = (VData) tVData.get(0);
			// 页面信息对象
			VData tPages = (VData) tVData.get(1);
			String tStrUrl = "";
			String tStrPages = "";
			// 根据查询结果初始化影印件信息数组
			mScanFile = new String[tUrl.size()][2];
			for (int nIndex = 0; nIndex < tUrl.size(); nIndex++) {
				// if (nIndex == 3)
				// {
				// continue; // 去掉第四页扫描件
				// }
				tStrUrl = (String) tUrl.get(nIndex);
				tStrPages = (String) tPages.get(nIndex);
				tStrUrl = tStrUrl.substring(0, tStrUrl.lastIndexOf("."))
						+ ".tif";
				mScanFile[nIndex][0] = tStrUrl;
				mScanFile[nIndex][1] = mLCContSchema.getContNo() + "_"
						+ tStrPages + ".tif";
				tXmlDataList.setColValue("FileUrl", mScanFile[nIndex][1]);
				tXmlDataList.setColValue("HttpUrl", tStrUrl);
				tXmlDataList.setColValue("PageIndex", nIndex + 1);
				tXmlDataList.insertRow(0);
			}
		}
		cXmlDataset.addDataObject(tXmlDataList);
		return true;
	}

	/**
	 * 获取发票信息
	 * 
	 * @param cXmlDataset
	 *            XMLDataset
	 * @return boolean
	 * @throws Exception
	 */
	private boolean getInvoice(XMLDataset cXmlDataset) throws Exception {
		// 数据流
		// InnerFormater innerFormater = null;
		String szTemplateFile = "";
		// 团险默认配置文件
		szTemplateFile = mTemplatePath + "ContInvoice.xml";
		// 校验配置文件是否存在
		mFile = new File(szTemplateFile);
		if (!mFile.exists()) {
			buildError("getInvoice", "XML配置文件不存在！");
			return false;
		}
		try {
			XMLDataList tXMLDataList = new XMLDataList();
			tXMLDataList.setDataObjectID("Money");
			tXMLDataList.addColHead("ChinaMoney");
			tXMLDataList.buildColHead();

			tXMLDataList.setColValue("ChinaMoney", PubFun
					.getChnMoney(this.mLCContSchema.getPrem()));
			tXMLDataList.insertRow(0);

			cXmlDataset.addDataObject(tXMLDataList);

			Hashtable hashData = new Hashtable();
			// 将变量ContNo的值赋给xml文件
			hashData.put("_CONTNO", this.mLCContSchema.getContNo());
			// 根据配置文件生成xml数据
			XMLDataMine xmlDataMine = new XMLDataMine(new FileInputStream(
					szTemplateFile), hashData);
			// xmlDataMine.setDataFormater(innerFormater);
			cXmlDataset.addDataObject(xmlDataMine);

			cXmlDataset.addDataObject(new XMLDataTag("Today", PubFun
					.getCurrentDate())); // 签发日期

			// cXmlDataset.addDataObject(new XMLDataTag("ChinaMoney",
			// PubFun.getChnMoney(); //保费大写

			// 查询缴费方式信息
			// 根据合同号，查询缴费记录信息
			// String tSql =
			// "select distinct TempFeeNo from LJTempFee where OtherNo = '" +
			// this.mLCContSchema.getContNo() + "' and OtherNoType = '2'";
			// tSql = "select distinct TempFeeNo from LJTempFee";
			StringBuffer tSBql = new StringBuffer(256);
			tSBql.append("select distinct TempFeeNo from LJTempFee where OtherNo = '");
			tSBql.append("?OtherNo?");
			tSBql.append("' and OtherNoType = '2'");
			
			SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
			sqlbv17.sql(tSBql.toString());
			sqlbv17.put("OtherNo", this.mLCContSchema.getContNo());
			
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = tExeSQL.execSQL(sqlbv17);
			ArrayList<String> list = new ArrayList<String>();
			// String tWhere = " where a.TempFeeNo in ('";
			StringBuffer tBWhere = new StringBuffer(256);
			tBWhere.append(" where a.TempFeeNo in (");
			
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				list.add(tSSRS.GetText(i, 1));
				// 将每笔缴费的缴费号串连起来作为查询条件
//				if (i == tSSRS.getMaxRow()) {
//					// tWhere = tWhere + tSSRS.GetText(i, 1) + "')";
					tBWhere.append("?list?");
					tBWhere.append(")");
//				} else {
//					// tWhere = tWhere + tSSRS.GetText(i, 1) + "','";
//					tBWhere.append(tSSRS.GetText(i, 1));
//					tBWhere.append("','");
//				}
			}
			// cXmlDataset.addDataObject(new XMLDataTag("TempFeeNo",
			// tSSRS.GetText(1, 1))); //收据号

			// 根据上面的查询条件，查询缴费方式为现金的数据
			// tSql = "select a.ConfMakeDate from LJTempFeeClass a" +
			// tWhere + " and a.PayMode = '1' order by a.ConfMakeDate";
			tSBql = new StringBuffer(256);
			tSBql.append("select a.ConfMakeDate from LJTempFeeClass a");
			tSBql.append(tBWhere);
			tSBql.append(" and a.PayMode = '1' order by a.ConfMakeDate");
			
			SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
			sqlbv18.sql(tSBql.toString());
			sqlbv18.put("list", list);
			
			tSSRS = tExeSQL.execSQL(sqlbv18);
			if (tSSRS.getMaxRow() > 0) {
				// 添加缴费方式为现金的标签
				tXMLDataList = new XMLDataList();
				// 添加xml一个新的对象Term
				tXMLDataList.setDataObjectID("Cash");
				// 添加Head单元内的信息
				tXMLDataList.addColHead("PayMode");
				tXMLDataList.addColHead("ConfMakeDate");
				tXMLDataList.buildColHead();
				// 此缴费方式只需知道确认日期
				for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
					tXMLDataList.setColValue("PayMode", "现金");
					tXMLDataList.setColValue("ConfMakeDate", tSSRS
							.GetText(i, 1));
					tXMLDataList.insertRow(0);
				}
				cXmlDataset.addDataObject(tXMLDataList);
			}

			// 根据上面的查询条件，查询缴费方式不是现金的数据
			// tSql =
			// "select b.CodeName,a.BankAccNo,a.AccName,a.ConfMakeDate from
			// LJTempFeeClass a,LDCode b" +
			// tWhere + " and a.PayMode <> '1' and a.BankCode = b.Code and
			// b.CodeType = 'bank' order by a.BankAccNo,a.ConfMakeDate";
			tSBql = new StringBuffer(256);
			tSBql.append("select b.CodeName,a.BankAccNo,a.AccName,a.ConfMakeDate from LJTempFeeClass a,LDCode b");
			tSBql.append(tBWhere);
			tSBql.append(" and a.PayMode <> '1' and a.BankCode = b.Code and b.CodeType = 'bank' order by a.BankAccNo,a.ConfMakeDate");
			SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
			sqlbv19.sql(tSBql.toString());
			sqlbv19.put("list", list);
			tSSRS = tExeSQL.execSQL(sqlbv19);
			if (tSSRS.getMaxRow() > 0) {
				// 添加缴费方式为支票的标签
				tXMLDataList = new XMLDataList();
				// 添加xml一个新的对象Term
				tXMLDataList.setDataObjectID("Check");
				// 添加Head单元内的信息
				tXMLDataList.addColHead("PayMode");
				tXMLDataList.addColHead("Bank");
				tXMLDataList.addColHead("AccName");
				tXMLDataList.addColHead("BankAccNo");
				tXMLDataList.addColHead("ConfMakeDate");
				tXMLDataList.buildColHead();
				// 此标签需要获得银行、户名、帐户的信息
				for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
					tXMLDataList.setColValue("PayMode", "银行转帐");
					tXMLDataList.setColValue("Bank", tSSRS.GetText(i, 1));
					tXMLDataList.setColValue("AccName", tSSRS.GetText(i, 2));
					tXMLDataList.setColValue("BankAccNo", tSSRS.GetText(i, 3));
					tXMLDataList.setColValue("ConfMakeDate", tSSRS
							.GetText(i, 4));
					tXMLDataList.insertRow(0);
				}
				cXmlDataset.addDataObject(tXMLDataList);
			}
			// //添加xml结束标签
			// cXmlDataset.addDataObject(new XMLDataTag("End", "1"));
			// 添加xml结束标签
			tXMLDataList = new XMLDataList();
			tXMLDataList.setDataObjectID("End");
			tXMLDataList.addColHead("Flag");
			tXMLDataList.buildColHead();

			tXMLDataList.setColValue("Flag", "1");
			tXMLDataList.insertRow(0);

			cXmlDataset.addDataObject(tXMLDataList);
		} catch (Exception e) {
			buildError("getInvoice", "根据XML文件生成报表数据失败！");
			return false;
		}
		return true;
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
		tLCPolSet = tLCPolDB.query();
		// 返回个单险种集合
		return tLCPolSet;
	}

	/**
	 * 获取补打保单的数据
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	private boolean getRePrintData() throws Exception {
		// String tCurDate = PubFun.getCurrentDate();
		// String tCurTime = PubFun.getCurrentTime();

		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = null;

		// 查询LCPolPrint表，获取要补打的保单数据
		String tSql = "SELECT PrtTimes + 1 FROM LCPolPrint WHERE MainPolNo = '"
				+ "?MainPolNo?" + "'";
		logger.debug(tSql);
		SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
		sqlbv20.sql(tSql);
		sqlbv20.put("MainPolNo", mLCContSchema.getContNo());
		tSSRS = tExeSQL.execSQL(sqlbv20);
		// 查询出错返回，一般是大异常导致
		if (tExeSQL.mErrors.needDealError()) {
			mErrors.copyAllErrors(tExeSQL.mErrors);
			throw new Exception("获取打印数据失败");
		}
		// 查询结果为空的处理，表示没有查询到以前的打印数据
		if (tSSRS.MaxRow < 1) {
			throw new Exception("找不到原来的打印数据，可能传入的不是主险保单号！");
		}
		mResult.clear();
		mResult.add(mLCContSchema);
		// mResult.addElement(xmlexport);
		mResult.addElement(xmlexport1);

		// 取打印数据
		Connection conn = null;

		try {
			DOMBuilder tDOMBuilder = new DOMBuilder();
			Element tRootElement = new Element("DATASETS");

			conn = DBConnPool.getConnection();

			if (conn == null) {
				throw new Exception("连接数据库失败！");
			}

			Blob tBlob = null;
			// CBlob tCBlob = new CBlob();
			// 拼写查询条件
			tSql = " and MainPolNo = '" + mLCContSchema.getContNo() + "'";
			// 根据查询条件获取原始打印数据
			tBlob = CBlob.SelectBlob("LCPolPrint", "PolInfo", tSql, conn);
			// 如果数据为空
			if (tBlob == null) {
				throw new Exception("找不到打印数据");
			}
			Element tElement = tDOMBuilder.build(tBlob.getBinaryStream())
					.getRootElement();
			tElement = new Element("DATASET").setMixedContent(tElement
					.getMixedContent());
			tRootElement.addContent(tElement);

			ByteArrayOutputStream tByteArrayOutputStream = new ByteArrayOutputStream();
			XMLOutputter tXMLOutputter = new XMLOutputter("  ", true, "UTF-8");
			tXMLOutputter.output(tRootElement, tByteArrayOutputStream);

			// mResult.clear();
			// mResult.add(new ByteArrayInputStream(tByteArrayOutputStream.
			// toByteArray()));
			ByteArrayInputStream tByteArrayInputStream = new ByteArrayInputStream(
					tByteArrayOutputStream.toByteArray());

			// 仅仅重新生成xml打印数据，影印件信息暂时不重新获取
			String FilePath = mOutXmlPath + "/printdata";
			mFile = new File(FilePath);
			if (!mFile.exists()) {
				mFile.mkdir();
			}
			// 根据合同号生成打印数据存放文件夹
			FilePath = mOutXmlPath + "/printdata" + "/"
					+ mLCContSchema.getContNo();
			mFile = new File(FilePath);
			if (!mFile.exists()) {
				mFile.mkdir();
			}

			// 根据团单合同号生成文件
			String XmlFile = FilePath + "/" + mLCContSchema.getContNo()
					+ ".xml";
			FileOutputStream fos = new FileOutputStream(XmlFile);
			// 此方法写入数据准确，但是相对效率比较低下
			int n = 0;
			while ((n = tByteArrayInputStream.read()) != -1) {
				fos.write(n);
			}
			fos.close();

			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();

			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				// do nothing
			}

			throw ex;
		}
		return true;
	}

	public float parseFloat(String s) {
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
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "8651";
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema.setContNo("880168498222");
		VData vData = new VData();
		vData.add(tLCContSchema);
		vData.add(tG);
		InvoiceF1PBL tInvoiceF1PBL = new InvoiceF1PBL();
		if (!tInvoiceF1PBL.submitData(vData, "PRINT")) {
			logger.debug("Wrong!");
		} else {
			logger.debug("OK!");
		}
		XmlExport xmlexportAll = new XmlExport();
		xmlexportAll.createDocuments("", tG);
		xmlexportAll.setTemplateName("Invoice.vts");
		VData mResult = tInvoiceF1PBL.getResult();
		XmlExport xmlexport = (XmlExport) mResult.getObjectByObjectName(
				"XmlExport", 0);
		xmlexportAll.addDataSet(xmlexportAll.getDocument().getRootElement(),
				xmlexport.getDocument().getRootElement());
		xmlexportAll.outputDocumentToFile("d:\\", "Invoice"
				+ PubFun.getCurrentTime().toString().replaceAll(":", "_"));
	}

}
