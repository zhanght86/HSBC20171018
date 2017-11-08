package com.sinosoft.lis.get;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

import java.text.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author z
 * @version 1.0
 */
import java.util.*;

public class LFGetNoticePrintBL
{
private static Logger logger = Logger.getLogger(LFGetNoticePrintBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();

    //业务处理相关变量

    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private TransferData mTransferData = new TransferData();
    private String mOperate = "";
    private String mGetNoticeNo = "";
    private String mActuGetNo = "";

    public LFGetNoticePrintBL()
    {
    }

    /**
     * 传输数据的公共方法
     * @param cInputData
     * @param cOperate
     * @return
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        mOperate = cOperate;

        try
        {
            if (!cOperate.equals("PRINT|GRP") &&
                    !cOperate.equals("PRINT|PERSON|LJS") &&
                    !cOperate.equals("PRINT|PERSON|LJA") &&
                    !cOperate.equals("PRINT|PERSON|GetStat")&&
                    !cOperate.equals("PRINT|PERSON|Bonus"))
            {
                buildError("submitData", "不支持的操作字符串");

                return false;
            }

            // 得到外部传入的数据，将数据备份到本类中（不管有没有operate,都要执行这一部）
            if (!getInputData(cInputData))
            {
                return false;
            }

            if (cOperate.equals("PRINT|GRP"))
            {
                if (!getGrpPrintData())
                {
                    return false;
                }
            }
            else if (cOperate.equals("PRINT|PERSON|LJS"))
            {
                if (!getPersonLJSPrintData())
                {
                    return false;
                }
            }
            else if (cOperate.equals("PRINT|PERSON|LJA"))
            {
                if (!getPersonLJAPrintData())
                {
                    return false;
                }
            }
            else if (cOperate.equals("PRINT|PERSON|GetStat"))
            {
                if (!getPersonGetStat())
                {
                    return false;
                }
            }
            else if (cOperate.equals("PRINT|PERSON|Bonus"))
            {
                if (!getPersonBonusData())
                {
                    return false;
                }
            }

            return true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            buildError("submitData", ex.toString());

            return false;
        }
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
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput",
                                                                              0));
        mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData",
                                                                        0);

        if ((mTransferData != null) &&
                (mOperate.equals("PRINT|GRP") ||
                mOperate.equals("PRINT|PERSON|LJS")))
        {
            mGetNoticeNo = (String) mTransferData.getValueByName("GetNoticeNo");
        }
        else if ((mTransferData != null) &&
                     (mOperate.equals("PRINT|PERSON|LJA")||mOperate.equals("PRINT|PERSON|Bonus")))
        {
            mActuGetNo = (String) mTransferData.getValueByName("ActuGetNo");
        }

        return true;
    }

    //得到返回值
    public VData getResult()
    {
        return this.mResult;
    }

    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError();

        cError.moduleName = "LFGetNoticePrintBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }
    // 准备所有要打印的个险红利领却数据

    private boolean getPersonBonusData() throws Exception
     {
            if(mActuGetNo == null || "".equals(mActuGetNo))
            {
              buildError("getPersonBonusData", "实付号传入错误！");
              return false;
            }

            XmlExport xmlExport = new XmlExport(); //新建一个XmlExport的实例
            xmlExport.createDocument("LJAGetBonus.vts", "printer"); //最好紧接着就初始化xml文档

            String[] strArr = null;
            String ActuGetNo = "";
            LJAGetDB tLJAGetDB = new LJAGetDB();
            tLJAGetDB.setActuGetNo(mActuGetNo);

            if (!tLJAGetDB.getInfo())
            {
                buildError("getPersonBonusData", "无法获得领款通知书" + mActuGetNo + "的实付总表信息！");

                return false;
            }

            ActuGetNo = tLJAGetDB.getActuGetNo();
            TextTag texttag = new TextTag();
            texttag.add("GetMoney",
                        tLJAGetDB.getSumGetMoney() + "元(大写:" +
                        PubFun.getChnMoney(tLJAGetDB.getSumGetMoney()) + ")");

            //实付号
            texttag.add("ActuGetNo", ActuGetNo);
            //领取人
            texttag.add("Drawer", tLJAGetDB.getDrawer());
            //领取时间
            texttag.add("SysDate", PubFun.getCurrentDate());

            LJABonusGetDB tLJABonusGetDB = new LJABonusGetDB();
            LJABonusGetSet tLJABonusGetSet = new LJABonusGetSet();
            SQLwithBindVariables sqlbv=new SQLwithBindVariables();
            sqlbv.sql("select * from LJABonusGet where actugetno='?ActuGetNo?' order by otherno,getnoticeno");
            sqlbv.put("ActuGetNo", ActuGetNo);
            tLJABonusGetSet = tLJABonusGetDB.executeQuery(sqlbv);

            if (tLJABonusGetSet.size() == 0)
            {
                buildError("getPersonBonusData", "无法获得领款通知书" + mActuGetNo + "的实付子表信息！");
                return false;
            }

            ListTable tlistTable;
            tlistTable = new ListTable();
            tlistTable.setName("GET");

            for (int i = 1; i <= tLJABonusGetSet.size(); i++)
            {
                LJABonusGetSchema tLJABonusGetSchema = new LJABonusGetSchema();
                tLJABonusGetSchema = tLJABonusGetSet.get(i);
                strArr = new String[5];
                String tRiskSql = "select riskname from lmrisk where riskcode in (select riskcode from v_lcpol_lbpol where polno='?polno?')";
                ExeSQL tExeSQL = new ExeSQL();
                SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
                sqlbv1.sql(tRiskSql);
                sqlbv1.put("polno", tLJABonusGetSchema.getOtherNo());
                strArr[0] = tExeSQL.getOneValue(sqlbv1);
                strArr[1] = "红利给付";
                strArr[2] = "";

                if (tLJABonusGetSchema.getFeeFinaType().equals("HLTF"))
                {
                    strArr[2] = "红利退费";
                }
                else if (tLJABonusGetSchema.getFeeFinaType().equals("YCLXTF"))
                {
                    strArr[2] = "延迟利息退费";
                }
                else if (tLJABonusGetSchema.getFeeFinaType().equals("LJTF"))
                {
                    strArr[2] = "红利累计退费";
                }

                //应交金额
                strArr[3] = "0.00";

                //应付金额
                strArr[4] = String.valueOf(tLJABonusGetSchema.getGetMoney());
                tlistTable.add(strArr);
            }

            strArr = new String[5];
            strArr[0] = "险种名称";
            strArr[1] = "保全项目";
            strArr[2] = "财务类型";
            strArr[3] = "应交金额";
            strArr[4] = "应付金额";

            if (texttag.size() > 0)
            {
                xmlExport.addTextTag(texttag);
                xmlExport.addListTable(tlistTable, strArr);
            }

            mResult.clear();
            mResult.addElement(xmlExport);

            return true;
    }

    // 准备所有要打印的团单数据
    private boolean getGrpPrintData() throws Exception
    {
        XmlExport xmlExport = new XmlExport(); //新建一个XmlExport的实例
        xmlExport.createDocument("LFGetNotice.vts", ""); //最好紧接着就初始化xml文档

        LJSGetDB tLJSGetDB = new LJSGetDB();
        tLJSGetDB.setGetNoticeNo(mGetNoticeNo);

        if (!tLJSGetDB.getInfo())
        {
            buildError("getPrintData", "无法获得通知书" + mGetNoticeNo + "的应付信息！");

            return false;
        }

        if (!vertifyLJSGet(tLJSGetDB))
        {
            return false;
        }

        String tPolNo = tLJSGetDB.getOtherNo();

        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(tPolNo);

        if (!tLCPolDB.getInfo())
        {
            buildError("getPrintData", "在获取保单信息时出错！");

            return false;
        }

        ExeSQL tExeSQL = new ExeSQL();
        String sql = "select ZipCode,PostalAddress,idno from lcinsured " +
                     "where polno = '?tPolNo?' and insuredgrade = 'M' " +
                     "and customerno = '?customerno?' ";
        SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
        sqlbv2.sql(sql);
        sqlbv2.put("tPolNo", tPolNo);
        sqlbv2.put("customerno", tLCPolDB.getInsuredNo());
        logger.debug("Query Insured : " + sql);

        SSRS tSSRS = tExeSQL.execSQL(sqlbv2);

        if (tExeSQL.mErrors.needDealError() || (tSSRS == null) ||
                (tSSRS.getMaxRow() <= 0))
        {
            CError.buildErr(this,
                            "查询投保人通讯地址/邮编失败!(保单号:" + tLCPolDB.getPolNo() + ")");

            return false;
        }

        LJSGetDrawDB tLJSGetDrawDB = new LJSGetDrawDB();
        tLJSGetDrawDB.setGetNoticeNo(mGetNoticeNo);

        LJSGetDrawSet tLJSGetDrawSet = tLJSGetDrawDB.query();

        if (tLJSGetDrawSet.size() <= 0)
        {
            buildError("getPrintData", "查询生存领取给付表失败！");

            return false;
        }

        sql = "select getdutyname from lmdutyget " + "where getdutycode = '?getdutycode?' ";
        SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
        sqlbv3.sql(sql);
        sqlbv3.put("getdutycode", tLJSGetDrawSet.get(1).getGetDutyCode());

        SSRS gssrs = tExeSQL.execSQL(sqlbv3);

        if (tExeSQL.mErrors.needDealError() || (gssrs == null) ||
                (gssrs.getMaxRow() <= 0) || gssrs.GetText(1, 1).equals(""))
        {
            buildError("getPrintData", "查询给付类型失败！");

            return false;
        }

        sql = "select getstartdate,getintv from lcget " + "where polno = '?tPolNo?' and getdutycode = '?getdutycode?' and dutycode = '?dutycode?' ";
        SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
        sqlbv4.sql(sql);
        sqlbv4.put("tPolNo", tPolNo);
        sqlbv4.put("getdutycode", tLJSGetDrawSet.get(1).getGetDutyCode());
        sqlbv4.put("dutycode", tLJSGetDrawSet.get(1).getDutyCode());
        logger.debug("Query LCGet : " + sql);

        SSRS assrs = tExeSQL.execSQL(sqlbv4);

        if (tExeSQL.mErrors.needDealError() || (assrs == null) ||
                (assrs.getMaxRow() <= 0) || assrs.GetText(1, 1).equals(""))
        {
            buildError("getPrintData", "查询领取项表失败！");

            return false;
        }

        sql = "select shortname,phone from ldcom " + "where comcode = '?comcode?' ";
        SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
        sqlbv5.sql(sql);
        sqlbv5.put("comcode", tLCPolDB.getManageCom());

        SSRS dssrs = tExeSQL.execSQL(sqlbv5);

        if (tExeSQL.mErrors.needDealError() || (dssrs == null) ||
                (dssrs.getMaxRow() <= 0))
        {
            buildError("getPrintData", "查询机构信息失败！");

            //            return false;
        }

        //目前系统中仅支持现金领取，故默认为该方式；
        String tGetMode = "现金领取";
        String tPrompt = "届时，请您携带相关证件到本公司柜面办理领款手续。";

        TextTag texttag = new TextTag();
        texttag.add("InsuredName", tLCPolDB.getInsuredName());
        texttag.add("ZipCode", tSSRS.GetText(1, 1));
        texttag.add("Address", tSSRS.GetText(1, 2));
        texttag.add("StartGetDate", assrs.GetText(1, 1));
        texttag.add("GetMode", tGetMode);
        texttag.add("Prompt", tPrompt);
        texttag.add("RiskName",
                    ChangeCodetoName.getRiskName(tLCPolDB.getRiskCode()));
        texttag.add("GetDutyName", gssrs.GetText(1, 1));
        texttag.add("GetMoney", tLJSGetDB.getSumGetMoney());
        texttag.add("GetIntv", assrs.GetText(1, 2));
        texttag.add("SGetDate", tLJSGetDB.getGetDate());
        texttag.add("GetNoticeNo", mGetNoticeNo);
        texttag.add("GrpPolNo", tLCPolDB.getGrpPolNo());
        texttag.add("PolNo", tPolNo);
        //texttag.add("AccNo", tLCPolDB.getBankAccNo());
        //texttag.add("AccName", tLCPolDB.getAccName());
        texttag.add("TelNo", dssrs.GetText(1, 2));
        texttag.add("AgentCode", tLCPolDB.getAgentCode());
        texttag.add("MngName", dssrs.GetText(1, 1));
        texttag.add("IDNo", tSSRS.GetText(1, 3));

        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        texttag.add("Today", df.format(new Date()));

        if (texttag.size() > 0)
        {
            xmlExport.addTextTag(texttag);
        }

        mResult.clear();
        mResult.addElement(xmlExport);

        return true;
    }

    // 准备所有要打印的个险数据
    private boolean getPersonLJAPrintData() throws Exception
    {
        XmlExport xmlExport = new XmlExport(); //新建一个XmlExport的实例
        xmlExport.createDocument("LJAGetPerson.vts", ""); //最好紧接着就初始化xml文档

        String[] strArr = null;
        String ActuGetNo = "";
        LJAGetDB tLJAGetDB = new LJAGetDB();
        tLJAGetDB.setActuGetNo(mActuGetNo);

        if (!tLJAGetDB.getInfo())
        {
            buildError("getPrintData", "无法获得领款通知书" + mActuGetNo + "的实付总表信息！");

            return false;
        }

        ActuGetNo = tLJAGetDB.getActuGetNo();

        TextTag texttag = new TextTag();
        texttag.add("GetMoney",
                    tLJAGetDB.getSumGetMoney() + "元(大写:" +
                    PubFun.getChnMoney(tLJAGetDB.getSumGetMoney()) + ")");

        //实付号
        texttag.add("ActuGetNo", ActuGetNo);

        LJAGetDrawDB tLJAGetDrawDB = new LJAGetDrawDB();

        LJAGetDrawSet tLJAGetDrawSet = new LJAGetDrawSet();
        SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
        sqlbv6.sql("select * from ljagetdraw where actugetno='?ActuGetNo?' order by polno,getnoticeno");
        sqlbv6.put("ActuGetNo", ActuGetNo);
        tLJAGetDrawSet = tLJAGetDrawDB.executeQuery(sqlbv6);

        if (tLJAGetDrawSet.size() == 0)
        {
            buildError("getPrintData", "无法获得领款通知书" + mActuGetNo + "的实付子表信息！");

            return false;
        }

        //给付责任
        String GetDutyCode = tLJAGetDrawSet.get(1).getGetDutyCode();

        //默认现金转帐
        String GetTypeFlag = "1";

        for (int i = 1; i <= tLJAGetDrawSet.size(); i++)
        {
            LCBankAuthDB tLCBankAuthDB = new LCBankAuthDB();
            tLCBankAuthDB.setPolNo(tLJAGetDrawSet.get(i).getPolNo());
            tLCBankAuthDB.setPayGetFlag("1");
            tLCBankAuthDB.setState("1");

            if (!tLCBankAuthDB.getInfo())
            {
                logger.debug("保单" + tLJAGetDrawSet.get(i).getPolNo() +
                                   "的领取方式为现金领取！");
                GetTypeFlag = "1";
            }
            else
            {
                logger.debug("保单" + tLJAGetDrawSet.get(i).getPolNo() +
                                   "的领取方式为银行转帐！");
                GetTypeFlag = "0";
            }
        }

        //支付方式
        if (GetTypeFlag.equals("0"))
        {
            texttag.add("GetMode", "银行转帐");
        }
        else
        {
            texttag.add("GetMode", "现金领取");
        }

        //领取人
        if ((tLJAGetDB.getDrawer() != null) &&
                !tLJAGetDB.getDrawer().equals(""))
        {
            texttag.add("Drawer", tLJAGetDB.getDrawer());
        }
        else
        {
            LCPolDB tLCPolDB = new LCPolDB();
            tLCPolDB.setPolNo(tLJAGetDrawSet.get(1).getPolNo());

            if (tLCPolDB.getInfo())
            {
                texttag.add("Drawer", tLCPolDB.getInsuredName());
            }
            else
            {
                buildError("getPrintData",
                           "无法获得保单" + tLJAGetDrawSet.get(1).getPolNo() +
                           "的信息！");

                return false;
            }
        }

        //领取时间
        texttag.add("ApplyDate", tLJAGetDB.getMakeDate());

        ListTable tlistTable;
        tlistTable = new ListTable();
        tlistTable.setName("GET");

        for (int i = 1; i <= tLJAGetDrawSet.size(); i++)
        {
            LJAGetDrawSchema tLJAGetDrawSchema = new LJAGetDrawSchema();
            tLJAGetDrawSchema = tLJAGetDrawSet.get(i);
            strArr = new String[5];

            LMRiskDB tLMRiskDB = new LMRiskDB();
            tLMRiskDB.setRiskCode(tLJAGetDrawSchema.getRiskCode());

            if (!tLMRiskDB.getInfo())
            {
                buildError("getPrintData", "查询险种信息失败！");

                return false;
            }

            strArr[0] = tLMRiskDB.getRiskName();

            ExeSQL tExeSQL = new ExeSQL();
            String sql = "";
            sql = "select getdutyname,gettype1 from lmdutyget " +
                  "where getdutycode = '?getdutycode?' ";
            SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
            sqlbv7.sql(sql);
            sqlbv7.put("getdutycode", tLJAGetDrawSchema.getGetDutyCode());

            SSRS gssrs = tExeSQL.execSQL(sqlbv7);

            if (tExeSQL.mErrors.needDealError() || (gssrs == null) ||
                    (gssrs.getMaxRow() <= 0) || gssrs.GetText(1, 1).equals("") ||
                    gssrs.GetText(1, 2).equals(""))
            {
                buildError("getPrintData", "查询给付类型失败！");

                return false;
            }

            String GetType = "";

            if (gssrs.GetText(1, 2).equals("0"))
            {
                GetType = "满期金领取";
            }
            else
            {
                GetType = "生存金领取";
            }

            strArr[1] = GetType;
            strArr[2] = "";

            if (tLJAGetDrawSchema.getFeeFinaType().equals("LIVGET"))
            {
                strArr[2] = "本金";
            }
            else if (tLJAGetDrawSchema.getFeeFinaType().equals("LX"))
            {
                strArr[2] = "利息";
            }

            //应交金额
            strArr[3] = "0.00";

            //应付金额
            strArr[4] = String.valueOf(tLJAGetDrawSchema.getGetMoney());
            tlistTable.add(strArr);
        }

        strArr = new String[5];
        strArr[0] = "险种名称";
        strArr[1] = "保全项目";
        strArr[2] = "财务类型";
        strArr[3] = "应交金额";
        strArr[4] = "应付金额";

        if (texttag.size() > 0)
        {
            xmlExport.addTextTag(texttag);
            xmlExport.addListTable(tlistTable, strArr);
        }

        mResult.clear();
        mResult.addElement(xmlExport);

        return true;
    }

    private boolean getPersonGetStat() throws Exception
    {
        XmlExport xmlExport = new XmlExport(); //新建一个XmlExport的实例
        xmlExport.createDocument("LFGetStatPerson.vts", ""); //最好紧接着就初始化xml文档

        String PolNo = (String) mTransferData.getValueByName("PolNo");
        String ManageCom = (String) mTransferData.getValueByName("ManageCom");
        String AgentCode = (String) mTransferData.getValueByName("AgentCode");
        String RiskCode = (String) mTransferData.getValueByName("RiskCode");
        String StartDate = (String) mTransferData.getValueByName("StartDate");
        String EndDate = (String) mTransferData.getValueByName("EndDate");
        String GetFlag = (String) mTransferData.getValueByName("GetFlag");

        String strsql = "";

        if (GetFlag.equals("0")) //应领未领
        {
            strsql = "select getnoticeno,";
        }
        else if (GetFlag.equals("1")) //已领
        {
        }
        else
        {
            buildError("getPrintData", "请选择领取状态！");

            return false;
        }

        return true;
    }

    // 准备所有要打印的个险数据
    private boolean getPersonLJSPrintData() throws Exception
    {
        XmlExport xmlExport = new XmlExport(); //新建一个XmlExport的实例
        xmlExport.createDocument("LFGetNoticePerson.vts", ""); //最好紧接着就初始化xml文档

        LJSGetDB tLJSGetDB = new LJSGetDB();
        tLJSGetDB.setGetNoticeNo(mGetNoticeNo);

        if (!tLJSGetDB.getInfo())
        {
            buildError("getPrintData", "无法获得通知书" + mGetNoticeNo + "的应付信息！");

            return false;
        }

        if (!vertifyLJSGet(tLJSGetDB))
        {
            return false;
        }

        String tPolNo = tLJSGetDB.getOtherNo();

        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(tPolNo);

        if (!tLCPolDB.getInfo())
        {
            buildError("getPrintData", "在获取保单信息时出错！");

            return false;
        }

        ExeSQL tExeSQL = new ExeSQL();
        String sql = "select ZipCode,PostalAddress,idno from lcappntind " +
                     "where polno = '?tPolNo?' and customerno = '?customerno?' ";
        SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
        sqlbv8.sql(sql);
        sqlbv8.put("tPolNo", tPolNo);
        sqlbv8.put("customerno", tLCPolDB.getAppntNo());
        logger.debug("Query appntind : " + sql);

        SSRS tSSRS = tExeSQL.execSQL(sqlbv8);

        if (tExeSQL.mErrors.needDealError() || (tSSRS == null) ||
                (tSSRS.getMaxRow() <= 0))
        {
            CError.buildErr(this,
                            "查询被保人通讯地址/邮编失败!(保单号:" + tLCPolDB.getPolNo() + ")");

            return false;
        }

        LJSGetDrawDB tLJSGetDrawDB = new LJSGetDrawDB();
        tLJSGetDrawDB.setGetNoticeNo(mGetNoticeNo);

        LJSGetDrawSet tLJSGetDrawSet = tLJSGetDrawDB.query();

        if (tLJSGetDrawSet.size() <= 0)
        {
            buildError("getPrintData", "查询生存领取给付表失败！");

            return false;
        }

        sql = "select getdutyname,gettype1 from lmdutyget " +
              "where getdutycode = '?getdutycode?' ";
        SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
        sqlbv9.sql(sql);
        sqlbv9.put("getdutycode", tLJSGetDrawSet.get(1).getGetDutyCode());

        SSRS gssrs = tExeSQL.execSQL(sqlbv9);

        if (tExeSQL.mErrors.needDealError() || (gssrs == null) ||
                (gssrs.getMaxRow() <= 0) || gssrs.GetText(1, 1).equals("") ||
                gssrs.GetText(1, 2).equals(""))
        {
            buildError("getPrintData", "查询给付类型失败！");

            return false;
        }

        String GetType = "";

        if (gssrs.GetText(1, 2).equals("0"))
        {
            GetType = "满期保险金";
        }
        else
        {
            GetType = "生存保险金";
        }

        sql = "select getstartdate,getintv,standmoney from lcget " +
              "where polno = '?tPolNo?' and getdutycode = '?getdutycode?' and dutycode = '?dutycode?' ";
        SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
        sqlbv10.sql(sql);
        sqlbv10.put("tPolNo", tPolNo);
        sqlbv10.put("getdutycode", tLJSGetDrawSet.get(1).getGetDutyCode());
        sqlbv10.put("dutycode", tLJSGetDrawSet.get(1).getDutyCode());
        logger.debug("Query LCGet : " + sql);

        SSRS assrs = tExeSQL.execSQL(sqlbv10);

        if (tExeSQL.mErrors.needDealError() || (assrs == null) ||
                (assrs.getMaxRow() <= 0) || assrs.GetText(1, 1).equals(""))
        {
            buildError("getPrintData", "查询领取项表失败！");

            return false;
        }

        //应领金额
        //String StandMoney = assrs.GetText(1, 3);
        sql = "select shortname,phone from ldcom " + "where comcode = '?comcode?' ";
        SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
        sqlbv11.sql(sql);
        sqlbv11.put("comcode", tLCPolDB.getManageCom());

        SSRS dssrs = tExeSQL.execSQL(sqlbv11);

        if (tExeSQL.mErrors.needDealError() || (dssrs == null) ||
                (dssrs.getMaxRow() <= 0))
        {
            buildError("getPrintData", "查询机构信息失败！");

            //            return false;
        }

        TextTag texttag = new TextTag();
        texttag.add("ZipCode", tSSRS.GetText(1, 1));
        texttag.add("Address", tSSRS.GetText(1, 2));
        texttag.add("AppntName", tLCPolDB.getAppntName());
        texttag.add("MainPolNo", tLCPolDB.getMainPolNo());
        texttag.add("GetNoticeNo", mGetNoticeNo);
        texttag.add("RiskName",
                    ChangeCodetoName.getRiskName(tLCPolDB.getRiskCode()));
        texttag.add("CValiDate", tLCPolDB.getCValiDate());

        //领取项目
        texttag.add("GetType", GetType);

        texttag.add("GetDate", tLJSGetDB.getGetDate());
        texttag.add("GetDutyName", gssrs.GetText(1, 1));

        //领取标准
        texttag.add("GetMoney", tLJSGetDB.getSumGetMoney());

        String GetIntv = "";

        if (assrs.GetText(1, 2).equals("1"))
        {
            GetIntv = "月领";
        }
        else if (assrs.GetText(1, 2).equals("12"))
        {
            GetIntv = "年领";
        }

        logger.debug("");

        //领取次数的计算
        //如果是趸领则将次数置为1，否则计算
        if (assrs.GetText(1, 2).equals("0"))
        {
            texttag.add("GetCount", "1");
        }
        else
        {
            sql = "select months_between(getdate,startgetdate)/" +
                  assrs.GetText(1, 2) + " from ljsget where otherno = '?tPolNo?' and getnoticeno='?mGetNoticeNo?'";
            logger.debug("sql:" + sql);
            SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
            sqlbv12.sql(sql);
            sqlbv12.put("tPolNo", tPolNo);
            sqlbv12.put("mGetNoticeNo", mGetNoticeNo);

            SSRS getcount = tExeSQL.execSQL(sqlbv12);

            if (tExeSQL.mErrors.needDealError() || (getcount == null) ||
                    (getcount.getMaxRow() <= 0) ||
                    getcount.GetText(1, 1).equals(""))
            {
                buildError("getPrintData", "查询领取项表失败！");

                return false;
            }

            if (assrs.GetText(1, 2).equals("1"))
            {
                texttag.add("GetCount",
                            (Integer.parseInt(getcount.GetText(1, 1)) / 12) +
                            1);
            }
            else
            {
                texttag.add("GetCount",
                            Integer.parseInt(getcount.GetText(1, 1)) + 1);
            }
        }

        texttag.add("GetIntv", GetIntv);

        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        texttag.add("Today", df.format(new Date()));

        if (texttag.size() > 0)
        {
            xmlExport.addTextTag(texttag);
        }

        mResult.clear();
        mResult.addElement(xmlExport);

        return true;
    }

    /**
     * 校验查询到的该给付通知书打印的必要信息
     * @return
     */
    private boolean vertifyLJSGet(LJSGetDB cLJSGetDB)
    {
        if (cLJSGetDB == null)
        {
            buildError("vertifyLJSGet", "该通知书的应收信息为空！");

            return false;
        }

        if ((cLJSGetDB.getGetDate() == null) ||
                cLJSGetDB.getGetDate().equals(""))
        {
            buildError("vertifyLJSGet", "该通知书的本期应领日期为空！");

            return false;
        }

        return true;
    }

    //////////////////////////////////////////////////
    // 测试
    //////////////////////////////////////////////////
    public static void main(String[] args)
    {
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput.ComCode = "86";

        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("GetNoticeNo", "86330120040360000327");

        VData tVData = new VData();
        tVData.add(tGlobalInput);
        tVData.add(tTransferData);

        LFGetNoticePrintBL tLFGetNoticePrintBL = new LFGetNoticePrintBL();

        if (!tLFGetNoticePrintBL.submitData(tVData, "PRINT"))
        {
            logger.debug(tLFGetNoticePrintBL.mErrors.getFirstError());
        }

        logger.debug("Success !");
    }
}
