package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sinosoft.lis.bq.GEdorDetailUI;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LMDutyDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LMDutySchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LMDutySet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XMLPathTool;


/**
 * <p>Title:GrpEdorGBForLoadBL.java </p>
 * <p>Description: GB导入</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:sinosoft </p>
 * @author
 * @version 1.0
 * @date 2006-07-05
 */
public class GrpEdorAmntForLoadBL implements BusinessService{
private static Logger logger = Logger.getLogger(GrpEdorAmntForLoadBL.class);
    public GrpEdorAmntForLoadBL() {
        bulidDocument();
    }

    //错误处理类
    public CErrors mErrors = new CErrors();
    private VData mInputData = new VData();
    /** 往前面传输数据的容器 */
    private VData mResult = new VData();
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
    private String mGrpContNo= "";
    private String mEdorNo = "";
    private String mEdorType = "";
    private String mEdorAcceptNo="";
    private String mContNo = "";
    
    private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
    
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
               tError.moduleName = "GrpEdorGBForLoadBL";
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
           this.mResult.clear();
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
                   tError.moduleName = "GrpEdorGAForLoadBL";
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
                   tError.moduleName = "GrpEdorGAForLoadBL";
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
           XMLPathTool tXPT = new XMLPathTool(XmlFileName);
           this.mErrors.clearErrors();


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
           List tInsertContNo = new ArrayList();
           for (int i = 0; i < nNodeCount; i++) {
        	   Node node = nodeList.item(i);
        	   try {
        		   node = transformNode(node);
        	   } catch (Exception ex) {
        		   ex.printStackTrace();
        		   node = null;
        	   }
        	   String tInsuredNo = parseNode(node,"InsuredNo");
        	   String tContNo=parseNode(node,"ContNo");
        	   
        	   if(tContNo== null || tContNo.equals("")){
         		  CError.buildErr(this, "请检查是否有未录入的合同号(第"+(i+1)+"行)");
      			  return false;
         	   }
        	   
        	   if(tInsuredNo== null || tInsuredNo.equals("")){
        		  CError.buildErr(this, "请检查是否有未录入的客户号(第"+(i+1)+"行)");
     			  return false;
        	   }
        	   LCContSchema tLCContSchema = new LCContSchema();
        	   LCContSet tLCContSet = new LCContSet();
        	   LCContDB tLCContDB = new LCContDB();
        	   tLCContDB.setContNo(tContNo);
        	   tLCContDB.setInsuredNo(tInsuredNo);
        	   tLCContSet = tLCContDB.query();
        	   if(tLCContSet.size()<1)
        	   {
        			  CError.buildErr(this, "查询合同号为"+tContNo+"客户号为"+tInsuredNo+"的个人合同信息失败!(第"+(i+1)+"行)");
        			  return false;
        	   }
        	   tLCContSchema = tLCContSet.get(1);
        	   mContNo = tLCContSchema.getContNo();
        	   if(!mGrpContNo.equals(tLCContSchema.getGrpContNo()))
        	   {
        		   CError.buildErr(this, "合同号为"+tContNo+"客户号为"+tInsuredNo+"的个人合同不属于该团单!(第"+(i+1)+"行)");
        		   return false;

        	   }
        	   if(!tInsertContNo.contains(mContNo)){
        		   //变更集合里已经不存在变更合同则添加
        		   tInsertContNo.add(mContNo);
        		   VData tVData = creatContData(mContNo,tInsuredNo);
        		   GEdorDetailUI tGEdorDetailUI = new GEdorDetailUI();
        		   if(!tGEdorDetailUI.submitData(tVData, "INSERT||EDOR")){ 
        			   CError.buildErr(this, "增加合同号为"+tContNo+"客户号为"+tInsuredNo+"的个人保全失败!(第"+(i+1)+"行)");
            		   return false;

        		   }
        	   }
        	   //数据提交
        	   String tRiskCode = parseNode(node,"RiskCode");
        	   LCPolDB tLCPolDB = new LCPolDB();
        	   tLCPolDB.setContNo(mContNo);
        	   tLCPolDB.setRiskCode(tRiskCode);
        	   LCPolSet tLCPolSet = tLCPolDB.query();
        	   if(tLCPolSet== null || tLCPolSet.size()<1)
        	   {
        		   //未查到该合同下有该险种
        		   CError.buildErr(this, "查询合同号为"+tContNo+"客户号为"+tInsuredNo+"的个人合同的险种编码为"+tRiskCode+"的信息失败!(第"+(i+1)+"行)");
        		   deleteContData(tContNo,tInsuredNo);
     			   return false;
        	   }

        	   LCPolSchema tLCPolSchema = tLCPolSet.get(1);
        	   LPPolSchema tLPPolSchema = new LPPolSchema();
        	   mRef.transFields(tLPPolSchema, tLCPolSchema);
        	   tLPPolSchema.setEdorNo(mEdorNo);
        	   tLPPolSchema.setEdorType(mEdorType);

        	   LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
        	   tLPEdorItemSchema.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());    
        	   tLPEdorItemSchema.setPolNo(tLCPolSchema.getPolNo());
        	   tLPEdorItemSchema.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
        	   tLPEdorItemSchema.setInsuredNo(tInsuredNo);
        	   tLPEdorItemSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
        	   tLPEdorItemSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
        	   tLPEdorItemSchema.setContNo(mContNo);
        	   tLPEdorItemSchema.setEdorTypeCal(mLPGrpEdorItemSchema.getEdorTypeCal());
        	   
