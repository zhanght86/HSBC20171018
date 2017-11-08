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

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDBankDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPBnfSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LDBankSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XMLPathTool;


/**
 * <p>Title:GEdorTypeCAForLoadBL.java </p>
 * <p>Description: 分账户给付信息导入</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * @author sunsx 2009-02-03
 * @version 1.0
 */
public class SubAccForLoadBL implements BusinessService{
private static Logger logger = Logger.getLogger(SubAccForLoadBL.class);
    public SubAccForLoadBL() {
        bulidDocument();
    }

    //错误处理类
    public CErrors mErrors = new CErrors();
    private VData mInputData = new VData();
    /** 往前面传输数据的容器 */
    private VData mResult = new VData();
    private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
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
    private String mEdorNo = "";
    private String mEdorType = "";
    private String mEdorAcceptNo="";
    private String mGrpContNo= "";

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
            tError.moduleName = "GrpEdorLBForLoadBL";
            tError.functionName = "checkData";
            tError.errorMessage = "导入文件格式有误!";
            mErrors.addOneError(tError);
        }
        logger.debug("结束时间:" + PubFun.getCurrentTime());
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
                tError.moduleName = "SubAccForLoadBL";
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
                tError.moduleName = "SubAccForLoadBL";
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
     * 解析xml MD变态人提出的变态导入需求!!!!!!!
     * @author sunsx 
     * @return true or false!
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
//            String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
//            mBatchNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
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
        List iContNoList = new ArrayList();
        List iInsuredNoList = new ArrayList();
        LPBnfSet tLPBnfSet = new LPBnfSet();
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
            String tContNo = parseNode(node, "ContNo");//合同号
            String tInsuredNo = parseNode(node, "InsuredNo");//客户号
            
            if(iContNoList.contains(tContNo))
            {
                // @@错误处理
                CError.buildErr(this, "导入的模板中出现重复合同号:"+tContNo);
                return false;
            }
            
            if(iInsuredNoList.contains(tContNo))
            {
                // @@错误处理
                CError.buildErr(this, "导入的模板中出现重复客户号:"+tInsuredNo);
                return false;
            }
            LCContDB tLCContDB = new LCContDB();
            

            //查询转出帐户信息
            tLCContDB.setContNo(tContNo);
            LCContSet tLCContSet =tLCContDB.query();
            if(tLCContSet == null ||tLCContSet.size()<1)
            {
                CError tError = new CError();
                tError.moduleName = "SubAccForLoadBL";
                tError.functionName = "ParseXml";
                tError.errorMessage = "未查询到合同号为"+tContNo+"的个人保单信息！(第"+(i+1)+"行)";
                this.mErrors.addOneError(tError);
                return false;
            }
            LCContSchema tLCContSchema = tLCContSet.get(1);
            if(!tLCContSchema.getInsuredNo().equals(tInsuredNo))
            {
            	  CError.buildErr(this, "未查询到合同号为"+tContNo+"客户号为"+tInsuredNo+"的个人保单信息！(第"+(i+1)+"行)");
                  return false;
            }
            if(!tLCContSchema.getGrpContNo().equals(mLPGrpEdorItemSchema.getGrpContNo()))
            {
            	  CError.buildErr(this, "合同号为"+tContNo+"客户号为"+tInsuredNo+"的个人保单信息不属于本团单！(第"+(i+1)+"行)");
                  return false;
            }
            LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
            tLJSGetEndorseDB.setContNo(tContNo);
            tLJSGetEndorseDB.setOtherNo(mEdorNo);
            tLJSGetEndorseDB.setInsuredNo(tInsuredNo);
            tLJSGetEndorseDB.setGetFlag("1");
            LJSGetEndorseSet tLJSGetEndorseSet = tLJSGetEndorseDB.query();
            if(tLJSGetEndorseSet == null || tLJSGetEndorseSet.size()<1)
            {
            	CError.buildErr(this, "未查询到合同号为"+tContNo+"客户号为"+tInsuredNo+"的补退费信息！(第"+(i+1)+"行)");
            	return false;
            }
            
            iContNoList.add(tContNo);
            iInsuredNoList.add(tInsuredNo);
            
            String tPayMode = parseNode(node, "PayMode");//给付方式 对应BNF的Remark
            String tDrawer = parseNode(node, "Drawer");//领取人姓名,对应BNF的Name
            String tIDNo =  parseNode(node, "IDNo");//领取人的身份证号,对应BNF的IDNo
            String tBankCode = parseNode(node, "BankCode");//领取人的开户银行编码,对应BNF的BankCode
            String tBankAccNo = parseNode(node, "BankAccNo");//领取人的开户银行账户,对应BNF的BankAccNo
            String tAccName = parseNode(node, "AccName");//领取人的开户银行户名,对应BNF的AccName
            if(tPayMode == null || tDrawer == null || tIDNo == null)
            {
            	CError.buildErr(this, "合同号为"+tContNo+"客户号为"+tInsuredNo+"的给付信息录入不完整(第"+(i+1)+"行)");
            	return false;
            }
            if(tPayMode.equals("4")){
            	if(tBankCode == null ||tBankAccNo == null|| tAccName == null){
            		CError.buildErr(this, "合同号为"+tContNo+"客户号为"+tInsuredNo+"的给付方式选择了银行转帐,请录入银行的相关信息(第"+(i+1)+"行)");
            		return false;
            	}
            	LDBankDB tLDBankDB = new LDBankDB();
            	tLDBankDB.setBankCode(tBankCode);
            	LDBankSet tLDBankSet = tLDBankDB.query();
            	if(tLDBankSet == null || tLDBankSet.size() <1)
            	{
            		CError.buildErr(this, "查询合同号为"+tContNo+"客户号为"+tInsuredNo+"的给付银行(编码:"+tBankCode+")信息出错(第"+(i+1)+"行)");
            		return false;
            	}
            }
            
            LPBnfSchema tLPBnfSchema = new LPBnfSchema();
            tLPBnfSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
            tLPBnfSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
            tLPBnfSchema.setContNo(tContNo);
            tLPBnfSchema.setPolNo("000000");
            tLPBnfSchema.setInsuredNo(tInsuredNo);
            tLPBnfSchema.setBnfType("1");
            tLPBnfSchema.setBnfNo(1);
            tLPBnfSchema.setBnfGrade("1");
            tLPBnfSchema.setRemark(tPayMode);//给付方式
            tLPBnfSchema.setName(tDrawer);
            tLPBnfSchema.setIDNo(tIDNo);
            tLPBnfSchema.setBankCode(tBankCode);
            tLPBnfSchema.setBankAccNo(tBankAccNo);
            tLPBnfSchema.setAccName(tAccName);
            tLPBnfSchema.setOperator(mGlobalInput.Operator);
            tLPBnfSchema.setMakeDate(PubFun.getCurrentDate());
            tLPBnfSchema.setMakeTime(PubFun.getCurrentTime());
            tLPBnfSchema.setModifyDate(PubFun.getCurrentDate());
            tLPBnfSchema.setModifyTime(PubFun.getCurrentTime());
            tLPBnfSet.add(tLPBnfSchema);
            if (nodeName.equals("#text")) {
                continue;
            }
            if (nodeName.equals("ROW")) {
            }
            


        }
        MMap tMap = new MMap();
        tMap.put(tLPBnfSet, "DELETE&INSERT");
        VData tVData = new VData();
        tVData.add(tMap);
        //数据提交
        PubSubmit tSubmit = new PubSubmit();
        if (!tSubmit.submitData(tVData, "")) {

        	// @@错误处理
        	this.mErrors.copyAllErrors(tSubmit.mErrors);
        	CError tError = new CError();
        	tError.moduleName   = "SubAccForLoadBL";
        	tError.functionName = "submitData";
        	tError.errorMessage = "数据提交失败!";
        	this.mErrors.addOneError(tError);
        	logger.debug("return false;");
        	return false;
        }
        
