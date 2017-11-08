package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeTraceSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LMRiskFeeSchema;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCPremToAccSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LMRiskFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccClassFeeDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCPerInvestPlanDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremToAccDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskFeeDB;
import com.sinosoft.lis.vdb.LMRiskAppDBSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeTraceSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeSchema;
import com.sinosoft.lis.db.LCInsureAccFeeDB;
import com.sinosoft.lis.db.LMCalModeDB;
import com.sinosoft.lis.vschema.LMCalModeSet;
import com.sinosoft.lis.vdb.LMCalModeDBSet;
import com.sinosoft.lis.schema.LMCalModeSchema;
import com.sinosoft.lis.acc.PubInsuAccFun;
import com.sinosoft.lis.schema.LOAccUnitPriceSchema;

/**
 * <p>Title: </p>
 *    续期核销帐户处理
 * <p>Description: </p>
 *    投连产品及期缴万能的续期帐户处理
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: Sinosoft</p>
 *
 * @author gaoht
 * @version 1.0
 */
public class RnAccountDeal
{
private static Logger logger = Logger.getLogger(RnAccountDeal.class);


    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    private GlobalInput tGI = new GlobalInput();
    /*应收*/
    private LJAPayPersonSet mLJAPayPersonSet = new LJAPayPersonSet();

    /*管理费信息表*/
    private LCInsureAccClassFeeSet mLCInsureAccClassFeeSet = new
            LCInsureAccClassFeeSet();
    private LCInsureAccFeeTraceSet mLCInsureAccFeeTraceSet = new
            LCInsureAccFeeTraceSet();
    private LCInsureAccFeeSet mLCInsureAccFeeSet = new LCInsureAccFeeSet();
    /*帐户信息表*/
    private LCInsureAccClassSet mLCInsureAccClassSet = new LCInsureAccClassSet();
    private LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();
    private LCInsureAccSet mLCInsureAccSet = new LCInsureAccSet();

    private String PayNo = "";
    private String RiskType = "";
    private String mBusyType = "RN";
    private boolean mBQFlag = false;

    private MMap mMap = new MMap();


    public RnAccountDeal()
    {

    }

    public void SetBQFlag()
    {
        mBQFlag = true;
    }

    public void SetBusyType(String cBusyType)
    {
        mBusyType = cBusyType;
    }

    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug("=============================续期帐户处理开始 =============================================");
        if (!getInputData(cInputData))
        {
            return false;
        }

        if (!dealData())
        {
            return false;
        }

