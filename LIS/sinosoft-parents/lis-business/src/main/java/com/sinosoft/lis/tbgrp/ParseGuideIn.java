/*
 * @(#)ParseGuideIn.java	2004-12-13
 *
 * Copyright 2004 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpImportLogDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.MultThreadErrorDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LDCodeSchema;
import com.sinosoft.lis.tb.ContInsuredBL;
import com.sinosoft.lis.vdb.LCGrpImportLogDBSet;
import com.sinosoft.lis.vdb.MultThreadErrorDBSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpImportLogSet;
import com.sinosoft.lis.vschema.LCInsuredRelatedSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.lis.vschema.MultThreadErrorSet;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
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
	private org.jdom.Element myElement;
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();
	

	private String FileName;
	private String XmlFileName;
	private String mPreFilePath;

	private String mGrpContNo;

	private String mEdorType;
	private String mEdorTypeCal;
	private String mBQFlag;
	private String mSavePolType;
	private String mEdorValiDate;
	
	private boolean hasDel = false;

	// 数据Xml解析节点描述
	private String FilePath = "E:/temp/";
	private String ParseRootPath = "/DATASET/BATCHID";
	private String PATH_GRPCONTNO = "/DATASET/GRPCONTNO";
	private String ParsePath = "/DATASET/CONTTABLE/ROW";

	// 配置文件Xml节点描述
	private String ImportFileName;
	private String ConfigFileName;
	private org.w3c.dom.Document m_doc = null; // 临时的Document对象


	private List mInsuredList = new ArrayList();
	private String[] m_strDataFiles = null;

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
		this.getInputData();
		if (!this.checkData())
			return false;
		
		long tStartTime = System.currentTimeMillis();
		try {
			if (!this.parseVts()) {
				return false;
			}

			for (int nIndex = 0; nIndex < m_strDataFiles.length; nIndex++) {
				XmlFileName = m_strDataFiles[nIndex];
				if (!ParseXml()){
					return false;
				}
			}

		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "导入文件格式有误!";
			this.logErrors.addOneError(tError);
		}
		if (this.mErrors.getErrorCount() > 0){
			
			return false;
		}
		long tEndTime = System.currentTimeMillis();
		logger.debug("**************导入用时总计:" + ((tEndTime-tStartTime)/1000)+"秒**************************");
		logger.debug("**************导入被保人数总计:"+mInsuredList.size()+"人********************");
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
		mEdorType =  (String)mTransferData.getValueByName("EdorType");
		mEdorTypeCal = (String)mTransferData.getValueByName("EdorTypeCal");
		mBQFlag = (String)mTransferData.getValueByName("BQFlag");
		mSavePolType = (String)mTransferData.getValueByName("SavePolType");
		mEdorValiDate = (String)mTransferData.getValueByName("EdorValiDate");
		if(mBQFlag != null && mBQFlag.equals("2")&&("NI".equals(mEdorType)||"NR".equals(mEdorType))){
			mSavePolType = "2";
		}

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
			tElt = (org.jdom.Element) tElt.getChildren((String) aVT.get(i - 1))
					.get(tElt.getChildren((String) aVT.get(i - 1)).size() - 1);
			NodeCount--;
		}
		tElt.addContent(aElt);
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
		File tFile = new File(XmlFileName);
		XMLPathTool tXPT = new XMLPathTool(XmlFileName);
		String tBatchNo = "";
		String tPrtNo = "";
		try {
			// 批次号
			tBatchNo = tXPT.parseX(ParseRootPath).getFirstChild().getNodeValue();
			// 印刷号
			tPrtNo = tXPT.parseX(PATH_GRPCONTNO).getLastChild().getNodeValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		LCGrpContDB grpContDB = new LCGrpContDB();
		grpContDB.setPrtNo(tPrtNo);
		LCGrpContSet tLCGrpContSet = grpContDB.query();
		if (tLCGrpContSet == null || tLCGrpContSet.size() <= 0) {
			CError.buildErr(this, "没有找到团体合同[" + tPrtNo + "]的信息");
			return false;
		}
		LCGrpContSchema tLCGrpContSchema = tLCGrpContSet.get(1);
		mGrpContNo = tLCGrpContSchema.getGrpContNo();
		
			
		
		if (mTransferData.getValueByName("BQFlag") != null
				&& mTransferData.getValueByName("BQFlag") != ""
				&& "2".equals(StrTool.cTrim(mTransferData.getValueByName(
						"BQFlag").toString())))
		{

		} else {

			if ("1".equals(StrTool.cTrim(tLCGrpContSchema.getAppFlag()))) {
				CError.buildErr(this, "团体合同[" + mGrpContNo + "]已经签单，不再接受投保");
				return false;
			}
		}
		if(!hasDel){
			//防止连带被保人,当拆分分若干个XML时,第1000例数据为主被保人,第1001例为连带被保人,如重复做删除操作将会导致第1001例找不到主被保人
			String tSQL = "select * from lcgrpimportlog where grpcontno='"+mGrpContNo+"' ";
			if(tPrtNo!= null && !mGrpContNo.equals(tPrtNo)){
				tSQL = "select * from lcgrpimportlog where grpcontno in ('"+mGrpContNo+"','"+tPrtNo+"') ";
			}
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
			hasDel = true;
		}
		// 团单合同的录单完成日期不为空则表示已经录入完成
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
		Vector mTaskWaitList = new Vector(); 
		Vector mTaskWaitListRela = new Vector();
		
		for (int i = 0; i < nNodeCount; i++) { // nNodeCount为node数量(合同数、人数)
			Node node = nodeList.item(i);

			try {
				node = transformNode(node);
			} catch (Exception ex) {
				ex.printStackTrace();
				node = null;
			}

			NodeList contNodeList = node.getChildNodes();
			if (contNodeList.getLength() <= 0)
				continue; // 该节点没有值
			Node contNode = null;
			String nodeName = "";
			LCInsuredRelatedSet tLCInsuredRelatedSet = null;
			LCInsuredSet tLCInsuredSet = null;
			LCBnfSet tLCBnfSet = null;
			String contId = "";
			
			VData tCurrentContData = new VData();
			TransferData tCurrentTransferData = new TransferData();
			tCurrentTransferData.setNameAndValue("BQFlag", mBQFlag);
			tCurrentTransferData.setNameAndValue("EdorType", mEdorType);
			tCurrentTransferData.setNameAndValue("EdorTypeCal", mEdorTypeCal);
			tCurrentTransferData.setNameAndValue("EdorValiDate", mEdorValiDate);
			tCurrentTransferData.setNameAndValue("SavePolType", mSavePolType);
			
			boolean isRelation = false;
			for (int j = 0; j < contNodeList.getLength(); j++) { // j<4
				
				contNode = contNodeList.item(j); // 合同项
				nodeName = contNode.getNodeName();
				if (nodeName.equals("#text"))
					continue;
				if (nodeName.equals("CONTID")) {
					contId = contNode.getFirstChild().getNodeValue();
					tCurrentTransferData.setNameAndValue("CONTID", contId);
					tCurrentTransferData.setNameAndValue("BatchNo", tBatchNo);
					tCurrentContData.add(0,tCurrentTransferData);
					continue;
				}

				if (nodeName.equals("INSUREDTABLE")) {
					// 解析被保险人xml
					tLCInsuredSet = (LCInsuredSet) tGrpPolParser.getLCInsuredSet(contNode);
					tLCInsuredRelatedSet = (LCInsuredRelatedSet) tGrpPolParser.getLCInsuredRelatedSet(contNode);
					//保存连带被保人的连带险种
					if (tLCInsuredSet == null || tLCInsuredSet.size() <= 0) {
						this.mErrors.copyAllErrors(tGrpPolParser.mErrors);
						continue;
					}
					if(tLCInsuredRelatedSet!= null && tLCInsuredRelatedSet.size()==1){
						String tRelation = tLCInsuredRelatedSet.get(1).getRelationToInsured();
						if(tRelation != null && !tRelation.equals("")&& !tRelation.equals("00")){
							isRelation = true;
						}
					}
					LCInsuredSchema tLCInsuredSchema = tLCInsuredSet.get(1);
					String tName = StrTool.cTrim(tLCInsuredSchema.getName());
					String tSex = StrTool.cTrim(tLCInsuredSchema.getSex());
					String tIDType = StrTool.cTrim(tLCInsuredSchema.getIDType());
					String tIDNo = StrTool.cTrim(tLCInsuredSchema.getIDNo());
					String tBirthDay = StrTool.cTrim(tLCInsuredSchema.getBirthday());
					
					if("0".equals(tIDType)){
						if(tIDNo.length()==15){
							//15位升为18位
							tIDNo = ContInsuredBL.get18IDNo(tIDNo, tBirthDay);
						}
					}
					//五要素校验重复信息
					String insuredInfo = tName+"_"+tSex+"_"+tIDType+"_"+tIDNo+"_"+tBirthDay;
					if(mInsuredList.contains(insuredInfo)){
						CError.buildErr(this, "模板内出现重复被保人信息,请检查!");
						return false;
					}else{
						mInsuredList.add(insuredInfo);
					}
					tCurrentContData.add(1,tLCInsuredSet);
					tCurrentContData.add(2,tLCInsuredRelatedSet);
					continue;
				}
				
				if (nodeName.equals("LCPOLTABLE")) {
					// 保单信息
					VData tVData = null;
					// 解析一个保单节点
					tVData = tGrpPolParser.parseLCPolNode(contNode); // 获得改险种相关的责任及保费项信息
					//m_GrpPolImpInfo.addContPolData(contId, tVData);
					tCurrentContData.add(3,tVData);
					continue;
				}
				
				if (nodeName.equals("BNFTABLE")) {
					// 受益人解析
					tLCBnfSet = (LCBnfSet) tGrpPolParser.getLCBnfSet(contNode);
					tCurrentContData.add(4,tLCBnfSet);
					continue;
				}

			}
			if (tLCInsuredSet == null) {
				CError.buildErr(this, "被保险人节点解析失败,被保险人不能为空!请确认录入的被保人信息是否正确!");
				return false;
			}
			tCurrentContData.add(5,tLCGrpContSchema);
			tCurrentContData.add(6,this.mGlobalInput);
			if(!isRelation){
				mTaskWaitList.add(tCurrentContData);
			}else{
				mTaskWaitListRela.add(tCurrentContData);
			}



		}
		String tThreadGrade = "S";//Slow
		
		int mainContNum = mTaskWaitList.size();//主被保人队列
		
		
		if(mainContNum>100&&mainContNum<=500){
			 tThreadGrade = "C";//common
		}else if(mainContNum>500){
			tThreadGrade = "F";//fast
		}
		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeDB.setCodeType("DiskThread");
		tLDCodeDB.setCode(tThreadGrade);
		LDCodeSet tLDCodeSet= tLDCodeDB.query();
		if(tLDCodeSet == null ||tLDCodeSet.size()<1){
			CError.buildErr(this, "获得线程启动数失败");
			return false;
		}
		LDCodeSchema tLDCodeSchema = tLDCodeSet.get(1);
		int tThreadNum = Integer.parseInt(tLDCodeSchema.getCodeName());
		
		
		logger.debug("*****启动线程级别为:"+tThreadGrade+",线程数为:"+tThreadNum+"******");
		//开始调用多线程
		ServiceA tService = new ServiceA("com.sinosoft.lis.tbgrp.GrpParseInMthread", mTaskWaitList, tThreadNum, 10);
		tService.start();


		
		int relaContNum = mTaskWaitListRela.size();
		if(relaContNum > 0){
			String rThreadGrade = "S";
			if(relaContNum>100&&relaContNum<=500){
				 rThreadGrade = "C";//common
			}else if(relaContNum>500){
				rThreadGrade = "F";//fast
			}
			LDCodeDB rLDCodeDB = new LDCodeDB();
			rLDCodeDB.setCodeType("DiskThread");
			rLDCodeDB.setCode(rThreadGrade);
			LDCodeSet rLDCodeSet= rLDCodeDB.query();
			if(rLDCodeSet == null ||rLDCodeSet.size()<1){
				CError.buildErr(this, "获得线程启动数失败");
				return false;
			}
			LDCodeSchema rLDCodeSchema = rLDCodeSet.get(1);
			int rThreadNum = Integer.parseInt(rLDCodeSchema.getCodeName());
			
			
			logger.debug("*****启动线程级别为:"+rThreadGrade+",线程数为:"+rThreadNum+"******");
			//开始调用多线程
			ServiceA rService = new ServiceA("com.sinosoft.lis.tbgrp.GrpParseInMthread", mTaskWaitListRela, rThreadNum, 10);
			rService.start();
		}
		
		if(deleteMainCustomer()==false){ }
		
		if((mBQFlag == null || mBQFlag.equals(""))&&(mEdorType == null||mEdorType.equals(""))){
			if(upGrpState()==false){ }
		}
			
		MultThreadErrorDB tMultThreadErrorDB =new MultThreadErrorDB();
		MultThreadErrorSet tMultThreadErrorSet =new MultThreadErrorSet();
		tMultThreadErrorDB.setBatchNo(tBatchNo);
		tMultThreadErrorSet=tMultThreadErrorDB.query();
		if(tMultThreadErrorSet!= null && tMultThreadErrorSet.size()>0){
			for(int i=1;i<=tMultThreadErrorSet.size();i++){
				CError.buildErr(this, tMultThreadErrorSet.get(i).getReason());
			}
			
			MultThreadErrorDBSet tMultThreadErrorDBSet = new MultThreadErrorDBSet();
			tMultThreadErrorDBSet.set(tMultThreadErrorSet);
			if(!tMultThreadErrorDBSet.delete())
			{
				CError.buildErr(this,"删除日志表失败!");
				return false;
			}
		}
		
		if (!tFile.delete()) {
			// @@错误处理
			CError.buildErr(this, "XML文件删除失败");
			return false;
		}
		return true;
	}

	private boolean upGrpState() {
		MMap tmpMap = new MMap();
		boolean de ;
    	String upGrpContSql = "update LCGrpCont a set "
			+ "	a.prem = nvl((select sum(Prem) from LCPol where GrpContNo = a.GrpContNo),0) ,"
			+ " a.peoples = nvl((select count(distinct InsuredNo) from LCPol where GrpContNo = a.GrpContNo),0) ,"
			+ "	a.Amnt = nvl((select sum(Amnt) from LCPol where GrpContNo = a.GrpContNo),0) ,"
			+ "	a.Mult = nvl((select sum(Mult) from LCPol where GrpContNo = a.GrpContNo),0) ,"
			+ "	a.SumPrem = nvl((select sum(SumPrem) from LCPol where GrpContNo = a.GrpContNo),0) "
			+ " where a.GrpContNo = '"+mGrpContNo+"'";
    		tmpMap.put(upGrpContSql, "UPDATE");

    		String upGrpPolSql = "update LCGrpPol a set "
			+ "	a.Prem = nvl((select sum(Prem) from LCPol where GrpPolNo = a.GrpPolNo),0) ,"
			+ " a.Peoples2 = nvl((select count(distinct InsuredNo) from LCPol where GrpPolNo = a.GrpPolNo),0),"
			+ "	a.Amnt = nvl((select sum(Amnt) from LCPol where GrpPolNo = a.GrpPolNo),0) ,"
			+ "	a.Mult = nvl((select sum(Mult) from LCPol where GrpPolNo = a.GrpPolNo),0) ,"
			+ "	a.SumPrem = nvl((select sum(SumPrem) from LCPol where GrpPolNo = a.GrpPolNo),0) "
			+ " where a.GrpContNo = '"+mGrpContNo+"'";
    		tmpMap.put(upGrpPolSql, "UPDATE");
		de = batchSave(tmpMap);
		if(!de){
			CError.buildErr(this, "更新团单信息失败！");
		}
		return true;
	}

	private boolean deleteMainCustomer(){
		MMap tmpMap = new MMap();
		boolean de ;
		String deleteSQL=" delete from lcinsuredrelated a where maincustomerno = customerno "
			            +"  and exists (select 'X' from lcpol"
			            +" where grpcontno = "+mGrpContNo+" and polno = a.polno)";
		tmpMap.put(deleteSQL, "DELETE");
		de = batchSave(tmpMap);
		if(!de){
			CError.buildErr(this, "删除连带被保人表中的住被保人信息失败！");
		}
		return true;
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

}
