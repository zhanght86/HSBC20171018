package com.sinosoft.lis.f1print;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LOPRTManagerSubDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDCodeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LOPRTManagerSubSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LOPRTManagerSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ConfigInfo;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lh
 * @version 1.0
 */

public class PRnewNoticePrintBL implements PrintService
{
private static Logger logger = Logger.getLogger(PRnewNoticePrintBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();

    //取得的保单号码
    private String mPolNo = "";

    //String InsuredName="";
    //输入的查询sql语句
    private String mBatchSQL = "";
    SQLwithBindVariables sqlbvb = new SQLwithBindVariables();
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
    private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
    private LCPolSchema mLCPolSchema = new LCPolSchema();
    private LCPolSchema mMainLCPolSchema = new LCPolSchema();
    private LCPolSet mSubLCPolSet = new LCPolSet();
    private LOPRTManagerSubSet tSubLOPRTManagerSubSet = new LOPRTManagerSubSet();
    private LOPRTManagerSubSchema tMainLOPRTManagerSubSchema = new LOPRTManagerSubSchema();
    private LCPolSet mLCPolSet = new LCPolSet();
    private LAAgentSchema mLAAgentSchema = new LAAgentSchema();
    private String FORMATMODOL = "0.00"; //保费保额计算出来后的精确位数
    private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL); //数字转换对象

    public PRnewNoticePrintBL()
    {
    }

