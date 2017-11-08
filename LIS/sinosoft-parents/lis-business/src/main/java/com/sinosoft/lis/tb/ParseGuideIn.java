/*
 * @(#)ParseGuideIn.java	2004-12-13
 *
 * Copyright 2004 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.tb;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.LCContPlanRiskDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContPlanRiskSchema;
import com.sinosoft.lis.schema.LCContPlanSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpImportLogSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPayRuleFactorySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.vbl.LCBnfBLSet;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vbl.LCPremBLSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCContPlanRiskSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpImportLogSet;
import com.sinosoft.lis.vschema.LCInsuredRelatedSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCPremToAccSet;
import com.sinosoft.lis.vschema.LMRiskAppSet;
import com.sinosoft.lis.vschema.LMRiskSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XMLPathTool;

/*
 * <p>Title: Web业务系统</p> <p>ClassName:ParserGuidIn </p> <p>Description:
 * 磁盘投保入口类文件 </p> <p>Copyright: Copyright (c) 2004</p> <p>Company: Sinosoft
 * </p> @author：WUJS @version：6.0 @CreateDate：2004-12-13
 */

public class ParseGuideIn {
private static Logger logger = Logger.getLogger(ParseGuideIn.class);
	// @Fields
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	public CErrors logErrors = new CErrors();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 内存文件暂存 */
	private org.jdom.Document myDocument;
	private org.jdom.Element myElement;
	private GlobalInput mGlobalInput = new GlobalInput();

	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LCDutySchema mLCDutySchema = new LCDutySchema();
	private LCDutySet mLCDutySet = new LCDutySet();
	private LCInsuredSet mLCInsuredSet = new LCInsuredSet();
	private LCBnfSet mLCBnfSet = new LCBnfSet();
	private LCPremToAccSet mLCPremToAccSet = new LCPremToAccSet();
	private TransferData mTransferData = new TransferData();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LCInsuredRelatedSet mLCInsuredRelatedSet = new LCInsuredRelatedSet();
	private LCGrpImportLogSet mLCGrpImportLogSet = new LCGrpImportLogSet();

	private String FileName;
	private String XmlFileName;
	private String mPreFilePath;

	private String mGrpContNo;

	// 数据Xml解析节点描述
	private String FilePath = "upload/";
	private String ParseRootPath = "/DATASET/BATCHID";
	private String PATH_GRPCONTNO = "/DATASET/GRPCONTNO";
	private String ParseLCPolPath = "/DATASET/LCPOLTABLE/ROW";
	private String ParseInsuredPath = "/DATASET/INSUREDTABLE";
	private String ParseBnfPath = "/DATASET/BNFTABLE";
	private String parseInsuredRelaPath = "/DATASET/INSURED_RELA_TABLE";
	private String ParsePath = "/DATASET/CONTTABLE/ROW";

	// 配置文件Xml节点描述
	private String ImportFileName;
	private String ConfigFileName;
	private String ConfigRootPath = "/CONFIG";
	private String mBatchNo = "";
	private String mPrtNo = "";
	private String mContNo = "";
	private String mainRiskCode = "";
	private LCPolBL mainPolBL = new LCPolBL();
	private org.w3c.dom.Document m_doc = null; // 临时的Document对象
	private String mPubAmntFlag = ""; // 公共保额导入标记

	private GrpPolImpInfo m_GrpPolImpInfo = new GrpPolImpInfo();
	private String mProposalGrpContNo;

	private String[] m_strDataFiles = null;
	private String[][] m_IDNoSet = null;

	private boolean mISPubAcc = false;
	private Hashtable mHashInfo = new Hashtable();

	/**
	 * sql绑定变量类
	 */
	private SQLwithBindVariables sqlbv= new SQLwithBindVariables();
	// @Constructors
	public ParseGuideIn() {
		bulidDocument();
	}

	public ParseGuideIn(String fileName) {
		bulidDocument();
		FileName = fileName;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		boolean re = true;
		mISPubAcc = false;
		this.getInputData();
		if (!this.checkData()) {
			return false;
		}
		logger.debug("开始时间:" + PubFun.getCurrentTime());
		try {
			if (!this.parseVts()) {
				return false;
			}

			for (int nIndex = 0; nIndex < m_strDataFiles.length; nIndex++) {
				XmlFileName = m_strDataFiles[nIndex];
				boolean res = this.ParseXml();
			}

			// mLCGrpImportLogSet = m_GrpPolImpInfo.getErrors();
			//
			// //错误日志
			// if (mLCGrpImportLogSet.size() > 0)
			// {
			// String tReturn = mLCGrpImportLogSet.encode();
			// tReturn = "0|" + mLCGrpImportLogSet.size() + "^" + tReturn;
			// mResult.clear();
			// mResult.addElement(tReturn);
			// }
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.debug(ex.toString());
			String tMsg = ex.toString();
			tMsg = tMsg.replaceAll("\"", "");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = tMsg;
			this.logErrors.addOneError(tError);
			// return false;
		}

		if (this.logErrors.getErrorCount() > 0) {
			this.mTransferData.setNameAndValue("BatchNo", this.mBatchNo);
			mResult.add(this.mTransferData);
			this.mErrors = this.logErrors;
			logger.debug("结束时间:" + PubFun.getCurrentTime());
			return false;
		}

		logger.debug("结束时间:" + PubFun.getCurrentTime());
		return true;
	}