//      －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//      No.6 解析完删除XML文件
//      －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

        if (!tFile.delete())
        {
        	
        }
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

        ConfigFileName = filePath + "GrpSubAcc.xml";
        File tFile2 = new File(ConfigFileName);
        if (!tFile2.exists()) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "SubAccForLoadBL";
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
        ImportFileName = mPreFilePath + FilePath+ "BqUp/" + FileName;
        File tFile = new File(ImportFileName);
        if (!tFile.exists()) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "SubAccForLoadBL";
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
            tError.moduleName = "SubAccForLoadBL";
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
        mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData.getObjectByObjectName(
                "LPGrpEdorItemSchema", 0);
        mTransferData = (TransferData) mInputData.getObjectByObjectName(
                "TransferData", 0);
        mPreFilePath = (String) mTransferData.getValueByName("FilePath");
        FileName = (String) mTransferData.getValueByName("FileName");
        mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
        mEdorAcceptNo = (String) mTransferData.getValueByName("EdorAcceptNo");
        mEdorNo = (String) mTransferData.getValueByName("EdorNo");
        mEdorType = (String) mTransferData.getValueByName("EdorType");
        if (mGlobalInput == null) {
            CError tError = new CError();
            tError.moduleName = "SubAccForLoadBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受前台传入的GlobalInput数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (mTransferData == null) {
            CError tError = new CError();
            tError.moduleName = "SubAccForLoadBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受前台传入的TransferData数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (FileName == null) {
            CError tError = new CError();
            tError.moduleName = "SubAccForLoadBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受前台传入的FileName数据失败!";
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
        
    }

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}

}
