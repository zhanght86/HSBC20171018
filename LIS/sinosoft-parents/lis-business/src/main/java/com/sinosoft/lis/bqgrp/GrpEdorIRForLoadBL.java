package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LDOccupationDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorImportLogSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.tb.GrpPolImpInfo;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LDOccupationSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XMLPathTool;

/**
 * <p>Title:GrpEdorIRForLoadBL.java </p>
 * <p>Description: IR导入</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:sinosoft </p>
 * @author
 * @version 1.0
 * @date 2006-07-05
 */
public class GrpEdorIRForLoadBL implements BusinessService{
private static Logger logger = Logger.getLogger(GrpEdorIRForLoadBL.class);
	public GrpEdorIRForLoadBL() {
		bulidDocument();
	}

	//错误处理类
	public CErrors mErrors = new CErrors();
	private VData mInputData = new VData();
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	private GrpPolImpInfo m_GrpPolImpInfo = new GrpPolImpInfo();
	private String FileName; // execl 文件名称
	private String XmlFileName; // xml 文件名称
	private String mPreFilePath; // 文件路径
	private String FilePath = "d:/temp/";
	private String ParsePath = "/DATASET/LLCASETABLE/ROW";
	//配置文件Xml节点描述
	private String ImportFileName;
	private String ConfigFileName;
	private String[] m_strDataFiles = null;
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private org.w3c.dom.Document m_doc = null;
	private Reflections mRef = new Reflections();
	private String mBatchNo = "";
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private String mGrpContNo = "";
	private String mEdorNo = "";
	private String mEdorType = "";
	private String mEdorAcceptNo="";
	private String mContNo = "";
	private String PolNo = "000000";
	private String mOccuType = "";
	private VerifyIdCard mVerifyIdCard = new VerifyIdCard();
	MMap mMap = new MMap();


	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		//得到数据
		if (!getInputData()) {
			return false;
		}
		logger.debug("开始时间:" + PubFun.getCurrentTime());
		try {
			//把Excel转化为Xml
			if (!parseVts()) {
				return false;
			}
			for (int nIndex = 0; nIndex < m_strDataFiles.length; nIndex++) {
				//把Xml转化为Schema
				XmlFileName = m_strDataFiles[nIndex];
				if (!ParseXml()) {
					return false;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpEdorIRForLoadBL";
			tError.functionName = "checkData";
			tError.errorMessage = "导入文件格式有误!";
			mErrors.addOneError(tError);
		}
		logger.debug("结束时间:" + PubFun.getCurrentTime());
		logger.debug("-----------complete---------"+this.mErrors.getErrorCount());
		if (this.mErrors.getErrorCount() > 0)
		{
			return false;
		}
		mResult.clear();
		return true;
	}

	/**
	 * 检验文件是否存在
	 * @return
	 */
	private boolean checkXmlFileName() {
		File tFile = new File(XmlFileName);
		if (!tFile.exists()) {
			LDSysVarDB tLDSysVarDB = new LDSysVarDB();
			tLDSysVarDB.setSysVar("BqXmlPath");
			if (!tLDSysVarDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpEdorLBForLoadBL";
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
				tError.moduleName = "GrpEdorIRForLoadBL";
				tError.functionName = "checkData";
				tError.errorMessage = "请上传相应的数据文件到指定路径" +
				FilePath + "!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return true;
	}

	/**
	 * 解析xml
	 * @return
	 */
	private boolean ParseXml() {
		/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         No.1 检查xml文件存在
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!checkXmlFileName()) {
			return false;
		}
		File tFile = new File(XmlFileName);
		XMLPathTool tXPT = new XMLPathTool(XmlFileName);
		this.mErrors.clearErrors();
		try {
			/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             No.2 批次号
             －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
			mBatchNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         No.3 取得xml文件的第一类字段的值（出险人信息列）
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		NodeList nodeList = tXPT.parseN(ParsePath);
		int nNodeCount = 0;

		if (nodeList != null) {
			nNodeCount = nodeList.getLength();
		}
		/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         No.4 单次提交出险人信息列
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		boolean allInput = true;
		for (int i = 0; i < nNodeCount; i++) {
			Node node = nodeList.item(i);
			try {
				node = transformNode(node);
			} catch (Exception ex) {
				ex.printStackTrace();
				node = null;
			}
			String nodeName = "";
			nodeName = node.getNodeName();
			String tOldCustomerNo = parseNode(node, "OldCustomerNo");
			String tOldCustomerName = parseNode(node, "OldName");
			LCContSchema tLCContSchema = new LCContSchema();
			LCContSet tLCContSet = new LCContSet();
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setGrpContNo(mGrpContNo);
			tLCContDB.setInsuredNo(tOldCustomerNo);
			tLCContDB.setAppFlag("1");
			tLCContSet=tLCContDB.query();
			if(tLCContSet.size()<1)
			{
				CError tError = new CError();
				tError.moduleName = "LJSPayPersonForLoadBL";
				tError.functionName = "ParseXml";
				tError.errorMessage = "未查询到团单号为"+mGrpContNo+"客户号为"+tOldCustomerNo+"的个人有效保单信息！";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLCContSchema=tLCContSet.get(1);
			mContNo=tLCContSchema.getContNo();
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			tLCInsuredDB.setContNo(mContNo);
			tLCInsuredDB.setInsuredNo(tOldCustomerNo);
			LCInsuredSchema tLCInsuredSchema = tLCInsuredDB.query().get(1);
			String tGrpContNo = tLCContSchema.getGrpContNo();
			
			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			tLCGrpContDB.setGrpContNo(tGrpContNo);
			LCGrpContSchema tLCGrpContSchema = tLCGrpContDB.query().get(1);
			
			double tIRRate = tLCGrpContSchema.getEdorTransPercent();
			if(tIRRate <= 0){
				tIRRate = 0.6;
			}
			String tSQL = " select 1  from dual "
						+ " where (select count(*) + 1 "
						+ " from lpinsured "
						+ " where grpcontno = '"+tGrpContNo+"' "
						+ " and edortype = 'IR' ) > "
						+ " (select count(*) * "+tIRRate+" "
						+ " from lcinsured "
						+ " where grpcontno = '"+tGrpContNo+"' "
						+ " and exists (select 1 "
						+ " from LCpol "
						+ "  where appflag = '1' "
						+ "  and GrpContNo = '"+tGrpContNo+"' "
						+ " and ContNo = LCInsured.ContNo "
						+ " and InsuredNo = LCInsured.InsuredNo))";
			ExeSQL tExeSQL = new ExeSQL();
			String tRet = tExeSQL.getOneValue(tSQL);
			if (tRet != null && tRet.equals("1")) {
				CError tError = new CError();
				tError.moduleName = "LJSPayPersonForLoadBL";
				tError.functionName = "ParseXml";
				tError.errorMessage = "团体保单替换人数已经超出规定的替换比例:"+PubFun.round(tIRRate, 2)+",不能继续替换!";
				this.mErrors.addOneError(tError);
				return false;
			}
			
//			tSQL = "select 1  from lcpol a  where a.riskcode in ('211801','221502','211301','221703','221704','221702') and a.appflag = '1' and a.contno = '"+mContNo+"'";
//			tExeSQL = new ExeSQL();
//			tRet = tExeSQL.getOneValue(tSQL);
//			if (tRet != null && tRet.equals("1")) {
//				CError tError = new CError();
//				tError.moduleName = "LJSPayPersonForLoadBL";
//				tError.functionName = "ParseXml";
//				tError.errorMessage = "客户号为"+tOldCustomerNo+"的个人合同下有医疗责任的险种!不能导入替换,请手动界面判断替换!";
//				this.mErrors.addOneError(tError);
//				return false;
//			}
			
			String tSql = "select distinct 1 from llclaimpolicy where contno = '"+mContNo+"' and clmstate in ('10','20','30','35','40','50')";
            tExeSQL = new ExeSQL();
            tRet = tExeSQL.getOneValue(tSql);
			if (tRet != null && tRet.equals("1")) {
				CError tError = new CError();
				tError.moduleName = "LJSPayPersonForLoadBL";
				tError.functionName = "ParseXml";
				tError.errorMessage = "第"+(i+1)+"行被保人保单:"+mContNo+"正在理赔中不能对其进行操作！";
				this.mErrors.addOneError(tError);
				return false;
			}
			
			tSql="select 1 from llclaimdetail where contno= '"+mContNo+"'";
			tExeSQL = new ExeSQL();
			tRet = tExeSQL.getOneValue(tSql);
			if (tRet != null && tRet.equals("1")) {
				CError tError = new CError();
				tError.moduleName = "LJSPayPersonForLoadBL";
				tError.functionName = "ParseXml";
				tError.errorMessage = "第"+(i+1)+"行被保人保单:"+mContNo+"有理赔记录,不能对其进行减人操作";
				this.mErrors.addOneError(tError);
				return false;
			}
			String oldOccuType = tLCInsuredSchema.getOccupationType();
			if(oldOccuType == null || "".equals(oldOccuType))
			{
				oldOccuType = "4";
			}
			String oldName = tLCInsuredSchema.getName();
			if(!oldName.equals(tOldCustomerName)){
				//蠢人要求加的蠢校验,脱裤子放屁的校验
				CError tError = new CError();
				tError.moduleName = "LJSPayPersonForLoadBL";
				tError.functionName = "ParseXml";
				tError.errorMessage = "第"+(i+1)+"行客户号为"+tOldCustomerNo+"的原姓名信息不正确！";
				this.mErrors.addOneError(tError);
				return false;
				
			}
			//保全校验
			TransferData  mTransferData =new TransferData();
			mTransferData.setNameAndValue("EdorNo",mEdorNo);
			mTransferData.setNameAndValue("ContNo",mContNo);
			mTransferData.setNameAndValue("Action","2");
			mTransferData.setNameAndValue("EdorType",mEdorType);
			logger.debug(mTransferData.getValueByName("EdorType"));

			LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
			LPInsuredSchema mLPInsuredSchema = new LPInsuredSchema();
			LPContSchema mLPContSchema = new LPContSchema();
			LPPersonSchema mLPPersonSchema = new LPPersonSchema();
			LPAddressSchema mLPAddressSchema = new LPAddressSchema();
			if (nodeName.equals("#text")) {
				continue;
			}
			if (nodeName.equals("ROW")) {
				mLPEdorItemSchema = this.getLPEdorItem(node);
				logger.debug("LPEdorItem");
				mLPInsuredSchema = this.getLPInsured(node);
				logger.debug("LPInsured");
				mLPContSchema = this.getLPCont(node);
				logger.debug("LPCont");
				mLPPersonSchema = this.getLPPerson(node);
				logger.debug("LPPerson");
				mLPAddressSchema = this.getLPAddress(node);
				logger.debug("LPAddress");
			}
			if (mLPEdorItemSchema == null) {
				CError tError = new CError();
				tError.moduleName = "GrpEdorIRForLoadBL";
				tError.functionName = "parserXml";
				tError.errorMessage = "获得导入的文件信息失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			
			String newOccType = mLPInsuredSchema.getOccupationType();
			String newOccCode = mLPInsuredSchema.getOccupationCode();
			String tName = mLPInsuredSchema.getName();
			String tSex = mLPInsuredSchema.getSex();
			String tBirthday = mLPInsuredSchema.getBirthday();
			String tIDNo = mLPInsuredSchema.getIDNo();
			String tIDType = mLPInsuredSchema.getIDType();
			
			if(tName==null||tName.trim().equals("")||newOccType==null||newOccType.trim().equals("")||newOccCode==null||newOccCode.trim().equals("")||tSex==null||tSex.trim().equals("")||tBirthday==null||tBirthday.trim().equals(""))
			{
				CError tError = new CError();
				tError.moduleName = "GrpEdorIRForLoadBL";
				tError.functionName = "parserXml";
				tError.errorMessage = "第"+(i+1)+"行被替换的被保人(客户号:"+tOldCustomerNo+")的换入人员信息录入不完整";
				this.mErrors.addOneError(tError);
				return false;
			}
			
			if("0".equals(tIDType)){
				boolean ret = true;
				try {
					ret = mVerifyIdCard.verify(tIDNo);
				} catch (Exception e) {
					CError.buildErr(this, "第"+(i+1)+"行被替换的被保人(客户号:"+tOldCustomerNo+")的换入人员身份证录入不合法,请仔细检查");
					return false;
				}
				if(!ret){
					CError.buildErr(this, "第"+(i+1)+"行被替换的被保人(客户号:"+tOldCustomerNo+")的换入人员身份证录入不合法,请仔细检查");
					return false;
				}
				try {

					ret = mVerifyIdCard.chkIdNo(tIDNo, tBirthday, tSex);

				} catch (Exception e) {

					CError.buildErr(this, e.getMessage());
					return false;
				}
				if(!ret){
					CError.buildErr(this, "第"+(i+1)+"行被替换的被保人(客户号:"+tOldCustomerNo+")的换入人员身份证录入不合法,请仔细检查");
					return false;
				}
			}
			
			LDOccupationDB tLDOccupationDB = new LDOccupationDB();
			tLDOccupationDB.setOccupationCode(newOccCode);
			tLDOccupationDB.setOccupationType(newOccType);
			LDOccupationSet tLDOccupationSet = tLDOccupationDB.query();
			if(tLDOccupationSet == null ||tLDOccupationSet.size()<1)
			{
				CError tError = new CError();
				tError.moduleName = "GrpEdorIRForLoadBL";
				tError.functionName = "parserXml";
				tError.errorMessage = "第"+(i+1)+"行被替换的被保人(客户号:"+tOldCustomerNo+")的换入人员职业类别信息不匹配!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if(Integer.parseInt(newOccType)>Integer.parseInt(oldOccuType))
			{
				CError tError = new CError();
				tError.moduleName = "GrpEdorIRForLoadBL";
				tError.functionName = "parserXml";
				tError.errorMessage = "第"+(i+1)+"行换入被保人职业类别高于被替换的被保人(客户号:"+tOldCustomerNo+")!";
				this.mErrors.addOneError(tError);
				return false;
			}
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("ContNo", mContNo);
			tTransferData.setNameAndValue("CustomerNo", tOldCustomerNo);
			tTransferData.setNameAndValue("CustomerNoBak", tOldCustomerNo);
			mInputData.clear();
			mInputData.add(mGlobalInput);
			mInputData.add(mLPContSchema);
			mInputData.add(mLPInsuredSchema);
			mInputData.add(mLPAddressSchema);
			mInputData.add(tTransferData);
			mInputData.add(mLPPersonSchema);
			mInputData.add(mLPEdorItemSchema);

			String tErrorMsg = "导入成功！";
			PEdorIRDetailUI tPEdorIRDetailUI = new PEdorIRDetailUI();
			LPGrpEdorImportLogSchema tLPGrpEdorImportLogSchema = null;
			logger.debug("---------进入tPEdorIRDetailUI！----");
			if (!tPEdorIRDetailUI.submitData(mInputData, "REPLACE||CONTINSURED")) {
				this.mErrors.copyAllErrors(tPEdorIRDetailUI.mErrors);
				CError tError = new CError();
				tError.moduleName = "PEdorRTDetailUI";
				tError.functionName = "parserXml";
				tError.errorMessage = "数据提交失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (mErrors != null && mErrors.getErrorCount() > 0){

				//输出错误到日志表
				tErrorMsg = mErrors.getFirstError();
				mErrors.clearErrors();
			}

			//输出日志信息到LPGrpEdorImportLog表中
			tLPGrpEdorImportLogSchema = new LPGrpEdorImportLogSchema();
			tLPGrpEdorImportLogSchema.setMakeDate(CurrentDate);
			tLPGrpEdorImportLogSchema.setMakeTime(CurrentTime);
			tLPGrpEdorImportLogSchema.setIDNo(String.valueOf(i));
			tLPGrpEdorImportLogSchema.setEdorNo(mEdorNo);
			tLPGrpEdorImportLogSchema.setEdorType(mEdorType);
			tLPGrpEdorImportLogSchema.setGrpContNo(mGrpContNo);
			tLPGrpEdorImportLogSchema.setContNo(mContNo);
			tLPGrpEdorImportLogSchema.setPolNo(tOldCustomerNo);
			tLPGrpEdorImportLogSchema.setInsuAccNo(mLPInsuredSchema.getName());
			tLPGrpEdorImportLogSchema.setReturnMSG(tErrorMsg);
			mMap.put(tLPGrpEdorImportLogSchema, "DELETE&INSERT");
			mResult = new VData();
			mResult.add(mMap);

			//数据提交
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(mResult, "")) {

				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName   = "PEdorPTDetailBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				logger.debug("return false;");
				return false;
			}
		}
		/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         No.6 解析完删除XML文件
               －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		/*if (!tFile.delete())
                 {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LJSPayPersonForLoadBL";
            tError.functionName = "checkData";
            tError.errorMessage = "Xml文件删除失败!";
            this.mErrors.addOneError(tError);
            return false;
                 }*/
//		logger.debug("===================================================="+allInput);
//		if (allInput)
//		{
//		this.mErrors.addOneError("保全换人导入成功！");
//		}
		return true;
	}

	/**
	 * Detach node from original document, fast XPathAPI process.
	 * @param node
	 * @return
	 * @throws Exception
	 */
	private org.w3c.dom.Node transformNode(org.w3c.dom.Node node) throws
	Exception {
		Node nodeNew = m_doc.importNode(node, true);
		return nodeNew;
	}

	/**
	 * 解析excel并转换成xml文件,用完后删除xml文件
	 * @return
	 */
	private boolean parseVts() throws Exception {
		logger.debug("--------excel--导入-xml---开始----------");
		/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
          No.1 初始化导入文件
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!this.initImportFile()) {
			return false;
		}
		/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         No.2 检查导入配置文件是否存在
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!this.checkImportConfig()) {
			return false;
		}
		/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         No.3 指定导入文件名称
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LCTempCustomerVTSParser GCvp = new LCTempCustomerVTSParser();
		if (!GCvp.setFileName(ImportFileName)) {
			mErrors.copyAllErrors(GCvp.mErrors);
			return false;
		}
		/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         No.4 指定配置文件名称
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!GCvp.setConfigFileName(ConfigFileName)) {
			mErrors.copyAllErrors(GCvp.mErrors);
			return false;
		}
		/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         No.5 转换excel到xml
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!GCvp.transform()) {
			mErrors.copyAllErrors(GCvp.mErrors);
			return false;
		}
		/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         No.5 得到生成的XML文件名列表
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		m_strDataFiles = GCvp.getDataFiles();
		logger.debug("-------excel--导入-xml----结束----------");
		return true;
	}

	/**
	 * 检查导入配置文件是否存在
	 * @return
	 */
	private boolean checkImportConfig() {
		this.getFilePath();

		String filePath = mPreFilePath + FilePath;
		File tFile1 = new File(filePath);
		if (!tFile1.exists()) {
			//初始化创建目录
			tFile1.mkdirs();
		}

		ConfigFileName = filePath + "IREdor.xml";
		File tFile2 = new File(ConfigFileName);
		if (!tFile2.exists()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJSPayPersonForLoadBL";
			tError.functionName = "checkImportConfig";
			tError.errorMessage = "请上传配置文件到指定路径" +
			FilePath + "!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 初始化上传文件
	 * @return
	 */
	private boolean initImportFile() {
		this.getFilePath();
		ImportFileName = mPreFilePath + FilePath + "BqUp/"+ FileName;
		File tFile = new File(ImportFileName);
		if (!tFile.exists()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJSPayPersonForLoadBL";
			tError.functionName = "checkImportConfig";
			tError.errorMessage = "未上传文件到指定路径" +
			FilePath + "!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 得到生成文件路径
	 * @return
	 */
	private boolean getFilePath() {
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("BqXmlPath");
		if (!tLDSysVarDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJSPayPersonForLoadBL";
			tError.functionName = "checkData";
			tError.errorMessage = "缺少文件导入路径!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			FilePath = tLDSysVarDB.getSysVarValue();
		}
		logger.debug("FilePath：" + FilePath);
		return true;
	}

	/**
	 * getInputData
	 */
	private boolean getInputData() {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput",
				0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mPreFilePath = (String) mTransferData.getValueByName("FilePath");
		FileName = (String) mTransferData.getValueByName("FileName");
		mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
		mEdorNo = (String) mTransferData.getValueByName("EdorNo");
		mEdorType = (String) mTransferData.getValueByName("EdorType");
		mEdorAcceptNo = (String) mTransferData.getValueByName("EdorAcceptNo");
		if (mGlobalInput == null) {
			CError tError = new CError();
			tError.moduleName = "GrpEdorIRForLoadBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接受前台传入的GlobalInput数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mTransferData == null) {
			CError tError = new CError();
			tError.moduleName = "GrpEdorIRForLoadBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接受前台传入的TransferData数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (FileName == null) {
			CError tError = new CError();
			tError.moduleName = "GrpEdorIRForLoadBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接受前台传入的FileName数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mGrpContNo == null) {
			CError tError = new CError();
			tError.moduleName = "GrpEdorIRForLoadBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接受前台传入的团体保单号数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mEdorNo == null) {
			CError tError = new CError();
			tError.moduleName = "GrpEdorIRForLoadBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接受前台传入的保全号数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mEdorType == null) {
			CError tError = new CError();
			tError.moduleName = "GrpEdorIRForLoadBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接受前台传入的保全号数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mEdorAcceptNo == null) {
			CError tError = new CError();
			tError.moduleName = "GrpEdorIRForLoadBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接受前台传入的保全号数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 得到日志显示结果
	 * @return VData
	 */
	public VData getResult() {
//		mResult.add(mBatchNo);
		return mResult;

	}

	/**
	 * 利用XPathAPI取得某个节点的节点值
	 * @param node
	 * @param strPath
	 * @return
	 */
	private static String parseNode(Node node,
			String strPath) {
		String strValue = "";
		try {
			XObject xobj = XPathAPI.eval(node, strPath);
			strValue = xobj == null ? "" : xobj.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return strValue;
	}

	/**===============================================================================================
	 * 从XML中解析信息
        ==============================================================================================*/
	private LPEdorItemSchema getLPEdorItem(Node node) {
		// String tContNo = parseNode(node, "ContNo");
		String tOldCustomerNo = parseNode(node, "OldCustomerNo");

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorAcceptNo(mEdorAcceptNo);
		tLPEdorItemSchema.setEdorNo(mEdorNo);
		tLPEdorItemSchema.setEdorType(mEdorType);
		tLPEdorItemSchema.setContNo(mContNo);
		tLPEdorItemSchema.setInsuredNo(tOldCustomerNo);
		tLPEdorItemSchema.setPolNo(PolNo);
		tLPEdorItemSchema.setOperator(mGlobalInput.ManageCom);
		tLPEdorItemSchema.setManageCom(mGlobalInput.Operator);
		tLPEdorItemSchema.setMakeDate(PubFun.getCurrentDate());
		tLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		tLPEdorItemSchema.setMakeTime(PubFun.getCurrentTime());
		tLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		tLPEdorItemSchema.setEdorState("3");

		return tLPEdorItemSchema;
	}

	private LPInsuredSchema getLPInsured(Node node) {
		String tOldCustomerNo = parseNode(node, "OldCustomerNo");
		String tName = parseNode(node, "Name");
		String tSex = parseNode(node, "Sex");
		String tBirthday = parseNode(node, "Birthday");
		String tIDType = parseNode(node, "IDType");
		String tIDNo = parseNode(node, "IDNo");
		if (tIDType.equals("0") && tIDNo.length()==18)
		{
			tIDNo=tIDNo.toUpperCase();
			logger.debug("tIDNo:"+tIDNo);
		}

		String tMarriage = parseNode(node, "Marriage");
		String tStature = parseNode(node,"Stature");
		String tAvoirdupois = parseNode(node,"Avoirdupois");
		String tDegree = parseNode(node,"Degree");
		String tPosition = parseNode(node, "Position");
		String tOccupationType = parseNode(node, "OccupationType");
		String tOccupationCode = parseNode(node,"OccupationCode");
		String tWorkType = parseNode(node,"WorkType");
		String tPluralityType = parseNode(node,"PluralityType");
		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(mContNo);
		tLCInsuredDB.setInsuredNo(tOldCustomerNo);
		LCInsuredSet tLCInsuredSet = tLCInsuredDB.query();
		if (tLCInsuredSet.size() > 0) {
			mRef.transFields(tLPInsuredSchema, tLCInsuredSet.get(1));
		}
		tLPInsuredSchema.setInsuredNo(tOldCustomerNo);
		tLPInsuredSchema.setEdorNo(mEdorNo);
		tLPInsuredSchema.setEdorType(mEdorType);
		tLPInsuredSchema.setContNo(mContNo);
		tLPInsuredSchema.setName(tName);
		tLPInsuredSchema.setSex(tSex);
		tLPInsuredSchema.setBirthday(tBirthday);
		tLPInsuredSchema.setIDType(tIDType);
		tLPInsuredSchema.setIDNo(tIDNo);
		tLPInsuredSchema.setMarriage(tMarriage);
		tLPInsuredSchema.setStature(tStature);
		tLPInsuredSchema.setAvoirdupois(tAvoirdupois);
		tLPInsuredSchema.setDegree(tDegree);
		tLPInsuredSchema.setPosition(tPosition);
		tLPInsuredSchema.setOccupationType(tOccupationType);
		tLPInsuredSchema.setOccupationCode(tOccupationCode);
		tLPInsuredSchema.setWorkType(tWorkType);
		tLPInsuredSchema.setPluralityType(tPluralityType);
		tLPInsuredSchema.setOperator(mGlobalInput.Operator);
		tLPInsuredSchema.setMakeDate(PubFun.getCurrentDate());
		tLPInsuredSchema.setMakeTime(PubFun.getCurrentTime());
		tLPInsuredSchema.setModifyDate(PubFun.getCurrentDate());
		tLPInsuredSchema.setModifyTime(PubFun.getCurrentTime());
		return tLPInsuredSchema;
	}

	private LPContSchema getLPCont(Node node) {
		String tOldCustomerNo = parseNode(node, "OldCustomerNo");
		String tName = parseNode(node, "Name");
		String tSex = parseNode(node, "Sex");
		String tBirthday = parseNode(node, "Birthday");
		String tIDType = parseNode(node, "IDType");
		String tIDNo = parseNode(node, "IDNo");
		if (tIDType.equals("0") && tIDNo.length()==18)
		{
			tIDNo=tIDNo.toUpperCase();
			logger.debug("tIDNo:"+tIDNo);
		}
		LPContSchema tLPContSchema = new LPContSchema();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		LCContSet tLCContSet = tLCContDB.query();
		if (tLCContSet.size() > 0) {
			mRef.transFields(tLPContSchema, tLCContSet.get(1));
		}
		tLPContSchema.setEdorNo(mEdorNo);
		tLPContSchema.setEdorType(mEdorType);
		tLPContSchema.setContNo(mContNo);
		tLPContSchema.setManageCom(mGlobalInput.ManageCom);
		tLPContSchema.setInsuredNo(tOldCustomerNo);
		tLPContSchema.setInsuredName(tName);
		tLPContSchema.setInsuredSex(tSex);
		tLPContSchema.setInsuredBirthday(tBirthday);
		tLPContSchema.setInsuredIDType(tIDType);
		tLPContSchema.setInsuredIDNo(tIDNo);
		tLPContSchema.setMakeDate(PubFun.getCurrentDate());
		tLPContSchema.setModifyDate(PubFun.getCurrentDate());
		tLPContSchema.setMakeTime(PubFun.getCurrentTime());
		tLPContSchema.setModifyTime(PubFun.getCurrentTime());
		return tLPContSchema;
	}
	private LPPersonSchema getLPPerson(Node node) {
		// String tContNo = parseNode(node, "ContNo");
		String tOldCustomerNo = parseNode(node, "OldCustomerNo");
		String tName = parseNode(node, "Name");
		String tSex = parseNode(node, "Sex");
		String tBirthday = parseNode(node, "Birthday");
		String tIDType = parseNode(node, "IDType");
		String tIDNo = parseNode(node, "IDNo");
		if (tIDType.equals("0") && tIDNo.length()==18)
		{
			tIDNo=tIDNo.toUpperCase();
			logger.debug("tIDNo:"+tIDNo);
		}
		String tMarriage = parseNode(node, "Marriage");
		String tStature = parseNode(node,"Stature");
		String tAvoirdupois = parseNode(node,"Avoirdupois");
		String tDegree = parseNode(node,"Degree");
		String tPosition = parseNode(node, "Position");
		String tOccupationType = parseNode(node, "OccupationType");
		String tOccupationCode = parseNode(node,"OccupationCode");
		String tWorkType = parseNode(node,"WorkType");
		String tPluralityType = parseNode(node,"PluralityType");
		LPPersonSchema tLPPersonSchema = new LPPersonSchema();
		tLPPersonSchema.setEdorNo(mEdorNo);
		tLPPersonSchema.setEdorType(mEdorType);
		tLPPersonSchema.setCustomerNo(tOldCustomerNo);
		tLPPersonSchema.setName(tName);
		tLPPersonSchema.setSex(tSex);
		tLPPersonSchema.setBirthday(tBirthday);
		tLPPersonSchema.setIDType(tIDType);
		tLPPersonSchema.setIDNo(tIDNo);
		tLPPersonSchema.setMarriage(tMarriage);
		tLPPersonSchema.setStature(tStature);
		tLPPersonSchema.setAvoirdupois(tAvoirdupois);
		tLPPersonSchema.setDegree(tDegree);
		tLPPersonSchema.setPosition(tPosition);
		tLPPersonSchema.setWorkType(tWorkType);
		tLPPersonSchema.setOccupationType(tOccupationType);
		tLPPersonSchema.setOccupationCode(tOccupationCode);
		tLPPersonSchema.setPluralityType(tPluralityType);
		tLPPersonSchema.setOperator(mGlobalInput.Operator);
		tLPPersonSchema.setMakeDate(PubFun.getCurrentDate());
		tLPPersonSchema.setMakeTime(PubFun.getCurrentTime());
		tLPPersonSchema.setModifyDate(PubFun.getCurrentDate());
		tLPPersonSchema.setModifyTime(PubFun.getCurrentTime());

		return tLPPersonSchema;
	}

	private LPAddressSchema getLPAddress(Node node) {

		String tOldCustomerNo = parseNode(node, "OldCustomerNo");
		String tPostalAddress = parseNode(node, "PostalAddress");
		String tZipCode = parseNode(node, "ZipCode");
		String tInsuredCompanyPhone = parseNode(node, "InsuredCompanyPhone");
		String tInsuredHomePhone = parseNode(node, "InsuredHomePhone");
		String tMobile = parseNode(node, "Mobile");
		String tGrpAddress = parseNode(node, "GrpAddress");
		String tEMail = parseNode(node,"EMail");
		String tAvoirdupois = parseNode(node,"Avoirdupois");
		String tDegree = parseNode(node,"Degree");
		String tPosition = parseNode(node, "Position");
		String tOccupationType = parseNode(node, "OccupationType");
		String tOccupationCode = parseNode(node,"OccupationCode");
		String tWorkType = parseNode(node,"WorkType");
		String tPluralityType = parseNode(node,"PluralityType");

		SSRS tSSRS = new SSRS();
		String sql = "Select Case When max(AddressNo) Is Null Then 0 Else max(AddressNo) End from LCAddress where CustomerNo='"
			+ tOldCustomerNo + "'";
		ExeSQL tExeSQL = new ExeSQL();
		logger.debug("1");
		tSSRS = tExeSQL.execSQL(sql);
		logger.debug("2");
		//Double firstinteger = Double.valueOf(tSSRS.GetText(1, 1));
		logger.debug("3");
		//Double ttNo = firstinteger.doubleValue() + new Double(1).doubleValue();
		//Int ttNo = firstinteger.doubleValue();
		//logger.debug("tNo:"+ttNo);
		//Int integer = new Int(ttNo);
		int tNo = Integer.parseInt(tSSRS.GetText(1, 1));
		logger.debug("tNo:"+tNo);
		LPAddressSchema tLPAddressSchema = new LPAddressSchema();
		tLPAddressSchema.setEdorNo(mEdorNo);
		tLPAddressSchema.setEdorType(mEdorType);
		logger.debug("tNo0:"+tNo);
		tLPAddressSchema.setCustomerNo(tOldCustomerNo);
		tLPAddressSchema.setAddressNo(tNo);
		tLPAddressSchema.setPostalAddress(tPostalAddress);
		tLPAddressSchema.setZipCode(tZipCode);
		logger.debug("tNo0:"+tNo);
		tLPAddressSchema.setCompanyPhone(tInsuredCompanyPhone);
		tLPAddressSchema.setHomePhone(tInsuredHomePhone);
		tLPAddressSchema.setMobile(tMobile);
		tLPAddressSchema.setCompanyAddress(tGrpAddress);
		logger.debug("tNo0:"+tNo);
		tLPAddressSchema.setEMail(tEMail);
		logger.debug("tNo1:"+tNo);
		tLPAddressSchema.setOperator(mGlobalInput.Operator);
		logger.debug("tNo2:"+tNo);
		tLPAddressSchema.setMakeDate(PubFun.getCurrentDate());
		logger.debug("tNo3:"+tNo);
		tLPAddressSchema.setMakeTime(PubFun.getCurrentTime());
		tLPAddressSchema.setModifyDate(PubFun.getCurrentDate());
		tLPAddressSchema.setModifyTime(PubFun.getCurrentTime());

		return tLPAddressSchema;
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

	public static void main(String[] args) {
		/*
        LJSPayPersonForLoadBL tPGI = new LJSPayPersonForLoadBL();
        VData tVData = new VData();
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("FileName", "20060606.xls");
        tTransferData.setNameAndValue("FilePath", "d:/lis/ui");
        tTransferData.setNameAndValue("GrpContNo", "280101000618");
        GlobalInput tG = new GlobalInput();
        tG.Operator = "claim";
        tG.ManageCom = "86";
        tG.ComCode = "0101";
        long a = System.currentTimeMillis();
        logger.debug(a);
        tVData.add(tTransferData);
        tVData.add(tG);
        if (!tPGI.submitData(tVData, "")) {
            logger.debug("提交失败！！！！");
        }
        logger.debug(System.currentTimeMillis() - a);
		 */
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}

}