	// 公共帐户处理
	public boolean submitData2(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		boolean re = true;
		this.getInputData2();
		if (!this.checkData2()) {
			return false;
		}
		logger.debug("公共帐户处理开始时间:" + PubFun.getCurrentTime());
		String contId = "1"; // 一次保存一个公共帐户
		// 批次号
		mBatchNo = mLCGrpContSchema.getGrpContNo();
		mISPubAcc = true;

		LCGrpContDB grpContDB = new LCGrpContDB();
		String tGrpContNo = mLCGrpContSchema.getGrpContNo();
		grpContDB.setGrpContNo(tGrpContNo);
		LCGrpContSet tLCGrpContSet = grpContDB.query();
		if (tLCGrpContSet == null || tLCGrpContSet.size() <= 0) {
			CError.buildErr(this, "没有找到团体合同[" + tGrpContNo + "]的信息");
			m_GrpPolImpInfo.logError(mBatchNo, grpContDB.getGrpContNo(), "",
					"", "", "", "", null, this.mErrors, mGlobalInput, FileName);
			return false;
		}
		mLCGrpContSchema = tLCGrpContSet.get(1);
		mPrtNo = mLCGrpContSchema.getPrtNo();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("PolKey", "1-1-"
				+ mLCPolSchema.getRiskCode());
		tTransferData.setNameAndValue("ID", "1-" + mLCPolSchema.getRiskCode());
		mGrpContNo = mLCGrpContSchema.getGrpContNo();

		VData mtVData = new VData();
		VData tVData = new VData();
		mtVData.add(mLCPolSchema);
		mtVData.add(mLCInsuredSet);
		mtVData.add(tTransferData);
		mtVData.add(mLCDutySet);
		tVData.add(mtVData);

		m_GrpPolImpInfo.addContPolData(contId, tVData);
		if (!m_GrpPolImpInfo.init(mBatchNo, this.mGlobalInput,
				this.mTransferData, mLCInsuredSet, mLCBnfSet,
				mLCInsuredRelatedSet, mLCGrpContSchema)) {

			m_GrpPolImpInfo.logError(mBatchNo, mLCGrpContSchema.getGrpContNo(),
					"", "", "", "", "", null, this.mErrors, mGlobalInput,
					FileName);
			this.mErrors.clearErrors();
			return false;
		}

		// 按合同导入
		boolean bl = DiskContImport(contId);

		if (!bl) {
			logger.debug("保存失败");
			// return false;
		} else {
			logger.debug("保存成功");
		}

		this.mErrors = this.logErrors;
		logger.debug("公共帐户处理结束时间:" + PubFun.getCurrentTime());
		if (this.mErrors.getErrorCount() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 得到传入数据
	 */
	private void getInputData() {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mPreFilePath = (String) mTransferData.getValueByName("FilePath");
		logger.debug(mPreFilePath);
	}

	// 公共帐户处理
	private void getInputData2() {

		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mLCInsuredSet = (LCInsuredSet) mInputData.getObjectByObjectName(
				"LCInsuredSet", 0);
		mLCPolSchema = (LCPolSchema) mInputData.getObjectByObjectName(
				"LCPolSchema", 0);
		mLCDutySet = (LCDutySet) mInputData.getObjectByObjectName("LCDutySet",
				0);
		mLCBnfSet = (LCBnfSet) mInputData.getObjectByObjectName("LCBnfSet", 0);

		mLCInsuredRelatedSet = (LCInsuredRelatedSet) mInputData
				.getObjectByObjectName("LCInsuredRelatedSet", 0);

		mLCGrpContSchema = (LCGrpContSchema) mInputData.getObjectByObjectName(
				"LCGrpContSchema", 0);
	}

	private boolean checkData2() {
		if (mGlobalInput == null) {
			// @@错误处理
			buildError("checkData", "没有传入全局信息");
			return false;
		}
		if (mTransferData == null) {
			// @@错误处理
			buildError("checkData", "公共数据传入为空，请重新导入!");
			return false;
		}

		if (mLCGrpContSchema == null) {
			// @@错误处理
			buildError("checkData", "合同数据传入为空!");
			return false;
		}
		return true;
	};

	/**
	 * 校验传输数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "无操作员信息，请重新登录!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mTransferData == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "无导入文件信息，请重新导入!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			FileName = (String) mTransferData.getValueByName("FileName");
			if (FileName == null) {
				FileName = "";
			}
		}
		return true;
	}

	/**
	 * 得到生成文件路径
	 * 
	 * @return
	 */
	private boolean getFilePath() {
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		// tLDSysVarDB.setSysVar("XmlPath2");
		tLDSysVarDB.setSysVar("ImportPath");
		if (!tLDSysVarDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "缺少文件导入路径!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			FilePath = tLDSysVarDB.getSysVarValue();
		}

		return true;
	}

	/**
	 * 检验文件是否存在
	 * 
	 * @return
	 */
	private boolean checkXmlFileName() {
		// XmlFileName = (String)mTransferData.getValueByName("FileName");
		File tFile = new File(XmlFileName);
		if (!tFile.exists()) {
			LDSysVarDB tLDSysVarDB = new LDSysVarDB();
			tLDSysVarDB.setSysVar("XmlPath");
			if (!tLDSysVarDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ParseGuideIn";
				tError.functionName = "checkData";
				tError.errorMessage = "缺少文件导入路径!";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				FilePath = tLDSysVarDB.getSysVarValue();
			}

			File tFile1 = new File(FilePath);
			if (!tFile1.exists()) {
				tFile1.mkdirs();
			}
			XmlFileName = FilePath + XmlFileName;
			File tFile2 = new File(XmlFileName);
			if (!tFile2.exists()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ParseGuideIn";
				tError.functionName = "checkData";
				tError.errorMessage = "请上传相应的数据文件到指定路径" + FilePath + "!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查导入配置文件是否存在
	 * 
	 * @return
	 */
	private boolean checkImportConfig() {
		this.getFilePath();
		// FilePath="E:/temp/";

		String filePath = mPreFilePath + FilePath;
		File tFile1 = new File(filePath);
		if (!tFile1.exists()) {
			tFile1.mkdirs();
		}

		ConfigFileName = filePath + "Import.xml";
		logger.debug("******导入配置文件：" + ConfigFileName);
		File tFile2 = new File(ConfigFileName);
		if (!tFile2.exists()) {
			logger.debug("检查导入配置文件是否存在");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "请上传配置文件到指定路径" + FilePath + "!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 初始化上传文件
	 * 
	 * @return
	 */
	private boolean initImportFile() {
		this.getFilePath();
		ImportFileName = mPreFilePath + FilePath + FileName;
		// ImportFileName = "E:/temp/" + FileName;

		File tFile = new File(ImportFileName);
		if (!tFile.exists()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "未上传文件到指定路径" + FilePath + "!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("-----导入文件");
		return true;
	}

	/**
	 * 解析excel并转换成xml文件
	 * 
	 * @return
	 */
	private boolean parseVts() throws Exception {
		// 初始化导入文件
		if (!this.initImportFile()) {
			return false;
		}
		if (!this.checkImportConfig()) {
			return false;
		}

		GrpPolVTSParser gpvp = new GrpPolVTSParser();

		if (!gpvp.setFileName(ImportFileName)) {
			mErrors.copyAllErrors(gpvp.mErrors);
			return false;
		}
		if (!gpvp.setConfigFileName(ConfigFileName)) {
			mErrors.copyAllErrors(gpvp.mErrors);
			return false;
		}
		// 转换excel到xml
		if (!gpvp.transform()) {
			mErrors.copyAllErrors(gpvp.mErrors);
			return false;
		}

		// 得到生成的XML数据文件名
		m_strDataFiles = gpvp.getDataFiles();

		return true;
	}

	/**
	 * 提交第一层内的循环体到第一层循环内的指定结点下 ???目前不知道有啥用 -wujs
	 * 
	 * @param aVT
	 *            Vector
	 * @param aElt
	 *            Element
	 */
	public void AddMuliElement(Vector aVT, org.jdom.Element aElt) {
		int NodeCount = aVT.size();
		// org.jdom.Element tElt1 = (org.jdom.Element)myElement.clone();
		org.jdom.Element tElt = myElement;
		int i = 0;
		while (NodeCount > 0) {
			i++;
			String NodeName = (String) aVT.get(i - 1);
			// tElt = tElt.getChild((String)aVT.get(i-1));
			tElt = (org.jdom.Element) tElt.getChildren((String) aVT.get(i - 1))
					.get(tElt.getChildren((String) aVT.get(i - 1)).size() - 1);
			// myElement.addContent(t.Elt);
			NodeCount--;
		}
		tElt.addContent(aElt);
		// myDocument = new org.jdom.Document(tElt1);
	}

	/**
	 * 解析xml,
	 * 
	 * @return
	 */
	private boolean ParseXml() {
		// logger.debug("-----开始读取Xml文件");
		if (!checkXmlFileName()) {
			return false;
		}
		File tFile = new File(XmlFileName);
		XMLPathTool tXPT = new XMLPathTool(XmlFileName);
		this.mErrors.clearErrors();

		String tBatchNo = "";
		String tPrtNo = "";
		int tPeoples = 0;
		try {
			// 批次号
			tBatchNo = tXPT.parseX(ParseRootPath).getFirstChild()
					.getNodeValue();
			// 印刷号
			tPrtNo = tXPT.parseX(PATH_GRPCONTNO).getLastChild().getNodeValue();
			mBatchNo = tBatchNo;
			mPrtNo = tPrtNo;

			// 删除此批次号对应的导入日志,后边重新写入
			m_GrpPolImpInfo.delImportLog(mPrtNo, tPrtNo);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		LCGrpContDB grpContDB = new LCGrpContDB();
		// grpContDB.setPrtNo(tPrtNo);
		grpContDB.setGrpContNo(tPrtNo); // modify zhangtao 2006-11-27
		LCGrpContSet tLCGrpContSet = grpContDB.query();
		if (tLCGrpContSet == null || tLCGrpContSet.size() <= 0) {
			CError.buildErr(this, "没有找到团体合同[" + tPrtNo + "]的信息");
			m_GrpPolImpInfo.logError(tBatchNo, grpContDB.getGrpContNo(), "",
					"", "", "", "", null, this.mErrors, mGlobalInput, FileName);
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "ParseXml";
			tError.errorMessage = "没有找到团体合同[" + tPrtNo + "]的信息";
			this.logErrors.addOneError(tError);

			return false;
		}
		LCGrpContSchema tLCGrpContSchema = tLCGrpContSet.get(1);
		mGrpContNo = tLCGrpContSchema.getGrpContNo();
		// 2005-5-13-增加mProposalGrpContNo全局变量
		mProposalGrpContNo = tLCGrpContSchema.getProposalGrpContNo();
		tPeoples = tLCGrpContSchema.getPeoples3();
		// ========Liuliang==2005-5-9==Update================================================================
		// 增加了保全分支判断,如果是 保全增加被保人，则先查询要验证的数据[身份证号码]
		if (mTransferData.getValueByName("BQFlag") != null
				&& mTransferData.getValueByName("BQFlag") != ""
				&& "Y".equals(StrTool.cTrim(mTransferData.getValueByName(
						"BQFlag").toString()))
				&& "NI".equals(StrTool.cTrim(mTransferData.getValueByName(
						"EdorType").toString())))

		{
			// 查询所有被保人的身份证号
			//update by cxq 修改绑定变量
			sqlbv = new SQLwithBindVariables();
			String strSQL = "Select IDNo From LCInsured Where IDType='0' and GrpContNo='?mGrpContNo?'";
			sqlbv.sql(strSQL);
			sqlbv.put("mGrpContNo", mGrpContNo);
			logger.debug(sqlbv.sql());
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tssrs = tExeSQL.execSQL(sqlbv);
			// 将所有的身份证号放入m_IDNoSet二维数组中
			m_IDNoSet = tssrs.getAllData();

		} else {
			if ("1".equals(StrTool.cTrim(tLCGrpContSchema.getAppFlag()))) {
				CError.buildErr(this, "团体合同[" + mGrpContNo + "]已经签单，不再接受投保");
				m_GrpPolImpInfo.logError(tBatchNo, grpContDB.getGrpContNo(),
						"", "", "", "", "", null, this.mErrors, mGlobalInput,
						FileName);
				this.mErrors.clearErrors();

				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ParseGuideIn";
				tError.functionName = "ParseXml";
				tError.errorMessage = "团体合同[" + mGrpContNo + "]已经签单，不再接受投保";
				this.logErrors.addOneError(tError);

				return false;
			}
		}
		// 团单合同的录单完成日期不为空则表示已经录入完成
		// if (!"".equals(StrTool.cTrim(tLCGrpContSchema.getInputDate()))) {
		// CError.buildErr(this,"团体合同[" + mGrpContNo + "]已录入完成，不再接受投保");
		// m_GrpPolImpInfo.logError(tBatchNo, grpContDB.getGrpContNo(), "",
		// "",
		// "", "", "", null, this.mErrors,
		// mGlobalInput, FileName);
		// this.mErrors.clearErrors();
		//
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "ParseGuideIn";
		// tError.functionName = "ParseXml";
		// tError.errorMessage = "团体合同[" + mGrpContNo + "]已录入完成，不再接受投保";
		// this.logErrors.addOneError(tError);
		//
		// return false;
		// }

		/** **************************** */
		// 得到保单的传入信息
		NodeList nodeList = tXPT.parseN(ParsePath);
		int nNodeCount = 0;

		if (nodeList != null) {
			nNodeCount = nodeList.getLength();
		}

		logger.debug("nNodeCount=" + nNodeCount);
		logger.debug("tPeoples=" + tPeoples);
		if (tPeoples < nNodeCount) {
			CError.buildErr(this, "被保人数超过团体合同[" + mGrpContNo + "]投保单位总人数！");
			m_GrpPolImpInfo.logError(tBatchNo, grpContDB.getGrpContNo(), "",
					"", "", "", "", null, this.mErrors, mGlobalInput, FileName);
			this.mErrors.clearErrors();

			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "ParseXml";
			tError.errorMessage = "被保人数超过团体合同[" + mGrpContNo + "]投保单位总人数！";
			this.logErrors.addOneError(tError);
			return false;
		}

		// 循环中，每一次都调用一次承包后台程序，首先挑出一条保单纪录，再通过ID号找到保费项，责任，给付等，组合成Set集合
		// 此时新单是没有投保单号的，因此在保单，保费项，责任等纪录中投保单号为空,后台自动生成投保单号
		// 最后将数据放入VData中，传给GrpPollmport模块。一次循环完毕
		GrpPolParser tGrpPolParser = new GrpPolParser();
		for (int i = 0; i < nNodeCount; i++) {
			boolean tSucc = true;
			Node node = nodeList.item(i);

			try {
				node = transformNode(node);
			} catch (Exception ex) {
				ex.printStackTrace();
				node = null;
			}

			NodeList contNodeList = node.getChildNodes();
			if (contNodeList.getLength() <= 0) {
				continue;
			}
			Node contNode = null;
			String nodeName = "";
			LCInsuredRelatedSet tLCInsuredRelatedSet = null;
			LCInsuredSet tLCInsuredSet = null;
			LCBnfSet tLCBnfSet = null;
			String contId = "";
			for (int j = 0; j < contNodeList.getLength(); j++) {

				contNode = contNodeList.item(j);
				nodeName = contNode.getNodeName();
				if (nodeName.equals("#text")) {
					continue;
				}
				if (nodeName.equals("CONTID")) {
					contId = contNode.getFirstChild().getNodeValue();

					continue;
				}

				if (nodeName.equals("INSUREDTABLE")) {
					// 解析被保险人xml
					tLCInsuredSet = (LCInsuredSet) tGrpPolParser
							.getLCInsuredSet(contNode);

					continue;
				}

				if (tLCInsuredSet == null) {
					CError.buildErr(this, "被保险人节点解析失败");
					this.mErrors.copyAllErrors(tGrpPolParser.mErrors);
					m_GrpPolImpInfo.logError(tBatchNo,
							grpContDB.getGrpContNo(), "", "", "", "", "", null,
							this.mErrors, mGlobalInput, FileName);
					this.mErrors.clearErrors();

					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ParseGuideIn";
					tError.functionName = "ParseXml";
					tError.errorMessage = "被保险人节点解析失败,被保险人不能为空!";
					this.logErrors.addOneError(tError);
					tSucc = false;
					break;
				}

				if (nodeName.equals("LCPOLTABLE")) {
					// 保单信息
					VData tVData = null;

					// 解析一个保单节点
					tVData = tGrpPolParser.parseLCPolNode(contNode);
					if (tVData == null) {
						this.mErrors.addOneError("被保险人险种解析失败");
						this.mErrors.copyAllErrors(tGrpPolParser.mErrors);
						this.logError(contId, contId, "", "", "", tLCInsuredSet
								.get(1).getSchema());
						this.mErrors.clearErrors();

						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "ParseGuideIn";
						tError.functionName = "ParseXml";
						tError.errorMessage = "被保险人险种解析失败!";
						this.logErrors.addOneError(tError);
						tSucc = false;
						break;

					}
					// 缓存险种保单信息
					m_GrpPolImpInfo.addContPolData(contId, tVData); // contid
					continue;
				}
				if (nodeName.equals("BNFTABLE")) {
					// 受益人解析
					tLCBnfSet = (LCBnfSet) tGrpPolParser.getLCBnfSet(contNode);
					continue;
				}
			}

			if (!tSucc) {
				continue;
			}

			// ========Liuliang==2005-5-9==Update==Start=============================
			// 进行保全增加被保人身份证号的校验
			if (m_IDNoSet != null) {
				for (int m = 1; m <= tLCInsuredSet.size(); m++) {
					LCInsuredSchema temp = tLCInsuredSet.get(m);
					for (int j = 1; j < m_IDNoSet.length; j++) {
						if (temp.getIDNo() != null
								&& temp.getIDNo().equals(m_IDNoSet[j][0])) {
							CError.buildErr(this, "被保险人身份证号" + temp.getIDNo()
									+ "已经存在!");
							m_GrpPolImpInfo.logError(tBatchNo, grpContDB
									.getGrpContNo(), "", "", "", "", "", null,
									this.mErrors, mGlobalInput, FileName);
							this.mErrors.clearErrors();
							// @@错误处理
							CError tError = new CError();
							tError.moduleName = "ParseGuideIn";
							tError.functionName = "ParseXml";
							tError.errorMessage = "被保险人身份证号" + temp.getIDNo()
									+ "已经存在!";
							this.logErrors.addOneError(tError);

							return false;
						}
					}
				}
			}
			// ========Liuliang==2005-5-9==Update==End=====================================

			logger.debug("ParseXml,birthday="
					+ tLCInsuredSet.get(1).getBirthday());

			if (!m_GrpPolImpInfo.init(tBatchNo,
					this.mGlobalInput,
					this.mTransferData, // add this.mTransferData参数
					tLCInsuredSet, tLCBnfSet, tLCInsuredRelatedSet,
					tLCGrpContSchema)) {

				m_GrpPolImpInfo.logError(tBatchNo, grpContDB.getGrpContNo(),
						"", "", "", "", "", null, this.mErrors, mGlobalInput,
						FileName);
				this.mErrors.clearErrors();
				return false;
			}

			// 按合同导入
			boolean bl = DiskContImport(contId);

			if (!bl) {
				logger.debug("导入失败");
				// return false;
			} else {
				logger.debug("导入成功");
			}

		}

		/* 、××××××××、 */

		// if (tBatchNo == null || tBatchNo.trim().equals("")) {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "ParseGuideIn";
		// tError.functionName = "checkData";
		// tError.errorMessage = "批次号不能为空!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// BatchNo = tBatchNo;
		// initLDGrp 保存了集体信息

		// //解析被保险人xml
		// LCInsuredSet tLCInsuredSet = (LCInsuredSet)this.ParseOneNode(tXPT,
		// this.ParseInsuredPath);
		// if (tLCInsuredSet == null)
		// {
		// CError.buildErr(this, "被保险人节点解析失败,被保险人不能为空!");
		// m_GrpPolImpInfo.logError(tBatchNo, grpContDB.getGrpContNo(), "", "",
		// "", "", "", null, this.mErrors,
		// mGlobalInput);
		// this.mErrors.clearErrors();
		// return false;
		// }
		// //受益人解析
		// LCBnfSet tLCBnfSet = (LCBnfSet)this.ParseOneNode(tXPT,
		// this.ParseBnfPath);
		// //连身被保人表
		// LCInsuredRelatedSet tLCInsuredRelatedSet = (LCInsuredRelatedSet)this.
		// ParseOneNode(tXPT, this.parseInsuredRelaPath);
		//
		//
		// //保单信息
		// m_GrpPolImpInfo.intiContPolData();
		// boolean pd = parsePolData(tXPT);
		// if (!pd)
		// {
		// logger.debug("解析保单节点出错!");
		// return false;
		// }

		// if (!m_GrpPolImpInfo.init(tBatchNo, this.mGlobalInput,
		// tLCInsuredSet, tLCBnfSet,
		// tLCInsuredRelatedSet,
		// tLCGrpContSchema))
		// {
		//
		// m_GrpPolImpInfo.logError(tBatchNo, grpContDB.getGrpContNo(), "", "",
		// "", "", "", null, this.mErrors,
		// mGlobalInput);
		// this.mErrors.clearErrors();
		// return false;
		// }
		//
		// //导入
		// boolean bl = DiskContImport();

		try {
			if (!tFile.delete()) {
				// 删除不了临时文件不是致命错误，不返回错误，在后台打印出错误信息就可以了
				logger.debug("删除" + tFile.getAbsolutePath() + "文件失败！");

			}
		}

		catch (Exception ex) {
			logger.debug("删除" + tFile.getAbsolutePath() + "文件失败！"
					+ ex.getMessage());
		}
		// }
		if (this.mErrors.needDealError()) {
			logger.debug(this.mErrors.getErrorCount() + "error:"
					+ this.mErrors.getFirstError());
		}

		return true;
	}

	/**
	 * 提交到proposalBL ,做数据生成和准备
	 * 
	 * @param tNewVData
	 *            VData
	 * @return boolean
	 */
	private MMap submiDatattoProposalBL(VData tNewVData) {
		// tNewVData 里面还有一些要创建的信息，因此需要重新获得

		logger.debug("submiDatattoProposalBL begin");
		MMap map = (MMap) tNewVData.getObjectByObjectName("MMap", 0);
		if (map == null) {
			map = new MMap();
		}
		LCPolSchema tLCPolSchema = (LCPolSchema) tNewVData
				.getObjectByObjectName("LCPolSchema", 0);
		String strID = (String) ((TransferData) tNewVData
				.getObjectByObjectName("TransferData", 0)).getValueByName("ID");
		String PolKey = (String) ((TransferData) tNewVData
				.getObjectByObjectName("TransferData", 0))
				.getValueByName("PolKey");
		logger.debug("提交数据,PolKey=" + PolKey);
		ProposalBL tProposalBL = new ProposalBL();
		if (!tProposalBL.PrepareSubmitData(tNewVData, "INSERT||PROPOSAL")) {
			logger.debug("提交完毕,PolKey=" + PolKey);
			this.mErrors.copyAllErrors(tProposalBL.mErrors);
			// m_GrpPolImpInfo.logError(strID, tLCPolSchema, false,
			// this.mErrors,
			// mGlobalInput);
			return null;
		} else {
			logger.debug("提交完毕,PolKey=" + PolKey);
			if (tProposalBL.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tProposalBL.mErrors);
			}
			VData pData = tProposalBL.getSubmitResult();
			if (pData == null) {
				CError.buildErr(this, "保单提交计算失败!");
				return null;
			}
			//
			// LCContSchema rcontSchema =
			// (LCContSchema)pData.getObjectByObjectName("LCContSchema",2);
			LCPolSchema rPolSchema = (LCPolSchema) pData.getObjectByObjectName(
					"LCPolSchema", 0);
			LCGrpPolSchema rGrpPol = (LCGrpPolSchema) pData
					.getObjectByObjectName("LCGrpPolSchema", 0);
			LCGrpContSchema rGrpCont = (LCGrpContSchema) pData
					.getObjectByObjectName("LCGrpContSchema", 0);
			LCPremBLSet rPremBLSet = (LCPremBLSet) pData.getObjectByObjectName(
					"LCPremBLSet", 0);
			if (rPremBLSet == null) {
				CError.buildErr(this, "保单提交计算保费项数据准备有误!");
				return null;
			}
			LCPremSet rPremSet = new LCPremSet();
			for (int i = 1; i <= rPremBLSet.size(); i++) {
				rPremSet.add(rPremBLSet.get(i));
			}

			LCDutyBLSet rDutyBLSet = (LCDutyBLSet) pData.getObjectByObjectName(
					"LCDutyBLSet", 0);
			if (rDutyBLSet == null) {
				CError.buildErr(this, "保单提交计算责任项数据准备有误!");
				return null;
			}
			LCDutySet rDutySet = new LCDutySet();
			for (int i = 1; i <= rDutyBLSet.size(); i++) {
				rDutySet.add(rDutyBLSet.get(i));
			}

			LCGetBLSet rGetBLSet = (LCGetBLSet) pData.getObjectByObjectName(
					"LCGetBLSet", 0);
			if (rGetBLSet == null) {
				CError.buildErr(this, "保单提交计算给付项数据准备有误!");
				return null;
			}
			LCGetSet rGetSet = new LCGetSet();
			for (int i = 1; i <= rGetBLSet.size(); i++) {
				rGetSet.add(rGetBLSet.get(i));
			}

			LCBnfBLSet rBnfBLSet = (LCBnfBLSet) pData.getObjectByObjectName(
					"LCBnfBLSet", 0);
			LCBnfSet rBnfSet = new LCBnfSet();
			if (rBnfBLSet != null) {
				for (int i = 1; i <= rBnfBLSet.size(); i++) {
					rBnfSet.add(rBnfBLSet.get(i));
				}
			}
			LCInsuredRelatedSet tLCInsuredRelatedSet = (LCInsuredRelatedSet) pData
					.getObjectByObjectName("LCInsuredRelatedSet", 0);
			// if ( tLCInsuredRelatedSet!=null && LCInsuredRelatedSet.size()>0)

			// 更新个单合同金额相关，更新的时候同时更新缓存中的数据
			String contId = m_GrpPolImpInfo.getContKey(PolKey);
			LCContSchema tLCContSchema = m_GrpPolImpInfo
					.findLCContfromCache(contId);
			if (tLCContSchema == null) {
				CError.buildErr(this, "查找合同[" + contId + "]失败");
				return null;
			}
			mContNo = tLCContSchema.getContNo();
			tLCContSchema.setPrem(PubFun.setPrecision(tLCContSchema.getPrem()
					+ rPolSchema.getPrem(), "0.00"));
			tLCContSchema.setAmnt(tLCContSchema.getAmnt()
					+ rPolSchema.getAmnt());
			tLCContSchema.setSumPrem(tLCContSchema.getSumPrem()
					+ rPolSchema.getSumPrem());
			tLCContSchema.setMult(tLCContSchema.getMult()
					+ rPolSchema.getMult());
			LCContSchema tContSchema = new LCContSchema();

			tContSchema.setSchema(tLCContSchema);
			LCContSet tContSet = new LCContSet();
			// 更新集体险种金额相关
			String riskCode = rPolSchema.getRiskCode();
			LCGrpPolSchema tLCGrpPolSchema = m_GrpPolImpInfo
					.findLCGrpPolSchema(riskCode);
			tLCGrpPolSchema.setPrem(PubFun.setPrecision(tLCGrpPolSchema
					.getPrem()
					+ rPolSchema.getPrem(), "0.00"));
			tLCGrpPolSchema.setAmnt(tLCGrpPolSchema.getAmnt()
					+ rPolSchema.getAmnt());
			/*----------------------caihy modi 20050614----------------------*/
			/* 如果是保全增人则不修改集体险种表的交至日期等 */
			if (mTransferData.getValueByName("BQFlag") != null
					&& mTransferData.getValueByName("BQFlag") != ""
					&& "Y".equals(StrTool.cTrim(mTransferData.getValueByName(
							"BQFlag").toString()))
					&& "NI".equals(StrTool.cTrim(mTransferData.getValueByName(
							"EdorType").toString()))) {
				// 不修改集体险种表的日期
			} else {
				tLCGrpPolSchema.setPayEndDate(PubFun.getLaterDate(
						tLCGrpPolSchema.getPayEndDate(), rPolSchema
								.getPayEndDate()));
				tLCGrpPolSchema.setPaytoDate(PubFun.getLaterDate(
						tLCGrpPolSchema.getPaytoDate(), rPolSchema
								.getPaytoDate()));
				tLCGrpPolSchema.getPayEndDate();
				tLCGrpPolSchema.setFirstPayDate(PubFun.getBeforeDate(
						tLCGrpPolSchema.getFirstPayDate(), rPolSchema
								.getFirstPayDate()));
			}
			/*---------------------------modi end -------------------------------*/
			rGrpPol.setPrem(tLCGrpPolSchema.getPrem());
			rGrpPol.setAmnt(tLCGrpPolSchema.getAmnt());
			rGrpPol.setPayEndDate(tLCGrpPolSchema.getPayEndDate());
			rGrpPol.setPaytoDate(tLCGrpPolSchema.getPaytoDate());
			rGrpPol.setFirstPayDate(tLCGrpPolSchema.getFirstPayDate());

			// 更新团单合同金额相关
			LCGrpContSchema sGrpContSchema = m_GrpPolImpInfo
					.getLCGrpContSchema();
			sGrpContSchema.setPrem(PubFun.setPrecision(sGrpContSchema.getPrem()
					+ rPolSchema.getPrem(), "0.00"));
			sGrpContSchema.setAmnt(sGrpContSchema.getAmnt()
					+ rPolSchema.getAmnt());

			rGrpCont.setPrem(sGrpContSchema.getPrem());
			rGrpCont.setAmnt(sGrpContSchema.getAmnt());

			tContSet.add(tContSchema);
			map.put(tContSet, "UPDATE");
			map.put(rPolSchema, "INSERT");
			map.put(rGrpPol, "UPDATE");
			map.put(rGrpCont, "UPDATE");
			map.put(rPremSet, "INSERT");
			map.put(rDutySet, "INSERT");
			map.put(rGetSet, "INSERT");
			map.put(rBnfSet, "INSERT");
			map.put(tLCInsuredRelatedSet, "INSERT");
			Date date = new Date();
			Random rd = new Random(date.getTime());
			long u = rd.nextLong();
			// 个人单需要重新统计个单合同人数
			if ("0".equals(tContSchema.getPolType())) {
				//update by cxq 修改绑定变量
				sqlbv = new SQLwithBindVariables();
				String s0 = " update lccont set peoples=(select count(distinct insuredno) from lcpol where '?u?'='?u?' and contno='?ContNo?')"
						+ " where contno='?ContNo?'" + " and PolType = '0' ";
				sqlbv.sql(s0);
				sqlbv.put("u", u);
				sqlbv.put("ContNo", tContSchema.getContNo());
				
				map.put(sqlbv, "UPDATE");
			}
			//update by cxq 修改绑定变量
			sqlbv = new SQLwithBindVariables();
			String s1 = "update lcgrpcont  set peoples2=(select sum(peoples) from lccont where '?u?'='?u?' and GrpContNo='?GrpContNo1?')"
					+ " where grpcontno='?GrpContNo2?'";
			sqlbv.sql(s1);
			sqlbv.put("u", u);
			sqlbv.put("GrpContNo1", tContSchema.getGrpContNo());
			sqlbv.put("GrpContNo2", rGrpCont.getGrpContNo());
			map.put(sqlbv, "UPDATE");
			//update by cxq 修改绑定变量
			sqlbv = new SQLwithBindVariables();
			String s2 = "update lcgrppol set peoples2=(select sum(insuredpeoples) from lcpol where '?u?'='?u?' and grppolno='?GrpPolNo?')"
					+ " where grppolno='?GrpPolNo?'";
			sqlbv.sql(s2);
			sqlbv.put("u", u);
			sqlbv.put("GrpPolNo", rGrpPol.getGrpPolNo());
			map.put(sqlbv, "UPDATE");
			// 缓存保存的主险保单信息
			m_GrpPolImpInfo.cachePolInfo(strID, rPolSchema);
		}

		return map;
	}

	private boolean batchSave(MMap map) {
		PubSubmit pubSubmit = new PubSubmit();
		VData sData = new VData();
		sData.add(map);
		boolean tr = pubSubmit.submitData(sData, "");

		if (!tr) {
			if (pubSubmit.mErrors.getErrorCount() > 0) {
				// 错误回退
				mErrors.copyAllErrors(pubSubmit.mErrors);
				pubSubmit.mErrors.clearErrors();
			} else {
				CError.buildErr(this, "保存数据库的时候失败！");
			}
			return false;
		}
		return true;
	}

	/**
	 * 以合同为单位做磁盘投保,对一个合同所有保单，一旦有一个险种保单导入失败，则要么失败
	 * 
	 * @return boolean
	 */
	private boolean DiskContImport(String contIndex) {
		LCInsuredSchema insuredSchema = null;
		LCInsuredSet tInsuredSet = m_GrpPolImpInfo.getLCInsuredSet();
		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		LMRiskAppSet tLMRiskAppSet = new LMRiskAppSet();
		MMap subMap = null; // 提交结果集缓存
		boolean state = true; // 导入状态，
		boolean saveState = true;
		String logRiskCode = "";
		String logContPlanCode = "";
		String logPolId = "";
		String insuredIndex = "";

		subMap = null;
		mContNo = "";
		LCGrpImportLogSchema logSchema = m_GrpPolImpInfo.getLogInfo(mBatchNo,
				contIndex);

		if (logSchema != null && "0".equals(logSchema.getErrorState())
				&& mTransferData.getValueByName("BQFlag") == null
				&& mTransferData.getValueByName("BQFlag") == "") {
			return true;
		}

		state = true;
		// 先查找有保障计划的导入
		for (int i = 1; i <= tInsuredSet.size(); i++) {
			logger.debug("Recycle Count=" + i);

			// 初始化，避免重复使用
			logRiskCode = "";
			logContPlanCode = "";
			logPolId = "";
			if (state == false) {
				break;
			}
			insuredSchema = tInsuredSet.get(i);
			logger.debug("DiskContImport:birthday="
					+ insuredSchema.getBirthday());
			insuredSchema.setSequenceNo(String.valueOf(i));
			// 不是同一个合同的，跳出
			if (!insuredSchema.getContNo().equals(contIndex)) {
				continue;
			}
			// 不是保障计划的，跳出
			if ("".equals(StrTool.cTrim(insuredSchema.getContPlanCode()))) {
				continue;
			}
			// 解析XML时借用PrtNo保存被保人序号
			insuredIndex = insuredSchema.getPrtNo();
			if ("".equals(StrTool.cTrim(insuredIndex))) {
				CError.buildErr(this, "第[" + i + "]个被保人ID没有填写");
				// continue;
				state = false;
				break; // break for insured
			}
			// 保障计划代码不空才导入
			logContPlanCode = insuredSchema.getContPlanCode();
			LCContPlanSchema tContPlanSchema = m_GrpPolImpInfo
					.findLCContPlan(insuredSchema.getContPlanCode());
			if (tContPlanSchema == null) {
				CError.buildErr(this, "被保险人[" + i + "]保单保险计划["
						+ insuredSchema.getContPlanCode() + "]查找失败");
				// continue;
				state = false;
				break; // break for insured
			}

			// 险种保险计划
			LCContPlanRiskSet tLCContPlanRiskSet = m_GrpPolImpInfo
					.findLCContPlanRiskSet(insuredSchema.getContPlanCode());

			if (tLCContPlanRiskSet == null || tLCContPlanRiskSet.size() <= 0) {
				CError.buildErr(this, "被保险人[" + i + "]保单险种保险计划["
						+ insuredSchema.getContPlanCode() + "]查找失败");
				// continue;
				state = false;
				break; // break for insured
			}

			// 对保险险种计划排序，确保主险在前面
			LCContPlanRiskSet mainPlanRiskSet = new LCContPlanRiskSet();
			LCContPlanRiskSet subPlanRiskSet = new LCContPlanRiskSet();
			LCContPlanRiskSchema contPlanRiskSchema = null;
			for (int t = 1; t <= tLCContPlanRiskSet.size(); t++) {
				contPlanRiskSchema = tLCContPlanRiskSet.get(t);

				// ----------------------------------------Beg
				// Add By:chenrong
				// Description:记录保险计划中的险种信息
				// Date:2006.6.5
				tLMRiskAppDB = new LMRiskAppDB();
				tLMRiskAppDB.setRiskCode(contPlanRiskSchema.getRiskCode());
				if (tLMRiskAppDB.getInfo()) {
					tLMRiskAppSet.add(tLMRiskAppDB.getSchema());
				} else {
					CError.buildErr(this, "险种"
							+ contPlanRiskSchema.getRiskCode() + "查询失败！");
					state = false;
					break;
				}
				// ----------------------------------------End
				if (contPlanRiskSchema.getRiskCode().equals(
						contPlanRiskSchema.getMainRiskCode())) {
					mainPlanRiskSet.add(contPlanRiskSchema);
				} else {
					subPlanRiskSet.add(contPlanRiskSchema);
				}

			}
			mainPlanRiskSet.add(subPlanRiskSet);
			// 根据险种计划生成险种保单相关信息
			double tMult = insuredSchema.getStature(); // 取出暂存份数
			insuredSchema.setStature("0"); // 将值恢复为0
			for (int j = 1; j <= mainPlanRiskSet.size(); j++) {
				logRiskCode = mainPlanRiskSet.get(j).getRiskCode();
				// 生成保单，责任等信息
				VData tData = prepareContPlanData(contIndex, insuredIndex,
						insuredSchema, mainPlanRiskSet.get(j), tMult);
				if (tData == null) {
					state = false;
					break; // break for conplarisk
				}
				// 提交生成数据
				MMap oneRisk = submiDatattoProposalBL(tData);
				if (oneRisk == null) {
					// 失败
					state = false;
					break; // break for insured
				} else {
					// 成功
					if (subMap == null) {
						subMap = oneRisk;
					} else {
						subMap.add(oneRisk);
					}
				}
			}
		}
		// 计划导入准备期间发生错误
		if (state == false) {
			logError(contIndex, insuredIndex, logPolId, "", logRiskCode,
					insuredSchema);
			saveState = false;
			// continue;
			return false;
		} else { // yaory
			// 无计划导入
			// 导入险种保单，先根据合同ID查找到所有相同合同id下的所有保单数据，再一张一张保单信息导入
			VData tContPolData = (VData) m_GrpPolImpInfo
					.getContPolData(contIndex);
			logger.debug("合同ＩＤ＝＝" + contIndex);
			if (tContPolData != null) {

				LCPolSchema tLCPolSchema = null;
				VData tPolData = null;
				for (int u = 0; u < tContPolData.size(); u++) {
					tPolData = (VData) tContPolData.get(u);
					if (state == false) {
						break;
					}
					LMRiskSet tLMRiskSet = new LMRiskSet();
					for (int i = 0; i < tPolData.size(); i++) {

						boolean tContinue = false;
						// 初始化，避免重复使用
						logRiskCode = "";
						logContPlanCode = "";
						logPolId = "";
						if (state == false) {
							break;
						}

						VData onePolData = (VData) tPolData.get(i);
						if (onePolData == null) {
							continue;
						}
						// 备份原ID号
						tLCPolSchema = (LCPolSchema) onePolData
								.getObjectByObjectName("LCPolSchema", 0);
						tLCPolSchema.setInterestDifFlag("0");
						logRiskCode = tLCPolSchema.getRiskCode();
						insuredIndex = tLCPolSchema.getInsuredNo();
						contIndex = tLCPolSchema.getContNo();

						// ----------------------------------------Beg
						// Add By:chenrong
						// Description:同一被保人多次导入同一险种，则为错
						// Date:2006.6.17
						LMRiskSchema tLMRiskSchema = new LMRiskSchema();
						if (tLMRiskSet == null || tLMRiskSet.size() < 1) {
							tLMRiskSchema = new LMRiskSchema();
							tLMRiskSchema.setRiskCode(logRiskCode);
							tLMRiskSet.add(tLMRiskSchema);
						} else {
							int tOldSize = tLMRiskSet.size();
							for (int n = 1; n <= tOldSize; n++) {
								tLMRiskSchema = tLMRiskSet.get(n);
								if (tLMRiskSchema.getRiskCode().equals(
										logRiskCode)
										&& insuredIndex.equals(insuredSchema
												.getPrtNo())) {
									CError.buildErr(this, "同一被保人--"
											+ insuredIndex + "--不能重复添加相同险种！");
									state = false;
								} else {
									tLMRiskSchema = new LMRiskSchema();
									tLMRiskSchema.setRiskCode(logRiskCode);
									tLMRiskSet.add(tLMRiskSchema);
								}
							}
						}

						if (state == false) {
							break;
						}

						// ----------------------------------------End
						// ----------------------------------------Beg
						// Add By:chenrong
						// Description:如果保险计划中经有相同险种，则不再进行重复导入
						// Date:2006.6.5
						if (tLMRiskAppSet != null && tLMRiskAppSet.size() > 0) {
							for (int j = 1; j <= tLMRiskAppSet.size(); j++) {
								LMRiskAppSchema tLMRiskAppSchema = new LMRiskAppSchema();
								tLMRiskAppSchema = tLMRiskAppSet.get(j);
								if (tLMRiskAppSchema.getRiskCode().trim()
										.equals(logRiskCode.trim())
										&& insuredIndex.equals(insuredSchema
												.getPrtNo())) {
									tContinue = true;
									break;
								}
							}
						}
						// -----------------------------------------End

						if (tContinue) {
							continue;
						}

						/*---------caihy modi 20050509-----------*/
						/* 增加分红险缴费规则的处理 */
						LCPremSet tLCPremSet = new LCPremSet();
						LCInsuredSet tLCInsuredSet = m_GrpPolImpInfo
								.getLCInsuredSet();
						LCInsuredSchema tLCInsuredSchema = tLCInsuredSet.get(1);
						// logger.debug("---insuredno:"+tLCInsuredSchema.encode());
						// logger.debug("------payrulecode:"+tLCPolSchema.getPayRuleCode()
						// +"
						// ascrulecode:"+tLCPolSchema.getAscriptionRuleCode());

						if (tLCPolSchema.getPayRuleCode() != null
								&& !"".equals(tLCPolSchema.getPayRuleCode())) {
							// 如果有缴费规则则取得缴费信息
							LCPayRuleFactorySchema tLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
							tLCPayRuleFactorySchema.setGrpContNo(mGrpContNo);
							tLCPayRuleFactorySchema.setRiskCode(tLCPolSchema
									.getRiskCode());
							tLCPayRuleFactorySchema.setPayRuleCode(tLCPolSchema
									.getPayRuleCode());

							VData tVData = new VData();
							tVData.addElement(tLCPayRuleFactorySchema);
							tVData.addElement(tLCPolSchema);
							tVData.addElement(tLCInsuredSchema);

							QueryPayRuleUI tQueryPayRuleUI = new QueryPayRuleUI();
							if (!tQueryPayRuleUI.submitData(tVData)) {
								logger.debug("缴费规则查询失败！");
							} else {
								tLCPremSet = (LCPremSet) tQueryPayRuleUI
										.getResult().getObjectByObjectName(
												"LCPremSet", 0);
							}
							// logger.debug("------------lcprem.size:" +
							// tLCPremSet.size());
						}

						// logger.debug("----afd--------lcprem.size:" +
						// tLCPremSet.size());
						VData newPolData = new VData();
						if (tLCPremSet.size() == 0) {
							newPolData = onePolData;
							// tLCPremSet =
							// (LCPremSet)newPolData.getObjectByObjectName("LCPremSet",0);
							// logger.debug(tLCPremSet.size());
						} else {
							newPolData.add((TransferData) onePolData
									.getObjectByObjectName("TransferData", 0));
							newPolData.add((LCPolSchema) onePolData
									.getObjectByObjectName("LCPolSchema", 0));
							newPolData
									.add((LCPremToAccSet) onePolData
											.getObjectByObjectName(
													"LCPremToAccSet", 0));
							newPolData.add((LCDutySet) onePolData
									.getObjectByObjectName("LCDutySet", 0));
							LCPremSet vLCPremSet = new LCPremSet();
							vLCPremSet = (LCPremSet) onePolData
									.getObjectByObjectName("LCPremSet", 0);
							if (vLCPremSet.size() > 0) {
								for (int k = 1; k <= vLCPremSet.size(); k++) {
									int premCount = 0;
									for (int m = 1; m <= tLCPremSet.size(); m++) {
										if (tLCPremSet
												.get(m)
												.getPayPlanCode()
												.equals(
														vLCPremSet
																.get(k)
																.getPayPlanCode())) {
											premCount = premCount + 1;
										}
									}
									if (premCount == 0) {
										tLCPremSet.add(vLCPremSet.get(k));
									}
								}
							}
							newPolData.add(tLCPremSet);
						}
						// logger.debug("prem:"+tLCPremSet.get(1).encode());
						VData tprepareData = this
								.prepareProposalBLData(newPolData);
						logger.debug("prepareProposalBL end");
						/*---------------caihy modi end 20050509---------------*/
						if (tprepareData == null) {
							logger.debug("准备数据的时候出错！");
							state = false;
						} else {
							MMap tMap = this
									.submiDatattoProposalBL(tprepareData);
							if (tMap == null) {

								state = false;
							}
							if (subMap == null) {
								subMap = tMap;
							} else {
								subMap.add(tMap);
							}
						}
					}
				}
			}
		}
		if (state == false) {

			logError(contIndex, insuredIndex, logPolId, "", logRiskCode,
					insuredSchema);
			saveState = false;
			return false;
			// continue;
		}

		boolean bs = true;

		if (state && subMap != null && subMap.keySet().size() > 0) {
			MMap logMap = m_GrpPolImpInfo.logSucc(mBatchNo, mGrpContNo,
					contIndex, mPrtNo, mContNo, mGlobalInput);
			subMap.add(logMap);
			bs = batchSave(subMap);
		}

		if (!bs || !state) {
			// 合同导入没有成功，需要回退缓存
			m_GrpPolImpInfo.removeCaceh(contIndex);
			logger.debug("合同[" + contIndex + "]导入失败！！"
					+ this.mErrors.getErrContent());

			// 导入失败日志，数据库保存，只能定位到合同上的失败
			logError(contIndex, "", "", "", "", null);

			// 保存导入信息
			saveState = false;
		} else {
			logger.debug("合同[" + contIndex + "]导入成功！！");
			// 保存导入信息
		}

		// } //end contKeyId

		return saveState;
	}

	/**
	 * 按保险计划导入的数据准备
	 * 
	 * @param contIndex
	 *            String 合同Id
	 * @param insuredIndex
	 *            String 被保险人Id
	 * @param insuredSchema
	 *            LCInsuredSchema 主被保险人信息
	 * @param tLCContPlanRiskSchema
	 *            LCContPlanRiskSchema 保险险种计划
	 * @return VData
	 */
	private VData prepareContPlanData(String contIndex, String insuredIndex,
			LCInsuredSchema insuredSchema,
			LCContPlanRiskSchema tLCContPlanRiskSchema, double tMult) {
		VData tNewVData = new VData();
		MMap tmpMap = new MMap();
		String strRiskCode = tLCContPlanRiskSchema.getRiskCode();
		String tPlanType = tLCContPlanRiskSchema.getPlanType();
		String tStrID = contIndex + "-" + strRiskCode;
		LCGrpPolSchema sLCGrpPolSchema = m_GrpPolImpInfo
				.findLCGrpPolSchema(strRiskCode);
		if (sLCGrpPolSchema == null) {
			buildError("prepareContPlanData", strRiskCode + "险种对应的集体投保单没有找到!");
			return null;
		}

		// 拷贝一份，避免修改本地数据
		LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
		tLCGrpPolSchema.setSchema(sLCGrpPolSchema);

		String PolKey = m_GrpPolImpInfo.getPolKey(contIndex, insuredIndex,
				strRiskCode);
		// ======UPD===zhangtao======2005-03-28=========BGN=========================
		boolean contFlag = false;
		LCContSchema contSchema = new LCContSchema();
		contSchema = m_GrpPolImpInfo.findLCContfromCache(contIndex);
		if (contSchema != null
				&& !"".equals(StrTool.cTrim(contSchema.getContNo()))) { // 合同已经创建过
			contFlag = true;
		}

		// 提交生成被保险人号,创建合同的sql
		VData tContData = m_GrpPolImpInfo.getContData(contIndex, insuredIndex,
				tLCGrpPolSchema);
		if (tContData == null) {
			logger.debug("创建合同信息时出错!");
			return null;
		}
		MMap contMap = (MMap) tContData.getObjectByObjectName("MMap", 0);
		tmpMap.add(contMap);
		insuredSchema = (LCInsuredSchema) tContData.getObjectByObjectName(
				"LCInsuredSchema", 0);
		tNewVData.add(insuredSchema);
		contSchema = (LCContSchema) tContData.getObjectByObjectName(
				"LCContSchema", 0);
		if (!contFlag) {
			// 合同第一次创建
			// 设置主被保险人信息
			contSchema.setInsuredBirthday(insuredSchema.getBirthday());
			contSchema.setInsuredNo(insuredSchema.getInsuredNo());
			contSchema.setInsuredName(insuredSchema.getName());
			contSchema.setInsuredIDNo(insuredSchema.getIDNo());
			contSchema.setInsuredIDType(insuredSchema.getIDType());
			contSchema.setInsuredSex(insuredSchema.getSex());
		}

		tNewVData.add(contSchema);
		// ======UPD===zhangtao======2005-03-28=========END=========================
		// 生成保单个人险种信息
		// LCPolSchema tLCPolSchema = formLCPolSchema(insuredSchema,
		// tLCContPlanRiskSchema);
		LCPolSchema tLCPolSchema = formLCPolSchema(insuredSchema,
				tLCContPlanRiskSchema, contSchema);
		// 主险保单信息
		String mainRiskCode = m_GrpPolImpInfo.getMainRiskCode(
				tLCContPlanRiskSchema.getContPlanCode(), strRiskCode);

		if (mainRiskCode.equals("")) {
			mainRiskCode = strRiskCode;
			mainPolBL.setRiskCode(mainRiskCode);
		}
		String mainPolKey = m_GrpPolImpInfo.getPolKey(contIndex, insuredIndex,
				mainRiskCode);
		tLCPolSchema.setMainPolNo(mainRiskCode);
		logger.debug("主险标志=====" + mainPolKey);
		tLCPolSchema.setInsuredNo(insuredIndex);
		// 填充所有保单信息
		VData polData = m_GrpPolImpInfo.formatLCPol(tLCPolSchema, PolKey,
				mISPubAcc);
		if (polData == null) {
			CError.buildErr(this, "出错了:", m_GrpPolImpInfo.mErrors);
			return null;
		}
		MMap polRelaMap = (MMap) polData.getObjectByObjectName("MMap", 0);
		tLCPolSchema = (LCPolSchema) polData.getObjectByObjectName(
				"LCPolSchema", 0);
		tLCPolSchema.setInsuredNo(insuredSchema.getInsuredNo());
		if (tMult > 0) {
			tLCPolSchema.setMult(tMult);
		}
		if (polRelaMap != null && polRelaMap.keySet().size() > 0) {
			tmpMap.add(polRelaMap);
		}

		// 责任信息查询和生成
		ContPlanQryBL contPlanQryBL = new ContPlanQryBL();
		VData tVData = new VData();
		tVData.add(tLCContPlanRiskSchema);
		mTransferData.setNameAndValue("PlanType", tPlanType);
		tVData.add(mTransferData);

		boolean b = contPlanQryBL.submitData(tVData, "");
		if (!b || contPlanQryBL.mErrors.needDealError()) {
			CError.buildErr(this, "查询计划要约出错:", contPlanQryBL.mErrors);
			return null;
		}
		tVData = contPlanQryBL.getResult();
		TransferData tTransferData1 = new TransferData();
		tTransferData1 = (TransferData) tVData.getObjectByObjectName(
				"TransferData", 0);
		LCDutySet tDutySet = (LCDutySet) tVData.getObjectByObjectName(
				"LCDutySet", 0);
		if (tDutySet == null) {
			LCDutySchema tDutySchema = (LCDutySchema) tVData
					.getObjectByObjectName("LCDutySchema", 0);
			if (tDutySchema == null) {
				CError.buildErr(this, "查询计划要约出错:责任信息不能为空");
				return null;
			}
			setPolInfoByDuty(tLCPolSchema, tDutySchema);
			tDutySet = new LCDutySet();
			tDutySet.add(tDutySchema);
		}
		if (tDutySet == null || tDutySet.size() <= 0) {
			CError.buildErr(this, strRiskCode + "要约信息生成错误出错");
			return null;
		}
		tNewVData.add(tDutySet);
		// 保费
		LCPremSet tPremSet = new LCPremSet();
		String tPayRuleCode = (String) tTransferData1
				.getValueByName("PayRuleCode");
		if (tPayRuleCode != null && !"".equals(tPayRuleCode)) {
			// 如果有缴费规则则取得缴费信息
			LCPayRuleFactorySchema tLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
			tLCPayRuleFactorySchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLCPayRuleFactorySchema.setRiskCode(tLCPolSchema.getRiskCode());
			tLCPayRuleFactorySchema.setPayRuleCode(tPayRuleCode);

			VData tPayVData = new VData();
			tLCPolSchema.setPayRuleCode(tPayRuleCode);
			tPayVData.addElement(tLCPayRuleFactorySchema);
			tPayVData.addElement(tLCPolSchema);
			tPayVData.addElement(insuredSchema);

			QueryPayRuleUI tQueryPayRuleUI = new QueryPayRuleUI();
			if (!tQueryPayRuleUI.submitData(tPayVData)) {
				logger.debug("缴费规则查询失败！");
			} else {
				tPremSet = (LCPremSet) tQueryPayRuleUI.getResult()
						.getObjectByObjectName("LCPremSet", 0);
			}
		}
		tNewVData.add(tPremSet);

		String tAscriptionRuleCode = (String) tTransferData1
				.getValueByName("AscriptionRuleCode");
		if (tAscriptionRuleCode != null && !"".equals(tAscriptionRuleCode)) {
			tLCPolSchema.setAscriptionRuleCode(tAscriptionRuleCode);
		}

		// 连身被保险人信息
		// 处理连身被保险人
		String[] relaIns = null;
		relaIns = m_GrpPolImpInfo.getInsuredRela(contIndex, insuredIndex,
				strRiskCode);
		if (relaIns != null) {
			LCInsuredRelatedSet tRelaInsSet = m_GrpPolImpInfo
					.getInsuredRelaData(tLCPolSchema.getInsuredNo(), relaIns);
			if (tRelaInsSet == null) {
				// 创建连带被保险人出错
				mErrors.copyAllErrors(m_GrpPolImpInfo.mErrors);
				return null;
			}
			tNewVData.add(tRelaInsSet);
		}

		// 受益人信息
		LCBnfSet tLCBnfSet = new LCBnfSet();
		String tBnfKey = m_GrpPolImpInfo.getPolKey(contIndex, insuredIndex,
				tLCContPlanRiskSchema.getContPlanCode());
		// 主被保险人受益人
		LCBnfSet lLCBnfSet = m_GrpPolImpInfo.getBnfSet(tBnfKey);
		if (lLCBnfSet != null && lLCBnfSet.size() > 0) {
			tLCBnfSet.add(lLCBnfSet);
		}
		// 连带被保险人受益人
		if (relaIns != null) {
			for (int i = 0; i < relaIns.length; i++) {
				String tmpPolKey = m_GrpPolImpInfo.getPolKey(contIndex,
						relaIns[i], strRiskCode);
				LCBnfSet tmpLCBnfSet = m_GrpPolImpInfo.getBnfSet(tmpPolKey);
				if (tmpLCBnfSet != null && tmpLCBnfSet.size() > 0) {
					tLCBnfSet.add(tmpLCBnfSet);
				}
			}
		}
		if (tLCBnfSet != null && tLCBnfSet.size() > 0) {
			VData tr = m_GrpPolImpInfo.perareBnf(tLCBnfSet, insuredSchema,
					tLCGrpPolSchema, insuredIndex);

			MMap tmap = (MMap) tr.getObjectByObjectName("MMap", 0);
			if (tmap != null) {
				tmpMap.add(tmap);
			}
			tLCBnfSet = (LCBnfSet) tr.getObjectByObjectName("LCBnfSet", 0);
			tNewVData.add(tLCBnfSet);

		}

		// 加入对应险种的集体投保单信息,险种承保描述信息
		LMRiskAppSchema tLMRiskAppSchema = m_GrpPolImpInfo
				.findLMRiskAppSchema(strRiskCode);
		if (tLMRiskAppSchema == null) {
			buildError("prepareContPlanData", strRiskCode + "险种对应的险种承保描述没有找到!");
			return null;
		}
		LMRiskSchema tLMRiskSchema = m_GrpPolImpInfo
				.findLMRiskSchema(strRiskCode);
		if (tLMRiskSchema == null) {
			buildError("prepareContPlanData", strRiskCode + "险种对应的险种承保描述没有找到!");
			return null;
		}

		LDGrpSchema ttLDGrpSchema = m_GrpPolImpInfo
				.findLDGrpSchema(tLCGrpPolSchema.getCustomerNo());
		if (ttLDGrpSchema == null) {
			buildError("prepareContPlanData", tLCGrpPolSchema.getCustomerNo()
					+ "对应的集体客户信息没有找到!");
			return null;
		}

		// 其他信息
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("samePersonFlag", "0");
		tTransferData.setNameAndValue("GrpImport", 1);
		tTransferData.setNameAndValue("ID", tStrID);
		tTransferData.setNameAndValue("PolKey", PolKey);
		// ========Liuliang==2005-5-9==Update==Start============================
		// 增加了保全分支判断,如果是 保全增加被保人，设置保全保存标记 2
		if (mTransferData.getValueByName("BQFlag") != null
				&& mTransferData.getValueByName("BQFlag") != ""
				&& "Y".equals(StrTool.cTrim(mTransferData.getValueByName(
						"BQFlag").toString()))
				&& "NI".equals(StrTool.cTrim(mTransferData.getValueByName(
						"EdorType").toString()))) {
			// 保全保存标记
			tTransferData.setNameAndValue("SavePolType", "2");
			tTransferData.setNameAndValue("EdorType", mTransferData
					.getValueByName("EdorType"));

			tTransferData.setNameAndValue("EdorValiDate", mTransferData
					.getValueByName("EdorValiDate"));

			logger.debug(tTransferData.getValueByName("SavePolType"));
		}

		logger.debug("保险计划类型：" + tPlanType);
		if (tPlanType != null && "6".equals(tPlanType)) {
			tTransferData.setNameAndValue("ISPlanRisk", "Y");
			tTransferData.setNameAndValue("Mult", Double.toString(tMult));
		}

		// ========Liuliang==2005-5-9==Update==End==============================

		tNewVData.add(mGlobalInput);
		tNewVData.add(tLCGrpPolSchema);
		tNewVData.add(tLMRiskAppSchema);
		tNewVData.add(tLMRiskSchema);
		tNewVData.add(ttLDGrpSchema);
		tNewVData.add(m_GrpPolImpInfo.getLCGrpAppntSchema());
		tNewVData.add(tLCPolSchema);
		tNewVData.add(tTransferData);
		tNewVData.add(this.mainPolBL);
		tNewVData.add(tmpMap);
		return tNewVData;

	}

	/**
	 * 生成保单信息
	 * 
	 * @param insuredSchema
	 *            LCInsuredSchema
	 * @param tLCContPlanRiskSchema
	 *            LCContPlanRiskSchema
	 * @return LCPolSchema
	 */
	private LCPolSchema formLCPolSchema(LCInsuredSchema insuredSchema,
			LCContPlanRiskSchema tLCContPlanRiskSchema, LCContSchema contSchema) {
		LCPolSchema tLCPolSchema = new LCPolSchema();
		String strRiskCode = tLCContPlanRiskSchema.getRiskCode();

		LCGrpPolSchema tLCGrpPolSchema = m_GrpPolImpInfo
				.findLCGrpPolSchema(strRiskCode);

		if (tLCGrpPolSchema == null) {
			buildError("formatLCPol", "找不到指定险种所对应的集体保单，险种为：" + strRiskCode);
			return null;
		}

		tLCPolSchema.setPrtNo(tLCGrpPolSchema.getPrtNo());
		tLCPolSchema.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
		tLCPolSchema.setPayIntv(tLCGrpPolSchema.getPayIntv());
		if ("Y".equals(tLCPolSchema.getSpecifyValiDate())
				&& tLCPolSchema.getCValiDate() != null) {
			// 如果磁盘倒入指定生效日期，且生效日期字段有值,那么就用 生效日期字段的值
		} else {
			// 否则一律用集体单的生效日期
			tLCPolSchema.setCValiDate(tLCGrpPolSchema.getCValiDate());
			// 如果是保全增人，磁盘倒入指定生效日期，且生效日期字段无值,那么就用 页面传入值
			if (mTransferData.getValueByName("BQFlag") != null
					&& mTransferData.getValueByName("BQFlag") != "") {
				String EdorValiDate = (String) mTransferData
						.getValueByName("EdorValiDate");
				tLCPolSchema.setCValiDate(EdorValiDate);
			}
		}

		tLCPolSchema.setManageCom(tLCGrpPolSchema.getManageCom());
		tLCPolSchema.setSaleChnl(tLCGrpPolSchema.getSaleChnl());
		tLCPolSchema.setAgentCom(tLCGrpPolSchema.getAgentCom());
		tLCPolSchema.setAgentCode(tLCGrpPolSchema.getAgentCode());
		tLCPolSchema.setAgentGroup(tLCGrpPolSchema.getAgentGroup());
		tLCPolSchema.setAgentCode1(tLCGrpPolSchema.getAgentCode1());
		tLCPolSchema.setGrpContNo(tLCGrpPolSchema.getGrpContNo());
		// tLCPolSchema.set
		tLCPolSchema.setContType("2");
		// tLCPolSchema.setPolTypeFlag("2");
		tLCPolSchema.setPolTypeFlag(contSchema.getPolType());
		tLCPolSchema.setInsuredPeoples(contSchema.getPeoples());
		tLCPolSchema.setRiskCode(strRiskCode);
		tLCPolSchema.setInterestDifFlag("0");

		tLCPolSchema.setContNo(insuredSchema.getContNo().trim());
		tLCPolSchema.setInsuredSex(insuredSchema.getSex().trim());
		tLCPolSchema.setInsuredBirthday(insuredSchema.getBirthday());
		tLCPolSchema.setInsuredName(insuredSchema.getName().trim());

		tLCPolSchema.setInsuredNo(insuredSchema.getInsuredNo());

		return tLCPolSchema;

	}

	public String getExtendFileName(String aFileName) {
		File tFile = new File(aFileName);
		String aExtendFileName = "";
		String name = tFile.getName();
		for (int i = name.length() - 1; i >= 0; i--) {
			if (i < 1) {
				i = 1;
			}
			if (name.substring(i - 1, i).equals(".")) {
				aExtendFileName = name.substring(i, name.length());
				logger.debug("ExtendFileName;" + aExtendFileName);
				return aExtendFileName;
			}
		}
		return aExtendFileName;
	}

	/**
	 * 字符串替换
	 * 
	 * @param s1
	 * @param OriginStr
	 * @param DestStr
	 * @return
	 */
	public static String replace(String s1, String OriginStr, String DestStr) {
		s1 = s1.trim();
		int mLenOriginStr = OriginStr.length();
		for (int j = 0; j < s1.length(); j++) {
			int befLen = s1.length();
			if (s1.substring(j, j + 1) == null
					|| s1.substring(j, j + 1).trim().equals("")) {
				continue;
			} else {
				if (OriginStr != null && DestStr != null) {
					if (j + mLenOriginStr <= s1.length()) {

						if (s1.substring(j, j + mLenOriginStr)
								.equals(OriginStr)) {

							OriginStr = s1.substring(j, j + mLenOriginStr);

							String startStr = s1.substring(0, j);
							String endStr = s1.substring(j + mLenOriginStr, s1
									.length());

							s1 = startStr + DestStr + endStr;

							j = j + s1.length() - befLen;
							if (j < 0) {
								j = 0;
							}
						}
					} else {
						break;
					}
				} else {
					break;
				}
			}
		}
		return s1;
	}

	/**
	 * 得到日志显示结果
	 * 
	 * @return
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		ParseGuideIn tPGI = new ParseGuideIn();
		GlobalInput tGI = new GlobalInput(); // repair:
		tGI.ManageCom = "86";
		tGI.Operator = "001";

		// 准备传输数据 VData
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("FileName", "880000018147_9.xls");
		tTransferData.setNameAndValue("FilePath", "D:/LIS/ui/");
		tTransferData.setNameAndValue("EdorAcceptNo", "6120070503000011");
		tTransferData.setNameAndValue("EdorNo", "6020070521000001");
		tTransferData.setNameAndValue("EdorType", "NI");
		tTransferData.setNameAndValue("EdorValiDate", "2007-05-21");
		tTransferData.setNameAndValue("BQFlag", "Y");

		tVData.add(tTransferData);
		tVData.add(tGI);
		try {
			if (tPGI.submitData(tVData, "")) {
				logger.debug("Succ");
			} else {
				logger.debug(tPGI.mErrors.getFirstError());
			}
		} catch (Exception ex) {
			logger.debug(ex.toString());
		}

		// //接收信息，并作校验处理。
		// //输入参数
		//
		// //公共账户保存
		// LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		// LCInsuredSchema tmLCInsuredSchema = new LCInsuredSchema();
		// LCPolSchema tLCPolSchema = new LCPolSchema();
		// LCDutySet tLCDutySet = new LCDutySet();
		// LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		// LCBnfSet tLCBnfSet = new LCBnfSet();
		// LCInsuredRelatedSet tLCInsuredRelatedSet = new LCInsuredRelatedSet();
		// TransferData tTransferData = new TransferData();
		// //输出参数
		//
		// // GlobalInput tGI = new GlobalInput(); //repair:
		// // tGI.ManageCom = "86";
		// // tGI.Operator = "001";
		// logger.debug("开始保存");
		//
		// tLCGrpContSchema.setGrpContNo("20061211000001");
		// tLCGrpContSchema.setProposalGrpContNo("20061211000001");
		// logger.debug("集体投保单合同号 " +
		// tLCGrpContSchema.getProposalGrpContNo());
		//
		// tmLCInsuredSchema.setInsuredNo("");
		// tmLCInsuredSchema.setName("公共帐户"); //公共帐户名称
		// tmLCInsuredSchema.setRelationToMainInsured("00");
		// tmLCInsuredSchema.setContNo("1");
		// tmLCInsuredSchema.setPrtNo("1");
		// tmLCInsuredSchema.setGrpContNo("20061211000001");
		// //tmLCInsuredSchema.setContPlanCode(request.getParameter("ContPlanCode"));
		// //tmLCInsuredSchema.setExecuteCom(request.getParameter("ExecuteCom"));
		// tmLCInsuredSchema.setInsuredPeoples(0);
		// tmLCInsuredSchema.setCreditGrade("2"); //借用 被保人信用等级 字段保存 保单类型标记
		// tmLCInsuredSchema.setSex("0");
		// tmLCInsuredSchema.setBirthday("1975-01-01");
		//
		// tLCInsuredSet.add(tmLCInsuredSchema);
		//
		// tLCPolSchema.setInsuredNo("1");
		// tLCPolSchema.setGrpContNo("20061211000001");
		// tLCPolSchema.setManageCom("8621");
		// tLCPolSchema.setInsuredPeoples(0); //被保人人数
		// tLCPolSchema.setPolTypeFlag("2"); //保单类型标记
		// //tLCPolSchema.setAutoPubAccFlag(tPubAccFlag[i]);//AutoPubAccFlag
		// tLCPolSchema.setGrpContNo("20061211000001");
		// //tLCPolSchema.setMult(1); //份数
		// tLCPolSchema.setPrem("10000"); //保费
		// //tLCPolSchema.setAmnt(10); //保额
		// //tLCPolSchema.setPayRuleCode("0");
		// tLCPolSchema.setRiskCode("212403");
		// tLCPolSchema.setMainPolNo("212403");
		// tLCPolSchema.setContNo("1");
		//
		// LCBnfSchema tLCBnfSchema = new LCBnfSchema();
		// tLCBnfSchema.setRelationToInsured("00");
		// tLCBnfSet.add(tLCBnfSchema);
		//
		// LCDutySchema tLCDutySchema = new LCDutySchema();
		// tLCDutySchema.setDutyCode("692002");
		// tLCDutySet.add(tLCDutySchema);
		//
		// tTransferData.setNameAndValue("SavePolType", "0");
		// //保全保存标记，默认为0，标识非保全
		// tTransferData.setNameAndValue("SavePolType", "0");
		// //保全保存标记，默认为0，标识非保全
		// //tTransferData.setNameAndValue("FamilyType","0");//家庭单标记
		// tTransferData.setNameAndValue("ContType", "2"); //团单，个人单标记
		// tTransferData.setNameAndValue("PolTypeFlag", "2"); //无名单标记
		// tTransferData.setNameAndValue("InsuredPeoples", 0); //被保险人人数
		// tTransferData.setNameAndValue("PubAmntFlag", "N"); //公共保额标记
		//
		// logger.debug("------PubAmntFlag-----" +
		// tTransferData.getValueByName("PubAmntFlag"));
		//
		// try {
		// // 准备传输数据 VData
		// VData tVData = new VData();
		// tVData.add(tTransferData);
		// tVData.add(tGI);
		// tVData.add(tLCGrpContSchema);
		// tVData.add(tLCInsuredSet);
		// tVData.add(tLCPolSchema);
		// tVData.add(tLCBnfSet);
		// tVData.add(tLCDutySet);
		// tVData.addElement(tLCInsuredRelatedSet);
		//
		// logger.debug("Start to save PubAcc **************");
		// //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
		// if (tPGI.submitData2(tVData, "INSERT||PUBACC")) {
		// tVData.clear();
		// tVData = tPGI.getResult();
		// logger.debug("tVData-----size:" + tVData.size());
		// }
		// }
		//
		// catch (Exception ex) {
		// logger.debug("tVData-----size:" + ex.toString());
		// }

	}

	/**
	 * Build a instance document object for function transfromNode()
	 */
	private void bulidDocument() {
		DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
		dfactory.setNamespaceAware(true);

		try {
			m_doc = dfactory.newDocumentBuilder().newDocument();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Detach node from original document, fast XPathAPI process.
	 * 
	 * @param node
	 * @return
	 * @throws Exception
	 */
	private org.w3c.dom.Node transformNode(org.w3c.dom.Node node)
			throws Exception {
		Node nodeNew = m_doc.importNode(node, true);

		return nodeNew;
	}

	/**
	 * 保单数据准备(按险种单导入的时候使用)
	 * 
	 * @param aVData
	 *            VData
	 * @return VData 返回数据集，包含提交给ProposalBL需要用到的信息。
	 */
	private VData prepareProposalBLData(VData aVData) {
		VData tNewVData = new VData();
		MMap tmpMap = new MMap();
		TransferData tTransferData = (TransferData) aVData
				.getObjectByObjectName("TransferData", 0);
		LCPolSchema tLCPolSchema = (LCPolSchema) aVData.getObjectByObjectName(
				"LCPolSchema", 0);
		LCPremSet tLCPremSet = (LCPremSet) aVData.getObjectByObjectName(
				"LCPremSet", 0);
		LCPremToAccSet tLCPremToAccSet = (LCPremToAccSet) aVData
				.getObjectByObjectName("LCPremToAccSet", 0);
		LCDutySet tLCDutySet = (LCDutySet) aVData.getObjectByObjectName(
				"LCDutySet", 0);
		// 险种ID
		String strID = (String) tTransferData.getValueByName("ID");

		// 备份原ID号
		String strRiskCode = tLCPolSchema.getRiskCode();
		String InsuredId = tLCPolSchema.getInsuredNo();
		String contId = tLCPolSchema.getContNo();
		// 导入的时候使用appflag字段缓存连带被保险人ID信息
		String relaInsId = tLCPolSchema.getAppFlag();

		// 查询集体保单信息
		LCGrpPolSchema sLCGrpPolSchema = m_GrpPolImpInfo
				.findLCGrpPolSchema(strRiskCode);
		if (sLCGrpPolSchema == null) {
			buildError("prepareProposalBLData", strRiskCode + "险种对应的集体投保单没有找到!");
			return null;
		}
		// add by zhangtao 2007-04-03 保全加人时自动计算保险期间需要使用生效日期
		mTransferData.setNameAndValue("CvaliDate", sLCGrpPolSchema
				.getCValiDate());

		// 拷贝一份，避免修改本地数据
		LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
		tLCGrpPolSchema.setSchema(sLCGrpPolSchema);
		boolean mainPolFlag = false;
		// 初始化是不是主险 如果不是需要主险信息
		logger.debug("主险号码=====" + tLCPolSchema.getMainPolNo());
		if (strRiskCode.trim().equals(tLCPolSchema.getMainPolNo().trim())) {
			// mainRiskCode = tLCPolSchema.getRiskCode() ;
			mainPolFlag = true;
		}
		/*
		 * mainRiskCode = tLCPolSchema.getRiskCode(); if
		 * (!tLCPolSchema.getMainPolNo().equals("") &&
		 * !tLCPolSchema.getMainPolNo().equals( strID )) { LCPolSchema
		 * tMainLCPolSchema = m_GrpPolImpInfo.findMainLCPolSchema(
		 * tLCPolSchema.getMainPolNo()); if (tMainLCPolSchema == null) {
		 * CError.buildErr(this, "查找主险保单[" + tLCPolSchema.getMainPolNo() +
		 * "]失败"); return null; } mainRiskCode = tMainLCPolSchema.getRiskCode(); }
		 */
		// 格式化保单信息
		VData tPolData = m_GrpPolImpInfo.formatLCPol(tLCPolSchema, strID,
				mISPubAcc);
		if (tPolData == null) {
			logger.debug(m_GrpPolImpInfo.mErrors.toString());
			mErrors.copyAllErrors(m_GrpPolImpInfo.mErrors);
			return null;
		}
		tLCPolSchema = (LCPolSchema) tPolData.getObjectByObjectName(
				"LCPolSchema", 0);
		if (!mainPolFlag) {
			LCPolSchema mainPolSchema = m_GrpPolImpInfo
					.findMainPolSchema(tLCPolSchema.getMainPolNo());
			if (mainPolSchema == null) {
				CError.buildErr(this, "查找主险保单[" + strID + "]失败");
				return null;
			}
			mainPolBL.setSchema(mainPolSchema);
		} else {
			mainPolBL.setSchema(tLCPolSchema);
		}

		MMap poldataMap = (MMap) tPolData.getObjectByObjectName("MMap", 0);
		if (poldataMap != null) {
			tmpMap.add(poldataMap);
		}

		// 被保险人、合同信息
		LCInsuredSchema insuredSchema = m_GrpPolImpInfo
				.findInsuredfromCache(InsuredId);
		if (insuredSchema == null) {
			CError.buildErr(this, "未找到被保险人[" + InsuredId + "]");
			return null;
		}

		LCContSchema contSchema = m_GrpPolImpInfo.findLCContfromCache(contId);
		if (contSchema == null) {
			CError.buildErr(this, "未找到合同[" + contId + "]");
			return null;
		}
		// 设置主被保险人信息
		contSchema.setInsuredBirthday(insuredSchema.getBirthday());
		contSchema.setInsuredNo(insuredSchema.getInsuredNo());
		contSchema.setInsuredName(insuredSchema.getName());
		contSchema.setInsuredIDNo(insuredSchema.getIDNo());
		contSchema.setInsuredIDType(insuredSchema.getIDType());
		contSchema.setInsuredSex(insuredSchema.getSex());

		// 责任信息查询和生成

		LCContPlanRiskSchema tLCContPlanRiskSchema = new LCContPlanRiskSchema();
		tLCContPlanRiskSchema.setMainRiskCode(mainPolBL.getRiskCode());
		tLCContPlanRiskSchema.setGrpContNo(this.mGrpContNo);
		tLCContPlanRiskSchema.setRiskCode(tLCPolSchema.getRiskCode());
		// 00-默认值
		tLCContPlanRiskSchema.setContPlanCode("00");
		tLCContPlanRiskSchema.setProposalGrpContNo(this.mProposalGrpContNo);
		tLCContPlanRiskSchema.setPlanType("3"); // 默认值

		LCContPlanRiskDB tLCContPlanRiskDB = new LCContPlanRiskDB();
		tLCContPlanRiskDB.setSchema(tLCContPlanRiskSchema);

		LCDutySet tempDutySet = null;
		LCDutySet temp2DutySet = null;

		logger.debug("查询险种信息，tLCDutySet = " + tLCDutySet.encode());

		
		if (!tLCContPlanRiskDB.getInfo()) {
			if (mISPubAcc) {
				if (tLCDutySet != null && tLCDutySet.size() > 0) {
					tempDutySet = tLCDutySet;
				} else {
					// 责任项页签没有数据，则用险种保单页签的数据
					LCDutySchema ntempDutySchema = new LCDutySchema();
					// logger.debug("tLCPolSchema==============="+
					// tLCPolSchema.encode());
					// ===============加上specflag='N'的条件，把用于公共帐户的责任过滤掉，分红险用到=====//
					String tDutyCode = "";
					//update by cxq 修改绑定变量
					sqlbv = new SQLwithBindVariables();
					String tSQL = "select dutycode from lmriskduty where riskcode='?RiskCode?' and specflag='N'";
					sqlbv.sql(tSQL);
					sqlbv.put("RiskCode", tLCPolSchema.getRiskCode());
					ExeSQL tExSQL = new ExeSQL();
					tDutyCode = tExSQL.getOneValue(sqlbv);
					logger.debug("DutyCode== " + tDutyCode);
					ntempDutySchema.setDutyCode(tDutyCode);
					// ===============end UpDate
					// ========================================================//
					setDutyByPolInfo(ntempDutySchema, tLCPolSchema);
					tempDutySet = new LCDutySet();
					tempDutySet.add(ntempDutySchema);
				}
			} else {
				CError.buildErr(this, "请先录入险种[" + strRiskCode + "]的险种信息！");
				return null;
			}
		} else {
			// 如果有默认值，则需要查询默认值
			ContPlanQryBL contPlanQryBL = new ContPlanQryBL();
			VData tVData = new VData();
			tVData.add(tLCContPlanRiskSchema);
			logger.debug("-----------查询默认值");
			mPubAmntFlag = (String) mTransferData.getValueByName("PubAmntFlag");
			logger.debug("+++++++++mPubAmntFlag:  " + mPubAmntFlag);
			tVData.add(mTransferData);

			boolean b = contPlanQryBL.submitData(tVData, "");
			if (!b || contPlanQryBL.mErrors.needDealError()) {
				CError.buildErr(this, "查询计划要约出错:", contPlanQryBL.mErrors);
				return null;
			}
			tVData = contPlanQryBL.getResult();
			temp2DutySet = (LCDutySet) tVData.getObjectByObjectName(
					"LCDutySet", 0);

			// logger.debug("tempDutySet.size======"+tempDutySet.size());
			if (temp2DutySet == null || temp2DutySet.size() == 0) {
				// 只有一条责任项的险种，查选计划要约会返回Schema而不是Set
				LCDutySchema ttempDutySchema = (LCDutySchema) tVData
						.getObjectByObjectName("LCDutySchema", 0);

				// 对于一条责任的险种，在模板中填写了责任代码，则在进行解析时，tLCDutySet中会有一条记录
				// 这样，就做以下判断处理
				if (tLCDutySet != null && tLCDutySet.size() == 1) {
					if (!ttempDutySchema.getDutyCode().equals(
							tLCDutySet.get(1).getDutyCode())) {
						CError.buildErr(this, "责任" + tLCDutySet.get(1)
								+ "查询计划要约出错:", contPlanQryBL.mErrors);
						return null;
					}
				}

				setDutyByPolInfo(ttempDutySchema, tLCPolSchema);
				setPolInfoByDuty(tLCPolSchema, ttempDutySchema);
				if (!checkMult(tLCPolSchema.getRiskCode(), ttempDutySchema)) {
					return null;
				}
				tempDutySet = new LCDutySet();
				tempDutySet.add(ttempDutySchema);

			} else if ((tLCDutySet == null || tLCDutySet.size() == 0)
					&& temp2DutySet != null && temp2DutySet.size() > 0) {
				// 模板中填写责任代码为空，但是又是多责任险种，取计划要约默认记录
				logger.debug("责任项为空=========  " + temp2DutySet.encode());
				tempDutySet = new LCDutySet();
				LCDutySchema ttempDutySchema = new LCDutySchema();
				for (int i = 1; i <= temp2DutySet.size(); i++) {
					ttempDutySchema = temp2DutySet.get(i);
					setDutyByPolInfo(ttempDutySchema, tLCPolSchema);
					if (!checkMult(tLCPolSchema.getRiskCode(), ttempDutySchema)) {
						return null;
					}
					tempDutySet.add(ttempDutySchema);
				}
				setPolInfoByDuty(tLCPolSchema, temp2DutySet.get(1));
			} else if (tLCDutySet != null && tLCDutySet.size() > 0
					&& temp2DutySet != null && temp2DutySet.size() > 0) {
				// 根据传入的责任项编码，筛选默认值中的责任项
				LCDutySet newtempDutySet = new LCDutySet();
				for (int i = 1; i <= temp2DutySet.size(); i++) {
					for (int j = 1; j <= tLCDutySet.size(); j++) {
						logger.debug("tLCDutySet=========  "
								+ tLCDutySet.encode());
						if (temp2DutySet.get(i).getDutyCode().equals(
								tLCDutySet.get(j).getDutyCode())) {
							logger.debug("tempDutySet.get(j)"
									+ temp2DutySet.get(i).encode());
							if (tLCDutySet.get(j).getPrem() > 0) {
								temp2DutySet.get(i).setPrem(
										tLCDutySet.get(j).getPrem());
							}
							if (tLCDutySet.get(j).getAmnt() > 0) {
								temp2DutySet.get(i).setAmnt(
										tLCDutySet.get(j).getAmnt());
							}
							if (tLCDutySet.get(j).getMult() > 0) {
								temp2DutySet.get(i).setMult(
										tLCDutySet.get(j).getMult());
							}
							temp2DutySet.get(i).setPayIntv(
									tLCPolSchema.getPayIntv());
							if (!checkMult(tLCPolSchema.getRiskCode(),
									temp2DutySet.get(i))) {
								return null;
							}
							newtempDutySet.add(temp2DutySet.get(i));
						}
					}

				}
				setPolInfoByDuty(tLCPolSchema, temp2DutySet.get(1));
				if (newtempDutySet != null && newtempDutySet.size() > 0) {
					tempDutySet = newtempDutySet;
					logger.debug("根据传入的责任项编码筛选后的默认值中的责任项"
							+ tempDutySet.encode());
				} else {
					CError.buildErr(this, "查询计划要约出错:", contPlanQryBL.mErrors);
					return null;
				}

			}

		}
		logger.debug("责任项准备完毕");
		// 清空借用字段
		tLCPolSchema.setAppFlag("");
		tLCPolSchema.setUWCode("");
		tLCPolSchema.setApproveFlag("");
		tLCPolSchema.setUWFlag("");

		// 处理连身被保险人
		LCInsuredRelatedSet tRelaInsSet = null;
		String[] relaIns = null;
		if (!"".equals(StrTool.cTrim(relaInsId))) {
			relaIns = relaInsId.split(",");
			if (relaIns == null) {
				relaInsId.split(";");
			}
			if (relaIns != null) {
				tRelaInsSet = m_GrpPolImpInfo.getInsuredRelaData(tLCPolSchema
						.getInsuredNo(), relaIns);
				if (tRelaInsSet == null) {
					// 创建连带被保险人出错
					mErrors.copyAllErrors(m_GrpPolImpInfo.mErrors);
					return null;
				}

			}
		}

		String PolKey = m_GrpPolImpInfo.getPolKey(contId, InsuredId,
				strRiskCode);
		tTransferData.setNameAndValue("PolKey", PolKey);
		logger.debug("责任项准备完毕-PolKey" + PolKey);
		logger.debug("责任项准备完毕-strID" + strID);

		// 受益人信息
		LCBnfSet tLCBnfSet = new LCBnfSet();
		if (!mISPubAcc) {
			tLCBnfSet = m_GrpPolImpInfo.getBnfSetByPolId(strID);
			logger.debug("受益人信息！");
			if (tLCBnfSet != null && tLCBnfSet.size() > 0) {
				VData tr = m_GrpPolImpInfo.perareBnf(tLCBnfSet, insuredSchema,
						tLCGrpPolSchema, InsuredId);

				MMap tmap = (MMap) tr.getObjectByObjectName("MMap", 0);
				if (tmap != null) {
					tmpMap.add(tmap);
				}
				tLCBnfSet = (LCBnfSet) tr.getObjectByObjectName("LCBnfSet", 0);
			}
		}
		logger.debug("受益人信息-tLCBnfSet");
		tTransferData.setNameAndValue("samePersonFlag", 0);
		tTransferData.setNameAndValue("GrpImport", 1); // 磁盘投保标志
		// 险种描述信息
		LMRiskAppSchema tLMRiskAppSchema = m_GrpPolImpInfo
				.findLMRiskAppSchema(strRiskCode);
		if (tLMRiskAppSchema == null) {
			buildError("prepareData", strRiskCode + "险种对应的险种承保描述没有找到!");
			return null;
		}
		LMRiskSchema tLMRiskSchema = m_GrpPolImpInfo
				.findLMRiskSchema(strRiskCode);
		if (tLMRiskSchema == null) {
			buildError("prepareData", strRiskCode + "险种对应的险种承保描述没有找到!");
			return null;
		}
		logger.debug("险种描述信息");

		LDGrpSchema ttLDGrpSchema = m_GrpPolImpInfo
				.findLDGrpSchema(tLCGrpPolSchema.getCustomerNo());
		if (ttLDGrpSchema == null) {
			buildError("prepareData", tLCGrpPolSchema.getCustomerNo()
					+ "对应的集体信息没有找到!");
			return null;
		}
		logger.debug("对应的集体信息");
		// ========Liuliang==2005-5-9==Update==Start==
		// 增加了保全分支判断,如果是 保全增加被保人，设置保全保存标记 2
		if (mTransferData.getValueByName("BQFlag") != null
				&& mTransferData.getValueByName("BQFlag") != ""
				&& "Y".equals(StrTool.cTrim(mTransferData.getValueByName(
						"BQFlag").toString()))
				&& "NI".equals(StrTool.cTrim(mTransferData.getValueByName(
						"EdorType").toString()))) {
			// 保全保存标记
			tTransferData.removeByName("SavePolType");
			tTransferData.setNameAndValue("SavePolType", "2");
			tTransferData.setNameAndValue("EdorType", mTransferData
					.getValueByName("EdorType"));
			tTransferData.setNameAndValue("EdorValiDate", mTransferData
					.getValueByName("EdorValiDate"));
		}
		logger.debug("对应的集体信息");
		// ========Liuliang==2005-5-9==Update==End==

		tNewVData.add(tTransferData);
		tNewVData.add(mGlobalInput);

		// if (tLCDutySet.size() > 1)
		// {
		// tNewVData.add(tLCDutySet);
		// }
		// else if (tLCDutySet.size() == 1)
		// {
		// setPolInfoByDuty(tLCPolSchema, tLCDutySet.get(1));
		// tNewVData.add(tLCDutySet.get(1));
		// }
		// else
		// {
		// tNewVData.add(new LCDutySchema());
		// }
		if (tempDutySet == null || tempDutySet.size() <= 0) {
			CError.buildErr(this, "险种[" + strRiskCode + "]责任不能为空");
			return null;
		}
		// 本处处理添加日期：2007-01-31
		// 本处用于特殊处理,将lcinsured的字段Salary值赋给LCPolSchema.StandbyFlag3字段
		// 该处理目前用于处理团体工伤险
		try {
			if (tLCPolSchema.getStandbyFlag3() == null
					|| tLCPolSchema.getStandbyFlag3().trim().equals("")) {
				tLCPolSchema.setStandbyFlag3(String.valueOf(insuredSchema
						.getSalary()));
			}
			for (int n = 0; n < tempDutySet.size(); n++) {
				tempDutySet.get(n + 1).setStandbyFlag3(
						tLCPolSchema.getStandbyFlag3());
			}
		} catch (Exception ex) {
		}
		logger.debug("tempDutySet" + tempDutySet.encode());
		tNewVData.add(tempDutySet);
		if (tRelaInsSet != null) {
			tNewVData.add(tRelaInsSet);
		}
		tNewVData.add(tLCPolSchema);
		tNewVData.add(tLCPremSet);
		tNewVData.add(tLCPremToAccSet);
		tNewVData.add(contSchema);
		tNewVData.add(insuredSchema);
		tNewVData.add(tLCBnfSet);

		tNewVData.add(tLCGrpPolSchema);
		tNewVData.add(m_GrpPolImpInfo.getLCGrpAppntSchema());
		tNewVData.add(tLMRiskAppSchema);
		tNewVData.add(tLMRiskSchema);
		tNewVData.add(ttLDGrpSchema);
		tNewVData.add(mainPolBL);
		tNewVData.add(tmpMap);
		logger.debug("数据准备完毕！");

		return tNewVData;
	}

	/**
	 * 创建错误日志
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "ParseGuideIn";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 记录日志信息
	 * 
	 * @param contId
	 *            String
	 * @param insuredId
	 *            String
	 * @param polId
	 *            String
	 * @param contplancode
	 *            String
	 * @param riskcode
	 *            String
	 * @param insuredSchema
	 *            LCInsuredSchema
	 */
	private void logError(String contId, String insuredId, String polId,
			String contplancode, String riskcode, LCInsuredSchema insuredSchema) {
		if (m_GrpPolImpInfo.mErrors.getErrorCount() >= 0) {
			this.mErrors.copyAllErrors(m_GrpPolImpInfo.mErrors);
		}
		m_GrpPolImpInfo.logError(mBatchNo, mGrpContNo, contId, insuredId,
				polId, contplancode, riskcode, insuredSchema, this.mErrors,
				mGlobalInput, FileName);
		logErrors.copyAllErrors(this.mErrors);
		this.mErrors.clearErrors();
		m_GrpPolImpInfo.mErrors.clearErrors();

	}

	/**
	 * 获取排序的keyset
	 * 
	 * @param set
	 *            Set
	 * @return Object[]
	 */

	private int[] getOrderKeySetInt(Set set) {
		if (set == null) {
			return null;
		}
		Object[] oSet = set.toArray();
		int dest[] = new int[oSet.length];
		for (int i = 0; i < oSet.length; i++) {
			dest[i] = Integer.parseInt((String) oSet[i]);
		}
		Arrays.sort(dest);
		return dest;
	}

	/**
	 * 通过duty设置一些lcpol的元素
	 * 
	 * @param tLCPolSchema
	 *            LCPolSchema
	 * @param dutySchema
	 *            LCDutySchema
	 */
	private void setPolInfoByDuty(LCPolSchema tLCPolSchema,
			LCDutySchema dutySchema) {

		tLCPolSchema.setInsuYear(dutySchema.getInsuYear());
		tLCPolSchema.setInsuYearFlag(dutySchema.getInsuYearFlag());
		logger.debug(dutySchema.getInsuYear() + "-"
				+ dutySchema.getInsuYearFlag());
		if (tLCPolSchema.getPrem() <= 0) {
			tLCPolSchema.setPrem(dutySchema.getPrem());
		}
		if (tLCPolSchema.getAmnt() <= 0) {
			tLCPolSchema.setAmnt(dutySchema.getAmnt());
		}
		tLCPolSchema.setPayIntv(dutySchema.getPayIntv());
		tLCPolSchema.setPayEndYear(dutySchema.getPayEndYear());
		tLCPolSchema.setPayEndYearFlag(dutySchema.getPayEndYearFlag());
		tLCPolSchema.setGetYear(dutySchema.getGetYear());
		tLCPolSchema.setGetYearFlag(dutySchema.getGetYearFlag());
		tLCPolSchema.setAcciYear(dutySchema.getAcciYear());
		tLCPolSchema.setAcciYearFlag(dutySchema.getAcciYearFlag());
		if (tLCPolSchema.getMult() <= 0) {
			tLCPolSchema.setMult(dutySchema.getMult());
		}
		// 计算方向,在按分数卖的保单，切记算方向为O的时候
		if (dutySchema.getMult() > 0
				&& "O".equals(StrTool.cTrim(dutySchema.getPremToAmnt()))) {
			tLCPolSchema.setPremToAmnt(dutySchema.getPremToAmnt());
		}
		tLCPolSchema.setStandbyFlag1(dutySchema.getStandbyFlag1());
		tLCPolSchema.setStandbyFlag2(dutySchema.getStandbyFlag2());
		tLCPolSchema.setStandbyFlag3(dutySchema.getStandbyFlag3());
	}

	private void setDutyByPolInfo(LCDutySchema dutySchema,
			LCPolSchema tLCPolSchema) {
		logger.debug(mGrpContNo);
		String temcontno = mGrpContNo;
		String temriskcode = tLCPolSchema.getRiskCode();
		logger.debug(tLCPolSchema.getRiskCode());
		// /////////////////////////here i will search info from
		// database///////////write by yaory
		// //利用集体保单号与险种号查询LCContPlanDutyParam/////////////////////////////
		if (tLCPolSchema.getMult() > 0) {
			dutySchema.setMult(tLCPolSchema.getMult());
		}
		if (tLCPolSchema.getPrem() > 0) {
			dutySchema.setPrem(tLCPolSchema.getPrem());
		}
		if (tLCPolSchema.getAmnt() > 0) {
			dutySchema.setAmnt(tLCPolSchema.getAmnt());
		}
		ExeSQL exeSql = new ExeSQL();
		SSRS tSSRS = new SSRS();
		//update by cxq 修改绑定变量
		sqlbv = new SQLwithBindVariables();
		sqlbv.sql("select payintv from lcgrppol where grpcontno='?temcontno?' and riskcode='?temriskcode?'");
		sqlbv.put("temcontno", temcontno);
		sqlbv.put("temriskcode", temriskcode);
		tSSRS = exeSql.execSQL(sqlbv);
		if (tSSRS.MaxRow > 0) {
			dutySchema.setPayIntv(tSSRS.GetText(1, 1));
		} else {

			if (tLCPolSchema.getPayIntv() > 0) {
				dutySchema.setPayIntv(tLCPolSchema.getPayIntv());
			}
		}
		// ///////////
		// tSSRS = exeSql.execSQL(
		// "select calfactorvalue,calfactor from LCContPlanDutyParam where
		// grpcontno='" +
		// temcontno + "' and riskcode='" + temriskcode +
		// "' and calfactor='InsuYear'");
		// if (tSSRS.MaxRow > 0) {
		// dutySchema.setInsuYear(tSSRS.GetText(1, 1));
		// }
		// else {
		//
		// if (tLCPolSchema.getInsuYear() > 0) {
		// dutySchema.setInsuYear(tLCPolSchema.getInsuYear());
		// }
		// }
		// //////////////
		// tSSRS = exeSql.execSQL(
		// "select calfactorvalue,calfactor from LCContPlanDutyParam where
		// grpcontno='" +
		// temcontno + "' and riskcode='" + temriskcode +
		// "' and calfactor='InsuYearFlag'");
		// if (tSSRS.MaxRow > 0) {
		// dutySchema.setInsuYearFlag(tSSRS.GetText(1, 1));
		// }
		// else {
		//
		// if (!"".equals(StrTool.cTrim(tLCPolSchema.getInsuYearFlag()))) {
		// dutySchema.setInsuYearFlag(tLCPolSchema.getInsuYearFlag());
		// }
		// }
		// ///////////
		// logger.debug(tLCPolSchema.getInsuYearFlag() + "-" +
		// tLCPolSchema.getInsuYear());
		// tSSRS = exeSql.execSQL(
		// "select calfactorvalue,calfactor from LCContPlanDutyParam where
		// grpcontno='" +
		// temcontno + "' and riskcode='" + temriskcode +
		// "' and calfactor='PayEndYear'");
		// if (tSSRS.MaxRow > 0) {
		// dutySchema.setPayEndYear(tSSRS.GetText(1, 1));
		// }
		// else {
		//
		// if (tLCPolSchema.getPayEndYear() > 0) {
		// dutySchema.setPayEndYear(tLCPolSchema.getPayEndYear());
		// }
		// }
		// ///////////
		// tSSRS = exeSql.execSQL(
		// "select calfactorvalue,calfactor from LCContPlanDutyParam where
		// grpcontno='" +
		// temcontno + "' and riskcode='" + temriskcode +
		// "' and calfactor='PayEndYearFlag'");
		// if (tSSRS.MaxRow > 0) {
		// dutySchema.setPayEndYearFlag(tSSRS.GetText(1, 1));
		// }
		// else {
		//
		// if (!"".equals(StrTool.cTrim(tLCPolSchema.getPayEndYearFlag()))) {
		// dutySchema.setPayEndYearFlag(tLCPolSchema.getPayEndYearFlag());
		// }
		// }
		// ////////////////////
		// tSSRS = exeSql.execSQL(
		// "select calfactorvalue,calfactor from LCContPlanDutyParam where
		// grpcontno='" +
		// temcontno + "' and riskcode='" + temriskcode +
		// "' and calfactor='GetYear'");
		// if (tSSRS.MaxRow > 0) {
		// dutySchema.setGetYear(tSSRS.GetText(1, 1));
		// }
		// else {
		//
		// if (tLCPolSchema.getGetYear() > 0) {
		// dutySchema.setGetYear(tLCPolSchema.getGetYear());
		// }
		// }
		// //////////////////////
		// tSSRS = exeSql.execSQL(
		// "select calfactorvalue,calfactor from LCContPlanDutyParam where
		// grpcontno='" +
		// temcontno + "' and riskcode='" + temriskcode +
		// "' and calfactor='GetYearFlag'");
		// if (tSSRS.MaxRow > 0) {
		// dutySchema.setGetYearFlag(tSSRS.GetText(1, 1));
		// }
		// else {
		//
		// if (!"".equals(StrTool.cTrim(tLCPolSchema.getGetYearFlag()))) {
		// dutySchema.setGetYearFlag(tLCPolSchema.getGetYearFlag());
		// }
		// }
		// ////////////////////////
		// tSSRS = exeSql.execSQL(
		// "select calfactorvalue,calfactor from LCContPlanDutyParam where
		// grpcontno='" +
		// temcontno + "' and riskcode='" + temriskcode +
		// "' and calfactor='GetStartType'");
		// if (tSSRS.MaxRow > 0) {
		// dutySchema.setGetStartType(tSSRS.GetText(1, 1));
		// }
		// else {
		//
		// if (!"".equals(StrTool.cTrim(tLCPolSchema.getGetStartType()))) {
		// dutySchema.setGetStartType(tLCPolSchema.getGetStartType());
		// }
		// }
		// /////////////////////////
		// //计算方向
		// tSSRS = exeSql.execSQL(
		// "select calfactorvalue,calfactor from LCContPlanDutyParam where
		// grpcontno='" +
		// temcontno + "' and riskcode='" + temriskcode +
		// "' and calfactor='PremToAmnt'");
		// if (tSSRS.MaxRow > 0) {
		// dutySchema.setPremToAmnt(tSSRS.GetText(1, 1));
		// }
		// else {
		// if (!"".equals(StrTool.cTrim(tLCPolSchema.getPremToAmnt()))) {
		// dutySchema.setPremToAmnt(tLCPolSchema.getPremToAmnt());
		// }
		// }
		// ///////////////////////
		// //计算规则被缓存在 最终核保人编码 UWCode 字段
		// tSSRS = exeSql.execSQL(
		// "select calfactorvalue,calfactor from LCContPlanDutyParam where
		// grpcontno='" +
		// temcontno + "' and riskcode='" + temriskcode +
		// "' and calfactor='CalRule'");
		// if (tSSRS.MaxRow > 0) {
		// dutySchema.setCalRule(tSSRS.GetText(1, 1));
		// }
		// else {
		//
		// if (!"".equals(StrTool.cTrim(tLCPolSchema.getUWCode()))) {
		// dutySchema.setCalRule(tLCPolSchema.getUWCode());
		// }
		// }
		// /////////////////////
		// //费率
		// tSSRS = exeSql.execSQL(
		// "select calfactorvalue,calfactor from LCContPlanDutyParam where
		// grpcontno='" +
		// temcontno + "' and riskcode='" + temriskcode +
		// "' and calfactor='FloatRate'");
		// if (tSSRS.MaxRow > 0) {
		// dutySchema.setFloatRate(tSSRS.GetText(1, 1));
		// }
		// else {
		//
		// if (tLCPolSchema.getFloatRate() > 0) {
		// if ("0".equals(dutySchema.getCalRule()) ||
		// "1".equals(dutySchema.getCalRule())) {
		// //计算规则为 0-表定费率 或 1-统一费率 时
		// //费率必须是从默认值中取得
		// }
		// else {
		// dutySchema.setFloatRate(tLCPolSchema.getFloatRate());
		// }
		// }
		// }
		// ////////////////
		// tSSRS = exeSql.execSQL(
		// "select calfactorvalue,calfactor from LCContPlanDutyParam where
		// grpcontno='" +
		// temcontno + "' and riskcode='" + temriskcode +
		// "' and calfactor='standbyflag1'");
		// if (tSSRS.MaxRow > 0) {
		// dutySchema.setStandbyFlag1(tSSRS.GetText(1, 1));
		// }
		// else {
		//
		// if (!"".equals(StrTool.cTrim(tLCPolSchema.
		// getStandbyFlag1()))) {
		// dutySchema.setStandbyFlag1(tLCPolSchema.
		// getStandbyFlag1());
		// }
		// }

		if (dutySchema.getInsuYear() <= 0) {
			if (tLCPolSchema.getInsuYear() > 0) {
				dutySchema.setInsuYear(tLCPolSchema.getInsuYear());
			}
		}
		// ////////////
		if (dutySchema.getInsuYearFlag() == null
				|| dutySchema.getInsuYearFlag().equals("")) {

			if (!"".equals(StrTool.cTrim(tLCPolSchema.getInsuYearFlag()))) {
				dutySchema.setInsuYearFlag(tLCPolSchema.getInsuYearFlag());
			}
		}
		// /////////
		if (dutySchema.getPayEndYear() <= 0) {
			if (tLCPolSchema.getPayEndYear() > 0) {
				dutySchema.setPayEndYear(tLCPolSchema.getPayEndYear());
			}
		}
		// /////////
		if (dutySchema.getPayEndYearFlag() == null
				|| "".equals(dutySchema.getPayEndYearFlag())) {
			if (!"".equals(StrTool.cTrim(tLCPolSchema.getPayEndYearFlag()))) {
				dutySchema.setPayEndYearFlag(tLCPolSchema.getPayEndYearFlag());
			}
		}
		// //////////////////
		if (dutySchema.getGetYear() <= 0) {
			if (tLCPolSchema.getGetYear() > 0) {
				dutySchema.setGetYear(tLCPolSchema.getGetYear());
			}
		}
		// ////////////////////
		if (dutySchema.getGetYearFlag() == null
				|| "".equals(dutySchema.getGetYearFlag())) {

			if (!"".equals(StrTool.cTrim(tLCPolSchema.getGetYearFlag()))) {
				dutySchema.setGetYearFlag(tLCPolSchema.getGetYearFlag());
			}
		}
		// //////////////////////
		if (dutySchema.getGetStartType() == null
				|| "".equals(dutySchema.getGetStartType())) {

			if (!"".equals(StrTool.cTrim(tLCPolSchema.getGetStartType()))) {
				dutySchema.setGetStartType(tLCPolSchema.getGetStartType());
			}
		}
		// ///////////////////////
		// 计算方向
		if (dutySchema.getPremToAmnt() == null
				|| "".equals(dutySchema.getPremToAmnt())) {
			if (!"".equals(StrTool.cTrim(tLCPolSchema.getPremToAmnt()))) {
				dutySchema.setPremToAmnt(tLCPolSchema.getPremToAmnt());
			}
		}
		// /////////////////////
		// 计算规则被缓存在 最终核保人编码 UWCode 字段
		if (dutySchema.getCalRule() == null
				|| "".equals(dutySchema.getCalRule())) {
			if (!"".equals(StrTool.cTrim(tLCPolSchema.getUWCode()))) {
				dutySchema.setCalRule(tLCPolSchema.getUWCode());
			}
		}
		// ///////////////////
		// 费率
		if (dutySchema.getFloatRate() <= 0) {
			if (tLCPolSchema.getFloatRate() > 0) {
				if ("0".equals(dutySchema.getCalRule())
						|| "1".equals(dutySchema.getCalRule())) {
					// 计算规则为 0-表定费率 或 1-统一费率 时
					// 费率必须是从默认值中取得
				} else {
					dutySchema.setFloatRate(tLCPolSchema.getFloatRate());
				}
			}
		}
		if (dutySchema.getStandbyFlag1() == null
				|| "".equals(dutySchema.getStandbyFlag1())) {
			if (!"".equals(StrTool.cTrim(tLCPolSchema.getStandbyFlag1()))) {
				dutySchema.setStandbyFlag1(tLCPolSchema.getStandbyFlag1());
			}
		}

		if (!"".equals(StrTool.cTrim(tLCPolSchema.getStandbyFlag2()))) {
			dutySchema.setStandbyFlag2(tLCPolSchema.getStandbyFlag2());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getStandbyFlag3()))) {
			// dutySchema.setStandbyFlag3( tLCPolSchema.getStandbyFlag3() );
			dutySchema.setDutyCode(tLCPolSchema.getStandbyFlag3()); // edit
																	// yaory
			logger.debug("责任==" + tLCPolSchema.getStandbyFlag3());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getApproveFlag()))) {
			dutySchema.setGetLimit(tLCPolSchema.getApproveFlag());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getUWFlag()))) {
			dutySchema.setGetRate(tLCPolSchema.getUWFlag());
		}

		// ====add===zhangtao====保全加人特殊处理，保险期间根据保全生效日期自动计算===BGN======
		String sBQFlag = (String) mTransferData.getValueByName("BQFlag");
		if (sBQFlag != null && sBQFlag.equals("Y")) {
			dutySchema.setPayIntv(0); // 加人都是趸交

			if (dutySchema.getInsuYearFlag() != null
					&& dutySchema.getInsuYearFlag().equals("M")) {
				// String sEdorAcceptNo = (String)
				// mTransferData.getValueByName("EdorAcceptNo");
				// String sEdorNo = (String)
				// mTransferData.getValueByName("EdorNo");
				// String sEdorType = (String)
				// mTransferData.getValueByName("EdorType");

				String sEdorValiDate = (String) mTransferData
						.getValueByName("EdorValiDate");
				String sCvaliDate = (String) mTransferData
						.getValueByName("CvaliDate");
				int iInsuYear = PubFun.calInterval(sCvaliDate, sEdorValiDate,
						"M");

				// logger.debug("_______________________________\n
				// sCvaliDate:" + sCvaliDate);
				// logger.debug("_______________________________\n
				// sEdorValiDate:" + sEdorValiDate);
				// logger.debug("_______________________________\n
				// iInsuYear:" + iInsuYear);

				dutySchema.setInsuYear(dutySchema.getInsuYear() - iInsuYear);
				// dutySchema.setPayEndYear();
				// dutySchema.setPayEndYearFlag();
			}
		}
		// ====add===zhangtao====保全加人特殊处理，保险期间根据保全生效日期自动计算===END======
	}

	private void qryPerDutyCalMode(String tRiskCode) {
		String tStrSQL = "";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();

		//update by cxq 修改绑定变量
		sqlbv = new SQLwithBindVariables();
		tStrSQL = "select a.dutycode,a.calmode from lmduty a,lmriskduty b"
				+ " where a.dutycode=b.dutycode and b.riskcode='?tRiskCode?'";
		sqlbv.sql(tStrSQL);
		sqlbv.put("tRiskCode",tRiskCode);
		tSSRS = tExeSQL.execSQL(sqlbv);
		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				mHashInfo.put(tSSRS.GetText(i, 1), tSSRS.GetText(i, 2));
			}
		}
	}

	private String getDutyCalMode(String tRiskCode, String tDutyCode) {
		if (mHashInfo == null || mHashInfo.size() <= 0
				|| (String) mHashInfo.get(tDutyCode) == null) {
			qryPerDutyCalMode(tRiskCode);
		}
		return mHashInfo.get(tDutyCode).toString();
	}

	private void qryPerDutyAmntFlag(String tRiskCode) {
		String tStrSQL = "";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		//update by cxq 修改绑定变量
		sqlbv = new SQLwithBindVariables();
		tStrSQL = "select a.dutycode,a.AmntFlag from lmduty a,lmriskduty b"
				+ " where a.dutycode=b.dutycode and b.riskcode='?tRiskCode?'";
		sqlbv.sql(tStrSQL);
		sqlbv.put("tRiskCode", tRiskCode);
		tSSRS = tExeSQL.execSQL(sqlbv);
		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				mHashInfo.put(tSSRS.GetText(i, 1), tSSRS.GetText(i, 2));
			}
		}
	}

	private String getDutyAmntFlag(String tRiskCode, String tDutyCode) {
		if (mHashInfo == null || mHashInfo.size() <= 0
				|| (String) mHashInfo.get(tDutyCode) == null) {
			qryPerDutyAmntFlag(tRiskCode);
		}
		return mHashInfo.get(tDutyCode).toString();
	}

	private boolean checkMult(String tRiskCode, LCDutySchema tLCDutySchema) {
		String tAmntFlag = "";
		tAmntFlag = this
				.getDutyAmntFlag(tRiskCode, tLCDutySchema.getDutyCode());
		if ("2".equals(tAmntFlag)) {
			if (tLCDutySchema.getMult() <= 0) {
				CError.buildErr(this, "险种" + tRiskCode + "是按份数卖的，请录入份数");
				return false;
			}
			if (tLCDutySchema.getAmnt() > 0) {
				CError.buildErr(this, "险种" + tRiskCode + "是按份数卖的，不需要录入保额！");
				return false;
			}
		}
		return true;
	}
}
