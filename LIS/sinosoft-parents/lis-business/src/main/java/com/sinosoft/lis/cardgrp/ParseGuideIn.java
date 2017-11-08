/*
 * @(#)ParseGuideIn.java	2004-12-13
 *
 * Copyright 2004 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.LCAscriptionRuleFactoryDB;
import com.sinosoft.lis.db.LCContPlanRiskDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpImportLogDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LDSysVarDB;
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
import com.sinosoft.lis.schema.LCInsuredRelatedSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LLAccountSchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.tb.ContPlanQryBL;
import com.sinosoft.lis.tb.ProposalBL;
import com.sinosoft.lis.vbl.LCBnfBLSet;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vbl.LCPremBLSet;
import com.sinosoft.lis.vdb.LCGrpImportLogDBSet;
import com.sinosoft.lis.vschema.LCAscriptionRuleFactorySet;
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
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
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

	// private LCPolSchema mLCPolSchema = new LCPolSchema();
	// private LCDutySchema mLCDutySchema = new LCDutySchema();
	// private LCDutySet mLCDutySet = new LCDutySet();
	// private LCInsuredSet mLCInsuredSet = new LCInsuredSet();
	// private LCBnfSet mLCBnfSet = new LCBnfSet();
	// private LCPremToAccSet mLCPremToAccSet = new LCPremToAccSet();
	private TransferData mTransferData = new TransferData();
	private LCGrpImportLogSet mLCGrpImportLogSet = new LCGrpImportLogSet();

	// private LCPremSet mLCPremSet = new LCPremSet();
	// private HashMap mContNoMap = new HashMap(); //合同号Id对应合同号

	// private LC

	// private LCGrpAppntSchema mLCGrpAppntSchema= new LCGrpAppntSchema();
	// 保持对象
	/*
	 * private LCDutySet mHoldLCDutySet = new LCDutySet(); private LCInsuredSet
	 * mHoldLCInsuredSet = new LCInsuredSet(); private LCBnfSet mHoldLCBnfSet =
	 * new LCBnfSet(); private LCPremToAccSet mHoldLCPremToAccSet = new
	 * LCPremToAccSet(); private LCPremSet mHoldLCPremSet = new LCPremSet();
	 */
	// private String mDutyFlag = "0";
	// private InputStream DataIn;
	private String FileName;
	private String XmlFileName;
	private String mPreFilePath;

	// private String BatchNo;
	// private String GrpPolNo;
	// private String ID;
	// private String InsuredName;
	private String mGrpContNo;
	private int tflag = 0; // 记录是否是000006却没有填入公司日，没有则为1

	//
	// private String MainPolNo = "";
	// private String InsuredNo = "";
	// private String MainRiskCode = "";
	// private String PrtNo = "";
	// private String RiskCode = "";

	// 节点是否存在标志
	// private boolean HaveNodeFlag = true;

	// 数据Xml解析节点描述
	private String FilePath = "E:/temp/";
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

	private GrpPolImpInfo m_GrpPolImpInfo = new GrpPolImpInfo();

	private String[] m_strDataFiles = null;
	private String[][] m_IDNoSet = null;
	private boolean mRelation=false;
	private String mMainSequenceNo = "1";
	private String mRelationToRisk = "";
	private String mRiskCode[]=null;

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
		this.getInputData();
		if (!this.checkData())
			return false;
		logger.debug("开始时间:" + PubFun.getCurrentTime());
		
	
		try {
			if (!this.parseVts()) {
				return false;
			}

			for (int nIndex = 0; nIndex < m_strDataFiles.length; nIndex++) {
				XmlFileName = m_strDataFiles[nIndex];
				boolean res = this.ParseXml();
				// if (!res)break;
			}

			 mLCGrpImportLogSet = m_GrpPolImpInfo.getErrors();
			 if(mLCGrpImportLogSet.size()>0)
			 {
				 this.mErrors.clearErrors();
				 for(int i=1;i<=mLCGrpImportLogSet.size();i++)
				 {
					 CError.buildErr(this, mLCGrpImportLogSet.get(i).getErrorInfo());
					 
				 }
				 return false;
			 }else{
				 //估计这段是多余 不过至少现在比较管用
				 if(m_GrpPolImpInfo.mErrors.getErrorCount()>0){
					 for(int i=0;i<m_GrpPolImpInfo.mErrors.getErrorCount();i++){
						 CError.buildErr(this, m_GrpPolImpInfo.mErrors.getError(i).errorMessage);
					 }
					 return false;
				 }else{
					 if(logErrors.getErrorCount()>0){
						 for(int j=0;j<logErrors.getErrorCount();j++){
							 CError.buildErr(this, logErrors.getError(j).errorMessage);
						 }
						 return false;
					 }
				 }
			 }
//			
//			 //错误日志
//			 if (mLCGrpImportLogSet.size() > 0)
//			 {
//			 String tReturn = mLCGrpImportLogSet.encode();
//			 tReturn = "0|" + mLCGrpImportLogSet.size() + "^" + tReturn;
//			 mResult.clear();
//			 CError.buildErr(this, tReturn);
//			 return false;
//			 //mResult.addElement(tReturn);
//			 }
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "导入文件格式有误!";
			this.logErrors.addOneError(tError);
			// return false;
		}

