/*
 * @(#)EdorParseGuideIn.java	2005-008-18
 * writer Alex
 */

package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilderFactory;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.LCContPlanRiskDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.tbgrp.GrpPolImpInfo;
import com.sinosoft.lis.tb.GrpPolVTSParser;
import com.sinosoft.lis.tb.GrpPolParser;
import com.sinosoft.lis.tb.ProposalBL;
import com.sinosoft.lis.tb.ContPlanQryBL;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCGrpImportLogDB;


/*
 * <p>Title: Web业务系统</p>
 * <p>ClassName:EdorParseGuidIn </p>
 * <p>Description: 保全磁盘投保入口类文件 </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft </p>
 * @author：Alex
 * @version：6.0
 * @CreateDate：2004-12-13
 */

public class EdorParseGuideIn
{
private static Logger logger = Logger.getLogger(EdorParseGuideIn.class);

//  @Fields
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    public CErrors logErrors = new CErrors();

    /** 往前面传输数据的容器 */
    private VData mResult = new VData();


    /** 往后面传输数据的容器 */
    private VData mInputData;


    /**内存文件暂存*/

    private org.jdom.Element myElement;
    private GlobalInput mGlobalInput = new GlobalInput();

    private TransferData mTransferData = new TransferData();


    private String FileName;
    private String XmlFileName;
    private String mPreFilePath;

;
    private String mGrpContNo;



    private String mProposalGrpContNo;




//数据Xml解析节点描述
    private String FilePath = "E:/temp/";
    private String ParseRootPath = "/DATASET/BATCHID";
    private String PATH_GRPCONTNO = "/DATASET/GRPCONTNO";


    private String ParsePath = "/DATASET/CONTTABLE/ROW";


    //配置文件Xml节点描述
    private String ImportFileName;
    private String ConfigFileName;


    private String mBatchNo = "";
    private String mPrtNo = "";
    private String mContNo = "";
    private String mEdorNo = "";
    private String mEdorType = "";
    private String mEdorTypeCal = "";


    private LCPolBL mainPolBL = new LCPolBL();
    private org.w3c.dom.Document m_doc = null; // 临时的Document对象

    private GrpPolImpInfo m_GrpPolImpInfo = new GrpPolImpInfo();

    private String[] m_strDataFiles = null;


    //2005-5-9增加
    private String[][] m_IDNoSet = null;


    //     @Constructors
    public EdorParseGuideIn()
    {
        bulidDocument();

    }

    public EdorParseGuideIn(String fileName)
    {
        bulidDocument();
        FileName = fileName;
    }

    public boolean submitData(VData cInputData, String cOperate)
    {
        mInputData = (VData) cInputData.clone();
        this.getInputData();

        if (!this.checkData())
        {
            return false;
        }
        logger.debug("开始时间:" + PubFun.getCurrentTime());
        try
        {
            //解析execl并生成xml数据文件
            if (!this.parseVts())
            {
                return false;
            }

            for (int nIndex = 0; nIndex < m_strDataFiles.length; nIndex++)
            {
                XmlFileName = m_strDataFiles[nIndex];
                this.ParseXml();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "EdorParseGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "导入文件格式有误!";
            this.logErrors.addOneError(tError);
            //  return false;
        }

        this.mErrors = this.logErrors;
        logger.debug("结束时间:" + PubFun.getCurrentTime());
        logger.debug("-----------complete---------"+this.mErrors.getErrorCount());
        if (this.mErrors.getErrorCount() > 0)
        {
            return false;
        }
        return true;
    }


    /**
     * 得到传入数据
     */
    private void getInputData()
    {
        mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
                "GlobalInput",
                0);
        mTransferData = (TransferData) mInputData.getObjectByObjectName(
                "TransferData", 0);
        mPreFilePath = (String) mTransferData.getValueByName("FilePath");
        logger.debug(mPreFilePath);
    }


    /**
     * 校验传输数据
     * @return boolean
     */
    private boolean checkData()
    {
        if (mGlobalInput == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "EdorParseGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "无操作员信息，请重新登录!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (mTransferData == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "EdorParseGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "无导入文件信息，请重新导入!";
            this.mErrors.addOneError(tError);
            return false;
        }
        else
        {
            //获得上传文件
            FileName = (String) mTransferData.getValueByName("FileName");
            mEdorNo = StrTool.cTrim(mTransferData.getValueByName("EdorNo").
                                    toString());
            mEdorType = StrTool.cTrim(mTransferData.getValueByName("EdorType").
                                      toString());
            mEdorTypeCal = StrTool.cTrim(mTransferData.getValueByName("EdorTypeCal").
                                      toString());
            if ("".equals(mEdorNo) || "".equals(mEdorType))
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "EdorParseGuideIn";
                tError.functionName = "checkData";
                tError.errorMessage = "传入保全信息不全!";
                this.mErrors.addOneError(tError);
                return false;

            }
        }
        return true;
    }


