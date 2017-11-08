package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;
/*
 * <p>ClassName: GrpCustomerGuideForAccIn </p>
 * <p>Description: GrpCustomerGuideForAccIn类文件，处理 [账户案件理赔录入] </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Author：万泽辉 and wangjm
 * @CreateDate：2006-01-12
 */
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
public class GrpCustomerGuideForAccIn implements BusinessService{
private static Logger logger = Logger.getLogger(GrpCustomerGuideForAccIn.class);
    //错误处理类
    public CErrors logErrors = new CErrors();
    public CErrors mlogErrors = new CErrors();
    public CErrors mErrors = new CErrors();
    private VData mInputData = new VData();
    /** 往前面传输数据的容器 */
    private VData mResult = new VData();

    private String FileName; // execl 文件名称
    private String XmlFileName; // xml 文件名称
    private String mPreFilePath; // 文件路径

    private String FilePath = "d:/temp/";
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
    private String rRgtNo = "";
    private String AccNo = "";
    private TransferData mTransferData = new TransferData();

    private org.w3c.dom.Document m_doc = null;
    private int mTrue = 0; //记录导入成功的个数
    private int mFalse = 0;//记录导入失败的个数
    private String mContent = "";
    private TransferData mReturnData = new TransferData();

    private LLCaseSet mLLCaseSet = new LLCaseSet(); // 立案队列
    LCGrpCustomerImportLogSchema delLCGrpCustomerImportLogSchema =
            new LCGrpCustomerImportLogSchema(); // 日志中的删除纪录
    LCGrpCustomerImportLogSchema InLCGrpCustomerImportLogSchema =
            new LCGrpCustomerImportLogSchema(); // 日志中的插入纪录


    public GrpCustomerGuideForAccIn()
    {
        bulidDocument();
    }

    public boolean submitData(VData cInputData, String cOperate)
    {
        CErrors mErrors = new CErrors();
        mInputData = (VData) cInputData.clone();
        //得到数据
        getInputData();
        logger.debug(
                "----------GrpCustomerGuideIn after getInputData----------");
        //检查数据
        if (!checkData())
        {
            return false;
        }
        logger.debug(
                "----------GrpCustomerGuideIn after checkData----------");
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
            tError.moduleName = "LCParseGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "导入文件格式有误!";
            logErrors.addOneError(tError);
        }

        mErrors = logErrors;
        logger.debug("结束时间:" + PubFun.getCurrentTime());
        /*if (mErrors.getErrorCount() > 0)
             {
           return false;
             }*/

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
                tError.moduleName = "ParseGuideIn";
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
            tBatchNo = tXPT.parseX(ParseRootPath).
                       getFirstChild().getNodeValue();
            mBatchNo = tBatchNo;
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
        int t=0;

        if (nodeList != null)
        {
            nNodeCount = nodeList.getLength();
        }
        GrpCustomerParser tGrpCustomerParser = new GrpCustomerParser();

