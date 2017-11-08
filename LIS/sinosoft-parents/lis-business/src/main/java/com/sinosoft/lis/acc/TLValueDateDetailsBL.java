package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.f1print.*;

import java.text.DecimalFormat;

public class TLValueDateDetailsBL
        implements BqBill
{
private static Logger logger = Logger.getLogger(TLValueDateDetailsBL.class);

    // 公共数据
    private VData mResult = new VData();

    public CErrors mErrors = new CErrors();

    // 全局变量
    private String mOperate;

    private VData mInputData = new VData();

    private TextTag mTextTag = new TextTag();

    private GlobalInput mGlobalInput = new GlobalInput();

    private String CurrentDate = PubFun.getCurrentDate();

    private String CurrentTime = PubFun.getCurrentTime();

    private BqNameFun mBqNameFun = new BqNameFun();

    private DecimalFormat df = new DecimalFormat("0.00");

    // 输入数据
    private VData rInputData;

    private String rOperation;

    private String mRiskCode;

    private String mInsuAccNo;

    private String mStartdate;

    private GlobalInput rGlobalInput;

    private LOAccUnitPriceSchema rLOAccUnitPriceSchema = new
            LOAccUnitPriceSchema();

    // 输出数据
    private XmlExport tXmlExport = new XmlExport();

    private VData rResultData;

    // FormulaOne 打印模板文件

    public static final String VTS_NAME = "TLValueDateDetails.vts"; // 模板名称

    /**
     * 外部调用本类的业务处理接口
     *
     * @param VData
     * @param String
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug("计价日交易明细");

        // 接收参数
        if(cInputData == null)
        {
            CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
            logger.debug(
                            "\t@> EdorPayGetFormChangePrintBL.submitData() : 无法获取 InputData 数据！");
            return false;
        }
        else
        {
            rInputData = new VData();
            rInputData = (VData) cInputData.clone();
        }
        rOperation = (cOperate != null) ? cOperate.trim() : "";

        // ----------------------------------------------------------------------

        // 业务处理
        if(!getInputData())
        {
            return false;
        }
        // 数据校验
        if(!checkData())
        {
            return false;
        }

        // 打印数据处理
        if(!dealData())
        {
            return false;
        }

        if(!outputData())
        {
            return false;
        }

        // 垃圾处理
        collectGarbage();

        return true;
    }

    /**
     * 获取外部传入数据和校验必录字段的合法性
     *
     * @param null
     * @return boolean
     */
    private boolean getInputData()
    {
        rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
                "GlobalInput", 0);
        if(rGlobalInput == null)
        {
            CError.buildErr(this, "无法获取用户登录机构信息！");
            logger.debug(
                            "\t@> TLValueDateDetailsBL.getInputData() : 无法获取 GlobalInput 数据！");
            return false;
        }

        rLOAccUnitPriceSchema = (LOAccUnitPriceSchema) rInputData
                                .getObjectByObjectName("LOAccUnitPriceSchema",
                0);
        if(rLOAccUnitPriceSchema == null)
        {
            mErrors.addOneError(new CError("数据传输不完全！"));
            return false;
        }
        mInsuAccNo = rLOAccUnitPriceSchema.getInsuAccNo();
        // mRiskCode=rLOAccUnitPriceSchema.getRiskCode();
        mStartdate = rLOAccUnitPriceSchema.getStartDate();
        return true;
    }

    private boolean checkData()
    {
        return true;
    }

    /**
     * 本类的核心业务处理过程
     *
     * @param null
     * @return boolean
     */
    private boolean dealData()
    {
        tXmlExport = new XmlExport();
        tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档
        logger.debug("初始化xml文档");

        String sql = "";
        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = new SSRS();
        TextTag tTextTag = new TextTag();

        // 表头：计价日期、帐户名称、单位价格
        sql = " select m.insuaccname, r.unitpricebuy, r.unitpricesell "
              + "  from loaccunitprice r, lmriskinsuacc m "
              + " where m.insuaccno = r.insuaccno and r.state = '0' "
              + "  and r.insuaccno = '" + "?insuaccno?"
              + "' and Startdate = to_date('"+"?Startdate?"+"','yyyy-mm-dd')";
        SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
        sqlbv12.sql(sql);
        sqlbv12.put("insuaccno", mInsuAccNo);
        sqlbv12.put("Startdate", mStartdate);
        tSSRS = tExeSQL.execSQL(sqlbv12);
        if(tExeSQL.mErrors.needDealError())
        {
            CError.buildErr(this, "单位价格查询失败!");
            return false;
        }
        if(tSSRS == null || tSSRS.getMaxRow() < 1)
        {
            CError.buildErr(this, "单位价格查询失败!");
            return false;
        }

        String buyPrice = mBqNameFun.getRound(tSSRS.GetText(1, 2), "0.0000"); // 买入价
        String payPrice = mBqNameFun.getRound(tSSRS.GetText(1, 3), "0.0000"); // 卖出价

        tTextTag.add("ValueDate", mStartdate); // 计价日
        tTextTag.add("InsuAccName", tSSRS.GetText(1, 1)); // 账户名称
        tTextTag.add("UnitPriceBuy", buyPrice); // 买入价
        tTextTag.add("UnitPriceSell", payPrice); // 卖出价
        
        sql = "Select codealias From ldcurrency a Where Exists(Select 1 From lmriskinsuacc Where insuaccno = '"+"?insuaccno1?"+"' And currency =a.currcode)";
        SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
        sqlbv13.sql(sql);
        sqlbv13.put("insuaccno1", mInsuAccNo);
        tTextTag.add("Currency", tExeSQL.getOneValue(sqlbv13));

        ListTable tListTable = new ListTable(); // 分项记录
        tListTable.setName("WG");
        String[] tTitle = new String[19];
        for(int i = 0; i < 19; i++)
        {
            tTitle[i] = "Title" + i;
        }
        /*
         * // 循环险种进行统计 sql = " select m.riskcode, m.riskname from lmrisktoacc a,
         * lmrisk m " + " where m.riskcode = a.riskcode and a.insuaccno = '" +
         * mInsuAccNo + "'"; SSRS riskSSRS = tExeSQL.execSQL(sql); if
         * (tExeSQL.mErrors.needDealError()) { CError.buildErr(this, "险种查询失败!");
         * return false; } if (riskSSRS == null || riskSSRS.getMaxRow() < 1) {
         * CError.buildErr(this, "险种查询失败!"); return false; }
         */
       /* "Oracle：select nvl(sum(prem),0) from ljtempfee_lmriskapp
        改造为：select (case when sum(prem) is not null then sum(prem)  else 0 end)  from ljtempfee_lmriskapp;"
*/
        sql = "select  substr(a.managecom,1,4) aa," // 分公司
              + "contno bb," // 保单号
              + "a.riskcode cc," // 险种
              + " sum((case busytype when 'NB' then unitcount else 0 end)*c.unitpricebuy) BF_NB , " // 新契约买入
              + " sum((case busytype when 'IP' then (case moneytype when 'BF' then unitcount else 0 end) else 0 end)*c.unitpricebuy) BF_IP," // 追加保费
              + " sum((case busytype when 'RN' then (case moneytype when 'BF' then unitcount else 0 end) else 0 end)*c.unitpricebuy) TF_RN," // 续期保费买入
              + " sum((case busytype when 'TI' then (case moneytype when 'IN' then unitcount else 0 end) else 0 end)*c.unitpricebuy) BF_in , " // 其他帐户转入
              + " sum((case busytype when 'TI' then (case moneytype when 'TI' then unitcount else 0 end) else 0 end)*c.unitpricebuy) TI_TI, " // 转出赎回
              + " sum((case busytype when 'IT' then (case moneytype when 'TF' then unitcount else 0 end) else 0 end)*c.unitpricebuy) TF_IT," // 犹豫期退费赎回
              + " sum((case busytype when 'AR' then (case moneytype when 'AR' then unitcount else 0 end) else 0 end)*c.unitpricebuy) AR_AR ," // 部分领取赎回
              + " sum((case busytype when 'CT' then (case moneytype when 'TB' then unitcount else 0 end) else 0 end)*c.unitpricebuy) TB_CT, " // 退保赎回
              + " sum((case busytype when 'CL' then unitcount else 0 end)*c.unitpricebuy) PK_CL," // 理赔赎回
              + " sum((case busytype when 'AG' then (case moneytype when 'EF' then unitcount else 0 end) else 0 end)*c.unitpricebuy) EF_AG," // 满期赎回
              + " sum((case moneytype when 'GL' then (case when shouldvaluedate = valuedate then (case feecode when 'RPUL07' then unitcount else 0 end) else 0 end) else 0 end)*c.unitpricebuy) GL_001," // 风险保费扣除
              + " sum((case moneytype when 'GL' then (case when shouldvaluedate = valuedate then (case feecode when '890000' then unitcount else 0 end) else 0 end) else 0 end)*c.unitpricebuy) GL_002," // 保单管理费扣除
              +
              " sum((case moneytype when 'SX' then unitcount else 0 end)*c.unitpricebuy) SX ," // 手续费
              + " sum((case moneytype when 'SP' then unitcount else 0 end)*c.unitpricebuy) SP_NULL ," // 持续奖金
              + " (case when sum(unitcount*c.unitpricebuy) is not null then sum(unitcount*c.unitpricebuy) else 0 end), " // 净流量
              + " (case when sum(UnitCount) is not null then sum(UnitCount) else 0 end)" // 换算净单位数
              + " from  LCInsureAcctrace a,loaccunitprice c"
              + " where a.insuaccno=c.insuaccno  and a.shouldvaluedate=c.startdate "
              + " and ( busytype<>'EB' or busytype is null) and a.state <> 0 and a.insuaccno = '"
              + "?a1?"
              + "'  "
              + " and a.valuedate = to_date('"
              + "?a2?"
              + "','YYYY-MM-DD')"
              + " group by substr(a.managecom,1,4),contno,a.riskcode "
              + " union all "
              + "select  substr(a.managecom,1,4) aa," // 分公司
              + "a.contno," // 保单号
              + "a.riskcode," // 险种
              //+ " -sum((case busytype when 'NB' then unitcount else 0 end)*c.unitpricebuy) BF_NB , " // 新契约买入
              + " 0,"
              //+ " -sum((case busytype when 'IP' then (case moneytype when 'BF' then unitcount else 0 end) else 0 end)*c.unitpricebuy) BF_IP," // 追加保费
              + " 0,"
              //+ " -sum((case busytype when 'RN' then (case moneytype when 'BF' then unitcount else 0 end) else 0 end)*c.unitpricebuy) TF_RN," // 续期保费买入
              + " 0,"
              //+ " -sum((case busytype when 'TI' then (case moneytype when 'IN' then unitcount else 0 end) else 0 end)*c.unitpricebuy) BF_in , " // 其他帐户转入
              + " 0,"
              //+ " -sum((case busytype when 'TI' then (case moneytype when 'TI' then unitcount else 0 end) else 0 end)*c.unitpricebuy) TI_TI, " // 转出赎回
              + " 0,"
              //+ " -sum((case busytype when 'IT' then (case moneytype when 'TF' then unitcount else 0 end) else 0 end)*c.unitpricebuy) TF_IT," // 犹豫期退费赎回
              + " 0,"
              //+ " -sum((case busytype when 'AR' then (case moneytype when 'AR' then unitcount else 0 end) else 0 end)*c.unitpricebuy) AR_AR ," // 部分领取赎回
              + " 0,"
              //+ " -sum((case busytype when 'CT' then (case moneytype when 'TB' then unitcount else 0 end) else 0 end)*c.unitpricebuy) TB_CT, " // 退保赎回
              + " 0,"
              //+ " -sum((case busytype when 'CL' then unitcount else 0 end)*c.unitpricebuy) PK_CL," // 理赔赎回
              + " 0,"
              //+ " -sum((case busytype when 'AG' then (case moneytype when 'EF' then unitcount else 0 end) else 0 end)*c.unitpricebuy) EF_AG," // 满期赎回
              + " 0,"
              //+ " -sum((case moneytype when 'GL' then (case when shouldvaluedate = valuedate then (case feecode when 'RPUL07' then unitcount else 0 end) else 0 end) else 0 end)*c.unitpricebuy) GL_001," // 风险保费扣除
              + " 0,"
              //+ " -sum((case moneytype when 'GL' then (case when shouldvaluedate = valuedate then (case feecode when '890000' then unitcount else 0 end) else 0 end) else 0 end)*c.unitpricebuy) GL_002," // 保单管理费扣除
              + " 0,"
              //+
              //" -sum((case moneytype when 'SX' then unitcount else 0 end)*c.unitpricebuy) SX ," // 手续费
              + " 0,"
              //+ " -sum((case moneytype when 'SP' then unitcount else 0 end)*c.unitpricebuy) SP_NULL ," // 持续奖金
              + " 0,"
              
             // "Oracle：select nvl(sum(prem),0) from ljtempfee_lmriskapp
             // 改造为：select (case when sum(prem) is not null then sum(prem)  else 0 end)  from ljtempfee_lmriskapp;"

              + " (case when sum(a.unitcount*c.unitpricebuy) is not null then sum(a.unitcount*c.unitpricebuy) else 0 end), " // 净流量
              + " (case when sum(a.UnitCount) is not null then sum(a.UnitCount) else 0 end)" // 换算净单位数
              + " from LCInsureAcctrace a, loaccunitprice c,LCInsureAcctrace g "
              + " where a.insuaccno = c.insuaccno  and g.serialno=a.accalterno "
              + " and g.insuaccno=a.insuaccno and c.startdate=g.valuedate and a.riskcode=g.riskcode and g.busytype<>'EB' and a.busytype = 'EB' and a.accaltertype<>'7' and a.state <> 0 and a.insuaccno = '"
              + "?a3?"
              //+ "' and a.serialno in (select accalterno from lcinsureacctrace b where b.busytype = 'EB' and b.valuedate = '"+mStartdate+"' and b.insuaccno = '"+mInsuAccNo+"')"
              + "' and a.valuedate = '"+"?valuedate?"+"' group by substr(a.managecom,1,4),a.contno,a.riskcode "
              + " order by aa,bb,cc"
              ;
        SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
        sqlbv14.sql(sql);
        sqlbv14.put("a1", mInsuAccNo);
        sqlbv14.put("a2", mStartdate);
        sqlbv14.put("a3", mInsuAccNo);
        sqlbv14.put("valuedate", mStartdate);
        tSSRS = tExeSQL.execSQL(sqlbv14);
        if(tExeSQL.mErrors.needDealError())
        {
            CError.buildErr(this, "数据查询失败!");
            return false;
        }

        if(tSSRS == null || tSSRS.getMaxRow() == 0)
        {
            logger.debug("该计价日内没有交易记录");
        }
        else
        {
            double tNB = 0.0;
            double tIP = 0.0;
            double tRN = 0.0;
            double tIN = 0.0;
            double tTI = 0.0;
            double tIT = 0.0;
            double tAR = 0.0;
            double tCT = 0.0;
            double tCL = 0.0;
            double tAG = 0.0;
            double tGL1 = 0.0;
            double tGL2 = 0.0;
            double tSX = 0.0;
            double tSP = 0.0; //持续奖金
            double tSumMoney = 0.0;
            double tSumUnitCount = 0.0;

            for(int i = 1; i <= tSSRS.getMaxRow(); i++)
            {
                String[] strArr = new String[19];
                strArr[0] = tSSRS.GetText(i, 1); // 分公司
                strArr[1] = tSSRS.GetText(i, 2); // 保单号
                strArr[2] = tSSRS.GetText(i, 3); // 险种代码
                strArr[3] = tSSRS.GetText(i, 4); // 新契约买入
                strArr[4] = tSSRS.GetText(i, 5); // 追加保费

                strArr[5] = tSSRS.GetText(i, 6); // 续期保费买入
                strArr[6] = tSSRS.GetText(i, 7); // 其他账户转入
                strArr[7] = tSSRS.GetText(i, 8); // 其他帐户转入
                strArr[8] = tSSRS.GetText(i, 9); // 犹豫期退费赎回
                strArr[9] = tSSRS.GetText(i, 10); // 部分领取赎回

                strArr[10] = tSSRS.GetText(i, 11); // 退保赎回
                strArr[11] = tSSRS.GetText(i, 12); // 理赔赎回
                strArr[12] = tSSRS.GetText(i, 13); // 满期赎回
                strArr[13] = tSSRS.GetText(i, 14); // 风险保费扣除
                strArr[14] = tSSRS.GetText(i, 15); // 保单管理费扣除

                strArr[15] = tSSRS.GetText(i, 16); // 手续费

                strArr[18] = tSSRS.GetText(i, 17); // 持续奖金　

                strArr[16] = tSSRS.GetText(i, 18); // 净现金流
                strArr[17] = tSSRS.GetText(i, 19); // 换算净单位数

                tNB += Double.parseDouble(strArr[3]);
                tIP += Double.parseDouble(strArr[4]);
                tRN += Double.parseDouble(strArr[5]);
                tIN += Double.parseDouble(strArr[6]);
                tTI += Double.parseDouble(strArr[7]);
                tIT += Double.parseDouble(strArr[8]);
                tAR += Double.parseDouble(strArr[9]);
                tCT += Double.parseDouble(strArr[10]);
                tCL += Double.parseDouble(strArr[11]);
                tAG += Double.parseDouble(strArr[12]);
                tGL1 += Double.parseDouble(strArr[13]);
                tGL2 += Double.parseDouble(strArr[14]);
                tSX += Double.parseDouble(strArr[15]);

                tSP += Double.parseDouble(strArr[18]);

                tSumMoney += Double.parseDouble(strArr[16]);
                tSumUnitCount += Double.parseDouble(strArr[17]);

                // 计算完之后再截位
                strArr[3] = mBqNameFun.getRound(strArr[3], "0.00");
                strArr[4] = mBqNameFun.getRound(strArr[4], "0.00");
                strArr[5] = mBqNameFun.getRound(strArr[5], "0.00");
                strArr[6] = mBqNameFun.getRound(strArr[6], "0.00");
                strArr[7] = mBqNameFun.getRound(strArr[7], "0.00");
                strArr[8] = mBqNameFun.getRound(strArr[8], "0.00");
                strArr[9] = mBqNameFun.getRound(strArr[9], "0.00");
                strArr[10] = mBqNameFun.getRound(strArr[10], "0.00");
                strArr[11] = mBqNameFun.getRound(strArr[11], "0.00");
                strArr[12] = mBqNameFun.getRound(strArr[12], "0.00");
                strArr[13] = mBqNameFun.getRound(strArr[13], "0.00");
                strArr[14] = mBqNameFun.getRound(strArr[14], "0.00");
                strArr[15] = mBqNameFun.getRound(strArr[15], "0.00");
                strArr[16] = mBqNameFun.getRound(strArr[16], "0.00");
                strArr[17] = mBqNameFun.getRound(strArr[17], "0.0000");

                strArr[18] = mBqNameFun.getRound(strArr[18], "0.00");

                tListTable.add(strArr); // 插入分项
            }
            tTextTag.add("NB", mBqNameFun.getRound(tNB));
            tTextTag.add("IP", mBqNameFun.getRound(tIP));
            tTextTag.add("RN", mBqNameFun.getRound(tRN));
            tTextTag.add("IN", mBqNameFun.getRound(tIN));
            tTextTag.add("TI", mBqNameFun.getRound(tTI));
            tTextTag.add("IT", mBqNameFun.getRound(tIT));
            tTextTag.add("AR", mBqNameFun.getRound(tAR));
            tTextTag.add("CT", mBqNameFun.getRound(tCT));
            tTextTag.add("CL", mBqNameFun.getRound(tCL));
            tTextTag.add("AG", mBqNameFun.getRound(tAG));
            tTextTag.add("GL1", mBqNameFun.getRound(tGL1));
            tTextTag.add("GL2", mBqNameFun.getRound(tGL2));
            tTextTag.add("SX", mBqNameFun.getRound(tSX));

            tTextTag.add("SP", mBqNameFun.getRound(tSP));

            tTextTag.add("SumMoney", mBqNameFun.getRound(tSumMoney));
            tTextTag.add("SumUnitCount", mBqNameFun.getRound(tSumUnitCount,
                    "0.0000"));
        }
        tXmlExport.addTextTag(tTextTag);
        tXmlExport.addListTable(tListTable, tTitle);

        // 垃圾处理
        tTextTag = null;
        tExeSQL = null;

        logger.debug("开始打印");
        return true;
    }

    /** 将xmlexport添加到mResult中，以供以后调用 */
    private boolean outputData()
    {
        mResult = new VData();
        mResult.addElement(tXmlExport);
        return true;
    }

    /** 为外部构造一个返回mResult的接口 */
    public VData getResult()
    {
        return mResult;
    }

    public CErrors getErrors()
    {
        return mErrors;
    }

    private void collectGarbage()
    {
        if(rInputData != null)
        {
            rInputData = null;
        }
        if(rGlobalInput != null)
        {
            rGlobalInput = null;
        }

        if(tXmlExport != null)
        {
            tXmlExport = null;
        }
    }

    /**
     * 调试主程序业务方法
     *
     * @param String[]
     */
    public static void main(String[] args)
    {
        logger.debug("test start:");
        VData tVData = new VData();

        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput.ManageCom = "86";
        tGlobalInput.Operator = "001";

        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("InsuAccNo", "890000");
        tTransferData.setNameAndValue("ValueDate", "2007-06-15");
        tVData.add(tGlobalInput);
        tVData.add(tTransferData);
        TLReportValueDatePrintBL tTLReportValueDatePrintBL = new
                TLReportValueDatePrintBL();
        if(!tTLReportValueDatePrintBL.submitData(tVData, ""))
        {
            logger.debug("test failed:"
                               +
                               tTLReportValueDatePrintBL.mErrors.getFirstError()
                               .toString());
        }
        logger.debug("test end");

    }
}
