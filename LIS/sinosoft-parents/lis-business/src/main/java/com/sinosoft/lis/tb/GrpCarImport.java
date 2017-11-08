package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Date;
import java.util.Random;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.LCContPlanRiskDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContPlanRiskSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpImportLogSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolOtherSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.vbl.LCBnfBLSet;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vbl.LCPremBLSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCInsuredRelatedSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolOtherSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCPremToAccSet;
import com.sinosoft.lis.vschema.LMRiskSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XMLPathTool;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class GrpCarImport {
private static Logger logger = Logger.getLogger(GrpCarImport.class);
	// @Fields
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	public CErrors logErrors = new CErrors();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 内存文件暂存 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	private String FileName;
	private String XmlFileName;
	private String mPreFilePath;

	private String mGrpContNo;

	// 数据Xml解析节点描述
	private String FilePath = "upload/";
	private String ParseRootPath = "/DATASET/BATCHID";
	private String PATH_GRPCONTNO = "/DATASET/GRPCONTNO";
	private String ParsePath = "/DATASET/CONTTABLE/ROW";

	// 配置文件Xml节点描述
	private String ImportFileName;
	private String ConfigFileName;
	private String mBatchNo = "";
	private String mPrtNo = "";
	private String mContNo = "";
	private LCPolBL mainPolBL = new LCPolBL();
	private org.w3c.dom.Document m_doc = null; // 临时的Document对象

	private GrpCarImpInfo m_GrpCarImpInfo = new GrpCarImpInfo();

	private String[] m_strDataFiles = null;
	private String[][] m_IDNoSet = null;

	private String mProposalGrpContNo;

	public GrpCarImport() {
		bulidDocument();
	}

	public GrpCarImport(String fileName) {
		bulidDocument();
		FileName = fileName;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();

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
				this.ParseXml();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.debug(ex.toString());
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "导入文件格式有误!";
			this.logErrors.addOneError(tError);
		}

		if (this.logErrors.getErrorCount() > 0) {
			this.mErrors = this.logErrors;
			logger.debug("结束时间:" + PubFun.getCurrentTime());
			return false;
		}
		logger.debug("结束时间:" + PubFun.getCurrentTime());
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

		ConfigFileName = filePath + "CarImport.xml";
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

		GrpCarVTSParser gcvp = new GrpCarVTSParser();

		if (!gcvp.setFileName(ImportFileName)) {
			mErrors.copyAllErrors(gcvp.mErrors);
			return false;
		}
		if (!gcvp.setConfigFileName(ConfigFileName)) {
			mErrors.copyAllErrors(gcvp.mErrors);
			return false;
		}
		// 转换excel到xml
		if (!gcvp.transform()) {
			mErrors.copyAllErrors(gcvp.mErrors);
			return false;
		}

		// 得到生成的XML数据文件名
		m_strDataFiles = gcvp.getDataFiles();

		return true;
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
		LCGrpContSet tLCGrpContSet = grpContDB.query();
		if (tLCGrpContSet == null || tLCGrpContSet.size() <= 0) {
			CError.buildErr(this, "没有找到团体合同[" + tPrtNo + "]的信息");
			m_GrpCarImpInfo.logError(tBatchNo, tPrtNo, "", "", "", "", "",
					null, this.mErrors, mGlobalInput, FileName);
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

		if ("1".equals(StrTool.cTrim(tLCGrpContSchema.getAppFlag()))) {
			CError.buildErr(this, "团体合同[" + mGrpContNo + "]已经签单，不再接受投保");
			m_GrpCarImpInfo.logError(tBatchNo, mGrpContNo, "", "", "", "", "",
					null, this.mErrors, mGlobalInput, FileName);
			this.mErrors.clearErrors();

			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "ParseXml";
			tError.errorMessage = "团体合同[" + mGrpContNo + "]已经签单，不再接受投保";
			this.logErrors.addOneError(tError);

			return false;
		}

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
		GrpCarParser tGrpCarParser = new GrpCarParser();
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
			LCInsuredSet tLCInsuredSet = null;
			LCPolOtherSet tLCPolOtherSet = null;
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

				if (nodeName.equals("CARTABLE")) {
					// 解析被保险人xml
					tLCInsuredSet = (LCInsuredSet) tGrpCarParser
							.getLCInsuredSet(contNode);
					tLCPolOtherSet = (LCPolOtherSet) tGrpCarParser
							.getLCPolOtherSet(contNode);
					continue;
				}

				if (nodeName.equals("LCPOLTABLE")) {
					// 保单信息
					VData tVData = null;

					// 解析一个保单节点
					tVData = tGrpCarParser.parseLCPolNode(contNode);
					// 缓存险种保单信息
					m_GrpCarImpInfo.addContPolData(contId, tVData); // contid
					continue;
				}
			}

			if (tLCInsuredSet == null || tLCPolOtherSet == null) {
				CError.buildErr(this, "车辆信息节点解析失败,车辆信息不能为空!");
				m_GrpCarImpInfo.logError(tBatchNo, grpContDB.getGrpContNo(),
						"", "", "", "", "", null, this.mErrors, mGlobalInput,
						FileName);
				this.mErrors.clearErrors();

				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ParseGuideIn";
				tError.functionName = "ParseXml";
				tError.errorMessage = "车辆信息节点解析失败,车辆信息不能为空!";
				this.logErrors.addOneError(tError);

				return false;
			}

			logger.debug("ParseXml,birthday="
					+ tLCInsuredSet.get(1).getBirthday());

			if (!m_GrpCarImpInfo.init(tBatchNo, this.mGlobalInput,
					this.mTransferData, // add this.mTransferData参数
					tLCInsuredSet, tLCGrpContSchema, tLCPolOtherSet)) {
				m_GrpCarImpInfo.logError(tBatchNo, grpContDB.getGrpContNo(),
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
			// m_GrpCarImpInfo.logError(strID, tLCPolSchema, false,
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
			LCPolOtherSchema rLCPolOtherSchema = (LCPolOtherSchema) pData
					.getObjectByObjectName("LCPolOtherSchema", 0);

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
			String contId = m_GrpCarImpInfo.getContKey(PolKey);
			LCContSchema tLCContSchema = m_GrpCarImpInfo
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
			LCGrpPolSchema tLCGrpPolSchema = m_GrpCarImpInfo
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
			LCGrpContSchema sGrpContSchema = m_GrpCarImpInfo
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
			map.put(rLCPolOtherSchema, "INSERT");
			map.put(rGrpPol, "UPDATE");
			map.put(rGrpCont, "UPDATE");
			map.put(rPremSet, "INSERT");
			map.put(rDutySet, "INSERT");
			map.put(rGetSet, "INSERT");
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
			map.put(s1, "UPDATE");
			map.put(s2, "UPDATE");
			// 缓存保存的主险保单信息
			m_GrpCarImpInfo.cachePolInfo(strID, rPolSchema);
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
		LCInsuredSet tInsuredSet = m_GrpCarImpInfo.getLCInsuredSet();
		LCPolOtherSet tLCPolOtherSet = m_GrpCarImpInfo.getLCPolOtherSet();
		LCPolOtherSchema tLCPolOtherSchema = null;
		if (tLCPolOtherSet != null && tLCPolOtherSet.size() > 0) {
			tLCPolOtherSchema = tLCPolOtherSet.get(1);
		}
		MMap subMap = null; // 提交结果集缓存
		boolean state = true; // 导入状态，
		boolean saveState = true;
		String logRiskCode = "";
		String logPolId = "";
		String insuredIndex = "";

		subMap = null;
		mContNo = "";
		LCGrpImportLogSchema logSchema = m_GrpCarImpInfo.getLogInfo(mBatchNo,
				contIndex);

		if (logSchema != null && "0".equals(logSchema.getErrorState())
				&& mTransferData.getValueByName("BQFlag") == null
				&& mTransferData.getValueByName("BQFlag") == "") {
			return true;
		}

		for (int i = 1; i <= tInsuredSet.size(); i++) {
			logger.debug("Recycle Count=" + i);

			// 初始化，避免重复使用
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
			} else {
				break;
			}
		}

		state = true;

		// 导入险种保单，先根据合同ID查找到所有相同合同id下的所有保单数据，再一张一张保单信息导入
		VData tContPolData = (VData) m_GrpCarImpInfo.getContPolData(contIndex);
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
					// 初始化，避免重复使用
					logRiskCode = "";
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

					// Description:同一被保人多次导入同一险种，则为错
					LMRiskSchema tLMRiskSchema = new LMRiskSchema();
					if (tLMRiskSet == null || tLMRiskSet.size() < 1) {
						tLMRiskSchema = new LMRiskSchema();
						tLMRiskSchema.setRiskCode(logRiskCode);
						tLMRiskSet.add(tLMRiskSchema);
					} else {
						int tOldSize = tLMRiskSet.size();
						for (int n = 1; n <= tOldSize; n++) {
							tLMRiskSchema = tLMRiskSet.get(n);
							if (tLMRiskSchema.getRiskCode().equals(logRiskCode)
									&& insuredIndex.equals(insuredSchema
											.getPrtNo())) {
								CError.buildErr(this, "同一被保人--" + insuredIndex
										+ "--不能重复添加相同险种！");
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

					VData newPolData = new VData();
					newPolData = onePolData;
					VData tprepareData = this.prepareProposalBLData(newPolData);
					logger.debug("prepareProposalBL end");

					if (tprepareData == null) {
						logger.debug("准备数据的时候出错！");
						state = false;
					} else {
						MMap tMap = this.submiDatattoProposalBL(tprepareData);
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
		if (state == false) {

			logError(contIndex, insuredIndex, logPolId, "", logRiskCode,
					tLCPolOtherSchema);
			saveState = false;
			return false;
			// continue;
		}

		boolean bs = true;

		if (state && subMap != null && subMap.keySet().size() > 0) {
			MMap logMap = m_GrpCarImpInfo.logSucc(mBatchNo, mGrpContNo,
					contIndex, mPrtNo, mContNo, mGlobalInput);
			subMap.add(logMap);
			bs = batchSave(subMap);
		}

		if (!bs || !state) {
			// 合同导入没有成功，需要回退缓存
			m_GrpCarImpInfo.removeCaceh(contIndex);
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
		return saveState;
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

		// 查询集体保单信息
		LCGrpPolSchema sLCGrpPolSchema = m_GrpCarImpInfo
				.findLCGrpPolSchema(strRiskCode);
		if (sLCGrpPolSchema == null) {
			buildError("prepareProposalBLData", strRiskCode + "险种对应的集体投保单没有找到!");
			return null;
		}
		// 拷贝一份，避免修改本地数据
		LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
		tLCGrpPolSchema.setSchema(sLCGrpPolSchema);
		boolean mainPolFlag = false;
		// 初始化是不是主险 如果不是需要主险信息
		logger.debug("主险号码=====" + tLCPolSchema.getMainPolNo());
		if (strRiskCode.trim().equals(tLCPolSchema.getMainPolNo().trim())) {
			mainPolFlag = true;
		}

		// 格式化保单信息
		VData tPolData = m_GrpCarImpInfo.formatLCPol(tLCPolSchema, strID);
		if (tPolData == null) {
			logger.debug(m_GrpCarImpInfo.mErrors.toString());
			mErrors.copyAllErrors(m_GrpCarImpInfo.mErrors);
			return null;
		}
		tLCPolSchema = (LCPolSchema) tPolData.getObjectByObjectName(
				"LCPolSchema", 0);
		if (!mainPolFlag) {
			LCPolSchema mainPolSchema = m_GrpCarImpInfo
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
		LCInsuredSchema insuredSchema = m_GrpCarImpInfo
				.findInsuredfromCache(InsuredId);
		if (insuredSchema == null) {
			CError.buildErr(this, "未找到被保险人[" + InsuredId + "]");
			return null;
		}

		LCContSchema contSchema = m_GrpCarImpInfo.findLCContfromCache(contId);
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

		
		if (!tLCContPlanRiskDB.getInfo()) {

			CError.buildErr(this, "请先录入险种[" + strRiskCode + "]的险种信息！");
			return null;
		} else {
			// 如果有默认值，则需要查询默认值
			ContPlanQryBL contPlanQryBL = new ContPlanQryBL();
			VData tVData = new VData();
			tVData.add(tLCContPlanRiskSchema);
			logger.debug("-----------查询默认值");
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

		String PolKey = m_GrpCarImpInfo.getPolKey(contId, InsuredId,
				strRiskCode);
		tTransferData.setNameAndValue("PolKey", PolKey);
		logger.debug("责任项准备完毕-PolKey" + PolKey);
		logger.debug("责任项准备完毕-strID" + strID);

		// 受益人信息
		LCBnfSet tLCBnfSet = new LCBnfSet();

		logger.debug("受益人信息-tLCBnfSet");
		tTransferData.setNameAndValue("samePersonFlag", 0);
		tTransferData.setNameAndValue("GrpImport", 1); // 磁盘投保标志
		// 险种描述信息
		LMRiskAppSchema tLMRiskAppSchema = m_GrpCarImpInfo
				.findLMRiskAppSchema(strRiskCode);
		if (tLMRiskAppSchema == null) {
			buildError("prepareData", strRiskCode + "险种对应的险种承保描述没有找到!");
			return null;
		}
		LMRiskSchema tLMRiskSchema = m_GrpCarImpInfo
				.findLMRiskSchema(strRiskCode);
		if (tLMRiskSchema == null) {
			buildError("prepareData", strRiskCode + "险种对应的险种承保描述没有找到!");
			return null;
		}
		logger.debug("险种描述信息");

		LDGrpSchema ttLDGrpSchema = m_GrpCarImpInfo
				.findLDGrpSchema(tLCGrpPolSchema.getCustomerNo());
		if (ttLDGrpSchema == null) {
			buildError("prepareData", tLCGrpPolSchema.getCustomerNo()
					+ "对应的集体信息没有找到!");
			return null;
		}
		logger.debug("对应的集体信息");

		tNewVData.add(tTransferData);
		tNewVData.add(mGlobalInput);

		if (tempDutySet == null || tempDutySet.size() <= 0) {
			CError.buildErr(this, "险种[" + strRiskCode + "]责任不能为空");
			return null;
		}

		LCPolOtherSet tLCPolOtherSet = m_GrpCarImpInfo
				.formatLCPolOtherSet(tLCPolSchema);
		if (tLCPolOtherSet != null && tLCPolOtherSet.size() > 0) {
			for (int i = 0; i < tLCPolOtherSet.size(); i++) {
				tLCPolOtherSet.get(i + 1).setDutyCode(
						tempDutySet.get(1).getDutyCode());
			}
		}

		logger.debug("tempDutySet" + tempDutySet.encode());
		tNewVData.add(tempDutySet);
		tNewVData.add(tLCPolSchema);
		tNewVData.add(tLCPolOtherSet);
		tNewVData.add(tLCPremSet);
		tNewVData.add(tLCPremToAccSet);
		tNewVData.add(contSchema);
		tNewVData.add(insuredSchema);
		tNewVData.add(tLCBnfSet);

		tNewVData.add(tLCGrpPolSchema);
		tNewVData.add(m_GrpCarImpInfo.getLCGrpAppntSchema());
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
			String contplancode, String riskcode,
			LCPolOtherSchema cLCPolOtherSchema) {
		if (m_GrpCarImpInfo.mErrors.getErrorCount() >= 0) {
			this.mErrors.copyAllErrors(m_GrpCarImpInfo.mErrors);
		}
		m_GrpCarImpInfo.logError(mBatchNo, mGrpContNo, contId, insuredId,
				polId, contplancode, riskcode, cLCPolOtherSchema, this.mErrors,
				mGlobalInput, FileName);
		logErrors.copyAllErrors(this.mErrors);
		this.mErrors.clearErrors();
		m_GrpCarImpInfo.mErrors.clearErrors();

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
		tSSRS = exeSql.execSQL("select payintv from lcgrppol where grpcontno='"
				+ temcontno + "' and riskcode='" + temriskcode + "'");
		if (tSSRS.MaxRow > 0) {
			dutySchema.setPayIntv(tSSRS.GetText(1, 1));
		} else {

			if (tLCPolSchema.getPayIntv() > 0) {
				dutySchema.setPayIntv(tLCPolSchema.getPayIntv());
			}
		}

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
		GrpCarImport grpcarimport = new GrpCarImport();
		GlobalInput tGI = new GlobalInput(); // repair:
		tGI.ManageCom = "86";
		tGI.Operator = "001";

		// 准备传输数据 VData
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("FileName", "20061020000002.xls");
		tTransferData.setNameAndValue("FilePath", "D:/lis/ui/");
		tVData.add(tTransferData);
		tVData.add(tGI);
		try {
			if (grpcarimport.submitData(tVData, "")) {
				logger.debug("Succ");
			} else {
				logger.debug(grpcarimport.mErrors.getFirstError());
			}
		} catch (Exception ex) {
			logger.debug(ex.toString());
		}

	}
}
