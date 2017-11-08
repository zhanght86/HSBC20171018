package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;
import javax.xml.parsers.*;

import org.w3c.dom.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XObject;
import java.io.File;
/**
 * <p>Title:LLAccCountForLoadBL.java </p>
 * <p>Description: 帐户信息导入，进行出险人的受益人分配的磁盘信息导入</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:sinosoft </p>
 * @author  万泽辉
 * @version 1.0
 * @date 2006-03-15
 */
public class LLAccCountForLoadBL
{
private static Logger logger = Logger.getLogger(LLAccCountForLoadBL.class);

    public LLAccCountForLoadBL()
    {
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
    private MMap mMap =  new MMap();
    LCGrpCustomerImportLogSchema delLCGrpCustomerImportLogSchema =
            new LCGrpCustomerImportLogSchema(); // 日志中的删除纪录
    LCGrpCustomerImportLogSchema InLCGrpCustomerImportLogSchema =
            new LCGrpCustomerImportLogSchema(); // 日志中的插入纪录

    public boolean submitData(VData cInputData, String cOperate)
    {
        mInputData = (VData) cInputData.clone();
        //得到数据
        if(!getInputData())
        {
            return false;
        }
        logger.debug(
                "----------LLAccCountForLoadBL after checkData----------");
        logger.debug("开始时间:" + PubFun.getCurrentTime());
        try
        {
            //把Excel转化为Xml
            if (!parseVts())
            {
                return false;
            }
            logger.debug(
                    "---------------bengin enter to xml -----------------");
            for (int nIndex = 0; nIndex < m_strDataFiles.length; nIndex++)
            {
                //把Xml转化为Schema
                XmlFileName = m_strDataFiles[nIndex];
                logger.debug("-----------XmlFileName-----[ " + nIndex +
                                   " ]--------" + XmlFileName);
                if (!ParseXml())
                {
                    return false;
                }
            }
            logger.debug(
                    "---------------end enter to xml -----------------");

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLAccCountForLoadBL";
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
    private boolean checkXmlFileName()
    {
        File tFile = new File(XmlFileName);
        if (!tFile.exists())
        {
            LDSysVarDB tLDSysVarDB = new LDSysVarDB();
            tLDSysVarDB.setSysVar("XmlPath");
            if (!tLDSysVarDB.getInfo())
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LLAccCountForLoadBL";
                tError.functionName = "checkData";
                tError.errorMessage = "缺少文件导入路径!";
                this.mErrors.addOneError(tError);
                return false;
            }
            else
                FilePath = tLDSysVarDB.getSysVarValue();
            File tFile1 = new File(FilePath);
            if (!tFile1.exists())
            {
                tFile1.mkdirs();
            }
            XmlFileName = FilePath + XmlFileName;
            File tFile2 = new File(XmlFileName);
            if (!tFile2.exists())
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LLAccCountForLoadBL";
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
    private boolean ParseXml()
    {
        /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         No.1 检查xml文件存在
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (!checkXmlFileName())
            return false;
        File tFile = new File(XmlFileName);
        XMLPathTool tXPT = new XMLPathTool(XmlFileName);
        this.mErrors.clearErrors();
        String tBatchNo = "";
        try
        {
            /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             No.2 批次号
             －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
            tBatchNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
            logger.debug("tBatchNo=:"+tBatchNo);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         No.3 取得xml文件的第一类字段的值（出险人信息列）
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        NodeList nodeList = tXPT.parseN(ParsePath);
        int nNodeCount = 0;

        if (nodeList != null)
        {
            nNodeCount = nodeList.getLength();
        }
        /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         No.4 单次提交出险人信息列
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        for (int i = 0; i < nNodeCount; i++)
        {
            Node node = nodeList.item(i);
            try
            {
                node = transformNode(node);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                node = null;
            }
            String nodeName = "";
            nodeName = node.getNodeName();

            //案件
            VData mVData = new VData();

            logger.debug("节点名称：" + nodeName);
            if (nodeName.equals("#text"))
                continue;
            if (nodeName.equals("ROW"))
            {
                /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 No.4.1 解析案件XML，并获得返回的数据包
                 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
               mVData  = this.getLLAccCount(node);
            }
            if (mVData != null && mVData.size() > 0)
            {
                mVData.add(mGlobalInput);
            }
            else
            {
                CError tError = new CError();
                tError.moduleName = "LLAccCountForLoadBL";
                tError.functionName = "parserXml";
                tError.errorMessage = "获得导入的文件信息失败!";
                this.mErrors.addOneError(tError);
                return false;
            }
            //得到导入的数据
            LLAccountSchema mLLAccountSchema =  new LLAccountSchema();
            mLLAccountSchema =  (LLAccountSchema)mVData.getObjectByObjectName("LLAccountSchema",0);
            if(mLLAccountSchema == null)
            {
                CError tError = new CError();
                tError.moduleName = "LLAccCountForLoadBL";
                tError.functionName = "parserXml";
                tError.errorMessage = "获得导入的文件信息失败!";
                this.mErrors.addOneError(tError);
                return false;
            }
            // llaccount表contno号不能为空
            if(mLLAccountSchema.getContNo() == null ||mLLAccountSchema.getContNo().equals(""))
            {
                CError tError = new CError();
                tError.moduleName = "LLAccCountForLoadBL";
                tError.functionName = "parserXml";
                tError.errorMessage = "导入文件的投保人个人合同号为空或者投保人个人合同号丢失!";
                this.mErrors.addOneError(tError);
                //return false;
            }
            //llaccount表name号不能为空
            if(mLLAccountSchema.getName() == null ||mLLAccountSchema.getName().equals(""))
            {
                CError tError = new CError();
                tError.moduleName = "LLAccCountForLoadBL";
                tError.functionName = "parserXml";
                tError.errorMessage = "导入文件的受益人姓名为空或者受益人姓名丢失!";
                this.mErrors.addOneError(tError);
                //return false;
            }
            //llaccount表grpcontno号不能为空
            if(mLLAccountSchema.getGrpContNo() == null ||mLLAccountSchema.getGrpContNo().equals(""))
            {
                CError tError = new CError();
                tError.moduleName = "LLAccCountForLoadBL";
                tError.functionName = "parserXml";
                tError.errorMessage = "导入文件的团体合同号为空或者团体合同号丢失!";
                this.mErrors.addOneError(tError);
                //return false;
            }
            //llaccount表account号不能为空
            if(mLLAccountSchema.getAccount()== null ||mLLAccountSchema.getAccount().equals(""))
            {
                CError tError = new CError();
                tError.moduleName = "LLAccCountForLoadBL";
                tError.functionName = "parserXml";
                tError.errorMessage = "导入文件的银行账号为空或者银行账号丢失!";
                this.mErrors.addOneError(tError);
                //return false;
            }
            MMap tMap = new MMap();
            tMap.put(mLLAccountSchema,"DELETE&INSERT");
            PubSubmit tPubSubmit = new PubSubmit();
            mInputData.clear();
            mInputData.add(tMap);

            /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
              No.5 报案完成，分别向lLCGrpCustomerImportLog表中提交数据，提交成功的和不成功的记录
             －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            if (!tPubSubmit.submitData(mInputData, ""))
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LLAccCountForLoadBL";
                tError.functionName = "parserXml";
                tError.errorMessage = "数据提交失败！客户号：" + mLLAccountSchema.getInsuredNo() + "!";
                this.mErrors.addOneError(tError);
                // @@如果提交错误则添加错误日志
                LogError(mLLAccountSchema.getContNo(), tBatchNo, mLLAccountSchema.getIdNo(), mLLAccountSchema.getInsuredNo(),
                         mLLAccountSchema.getName(),
                         mGlobalInput.Operator, false,
                         mErrors);
                MMap nMap = new  MMap();
                nMap.put(InLCGrpCustomerImportLogSchema, "DELETE&INSERT");
                mInputData.clear();
                mInputData.add(nMap);
                tPubSubmit.submitData(mInputData, "");
            }
            mErrors.clearErrors();
        }
        if (this.mErrors.needDealError())
        {
            logger.debug(this.mErrors.getErrorCount() +
                               "error:" +
                               this.mErrors.getFirstError());
        }
        /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         No.6 解析完删除XML文件
               －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        /*if (!tFile.delete())
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ParseGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "Xml文件删除失败!";
            this.mErrors.addOneError(tError);
            return false;
        }*/
        return true;
    }

    //创建导入日志
    public void LogError(String tRgtNo, String tBatchNo, String ID,
                         String tCustomerNo, String customerName,
                         String operator, boolean tErrorState, CErrors tErrors)
    {

        InLCGrpCustomerImportLogSchema =
                new LCGrpCustomerImportLogSchema();
        //插入日志
        InLCGrpCustomerImportLogSchema.setRgtNo(tRgtNo);
        InLCGrpCustomerImportLogSchema.setBatchNo(tBatchNo);
        InLCGrpCustomerImportLogSchema.setID(ID);
        InLCGrpCustomerImportLogSchema.setCustomerNo(tCustomerNo);
        InLCGrpCustomerImportLogSchema.setCustomerName(customerName);
        InLCGrpCustomerImportLogSchema.setOperator(operator);
        if (tErrorState)
            InLCGrpCustomerImportLogSchema.setErrorState("1");
        else
            InLCGrpCustomerImportLogSchema.setErrorState("0");
        InLCGrpCustomerImportLogSchema.setMakeDate(PubFun.getCurrentDate());
        InLCGrpCustomerImportLogSchema.setMakeTime(PubFun.getCurrentTime());
        String errorMess = "";
        for (int i = 0; i < tErrors.getErrorCount(); i++)
        {
            errorMess += tErrors.getError(i).errorMessage;
        }
        if ("".equals(errorMess))
            errorMess = "未捕捉到错误";
        InLCGrpCustomerImportLogSchema.setErrorInfo(errorMess);

    }

    //取得日志信息
    public LCGrpCustomerImportLogSchema getLogInfo(String mRgtNo,
            String batchNo, String ID)
    {
        LCGrpCustomerImportLogDB tLCGrpCustomerImportLogDB = new
                LCGrpCustomerImportLogDB();
        tLCGrpCustomerImportLogDB.setRgtNo(mRgtNo);
        tLCGrpCustomerImportLogDB.setBatchNo(batchNo);
        tLCGrpCustomerImportLogDB.setID(ID);
        if (tLCGrpCustomerImportLogDB.getInfo())
        {
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
            Exception
    {
        Node nodeNew = m_doc.importNode(node, true);

        return nodeNew;
    }

    /**
     * 解析excel并转换成xml文件,用完后删除xml文件
     * @return
     */
    private boolean parseVts() throws Exception
    {
        logger.debug("--------excel--导入-xml---开始----------");
        /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
          No.1 初始化导入文件
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (!this.initImportFile())return false;
        logger.debug("----LLAccCountForLoadBL initImportFile()------");
        /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         No.2 检查导入配置文件是否存在
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (!this.checkImportConfig())return false;
        logger.debug("-----LLAccCountForLoadBL checkImportConfig()-----");
        /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         No.3 指定导入文件名称
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LLAccountVTSParser GCvp = new LLAccountVTSParser();
        logger.debug("----------得到倒入文件名----------" + ImportFileName);
        if (!GCvp.setFileName(ImportFileName))
        {
            mErrors.copyAllErrors(GCvp.mErrors);
            return false;
        }
        /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         No.4 指定配置文件名称
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        logger.debug(
                "----------LLAccCountForLoadBL setFileName()----------");
        logger.debug("----------得到配置文件名----------" + ConfigFileName);
        if (!GCvp.setConfigFileName(ConfigFileName))
        {
            mErrors.copyAllErrors(GCvp.mErrors);
            return false;
        }
        /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         No.5 转换excel到xml
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (!GCvp.transform())
        {
            mErrors.copyAllErrors(GCvp.mErrors);
            return false;
        }
        logger.debug("----LLAccCountForLoadBL transform()------");
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
    private boolean checkImportConfig()
    {
        this.getFilePath();

        String filePath = mPreFilePath + FilePath;
        File tFile1 = new File(filePath);
        if (!tFile1.exists())
        {
            //初始化创建目录
            tFile1.mkdirs();
        }

        ConfigFileName = filePath + "llaccount.xml";
        logger.debug("ConfigFileName："+ConfigFileName);
        File tFile2 = new File(ConfigFileName);
        if (!tFile2.exists())
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ParseGuideIn";
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
    private boolean initImportFile()
    {
        this.getFilePath();
        logger.debug("this.getFilePath():"+this.getFilePath());
        logger.debug("mPreFilePath:"+mPreFilePath);
        logger.debug("FilePath:"+FilePath);
        logger.debug("FileName:"+FileName);
        ImportFileName = mPreFilePath + FilePath + FileName;
        logger.debug("ImportFileName:"+ImportFileName);
        File tFile = new File(ImportFileName);
        if (!tFile.exists())
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ParseGuideIn";
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
    private boolean getFilePath()
    {
        LDSysVarDB tLDSysVarDB = new LDSysVarDB();
        tLDSysVarDB.setSysVar("XmlPath");
        if (!tLDSysVarDB.getInfo())
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ParseGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "缺少文件导入路径!";
            this.mErrors.addOneError(tError);
            return false;
        }
        else
            FilePath = tLDSysVarDB.getSysVarValue();
        logger.debug("FilePath："+FilePath);
        return true;
    }
    /**
     * getInputData
     */
    private boolean getInputData()
    {
        mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
                "GlobalInput",
                0);
        mTransferData = (TransferData) mInputData.getObjectByObjectName(
                "TransferData", 0);
        mPreFilePath = (String) mTransferData.getValueByName("FilePath");
        FileName = (String) mTransferData.getValueByName("FileName");
        if(mGlobalInput == null)
        {
            CError tError = new CError();
            tError.moduleName = "LLAccCountForLoadBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受前台传入的GlobalInput数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if(mTransferData == null)
        {
            CError tError = new CError();
            tError.moduleName = "LLAccCountForLoadBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受前台传入的TransferData数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if(mPreFilePath == null)
        {
            CError tError = new CError();
            tError.moduleName = "LLAccCountForLoadBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受前台传入的PreFilePath数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 得到日志显示结果
     * @return VData
     */
    public VData getResult()
    {
        return mResult;
    }
    /**
     * 利用XPathAPI取得某个节点的节点值
     * @param node
     * @param strPath
     * @return
     */
    private static String parseNode(Node node,
                                    String strPath)
    {
        String strValue = "";
        try
        {
            XObject xobj = XPathAPI.eval(node, strPath);
            strValue = xobj == null ? "" : xobj.toString();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return strValue;
    }
    /**===============================================================================================
        * 从XML中解析立案信息,
        * @param node
        * @return vdata
        * desc: 用于账户案件理赔
        * auther: 万泽辉
        * date:2006-03-15
        ==================================================================================================
        */
       public VData getLLAccCount(Node node)
       {
           VData tVData = new VData();
           LLAccountSchema  tLLAccountSchema = new  LLAccountSchema();
           ExeSQL tExeSQL = new ExeSQL();

           String ID             = parseNode(node, "CaseID");        //
           String tGrpCustomerNo = parseNode(node, "GrpCustomerNo"); //
           String tGrpContNo     = parseNode(node, "GrpContNo");     //
           String tCustomerNo    = parseNode(node, "CustomerNo");        //
           String tContNo        = parseNode(node, "ContNo");    //
           String tCustomerName  = parseNode(node, "CustomerName");  //
           String tCustomerSex   = parseNode(node, "CustomerSex");   //
           String tBirthDay      = parseNode(node, "BirthDay");          //
           String tIDType        = parseNode(node, "IDType");  //
           String tIDCode        = parseNode(node, "IDCode");      //
           String tRelation      = parseNode(node, "Relation");       //
           String tName          = parseNode(node, "Name");     //
           String tIDNo          = parseNode(node, "IDNo"); //
           String tBankCode      = parseNode(node, "BankCode");
           String tBranchCode    = parseNode(node, "BranchCode");
           String tBranchName    = parseNode(node, "BranchName");
           String tAccount       = parseNode(node, "Account");
           String tBnfRate       = parseNode(node, "BnfRate");
           if(tBnfRate == ""||tBnfRate.equals(""))
               tBnfRate = "0";
           //通过客户号或客户姓名证件类型以及证件号码得到客户信息
           String strSql ="select insuredno,contno,name from lcinsured where grpcontno='" +
                   tGrpContNo + "' and ";
           if (tCustomerNo != null && !tCustomerNo.equals(""))
           {
               strSql += " insuredno = '" + tCustomerNo + "'";
           }
           else if (tIDType.equals("0"))
           { //身份证
               strSql += " name = '" + tCustomerName + "' and idno = '" +
                       tIDCode + "' and idtype = '" + tIDType + "'";
           }
           else
           {
               strSql += " name = '" + tCustomerName + "' and idno = '" +
                       tIDCode + "' and idtype = '" + tIDType + "' and sex = '" +
                       tCustomerSex + "'";
           }
           logger.debug("===strSql====="+strSql);
           SSRS tSSRS1 = tExeSQL.execSQL(strSql);
           if (tSSRS1.getMaxRow() > 0)
           {
               tCustomerNo   = tSSRS1.GetText(1,1);//个人客户号
               tCustomerName = tSSRS1.GetText(1,3);//个人客户姓名
               tContNo       = tSSRS1.GetText(1,2);//个人合同好
           }
           tLLAccountSchema.setAccount(tAccount);
           tLLAccountSchema.setBankCode(tBankCode);
           tLLAccountSchema.setBnfRate(Double.parseDouble(tBnfRate));
           tLLAccountSchema.setBranchCode(tBranchCode);
           tLLAccountSchema.setBranchName(tBranchName);
           tLLAccountSchema.setContNo(tContNo);
           tLLAccountSchema.setGrpContNo(tGrpContNo);

           tLLAccountSchema.setIdNo(tIDCode);
           tLLAccountSchema.setInsuredNo(tCustomerNo);
           tLLAccountSchema.setInsuredName(tCustomerName);
           tLLAccountSchema.setMakeTime(PubFun.getCurrentTime());
           tLLAccountSchema.setMakeDate(PubFun.getCurrentDate());
           tLLAccountSchema.setModefyTime(PubFun.getCurrentTime());
           tLLAccountSchema.setModifyDate(PubFun.getCurrentDate());
           tLLAccountSchema.setName(tName);
           tLLAccountSchema.setOperator(mGlobalInput.Operator);
           tVData.add(tGrpContNo);
           tVData.add(tCustomerNo);
           tVData.add(tLLAccountSchema);

       return tVData;
   }

   /**
    * Build a instance document object for function transfromNode()
    */
   private void bulidDocument()
   {
       DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
       dfactory.setNamespaceAware(true);

       try
       {
           m_doc = dfactory.newDocumentBuilder().newDocument();
       }
       catch (Exception ex)
       {
           ex.printStackTrace();
       }
   }
   /*
    * desc：检查受益比例是否已经发到饱和（bnfrate = 1）
    * param：String contno (个人合同号)
    */
   private  boolean checkBnfRate(String tContNo,double tBnfRate)
   {
       String tSql = "select sum(bnfrate) from llaccount where contno = '"+tContNo+"' group by contno";
       ExeSQL tExeSQL   =  new  ExeSQL();
       SSRS tSSRS  = tExeSQL.execSQL(tSql);
       String t  = "";
       if(tSSRS.getMaxRow() <1)
           t ="0";
       else
           t = tSSRS.GetText(1,1);
       double sumBnfRate = Double.parseDouble(t);
       if(sumBnfRate + tBnfRate > 1)
           return false;
       else
           return true;
   }

   public static void main(String[] args)
  {
       LLAccCountForLoadBL tPGI = new LLAccCountForLoadBL();
       VData tVData = new VData();
       TransferData tTransferData = new TransferData();
       tTransferData.setNameAndValue("FileName", "llaccount.xls");
       tTransferData.setNameAndValue("FilePath", "d:");
       GlobalInput tG = new GlobalInput();
       tG.Operator = "claim";
       tG.ManageCom = "86";
       tG.ComCode = "0101";
       long a = System.currentTimeMillis();
       logger.debug(a);
       tVData.add(tTransferData);
       tVData.add(tG);
       if(!tPGI.submitData(tVData, ""))
       {
           logger.debug("提交失败！！！！");
       }
       logger.debug(System.currentTimeMillis() - a);
   }

}