    /**
    传输数据的公共方法
    */
    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug("Begin PRnewNoticePrintBL SubmitData");

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
        mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
                                      .getObjectByObjectName("LOPRTManagerSchema",
                                                             0));

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
        //全局变量
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput",
                                                                              0));
        
        
        if(cInputData.get(1) instanceof SQLwithBindVariables){
        	sqlbvb = (SQLwithBindVariables)cInputData.get(1);
        	mBatchSQL = sqlbvb.sql();
        }else{
        	mBatchSQL = (String) cInputData.get(1);
        	sqlbvb.sql(mBatchSQL);
        }
        this.mIP = (String) cInputData.get(2);
        this.mConfigFile = (String) cInputData.get(3);

        if (mBatchSQL == null)
        {
            buildError("getInputData", "没有得到足够的信息！");

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

    private boolean getPrintDataBatch()
    {
        LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
        this.mLOPRTManagerSet = tLOPRTManagerDB.executeQuery(this.sqlbvb);

        if (mLOPRTManagerSet.size() == 0)
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

            //      UserLog.println("pathname:"+pathname);
            pathfull = pathfull.substring(intIndex + 1);
            printer = pathfull;

            //      UserLog.println("printername:"+printer);
        }
        else
        {
            buildError("getPrintDataGrpPer", "IP解析错误！");

            return false;
        }

        for (int i = 1; i <= mLOPRTManagerSet.size(); i++)
        {
            this.mLOPRTManagerSchema = mLOPRTManagerSet.get(i);
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
            String Xmlfilename = "RenewNotice" + String.valueOf(date.getTime());
            xmlexport.outputDocumentToFile(pathname, Xmlfilename);
        }

        return true;
    }

    private boolean getPrintData(String tPrinter)
    {
        try
        {
        //根据印刷号查询打印队列中的纪录
        String PrtNo = mLOPRTManagerSchema.getPrtSeq();
        LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
        tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);

        if (tLOPRTManagerDB.getInfo() == false)
        {
            mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
            buildError("getPrintData", "在取得打印队列中数据时发生错误");

            return false;
        }

        mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();

        double SumPrem = 0; //合计保费
        double tSumDuePrem = 0; //合计应交保费
        String tSumDuePremString = "0"; //合计应交保费
        double tSumActualPrem = 0; //合计应交保费
        double tSumLeavingMoney = 0;  //各险种余额合计
        double tSumLeavingMoneyNew = 0;  //各险种余额之和扣除本次各险种保费合计
        String MainRiskName = "";
        LCPolDB tLCPolDB = new LCPolDB();
        String strSQL = "SELECT * FROM LCPol WHERE contno = '"
                        + "?contno?"
                        + "' and polno=mainpolno and appflag ='1'";
        SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
        sqlbv1.sql(strSQL);
        sqlbv1.put("contno", mLOPRTManagerSchema.getOtherNo());
        LCPolSet tempLCPolSet = tLCPolDB.executeQuery(sqlbv1);

        if (tempLCPolSet.size() == 0)
        {
            mErrors.copyAllErrors(tLCPolDB.mErrors);
            buildError("getPrintData", "在LCPol表中找不到相关信息");

            return false;
        }
        mLCPolSchema = tempLCPolSet.get(1).getSchema();
        mMainLCPolSchema.setSchema(mLCPolSchema.getSchema());//保存主险投保单信息
        if(String.valueOf(mMainLCPolSchema.getLeavingMoney()) != null)
        {
          tSumLeavingMoney = tSumLeavingMoney + mMainLCPolSchema.getLeavingMoney();
        }

        mAgentCode = mLCPolSchema.getAgentCode();
        LAAgentDB tLAAgentDB = new LAAgentDB();
        tLAAgentDB.setAgentCode(mAgentCode);

        if (!tLAAgentDB.getInfo())
        {
            mErrors.copyAllErrors(tLAAgentDB.mErrors);
            buildError("getPrintData", "在取得LAAgent的数据时发生错误");
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
            buildError("getPrintData", "在取得管理机构名称数据时发生错误");

            return false;
        }

        tLDCodeSchema = tLDCodeDB.getSchema();
        LOPRTManagerSubDB tLOPRTManagerSubDB = new LOPRTManagerSubDB();
        LOPRTManagerSubSet tLOPRTManagerSubSet = new LOPRTManagerSubSet();
        tLOPRTManagerSubDB.setPrtSeq(mLOPRTManagerSchema.getOldPrtSeq());
        tLOPRTManagerSubSet = tLOPRTManagerSubDB.query();

        if ((tLOPRTManagerSubSet == null) && (tLOPRTManagerSubSet.size() <= 0))
        {
            mErrors.copyAllErrors(tLAAgentDB.mErrors);
            buildError("getPrintData", "在取得LOPRTManagerSub的数据时发生错误");

            return false;
        }

        logger.debug("$$$$$tLOPRTManagerSubSet.size() :"+ tLOPRTManagerSubSet.size());

        for (int j = 1; j <= tLOPRTManagerSubSet.size(); j++)
        {
            if (tLOPRTManagerSubSet.get(j).getRiskCode().trim().equals(mLCPolSchema.getRiskCode().trim()))
            {
                tMainLOPRTManagerSubSchema = tLOPRTManagerSubSet.get(j);
            }
            else
            {
                tSubLOPRTManagerSubSet.add(tLOPRTManagerSubSet.get(j));
                LCPolDB tempLCPolDB = new LCPolDB();
                tempLCPolDB.setPolNo(tLOPRTManagerSubSet.get(j).getOtherNo());

                LCPolSet tLCPolSet = new LCPolSet();
                tLCPolSet = tempLCPolDB.query();

                if ((tLCPolSet == null) && (tLCPolSet.size() != 1))
                {
                    mErrors.copyAllErrors(tLCPolDB.mErrors);
                    buildError("getPrintData", "在取得LCPol的数据时发生错误");

                    return false;
                }

                mSubLCPolSet.add(tLCPolSet.get(1));
            }
        }

        //1-续保缴费险种信息：
        ListTable tRiskListTable = new ListTable();
        String[] RiskInfoTitle = new String[2];
        String[] RiskInfo = new String[2];
        tRiskListTable.setName("MAIN"); //对应模版投保信息部分的行对象名
        RiskInfoTitle[0] = "RiskName"; //险种名称
        RiskInfoTitle[1] = "Prem"; //保费
        String duepremsql="";
        ExeSQL tExeSQL = new ExeSQL();

        if ((tMainLOPRTManagerSubSchema != null)
                && (tMainLOPRTManagerSubSchema.getPrtSeq() != null)
                && !tMainLOPRTManagerSubSchema.getPrtSeq().trim().equals(""))
        {
            LMRiskDB tLMRiskDB = new LMRiskDB();
            tLMRiskDB.setRiskCode(mMainLCPolSchema.getRiskCode());

            if (!tLMRiskDB.getInfo())
            {
                mErrors.copyAllErrors(tLMRiskDB.mErrors);
                buildError("getPrintData", "在取得主险LMRisk的数据时发生错误");

                return false;
            }

            MainRiskName = tLMRiskDB.getRiskName();
            RiskInfo[0] = tLMRiskDB.getRiskName(); //险种名称
            String tMainDuePremSql = "select (select currname from ldcurrency where currcode=currency),sum(SumDuePayMoney) from LJSPayPerson where PolNo= '"
                                   + "?PolNo?" +"' and PayType = 'ZC' group by currency ";
            //String tMainDuePrem = tExeSQL.getOneValue(tMainDuePremSql);
            SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
            sqlbv2.sql(tMainDuePremSql);
            sqlbv2.put("PolNo", tMainLOPRTManagerSubSchema.getOtherNo());
            SSRS tSSRS = tExeSQL.execSQL(sqlbv2);
            RiskInfo[1] = tSSRS.GetText(1, 2)+"("+tSSRS.GetText(1, 1)+")"; //本期主险续期应交保费 加上币种
            tRiskListTable.add(RiskInfo); //加入主险信息
            //记录应该交费金额
            tSumDuePrem = tSumDuePrem + Double.parseDouble(tSSRS.GetText(1, 2));
            duepremsql = duepremsql + "select '" + tSSRS.GetText(1, 1)+"' currency,"+tSSRS.GetText(1, 2) +" prem from dual";
            //记录实际应交金额(标准保费扣除余额)
            tSumActualPrem = tSumActualPrem + tMainLOPRTManagerSubSchema.getDuePayMoney();
        }

        //1.2-取附险信息
        if ((tSubLOPRTManagerSubSet != null) && (tSubLOPRTManagerSubSet.size() > 0))
        {
            for (int n = 1; n <= mSubLCPolSet.size(); n++)
            {
                LMRiskDB tLMRiskDB = new LMRiskDB();
                LCPolSchema tLCPolSchema = new LCPolSchema();
                LOPRTManagerSubSchema tLOPRTManagerSubSchema = new LOPRTManagerSubSchema();
                tLOPRTManagerSubSchema = tSubLOPRTManagerSubSet.get(n);
                tLCPolSchema = mSubLCPolSet.get(n);
                if(String.valueOf(tLCPolSchema.getLeavingMoney()) != null)
                {
                   tSumLeavingMoney = tSumLeavingMoney + tLCPolSchema.getLeavingMoney();
                }
                RiskInfo = new String[2];
                tLMRiskDB.setRiskCode(tLCPolSchema.getRiskCode());

                if (!tLMRiskDB.getInfo())
                {
                    mErrors.copyAllErrors(tLMRiskDB.mErrors);
                    buildError("getPrintData", "在取得附险LMRisk的数据时发生错误");

                    return false;
                }

                RiskInfo[0] = tLMRiskDB.getRiskName(); //险种名称
                //附加险标准保费
                String tSubDuePremSql = "select (select currname from ldcurrency where currcode=currency),sum(SumDuePayMoney) from LJSPayPerson where PolNo= '"
                                   + "?PolNo?" +"' and PayType = 'ZC' group by currency";      
                SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
                sqlbv3.sql(tSubDuePremSql);
                sqlbv3.put("PolNo", tLCPolSchema.getPolNo());
                //String tSubDuePrem = tExeSQL.getOneValue(tSubDuePremSql);
                SSRS tSSRS = tExeSQL.execSQL(sqlbv3);
                RiskInfo[1] = tSSRS.GetText(1, 2)+"("+tSSRS.GetText(1, 1)+")"; //保费 加上币种信息
                tRiskListTable.add(RiskInfo); //加入附险信息
                //附加险的标准保费之和
                //tSumDuePrem = tSumDuePrem + Double.parseDouble(tSubDuePrem);
                if(!duepremsql.equals(""))
                	duepremsql = duepremsql + " union select '" + tSSRS.GetText(1, 1)+"' currency,"+tSSRS.GetText(1, 2) +" prem from dual ";
                else
                	duepremsql = duepremsql + " select '" + tSSRS.GetText(1, 1)+"' currency,"+tSSRS.GetText(1, 2) +" prem from dual ";
                //记录附加险实际应交金额(标准保费扣除余额)
                tSumActualPrem = tSumActualPrem + tLOPRTManagerSubSchema.getDuePayMoney();
            }
        }
        if(!duepremsql.equals(""))
        {
        	duepremsql = "select currency,sum(prem) from (" + duepremsql + ") a group by currency";
        	SQLwithBindVariables sqlbva = new SQLwithBindVariables();
        	sqlbva.sql(duepremsql);
        	logger.debug("应交保费合计："+duepremsql);        	
        	SSRS tSSRS= tExeSQL.execSQL(sqlbva);
        	tSumDuePremString="";
        	for(int i=1;i<=tSSRS.getMaxRow();i++)
        		tSumDuePremString = tSumDuePremString+tSSRS.GetText(i, 2)+"("+tSSRS.GetText(i, 1)+")";
        }
        
        tSumLeavingMoneyNew = tSumLeavingMoney - tSumDuePrem;
        logger.debug("$$$$$$扣除本次保费剩余金额: "+tSumLeavingMoneyNew);
        if( tSumLeavingMoneyNew<0 )
        {
          tSumLeavingMoneyNew = 0;
        }
        tSumLeavingMoney = Double.parseDouble(mDecimalFormat.format(tSumLeavingMoney));
        tSumDuePrem = Double.parseDouble(mDecimalFormat.format(tSumDuePrem));
        tSumActualPrem = Double.parseDouble(mDecimalFormat.format(tSumActualPrem));
        tSumLeavingMoneyNew = Double.parseDouble(mDecimalFormat.format(tSumLeavingMoneyNew)); //转换计算后的保费(规定的精度)

        //其它模版上单独不成块的信息
        TextTag texttag = new TextTag(); //新建一个TextTag的实例
        XmlExport xmlexport = new XmlExport(); //新建一个XmlExport的实例
        xmlexport.createDocument("PRnewNotice.vts", tPrinter); //最好紧接着就初始化xml文档

        //生成-年-月-日格式的日期
        StrTool tSrtTool = new StrTool();
        String SysDate = tSrtTool.getYear() + "年" + tSrtTool.getMonth() + "月"
                         + tSrtTool.getDay() + "日";

        //新增投保人通信地址和投保人邮编
        ExeSQL ttExeSQL = new ExeSQL();
        SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
        sqlbv4.sql(" select b.zipcode,b.postaladdress from lcappnt a,LCAddress b  "
                + " where a.appntno=b.customerno and a.addressno=b.addressno and a.contno='"+"?contno?"+"' ");
        sqlbv4.put("contno", mMainLCPolSchema.getContNo());
        SSRS tSSRS = ttExeSQL.execSQL(sqlbv4);

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
        texttag.add("LCPol.PolNo", mMainLCPolSchema.getPolNo()); //投保单号
        texttag.add("LCPol.AccName", tLCContSchema.getAccName()); //账户名
        texttag.add("LCPol.BankAccNo", tLCContSchema.getBankAccNo()); //账户号
        texttag.add("RiskName", MainRiskName); //主险名称

        if ((mMainLCPolSchema.getPaytoDate() != null)
                && !mMainLCPolSchema.getPaytoDate().trim().equals(""))
        {
            texttag.add("LCPol.PayToDate", mMainLCPolSchema.getPaytoDate());
        }
        else
        {
            texttag.add("LCPol.PayToDate", mMainLCPolSchema.getCValiDate());
        }

        texttag.add("SumFee", tSumDuePremString); //合计应交保费
        texttag.add("SumLeavingMoney", tSumLeavingMoney); //合计预交金额
        texttag.add("SumActualPrem", tSumActualPrem); //合计实交保费
        texttag.add("SumLeavingMoneyNew", tSumLeavingMoneyNew); //合计预收剩余余额

        texttag.add("LAAgent.Name", mLAAgentSchema.getName()); //代理人姓名
        texttag.add("LCPol.AgentCode", mMainLCPolSchema.getAgentCode()); //代理人业务号
        texttag.add("LCPol.ManageCom",
                    getComName(mMainLCPolSchema.getManageCom())); //营业机构
        texttag.add("LCPol.ManageComName", tLDCodeSchema.getCodeName());
        texttag.add("PrtNo", PrtNo); //流水号

//        ExeSQL tExeSQL = new ExeSQL();
//        String Phone = tExeSQL.getOneValue("select Phone from LDCom where ComCode='"
//                                           + mMainLCPolSchema.getManageCom()
//                                           + "'");
//
//        if (tExeSQL.mErrors.needDealError() || (Phone == null)
//                || Phone.equals(""))
//        {
//            CError.buildErr(this,"查询营业机构电话错误，机构:" + mMainLCPolSchema.getManageCom());
//
//            return false;
//        }
//        texttag.add("Phone", Phone);
//
        logger.debug("GetNoticeNo : "+ mLOPRTManagerSchema.getStandbyFlag2());
        texttag.add("GetNoticeNo", mLOPRTManagerSchema.getStandbyFlag2()); //续期缴费应收总表通知书号（考虑补打的问题）
        texttag.add("LCPol.PolNo", mMainLCPolSchema.getPolNo()); //印刷号
        texttag.add("SysDate", SysDate);

        // 核保师代码
        texttag.add("LOPRTManager.ReqOperator",mLOPRTManagerSchema.getReqOperator());

        if (texttag.size() > 0)
        {
            xmlexport.addTextTag(texttag);
        }

        xmlexport.addListTable(tRiskListTable, RiskInfoTitle); //保存险种信息及其标题栏
        //xmlexport.outputDocumentToFile("e:\\","1.xml");
        mResult.clear();
        mResult.addElement(xmlexport);
        }catch(Exception ex)
        {
         ex.printStackTrace();
         return false;
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
