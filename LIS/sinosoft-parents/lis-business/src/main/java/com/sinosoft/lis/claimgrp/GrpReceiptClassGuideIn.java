package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import java.io.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XObject;
import com.sinosoft.service.BusinessService;

public class GrpReceiptClassGuideIn implements BusinessService{
private static Logger logger = Logger.getLogger(GrpReceiptClassGuideIn.class);
    //错误处理类
    public CErrors logErrors = new CErrors();
    public CErrors mErrors = new CErrors();

    private VData mInputData = new VData();
    /** 往前面传输数据的容器 */
    private VData mResult = new VData();

    private String FileName;
    private String XmlFileName;
    private String mPreFilePath;

    private String FilePath = "E:/temp/";
    private String ParseRootPath = "/DATASET/BATCHID";
    private String ParsePath = "/DATASET/LLCASETABLE/ROW";
    private String ParsePath2 = "/DATASET/LLFEEMAINTABLE/ROW";

    //配置文件Xml节点描述
    private String ImportFileName;
    private String ConfigFileName;
    private String mBatchNo = "";

    private String[] m_strDataFiles = null;
    private GlobalInput mGlobalInput = new GlobalInput();
    private String mRgtNo = "";
    private String AccNo = "";
    private TransferData mTransferData = new TransferData();
    private org.w3c.dom.Document m_doc = null;

    private LLCaseSet mLLCaseSet = new LLCaseSet();
    LCGrpCustomerImportLogSchema delLCGrpCustomerImportLogSchema = new
            LCGrpCustomerImportLogSchema();
    LCGrpCustomerImportLogSchema InLCGrpCustomerImportLogSchema = new
            LCGrpCustomerImportLogSchema();


    public GrpReceiptClassGuideIn() {
        bulidDocument();
    }