        	   LCDutyDB tLCDutyDB = new LCDutyDB();
        	   tLCDutyDB.setPolNo(tLCPolSchema.getPolNo());
        	   LCDutySet tLCDutySet = tLCDutyDB.query();
        	   if(tLCDutySet== null || tLCDutySet.size()!=1)
        	   {
        		   //多责任的要求手工录入
        		   CError.buildErr(this, "合同号"+tContNo+"个人合同的险种编码为"+tRiskCode+"的责任为多责任,不支持模板导入,请手动在系统录入!(第"+(i+1)+"行)");
        		   deleteContData(tContNo,tInsuredNo);
        		   return false;
        	   }

        	   LMDutyDB tLMDutyDB = new LMDutyDB();
        	   LMDutySet tLMDutySet = new LMDutySet();
        	   tLMDutyDB.setDutyCode(tLCDutySet.get(1).getDutyCode().substring(0,6));
        	   tLMDutySet = tLMDutyDB.query();
        	   if (tLMDutySet.size() < 1) {
        		   CError.buildErr(this, "合同号"+tContNo+"个人合同的险种编码为"+tRiskCode+"的责任信息查询失败!(第"+(i+1)+"行)");
        		   deleteContData(tContNo,tInsuredNo);
        		   return false;
        	   }
        	   LMDutySchema tLMDutySchema = tLMDutySet.get(1);
        	   LCDutySchema tLCDutySchema = tLCDutySet.get(1);
        	   if("3".equals(tLCDutySchema.getCalRule()))
        	   {
        		   //约定费率的需要录入保费,需求手工录入
        		   CError.buildErr(this, "合同号"+tContNo+"个人合同的险种编码为"+tRiskCode+"的责任不支持模板导入,请手动在系统录入!(第"+(i+1)+"行)");
        		   deleteContData(tContNo,tInsuredNo);
            	   return false;
        	   }

