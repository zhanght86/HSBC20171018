/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1print;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;

import com.sinosoft.lis.bq.EdorCalZT;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LABranchGroupSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCInsuredRelatedSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.schema.LDContInvoiceMapSchema;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.utility.CBlob;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XMLDataList;
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
public class LCContF1PBL implements PrintService {
private static Logger logger = Logger.getLogger(LCContF1PBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private File mFile = null;
	private MMap map = new MMap();
	private MMap map1 = new MMap();
	private MMap calMap; // 用于传输计算因子

	// 操作流转控制变量
	private String mOperate = "";
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCAppntSchema mLCAppntSchema = new LCAppntSchema();
	private LCInsuredRelatedSchema mLCInsuredRelatedSchema = new LCInsuredRelatedSchema(); // 连带被保险人
	private LCBnfSchema mLCBnfSchema = new LCBnfSchema(); // 受益人
	private LCPolSchema mLCPolSchema = new LCPolSchema(); // 险种
	private String flag1 = "0"; // 是否具有连带被保险人标志
	private String flag2 = "0"; // 是否具有受益人标志
	private String flag3 = "0"; // 是否具有连第二被保险人受益人标志
	private String flag4 = "0"; // 附加险标志
	private String flag5 = "0"; // 特约标志
	private String flag6 = "0"; // 现金价值标志
	private String flag7 = "0";
	private String flag8 = "0";
	private String occupationcode = "";
	private String mainInsuredno = ""; // 主险被保险人
	private String secondInsuredno = ""; // 第二被保险人
	private String mainRiskName = ""; // 主险名称
	private String mainRiskcode = "";
	private String addRiskcode1 = "";
	private String addRiskcode2 = "";
	private String addRiskcode3 = "";
	private String addRiskcode4 = "";
	private String addRiskcode11 = "";
	private String addRiskcode21 = "";
	private String addRiskcode31 = "";
	private String addRiskcode41 = "";
	private String insuredName = ""; // 被保险人姓名
	private String insuredSex = ""; // 被保险人性别
	// ----baimd add 070312
	private String standRate = ""; // 年交保费
	// ----baimd add 070312
	private String insuredIDType = "";
	private String insuredIDNo = "";
	private String insuredRemark = ""; // 被保险人备注
	private String insuYear = ""; // 现金价值用到的保险期间
	private String payendyear = "";
	private String payintv = "0";
	private String expiryScale = ""; // 卓越两全满期比例
	private String insuFlag = ""; // 现金价值用到的保险期间单位
	private String insuAge = ""; // 被保险人年龄
	private String insuAge1 = ""; // 第二被保人
	private String insuSex1 = "";
	private int n = 0; // 附加险具有现金价值的个数
	private int m = 0; // 附加险具有的红利先进价值的个数
	private String appntAddress = "";
	private String WithDrawPrem1 = "";
	private String WithDrawPrem2 = "";
	private String WithDrawPrem11 = "";
	private String WithDrawPrem21 = "";
	private String WithDrawPrem12 = "";
	private String WithDrawPrem22 = "";
	private String WithDrawPrem13 = "";
	private String WithDrawPrem23 = "";
	private String WithDrawPrem14 = "";
	private String WithDrawPrem24 = "";
	private String WithDraw = "0";
	private String WithDraw1 = "0"; // 附件险风险保额
	private String Tel = "";
	private String ZipCode = "";
	private String mobile = "";
	private String companyphone = "";
	private String manageCom = "";
	private String tManageCom = "";
	private String manageComName = "";
	private LCAddressSchema mLCAddressSchema = new LCAddressSchema();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();
	private LABranchGroupSchema mLABranchGroupSchema = new LABranchGroupSchema();
	private LDComSchema mLDComSchema = new LDComSchema();
	private String rePrintType = null; // 用于判断事 保单重打 还是 补发保单
	private final String RPBD = "rpbd"; // 保单重打
	private final String RIBD = "ribd"; // 补发保单
	private String mMainPayTerm = "";
	// 扫描件集合
	// private VData mEasyScan = new VData();

	private String mTemplatePath = null;
	private String mOutXmlPath = null;
	private XMLDatasets mXMLDatasets = null;
	private String[][] mScanFile = null;
	private XmlExport xmlexport = new XmlExport(); // add by yaory
	private XmlExport xmlexport1 = new XmlExport();
	private TextTag mTextTag = new TextTag(); // add by yaory
	private TextTag mTextTag1 = new TextTag();
	private TextTag mTextTag2 = new TextTag();
	private TextTag mTextTag3 = new TextTag();

	private String startNo = ""; // 发票号
	private String prtSeq = ""; // 打印批次号

	public LCContF1PBL() {
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
		this.mLCContSchema.setModifyDate(PubFun.getCurrentDate());
		this.mLCContSchema.setModifyTime(PubFun.getCurrentTime());
		mResult.addElement(xmlexport);
		if (rePrintType == null)
			mResult.addElement(xmlexport1); // 如果不是 保单重打和补发保单,不需要发票;
			// VData tResult = new VData();
			// tResult.add(this.mLCContSchema); //只更新这个表就好拉
			// tResult.add(mGlobalInput);
			// tResult.add(prtSeq);
			// tResult.add(startNo);
			// tResult.add(rePrintType);

		// map.put(mLCContSchema,"UPDATE");

		if (rePrintType == null) {
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
					+ "?mContNo?"
					+ "','"
					+ "?startNo?"
					+ "','"
					+ "?prtSeq?"
					+ "','"
					+ "?date1?"
					+ "',"
					+ "'"
					+ "?time1?"
					+ "','"
					+ "?date1?"
					+ "','"
					+ "?time1?"
					+ "','"
					+ "?Operator?" + "','1')";
			// logger.debug("Insert Sql is ===:"+insSql);
			// map.put(tLDMapSchema,"INSERT");
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(insSql);
			sqlbv1.put("mContNo", mContNo);
			sqlbv1.put("startNo", startNo);
			sqlbv1.put("prtSeq", prtSeq);
			sqlbv1.put("date1", PubFun.getCurrentDate());
			sqlbv1.put("time1", PubFun.getCurrentTime());
			sqlbv1.put("Operator", mGlobalInput.Operator);
			map.put(sqlbv1, "INSERT");
		}
		mResult.add(map);
		// mResult.add(map1);
		// 后台提交
		// LCContF1PBLS tLCContF1PBLS = new LCContF1PBLS();
		// tLCContF1PBLS.submitData(tResult, "PRINT");
		// //判错处理
		// if (tLCContF1PBLS.mErrors.needDealError())
		// {
		// mErrors.copyAllErrors(tLCContF1PBLS.mErrors);
		// buildError("saveData", "提交数据库出错！");
		// return false;
		// }
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
		// xml对象
		EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);
		String sumprem = "";
		String mainsumprem = "";
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
		tManageCom = mLCContSchema.getManageCom().substring(0, 4);
		if (tManageCom.equals("8623"))
			tManageCom = mLCContSchema.getManageCom().substring(0, 6);

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
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSql);
		sqlbv3.put("agentcode", agentcode);
		pSSRS = tExeSQL.execSQL(sqlbv3);
		if (pSSRS.MaxRow == 0) {
			buildError("getInfo", "查询代理人所属部门信息失败！");
			return false;
		} else {
			department = pSSRS.GetText(1, 1);
		}