    /**
     * 得到生成文件路径
     * @return boolean
     */
    private boolean getFilePath()
    {
        LDSysVarDB tLDSysVarDB = new LDSysVarDB();
        tLDSysVarDB.setSysVar("XmlPath");
        if (tLDSysVarDB.getInfo())
        {
            FilePath = tLDSysVarDB.getSysVarValue();
            return true;
        }
        else
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "EdorParseGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "缺少文件导入路径!";
            this.mErrors.addOneError(tError);
            return false;
        }
    }


    /**
     * 检验文件是否存在
     * @return boolean
     */
    private boolean checkXmlFileName()
    {
        File tFile = new File(XmlFileName);
        if (!tFile.exists())
        {
            LDSysVarDB tLDSysVarDB = new LDSysVarDB();
            tLDSysVarDB.setSysVar("XmlPath");
            if (tLDSysVarDB.getInfo())
            {
                FilePath = tLDSysVarDB.getSysVarValue();
            }
            else
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "EdorParseGuideIn";
                tError.functionName = "checkData";
                tError.errorMessage = "缺少文件导入路径!";
                this.mErrors.addOneError(tError);
                return false;
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
                tError.moduleName = "EdorParseGuideIn";
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
     * @return boolean
     */
    private boolean checkImportConfig()
    {
        this.getFilePath();
        //FilePath="E:/temp/";

        String filePath = mPreFilePath + FilePath;
        File tFile1 = new File(filePath);
        if (!tFile1.exists())
        {
            tFile1.mkdirs();
        }

        ConfigFileName = filePath + "Import.xml";
        File tFile2 = new File(ConfigFileName);
        if (tFile2.exists())
        {
            return true;
        }
        else
        {
            logger.debug("检查导入配置文件是否存在");
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "EdorParseGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "请上传配置文件到指定路径" + FilePath + "!";
            this.mErrors.addOneError(tError);
            return false;
        }
    }


    /**
     * 初始化上传文件
     * @return boolean
     */
    private boolean initImportFile()
    {
        this.getFilePath();
        ImportFileName = mPreFilePath + FilePath + FileName;
        //ImportFileName = "E:/temp/" + FileName;

        File tFile = new File(ImportFileName);
        if (tFile.exists())
        {
            logger.debug("-----导入文件");
            return true;
        }
        else
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "EdorParseGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "未上传文件到指定路径" + FilePath + "!";
            this.mErrors.addOneError(tError);
            return false;
        }
    }


    /**
     * 解析excel并转换成xml文件
     * @return boolean
     * @throws Exception
     */
    private boolean parseVts() throws Exception
    {
        //初始化导入文件
        if (!this.initImportFile())
        {
            return false;
        }
        if (!this.checkImportConfig())
        {
            return false;
        }

        GrpPolVTSParser gpvp = new GrpPolVTSParser();
        if (!gpvp.setFileName(ImportFileName))
        {
            mErrors.copyAllErrors(gpvp.mErrors);
            return false;
        }
        if (!gpvp.setConfigFileName(ConfigFileName))
        {
            mErrors.copyAllErrors(gpvp.mErrors);
            return false;
        }
        //转换excel到xml
        if (gpvp.transform())
        {
            // 得到生成的XML数据文件名
            m_strDataFiles = gpvp.getDataFiles();
            return true;
        }
        else
        {
            mErrors.copyAllErrors(gpvp.mErrors);
            return false;
        }
    }


    /**
     * 提交第一层内的循环体到第一层循环内的指定结点下
     * ???目前不知道有啥用 -wujs
     * @param aVT Vector
     * @param aElt Element
     */
    public void AddMuliElement(Vector aVT, org.jdom.Element aElt)
    {
        int NodeCount = aVT.size();
        //org.jdom.Element tElt1 = (org.jdom.Element)myElement.clone();
        org.jdom.Element tElt = myElement;
        int i = 0;
        while (NodeCount > 0)
        {
            i++;
            String NodeName = (String) aVT.get(i - 1);
//      tElt = tElt.getChild((String)aVT.get(i-1));
            tElt = (org.jdom.Element) tElt.getChildren((String) aVT.get(i - 1)).
                   get(
                    tElt.getChildren((String) aVT.get(i - 1)).size() - 1);
            //myElement.addContent(t.Elt);
            NodeCount--;
        }
        tElt.addContent(aElt);
        //myDocument = new org.jdom.Document(tElt1);
    }


    /**
     * 解析xml,
     * @return boolean
     */
    private boolean ParseXml()
    {
        // logger.debug("-----开始读取Xml文件");
        if (!checkXmlFileName())
        {
            return false;
        }
        File tFile = new File(XmlFileName);
        XMLPathTool tXPT = new XMLPathTool(XmlFileName);
        this.mErrors.clearErrors();

        String tBatchNo = "";
        String tPrtNo = "";
        try
        {
            //批次号
            tBatchNo = tXPT.parseX(ParseRootPath).getFirstChild().getNodeValue();
            //印刷号
            tPrtNo = tXPT.parseX(PATH_GRPCONTNO).getLastChild().getNodeValue();
            mBatchNo = tBatchNo;
            mPrtNo = tPrtNo;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        LCGrpContDB grpContDB = new LCGrpContDB();
        grpContDB.setPrtNo(tPrtNo);
        grpContDB.setContType("2");
        LCGrpContSet tLCGrpContSet = grpContDB.query();
        if (tLCGrpContSet == null || tLCGrpContSet.size() <= 0)
        {
            CError.buildErr(this,
                            "没有找到团体合同[" + grpContDB.getGrpContNo() + "]的信息");
            m_GrpPolImpInfo.logError(tBatchNo, grpContDB.getGrpContNo(), "", "",
                                     "", "", "", null
                                     , this.mErrors, mGlobalInput);
            return false;
        }
        LCGrpContSchema tLCGrpContSchema = tLCGrpContSet.get(1);
        mGrpContNo = tLCGrpContSchema.getGrpContNo();
        mProposalGrpContNo = tLCGrpContSchema.getProposalGrpContNo();

        /*******************************/
        //得到保单的传入信息
        NodeList nodeList = tXPT.parseN(ParsePath);
        int nNodeCount = 0;

        if (nodeList != null)
        {
            nNodeCount = nodeList.getLength();
        }

        //循环中，每一次都调用一次承包后台程序，首先挑出一条保单纪录，再通过ID号找到保费项，责任，给付等，组合成Set集合
        //此时新单是没有投保单号的，因此在保单，保费项，责任等纪录中投保单号为空,后台自动生成投保单号
        //最后将数据放入VData中，传给GrpPollmport模块。一次循环完毕
        GrpPolParser tGrpPolParser = new GrpPolParser();
        logger.debug("-----------nNodeCount----"+nNodeCount);
        for (int i = 0; i < nNodeCount; i++)
        {
//            boolean tSucc = true;
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

            NodeList contNodeList = node.getChildNodes();
            if (contNodeList.getLength() <= 0)
            {
                continue;
            }
            Node contNode = null;
            String nodeName = "";
            LCInsuredRelatedSet tLCInsuredRelatedSet = null;
            LCInsuredSet tLCInsuredSet = null;
            LCBnfSet tLCBnfSet = null;
            String contId = "";
            logger.debug("-----------length----"+contNodeList.getLength());
            for (int j = 0; j < contNodeList.getLength(); j++)
            {

                contNode = contNodeList.item(j);
                nodeName = contNode.getNodeName();
                if (nodeName.equals("#text"))
                {
                    continue;
                }
                if (nodeName.equals("CONTID"))
                {
                    contId = contNode.getFirstChild().getNodeValue();
                    continue;
                }

                if (nodeName.equals("INSUREDTABLE"))
                {
                    //解析被保险人xml
                    tLCInsuredSet = tGrpPolParser.getLCInsuredSet(contNode);

                    continue;
                }
                if (nodeName.equals("LCPOLTABLE"))
                {
                    //保单信息
                    VData tVData = null;

                    // 解析一个保单节点
                    tVData = tGrpPolParser.parseLCPolNode(contNode);
                    //缓存险种保单信息
                    m_GrpPolImpInfo.addContPolData(contId, tVData);
                    continue;
                }
                if (nodeName.equals("BNFTABLE"))
                {
                    //受益人解析
                    tLCBnfSet = tGrpPolParser.getLCBnfSet(contNode);
                    continue;
                }
                if (nodeName.equals("INSURED_RELA_TABLE"))
                {
                    //连身被保人表
                    tLCInsuredRelatedSet = (LCInsuredRelatedSet) tGrpPolParser.
                                           getLCInsuredRelatedSet(contNode);
                    continue;
                }
            }

            if (tLCInsuredSet == null)
            {
                CError.buildErr(this, "被保险人节点解析失败,被保险人不能为空!");
                m_GrpPolImpInfo.logError(tBatchNo, mGrpContNo, contId,
                                         "", "", "", "", null
                                         , this.mErrors, mGlobalInput);
                this.mErrors.clearErrors();
                return false;
            }
            //通过磁盘投保文件中的团体保单号来初始化一些信息
            if (!m_GrpPolImpInfo.init(tBatchNo, this.mGlobalInput,
                                      this.mTransferData
                                      , tLCInsuredSet, tLCBnfSet
                                      , tLCInsuredRelatedSet, tLCGrpContSchema))
            {

                m_GrpPolImpInfo.logError(tBatchNo, mGrpContNo, contId,
                                         "", "", "", "", null
                                         , this.mErrors, mGlobalInput);
                this.mErrors.clearErrors();
                return false;
            }
            logger.debug("BQFlag==========="+mTransferData.getValueByName("BQFlag"));
            if (mTransferData.getValueByName("BQFlag") != null
                && !mTransferData.getValueByName("BQFlag").equals("")
                &&
                "Y".equals(StrTool.cTrim(mTransferData.getValueByName("BQFlag").
                                         toString())))
            {
            logger.debug("BQFlag==========="+mTransferData.getValueByName("EdorType"));
                if ("ZT".equals(StrTool.cTrim(mTransferData.getValueByName(
                        "EdorType").toString())))
                {
                    logger.debug("===========");
                    if (!this.ZTDiskInput())
                    {
                        //生成错误日志
                        m_GrpPolImpInfo.logError(tBatchNo, mGrpContNo, contId,
                                                 "", "", "", "", null
                                                 , mErrors, mGlobalInput);
                        this.mErrors.clearErrors();

                        continue;
                    }
                    logger.debug("---------------------------------");
                }
                if ("GP".equals(StrTool.cTrim(mTransferData.getValueByName(
                        "EdorType").toString())))
                {
                    logger.debug("===========");
                    if (!this.GPDiskInput())
                    {
                        //生成错误日志
                        m_GrpPolImpInfo.logError(tBatchNo, mGrpContNo, contId,
                                                 "", "", "", "", null
                                                 , mErrors, mGlobalInput);
                        this.mErrors.clearErrors();

                        continue;
                    }
                    logger.debug("---------------------------------");
                }
                if ("LC".equals(StrTool.cTrim(mTransferData.getValueByName(
                        "EdorType").toString())))
                {

                    if (!this.LCDiskInput())
                    {
                        //生成错误日志
                        m_GrpPolImpInfo.logError(tBatchNo, mGrpContNo, contId,
                                                 "", "", "", "", null
                                                 , mErrors, mGlobalInput);
                        this.mErrors.clearErrors();

                        continue;
                    }

                }
            }
            //生成错误日志
            m_GrpPolImpInfo.logError(tBatchNo, mGrpContNo, contId,
                                     "", "", "", "", null
                                     , mErrors, mGlobalInput);
            this.mErrors.clearErrors();
        }
        logger.debug("2222222222222222222222");
        if (!tFile.delete())
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "EdorParseGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "Xml文件删除失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        logger.debug("22222222222222223333");
        if (this.mErrors.needDealError())
        {
            logger.debug(this.mErrors.getErrorCount() + "error:" +
                               this.mErrors.getFirstError());
        }
                logger.debug("22222222222222244444");
        return true;
    }


    /**团单磁盘减人处理函数
     * @return boolean
     */

    private boolean ZTDiskInput()
    {
        LPGrpEdorItemDB tLPGrpEdorItemBD = new LPGrpEdorItemDB();
        tLPGrpEdorItemBD.setEdorNo(mEdorNo);
        tLPGrpEdorItemBD.setEdorType(mEdorType);
        LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemBD.query();
        if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() <= 0)
        {
            CError.buildErr(this,
                            "查不到此保全受理!");
            this.mErrors.clearErrors();
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "EdorParseGuideIn";
            tError.functionName = "ZTDiskInput";
            tError.errorMessage = "查不到此保全受理!";
            //this.logErrors.addOneError(tError);
            this.mErrors.addOneError(tError);
            return false;

        }
        LPGrpEdorItemSchema tLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(1);
        String EdorValidate = tLPGrpEdorItemSchema.getEdorValiDate();

        LCInsuredSet tLCInsuredSet = m_GrpPolImpInfo.getLCInsuredSet();
        //清单中所有人都导入成功的标识
        boolean allInput = true;
        for (int m = 1; m <= tLCInsuredSet.size(); m++)
        {
            LCInsuredSchema tLCInsuredSchema = tLCInsuredSet.get(m);
            LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
            //校验被保人
            boolean flag = true;
            LCInsuredSchema orgLCInsuredSchema = checkInsured(tLCInsuredSchema);
            if (orgLCInsuredSchema == null)
            {
                CError.buildErr(this,
                                "序号" + tLCInsuredSchema.getContNo() + "的被保险人" +
                                tLCInsuredSchema.getName() +
                                "信息不符!");
                this.mErrors.clearErrors();
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "EdorParseGuideIn";
                tError.functionName = "ZTDiskInput";
                tError.errorMessage = "序号" + tLCInsuredSchema.getContNo() +
                                      "的被保险人" + tLCInsuredSchema.getName() +
                                      "信息不符!";
                this.logErrors.addOneError(tError);
                this.mErrors.addOneError(tError);
                flag = false;
            }
            //校验结束
            logger.debug("111111111111111111"+flag);
            String cOperate="";
            if (flag)
            {
                //LCPolSet tLCPolSet = m_GrpPolImpInfo.getLCPolSet();
                //jixf
                logger.debug("---insuredNo---"+tLCInsuredSchema.getInsuredNo());
                logger.debug("---ContNo---"+tLCInsuredSchema.getContNo());
                String contIndex=tLCInsuredSchema.getContNo();

                VData tContPolData = (VData) m_GrpPolImpInfo.getContPolData(
                    contIndex);
                logger.debug("合同ＩＤ＝＝" + contIndex);
                logger.debug("----");
                logger.debug("---------------"+tContPolData.size());
                if (tContPolData != null) {
                    logger.debug("==================");
                    LCPolSchema tLCPolSchema = null;
                    VData tPolData = null;
                    logger.debug("-------------contpoldata.size----"+ tContPolData.size());
                    for (int u = 0; u < tContPolData.size(); u++) {
                        tPolData = (VData) tContPolData.get(u);
                        //if (state == false) {
                          //  break;
                        //}
                        logger.debug("-------------poldata.size----"+ tPolData.size());
                        if (tPolData.size()>0)
                        {
                            logger.debug("888888888888" + tPolData.size());
                            for (int i = 0; i < tPolData.size(); i++) {

                                //初始化，避免重复使用

                                // if (state == false) {
                                //   break;
                                //}

                                VData onePolData = (VData) tPolData.get(i);
                                if (onePolData == null) {
                                    continue;
                                }
                                //备份原ID号
                                tLCPolSchema = (LCPolSchema) onePolData.
                                               getObjectByObjectName(
                                        "LCPolSchema",
                                        0);
                                //logRiskCode = tLCPolSchema.getRiskCode();
                                //insuredIndex = tLCPolSchema.getInsuredNo();
                                //contIndex = tLCPolSchema.getContNo();
                                logger.debug("------" +
                                        tLCPolSchema.getRiskCode());
                                LPEdorItemSchema tLPEdorItemSchema = new
                                        LPEdorItemSchema();
                                tLPEdorItemSchema.setEdorAcceptNo(
                                        tLPGrpEdorItemSchema.
                                        getEdorAcceptNo());
                                tLPEdorItemSchema.setEdorNo(mEdorNo);
                                tLPEdorItemSchema.setEdorType(mEdorType);
                                tLPEdorItemSchema.setEdorTypeCal(mEdorTypeCal);
                                tLPEdorItemSchema.setContNo(orgLCInsuredSchema.
                                        getContNo());
                                tLPEdorItemSchema.setInsuredNo(
                                        orgLCInsuredSchema.
                                        getInsuredNo());
                                tLPEdorItemSchema.setPolNo(tLCPolSchema.
                                        getRiskCode());
                                tLPEdorItemSet.add(tLPEdorItemSchema);
                            }
                            cOperate = "INSERT||EDOR";
                        }
                        else
                        {
                            logger.debug("--------------nopoldata-----");
                            LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
                            tLPEdorItemSchema.setEdorAcceptNo(tLPGrpEdorItemSchema.getEdorAcceptNo());
                            tLPEdorItemSchema.setEdorNo(mEdorNo);
                            tLPEdorItemSchema.setEdorType(mEdorType);
                            tLPEdorItemSchema.setEdorTypeCal(mEdorTypeCal);
                            tLPEdorItemSchema.setContNo(orgLCInsuredSchema.getContNo());
                            tLPEdorItemSchema.setInsuredNo(orgLCInsuredSchema.
                                getInsuredNo());
                            tLPEdorItemSchema.setPolNo("000000");
                            tLPEdorItemSet.add(tLPEdorItemSchema);
                            cOperate="INSERT||MUlTIEDOR";
                        }
                    }
                }
                else
                    {

                        LPEdorItemSchema tLPEdorItemSchema = new
                                LPEdorItemSchema();
                        tLPEdorItemSchema.setEdorAcceptNo(tLPGrpEdorItemSchema.
                                getEdorAcceptNo());
                        tLPEdorItemSchema.setEdorNo(mEdorNo);
                        tLPEdorItemSchema.setEdorType(mEdorType);
                        tLPEdorItemSchema.setEdorTypeCal(mEdorTypeCal);
                        tLPEdorItemSchema.setContNo(orgLCInsuredSchema.
                                getContNo());
                        tLPEdorItemSchema.setInsuredNo(orgLCInsuredSchema.
                                getInsuredNo());
                        tLPEdorItemSchema.setPolNo("000000");
                        tLPEdorItemSet.add(tLPEdorItemSchema);
                        cOperate="INSERT||MUlTIEDOR";
                    }
            }

            else
            {
                allInput = false;
            }

            /*
            if (tLCInsuredSchema.getIValiDate() != null &&
                !"".equals(tLCInsuredSchema.getIValiDate()))
            {
                EdorValidate=tLCInsuredSchema.getIValiDate();
            }
            */
           logger.debug("-------edoritem-------"+tLPEdorItemSet.size());
            if (tLPEdorItemSet.size() > 0)
            {
                TransferData tTransferData=new TransferData();
                tTransferData.setNameAndValue("EdorValiDate",EdorValidate);
                VData tVData = new VData();
                tVData.addElement(mGlobalInput);
                tVData.addElement(tLPGrpEdorItemSchema);
                tVData.addElement(tLPEdorItemSet);
                tVData.addElement(tTransferData);
                //GEdorDetailUI tGEdorDetailUI = new GEdorDetailUI();
                //tGEdorDetailUI.submitData(tVData, operator);
                logger.debug("-----------cOperate----"+cOperate);
                GEdorTypeZTDetailBL tGEdorTypeZTDetailBL = new GEdorTypeZTDetailBL();

                tGEdorTypeZTDetailBL.submitData(tVData, cOperate);

                CErrors tError = new CErrors();
                tError = tGEdorTypeZTDetailBL.mErrors;
                logger.debug("+++++++++++++++++++++"+tError.needDealError());
                if (tError.needDealError())
                {
                    //this.logErrors.addOneError(tError.getFirstError());
                    this.mErrors.addOneError(tError.getFirstError());
                    allInput = false;
                }
            }

        }
        logger.debug("===================================================="+allInput);
        if (allInput)
        {
            this.mErrors.addOneError("保全减人导入成功！");
        }

        return allInput;
    }

    private boolean LCDiskInput()
    {

        LPGrpEdorItemDB tLPGrpEdorItemBD = new LPGrpEdorItemDB();
        tLPGrpEdorItemBD.setEdorNo(mEdorNo);
        tLPGrpEdorItemBD.setEdorType(mEdorType);
        LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemBD.query();
        if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() <= 0)
        {
            CError.buildErr(this,
                            "查不到此保全受理!");
            this.mErrors.clearErrors();
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "EdorParseGuideIn";
            tError.functionName = "ZTDiskInput";
            tError.errorMessage = "查不到此保全受理!";
            this.logErrors.addOneError(tError);
            return false;

        }
        LPGrpEdorItemSchema tLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(1);

        LCInsuredSet tLCInsuredSet = m_GrpPolImpInfo.getLCInsuredSet();
        //清单中所有人都导入成功的标识
        boolean allInput = true;
        for (int m = 1; m <= tLCInsuredSet.size(); m++)
        {
            LCInsuredSchema tLCInsuredSchema = tLCInsuredSet.get(m);
            LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
            Reflections ref = new Reflections();
            ref.transFields(tLPInsuredSchema, tLCInsuredSchema);
            tLPInsuredSchema.setEdorNo(mEdorNo);
            tLPInsuredSchema.setEdorType(mEdorType);
            LPInsuredSet tLPInsuredSet = new LPInsuredSet();
            tLPInsuredSet.add(tLPInsuredSchema);

            TransferData tTransferData = new TransferData();
            tTransferData.setNameAndValue("ContPlanCode",
                                          tLPInsuredSchema.getContPlanCode());
            //jixf tTransferData.setNameAndValue("CValiDate",tLPInsuredSchema.getIValiDate());

            VData tVData = new VData();
            tVData.addElement(mGlobalInput);
            tVData.addElement(tTransferData);
            tVData.addElement(tLPGrpEdorItemSchema);
            tVData.addElement(tLPInsuredSet);
            //jixf GrpEdorLCDetailUI tGrpEdorLCDetailUI = new GrpEdorLCDetailUI();
            //jixf tGrpEdorLCDetailUI.submitData(tVData, "INSERT||EDOR");
            CErrors tError = new CErrors();
            //jixf tError = tGrpEdorLCDetailUI.mErrors;

            if (tError.needDealError())
            {
                this.logErrors.addOneError(tError.getFirstError());
                allInput = false;
            }

        }

        return allInput;

    }
    private boolean GPDiskInput()
     {
         LPGrpEdorItemDB tLPGrpEdorItemBD = new LPGrpEdorItemDB();
         tLPGrpEdorItemBD.setEdorNo(mEdorNo);
         tLPGrpEdorItemBD.setEdorType(mEdorType);
         LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemBD.query();
         if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() <= 0)
         {
             CError.buildErr(this,
                             "查不到此保全受理!");
             this.mErrors.clearErrors();
             // @@错误处理
             CError tError = new CError();
             tError.moduleName = "EdorParseGuideIn";
             tError.functionName = "ZTDiskInput";
             tError.errorMessage = "查不到此保全受理!";
             //this.logErrors.addOneError(tError);
             this.mErrors.addOneError(tError);
             return false;

         }
         LPGrpEdorItemSchema tLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(1);
         String EdorValidate = tLPGrpEdorItemSchema.getEdorValiDate();

         LCInsuredSet tLCInsuredSet = m_GrpPolImpInfo.getLCInsuredSet();
         //清单中所有人都导入成功的标识
         boolean allInput = true;
         for (int m = 1; m <= tLCInsuredSet.size(); m++)
         {
             LCInsuredSchema tLCInsuredSchema = tLCInsuredSet.get(m);
             LCContSet tLCContSet = new LCContSet();
             //校验被保人
             boolean flag = true;
             LCInsuredSchema orgLCInsuredSchema = checkInsured(tLCInsuredSchema);
             if (orgLCInsuredSchema == null)
             {
                 CError.buildErr(this,
                                 "序号" + tLCInsuredSchema.getContNo() + "的被保险人" +
                                 tLCInsuredSchema.getName() +
                                 "信息不符!");
                 this.mErrors.clearErrors();
                 // @@错误处理
                 CError tError = new CError();
                 tError.moduleName = "EdorParseGuideIn";
                 tError.functionName = "ZTDiskInput";
                 tError.errorMessage = "序号" + tLCInsuredSchema.getContNo() +
                                       "的被保险人" + tLCInsuredSchema.getName() +
                                       "信息不符!";
                 this.logErrors.addOneError(tError);
                 this.mErrors.addOneError(tError);
                 flag = false;
             }
             //校验结束
             logger.debug("111111111111111111"+flag);
             String cOperate="";
             if (flag)
             {
                 //LCPolSet tLCPolSet = m_GrpPolImpInfo.getLCPolSet();
                 //jixf
                 logger.debug("---insuredNo---"+tLCInsuredSchema.getInsuredNo());
                 logger.debug("---ContNo---"+tLCInsuredSchema.getContNo());
                 String contIndex=tLCInsuredSchema.getContNo();

                 VData tContPolData = (VData) m_GrpPolImpInfo.getContPolData(
                     contIndex);
                 logger.debug("合同ＩＤ＝＝" + contIndex);
                 logger.debug("----");
                 logger.debug("---------------"+tContPolData.size());
                 if (tContPolData != null) {
                     logger.debug("==================");
                     LCPolSchema tLCPolSchema = null;
                     VData tPolData = null;
                     logger.debug("-------------contpoldata.size----"+ tContPolData.size());
                     for (int u = 0; u < tContPolData.size(); u++) {
                         tPolData = (VData) tContPolData.get(u);
                         //if (state == false) {
                           //  break;
                         //}
                         logger.debug("-------------poldata.size----"+ tPolData.size());
                         if (tPolData.size()>0)
                         {
                             logger.debug("888888888888" + tPolData.size());
                             for (int i = 0; i < tPolData.size(); i++) {

                                 //初始化，避免重复使用

                                 // if (state == false) {
                                 //   break;
                                 //}

                                 VData onePolData = (VData) tPolData.get(i);
                                 if (onePolData == null) {
                                     continue;
                                 }
                                 //备份原ID号
                                 tLCPolSchema = (LCPolSchema) onePolData.
                                                getObjectByObjectName(
                                         "LCPolSchema",
                                         0);
                                 //logRiskCode = tLCPolSchema.getRiskCode();
                                 //insuredIndex = tLCPolSchema.getInsuredNo();
                                 //contIndex = tLCPolSchema.getContNo();
                                 logger.debug("------" +
                                         tLCPolSchema.getRiskCode());
                                 LCContSchema tLCContSchema = new
                                         LCContSchema();
                                 tLCContSchema.setContNo(orgLCInsuredSchema.
                                         getContNo());
                                 tLCContSet.add(tLCContSchema);
                             }
                             cOperate = "INSERT";
                         }
                         else
                         {
                             logger.debug("--------------nopoldata-----");
                             LCContSchema tLCContSchema = new
                                     LCContSchema();
                             tLCContSchema.setContNo(orgLCInsuredSchema.
                                         getContNo());
                             tLCContSet.add(tLCContSchema);
                             cOperate="INSERT";
                         }
                     }
                 }
                 else
                     {
                         LCContSchema tLCContSchema = new
                                 LCContSchema();
                         tLCContSchema.setContNo(orgLCInsuredSchema.
                                         getContNo());
                         tLCContSet.add(tLCContSchema);
                         cOperate="INSERT";
                     }
             }

             else
             {
                 allInput = false;
             }

             /*
             if (tLCInsuredSchema.getIValiDate() != null &&
                 !"".equals(tLCInsuredSchema.getIValiDate()))
             {
                 EdorValidate=tLCInsuredSchema.getIValiDate();
             }
             */
            logger.debug("-------edoritem-------"+tLCContSet.size());
             if (tLCContSet.size()> 0)
             {
                 TransferData tTransferData=new TransferData();
                 tTransferData.setNameAndValue("EdorAcceptNo", mTransferData.getValueByName("EdorNo"));
                 tTransferData.setNameAndValue("EdorNo", mTransferData.getValueByName("EdorNo"));
                 tTransferData.setNameAndValue("GrpContNo", mTransferData.getValueByName("GrpContNo"));
                 tTransferData.setNameAndValue("EdorType", mTransferData.getValueByName("EdorType"));


                 VData tVData = new VData();
                 tVData.addElement(mGlobalInput);
                 tVData.addElement(tLCContSet);
                 tVData.addElement(tTransferData);
                 //GEdorDetailUI tGEdorDetailUI = new GEdorDetailUI();
                 //tGEdorDetailUI.submitData(tVData, operator);
                 logger.debug("-----------cOperate----"+cOperate);
//                 GEdorGPDetailBL tGEdorGPDetailBL = new GEdorGPDetailBL();
//
//                 tGEdorGPDetailBL.submitData(tVData, cOperate);
//
//                 CErrors tError = new CErrors();
//                 tError = tGEdorGPDetailBL.mErrors;
//                 logger.debug("+++++++++++++++++++++"+tError.needDealError());
//                 if (tError.needDealError())
//                 {
//                     //this.logErrors.addOneError(tError.getFirstError());
//                     this.mErrors.addOneError(tError.getFirstError());
//                     allInput = false;
//                 }
             }

         }
         logger.debug("===================================================="+allInput);
         if (allInput)
         {
             this.mErrors.addOneError("保全团转个导入成功");
         }

         return allInput;
    }

    /**
     * 团单保障变更处理函数
     * @param contIndex String
     * @return boolean
     */
    private boolean LCDiskInput(String contIndex)
    {
        LCInsuredSchema insuredSchema = null;
        LCInsuredSet tInsuredSet = m_GrpPolImpInfo.getLCInsuredSet();
        MMap subMap = null; //提交结果集缓存
        boolean state = true; //导入状态
        boolean saveState = true;
        String logRiskCode = "";
        String logContPlanCode = "";
        String logPolId = "";
        String insuredIndex = "";

        subMap = null;
        mContNo = "";
        LCGrpImportLogSchema logSchema = m_GrpPolImpInfo.getLogInfo(mBatchNo,
                contIndex);

        if (logSchema != null && "1".equals(logSchema.getErrorState()))
        {
            //该批次号该合同已经导入成功
            //CError.buildErr(this, "该批次号该合同已经导入成功，不可重复导入!");
            //return false;
        }

        state = true;
        //先查找有保障计划的导入
        for (int i = 1; i <= tInsuredSet.size(); i++)
        {

            //初始化，避免重复使用
            logRiskCode = "";
            logContPlanCode = "";
            logPolId = "";
            if (!state)
            {
                break;
            }
            insuredSchema = tInsuredSet.get(i);
            //校验被保人
            LCInsuredSchema orgLCInsuredSchema = checkInsured(insuredSchema);
            if (orgLCInsuredSchema == null)
            {
                break;
            }
            //校验结束

            insuredSchema.setSequenceNo(String.valueOf(i));
            //不是同一个合同的，跳出
            if (!insuredSchema.getContNo().equals(contIndex))
            {
                continue;
            }
            //不是保障计划的，跳出
            if ("".equals(StrTool.cTrim(insuredSchema.getContPlanCode())))
            {
                continue;
            }
            //解析XML时借用PrtNo保存被保人序号
            insuredIndex = insuredSchema.getPrtNo();
            if ("".equals(StrTool.cTrim(insuredIndex)))
            {
                CError.buildErr(this, "第[" + i + "]个被保人ID没有填写");
                state = false;
                break;
            }
            //保障计划代码不空才导入
            logContPlanCode = insuredSchema.getContPlanCode();
            LCContPlanSchema tContPlanSchema = m_GrpPolImpInfo.findLCContPlan(
                    insuredSchema.
                    getContPlanCode());
            if (tContPlanSchema == null)
            {
                CError.buildErr(this
                                ,
                                "被保险人[" + i + "]保单保险计划[" +
                                insuredSchema.getContPlanCode() +
                                "]查找失败");
                state = false;
                break;
                //return false;
            }

            //险种保险计划
            LCContPlanRiskSet tLCContPlanRiskSet = m_GrpPolImpInfo.
                    findLCContPlanRiskSet(
                    insuredSchema.getContPlanCode());

            if (tLCContPlanRiskSet == null || tLCContPlanRiskSet.size() <= 0)
            {
                CError.buildErr(this
                                ,
                                "被保险人[" + i + "]保单险种保险计划[" +
                                insuredSchema.getContPlanCode() +
                                "]查找失败");
                //continue;
                state = false;
                break; //break for insured
            }

            //对保险险种计划排序，确保主险在前面
            LCContPlanRiskSet mainPlanRiskSet = new LCContPlanRiskSet();
            LCContPlanRiskSet subPlanRiskSet = new LCContPlanRiskSet();
            LCContPlanRiskSchema contPlanRiskSchema = null;
            for (int t = 1; t <= tLCContPlanRiskSet.size(); t++)
            {
                contPlanRiskSchema = tLCContPlanRiskSet.get(t);
                if (contPlanRiskSchema.getRiskCode().equals(contPlanRiskSchema.
                        getMainRiskCode()))
                {
                    mainPlanRiskSet.add(contPlanRiskSchema);
                }
                else
                {
                    subPlanRiskSet.add(contPlanRiskSchema);
                }

            }
            mainPlanRiskSet.add(subPlanRiskSet);
            //根据险种计划生成险种保单相关信息
            for (int j = 1; j <= mainPlanRiskSet.size(); j++)
            {
                logRiskCode = mainPlanRiskSet.get(j).getRiskCode();
                //生成保单，责任等信息
                VData tData = prepareContPlanData(contIndex, insuredIndex,
                                                  insuredSchema
                                                  , mainPlanRiskSet.get(j));
                if (tData == null)
                {
                    state = false;
                    break; //break for conplarisk
                }
                //提交生成数据
                MMap oneRisk = submitDatatoProposalBL(tData,
                        orgLCInsuredSchema.getContNo());
                if (oneRisk == null)
                {
                    //失败
                    state = false;
                    break; //break for insured
                }
                else
                {
                    //成功
                    if (subMap == null)
                    {
                        subMap = oneRisk;
                    }
                    else
                    {
                        subMap.add(oneRisk);
                    }
                }
            }
            //生成保全索引信息
            Reflections tRef = new Reflections();
            LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
            tRef.transFields(tLPInsuredSchema, orgLCInsuredSchema);
            tLPInsuredSchema.setEdorNo(mEdorNo);
            tLPInsuredSchema.setEdorType(mEdorType);
            tLPInsuredSchema.setContPlanCode(insuredSchema.getContPlanCode());
            subMap.put(tLPInsuredSchema, "DELETE&INSERT");

            LPGrpEdorItemDB tLPGrpEdorItemBD = new LPGrpEdorItemDB();
            tLPGrpEdorItemBD.setEdorNo(mEdorNo);
            tLPGrpEdorItemBD.setEdorType(mEdorType);
            LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemBD.query();
            if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() <= 0)
            {
                CError.buildErr(this,
                                "查不到此保全受理!");
                this.mErrors.clearErrors();
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "EdorParseGuideIn";
                tError.functionName = "ZTDiskInput";
                tError.errorMessage = "查不到此保全受理!";
                this.logErrors.addOneError(tError);
                return false;

            }
            LPGrpEdorItemSchema tLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(1);

            LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
            tRef.transFields(tLPEdorItemSchema, tLPGrpEdorItemSchema);
            tLPEdorItemSchema.setContNo(tLPInsuredSchema.getContNo());
            tLPEdorItemSchema.setInsuredNo(tLPInsuredSchema.getInsuredNo());
            tLPEdorItemSchema.setPolNo("000000");
            tLPEdorItemSchema.setEdorAppDate(PubFun.getCurrentDate());
            //tLPEdorItemSchema.setGetMoney(tLPInsuredSchema.getPrem());
            tLPEdorItemSchema.setUWFlag("0");
            tLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
            tLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
            tLPEdorItemSchema.setMakeDate(PubFun.getCurrentDate());
            tLPEdorItemSchema.setMakeTime(PubFun.getCurrentTime());
            subMap.put(tLPEdorItemSchema, "DELETE&INSERT");

            LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
            tRef.transFields(tLPEdorMainSchema, tLPEdorItemSchema);
            tLPEdorMainSchema.setUWState("0");
            //tLPEdorMainSchema.setGetMoney(tLCContSchema.getPrem());
            tLPEdorMainSchema.setModifyDate(PubFun.getCurrentDate());
            tLPEdorMainSchema.setModifyTime(PubFun.getCurrentTime());
            tLPEdorMainSchema.setMakeDate(PubFun.getCurrentDate());
            tLPEdorMainSchema.setMakeTime(PubFun.getCurrentTime());
            subMap.put(tLPEdorMainSchema, "DELETE&INSERT");

        }

        if (!state)
        {

            logError(contIndex, insuredIndex, logPolId, "",
                     logRiskCode, insuredSchema);
            saveState = false;
            return false;
            //continue;
        }

        if (state && subMap != null && subMap.keySet().size() > 0)
        {
            MMap logMap = m_GrpPolImpInfo.logSucc(mBatchNo, mGrpContNo,
                                                  contIndex, mPrtNo, mContNo,
                                                  mGlobalInput);
            subMap.add(logMap);
            //保存生成的数据
            state = dataSave(subMap);
        }

        if (!state)
        {
            //合同导入没有成功，需要回退缓存
            m_GrpPolImpInfo.removeCaceh(contIndex);
            logger.debug("合同[" + contIndex + "]导入失败！！" +
                               this.mErrors.getErrContent());

            //导入失败日志，数据库保存，只能定位到合同上的失败
            logError(contIndex, "", "", "", "", null);

            //保存导入信息
            saveState = false;
        }