        	   if(tLMDutySchema.getCalMode().equals("P"))
        	   {
        		   //保费算保额的需要手工录入
        		   CError.buildErr(this, "合同号"+tContNo+"个人合同的险种编码为"+tRiskCode+"的责任不支持模板导入,请手动在系统录入(第"+(i+1)+"行)");
        		   deleteContData(tContNo,tInsuredNo);
            	   return false;
            	   
        	   }
        	   LPDutySet tLPDutySet = new LPDutySet();
        	   LPDutySchema tLPDutySchema = new LPDutySchema();
        	   mRef.transFields(tLPDutySchema, tLCDutySchema);
        	   tLPDutySchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
        	   tLPDutySchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());

        	   String tAmnt = parseNode(node,"Amnt");
        	   double dAmnt = 0.0;
        	   try {
        		   dAmnt = Double.parseDouble(tAmnt);
        	   } catch (NumberFormatException e) {
	       		 	//录入数字为错误格式
        		   CError.buildErr(this, "请检查合同号"+tContNo+"个人合同的险种编码为"+tRiskCode+"的责任变动后的保额录入是否格式正确!(第"+(i+1)+"行)");
        		   deleteContData(tContNo,tInsuredNo);
            	   return false;
        	   }
        	   
        	   if("AA".equals(mLPGrpEdorItemSchema.getEdorType())) {
        		   //加保
        		   if(dAmnt <= tLPDutySchema.getAmnt())
        		   {
        			   //录入数字为错误格式
            		   CError.buildErr(this, "合同号"+tContNo+"个人合同的险种编码为"+tRiskCode+"的责任变动后的保额"+dAmnt+"应大于实际保额"+tLPDutySchema.getAmnt()+"!(第"+(i+1)+"行)");
            		   deleteContData(tContNo,tInsuredNo);
                	   return false;
        		   }
        		   tLPDutySchema.setAmnt(dAmnt);
        		   tLPDutySchema.setPrem(0);
        		   tLPDutySet.add(tLPDutySchema);
        		   VData tVData = new VData(); 	      
        		   tVData.addElement(mGlobalInput);
        		   tVData.addElement(tLPEdorItemSchema);
        		   tVData.addElement(mLPGrpEdorItemSchema);
        		   tVData.addElement(tLPPolSchema);
        		   tVData.addElement(tLPDutySet);
        		   PGrpEdorAADetailUI tPGrpEdorAADetailUI = new PGrpEdorAADetailUI();
        		   if(!tPGrpEdorAADetailUI.submitData(tVData, "INSERT||MAIN"))
        		   {
        			   CError.buildErr(this, "合同号"+tContNo+"个人合同的险种编码为"+tRiskCode+"的责任变动失败!(第"+(i+1)+"行)");
            		   deleteContData(tContNo,tInsuredNo);
                	   return false;
        		   }
        		   
        	   }else {
        		   //减保
        		   if(dAmnt >= tLPDutySchema.getAmnt() || dAmnt <=0)
        		   {
        			   //录入数字为错误格式
        			   CError.buildErr(this, "合同号"+tContNo+"个人合同的险种编码为"+tRiskCode+"的责任变动后的保额"+dAmnt+"应大于0并小于实际保额"+tLPDutySchema.getAmnt()+"!(第"+(i+1)+"行)");
            		   deleteContData(tContNo,tInsuredNo);
                	   return false;
        		   }
        		   tLPDutySchema.setAmnt(dAmnt);
        		   tLPDutySchema.setPrem(0);
        		   tLPDutySet.add(tLPDutySchema);
        		   VData tVData = new VData(); 	      
        		   tVData.addElement(mGlobalInput);
        		   tVData.addElement(tLPEdorItemSchema);
        		   tVData.addElement(mLPGrpEdorItemSchema);
        		   tVData.addElement(tLPPolSchema);
        		   tVData.addElement(tLPDutySet);
        		   PGrpEdorPTDetailUI tPGrpEdorPTDetailUI = new PGrpEdorPTDetailUI();
        		   if(!tPGrpEdorPTDetailUI.submitData(tVData, "INSERT||MAIN"))
        		   {
        			   CError.buildErr(this, "合同号"+tContNo+"个人合同的险种编码为"+tRiskCode+"的责任变动失败!(第"+(i+1)+"行)");
            		   deleteContData(tContNo,tInsuredNo);
                	   return false;
        		   }
        	   }
        	   
           }
           //－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
           // No.6 解析完删除XML文件
           //－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
           



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

           ConfigFileName = filePath + "GrpEdorAmnt.xml";
           File tFile2 = new File(ConfigFileName);
            if (!tFile2.exists()) {
               // @@错误处理
               CError tError = new CError();
               tError.moduleName = "GrpEdorGAForLoadBL";
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
           ImportFileName = mPreFilePath + FilePath+"BqUp/" + FileName;
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
           mEdorAcceptNo = (String) mTransferData.getValueByName("EdorAcceptNo");
           mEdorNo = (String) mTransferData.getValueByName("EdorNo");
           mEdorType = (String) mTransferData.getValueByName("EdorType");
           //mContNo = (String) mTransferData.getValueByName("ContNo");
           //mPolNo = (String) mTransferData.getValueByName("PolNo");

           if (mGlobalInput == null) {
               CError tError = new CError();
               tError.moduleName = "GrpEdorGBForLoadBL";
               tError.functionName = "getInputData";
               tError.errorMessage = "接受前台传入的GlobalInput数据失败!";
               this.mErrors.addOneError(tError);
               return false;
           }
           if (mTransferData == null) {
               CError tError = new CError();
               tError.moduleName = "GrpEdorGBForLoadBL";
               tError.functionName = "getInputData";
               tError.errorMessage = "接受前台传入的TransferData数据失败!";
               this.mErrors.addOneError(tError);
               return false;
           }
           if (FileName == null) {
               CError tError = new CError();
               tError.moduleName = "GrpEdorGBForLoadBL";
               tError.functionName = "getInputData";
               tError.errorMessage = "接受前台传入的FileName数据失败!";
               this.mErrors.addOneError(tError);
               return false;
           }
           if (mGrpContNo == null) {
               CError tError = new CError();
               tError.moduleName = "GrpEdorGBForLoadBL";
               tError.functionName = "getInputData";
               tError.errorMessage = "接受前台传入的团体保单号数据失败!";
               this.mErrors.addOneError(tError);
               return false;
           }
           if (mEdorNo == null) {
               CError tError = new CError();
               tError.moduleName = "GrpEdorGBForLoadBL";
               tError.functionName = "getInputData";
               tError.errorMessage = "接受前台传入的保全号数据失败!";
               this.mErrors.addOneError(tError);
               return false;
           }
           if (mEdorType == null) {
               CError tError = new CError();
               tError.moduleName = "GrpEdorGBForLoadBL";
               tError.functionName = "getInputData";
               tError.errorMessage = "接受前台传入的保全号数据失败!";
               this.mErrors.addOneError(tError);
               return false;
           }
           
           LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
           tLPGrpEdorItemDB.setEdorAcceptNo(mEdorAcceptNo);
           tLPGrpEdorItemDB.setEdorNo(mEdorNo);
           tLPGrpEdorItemDB.setEdorType(mEdorType);
           tLPGrpEdorItemDB.setGrpContNo(mGrpContNo);
           mLPGrpEdorItemSchema = tLPGrpEdorItemDB.query().get(1);

           return true;
       }

       /**
        * 得到日志显示结果
        * @return VData
        */
       public VData getResult() {
//        mResult.add(mBatchNo);
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
           /**LJSPayPersonForLoadBL tPGI = new LJSPayPersonForLoadBL();
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
       
       private VData creatContData(String iContNo,String iInsuredNo)
       {
		   LPEdorItemSet tLPEdorItemSet   = new LPEdorItemSet();
		   LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
		   tLPEdorItemSchema.setEdorAcceptNo(mEdorAcceptNo);
		   tLPEdorItemSchema.setContNo(iContNo);
		   tLPEdorItemSchema.setEdorNo(mEdorNo);
		   tLPEdorItemSchema.setInsuredNo(iInsuredNo);
		   tLPEdorItemSet.add(tLPEdorItemSchema);
		   TransferData tTransferData = new TransferData();
		   tTransferData.setNameAndValue("EdorNo", mEdorNo);
		   tTransferData.setNameAndValue("EdorType", mEdorType);
		   tTransferData.setNameAndValue("EdorAcceptNo", mEdorAcceptNo);
		   tTransferData.setNameAndValue("ContNo", iContNo);
		   tTransferData.setNameAndValue("GrpContNo", mGrpContNo);
		   tTransferData.setNameAndValue("InsuredNo", iInsuredNo);

		   VData tVData = new VData();
		   tVData.addElement(mGlobalInput);
		   tVData.addElement(tTransferData);
		   tVData.addElement(tLPEdorItemSet);
		   tVData.addElement(mLPGrpEdorItemSchema);
		   return tVData;
       }
       
       private void deleteContData(String iContNo,String iInsuredNo)
       {
    	   VData tVData = creatContData(iContNo,iInsuredNo);
		   GEdorDetailUI tGEdorDetailUI = new GEdorDetailUI();
		   tGEdorDetailUI.submitData(tVData, "DELETE||EDOR");	   
       }

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
}
