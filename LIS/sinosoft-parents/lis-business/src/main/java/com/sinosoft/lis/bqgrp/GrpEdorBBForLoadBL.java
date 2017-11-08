package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.operfee.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XObject;
import java.io.File;
import com.sinosoft.lis.vschema.*;


/**
 * <p>Title:GrpEdorBBForLoadBL.java </p>
 * <p>Description: BB导入</p>
 * <p>Copyright: Copyright (c) 2005</p>
 */
public class GrpEdorBBForLoadBL {
private static Logger logger = Logger.getLogger(GrpEdorBBForLoadBL.class);
    public GrpEdorBBForLoadBL() {
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
    private String mBatchNo = "";
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private String mGrpContNo = "";
    private String mEdorNo = "";
    private String mContNo = "";
    private String mEdorValiDate="";


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
            tLDSysVarDB.setSysVar("XmlPath");
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
                tError.moduleName = "GrpEdorBBForLoadBL";
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
            String tInsuredNo = parseNode(node, "InsuredNo");
            LCContSchema tLCContSchema = new LCContSchema();
            LCContSet tLCContSet = new LCContSet();
            LCContDB tLCContDB = new LCContDB();
            tLCContDB.setGrpContNo(mGrpContNo);
            tLCContDB.setInsuredNo(tInsuredNo);
            tLCContSet=tLCContDB.query();
            if(tLCContSet.size()<1)
            {
                CError tError = new CError();
                tError.moduleName = "LJSPayPersonForLoadBL";
                tError.functionName = "ParseXml";
                tError.errorMessage = "未查询到团单号为"+mGrpContNo+"客户号为"+tInsuredNo+"的个人保单信息！(第"+(i+1)+"行)";
                this.mErrors.addOneError(tError);
                return false;
            }
            tLCContSchema=tLCContSet.get(1);
            mContNo=tLCContSchema.getContNo();
            LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
            LPInsuredSchema mLPInsuredSchema = new LPInsuredSchema();
            LPContSchema mLPContSchema = new LPContSchema();
            LPAddressSchema mLPAddressSchema = new LPAddressSchema();
            if (nodeName.equals("#text")) {
                continue;
            }
            if (nodeName.equals("ROW")) {
                mLPEdorItemSchema = this.getLPEdorItem(node);
                mLPInsuredSchema = this.getLPInsured(node);
                mLPContSchema = this.getLPCont(node);
                mLPAddressSchema = this.getLPAddress(node);
            }
            if (mLPEdorItemSchema == null) {
               return false;
            }
            if (mLPInsuredSchema == null) {
               return false;
            }

            mInputData.clear();
            mInputData.add(mGlobalInput);
            mInputData.add(mLPEdorItemSchema);
            mInputData.add(mLPInsuredSchema);
            mInputData.add(mLPContSchema);
            mInputData.add(mLPAddressSchema);
            PEdorBBDetailUI tPEdorBBDetailUI = new PEdorBBDetailUI();
            if (!tPEdorBBDetailUI.submitData(mInputData, "LOAD")) {
                CError tError = new CError();
                tError.moduleName = "PEdorBBDetailUI";
                tError.functionName = "parserXml";
                tError.errorMessage = "数据提交失败！";
                this.mErrors.addOneError(tError);
                this.mErrors.copyAllErrors(tPEdorBBDetailUI.mErrors);
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

        ConfigFileName = filePath + "GrpEdorBB.xml";
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
        mEdorValiDate = (String) mTransferData.getValueByName("EdorValiDate");
        if (mGlobalInput == null) {
            CError tError = new CError();
            tError.moduleName = "GrpEdorBBForLoadBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受前台传入的GlobalInput数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (mTransferData == null) {
            CError tError = new CError();
            tError.moduleName = "GrpEdorBBForLoadBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受前台传入的TransferData数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (FileName == null) {
            CError tError = new CError();
            tError.moduleName = "GrpEdorBBForLoadBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受前台传入的FileName数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (mGrpContNo == null) {
            CError tError = new CError();
            tError.moduleName = "GrpEdorBBForLoadBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受前台传入的团体保单号数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (mEdorNo == null) {
            CError tError = new CError();
            tError.moduleName = "GrpEdorBBForLoadBL";
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

    /**===============================================================================================
     * 从XML中解析信息
        ==============================================================================================*/
    private LPEdorItemSchema getLPEdorItem(Node node) {
       // String tContNo = parseNode(node, "ContNo");
        String tInsuredNo = parseNode(node, "InsuredNo");


        LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
        tLPEdorItemSchema.setEdorAcceptNo(mEdorNo);
        tLPEdorItemSchema.setEdorNo(mEdorNo);
        tLPEdorItemSchema.setEdorType("BB");
        tLPEdorItemSchema.setGrpContNo(mGrpContNo);
        tLPEdorItemSchema.setContNo(mContNo);
        tLPEdorItemSchema.setInsuredNo(tInsuredNo);
        tLPEdorItemSchema.setPolNo("000000");
        tLPEdorItemSchema.setEdorValiDate(mEdorValiDate);
        return tLPEdorItemSchema;
    }

    private LPInsuredSchema getLPInsured(Node node) {
       // String tContNo = parseNode(node, "ContNo");
        String tInsuredNo = parseNode(node, "InsuredNo");
        String tInsuredName = parseNode(node, "InsuredName");
        String tSalary = parseNode(node, "Salary");
        String tWorkNo = parseNode(node, "WorkNo");
        String tJoinCompanyDate = parseNode(node,"JoinCompanyDate");
        String tBankCode=parseNode(node,"BankCode");
        String tBankAccNo=parseNode(node,"BankAccNo");
        LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
        LCInsuredDB tLCInsuredDB = new LCInsuredDB();
        tLCInsuredDB.setContNo(mContNo);
        tLCInsuredDB.setInsuredNo(tInsuredNo);
        LCInsuredSet tLCInsuredSet = tLCInsuredDB.query();
        if (tLCInsuredSet.size() > 0) {
            mRef.transFields(tLPInsuredSchema, tLCInsuredSet.get(1));
        }

        tLPInsuredSchema.setEdorNo(mEdorNo);
        tLPInsuredSchema.setEdorType("BB");
        if (tInsuredName != null && !tInsuredName.equals("")) {
            tLPInsuredSchema.setName(tInsuredName);
        }
        if (tSalary != null && !tSalary.equals("")) {
        	tLPInsuredSchema.setSalary(tSalary);
        }
	if (tWorkNo != null && !tWorkNo.equals("")) {
		tLPInsuredSchema.setWorkNo(tWorkNo);
	}
	if (tBankAccNo != null && !tBankAccNo.equals("")) {
		tLPInsuredSchema.setBankAccNo(tBankAccNo);
	}
	if (tJoinCompanyDate != null && !tJoinCompanyDate.equals("")) {
		tLPInsuredSchema.setJoinCompanyDate(tJoinCompanyDate);
	}
	if (tBankCode != null && !tBankCode.equals("")) {
		LDBankDB tLDBankDB = new LDBankDB();
		tLDBankDB.setBankCode(tBankCode);
		LDBankSet tLDBankSet = tLDBankDB.query();
		if(tLDBankSet==null ||tLDBankSet.size()<1){
			CError.buildErr(this, "客户号:"+tInsuredNo+"录入的银行编码不在系统保存范围内!");
			return null;
		}
		tLPInsuredSchema.setBankCode(tBankCode);
	}
        return tLPInsuredSchema;
    }

    private LPContSchema getLPCont(Node node) {

        String tInsuredName = parseNode(node, "InsuredName");


        LPContSchema tLPContSchema = new LPContSchema();
        LCContDB tLCContDB = new LCContDB();
        tLCContDB.setContNo(mContNo);
        LCContSet tLCContSet = tLCContDB.query();
        if (tLCContSet.size() > 0) {
            mRef.transFields(tLPContSchema, tLCContSet.get(1));
        }
        tLPContSchema.setEdorNo(mEdorNo);
        tLPContSchema.setEdorType("BB");
	if (tInsuredName != null && !tInsuredName.equals("")) {
            tLPContSchema.setInsuredName(tInsuredName);
        }
        return tLPContSchema;
    }

    private LPAddressSchema getLPAddress(Node node) {
        //String tContNo = parseNode(node, "ContNo");
        String tInsuredNo = parseNode(node, "InsuredNo");
        String tMobile = parseNode(node, "Mobile");
        String tEMail = parseNode(node, "EMail");

        LPAddressSchema tLPAddressSchema = new LPAddressSchema();
        LCAddressDB tLCAddressDB = new LCAddressDB();
        tLCAddressDB.setCustomerNo(tInsuredNo);
        LCAddressSet tLCAddressSet = tLCAddressDB.query();
        if (tLCAddressSet.size() > 0) {
            int i = tLCAddressSet.size();
            mRef.transFields(tLPAddressSchema, tLCAddressSet.get(i));
        }

        tLPAddressSchema.setEdorNo(mEdorNo);
        tLPAddressSchema.setEdorType("BB");
        tLPAddressSchema.setCustomerNo(tInsuredNo);

	if (tMobile != null && !tMobile.equals("")) {
            tLPAddressSchema.setMobile(tMobile);
        }

	if (tEMail != null && !tEMail.equals("")) {
            tLPAddressSchema.setEMail(tEMail);
        }
	

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
        
    }

}