//	    if(this.mErrors==null)
//		  this.mErrors = this.logErrors;
		logger.debug("结束时间:" + PubFun.getCurrentTime());
		if (this.mErrors.getErrorCount() > 0)
			return false;
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
		tLDSysVarDB.setSysVar("XmlPath");
		if (!tLDSysVarDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "缺少文件导入路径!";
			this.mErrors.addOneError(tError);
			return false;
		} else
			FilePath = tLDSysVarDB.getSysVarValue();

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
			} else
				FilePath = tLDSysVarDB.getSysVarValue();

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
		if (!this.initImportFile())
			return false;
		if (!this.checkImportConfig())
			return false;

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
		logger.debug("-----开始读取Xml文件");
		if (!checkXmlFileName())
			return false;
		logger.debug("Xml文件存在");
		File tFile = new File(XmlFileName);
		XMLPathTool tXPT = new XMLPathTool(XmlFileName);
		this.mErrors.clearErrors();
		String tBatchNo = "";
		String tPrtNo = "";
		try {
			// 批次号
			tBatchNo = tXPT.parseX(ParseRootPath).getFirstChild()
					.getNodeValue();
			// 印刷号
			tPrtNo = tXPT.parseX(PATH_GRPCONTNO).getLastChild().getNodeValue();
			mBatchNo = tBatchNo;
			mPrtNo = tPrtNo;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		LCGrpContDB grpContDB = new LCGrpContDB();
		grpContDB.setPrtNo(tPrtNo);
		grpContDB.setContType("2");
		LCGrpContSet tLCGrpContSet = grpContDB.query();
		if (tLCGrpContSet == null || tLCGrpContSet.size() <= 0) {
			CError.buildErr(this, "没有找到团体合同[" + tPrtNo + "]的信息");
			m_GrpPolImpInfo.logError(tBatchNo, grpContDB.getGrpContNo(), "",
					"", "", "", "", null, this.mErrors, mGlobalInput);
			return false;
		}
		LCGrpContSchema tLCGrpContSchema = tLCGrpContSet.get(1);
		mGrpContNo = tLCGrpContSchema.getGrpContNo();
		
		String tSQL = "select * from lcgrpimportlog where grpcontno='"+mGrpContNo+"' ";
		
		LCGrpImportLogDB tLCGrpImportLogDB = new LCGrpImportLogDB();
		LCGrpImportLogSet tLCGrpImportLogSet = new LCGrpImportLogSet();
		LCGrpImportLogDBSet tLCGrpImportLogDBSet = new LCGrpImportLogDBSet();
		tLCGrpImportLogSet = tLCGrpImportLogDB.executeQuery(tSQL);
		if(tLCGrpImportLogSet.size()>0)
		{
			tLCGrpImportLogDBSet.set(tLCGrpImportLogSet);
			if(!tLCGrpImportLogDBSet.delete())
			{
				CError.buildErr(this,"删除日志表失败!");
				return false;
			}
		}
		if (mTransferData.getValueByName("BQFlag") != null
				&& mTransferData.getValueByName("BQFlag") != ""
				&& "2".equals(StrTool.cTrim(mTransferData.getValueByName(
						"BQFlag").toString())))
		// &&
		// "NI".equals(StrTool.cTrim(mTransferData.getValueByName("EdorType").
		// toString())))

		{
			// 查询所有被保人的身份证号
			// String strSQL =
			// "Select IDNo From LCInsured Where IDType='0' and GrpContNo='" +
			// mGrpContNo + "'";
			// logger.debug(strSQL);
			// ExeSQL tExeSQL = new ExeSQL();
			// SSRS tssrs = tExeSQL.execSQL(strSQL);
			// //将所有的身份证号放入m_IDNoSet二维数组中
			// m_IDNoSet = tssrs.getAllData();

		} else {

			if ("1".equals(StrTool.cTrim(tLCGrpContSchema.getAppFlag()))) {
				CError.buildErr(this, "团体合同[" + mGrpContNo + "]已经签单，不再接受投保");
				m_GrpPolImpInfo.logError(tBatchNo, mGrpContNo, "", "", "", "",
						"", null, this.mErrors, mGlobalInput);
				this.mErrors.clearErrors();

				return false;
			}
		}
		// 团单合同的录单完成日期不为空则表示已经录入完成
		// 团单合同的录单完成日期不为空则表示已经录入完成
		/**
		 * -----------------------------jixf del 增人---------------- if
		 * (!"".equals(StrTool.cTrim(tLCGrpContSchema.getInputDate()))) {
		 * CError.buildErr(this, "团体合同[" + mGrpContNo + "]已录入完成，不再接受投保");
		 * m_GrpPolImpInfo.logError(tBatchNo, grpContDB.getGrpContNo(), "", "",
		 * "", "", "", null, this.mErrors, mGlobalInput);
		 * this.mErrors.clearErrors();
		 * 
		 * return false; }
		 */
		/*------------------------------jixf end ----------------------------*/
		/** **************************** */
		// 得到保单的传入信息
		NodeList nodeList = tXPT.parseN(ParsePath);
		int nNodeCount = 0;

		if (nodeList != null) {
			nNodeCount = nodeList.getLength();
		}

		// 循环中，每一次都调用一次承包后台程序，首先挑出一条保单纪录，再通过ID号找到保费项，责任，给付等，组合成Set集合
		// 此时新单是没有投保单号的，因此在保单，保费项，责任等纪录中投保单号为空,后台自动生成投保单号
		// 最后将数据放入VData中，传给GrpPollmport模块。一次循环完毕
		GrpPolParser tGrpPolParser = new GrpPolParser();
		for (int i = 0; i < nNodeCount; i++) { // nNodeCount为node数量(合同数、人数)
			boolean tSucc = true;
			Node node = nodeList.item(i);

			try {
				node = transformNode(node);
			} catch (Exception ex) {
				ex.printStackTrace();
				node = null;
			}

			NodeList contNodeList = node.getChildNodes();
			// 此list包含了<CONTID>
			// <INSUREDTABLE>
			// <LCPOLTABLE >
			// <BNFTABLE >
			if (contNodeList.getLength() <= 0)
				continue; // 该节点没有值
			Node contNode = null;
			String nodeName = "";
			LCInsuredRelatedSet tLCInsuredRelatedSet = null;
			LCInsuredSet tLCInsuredSet = null;
			LCBnfSet tLCBnfSet = null;
			String contId = "";
			for (int j = 0; j < contNodeList.getLength(); j++) { // j<4

				contNode = contNodeList.item(j); // 合同项
				nodeName = contNode.getNodeName();
				if (nodeName.equals("#text"))
					continue;
				if (nodeName.equals("CONTID")) {
					contId = contNode.getFirstChild().getNodeValue();
					continue;
				}

				if (nodeName.equals("INSUREDTABLE")) {
					// 解析被保险人xml
					tLCInsuredSet = (LCInsuredSet) tGrpPolParser
							.getLCInsuredSet(contNode);
					//VData tVData =new VData();
					tLCInsuredRelatedSet = (LCInsuredRelatedSet) tGrpPolParser.getLCInsuredRelatedSet(contNode);
					//保存连带被保人的连带险种
					if(tLCInsuredRelatedSet.size()==1){
						mRelationToRisk =tLCInsuredRelatedSet.get(1).getModifyTime();
					}
					if (tLCInsuredSet == null || tLCInsuredSet.size() <= 0) {
						this.mErrors.copyAllErrors(tGrpPolParser.mErrors);
						m_GrpPolImpInfo.logError(tBatchNo, mGrpContNo, contId,
								"", "", "", "", null, this.mErrors,
								mGlobalInput);
						this.mErrors.clearErrors();
						continue;
					}

					continue;
				}
				if (nodeName.equals("LCPOLTABLE")) {
					// 保单信息
					VData tVData = null;

					// 解析一个保单节点
					tVData = tGrpPolParser.parseLCPolNode(contNode); // 获得改险种相关的责任及保费项信息
					// if ( tVData==null || tVData.size()<=0)
					// {
					// this.mErrors.copyAllErrors(tGrpPolParser.mErrors);
					// return false;
					// }

					// TransferData tTransferData =
					// (TransferData) tVData.getObjectByObjectName(
					// "TransferData",
					// 0);
					// String ContId = (String) tTransferData.getValueByName(
					// "ContId");
					// 缓存险种保单信息
					m_GrpPolImpInfo.addContPolData(contId, tVData);
					logger.debug("ContPolData is "
							+ m_GrpPolImpInfo.getContPolData(contId));
					continue;
				}
				if (nodeName.equals("BNFTABLE")) {
					// 受益人解析
					tLCBnfSet = (LCBnfSet) tGrpPolParser.getLCBnfSet(contNode);
					continue;
				}
				// if (nodeName.equals("INSURED_RELA_TABLE"))
				// {
				// //连身被保人表
				// tLCInsuredRelatedSet = (
				// LCInsuredRelatedSet)
				// tGrpPolParser.getLCInsuredRelatedSet(contNode);
				// continue;
				// }
			}

			// 进行保全增加被保人身份证号的校验
			// if (m_IDNoSet != null) {
			// for (int m = 1; m <= tLCInsuredSet.size(); m++) {
			// LCInsuredSchema temp = tLCInsuredSet.get(m);
			// for (int j = 1; j < m_IDNoSet.length; j++) {
			// if (temp.getIDNo() != null &&
			// !temp.getIDNo().equals("") &&
			// temp.getIDNo().equals(m_IDNoSet[j][0])) {
			// CError.buildErr(this,
			// "被保险人身份证号" + temp.getIDNo() + "已经存在!");
			// m_GrpPolImpInfo.logError(tBatchNo,
			// grpContDB.getGrpContNo(), "", "", "", "",
			// "", null
			// , this.mErrors, mGlobalInput);
			// this.mErrors.clearErrors();
			// // @@错误处理
			// CError tError = new CError();
			// tError.moduleName = "ParseGuideIn";
			// tError.functionName = "ParseXml";
			// tError.errorMessage = "被保险人身份证号" + temp.getIDNo() +
			// "已经存在!";
			// this.logErrors.addOneError(tError);
			//
			// return false;
			// }
			// }
			// }
			// }

			if (tLCInsuredSet == null) {
				CError.buildErr(this, "被保险人节点解析失败,被保险人不能为空!请确认录入的被保人信息是否正确!");
				m_GrpPolImpInfo.logError(tBatchNo, mGrpContNo, "", "", "", "",
						"", null, this.mErrors, mGlobalInput);
				this.mErrors.clearErrors();
				return false;
			}
			// 有保险计划的入司日期校验

			// 增加出生日期校验
			String ttFlag = "0";
			for (int k = 1; k <= tLCInsuredSet.size(); k++) { // size==1
				if (tLCInsuredSet.get(1).getBirthday().compareTo("1900-01-01") == 0) {
					CError.buildErr(this, "生日期格式不正确!");

					m_GrpPolImpInfo.logError(tBatchNo, mGrpContNo, contId, "",
							"", "", "", null, this.mErrors, mGlobalInput);
					this.mErrors.clearErrors();
					ttFlag = "1";

				}
			}
			if (ttFlag.equals("1")) {
				continue;
			}
			if (!m_GrpPolImpInfo.init(tBatchNo, this.mGlobalInput,
					this.mTransferData, tLCInsuredSet, tLCBnfSet,
					tLCInsuredRelatedSet, tLCGrpContSchema)) {

				m_GrpPolImpInfo.logError(tBatchNo, mGrpContNo, "", "", "", "",
						"", null, this.mErrors, mGlobalInput);
				this.mErrors.clearErrors();
				return false;
			}

			// 按合同导入
			boolean bl = DiskContImport(contId);

			if (!bl) {
				logger.debug("导入失败");

			} else
				logger.debug("导入成功");
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
		if(deleteMainCustomer()==false){
        	logger.debug("没能成功删除连带被保人中住被保人信息！");
        }

		if (!tFile.delete()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "Xml文件删除失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// }
		if (this.mErrors.needDealError()) {
			logger.debug(this.mErrors.getErrorCount() + "error:"
					+ this.mErrors.getFirstError());
		}

		return true;
	}

	private boolean deleteMainCustomer(){
		MMap tmpMap = new MMap();
		boolean de ;
		String deleteSQL=" delete from lcinsuredrelated a where maincustomerno = customerno "
			            +"  and polno in  (select polno from lcpol"
			            +" where contno in (select contno	from lcgrpimportlog  where batchno = '"+this.mBatchNo+"'))";
		tmpMap.put(deleteSQL, "DELETE");
		de = batchSave(tmpMap);
		if(!de){
			CError.buildErr(this, "删除连带被保人表中的住被保人信息失败！");
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
		MMap map = (MMap) tNewVData.getObjectByObjectName("MMap", 0);
		if (map == null)
			map = new MMap();
		LCPolSchema tLCPolSchema = (LCPolSchema) tNewVData
				.getObjectByObjectName("LCPolSchema", 0);
		String strID = (String) ((TransferData) tNewVData
				.getObjectByObjectName("TransferData", 0)).getValueByName("ID");
		String PolKey = (String) ((TransferData) tNewVData
				.getObjectByObjectName("TransferData", 0))
				.getValueByName("PolKey");
		String contId = m_GrpPolImpInfo.getContKey(PolKey);
		ProposalBL tProposalBL = new ProposalBL();
		if (!tProposalBL.PrepareSubmitData(tNewVData, "INSERT||PROPOSAL")) {
			CError.buildErr(this, "合同["+contId+"]错误:");
			this.mErrors.copyAllErrors(tProposalBL.mErrors);
//			 m_GrpPolImpInfo.logError(strID, tLCPolSchema, false, this.mErrors,
//			 mGlobalInput);
			return null;
		} else {
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
//			String contId = m_GrpPolImpInfo.getContKey(PolKey);
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
			tLCGrpPolSchema.setPayEndDate(PubFun.getLaterDate(tLCGrpPolSchema
					.getPayEndDate(), rPolSchema.getPayEndDate()));
			tLCGrpPolSchema.setPaytoDate(PubFun.getLaterDate(tLCGrpPolSchema
					.getPaytoDate(), rPolSchema.getPaytoDate()));
			tLCGrpPolSchema.setFirstPayDate(PubFun.getBeforeDate(
					tLCGrpPolSchema.getFirstPayDate(), rPolSchema
							.getFirstPayDate()));
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
				String s0 = " update lccont set peoples=(select count(distinct insuredno) from lcpol where '"
						+ u
						+ "'='"
						+ u
						+ "' and contno='"
						+ tContSchema.getContNo()
						+ "')"
						+ " where contno='"
						+ tContSchema.getContNo() + "'" + " and PolType = '0' ";
				map.put(s0, "UPDATE");
			}

			String s1 = "update lcgrpcont  set peoples2=(select sum(peoples) from lccont where '"
					+ u
					+ "'='"
					+ u
					+ "' and GrpContNo='"
					+ tContSchema.getGrpContNo()
					+ "')"
					+ " where grpcontno='"
					+ rGrpCont.getGrpContNo() + "'";
			String s2 = "update lcgrppol set peoples2=(select sum(insuredpeoples) from lcpol where '"
					+ u
					+ "'='"
					+ u
					+ "' and grppolno='"
					+ rGrpPol.getGrpPolNo()
					+ "')"
					+ " where grppolno='"
					+ rGrpPol.getGrpPolNo() + "'";

			// String s0 =
			// "update lccont set peoples=(select count(distinct insuredno) from lcpol where
			// "
			// + u + "=" + u + " and contno='"
			// + tContSchema.getContNo() + "')";
			// 更新人数
			// String s1 =
			// "update lcgrpcont set peoples2=(select count(*) from lcinsured where "
			// + u + "=" + u + " and GrpContNo='" +
			// tContSchema.getGrpContNo() + "')"
			// + " where grpcontno='" + rGrpCont.getGrpContNo() + "'";
			// String s2 = "update lcgrppol set peoples2=(select count(distinct insuredno)
			// from lcpol where "
			// + u + "=" + u + " and grppolno='"
			// + rGrpPol.getGrpPolNo()
			// + "' and riskcode='" + tLCPolSchema.getRiskCode() +
			// "')"
			// + " where grppolno='" + rGrpPol.getGrpPolNo() + "'";
			// map.put(s0, "UPDATE");
			map.put(s1, "UPDATE");
			map.put(s2, "UPDATE");
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
		logger.debug("tInsuredSet is " + tInsuredSet.encode());
		MMap subMap = null; // 提交结果集缓存
		boolean state = true; // 导入状态，
		boolean saveState = true;
		boolean somedata = true;//是否有可保存的数据
		String logRiskCode = "";
		String logContPlanCode = "";
		String logPolId = "";
		String insuredIndex = "";

		// 取出缓存的合同表，并对序号排序，按合同序号从小到大导入
		// HashMap contMap = m_GrpPonodeNamelImpInfo.getContCache();

		// Set keySet = contMap.keySet() ;
		// int[] ContKey = this.getOrderKeySetInt(keySet);

		// for (int uu = 0; uu < ContKey.length; uu++)

		// {
		// Object key = contMapp.getKeyByOrder(String.valueOf(g + 1));
		subMap = null;
		// String contIndex = String.valueOf( ContKey[uu] ) ;
		mContNo = "";
		LCGrpImportLogSchema logSchema = m_GrpPolImpInfo.getLogInfo(mBatchNo,
				contIndex);
		if (logSchema != null && "0".equals(logSchema.getErrorState())) {
			// 该批次号该合同已经导入成功
			// continue;
			return true;
		}

		state = true;
		// 先查找有保障计划的导入
		for (int i = 1; i <= tInsuredSet.size(); i++) {
			logger.debug("先查找有保障计划的导入" + i); // 初始化，避免重复使用

			logRiskCode = "";
			logContPlanCode = "";
			logPolId = "";
			if (state == false)
				break;
			insuredSchema = tInsuredSet.get(i);
			insuredSchema.setSequenceNo(String.valueOf(i));
			// 不是同一个合同的，跳出
			if (!insuredSchema.getContNo().equals(contIndex))
				continue;
			// 不是保障计划的，跳出
			if ("".equals(StrTool.cTrim(insuredSchema.getContPlanCode()))) {
				continue;
			}

			logger.debug("是保障计划");

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

				state = false;
				break; // bre家ak for insured
			}
			// 归属校验

			logger.debug("getLCGrpContSchema" + mPrtNo);
			String PlanAscripsql = null;
			PlanAscripsql = "select d.CalFactorValue  from LMRiskDutyFactor a, LMRisk b, LMDuty c, LCContPlanDutyParam d where  a.calfactor='AscriptionRuleCode' and a.RiskCode = b.RiskCode and a.DutyCode = c.DutyCode and a.RiskCode = d.RiskCode and a.DutyCode = d.DutyCode and a.CalFactor = d.CalFactor and b.RiskVer = d.RiskVersion and ContPlanCode = '"
					+ insuredSchema.getContPlanCode()
					+ "'and GrpContNO = '"
					+ mPrtNo
					+ "' order by a.RiskCode,d.MainRiskCode,a.DutyCode,a.factororder";
			ExeSQL PlanAscripExeSQL = new ExeSQL();
			SSRS PlanAscripssrs = new SSRS();
			PlanAscripssrs = PlanAscripExeSQL.execSQL(PlanAscripsql);
			if (PlanAscripssrs.getMaxRow() >= 1) {
				if (!PlanAscripssrs.GetText(1, 1).equals("")) {
					// 有归属
					logger.debug("有归属");
					String Ascripsql1 = null;
					Ascripsql1 = "select * from LCAscriptionRuleFactory where grpcontno='"
							+ mPrtNo
							+ "' and ASCRIPTIONRULECODE='"
							+ PlanAscripssrs.GetText(1, 1) + "'";
					LCAscriptionRuleFactorySet tLCAscriptionRuleFactorySet = new LCAscriptionRuleFactorySet();
					LCAscriptionRuleFactoryDB tLCAscriptionRuleFactoryDB = new LCAscriptionRuleFactoryDB();
					tLCAscriptionRuleFactoryDB.executeQuery(Ascripsql1);
					tLCAscriptionRuleFactorySet.set(tLCAscriptionRuleFactoryDB
							.query());
					for (int m = 1; m <= tLCAscriptionRuleFactorySet.size(); m++) {
						if (tLCAscriptionRuleFactorySet.get(m).getFactoryType()
								.equals("000006")
								&& (insuredSchema.getJoinCompanyDate() == null || insuredSchema
										.getJoinCompanyDate().equals(""))) {
							state = false;
							break; // break for tLCAscriptionRuleFactorySet
						}

					}
				}
			}

			if (state == false) {
				CError.buildErr(this, "被保险人" + contIndex + "需要填写入司日期");
				break;
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
				if (contPlanRiskSchema.getRiskCode().equals(
						contPlanRiskSchema.getMainRiskCode())) {
					mainPlanRiskSet.add(contPlanRiskSchema);
				} else {
					subPlanRiskSet.add(contPlanRiskSchema);
				}

			}
			mainPlanRiskSet.add(subPlanRiskSet);
			// 根据险种计划生成险种保单相关信息
			for (int j = 1; j <= mainPlanRiskSet.size(); j++) {
				logRiskCode = mainPlanRiskSet.get(j).getRiskCode();
				// 生成保单，责任等信息
				VData tData = prepareContPlanData(contIndex, insuredIndex,
						insuredSchema, mainPlanRiskSet.get(j));

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
					if (subMap == null)
						subMap = oneRisk;
					else
						subMap.add(oneRisk);
				}
			}

		}
		// 计划导入准备期间发生错误
		if (state == false) {
			logger.debug("计划导入准备期间发生错误###！");
			logError(contIndex, insuredIndex, logPolId, logContPlanCode,
					logRiskCode, insuredSchema);
			logger.debug("写入错误日志###！");
			saveState = false;

			return false;
		} else {
			logger.debug("无计划导入....");
			// 无计划导入
			// 导入险种保单，先根据合同ID查找到所有相同合同id下的所有保单数据，再一张一张保单信息导入
			VData tContPolData = (VData) m_GrpPolImpInfo
					.getContPolData(contIndex);
			if (tContPolData != null) {

				LCPolSchema tLCPolSchema = null;
				VData tPolData = null;
				for (int u = 0; u < tContPolData.size(); u++) {
					tPolData = (VData) tContPolData.get(u);
					if (state == false)
						break;
					for (int i = 0; i < tPolData.size(); i++) {

						// 初始化，避免重复使用
						logRiskCode = "";
						logContPlanCode = "";
						logPolId = "";

						if (state == false)
							break;

						VData onePolData = (VData) tPolData.get(i);
						if (onePolData == null)
							continue;
						// 备份原ID号
						tLCPolSchema = (LCPolSchema) onePolData
								.getObjectByObjectName("LCPolSchema", 0);
						logRiskCode = tLCPolSchema.getRiskCode();
						for (int k = 0; k < tPolData.size(); k++) {
							LCPolSchema ttLCPolSchema = new LCPolSchema();
							VData tonePolData = (VData) tPolData.get(k);
							if (tonePolData == null)
								continue;
							if (k == i)
								continue;
							ttLCPolSchema = (LCPolSchema) tonePolData
									.getObjectByObjectName("LCPolSchema", 0);
							if (tLCPolSchema.getRiskCode().equals(
									ttLCPolSchema.getRiskCode())) {
								logger.debug("险种保单有相同的数据！");
								CError tError = new CError();
								tError.moduleName = "ContBL";
								tError.functionName = "submitData";
								tError.errorMessage = "合同ID号为" + contIndex
										+ "险种保单有相同的数据!";
								this.mErrors.addOneError(tError);
								state = false;
							}
						}
						insuredIndex = tLCPolSchema.getInsuredNo();
						contIndex = tLCPolSchema.getContNo();

						logger.debug("AscriptionRuleCode is "
								+ tLCPolSchema.getAscriptionRuleCode());

						VData tprepareData = this
								.prepareProposalBLData(onePolData);
						if (tprepareData == null) {
							logger.debug("准备数据的时候出错！");
							state = false;
						} else {
							MMap tMap = new MMap();
							if(mRelation){
								//是连带被保人 则只提交lcinsuredrelated和lcinsured两个表
								 tMap =this.lcinsuredRealtedDataSubmit(tprepareData);
							}else{
								 tMap = this
								.submiDatattoProposalBL(tprepareData);
							}
							if (tMap == null) {

								state = false;
							}
							if (subMap == null)
								subMap = tMap;
							else
								subMap.add(tMap);
						}
					}
				}
			 if(tPolData.size()<1&&"".equals(StrTool.cTrim(insuredSchema.getContPlanCode()))){
				 //当没有录入保障计划和险种信息时，没有提交数据， 应提示保存失败 add at 2008-12-18
				 logger.debug("没有险种信息!");
				 somedata = false;
				 CError.buildErr(this, "没有录入险种信息!");
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
			//提交所有数据
			bs = batchSave(subMap);
		}

		if (!bs || !state ||!somedata) {
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

	private MMap lcinsuredRealtedDataSubmit(VData tRelationData){
		MMap tMap = new MMap();
		String tNowDate = PubFun.getCurrentDate();
		String tNowTime = PubFun.getCurrentTime();
		Reflections tReflections = new Reflections();
		LDPersonSet tLDPersonSet= new LDPersonSet();
		LDPersonDB tLDPersonDB =new LDPersonDB();
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		LCInsuredRelatedSet tLCInsuredRelatedSet = (LCInsuredRelatedSet) tRelationData
				.getObjectByObjectName("LCInsuredRelatedSet", 0);
		LCInsuredRelatedSchema tLCInsuredRelatedSchema = new LCInsuredRelatedSchema();
//		if(tLCInsuredRelatedSet.size()>0)
//		    tLCInsuredRelatedSchema = tLCInsuredRelatedSet.get(1);
		LCInsuredSchema tLCInsuredSchema = (LCInsuredSchema) tRelationData
					.getObjectByObjectName("LCInsuredSchema", 0);
		LDPersonSchema tLDPersonSchema = new LDPersonSchema();
		tReflections.transFields(tLDPersonSchema, tLCInsuredSchema);
		tLDPersonSchema.setOperator(mGlobalInput.Operator);
		tLDPersonSchema.setMakeDate(tNowDate);
		tLDPersonSchema.setMakeTime(tNowTime);
		tLDPersonSchema.setModifyDate(tNowDate);
		tLDPersonSchema.setCustomerNo(tLCInsuredSchema.getInsuredNo());
		tLDPersonSet.add(tLDPersonSchema);
		tLDPersonDB.setCustomerNo(tLCInsuredSchema.getInsuredNo());
		if(!tLDPersonDB.getInfo()){
			tMap.put(tLDPersonSet, "INSERT");
		}
		//连带被保人的合同号应该和其主被保人合同号相同
		String tMainContNo = queryLCInsuRelaNo(mBatchNo,"ContNo");
		String tMainPolNo = queryLCInsuRelaNo(mBatchNo,"PolNo");
		//tLCInsuredRelatedSchema.setPolNo(tMainPolNo);
//		tLCInsuredRelatedSet.clear();
//		tLCInsuredRelatedSet.add(tLCInsuredRelatedSchema);
		tLCInsuredSchema.setContNo(tMainContNo);
		tLCInsuredSet.add(tLCInsuredSchema);
		tMap.put(tLCInsuredRelatedSet, "INSERT");
		tMap.put(tLCInsuredSet, "INSERT");
		return tMap;
	}
	/*
	 * 查询主被保人的合同号
	 * */
	private String queryLCInsuRelaNo(String tBatchNo,String tFlag){
    	String No = "";
    	String tStrSQL = "";
    	String tPrtNo = tBatchNo.substring(0, 14);
    	ExeSQL tExeSQL = new ExeSQL();
    	if(tFlag.equals("ContNo")){
    	tStrSQL =  "select contno from lcgrpimportlog where batchno = '"+tBatchNo+"'  and contno in (select contno" +
    	" from lcpol where polno in (select polno from lcinsuredrelated  where maincustomerno = customerno" +
    	" and sequenceno='"+mMainSequenceNo+"'))";
    	}else if (tFlag.equals("PolNo")){
    		tStrSQL = "select polno from lcpol where contno = "+
    		" (select contno from lcgrpimportlog where batchno = '"+tBatchNo+"'  and contno in (select contno" +
    		" from lcpol where polno in (select polno from lcinsuredrelated  where maincustomerno = customerno" +
    		" and sequenceno='"+mMainSequenceNo+"'))) and riskcode='"+mRelationToRisk+"'";
    	}
    	 No = tExeSQL.getOneValue(tStrSQL);
    	//    	LCInsuredRelatedDB tLCInsuredRelatedDB = new LCInsuredRelatedDB();
//    	LCInsuredRelatedSet tLCInsuredRelatedSet = tLCInsuredRelatedDB.executeQuery(tStrSQL);
//    	if(tLCInsuredRelatedSet!=null && tLCInsuredRelatedSet.size()>0){
//    		ContNo = tLCInsuredRelatedSet.get(1)
//    	}
    	logger.debug("-----> GrpPolImpInfo-729-主被保险人的合同号为:"+No);
    	return No;
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
			LCContPlanRiskSchema tLCContPlanRiskSchema) {
		LLAccountSchema tLLAccountSchema = new LLAccountSchema();
		VData tNewVData = new VData();
		MMap tmpMap = new MMap();
		String strRiskCode = tLCContPlanRiskSchema.getRiskCode();
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

		boolean contFlag = false;
		LCContSchema contSchema = new LCContSchema();
		contSchema = m_GrpPolImpInfo.findLCContfromCache(contIndex);
		if (contSchema != null
				&& !"".equals(StrTool.cTrim(contSchema.getContNo()))) { // 合同已经创建过
			contFlag = true;
		}
		logger.debug("-contFlag-" + contFlag);
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
		tLLAccountSchema = (LLAccountSchema) tContData.getObjectByObjectName(
				"LLAccountSchema", 0);
		tNewVData.add(insuredSchema);
		tNewVData.add(tLLAccountSchema); // added by wanglei
		contSchema = (LCContSchema) tContData.getObjectByObjectName(
				"LCContSchema", 0);
		logger.debug("====contFlag==========" + contFlag);
		if (!contFlag) {
			// 合同第一次创建
			// 设置主被保险人信息
			contSchema.setInsuredBirthday(insuredSchema.getBirthday());
			contSchema.setInsuredNo(insuredSchema.getInsuredNo());
			contSchema.setInsuredName(insuredSchema.getName());
			if (insuredSchema.getIDType().equals("0")) {
				contSchema
						.setInsuredIDNo(insuredSchema.getIDNo().toUpperCase());
			} else {
				contSchema.setInsuredIDNo(insuredSchema.getIDNo());
			}

			contSchema.setInsuredIDType(insuredSchema.getIDType());
			contSchema.setInsuredSex(insuredSchema.getSex());
		}

		tNewVData.add(contSchema);

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
		tLCPolSchema.setMainPolNo(mainPolKey);
		tLCPolSchema.setInsuredNo(insuredIndex);
		// 填充所有保单信息
		VData polData = m_GrpPolImpInfo.formatLCPol(tLCPolSchema, PolKey);
		if (polData == null) {
			CError.buildErr(this, "出错了:", m_GrpPolImpInfo.mErrors);
			return null;
		}
		MMap polRelaMap = (MMap) polData.getObjectByObjectName("MMap", 0);
		tLCPolSchema = (LCPolSchema) polData.getObjectByObjectName(
				"LCPolSchema", 0);
		tLCPolSchema.setInsuredNo(insuredSchema.getInsuredNo());
		if (polRelaMap != null && polRelaMap.keySet().size() > 0)
			tmpMap.add(polRelaMap);

		// 责任信息查询和生成

		ContPlanQryBL contPlanQryBL = new ContPlanQryBL();
		VData tVData = new VData();
		tVData.add(tLCContPlanRiskSchema);
		boolean b = contPlanQryBL.submitData(tVData, "");
		if (!b || contPlanQryBL.mErrors.needDealError()) {
			CError.buildErr(this, "查询计划要约出错:", contPlanQryBL.mErrors);
			return null;
		}
		tVData = contPlanQryBL.getResult();
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
		/*
		 * if (tDutySet!=null&&tDutySet.size()>0){ for (int a=1;a<=tDutySet.size();a++){
		 * String tAscription=tDutySet.get(a).getAscriptionRuleCode();
		 * logger.debug("tAscription is "+tAscription); String
		 * mSql="select distinct FACTORYTYPE from lcascriptionrulefactory where
		 * GrpContNo='"+mGrpContNo+"' and ASCRIPTIONRULECODE='"+tAscription+"'";
		 * ExeSQL exesql = new ExeSQL(); SSRS ssrs = new SSRS(); ssrs =
		 * exesql.execSQL(mSql); int size = ssrs.getMaxRow(); for (int i = 1; i <=
		 * size; i++) { String factorytype = ssrs.GetText(i, 1);
		 * if(factorytype.equals("000006")){ logger.debug("yes ,there is
		 * 000006 "); if
		 * (insuredSchema.getJoinCompanyDate()==null||insuredSchema.getJoinCompanyDate().equals("")){
		 * logger.debug("no joindate , go to next guy");
		 * CError.buildErr(this, strRiskCode + "没有填写入公司日期"); return null; } } } } }
		 */
		tNewVData.add(tDutySet);
		// 保费
		LCPremSet tPremSet = new LCPremSet();
		tNewVData.add(tPremSet);

		// 连身被保险人信息
		// 处理连身被保险人
		// String[] relaIns = null;
		// relaIns = m_GrpPolImpInfo.getInsuredRela(contIndex, insuredIndex,
		// strRiskCode);
		// if (relaIns != null)
		// {
		// LCInsuredRelatedSet tRelaInsSet = m_GrpPolImpInfo.
		// getInsuredRelaData(
		// tLCPolSchema.getInsuredNo(), relaIns);
		// if (tRelaInsSet == null)
		// {
		// //创建连带被保险人出错
		// mErrors.copyAllErrors(m_GrpPolImpInfo.mErrors);
		// return null;
		// }
		// tNewVData.add(tRelaInsSet);
		// }

		// 受益人信息
		LCBnfSet tLCBnfSet = new LCBnfSet();
		// 主被保险人受益人
		LCBnfSet lLCBnfSet = m_GrpPolImpInfo.getBnfSet(PolKey);
		if (lLCBnfSet != null && lLCBnfSet.size() > 0)
			tLCBnfSet.add(lLCBnfSet);
		// 连带被保险人受益人
		// if (relaIns != null)
		// {
		// for (int i = 0; i < relaIns.length; i++)
		// {
		// String tmpPolKey = m_GrpPolImpInfo.getPolKey(contIndex,
		// relaIns[i],
		// strRiskCode);
		// LCBnfSet tmpLCBnfSet = m_GrpPolImpInfo.getBnfSet(tmpPolKey);
		// if (tmpLCBnfSet != null && tmpLCBnfSet.size() > 0) tLCBnfSet.
		// add(
		// tmpLCBnfSet);
		// }
		// }
		if (tLCBnfSet != null && tLCBnfSet.size() > 0) {
			VData tr = m_GrpPolImpInfo.perareBnf(tLCBnfSet, insuredSchema,
					tLCGrpPolSchema, insuredIndex);

			MMap tmap = (MMap) tr.getObjectByObjectName("MMap", 0);
			if (tmap != null)
				tmpMap.add(tmap);
			tLCBnfSet = (LCBnfSet) tr.getObjectByObjectName("LCBnfSet", 0);
			tNewVData.add(tLCBnfSet);

		}

		// 加入对应险种的集体投保单信息,险种承保描述信息
		LMRiskAppSchema tLMRiskAppSchema =new LMRiskAppSchema();
		LMRiskSchema tLMRiskSchema=new LMRiskSchema ();
		String RiskCode1[]=strRiskCode.split(";");
		String RiskCode2[]=strRiskCode.split("；");
		      mRiskCode=RiskCode2;//模板中可能用";"或"；" 所以要判断
		if(mRiskCode!=null){
			for(int i=0;i<mRiskCode.length;i++){
				tLMRiskAppSchema = m_GrpPolImpInfo
				.findLMRiskAppSchema(mRiskCode[i]);
				if (tLMRiskAppSchema == null) {
					buildError("prepareContPlanData", strRiskCode + "险种对应的险种承保描述没有找到!");
					return null;
				}
				tLMRiskSchema = m_GrpPolImpInfo
				.findLMRiskSchema(mRiskCode[i]);
				if (tLMRiskSchema == null) {
					buildError("prepareContPlanData", strRiskCode + "险种对应的险种承保描述没有找到!");
					return null;
				}
			}
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
		tTransferData.setNameAndValue("ID", PolKey);
		tTransferData.setNameAndValue("PolKey", PolKey);
		// jixf 20051102
		if (mTransferData.getValueByName("BQFlag") != null
				&& mTransferData.getValueByName("BQFlag") != ""
				&& "2".equals(StrTool.cTrim(mTransferData.getValueByName(
						"BQFlag").toString()))) {
			// &&
			// "NI".equals(StrTool.cTrim(mTransferData.getValueByName("EdorType").
			// toString()))) {
			// 保全保存标记
			tTransferData.setNameAndValue("SavePolType", "2");
			tTransferData.setNameAndValue("EdorType", mTransferData
					.getValueByName("EdorType"));
			tTransferData.setNameAndValue("EdorTypeCal", mTransferData
					.getValueByName("EdorTypeCal"));

			logger.debug("-EdorType-"
					+ mTransferData.getValueByName("EdorType"));
			tTransferData.setNameAndValue("EdorValiDate", mTransferData
					.getValueByName("EdorValiDate"));

			logger.debug(tTransferData.getValueByName("SavePolType"));
		}
		// jixf end

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
		//tongmeng 2009-03-23 modify
		//如果指定生效日,使用指定的生效日
		if(contSchema.getCValiDate()!=null&&!contSchema.getCValiDate().equals(""))
		{
			tLCPolSchema.setCValiDate(contSchema.getCValiDate());
			tLCPolSchema.setSpecifyValiDate("Y");
		}
		else
		{
			// 否则一律用集体单的生效日期
			tLCPolSchema.setCValiDate(tLCGrpPolSchema.getCValiDate());
			// 如果是保全增人，磁盘倒入指定生效日期，且生效日期字段无值,那么就用 页面传入值
			// jixf 20051102
			logger.debug("---------------------");
			if (mTransferData.getValueByName("BQFlag") != null
					&& mTransferData.getValueByName("BQFlag") != "") {
				String EdorValiDate = (String) mTransferData
						.getValueByName("EdorValiDate");
				logger.debug("----------Edorvalidate-----------"
						+ EdorValiDate);
				tLCPolSchema.setCValiDate(EdorValiDate);
			}
		}
		/*	
		if ("Y".equals(tLCPolSchema.getSpecifyValiDate())
				&& tLCPolSchema.getCValiDate() != null) {
			// 如果磁盘倒入指定生效日期，且生效日期字段有值,那么就用 生效日期字段的值
		} else {
			// 否则一律用集体单的生效日期
			tLCPolSchema.setCValiDate(tLCGrpPolSchema.getCValiDate());
			// 如果是保全增人，磁盘倒入指定生效日期，且生效日期字段无值,那么就用 页面传入值
			// jixf 20051102
			logger.debug("---------------------");
			if (mTransferData.getValueByName("BQFlag") != null
					&& mTransferData.getValueByName("BQFlag") != "") {
				String EdorValiDate = (String) mTransferData
						.getValueByName("EdorValiDate");
				logger.debug("----------Edorvalidate-----------"
						+ EdorValiDate);
				tLCPolSchema.setCValiDate(EdorValiDate);
			}

		}*/

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
							if (j < 0)
								j = 0;
						}
					} else {
						break;
					}
				} else
					break;
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
		// try
		// {
		// tPGI.parseVts();
		// }
		// catch(Exception e)
		// {
		// e.printStackTrace();
		// }
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("FileName", "37370000001_01.xls");
		tTransferData.setNameAndValue("FilePath", "E:");
		GlobalInput tG = new GlobalInput();
		tG.Operator = "GrpImport";
		tG.ManageCom = "86110000";

		tVData.add(tTransferData);
		tVData.add(tG);
		// tPGI.outputDocumentToFile("E:/","ProcedureXml1");
		tPGI.submitData(tVData, "");

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
		logger.debug("tTransferData is " + tTransferData.toString());
		logger.debug("tLCDutySet is " + tLCDutySet.encode());

		// 险种ID
		String strID = (String) tTransferData.getValueByName("ID");
		String tgetintv = (String) tTransferData.getValueByName("GetIntv");
		// 备份原ID号
		String strRiskCode = tLCPolSchema.getRiskCode();
		String InsuredId = tLCPolSchema.getInsuredNo();
		String contId = tLCPolSchema.getContNo();
		// 导入的时候使用appflag字段缓存连带被保险人ID信息
		String relaInsId = tLCPolSchema.getAppFlag();

		// 查询集体保单信息
		LCGrpPolSchema sLCGrpPolSchema =new LCGrpPolSchema();
		String RiskCode1[]=strRiskCode.split(";");
		String RiskCode2[]=strRiskCode.split("；");
		      mRiskCode=RiskCode2;//模板中可能用";"或"；" 所以要判断
		if(RiskCode1.length>RiskCode2.length)
			mRiskCode=RiskCode1;
		if(mRiskCode!=null){
			for(int i=0;i<mRiskCode.length;i++){
				sLCGrpPolSchema = m_GrpPolImpInfo
				.findLCGrpPolSchema(mRiskCode[i]);
				if (sLCGrpPolSchema == null) {
					buildError("prepareProposalBLData", strRiskCode + "险种对应的集体投保单没有找到!");
					return null;
				}
			}
		}
//		LCGrpPolSchema sLCGrpPolSchema = m_GrpPolImpInfo
//				.findLCGrpPolSchema(strRiskCode);
//		if (sLCGrpPolSchema == null) {
//			buildError("prepareProposalBLData", strRiskCode + "险种对应的集体投保单没有找到!");
//			return null;
//		}
		// 拷贝一份，避免修改本地数据
		LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
		tLCGrpPolSchema.setSchema(sLCGrpPolSchema);
		boolean mainPolFlag = false;
		if (strID.equals(tLCPolSchema.getMainPolNo())) {
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
		VData tPolData = m_GrpPolImpInfo.formatLCPol(tLCPolSchema, strID);
		if (tPolData == null) {
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
		if (poldataMap != null)
			tmpMap.add(poldataMap);

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
		
		for (int z = 1; z <= tLCDutySet.size(); z++) {
			if (!tLCDutySet.get(z).getAscriptionRuleCode().equals("")) {

				String Ascripsql = null;

				Ascripsql = "select FACTORYTYPE from lcascriptionrulefactory where grpcontno='"
						+ mGrpContNo
						+ "' and '"
						+ tLCDutySet.get(z).getAscriptionRuleCode()
						+ "' in (ASCRIPTIONRULECODE)";
				ExeSQL AscripExeSQL = new ExeSQL();
				SSRS Ascripssrs = new SSRS();
				Ascripssrs = AscripExeSQL.execSQL(Ascripsql);
				if (Ascripssrs.getMaxRow() >= 1) {
					String Ascrip = null;
					Ascrip = Ascripssrs.GetText(1, 1);
					logger.debug("Ascrip is" + Ascrip);
					logger.debug("insuredSchema is"
							+ insuredSchema.encode());
					if (Ascrip.equals("000006")
							&& (insuredSchema.getJoinCompanyDate() == null || insuredSchema
									.getJoinCompanyDate().equals(""))) {
						CError.buildErr(this, "未填写入司日期[" + contId + "]");
						return null;
					}
				} else {
					CError.buildErr(this, "未查询到[" + contId + "]对应的归属规则");
					return null;
				}
			} else // 归属规则判断，万能险(暂只有188，198)不允许为空，描述字段risktype6='4'。
			// liuyu-070808- IAAF один года
			{
				String WSQL = "select risktype6 from lmriskapp where riskcode='"
						+ tLCPolSchema.getRiskCode() + "' ";
				ExeSQL WExeSQL = new ExeSQL();
				String WFlag = WExeSQL.getOneValue(WSQL);
				if (WFlag == "4" || WFlag.equals("4")) {
					CError.buildErr(this, "[" + contId + "]对应的归属规则没有录入");
					return null;
				}
			}
		}

		// 设置主被保险人信息
		contSchema.setInsuredBirthday(insuredSchema.getBirthday());
		contSchema.setInsuredNo(insuredSchema.getInsuredNo());
		contSchema.setInsuredName(insuredSchema.getName());
		if (insuredSchema.getIDType().equals("0")) {
			contSchema.setInsuredIDNo(insuredSchema.getIDNo().toUpperCase());
		} else {
			contSchema.setInsuredIDNo(insuredSchema.getIDNo());
		}

		contSchema.setInsuredIDType(insuredSchema.getIDType());
		contSchema.setInsuredSex(insuredSchema.getSex());

		// 责任信息查询和生成

		LCContPlanRiskSchema tLCContPlanRiskSchema = new LCContPlanRiskSchema();
		tLCContPlanRiskSchema.setMainRiskCode(mainPolBL.getRiskCode());
		tLCContPlanRiskSchema.setGrpContNo(this.mGrpContNo);
		tLCContPlanRiskSchema.setRiskCode(tLCPolSchema.getRiskCode());
		// 00-默认值
		tLCContPlanRiskSchema.setContPlanCode("00");
		tLCContPlanRiskSchema.setProposalGrpContNo(this.mGrpContNo);

		LCContPlanRiskDB tLCContPlanRiskDB = new LCContPlanRiskDB();
		tLCContPlanRiskDB.setSchema(tLCContPlanRiskSchema);

		LCDutySet tempDutySet = null;
		// 如果没有默认值，则取excel中提交的数据
		if (!tLCContPlanRiskDB.getInfo()) {
			if (tLCDutySet != null && tLCDutySet.size() > 0) {
				// 责任项页签中有数据
				for (int j = 1; j <= tLCDutySet.size(); j++) {
					if ("0".equals(tLCDutySet.get(j).getCalRule())
							|| "1".equals(tLCDutySet.get(j).getCalRule())) {

						// CError.buildErr(this,
						// "责任项对应计算规则为表定费率或统一费率，但没有查到默认值");
						// return null;
					}
				}
				tempDutySet = tLCDutySet;
				logger.debug("责任项页签中有数据tempDutySet"
						+ tempDutySet.encode());
			} else {
				// 责任项页签没有数据，则用险种保单页签的数据
				LCDutySchema ntempDutySchema = new LCDutySchema();
				// tgetintv
				tLCPolSchema.setAgentCode1(tgetintv);
				setDutyByPolInfo(ntempDutySchema, tLCPolSchema);
				tempDutySet = new LCDutySet();
				tempDutySet.add(ntempDutySchema);
				logger.debug("责任项页签中没有数据tempDutySet"
						+ tempDutySet.encode());
			}
		} else {
			logger.debug("有默认值");

			// 如果有默认值，则需要查询默认值
			ContPlanQryBL contPlanQryBL = new ContPlanQryBL();
			VData tVData = new VData();
			tVData.add(tLCContPlanRiskSchema);
			boolean b = contPlanQryBL.submitData(tVData, "");
			if (!b || contPlanQryBL.mErrors.needDealError()) {
				CError.buildErr(this, "查询计划要约出错:", contPlanQryBL.mErrors);
				return null;
			}
			tVData = contPlanQryBL.getResult();
			tempDutySet = (LCDutySet) tVData.getObjectByObjectName("LCDutySet",
					0);
			if (tempDutySet == null) {
				// 只有一条责任项，以险种保单页签的数据为准
				LCDutySchema ttempDutySchema = (LCDutySchema) tVData
						.getObjectByObjectName("LCDutySchema", 0);

				// /setPolInfoByDuty(tLCPolSchema, ttempDutySchema);
				setDutyByPolInfo(ttempDutySchema, tLCPolSchema);
				tempDutySet = new LCDutySet();
				tempDutySet.add(ttempDutySchema);

			}
			// 有多条责任项，以责任项页签的数据为准
			for (int i = 1; i <= tLCDutySet.size(); i++) {

				for (int j = 1; j <= tempDutySet.size(); j++) {
					if (tempDutySet.get(j).getDutyCode().equals(
							tLCDutySet.get(i).getDutyCode())) {
						if (tLCDutySet.get(i).getMult() > 0) {
							tempDutySet.get(j).setMult(
									tLCDutySet.get(i).getMult());
						}
						if (tLCDutySet.get(i).getPrem() > 0) {
							tempDutySet.get(j).setPrem(
									tLCDutySet.get(i).getPrem());
						}
						if (tLCDutySet.get(i).getAmnt() > 0) {
							tempDutySet.get(j).setAmnt(
									tLCDutySet.get(i).getAmnt());
						}
						if (tLCDutySet.get(i).getPayIntv() > 0)
							tempDutySet.get(j).setPayIntv(
									tLCDutySet.get(i).getPayIntv());
						if (tLCDutySet.get(i).getInsuYear() > 0)
							tempDutySet.get(j).setInsuYear(
									tLCDutySet.get(i).getInsuYear());

						if (!"".equals(StrTool.cTrim(tLCDutySet.get(i)
								.getInsuYearFlag())))
							tempDutySet.get(j).setInsuYearFlag(
									tLCDutySet.get(i).getInsuYearFlag());

						if (tLCDutySet.get(i).getPayEndYear() > 0)
							tempDutySet.get(j).setPayEndYear(
									tLCDutySet.get(i).getPayEndYear());
						if (!"".equals(StrTool.cTrim(tLCDutySet.get(i)
								.getPayEndYearFlag())))
							tempDutySet.get(j).setPayEndYearFlag(
									tLCDutySet.get(i).getPayEndYearFlag());
						if (tLCDutySet.get(i).getGetYear() > 0)
							tempDutySet.get(j).setGetYear(
									tLCDutySet.get(i).getGetYear());

						if (!"".equals(StrTool.cTrim(tLCDutySet.get(i)
								.getGetYearFlag())))
							tempDutySet.get(j).setGetYearFlag(
									tLCDutySet.get(i).getGetYearFlag());

						if (!"".equals(StrTool.cTrim(tLCDutySet.get(i)
								.getGetStartType())))
							tempDutySet.get(j).setGetStartType(
									tLCDutySet.get(i).getGetStartType());

						// 计算方向
						if (!"".equals(StrTool.cTrim(tLCDutySet.get(i)
								.getPremToAmnt()))) {
							tempDutySet.get(j).setPremToAmnt(
									tLCDutySet.get(i).getPremToAmnt());
						}
						// 计算规则 被缓存在 最终核保人编码 字段
						if (!"".equals(StrTool.cTrim(tLCDutySet.get(i)
								.getCalRule()))) {
							tempDutySet.get(j).setCalRule(
									tLCDutySet.get(i).getCalRule());
						}
						// 费率
						if (tLCDutySet.get(i).getFloatRate() > 0) {
							if ("0".equals(tempDutySet.get(j).getCalRule())
									|| "1".equals(tempDutySet.get(j)
											.getCalRule())) {
								// 计算规则为 0-表定费率 或 1-统一费率 时
								// 费率必须是从默认值中取得
							} else {
								tempDutySet.get(j).setFloatRate(
										tLCDutySet.get(i).getFloatRate());
							}
						}
						// 免赔额
						if (tLCDutySet.get(i).getGetLimit() > 0) {
							tempDutySet.get(j).setGetLimit(
									tLCDutySet.get(i).getGetLimit());
						}
						// 赔付比例
						if (tLCDutySet.get(i).getGetRate() > 0) {
							tempDutySet.get(j).setGetRate(
									tLCDutySet.get(i).getGetRate());
						}
						if (tLCDutySet.get(i).getPeakLine()>0){
							tempDutySet.get(j).setPeakLine(
									tLCDutySet.get(i).getPeakLine());
                        }
                        if (tLCDutySet.get(i).getStandbyFlag1()!=null && !tLCDutySet.get(j).getStandbyFlag1().equals("")){
                        	tempDutySet.get(j).setStandbyFlag1(
                        			tLCDutySet.get(i).getStandbyFlag1());
                        }
                        if (tLCDutySet.get(i).getStandbyFlag2()!=null && !tLCDutySet.get(j).getStandbyFlag2().equals("")){
                        	tempDutySet.get(j).setStandbyFlag2(
                        			tLCDutySet.get(i).getStandbyFlag2());
                        }
                        if (tLCDutySet.get(i).getStandbyFlag3()!=null && !tLCDutySet.get(j).getStandbyFlag3().equals("")){
                        	tempDutySet.get(j).setStandbyFlag3(
                        			tLCDutySet.get(i).getStandbyFlag3());
                        }
                        if (tLCDutySet.get(i).getAscriptionRuleCode()!=null && !tLCDutySet.get(j).getAscriptionRuleCode().equals("")){
                        	tempDutySet.get(j).setAscriptionRuleCode(
                        			tLCDutySet.get(i).getAscriptionRuleCode());
                        }
                        tempDutySet.get(j).setGetIntv(tLCDutySet.get(j).getGetIntv());
                        tempDutySet.get(j).setPayIntv(tLCPolSchema.getPayIntv());

						break;
					}
				}
			}
		}
		// 清空借用字段
		tLCPolSchema.setAppFlag("");
		tLCPolSchema.setUWCode("");
		tLCPolSchema.setApproveFlag("");
		tLCPolSchema.setUWFlag("");

		// 处理连身被保险人
		LCInsuredRelatedSet tRelaInsSet = null;
		String[] relaIns = null;
		relaIns = m_GrpPolImpInfo.getInsuredRela(contId,InsuredId,strRiskCode);
//		if (!"".equals(StrTool.cTrim(relaInsId))) {
//			relaIns = relaInsId.split(",");
//			if (relaIns == null)
//				relaInsId.split(";");
			if (relaIns != null) {
				tRelaInsSet = m_GrpPolImpInfo.getInsuredRelaData(tLCPolSchema
						.getInsuredNo(), relaIns,this.mBatchNo);
				if (tRelaInsSet == null) {
					// 创建连带被保险人出错
					mErrors.copyAllErrors(m_GrpPolImpInfo.mErrors);
					return null;
				}else{
					//判断是否为连带被保人 如果是 则提交所有数据时只提交lcinsured & lcinsuredrelated表
					if(tRelaInsSet.size()>0){
						String tRelation =tRelaInsSet.get(1).getRelationToInsured();
						if(!"00".equals(tRelation)&&!"".equals(tRelation)){
							//连带被保人
							mRelation =true;
						}else{
							mRelation =false;
							mMainSequenceNo = tRelaInsSet.get(1).getSequenceNo();
						}
					}
				}

			}
//		}
//			//统计连带被保人 
//			if(tRelaInsSet!=null&&tempDutySet!=null){
//				//有连带被保人
//				for(int i=1;i<=tempDutySet.size();i++){
//					tempDutySet.get(i).setStandbyFlag1("1");
//					tempDutySet.get(i).setStandbyFlag2(String.valueOf(tRelaInsSet.size()));
//				}
//			}

		String PolKey = m_GrpPolImpInfo.getPolKey(contId, InsuredId,
				strRiskCode);
		tTransferData.setNameAndValue("PolKey", PolKey);

		// 受益人信息
		LCBnfSet tLCBnfSet = new LCBnfSet();
		tLCBnfSet = m_GrpPolImpInfo.getBnfSetByPolId(strID);
		if (tLCBnfSet != null && tLCBnfSet.size() > 0) {
			VData tr = m_GrpPolImpInfo.perareBnf(tLCBnfSet, insuredSchema,
					tLCGrpPolSchema, InsuredId);

			MMap tmap = (MMap) tr.getObjectByObjectName("MMap", 0);
			if (tmap != null)
				tmpMap.add(tmap);
			tLCBnfSet = (LCBnfSet) tr.getObjectByObjectName("LCBnfSet", 0);
		}

		tTransferData.setNameAndValue("samePersonFlag", 0);
		tTransferData.setNameAndValue("GrpImport", 1); // 磁盘投保标志
		// 险种描述信息
		LMRiskAppSchema tLMRiskAppSchema =new LMRiskAppSchema();
		LMRiskSchema tLMRiskSchema=new LMRiskSchema();
		if(mRiskCode!=null){
			for(int j=0;j<mRiskCode.length;j++){
				tLMRiskAppSchema = m_GrpPolImpInfo
				.findLMRiskAppSchema(mRiskCode[j]);
				if (tLMRiskAppSchema == null) {
					buildError("prepareData", strRiskCode + "险种对应的险种承保描述没有找到!");
					return null;
				}
				tLMRiskSchema = m_GrpPolImpInfo
				.findLMRiskSchema(mRiskCode[j]);
				if (tLMRiskSchema == null) {
					buildError("prepareData", strRiskCode + "险种对应的险种承保描述没有找到!");
					return null;
				}
			}
		}

		LDGrpSchema ttLDGrpSchema = m_GrpPolImpInfo
				.findLDGrpSchema(tLCGrpPolSchema.getCustomerNo());
		if (ttLDGrpSchema == null) {
			buildError("prepareData", tLCGrpPolSchema.getCustomerNo()
					+ "对应的集体信息没有找到!");
			return null;
		}

		// 增加了保全分支判断,如果是 保全增加被保人，设置保全保存标记 2
		// jixf 20051102
		if (mTransferData.getValueByName("BQFlag") != null
				&& mTransferData.getValueByName("BQFlag") != ""
				&& "2".equals(StrTool.cTrim(mTransferData.getValueByName(
						"BQFlag").toString()))
				&& ("NI".equals(StrTool.cTrim(mTransferData.getValueByName(
						"EdorType").toString())) || "NR".equals(StrTool
						.cTrim(mTransferData.getValueByName("EdorType")
								.toString())))) {
			// 保全保存标记
			tTransferData.removeByName("SavePolType");
			tTransferData.setNameAndValue("SavePolType", "2");
			tTransferData.setNameAndValue("EdorType", mTransferData
					.getValueByName("EdorType"));
			tTransferData.setNameAndValue("EdorTypeCal", mTransferData
					.getValueByName("EdorTypeCal"));
			tTransferData.setNameAndValue("EdorValiDate", mTransferData
					.getValueByName("EdorValiDate"));
		}
		if ("Y".equals(tLCPolSchema.getSpecifyValiDate())
				&& tLCPolSchema.getCValiDate() != null) {
			// 如果磁盘倒入指定生效日期，且生效日期字段有值,那么就用 生效日期字段的值
		} else {
			// 否则一律用集体单的生效日期
			tLCPolSchema.setCValiDate(tLCGrpPolSchema.getCValiDate());
			// 如果是保全增人，磁盘倒入指定生效日期，且生效日期字段无值,那么就用 页面传入值
			// jixf 20051102
			logger.debug("---------------------");
			if (mTransferData.getValueByName("BQFlag") != null
					&& mTransferData.getValueByName("BQFlag") != "") {
				String EdorValiDate = (String) mTransferData
						.getValueByName("EdorValiDate");
				logger.debug("----------Edorvalidate-----------"
						+ EdorValiDate);
				tLCPolSchema.setCValiDate(EdorValiDate);
			}

		}

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
		tNewVData.add(tempDutySet);
		if (tRelaInsSet != null)
			tNewVData.add(tRelaInsSet);
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
				mGlobalInput);
		logErrors.copyAllErrors(this.mErrors);
		this.mErrors.clearErrors();
		//m_GrpPolImpInfo.mErrors.clearErrors();

	}

	/**
	 * 获取排序的keyset
	 * 
	 * @param set
	 *            Set
	 * @return Object[]
	 */

	private int[] getOrderKeySetInt(Set set) {
		if (set == null)
			return null;
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
		tLCPolSchema.setPrem(dutySchema.getPrem());
		tLCPolSchema.setAmnt(dutySchema.getAmnt());
		tLCPolSchema.setPayEndYear(dutySchema.getPayEndYear());
		tLCPolSchema.setPayEndYearFlag(dutySchema.getPayEndYearFlag());
		tLCPolSchema.setGetYear(dutySchema.getGetYear());
		tLCPolSchema.setGetYearFlag(dutySchema.getGetYearFlag());
		tLCPolSchema.setAcciYear(dutySchema.getAcciYear());
		tLCPolSchema.setAcciYearFlag(dutySchema.getAcciYearFlag());
		tLCPolSchema.setMult(dutySchema.getMult());
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
		if (tLCPolSchema.getMult() > 0) {
			dutySchema.setMult(tLCPolSchema.getMult());
		}
		if (tLCPolSchema.getPrem() > 0) {
			dutySchema.setPrem(tLCPolSchema.getPrem());
		}
		if (tLCPolSchema.getAmnt() > 0) {
			dutySchema.setAmnt(tLCPolSchema.getAmnt());
		}
		if (tLCPolSchema.getPayIntv() > 0) {
			dutySchema.setPayIntv(tLCPolSchema.getPayIntv());
		}
		if (tLCPolSchema.getInsuYear() > 0) {
			dutySchema.setInsuYear(tLCPolSchema.getInsuYear());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getInsuYearFlag()))) {
			dutySchema.setInsuYearFlag(tLCPolSchema.getInsuYearFlag());
		}
		if (tLCPolSchema.getPayEndYear() > 0) {
			dutySchema.setPayEndYear(tLCPolSchema.getPayEndYear());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getPayEndYearFlag()))) {
			dutySchema.setPayEndYearFlag(tLCPolSchema.getPayEndYearFlag());
		}
		if (tLCPolSchema.getGetYear() > 0) {
			dutySchema.setGetYear(tLCPolSchema.getGetYear());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getGetYearFlag()))) {
			dutySchema.setGetYearFlag(tLCPolSchema.getGetYearFlag());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getGetStartType()))) {
			dutySchema.setGetStartType(tLCPolSchema.getGetStartType());
		}
		// 计算方向
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getPremToAmnt()))) {
			dutySchema.setPremToAmnt(tLCPolSchema.getPremToAmnt());
		}
		// 计算规则被缓存在 最终核保人编码 UWCode 字段
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getUWCode()))) {
			dutySchema.setCalRule(tLCPolSchema.getUWCode());
		}
		// 费率
		if (tLCPolSchema.getFloatRate() > 0) {
			if ("0".equals(dutySchema.getCalRule())
					|| "1".equals(dutySchema.getCalRule())) {
				// 计算规则为 0-表定费率 或 1-统一费率 时
				// 费率必须是从默认值中取得
			} else {
				dutySchema.setFloatRate(tLCPolSchema.getFloatRate());
			}
		}

		if (!"".equals(StrTool.cTrim(tLCPolSchema.getStandbyFlag1()))) {
			dutySchema.setStandbyFlag1(tLCPolSchema.getStandbyFlag1());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getStandbyFlag2()))) {
			dutySchema.setStandbyFlag2(tLCPolSchema.getStandbyFlag2());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getStandbyFlag3()))) {
			dutySchema.setStandbyFlag3(tLCPolSchema.getStandbyFlag3());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getApproveFlag()))) {
			dutySchema.setGetLimit(tLCPolSchema.getApproveFlag());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getUWFlag()))) {
			dutySchema.setGetRate(tLCPolSchema.getUWFlag());
		}
		// 增加缴费规则与归属规则
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getAscriptionRuleCode()))) {
			dutySchema.setAscriptionRuleCode(tLCPolSchema
					.getAscriptionRuleCode());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getPayRuleCode()))) {
			dutySchema.setPayRuleCode(tLCPolSchema.getPayRuleCode());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getBonusGetMode()))) {
			dutySchema.setBonusGetMode(tLCPolSchema.getBonusGetMode());
		}
		if (!"".equals(StrTool.cTrim(tLCPolSchema.getAgentCode1()))) {
			dutySchema.setGetIntv(tLCPolSchema.getAgentCode1());
		}
	}

}
