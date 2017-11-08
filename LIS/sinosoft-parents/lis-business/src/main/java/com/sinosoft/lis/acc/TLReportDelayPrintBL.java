package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.f1print.BqBill;
import com.sinosoft.lis.f1print.BqNameFun;

import java.lang.*;
import java.util.*;
import com.sinosoft.lis.db.LMRiskToAccDB;

//******************************************************************************
//延迟报告

public class TLReportDelayPrintBL
        implements BqBill
{
private static Logger logger = Logger.getLogger(TLReportDelayPrintBL.class);

    public TLReportDelayPrintBL()
    {
    }

    // ==========================================================================

    // 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
    public CErrors mErrors = new CErrors();

    // 输入数据
    // 输入数据
    private VData rInputData;

    private String rOperation;

    private String mRiskCode;

    private String mInsuAccNo;

    private String mStartdate;

    private GlobalInput rGlobalInput;

    private LOAccUnitPriceSchema rLOAccUnitPriceSchema = new
            LOAccUnitPriceSchema();

    private TransferData mTransferData = new TransferData();

    // 本类变量
    private String CurrentDate = PubFun.getCurrentDate();

    private String CurrentTime = PubFun.getCurrentTime();

    private BqNameFun mBqNameFun = new BqNameFun();

    // 输出数据
    private XmlExport rXmlExport;

    private VData rResultData;

    // FormulaOne 打印模板文件
    public static final String FormulaOneVTS = "TLReportDelay.vts";

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
        logger.debug("\t@> AccountDelayPrintBL.submitData() 开始");

        // 接收参数
        if (cInputData == null)
        {
            CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
            logger.debug(
                    "\t@> AccountDelayPrintBL.submitData() : 无法获取 InputData 数据！");
            return false;
        }
        else
        {
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
        // 存入LOREPORTMANAGER
        // if (!saveoutputData())
        // {
        // return false;
        // }

        // 垃圾处理
        collectGarbage();

        // logger.debug("\t@> EdorPayGetFormChangePrintBL.submitData()
        // 成功");
        return true;
    } // function submitData end

    // ==========================================================================

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
                    "\t@> AccountDelayPrintBL.getInputData() : 无法获取 GlobalInput 数据！");
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
    } // function checkData end

    // ==========================================================================

    /**
     * 本类的核心业务处理过程
     *
     * @param null
     * @return boolean
     */
    private boolean dealData()
    {

        logger.debug("\t@> AccountDelayPrintBL.dealData() 开始");
        // ----------------------------------------------------------------------
        // 输出 XML 文档
        rXmlExport = new XmlExport();
        try
        {
            rXmlExport.createDocument(FormulaOneVTS, "printer"); // 初始化 XML
            // 文档
        }
        catch (Exception ex)
        {
            CError.buildErr(this, "初始化打印模板错误！");
            logger.debug(
                    "\t@> AccountDelayPrintBL.dealData() : 设置 FormulaOne VTS 文件异常！");
            return false;
        }
        ExeSQL tExeSQL = new ExeSQL();
        String sql = "";
        SSRS tssrs = new SSRS();
        TextTag tTextTag = new TextTag();
        // 表头：计价日期、帐户名称、单位价格
        sql = " select m.insuaccname, r.unitpricebuy, r.unitpricesell "
              + "  from loaccunitprice r, lmriskinsuacc m "
              + " where m.insuaccno = r.insuaccno and r.state = '0' "
              + "  and r.insuaccno = '" + "?mInsuAccNo?"
              + "' and Startdate = to_date('" + "?mStartdate?" + "','yyyy-mm-dd')";
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        sqlbv.sql(sql);
        sqlbv.put("mInsuAccNo", mInsuAccNo);
        sqlbv.put("mStartdate", mStartdate);
        tssrs = tExeSQL.execSQL(sqlbv);
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
        tTextTag.add("ValueDate", mStartdate); // 计价日
        tTextTag.add("InsuAccName", tssrs.GetText(1, 1)); // 账户名称
        tTextTag.add("UnitPriceBuy", // 买入价
                     mBqNameFun.getRound(tssrs.GetText(1, 2), "0.0000"));
        tTextTag.add("UnitPriceSell", // 卖出价
                     mBqNameFun.getRound(tssrs.GetText(1, 3), "0.0000"));
        
        sql = "Select codealias From ldcurrency a Where Exists(Select 1 From lmriskinsuacc Where insuaccno = '"+"?mInsuAccNo?"+"' And currency =a.currcode)";
        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
        sqlbv1.sql(sql);
        sqlbv1.put("mInsuAccNo", mInsuAccNo);
        tTextTag.add("Currency", tExeSQL.getOneValue(sqlbv1));
        ListTable tListTable = new ListTable(); // 分项记录
        tListTable.setName("AccDelay");

        String[] tTitle = new String[11];
        for (int i = 0; i < 11; i++)
        {
            tTitle[i] = "Title" + i;
        }

        double dSumUnitCount = 0.0;
        double dSumMoney = 0.0;
        double dSumTransferMoney = 0.0;

        // 循环险种进行统计
        sql =
                " select m.riskcode, m.riskshortname from lmrisktoacc a, lmrisk m  "
                + " where m.riskcode = a.riskcode and a.insuaccno = '"
                + "?mInsuAccNo?" + "'";
        SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
        sqlbv2.sql(sql);
        sqlbv2.put("mInsuAccNo", mInsuAccNo);
        SSRS riskSSRS = tExeSQL.execSQL(sqlbv2);
        if (tExeSQL.mErrors.needDealError())
        {
            CError.buildErr(this, "险种查询失败!");
            return false;
        }
        if (riskSSRS == null || riskSSRS.getMaxRow() < 1)
        {
            CError.buildErr(this, "险种查询失败!");
            return false;
        }

        ListTable riskSumListTable = new ListTable(); // 合计记录
        riskSumListTable.setName("RiskSum");
        String[] tTitleRiskSum = new String[4];
        for (int i = 0; i < 4; i++)
        {
            tTitleRiskSum[i] = "TitleRiskSum" + i;
        }
        for (int k = 1; k <= riskSSRS.getMaxRow(); k++)
        {
            double dRiskSumUnitCount = 0.0;
            double dRiskSumMoney = 0.0;
            double dRiskSumTransferMoney = 0.0;

            sql = " select cur.contno," // 保单号
                  + " cur.paydate," // --申请处理日期
                  + "(select distinct unitpricebuy from loaccunitprice t where t.state = '0' and t.insuaccno = cur.insuaccno and t.startdate =("
                  +
                    "select min(startdate) from loaccunitprice where startdate>=cur.paydate"
                  + "))," // --申请处理日下一日价格
                  + " sum(cur.unitcount)," // --申请的单位数
                  + " sum(cur.money)," // --申请发生的金额
                  + " cur.valuedate," // --实际处理日期
                  + " (select distinct unitpricebuy from loaccunitprice t where t.state = '0' and t.insuaccno = cur.insuaccno and t.startdate = cur.valuedate)," // --实际处理日期的价格
                  + " '', "
                  + " '', "
                  + " '', "
                  // + " (select distinct p.edorreason from lpedoritem p where
                  // p.contno = cur.contno and p.edortype = cur.busytype and
                  // p.edorvalidate = cur.paydate), " //--出错原因
                  // + " (select riskshortname from lmrisk m where m.riskcode
                  // = cur.riskcode), "
                  + " cur.managecom" // 分公司
                  + " from lcinsureacctrace cur"
                  + " where 1=1"
                  + " and (cur.busytype is null or cur.busytype <> 'EB') "
                  + " and cur.state <>'0' "
                  + " and cur.valuedate = to_date('" + "?mStartdate?"  + "','yyyy-mm-dd')"
                  + " and cur.valuedate <> cur.shouldvaluedate"
                  + " and cur.insuaccno = '"
                  + "?mInsuAccNo?"
                  + "'"
                  + " and cur.riskcode = '"
                  + "?riskcode?"
                  + "'"
                  + " group by cur.managecom, cur.contno,cur.insuaccno,cur.paydate,cur.paydate,cur.valuedate"
                  + " order by cur.managecom, cur.contno ";
            SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
            sqlbv3.sql(sql);
            sqlbv3.put("mStartdate", mStartdate);      
            sqlbv3.put("mInsuAccNo", mInsuAccNo);
            sqlbv3.put("riskcode", riskSSRS.GetText(k, 1));
            logger.debug(sql);
            tssrs = tExeSQL.execSQL(sqlbv3);
            if (tExeSQL.mErrors.needDealError())
            {
                CError.buildErr(this, "数据查询失败!");
                return false;
            }

            double tTransferCount;
            double tShouldPrice;
            double tValuePrice;
            double tTransferMoney;

            if (tssrs != null && tssrs.getMaxRow() > 0)
            {
                for (int i = 1; i <= tssrs.getMaxRow(); i++)
                {
                    String[] strArr = new String[11]; // 保单列表

                    strArr[0] = tssrs.GetText(i, 1); // 保单号
                    strArr[1] = tssrs.GetText(i, 2); // 申请处理日期
                    strArr[2] = tssrs.GetText(i, 3); // 申请日价格
                    tShouldPrice = Double.parseDouble(tssrs.GetText(i, 3));
                    strArr[3] = tssrs.GetText(i, 4); // 单位数
                    strArr[4] = tssrs.GetText(i, 5); // 发生金额
                    strArr[5] = tssrs.GetText(i, 6); // 实际处理日期
                    strArr[6] = tssrs.GetText(i, 7); // 实际日期价格
                    tValuePrice = Double.parseDouble(tssrs.GetText(i, 7));
                    tTransferCount = Double.parseDouble(tssrs.GetText(i, 4));
                    tTransferMoney = tTransferCount
                                     * (tValuePrice - tShouldPrice); // 公司损失金额
                    strArr[7] = mBqNameFun.getRound(tTransferMoney, "0.00"); // 公司损失金额
                    strArr[8] = tssrs.GetText(i, 9); // 错误原因
                    strArr[9] = tssrs.GetText(i, 10); // 险种
                    strArr[10] = tssrs.GetText(i, 11); // 分公司

                    sql =  "select riskshortname from lmrisk m where m.riskcode = '"
                            + "?riskcode?" + "'";
                    SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
                    sqlbv4.sql(sql);
                    sqlbv4.put("riskcode", riskSSRS.GetText(k, 1));
                    strArr[9] = tExeSQL.getOneValue(sqlbv4); // 险种名称

                    // 险种小计
                    dRiskSumUnitCount += Double
                            .parseDouble(tssrs.GetText(i, 4)); // 单位数合计
                    dRiskSumMoney += Double.parseDouble(tssrs.GetText(i, 5)); // 金额合计
                    dRiskSumTransferMoney += tTransferMoney; // 损失金额合计

                    // 合计
                    dSumUnitCount += Double.parseDouble(tssrs.GetText(i, 4)); // 单位数合计
                    dSumMoney += Double.parseDouble(tssrs.GetText(i, 5)); // 金额合计
                    dSumTransferMoney += tTransferMoney; // 损失金额合计

                    // 以6位小数计算完之后再以4位显示
                    strArr[2] = mBqNameFun.getRound(strArr[2], "0.0000");
                    strArr[3] = mBqNameFun.getRound(strArr[3], "0.0000");
                    strArr[4] = mBqNameFun.getRound(strArr[4], "0.00");
                    strArr[6] = mBqNameFun.getRound(strArr[6], "0.0000");
                    strArr[7] = mBqNameFun.getRound(strArr[7], "0.00");

                    tListTable.add(strArr); // 插入一条记录
                }
                String[] strArrRiskSum = new String[4]; // 险种合计
                strArrRiskSum[0] = riskSSRS.GetText(k, 2); // 险种名称
                strArrRiskSum[1] = mBqNameFun.getRound(Double.toString(
                        dRiskSumUnitCount), "0.0000");
                strArrRiskSum[2] = mBqNameFun.getRound(Double.toString(
                        dRiskSumMoney), "0.00");
                strArrRiskSum[3] = mBqNameFun.getRound(dRiskSumTransferMoney,
                        "0.00");
                riskSumListTable.add(strArrRiskSum);
            }
        }

        ListTable sumListTable = new ListTable(); // 合计记录
        sumListTable.setName("Sum");
        String[] tTitleSum = new String[3];
        for (int i = 0; i < 3; i++)
        {
            tTitleSum[i] = "TitleSum" + i;
        }
        String[] strArrSum = new String[3]; // 合计
        strArrSum[0] = mBqNameFun.getRound(Double.toString(dSumUnitCount),
                                           "0.0000");
        strArrSum[1] = mBqNameFun.getRound(Double.toString(dSumMoney), "0.00");
        strArrSum[2] = mBqNameFun.getRound(dSumTransferMoney, "0.00");
        sumListTable.add(strArrSum); // 插入合计数
        tssrs = null;

        // 输出数据
        rXmlExport.addTextTag(tTextTag); // 输出表头
        rXmlExport.addListTable(tListTable, tTitle); //
        rXmlExport.addListTable(sumListTable, tTitleSum); //
        rXmlExport.addListTable(riskSumListTable, tTitleSum); //
        // 垃圾处理
        tTextTag = null;
        tExeSQL = null;

        logger.debug("\t@> AccountDelayPrintBL.dealData() 成功");
        return true;
    } // function printData end

    // ==========================================================================

    /**
     * 准备经过本类处理的输出数据
     *
     * @param null
     * @return boolean
     */
    private boolean outputData()
    {
        rResultData = new VData();
        rResultData.addElement(rXmlExport);
        return true;
    } // function prepareOutputData end

    // ==========================================================================

    /**
     * 将输出数据保存在LOREPORTMANAGER表中
     *
     * @param null
     * @return boolean
     */
    private boolean saveoutputData()
    {

        // LOReportManagerSchema

        LOReportManagerSchema tLOReportManagerSchema = new
                LOReportManagerSchema();
        String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", 20); // 生成印刷流水号
        tLOReportManagerSchema.setPrtSeq(tPrtSeq);
        tLOReportManagerSchema.setReportType("1");
        tLOReportManagerSchema.setReportCode("tl_103"); // 等代码定了以后再改
        tLOReportManagerSchema.setPrtFlag("0");
        tLOReportManagerSchema.setPrtTimes(0);
        tLOReportManagerSchema.setFilters("86");
        tLOReportManagerSchema.setCreateManageCom("86"); // 可能需要修改，定义成变量
        tLOReportManagerSchema.setManageCom("86");
        tLOReportManagerSchema.setTemplate(FormulaOneVTS);
        tLOReportManagerSchema.setOperator("001");
        tLOReportManagerSchema.setMakeDate(CurrentDate);
        tLOReportManagerSchema.setMakeTime(CurrentTime);
        tLOReportManagerSchema.setModifyDate(CurrentDate);
        tLOReportManagerSchema.setModifyTime(CurrentTime);
        tLOReportManagerSchema.setReportInfo(rXmlExport.getInputStream());
        // LOReportParamSchema
        LOReportParamSchema tLOReportParamSchema = new LOReportParamSchema();
        tLOReportParamSchema.setPrtSeq(tPrtSeq);
        tLOReportParamSchema.setParamName("InsuAccNo");
        tLOReportParamSchema.setParamValue(mInsuAccNo);
        tLOReportParamSchema.setOperator("001");
        tLOReportParamSchema.setMakeDate(CurrentDate);
        tLOReportParamSchema.setMakeTime(CurrentTime);
        tLOReportParamSchema.setModifyDate(CurrentDate);
        tLOReportParamSchema.setModifyTime(CurrentTime);

        LOReportParamSchema tLOReportParamSchema1 = new LOReportParamSchema();
        tLOReportParamSchema1.setPrtSeq(tPrtSeq);
        tLOReportParamSchema1.setParamName("ValueDate");
        tLOReportParamSchema1.setParamValue(mStartdate);
        tLOReportParamSchema1.setOperator("001");
        tLOReportParamSchema1.setMakeDate(CurrentDate);
        tLOReportParamSchema1.setMakeTime(CurrentTime);
        tLOReportParamSchema1.setModifyDate(CurrentDate);
        tLOReportParamSchema1.setModifyTime(CurrentTime);

        // 提交后台
        MMap tmap = new MMap();

        tmap.put(tLOReportParamSchema, "INSERT");
        tmap.put(tLOReportParamSchema1, "INSERT");
        tmap.put(tLOReportManagerSchema, "BLOBINSERT");

        VData pVData = new VData();
        PubSubmit tPubSubmit = new PubSubmit();
        pVData.add(tmap);
        if (!tPubSubmit.submitData(pVData, "PRINT"))
        {
            logger.debug("信息提交失败！");
            return false;
        }

        return true;
    } // function saveoutputData end

    // ==========================================================================

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

    // ==========================================================================

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

    // ==========================================================================

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

    // ==========================================================================

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
        if (rXmlExport != null)
        {
            rXmlExport = null;
        }
    } // function collectGarbage end

    // ==========================================================================

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
        tGlobalInput.ManageCom = "86110000";
        tGlobalInput.Operator = "001";

        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("InsuAccNo", "890000");
        tTransferData.setNameAndValue("ValueDate", "2007-06-09");
        tVData.add(tGlobalInput);
        tVData.add(tTransferData);
        TLReportDelayPrintBL tTLReportDelayPrintBL = new TLReportDelayPrintBL();
        if (!tTLReportDelayPrintBL.submitData(tVData, ""))
        {
            logger.debug("test failed:"
                               +
                               tTLReportDelayPrintBL.mErrors.getFirstError().toString());
        }
        logger.debug("test end");

    } // function main end

    // ==========================================================================

} // class EdorPayGetFormChangePrintBL end