//        else
//        {
//            logger.debug("合同[" + contIndex + "]导入成功！！");
//            //保存导入信息
//        }
        return saveState;
    }


    /**
     * 提交到proposalBL ,做数据生成和准备
     * @param tNewVData VData
     * @return boolean
     */
    private MMap submitDatatoProposalBL(VData tNewVData, String tContNo)
    {
        MMap map = new MMap();

        ProposalBL tProposalBL = new ProposalBL();
        if (!tProposalBL.PrepareSubmitData(tNewVData, "INSERT||PROPOSAL"))
        {
            this.mErrors.copyAllErrors(tProposalBL.mErrors);
            return null;
        }
        else
        {
            VData pData = tProposalBL.getSubmitResult();
            if (pData == null)
            {
                CError.buildErr(this, "保单提交计算失败!");
                return null;
            }
//            LCContSchema rcontSchema = (LCContSchema) pData.getObjectByObjectName("LCContSchema", 2);
            LCPolSchema rPolSchema = (LCPolSchema) pData.getObjectByObjectName(
                    "LCPolSchema", 0);
            rPolSchema.setContNo(tContNo);
            LCPremBLSet rPremBLSet = (LCPremBLSet) pData.getObjectByObjectName(
                    "LCPremBLSet", 0);
            if (rPremBLSet == null)
            {
                CError.buildErr(this, "保单提交计算保费项数据准备有误!");
                return null;
            }
            LCPremSet rPremSet = new LCPremSet();
            for (int i = 1; i <= rPremBLSet.size(); i++)
            {
                rPremBLSet.get(i).setContNo(tContNo);
                rPremSet.add(rPremBLSet.get(i));
            }

            LCDutyBLSet rDutyBLSet = (LCDutyBLSet) pData.getObjectByObjectName(
                    "LCDutyBLSet", 0);
            if (rDutyBLSet == null)
            {
                CError.buildErr(this, "保单提交计算责任项数据准备有误!");
                return null;
            }
            LCDutySet rDutySet = new LCDutySet();
            for (int i = 1; i <= rDutyBLSet.size(); i++)
            {
                rDutyBLSet.get(i).setContNo(tContNo);
                rDutySet.add(rDutyBLSet.get(i));
            }

            LCGetBLSet rGetBLSet = (LCGetBLSet) pData.getObjectByObjectName(
                    "LCGetBLSet", 0);
            if (rGetBLSet == null)
            {
                CError.buildErr(this, "保单提交计算给付项数据准备有误!");
                return null;
            }
            LCGetSet rGetSet = new LCGetSet();
            for (int i = 1; i <= rGetBLSet.size(); i++)
            {
                rGetBLSet.get(i).setContNo(tContNo);
                rGetSet.add(rGetBLSet.get(i));
            }

            /**如为新增险种，则存C表以在确认时进行签单
             * 如仅为变更，则在P表中存相应的分段数据
             * 分段数据的规则为:
             * Pol表的数据不分段，更新保费保额数据后存LPPol表
             * Duty表数据分为变更前和变更后两段，变更前为原有LCDuty表记录，设置责任终止日期为变更时间，并在dutycode中加流水号，变更后为新生成数据
             * Prem表，Get表分段方法同Duty表
             * Alex 2005－08－25
             */
            LPDutySet rLPDutySet = new LPDutySet();
            LPGetSet rLPGetSet = new LPGetSet();
            LPPremSet rLPPremSet = new LPPremSet();
            Reflections tRef = new Reflections();
            StringBuffer tSBql = new StringBuffer(128);
            tSBql.append(
                    "Select a.* From LCPol a Where a.ContNo='" + tContNo +
                    "' and a.appflag='1' and a.GrpContNo='");
            tSBql.append(mGrpContNo);
            tSBql.append("' and riskcode='");
            tSBql.append(rPolSchema.getRiskCode());
            tSBql.append("' ");
            LCPolDB tLCPolDB = new LCPolDB();
            LCPolSet tLCPolSet = tLCPolDB.executeQuery(tSBql.toString());
            if (tLCPolSet == null || tLCPolSet.size() <= 0)
            {
                //新增险种，直接存C表
                map.put(rPolSchema, "DELETE&INSERT");
                map.put("delete from lcduty where contno='" + tContNo +
                        "' and appflag='2'", "DELETE");
                map.put(rDutySet, "INSERT");
                map.put("delete from lcprem where contno='" + tContNo +
                        "' and appflag='2'", "DELETE");
                map.put(rPremSet, "INSERT");
                map.put("delete from lcget where contno='" + tContNo +
                        "' and appflag='2'", "DELETE");
                map.put(rGetSet, "INSERT");
            }
            else
            {
                //只是对险种的变更
                LCPolSchema tLCPolSchema = tLCPolSet.get(1).getSchema(); //原险种信息
                //处理险种
                LPPolSchema tLPPolSchema = new LPPolSchema();
                tRef.transFields(tLPPolSchema, tLCPolSchema);
                tLPPolSchema.setEdorNo(mEdorNo);
                tLPPolSchema.setEdorType(mEdorType);
                tLPPolSchema.setPrem(rPolSchema.getPrem());
                tLPPolSchema.setRiskAmnt(rPolSchema.getRiskAmnt());
                tLPPolSchema.setAmnt(rPolSchema.getAmnt());
                tLPPolSchema.setMult(rPolSchema.getMult());
                map.put(tLPPolSchema, "DELETE&INSERT");

                //处理责任
                tSBql = new StringBuffer(128);
                tSBql.append(
                        "Select a.* From LCDuty a Where a.PolNo='");
                tSBql.append(tLCPolSchema.getPolNo());
                tSBql.append("' and length(trim(a.dutycode))=6"); //只查找现在有效的责任
                LCDutyDB tLCDutyDB = new LCDutyDB();
                LCDutySet tLCDutySet = tLCDutyDB.executeQuery(tSBql.toString());

                if (tLCDutySet == null || tLCDutySet.size() <= 0)
                {
                    CError.buildErr(this,
                                    "险种" + tLCPolSchema.getRiskCode() +
                                    "下无责任信息!");
                    return null;
                }
                for (int j = 1; j <= tLCDutySet.size(); j++)
                {

                    //原有责任设置责任终止日期
                    LPDutySchema oldLPDutySchema = new LPDutySchema();
                    String oldDutyCode = creatDutyCode(tLCPolSchema.
                            getPolNo(), tLCDutySet.get(j).getDutyCode());
                    tRef.transFields(oldLPDutySchema,
                                     tLCDutySet.get(j).getSchema());
                    oldLPDutySchema.setDutyCode(oldDutyCode);
                    oldLPDutySchema.setEndDate(rPolSchema.getCValiDate()); //将原有责任记录的终止日期设为新责任的起领日期
                    oldLPDutySchema.setEdorNo(mEdorNo);
                    oldLPDutySchema.setEdorType(mEdorType);
                    rLPDutySet.add(oldLPDutySchema);

                }
                for (int i = 1; i <= rDutySet.size(); i++)
                {
                    LCDutySchema tLCDutySchema = rDutySet.get(i).getSchema();
                    //新责任设置polno和contno
                    LPDutySchema newLPDutySchema = new LPDutySchema();
                    tRef.transFields(newLPDutySchema, tLCDutySchema);
                    newLPDutySchema.setPolNo(tLCPolSchema.getPolNo());
                    newLPDutySchema.setContNo(tLCPolSchema.getContNo());
                    newLPDutySchema.setEdorNo(mEdorNo);
                    newLPDutySchema.setEdorType(mEdorType);
                    rLPDutySet.add(newLPDutySchema);

                }

                //处理保费项
                tSBql = new StringBuffer(128);
                tSBql.append(
                        "Select a.* From LCPrem a Where a.PolNo='");
                tSBql.append(tLCPolSchema.getPolNo());
                tSBql.append("' and length(trim(a.dutycode))=6");
                LCPremDB tLCPremDB = new LCPremDB();
                LCPremSet tLCPremSet = tLCPremDB.executeQuery(tSBql.toString());
                if (tLCPremSet == null || tLCPremSet.size() <= 0)
                {
                    CError.buildErr(this,
                                    "险种" + tLCPolSchema.getRiskCode() +
                                    "下无保费项信息!");
                    return null;
                }

                for (int j = 1; j <= tLCPremSet.size(); j++)
                {
                    //更新原保费项
                    LPPremSchema oldLPPremSchema = new LPPremSchema();
                    String oldDutyCode = creatDutyCode(tLCPolSchema.
                            getPolNo(), tLCPremSet.get(j).getDutyCode());
                    tRef.transFields(oldLPPremSchema,
                                     tLCPremSet.get(j).getSchema());
                    oldLPPremSchema.setDutyCode(oldDutyCode);
                    oldLPPremSchema.setPayEndDate(rPolSchema.getCValiDate()); //将原有终交日期设为新保费项的起交日期
                    oldLPPremSchema.setEdorNo(mEdorNo);
                    oldLPPremSchema.setEdorType(mEdorType);
                    rLPPremSet.add(oldLPPremSchema);
                }

                for (int i = 1; i <= rPremSet.size(); i++)
                {
                    LCPremSchema tLCPremSchema = rPremSet.get(i).getSchema();
                    //新责任设置polno和contno
                    LPPremSchema newLPPremSchema = new LPPremSchema();
                    tRef.transFields(newLPPremSchema, tLCPremSchema);
                    newLPPremSchema.setPolNo(tLCPolSchema.getPolNo());
                    newLPPremSchema.setContNo(tLCPolSchema.getContNo());
                    newLPPremSchema.setEdorNo(mEdorNo);
                    newLPPremSchema.setEdorType(mEdorType);
                    rLPPremSet.add(newLPPremSchema);

                }

                //处理领取项
                tSBql = new StringBuffer(128);
                tSBql.append(
                        "Select a.* From LCGet a Where a.PolNo='");
                tSBql.append(tLCPolSchema.getPolNo());
                tSBql.append("' and length(trim(a.dutycode))=6");
                LCGetDB tLCGetDB = new LCGetDB();
                LCGetSet tLCGetSet = tLCGetDB.executeQuery(tSBql.toString());
                if (tLCGetSet == null || tLCGetSet.size() <= 0)
                {
                    CError.buildErr(this,
                                    "险种" + tLCPolSchema.getRiskCode() +
                                    "下无领取项信息!");
                    return null;
                }
                for (int j = 1; j <= tLCGetSet.size(); j++)
                {
                    //原有领取项领取终止时间设置为新领取的领取开始时间
                    LPGetSchema oldLPGetSchema = new LPGetSchema();
                    tRef.transFields(oldLPGetSchema,
                                     tLCGetSet.get(j).getSchema());
                    String oldDutyCode = creatDutyCode(tLCPolSchema.
                            getPolNo(), tLCGetSet.get(j).getDutyCode());
                    oldLPGetSchema.setDutyCode(oldDutyCode);
                    oldLPGetSchema.setGetEndDate(rPolSchema.getCValiDate()); //原有领取项领取终止时间设置为新领取的领取开始时间
                    oldLPGetSchema.setEdorNo(mEdorNo);
                    oldLPGetSchema.setEdorType(mEdorType);
                    rLPGetSet.add(oldLPGetSchema);

                }
                for (int i = 1; i <= rGetSet.size(); i++)
                {
                    LCGetSchema tLCGetSchema = rGetSet.get(i).getSchema();
                    //新领取项设置polno和contno
                    LPGetSchema newLPGetSchema = new LPGetSchema();
                    tRef.transFields(newLPGetSchema, tLCGetSchema);
                    newLPGetSchema.setPolNo(tLCPolSchema.getPolNo());
                    newLPGetSchema.setContNo(tLCPolSchema.getContNo());
                    newLPGetSchema.setEdorNo(mEdorNo);
                    newLPGetSchema.setEdorType(mEdorType);
                    rLPGetSet.add(newLPGetSchema);
                }

                tSBql = new StringBuffer(128);
                tSBql.append("delete from lpduty where edorno='");
                tSBql.append(mEdorNo);
                tSBql.append("' and edortype='");
                tSBql.append(mEdorType);
                tSBql.append("' and contno='");
                tSBql.append(tLCPolSchema.getContNo());
                tSBql.append("' and polno='");
                tSBql.append(tLCPolSchema.getPolNo());
                tSBql.append("' ");
                map.put(tSBql.toString(), "DELETE");
                map.put(rLPDutySet, "INSERT");

                tSBql = new StringBuffer(128);
                tSBql.append("delete from lpprem where edorno='");
                tSBql.append(mEdorNo);
                tSBql.append("' and edortype='");
                tSBql.append(mEdorType);
                tSBql.append("' and contno='");
                tSBql.append(tLCPolSchema.getContNo());
                tSBql.append("' and polno='");
                tSBql.append(tLCPolSchema.getPolNo());
                tSBql.append("' ");
                map.put(tSBql.toString(), "DELETE");
                map.put(rLPPremSet, "INSERT");

                tSBql = new StringBuffer(128);
                tSBql.append("delete from lpget where edorno='");
                tSBql.append(mEdorNo);
                tSBql.append("' and edortype='");
                tSBql.append(mEdorType);
                tSBql.append("' and contno='");
                tSBql.append(tLCPolSchema.getContNo());
                tSBql.append("' and polno='");
                tSBql.append(tLCPolSchema.getPolNo());
                tSBql.append("' ");
                map.put(tSBql.toString(), "DELETE");
                map.put(rLPGetSet, "INSERT");

            }

        }

        return map;
    }


    /**
     * 别保人校验
     * @param tLCInsuredSchema LCInsuredSchema
     * @param dutySchema LCDutySchema
     */
    private LCInsuredSchema checkInsured(LCInsuredSchema tLCInsuredSchema)
    {
        StringBuffer tSBql = new StringBuffer(128);
	if(tLCInsuredSchema.getInsuredNo().equals(""))
	{
            if (tLCInsuredSchema.getIDType().equals("9")) {
                tSBql.append(
                        "Select b.* From LCCont a,LCInsured b Where a.ContNo=b.ContNo and a.appflag='1' and a.GrpContNo='");
                tSBql.append(mGrpContNo);
                tSBql.append("' and trim(b.name)='");
                tSBql.append(tLCInsuredSchema.getName());
                tSBql.append("' and b.IDType='");
                tSBql.append(tLCInsuredSchema.getIDType());
                tSBql.append("' and b.IDNo is null");
                tSBql.append(tLCInsuredSchema.getIDNo());
                if (!"0".equals(tLCInsuredSchema.getIDType())) {
                    //当输入的不是身份证时才校验性别和生日
                    tSBql.append(" and b.Sex='");
                    tSBql.append(tLCInsuredSchema.getSex());
                    tSBql.append("' and b.Birthday='");
                    tSBql.append(tLCInsuredSchema.getBirthday());
                }
                tSBql.append("'");

            } else {

                tSBql.append(
                        "Select b.* From LCCont a,LCInsured b Where a.ContNo=b.ContNo and a.appflag='1' and a.GrpContNo='");
                tSBql.append(mGrpContNo);
                tSBql.append("' and trim(b.name)='");
                tSBql.append(tLCInsuredSchema.getName());
                tSBql.append("' and b.IDType='");
                tSBql.append(tLCInsuredSchema.getIDType());
                tSBql.append("' and b.IDNo='");
                tSBql.append(tLCInsuredSchema.getIDNo());
                if (!"0".equals(tLCInsuredSchema.getIDType())) {
                    //当输入的不是身份证时才校验性别和生日
                    tSBql.append("' and b.Sex='");
                    tSBql.append(tLCInsuredSchema.getSex());
                    tSBql.append("' and b.Birthday='");
                    tSBql.append(tLCInsuredSchema.getBirthday());
                }
                tSBql.append("'");
            }
        }
	else
	{
	    tSBql.append(
		    "Select b.* From LCCont a,LCInsured b Where a.ContNo=b.ContNo and a.appflag='1' and a.GrpContNo='");
	    tSBql.append(mGrpContNo);
	    tSBql.append("' and trim(b.insuredno)='");
	    tSBql.append(tLCInsuredSchema.getInsuredNo());
	    tSBql.append("'");
	}
        logger.debug("---------------------"+tSBql.toString());
        LCInsuredDB tLCInsuredDB = new LCInsuredDB();
        LCInsuredSet orgLCInsuredSet = tLCInsuredDB.executeQuery(tSBql.
                toString());
        if (orgLCInsuredSet == null || orgLCInsuredSet.size() <= 0)
        {
            return null;
        }
        return orgLCInsuredSet.get(1).getSchema();
    }


    /**
     * 别保人校验
     * @param tLCInsuredSchema LCInsuredSchema
     * @param dutySchema LCDutySchema
     */
    private String creatDutyCode(String tPolNo, String tDutyCode)
    {
        StringBuffer tSBql = new StringBuffer(128);
        tSBql.append(
                "Select max(substr(a.DutyCode,7,10)) From lcduty a Where a.polno='");
        tSBql.append(tPolNo);
        tSBql.append("' and substr(a.DutyCode,1,6)='");
        tSBql.append(tDutyCode);
        tSBql.append("'");

        ExeSQL exesql = new ExeSQL();
        String dutycode = "";
        String nDutyCode = "";
        dutycode = exesql.getOneValue(tSBql.toString());
        if ("".equals(dutycode))
        {
            dutycode = "0";
        }

        int b = Integer.parseInt(dutycode) + 1;
        Integer c = new Integer(b);
        String d = c.toString();
        if (d.length() == 1)
        {
            nDutyCode = "000" + d;
        }
        else if (d.length() == 2)
        {
            nDutyCode = "00" + d;
        }
        else if (d.length() == 3)
        {
            nDutyCode = "0" + d;
        }
        else
        {
            nDutyCode = d;
        }

        return tDutyCode + nDutyCode;

    }


    private boolean dataSave(MMap map)
    {
        PubSubmit pubSubmit = new PubSubmit();
        VData sData = new VData();
        sData.add(map);
//        boolean tr = pubSubmit.submitData(sData, "");

        if (pubSubmit.submitData(sData, ""))
        {
            return true;
        }
        else
        {
            if (pubSubmit.mErrors.getErrorCount() > 0)
            {
                //错误回退
                mErrors.copyAllErrors(pubSubmit.mErrors);
                pubSubmit.mErrors.clearErrors();
            }
            else
            {
                CError.buildErr(this, "保存数据库的时候失败！");
            }
            return false;
        }
    }


    /**
     * 按保险计划导入的数据准备
     * @param contIndex String     合同Id
     * @param insuredIndex String  被保险人Id
     * @param insuredSchema LCInsuredSchema 主被保险人信息
     * @param tLCContPlanRiskSchema LCContPlanRiskSchema 保险险种计划
     * @return VData
     */
    private VData prepareContPlanData(String contIndex, String insuredIndex
                                      , LCInsuredSchema insuredSchema,
                                      LCContPlanRiskSchema
                                      tLCContPlanRiskSchema)
    {
        VData tNewVData = new VData();
        MMap tmpMap = new MMap();
        String strRiskCode = tLCContPlanRiskSchema.getRiskCode();
        LCGrpPolSchema sLCGrpPolSchema = m_GrpPolImpInfo.findLCGrpPolSchema(
                strRiskCode);
        if (sLCGrpPolSchema == null)
        {
            buildError("prepareContPlanData", strRiskCode + "险种对应的集体投保单没有找到!");
            return null;
        }

        //拷贝一份，避免修改本地数据
        LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
        tLCGrpPolSchema.setSchema(sLCGrpPolSchema);

        String PolKey = m_GrpPolImpInfo.getPolKey(contIndex, insuredIndex,
                                                  strRiskCode);
        //查找被保人相关的合同表信息和被保人表信息
        LCContSchema contSchema = new LCContSchema();
        LCContDB tLCContDB = new LCContDB();
        tLCContDB.setGrpContNo(sLCGrpPolSchema.getGrpContNo());
        tLCContDB.setInsuredName(insuredSchema.getName());
        tLCContDB.setInsuredIDType(insuredSchema.getIDType());
        tLCContDB.setInsuredIDNo(insuredSchema.getIDNo());
        LCContSet tLCContSet = tLCContDB.query();
        if (tLCContSet == null || tLCContSet.size() <= 0)
        {
            CError.buildErr(this, "查不到合同信息", m_GrpPolImpInfo.mErrors);
            return null;
        }
        contSchema = tLCContSet.get(1);
        MMap contMap = new MMap();
        contMap.put(contSchema, "DELETE&INSERT");
        tmpMap.add(contMap);

        LCPolSchema tLCPolSchema = formLCPolSchema(insuredSchema,
                tLCContPlanRiskSchema, contSchema);
        //主险保单信息
        String mainRiskCode = m_GrpPolImpInfo.getMainRiskCode(
                tLCContPlanRiskSchema.getContPlanCode()
                , strRiskCode);

        if (mainRiskCode.equals(""))
        {
            mainRiskCode = strRiskCode;
            mainPolBL.setRiskCode(mainRiskCode);
        }
        String mainPolKey = m_GrpPolImpInfo.getPolKey(contIndex, insuredIndex,
                mainRiskCode);
        tLCPolSchema.setMainPolNo(mainPolKey);
        tLCPolSchema.setInsuredNo(insuredIndex);

        //填充所有保单信息--应该从现有的C表中取lccont等信息，但是由于计算不需要，所以取消，Alex 2005－08－25
//        VData polData = m_GrpPolImpInfo.formatLCPol(tLCPolSchema, PolKey);
//        if (polData == null)
//        {
//            CError.buildErr(this, "出错了:", m_GrpPolImpInfo.mErrors);
//            return null;
//        }
//        MMap polRelaMap = (MMap) polData.getObjectByObjectName("MMap", 0);
//        tLCPolSchema = (LCPolSchema) polData.getObjectByObjectName(
//                "LCPolSchema", 0);
//        tLCPolSchema.setInsuredNo(insuredSchema.getInsuredNo());
//        if (polRelaMap != null && polRelaMap.keySet().size() > 0)
//        {
//            tmpMap.add(polRelaMap);
//        }

        //责任信息查询和生成
        ContPlanQryBL contPlanQryBL = new ContPlanQryBL();
        VData tVData = new VData();
        tVData.add(tLCContPlanRiskSchema);
        boolean b = contPlanQryBL.submitData(tVData, "");
        if (!b || contPlanQryBL.mErrors.needDealError())
        {
            CError.buildErr(this, "查询计划要约出错:", contPlanQryBL.mErrors);
            return null;
        }
        tVData = contPlanQryBL.getResult();
        LCDutySet tDutySet = (LCDutySet) tVData.getObjectByObjectName(
                "LCDutySet", 0);
        if (tDutySet == null)
        {
            LCDutySchema tDutySchema = (LCDutySchema) tVData.
                                       getObjectByObjectName("LCDutySchema"
                    , 0);
            if (tDutySchema == null)
            {
                CError.buildErr(this, "查询计划要约出错:责任信息不能为空");
                return null;
            }
            setPolInfoByDuty(tLCPolSchema, tDutySchema);
            tDutySet = new LCDutySet();
            tDutySet.add(tDutySchema);
        }
        if (tDutySet == null || tDutySet.size() <= 0)
        {
            CError.buildErr(this, strRiskCode + "要约信息生成错误出错");
            return null;
        }

        //重算责任中的insuryearflag,insuryear，置为D和对应的天数
        //对应天数指险种生效日期与团单此险种的结束日期之间的天数
        LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
        tLMRiskAppDB.setRiskCode(tLCPolSchema.getRiskCode());
        if (tLMRiskAppDB.getInfo())
        {
            if (!"L".equals(tLMRiskAppDB.getRiskPeriod()))
            { //短险才需要重算保险期间，长险另外处理
                int insurDays = 0;
                for (int dutyIndex = 1; dutyIndex <= tDutySet.size();
                                     dutyIndex++)
                {
                    String sql =
                            "select b.enddate from lcpol b where b.grpcontno='" +
                            tLCPolSchema.getGrpContNo() +
                            "' and b.appflag='1'";
                    ExeSQL tExeSQl = new ExeSQL();
                    String EndDate = tExeSQl.getOneValue(sql);
                    if (EndDate != null && !"".equals(EndDate))
                    {
                        insurDays = PubFun.calInterval(tLCPolSchema.
                                getCValiDate(), EndDate, "D");
                        if (insurDays > 0)
                        {
                            tDutySet.get(dutyIndex).setInsuYear(insurDays);
                            tDutySet.get(dutyIndex).setInsuYearFlag("D");
                        }
                    }
                }
                if (tDutySet.size() == 1 && insurDays != 0)
                {
                    tLCPolSchema.setInsuYear(insurDays);
                    tLCPolSchema.setInsuYearFlag("D");
                }
            }
        }

        tNewVData.add(tDutySet);
        //保费
        LCPremSet tPremSet = new LCPremSet();
        tNewVData.add(tPremSet);

        //连身被保险人信息
        //处理连身被保险人
        String[] relaIns = null;
        relaIns = m_GrpPolImpInfo.getInsuredRela(contIndex, insuredIndex,
                                                 strRiskCode);
        if (relaIns != null)
        {
            LCInsuredRelatedSet tRelaInsSet = m_GrpPolImpInfo.
                                              getInsuredRelaData(tLCPolSchema.
                    getInsuredNo(), relaIns,mBatchNo);
            if (tRelaInsSet == null)
            {
                //创建连带被保险人出错
                mErrors.copyAllErrors(m_GrpPolImpInfo.mErrors);
                return null;
            }
            tNewVData.add(tRelaInsSet);
        }

        //加入对应险种的集体投保单信息,险种承保描述信息
        LMRiskAppSchema tLMRiskAppSchema = m_GrpPolImpInfo.findLMRiskAppSchema(
                strRiskCode);
        if (tLMRiskAppSchema == null)
        {
            buildError("prepareContPlanData", strRiskCode + "险种对应的险种承保描述没有找到!");
            return null;
        }
        LMRiskSchema tLMRiskSchema = m_GrpPolImpInfo.findLMRiskSchema(
                strRiskCode);
        if (tLMRiskSchema == null)
        {
            buildError("prepareContPlanData", strRiskCode + "险种对应的险种承保描述没有找到!");
            return null;
        }

        LDGrpSchema ttLDGrpSchema = m_GrpPolImpInfo.findLDGrpSchema(
                tLCGrpPolSchema.getCustomerNo());
        if (ttLDGrpSchema == null)
        {
            buildError("prepareContPlanData",
                       tLCGrpPolSchema.getCustomerNo() + "对应的集体客户信息没有找到!");
            return null;
        }

        //其他信息
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("samePersonFlag", "0");
        tTransferData.setNameAndValue("GrpImport", 1);
        tTransferData.setNameAndValue("ID", PolKey);
        tTransferData.setNameAndValue("PolKey", PolKey);

        //设置保全保存标记
        tTransferData.setNameAndValue("SavePolType", "2");
        tTransferData.setNameAndValue("EdorType",
                                      mTransferData.
                                      getValueByName("EdorType"));
        tTransferData.setNameAndValue("CValiDate",
                                      mTransferData.
                                      getValueByName("CValiDate"));

        tNewVData.add(mGlobalInput);
        tNewVData.add(contSchema);
        tNewVData.add(insuredSchema);
        tNewVData.add(tLCGrpPolSchema);
        tNewVData.add(tLMRiskAppSchema);
        tNewVData.add(tLMRiskSchema);
        tNewVData.add(ttLDGrpSchema);
        tNewVData.add(m_GrpPolImpInfo.getLCGrpAppntSchema());
        tNewVData.add(tLCPolSchema);
        tNewVData.add(tTransferData);
        tNewVData.add(this.mainPolBL);
        tNewVData.add(tmpMap);
        return tNewVData;
    }


    /**
     * 生成保单信息
     * @param insuredSchema LCInsuredSchema
     * @param tLCContPlanRiskSchema LCContPlanRiskSchema
     * @param contSchema LCContSchema
     * @return LCPolSchema
     */
    private LCPolSchema formLCPolSchema(LCInsuredSchema insuredSchema
                                        ,
                                        LCContPlanRiskSchema
                                        tLCContPlanRiskSchema,
                                        LCContSchema contSchema)
    {
        LCPolSchema tLCPolSchema = new LCPolSchema();
        String strRiskCode = tLCContPlanRiskSchema.getRiskCode();

        LCGrpPolSchema tLCGrpPolSchema = m_GrpPolImpInfo.findLCGrpPolSchema(
                strRiskCode);

        if (tLCGrpPolSchema == null)
        {
            buildError("formatLCPol", "找不到指定险种所对应的集体保单，险种为：" + strRiskCode);
            return null;
        }

        tLCPolSchema.setPrtNo(tLCGrpPolSchema.getPrtNo());
        tLCPolSchema.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
        if ("Y".equals(tLCPolSchema.getSpecifyValiDate()) &&
            tLCPolSchema.getCValiDate() != null)
        {
            //如果磁盘倒入指定生效日期，且生效日期字段有值,那么就用 生效日期字段的值
        }
        else
        {
            //在导入文件的险种sheet中没有指定时，默认回溯为整个团单的生效日期
            tLCPolSchema.setCValiDate(tLCGrpPolSchema.getCValiDate());
            //判断是否在导入文件被保人sheet指定了保全生效日期
            /*jixf
            if (insuredSchema.getIValiDate() != null &&
                !insuredSchema.getIValiDate().equals(""))
            {
                tLCPolSchema.setCValiDate(insuredSchema.getIValiDate());
                tLCPolSchema.setSpecifyValiDate("Y");
            }
            */

        }

        tLCPolSchema.setManageCom(tLCGrpPolSchema.getManageCom());
        tLCPolSchema.setSaleChnl(tLCGrpPolSchema.getSaleChnl());
        tLCPolSchema.setAgentCom(tLCGrpPolSchema.getAgentCom());
        tLCPolSchema.setAgentCode(tLCGrpPolSchema.getAgentCode());
        tLCPolSchema.setAgentGroup(tLCGrpPolSchema.getAgentGroup());
        tLCPolSchema.setAgentCode1(tLCGrpPolSchema.getAgentCode1());
        tLCPolSchema.setGrpContNo(tLCGrpPolSchema.getGrpContNo());
        tLCPolSchema.setContType("2");
//        tLCPolSchema.setPolTypeFlag("2");
        tLCPolSchema.setPolTypeFlag(contSchema.getPolType());
        tLCPolSchema.setInsuredPeoples(contSchema.getPeoples());
        tLCPolSchema.setRiskCode(strRiskCode);

        tLCPolSchema.setContNo(insuredSchema.getContNo().trim());
        tLCPolSchema.setInsuredSex(insuredSchema.getSex().trim());
        tLCPolSchema.setInsuredBirthday(insuredSchema.getBirthday());
        tLCPolSchema.setInsuredName(insuredSchema.getName().trim());

        tLCPolSchema.setInsuredNo(insuredSchema.getInsuredNo());

        return tLCPolSchema;

    }


    public String getExtendFileName(String aFileName)
    {
        File tFile = new File(aFileName);
        String aExtendFileName = "";
        String name = tFile.getName();
        for (int i = name.length() - 1; i >= 0; i--)
        {
            if (i < 1)
            {
                i = 1;
            }
            if (name.substring(i - 1, i).equals("."))
            {
                aExtendFileName = name.substring(i, name.length());
                logger.debug("ExtendFileName;" + aExtendFileName);
                return aExtendFileName;
            }
        }
        return aExtendFileName;
    }


    /**
     * 字符串替换
     * @param s1 String
     * @param OriginStr String
     * @param DestStr String
     * @return String
     */
    public static String replace(String s1, String OriginStr, String DestStr)
    {
        s1 = s1.trim();
        int mLenOriginStr = OriginStr.length();
        for (int j = 0; j < s1.length(); j++)
        {
            int befLen = s1.length();
            if (s1.substring(j, j + 1) == null ||
                s1.substring(j, j + 1).trim().equals(""))
            {
                continue;
            }
            else
            {
                if (OriginStr != null && DestStr != null)
                {
                    if (j + mLenOriginStr <= s1.length())
                    {

                        if (s1.substring(j, j + mLenOriginStr).equals(OriginStr))
                        {

                            OriginStr = s1.substring(j, j + mLenOriginStr);

                            String startStr = s1.substring(0, j);
                            String endStr = s1.substring(j + mLenOriginStr,
                                    s1.length());

                            s1 = startStr + DestStr + endStr;

                            j = j + s1.length() - befLen;
                            if (j < 0)
                            {
                                j = 0;
                            }
                        }
                    }
                    else
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
        }
        return s1;
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


    /**
     * Detach node from original document, fast XPathAPI process.
     * @param node Node
     * @return Node
     * @throws Exception
     */
    private org.w3c.dom.Node transformNode(org.w3c.dom.Node node) throws
            Exception
    {
        Node nodeNew = m_doc.importNode(node, true);

        return nodeNew;
    }


    /**
     * 创建错误日志
     * @param szFunc String
     * @param szErrMsg String
     */
    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError();
        cError.moduleName = "EdorParseGuideIn";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }


    /**
     * 记录日志信息
     * @param contId String
     * @param insuredId String
     * @param polId String
     * @param contplancode String
     * @param riskcode String
     * @param insuredSchema LCInsuredSchema
     */
    private void logError(String contId, String insuredId, String polId,
                          String contplancode,
                          String riskcode, LCInsuredSchema insuredSchema)
    {
        if (m_GrpPolImpInfo.mErrors.getErrorCount() >= 0)
        {
            this.mErrors.copyAllErrors(m_GrpPolImpInfo.mErrors);
        }
        m_GrpPolImpInfo.logError(mBatchNo, mGrpContNo, contId, insuredId, polId,
                                 contplancode, riskcode, insuredSchema,
                                 this.mErrors, mGlobalInput);
        logErrors.copyAllErrors(this.mErrors);
        this.mErrors.clearErrors();
        m_GrpPolImpInfo.mErrors.clearErrors();

    }


    /**
     * 通过duty设置一些lcpol的元素
     * @param tLCPolSchema LCPolSchema
     * @param dutySchema LCDutySchema
     */
    private static void setPolInfoByDuty(LCPolSchema tLCPolSchema,
                                         LCDutySchema dutySchema)
    {

        tLCPolSchema.setInsuYear(dutySchema.getInsuYear());
        tLCPolSchema.setInsuYearFlag(dutySchema.getInsuYearFlag());
        tLCPolSchema.setPrem(dutySchema.getPrem());
        tLCPolSchema.setAmnt(dutySchema.getAmnt());
        tLCPolSchema.setPayEndYear(dutySchema.getPayEndYear());
        tLCPolSchema.setPayEndYearFlag(dutySchema.getPayEndYearFlag());
        tLCPolSchema.setGetYear(dutySchema.getGetYear());
        tLCPolSchema.setGetYearFlag(dutySchema.getGetYearFlag());
        tLCPolSchema.setAcciYear(dutySchema.getAcciYear());
        tLCPolSchema.setAcciYearFlag(dutySchema.getAcciYearFlag());
        tLCPolSchema.setMult(dutySchema.getMult());
        //计算方向,在按分数卖的保单，切记算方向为O的时候
        if (dutySchema.getMult() > 0 &&
            "O".equals(StrTool.cTrim(dutySchema.getPremToAmnt())))
        {
            tLCPolSchema.setPremToAmnt(dutySchema.getPremToAmnt());
        }
        tLCPolSchema.setStandbyFlag1(dutySchema.getStandbyFlag1());
        tLCPolSchema.setStandbyFlag2(dutySchema.getStandbyFlag2());
        tLCPolSchema.setStandbyFlag3(dutySchema.getStandbyFlag3());
    }

    private static void setDutyByPolInfo(LCDutySchema dutySchema,
                                         LCPolSchema tLCPolSchema)
    {
        if (tLCPolSchema.getMult() > 0)
        {
            dutySchema.setMult(tLCPolSchema.getMult());
        }
        if (tLCPolSchema.getPrem() > 0)
        {
            dutySchema.setPrem(tLCPolSchema.getPrem());
        }
        if (tLCPolSchema.getAmnt() > 0)
        {
            dutySchema.setAmnt(tLCPolSchema.getAmnt());
        }
        if (tLCPolSchema.getPayIntv() > 0)
        {
            dutySchema.setPayIntv(tLCPolSchema.getPayIntv());
        }
        if (tLCPolSchema.getInsuYear() > 0)
        {
            dutySchema.setInsuYear(tLCPolSchema.getInsuYear());
        }
        if (!"".equals(StrTool.cTrim(tLCPolSchema.getInsuYearFlag())))
        {
            dutySchema.setInsuYearFlag(tLCPolSchema.getInsuYearFlag());
        }
        if (tLCPolSchema.getPayEndYear() > 0)
        {
            dutySchema.setPayEndYear(tLCPolSchema.getPayEndYear());
        }
        if (!"".equals(StrTool.cTrim(tLCPolSchema.getPayEndYearFlag())))
        {
            dutySchema.setPayEndYearFlag(tLCPolSchema.getPayEndYearFlag());
        }
        if (tLCPolSchema.getGetYear() > 0)
        {
            dutySchema.setGetYear(tLCPolSchema.getGetYear());
        }
        if (!"".equals(StrTool.cTrim(tLCPolSchema.getGetYearFlag())))
        {
            dutySchema.setGetYearFlag(tLCPolSchema.getGetYearFlag());
        }
        if (!"".equals(StrTool.cTrim(tLCPolSchema.getGetStartType())))
        {
            dutySchema.setGetStartType(tLCPolSchema.getGetStartType());
        }
        //计算方向
        if (!"".equals(StrTool.cTrim(tLCPolSchema.getPremToAmnt())))
        {
            dutySchema.setPremToAmnt(tLCPolSchema.getPremToAmnt());
        }
        //计算规则被缓存在 最终核保人编码 UWCode 字段
        if (!"".equals(StrTool.cTrim(tLCPolSchema.getUWCode())))
        {
            dutySchema.setCalRule(tLCPolSchema.getUWCode());
        }
        //费率
        if (tLCPolSchema.getFloatRate() > 0)
        {
            if ("0".equals(dutySchema.getCalRule()) ||
                "1".equals(dutySchema.getCalRule()))
            {
                //计算规则为 0-表定费率 或 1-统一费率 时
                //费率必须是从默认值中取得
            }
            else
            {
                dutySchema.setFloatRate(tLCPolSchema.getFloatRate());
            }
        }

        if (!"".equals(StrTool.cTrim(tLCPolSchema.getStandbyFlag1())))
        {
            dutySchema.setStandbyFlag1(tLCPolSchema.getStandbyFlag1());
        }
        if (!"".equals(StrTool.cTrim(tLCPolSchema.getStandbyFlag2())))
        {
            dutySchema.setStandbyFlag2(tLCPolSchema.getStandbyFlag2());
        }
        if (!"".equals(StrTool.cTrim(tLCPolSchema.getStandbyFlag3())))
        {
            dutySchema.setStandbyFlag3(tLCPolSchema.getStandbyFlag3());
        }
        if (!"".equals(StrTool.cTrim(tLCPolSchema.getApproveFlag())))
        {
            dutySchema.setGetLimit(tLCPolSchema.getApproveFlag());
        }
        if (!"".equals(StrTool.cTrim(tLCPolSchema.getUWFlag())))
        {
            dutySchema.setGetRate(tLCPolSchema.getUWFlag());
        }

    }


    public static void main(String[] args)
    {
        EdorParseGuideIn tPGI = new EdorParseGuideIn();
        VData tVData = new VData();
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("BQFlag", "Y");
        //批改类型 “NI”代表增加被保人
        tTransferData.setNameAndValue("EdorType", "ZT");
        tTransferData.setNameAndValue("EdorTypeCal", "091");
        tTransferData.setNameAndValue("EdorNo","410101000004");
        tTransferData.setNameAndValue("FileName", "55555555666666_01.xls");
        tTransferData.setNameAndValue("FilePath", "E:");
        GlobalInput tG = new GlobalInput();
        tG.Operator = "GrpImport";
        tG.ManageCom = "86110000";

        long a = System.currentTimeMillis();
        logger.debug(a);
        tVData.add(tTransferData);
        tVData.add(tG);
        tPGI.submitData(tVData, "");
        logger.debug(System.currentTimeMillis() - a);
    }
}