        /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         No.3 单次提交出险人信息列
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
            String cGrpContNo     = parseNode(node, "GrpContNo"); //团体客户号
            String cCustomerNo    = parseNode(node, "CustomerNo"); //投保人号码
            String cCustomerName  = parseNode(node, "CustomerName");  //出险人名称
            String cPesAcc        = parseNode(node, "OwnCancel"); //个报销额
            String cAccAmt        = parseNode(node, "CompanyCancel"); //团体报销
            String tAccFlag       = parseNode(node, "GrpFlag");       //是否使用团体帐户金额
            //案件
            VData tLLCaseVData = new VData();
            VData tLLFeeVData = new VData();
            String ID = "";
            String customerNo = "";
            String customerName = "";
            String flagRgtNo = "";
            boolean tErrorState = false;
            MMap tMap = new MMap();
            MMap FMap = new MMap();
            logger.debug("节点名称：" + nodeName);
            LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
            if (nodeName.equals("#text"))
                continue;
            if (nodeName.equals("ROW"))
            {
               /**update by wood 未结案件的判断在立案的时候判断，这里不在判断，这样的话，立案的时候就允许多次导入。
            	//增加对出险人的立案情况进行判断，如果有未结案的案件存在，不允许新增
                String StrSQL = " select count(*) from llregister  where"
                                + " grpcontno = '" + cGrpContNo +
                                "' and clmstate not in ('60', '80', '50', '70') and rgtstate='02' ";
                ExeSQL tExeSQL = new ExeSQL();
                String tCont = tExeSQL.getOneValue(StrSQL);
                if (!tCont.equals("0")&&(mRgtNo==null || mRgtNo.equals(""))) {
                        mContent = "该保单(" + cGrpContNo + ")有未结案件，请结案后在做新增！";
                        return false;
                }
                */
//
                /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 No.3.1 解析案件XML
                 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
                boolean tFlag = updateLCIsureAccClass(cGrpContNo, cCustomerNo);
                if(!tFlag)
                {
                    return false;
                }

                /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 * 首先检查导入的帐单中[团体赔付金额]，如果[团体赔付金额]小于团体帐单余额，
                 * 那么报案失败,(注：[个人赔付金额]的处理在GrpCustomerParser.getLLCaseDataForAcc()
                 * 进行,因为如果团体余额小于[团体赔付金额]的话，那么就不必进行下去了)
                 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
                if(!cAccAmt.equals("")&&cAccAmt!=null )

               {
                   boolean pFlag = checkPAccount(cGrpContNo, cAccAmt);

//                if((!cAccAmt.equals("")&&cAccAmt!=null&&cAccAmt.trim()!="0"&&!cAccAmt.trim().equals("0"))
//                   ||(cPesAcc.trim()!="0"&&!cPesAcc.trim().equals("0")&&!cPesAcc.trim().equals("")&&cPesAcc!=null))
//                {
//                    t=1;
//                    boolean pFlag=true;
//                    if (tAccFlag=="10")
//                    {
//                      pFlag = checkPAccount(cGrpContNo, cAccAmt);
//                    }
                    if (!pFlag )
                    {
                        CError tError = new CError();
                        tError.moduleName = "GrpCustomerGuideForAccIn";
                        tError.functionName = "ParseXML";
                        tError.errorMessage = "团体保单号[" + cGrpContNo +
                                              "]下的帐户余额小于录入的团体帐户赔付金额!";
                        tGrpCustomerParser.mErrors.addOneError(tError);
                    }
                    else
                    {
                        tLLCaseVData = new VData(); //出险信息结果集
                        tLLCaseVData = tGrpCustomerParser.getLLCaseDataForAcc(
                                node);
                    }
                }
                else
                {
                        //t=0;
                        tLLCaseVData = new VData(); //出险信息结果集
                        tLLCaseVData = tGrpCustomerParser.getLLCaseDataForAcc(
                                node);
                }
            }
            if (tLLCaseVData != null && tLLCaseVData.size() > 0)
            {
//                logger.debug("tllcaseVdate !=null!");
                tLLCaseVData.add(mGlobalInput);
                ID = (String) tLLCaseVData.getObjectByObjectName("String", 0);
                customerNo = ((String) tLLCaseVData.getObjectByObjectName("String", 1)).trim();
                customerName = (String) tLLCaseVData.getObjectByObjectName("String", 2);
                flagRgtNo= (String) tLLCaseVData.getObjectByObjectName("String", 3);



                if(!flagRgtNo.equals(""))
                {
                    mRgtNo=flagRgtNo;
                }


                //add by wood 增加对没有赔案号的校验
                if(!rRgtNo.equals(mRgtNo)){
                	 mContent = "导入失败，出险人("+ cCustomerNo + ":"+ cCustomerName + ")对应赔案号输入不正确！";
                     return false;
                }

              //add by wood 增加对没有报案数据的出险人校验，因为立案可以删除出险人
                String StrSQL = " select count(*) from llsubreport  where"
                  + " subrptno='" + mRgtNo +"' and customerno='" + cCustomerNo + "'";
                ExeSQL myExeSQL = new ExeSQL();
                String tCont = myExeSQL.getOneValue(StrSQL);
                if (tCont.equals("0")) {
                 mContent = "导入失败，出险人("+ cCustomerNo + ":"+ cCustomerName + ")没有报案信息！";
                 return false;
                 }
                LLGrpClaimRegisterBL tLLGrpClaimRegisterBL = new
                        LLGrpClaimRegisterBL();
                /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 No.3.2 准备数据,分别向 llcase,llaccident,llclaimaccount,llappclaimreason,
                        llregister ,llfeemain ,llcasereceipt 这些表中提交数据
                 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
                LLCaseSchema tLLCaseSchema = new LLCaseSchema();
                tLLCaseSchema = (LLCaseSchema) tLLCaseVData.
                                getObjectByObjectName(
                                        "LLCaseSchema", 0);

                if (tLLCaseSchema != null &&
                    !tLLCaseSchema.getCustomerNo().equals(""))
                {
                    if (mRgtNo != null && !mRgtNo.equals(""))
                    {
                        if(AccNo==null||AccNo.equals(""))
                        {
                            String sql="";
                            sql="select CaseRelaNo from llcaserela where caseno='"+mRgtNo+"'";
                            ExeSQL tExeSQL=new ExeSQL();
                            AccNo = tExeSQL.getOneValue(sql);
                        }

                        LLAccidentSchema tLLAccidentSchema = new
                                LLAccidentSchema(); //事件表
                        LLClaimAccountSet tLLClaimAccountSet = new
                                LLClaimAccountSet(); //帐户表
                        LLClaimAccountSet tLLClaimAccountSet2 = new
                                LLClaimAccountSet();
                        LLFeeMainSchema tLLFeeMainSchema = new LLFeeMainSchema(); //帐单信息主表
                        LLCaseReceiptSchema tLLCaseReceiptSchema = new
                                LLCaseReceiptSchema(); //帐单信息表
                        LLAppClaimReasonSet tLLAppClaimReasonSet = new
                                LLAppClaimReasonSet(); //理赔类型集合
                        LLAppClaimReasonSet tLLAppClaimReasonSet2 = new
                                LLAppClaimReasonSet(); //理赔类型集合
                        tLLCaseSchema = (LLCaseSchema) tLLCaseVData.
                                        getObjectByObjectName("LLCaseSchema", 0);
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
                        tLLClaimAccountSet = (LLClaimAccountSet) tLLCaseVData.
                                             getObjectByObjectName(
                                "LLClaimAccountSet", 0);
                        tLLAccidentSchema.setAccNo(AccNo); //事件号
                        tLLRegisterSchema.setRgtNo(mRgtNo); //报案号=赔案号
                        tLLCaseSchema.setCaseNo(mRgtNo); //分案号=报案号=赔案号
                        mLLCaseSet.add(tLLCaseSchema); //用于帐单客户号保存
                        double tAccPayMoney = 0; //个人和团体总的赔付金额
                        String tCustomerNo = ""; //客户号
                        String tGrpContNo = "";

                        for (int n = 1; n <= tLLClaimAccountSet.size(); n++)
                        {
                            LLClaimAccountSchema tLLClaimAccountSchema = new
                                    LLClaimAccountSchema();
                            tLLClaimAccountSchema = tLLClaimAccountSet.get(n);
                            tLLClaimAccountSchema.setClmNo(mRgtNo);
                            tLLClaimAccountSchema.setSerialNo(mBatchNo);
                            tLLClaimAccountSchema.setKindCode("1");
                            tLLClaimAccountSet2.add(tLLClaimAccountSchema);
                            tAccPayMoney += tLLClaimAccountSchema.
                                    getAccPayMoney(); //个人赔付金额+团体赔付金额
                            tCustomerNo = tLLClaimAccountSchema.getDeclineNo(); // 客户号
                            tGrpContNo = tLLClaimAccountSchema.getGrpContNo(); //团体保单号
                        }

                        logger.debug("开始写表LLCaseReceipt" + tAccPayMoney);
                        // 在帐单导入时，如果个人帐户赔付或者团体帐户赔付有金额，那么要在llfeemain和
                        // llcasereceipt表中分别添加一条 mainfeeno = 0000000000 的记录(理算时需要)
                        if (tAccPayMoney > 0)
                        {
                        	logger.debug("开始写表LLCaseReceipt1" + tAccPayMoney);
                        	// 添加帐单主表信息
                            tLLFeeMainSchema.setCaseNo(mRgtNo); //赔案号
                            tLLFeeMainSchema.setClmNo(mRgtNo); //赔案号
                            tLLFeeMainSchema.setCustomerNo(tCustomerNo); //客户号
                            tLLFeeMainSchema.setMainFeeNo("0000000000");
                            // 添加帐单信息
                            logger.debug("开始写表LLCaseReceipt2" + tAccPayMoney);
                            tLLCaseReceiptSchema.setClmNo(mRgtNo); //赔案号
                            tLLCaseReceiptSchema.setCaseNo(mRgtNo); //赔案号
                            tLLCaseReceiptSchema.setMainFeeNo("0000000000"); //非明细帐单
                            tLLCaseReceiptSchema.setCustomerNo(tCustomerNo); //客户号
                            tLLCaseReceiptSchema.setRgtNo(mRgtNo); //赔案号
                            tLLCaseReceiptSchema.setFee(tAccPayMoney); //原始费用
                            tLLCaseReceiptSchema.setAdjSum(tAccPayMoney); //合理费用
                            logger.debug("开始写表LLCaseReceipt3" + tAccPayMoney);
                            tLLCaseVData.add(tLLFeeMainSchema);
                            tLLCaseVData.add(tLLCaseReceiptSchema);
                            logger.debug("看看就是有没有啊..2...");
                        }

                        //准备理赔类型表
                        for (int j = 1; j <= tLLAppClaimReasonSet.size(); j++)
                        {
                            LLAppClaimReasonSchema tLLAppClaimReasonSchema = new
                                    LLAppClaimReasonSchema(); //理赔类型表
                            tLLAppClaimReasonSchema = tLLAppClaimReasonSet.get(
                                    j);
                            tLLAppClaimReasonSchema.setCaseNo(mRgtNo); //报案号=赔案号
                            tLLAppClaimReasonSchema.setRgtNo(mRgtNo); //报案号=赔案号
                            tLLAppClaimReasonSet2.add(tLLAppClaimReasonSchema);
                        }
                        // 数据提交打包
                        tLLCaseVData.add(tLLCaseSchema);
                        tLLCaseVData.add(tLLRegisterSchema);
                        tLLCaseVData.add(tLLAccidentSchema);
                        tLLCaseVData.add(tLLAppClaimReasonSet2);
                        tLLCaseVData.add(tLLClaimAccountSet2);
                        tErrorState = tLLGrpClaimRegisterBL.submitData(
                                tLLCaseVData,
                                "ForAccInsert");
                    }
                    else
                    {
                        /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                         No.3.3 准备数据,生成报案信息 ,分别向 llcase,llaccident,
                                llregister 这些表中提交数据
                         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                         */
                        //单独处理第一条，如果有金额，则要生成llfeemain和llcasereceipt表信息
                        LLClaimAccountSet tLLClaimAccountSet = new
                                LLClaimAccountSet();
                        LLClaimAccountSet tLLClaimAccountSet2 = new
                                LLClaimAccountSet();
                        LLFeeMainSchema tLLFeeMainSchema = new LLFeeMainSchema();
                        LLCaseReceiptSchema tLLCaseReceiptSchema = new
                                LLCaseReceiptSchema();
                        double tAccPayMoney = 0.0;
                        String tCustomerNo = "";
                        tLLClaimAccountSet = (LLClaimAccountSet) tLLCaseVData.
                                             getObjectByObjectName(
                                "LLClaimAccountSet", 0);
                        for (int n = 1; n <= tLLClaimAccountSet.size(); n++)
                        {
                            LLClaimAccountSchema tLLClaimAccountSchema = new
                                    LLClaimAccountSchema();
                            tLLClaimAccountSchema = tLLClaimAccountSet.get(n);
                            tLLClaimAccountSchema.setClmNo(mRgtNo);
                            tLLClaimAccountSchema.setSerialNo(mBatchNo);
                            tLLClaimAccountSet2.add(tLLClaimAccountSchema);
                            tAccPayMoney += tLLClaimAccountSchema.
                                    getAccPayMoney(); //个人赔付金额+团体赔付金额
                            tCustomerNo = tLLClaimAccountSchema.getDeclineNo(); // 客户号
                        }
                        // 在帐单导入时，如果个人帐户赔付或者团体帐户赔付有金额，那么要在llfeemain和
                        // llcasereceipt表中分别添加一条 mainfeeno = 0000000000 的记录(理算时需要)
                        if (tAccPayMoney > 0 )
                        {
                            // 添加帐单主表信息
                            tLLFeeMainSchema.setCustomerNo(tCustomerNo); //客户号
                            tLLFeeMainSchema.setMainFeeNo("0000000000");
                            // 添加帐单信息
                            tLLCaseReceiptSchema.setMainFeeNo("0000000000"); //非明细帐单
                            tLLCaseReceiptSchema.setCustomerNo(tCustomerNo); //客户号
                            tLLCaseReceiptSchema.setFee(tAccPayMoney); //原始费用
                            tLLCaseReceiptSchema.setAdjSum(tAccPayMoney); //合理费用
                            tLLCaseVData.add(tLLFeeMainSchema);
                            tLLCaseVData.add(tLLCaseReceiptSchema);
                        }

                        tErrorState = tLLGrpClaimRegisterBL.submitData(
                                tLLCaseVData, "ForAccInsert");
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
                        tLLCaseSchema = (LLCaseSchema) tLLCaseVData.
                                        getObjectByObjectName("LLCaseSchema", 0);
                        mLLCaseSet.add(tLLCaseSchema);
                        mRgtNo = tLLRegisterSchema.getRgtNo(); //返回生成的赔案号
                        AccNo = tLLAccidentSchema.getAccNo(); //返回生成的事件号
                    }
                }
                /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                  No.3.4 准备数据,帐单录入 ,分别向 llfeemain,llcasereceipt 这些表中提交数据
                 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
//                if (tErrorState)
//                {
//                    NodeList nodeList1 = tXPT.parseN(ParsePath2);
////                    if (nodeList != null && nodeList.getLength() > 0)
////                    {
//                    int tNodeCount = 0;
//                    if (nodeList1 != null)
//                    {
//                        tNodeCount = nodeList1.getLength();
//                    }
//                    for (int k = 0; k < tNodeCount; k++)
//                    {
//                        Node tnode = nodeList1.item(k);
//                        tLLFeeVData = tGrpCustomerParser.getFeeVDataForAcc(
//                                tnode, mRgtNo, mLLCaseSet);
//                        tLLFeeVData.add(mGlobalInput);
//                        LLGrpMedicalFeeInpForAccBL tLLGrpMedicalFeeInpForAccBL = new
//                                LLGrpMedicalFeeInpForAccBL();
//                        tLLGrpMedicalFeeInpForAccBL.submitData(tLLFeeVData,
//                                "IMPORT||MAIN");
//                        FMap = tLLGrpMedicalFeeInpForAccBL.getMMap();
//
//                    }
////                    }
//                }
//                tMap.add(FMap);
            }
            logErrors = tGrpCustomerParser.mErrors;
            String a = Integer.toString(i + 1);
            if(customerNo.equals("")&&cCustomerNo!= null &&!cCustomerNo.equals(""))
            {
                 customerNo = cCustomerNo;
                 String tSql = "select name from ldperson where customerno = '"+cCustomerNo+"'";
                 ExeSQL tExeSQL = new ExeSQL();
                 SSRS ss = tExeSQL.execSQL(tSql);
                 if(ss.getMaxRow()>0)
                 {
                     customerName = ss.GetText(1, 1);
                 }
            }
            if( (cCustomerName != null && !cCustomerNo.equals(""))&&
                    customerName.equals("") )
            {
                customerName = cCustomerName;
            }
            if (logErrors != null && !logErrors.equals(""))
            {
                if (mRgtNo != null && !mRgtNo.equals(""))
                {
                    LogError(mRgtNo, mBatchNo, a, customerNo, customerName,
                             mGlobalInput.Operator, tErrorState, logErrors);
                }
                else
                {
                    LogError("000000", mBatchNo, a, customerNo,
                             customerName,
                             mGlobalInput.Operator, tErrorState, logErrors);
                }
                tMap.put(delLCGrpCustomerImportLogSchema, "DELETE");
                tMap.put(InLCGrpCustomerImportLogSchema, "INSERT");
            }
            PubSubmit tPubSubmit = new PubSubmit();
            mInputData.clear();
            mInputData.add(tMap);
            /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
              No.4 报案完成，分别向lLCGrpCustomerImportLog表中提交数据，提交成功的和不成功的记录
             －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            if (!tPubSubmit.submitData(mInputData, ""))
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "GrpCustomerGuideIn";
                tError.functionName = "parserXml";
                tError.errorMessage = "数据提交失败！客户号：" + cCustomerNo + "!";
                this.logErrors.addOneError(tError);
                // @@如果提交错误则添加错误日志
                LogError(mRgtNo, mBatchNo, ID, customerNo,
                         customerName,
                         mGlobalInput.Operator, tErrorState,
                         logErrors);
                tMap = null;
                tMap.put(delLCGrpCustomerImportLogSchema, "DELETE");
                tMap.put(InLCGrpCustomerImportLogSchema, "INSERT");
                mInputData.clear();
                mInputData.add(tMap);
                tPubSubmit.submitData(mInputData, "");
            }
            logErrors.clearErrors();

        }

        //分两个事务导入首先导入出险人信息在通过查先caseid找到客户号在导入帐单信息
        //帐单导入

            NodeList nodeList1 = tXPT.parseN(ParsePath2);
            int tNodeCount = 0;
            if (nodeList1 != null) {
                tNodeCount = nodeList1.getLength();
            }
            VData tLLFeeVData = new VData();
            MMap FMap = new MMap();