    public boolean submitData(VData cInputData, String cOperate) {
        CErrors mErrors = new CErrors();
        mInputData = (VData) cInputData.clone();
        //得到数据
        getInputData();
        logger.debug(
                "----------GrpCustomerGuideIn after getInputData----------");
        //检查数据
        if (!checkData()) {
            return false;
        }
        logger.debug(
                "----------GrpCustomerGuideIn after checkData----------");
        logger.debug("开始时间:" + PubFun.getCurrentTime());
        try {
            //把Excel转化为Xml
            if (!parseVts()) {
                return false;
            }
            logger.debug(
                    "----------GrpCustomerGuideIn after parseVts----------");
            for (int nIndex = 0; nIndex < m_strDataFiles.length; nIndex++) {
                //把Xml转化为Schema
                XmlFileName = m_strDataFiles[nIndex];
                if (!ParseXml()) {
                    return false;
                }
            }
            logger.debug(
                    "----------GrpCustomerGuideIn after ParseXml----------");
        } catch (Exception ex) {
            ex.printStackTrace();
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LCParseGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "导入文件格式有误!";
            logErrors.addOneError(tError);
        }

        mErrors = logErrors;
        logger.debug("结束时间:" + PubFun.getCurrentTime());
        if (mErrors.getErrorCount() > 0) {
            return false;
        }

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
                tError.errorMessage = "请上传相应的数据文件到指定路径" +
                                      FilePath + "!";
                this.mErrors.addOneError(tError);
                return false;
            }
        }
        return true;
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
     * 解析xml
     * @return
     */
    private boolean ParseXml() {
        if (!checkXmlFileName()) {
            return false;
        }
        File tFile = new File(XmlFileName);
        XMLPathTool tXPT = new XMLPathTool(XmlFileName);
        this.mErrors.clearErrors();

        String tBatchNo = "";
        try {
            //批次号
            tBatchNo = tXPT.parseX(ParseRootPath).getFirstChild().getNodeValue();
            mBatchNo = tBatchNo;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        NodeList nodeList = tXPT.parseN(ParsePath);
        int nNodeCount = 0;

        if (nodeList != null) {
            nNodeCount = nodeList.getLength();
        }

        GrpCustomerParser tGrpCustomerParser = new GrpCustomerParser();
        //单次提交
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
            String cGrpContNo = parseNode(node, "GrpContNo"); //团体客户号
            String cCustomerNo = parseNode(node, "CustomerNo"); //投保人号码
            VData tLLCaseVData = new VData();

            String ID = "";
            String customerNo = "";
            String customerName = "";
            String flagRgtNo = "";
            boolean tErrorState = false;
            MMap tMap = new MMap();
            LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();

            if (nodeName.equals("#text")) {
                continue;
            }
            if (nodeName.equals("ROW")) {

                //增加对出险人的立案情况进行判断，如果有未结案的案件存在，不允许新增
                String StrSQL = " select caseno from llcase a,llregister b where a.rgtno=b.rgtno and b.grpcontno='" +
                                cGrpContNo + "' and a.customerno = '" +
                                cCustomerNo +
                        "' and  a.rgtstate not in ('50','60','70','80')";
                ExeSQL tExeSQL = new ExeSQL();
                String reptCont = tExeSQL.getOneValue(StrSQL);
                if (!(reptCont == null || reptCont.equals(""))) {

                    tMap.put("delete from llreport where rptno='" + mRgtNo +
                             "' ", "DELETE");
                    tMap.put("delete from llsubreport where subrptno='" +
                             mRgtNo + "' ", "DELETE");
                    tMap.put("delete from llreportreason where rpno='" + mRgtNo +
                             "' ", "DELETE");
                    tMap.put("delete from llregister where rgtno='" + mRgtNo +
                             "' ", "DELETE");
                    tMap.put("delete from llcase where caseno='" + mRgtNo +
                             "' ", "DELETE");
                    tMap.put("delete from llappclaimreason where rgtno='" +
                             mRgtNo + "' ", "DELETE");

                    mInputData.clear();
                    mInputData.add(tMap);
                    PubSubmit mPubSubmit = new PubSubmit();
                    mPubSubmit.submitData(mInputData, "");

                    CError tError = new CError();
                    tError.moduleName = "ParseGuideIn";
                    tError.functionName = "checkData";
                    tError.errorMessage = "该客户(" + cCustomerNo + ")在赔案(" +
                                          reptCont + ")有未结案件，请结案后再做新增！";
                    this.mErrors.addOneError(tError);
                    return false;

                }
                //解析案件XML
                tLLCaseVData = new VData(); //出险信息/帐单信息的结果集
                tLLCaseVData = tGrpCustomerParser.getLLCaseData(node);
            }
            if (tLLCaseVData != null && tLLCaseVData.size() > 0) {
                tLLCaseVData.add(mGlobalInput);
                ID = (String) tLLCaseVData.getObjectByObjectName("String", 0);
                customerNo = ((String) tLLCaseVData.getObjectByObjectName(
                        "String", 1)).trim();
                customerName = (String) tLLCaseVData.getObjectByObjectName(
                        "String", 2);
                flagRgtNo = (String) tLLCaseVData.getObjectByObjectName(
                        "String", 3);
                if (!flagRgtNo.equals("")) {
                    mRgtNo = flagRgtNo;
                }
                LLGrpClaimRegisterBL tLLGrpClaimRegisterBL = new
                        LLGrpClaimRegisterBL();
                //准备数据
                LLCaseSchema tLLCaseSchema = new LLCaseSchema();
                tLLCaseSchema = (LLCaseSchema) tLLCaseVData.
                                getObjectByObjectName("LLCaseSchema", 0);
                if (tLLCaseSchema != null &&
                    !tLLCaseSchema.getCustomerNo().equals("")) {
                    if (mRgtNo != null && !mRgtNo.equals("")) {
                        if (AccNo == null || AccNo.equals("")) {
                            String sql = "";
                            sql =
                                    "select CaseRelaNo from llcaserela where caseno='" +
                                    mRgtNo + "'";
                            ExeSQL tExeSQL = new ExeSQL();
                            AccNo = tExeSQL.getOneValue(sql);
                        }
                        LLAccidentSchema tLLAccidentSchema = new
                                LLAccidentSchema(); //事件表
                        LLAppClaimReasonSet tLLAppClaimReasonSet = new
                                LLAppClaimReasonSet(); //理赔类型集合
                        LLAppClaimReasonSet tLLAppClaimReasonSet2 = new
                                LLAppClaimReasonSet(); //理赔类型集合

                        tLLAccidentSchema = (LLAccidentSchema) tLLCaseVData.
                                            getObjectByObjectName(
                                "LLAccidentSchema", 0);
                        tLLRegisterSchema = (LLRegisterSchema) tLLCaseVData.
                                            getObjectByObjectName(
                                "LLRegisterSchema", 0);
                        tLLAppClaimReasonSet = (LLAppClaimReasonSet)
                                               tLLCaseVData.
                                               getObjectByObjectName(
                                "LLAppClaimReasonSet", 0);
                        tLLAccidentSchema.setAccNo(AccNo); //事件号
                        tLLRegisterSchema.setRgtNo(mRgtNo); //报案号=赔案号
                        tLLCaseSchema.setCaseNo(mRgtNo); //分案号=报案号=赔案号
                        mLLCaseSet.add(tLLCaseSchema); //用于帐单客户号保存

                        for (int j = 1; j <= tLLAppClaimReasonSet.size(); j++) {
                            LLAppClaimReasonSchema tLLAppClaimReasonSchema = new
                                    LLAppClaimReasonSchema(); //理赔类型表
                            tLLAppClaimReasonSchema = tLLAppClaimReasonSet.get(
                                    j);
                            tLLAppClaimReasonSchema.setCaseNo(mRgtNo); //报案号=赔案号
                            tLLAppClaimReasonSchema.setRgtNo(mRgtNo); //报案号=赔案号
                            tLLAppClaimReasonSet2.add(tLLAppClaimReasonSchema);
                        }
                        tLLCaseVData.add(tLLCaseSchema);
                        tLLCaseVData.add(tLLRegisterSchema);
                        tLLCaseVData.add(tLLAccidentSchema);
                        tLLCaseVData.add(tLLAppClaimReasonSet2);

                        tErrorState = tLLGrpClaimRegisterBL.submitData(
                                tLLCaseVData, "INSERT||TOSIMALL");
                    } else {
                        tErrorState = tLLGrpClaimRegisterBL.submitData(
                                tLLCaseVData, "INSERT||TOSIMALL");
                        tLLRegisterSchema = (LLRegisterSchema)
                                            tLLGrpClaimRegisterBL.getResult().
                                            getObjectByObjectName(
                                "LLRegisterSchema", 0);
                        LLAccidentSchema tLLAccidentSchema = new
                                LLAccidentSchema(); //事件表
                        tLLAccidentSchema = (LLAccidentSchema)
                                            tLLGrpClaimRegisterBL.getResult().
                                            getObjectByObjectName(
                                "LLAccidentSchema", 0);
                        mLLCaseSet.add(tLLCaseSchema);
                        mRgtNo = tLLRegisterSchema.getRgtNo(); //返回生成的赔案号
                        AccNo = tLLAccidentSchema.getAccNo(); //返回生成的事件号
                    }
                }
                //将顺序号与立案同步
                String updateSql = "update llsubreport set condoleflag=(select seconduwflag from llcase where caseno=llsubreport.subrptno and customerno=llsubreport.customerno ) where subrptno='" +
                                   mRgtNo + "' and customerno='" + cCustomerNo +
                                   "'  ";
                tMap.put(updateSql, "UPDATE");
            }

            logErrors = tGrpCustomerParser.mErrors;
            String a = Integer.toString(i + 1);
            if (logErrors != null && !logErrors.equals("")) {
                if (mRgtNo != null && !mRgtNo.equals("")) {
                    LogError(mRgtNo, mBatchNo, a, customerNo, customerName,
                             mGlobalInput.Operator, tErrorState, logErrors);
                } else {
                    LogError("000000", mBatchNo, a, customerNo, customerName,
                             mGlobalInput.Operator, tErrorState, logErrors);
                }

                tMap.put(delLCGrpCustomerImportLogSchema, "DELETE");
                tMap.put(InLCGrpCustomerImportLogSchema, "INSERT");
            }
            PubSubmit tPubSubmit = new PubSubmit();
            mInputData.clear();
            mInputData.add(tMap);
            if (!tPubSubmit.submitData(mInputData, "")) {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "GrpCustomerGuideIn";
                tError.functionName = "parserXml";
                tError.errorMessage = "数据提交失败！客户号：" + customerNo + "!";
                this.logErrors.addOneError(tError);

                // @@如果提交错误则添加错误日志
                LogError(mRgtNo, mBatchNo, ID, customerNo, customerName,
                         mGlobalInput.Operator, tErrorState, logErrors);
                tMap = null;
                tMap.put(delLCGrpCustomerImportLogSchema, "DELETE");
                tMap.put(InLCGrpCustomerImportLogSchema, "INSERT");
                mInputData.clear();
                mInputData.add(tMap);
                tPubSubmit.submitData(mInputData, "");
            }

        }

        //分事务导入首先导入出险人信息在通过查先caseid找到客户号在导入帐单信息
        //社保帐单导入
        VData tLLFeeVData = new VData();
        MMap FMap = new MMap();
        boolean mErrorState = false;
        NodeList nodeList1 = tXPT.parseN(ParsePath2);
        int tNodeCount = 0;
        if (nodeList1 != null) {
            tNodeCount = nodeList1.getLength();
        }
        for (int k = 0; k < tNodeCount; k++) {
            Node tnode = nodeList1.item(k);
            tLLFeeVData = tGrpCustomerParser.getReceiptClass(tnode, mRgtNo);
            tLLFeeVData.add(mGlobalInput);
            LLGrpReceiptClassInpBL tLLGrpReceiptClassInpBL = new
                    LLGrpReceiptClassInpBL();
            mErrorState = tLLGrpReceiptClassInpBL.submitData(tLLFeeVData,
                    "IMPORT||MAIN");
            FMap = tLLGrpReceiptClassInpBL.getMMap();

            LLCaseReceiptClassSchema tLLCaseReceiptClassSchema = new
                    LLCaseReceiptClassSchema();
            tLLCaseReceiptClassSchema = (LLCaseReceiptClassSchema) tLLFeeVData.
                                        getObjectByObjectName(
                                                "LLCaseReceiptClassSchema", 0);
            String customerNo = tLLCaseReceiptClassSchema.getInsuredNo();
            String customerName = tLLCaseReceiptClassSchema.getName();

            logErrors = tGrpCustomerParser.mErrors;
            String a = Integer.toString(k + 1);
            if (logErrors != null && !logErrors.equals("")) {
                LogError(mRgtNo, mBatchNo, a, customerNo, customerName,
                         mGlobalInput.Operator, mErrorState, logErrors);

                FMap.put(delLCGrpCustomerImportLogSchema, "DELETE");
                FMap.put(InLCGrpCustomerImportLogSchema, "INSERT");
            }

            if (FMap != null) {
                PubSubmit tPubSubmit = new PubSubmit();
                mInputData.clear();
                mInputData.add(FMap);
                if (!tPubSubmit.submitData(mInputData, "")) {
                    // @@错误处理
                    CError tError = new CError();
                    tError.moduleName = "GrpCustomerGuideIn";
                    tError.functionName = "parserXml";
                    tError.errorMessage = "帐单信息提交失败!";
                    this.logErrors.addOneError(tError);
                }
            }
        }

        if (this.mErrors.needDealError()) {
            logger.debug(this.mErrors.getErrorCount() +
                               "error:" + this.mErrors.getFirstError());
        }
        //解析完删除XML文件
        if (!tFile.delete()) {
            // @@错误处理
//            CError tError = new CError();
//            tError.moduleName = "ParseGuideIn";
//            tError.functionName = "checkData";
//            tError.errorMessage = "Xml文件删除失败!";
//            this.mErrors.addOneError(tError);
//            return false;
        }
        return true;
    }

    //创建导入日志
    public void LogError(String tRgtNo, String tBatchNo, String ID,
                         String tCustomerNo, String customerName,
                         String operator, boolean tErrorState, CErrors tErrors) {
        delLCGrpCustomerImportLogSchema = new LCGrpCustomerImportLogSchema();
        InLCGrpCustomerImportLogSchema = new LCGrpCustomerImportLogSchema();
        //删除日志
        delLCGrpCustomerImportLogSchema.setRgtNo(tRgtNo);
        delLCGrpCustomerImportLogSchema.setBatchNo(tBatchNo);
        delLCGrpCustomerImportLogSchema.setID(ID);
        //插入日志
        InLCGrpCustomerImportLogSchema.setRgtNo(tRgtNo);
        InLCGrpCustomerImportLogSchema.setBatchNo(tBatchNo);
        InLCGrpCustomerImportLogSchema.setID(ID);
        InLCGrpCustomerImportLogSchema.setCustomerNo(tCustomerNo);
        InLCGrpCustomerImportLogSchema.setCustomerName(customerName);
        InLCGrpCustomerImportLogSchema.setOperator(operator);
        if (tErrorState) {
            InLCGrpCustomerImportLogSchema.setErrorState("1");
        } else {
            InLCGrpCustomerImportLogSchema.setErrorState("0");
        }
        InLCGrpCustomerImportLogSchema.setMakeDate(PubFun.getCurrentDate());
        InLCGrpCustomerImportLogSchema.setMakeTime(PubFun.getCurrentTime());
        String errorMess = "";
        for (int i = 0; i < logErrors.getErrorCount(); i++) {
            errorMess += logErrors.getError(i).errorMessage;
        }
        if ("".equals(errorMess)) {
            errorMess = "未捕捉到错误";
        }
        InLCGrpCustomerImportLogSchema.setErrorInfo(errorMess);

    }

    //取得日志信息
    public LCGrpCustomerImportLogSchema getLogInfo(String mRgtNo,
            String batchNo, String ID) {
        LCGrpCustomerImportLogDB tLCGrpCustomerImportLogDB = new
                LCGrpCustomerImportLogDB();
        tLCGrpCustomerImportLogDB.setRgtNo(mRgtNo);
        tLCGrpCustomerImportLogDB.setBatchNo(batchNo);
        tLCGrpCustomerImportLogDB.setID(ID);
        if (tLCGrpCustomerImportLogDB.getInfo()) {
            return tLCGrpCustomerImportLogDB.getSchema();
        }
        return null;
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
     * 解析excel并转换成xml文件
     * @return
     */
    private boolean parseVts() throws Exception {
        //初始化导入文件
        if (!this.initImportFile()) {
            return false;
        }
        logger.debug(
                "----------GrpCustomerGuideIn initImportFile()----------");
        //检查导入配置文件是否存在
        if (!this.checkImportConfig()) {
            return false;
        }
        logger.debug(
                "----------GrpCustomerGuideIn checkImportConfig()----------");
        GrpCustomerVTSParser GCvp = new GrpCustomerVTSParser();

        if (!GCvp.setFileName(ImportFileName)) {
            mErrors.copyAllErrors(GCvp.mErrors);
            return false;
        }
        logger.debug(
                "----------GrpCustomerGuideIn setFileName()----------");
        if (!GCvp.setConfigFileName(ConfigFileName)) {
            mErrors.copyAllErrors(GCvp.mErrors);
            return false;
        }
        logger.debug(
                "----------GrpCustomerGuideIn setConfigFileName()----------");
        //转换excel到xml
        if (!GCvp.transform()) {
            mErrors.copyAllErrors(GCvp.mErrors);
            return false;
        }
        logger.debug("----------GrpCustomerGuideIn transform()----------");
        // 得到生成的XML文件名列表
        m_strDataFiles = GCvp.getDataFiles();

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

        ConfigFileName = filePath + "GrpReceiptClassImport.xml";
        File tFile2 = new File(ConfigFileName);
        if (!tFile2.exists()) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ParseGuideIn";
            tError.functionName = "checkData";
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
        ImportFileName = mPreFilePath + FilePath + FileName;
        //ImportFileName = "E:/temp/" + FileName;
        logger.debug("开始导入文件-----" + ImportFileName);
        File tFile = new File(ImportFileName);
        if (!tFile.exists()) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ParseGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "未上传文件到指定路径" +
                                  FilePath + "!";
            this.mErrors.addOneError(tError);
            return false;
        }
        logger.debug("-----导入文件");
        return true;
    }

    /**
     * 得到生成文件路径
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
        } else {
            FilePath = tLDSysVarDB.getSysVarValue();
        }

        return true;
    }

    /**
     * checkData
     *
     * @return boolean
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
     * getInputData
     */
    private void getInputData() {
        mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
                "GlobalInput", 0);
        mTransferData = (TransferData) mInputData.getObjectByObjectName(
                "TransferData", 0);
        mRgtNo = (String) mInputData.getObjectByObjectName("String", 0);
        mPreFilePath = (String) mTransferData.getValueByName("FilePath");
        logger.debug(mPreFilePath);
        logger.debug(mRgtNo);
    }

    /**
     * 得到日志显示结果
     * @return VData
     */
    public VData getResult() {
        mResult.add(mRgtNo);
        return mResult;
    }

    public static void main(String[] args) {
        GrpReceiptClassGuideIn tPGI = new GrpReceiptClassGuideIn();
        VData tVData = new VData();
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("FileName", "20050426.xls");
        tTransferData.setNameAndValue("FilePath", "E:");
        GlobalInput tG = new GlobalInput();
        tG.Operator = "claim";
        tG.ManageCom = "86";

        long a = System.currentTimeMillis();
        logger.debug(a);
        tVData.add(tTransferData);
        tVData.add(tG);
        tPGI.submitData(tVData, "");
        logger.debug(System.currentTimeMillis() - a);
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

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}


}