		tSql = "select name From Ldcom where comcode='"
				+ "?comcode?" + "'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSql);
		sqlbv4.put("comcode", mLCContSchema.getManageCom());
		pSSRS = tExeSQL.execSQL(sqlbv4);
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
		// 查询邮政编码与联系电话
		tSql = "select a.homephone,a.zipcode,concat((select b.placename from ldaddress b where trim(b.placecode)=trim(a.county)),a.postaladdress),a.mobile,a.companyphone from lcaddress a where a.customerno='"
				+ "?customerno?"
				+ "' and a.addressno = '"
				+ "?addressno?" + "'";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tSql);
		sqlbv5.put("customerno", tLCAppntDB.getAppntNo());
		sqlbv5.put("addressno", tLCAppntDB.getAddressNo());
		pSSRS = tExeSQL.execSQL(sqlbv5);
		if (pSSRS.MaxRow == 0) {
			buildError("getInfo", "查询投保人信息失败！");
			return false;
		} else {
			// Tel = pSSRS.GetText(1, 1);
			ZipCode = pSSRS.GetText(1, 2);
			appntAddress = pSSRS.GetText(1, 3);
			mobile = pSSRS.GetText(1, 4);
			companyphone = pSSRS.GetText(1, 5);

		}
		// 查询polno
		String ppsql = "";
		String ppsql628 = "";
		String tppsql = "";
		ExeSQL exeSql = new ExeSQL();
		SSRS tSSRS = new SSRS(); // 临时的，谁偶可以用
		SSRS xSSRS = new SSRS(); // 基本险专用
		SSRS mSSRS = new SSRS(); // 连带被保险人专用
		SSRS bSSRS = new SSRS(); // 受益人专用
		SSRS sbSSRS = new SSRS(); // 第二被保险人受益人专用
		SSRS appSSRS = new SSRS(); // 附加险专用(部分字段)
		SSRS specSSRS = new SSRS(); // 特约信息专用
		SSRS cashSSRS = new SSRS(); // 现金价值信息专用
		SSRS hlSSRS = new SSRS(); // 主险红利信息专用
		boolean flag_717_718_1 = false; // 表明是否有717，718险种
		boolean flag_717_718_2 = false; // 表明是否有717，718附加险
		String salechnl = ""; // 险种类型

		ListTable tListTableInvoice = null;

		ListTable tListTableURL = null;
		String strURL[] = null;
		String strURLTitle[] = null;
		ppsql = "select (select b.serverport from es_server_info b where b.hostname = a.hostname),a.picpath , a.pagename from es_doc_pages a where a.docid in(select docid from es_doc_relation ";
		ppsql += "where bussnotype = '11' and busstype = 'TB'  and subtype in ('UA001','UA006','UA081','UA013','UN001','UN002','UN015','UN017','UN018','UN019','UN020','UN021','UN022','UN016','UA015') "; // ('UA001','UA006','UA081','UA013')
																																																			// ";
		ppsql += "and bussno = '" + "?bussno?"
				+ "') order by a.docid,a.pageid";
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

		ppsql = "select a.polno,a.insuredno,a.riskcode,a.InsuYear,a.InsuYearFlag,floor(Months_between(a.CValiDate,a.InsuredBirthday)/12),a.payintv,a.payyears,concat(trim(a.standbyflag1),'%'),case b.mngcom when 'B' then '3' when 'I' then '1' when 'G' then '2' when 'T' then '5' end,payendyearflag,payendyear,paymode,cvalidate,payenddate from lcpol a,lmriskapp b where a.contno='"
				+ "?contno?"
				+ "' and a.polno=a.mainpolno and a.appflag = '1' and b.riskcode = a.riskcode";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(ppsql);
		sqlbv7.put("contno", tLCContDB.getContNo());
		tSSRS = exeSql.execSQL(sqlbv7);
		if (tSSRS.MaxRow == 0) {
			buildError("getInfo", "查询个单险种信息失败！");
			return false;
		} else {
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql("select * from lcpol where contno='"
					+ "?contno?"
					+ "' and polno=mainpolno and appflag = '1'");
			sqlbv.put("contno", tLCContDB.getContNo());
			xSSRS = exeSql.execSQL(sqlbv);
		}
		mainsumprem = xSSRS.GetText(1, 61);
		salechnl = tSSRS.GetText(1, 10);

		// 主险号码
		SSRS tSSRS1 = new SSRS();
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql("Select riskname,riskshortname,riskstatname From lmrisk Where riskcode='"
				+ "?riskcode?" + "'");
		sqlbv8.put("riskcode", tSSRS.GetText(1, 3));
		tSSRS1 = exeSql.execSQL(sqlbv8);
		String maipolName = tSSRS1.GetText(1, 1);
		String maipolName1 = tSSRS1.GetText(1, 2);
		String maipolName2 = tSSRS1.GetText(1, 3);

		// 交费方式
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql("Select codename From ldcode Where codetype='payintv' And code='"
				+ "?code?" + "'");
		sqlbv9.put("code", tSSRS.GetText(1, 7));
		String payIntvName = exeSql.getOneValue(sqlbv9);

		// 收费形式
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql("Select codename From ldcode Where codetype='paymode' And code='"
				+ "?code?" + "'");
		sqlbv10.put("code", tLCContDB.getPayLocation());
		String payStyleName = exeSql.getOneValue(sqlbv10);

		// 交费期间(年/岁)
		String payEnd = "";
		if (tSSRS.GetText(1, 11).equals("Y"))
			payEnd = tSSRS.GetText(1, 12) + "年";
		else if (tSSRS.GetText(1, 11).equals("A"))
			payEnd = tSSRS.GetText(1, 12) + "岁";

		// 收款员
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql("select operator from ljtempfee where OtherNo='"
				+ "?OtherNo?"
				+ "'and tempfeetype='1' and (othernotype = '6' or othernotype = '7' or othernotype = '0')and riskcode='"
				+ "?riskcode?" + "' order by paydate");
		sqlbv11.put("OtherNo", tLCContDB.getContNo());
		sqlbv11.put("riskcode", xSSRS.GetText(1, 12));
		String oper = exeSql.getOneValue(sqlbv11);

		// 营业单位
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		sqlbv12.sql("Select Name From ldcom Where comcode='"
				+ "?comcode?" + "'");
		sqlbv12.put("comcode", mLCContSchema.getManageCom().substring(0, 6));
		String managecomName = exeSql.getOneValue(sqlbv12);
		mMainPayTerm = getPayTerm(xSSRS.GetText(1, 12), xSSRS.GetText(1, 31),
				xSSRS.GetText(1, 36), xSSRS.GetText(1, 53), xSSRS
						.GetText(1, 44), xSSRS.GetText(1, 43));
		// 南京发票基本险保费信息
		tListTableInvoice = new ListTable();
		tListTableInvoice.setName("RISK");
		String invoiceRisk[] = new String[7];
		invoiceRisk[0] = "险种：";
		invoiceRisk[1] = xSSRS.GetText(1, 12) + "  保费";
		invoiceRisk[2] = format(Double.parseDouble(xSSRS.GetText(1, 61)));
		invoiceRisk[3] = xSSRS.GetText(1, 12);
		invoiceRisk[4] = "保费";
		logger.debug("xSSRS.GetText(1,12)=" + xSSRS.GetText(1, 12));
		SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
		sqlbv13.sql("Select riskshortname From lmrisk Where riskcode='"
				+ "?riskcode?" + "'");
		sqlbv13.put("riskcode", xSSRS.GetText(1, 12));
		invoiceRisk[5] = exeSql.getOneValue(sqlbv13); // 收费项目
		// 交费起止日期根据李\u73A5要求修改，发票上主险缴费起止日期与保单上缴费期限一致
		invoiceRisk[6] = mMainPayTerm; // getPayTermSub(tSSRS.GetText(1,14),tSSRS.GetText(1,15));
		String mInvoicePayTerm = mMainPayTerm; // getChnPayTermSub(tSSRS.GetText(1,14),tSSRS.GetText(1,15));
		tListTableInvoice.add(invoiceRisk);

		// 定义主险LCPOL的POLNO
		String mainPolno = tSSRS.GetText(1, 1);
		mainInsuredno = tSSRS.GetText(1, 2); // 主险被保险人
		mainRiskcode = tSSRS.GetText(1, 3);
		insuYear = tSSRS.GetText(1, 4);
		insuFlag = tSSRS.GetText(1, 5);
		insuAge = tSSRS.GetText(1, 6);
		payintv = tSSRS.GetText(1, 7);
		payendyear = tSSRS.GetText(1, 8);
		expiryScale = tSSRS.GetText(1, 9);

		// 领取方式
		if (mainRiskcode.equals("00628000") || mainRiskcode.equals("00630000")) {
			ppsql = "select c.codename, a.getintv,b.paramsname,substr(a.getdutykind,3,2) from lcget a,ldcode c,lmriskparamsdef b where a.contno = '"
					+ "?contno?"
					+ "' and b.dutycode = a.dutycode and b.paramstype = 'getdutykind3' and c.Code = a.Getintv and b.paramscode = To_number(substr(a.getdutykind,5,2)) and c.codetype = 'getintv' and a.getdutykind <> '1'";
		} else
			ppsql = "select c.codename,a.getintv from lcget a,lmdutyget b,ldcode c where a.contno = '"
					+ "?contno?"
					+ "' and a.getdutycode = b.getdutycode and b.gettype1 = '1' and c.codetype = 'getintv' and c.code = a.getintv";
		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		sqlbv14.sql(ppsql);
		sqlbv14.put("contno", mLCContSchema.getContNo());
		tSSRS = exeSql.execSQL(sqlbv14);
		// 0 -- 趸领
		// 1 －－ 月领
		// 3 －－ 季领
		// 12－－ 年龄
		// 36－－ 每3年领
		String ygetintv = "";
		String tDrawTerm = "";
		if (tSSRS.MaxRow > 0) {
			ygetintv = tSSRS.GetText(1, 1);
			if (mainRiskcode.equals("00628000")
					|| mainRiskcode.equals("00630000")) {
				if (tSSRS.GetText(1, 4).equals("01"))
					ygetintv = "固定期限平准式年领";
				else if (tSSRS.GetText(1, 4).equals("02"))
					ygetintv = "6％算术递增式年领";
				tDrawTerm = tSSRS.GetText(1, 3);
			}
		} else if (mainRiskcode.equals("00628000")
				|| mainRiskcode.equals("00630000"))
			ygetintv = "一次性领取";

		// 初始费用率
		ppsql = "select concat(to_char(feevalue * 100, '0.9900'),'%') From lmriskfee where payplancode in (select payplancode From lmdutypayrela  where dutycode in (select dutycode from lmriskduty where riskcode='"
				+ "?mainRiskcode?" + "'))";
		SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
		sqlbv15.sql(ppsql);
		sqlbv15.put("riskcode", mainRiskcode);
		tSSRS = exeSql.execSQL(sqlbv15);

		if (tSSRS.MaxRow > 0) {
			// --------------baimd add 070307---------------
			if (mainRiskcode.trim().equals("00904000")) {
				mTextTag.add("ChargeRate", "10%");
			} else {
				if (mainRiskcode.trim().equals("00903000")) {
					ExeSQL rexeSql = new ExeSQL();
					SSRS nSSRS = new SSRS();
					SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
					sqlbv16.sql("select prem from lcpol where ContNo='"
							+ "?ContNo?"
							+ "' and polno = mainpolno and appflag = '1'");
					sqlbv16.put("ContNo", mLCContSchema.getContNo());
					nSSRS = rexeSql.execSQL(sqlbv16);
					if (this.parseFloat(nSSRS.GetText(1, 1)) <= 50000) {
						mTextTag.add("ChargeRate", "10%");
					} else {
						mTextTag
								.add(
										"ChargeRate",
										"50,000元及以下部分，比例为10%；50,000元以上的"
												+ (Integer.parseInt(nSSRS
														.GetText(1, 1)) - 50000)
												+ "元部分，比例为5%");
					}
				} else
					mTextTag.add("ChargeRate", tSSRS.GetText(1, 1));
			}
		}
		// --------------baimd add 070307---------------
		// 初始保单价值
		ppsql = "select insuaccbala from lcinsureacc where polno = '"
				+ "?mainPolno?" + "'";
		SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
		sqlbv17.sql(ppsql);
		sqlbv17.put("mainPolno", mainPolno);
		tSSRS = exeSql.execSQL(sqlbv17);

		if (tSSRS.MaxRow > 0) {
			mTextTag.add("CInitWorth", PubFun.getChnMoney(parseFloat(tSSRS
					.GetText(1, 1))));
			mTextTag.add("InitWorth", tSSRS.GetText(1, 1));
		}

		// 保证利率
		ppsql = "select concat(to_char(rate * 100, '0.9900'),'%') from lmaccguratrate where insuaccno = '902000'";
		SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
		sqlbv18.sql(ppsql);
		tSSRS = exeSql.execSQL(sqlbv18);

		if (tSSRS.MaxRow > 0) {
			mTextTag.add("InterestRate", tSSRS.GetText(1, 1));
		}

		// 查询主险名称

		ppsql = "select riskstatname from lmrisk where riskcode='"
				+ "?mainRiskcode?" + "'";
		SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
		sqlbv19.sql(ppsql);
		sqlbv19.put("mainRiskcode", mainRiskcode);
		tSSRS = exeSql.execSQL(sqlbv19);
		if (tSSRS.MaxRow == 0) {
			buildError("getInfo", "此险种在定义时信息存储不全！");
			return false;
		} else {
			mainRiskName = tSSRS.GetText(1, 1);
		}
		// 查询被保险人信息
		ppsql = "select Name,Sex,IDType,IDNo from lcinsured where contno='"
				+ "?contno?" + "' and sequenceno='1'";
		// 根据合同号查询
		SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
		sqlbv20.sql(ppsql);
		sqlbv20.put("contno", mLCContSchema.getContNo());
		mSSRS = exeSql.execSQL(sqlbv20);
		if (mSSRS.MaxRow > 0) {
			insuredName = mSSRS.GetText(1, 1);
			insuredSex = mSSRS.GetText(1, 2);
			insuredIDType = mSSRS.GetText(1, 3);
			insuredIDNo = mSSRS.GetText(1, 4);
		}

		// 查询第二被保险人信息
		ppsql = "select * from lcinsured where contno='"
				+ "?contno?" + "' and sequenceno='2'";
		// 根据合同号查询
		SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
		sqlbv21.sql(ppsql);
		sqlbv21.put("contno", mLCContSchema.getContNo());
		mSSRS = exeSql.execSQL(sqlbv21);

		// 如果查询失败，无论是查询为空，还是真正的查询失败，则返回
		if (mSSRS.MaxRow == 0) {
			logger.debug("查询连带被保险人信息失败！");
			flag1 = "1";
		} else {
			secondInsuredno = mSSRS.GetText(1, 3);
			String insuredBirthday1 = mSSRS.GetText(1, 15).trim();
			ppsql = "select floor(Months_between('"
					+ "?date?" + "',to_char(to_date('"
					+ "?insuredBirthday1?" + "','YYYY-MM-DD'),'YYYY-MM-DD'))/12) from dual";
			SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
			sqlbv22.sql(ppsql);
			sqlbv22.put("date", mLCContSchema.getCValiDate());
			sqlbv22.put("insuredBirthday1", insuredBirthday1);
			insuAge1 = exeSql.getOneValue(sqlbv22);

			logger.debug(insuAge1);
		}
		// 查询受益人
		ppsql = "select a.Name,a.IDType,a.IDNo,a.BnfGrade,a.BnfLot,b.name from lcbnf a,lcinsured b where a.contno='"
				+ "?contno?"
				+ "' and a.polno='"
				+ "?mainPolno?"
				+ "' and a.insuredno='"
				+ "?mainInsuredno?"
				+ "' and a.insuredno = b.insuredno and b.contno = '"
				+ "?contno?" + "'";
		SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
		sqlbv23.sql(ppsql);
		sqlbv23.put("contno", tLCContDB.getContNo());
		sqlbv23.put("mainPolno", mainPolno);
		sqlbv23.put("mainInsuredno", mainInsuredno);
		bSSRS = exeSql.execSQL(sqlbv23);
		if (bSSRS.MaxRow == 0) {
			logger.debug("查询受益人信息失败！");
			flag2 = "1";
		}
		// 查询第二被保险人受益人
		if (flag1.equals("1")) {
			logger.debug("没有连带被保险人就没有连带被保险人受益人！");
			flag3 = "1";
		} else {
			// ppsql = "select * from lcbnf where contno='" +
			// tLCContDB.getContNo() +
			// "' and polno='" + mainPolno + "' and insuredno='" +
			// secondInsuredno + "'";
			ppsql = "select a.Name,a.IDType,a.IDNo,a.BnfGrade,a.BnfLot,b.name from lcbnf a,lcinsured b where a.contno='"
					+ "?contno?"
					+ "' and a.polno='"
					+ "?mainPolno?"
					+ "' and a.insuredno='"
					+ "?secondInsuredno?"
					+ "' and a.insuredno = b.insuredno and b.contno = '"
					+ "?contno?" + "'";
			SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
			sqlbv24.sql(ppsql);
			sqlbv24.put("contno", tLCContDB.getContNo());
			sqlbv24.put("mainPolno", mainPolno);
			sqlbv24.put("secondInsuredno", secondInsuredno);
			sbSSRS = exeSql.execSQL(sqlbv24);
			if (sbSSRS.MaxRow == 0) {
				// buildError("getInfo", "查询第二被保险人受益人信息失败！");
				flag3 = "1";
			}
		}
		// 查询附加险
		ppsql = "select b.RiskStatName,a.RiskCode,a.Amnt,a.Mult,a.Prem,a.Years,c.RiskPeriod,c.bonusflag,a.cvalidate,a.payenddate,a.payintv,a.enddate,a.payendyearflag,a.payendyear,a.insuyear From lcpol a,lmrisk b,lmriskapp c where a.polno!=a.mainpolno and a.mainpolno='"
				+ "?mainPolno?"
				+ "' and a.riskcode = b.riskcode and a.riskcode = c.riskcode and a.appflag = '1'";
		SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
		sqlbv25.sql(ppsql);
		sqlbv25.put("mainPolno", mainPolno);
		appSSRS = exeSql.execSQL(sqlbv25);
		if (appSSRS.MaxRow == 0) {
			// buildError("getInfo", "查询附加险信息失败！");
			flag4 = "1";
		}
		// 特别约定
		ppsql = "select * from LCSpec where polno in (select polno from lcpol where contno = '"
				+ "?contno?"
				+ "' and appflag = '1') order by serialno";
		SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
		sqlbv26.sql(ppsql);
		sqlbv26.put("contno", tLCContDB.getContNo());
		specSSRS = exeSql.execSQL(sqlbv26);
		if (specSSRS.MaxRow == 0) {
			// buildError("getInfo", "查询特约信息失败！");
			flag5 = "1";
		}
		// 现金价值-主险
		ListTable tListTable3 = new ListTable();
		tListTable3.setName("WORTH"); // 对应模版部分的行对象名
		String str3[] = null;
		logger.debug("cLCPolSet.size()======" + cLCPolSet.size());
		double dCashValue = 0;

		for (int i = 1; i <= cLCPolSet.size(); i++) {
			LCPolSchema mLCPolSchema = (LCPolSchema) cLCPolSet.get(i);
			String tRiskCode = mLCPolSchema.getRiskCode();
			ppsql = "select * from lmcalmode where riskcode = '" + "?tRiskCode?"
					+ "' and type = 'E' and remark = 'CVT'";
			SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
			sqlbv27.sql(ppsql);
			sqlbv27.put("tRiskCode", tRiskCode);
			if (mainRiskcode.equals(tRiskCode)) {
				String tInsuAge = insuAge;
				if (tRiskCode.equals("00128000")
						|| tRiskCode.equals("00128001")
						|| tRiskCode.equals("00325000")
						|| tRiskCode.equals("00115000")
						|| tRiskCode.equals("00115001")) { // 这些险种的现价不显示，业务需求
					flag6 = "1";
					break;
				}
				if (exeSql.execSQL(sqlbv27).MaxRow == 0) {
					flag6 = "1";
					break;
				}
				if (!flag1.equals("1")
						&& (tRiskCode.equals("00144000") || tRiskCode
								.equals("00118000"))) {
					if (Integer.parseInt(insuAge) > Integer.parseInt(insuAge1)) {
						tInsuAge = insuAge1;
					}
				}
				int j = 0; // 保单年度
				if (insuYear.equals("1000")) {
					j = 100 - Integer.parseInt(tInsuAge);
					if (j > 100)
						j = 100;
				}
				if (!insuYear.equals("1000") && insuFlag.equals("Y")) {
					j = Integer.parseInt(insuYear);
				}
				if (!insuYear.equals("1000") && insuFlag.equals("A")) {
					j = Integer.parseInt(insuYear) - Integer.parseInt(tInsuAge);
				}
				if (!insuYear.equals("1000") && insuFlag.equals("M")) {
					j = 1;
				}
				LCDutySchema tLCDutySchema = new LCDutySchema();
				tLCDutySchema.setPolNo(mLCPolSchema.getPolNo());
				LCDutyDB tLCDutyDB = new LCDutyDB();
				tLCDutyDB.setSchema(tLCDutySchema);
				LCDutySet tLCDutySet = tLCDutyDB.query();
				if (tLCDutySet == null || tLCDutySet.size() < 1) {
					continue;
				}
				tLCDutySchema = tLCDutySet.get(1);
				tSql = "select amntflag,vpu from lmduty where dutycode ='"
						+ "?dutycode?" + "'";
				SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
				sqlbv28.sql(tSql);
				sqlbv28.put("dutycode", tLCDutySchema.getDutyCode());
				double amnt = mLCPolSchema.getAmnt(), mult = mLCPolSchema
						.getMult(), gene = 1;
				String amntflag = "", vpu = "";
				double standPrem = mLCPolSchema.getStandPrem();
				DecimalFormat df = new DecimalFormat("0.00");
				int index = 0;
				SSRS worthSSRS = this.getCashValueTable(mLCPolSchema,
						tLCDutySchema, 0, j, mLCContSchema.getCValiDate(), "");
				if (worthSSRS == null || worthSSRS.MaxRow == 0) {
					flag6 = "1";
					logger.debug("没有取到现金价值！");
				}
				for (int k = 0; worthSSRS != null && worthSSRS.MaxRow > 0
						&& k < worthSSRS.MaxRow; k++) {
					double cash = Double.parseDouble(worthSSRS
							.GetText(k + 1, 1));
					if (cash == 0.0 && k == 0) {
						if (!(tRiskCode.equals("00110B00")
								|| tRiskCode.equals("00201000")
								|| tRiskCode.equals("00210000")
								|| tRiskCode.equals("00107000")
								|| tRiskCode.equals("00104000")
								|| tRiskCode.equals("00110A00") || tRiskCode
								.equals("00109000")))
							continue;
					}
					index++;
					// logger.debug("amnt is "+amnt);
					// logger.debug("vpu is "+vpu);
					// logger.debug("gene is "+gene);
					String strcash = "";
					if (tRiskCode.equals("00128002")
							|| tRiskCode.equals("00108000")
							|| tRiskCode.equals("00108001")) {
						if (payintv.equals("0")) {
							strcash = df.format(standPrem * cash);
						}
						if (payintv.equals("12")) {
							strcash = df.format(standPrem * index * cash);
						}
						if (payintv.equals("1")) {
							strcash = df.format(standPrem * 12 * index * cash);
						}
					} else {
						// -------------baimd add
						// 070305----------------------------
						if (index == 1) {
							tSql = "select bonusflag,riskprop From lmriskapp where riskcode='"
									+ "?tRiskCode?" + "'";
							SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
							sqlbv29.sql(tSql);
							sqlbv29.put("tRiskCode", tRiskCode);
							tSSRS = tExeSQL.execSQL(sqlbv29);
							if (tSSRS.MaxRow >= 0
									&& tSSRS.GetText(1, 1).trim().equals("2")
									&& tSSRS.GetText(1, 2).trim().equals("Y")) {
								gene = 1;
							} else {
								tSql = "select amntflag,vpu from lmduty where dutycode ='"
										+ "?dutycode?" + "'";
								SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
								sqlbv30.sql(tSql);
								sqlbv30.put("dutycode", tLCDutySchema.getDutyCode());
								SSRS dSSRS = tExeSQL.execSQL(sqlbv30);
								if (dSSRS.MaxRow > 0) {
									amntflag = dSSRS.GetText(1, 1);
									vpu = dSSRS.GetText(1, 2);
									if (amntflag != null
											&& amntflag.trim().equals("2")) { // 按份数
										gene = mult;
									}
									if (amntflag != null
											&& amntflag.trim().equals("1")) { // 按保额
										gene = amnt / (Double.parseDouble(vpu));
									}
								}
							}
						}
						strcash = df.format(cash * gene);
					}
					// -------------baimd add 070305----------------------------

					logger.debug("strcash is " + strcash);
					str3 = new String[2];
					str3[0] = String.valueOf(index);
					str3[1] = String.valueOf(strcash);
					tListTable3.add(str3);
					if (index == 1) {
						tSql = "select bonusflag From lmriskapp where riskcode='"
								+ "?tRiskCode?" + "'";
						SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
						sqlbv31.sql(tSql);
						sqlbv31.put("tRiskCode", tRiskCode);
						tSSRS = tExeSQL.execSQL(sqlbv31);
						String cashtitle = "现金价值表";
						if (tSSRS.MaxRow > 0
								&& tSSRS.GetText(1, 1).trim().equals("2"))
							cashtitle = "初始基本保额现金价值表";
						mTextTag.add("Title1", mainRiskName + cashtitle);
						// ----baimd add 070312--------------
						double gene1 = 1;
						tSql = "select bonusflag,riskprop From lmriskapp where riskcode='"+"?tRiskCode?"+"'";
						SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
						sqlbv32.sql(tSql);
						sqlbv32.put("tRiskCode", tRiskCode);
						tSSRS = tExeSQL.execSQL(sqlbv32);
						if (tSSRS.MaxRow >= 0
								&& tSSRS.GetText(1, 1).trim().equals("2")
								&& tSSRS.GetText(1, 2).trim().equals("Y")) {
							tSql = "select amntflag,vpu from lmduty where dutycode ='"
									+ "?dutycode?" + "'";
							SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
							sqlbv33.sql(tSql);
							sqlbv33.put("dutycode", tLCDutySchema.getDutyCode());
							SSRS dSSRS = tExeSQL.execSQL(sqlbv33);
							if (dSSRS.MaxRow > 0) {
								amntflag = dSSRS.GetText(1, 1);
								vpu = dSSRS.GetText(1, 2);
								if (amntflag != null
										&& amntflag.trim().equals("2")) { // 按份数
									tSql = "select prem from lcprem where payplancode not like'0%'and dutycode='"
											+ "?dutycode?"
											+ "' and contno='"
											+ "?contno?" + "'";
									SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
									sqlbv34.sql(tSql);
									sqlbv34.put("dutycode", tLCDutySchema.getDutyCode()); 
									sqlbv34.put("contno", tLCContDB.getContNo()); 
									SSRS vpuSSRS = tExeSQL.execSQL(sqlbv34);
									if (vpuSSRS.MaxRow > 0) {
										gene1 = Double.parseDouble(vpuSSRS
												.GetText(1, 1))
												/ mult;
									}
								}
								if (amntflag != null
										&& amntflag.trim().equals("1")) { // 按保额
									gene1 = Double.parseDouble(vpu);
								}
								// tSql = "select prem from lcprem where
								// payplancode not like'0%'and dutycode='" +
								// tLCDutySchema.getDutyCode() +
								// "' and contno='" + tLCContDB.getContNo() +
								// "'";
								// SSRS vpuSSRS = tExeSQL.execSQL(tSql);
								// if (vpuSSRS.MaxRow > 0) {
								// if (amntflag != null
								// &&amntflag.trim().equals("2")) { //按份数
								// prem = vpuSSRS.GetText(1, 1);
								// gene1 = Double.parseDouble(prem) / mult;
								// }
								// if (amntflag != null &&
								// amntflag.trim().equals("1")) { //按保额
								// gene1 = Double.parseDouble(vpu);
								// }
								// }
							}
							standRate = String.valueOf(gene1);
						}

						// ----baimd add 070312------------------
						WithDraw = String.valueOf(strcash); // 风险保额
						WithDrawPrem1 = String.valueOf(strcash);
					}
					if (index == 2) {
						WithDrawPrem2 = String.valueOf(strcash);
					}
				}
			}
		}
		// 主险红利

		ListTable tListTable4 = new ListTable();
		tListTable4.setName("SCALE"); // 对应模版部分的行对象名
		String str4[] = null;
		for (int i = 1; i <= cLCPolSet.size(); i++) {
			LCPolSchema mLCPolSchema = (LCPolSchema) cLCPolSet.get(i);
			String tRiskCode = mLCPolSchema.getRiskCode();
			if (mainRiskcode.equals(tRiskCode)) {

				String tInsuAge1 = insuAge;
				if (!flag1.equals("1")
						&& (tRiskCode.equals("00144000") || tRiskCode
								.equals("00118000"))) {
					if (Integer.parseInt(insuAge) > Integer.parseInt(insuAge1)) {
						tInsuAge1 = insuAge1;
					}
				}

				int j = 0; // 保单年度
				if (insuYear.equals("1000")) {
					j = 100 - Integer.parseInt(tInsuAge1);
					if (j > 100)
						j = 100;
				}
				if (!insuYear.equals("1000") && insuFlag.equals("Y")) {
					j = Integer.parseInt(insuYear);
				}
				if (!insuYear.equals("1000") && insuFlag.equals("A")) {
					j = Integer.parseInt(insuYear)
							- Integer.parseInt(tInsuAge1);
				}
				if (!insuYear.equals("1000") && insuFlag.equals("M")) {
					j = 1;
				}
				LCDutySchema tLCDutySchema = new LCDutySchema();
				tLCDutySchema.setPolNo(mLCPolSchema.getPolNo());
				LCDutyDB tLCDutyDB = new LCDutyDB();
				tLCDutyDB.setSchema(tLCDutySchema);
				LCDutySet tLCDutySet = tLCDutyDB.query();
				if (tLCDutySet == null || tLCDutySet.size() < 1) {
					continue;
				}
				tLCDutySchema = tLCDutySet.get(1);
				int index = 0;
				DecimalFormat df = new DecimalFormat("0.0000");
				SSRS BonusSSRS = this.getBonusCashValueTable(mLCPolSchema,
						tLCDutySchema, 0, j);
				if (BonusSSRS == null || BonusSSRS.MaxRow == 0) {
					flag7 = "1";
					logger.debug("没有取到现金价值！");
				}
				for (int k = 0; BonusSSRS != null && BonusSSRS.MaxRow > 0
						&& k < BonusSSRS.MaxRow; k++) {
					double cash = Double.parseDouble(BonusSSRS
							.GetText(k + 1, 1));
					if (cash == 0 && k == 0) {
						continue;
					}
					index++;
					if (index == 1) {
						mTextTag.add("Title2", mainRiskName + "红利保额现金价值折算比例表");
					}
					String strCash = df.format(cash);
					str4 = new String[2];
					str4[0] = String.valueOf(index);
					str4[1] = strCash;
					tListTable4.add(str4);
				}
			}
		}
		String hh = System.getProperty("/printdata/data/");
		logger.debug("路径======" + hh);
		// 附加险现金价值
		ListTable tListTable11 = new ListTable();
		tListTable11.setName("WORTH11"); // 对应模版部分的行对象名
		String str11[] = null;
		ListTable tListTable12 = new ListTable();
		tListTable12.setName("WORTH12"); // 对应模版部分的行对象名
		String str12[] = null;
		ListTable tListTable13 = new ListTable();
		tListTable13.setName("WORTH13"); // 对应模版部分的行对象名
		String str13[] = null;
		ListTable tListTable14 = new ListTable();
		tListTable14.setName("WORTH14"); // 对应模版部分的行对象名
		String str14[] = null;
		n = 0;
		if (!flag4.equals("1")) { // 最多四个附加险

			logger.debug("cLCPolSet.size()======" + cLCPolSet.size());
			String tRiskCode = "";
			String tRiskName = "";
			for (int i = 1; i <= cLCPolSet.size(); i++) {
				LCPolSchema mLCPolSchema = (LCPolSchema) cLCPolSet.get(i);
				tRiskCode = mLCPolSchema.getRiskCode();
				String tRiskPeriod = "";
				SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
				tSql = "select RiskPeriod,bonusflag From lmriskapp where riskcode='"+"?tRiskCode?"+"'";
				sqlbv35.sql(tSql);
				sqlbv35.put("tRiskCode", tRiskCode);
				tSSRS = tExeSQL.execSQL(sqlbv35);
				if (tSSRS.MaxRow == 0) {
					continue;
				} else {
					tRiskPeriod = tSSRS.GetText(1, 1);
				}
				String bonusTitle = "现金价值表";
				if (tSSRS.MaxRow > 0 && tSSRS.GetText(1, 2).trim().equals("2"))
					bonusTitle = "初始基本保额现金价值表";
				if (!mainRiskcode.equals(tRiskCode)
						&& tRiskPeriod.trim().equals("L")) { // 长期附加险
					ppsql = "select riskstatname from lmrisk where riskcode='"
							+"?tRiskCode?"+ "'";
					SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
					sqlbv36.sql(ppsql);
					sqlbv36.put("tRiskCode", tRiskCode);
					tSSRS = exeSql.execSQL(sqlbv36);
					if (tSSRS.MaxRow == 0) {
						// buildError("getInfo", "此险种在定义时信息存储不全！");
						tRiskName = "";
					} else {
						tRiskName = tSSRS.GetText(1, 1);
					}

					int j = 0; // 保单年度
					if (insuYear.equals("1000")) {
						j = 100 - Integer.parseInt(insuAge);
						if (j > 100)
							j = 100;
					}
					if (!insuYear.equals("1000") && insuFlag.equals("Y")) {
						j = Integer.parseInt(insuYear);
					}
					if (!insuYear.equals("1000") && insuFlag.equals("A")) {
						j = Integer.parseInt(insuYear)
								- Integer.parseInt(insuAge);
					}
					if (!insuYear.equals("1000") && insuFlag.equals("M")) {
						j = 1;
					}
					LCDutySchema tLCDutySchema = new LCDutySchema();
					tLCDutySchema.setPolNo(mLCPolSchema.getPolNo());
					LCDutyDB tLCDutyDB = new LCDutyDB();
					tLCDutyDB.setSchema(tLCDutySchema);
					LCDutySet tLCDutySet = tLCDutyDB.query();
					if (tLCDutySet == null || tLCDutySet.size() < 1) {
						continue;
					}
					tLCDutySchema = tLCDutySet.get(1);
					int index = 0;
					tSql = "select amntflag,vpu from lmduty where dutycode ='"
							+"?dutycode?"+ "'";
					SQLwithBindVariables sqlbv37 = new SQLwithBindVariables();
					sqlbv37.sql(tSql);
					sqlbv37.put("dutycode", tLCDutySchema.getDutyCode());
					double amnt = mLCPolSchema.getAmnt(), mult = mLCPolSchema
							.getMult(), gene = 1;
					String amntflag = "", vpu = "";
					DecimalFormat df = new DecimalFormat("0.00");
					SSRS worthSSRS = this.getCashValueTable(mLCPolSchema,
							tLCDutySchema, 0, j, mLCPolSchema.getCValiDate(),
							"");
					if (worthSSRS == null || worthSSRS.MaxRow == 0) {
						logger.debug(mLCPolSchema.getRiskCode()
								+ "险种没有取到现金价值！");
						continue;
					}

					for (int k = 0; worthSSRS != null && worthSSRS.MaxRow > 0
							&& k < worthSSRS.MaxRow; k++) {
						double cash = Double.parseDouble(worthSSRS.GetText(
								k + 1, 1));
						if (k == 0) {
							SSRS dSSRS = tExeSQL.execSQL(sqlbv37);
							if (dSSRS.MaxRow > 0) {
								amntflag = dSSRS.GetText(1, 1);
								vpu = dSSRS.GetText(1, 2);
								if (amntflag != null
										&& amntflag.trim().equals("2")) { // 按份数
									gene = mult;
								}
								if (amntflag != null
										&& amntflag.trim().equals("1")) { // 按保额
									gene = amnt / (Double.parseDouble(vpu));
								}
							}
						}
						if (cash == 0 && k == 0) {
							continue;
						}
						index++;
						String strcash = df.format(cash * gene);
						if (index == 1) {
							n = n + 1;
						}
						if (n == 1) {
							if (index == 1) {
								WithDrawPrem11 = String.valueOf(strcash);
								mTextTag.add("Title11", tRiskName + bonusTitle);
							}
							addRiskcode1 = tRiskCode;
							if (tRiskCode.equals("00332000"))
								WithDraw1 = WithDrawPrem11;
							str11 = new String[2];
							str11[0] = String.valueOf(index);
							str11[1] = String.valueOf(strcash);
							tListTable11.add(str11);
							if (index == 2) {
								WithDrawPrem21 = String.valueOf(strcash);
							}
						}
						if (n == 2) {
							if (index == 1) {
								mTextTag.add("Title12", tRiskName + bonusTitle);
								WithDrawPrem12 = String.valueOf(strcash);
							}
							addRiskcode2 = tRiskCode;
							if (tRiskCode.equals("00332000"))
								WithDraw1 = WithDrawPrem12;
							str12 = new String[2];
							str12[0] = String.valueOf(index);
							str12[1] = String.valueOf(strcash);
							tListTable12.add(str12);
							if (index == 2) {
								WithDrawPrem22 = String.valueOf(strcash);
							}
						}
						if (n == 3) {
							if (index == 1) {
								WithDrawPrem13 = String.valueOf(strcash);
								mTextTag.add("Title13", tRiskName + bonusTitle);
							}
							addRiskcode3 = tRiskCode;
							if (tRiskCode.equals("00332000"))
								WithDraw1 = WithDrawPrem13;
							str13 = new String[2];
							str13[0] = String.valueOf(index);
							str13[1] = String.valueOf(strcash);
							tListTable13.add(str13);
							if (index == 2) {
								WithDrawPrem23 = String.valueOf(strcash);
							}
						}
						if (n == 4) {
							if (index == 1) {
								mTextTag.add("Title14", tRiskName + bonusTitle);
								WithDrawPrem14 = String.valueOf(strcash);
							}
							addRiskcode4 = tRiskCode;
							if (tRiskCode.equals("00332000"))
								WithDraw1 = WithDrawPrem14;
							str14 = new String[2];
							str14[0] = String.valueOf(index);
							str14[1] = String.valueOf(strcash);
							tListTable14.add(str14);
							if (index == 2) {
								WithDrawPrem24 = String.valueOf(strcash);
							}
						}
					}
				}
			}
		}
		// 附加险红利现金价值
		ListTable tListTable21 = new ListTable();
		tListTable21.setName("SCALE21"); // 对应模版部分的行对象名
		String str21[] = null;

		ListTable tListTable22 = new ListTable();
		tListTable22.setName("SCALE22"); // 对应模版部分的行对象名
		String str22[] = null;

		ListTable tListTable23 = new ListTable();
		tListTable23.setName("SCALE23"); // 对应模版部分的行对象名
		String str23[] = null;

		ListTable tListTable24 = new ListTable();
		tListTable24.setName("SCALE24"); // 对应模版部分的行对象名
		String str24[] = null;
		m = 0;
		if (!flag4.equals("1")) { // 最多四个附加险

			logger.debug("cLCPolSet.size()======" + cLCPolSet.size());
			String tRiskName = "";
			String tRiskCode = "";
			for (int i = 1; i <= cLCPolSet.size(); i++) {
				LCPolSchema mLCPolSchema = (LCPolSchema) cLCPolSet.get(i);
				tRiskCode = mLCPolSchema.getRiskCode();
				String tRiskPeriod = "";
				tSql = "select RiskPeriod From lmriskapp where riskcode='"
						+"?tRiskCode?"+ "'";
				SQLwithBindVariables sqlbv38 = new SQLwithBindVariables();
				sqlbv38.sql(tSql);
				sqlbv38.put("tRiskCode", tRiskCode);
				tSSRS = tExeSQL.execSQL(sqlbv38);
				if (tSSRS.MaxRow == 0) {
					continue;
				} else {
					tRiskPeriod = tSSRS.GetText(1, 1);
				}

				if (!mainRiskcode.equals(tRiskCode)
						&& tRiskPeriod.trim().equals("L")) {
					ppsql = "select riskstatname from lmrisk where riskcode='"
							+"?tRiskCode?"+ "'";
					SQLwithBindVariables sqlbv39 = new SQLwithBindVariables();
					sqlbv39.sql(ppsql);
					sqlbv39.put("tRiskCode", tRiskCode);
					tSSRS = exeSql.execSQL(sqlbv39);
					if (tSSRS.MaxRow == 0) {
						// buildError("getInfo", "此险种在定义时信息存储不全！");
						tRiskName = "";
					} else {
						tRiskName = tSSRS.GetText(1, 1);
					}

					int j = 0; // 保单年度
					if (insuYear.equals("1000")) {
						j = 100 - Integer.parseInt(insuAge);
						if (j > 100)
							j = 100;
					}
					if (!insuYear.equals("1000") && insuFlag.equals("Y")) {
						j = Integer.parseInt(insuYear);
					}
					if (!insuYear.equals("1000") && insuFlag.equals("A")) {
						j = Integer.parseInt(insuYear)
								- Integer.parseInt(insuAge);
					}
					if (!insuYear.equals("1000") && insuFlag.equals("M")) {
						j = 1;
					}
					LCDutySchema tLCDutySchema = new LCDutySchema();
					tLCDutySchema.setPolNo(mLCPolSchema.getPolNo());
					LCDutyDB tLCDutyDB = new LCDutyDB();
					tLCDutyDB.setSchema(tLCDutySchema);
					LCDutySet tLCDutySet = tLCDutyDB.query();
					if (tLCDutySet == null || tLCDutySet.size() < 1) {
						continue;
					}
					tLCDutySchema = tLCDutySet.get(1);
					int index = 0;
					DecimalFormat df = new DecimalFormat("0.0000");
					SSRS BonusSSRS = this.getBonusCashValueTable(mLCPolSchema,
							tLCDutySchema, 0, j);
					if (BonusSSRS == null || BonusSSRS.MaxRow == 0) {
						logger.debug(mLCPolSchema.getRiskCode()
								+ "险种没有取到红利折算比例！");
						continue;
					}
					for (int k = 0; BonusSSRS != null && BonusSSRS.MaxRow > 0
							&& k < BonusSSRS.MaxRow; k++) {
						double cash = Double.parseDouble(BonusSSRS.GetText(
								k + 1, 1));
						if (cash == 0 && k == 0) {
							continue;
						}
						index++;
						String strCash = df.format(cash);
						if (index == 1) {
							m = m + 1;
						}
						if (m == 1) {
							if (index == 1) {
								mTextTag.add("Title21", tRiskName
										+ "红利保额现金价值折算比例表");
							}
							addRiskcode11 = tRiskCode;
							str21 = new String[2];
							str21[0] = String.valueOf(index);
							str21[1] = strCash;
							tListTable21.add(str21);
						}
						if (m == 2) {
							if (index == 1) {
								mTextTag.add("Title22", tRiskName
										+ "红利保额现金价值折算比例表");
							}
							addRiskcode21 = tRiskCode;
							str22 = new String[2];
							str22[0] = String.valueOf(index);
							str22[1] = strCash;
							tListTable22.add(str22);
						}
						if (m == 3) {
							if (index == 1) {
								mTextTag.add("Title23", tRiskName
										+ "红利保额现金价值折算比例表");
							}
							addRiskcode31 = tRiskCode;
							str23 = new String[2];
							str23[0] = String.valueOf(index);
							str23[1] = strCash;
							tListTable23.add(str23);
						}
						if (m == 4) {
							if (index == 1) {
								mTextTag.add("Title24", tRiskName
										+ "红利保额现金价值折算比例表");
							}
							addRiskcode41 = tRiskCode;
							str24 = new String[2];
							str24[0] = String.valueOf(index);
							str24[1] = strCash;
							tListTable24.add(str24);
						}
					}
				}
			}
		}

		// /////////write by yaory////////////
		// ////////////see whether today is festival/////
		// logger.debug("今天的日期=====" + currentDate);
		// int t = currentDate.length();
		// if (currentDate.substring(5, t).equals("01-01") ||
		// currentDate.substring(5, t).equals("05-01") ||
		// currentDate.substring(5, t).equals("10-01"))
		// {
		// flag8 = "1"; //如果是1则显示图片
		//
		// }
		// if (currentDate.substring(5, t).equals("03-08") &&
		// mLCAppntSchema.getAppntSex().equals("1") &&
		// Integer.parseInt(insuAge) >= 25)
		// {
		// flag8 = "1"; //如果是1则显示图片
		// }
		// if (currentDate.substring(5, t).equals("05-04") &&
		// Integer.parseInt(insuAge) >= 18)
		// {
		// flag8 = "1"; //如果是1则显示图片
		// }
		// if (currentDate.substring(5, t).equals("06-01") &&
		// Integer.parseInt(insuAge) <= 14)
		// {
		// flag8 = "1"; //如果是1则显示图片
		// }
		// ppsql = "select occupationcode From ldperson where customerno='" +
		// mLCContSchema.getInsuredNo() + "'";
		// tSSRS = exeSql.execSQL(ppsql);
		// if (tSSRS.MaxRow > 0)
		// {
		// occupationcode = tSSRS.GetText(1, 1);
		// }
		// if (currentDate.substring(5, t).equals("09-10") &&
		// occupationcode.equals("W001001"))
		// {
		// flag8 = "1"; //如果是1则显示图片
		// }
		// ////////////回执/////////////

		xmlexport.createDocument("Contract.vts", "");
		xmlexport.addControlItem("CONTROL", "RiskNo", mainRiskcode);
		xmlexport.addControlItem("CONTROL", "IsGrp", isGrp);

		// logger.debug("哈哈，，，，");
		if (flag8.equals("1")) {
			xmlexport.addControlItem("CONTROL", "individuation", "pic1.jpg");
			// logger.debug("哈哈2，，，，");
		} else if (flag8.equals("2")) {
			xmlexport.addControlItem("CONTROL", "individuation", "pic2.jpg");
		} else if (flag8.equals("3")) {
			xmlexport.addControlItem("CONTROL", "individuation", "pic3.jpg");
		} else if (flag8.equals("4")
				&& mLCContSchema.getInsuredSex().equals("1")
				&& Integer.parseInt(insuAge) >= 25) {
			xmlexport.addControlItem("CONTROL", "individuation", "pic4.jpg");
		} else if (flag8.equals("5")) {
			xmlexport.addControlItem("CONTROL", "individuation", "pic5.jpg");
		} else if (flag8.equals("6") && Integer.parseInt(insuAge) >= 18) {
			xmlexport.addControlItem("CONTROL", "individuation", "pic6.jpg");
		} else if (flag8.equals("7") && Integer.parseInt(insuAge) >= 14) {
			xmlexport.addControlItem("CONTROL", "individuation", "pic7.jpg");
		} else if (flag8.equals("8")) {
			xmlexport.addControlItem("CONTROL", "individuation", "pic8.jpg");
		} else if (flag8.equals("9")) {
			xmlexport.addControlItem("CONTROL", "individuation", "pic9.jpg");
		} else if (flag8.equals("10")
				&& ((Integer.parseInt(insuAge) >= 55 && mLCContSchema
						.getInsuredSex().equals("1")) || (Integer
						.parseInt(insuAge) >= 60 && mLCContSchema
						.getInsuredSex().equals("0")))) {

			xmlexport.addControlItem("CONTROL", "individuation", "pic10.jpg");
		} else if (flag8.equals("11")) {
			ppsql = "select occupationcode From ldperson where customerno='"
					+"?customerno?"+"'";
			SQLwithBindVariables sqlbv40 = new SQLwithBindVariables();
			sqlbv40.sql(ppsql);
			sqlbv40.put("customerno", mLCContSchema.getInsuredNo());
			tSSRS = exeSql.execSQL(sqlbv40);
			if (tSSRS.MaxRow > 0) {
				occupationcode = tSSRS.GetText(1, 1);
			}
			if (occupationcode.equals("W001001")) {
				xmlexport.addControlItem("CONTROL", "individuation",
						"pic11.jpg");
			}
		}

		// 投保人
		mTextTag.add("LCCont.ContNo", mLCAppntSchema.getContNo());
		mTextTag.add("LCCont.AppntName", mLCAppntSchema.getAppntName());
		mTextTag.add("AppntIDType", getIdtype(mLCAppntSchema.getIDType()));
		mTextTag.add("LCCont.AppntIDNo", (mLCAppntSchema.getIDType()
				.equals("9") ? mLCAppntSchema.getAppntBirthday()
				: mLCAppntSchema.getIDNo()));
		logger.debug("投保人的证件号码====" + mLCAppntSchema.getIDNo());
		mTextTag.add("LCCont.AppntSex", getSex(mLCAppntSchema.getAppntSex()));

		// 被保险人
		mTextTag.add("LCCont.InsuredName", insuredName);
		mTextTag.add("InsureIDType", getIdtype(insuredIDType));
		mTextTag.add("LCCont.InsuredIDNo",
				(insuredIDType.equals("9") ? mLCContSchema.getInsuredBirthday()
						: insuredIDNo));
		insuredSex = getSex(insuredSex); // 被保险人性别insuAge
		mTextTag.add("LCCont.InsuredSex", insuredSex);
		mTextTag.add("LCCont.InsuredAppAge", insuAge);
		insuredName = mLCContSchema.getInsuredName(); // 被保险人姓名
		// 第二被保险人
		if (!flag1.equals("1")) {
			mTextTag.add("LCCont.InsuredName1", mSSRS.GetText(1, 13));
			mTextTag.add("InsureIDType1", getIdtype(mSSRS.GetText(1, 16)));
			mTextTag.add("LCCont.InsuredIDNo1", (mSSRS.GetText(1, 16).equals(
					"9") ? mSSRS.GetText(1, 15) : mSSRS.GetText(1, 17)));
			insuSex1 = getSex(mSSRS.GetText(1, 14));
			mTextTag.add("LCCont.InsuredSex1", insuSex1);

		} else {
			logger.debug(" 没有连带被保险人");
		}
		// 基本险

		mTextTag.add("RiskName", mainRiskName);
		mTextTag.add("InsureAmnt", xSSRS.GetText(1, 59));
		// mTextTag.add("DrawMode", getform(xSSRS.GetText(1,
		// 42),xSSRS.GetText(1, 41)));
		mTextTag.add("DrawMode", ygetintv);
		mTextTag.add("DrawTerm", tDrawTerm); // 主险号码为“00628000”时，显示“缴费期限”
		mTextTag.add("InsureCMoney", PubFun.getChnMoney(parseFloat(xSSRS
				.GetText(1, 63))));
		mTextTag.add("InsureMoney", xSSRS.GetText(1, 63));
		mTextTag.add("CPrem", PubFun.getChnMoney(parseFloat(xSSRS
				.GetText(1, 61))));
		mTextTag.add("LCCont.Prem", xSSRS.GetText(1, 61));
		mTextTag.add("CAmnt", PubFun.getChnMoney(parseFloat(xSSRS
				.GetText(1, 63))));
		mTextTag.add("LCCont.Amnt", xSSRS.GetText(1, 63));
		mTextTag.add("ChargeDate", StrTool.getChnDate(xSSRS.GetText(1, 35)));
		if (mainRiskcode.trim().equals("00156000")) {
			mTextTag.add("ExpiryScale", expiryScale);
		}
		// mTextTag.add("LCCont.PayMode", xSSRS.GetText(1, 51));
		// mTextTag.add("LCCont.PayMode",
		// getPayMode(mLCContSchema.getPayMode()));
		mTextTag.add("LCCont.PayMode", getPayendyear(xSSRS.GetText(1, 53)));
		logger.debug("约定交费方式====" + mLCContSchema.getPayIntv());
		mTextTag.add("PayTerm", mMainPayTerm); //
		mTextTag.add("CValiDate", StrTool.getChnDate(mLCContSchema
				.getCValiDate())); // 合同生效日期
		mTextTag.add("DrawAge", getAge(xSSRS.GetText(1, 42), xSSRS.GetText(1,
				41), insuAge)
				+ "周岁");
		mTextTag.add("DrawAge1", getAge(xSSRS.GetText(1, 42), xSSRS.GetText(1,
				41), insuAge)
				+ "周岁");
		// mTextTag.add("DrawAge",xSSRS.GetText(1, 42) );
		logger.debug("领取年龄的两个参数====" + xSSRS.GetText(1, 38) + "-"
				+ xSSRS.GetText(1, 25));
		mTextTag.add("InsureTerm", getInsureTerm(xSSRS.GetText(1, 39), xSSRS
				.GetText(1, 31), xSSRS.GetText(1, 46))); //

		logger.debug("约定领取方式====" + xSSRS.GetText(1, 114));
		// 附加险
		ListTable tListTable2 = new ListTable();
		String str2[] = null;
		tListTable2.setName("RISK"); // 对应模版部分的行对象名
		// logger.debug("测试====" + appSSRS.MaxRow);
		// logger.debug("测试====" + flag4);
		String[] ContractTitle1 = new String[5];
		ContractTitle1[0] = "险种";
		ContractTitle1[1] = "保额/份数";
		ContractTitle1[2] = "保费";
		ContractTitle1[3] = "保险期间";
		ContractTitle1[4] = "险种编码";
		// 717,718险种的特殊处理
		// 判断是否有717，718险种
		if (mainRiskcode.indexOf("717") >= 0
				|| mainRiskcode.indexOf("718") >= 0)
			flag_717_718_1 = true;
		for (int i = 1; !flag_717_718_1 && i <= appSSRS.MaxRow; i++) {
			if (appSSRS.GetText(i, 2).indexOf("717") >= 0
					|| appSSRS.GetText(i, 2).indexOf("718") >= 0) {
				flag_717_718_1 = true;
				flag_717_718_2 = true; // 判断是否有717，718附加险
				break;
			}
		}

		if (flag_717_718_1) { // 只要有717,718险种，就输出如下信息
			ListTable tListTable5 = new ListTable();
			tListTable5.setName("RISK1");
			String[] ContractTitle5 = new String[7]; // 717,718险种使用
			ContractTitle5[0] = "险种名称";
			ContractTitle5[1] = "保险金额";
			ContractTitle5[2] = "保险期间";
			ContractTitle5[3] = "保险费";
			ContractTitle5[4] = "交费期间";
			ContractTitle5[5] = "交费方式";
			ContractTitle5[6] = "险种编码";

			String[] RiskTable5 = new String[7];
			RiskTable5[0] = mainRiskName;
			RiskTable5[1] = format(Double.parseDouble(xSSRS.GetText(1, 63)));
			RiskTable5[2] = getInsureEndTerm(xSSRS.GetText(1, 39), xSSRS
					.GetText(1, 46));
			RiskTable5[3] = format(Double.parseDouble(xSSRS.GetText(1, 61)));
			RiskTable5[4] = getPayEndTerm(xSSRS.GetText(1, 12), xSSRS.GetText(
					1, 31), xSSRS.GetText(1, 36), xSSRS.GetText(1, 53), xSSRS
					.GetText(1, 44), xSSRS.GetText(1, 43));
			RiskTable5[5] = getPayendyear(xSSRS.GetText(1, 53));
			RiskTable5[6] = mainRiskcode;
			tListTable5.add(RiskTable5);
			if (!flag4.equals("1")) {
				for (int i = 1; i <= appSSRS.MaxRow; i++) {
					RiskTable5 = new String[7];
					SQLwithBindVariables sqlbv41 = new SQLwithBindVariables();
					sqlbv41.sql("select riskstatname from lmrisk where riskcode='"+"?riskcode?"+"'");
					sqlbv41.put("riskcode", appSSRS.GetText(i, 2).trim());
					RiskTable5[0] = exeSql.getOneValue(sqlbv41);
					RiskTable5[1] = format(Double.parseDouble(appSSRS.GetText(
							i, 3)));
					RiskTable5[2] = getInsureEndTerm(appSSRS.GetText(i, 12),
							appSSRS.GetText(i, 15));
					RiskTable5[3] = format(Double.parseDouble(appSSRS.GetText(
							i, 5)));
					RiskTable5[4] = getPayEndTerm(appSSRS.GetText(i, 2),
							appSSRS.GetText(i, 9), appSSRS.GetText(i, 10),
							appSSRS.GetText(i, 11), appSSRS.GetText(i, 14),
							appSSRS.GetText(i, 13));
					RiskTable5[5] = getPayendyear(appSSRS.GetText(i, 11));
					RiskTable5[6] = appSSRS.GetText(i, 2);
					tListTable5.add(RiskTable5);
				}
			}
			xmlexport.addDisplayControl("display717");
			xmlexport.addListTable(tListTable5, ContractTitle5);
		}
		if (!flag4.equals("1")) {
			for (int i = 1; i <= appSSRS.MaxRow; i++) {
				// logger.debug("测试附加险====" + appSSRS.GetText(i, 2));
				String triskcode = appSSRS.GetText(i, 2);
				str2 = new String[5];
				str2[0] = appSSRS.GetText(i, 1);
				String hh1 = appSSRS.GetText(i, 3);
				String hh2 = String.valueOf(Integer.parseInt(appSSRS.GetText(i,
						4)));
				String riskperiod = appSSRS.GetText(i, 7);
				if (hh2.equals("0") || triskcode.trim().equals("00280000")
						|| triskcode.trim().equals("00332000")
						|| mainRiskcode.trim().equals("00628000")) {
					str2[1] = format(Double.parseDouble(appSSRS.GetText(i, 3)));
				} else {
					str2[1] = appSSRS.GetText(i, 4);
				}
				str2[2] = appSSRS.GetText(i, 5);
				if (riskperiod.trim().equals("L")
						&& !triskcode.trim().equals("00280000")
						&& !triskcode.trim().equals("00332000")) {
					if (triskcode.trim().equals("00293000")
							|| triskcode.trim().equals("00294000")) {
						str2[3] = StrTool.getChnDate(appSSRS.GetText(i, 9))
								+ "至终身";
					} else
						str2[3] = "同主险";
				} else {
					str2[3] = appSSRS.GetText(i, 6) + "年";
				}
				str2[4] = appSSRS.GetText(i, 2);

				tListTable2.add(str2);
				invoiceRisk = new String[7];
				invoiceRisk[0] = "险种：";
				invoiceRisk[1] = triskcode + "  保费";
				invoiceRisk[2] = format(Double.parseDouble(appSSRS
						.GetText(i, 5)));
				invoiceRisk[3] = triskcode;
				invoiceRisk[4] = "保费";
				SQLwithBindVariables sqlbv42 = new SQLwithBindVariables();
				sqlbv42.sql("Select riskshortname From lmrisk Where riskcode='"
						+"?triskcode?"+ "'");
				sqlbv42.put("triskcode", triskcode);
				invoiceRisk[5] = exeSql.getOneValue(sqlbv42); // 收费项目
				if (appSSRS.GetText(i, 11) != null
						&& appSSRS.GetText(i, 11).trim().equals("0")) { // 根据李\u73A5要求修改，附加险如果是趸交，则其发票上交费起止日期与保单上主险缴费期限一致
					invoiceRisk[6] = mMainPayTerm;
				} else {
					invoiceRisk[6] = getChnPayTermSub(appSSRS.GetText(i, 9),
							appSSRS.GetText(i, 10)); // 收费起止日期
				}

				tListTableInvoice.add(invoiceRisk);
			}
			xmlexport.addListTable(tListTable2, ContractTitle1);
		} else {
			logger.debug("没有附加险信息！");
		}

		// 发票信息
		SSRS testSSRS = new SSRS();
		SQLwithBindVariables sqlbv43 = new SQLwithBindVariables();
		sqlbv43.sql("select sum(prem) from lcpol where contno='"
				+"?contno?"+ "' and appflag = '1'");
		sqlbv43.put("contno", tLCContDB.getContNo());
		testSSRS = exeSql.execSQL(sqlbv43);
		sumprem = testSSRS.GetText(1, 1);
		String sysDate = PubFun.getCurrentDate();
		String PayDate = "";
		if ((salechnl.trim().equals("1") || salechnl.trim().equals("5"))
				&& tLCContDB.getNewPayMode() != null
				&& (tLCContDB.getNewPayMode().trim().equals("1") || tLCContDB
						.getNewPayMode().trim().equals("3"))) {
			PayDate = tLCContDB.getFirstPayDate();
			// if(!manageCom.equals("8632")) sysDate = PayDate;
		} else {
			SQLwithBindVariables sqlbv44 = new SQLwithBindVariables();
			sqlbv44.sql("select to_char(EnterAccDate,'yyyy-mm-dd'),paydate from ljtempfee where OtherNo='"
					+"?OtherNo?"
					+ "' and (othernotype = '6' or othernotype = '7' or othernotype='0') order by paydate");
			sqlbv44.put("OtherNo", tLCContDB.getContNo());
			testSSRS = exeSql.execSQL(sqlbv44);
			if (testSSRS.getMaxRow() > 0) {
				// if(!manageCom.equals("8632")) sysDate =
				// testSSRS.GetText(1,1);
				PayDate = testSSRS.GetText(1, 2);
			}

		}
		if (rePrintType == null) { // 如果不是 保单重打和补发保单
			xmlexport1.createDocument("Invoice_" + manageCom + ".vts", "");
			mTextTag1.add("AreaID", manageCom);
			mTextTag1.add("SysDate", StrTool.getChnDate(sysDate));
			mTextTag1.add("PayToDate", StrTool.getChnDate(PayDate));
			mTextTag1.add("LCCont.AppntName", mLCAppntSchema.getAppntName());

			mTextTag1.add("Money", format(Double.parseDouble(sumprem)));
			mTextTag1.add("CMoney", PubFun.getChnMoney(parseFloat(sumprem)));
			mTextTag1.add("LCCont.ContNo", mLCAppntSchema.getContNo());
			mTextTag1.add("LAAgent.AgentCode", agentcode);
			mTextTag1.add("LAAgent.Name", agentname);
			mTextTag1.add("LABranchGroup.Name", department);
			mTextTag1.add("Address", getAddress(mLCContSchema.getManageCom()));
			invoiceRisk = new String[5];
			xmlexport1.addTextTag(mTextTag1);
			xmlexport1.addListTable(tListTableInvoice, invoiceRisk);
		}

		// 特别约定
		String[] ContractTitle11 = new String[1];
		ContractTitle11[0] = "特别约定";
		ListTable mListTable = new ListTable();
		String mstr[] = null;
		mListTable.setName("SpecAssump"); // 对应模版部分的行对象名
		boolean isEmpty = true;

		if (flag_717_718_2) { // 有717或718附加险, 717，718险种特别约定
			mstr = new String[1];
			String riskName1 = "";
			String riskName2 = "";
			for (int i = 1; i <= appSSRS.MaxRow; i++) {
				if (appSSRS.GetText(i, 2).indexOf("717") >= 0
						|| appSSRS.GetText(i, 2).indexOf("718") >= 0) {
					if (!riskName1.equals(""))
						riskName1 += "和";
					riskName1 += "《" + appSSRS.GetText(i, 1) + "》";
				} else {
					if (!riskName2.equals(""))
						riskName2 += "、";
					riskName2 += "《" + appSSRS.GetText(i, 1) + "》";
				}
			}
			// 只要客户将717或718作为附加险与其他主险同时投保，在保单的特别约定栏输出如下特别约定内容
			mstr[0] = "本保险单的险种《" + mainRiskName
					+ "》的效力因发生保险责任、责任免除、解除等事项终止时，险种" + riskName1 + "的效力终止。";
			mListTable.add(mstr);
			if (!riskName2.equals("")) { // 只要保单下还附加其他非717或718的附加险，则保单的特别约定栏中增加输出如下特别约定内容。
				mstr = new String[1];
				mstr[0] = "本保险单的附加险种" + riskName2 + "仅为险种《" + mainRiskName
						+ "》的附加险，其效力不受本保险单的其他险种效力的影响。";
				mListTable.add(mstr);
			}
			isEmpty = false;
		}
		if (!flag5.equals("1")) {
			// 改为列表模式
			for (int i = 1; i <= specSSRS.MaxRow; i++) {
				mstr = new String[1];
				// mstr[0] = specSSRS.GetText(specSSRS.MaxRow, 10);
				mstr[0] = specSSRS.GetText(i, 10);
				mListTable.add(mstr);
				isEmpty = false;
			}
			// xmlexport.addListTable(mListTable, ContractTitle11);
			// mTextTag.add("SpecAssump", specSSRS.GetText(1, 10));
		}
		calMap = new MMap();
		calMap.put("ManageCom", tManageCom);
		boolean calCond;
		if (this.DoCalculator("PRT001", calMap).GetText(1, 1).trim()
				.equals("1")) {
			calCond = true;
		} else {
			calCond = false;
		}

		calMap = new MMap();
		calMap.put("MainRiskcode", mainRiskcode);
		boolean calCond1;
		if (this.DoCalculator("PRT002", calMap).GetText(1, 1).trim()
				.equals("1")) {
			calCond1 = true;
		} else {
			calCond1 = false;
		}

		boolean calCond2;
		if (this.DoCalculator("PRT003", calMap).GetText(1, 1).trim()
				.equals("1")) {
			calCond2 = true;
		} else {
			calCond2 = false;
		}

		// if (mainRiskcode.equals("00265000") ||
		// mainRiskcode.equals("00227000") ||
		// mainRiskcode.equals("00232000") || mainRiskcode.equals("00235000") ||
		// mainRiskcode.equals("00240000") || mainRiskcode.equals("00268000") ||
		// mainRiskcode.equals("00236000") || mainRiskcode.equals("00201000") ||
		// mainRiskcode.equals("00201001") || mainRiskcode.equals("00210000") ||
		// mainRiskcode.equals("00210001") || mainRiskcode.equals("00231000") ||
		// mainRiskcode.equals("00215000") || mainRiskcode.equals("00216000") ||
		// mainRiskcode.equals("00233000") || mainRiskcode.equals("00234000") ||
		// mainRiskcode.equals("00701000"))
		if (calCond1) {
			if (appSSRS.MaxRow > 0) {
				for (int i = 1; i <= appSSRS.MaxRow; i++) {
					if (appSSRS.GetText(i, 2).trim().equals("00266000")) {
						mstr = new String[1];
						mstr[0] = "有关附加传染性非典型肺炎疾病保险的特别约定，见本保险单附件";
						xmlexport.addDisplayControl("displaySARS");
						mListTable.add(mstr);
						// xmlexport.addListTable(mListTable, ContractTitle11);
						isEmpty = false;
						break;
					}
				}
			}
		}
		// 这些险种，只要主险保额超过规定，就打印特约，如果是608 609 613，并且主险保额不足规定，
		// 但有332附件险，且附加险保额超过规定，也打印特约
		// else if ((mainRiskcode.equals("00608000") ||
		// mainRiskcode.equals("00609000") || mainRiskcode.equals("00613000")
		// ||mainRiskcode.equals("00602000") || mainRiskcode.equals("00603000")
		// ||mainRiskcode.equals("01227000") || mainRiskcode.equals("02232000")
		// ||mainRiskcode.equals("00153000") ||
		// mainRiskcode.equals("00614000")||mainRiskcode.equals("00628000")
		// ||mainRiskcode.equals("00615000")))
		else if (calCond2) {
			ExeSQL rexeSql = new ExeSQL();
			SQLwithBindVariables sqlbv45 = new SQLwithBindVariables();
			sqlbv45.sql("select InsuredAppAge from lcpol where ContNo='"
					+"?ContNo?"
					+ "' and polno = mainpolno and appflag = '1'");
			sqlbv45.put("ContNo", mLCContSchema.getContNo());
			String tInsuredAge = rexeSql.getOneValue(sqlbv45);

			String mark = "";
			if (tInsuredAge == null || tInsuredAge.trim().equals("")) {
				buildError("getPolPrintData", "没有取到被保人年龄");
				return false;
			}
			int tIntInsuredAge = Integer.parseInt(tInsuredAge);
			if (tIntInsuredAge < 18) {
				float amnt1 = parseFloat(xSSRS.GetText(1, 63)); // 主险保额
				float leave1;

				if (mainRiskcode.trim().equals("00628000"))
					amnt1 = amnt1 * 5;

				leave1 = amnt1 - parseFloat(WithDraw); // 主险风险保额

				if (mainRiskcode.trim().equals("00903000")
						|| mainRiskcode.trim().equals("00904000")) {
					leave1 = parseFloat(xSSRS.GetText(1, 61)) * 2;

				}

				// 对于00639000和00640000累计危险保额的计算：
				if (mainRiskcode.trim().equals("00639000")
						|| mainRiskcode.trim().equals("00640000")) {
					leave1 = amnt1 * 2;
				}

				float amnt2 = 0, leave2 = 0;
				if (!flag4.equals("1")
						&& (mainRiskcode.equals("00608000")
								|| mainRiskcode.equals("00609000") || mainRiskcode
								.equals("00613000"))) {
					// 如果附加险是00322000 ，计算其风险保额。
					for (int j = 1; j <= appSSRS.MaxRow; j++) {
						if (appSSRS.GetText(j, 2).equals("00332000")) {
							mark = "1";
							amnt2 = parseFloat(appSSRS.GetText(j, 3)); // 322附加险保额
							leave2 = amnt2 - parseFloat(WithDraw1); // 322险风险保额WithDraw1
						}
					}
				}
				if ((leave1 > 100000 || leave2 > 100000) && calCond)
				// (tManageCom.equals("8621")||tManageCom.equals("8622")||tManageCom.equals("862300")))
				{
					// manageCom还需要校验管理机构
					mstr = new String[1];
					mstr[0] = "父母以未满18周岁的子女为被保险人投保含有死亡责任的保险，被保险人18周岁以前的身故保险金额（分红类保险以基本保险金额计算）累计不超过10万元，对身故保险金额超出10万元部分对应的保费，本公司予以返还，本合同效力即行终止。";
					mListTable.add(mstr);
					// xmlexport.addListTable(mListTable, ContractTitle11);

					// xmlexport.addDisplayControl("displayBaBy");
					// mTextTag.add("Remark", insuredRemark);
					isEmpty = false;
				}
				if ((leave1 > 50000 || leave2 > 50000) && !calCond)
				// !(tManageCom.equals("8621")||tManageCom.equals("8622")||tManageCom.equals("862300")))
				{
					mstr = new String[1];
					mstr[0] = "父母以未满18周岁的子女为被保险人投保含有死亡责任的保险，被保险人18周岁以前的身故保险金额（分红类保险以基本保险金额计算）累计不超过5万元，对身故保险金额超出5万元部分对应的保费，本公司予以返还，本合同效力即行终止。";
					mListTable.add(mstr);
					// xmlexport.addListTable(mListTable, ContractTitle11);

					// xmlexport.addDisplayControl("displayBaBy");
					// mTextTag.add("Remark", insuredRemark);
					isEmpty = false;
				}
			}
		} else if (mainRiskcode.equals("00902000")) {
			ExeSQL rexeSql = new ExeSQL();
			SSRS nSSRS = new SSRS();
			SQLwithBindVariables sqlbv46 = new SQLwithBindVariables();
			sqlbv46.sql("select mult from lcpol where ContNo='"
					+"?ContNo?"
					+ "' and polno = mainpolno and appflag = '1' and InsuredAppAge < 18");
			sqlbv46.put("ContNo", mLCContSchema.getContNo());
			nSSRS = rexeSql.execSQL(sqlbv46);
			String appendmark = "0";
			for (int j = 1; j <= nSSRS.MaxRow; j++) {
				if (parseFloat(nSSRS.GetText(j, 1)) > 250 && !calCond)
				// !(tManageCom.equals("8621")||tManageCom.equals("8622")||tManageCom.equals("862300")))
				{
					insuredRemark = "父母以未满18周岁的子女为被保险人投保含有死亡责任的保险，被保险人18周岁以前的身故保险金额（分红类保险以基本保险金额计算）累计不超过5万元，对身故保险金额超出5万元部分对应的保费，本公司予以返还，本合同效力即行终止。";
					appendmark = "1";
				}
				if (parseFloat(nSSRS.GetText(j, 1)) > 500 && calCond)
				// (tManageCom.equals("8621")||tManageCom.equals("8622")||tManageCom.equals("862300")))
				{
					insuredRemark = "父母以未满18周岁的子女为被保险人投保含有死亡责任的保险，被保险人18周岁以前的身故保险金额（分红类保险以基本保险金额计算）累计不超过10万元，对身故保险金额超出10万元部分对应的保费，本公司予以返还，本合同效力即行终止。";
					appendmark = "1";
				}
			}
			if (nSSRS.MaxRow > 0 && appendmark.equals("1")) {
				mstr = new String[1];
				mstr[0] = insuredRemark;
				mListTable.add(mstr);
				// xmlexport.addListTable(mListTable, ContractTitle11);

				// xmlexport.addDisplayControl("displayBaBy");
				// mTextTag.add("Remark", insuredRemark);
				isEmpty = false;
			}
		}

		if (isEmpty) {
			mstr = new String[1];
			mstr[0] = "本栏以下空白";
			mListTable.add(mstr);
		}
		xmlexport.addListTable(mListTable, ContractTitle11);

		// 备注信息
		// if(mainRiskcode.equals("01227000")||mainRiskcode.equals("01266000")||mainRiskcode.equals("02232000")||mainRiskcode.equals("02266000")||mainRiskcode.equals("03235000")||mainRiskcode.equals("03266000"))
		if (mainRiskcode.equals("01227000") || mainRiskcode.equals("02232000")
				|| mainRiskcode.equals("03235000")) {
			insuredRemark = "本保单项下附加了《附加传染性非典型肺炎疾病保险》，保险期间为1年，凡本附加险责任项下的保险事故发生时，主险合同生效已满10日的，本公司将按主险合同当时有效的保险金额全额给付保险金。";
			mTextTag.add("Remark", insuredRemark); // edig by yaory只对三个附加特约起作用
		}

		mTextTag.add("ManageComName", manageComName);
		if (RIBD.equals(rePrintType))
			mTextTag.add("Reissue", "补  发"); // 补发保单时添加;
		if (rePrintType != null) {
			mTextTag.add("ReSysDate", StrTool.getChnDate(currentDate));
			tSql = "select to_char(max(makedate),'yyyy-mm-dd') from ldcontinvoicemap where opertype = '1' and contno = '"
					+"?contno?"+ "'";
			SQLwithBindVariables sqlbv47 = new SQLwithBindVariables();
			sqlbv47.sql(tSql);
			sqlbv47.put("contno", mLCContSchema.getContNo());
			String printdate = tExeSQL.getOneValue(sqlbv47);
			if (printdate == null || printdate.trim().equals(""))
				printdate = mLCContSchema.getSignDate();
			if (printdate == null || printdate.trim().equals(""))
				printdate = PubFun.getCurrentDate();
			mTextTag.add("LCCont.SignDate", StrTool.getChnDate(printdate));
		} else {
			mTextTag.add("LCCont.SignDate", StrTool.getChnDate(PubFun
					.getCurrentDate()));
		}
		mTextTag.add("SignDate", StrTool
				.getChnDate(mLCContSchema.getSignDate()));
		// 业务员信息
		mTextTag.add("LAAgent.AgentCode", agentcode);
		mTextTag.add("LAAgent.Name", agentname);
		mTextTag.add("LABranchGroup.Name", department);
		mTextTag.add("ManageComAddress", getAddress(mLCContSchema
				.getManageCom()));
		mTextTag.add("ServicePhone", "95567"); // getPhone(mLCAppntSchema.getManageCom())
		// 现金价值表被保险人姓名，性别
		mTextTag.add("AppAge", insuAge);
		mTextTag.add("Sex", insuredSex);
		// ----baimd add 070312
		mTextTag.add("StandRate", standRate);
		// ----baimd add 070312
		if (!flag1.equals("1")
				&& (mainRiskcode.equals("00144000")
						|| mainRiskcode.equals("00146000")
						|| mainRiskcode.equals("00150000")
						|| mainRiskcode.equals("00118000") || mainRiskcode
						.equals("00118001"))) {
			mTextTag.add("AppAge1", insuAge1);
			mTextTag.add("Sex1", insuSex1);
		}
		// 需要对不同的险种处理
		if (!flag6.equals("1")) {
			if (mainRiskcode.equals("00108000")
					|| mainRiskcode.equals("00108001")) {
				ppsql = "select rate From cv_rate where riskcode='"
						+"?mainRiskcode?"+ "' and duration='1'";
				SQLwithBindVariables sqlbv48 = new SQLwithBindVariables();
				sqlbv48.sql(ppsql);
				sqlbv48.put("mainRiskcode", mainRiskcode);
				tSSRS = exeSql.execSQL(sqlbv48);
				double backmoney = parseFloat(mainsumprem)
						* parseFloat(tSSRS.GetText(1, 1));
				WithDrawPrem1 = this.format(backmoney);
				ppsql = "select rate From cv_rate where riskcode='"+"?mainRiskcode?"+"' and duration='2'";
				SQLwithBindVariables sqlbv49 = new SQLwithBindVariables();
				sqlbv49.sql(ppsql);
				sqlbv49.put("mainRiskcode", mainRiskcode);
				tSSRS = exeSql.execSQL(sqlbv49);
				backmoney = parseFloat(mainsumprem)
						* parseFloat(tSSRS.GetText(1, 1));
				WithDrawPrem2 = this.format(backmoney);
			} else if (mainRiskcode.equals("00104000")
					|| mainRiskcode.equals("00107000")
					|| mainRiskcode.equals("00109000")
					|| mainRiskcode.equals("00110A00")
					|| mainRiskcode.equals("00110B00")
					|| mainRiskcode.equals("00201000")
					|| mainRiskcode.equals("00210000")) {
				ppsql = "select rate/100 From cv_rate where riskcode='"
						+"?mainRiskcode?"+"' and payintv='"+"?payintv?"
						+ "' and payendyear='"+"?payendyear?"
						+"' and duration='1'";
				SQLwithBindVariables sqlbv50 = new SQLwithBindVariables();
				sqlbv50.sql(ppsql);
				sqlbv50.put("mainRiskcode", mainRiskcode);
				sqlbv50.put("payintv", payintv);
				sqlbv50.put("payendyear", payendyear);
				tSSRS = exeSql.execSQL(sqlbv50);
				double backmoney = parseFloat(mainsumprem)
						* parseFloat(tSSRS.GetText(1, 1));
				WithDrawPrem1 = this.format(backmoney);
				ppsql = "select rate/100 From cv_rate where riskcode='"
						+"?mainRiskcode?"+"' and payintv='"+"?payintv?"
						+ "' and payendyear='"+"?payendyear?"
						+ "' and duration='2'";
				SQLwithBindVariables sqlbv51 = new SQLwithBindVariables();
				sqlbv51.sql(ppsql);
				sqlbv51.put("mainRiskcode", mainRiskcode);
				sqlbv51.put("payintv", payintv);
				sqlbv51.put("payendyear", payendyear);
				tSSRS = exeSql.execSQL(sqlbv51);
				backmoney = parseFloat(mainsumprem)
						* parseFloat(tSSRS.GetText(1, 1));
				WithDrawPrem2 = this.format(backmoney);
			}
			mTextTag.add("WithDrawPrem1", WithDrawPrem1);
			mTextTag.add("WithDrawPrem2", WithDrawPrem2);
		}
		if (n == 1) {
			mTextTag.add("WithDrawPrem11", WithDrawPrem11);
			mTextTag.add("WithDrawPrem21", WithDrawPrem21);
		}
		if (n == 2) {
			mTextTag.add("WithDrawPrem11", WithDrawPrem11);
			mTextTag.add("WithDrawPrem21", WithDrawPrem21);
			mTextTag.add("WithDrawPrem12", WithDrawPrem12);
			mTextTag.add("WithDrawPrem22", WithDrawPrem22);
		}
		if (n == 3) {
			mTextTag.add("WithDrawPrem11", WithDrawPrem11);
			mTextTag.add("WithDrawPrem21", WithDrawPrem21);
			mTextTag.add("WithDrawPrem12", WithDrawPrem12);
			mTextTag.add("WithDrawPrem22", WithDrawPrem22);
			mTextTag.add("WithDrawPrem13", WithDrawPrem13);
			mTextTag.add("WithDrawPrem23", WithDrawPrem23);
		}
		if (n == 4) {
			mTextTag.add("WithDrawPrem11", WithDrawPrem11);
			mTextTag.add("WithDrawPrem21", WithDrawPrem21);
			mTextTag.add("WithDrawPrem12", WithDrawPrem12);
			mTextTag.add("WithDrawPrem22", WithDrawPrem22);
			mTextTag.add("WithDrawPrem13", WithDrawPrem13);
			mTextTag.add("WithDrawPrem23", WithDrawPrem23);
			mTextTag.add("WithDrawPrem14", WithDrawPrem14);
			mTextTag.add("WithDrawPrem24", WithDrawPrem24);
		}

		SQLwithBindVariables sqlbv52 = new SQLwithBindVariables();
		sqlbv52.sql("select remark from lccont where contno='"
				+"?contno?"+"'");
		sqlbv52.put("contno", tLCContDB.getContNo());
		mTextTag.add("OtherStatement", exeSql.getOneValue(sqlbv52));
		mTextTag.add("CSumMoney", PubFun.getChnMoney(parseFloat(sumprem)));
		mTextTag.add("SumMoney", sumprem);

		if (mTextTag.size() > 0) {
			xmlexport.addTextTag(mTextTag);
		}
		// 受益人
		String[] ContractTitle = new String[5];
		ContractTitle[0] = "受益人";
		ContractTitle[1] = "证件号";
		ContractTitle[2] = "受益顺序";
		ContractTitle[3] = "受益份额";
		ContractTitle[4] = "被保险人";

		ListTable tListTable = new ListTable();
		String str[] = null;
		tListTable.setName("BENEFIT"); // 对应模版部分的行对象名
		// logger.debug("测试====" + bSSRS.MaxRow);
		// logger.debug("测试====" + flag2);
		if (!flag2.equals("1")) {
			for (int i = 1; i <= bSSRS.MaxRow; i++) {
				// logger.debug("测试第一受益人====" + bSSRS.GetText(i, 10));
				// logger.debug("测试====" + bSSRS.GetText(i, 14));
				// logger.debug("测试====" + bSSRS.GetText(i, 5));
				// logger.debug("测试====" + bSSRS.GetText(i, 8));

				str = new String[5];
				str[0] = bSSRS.GetText(i, 1);
				str[1] = getIdtype(bSSRS.GetText(i, 2)) + bSSRS.GetText(i, 3);
				str[2] = bSSRS.GetText(i, 4);
				if (parseFloat(bSSRS.GetText(i, 5)) == 0)
					str[3] = "";
				else
					str[3] = format(Double.parseDouble(bSSRS.GetText(i, 5)) * 100)
							+ "%";
				str[4] = bSSRS.GetText(i, 6);
				tListTable.add(str);
			}
			xmlexport.addListTable(tListTable, ContractTitle);
		} else {
			logger.debug("没有主险的受益人信息！");
			str = new String[5];
			str[0] = "法定继承人";
			str[1] = "";
			str[2] = "";
			str[3] = "";
			str[4] = "";
			tListTable.add(str);
			xmlexport.addListTable(tListTable, ContractTitle);

		}
		// 主险第二被保险人受益人信息
		ListTable tListTable1 = new ListTable();
		String str1[] = null;
		tListTable1.setName("BENEFIT1"); // 对应模版部分的行对象名
		// logger.debug("测试====" + sbSSRS.MaxRow);
		// logger.debug("测试====" + flag3);

		if (!flag3.equals("1")) {

			for (int i = 1; i <= sbSSRS.MaxRow; i++) {
				str1 = new String[5];
				str1[0] = sbSSRS.GetText(i, 1);
				str1[1] = getIdtype(sbSSRS.GetText(i, 2))
						+ sbSSRS.GetText(i, 3);
				str1[2] = sbSSRS.GetText(i, 4);
				if (parseFloat(sbSSRS.GetText(i, 5)) == 0)
					str1[3] = "";
				else
					str1[3] = format(parseFloat(sbSSRS.GetText(i, 5)) * 100)
							+ "%";
				str1[4] = sbSSRS.GetText(i, 6);

				tListTable1.add(str1);
			}
			ContractTitle = new String[5];
			xmlexport.addListTable(tListTable1, ContractTitle);
		} else {
			if (mainRiskcode.equals("00150000")) {
				str1 = new String[5];
				str1[0] = "法定继承人";
				str1[1] = "";
				str1[2] = "";
				str1[3] = "";
				str1[4] = "";
				tListTable1.add(str1);
				ContractTitle = new String[5];
				xmlexport.addListTable(tListTable1, ContractTitle);
			}
			logger.debug("没有主险第二被保险人的受益人信息！");
		}
		// 扫描影像
		if (tListTableURL != null) {
			xmlexport.addListTable(tListTableURL, strURLTitle);
			xmlexport.addDisplayControl("displayURL");
		}

		// 主险现金价值

		// logger.debug("测试现金价值标志====" + flag6);

		String[] ContractTitle2 = new String[2];
		ContractTitle2[0] = "保单年度";
		ContractTitle2[1] = "现金价值";

		if (!flag6.equals("1")) {
			ContractTitle2 = new String[2];
			ContractTitle2[0] = mainRiskcode;
			xmlexport.addListTable(tListTable3, ContractTitle2);

		} else {
			logger.debug("没有主险的现金价值信息！");
		}
		if (!flag7.equals("1")) {
			ContractTitle2 = new String[2];
			ContractTitle2[0] = mainRiskcode;
			xmlexport.addListTable(tListTable4, ContractTitle2);
			xmlexport.addDisplayControl("display2");
		} else {
			logger.debug("没有主险的红利现金价值信息！");
		}

		// 附加险现金价值
		if (n == 1) {
			ContractTitle2 = new String[2];
			ContractTitle2[0] = addRiskcode1;
			xmlexport.addListTable(tListTable11, ContractTitle2);
			xmlexport.addDisplayControl("display11");
		}
		if (n == 2) {
			ContractTitle2 = new String[2];
			ContractTitle2[0] = addRiskcode1;
			xmlexport.addListTable(tListTable11, ContractTitle2);
			ContractTitle2 = new String[2];
			ContractTitle2[0] = addRiskcode2;
			xmlexport.addListTable(tListTable12, ContractTitle2);
			xmlexport.addDisplayControl("display11");
			xmlexport.addDisplayControl("display12");
		}
		if (n == 3) {
			ContractTitle2 = new String[2];
			ContractTitle2[0] = addRiskcode1;
			xmlexport.addListTable(tListTable11, ContractTitle2);
			ContractTitle2 = new String[2];
			ContractTitle2[0] = addRiskcode2;
			xmlexport.addListTable(tListTable12, ContractTitle2);
			ContractTitle2 = new String[2];
			ContractTitle2[0] = addRiskcode3;
			xmlexport.addListTable(tListTable13, ContractTitle2);
			xmlexport.addDisplayControl("display11");
			xmlexport.addDisplayControl("display12");
			xmlexport.addDisplayControl("display13");
		}
		if (n == 4) {
			ContractTitle2 = new String[2];
			ContractTitle2[0] = addRiskcode1;
			xmlexport.addListTable(tListTable11, ContractTitle2);
			ContractTitle2 = new String[2];
			ContractTitle2[0] = addRiskcode2;
			xmlexport.addListTable(tListTable12, ContractTitle2);
			ContractTitle2 = new String[2];
			ContractTitle2[0] = addRiskcode3;
			xmlexport.addListTable(tListTable13, ContractTitle2);
			ContractTitle2 = new String[2];
			ContractTitle2[0] = addRiskcode4;
			xmlexport.addListTable(tListTable14, ContractTitle2);
			xmlexport.addDisplayControl("display11");
			xmlexport.addDisplayControl("display12");
			xmlexport.addDisplayControl("display13");
			xmlexport.addDisplayControl("display14");

		}
		// 附加险红利现金价值
		if (m == 1) {
			ContractTitle2 = new String[2];
			ContractTitle2[0] = addRiskcode11;
			xmlexport.addListTable(tListTable21, ContractTitle2);
			xmlexport.addDisplayControl("display21");
		}
		if (m == 2) {
			ContractTitle2 = new String[2];
			ContractTitle2[0] = addRiskcode11;
			xmlexport.addListTable(tListTable21, ContractTitle2);
			ContractTitle2 = new String[2];
			ContractTitle2[0] = addRiskcode21;
			xmlexport.addListTable(tListTable22, ContractTitle2);
			xmlexport.addDisplayControl("display21");
			xmlexport.addDisplayControl("display22");
		}
		if (m == 3) {
			ContractTitle2 = new String[2];
			ContractTitle2[0] = addRiskcode11;
			xmlexport.addListTable(tListTable21, ContractTitle2);
			ContractTitle2 = new String[2];
			ContractTitle2[0] = addRiskcode21;
			xmlexport.addListTable(tListTable22, ContractTitle2);
			ContractTitle2 = new String[2];
			ContractTitle2[0] = addRiskcode31;
			xmlexport.addListTable(tListTable23, ContractTitle2);
			xmlexport.addDisplayControl("display21");
			xmlexport.addDisplayControl("display22");
			xmlexport.addDisplayControl("display23");
		}
		if (m == 4) {
			ContractTitle2 = new String[2];
			ContractTitle2[0] = addRiskcode11;
			xmlexport.addListTable(tListTable21, ContractTitle2);
			ContractTitle2 = new String[2];
			ContractTitle2[0] = addRiskcode21;
			xmlexport.addListTable(tListTable22, ContractTitle2);
			ContractTitle2 = new String[2];
			ContractTitle2[0] = addRiskcode31;
			xmlexport.addListTable(tListTable23, ContractTitle2);
			ContractTitle2 = new String[2];
			ContractTitle2[0] = addRiskcode41;
			xmlexport.addListTable(tListTable24, ContractTitle2);
			xmlexport.addDisplayControl("display21");
			xmlexport.addDisplayControl("display22");
			xmlexport.addDisplayControl("display23");
			xmlexport.addDisplayControl("display24");

		}

		// 其他的
		if (rePrintType == null) { // 如果不是 保单重打和补发保单
			mTextTag2.add("Invoice", " ");
			xmlexport.addTextTag(mTextTag2);

			xmlexport.addControlItem("Invoice", "LmRisk.RiskName", maipolName);
			xmlexport
					.addControlItem("Invoice", "LmRisk.RiskName1", maipolName1);
			xmlexport
					.addControlItem("Invoice", "LmRisk.RiskName2", maipolName2);
			xmlexport.addControlItem("Invoice", "PayIntv", payIntvName);
			xmlexport.addControlItem("Invoice", "PayEnd", payEnd);
			xmlexport.addControlItem("Invoice", "PayTerm", mInvoicePayTerm);
			xmlexport.addControlItem("Invoice", "PayMode", payStyleName);
			xmlexport.addControlItem("Invoice", "LjTempFee.Oper", oper);
			if ((StrTool.getChnDate(PayDate)).length() == 11) {
				xmlexport.addControlItem("Invoice", "FeeYear", StrTool
						.getChnDate(PayDate).substring(0, 4));
				xmlexport.addControlItem("Invoice", "FeeMonth", StrTool
						.getChnDate(PayDate).substring(5, 7));
				xmlexport.addControlItem("Invoice", "FeeDay", StrTool
						.getChnDate(PayDate).substring(8, 10));
			}
			xmlexport.addControlItem("Invoice", "LjTempFee.ManageCom",
					managecomName);
			xmlexport.addControlItem("Invoice", "AreaID", manageCom);
			xmlexport.addControlItem("Invoice", "SysDate", StrTool
					.getChnDate(sysDate));
			xmlexport.addControlItem("Invoice", "SysYear", StrTool.getChnDate(
					sysDate).substring(0, 4));
			xmlexport.addControlItem("Invoice", "SysMonth", StrTool.getChnDate(
					sysDate).substring(5, 7));
			xmlexport.addControlItem("Invoice", "SysDay", StrTool.getChnDate(
					sysDate).substring(8, 10));
			xmlexport.addControlItem("Invoice", "PayToDate", StrTool
					.getChnDate(PayDate));
			xmlexport.addControlItem("Invoice", "LCCont.AppntName",
					mLCAppntSchema.getAppntName());
			xmlexport.addControlItem("Invoice", "CMoney", PubFun
					.getChnMoney(parseFloat(sumprem)));
			xmlexport.addControlItem("Invoice", "Money", format(Double
					.parseDouble(sumprem)));
			xmlexport.addControlItem("Invoice", "LCCont.ContNo", mLCAppntSchema
					.getContNo());
			xmlexport.addControlItem("Invoice", "LAAgent.AgentCode", agentcode);
			xmlexport.addControlItem("Invoice", "LAAgent.Name", agentname);
			xmlexport.addControlItem("Invoice", "LABranchGroup.Name",
					department);
			xmlexport.addControlItem("Invoice", "Address",
					getAddress(mLCContSchema.getManageCom()));
			invoiceRisk = new String[7];
			xmlexport.addListTableToEle(xmlexport.getDocument()
					.getRootElement().element("Invoice"), tListTableInvoice,
					invoiceRisk);
		}
		// logger.debug("哈哈4，，，，");

		mTextTag3.add("receipt", " ");

		xmlexport.addTextTag(mTextTag3);
		xmlexport.addControlItem("receipt", "LCCont.ContNo", mLCAppntSchema
				.getContNo());
		xmlexport.addControlItem("receipt", "LCCont.AppntName", mLCAppntSchema
				.getAppntName());
		String tAppntName1 = mLCAppntSchema.getAppntName();
		if ("0".equals(mLCAppntSchema.getAppntSex())) {
			tAppntName1 = mLCAppntSchema.getAppntName() + "先生";
		} else if ("1".equals(mLCAppntSchema.getAppntSex())) {
			tAppntName1 = mLCAppntSchema.getAppntName() + "女士";
		}
		xmlexport.addControlItem("receipt", "LCCont.AppntName1", tAppntName1);

		if (!mobile.equals("") && mobile != null) {
			Tel = mobile;
		} else
			Tel = companyphone;
		xmlexport.addControlItem("receipt", "Tel", Tel);
		xmlexport.addControlItem("receipt", "ZipCode", ZipCode);
		xmlexport.addControlItem("receipt", "BankAccNo", mLCContSchema
				.getBankAccNo());
		xmlexport.addControlItem("receipt", "Address", appntAddress);
		xmlexport.addControlItem("receipt", "AgentName", agentname);
		xmlexport.addControlItem("receipt", "AgentCode", mLCContSchema
				.getAgentCode());
		xmlexport.addControlItem("receipt", "SignDate", StrTool
				.getChnDate(mLCContSchema.getSignDate()));
		xmlexport.addControlItem("receipt", "LCCont.SignDate", StrTool
				.getChnDate(PubFun.getCurrentDate()));
		SQLwithBindVariables sqlbv53 = new SQLwithBindVariables();
		sqlbv53.sql("select tempFeeNo from ljtempfee where OtherNo='"
				+"?OtherNo?"
				+ "' and (othernotype = '6' or othernotype = '7' or othernotype = '0')");
		sqlbv53.put("OtherNo", mLCContSchema.getContNo());
		String tempFeeNo = exeSql.getOneValue(sqlbv53);
		xmlexport.addControlItem("receipt", "TempFeeNo", tempFeeNo);
		SQLwithBindVariables sqlbv54 = new SQLwithBindVariables();
		sqlbv54.sql("select branchAttr from LABranchGroup where agentgroup='"
				+"?agentgroup?"+"'");
		sqlbv54.put("agentgroup", mLCContSchema.getAgentGroup());
		String branchAttr = exeSql.getOneValue(sqlbv54);

		if (salechnl.trim().equals("3")) {
			SQLwithBindVariables sqlbv55 = new SQLwithBindVariables();
			sqlbv55.sql("select bankcode from lacom where agentcom ='"
					+"?agentcom?"+ "'");
			sqlbv55.put("agentcom", mLCContSchema.getAgentCom().trim());
			String tBankCode = exeSql.getOneValue(sqlbv55);
			if (tBankCode == null)
				tBankCode = "";

			xmlexport.addControlItem("receipt", "BranchAttr", tBankCode);
		} else
			xmlexport.addControlItem("receipt", "BranchAttr", branchAttr);

		xmlexport.addControlItem("receipt", "AgentCom", mLCContSchema
				.getAgentCom());

		// xmlexport.outputDocumentToFile("d:\\",
		// "LCContF1PBL"+PubFun.getCurrentTime().toString().replaceAll(":","_"));
		return true;
	}

	private String getform(String s1, String s2) {
		String returnvalue = "";
		if (s2.equals("Y")) {
			returnvalue = "年领";
		} else if (s2.equals("A")) {
			returnvalue = s1 + "周岁";
		}
		return returnvalue;
	}

	private String getIdtype(String s) {
		String returnValue = "";
		if (s.trim().equals("9"))
			return returnValue;
		ExeSQL exeSql = new ExeSQL();
		SSRS tSSRS = new SSRS();
		SQLwithBindVariables sqlbv56 = new SQLwithBindVariables();
		sqlbv56.sql("select codename from ldcode where code='"+"?s?"
				+ "' and codetype='idtype'");
		sqlbv56.put("s", s);
		tSSRS = exeSql.execSQL(sqlbv56);
		if (tSSRS.MaxRow == 0) {
			buildError("submitData", "没有查询到对应的证件类型！");

		} else {
			returnValue = tSSRS.GetText(1, 1) + "：";
		}

		return returnValue;
	}

	private String getPayMode(String s) {
		String returnValue = "";
		ExeSQL exeSql = new ExeSQL();
		SSRS tSSRS = new SSRS();
		SQLwithBindVariables sqlbv57 = new SQLwithBindVariables();
		sqlbv57.sql("select codename from ldcode where code='" +"?s?"
				+ "' and codetype='paymode'");
		sqlbv57.put("s", s);
		tSSRS = exeSql.execSQL(sqlbv57);
		if (tSSRS.MaxRow == 0) {
			buildError("submitData", "没有查询到对应的缴费类型！");

		} else {
			returnValue = tSSRS.GetText(1, 1);
		}

		return returnValue;
	}

	private String getAge(String s1, String s2, String s3) {
		String returnValue = "0";
		if (s2.equals("Y")) {
			returnValue = String.valueOf(Integer.parseInt(s1)
					+ Integer.parseInt(s3));
		} else {
			returnValue = s1;
		}

		return returnValue;
	}

	private String getPayendyear(String payintv) {
		String returnValue = "";
		ExeSQL exeSql = new ExeSQL();
		SSRS tSSRS = new SSRS();
		SQLwithBindVariables sqlbv58 = new SQLwithBindVariables();
		sqlbv58.sql("select codename from ldcode where codetype = 'payintv' and code = '"
				+"?payintv?"+ "'");
		sqlbv58.put("payintv", payintv);
		tSSRS = exeSql.execSQL(sqlbv58);
		if (tSSRS.MaxRow == 0) {
			returnValue = "一次交清";
		} else {
			returnValue = tSSRS.GetText(1, 1);
		}
		return returnValue;
	}

	private String getPayTerm(String riskcode, String s1, String s2, String s4,
			String payendyear, String payendyearflag) {
		String s3 = PubFun.calDate(s2, -1, "D", "");
		s1 = StrTool.getChnDate(s1);
		s3 = StrTool.getChnDate(s3);
		String s5 = s1 + "至" + s3;
		if (riskcode.equals("00614000")) { // payendyearflag.equals("A")
			s5 = "缴至" + payendyear + "周岁";
		}
		if (s4.equals("0")) {
			s5 = s1;
		}
		return s5;
	}

	private String getPayEndTerm(String riskcode, String s1, String s2,
			String s4, String payendyear, String payendyearflag) {
		String s3 = PubFun.calDate(s2, -1, "D", "");
		s1 = StrTool.getChnDate(s1);
		s3 = StrTool.getChnDate(s3);
		String s5 = "至" + s3;
		if (riskcode.equals("00614000")) { // payendyearflag.equals("A")
			s5 = "缴至" + payendyear + "周岁";
		}
		if (s4.equals("0")) {
			s5 = s1;
		}
		return s5;
	}

	private String getPayTermSub(String s1, String s2) {
		s1 = StrTool.getChnDate(s1).replaceAll("年", ".").replaceAll("月", ".")
				.replaceAll("日", "");
		s2 = StrTool.getChnDate(PubFun.calDate(s2, -1, "D", "")).replaceAll(
				"年", ".").replaceAll("月", ".").replaceAll("日", "");

		return s1 + "—" + s2;
	}

	private String getChnPayTermSub(String s1, String s2) {
		s1 = StrTool.getChnDate(s1);
		s2 = StrTool.getChnDate(PubFun.calDate(s2, -1, "D", ""));

		return s1 + "至" + s2;
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

	private String getInsureEndTerm(String s1, String s3) {
		String returnValue = "";
		if (!s3.equals("1000")) {
			returnValue = PubFun.calDate(s1, -1, "D", "");
			returnValue = StrTool.getChnDate(returnValue);
			returnValue = "至" + returnValue;
		} else {
			returnValue = "至 终身";
		}
		return returnValue;

	}

	private String getPhone(String s) {
		String returnValue = "";
		ExeSQL exeSql = new ExeSQL();
		SSRS tSSRS = new SSRS();
		SQLwithBindVariables sqlbv59 = new SQLwithBindVariables();
		sqlbv59.sql("select phone,servicephone from ldcom where comcode='"
				+"?s?"+ "'");
		sqlbv59.put("s", s);
		tSSRS = exeSql.execSQL(sqlbv59);
		if (tSSRS.MaxRow == 0) {
			// buildError("submitData", "没有查询到对应的数据！");

		} else {
			if (tSSRS.GetText(1, 2).trim().equals("")) {
				returnValue = tSSRS.GetText(1, 1);
			} else {
				returnValue = tSSRS.GetText(1, 2);
			}
		}

		return returnValue;

	}

	private String getAddress(String s) {
		String returnValue = "";
		ExeSQL exeSql = new ExeSQL();
		SSRS tSSRS = new SSRS();
		SQLwithBindVariables sqlbv60 = new SQLwithBindVariables();
		sqlbv60.sql("select address from ldcom where comcode = (select upcomcode from ldcom where comcode ='"
				+"?s?"+ "')");
		sqlbv60.put("s", s);
		tSSRS = exeSql.execSQL(sqlbv60);
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
		SQLwithBindVariables sqlbv61 = new SQLwithBindVariables();
		sqlbv61.sql("select codename from ldcode where code='" +"?s?"
				+ "' and codetype='sex'");
		sqlbv61.put("s", s);
		tSSRS = exeSql.execSQL(sqlbv61);
		if (tSSRS.MaxRow == 0) {
			// buildError("submitData", "没有查询到对应的数据！");

		} else {
			returnValue = tSSRS.GetText(1, 1);
		}

		return returnValue;

	}

	/**
	 * 条款信息查询
	 * 
	 * @param cXmlDataset
	 *            XMLDataset
	 * @return boolean
	 * @throws Exception
	 */
	private boolean getSpecDoc(XMLDataset cXmlDataset) throws Exception {
		XMLDataList xmlDataList = new XMLDataList();
		// 添加xml一个新的对象Term
		xmlDataList.setDataObjectID("LCCSpec");
		// 添加Head单元内的信息
		xmlDataList.addColHead("RowID");
		xmlDataList.addColHead("SpecContent");
		xmlDataList.buildColHead();
		// 查询合同下的特约信息
		LCCSpecDB tLCCSpecDB = new LCCSpecDB();
		tLCCSpecDB.setContNo(this.mLCContSchema.getContNo());
		LCCSpecSet tLCCSpecSet = tLCCSpecDB.query();
		for (int i = 1; i <= tLCCSpecSet.size(); i++) {
			xmlDataList.setColValue("RowID", i);
			xmlDataList.setColValue("SpecContent", tLCCSpecSet.get(i)
					.getSpecContent());
			xmlDataList.insertRow(0);
		}
		cXmlDataset.addDataObject(xmlDataList);
		return true;
	}

	/**
	 * 条款信息查询 会出现多险种对应同意主条款 排序方式为主条款，享有此主条款的险种的责任条款 可采用二级循环来实现，不过比较麻烦
	 * 
	 * @param xmlDataset
	 *            XMLDataset
	 * @param aLCPolSet
	 *            LCPolSet
	 * @return boolean
	 * @throws Exception
	 */
	private boolean getTerm(XMLDataset xmlDataset, LCPolSet aLCPolSet)
			throws Exception {
		XMLDataList tXmlDataList = new XMLDataList();
		// 添加xml一个新的对象Term
		tXmlDataList.setDataObjectID("Term");
		// 添加Head单元内的信息
		tXmlDataList.addColHead("PrintIndex");
		tXmlDataList.addColHead("TermName");
		tXmlDataList.addColHead("FileName");
		tXmlDataList.addColHead("DocumentName");
		tXmlDataList.buildColHead();

		// 设置查询对象
		ExeSQL tExeSQL = new ExeSQL();
		// 查询保单下的险种条款，根据条款级别排序
		// String tSql = "select distinct a.ItemName,a.ItemType,a.FileName from
		// LDRiskPrint a,LCPol b where b.ContNo = '" +
		// this.mLCContSchema.getContNo() +
		// "' and a.RiskCode = b.RiskCode order by a.ItemType";
		// 查询合同下的全部唯一主条款信息
		// String tSql = "select distinct ItemName,FileName from LDRiskPrint
		// where RiskCode in (select RiskCode from LCPol where ContNo = '" +
		// this.mLCContSchema.getContNo() + "') and ItemType = '0'";
		StringBuffer tSBql = new StringBuffer(256);
		tSBql.append("select distinct ItemName,FileName from LDRiskPrint where RiskCode in (select RiskCode from LCPol where ContNo = '");
		tSBql.append("?ContNo?");
		tSBql.append("' and appflag = '1') and ItemType = '0'");
		SQLwithBindVariables sqlbv62 = new SQLwithBindVariables();
		sqlbv62.sql(tSBql.toString());
		sqlbv62.put("ContNo", this.mLCContSchema.getContNo());
		SSRS tSSRS = new SSRS();
		SSRS tSSRS2 = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv62);
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			tXmlDataList.setColValue("PrintIndex", i);
			tXmlDataList.setColValue("TermName", tSSRS.GetText(i, 1));
			tXmlDataList.setColValue("FileName", "0");
			tXmlDataList.setColValue("DocumentName", tSSRS.GetText(i, 2));
			tXmlDataList.insertRow(0);
			// 查询当前主条款下的责任条款信息
			// tSql = "select distinct ItemName,FileName from LDRiskPrint where
			// RiskCode in (select RiskCode from LCPol where ContNo = '" +
			// this.mLCContSchema.getContNo() + "') and ItemType = '1' and
			// RiskCode in (select RiskCode from LDRiskPrint where FileName = '"
			// +
			// tSSRS.GetText(i, 2) + "')";
			tSBql = new StringBuffer(256);
			tSBql.append("select distinct ItemName,FileName from LDRiskPrint where RiskCode in (select RiskCode from LCPol where ContNo = '");
			tSBql.append("?ContNo?");
			tSBql.append("' and appflag = '1') and ItemType = '1' and RiskCode in (select RiskCode from LDRiskPrint where FileName = '");
			tSBql.append("?FileName?");
			tSBql.append("')");
			SQLwithBindVariables sqlbv63 = new SQLwithBindVariables();
			sqlbv63.sql(tSBql.toString());
			sqlbv63.put("ContNo", this.mLCContSchema.getContNo());
			sqlbv63.put("FileName", tSSRS.GetText(i, 2));
			tSSRS2 = tExeSQL.execSQL(sqlbv63);
			for (int j = 1; j <= tSSRS2.getMaxRow(); j++) {
				tXmlDataList.setColValue("PrintIndex", i);
				tXmlDataList.setColValue("TermName", tSSRS2.GetText(j, 1));
				tXmlDataList.setColValue("FileName", "1");
				tXmlDataList.setColValue("DocumentName", tSSRS2.GetText(j, 2));
				tXmlDataList.insertRow(0);
			}
		}
		xmlDataset.addDataObject(tXmlDataList);
		return true;
	}

	/**
	 * 查询基本保额现金价值表
	 * 
	 * @param pLCPolSchema
	 *            保单信息
	 * @param pLCDutySchema
	 *            责任项信息
	 * @param pInterval
	 *            保单年度
	 * @param sCValidate
	 *            保单生效日期
	 * @param sZTPoint
	 *            退保日期
	 * @return double
	 */
	public SSRS getCashValueTable(LCPolSchema pLCPolSchema,
			LCDutySchema pLCDutySchema, int pBeginInterval, int pEndInterval,
			String sCValidate, String sZTPoint) {
		if (pBeginInterval == -1) {
			logger.debug("保单年度不能为负值!");
			return null;
		}
		if (pBeginInterval > pEndInterval) {
			logger.debug("起始年度不能大于终止年度!");
			return null;
		}

		String sDutyCode = pLCDutySchema.getDutyCode();
		if (sDutyCode.length() > 6) {
			sDutyCode = sDutyCode.substring(0, 6);
		}
		// 从LMEdorCal表中查询出查现金价值表的公式
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setRiskCode(pLCPolSchema.getRiskCode());
		tLMEdorCalDB.setDutyCode(sDutyCode);
		tLMEdorCalDB.setCalType("CVT");
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query();
		if (tLMEdorCalDB.mErrors.needDealError()) {
			logger.debug("现金价值表查询代码查询失败!");
			return null;
		}
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() != 1) {
			logger.debug("没有查到现金价值表查询代码!");
			return null;
		}

		int iPayTimes = 0;
		iPayTimes = getPayTimes(pLCDutySchema);
		if (iPayTimes == -1) {
			logger.debug("没有查到缴费次数!");
			return null;
		}
		String sGetIntv = getGetIntv(pLCDutySchema);
		if (sGetIntv == null) {
			logger.debug("没有查到领取间隔!");
			return null;
		}

		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(tLMEdorCalSet.get(1).getCalCode());
		// 准备计算要素
		tCalculator.addBasicFactor("PolNo", pLCPolSchema.getPolNo()); // 保单号
		tCalculator.addBasicFactor("BeginInterval", // 保单起始年度
				String.valueOf(pBeginInterval));
		tCalculator.addBasicFactor("EndInterval", // 保单终止年度
				String.valueOf(pEndInterval));
		tCalculator.addBasicFactor("Sex", // 被保人性别
				pLCPolSchema.getInsuredSex());
		tCalculator.addBasicFactor("AppAge", // 投保年龄
				String.valueOf(pLCPolSchema.getInsuredAppAge()));
		tCalculator.addBasicFactor("PayEndYear", // 缴费终止年期
				String.valueOf(pLCDutySchema.getPayEndYear()));
		tCalculator.addBasicFactor("InsuYear", // 保险期间
				String.valueOf(pLCDutySchema.getInsuYear()));
		tCalculator.addBasicFactor("GetStartAge", // 起领年龄
				String.valueOf(pLCDutySchema.getGetYear()));
		tCalculator.addBasicFactor("PayIntv", // 缴费间隔
				String.valueOf(pLCDutySchema.getPayIntv()));
		tCalculator.addBasicFactor("GetIntv", // 领取间隔
				sGetIntv);
		tCalculator.addBasicFactor("GetYear", // 领取年龄年期
				String.valueOf(pLCDutySchema.getGetYear()));
		tCalculator.addBasicFactor("Standbyflag1", // 连带被保人客户号
				pLCPolSchema.getStandbyFlag1());
		tCalculator.addBasicFactor("ContNo", // 合同保单号
				pLCPolSchema.getContNo());
		tCalculator.addBasicFactor("CValiDate", // 生效日
				sCValidate);
		tCalculator.addBasicFactor("ZTPoint", sZTPoint); // 退保点
		tCalculator.addBasicFactor("PayTimes", // 缴费次数
				String.valueOf(iPayTimes));

		// 计算要素尚不完全确定，等待唐佩 2005-07-10 15:45

		String tCVSql = tCalculator.getCalSQL();
		SQLwithBindVariables sqlbva = new SQLwithBindVariables();
		sqlbva.sql(tCVSql);
		if (tCalculator.mErrors.needDealError()) {
			logger.debug("取现金价值表描述失败!");
			return null;
		}
		ExeSQL tExeSql = new ExeSQL();
		SSRS cvSSRS = tExeSql.execSQL(sqlbva);

		if (cvSSRS.MaxRow == 0) {
			logger.debug("现金价值表查询结果为空!");
			return null;
		}
		return cvSSRS;
	}

	/**
	 * 查询红利保额现金价值表
	 * 
	 * @param pLCPolSchema
	 *            保单信息
	 * @param pInterval
	 *            保单年度
	 * @return double
	 */
	public SSRS getBonusCashValueTable(LCPolSchema pLCPolSchema,
			LCDutySchema pLCDutySchema, int pBeginInterval, int pEndInterval) {

		String sDutyCode = pLCDutySchema.getDutyCode();
		if (sDutyCode.length() > 6) {
			sDutyCode = sDutyCode.substring(0, 6);
		}
		// 从LMEdorCal表中查询出查红利现金价值表的公式
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setRiskCode(pLCPolSchema.getRiskCode());
		tLMEdorCalDB.setDutyCode(sDutyCode);
		tLMEdorCalDB.setCalType("GVT");
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query();
		if (tLMEdorCalDB.mErrors.needDealError()) {
			logger.debug("红利保额现金价值表查询代码查询失败!");
			return null;
		}
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() != 1) {
			logger.debug("没有查到红利保额现金价值表查询代码!");
			return null;
		}

		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(tLMEdorCalSet.get(1).getCalCode());
		// 准备计算要素
		tCalculator.addBasicFactor("PolNo", pLCPolSchema.getPolNo()); // 保单号
		tCalculator.addBasicFactor("BeginInterval", // 保单起始年度
				String.valueOf(pBeginInterval));
		tCalculator.addBasicFactor("EndInterval", // 保单终止年度
				String.valueOf(pEndInterval));
		tCalculator.addBasicFactor("Sex", // 被保人性别
				pLCPolSchema.getInsuredSex());
		tCalculator.addBasicFactor("AppAge", // 投保年龄
				String.valueOf(pLCPolSchema.getInsuredAppAge()));
		tCalculator.addBasicFactor("PayEndYear", // 缴费终止年期
				String.valueOf(pLCPolSchema.getPayEndYear()));
		tCalculator.addBasicFactor("InsuYear", // 保险期间
				String.valueOf(pLCPolSchema.getInsuYear()));
		tCalculator.addBasicFactor("GetStartAge", // 起领年龄
				String.valueOf(pLCDutySchema.getGetYear()));
		tCalculator.addBasicFactor("GetYear", // 领取年龄年期
				String.valueOf(pLCDutySchema.getGetYear()));
		tCalculator.addBasicFactor("PayIntv", // 缴费间隔
				String.valueOf(pLCPolSchema.getPayIntv()));

		String tCVSql = tCalculator.getCalSQL();
		SQLwithBindVariables sqlbvb = new SQLwithBindVariables();
		sqlbvb.sql(tCVSql);
		if (tCalculator.mErrors.needDealError()) {
			logger.debug("取现金价值红利折算比例表描述失败!");
			return null;
		}
		ExeSQL tExeSql = new ExeSQL();
		SSRS cvSSRS = tExeSql.execSQL(sqlbvb);

		if (cvSSRS.MaxRow == 0) {
			logger.debug("现金价值红利折算比例表查询结果为空!");
			return null;
		}
		return cvSSRS;
	}

	/**
	 * 交费次数
	 * 
	 * @param pLCPolSchema
	 *            保单信息
	 * @return int
	 */
	public int getPayTimes(LCDutySchema pLCDutySchema) {
		int iPayTimes = 0;
		// 交费次数只能从实收表里取，不能从应收表里取
		String sql = " select max(paycount) from LJAPayPerson "
				+ " where polno = '" +"?polno?"
				+ "' and dutycode = '"+"?dutycode?"+"' ";
		SQLwithBindVariables sqlbv64 = new SQLwithBindVariables();
		sqlbv64.sql(sql);
		sqlbv64.put("polno", pLCDutySchema.getPolNo());
		sqlbv64.put("dutycode", pLCDutySchema.getDutyCode());
		ExeSQL tExeSQL = new ExeSQL();
		String sPayTimes = tExeSQL.getOneValue(sqlbv64);
		if (tExeSQL.mErrors.needDealError()) {
			logger.debug("交费次数查询失败!");
			return -1;
		}
		if (sPayTimes == null || sPayTimes.equals("")) {
			sPayTimes = "0";
		}
		try {
			iPayTimes = Integer.parseInt(sPayTimes);
		} catch (Exception e) {
			logger.debug("交费次数查询结果错误!" + "错误结果：" + sPayTimes);
			return -1;
		}

		return iPayTimes;
	}

	/**
	 * 查询领取间隔
	 * 
	 * @param pLCDutySchema
	 * @return String
	 */
	private String getGetIntv(LCDutySchema pLCDutySchema) {
		String sql = "";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			sql = " select getintv from lcget where polno = '"
					+"?polno?"
					+ "' and dutycode = '"
					+"?dutycode?"
					+ "' and getdutycode in (select getdutycode from lmdutyget where type = '0')  and rownum = 1 ";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			sql = " select getintv from lcget where polno = '"
					+"?polno?"
					+ "' and dutycode = '"
					+"?dutycode?"
					+ "' and getdutycode in (select getdutycode from lmdutyget where type = '0') limit 0,1 ";
		}
		SQLwithBindVariables sqlbv65 = new SQLwithBindVariables();
		sqlbv65.sql(sql);
		sqlbv65.put("polno", pLCDutySchema.getPolNo());
		sqlbv65.put("dutycode", pLCDutySchema.getDutyCode());
		ExeSQL tExeSQL = new ExeSQL();
		String sGetIntv = tExeSQL.getOneValue(sqlbv65);
		if (tExeSQL.mErrors.needDealError()) {
			logger.debug("查询领取间隔失败!");
			return null;
		}
		if (sGetIntv == null || sGetIntv.trim().equals("")) {
			logger.debug("领取间隔为空");
			sGetIntv = "";
		}
		return sGetIntv;
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
				+"?MainPolNo?"+ "'";
		logger.debug(tSql);
		SQLwithBindVariables sqlbv66 = new SQLwithBindVariables();
		sqlbv66.sql(tSql);
		sqlbv66.put("MainPolNo", mLCContSchema.getContNo());
		tSSRS = tExeSQL.execSQL(sqlbv66);
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
		mResult.addElement(xmlexport);
		mResult.addElement(xmlexport1);
		// 暂时不放在一个事务里面，因为基本上读入流后很少会发生错误
		LCContF1PBLS tLCContF1PBLS = new LCContF1PBLS();
		if (!tLCContF1PBLS.submitData(mResult, "REPRINT")) {
			if (tLCContF1PBLS.mErrors.needDealError()) {
				mErrors.copyAllErrors(tLCContF1PBLS.mErrors);
			}
			throw new Exception("保存数据失败");
		}

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
			String FilePath = "/printdata";
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
			logger.debug("XmlFile:　" + XmlFile);
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
	 * @param rePrintType
	 *            The rePrintType to set.
	 */
	public void setRePrintType(String rePrintType) {
		this.rePrintType = rePrintType;
	}

	/**
	 * 共用算法调度
	 */
	public SSRS DoCalculator(String calCode, MMap map) {
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(calCode);
		// 准备计算要素
		for (int i = 1; i <= map.keySet().size(); i++) {
			tCalculator.addBasicFactor((String) map.getKeyByOrder(String
					.valueOf(i)), (String) map.get(map.getKeyByOrder(String
					.valueOf(i))));
		}

		String tCVSql = tCalculator.getCalSQL();
		if (tCalculator.mErrors.needDealError()) {
			logger.debug("取算法" + calCode + "失败!");
			tCVSql = "select 0 from dual";
		}
		SQLwithBindVariables sqlbv67 = new SQLwithBindVariables();
		sqlbv67.sql(tCVSql);
		ExeSQL tExeSql = new ExeSQL();
		SSRS cvSSRS = tExeSql.execSQL(sqlbv67);

		if (cvSSRS.MaxRow == 0) {
			logger.debug("算法" + calCode + "计算失败!");
			tCVSql = "select 0 from dual";
			SQLwithBindVariables sqlbv68 = new SQLwithBindVariables();
			sqlbv68.sql(tCVSql);
			cvSSRS = tExeSql.execSQL(sqlbv68);
		}
		return cvSSRS;
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
		tlc.setContNo("880152395947");
		tVData.add(tg);
		tVData.add(tlc);
		tVData.add(tTD);
		LCContF1PBL tLCContF1PBL = new LCContF1PBL();
		if (!tLCContF1PBL.submitData(tVData, "PRINT"))
			logger.debug("Wrong!");
		else
			logger.debug("OK!");
		XmlExport txmlExportAll = new XmlExport();
		txmlExportAll.createDocuments("Zan", tg);
		txmlExportAll.setTemplateName("Invoice.vts");
		VData mResult = new VData();
		mResult = tLCContF1PBL.getResult();
		XmlExport txmlExport = (XmlExport) mResult.getObjectByObjectName(
				"XmlExport", 0);
		txmlExportAll.addDataSet(txmlExportAll.getDocument().getRootElement(),
				txmlExport.getDocument().getRootElement());
		txmlExportAll.outputDocumentToFile("d:\\", "LCContF1PBL"
				+ PubFun.getCurrentTime().toString().replaceAll(":", "_"));
	};
}