//            //先删掉以前导入的数据--不能删是因为有可能生成了 mainfeeno = 0000000000 的记录(理算时需要)；如果保留 mainfeeno = 0000000000 的记录，那么如果导入时要导入这些记录时会生成2条数据。
//            String Fsql = "delete from llfeemain where clmno='" + mRgtNo + "' ";
//            String Csql = "delete from llcasereceipt where clmno='" + mRgtNo + "' ";
//            FMap.put(Fsql, "DELETE");
//            FMap.put(Csql, "DELETE");
//            PubSubmit tfPubSubmit = new PubSubmit();
//            mInputData.clear();
//            mInputData.add(FMap);
//            if (!tfPubSubmit.submitData(mInputData, "")) {
//                // @@错误处理
//                CError tError = new CError();
//                tError.moduleName = "GrpCustomerGuideIn";
//                tError.functionName = "parserXml";
//                tError.errorMessage = "帐单信息提交失败!";
//                this.logErrors.addOneError(tError);
//        }

            for (int k = 0; k < tNodeCount; k++) {
                Node tnode = nodeList1.item(k);
//                String FeeID = parseNode(tnode, "FeeID"); //团体客户号
//                if (t==1&&(FeeID!=null||!FeeID.equals("")))
//                {
//                   mContent = "已经录入了团体或个人账户赔付金额，帐单信息不需要导入，请重新修改模板！";
//                   return false;
//                }

                tLLFeeVData = tGrpCustomerParser.getFeeVDataForAcc(tnode, mRgtNo, mLLCaseSet);
                if(tLLFeeVData!=null && tLLFeeVData.size()>0){
                tLLFeeVData.add(mGlobalInput);
                LLGrpMedicalFeeInpForAccBL tLLGrpMedicalFeeInpForAccBL = new
                        LLGrpMedicalFeeInpForAccBL();
                tLLGrpMedicalFeeInpForAccBL.submitData(tLLFeeVData,"IMPORT||MAIN");
                FMap = tLLGrpMedicalFeeInpForAccBL.getMMap();

                LLFeeMainSet    mLLFeeMainSet    = new LLFeeMainSet();
                LLFeeMainSchema mLLFeeMainSchema = new LLFeeMainSchema();

                mLLFeeMainSet = (LLFeeMainSet) tLLFeeVData.getObjectByObjectName("LLFeeMainSet", 0);
                mLLFeeMainSchema = mLLFeeMainSet.get(1);
                String tMainFeeNo = mLLFeeMainSchema.getMainFeeNo();
                String tCustomerNo = mLLFeeMainSchema.getCustomerNo();

                String strSql = "Select CustomerName from llcase where caseno = '" + mRgtNo + "' and CustomerNo ='"+tCustomerNo+"'";
                ExeSQL exesql = new ExeSQL();
                String tCustomerName = exesql.getOneValue(strSql);

                logErrors = tLLGrpMedicalFeeInpForAccBL.mErrors;
          if (FMap != null && !FMap.equals("") && tLLGrpMedicalFeeInpForAccBL.mErrors.getErrorCount()<=0) {
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
                }else{
              // @@如果提交错误则添加错误日志
              PubSubmit tPubSubmit = new PubSubmit();
              boolean tErrorState = false;
              LogError(mRgtNo, mBatchNo, tMainFeeNo, tCustomerNo, tCustomerName, mGlobalInput.Operator, tErrorState,logErrors);
              MMap tMap = new MMap();
              tMap.put(delLCGrpCustomerImportLogSchema, "DELETE");
              tMap.put(InLCGrpCustomerImportLogSchema, "INSERT");
              mInputData.clear();
              mInputData.add(tMap);
              tPubSubmit.submitData(mInputData, "");
          }
            }

            }

        if (this.mErrors.needDealError())
               {
            logger.debug(this.mErrors.getErrorCount() +
                               "error:" +
                               this.mErrors.getFirstError());
        }
        /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         No.5 解析完删除XML文件
               －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (!tFile.delete())
        {
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
                         String operator, boolean tErrorState, CErrors tErrors)
    {
        delLCGrpCustomerImportLogSchema =
                new LCGrpCustomerImportLogSchema();
        InLCGrpCustomerImportLogSchema =
                new LCGrpCustomerImportLogSchema();
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
        {
            errorMess = "未捕捉到错误";
             mTrue = mTrue + 1;
        }
        else
        {
             mFalse = mFalse + 1;
        }
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
        logger.debug("----GrpCustomerGuideIn initImportFile()------");
        /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         No.2 检查导入配置文件是否存在
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (!this.checkImportConfig())return false;
        logger.debug("-----GrpCustomerGuideIn checkImportConfig()-----");
        /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         No.3 指定导入文件名称
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        GrpCustomerVTSParser GCvp = new GrpCustomerVTSParser();
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
                "----------GrpCustomerGuideIn setFileName()----------");
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
        logger.debug("----GrpCustomerGuideIn transform()------");
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

        ConfigFileName = filePath + "GrpCustomerImportForAcc.xml";

        File tFile2 = new File(ConfigFileName);
        if (!tFile2.exists())
        {
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
    private boolean initImportFile()
    {
        this.getFilePath();
        ImportFileName = mPreFilePath + FilePath + FileName;
        File tFile = new File(ImportFileName);
        if (!tFile.exists())
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ParseGuideIn";
            tError.functionName = "checkData";
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
        return true;
    }

    /**
     * 前台数据输入校验
     * @return boolean
     */
    private boolean checkData()
    {
        if (mGlobalInput == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ParseGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "无操作员信息，请重新登录!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (mTransferData == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ParseGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "无导入文件信息，请重新导入!";
            this.mErrors.addOneError(tError);
            return false;
        }
        else
        {
            FileName = (String) mTransferData.getValueByName("FileName");
        }
        return true;
    }

    /**
     * getInputData
     */
    private void getInputData()
    {
        mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
                "GlobalInput",
                0);
        mTransferData = (TransferData) mInputData.getObjectByObjectName(
                "TransferData", 0);

        rRgtNo = (String) mTransferData.getValueByName("RgtNo");
        mRgtNo = (String) mInputData.getObjectByObjectName(
                "String", 0);
        mPreFilePath = (String) mTransferData.getValueByName("FilePath");
        logger.debug(mPreFilePath);
        logger.debug(mRgtNo);
        logger.debug("rRgtNo"+rRgtNo);

    }

    /**
     * 得到日志显示结果
     * @return VData
     */
    public VData getResult()
    {
        //mResult.add(mRgtNo);
        //带回导入成功或者失败的个数
        mReturnData.setNameAndValue("Succ",String.valueOf(mTrue));
        mReturnData.setNameAndValue("Fail",String.valueOf(mFalse));
        mReturnData.setNameAndValue("RgtNo",mRgtNo);
        mReturnData.setNameAndValue("Content",mContent);
        mResult.add(mReturnData);
        return mResult;
    }

    private boolean updateLCIsureAccClass(String nGrpContNo, String nInsuredNo)
    {
        String mCurrentDate = PubFun.getCurrentDate();
        String mCurrentTime = PubFun.getCurrentTime();
        MMap tMap = new MMap();
        VData tInputData = new VData();
        TransferData mReturnData = new TransferData(); //返回团体帐户金额
        TransferData mReturnData1 = new TransferData(); //返回个人帐户金额
        LCInsureAccClassSchema mLCInsureAccClassSchema1 = new
                LCInsureAccClassSchema(); //团体帐户
        LCInsureAccClassSchema mLCInsureAccClassSchema2 = new
                LCInsureAccClassSchema(); //个人帐户
        String tSql = "";
        double tSumPay1 = 0.0; //团体帐户金额
        double tSumPay2 = 0.0; //个人帐户金额
        String tBalaDate = PubFun.getCurrentDate(); //当前截息日期（系统日期）
        String tRateType = "Y"; //原始利率类型（）
        String tIntvType = "D"; //目标利率类型（日利率）
        int tPerio = 0; //银行利率期间
        String tType = "F"; //截息计算类型（单利还是复利）
        String tDepst = "D"; //贷存款标志（贷款还是存款）
        String pLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
        LCInsureAccClassSchema tLCInsureAccClassSchema = new
                LCInsureAccClassSchema();
        LCInsureAccClassSchema aLCInsureAccClassSchema = new
                LCInsureAccClassSchema();
        LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
        LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
        AccountManage tAccountManage = new AccountManage();
        AccountManage tAccountManage1 = new AccountManage();
        LCInsureAccClassDB tLCInsureAccClassDB1 = new LCInsureAccClassDB();
        LCInsureAccClassSet tLCInsureAccClassSet1 = new LCInsureAccClassSet();
//        String tContNo = "";//个人合同号
//        LCContDB tLCContDB =  new LCContDB();
//        tLCContDB.setGrpContNo(nGrpContNo);
//        tLCContDB.setPolType("2");
//        LCContSet tLCContSet = tLCContDB.query();
//        if(tLCContSet.size() > 0)
//        {
//            tContNo = tLCContSet.get(1).getContNo();
//        }
        String tPolNo = "";//个人合同号
        LCPolDB tLCPolDB =  new LCPolDB();
        tLCPolDB.setGrpContNo(nGrpContNo);
        tLCPolDB.setPolTypeFlag("2");
        LCPolSet tLCPolSet = tLCPolDB.query();
        if(tLCPolSet.size() > 0)
        {
            tPolNo = tLCPolSet.get(1).getPolNo();
        }
        //准备LCInsureAccClass表数据
        /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No.1 查询团体帐户，如果是当前天的记录，那么不用结息
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        tSql = "select * from lcinsureaccclass where grpcontno = '"+nGrpContNo + "'"
              +" and  baladate = '" + tBalaDate + "'"
              +" and polno = '"+tPolNo+"'";
        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = tExeSQL.execSQL(tSql);
        if (tSSRS.getMaxRow() < 1)
        {
            /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No.2 团体帐户截息
             －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            tLCInsureAccClassDB.setGrpContNo(nGrpContNo);
            tLCInsureAccClassDB.setPolNo(tPolNo);
            tLCInsureAccClassSet = tLCInsureAccClassDB.query();
            if (tLCInsureAccClassSet == null ||
                tLCInsureAccClassSet.size() == 0)
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "GrpCustomerGuideForAccIn";
                tError.functionName = "updateLCIsureAccClass";
                tError.errorMessage = "查询LCInsureAccClass表信息失败!";
                this.mErrors.addOneError(tError);
                return false;
            }
            aLCInsureAccClassSchema = tLCInsureAccClassSet.get(1);
            String pSerialNo = PubFun1.CreateMaxNo("SERIALNO", pLimit);
            tSql = "insert into lcclass_temp values('" +
                   aLCInsureAccClassSchema.getGrpContNo() + "','" +
                   aLCInsureAccClassSchema.getGrpPolNo() + "','" +
                   aLCInsureAccClassSchema.getPolNo() + "','" +
                   aLCInsureAccClassSchema.getContNo() + "','" +
                   aLCInsureAccClassSchema.getInsuredNo() + "','" +
                   aLCInsureAccClassSchema.getAccType() + "','" +
                   aLCInsureAccClassSchema.getRiskCode() + "','" +
                   aLCInsureAccClassSchema.getInsuAccNo() + "','" +
                   aLCInsureAccClassSchema.getPayPlanCode() + "','" +
                   aLCInsureAccClassSchema.getBalaDate() + "','" +
                   aLCInsureAccClassSchema.getBalaTime() + "'," +
                   aLCInsureAccClassSchema.getLastAccBala() + ",'" +
                   aLCInsureAccClassSchema.getOperator() + "','" +
                   mCurrentTime + "','" + mCurrentDate + "','" +
                   mCurrentDate +
                   "','" + mCurrentTime + "','" + pSerialNo + "')";
            tMap.put(tSql, "INSERT");
            mReturnData = tAccountManage.getAccClassInterestNew(
                    aLCInsureAccClassSchema,
                    tBalaDate,
                    tRateType,
                    tIntvType,
                    tPerio,
                    tType,
                    tDepst);
            if (mReturnData != null)
            {
                String tempmoney = String.valueOf(mReturnData.getValueByName(
                        "aAccClassSumPay"));
                tSumPay1 = Double.parseDouble(tempmoney);
                if(tSumPay1 < 0)
                    tSumPay1 = 0.00;
                tempmoney = String.valueOf(mReturnData.getValueByName(
                        "aAccClassInterest"));
                double tInterest = Double.parseDouble(tempmoney);
            }
            else
            {
                tSumPay1 = 0.0;
            }
            /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No.3 更新团体帐户LCInsureAccClass表信息
             －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            mLCInsureAccClassSchema1.setSchema(aLCInsureAccClassSchema);
            mLCInsureAccClassSchema1.setBalaDate(tBalaDate);
            mLCInsureAccClassSchema1.setBalaTime(PubFun.getCurrentTime());
            mLCInsureAccClassSchema1.setLastAccBala(tSumPay1); //团体帐户金额
            mLCInsureAccClassSchema1.setInsuAccBala(tSumPay1); //团体帐户金额
            mLCInsureAccClassSchema1.setModifyDate(PubFun.getCurrentDate());
            mLCInsureAccClassSchema1.setModifyTime(PubFun.getCurrentTime());
            tMap.put(mLCInsureAccClassSchema1, "DELETE&INSERT");
        }
        /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No.4 查询个人帐户，如果是当前天的记录，那么不用结息
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        //如果没有个人帐户则不用结息
        String ttPolNo = "";//个人合同号
        LCPolDB ttLCPolDB =  new LCPolDB();
        ttLCPolDB.setGrpContNo(nGrpContNo);
        ttLCPolDB.setInsuredNo(nInsuredNo);
        LCPolSet ttLCPolSet = ttLCPolDB.query();
        if(ttLCPolSet.size() > 0)
        {
            ttPolNo = ttLCPolSet.get(1).getPolNo();
        }
        tSql = "select lastaccbala from lcinsureaccclass where polno = '" +
               ttPolNo + "' ";
        SSRS tSSRS2 = tExeSQL.execSQL(tSql);
        if (tSSRS2.getMaxRow() < 1)
        {
            return true;
        }

        tSql = "select * from lcinsureaccclass where polno = '" +
               ttPolNo + "' and baladate = '" + tBalaDate + "'";
        SSRS tSSRS1 = tExeSQL.execSQL(tSql);
        if (tSSRS1.getMaxRow() < 1)
        {
            /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No.5 个人帐户截息
             －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            tLCInsureAccClassDB1.setGrpContNo(nGrpContNo);
            tLCInsureAccClassDB1.setPolNo(ttPolNo);
            tLCInsureAccClassSet1 = tLCInsureAccClassDB1.query();
            if (tLCInsureAccClassSet1.size() == 0 || tLCInsureAccClassSet1 == null)
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "GrpCustomerGuideForAccIn";
                tError.functionName = "updateLCIsureAccClass";
                tError.errorMessage = "查询LCInsureAccClass表信息失败!";
                this.mErrors.addOneError(tError);
                return false;
            }
            tLCInsureAccClassSchema = tLCInsureAccClassSet1.get(1);

            //增加一个临时表，来记录上次个人帐户class表的记录
            String pSerialNo = PubFun1.CreateMaxNo("SERIALNO", pLimit);
            tSql = "insert into lcclass_temp values('" +
                   tLCInsureAccClassSchema.getGrpContNo() + "','" +
                   tLCInsureAccClassSchema.getGrpPolNo() + "','" +
                   tLCInsureAccClassSchema.getPolNo() + "','" +
                   tLCInsureAccClassSchema.getContNo() + "','" +
                   tLCInsureAccClassSchema.getInsuredNo() + "','" +
                   tLCInsureAccClassSchema.getAccType() + "','" +
                   tLCInsureAccClassSchema.getRiskCode() + "','" +
                   tLCInsureAccClassSchema.getInsuAccNo() + "','" +
                   tLCInsureAccClassSchema.getPayPlanCode() + "','" +
                   tLCInsureAccClassSchema.getBalaDate() + "','" +
                   tLCInsureAccClassSchema.getBalaTime() + "'," +
                   tLCInsureAccClassSchema.getLastAccBala() + ",'" +
                   tLCInsureAccClassSchema.getOperator() + "','" +
                   mCurrentTime + "','" + mCurrentDate + "','" +
                   mCurrentDate +
                   "','" + mCurrentTime + "','" + pSerialNo + "')";
            tMap.put(tSql, "INSERT");

            mReturnData1 = tAccountManage1.getAccClassInterestNew(
                    tLCInsureAccClassSchema,
                    tBalaDate,
                    tRateType,
                    tIntvType,
                    tPerio,
                    tType,
                    tDepst);
            if (!mReturnData1.equals("") || mReturnData1 != null)
            {
                String tempmoney = String.valueOf(mReturnData1.getValueByName(
                        "aAccClassSumPay"));
                tSumPay2 = Double.parseDouble(tempmoney);
                if(tSumPay2 < 0)
                    tSumPay2 = 0.00;
                tempmoney = String.valueOf(mReturnData1.getValueByName(
                        "aAccClassInterest"));
                double tInterest = Double.parseDouble(tempmoney);
            }
            else
            {
                tSumPay2 = 0.0;
            }
            /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No.6 更新个人帐户LCInsureAccClass表信息
             －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            mLCInsureAccClassSchema2.setSchema(tLCInsureAccClassSchema);
            mLCInsureAccClassSchema2.setBalaDate(tBalaDate);
            mLCInsureAccClassSchema2.setBalaTime(PubFun.getCurrentTime());
            mLCInsureAccClassSchema2.setLastAccBala(tSumPay2); //个人帐户金额
            mLCInsureAccClassSchema2.setInsuAccBala(tSumPay2); //个人帐户金额
            mLCInsureAccClassSchema2.setModifyDate(PubFun.getCurrentDate());
            mLCInsureAccClassSchema2.setModifyTime(PubFun.getCurrentTime());
            tMap.put(mLCInsureAccClassSchema2, "DELETE&INSERT");
        }
        mInputData.add(tMap);
        tInputData.add(tMap);
        PubSubmit nPubSubmit = new PubSubmit();
        if (!nPubSubmit.submitData(tInputData, ""))
        {
            CError tError = new CError();
            tError.moduleName = "GrpCustomerGuideForAccIn";
            tError.functionName = "updateLCIsureAccClass";
            tError.errorMessage = "更新LCInsureAccClass表信息失败!";
            this.mErrors.addOneError(tError);
            return false;
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

    /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     * 检查导入的帐单中[团体赔付金额]
     －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    private boolean checkPAccount(String dGrpContNo,String dAccAmt)
    {
        String cSql = "";
        ExeSQL tExeSQL = new ExeSQL();
        if (dAccAmt != null && !dAccAmt.equals("") && !dAccAmt.equals("0"))
        {
            cSql =
                    "select lastaccbala from lcinsureaccclass where polno in ( select polno from lcpol where grpcontno = '" +
                    dGrpContNo + "' and poltypeflag='2')";

            SSRS cSSRS = tExeSQL.execSQL(cSql);
            if (cSSRS.getMaxRow() < 1)
            {
                CError tError = new CError();
                tError.moduleName = "GrpCustomerGuideForAccIn";
                tError.functionName = "ParseXML";
                tError.errorMessage = "没有找到团体保单号[" + dGrpContNo +
                                      "]下的帐户信息!";
                this.mlogErrors.addOneError(tError);
                return false;
            }
            if (Double.parseDouble(cSSRS.GetText(1, 1)) <
                Double.parseDouble(dAccAmt))
            {
                return false;
            }
        }
        return true;
    }

  public static void main(String[] args)
  {
       GrpCustomerGuideForAccIn tPGI = new GrpCustomerGuideForAccIn();
       VData tVData = new VData();
       TransferData tTransferData = new TransferData();
       tTransferData.setNameAndValue("FileName", "20060213.xls");
       tTransferData.setNameAndValue("FilePath", "d:");
       GlobalInput tG = new GlobalInput();
       tG.Operator = "claim";
       tG.ManageCom = "86";
       tG.ComCode = "0501";

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

public CErrors getErrors() {
	// TODO Auto-generated method stub
	return mErrors;
}


}
