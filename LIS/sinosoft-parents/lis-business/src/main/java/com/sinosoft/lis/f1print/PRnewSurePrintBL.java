package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lh
 * @version 1.0
 */
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.utility.*;

import java.io.*;

import java.text.*;

import java.util.Date;


public class PRnewSurePrintBL implements PrintService
{
private static Logger logger = Logger.getLogger(PRnewSurePrintBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();

    //取得的保单号码
    private String mPolNo = "";

    //输入的查询sql语句
    private String mBatchSQL = "";
    SQLwithBindVariables sqlbvbatch = new SQLwithBindVariables();
    //客户端ip
    private String mIP = "";

    //配置文件
    private String mConfigFile = "";

    //取得的延期承保原因
    private String mUWError = "";

    //取得的代理人编码
    private String mAgentCode = "";

    //业务处理相关变量

    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
    private LCPolSchema mLCPolSchema = new LCPolSchema();
    private LCPolSchema mMainLCPolSchema = new LCPolSchema();
    private LCPolSet mSubLCPolSet = new LCPolSet();
    private LOPRTManagerSubSet tSubLOPRTManagerSubSet = new LOPRTManagerSubSet();
    private LOPRTManagerSubSchema tMainLOPRTManagerSubSchema = new LOPRTManagerSubSchema();
    private LCPolSet mLCPolSet = new LCPolSet();
    private LAAgentSchema mLAAgentSchema = new LAAgentSchema();
    private String FORMATMODOL = "0.00"; //保费保额计算出来后的精确位数
    private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL); //数字转换对象

    public PRnewSurePrintBL()
    {
    }