        if (!prepareOutputData())
        {
            return false;
        }
        logger.debug("=============================续期帐户处理结束 =============================================");
        return true;
    }


    public boolean getInputData(VData mInputData)
    {

        tGI.setSchema((GlobalInput) mInputData.getObjectByObjectName(
                "GlobalInput", 0));

        mLJAPayPersonSet = (LJAPayPersonSet) mInputData.getObjectByObjectName(
                "LJAPayPersonSet",
                0);

        if (tGI == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "RnAccountDeal";
            tError.functionName = "getInputData";
            tError.errorMessage = "没有得到足够的数据，请您确认!";
            this.mErrors.addOneError(tError);
            return false;
        }

        if (mLJAPayPersonSet.size() == 0)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "RnAccountDeal";
            tError.functionName = "getInputData";
            tError.errorMessage = "获取必要保单信息失败";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;

    }

    private boolean dealData()
    {

        for (int i = 1; i <= mLJAPayPersonSet.size(); i++)
        {
            LJAPayPersonSchema mLJAPayPersonSchema = new LJAPayPersonSchema();

            mLJAPayPersonSchema = mLJAPayPersonSet.get(i);

            String PolNo = mLJAPayPersonSchema.getPolNo();
            String DutyCode = mLJAPayPersonSchema.getDutyCode();
            String PayPlanCode = mLJAPayPersonSchema.getPayPlanCode();
            String Riskcode = mLJAPayPersonSchema.getRiskCode();

            if (!VerifyAccountDeal(PolNo, DutyCode, PayPlanCode))
            {
                logger.debug("没有帐户需要处理");
                CError.buildErr(this, "没有帐户需要处理");
                continue;
            }

            if (CheckRiskType(Riskcode).equals("TL"))
            {

                if (!TLAccountDeal(mLJAPayPersonSchema))
                {
                    return false;

                }

            }

            else if (CheckRiskType(Riskcode).equals("WN"))
            {

                if (!WNAccountDeal(mLJAPayPersonSchema))
                {
                    return false;

                }

            }
            else
            {
                logger.debug(Riskcode + "该险种不用处理");
                continue;
            }
        }
        return true;
    }

    /*
     * 万能险帐户处理
     * */
    private boolean WNAccountDeal(LJAPayPersonSchema tLJAPayPersonSchema)
    {

        /*--险种--*/
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(tLJAPayPersonSchema.getPolNo());
        if (!tLCPolDB.getInfo())
        {
            CError.buildErr(this, "查询险种信息失败");
            return false;
        }
        LCPolSchema mLCPolSchema = tLCPolDB.getSchema();

        /*--进入帐户---*/
        LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
        tLCInsureAccClassDB.setPayPlanCode(tLJAPayPersonSchema.getPayPlanCode());
        tLCInsureAccClassDB.setPolNo(tLJAPayPersonSchema.getPolNo());
        PayNo = tLJAPayPersonSchema.getPayNo();

        LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
        tLCInsureAccClassSet = tLCInsureAccClassDB.query();

        if (tLCInsureAccClassSet.size() <= 0)
        {
            CError.buildErr(this, "查询保险帐户信息分类表失败");
            return false;
        }

        for (int j = 1; j <= tLCInsureAccClassSet.size(); j++)
        {

            double SumDuePrem = 0.00; //保费总额
            double SumDueFee = 0.00; //初始费用总额

            double SumPrem = 0.00;
            int mainpolyear;

            LCInsureAccClassSchema tLCInsureAccClassSchema = new
                    LCInsureAccClassSchema();
            tLCInsureAccClassSchema = tLCInsureAccClassSet.get(j);

            String sqlrisk = "select * from lmriskfee where insuaccno='?insuaccno?' and payplancode='?payplancode?' and FeeTakePlace ='01'";
            SQLwithBindVariables sqlbv=new SQLwithBindVariables();
            sqlbv.sql(sqlrisk);
            sqlbv.put("insuaccno", tLCInsureAccClassSchema.getInsuAccNo());
            sqlbv.put("payplancode", tLCInsureAccClassSchema.getPayPlanCode());
            LMRiskFeeDB pLMRiskFeeDB = new LMRiskFeeDB();
            LMRiskFeeSet pLMRiskFeeSet = new LMRiskFeeSet();
            pLMRiskFeeSet = pLMRiskFeeDB.executeQuery(sqlbv);
            LMRiskFeeSchema pLMRiskFeeSchema = pLMRiskFeeSet.get(1);

            /*初始管理费计算参数*/
            SumPrem = tLJAPayPersonSchema.getSumActuPayMoney();
            mainpolyear = tLJAPayPersonSchema.getMainPolYear();

            /*初始费用*/
            SumDueFee = calRiskFee(pLMRiskFeeSchema, mLCPolSchema, SumPrem,
                                   mainpolyear);

            /*保费*/
            SumDuePrem = tLJAPayPersonSchema.getSumActuPayMoney() - SumDueFee;

            /*创建保险帐户轨迹记表*/
            mLCInsureAccTraceSet.add(createInsuAccTrace(tLCInsureAccClassSchema,
                    SumDuePrem, "BF"));

            updateLCInsuerAccClass(tLCInsureAccClassSchema, SumDuePrem);

            updateLCInsuerAcc(tLCInsureAccClassSchema, SumDuePrem);

            /*管理费处理*/
            LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new
                    LCInsureAccClassFeeSet();
            LCInsureAccClassFeeDB tLCInsureAccClassFeeDB = new
                    LCInsureAccClassFeeDB();
            tLCInsureAccClassFeeDB.setPolNo(tLJAPayPersonSchema.getPolNo());
            tLCInsureAccClassFeeDB.setPayPlanCode(tLJAPayPersonSchema.
                                                  getPayPlanCode());
            tLCInsureAccClassFeeDB.setFeeCode(pLMRiskFeeSchema.getFeeCode());
            tLCInsureAccClassFeeDB.setInsuAccNo(tLCInsureAccClassSchema.
                                                getInsuAccNo());
            tLCInsureAccClassFeeSet = tLCInsureAccClassFeeDB.query();
            LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema =
                    tLCInsureAccClassFeeSet.get(1);

            /*创建保险帐户管理费轨迹记表*/
            mLCInsureAccFeeTraceSet.add(createInsuAccFeeTrace(
                    tLCInsureAccClassFeeSchema,
                    SumDueFee, "GL", tLCInsureAccClassFeeSchema.getFeeCode()));

            updateLCInsuerAccClassFee(tLCInsureAccClassFeeSchema, SumDueFee);

            updateLCInsuerAccFee(tLCInsureAccClassFeeSchema, SumDueFee);

        }

        return true;

    }


    /***
     *处理投连多帐户
     *
     ****/

    private boolean TLAccountDeal(LJAPayPersonSchema tLJAPayPersonSchema)
    {

        /*--险种--*/
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(tLJAPayPersonSchema.getPolNo());
        if (!tLCPolDB.getInfo())
        {
            CError.buildErr(this, "查询险种信息失败");
            return false;
        }
        LCPolSchema mLCPolSchema = tLCPolDB.getSchema();
        PayNo = tLJAPayPersonSchema.getPayNo();

        /*--进入帐户---*/
        LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
        tLCInsureAccClassDB.setPayPlanCode(tLJAPayPersonSchema.getPayPlanCode());
        tLCInsureAccClassDB.setPolNo(tLJAPayPersonSchema.getPolNo());
        LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
        tLCInsureAccClassSet = tLCInsureAccClassDB.query();

        if (tLCInsureAccClassSet.size() <= 0)
        {
            CError.buildErr(this, "查询保险帐户信息分类表失败");
            return false;
        }

        for (int j = 1; j <= tLCInsureAccClassSet.size(); j++)
        {

            double SumDuePrem = 0.00; //保费总额
            double SumDueFee = 0.00; //初始费用总额
            double dRiskFee = 0.00; //针对帐户管理费部分
            double dRiskPrem = 0.00; //保费部分
            double SumPrem = 0.00;
            int mainpolyear;

            LCInsureAccClassSchema tLCInsureAccClassSchema = new
                    LCInsureAccClassSchema();
            tLCInsureAccClassSchema = tLCInsureAccClassSet.get(j);

            String sqlrisk = "select * from lmriskfee where insuaccno='?insuaccno?' and payplancode='?payplancode?' and FeeTakePlace ='01'";
            SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
            sqlbv2.sql(sqlrisk);
            sqlbv2.put("insuaccno", tLCInsureAccClassSchema.getInsuAccNo());
            sqlbv2.put("payplancode", tLCInsureAccClassSchema.getPayPlanCode());
            LMRiskFeeDB pLMRiskFeeDB = new LMRiskFeeDB();
            LMRiskFeeSet pLMRiskFeeSet = new LMRiskFeeSet();
            pLMRiskFeeSet = pLMRiskFeeDB.executeQuery(sqlbv2);
            LMRiskFeeSchema pLMRiskFeeSchema = pLMRiskFeeSet.get(1);

            /*初始管理费计算参数*/
            SumPrem = tLJAPayPersonSchema.getSumActuPayMoney();
            mainpolyear = tLJAPayPersonSchema.getMainPolYear();

            /*初始费用*/
            SumDueFee = calRiskFee(pLMRiskFeeSchema, mLCPolSchema, SumPrem,
                                   mainpolyear);

            /*保费*/
            SumDuePrem = tLJAPayPersonSchema.getSumActuPayMoney() - SumDueFee;

            dRiskPrem = SumDuePrem *
                        getInvestRate(mLCPolSchema,
                                      tLCInsureAccClassSchema.getInsuAccNo(),
                                      tLCInsureAccClassSchema.getPayPlanCode());

            /*创建保险帐户轨迹记表*/
            mLCInsureAccTraceSet.add(createInsuAccTrace(tLCInsureAccClassSchema,
                    dRiskPrem, "BF"));

            updateLCInsuerAccClass(tLCInsureAccClassSchema, dRiskPrem);

            updateLCInsuerAcc(tLCInsureAccClassSchema, dRiskPrem);

            //持续奖金处理
            double prize = dealPrize(tLJAPayPersonSchema);
            if (prize > 0)
            {
                double insuAccPrize = prize * getInvestRate(mLCPolSchema,
                        tLCInsureAccClassSchema.
                        getInsuAccNo(),
                        tLCInsureAccClassSchema.
                        getPayPlanCode());
                LCInsureAccTraceSchema pLCInsureAccTraceSchema = new
                        LCInsureAccTraceSchema();
                pLCInsureAccTraceSchema = createInsuAccTrace(
                        tLCInsureAccClassSchema,
                        insuAccPrize, "SP"); //standing prize持续奖金
                pLCInsureAccTraceSchema.setBusyType("");
                pLCInsureAccTraceSchema.setAccAlterType("");
                pLCInsureAccTraceSchema.setAccAlterNo("");

                mLCInsureAccTraceSet.add(pLCInsureAccTraceSchema); //standing prize持续奖金
            }

            /*管理费处理*/
            LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new
                    LCInsureAccClassFeeSet();
            LCInsureAccClassFeeDB tLCInsureAccClassFeeDB = new
                    LCInsureAccClassFeeDB();
            tLCInsureAccClassFeeDB.setPolNo(tLJAPayPersonSchema.getPolNo());
            tLCInsureAccClassFeeDB.setPayPlanCode(tLJAPayPersonSchema.
                                                  getPayPlanCode());
            tLCInsureAccClassFeeDB.setFeeCode(pLMRiskFeeSchema.getFeeCode());
            tLCInsureAccClassFeeDB.setInsuAccNo(tLCInsureAccClassSchema.
                                                getInsuAccNo());
            tLCInsureAccClassFeeSet = tLCInsureAccClassFeeDB.query();
            LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema =
                    tLCInsureAccClassFeeSet.get(1);

            dRiskFee = SumDueFee * getInvestRate(mLCPolSchema,
                                                 tLCInsureAccClassFeeSchema.
                                                 getInsuAccNo(),
                                                 tLCInsureAccClassFeeSchema.
                                                 getPayPlanCode());

            /*创建保险帐户管理费轨迹记表*/
            mLCInsureAccFeeTraceSet.add(createInsuAccFeeTrace(
                    tLCInsureAccClassFeeSchema,
                    dRiskFee, "GL", tLCInsureAccClassFeeSchema.getFeeCode()));

            updateLCInsuerAccClassFee(tLCInsureAccClassFeeSchema, dRiskFee);

            updateLCInsuerAccFee(tLCInsureAccClassFeeSchema, dRiskFee);

        }

        return true;

    }

    /**
     * 持续奖金判断复用LMRiskFee表
     * LMRiskFee字段FeeItemType="09"表示持续奖金
     * **/
    private double dealPrize(LJAPayPersonSchema pLJAPayPersonSchema)
    {
        double prize = 0.00;
        LMRiskFeeSet mLMRiskFeeSet = new LMRiskFeeSet();
        LMRiskFeeDB mLMRiskFeeDB = new LMRiskFeeDB();
        mLMRiskFeeDB.setFeeItemType("09");
        mLMRiskFeeDB.setPayPlanCode(pLJAPayPersonSchema.getPayPlanCode());
        mLMRiskFeeDB.setInsuAccNo("000000");
        mLMRiskFeeSet = mLMRiskFeeDB.query();
        if (mLMRiskFeeSet != null && mLMRiskFeeSet.size() > 0)
        {
            LMRiskFeeSchema mLMRiskFeeSchema = mLMRiskFeeSet.get(1);
            LMCalModeDB mLMCalModeDB = new LMCalModeDB();
            mLMCalModeDB.setCalCode(mLMRiskFeeSchema.getFeeCalCode());

            //计算单位数以及金额
            if (mLMRiskFeeSchema.getFeeCalModeType().equals("0"))
            {
                //取固定值
                if (mLMRiskFeeSchema.getFeeCalMode().equals(
                        "01"))
                {
                    //固定值内扣
                    prize = mLMRiskFeeSchema.
                            getFeeValue();
                }
                else if (mLMRiskFeeSchema.getFeeCalMode().
                         equals(
                                 "02"))
                {
                    //固定比例内扣
                }
                else
                {
                    //默认情况
                    prize = mLMRiskFeeSchema.
                            getFeeValue();
                }
            }
            else if (mLMRiskFeeSchema.getFeeCalModeType().
                     equals(
                             "1"))
            {
                //通过sql计算得到h
                prize = calPrize(
                        mLMRiskFeeSchema.getFeeCalCode(), pLJAPayPersonSchema);
            }
        }

        return prize;
    }

    /**计算账户金额
     * aFeeCalCode管理费编码
     * aLCInsureAccClassSchema账户信息
     * aLOAccUnitPriceSchema投资账户信息
     **/
    public static double calPrize(String aFeeCalCode,
                                  LJAPayPersonSchema pLJAPayPersonSchema)
    {
        //通过sql计算得到
        PubFun tPubFun = new PubFun();
        ExeSQL tExeSQL = new ExeSQL();
        Calculator tCalculator = new Calculator();
        tCalculator.setCalCode(aFeeCalCode);
        //准备计算要素
        //扣除管理费之前的投资单位
        tCalculator.addBasicFactor("PayMoney",
                                   String.valueOf(pLJAPayPersonSchema.
                                                  getSumActuPayMoney()));
        tCalculator.addBasicFactor("PolNo", pLJAPayPersonSchema.getPolNo());
        tCalculator.addBasicFactor("MainPolYear",
                                   String.valueOf(pLJAPayPersonSchema.
                                                  getMainPolYear()));

        String tPrize = tCalculator.calculate();
        logger.debug("tPrize:" +
                           tPrize);

        return Double.parseDouble(tPrize);
    }

    /*
     * 计算初始费用
     */
    private double calRiskFee(LMRiskFeeSchema pLMRiskFeeSchema,
                              LCPolSchema mLCPolSchema,
                              double dSumPrem, int dInterval)
    {
        double dRiskFee = 0.0;
        if (pLMRiskFeeSchema.getFeeCalModeType().equals("0")) //0-直接取值
        {
            if (pLMRiskFeeSchema.getFeeCalMode().equals("01")) //固定值内扣
            {
                dRiskFee = pLMRiskFeeSchema.getFeeValue();
            }
            else if (pLMRiskFeeSchema.getFeeCalMode().equals("02")) //固定比例内扣
            {
                dRiskFee = dSumPrem * pLMRiskFeeSchema.getFeeValue();
            }
            else
            {
                dRiskFee = pLMRiskFeeSchema.getFeeValue(); //默认情况
            }
        }
        else if (pLMRiskFeeSchema.getFeeCalModeType().equals("1")) //1-SQL算法描述
        {
            //准备计算要素
            Calculator tCalculator = new Calculator();
            tCalculator.setCalCode(pLMRiskFeeSchema.getFeeCalCode());
            //累计已交保费
            tCalculator.addBasicFactor("InsuredNo", mLCPolSchema.getInsuredNo());
            tCalculator.addBasicFactor("SumPrem", String.valueOf(dSumPrem));
            tCalculator.addBasicFactor("AccValue", String.valueOf(dSumPrem));
            //投连管理费计算要素
            tCalculator.addBasicFactor("PayIntv",
                                       String.valueOf(mLCPolSchema.getPayIntv()));
            tCalculator.addBasicFactor("Interval", String.valueOf(dInterval));
            String sCalResultValue = tCalculator.calculate();
            if (tCalculator.mErrors.needDealError())
            {
                CError.buildErr(this, "初始费用计算失败!");
                return -1;
            }

            try
            {
                dRiskFee = Double.parseDouble(sCalResultValue);
            }
            catch (Exception e)
            {
                CError.buildErr(this, "初始费计算结果错误!" +
                                "错误结果：" + sCalResultValue);
                return -1;
            }
        }

        return dRiskFee;
    }


    /* 校验该保费项是否含有帐户
     * @param String cPolNo
     * @param String cDutyCode
     * @param String cPayPlanCode
     */
    public boolean VerifyAccountDeal(String cPolNo, String cDutyCode,
                                     String cPayPlanCode)
    {
        boolean tFlag = false;

        LCPremToAccDB tLCPremToAccDB = new LCPremToAccDB();
        tLCPremToAccDB.setPolNo(cPolNo);
        tLCPremToAccDB.setDutyCode(cDutyCode);
        tLCPremToAccDB.setPayPlanCode(cPayPlanCode);
        LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet();
        tLCPremToAccSet = tLCPremToAccDB.query();
        if (tLCPremToAccSet.size() == 0)
        {
            CError.buildErr(this, "查询不到保费项表和客户帐户表的关联");
            return tFlag;
        }

        return true;
    }

    /* 校验该产品类型 投连Or万能
     * @param String cPolNo
     * @param String cDutyCode
     * @param String cPayPlanCode
     */
    public String CheckRiskType(String cRiskCode)
    {

        LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();

        mLMRiskAppDB.setRiskCode(cRiskCode);

        if (!mLMRiskAppDB.getInfo())
        {
            CError.buildErr(this, "查询险种信息失败");
            RiskType = "Unknow";
        }
        /*--投连--*/
        if (mLMRiskAppDB.getRiskType3().equals("3"))
        {
            RiskType = "TL";
        }
        /*--万能--*/
        else if (mLMRiskAppDB.getRiskType3().equals("4"))
        {
            RiskType = "WN";
        }

        else
        {
            RiskType = "other";

        }
        return RiskType;
    }

    /**
     *计算投资比例
     *
     */
    private double getInvestRate(LCPolSchema tLCPolSchema, String InsuAccNo,
                                 String PayPlanCode)
    {
        double tRate = 0.00;
        LCPerInvestPlanDB tLCPerInvestPlanDB = new
                                               LCPerInvestPlanDB();
        tLCPerInvestPlanDB.setPolNo(tLCPolSchema.getPolNo());
        tLCPerInvestPlanDB.setPayPlanCode(PayPlanCode);
        tLCPerInvestPlanDB.setInsuAccNo(InsuAccNo);

        if (tLCPerInvestPlanDB.getInfo())
        {
            if (tLCPerInvestPlanDB.getInputMode().equals("1")) //按照比例
                tRate = tLCPerInvestPlanDB.getInvestRate();
            else
            { //按照金额
                ExeSQL tExeSQL = new ExeSQL();
                SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
                sqlbv3.sql("select sum(InvestMoney) from LCPerInvestPlan where polno='?polno?' and payplancode='?PayPlanCode?'");
                sqlbv3.put("polno", tLCPerInvestPlanDB.getPolNo());
                sqlbv3.put("PayPlanCode", PayPlanCode);
                tRate = tLCPerInvestPlanDB.getInvestMoney() /
                        Double.
                        parseDouble(tExeSQL.getOneValue(sqlbv3));
            }
        }

        return tRate;
    }

    /*
     * 生成保费帐户轨迹表
     */
    public LCInsureAccTraceSchema createInsuAccTrace(LCInsureAccClassSchema
            tLCInsureAccClassSchema,
            double dMoney,
            String sMoneyType)
    {
        LCInsureAccTraceSchema mLCInsureAccTraceSchema = new
                LCInsureAccTraceSchema();
        Reflections tReflections = new Reflections();
        tReflections.transFields(mLCInsureAccTraceSchema,
                                 tLCInsureAccClassSchema);
        String tLimit = PubFun.getNoLimit(mLCInsureAccTraceSchema.getManageCom());
        String SerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
        mLCInsureAccTraceSchema.setSerialNo(SerialNo);
        mLCInsureAccTraceSchema.setMoneyType(sMoneyType);
        mLCInsureAccTraceSchema.setPayDate(CurrentDate);
        mLCInsureAccTraceSchema.setBusyType(mBusyType);
        mLCInsureAccTraceSchema.setAccAlterType("2");
        mLCInsureAccTraceSchema.setAccAlterNo(PayNo);
        mLCInsureAccTraceSchema.setOtherNo(PayNo);
        mLCInsureAccTraceSchema.setOtherType("2");
        mLCInsureAccTraceSchema.setState("0");
        mLCInsureAccTraceSchema.setMoney(dMoney);
        mLCInsureAccTraceSchema.setUnitCount(0);
        mLCInsureAccTraceSchema.setOperator(tGI.Operator);
        mLCInsureAccTraceSchema.setMakeDate(CurrentDate);
        mLCInsureAccTraceSchema.setMakeTime(CurrentTime);
        mLCInsureAccTraceSchema.setModifyDate(CurrentDate);
        mLCInsureAccTraceSchema.setModifyTime(CurrentTime);
        mLCInsureAccTraceSchema.setFeeCode("000000");
        return mLCInsureAccTraceSchema;
    }

    /*
     *  生成管理费轨迹表
     */
    private LCInsureAccFeeTraceSchema createInsuAccFeeTrace(
            LCInsureAccClassFeeSchema pLCInsureAccClassFeeSchema,
            double dMoney,
            String sMoneyType,
            String sFeeCode)
    {
        Reflections ref = new Reflections();
        //创建帐户轨迹记录
        LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema = new
                LCInsureAccFeeTraceSchema();
        ref.transFields(tLCInsureAccFeeTraceSchema, pLCInsureAccClassFeeSchema);
        String tLimit = PubFun.getNoLimit(pLCInsureAccClassFeeSchema.
                                          getManageCom());
        String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);

        tLCInsureAccFeeTraceSchema.setSerialNo(serNo);
        tLCInsureAccFeeTraceSchema.setState("1");
        tLCInsureAccFeeTraceSchema.setMoneyType(sMoneyType);
        tLCInsureAccFeeTraceSchema.setFee(dMoney);
        tLCInsureAccFeeTraceSchema.setPayDate(CurrentDate);
        tLCInsureAccFeeTraceSchema.setFeeCode(sFeeCode);
        tLCInsureAccFeeTraceSchema.setOtherType("2");
        tLCInsureAccFeeTraceSchema.setOtherNo(PayNo);
        tLCInsureAccFeeTraceSchema.setOperator(tGI.Operator);
        tLCInsureAccFeeTraceSchema.setMakeDate(CurrentDate);
        tLCInsureAccFeeTraceSchema.setMakeTime(CurrentTime);
        tLCInsureAccFeeTraceSchema.setModifyDate(CurrentDate);
        tLCInsureAccFeeTraceSchema.setModifyTime(CurrentTime);

        return tLCInsureAccFeeTraceSchema;
    }

    /* 更新管理费分类表
     * @param tClassSchema LCInsureAccClassSchema
     * @param manaFee double
     * @param tLCPremToAccSchema LCPremToAccSchema
     */
    private void updateLCInsuerAccClassFee(LCInsureAccClassFeeSchema
                                           tClassSchema,
                                           double inputMoney)
    {

        tClassSchema.setFee(tClassSchema.getFee() + inputMoney);
        tClassSchema.setModifyDate(CurrentDate);
        tClassSchema.setModifyTime(CurrentTime);

        mLCInsureAccClassFeeSet.add(tClassSchema);
    }

    /* 更新帐户分类表
     * @param tClassSchema LCInsureAccClassSchema
     * @param manaFee double
     * @param tLCPremToAccSchema LCPremToAccSchema
     */
    private void updateLCInsuerAccClass(LCInsureAccClassSchema tClassSchema,
                                        double inputMoney)
    {

        tClassSchema.setSumPay(tClassSchema.getSumPay() + inputMoney);
        tClassSchema.setModifyDate(CurrentDate);
        tClassSchema.setModifyTime(CurrentTime);

        mLCInsureAccClassSet.add(tClassSchema);
    }

    /* 更新帐户表
     * @param tClassSchema LCInsureAccSchema
     * @param prem double
     * @param tLCPremToAccSchema LCPremToAccSchema
     */
    private void updateLCInsuerAcc(LCInsureAccClassSchema tClassSchema,
                                   double inputMoney)
    {

        LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
        tLCInsureAccDB.setPolNo(tClassSchema.getPolNo());
        tLCInsureAccDB.setInsuAccNo(tClassSchema.getInsuAccNo());
        LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
        if (tLCInsureAccDB.getInfo())
        {
            tLCInsureAccSchema = tLCInsureAccDB.getSchema();
            tLCInsureAccSchema.setSumPay(tLCInsureAccSchema.getSumPay() +
                                         inputMoney);
            tLCInsureAccSchema.setModifyDate(CurrentDate);
            tLCInsureAccSchema.setModifyTime(CurrentTime);

        }

        mLCInsureAccSet.add(tLCInsureAccSchema);
    }

    /* 更新帐户管理表
     * @param tClassSchema LCInsureAccFeeSchema
     * @param prem double
     * @param tLCPremToAccSchema LCInsureAccFeeSchema
     */
    private void updateLCInsuerAccFee(LCInsureAccClassFeeSchema tClassSchema,
                                      double inputMoney)
    {

        LCInsureAccFeeDB tLCInsureAccFeeDB = new LCInsureAccFeeDB();
        tLCInsureAccFeeDB.setPolNo(tClassSchema.getPolNo());
        tLCInsureAccFeeDB.setInsuAccNo(tClassSchema.getInsuAccNo());
        LCInsureAccFeeSchema tLCInsureAccFeeSchema = new LCInsureAccFeeSchema();
        if (tLCInsureAccFeeDB.getInfo())
        {
            tLCInsureAccFeeSchema = tLCInsureAccFeeDB.getSchema();
            tLCInsureAccFeeSchema.setFee(tLCInsureAccFeeSchema.getFee() +
                                         inputMoney);
            tLCInsureAccFeeSchema.setModifyDate(CurrentDate);
            tLCInsureAccFeeSchema.setModifyTime(CurrentTime);
        }
        mLCInsureAccFeeSet.add(tLCInsureAccFeeSchema);
    }

    private boolean prepareOutputData()
    {
        mResult.clear();
        mResult.add(mLCInsureAccFeeTraceSet);
        mResult.add(mLCInsureAccTraceSet);
        mResult.add(mLCInsureAccClassFeeSet);
        mResult.add(mLCInsureAccFeeSet);
        mResult.add(mLCInsureAccClassSet);
        mResult.add(mLCInsureAccSet);
        return true;
    }


    public VData getResult()
    {
        return mResult;
    }

    public static void main(String[] args)
    {
        RnAccountDeal rnaccountdeal = new RnAccountDeal();
        TransferData tTransferData = new TransferData();
//        tTransferData.setNameAndValue("Contno", "851000000206"); //autorenew
//        LJAPayPersonSchema tLCContSchema = new LJAPayPersonSchema();
//        LJAPayPersonDB tLCContDB = new LJAPayPersonDB();
//        tLCContDB.setContNo("851000000206");
//        tLCContDB.getInfo();
//        tLCContSchema = tLCContDB.getSchema();
//
//        RnDealBL IndiDueFeePartBLF1 = new RnDealBL();
//        GlobalInput tGI = new GlobalInput();
//        tGI.ManageCom = "86";
//        tGI.Operator = "001";
//        VData tv = new VData();
//        tv.add(tLCContSchema);
//        tv.add(tTransferData);
//        tv.add(tGI);
//
//
//        if(rnaccountdeal.submitData(tv, ))
//        {
//        	logger.debug("succ");
//        }
//        else
//            logger.debug("fail");
    }
}
