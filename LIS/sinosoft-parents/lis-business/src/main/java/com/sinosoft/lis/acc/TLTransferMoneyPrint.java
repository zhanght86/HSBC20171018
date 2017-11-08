package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.f1print.*;

import java.text.DecimalFormat;

public class TLTransferMoneyPrint
        implements BqBill
{
private static Logger logger = Logger.getLogger(TLTransferMoneyPrint.class);


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
    public static final String VTS_NAME = "TLTransferMoney.vts";

    // ==========================================================================

    /**
     * 外部调用本类的业务处理接口
     *
     * @param VData
     * @param String
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate)
    {

        // 接收参数
        if (cInputData == null)
        {
            CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
            logger.debug(
                    "\t@> TLTransferMoneyPrint.submitData() : 无法获取 InputData 数据！");
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
        if (!getInputData())
        {
            return false;
        }
        if (!checkData())
        {
            return false;
        }
        if (!dealData())
        {
            return false;
        }
        if (!outputData())
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
        if (rGlobalInput == null)
        {
            CError.buildErr(this, "无法获取用户登录机构信息！");
            logger.debug(
                    "\t@> TLTransferMoneyPrint.getInputData() : 无法获取 GlobalInput 数据！");
            return false;
        }

        rLOAccUnitPriceSchema = (LOAccUnitPriceSchema) rInputData
                                .getObjectByObjectName("LOAccUnitPriceSchema",
                0);
        if (rLOAccUnitPriceSchema == null)
        {
            mErrors.addOneError(new CError("数据传输不完全！"));
            return false;
        }
        mInsuAccNo = rLOAccUnitPriceSchema.getInsuAccNo();
        // mRiskCode = rLOAccUnitPriceSchema.getRiskCode();
        mStartdate = rLOAccUnitPriceSchema.getStartDate();
        return true;
    } // function getInputData end

    // ==========================================================================
    /**
     * 根据传入的数据进行业务逻辑层的合法性校验
     *
     * @param null
     * @return boolean
     */
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

        logger.debug("\t@> TLTransferMoneyPrint.dealData() 开始");
        tXmlExport = new XmlExport();
        tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档
        logger.debug("初始化xml文档");

        String QuerySQL = "";
        ExeSQL tExeSQL = new ExeSQL();
        SSRS tssrs = new SSRS();
        TextTag tTextTag = new TextTag();
        SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
        // 表头：计价日期、帐户名称、单位价格
        QuerySQL = " select m.insuaccname, r.unitpricebuy, r.unitpricesell "
                   + "  from loaccunitprice r, lmriskinsuacc m "
                   + " where m.insuaccno = r.insuaccno and r.state = '0' "
                   + "  and r.insuaccno = '" + "?mInsuAccNo?"
                   + "' and startdate = to_date('" + "?mStartdate?" + "','yyyy-mm-dd') ";
        sqlbv1.sql(QuerySQL);
        sqlbv1.put("mInsuAccNo", mInsuAccNo);
        sqlbv1.put("mStartdate", mStartdate);
        tssrs = tExeSQL.execSQL(sqlbv1);
        if (tExeSQL.mErrors.needDealError())
        {
            CError.buildErr(this, "单位价格查询失败!");
            return false;
        }
        if (tssrs == null || tssrs.getMaxRow() < 1)
        {
            CError.buildErr(this, "单位价格查询失败!");
            return false;
        }
        double unitPrice = Double.parseDouble(tssrs.GetText(1, 2));
        tTextTag.add("ValueDate", mStartdate); // 计价日
        tTextTag.add("InsuAccName", tssrs.GetText(1, 1)); // 账户名称
        tTextTag.add("UnitPriceBuy", // 买入价
                     mBqNameFun.getRound(tssrs.GetText(1, 2), "0.0000"));
        tssrs = null;
        SQLwithBindVariables sqlbv2= new SQLwithBindVariables();
        QuerySQL = "Select codealias From ldcurrency a Where Exists(Select 1 From lmriskinsuacc Where insuaccno = '"+"?mInsuAccNo?"+"' And currency =a.currcode)";
        sqlbv2.sql(QuerySQL);
        sqlbv2.put("mInsuAccNo", mInsuAccNo);
        tTextTag.add("Currency", tExeSQL.getOneValue(sqlbv2));
        
        // --------------------------------------------------------------------------------------------------------
        SQLwithBindVariables sqlbv3= new SQLwithBindVariables();
        QuerySQL = "select to_char(max(startdate),'YYYY-MM-DD') from loaccunitprice where startdate<to_date('" + "?mStartdate?" + "','yyyy-mm-dd') ";
        sqlbv3.sql(QuerySQL);
        sqlbv3.put("mStartdate", mStartdate);
        tTextTag.add("LastValueDate", tExeSQL.getOneValue(sqlbv3)); // 上一计价日
        logger.debug(tExeSQL.getOneValue(QuerySQL));
        tTextTag.add("CurrentDate", PubFun.getCurrentDate()); // 制表日期
        tTextTag.add("CurrentTime", PubFun.getCurrentTime()); // 制表时间
        // --------------------------------------------------------------------------------------------------------
        double tCustomerChg = 0.0;
        double tCorrectChg = 0.0;
        double tDelayChg = 0.0;
        double tAccAsManageFee = 0.0;
        double tTransferMoney = 0.0;
        SQLwithBindVariables sqlbv4= new SQLwithBindVariables();
        QuerySQL = "select (case when sum(unitcount*c.unitpricebuy) is not null then sum(unitcount*c.unitpricebuy)  else 0 end) from lcinsureacctrace a,loaccunitprice c"
                   + " where  a.insuaccno=c.insuaccno  and a.shouldvaluedate=c.startdate"
                   + " and valuedate=to_date('" + "?mStartdate?" + "','yyyy-mm-dd') "
                   + " and ( busytype<>'EB' or busytype is null) and a.state<>'0' and a.insuaccno='"
                   + "?mInsuAccNo?" + "'";
        sqlbv4.sql(QuerySQL);
        sqlbv4.put("mStartdate", mStartdate);
        sqlbv4.put("mInsuAccNo", mInsuAccNo);
        tCustomerChg = Double.parseDouble(tExeSQL.getOneValue(sqlbv4));
        SQLwithBindVariables sqlbv5= new SQLwithBindVariables();
        QuerySQL = "select -(case when sum(a.unitcount*c.unitpricebuy) is not null then sum(a.unitcount*c.unitpricebuy)  else 0 end) from LCInsureAcctrace a, loaccunitprice c,LCInsureAcctrace g"
                   + " where  a.insuaccno = c.insuaccno  and g.serialno=a.accalterno and g.insuaccno=a.insuaccno and c.startdate=g.valuedate and a.riskcode=g.riskcode and g.busytype<>'EB' and a.busytype = 'EB' and a.accaltertype<>'7'  and a.state <> 0 and a.insuaccno ='"
                   + "?mInsuAccNo?" + "' and a.valuedate = '"+"?mStartdate?"+"'";
        sqlbv5.sql(QuerySQL);
        sqlbv5.put("mInsuAccNo", mInsuAccNo);
        sqlbv5.put("mStartdate", mStartdate);
                   
        tCustomerChg = tCustomerChg -
                       Double.parseDouble(tExeSQL.getOneValue(sqlbv5));
        tTextTag.add("CustomerChg", mBqNameFun.getRound(tCustomerChg, "0.00")); // 客户净现金流
        // -------------------------------------------------------------------------------------------------------
        SQLwithBindVariables sqlbv6= new SQLwithBindVariables();
        QuerySQL = "select distinct (case when comchgunitcount is not null then comchgunitcount  else 0 end) from loaccunitprice "
                + " where 1=1  and state = 0 and insuaccno = '"
                + "?mInsuAccNo?"
                + "' and startdate = to_date('" + "?mStartdate?" + "','yyyy-mm-dd') ";
        sqlbv6.sql(QuerySQL);
        sqlbv6.put("mInsuAccNo", mInsuAccNo);
        sqlbv6.put("mStartdate", mStartdate);
        double tCompanyUnitcount = 0.0;
        double tCompanyMoney = 0.0;

        tCompanyUnitcount = Double.parseDouble(tExeSQL.getOneValue(sqlbv6));
        tCompanyMoney = tCompanyUnitcount * unitPrice;
        tTextTag
                .add("CompanyChg", mBqNameFun.getRound(tCompanyMoney, "0.00")); // 公司净现金流
        // --------------------------------------------------------------------------------------------------------
        SQLwithBindVariables sqlbv7= new SQLwithBindVariables();
        QuerySQL = "select distinct "
                   + "(case when (select distinct unitpricebuy from loaccunitprice t where t.state = '0' and t.insuaccno = bef.insuaccno and t.startdate = bef.valuedate) is not null then (select distinct unitpricebuy from loaccunitprice t where t.state = '0' and t.insuaccno = bef.insuaccno and t.startdate = bef.valuedate)  else 0 end)," // 错误日价格
                   + " cur.unitcount," // --申请单位数
                   + "(case when (select distinct unitpricebuy from loaccunitprice t where t.state = '0' and t.insuaccno = cur.insuaccno and t.startdate = cur.valuedate) is not null then (select distinct unitpricebuy from loaccunitprice t where t.state = '0' and t.insuaccno = cur.insuaccno and t.startdate = cur.valuedate)  else 0 end)" // --实际处理日的价格
                   + " from lcinsureacctrace cur, lcinsureacctrace bef"
                   + " where 1=1 " + " and cur.accalterno = bef.serialno "
                   + " and cur.busytype = 'EB' and cur.accaltertype<>'7' and cur.state <>'0' "
                   + " and cur.valuedate = to_date('" + "?mStartdate?" + "','yyyy-mm-dd')"
                   + " and cur.insuaccno = '" + "?mInsuAccNo?" + "'";
        sqlbv7.sql(QuerySQL);
        sqlbv7.put("mStartdate", mStartdate);
        sqlbv7.put("mInsuAccNo", mInsuAccNo);
        logger.debug("纠错================="+QuerySQL);
        double tCorrectrCount;
        double tShouldPrice;
        double tValuePrice;
        double tOneCorrectChg;
        tssrs = tExeSQL.execSQL(sqlbv7);
        if (tssrs != null && tssrs.getMaxRow() > 0)
        {
            for (int i = 1; i <= tssrs.getMaxRow(); i++)
            {
                tShouldPrice = Double.parseDouble(tssrs.GetText(i, 1));
                tValuePrice = Double.parseDouble(tssrs.GetText(i, 3));
                tCorrectrCount = Double.parseDouble(tssrs.GetText(i, 2));
                tOneCorrectChg = tCorrectrCount * (tValuePrice - tShouldPrice);
                logger.debug(tOneCorrectChg);
                tCorrectChg += tOneCorrectChg;
            }
        }
        SQLwithBindVariables sqlbv8= new SQLwithBindVariables();
		// 取帐户轨迹调涨操作损益
        QuerySQL = " select (case when sum(money) is not null then sum(money)  else 0 end) " // --损益金额
				+ " from lcinsureacctrace a "
				+ " where 1=1 "
				+ " and a.busytype = 'EB' and accaltertype='7' and a.state <>'0' "
				+ " and a.valuedate = to_date('" + "?mStartdate?"+ "','yyyy-mm-dd')"
				+ " and a.insuaccno = '" + "?mInsuAccNo?"
				+ "'";
        sqlbv8.sql(QuerySQL);
        sqlbv8.put("mStartdate", mStartdate);
        sqlbv8.put("mInsuAccNo", mInsuAccNo);
        tCorrectChg +=Double.parseDouble(tExeSQL.getOneValue(sqlbv8));
        
        tTextTag.add("CorrectChg", mBqNameFun.getRound(tCorrectChg, "0.00")); // 纠错
        // --------------------------------------------------------------------------------------------------------
        SQLwithBindVariables sqlbv9= new SQLwithBindVariables();
       QuerySQL = " select "
                   + "(select distinct unitpricebuy from loaccunitprice t where t.state = '0' and t.insuaccno = cur.insuaccno and t.startdate =("
                   +
                "select min(startdate) from loaccunitprice where startdate>=cur.paydate"
                   + "))," // --申请处理日下一日价格
                   + " sum(cur.unitcount)," // --申请的单位数
                   + " (select distinct unitpricebuy from loaccunitprice t where t.state = '0' and t.insuaccno = cur.insuaccno and t.startdate = cur.valuedate)" // --实际处理日期的价格
                   + " from lcinsureacctrace cur" + " where 1=1"
                   + " and (cur.busytype is null or cur.busytype <> 'EB') "
                   + " and cur.state <>'0' " + " and cur.valuedate = to_date('" + "?mStartdate?"+ "','yyyy-mm-dd')"
                   + " and cur.valuedate <> cur.shouldvaluedate"
                   + " and cur.insuaccno = '" + "?mInsuAccNo?" + "'"
                   + " group by cur.paydate,cur.valuedate,cur.insuaccno";
       sqlbv9.sql(QuerySQL);
       sqlbv9.put("mStartdate", mStartdate);
       sqlbv9.put("mInsuAccNo", mInsuAccNo);
        tssrs = tExeSQL.execSQL(sqlbv9);
        double tDalayrCount;
        double tOneDelayChg;
        if (tssrs != null && tssrs.getMaxRow() > 0)
        {
            for (int i = 1; i <= tssrs.getMaxRow(); i++)
            {
                tShouldPrice = Double.parseDouble(tssrs.GetText(i, 1));
                tValuePrice = Double.parseDouble(tssrs.GetText(i, 3));
                tDalayrCount = Double.parseDouble(tssrs.GetText(i, 2));
                tOneDelayChg = tDalayrCount * (tValuePrice - tShouldPrice);
                tDelayChg += tOneDelayChg;
            }
        }
        tTextTag.add("DelayChg", mBqNameFun.getRound(tDelayChg, "0.00")); // 延迟
        // --------------------------------------------------------------------------------------------------------
        SQLwithBindVariables sqlbv10= new SQLwithBindVariables();
        QuerySQL = "select distinct (case when -AccAsManageFee is not null then -AccAsManageFee  else 0 end) from loaccunitprice where Startdate=to_date('" + "?mStartdate?" + "','yyyy-mm-dd') and insuaccno='" + "?mInsuAccNo?" + "'";
        sqlbv10.sql(QuerySQL);
        sqlbv10.put("mStartdate", mStartdate);
        sqlbv10.put("mInsuAccNo", mInsuAccNo);
        tAccAsManageFee = Double.parseDouble(tExeSQL.getOneValue(sqlbv10));
        tTextTag.add("AccAsManageFee", mBqNameFun.getRound(tAccAsManageFee,
                "0.00")); // 资产管理费

        tTransferMoney = tCustomerChg + tCompanyMoney + tCorrectChg + tDelayChg
                         + tAccAsManageFee;
        tTextTag.add("TransferMoney", mBqNameFun.getRound(tTransferMoney,
                "0.00")); // 划款

        // 输出数据
        tXmlExport.addTextTag(tTextTag);
        // 垃圾处理
        tTextTag = null;
        tExeSQL = null;

        return true;
    }

    /**
     * 准备经过本类处理的输出数据
     *
     * @param null
     * @return boolean
     */
    private boolean outputData()
    {

        rResultData = new VData();
        rResultData.addElement(tXmlExport);

        return true;
    }

    /**
     * 返回经过本类处理的数据结果
     *
     * @param null
     * @return VData
     */
    public VData getResult()
    {
        return rResultData;
    } // function getResult end

    /**
     * 返回传入本类的操作类型
     *
     * @param null
     * @return String
     */
    public String getOperation()
    {
        return rOperation;
    } // function getResult end

    /**
     * 返回本类运行时产生的错误信息
     *
     * @param null
     * @return CErrors
     */
    public CErrors getErrors()
    {
        return mErrors;
    } // function getErrors end

    /**
     * 处理本类运行时产生的垃圾
     *
     * @param null
     */
    private void collectGarbage()
    {
        if (rInputData != null)
        {
            rInputData = null;
        }
        if (rGlobalInput != null)
        {
            rGlobalInput = null;
        }
        if (tXmlExport != null)
        {
            tXmlExport = null;
        }
    }
}