    /**
    传输数据的公共方法
    */
    public boolean submitData(VData cInputData, String cOperate)
    {
        if (!cOperate.equals("PRINT") && !cOperate.equals("PRINTBATCH"))
        {
            buildError("submitData", "不支持的操作字符串");

            return false;
        }

        // 得到外部传入的数据，将数据备份到本类中
        if (cOperate.equals("PRINT"))
        {
            if (!getInputData(cInputData))
            {
                return false;
            }
        }
        else
        {
            if (!getInputDataBatch(cInputData))
            {
                return false;
            }
        }

        mResult.clear();

        // 准备所有要打印的数据
        if (cOperate.equals("PRINT"))
        {
            if (!getPrintData("printer"))
            {
                return false;
            }
        }
        else //批量打印
        {
            if (!getPrintDataBatch())
            {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args)
    {
        LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
        tLOPRTManagerSchema.setPrtSeq("86110020040810010217");

        VData cInputData = new VData();
        cInputData.add(tLOPRTManagerSchema);

        PRnewSurePrintBL tPRnewSurePrintBL = new PRnewSurePrintBL();
        tPRnewSurePrintBL.submitData(cInputData, "PRINT");
    }

    /**
     * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
    {
        return true;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
        //全局变量
        //  mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
        mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData.getObjectByObjectName(
                "LOPRTManagerSchema", 0));

        if (mLOPRTManagerSchema == null)
        {
            buildError("getInputData", "没有得到足够的信息！");

            return false;
        }

        if (mLOPRTManagerSchema.getPrtSeq() == null)
        {
            buildError("getInputData", "没有得到足够的信息:印刷号不能为空！");

            return false;
        }

        return true;
    }

    private boolean getInputDataBatch(VData cInputData)
    {
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
                "GlobalInput", 0));
        if(cInputData.get(1) instanceof SQLwithBindVariables){
        	sqlbvbatch = (SQLwithBindVariables)cInputData.get(1);
        	mBatchSQL = sqlbvbatch.sql();
        }else{
        	mBatchSQL = (String) cInputData.get(1);
        	sqlbvbatch.sql(mBatchSQL);
        }
        
        mIP = (String) cInputData.get(2);
        mConfigFile = (String) cInputData.get(3);

        if (mBatchSQL == null)
        {
            buildError("getInputDataBatch", "没有得到足够的信息！");

            return false;
        }

        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

    public CErrors getErrors()
    {
        return mErrors;
    }

    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError();

        cError.moduleName = "LCPolF1PBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }

    private boolean getPrintData(String tPrinter)
    {
        //根据印刷号查询打印队列中的纪录
        String PrtNo = mLOPRTManagerSchema.getPrtSeq();
        String tOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();
        LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
        tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);

        if (tLOPRTManagerDB.getInfo() == false)
        {
            mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
            buildError("outputXML", "在取得打印队列中数据时发生错误");

            return false;
        }

        mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();

        boolean RemarkFlag = false; //保全核保中该变量记录保全备注部分的判断标记sxy
        boolean AddFeeFlag = false; //打印加费部分的判断标志
        boolean SpecFlag = false; //打印特别约定部分的判断标志
        double SumPrem = 0; //合计保费
        double oldSumPrem = 0; //合计加费
        String MainRiskName = "";
        LCPolDB tLCPolDB = new LCPolDB();
        String strSQL = "SELECT * FROM LCPol WHERE contno = '" +
            "?contno?" +
            "' and polno=mainpolno and appflag ='1'";
        SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
        sqlbv1.sql(strSQL);
        sqlbv1.put("contno", mLOPRTManagerSchema.getOtherNo());
        LCPolSet tempLCPolSet = tLCPolDB.executeQuery(sqlbv1);

        if (tempLCPolSet.size() == 0)
        {
            mErrors.copyAllErrors(tLCPolDB.mErrors);
            buildError("outputXML", "在LCPol表中找不到相关信息");

            return false;
        }

        int m;
        int i;
        tLCPolDB.setSchema(tempLCPolSet.get(1));

        if (!tLCPolDB.getPolNo().equals(tLCPolDB.getMainPolNo()))
        {
            //查询主险保单
            mLCPolSchema = queryMainPol(tLCPolDB.getMainPolNo());

            if (mLCPolSchema == null)
            {
                buildError("outputXML", "没有查询到主险保单");

                return false;
            }
        }
        else
        {
            mLCPolSchema = tLCPolDB.getSchema(); //保存主险投保单信息
        }

        mMainLCPolSchema.setSchema(mLCPolSchema);
        mAgentCode = mLCPolSchema.getAgentCode();

        LAAgentDB tLAAgentDB = new LAAgentDB();
        tLAAgentDB.setAgentCode(mAgentCode);

        if (!tLAAgentDB.getInfo())
        {
            mErrors.copyAllErrors(tLAAgentDB.mErrors);
            buildError("outputXML", "在取得LAAgent的数据时发生错误");

            return false;
        }

        mLAAgentSchema = tLAAgentDB.getSchema(); //保存代理人信息

        LDCodeSchema tLDCodeSchema = new LDCodeSchema();
        LDCodeDB tLDCodeDB = new LDCodeDB();
        tLDCodeDB.setCodeType("station");
        tLDCodeDB.setCode(mLCPolSchema.getManageCom());

        if (!tLDCodeDB.getInfo())
        {
            mErrors.copyAllErrors(tLDCodeDB.mErrors);
            buildError("outputXML", "在取得管理机构名称数据时发生错误");

            return false;
        }

        tLDCodeSchema = tLDCodeDB.getSchema();

        //select * from LDCode where CodeType='station' and Code='86110000'
        LDComSchema tLDComSchema = new LDComSchema();
        LDComDB tLDComDB = new LDComDB();
        tLDComDB.setComCode(mLCPolSchema.getManageCom().substring(0, 4));
        logger.debug(mLCPolSchema.getManageCom().substring(0, 4));

        if (!tLDComDB.getInfo())
        {
            mErrors.copyAllErrors(tLDCodeDB.mErrors);
            buildError("outputXML", "在取得管理机构名称数据时发生错误");

            return false;
        }

        tLDComSchema = tLDComDB.getSchema();

        //select * from LDCode where CodeType='station' and Code='86110000'
        LOPRTManagerSubDB tLOPRTManagerSubDB = new LOPRTManagerSubDB();
        LOPRTManagerSubSet tLOPRTManagerSubSet = new LOPRTManagerSubSet();
        tLOPRTManagerSubDB.setPrtSeq(mLOPRTManagerSchema.getOldPrtSeq());
        tLOPRTManagerSubSet = tLOPRTManagerSubDB.query();

        if ((tLOPRTManagerSubSet == null) && (tLOPRTManagerSubSet.size() <= 0))
        {
            mErrors.copyAllErrors(tLAAgentDB.mErrors);
            buildError("outputXML", "在取得LOPRTManagerSub的数据时发生错误");

            return false;
        }

        logger.debug("tLOPRTManagerSubSet.size():" +
            tLOPRTManagerSubSet.size());

        for (int j = 1; j <= tLOPRTManagerSubSet.size(); j++)
        {
            if (tLOPRTManagerSubSet.get(j).getRiskCode().trim().equals(mLCPolSchema.getRiskCode()
                                                                                       .trim()))
            { //主险
                tMainLOPRTManagerSubSchema = tLOPRTManagerSubSet.get(j);
            }
            else
            { //附加险

                ExeSQL tExeSQL = new ExeSQL();
                SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
                sqlbv2.sql("SELECT COUNT(*) FROM lcpol WHERE polno= '"+"?polno?"+"' and paytodate=payenddate and rnewflag='-1'");
                sqlbv2.put("polno", tLOPRTManagerSubSet.get(j).getOtherNo());
                String count = tExeSQL.getOneValue(sqlbv2);

                //                if ((count != null) && !count.equals("0"))
                //                {
                //                    continue;
                //                }
                if ((count == null) || count.equals("0"))
                {
                    continue;
                }

                tSubLOPRTManagerSubSet.add(tLOPRTManagerSubSet.get(j));

                LCPolDB tempLCPolDB = new LCPolDB();
                tempLCPolDB.setProposalNo(tLOPRTManagerSubSet.get(j).getOtherNo());

                LCPolSet tLCPolSet = new LCPolSet();
                tLCPolSet = tempLCPolDB.query();

                if ((tLCPolSet == null) && (tLCPolSet.size() != 1))
                {
                    mErrors.copyAllErrors(tLCPolDB.mErrors);
                    buildError("outputXML", "在取得LCPol的数据时发生错误");

                    return false;
                }

                mSubLCPolSet.add(tLCPolSet.get(1));
            }
        }

        logger.debug("mSubLCPolSet.size():" + mSubLCPolSet.size());

        //1-续保缴费险种信息：
        ListTable tRiskListTable = new ListTable();
        String[] RiskInfoTitle = new String[6];
        String[] RiskInfo = new String[7];
        tRiskListTable.setName("MAIN"); //对应模版投保信息部分的行对象名
        RiskInfoTitle[0] = "RiskName"; //险种名称
        RiskInfoTitle[1] = "Amnt"; //保险金额
        RiskInfoTitle[2] = "PayYears"; //缴费年期
        RiskInfoTitle[3] = "Prem"; //保费
        RiskInfoTitle[4] = "PayIntv"; //缴费方式
        RiskInfoTitle[5] = "CValidate"; //保险期间

        String sTemp = "";
        Double fTemp;

        if ((tMainLOPRTManagerSubSchema != null) &&
                (tMainLOPRTManagerSubSchema.getPrtSeq() != null) &&
                !tMainLOPRTManagerSubSchema.getPrtSeq().trim().equals(""))
        {
            LMRiskDB tLMRiskDB = new LMRiskDB();
            tLMRiskDB.setRiskCode(mMainLCPolSchema.getRiskCode());

            if (!tLMRiskDB.getInfo())
            {
                mErrors.copyAllErrors(tLMRiskDB.mErrors);
                buildError("outputXML", "在取得主险LMRisk的数据时发生错误");

                return false;
            }

            //校验个保单的有效性
            LCPolDB tempLCPolDB = new LCPolDB();
            LCPolSet tLCPolSet = new LCPolSet();
            tempLCPolDB.setProposalNo(tMainLOPRTManagerSubSchema.getOtherNo());
            tLCPolSet = tempLCPolDB.query();

            if ((tLCPolSet == null) || (tLCPolSet.size() < 1))
            {
                CError tError = new CError();
                tError.moduleName = "PRnewManualDunBL";
                tError.functionName = "checkData";
                tError.errorMessage = "投保单号为〔" +
                    tMainLOPRTManagerSubSchema.getOtherNo() + "〕的投保单表信息查询失败!";
                this.mErrors.addOneError(tError);

                return false;
            }

            mMainLCPolSchema = tLCPolSet.get(1);

            MainRiskName = tLMRiskDB.getRiskName();
            RiskInfo[0] = tLMRiskDB.getRiskName(); //险种名称
            fTemp = new Double(mMainLCPolSchema.getAmnt());

            int iTemp = 0;
            RiskInfo[1] = fTemp.toString(); //保险金额

            if ((mMainLCPolSchema.getPayEndYear() == 1000) &&
                    mMainLCPolSchema.getPayEndYearFlag().equals("A"))
            {
                RiskInfo[2] = "终生"; //交费年期
            }
            else
            {
                RiskInfo[2] = (new Integer(mMainLCPolSchema.getPayYears())).toString(); //交费年期
            }

            RiskInfo[3] = new Double(tMainLOPRTManagerSubSchema.getDuePayMoney()).toString(); //本期主险续期保费

            if (mLCPolSchema.getPayIntv() == -1)
            {
                sTemp = "不定期交费";
            }

            if (mLCPolSchema.getPayIntv() == 0)
            {
                sTemp = "趸交";
            }

            if (mLCPolSchema.getPayIntv() == 1)
            {
                sTemp = "月交";
            }

            if (mLCPolSchema.getPayIntv() == 3)
            {
                sTemp = "季交";
            }

            if (mLCPolSchema.getPayIntv() == 6)
            {
                sTemp = "半年交";
            }

            if (mLCPolSchema.getPayIntv() == 12)
            {
                sTemp = "年交";
            }

            RiskInfo[4] = sTemp; //交费方式

            RiskInfo[5] = "自 " + mMainLCPolSchema.getCValiDate();
            RiskInfo[6] = "至 " +
                PubFun.calDate(mMainLCPolSchema.getEndDate(), -1, "D", null);

            tRiskListTable.add(RiskInfo); //加入主险信息
            SumPrem = SumPrem + tMainLOPRTManagerSubSchema.getDuePayMoney(); // 本次续保催收保费合计
        }

        //1.2-取附险信息
        if ((tSubLOPRTManagerSubSet != null) &&
                (tSubLOPRTManagerSubSet.size() > 0))
        {
            for (int n = 1; n <= mSubLCPolSet.size(); n++)
            {
                LMRiskDB tLMRiskDB = new LMRiskDB();
                LCPolSchema tLCPolSchema = new LCPolSchema();
                LOPRTManagerSubSchema tLOPRTManagerSubSchema = new LOPRTManagerSubSchema();
                tLOPRTManagerSubSchema = tSubLOPRTManagerSubSet.get(n);
                tLCPolSchema = mSubLCPolSet.get(n);
                RiskInfo = new String[7];
                tLMRiskDB = new LMRiskDB();
                tLMRiskDB.setRiskCode(tLCPolSchema.getRiskCode());

                if (!tLMRiskDB.getInfo())
                {
                    mErrors.copyAllErrors(tLMRiskDB.mErrors);
                    buildError("outputXML", "在取得附险LMRisk的数据时发生错误");

                    return false;
                }

                RiskInfo[0] = tLMRiskDB.getRiskName(); //险种名称
                fTemp = new Double(tLCPolSchema.getAmnt());
                RiskInfo[1] = fTemp.toString(); //保险金额

                if ((tLCPolSchema.getPayEndYear() == 1000) &&
                        tLCPolSchema.getPayEndYearFlag().equals("A"))
                {
                    RiskInfo[2] = "终生"; //交费年期
                }
                else
                {
                    RiskInfo[2] = (new Integer(tLCPolSchema.getPayYears())).toString(); //交费年期
                }

                sTemp = "";
                RiskInfo[3] = new Double(tLOPRTManagerSubSchema.getDuePayMoney()).toString(); //保费

                if (tLCPolSchema.getPayIntv() == -1)
                {
                    sTemp = "不定期交费";
                }

                if (tLCPolSchema.getPayIntv() == 0)
                {
                    sTemp = "趸交";
                }

                if (tLCPolSchema.getPayIntv() == 1)
                {
                    sTemp = "月交";
                }

                if (tLCPolSchema.getPayIntv() == 3)
                {
                    sTemp = "季交";
                }

                if (tLCPolSchema.getPayIntv() == 6)
                {
                    sTemp = "半年交";
                }

                if (tLCPolSchema.getPayIntv() == 12)
                {
                    sTemp = "年交";
                }

                RiskInfo[4] = sTemp; //交费方式
                RiskInfo[5] = "自 " + tLCPolSchema.getCValiDate();
                RiskInfo[6] = "至 " +
                    PubFun.calDate(tLCPolSchema.getEndDate(), -1, "D", null);
                tRiskListTable.add(RiskInfo); //加入附险信息
                SumPrem = SumPrem + tLOPRTManagerSubSchema.getDuePayMoney(); // 原保费合计
            }
        }

        //SpecAddFeeSum=Double.parseDouble(mDecimalFormat.format(SpecAddFeeSum));//转换计算后的保费(规定的精度)
        SumPrem = Double.parseDouble(mDecimalFormat.format(SumPrem)); //转换计算后的保费(规定的精度)

        //其它模版上单独不成块的信息
        String PolNo = "";
        
        PolNo = mMainLCPolSchema.getContNo();
       
        TextTag texttag = new TextTag(); //新建一个TextTag的实例
        XmlExport xmlexport = new XmlExport(); //新建一个XmlExport的实例
        xmlexport.createDocument("PRnewSure.vts", tPrinter); //最好紧接着就初始化xml文档

        //生成-年-月-日格式的日期
        StrTool tSrtTool = new StrTool();
        String SysDate = tSrtTool.getYear() + "年" + tSrtTool.getMonth() + "月" +
            tSrtTool.getDay() + "日";

        //新增投保人通信地址和投保人邮编
        ExeSQL ttExeSQL = new ExeSQL();
        SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
        sqlbv3.sql(" select b.zipcode,b.postaladdress from lcappnt a,LCAddress b  "
                + " where a.appntno=b.customerno and a.addressno=b.addressno and a.contno='"+"?contno?"+"' ");
        sqlbv3.put("contno", mMainLCPolSchema.getContNo());
        SSRS tSSRS = ttExeSQL.execSQL(sqlbv3);

        if (ttExeSQL.mErrors.needDealError() || (tSSRS == null))
        {
            CError.buildErr(this, "查询投保人通信地址/邮编失败" + tLCPolDB.getPolNo() + ")");
        }

        texttag.add("ZipCode", tSSRS.GetText(1, 1)); //投保人邮编
        texttag.add("Address", tSSRS.GetText(1, 2)); //投保人地址
        
        LCContSchema tLCContSchema = new LCContSchema();
        LCContDB tLCContDB = new LCContDB();
        tLCContDB.setContNo(mMainLCPolSchema.getContNo());
        if(!tLCContDB.getInfo())
        {
            CError.buildErr(this, "查询合同信息失败" + mMainLCPolSchema.getContNo()+ ")");
        }
        tLCContSchema= tLCContDB.getSchema();

        //模版自上而下的元素
        texttag.add("LCPol.AppntName", mMainLCPolSchema.getAppntName()); //投保人名称
        texttag.add("LCPol.InsuredName", mMainLCPolSchema.getInsuredName()); //投保人名称
        texttag.add("LCPol.PolNo", PolNo); //投保单号
        texttag.add("LCPol.AccName", tLCContSchema.getAccName()); //账户名
        texttag.add("LCPol.BankAccNo", tLCContSchema.getBankAccNo()); //账户号
        texttag.add("RiskName", MainRiskName); //主险名称
        texttag.add("LCPol.CValiDate", mMainLCPolSchema.getCValiDate());
        texttag.add("FeeSum", SumPrem); //合计续保保费

        texttag.add("OldSumPrem", oldSumPrem);

        texttag.add("LAAgent.Name", mLAAgentSchema.getName()); //代理人姓名
        texttag.add("LCPol.AgentCode", mMainLCPolSchema.getAgentCode()); //代理人业务号
        texttag.add("LCPol.ManageCom",
            getComName(mMainLCPolSchema.getManageCom().substring(0, 4))); //营业机构
        texttag.add("LCPol.ManageComName", tLDComSchema.getShortName());
        texttag.add("LDCom.Name", tLDComSchema.getShortName());
        texttag.add("LDCom.Address", tLDComSchema.getAddress());
        texttag.add("LDCom.ZipCode", tLDComSchema.getZipCode());
        texttag.add("LDCom.Phone", tLDComSchema.getPhone());
        logger.debug("tLDComSchema.getName()" + tLDComSchema.getName());
        texttag.add("PrtNo", PrtNo); //流水号
        logger.debug("GetNoticeNo" +
            mLOPRTManagerSchema.getStandbyFlag2());
        texttag.add("GetNoticeNo", mLOPRTManagerSchema.getStandbyFlag2()); //续期缴费应收总表通知书号（考虑补打的问题）
        texttag.add("LCPol.PolNo", PolNo); //印刷号
        texttag.add("SysDate", SysDate);

        // 核保师代码
        texttag.add("LOPRTManager.ReqOperator",
            mLOPRTManagerSchema.getReqOperator());

        if (texttag.size() > 0)
        {
            xmlexport.addTextTag(texttag);
        }

        xmlexport.addListTable(tRiskListTable, RiskInfoTitle); //保存险种信息及其标题栏
        mResult.clear();
        mResult.addElement(xmlexport);

        return true;
    }

    private boolean getPrintDataBatch()
    {
        LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
        LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.executeQuery(this.sqlbvbatch);

        if (tLOPRTManagerSet.size() == 0)
        {
            mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
            buildError("outputXML", "在取得打印队列中数据时发生错误");

            return false;
        }

        mIP = mIP + ".0"; //普通模板
        logger.debug("mIP===" + mIP);

        ConfigInfo tConfigInfo = new ConfigInfo(this.mConfigFile);
        logger.debug("class.configfile:" + ConfigInfo.GetConfigPath());

        String pathfull = tConfigInfo.GetValuebyArea(mIP);
        int intIndex = 0;
        int intPosition = 0;
        String strRecords = "";
        String servername = "";
        String pathname = "";
        String printer = "";
        String printname = ""; //单据对应的打印机名

        if ((pathfull.length() > 0))
        {
            intIndex = StrTool.getPos(pathfull, SysConst.PACKAGESPILTER,
                    intPosition, 1);
            servername = pathfull.substring(intPosition, intIndex);
            pathfull = pathfull.substring(intIndex + 1);
            intIndex = StrTool.getPos(pathfull, SysConst.PACKAGESPILTER,
                    intPosition, 1);
            pathname = pathfull.substring(intPosition, intIndex);
            logger.debug("pathname:" + pathname);

            pathfull = pathfull.substring(intIndex + 1);
            printer = pathfull;
        }
        else
        {
            buildError("getPrintDataBatch", "IP解析错误！");

            return false;
        }

        for (int i = 1; i <= tLOPRTManagerSet.size(); i++)
        {
            this.mLOPRTManagerSchema = tLOPRTManagerSet.get(i);
            this.mResult.clear();

            if (!getPrintData(printer))
            {
                mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
                buildError("outputXML", "打印失败");

                return false;
            }

            XmlExport xmlexport = new XmlExport();
            xmlexport = (XmlExport) mResult.getObjectByObjectName("XmlExport", 0);

            File tFileXml = new File(pathname);
            logger.debug("Xml.pathname==" + pathname);

            if (!tFileXml.exists())
            {
                tFileXml.mkdirs();
            }

            Date date = new Date();
            String Xmlfilename = "RenewSure" + String.valueOf(date.getTime());
            xmlexport.outputDocumentToFile(pathname, Xmlfilename);
        }

        return true;
    }

    /**
     * 得到通过机构代码得到机构名称
     * @param strComCode
     * @return
     * @throws Exception
     */
    private String getComName(String strComCode)
    {
        LDCodeDB tLDCodeDB = new LDCodeDB();

        tLDCodeDB.setCode(strComCode);
        tLDCodeDB.setCodeType("station");

        if (!tLDCodeDB.getInfo())
        {
            mErrors.copyAllErrors(tLDCodeDB.mErrors);

            return "";
        }

        return tLDCodeDB.getCodeName();
    }

    /**
     * 根据主险保单号码查询主险保单
     * @param tMainPolNo
     * @return LCPolSchema
     */
    private LCPolSchema queryMainPol(String tMainPolNo)
    {
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(tMainPolNo);
        tLCPolDB.setMainPolNo(tMainPolNo);

        LCPolSet tLCPolSet = new LCPolSet();
        tLCPolSet = tLCPolDB.query();

        if (tLCPolSet == null)
        {
            buildError("queryMainPol", "没有找到主险保单！");

            return null;
        }

        if (tLCPolSet.size() == 0)
        {
            buildError("queryMainPol", "没有找到主险保单！");

            return null;
        }

        return tLCPolSet.get(1);
    }
}
