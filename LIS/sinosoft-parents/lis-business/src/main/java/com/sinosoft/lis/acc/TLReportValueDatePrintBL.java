package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.f1print.*;
import java.text.DecimalFormat;

//******************************************************************************

public class TLReportValueDatePrintBL
        implements BqBill
{
private static Logger logger = Logger.getLogger(TLReportValueDatePrintBL.class);

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

    public static final String VTS_NAME = "TLReportValueDate.vts"; // 模板名称

    /**
     * 外部调用本类的业务处理接口
     *
     * @param VData
     * @param String
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug("计价日报告（客户净现金流）");

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
        if(rGlobalInput == null)
        {
            CError.buildErr(this, "无法获取用户登录机构信息！");
            logger.debug(
                            "\t@> AccountDelayPrintBL.getInputData() : 无法获取 GlobalInput 数据！");
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
    } // function getInputData end

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
        // 变量
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
              + "  and r.insuaccno = '" + "?mInsuAccNo?"
              + "' and Startdate = to_date('" + "?mStartdate?" + "','yyyy-mm-dd') ";
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        sqlbv.sql(sql);
        sqlbv.put("mInsuAccNo", mInsuAccNo);
        sqlbv.put("mStartdate", mStartdate);
        tSSRS = tExeSQL.execSQL(sqlbv);
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
        double changePrice = Double.parseDouble(payPrice)
                             - Double.parseDouble(buyPrice); // 单位差价

        tTextTag.add("ValueDate", mStartdate); // 计价日
        tTextTag.add("InsuAccName", tSSRS.GetText(1, 1)); // 账户名称
        tTextTag.add("UnitPriceBuy", buyPrice); // 买入价
        tTextTag.add("UnitPriceSell", payPrice); // 卖出价

        sql = "Select codealias From ldcurrency a Where Exists(Select 1 From lmriskinsuacc Where insuaccno = '"+"?mInsuAccNo?"+"' And currency =a.currcode)";
        SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
        sqlbv2.sql(sql);
        sqlbv2.put("mInsuAccNo", mInsuAccNo);
        tTextTag.add("Currency", tExeSQL.getOneValue(sqlbv2));
        
        ListTable tListTable = new ListTable(); // 分项记录
        tListTable.setName("WG");
        String[] tTitle = new String[19];
        for(int i = 0; i < 19; i++)
        {
            tTitle[i] = "Title" + i;
        }

        ListTable sumListTable = new ListTable(); // 合计记录
        sumListTable.setName("Sum");
        String[] tTitleSum = new String[19];
        for(int i = 0; i < 19; i++)
        {
            tTitleSum[i] = "TitleSum" + i;
        }

        ListTable zSumListTable = new ListTable(); // 总计
        zSumListTable.setName("ZSum");
        String[] tTitleZSum = new String[17];
        for(int i = 0; i < 17; i++)
        {
            tTitleZSum[i] = "ZTitleSum" + i;
        }
        String[] tZSum = new String[18]; // 总计用数组

        // 循环险种进行统计
        sql = " select m.riskcode, m.riskname from lmrisktoacc a, lmrisk m  "
              + " where m.riskcode = a.riskcode and a.insuaccno = '"
              + "?mInsuAccNo?" + "'";
        SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
        sqlbv3.sql(sql);
        sqlbv3.put("mInsuAccNo", mInsuAccNo);
        SSRS riskSSRS = tExeSQL.execSQL(sqlbv3);
        if(tExeSQL.mErrors.needDealError())
        {
            CError.buildErr(this, "险种查询失败!");
            return false;
        }
        if(riskSSRS == null || riskSSRS.getMaxRow() < 1)
        {
            CError.buildErr(this, "险种查询失败!");
            return false;
        }
        for(int k = 1; k <= riskSSRS.getMaxRow(); k++)
        {
            String[] strSum = new String[20]; // 合计用的数组
            sql = "select  substr(a.managecom,1,4)," // 分公司
                  + " sum((case a.busytype when 'NB' then a.unitcount else 0 end)*c.unitpricebuy) BF_NB , " // 新契约买入
                  + " sum((case a.busytype when 'RN' then (case a.moneytype when 'BF' then a.unitcount else 0 end) else 0 end)*c.unitpricebuy) TF_RN," // 续期保费买入
                  + " sum((case a.busytype when 'TI' then (case a.moneytype when 'IN' then a.unitcount else 0 end) else 0 end)*c.unitpricebuy) BF_in , " // 其他帐户转入
                  + " sum((case a.busytype when 'TI' then (case a.moneytype when 'TI' then a.unitcount else 0 end) else 0 end)*c.unitpricebuy) TI_TI, " // 转出赎回
                  // + " sum((case busytype when 'NB' then unitcount else 0
                  // end))+sum((case busytype when 'IN' then unitcount else 0
                  // end))," //买入合计
                  + " sum((case a.busytype when 'IT' then (case a.moneytype when 'TF' then a.unitcount else 0 end) else 0 end)*c.unitpricebuy) TF_IT," // 犹豫期退费赎回
                  + " sum((case a.busytype when 'AR' then (case a.moneytype when 'AR' then a.unitcount else 0 end) else 0 end)*c.unitpricebuy) AR_AR ," // 部分领取赎回
                  + " sum((case a.busytype when 'CT' then (case a.moneytype when 'TB' then a.unitcount else 0 end) else 0 end)*c.unitpricebuy) TB_CT, " // 退保赎回
                  + " sum((case a.busytype when 'CL' then a.unitcount else 0 end)*c.unitpricebuy) PK_CL," // 理赔赎回
                  + " sum((case a.busytype when 'AG' then (case a.moneytype when 'EF' then a.unitcount else 0 end) else 0 end)*c.unitpricebuy) EF_AG," // 满期赎回
                  // + " 0, " //赎回合计
                  + " sum((case a.moneytype when 'GL' then (case when a.shouldvaluedate = a.valuedate then (case a.feecode when 'RPUL07' then a.unitcount else 0 end) else 0 end) else 0 end)*c.unitpricebuy) GL_001," // 风险保费扣除
                  + " sum((case a.moneytype when 'GL' then (case when a.shouldvaluedate = a.valuedate then (case a.feecode when '890000' then a.unitcount else 0 end) else 0 end) else 0 end)*c.unitpricebuy) GL_002," // 保单管理费扣除
                  // + " sum((case moneytype when 'GL' then (case when
                  // shouldvaluedate = valuedate then (case feecode when
                  // '890002' then money else 0 end) else 0 end) else 0 end))
                  // GL_003," //资产管理费扣除
                  + " sum((case a.busytype when 'IP' then (case a.moneytype when 'BF' then a.unitcount else 0 end) else 0 end)*c.unitpricebuy) BF_IP," // 追加保费
                  +
                  " sum((case a.moneytype when 'SX' then a.unitcount else 0 end)*c.unitpricebuy) SX ," // 手续费
                  + " 0," // 退保费用
                  + " sum((case a.moneytype when 'SP' then a.unitcount else 0 end)*c.unitpricebuy) SP_NULL," // 持续奖金买入 --luzhe--20071126
                  // + " sum((case moneytype when 'GL' then (case when
                  // shouldvaluedate = valuedate then unitcount else 0 end)
                  // else 0 end)) , " //扣除合计
                  // + " sum((case moneytype when 'GL' then (case when
                  // shouldvaluedate <> valuedate then unitcount else 0 end)
                  // else 0 end)) , " //签报纠错（投资单位调整数）
                  + " (case when sum(a.unitcount*c.unitpricebuy) is not null then sum(a.unitcount*c.unitpricebuy)  else 0 end), " // 净流量
                  + " (case when sum(a.UnitCount) is not null then sum(a.UnitCount)  else 0 end), " // 换算净单位数
                  + " 0 ," // 核对差额
                  // + " sum((case busytype when 'EB' then (case moneytype
                  // when 'GL' then 0 else unitcount end) else 0 end)) EB," //
                  // //签报纠错（差错回退）
                  + " 0"
                  + " from  LCInsureAcctrace a,loaccunitprice c"
                  + " where a.insuaccno=c.insuaccno and a.shouldvaluedate=c.startdate "
                  + " and ( busytype<>'EB' or busytype is null) and a.state <> 0 and a.insuaccno = '"
                  + "?mInsuAccNo?" + "' "
                  + " and a.valuedate = to_date('" + "?mStartdate?" + "','yyyy-mm-dd') "
                  + " and a.riskcode= '" + "?riskcode?" + "' "
                  + " group by substr(a.managecom,1,4)";
            SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
            sqlbv1.sql(sql);
            sqlbv1.put("mInsuAccNo", mInsuAccNo);
            sqlbv1.put("mStartdate", mStartdate);
            sqlbv1.put("riskcode", riskSSRS.GetText(k, 1));

            tSSRS = tExeSQL.execSQL(sqlbv1);
            if(tExeSQL.mErrors.needDealError())
            {
                CError.buildErr(this, "数据查询失败!");
                return false;
            }
            if(tSSRS == null || tSSRS.getMaxRow() == 0)
            {
                logger.debug("该险种无需要打印信息");
                continue;
            }

            for(int i = 1; i <= tSSRS.getMaxRow(); i++)
            {
                String[] strArr = new String[20];
                strArr[0] = tSSRS.GetText(i, 1); // 分公司
                strArr[1] = tSSRS.GetText(i, 2); // 新契约买入
                strArr[2] = tSSRS.GetText(i, 3); // 续期保费买入
                strArr[3] = tSSRS.GetText(i, 4); // 初始费用
                strArr[4] = tSSRS.GetText(i, 5); // 买卖差价

                strArr[5] = tSSRS.GetText(i, 6); // 犹豫期退费赎回
                strArr[6] = tSSRS.GetText(i, 7); // 部分领取赎回
                strArr[7] = tSSRS.GetText(i, 8); // 退保赎回
                strArr[8] = tSSRS.GetText(i, 9); // 理赔赎回
                strArr[9] = tSSRS.GetText(i, 10); // 满期赎回

                strArr[10] = tSSRS.GetText(i, 11); // 风险保费扣除
                strArr[11] = tSSRS.GetText(i, 12); // 保单管理费扣除
                strArr[12] = tSSRS.GetText(i, 13); // 追加保费
                strArr[13] = tSSRS.GetText(i, 14); // 手续费
                strArr[14] = tSSRS.GetText(i, 15); // 退保费用

                strArr[17] = tSSRS.GetText(i, 16); //

                strArr[15] = tSSRS.GetText(i, 17); // 净现金流
                strArr[16] = tSSRS.GetText(i, 18); // 换算净单位数
                String HTSql =
                        "select (case when sum(a.unitcount*c.unitpricebuy) is not null then sum(a.unitcount*c.unitpricebuy)  else 0 end),(case when sum(a.unitcount) is not null then sum(a.unitcount)  else 0 end) from LCInsureAcctrace a, loaccunitprice c,LCInsureAcctrace g where 1=1 "
                        + " and a.insuaccno = c.insuaccno and g.serialno=a.accalterno and g.insuaccno=a.insuaccno and c.startdate=g.valuedate and a.riskcode=g.riskcode and g.busytype<>'EB' and a.busytype = 'EB' and a.accaltertype<>'7' and a.state <> 0 and a.insuaccno = '"
                        + "?mInsuAccNo?"
                        + "' and a.valuedate = '"+"?mStartdate?"+"' and a.managecom like concat('"+ "?managecom?" +"','%')";
                SQLwithBindVariables tsqlbv1 = new SQLwithBindVariables();
                tsqlbv1.sql(HTSql);
                tsqlbv1.put("mInsuAccNo", mInsuAccNo);   
                tsqlbv1.put("mStartdate", mStartdate); 
                tsqlbv1.put("managecom", strArr[0]); 

                ExeSQL HtExeSQL = new ExeSQL();
                SSRS HtSSRS = new SSRS();
                HtSSRS = HtExeSQL.execSQL(tsqlbv1);

                strArr[18] = HtSSRS.GetText(1, 1); // 签报纠错（差错回退）

                double Ht = Double.parseDouble(strArr[18]);
                double JXJL = Double.parseDouble(strArr[15]);
                JXJL = JXJL + Ht;
                strArr[15] = String.valueOf(JXJL);

                Ht = Double.parseDouble(HtSSRS.GetText(1, 2));
                double JDWS = Double.parseDouble(strArr[16]);
                JDWS = JDWS + Ht;
                strArr[16] = String.valueOf(JDWS);
                strArr[19] = riskSSRS.GetText(k, 1); // 险种代码

                try
                {
                    /*
                     * //初始费用 sql="select nvl(sum(fee),0) from
                     * LCInsureAccfeetrace " +" where insuaccno='"+mInsuAccNo+"'
                     * and riskcode='"+riskSSRS.GetText(k,1)+"' " +" and paydate<=date'"+mStartdate
                     * +"' and paydate>(select max(startdate) from
                     * loaccunitprice where startdate<date'"+mStartdate +"')
                     * and substr(managecom,1,4)='"+tSSRS.GetText(i, 1)+"' " +"
                     * and feecode in (select feecode from lmriskfee where
                     * insuaccno='"+mInsuAccNo+"' " +" and feeitemtype='01' and
                     * feetakeplace='01')"; SSRS ttSSRS = tExeSQL.execSQL(sql);
                     * if (ttSSRS != null && ttSSRS.getMaxRow()>0) {
                     * if(ttSSRS.GetText(1, 1).equals("0")){ strArr[3] = "0";
                     * }else{ strArr[3] = "-"+ttSSRS.GetText(1, 1); //初始费用 }
                     * }else{ strArr[3] = "0"; }
                     * strArr[1]=Double.toString(Double.parseDouble(strArr[1])-Double.parseDouble(strArr[3]));
                     * logger.debug(strArr[3]); //买卖差价 strArr[4] =
                     * Double.toString((Double.parseDouble(strArr[1])
                     * +Double.parseDouble(strArr[2])
                     * -Double.parseDouble(strArr[3])) changePrice);
                     */
                    /*
                     * //资产管理费扣除 chenliang add at 2007-08-30 sql="select
                     * AccAsManageFee from LOAccUnitPrice " +" where
                     * insuaccno='"+mInsuAccNo+"' " +" and
                     * riskcode='"+riskSSRS.GetText(k,1)+"' " +" and Startdate =
                     * date'" + mStartdate + "' "; ttSSRS =
                     * tExeSQL.execSQL(sql); if (ttSSRS != null &&
                     * ttSSRS.getMaxRow() > 0) { strArr[12] =
                     * mBqNameFun.getRound(ttSSRS.GetText(1, 1),"0.00");
                     * //资产管理费扣除 }else{ strArr[12] = "0"; }
                     */
                    /*
                     * //签报纠错投资单位调整数 sql = " select
                     * nvl(sum(a.money)-sum(b.money),0) from LCInsureAccTrace a,
                     * LCInsureAccTrace b" + " where a.state='1' and
                     * a.insuaccno= '" + mInsuAccNo + "' " + " and
                     * substr(a.managecom,1,4)= '" + tSSRS.GetText(i, 1) + "'
                     * and a.valuedate=date'" + mStartdate + "' " + " and
                     * a.BusyType ='PG' and a.ShouldValueDate!=a.valuedate " + "
                     * and b.state='1' and b.insuaccno= '" + mInsuAccNo + "' " + "
                     * and substr(b.managecom,1,4)= '" + tSSRS.GetText(i, 1) + "'
                     * and b.valuedate=date'" + mStartdate + "' " + " and
                     * b.BusyType ='EB' and b.ShouldValueDate!=b.valuedate " + "
                     * and b.ShouldValueDate=a.ShouldValueDate"; // 投资单位调整数
                     * ttSSRS = tExeSQL.execSQL(sql); if (ttSSRS != null &&
                     * ttSSRS.getMaxRow() > 0) { String changeNum =
                     * ttSSRS.GetText(1, 1); //投资调整金额 }
                     */
                    /*
                     * // 净现金流 strArr[15] =
                     * Double.toString(Double.parseDouble(strArr[1]) +
                     * Double.parseDouble(strArr[2]) +
                     * Double.parseDouble(strArr[3]) +
                     * Double.parseDouble(strArr[4]) +
                     * Double.parseDouble(strArr[5]) +
                     * Double.parseDouble(strArr[6]) +
                     * Double.parseDouble(strArr[7]) +
                     * Double.parseDouble(strArr[8]) +
                     * Double.parseDouble(strArr[9]) +
                     * Double.parseDouble(strArr[10]) +
                     * Double.parseDouble(strArr[11]) +
                     * Double.parseDouble(strArr[12]) +
                     * Double.parseDouble(strArr[13]) +
                     * Double.parseDouble(strArr[14]));
                     */
                    logger.debug("--净现金流" + strArr[15]);

                    /*
                     * // 换算净单位 strArr[16] = Double.toString((Double
                     * .parseDouble(strArr[15])) /
                     * (Double.parseDouble(payPrice)));
                     */

                    // 合计
                    for(int r = 0; r < 18; r++) // 第0行不合计
                    {
                        if(strSum[r] == null || strSum[r].equals(""))
                        {
                            strSum[r] = "0";
                        }
                        strSum[r] = Double.toString(Double
                                .parseDouble(strSum[r])
                                + Double.parseDouble(strArr[r + 1]));
                    }

                    strSum[18] = riskSSRS.GetText(k, 1);
                    strSum[19] = "产品" + k + "合计";

                    // 以6位小数计算完之后再以4位显示
                    strArr[1] = mBqNameFun.getRound(strArr[1], "0.00");
                    strArr[2] = mBqNameFun.getRound(strArr[2], "0.00");
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
                    strArr[16] = mBqNameFun.getRound(strArr[16], "0.0000");

                    strArr[17] = mBqNameFun.getRound(strArr[17], "0.00");
                    strArr[18] = mBqNameFun.getRound(strArr[18], "0.00");

                    tListTable.add(strArr); // 插入分项
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    CError.buildErr(this, "数据统计失败!");
                    return false;
                }
            }

            // 总计
            for(int h = 0; h < 18; h++)
            {
                if(tZSum[h] == null || tZSum[h].equals(""))
                {
                    tZSum[h] = "0";
                }
                tZSum[h] = Double.toString(Double.parseDouble(tZSum[h])
                                           + Double.parseDouble(strSum[h]));
            }
            // 计算之后截位
            strSum[0] = mBqNameFun.getRound(strSum[0], "0.00");
            strSum[1] = mBqNameFun.getRound(strSum[1], "0.00");
            strSum[2] = mBqNameFun.getRound(strSum[2], "0.00");
            strSum[3] = mBqNameFun.getRound(strSum[3], "0.00");
            strSum[4] = mBqNameFun.getRound(strSum[4], "0.00");
            strSum[5] = mBqNameFun.getRound(strSum[5], "0.00");
            strSum[6] = mBqNameFun.getRound(strSum[6], "0.00");
            strSum[7] = mBqNameFun.getRound(strSum[7], "0.00");
            strSum[8] = mBqNameFun.getRound(strSum[8], "0.00");
            strSum[9] = mBqNameFun.getRound(strSum[9], "0.00");
            strSum[10] = mBqNameFun.getRound(strSum[10], "0.00");
            strSum[11] = mBqNameFun.getRound(strSum[11], "0.00");
            strSum[12] = mBqNameFun.getRound(strSum[12], "0.00");
            strSum[13] = mBqNameFun.getRound(strSum[13], "0.00");
            strSum[14] = mBqNameFun.getRound(strSum[14], "0.00");
            strSum[15] = mBqNameFun.getRound(strSum[15], "0.0000");

            strSum[16] = mBqNameFun.getRound(strSum[16], "0.00");
            strSum[17] = mBqNameFun.getRound(strSum[17], "0.00");
            sumListTable.add(strSum); // 插入合计数

        }
        //计算后截位
        for(int h = 0; h < 18; h++)
        {
            if(h != 15)
            {
            	if(tZSum[h] != null){
            		tZSum[h] = mBqNameFun.getRound(tZSum[h], "0.00");
            	}
            }
            else
            {
            	if(tZSum[h] != null){
            		tZSum[h] = mBqNameFun.getRound(tZSum[h], "0.0000");
            	}
            }
        }
        zSumListTable.add(tZSum); // 插入总计数
        tXmlExport.addTextTag(tTextTag);
        tXmlExport.addListTable(tListTable, tTitle);
        tXmlExport.addListTable(sumListTable, tTitleSum);
        tXmlExport.addListTable(zSumListTable, tTitleZSum);

        // 垃圾处理
        tTextTag = null;
        tExeSQL = null;
        /*
         * //chenliang add linshi
         *
         * ListTable tListTable = new ListTable(); //分项记录
         * tListTable.setName("WG"); String[] tTitle = new String[18]; for (int
         * i = 0; i < 18; i++) { tTitle[i] = "Title" + i; }
         *
         * ListTable sumListTable = new ListTable(); //合计记录
         * sumListTable.setName("Sum"); String[] tTitleSum = new String[18]; for
         * (int i = 0; i < 18; i++) { tTitleSum[i] = "TitleSum" + i; }
         *
         * ListTable zSumListTable = new ListTable(); //合计记录
         * zSumListTable.setName("ZSum"); String[] tzTitleSum = new String[18];
         * for (int i = 0; i < 18; i++) { tzTitleSum[i] = "tzTitleSum" + i; }
         * String arr[]=new String[18]; for(int i=0;i<arr.length;i++){
         * arr[i]="0"; } tListTable.add(arr); sumListTable.add(arr);
         * zSumListTable.add(arr);
         *
         * tXmlExport.addTextTag(tTextTag); tXmlExport.addListTable(tListTable,
         * tTitle); tXmlExport.addListTable(sumListTable, tTitleSum); //
         * tXmlExport.addListTable(zSumListTable, tzTitleSum); //end chenliang
         * add
         */
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
        tLOReportManagerSchema.setReportType("1"); // 报表类型
        tLOReportManagerSchema.setReportCode("tl_001"); // 报表编码
        tLOReportManagerSchema.setPrtFlag("0");
        tLOReportManagerSchema.setPrtTimes(0);
        tLOReportManagerSchema.setFilters("86"); // 统计条件
        tLOReportManagerSchema.setCreateManageCom("86"); // 可能需要修改，定义成变量
        tLOReportManagerSchema.setManageCom("86");
        tLOReportManagerSchema.setTemplate(VTS_NAME); // 模板
        tLOReportManagerSchema.setOperator("001");
        tLOReportManagerSchema.setMakeDate(CurrentDate);
        tLOReportManagerSchema.setMakeTime(CurrentTime);
        tLOReportManagerSchema.setModifyDate(CurrentDate);
        tLOReportManagerSchema.setModifyTime(CurrentTime);
        tLOReportManagerSchema.setReportInfo(tXmlExport.getInputStream());
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
        if(!tPubSubmit.submitData(pVData, "PRINT"))
        {
            logger.debug("信息提交失败！");
            return false;
        }

        return true;
    } // function saveoutputData end

    public CErrors getErrors()
    {
        return mErrors;
    } // function getErrors end

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
        tTransferData.setNameAndValue("InsuAccNo", "PUL04");
        tTransferData.setNameAndValue("ValueDate", "2007-11-30");

        LOAccUnitPriceSchema tLOAccUnitPriceSchema = new LOAccUnitPriceSchema();
//        tLOAccUnitPriceSchema.setRiskCode("RPUL");
        tLOAccUnitPriceSchema.setInsuAccNo("PUL04");
        tLOAccUnitPriceSchema.setStartDate("2007-11-30");

        tVData.add(tGlobalInput);
        tVData.add(tLOAccUnitPriceSchema);
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

    } // function main end

} // class end

