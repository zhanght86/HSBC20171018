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
import com.sinosoft.service.BusinessService;
public class GrpClaimSimpleGuideIn implements BusinessService
{
private static Logger logger = Logger.getLogger(GrpClaimSimpleGuideIn.class);

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
    private String ParsePath3 = "/DATASET/LLCLAIMDETAILTABLE/ROW";

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
    private LCGrpCustomerImportLogSchema delLCGrpCustomerImportLogSchema =
            new LCGrpCustomerImportLogSchema();
    private LCGrpCustomerImportLogSchema InLCGrpCustomerImportLogSchema =
            new LCGrpCustomerImportLogSchema();

    private LLClaimDetailSet mLLClaimDetailSet = new LLClaimDetailSet();
    private LLClaimPolicySet mLLClaimPolicySet = new LLClaimPolicySet();
    private LLClaimDutyKindSet mLLClaimDutyKindSet = new LLClaimDutyKindSet();
    private LLBalanceSet mLLBalanceSet = new LLBalanceSet();
    private LLBnfSet mLLBnfSet = new LLBnfSet();
    private LJSGetClaimSet mLJSGetClaimSet = new LJSGetClaimSet();


    private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
    private MMap mMMap = new MMap();


    public GrpClaimSimpleGuideIn()
    {
        bulidDocument();
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public boolean submitData(VData cInputData, String cOperate)
    {
        CErrors mErrors = new CErrors();
        mInputData = (VData) cInputData.clone();
        //得到数据
        getInputData();
        logger.debug(
                "----------GrpClaimSimpleGuideIn after getInputData----------");
        //检查数据
        if (!checkData())
        {
            return false;
        }
        logger.debug(
                "----------GrpClaimSimpleGuideIn after checkData----------");
        logger.debug("开始时间:" + PubFun.getCurrentTime());
        try
        {
            //把Excel转化为Xml
            if (!parseVts())
            {
                return false;
            }
            logger.debug(
                    "----------GrpClaimSimpleGuideIn after parseVts----------");
            for (int nIndex = 0; nIndex < m_strDataFiles.length; nIndex++)
            {
                //把Xml转化为Schema
                XmlFileName = m_strDataFiles[nIndex];
                if (!ParseXml())
                {
                    return false;
                }
            }
            logger.debug(
                    "----------GrpClaimSimpleGuideIn after ParseXml----------");
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
        if (mErrors.getErrorCount() > 0)
        {
            return false;
        }

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
            {
                FilePath = tLDSysVarDB.getSysVarValue();
            }

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
        if (!checkXmlFileName())
        {
            return false;
        }
        File tFile = new File(XmlFileName);
        XMLPathTool tXPT = new XMLPathTool(XmlFileName);
        this.mErrors.clearErrors();

        String tBatchNo = "";
        try
        {
            //批次号
            tBatchNo = tXPT.parseX(ParseRootPath).
                       getFirstChild().getNodeValue();
            mBatchNo = tBatchNo;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        NodeList nodeList = tXPT.parseN(ParsePath);
        int nNodeCount = 0;

        if (nodeList != null)
        {
            nNodeCount = nodeList.getLength();
        }

        GrpCustomerParser tGrpCustomerParser = new GrpCustomerParser();
        //单次提交
        for (int i = 0; i < nNodeCount; i++)
        {
            Node node = nodeList.item(i);
            mLLCaseSet = new LLCaseSet();

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
            {
                continue;
            }
            if (nodeName.equals("ROW"))
            {
                //解析案件XML
                tLLCaseVData = new VData(); //出险信息/帐单信息的结果集
                tLLCaseVData = tGrpCustomerParser.getLLCaseData(node);
            }
            if (tLLCaseVData != null && tLLCaseVData.size() > 0)
            {
                tLLCaseVData.add(mGlobalInput);
                ID = (String) tLLCaseVData.getObjectByObjectName("String", 0);
                customerNo = ((String) tLLCaseVData.getObjectByObjectName(
                        "String", 1)).trim();
                customerName = (String) tLLCaseVData.getObjectByObjectName(
                        "String", 2);
                flagRgtNo= (String) tLLCaseVData.getObjectByObjectName("String", 3);
               if(!flagRgtNo.equals(""))
               {
                   mRgtNo=flagRgtNo;
               }


                LLGrpClaimRegisterBL tLLGrpClaimRegisterBL = new
                        LLGrpClaimRegisterBL();
                //准备数据
                LLCaseSchema tLLCaseSchema = new LLCaseSchema();
                tLLCaseSchema = (LLCaseSchema) tLLCaseVData.
                                getObjectByObjectName("LLCaseSchema", 0);
                if (tLLCaseSchema != null &&
                    !tLLCaseSchema.getCustomerNo().equals(""))
                {
                    if (mRgtNo != null && !mRgtNo.equals(""))
                    {
                        //LLCaseSchema tLLCaseSchema = new LLCaseSchema();
                        if(AccNo==null||AccNo.equals(""))
                        {
                            String sql="";
                            sql="select CaseRelaNo from llcaserela where caseno='"+mRgtNo+"'";
                            ExeSQL tExeSQL=new ExeSQL();
                            AccNo = tExeSQL.getOneValue(sql);
                        }

                        LLAccidentSchema tLLAccidentSchema = new
                                LLAccidentSchema(); //事件表
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
                        tLLAccidentSchema.setAccNo(AccNo); //事件号
                        tLLRegisterSchema.setRgtNo(mRgtNo); //报案号=赔案号
                        tLLCaseSchema.setCaseNo(mRgtNo); //分案号=报案号=赔案号
                        mLLCaseSet.add(tLLCaseSchema); //用于帐单客户号保存
                        logger.debug(tLLRegisterSchema.getAccidentDate());
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
                        tLLCaseVData.add(tLLCaseSchema);
                        tLLCaseVData.add(tLLRegisterSchema);
                        tLLCaseVData.add(tLLAccidentSchema);
                        tLLCaseVData.add(tLLAppClaimReasonSet2);

//                tErrorState = tLLGrpClaimRegisterBL.submitData(tLLCaseVData,
//                        "IMPORT||MAIN");
                        tErrorState = tLLGrpClaimRegisterBL.submitData(
                                tLLCaseVData,
                                "INSERT||TOSIMALL");
                    }
                    else
                    {
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
                        //LLCaseSchema tLLCaseSchema = new LLCaseSchema();
                        tLLCaseSchema = (LLCaseSchema) tLLCaseVData.
                                        getObjectByObjectName("LLCaseSchema", 0);
                        mLLCaseSet.add(tLLCaseSchema);
                        mRgtNo = tLLRegisterSchema.getRgtNo(); //返回生成的赔案号
                        AccNo = tLLAccidentSchema.getAccNo(); //返回生成的事件号
                    }
                }

//                //帐单录入
//                if (tErrorState)
//                {
//                    //tMap = tLLGrpClaimRegisterBL.getMMap();
//
//                    NodeList nodeList1 = tXPT.parseN(ParsePath2);
//                    if (nodeList != null && nodeList.getLength() > 0)
//                    {
//                        tLLFeeVData = tGrpCustomerParser.getFeeVData(nodeList1,
//                                mRgtNo, mLLCaseSet);
//
//                        tLLFeeVData.add(mGlobalInput);
//                        LLGrpMedicalFeeInpBL tLLGrpMedicalFeeInpBL = new
//                                LLGrpMedicalFeeInpBL();
//                        tLLGrpMedicalFeeInpBL.submitData(tLLFeeVData,
//                                "IMPORT||MAIN");
//                        FMap = tLLGrpMedicalFeeInpBL.getMMap();
//                    }
//                }
//                tMap.add(FMap);

//===ADD by zhoulei===增加理算信息处理=====2006-1-17 13:29========================BEG
//                NodeList nodeList3 = tXPT.parseN(ParsePath3);
//                if (nodeList3 == null || nodeList3.getLength() <= 0)
//                {
//                    //errors
//                    mErrors.addOneError("未找到理算明细信息，请检查导入模板是否正确！");
//                    return false;
//                }
//                else
//                {
//                    VData tVData = tGrpCustomerParser.getClaimCalVData(
//                            nodeList3, mRgtNo,
//                            mLLCaseSet);
//                    mLLClaimDetailSet = (LLClaimDetailSet) tVData.
//                                        getObjectByObjectName(
//                                                "LLClaimDetailSet", 0);
//                    if (mLLClaimDetailSet != null &&
//                        mLLClaimDetailSet.size() > 0)
//                    {
//                        //补充操作人和操作机构
//                        for (int j = 1; j <= mLLClaimDetailSet.size(); j++)
//                        {
//                            mLLClaimDetailSet.get(j).setOperator(mGlobalInput.
//                                    Operator);
//                            mLLClaimDetailSet.get(j).setMngCom(mGlobalInput.
//                                    ManageCom);
//                        }
//
//                        //首先提交llclaimdetail,方便后面汇总
//                        MMap tempMap = new MMap();
//                        VData tempVData = new VData();
//                        //更改案件类型为 03非理算理赔
//                        String Sql = " update LLRegister set RgtState = '03' , ClmState = '25'"
//                                   + " where RgtNo = '" + mRgtNo + "'";
//                        tempMap.put(mLLClaimDetailSet, "INSERT");
//                        tempMap.put(Sql,"UPDATE");
//                        tempVData.add(tempMap);
//                        if (!pubSubmit(tempVData))
//                        {
//                            //errors
//                            mErrors.addOneError("数据提交失败，理赔明细信息保存失败！");
//                            return false; //基础数据失败退出
//                        }
//                    }
//
//                }
//===ADD by zhoulei===增加理算信息处理=====2006-1-17 13:29========================END
            }

            //logErrors = tLLGrpClaimRegisterBL.mErrors;
            //logErrors.clearErrors();
            logErrors = tGrpCustomerParser.mErrors;
            String a = Integer.toString(i + 1);
            if (logErrors != null && !logErrors.equals(""))
            {
                if (mRgtNo != null && !mRgtNo.equals(""))
                {
                    LogError(mRgtNo, mBatchNo, a, customerNo, customerName,
                             mGlobalInput.Operator, tErrorState, logErrors);
                }
                else
                {
                    LogError("000000", mBatchNo, a, customerNo, customerName,
                             mGlobalInput.Operator, tErrorState, logErrors);
                }

                tMap.put(delLCGrpCustomerImportLogSchema, "DELETE");
                tMap.put(InLCGrpCustomerImportLogSchema, "INSERT");
            }
            String Sql = " update LLRegister set RgtState = '03' , ClmState = '25'"
                   + " where RgtNo = '" + mRgtNo + "'";
            tMap.put(Sql,"UPDATE");
            PubSubmit tPubSubmit = new PubSubmit();
            mInputData.clear();
            mInputData.add(tMap);
            if (!tPubSubmit.submitData(mInputData, ""))
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "GrpClaimSimpleGuideIn";
                tError.functionName = "parserXml";
                tError.errorMessage = "数据提交失败！客户号：" + customerNo + "!";
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

        }
        //分事务导入首先导入出险人信息在通过查先caseid找到客户号在导入帐单信息
         //帐单导入
             VData tLLFeeVData = new VData();
             MMap FMap = new MMap();
             NodeList nodeList1 = tXPT.parseN(ParsePath2);
             int tNodeCount = 0;
             if (nodeList1 != null) {
                 tNodeCount = nodeList1.getLength();
             }
             for (int k = 0; k < tNodeCount; k++) {
                 Node tnode = nodeList1.item(k);
                 tLLFeeVData = tGrpCustomerParser.getFeeVDataNew( tnode, mRgtNo);
                 tLLFeeVData.add(mGlobalInput);
//                 LLGrpMedicalFeeInpBL tLLGrpMedicalFeeInpBL = new LLGrpMedicalFeeInpBL();
//                 tLLGrpMedicalFeeInpBL.submitData(tLLFeeVData, "IMPORT||MAIN");
//                 FMap = tLLGrpMedicalFeeInpBL.getMMap();
                 LLGrpMedicalFeeInpNewBL tLLGrpMedicalFeeInpNewBL = new LLGrpMedicalFeeInpNewBL();
                 tLLGrpMedicalFeeInpNewBL.submitData(tLLFeeVData, "IMPORT||MAIN");
                 FMap = tLLGrpMedicalFeeInpNewBL.getMMap();
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

//分事务处理 理算信息处理
      NodeList nodeList3 = tXPT.parseN(ParsePath3);
if (nodeList3 == null || nodeList3.getLength() <= 0)
{
    //errors
    mErrors.addOneError("未找到理算明细信息，请检查导入模板是否正确！");
    return false;
}
else
{
    int LNodeCount = nodeList3.getLength();
    for (int y = 0; y < LNodeCount; y++){
    Node tnode = nodeList3.item(y);
    VData tVData = tGrpCustomerParser.getClaimCalVData(tnode,mRgtNo);
    mLLClaimDetailSet = (LLClaimDetailSet) tVData.
                        getObjectByObjectName(
                                "LLClaimDetailSet", 0);
    if (mLLClaimDetailSet != null &&
        mLLClaimDetailSet.size() > 0)
    {
        //补充操作人和操作机构
        for (int j = 1; j <= mLLClaimDetailSet.size(); j++)
        {
            mLLClaimDetailSet.get(j).setOperator(mGlobalInput.
                    Operator);
            mLLClaimDetailSet.get(j).setMngCom(mGlobalInput.
                    ManageCom);
        }

        //首先提交llclaimdetail,方便后面汇总
        MMap tempMap = new MMap();
        VData tempVData = new VData();
        //更改案件类型为 03非理算理赔
//        String Sql = " update LLRegister set RgtState = '03' , ClmState = '25'"
//                   + " where RgtNo = '" + mRgtNo + "'";
        tempMap.put(mLLClaimDetailSet, "INSERT");
//        tempMap.put(Sql,"UPDATE");
        tempVData.add(tempMap);
        if (!pubSubmit(tempVData))
        {
            //errors
            mErrors.addOneError("数据提交失败，理赔明细信息保存失败！");
            return false; //基础数据失败退出
        }
    }
    }
}

//===ADD by zhoulei===增加理算信息处理=====2006-1-17 13:29========================BEG
        //进行理算其他信息的处理
        if (!dealLLClaimCal())
        {
            return false;
        }
//===ADD by zhoulei===增加理算信息处理=====2006-1-17 13:29========================END

        if (this.mErrors.needDealError())
        {
            logger.debug(this.mErrors.getErrorCount() +
                               "error:" +
                               this.mErrors.getFirstError());
        }

        //解析完删除XML文件
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

//===ADD by zhoulei===增加理算信息处理=====2006-1-17 13:29========================BEG

    /**
     * 根据取得的LLclaimdetail表的信息来生成其他表信息
     * 前提：llclaimdetail已经存在数据
     * 涉及表：llclaimpolicy,llclaim,llclaimdutykind,llbalance
     *        llbnf,ljsget,ljsgetclaim
     */
    public boolean dealLLClaimCal()
    {
        /**---------------------------------------------------------------------
         * No1 理算相关表
         *----------------------------------------------------------------------*/
        //险种层面费用信息llclaimpolicy
        if (!setLLClaimPolicy())
        {
            //errors
            return false;
        }
        //理赔类型层面llclaimdutykind
        if (!setLLClaimDutyKind())
        {
            //errors
            return false;
        }
        //赔案层面llclaim
        if (!setLLClaim())
        {
            //errors
            return false;
        }
        //理赔结算表llbalance
        if (!setLLBalance())
        {
            //errors
            return false;
        }
        //理赔费用信息LLClaimDutyFee
        if (!setLLClaimDutyFee())
        {
            //errors
            return false;
        }


        /**---------------------------------------------------------------------
         * No2 受益人及财务相关表
         * 进行llbnf、ljsget、ljsgetclaim表数据的生成
         * 由于复核时也做处理，在此注掉
         *----------------------------------------------------------------------*/
//        if (!setLLBnf())
//        {
//            //errors
//            return false;
//        }

        /**---------------------------------------------------------------------
         * No4 更改赔案状态为“25”，供虚拟工作队列使用
         *----------------------------------------------------------------------*/
        String tUpdate = "update llregister set clmstate = '25' "
                       + " where rgtno = '" + this.mRgtNo + "'";
        mMMap.put(tUpdate, "UPDATE");

        /**---------------------------------------------------------------------
         * No4 未提交数据在此一次性提交
         *----------------------------------------------------------------------*/
        mInputData.clear();
        mInputData.add(mMMap);
        if (!pubSubmit(mInputData))
        {
            //errors
            mErrors.addOneError("理算信息保存失败！");
            return false;
        }

        return true;
    }

    /**
     * 进行llclaimpolicy表数据的生成
     * @return true or false
     */
    private boolean setLLClaimPolicy()
    {
        String tSql = "select polno,getdutykind,sum(standpay),sum(realpay)"
                    + " from llclaimdetail where 1=1 "
                    + " and clmno = '" + this.mRgtNo + "'"
                    + " group by clmno,polno,getdutykind";
        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = tExeSQL.execSQL(tSql);
        if (tSSRS.getMaxRow() <= 0)
        {
            //errors
            mErrors.addOneError("查询赔案给付责任信息失败！");
            return false;
        }
        mLLClaimPolicySet = new LLClaimPolicySet();
        for (int i = 1; i <= tSSRS.getMaxRow(); i++)
        {
            String tPolNo = tSSRS.GetText(i, 1); //保单险种号
            String tGetDutyKind = tSSRS.GetText(i, 2); //理赔类型
            String tStandPay = tSSRS.GetText(i, 3); //理算金额
            String tRealPay = tSSRS.GetText(i, 4); //实赔金额

            LLClaimPolicySchema tLLClaimPolicySchema = new LLClaimPolicySchema();

            tLLClaimPolicySchema.setClmNo(this.mRgtNo); //赔案号
            tLLClaimPolicySchema.setCaseNo(this.mRgtNo); //分案号
            tLLClaimPolicySchema.setCaseRelaNo(this.mRgtNo); //事件号
            tLLClaimPolicySchema.setRgtNo(this.mRgtNo);    //赔案号
            tLLClaimPolicySchema.setPolNo(tPolNo); //保单号
            tLLClaimPolicySchema.setGetDutyKind(tGetDutyKind); //理赔类型

            tLLClaimPolicySchema.setStandPay(tStandPay);
            tLLClaimPolicySchema.setRealPay(tRealPay);
            tLLClaimPolicySchema.setGiveType("0");
            tLLClaimPolicySchema.setGiveTypeDesc("");

            tLLClaimPolicySchema.setMakeDate(PubFun.getCurrentDate());
            tLLClaimPolicySchema.setMakeTime(PubFun.getCurrentTime());
            tLLClaimPolicySchema.setModifyDate(PubFun.getCurrentDate());
            tLLClaimPolicySchema.setModifyTime(PubFun.getCurrentTime());
            tLLClaimPolicySchema.setOperator(this.mGlobalInput.Operator);

            //查询险种信息
            LCPolSchema tLCPolSchema = mLLClaimPubFunBL.getLCPol(tPolNo);
            if (tLCPolSchema == null)
            {
                mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
                return false;
            }

            tLLClaimPolicySchema.setGrpContNo(tLCPolSchema.getGrpContNo()); //集体合同号
            tLLClaimPolicySchema.setGrpPolNo(tLCPolSchema.getGrpPolNo()); //集体保单号
            tLLClaimPolicySchema.setContNo(tLCPolSchema.getContNo()); //个单合同号
            tLLClaimPolicySchema.setPolNo(tLCPolSchema.getPolNo()); //保单号

            tLLClaimPolicySchema.setKindCode(tLCPolSchema.getKindCode()); //险类代码
            tLLClaimPolicySchema.setRiskCode(tLCPolSchema.getRiskCode()); //险种代码
            tLLClaimPolicySchema.setRiskVer(tLCPolSchema.getRiskVersion()); //险种版本号
            tLLClaimPolicySchema.setPolMngCom(tLCPolSchema.getManageCom()); //保单所属机构

            tLLClaimPolicySchema.setInsuredNo(tLCPolSchema.getInsuredNo()); //被保人客户号
            tLLClaimPolicySchema.setInsuredName(tLCPolSchema.getInsuredName()); //被保人名称
            tLLClaimPolicySchema.setAppntNo(tLCPolSchema.getAppntNo()); //投保人客户号
            tLLClaimPolicySchema.setAppntName(tLCPolSchema.getAppntName()); //投保人名称

            tLLClaimPolicySchema.setCValiDate(tLCPolSchema.getCValiDate()); //保单生效日期
            tLLClaimPolicySchema.setPolState(tLCPolSchema.getPolState()); //保单状态
            tLLClaimPolicySchema.setMngCom(mGlobalInput.ManageCom); //保单的服务机构

            mLLClaimPolicySet.add(tLLClaimPolicySchema);
        }
        String tDelete = "delete LLClaimPolicy where clmno = '"+ this.mRgtNo + "'";
        mMMap.put(tDelete, "DELETE");
        mMMap.put(mLLClaimPolicySet, "INSERT");
        return true;
    }

    /**
     * 进行llclaimdutykind数据的生成
     * @return true or false
     */
    private boolean setLLClaimDutyKind()
    {
        String tSql = "select getdutykind,sum(standpay),sum(realpay)"
                    + " from llclaimdetail where 1=1 "
                    + " and clmno = '" + this.mRgtNo + "'"
                    + " group by clmno,getdutykind";
        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = tExeSQL.execSQL(tSql);
        if (tSSRS.getMaxRow() <= 0)
        {
            //errors
            mErrors.addOneError("查询赔案给付责任信息失败！");
            return false;
        }

        mLLClaimDutyKindSet = new LLClaimDutyKindSet();
        for (int i = 1; i <= tSSRS.getMaxRow(); i++)
        {
            String tGetDutyKind = tSSRS.GetText(i, 1); //理赔类型
            String tStandPay = tSSRS.GetText(i, 2); //理算金额
            String tRealPay = tSSRS.GetText(i, 3); //实赔金额

            LLClaimDutyKindSchema tLLClaimDutyKindSchema = new LLClaimDutyKindSchema();
            tLLClaimDutyKindSchema.setClmNo(this.mRgtNo);
            tLLClaimDutyKindSchema.setGetDutyKind(tGetDutyKind);
            tLLClaimDutyKindSchema.setRealPay(tRealPay);
            tLLClaimDutyKindSchema.setStandPay(tStandPay);

            tLLClaimDutyKindSchema.setClaimMoney(0);
            tLLClaimDutyKindSchema.setDeclinePay(0);
            tLLClaimDutyKindSchema.setOthPay(0);
            tLLClaimDutyKindSchema.setSocPay(0);

            mLLClaimDutyKindSet.add(tLLClaimDutyKindSchema);
        }
        String tDelete = "delete LLClaimDutyKind where clmno = '"+ this.mRgtNo + "'";
        mMMap.put(tDelete, "DELETE");
        mMMap.put(mLLClaimDutyKindSet, "INSERT");
        return true;
    }

    /**
     * 进行理赔总表数据的生成
     * @return true or false
     */
    private boolean setLLClaim()
    {
        String tSql = "select sum(standpay),sum(realpay)"
                    + " from llclaimdetail where 1=1 "
                    + " and clmno = '" + this.mRgtNo + "'"
                    + " group by clmno";
        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = tExeSQL.execSQL(tSql);
        if (tSSRS.getMaxRow() <= 0)
        {
            //errors
            mErrors.addOneError("查询赔案给付责任信息失败！");
            return false;
        }
        else
        {
            String tStandPay = tSSRS.GetText(1, 1); //理算金额
            String tRealPay = tSSRS.GetText(1, 2); //实赔金额

            LLClaimSchema tLLClaimSchema = new LLClaimSchema();
            tLLClaimSchema.setClmNo(this.mRgtNo);
            tLLClaimSchema.setRgtNo(this.mRgtNo);
            tLLClaimSchema.setCaseNo(this.mRgtNo);
            tLLClaimSchema.setClmState("20"); //赔案状态，20立案，30审核
            tLLClaimSchema.setCheckType("0"); //审核类型，0初次审核,1签批退回审核,2申诉审核

            tLLClaimSchema.setClmUWer(mGlobalInput.Operator);

            tLLClaimSchema.setGiveType("0"); //赔付结论，0给付，1拒付

            tLLClaimSchema.setMngCom(mGlobalInput.ManageCom);
            tLLClaimSchema.setOperator(mGlobalInput.Operator);
            tLLClaimSchema.setMakeDate(PubFun.getCurrentDate());
            tLLClaimSchema.setMakeTime(PubFun.getCurrentTime());
            tLLClaimSchema.setModifyDate(PubFun.getCurrentDate());
            tLLClaimSchema.setModifyTime(PubFun.getCurrentTime());

            tLLClaimSchema.setStandPay(tStandPay); //核算赔付金额
            tLLClaimSchema.setBalancePay(0); //结算金额
            tLLClaimSchema.setBeforePay(0); //预付金额
            tLLClaimSchema.setRealPay(tRealPay); //核赔赔付金额
            String tDelete = "delete LLClaim where clmno = '"+ this.mRgtNo + "'";
            mMMap.put(tDelete, "DELETE");
            mMMap.put(tLLClaimSchema, "INSERT");
        }
        return true;
    }

    /**
     * 进行理赔结算数据的生成
     * @return true or false
     */
    private boolean setLLBalance()
    {
        LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
//        tLLClaimDetailDB.setClmNo(this.mRgtNo);
//        tLLClaimDetailDB.setGiveType("0");
        String tlsql="select * from LLClaimDetail where clmno='"+this.mRgtNo+"' and givetype !='1' ";//赔付类型不为拒付
        LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB.executeQuery(tlsql);
        if (tLLClaimDetailDB.mErrors.needDealError())
        {
            mErrors .addOneError("生成赔案的数据时,没有找到赔案明细信息!!!") ;
            return false;
        }

        mLLBalanceSet = new LLBalanceSet();
        for ( int i=1 ; i<=tLLClaimDetailSet.size() ; i++ )
        {
            LLClaimDetailSchema tLLClaimDetailSchema = tLLClaimDetailSet.get(i);

            String tPolNo = StrTool.cTrim(tLLClaimDetailSchema.getPolNo());
            String tDutyCode = StrTool.cTrim(tLLClaimDetailSchema.getDutyCode());
            String tGetDutyKind = StrTool.cTrim(tLLClaimDetailSchema.getGetDutyKind());
            String tGetDutyCode = StrTool.cTrim(tLLClaimDetailSchema.getGetDutyCode());
            double tDRealPay = tLLClaimDetailSchema.getRealPay();

            //查询险种信息
            LCPolSchema tLCPolSchema = mLLClaimPubFunBL.getLCPol(tPolNo);
            if ( tLCPolSchema==null )
            {
                mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
                return false;
            }

            LLBalanceSchema tLLBalanceSchema = new LLBalanceSchema();

            tLLBalanceSchema.setClmNo(this.mRgtNo);                 //也就是赔案号
            tLLBalanceSchema.setFeeOperationType("A");              //补/退费业务类型
            tLLBalanceSchema.setSubFeeOperationType(tGetDutyKind);  //补/退费子业务类型,用来存放理赔类型
            tLLBalanceSchema.setFeeFinaType("PK");                  //补/退费财务类型

            tLLBalanceSchema.setBatNo("0");                  //批次号
            tLLBalanceSchema.setOtherNo("0");               //其它号码
            tLLBalanceSchema.setOtherNoType("0");           //其它号码类型

            tLLBalanceSchema.setGrpContNo(tLCPolSchema.getGrpContNo());   //集体合同号码
            tLLBalanceSchema.setContNo(tLCPolSchema.getContNo());         //合同号码
            tLLBalanceSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());     //集体保单号码
            tLLBalanceSchema.setPolNo(tLCPolSchema.getPolNo());           //保单号码


            tLLBalanceSchema.setDutyCode(tDutyCode);         //责任编码
            tLLBalanceSchema.setGetDutyKind(tGetDutyKind);   //给付责任类型
            tLLBalanceSchema.setGetDutyCode(tGetDutyCode);   //给付责任编码

            tLLBalanceSchema.setKindCode(tLCPolSchema.getKindCode());        //险类编码
            tLLBalanceSchema.setRiskCode(tLCPolSchema.getRiskCode());        //险种编码
            tLLBalanceSchema.setRiskVersion(tLCPolSchema.getRiskVersion());  //险种版本

            tLLBalanceSchema.setSaleChnl(tLCPolSchema.getSaleChnl());        //销售渠道
            tLLBalanceSchema.setAgentCode(tLCPolSchema.getAgentCode());      //代理人编码
            tLLBalanceSchema.setAgentGroup(tLCPolSchema.getAgentGroup());    //代理人组别

            tLLBalanceSchema.setGetDate(PubFun.getCurrentDate());            //给付日期
            tLLBalanceSchema.setPay(tDRealPay);                              //赔付金额

            tLLBalanceSchema.setPayFlag("0");                                //支付标志,0未支付,1已支付
            tLLBalanceSchema.setState("0");                                  //状态,0有效,1无效
            tLLBalanceSchema.setDealFlag("0");                               //处理标志,0未处理,1已处理

            tLLBalanceSchema.setManageCom(tLCPolSchema.getManageCom());     //管理机构
            tLLBalanceSchema.setAgentCom(tLCPolSchema.getAgentCom());       //代理机构
            tLLBalanceSchema.setAgentType(tLCPolSchema.getAgentType());     //代理机构内部分类

            tLLBalanceSchema.setOperator(this.mGlobalInput.Operator);
            tLLBalanceSchema.setMakeDate(PubFun.getCurrentDate());
            tLLBalanceSchema.setMakeTime(PubFun.getCurrentTime());
            tLLBalanceSchema.setModifyDate(PubFun.getCurrentDate());
            tLLBalanceSchema.setModifyTime(PubFun.getCurrentTime());
            //tLLBalanceSchema.setOriPay(tDRealPay);                          //原始金额

            mLLBalanceSet.add(tLLBalanceSchema);

        }

        MMap tMMap = new MMap();
        String tDelete = "delete LLBalance where clmno = '"+ this.mRgtNo + "'";
        tMMap.put(tDelete, "DELETE");
        tMMap.put(mLLBalanceSet, "INSERT");
        mInputData.clear();
        mInputData.add(tMMap);
        if (!pubSubmit(mInputData))
        {
            //errors
            mErrors.addOneError("数据提交失败，理赔结算信息保存失败！");
            return false;
        }

        return true;
    }

    /**
     * 进行llbnf、ljsget、ljsgetclaim表数据的生成
     * 处理：首先打包llbnf，然后提交给LLBnfBL处理
     * @return true or false
     */
    private boolean setLLBnf()
    {
        String tSql = "select polno,sum(standpay),sum(realpay)"
                    + " from llclaimdetail where 1=1 "
                    + " and clmno = '" + this.mRgtNo + "'"
                    + " group by clmno,polno";
        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = tExeSQL.execSQL(tSql);
        if (tSSRS.getMaxRow() <= 0)
        {
            //errors
            mErrors.addOneError("没有找到赔案明细信息!!!");
            return false;
        }
        mLLBnfSet = new LLBnfSet();
        for (int i = 1; i <= tSSRS.getMaxRow(); i++)
        {
            String tPolNo = tSSRS.GetText(i, 1); //保单险种号
            String tStandPay = tSSRS.GetText(i, 2); //理算金额
            String tRealPay = tSSRS.GetText(i, 3); //实赔金额

            LLBnfSchema tLLBnfSchema = new LLBnfSchema();

            tLLBnfSchema.setClmNo(this.mRgtNo);
            tLLBnfSchema.setCaseNo(mRgtNo);
            tLLBnfSchema.setBatNo("0");

            //查询险种信息
            LCPolSchema tLCPolSchema = mLLClaimPubFunBL.getLCPol(tPolNo);
            if ( tLCPolSchema==null )
            {
                mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
                return false;
            }

            tLLBnfSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
            tLLBnfSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
            tLLBnfSchema.setContNo(tLCPolSchema.getContNo());
            tLLBnfSchema.setBnfKind("A"); //a理赔金 b预付
            tLLBnfSchema.setPolNo(tPolNo);
            tLLBnfSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
            tLLBnfSchema.setBnfNo("1");
            tLLBnfSchema.setCustomerNo(tLCPolSchema.getInsuredNo());
            tLLBnfSchema.setName(tLCPolSchema.getInsuredName());
            tLLBnfSchema.setPayeeNo(tLCPolSchema.getInsuredNo());
            tLLBnfSchema.setPayeeName(tLCPolSchema.getInsuredName());
            tLLBnfSchema.setGetMoney(tRealPay);
            tLLBnfSchema.setBnfLot("100");
//            tLLBnfSchema.setBnfType(tGrid9[index]);
//            tLLBnfSchema.setBnfGrade(tGrid10[index]);
//            tLLBnfSchema.setRelationToInsured(tGrid11[index]);
//            tLLBnfSchema.setSex(tGrid12[index]);
//            tLLBnfSchema.setBirthday(tGrid13[index]);
//            tLLBnfSchema.setIDType(tGrid14[index]);
//            tLLBnfSchema.setIDNo(tGrid15[index]);
//            tLLBnfSchema.setRelationToPayee(tGrid16[index]);
//            tLLBnfSchema.setPayeeSex(tGrid17[index]);
//            tLLBnfSchema.setPayeeBirthday(tGrid18[index]);
//            tLLBnfSchema.setPayeeIDType(tGrid19[index]);
//            tLLBnfSchema.setPayeeIDNo(tGrid20[index]);
//            tLLBnfSchema.setCasePayMode(tGrid23[index]);
//            tLLBnfSchema.setCasePayFlag("0");//保险金支付标志
//            tLLBnfSchema.setBankCode(tGrid24[index]);
//            tLLBnfSchema.setBankAccNo(tGrid25[index]);
//            tLLBnfSchema.setAccName(tGrid26[index]);

            mLLBnfSet.add(tLLBnfSchema);
        }

        //String使用TransferData打包后提交
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("ClmNo", mRgtNo);
        tTransferData.setNameAndValue("BnfKind", "A");

        //准备传输数据 VData
        VData tVData = new VData();
        tVData.add(this.mGlobalInput);
        tVData.add(tTransferData);
        tVData.add(mLLBnfSet);

        //数据向后台提交
        LLBnfUI tLLBnfUI = new LLBnfUI();
        if (!tLLBnfUI.submitData(tVData, "INSERT"))
        {
            //errors
            mErrors.addOneError("数据提交失败，受益人信息保存失败！");
            return false;
        }

        return true;
    }

    /**
     * 进行LLClaimDutyFee表数据的生成
     * @return true or false
     */
    private boolean setLLClaimDutyFee()
    {
        LLCaseReceiptDB tLLCaseReceiptDB = new LLCaseReceiptDB();
        tLLCaseReceiptDB.setClmNo(this.mRgtNo);
        LLCaseReceiptSet tLLCaseReceiptSet = tLLCaseReceiptDB.query();

        LLClaimDutyFeeSet tLLClaimDutyFeeSet = new LLClaimDutyFeeSet();
        for (int i = 1; i <= tLLCaseReceiptSet.size(); i++)
        {
            LLCaseReceiptSchema tLLCaseReceiptSchema = tLLCaseReceiptSet.get(i);
            String tCustomerNo = tLLCaseReceiptSchema.getCustomerNo();//出险人号码

            //根据赔案号和出险人代码到llclaimdetail中查找一条记录
            String tSql = "select a.polno,a.dutycode,a.getdutykind"
                        + " ,a.getdutycode,a.grpcontno,a.grppolno,a.contno "
                        + " ,a.kindcode,a.riskcode,a.riskver,a.polmngcom"
                        + " from llclaimdetail a,lcpol b where 1=1 "
                        + " and a.clmno = '" + this.mRgtNo + "'"
                        + " and b.insuredno = '" + tCustomerNo + "'"
                        + " and a.polno = b.polno";

            ExeSQL tExeSQL = new ExeSQL();
            SSRS tSSRS = tExeSQL.execSQL(tSql);
            if (tSSRS.getMaxRow() <= 0)
            {
                //errors
                mErrors.addOneError("数据查询失败，未查到理赔明细信息！");
                return false;
            }

            String polno = tSSRS.GetText(1, 1); //保单险种号
            String dutycode = tSSRS.GetText(1, 2); //责任代码
            String getdutykind = tSSRS.GetText(1, 3); //给付责任类型
            String getdutycode = tSSRS.GetText(1, 4); //给付责任代码
            String grpcontno = tSSRS.GetText(1, 5);
            String grppolno = tSSRS.GetText(1, 6);
            String contno = tSSRS.GetText(1, 7);
            String kindcode = tSSRS.GetText(1, 8);
            String riskcode = tSSRS.GetText(1, 9);
            String riskver = tSSRS.GetText(1, 10);
            String polmngcom = tSSRS.GetText(1, 11);

            LLClaimDutyFeeSchema tLLClaimDutyFeeSchema = new LLClaimDutyFeeSchema();

            tLLClaimDutyFeeSchema.setClmNo(this.mRgtNo); //赔案号
            tLLClaimDutyFeeSchema.setCaseNo(this.mRgtNo); //分案号
            tLLClaimDutyFeeSchema.setPolNo(polno); //保单号

            tLLClaimDutyFeeSchema.setGetDutyType(getdutykind); //给付责任类型
            tLLClaimDutyFeeSchema.setGetDutyCode(getdutycode); //给付责任编码

            tLLClaimDutyFeeSchema.setDutyFeeType(tLLCaseReceiptSchema.
                    getFeeItemType()); //费用类型
            tLLClaimDutyFeeSchema.setDutyFeeCode(tLLCaseReceiptSchema.
                    getFeeItemCode()); //费用代码
            tLLClaimDutyFeeSchema.setDutyFeeName(tLLCaseReceiptSchema.
                    getFeeItemName()); //费用名称

            tLLClaimDutyFeeSchema.setDutyFeeStaNo(tLLCaseReceiptSchema.
                    getFeeDetailNo()); //账单费用明细序号

            tLLClaimDutyFeeSchema.setKindCode(kindcode); //险类代码
            tLLClaimDutyFeeSchema.setRiskCode(riskcode); //险种代码
            tLLClaimDutyFeeSchema.setRiskVer(riskver); //险种版本号
            tLLClaimDutyFeeSchema.setPolMngCom(polmngcom); //保单管理机构

//            tLLClaimDutyFeeSchema.setHosID(tLLFeeMainSchema.
//                                             getHospitalCode()); //医院编号
//            tLLClaimDutyFeeSchema.setHosName(tLLFeeMainSchema.
//                    getHospitalName()); //医院名称
//            tLLClaimDutyFeeSchema.setHosGrade(tLLFeeMainSchema.
//                    getHospitalGrade()); //医院等级

            tLLClaimDutyFeeSchema.setStartDate(tLLCaseReceiptSchema.
                    getStartDate()); //开始时间
            tLLClaimDutyFeeSchema.setEndDate(tLLCaseReceiptSchema.
                    getEndDate()); //结束时间
            tLLClaimDutyFeeSchema.setDayCount(tLLCaseReceiptSchema.
                    getDayCount()); //天数

            tLLClaimDutyFeeSchema.setOriSum(tLLCaseReceiptSchema.getFee()); //原始金额

            tLLClaimDutyFeeSchema.setAdjSum(tLLCaseReceiptSchema.getAdjSum()); //调整金额
            tLLClaimDutyFeeSchema.setCalSum(tLLCaseReceiptSchema.getAdjSum()); //理算金额

            tLLClaimDutyFeeSchema.setCustomerNo(tCustomerNo);//出险人编码
            tLLClaimDutyFeeSchema.setFeeItemType(tLLCaseReceiptSchema.getFeeItemType());//帐单类型 A门诊 B住院

            tLLClaimDutyFeeSchema.setAdjReason(tLLCaseReceiptSchema.
                    getAdjReason()); //调整原因
            tLLClaimDutyFeeSchema.setAdjRemark(tLLCaseReceiptSchema.
                    getAdjRemark()); //调整备注
            tLLClaimDutyFeeSchema.setCutApartAmnt(tLLCaseReceiptSchema.
                    getCutApartAmnt()); //分割单受益金额

            tLLClaimDutyFeeSchema.setOutDutyAmnt(0); //免赔额
            tLLClaimDutyFeeSchema.setDutyCode(dutycode);
            tLLClaimDutyFeeSchema.setGrpContNo(grpcontno);
            tLLClaimDutyFeeSchema.setGrpPolNo(grppolno);
            tLLClaimDutyFeeSchema.setContNo(contno);

            tLLClaimDutyFeeSchema.setMakeDate(PubFun.getCurrentDate());
            tLLClaimDutyFeeSchema.setMakeTime(PubFun.getCurrentTime());
            tLLClaimDutyFeeSchema.setModifyDate(PubFun.getCurrentDate());
            tLLClaimDutyFeeSchema.setModifyTime(PubFun.getCurrentTime());
            tLLClaimDutyFeeSchema.setOperator(mGlobalInput.Operator);
            tLLClaimDutyFeeSchema.setMngCom(mGlobalInput.ManageCom);

            tLLClaimDutyFeeSet.add(tLLClaimDutyFeeSchema);
        }
        String tDelete = "delete LLClaimDutyFee where clmno = '"+ this.mRgtNo + "'";
        mMMap.put(tDelete, "DELETE");
        mMMap.put(tLLClaimDutyFeeSet, "INSERT");
        return true;
    }

    /**
     * 公共提交类
     * @return true or false
     */
    private boolean pubSubmit(VData tVData)
    {
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(tVData, ""))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "GrpClaimSimpleGuideIn";
            tError.functionName = "pubSubmit";
            tError.errorMessage = "数据提交数据库失败！";
            this.mErrors.addOneError(tError);
        }
        return true;
    }

//===ADD by zhoulei===增加理算信息处理=====2006-1-17 13:29========================End

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
        {
            InLCGrpCustomerImportLogSchema.setErrorState("1");
        }
        else
        {
            InLCGrpCustomerImportLogSchema.setErrorState("0");
        }
        InLCGrpCustomerImportLogSchema.setMakeDate(PubFun.getCurrentDate());
        InLCGrpCustomerImportLogSchema.setMakeTime(PubFun.getCurrentTime());
        String errorMess = "";
        for (int i = 0; i < logErrors.getErrorCount(); i++)
        {
            errorMess += logErrors.getError(i).errorMessage;
        }
        if ("".equals(errorMess))
        {
            errorMess = "未捕捉到错误";
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
     * 解析excel并转换成xml文件
     * @return
     */
    private boolean parseVts() throws Exception
    {
        //初始化导入文件
        if (!this.initImportFile())
        {
            return false;
        }

        //检查导入配置文件是否存在
        if (!this.checkImportConfig())
        {
            return false;
        }
        logger.debug(
                "----------GrpClaimSimpleGuideIn checkImportConfig()----------");
        GrpClaimVTSForThree GCvp = new GrpClaimVTSForThree();

        if (!GCvp.setFileName(ImportFileName))
        {
            mErrors.copyAllErrors(GCvp.mErrors);
            return false;
        }
        logger.debug(
                "----------GrpClaimSimpleGuideIn setFileName()= " + ImportFileName);
        if (!GCvp.setConfigFileName(ConfigFileName))
        {
            mErrors.copyAllErrors(GCvp.mErrors);
            return false;
        }
        logger.debug(
                "----------GrpClaimSimpleGuideIn setConfigFileName()=" + ConfigFileName);
        //转换excel到xml
        if (!GCvp.transform())
        {
            mErrors.copyAllErrors(GCvp.mErrors);
            return false;
        }
        logger.debug(
                "----------GrpClaimSimpleGuideIn transform()----------");
        // 得到生成的XML文件名列表
        m_strDataFiles = GCvp.getDataFiles();

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

        ConfigFileName = filePath + "GrpClaimImport.xml";
        File tFile2 = new File(ConfigFileName);
        if (!tFile2.exists())
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "GrpClaimSimpleGuideIn";
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
        //ImportFileName = "E:/temp/" + FileName;
        logger.debug("开始导入文件-----" + ImportFileName);
        File tFile = new File(ImportFileName);
        if (!tFile.exists())
        {
            logger.debug("11111111111111");
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "GrpClaimSimpleGuideIn";
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
    private boolean getFilePath()
    {
        LDSysVarDB tLDSysVarDB = new LDSysVarDB();
        tLDSysVarDB.setSysVar("XmlPath");
        if (!tLDSysVarDB.getInfo())
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "GrpClaimSimpleGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "缺少文件导入路径!";
            this.mErrors.addOneError(tError);
            return false;
        }
        else
        {
            FilePath = tLDSysVarDB.getSysVarValue();
        }

        return true;
    }

    /**
     * checkData
     *
     * @return boolean
     */
    private boolean checkData()
    {
        if (mGlobalInput == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "GrpClaimSimpleGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "无操作员信息，请重新登录!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (mTransferData == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "GrpClaimSimpleGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "无导入文件信息，请重新导入!";
            this.mErrors.addOneError(tError);
            return false;
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

        mPreFilePath = (String) mTransferData.getValueByName("FilePath");
        FileName = (String) mTransferData.getValueByName("FileName");

        logger.debug("开始处理文件："+ mPreFilePath + FileName);
    }

    /**
     * 得到日志显示结果
     * @return VData
     */
    public VData getResult()
    {
        mResult.add(mRgtNo);
        return mResult;
    }

    public static void main(String[] args)
    {
        GrpClaimSimpleGuideIn tPGI = new GrpClaimSimpleGuideIn();
        VData tVData = new VData();
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("FileName", "zzzzzzzzzzzzzl.xls");
        tTransferData.setNameAndValue("FilePath", "D:/lis/ui");
        GlobalInput tG = new GlobalInput();
        tG.Operator = "tk";
        tG.ManageCom = "01";

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

    private void jbInit() throws Exception
    {
    }

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}


}
