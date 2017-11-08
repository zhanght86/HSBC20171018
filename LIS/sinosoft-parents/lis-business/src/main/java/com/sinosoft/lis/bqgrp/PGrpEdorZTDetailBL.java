package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.bq.BqCalBL;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保全退保金转公共帐户业务逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Tjj
 * @version 1.0
 */
public class PGrpEdorZTDetailBL {
private static Logger logger = Logger.getLogger(PGrpEdorZTDetailBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData;

    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    MMap map = new MMap();

    /** 数据操作字符串 */
    private TransferData mTransferData = new TransferData();
    private LPGrpEdorMainSchema mLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
    private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
    private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
    private LPPolSet mLPPolSet = new LPPolSet();
    private LPPolSchema mLPPolSchema = new LPPolSchema();
    private LPInsuredSet mLPInsuredSet = new LPInsuredSet();
    private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
    private LJSGetEndorseSchema mLJSGetEndorseSchema = new LJSGetEndorseSchema();
    private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
    private LPInsureAccTraceSet mLPInsureAccTraceSet = new LPInsureAccTraceSet();
    private LPInsureAccClassSet mLPInsureAccClassSet = new LPInsureAccClassSet();
    private LPInsureAccFeeTraceSet mLPInsureAccFeeTraceSet = new
            LPInsureAccFeeTraceSet();
    /** 全局数据 */
    private Reflections ref = new Reflections();
    private GlobalInput mGlobalInput = new GlobalInput();
    private String mCurrDate = PubFun.getCurrentDate();
    private String mCurrTime = PubFun.getCurrentTime();

    //标识是否将退保金转公共帐户
    private String mIfChecked = "";
    private String mOperator = "";
    private String mManageCom = "";

    //退保金额总和
    private double mZTMoney = 0.0;

    //险种退保金额
    double dPolZTMoney = 0.0;


    /**
     * Constructor
     **/
    public PGrpEdorZTDetailBL() {}


    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate) {
        mInputData = (VData) cInputData.clone();

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData()) {
            return false;
        }

        //数据校验操作(checkdata)
        if (!checkData()) {
            return false;
        }

        if (!dealData()) {
            return false;
        }
	if (!prepareData()) {
	    return false;
        }


        if (!prepareOutputData()) {
            return false;
        }

        PubSubmit tPubSubmit = new PubSubmit();
        if (tPubSubmit.submitData(mInputData, cOperate) == false) {
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            return false;
        }

        return true;
    }


    /**
     * dealData
     *
     * @return boolean
     */
    private boolean dealData() {
        //删除上次保存过的数据
        if (!delPData()) {
            return false;
        }
        logger.debug("=====delPData end=====\n");

        //保存需要退保的数据（保单，被保人，险种）到P表
        if (!savePData()) {
            return false;
        }
        logger.debug("=====savePData end=====\n");

        //计算退保金
        if (!calZTData()) {
            return false;
        }
        logger.debug("=====calZTData end=====\n");

        //更新批改项目状态信息
        if (!chgEdorState()) {
            return false;
        }
        logger.debug("=====chgEdorState end=====\n");

        return true;
    }


    /**
     * 计算退保金额
     * @return boolean
     */
    private boolean calZTData() {

        logger.debug(
                "=====This is PGrpEdorZTDetailBL->calZTData=====\n");

        mLPPolSet = new LPPolSet();
        LCPolDB tLCPolDB = new LCPolDB();
        LCPolSet tLCPolSet = new LCPolSet();
        tLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
        tLCPolDB.setAppFlag("1");
//	tLCPolDB.setPolState("1");//新增保单状态标志
        //从前台传入的报单号不为空(为空时：000000)
        if (!mLPEdorItemSchema.getPolNo().equals("000000")) {
            tLCPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
        }
        LCPolSchema tLCPolSchema;
        tLCPolSet = tLCPolDB.query();
        for (int i = 1; i <= tLCPolSet.size(); i++) {
            mLPPolSchema = new LPPolSchema();
            tLCPolSchema = new LCPolSchema();
            tLCPolSchema.setSchema(tLCPolSet.get(i));
            if (!dealZTData(tLCPolSchema)) {
                return false;
            }
        }
        mTransferData.setNameAndValue("ZTMoney", String.valueOf(mZTMoney));

        return true;
    }

    /**
     *
     **/
    private boolean dealZTData(LCPolSchema tLCPolSchema) {

        logger.debug(
                "=====This is PGrpEdorZTDetailBL->dealZTData=====\n");
        if (!dealPol(tLCPolSchema)) {
            return false;
        }
        if (!dealEdorItem()) {
            return false;
        }

        //计算退费
        if (!dealZTMoney(tLCPolSchema)) {
            return false;
        }

        //判断退费是否为0
        if (0.0 - mLPEdorItemSchema.getGetMoney() == 0) {
            return true;
        }

        //设置批改补退费表
        if (!dealGetEndorse()) {
            return false;
        }

        //判断个人退保金是否转入公共帐户
        if (mIfChecked.equals("") || !mIfChecked.equals("true")) {
            return true;
        }

        //个人退保金转入公共帐户，处理LCInsureAccTrace表
        if (!dealAccTrace()) {
            return false;
        }

        return true;
    }

    /**
     *
     **/
    private boolean dealPol(LCPolSchema tLCPolSchema) {

        logger.debug(
                "=====This is PGrpEdorZTDetailBL->dealPol=====\n");
        Reflections tRef = new Reflections();
        tRef.transFields(mLPPolSchema, tLCPolSchema);
        mLPPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
        mLPPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
        mLPPolSchema.setModifyDate(mCurrDate);
        mLPPolSchema.setModifyTime(mCurrTime);
        mLPPolSchema.setOperator(mGlobalInput.Operator);
        mLPPolSchema.setManageCom(mGlobalInput.ManageCom);
	mLPPolSchema.setLastEdorDate(mLPGrpEdorItemSchema.getEdorValiDate());
        mLPPolSet.add(mLPPolSchema);

        return true;
    }

    /**
     *
     **/
    private boolean dealEdorItem() {

        logger.debug(
                "=====This is PGrpEdorZTDetailBL->dealEdorItem=====\n");
        LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
        LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
        LPEdorItemDB mLPEdorItemDB = new LPEdorItemDB();
        mLPEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
        mLPEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
        mLPEdorItemDB.setContNo(mLPPolSchema.getContNo());
        tLPEdorItemSet = mLPEdorItemDB.query();
        tLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
        mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
        mLPEdorItemSchema.setPolNo(mLPPolSchema.getPolNo());
        mLPEdorItemSchema.setChgAmnt( -1 * mLPPolSchema.getAmnt());
        mLPEdorItemSchema.setChgPrem( -1 * mLPPolSchema.getPrem());

        return true;
    }

    /**
     *
     **/
    private boolean dealZTMoney(LCPolSchema tLCPolSchema) {

        logger.debug(
                "=====This is PGrpEdorZTDetailBL->dealZTMoney=====\n");
        double tSumPrem = 0.0;
        dPolZTMoney = 0.00;
        tSumPrem = tLCPolSchema.getSumPrem();

        //判断保单是否理赔,如果有理赔退费金额不为0,退费为0，否则计算退费
        LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
        tLLClaimDetailDB.setPolNo(mLPPolSchema.getPolNo());
        LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB.query();
	String sql="select nvl(sum(RealPay),0) from llclaimdetail where polno='"
		   +mLPPolSchema.getPolNo()+"'";
	ExeSQL tExeSQL = new ExeSQL();
        String tRealPay = tExeSQL.getOneValue(sql);
        if ((tLCPolSchema.getPolState() != null &&
             !tLCPolSchema.getPolState().equals("") &&
             !tLCPolSchema.getPolState().equals("1")) ||
            Double.parseDouble(tRealPay) > 0) {
            dPolZTMoney = 0.00;
        } else {
//            LCGrpContDB tLCGrpContDB = new LCGrpContDB();
//            tLCGrpContDB.setGrpContNo(mLPPolSchema.getGrpContNo());
//            tLCGrpContDB.setMarketType("1"); //卡单业务
//            LCGrpContSet tLCGrpContSet = tLCGrpContDB.query();
//            if (tLCGrpContSet.size() > 0) {
//                dPolZTMoney = mLPPolSchema.getPrem(); //卡单直接取保费
//            } else {

                //开始计算退费
                LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
                tLMRiskAppDB.setRiskCode(mLPPolSchema.getRiskCode());

                //长短险标志
                String tRiskPeriod = tLMRiskAppDB.query().get(1).
                                     getRiskPeriod();

                //分红险标志
                String tBonusFlag = tLMRiskAppDB.query().get(1).
                                    getBonusFlag();
                LMRiskDB tLMRiskDB = new LMRiskDB();
                tLMRiskDB.setRiskCode(mLPPolSchema.getRiskCode());

                //帐户标志
                String tInsuAccFlag = tLMRiskDB.query().get(1).getInsuAccFlag();

                //处理长险
                if (tRiskPeriod != null && !tRiskPeriod.equals("") &&
                    tRiskPeriod.equals("L")) {
                    if (!dealLRiskMoney(tInsuAccFlag, tBonusFlag, tSumPrem)) {
                        return false;
                    }
                } else {

                    //处理短险
                    if (!dealSRiskMoney(tLCPolSchema)) {
                        return false;
                    }
                }
//            }
        }
        if (dPolZTMoney < 0) {
            mErrors.addOneError(new CError("减人费用计算失败！"));
            return false;
        }
        mZTMoney += dPolZTMoney;
        mLPEdorItemSchema.setGetMoney( -1 * dPolZTMoney);
        mLPEdorItemSchema.setEdorState("1");
        mLPEdorItemSchema.setModifyDate(mCurrDate);
        mLPEdorItemSchema.setModifyTime(mCurrTime);

        return true;
    }

    /**
     * tInsuAccFlag: 是否帐户型险种(Y/N)
     * tBonusFlag: 是否红利帐户(1/0)
     **/
    private boolean dealLRiskMoney(String tInsuAccFlag,
                                   String tBonusFlag,
                                   double tSumPrem) {

        logger.debug(
                "=====This is PGrpEdorZTDetailBL->dealLongRiskMoney=====\n");
        if (tInsuAccFlag != null && !tInsuAccFlag.equals("") &&
            tInsuAccFlag.equals("Y")) { //帐户型险种

            //调用结息函数对红利型险种进行结息
            if (!getAccInterest(tBonusFlag)) {
                return false;
            }
        } else {

            //定额养老型
            dPolZTMoney = calNowPrize(tSumPrem, mLPEdorItemSchema.
                                      getEdorValiDate());
        }

        return true;
    }

    /**
     *
     **/
    private boolean getAccInterest(String tBonusFlag) {

        logger.debug(
                "=====This is PGrpEdorZTDetailBL->getAccInterest=====\n");

        //每个polno对应的退费
        double tInsuAccBala = 0.0;
	double dInsuAccBala = 0.0;
        LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
        tLCInsureAccClassDB.setPolNo(mLPPolSchema.getPolNo());
        LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB.query();
        LCInsureAccClassSchema tLCInsureAccClassSchema = null;
        LPInsureAccClassSchema tLPInsureAccClassSchema =
                new LPInsureAccClassSchema();
        for (int j = 1; j <= tLCInsureAccClassSet.size(); j++) {
            tLCInsureAccClassSchema = new LCInsureAccClassSchema();
            tLCInsureAccClassSchema = tLCInsureAccClassSet.get(j);

            //企业缴费未归属部分
            if (tLCInsureAccClassSchema.getAccAscription().equals("0")) {

                //校验归属
                String RiskCode = tLCInsureAccClassSchema.getRiskCode();
                String InsuAccNo = tLCInsureAccClassSchema.getInsuAccNo();
                String PayPlanCode = tLCInsureAccClassSchema.getPayPlanCode();
                LMRiskAccPayDB tLMRiskAccPayDB = new LMRiskAccPayDB();
                tLMRiskAccPayDB.setRiskCode(RiskCode);
                tLMRiskAccPayDB.setInsuAccNo(InsuAccNo);
                tLMRiskAccPayDB.setPayPlanCode(PayPlanCode);
                LMRiskAccPaySet tLMRiskAccPaySet = tLMRiskAccPayDB.query();
                if (tLMRiskAccPaySet.size() > 0) {

                    //0:代表需要作归属。
                    if (tLMRiskAccPaySet.get(1).getascription() != null
                        && !tLMRiskAccPaySet.get(1).getascription().equals("")
                        && tLMRiskAccPaySet.get(1).getascription().equals("0")) {
                        LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
                        tLPEdorItemDB.setPolNo(tLCInsureAccClassSchema.getPolNo());
                        tLPEdorItemDB.setEdorType("AJ");
                        tLPEdorItemDB.setEdorState("0");
                        LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
                        if (tLPEdorItemSet.size() < 1) {
                            mErrors.addOneError(new CError(
                                    "该个人还未进行帐户权益归属！"));
                            return false;
                        }
                    }
                }
                //归属校验完毕
            }
            AccountManage tAccountManage = new AccountManage();

            //需要结息
            //无论什么时候都需要结息

            //if (!mLPEdorItemSchema.getEdorValiDate().equals(
              //      tLCInsureAccClassSchema.getBalaDate())) {

                //原始利率类型(年利率)
                String tRateType = "Y";

                //目标利率类型(日利率)
                String tIntvType = "D";

                //银行利率期间
                int tPerio = 0;

                //利率类型：C-活期/F-定期
                String tType = "F";

                //贷存款标志（贷款还是存款）
                String tDepst = "D";
                TransferData mReturnData = new TransferData();
//                mReturnData = tAccountManage.getAccClassInterestNew(
//                        tLCInsureAccClassSchema,
//                        mLPEdorItemSchema.getEdorValiDate(),
//                        tRateType, tIntvType, tPerio,
//                        tType, tDepst);
                if (mReturnData != null) {
                    String tempmoney = String.valueOf(
                            mReturnData.getValueByName("aAccClassSumPay"));
                    tInsuAccBala = Math.round(Double.parseDouble(tempmoney)*100)/100.0;
                    logger.debug("=====取两位之前："+Double.parseDouble(tempmoney));
                    logger.debug("===结息金额===" + tInsuAccBala);
                } else {
                    tInsuAccBala = 0.0;
                }
		if(tInsuAccBala<-1.0)
		{
		   CError.buildErr(this, "结息后帐户余额为零!");
		       return false;
		}
		if(tInsuAccBala<0.0)
		{
		   tInsuAccBala=0.0;
		}
                tLPInsureAccClassSchema = new LPInsureAccClassSchema();
                ref.transFields(tLPInsureAccClassSchema,
                                tLCInsureAccClassSchema);
                tLPInsureAccClassSchema.setEdorNo(
                        mLPEdorItemSchema.getEdorNo());
                tLPInsureAccClassSchema.setEdorType("ZT");
                tLPInsureAccClassSchema.setBalaDate(
                        mLPEdorItemSchema.getEdorValiDate());
                tLPInsureAccClassSchema.setBalaTime(mCurrTime);
                tLPInsureAccClassSchema.setLastAccBala(
                        tLCInsureAccClassSchema.getInsuAccBala());
                tLPInsureAccClassSchema.setInsuAccBala(tInsuAccBala);
                tLPInsureAccClassSchema.setModifyDate(mCurrDate);
                tLPInsureAccClassSchema.setModifyTime(mCurrTime);
                mLPInsureAccClassSet.add(tLPInsureAccClassSchema);
            //}

            //已经结息
            /*
            else {
                tLPInsureAccClassSchema = new LPInsureAccClassSchema();
                ref.transFields(tLPInsureAccClassSchema,
                                tLCInsureAccClassSchema);
                tInsuAccBala = tLCInsureAccClassSchema.getInsuAccBala();
            }
           //jixf 20061023 end
           */
            //红利型险种缴费时建立新帐户，并向class和trace表各放入一条记录，
            //退费时更新class表，trace表插入记录，因为退保后余额为0，需要放入负记录
            if (!dealBonusMoney(tLPInsureAccClassSchema, tBonusFlag)) {
                return false;
            }
	    if(tBonusFlag.equals("2")
	       &&!tLCInsureAccClassSchema.getAccAscription().equals("0"))
	    {
		dInsuAccBala = dInsuAccBala+tInsuAccBala;
		logger.debug("个人帐户余额总和是："+dInsuAccBala);
	    }
        }
        if (tBonusFlag.equals("2") &&
            (mIfChecked.equals("") || !mIfChecked.equals("true"))) {
	     dPolZTMoney = calNowPrize(dInsuAccBala,
				   mLPEdorItemSchema.
                                           getEdorValiDate());
        }
        //万能扣年费
//        if (tBonusFlag.equals("2") &&
//            (mIfChecked.equals("") || !mIfChecked.equals("true"))) {
//            double per_fee = 0.00;
//            per_fee = calperfee(tLPInsureAccClassSchema);
//            dPolZTMoney = dPolZTMoney - per_fee;
//        }

        return true;
    }

    /**
     *
     **/
    private boolean dealBonusMoney(LPInsureAccClassSchema
                                   tLPInsureAccClassSchema,
                                   String tBonusFlag) {

        logger.debug(
                "=====This is PGrpEdorZTDetailBL->dealBonusMoney=====\n");

        //结息后除了更新class表外，同时向trace表插入数据
        String tManageCom = tLPInsureAccClassSchema.getManageCom();
        double tInsuAccBala = tLPInsureAccClassSchema.getInsuAccBala();
        LPInsureAccTraceSchema tLPInsureAccTraceSchema = new
                LPInsureAccTraceSchema();
        Reflections tRef = new Reflections();
        tRef.transFields(tLPInsureAccTraceSchema, tLPInsureAccClassSchema);
        String sLimit = PubFun.getNoLimit(tManageCom);
        String tSerNo = PubFun1.CreateMaxNo("SERIALNO", sLimit);
        tLPInsureAccTraceSchema.setSerialNo(tSerNo);
        tLPInsureAccTraceSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPInsureAccTraceSchema.setEdorType("ZT");
        tLPInsureAccTraceSchema.setMoneyType("PG");
        tLPInsureAccTraceSchema.setOtherNo(mLPEdorItemSchema.getEdorNo());
        tLPInsureAccTraceSchema.setPayNo(tLPInsureAccClassSchema.getOtherNo());
        tLPInsureAccTraceSchema.setOtherType("4");
        tLPInsureAccTraceSchema.setMoney( -1 * tInsuAccBala);
        tLPInsureAccTraceSchema.setState("0");
        tLPInsureAccTraceSchema.setAccAscription("1");
        tLPInsureAccTraceSchema.setPayDate(
                mLPEdorItemSchema.getEdorValiDate());
        tLPInsureAccTraceSchema.setMakeDate(mCurrDate);
        tLPInsureAccTraceSchema.setMakeTime(mCurrTime);
        tLPInsureAccTraceSchema.setModifyDate(mCurrDate);
        tLPInsureAccTraceSchema.setModifyTime(mCurrTime);
        tLPInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
        mLPInsureAccTraceSet.add(tLPInsureAccTraceSchema);
        map.put("delete from LPInsureAccTrace where edorno='"
                + tLPInsureAccTraceSchema.getEdorNo() +
                "' and edortype='ZT' and polno='"
                + tLPInsureAccTraceSchema.getPolNo() + "'", "DELETE");

        //退费中归属个人部分的退给个人，不归属个人部分的转帐
        //归属个人部分，如果用户要求转帐则转入同险种下的公共帐户，否则直接退费，
        //前面已经完成直接退费部分，如果转公共帐户，后面有处理
        double dInsuAccBala = 0.0;
        if (!tLPInsureAccClassSchema.getAccAscription().equals("0")) {

            //计算帐户金额现价
            if (tBonusFlag.equals("1")) { //162是按每笔缴费算现价
                dInsuAccBala = calAccPrize(tInsuAccBala,
                                           mLPEdorItemSchema.
                                           getEdorValiDate(),
                                           tLPInsureAccClassSchema);
            } else if(!tBonusFlag.equals("2")){
                dInsuAccBala = calNowPrize(tInsuAccBala,
                                           mLPEdorItemSchema.
                                           getEdorValiDate());
            }
            logger.debug("tInsuAccBala===============" + tInsuAccBala);
            logger.debug("dInsuAccBala===============" + dInsuAccBala);
            dPolZTMoney += dInsuAccBala;
        } else {

            //归属企业部分转入公共帐户或法人帐户
            LCPolDB aLCPolDB = new LCPolDB();
            aLCPolDB.setGrpPolNo(tLPInsureAccClassSchema.getGrpPolNo());

            //先查公共帐户，必须退到同一险种的公共帐户下
            aLCPolDB.setPolTypeFlag("2");
            LCPolSet aLCPolSet = aLCPolDB.query();
            if (aLCPolSet.size() - 1 < 0) {
                aLCPolDB = new LCPolDB();
                aLCPolDB.setGrpPolNo(tLPInsureAccClassSchema.getGrpPolNo());

                //法人帐户
                aLCPolDB.setPolTypeFlag("3");
                aLCPolSet = new LCPolSet();
                aLCPolSet = aLCPolDB.query();
            }
            if (aLCPolSet.size() < 1) {
                mErrors.addOneError(new CError(
                        "该保单下未查询到公共帐户或法人帐户！"));
                return false;
            }
            LCPolSchema aLCPolSchema = aLCPolSet.get(1);
            LCInsureAccClassDB aLCInsureAccClassDB = new LCInsureAccClassDB();
            aLCInsureAccClassDB.setPolNo(aLCPolSchema.getPolNo());
            LCInsureAccClassSet aLCInsureAccClassSet =
                    aLCInsureAccClassDB.query();
            if (aLCInsureAccClassSet.size() < 1) {
                mErrors.addOneError(new CError(
                        "该保单下未查询到公共帐户或法人帐户的帐户表记录！"));
                return false;
            }
            LCInsureAccClassSchema aLCInsureAccClassSchema =
                    aLCInsureAccClassSet.get(1);

            //转帐公共帐户或者法人帐户
//            if (!tBonusFlag.equals("1") && aLCInsureAccClassSet.size() - 1 == 0) {
            if ((aLCPolSchema.getPolTypeFlag() != null
                 && !aLCPolSchema.getPolTypeFlag().equals("")
                 && aLCPolSchema.getPolTypeFlag().equals("2")) ||
                !tBonusFlag.equals("1")) {
                //162转公共帐户和万能
                if (!addPDataToTrace(aLCInsureAccClassSchema,
                                     tLPInsureAccClassSchema.getPolNo(),
                                     tManageCom,
                                     tInsuAccBala)) {
                    return false;
                }
            } else {
                //162转法人帐户，trace表和class表数据缴费时同步
                if (!addPDataToTrace2(aLCInsureAccClassSchema,
                                      tLPInsureAccClassSchema.getPolNo(),
                                      tManageCom,
                                      tInsuAccBala)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     *
     **/
    private boolean addPDataToTrace(
            LCInsureAccClassSchema aLCInsureAccClassSchema,
            String tPolNo,
            String tManageCom,
            double tInsuAccBala) {

        logger.debug("=====addPDataToTrace=====\n");
        tManageCom = mGlobalInput.ManageCom;
        LPInsureAccTraceSchema tLPInsureAccTraceSchema =
                new LPInsureAccTraceSchema();
        String sLimit = PubFun.getNoLimit(tManageCom);
        String tSerNo = PubFun1.CreateMaxNo("SERIALNO", sLimit);
        tLPInsureAccTraceSchema.setSerialNo(tSerNo);
        tLPInsureAccTraceSchema.setEdorNo(
                mLPEdorItemSchema.getEdorNo());
        tLPInsureAccTraceSchema.setEdorType("ZT");
        tLPInsureAccTraceSchema.setPolNo(aLCInsureAccClassSchema.getPolNo());
        tLPInsureAccTraceSchema.setMoneyType("PG");
        tLPInsureAccTraceSchema.setRiskCode(
                aLCInsureAccClassSchema.getRiskCode());
        tLPInsureAccTraceSchema.setOtherNo(mLPEdorItemSchema.getEdorNo());
        tLPInsureAccTraceSchema.setPayNo(aLCInsureAccClassSchema.getOtherNo());
        tLPInsureAccTraceSchema.setOtherType("4");
        tLPInsureAccTraceSchema.setMoney(tInsuAccBala);
        tLPInsureAccTraceSchema.setContNo(aLCInsureAccClassSchema.getContNo());
        tLPInsureAccTraceSchema.setGrpPolNo(aLCInsureAccClassSchema.getGrpPolNo());
        tLPInsureAccTraceSchema.setInsuAccNo(
                aLCInsureAccClassSchema.getInsuAccNo());
        tLPInsureAccTraceSchema.setPolNo(aLCInsureAccClassSchema.getPolNo());
        tLPInsureAccTraceSchema.setGrpContNo(
                aLCInsureAccClassSchema.getGrpContNo());
        tLPInsureAccTraceSchema.setManageCom(
                aLCInsureAccClassSchema.getManageCom());
        tLPInsureAccTraceSchema.setPayPlanCode(
                aLCInsureAccClassSchema.getPayPlanCode());
        tLPInsureAccTraceSchema.setState("0");
        tLPInsureAccTraceSchema.setAccAscription("1");
        tLPInsureAccTraceSchema.setPayDate(
                mLPEdorItemSchema.getEdorValiDate());

        //个人帐户对应的polno
        tLPInsureAccTraceSchema.setAccAlterNo(tPolNo);
        tLPInsureAccTraceSchema.setAccAlterType("1");
        tLPInsureAccTraceSchema.setMakeDate(mCurrDate);
        tLPInsureAccTraceSchema.setMakeTime(mCurrTime);
        tLPInsureAccTraceSchema.setModifyDate(mCurrDate);
        tLPInsureAccTraceSchema.setModifyTime(mCurrTime);
        tLPInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
        mLPInsureAccTraceSet.add(tLPInsureAccTraceSchema);
        map.put("delete from LPInsureAccTrace where edorno='"
                + tLPInsureAccTraceSchema.getEdorNo() +
                "' and edortype='ZT' and polno='"
                + aLCInsureAccClassSchema.getPolNo() + "' and AccAlterNo='" +
                tPolNo + "'", "DELETE");

        return true;
    }

    /**
     *
     **/
    private boolean addPDataToTrace2(
            LCInsureAccClassSchema aLCInsureAccClassSchema,
            String tPolNo,
            String tManageCom,
            double tInsuAccBala) {
        logger.debug("=====addPDataToTrace2=====\n");
        Reflections tRef = new Reflections();
        LPInsureAccClassSchema tLPInsureAccClassSchema = new
                LPInsureAccClassSchema();
        tRef.transFields(tLPInsureAccClassSchema, aLCInsureAccClassSchema);
        tLPInsureAccClassSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPInsureAccClassSchema.setEdorType("ZT");
        String StrLimit = mGlobalInput.ManageCom;
        String tPayNo = PubFun1.CreateMaxNo("PAYNO", StrLimit);
        tLPInsureAccClassSchema.setOtherNo(tPayNo);
        tLPInsureAccClassSchema.setOtherType("2");
        tLPInsureAccClassSchema.setAccAscription("1");
        tLPInsureAccClassSchema.setBalaDate("1900-01-01");
        tLPInsureAccClassSchema.setBalaTime("00:00:00");
        tLPInsureAccClassSchema.setInsuAccBala(0.00);
        tLPInsureAccClassSchema.setSumPay(tInsuAccBala);
        tLPInsureAccClassSchema.setOperator(mGlobalInput.Operator);
        tLPInsureAccClassSchema.setMakeDate(mCurrDate);
        tLPInsureAccClassSchema.setMakeTime(mCurrTime);
        tLPInsureAccClassSchema.setModifyDate(mCurrDate);
        tLPInsureAccClassSchema.setModifyTime(mCurrTime);
        mLPInsureAccClassSet.add(tLPInsureAccClassSchema);
        map.put("delete from LPInsureAccClass where 1=1 and edorno='"
                + tLPInsureAccClassSchema.getEdorNo() +
                "' and edortype='ZT' and polno='"
                + aLCInsureAccClassSchema.getPolNo()
                +
                "' and otherno not in (select payno from LPInsureAccTrace where edorno='"
                + tLPInsureAccClassSchema.getEdorNo() +
                "' and edortype='ZT' and polno='"
                + aLCInsureAccClassSchema.getPolNo() + "' and AccAlterNo='"
                + tPolNo + "')", "DELETE");

        LPInsureAccTraceSchema tLPInsureAccTraceSchema =
                new LPInsureAccTraceSchema();
        String sLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
        String tSerNo = PubFun1.CreateMaxNo("SERIALNO", sLimit);
        tLPInsureAccTraceSchema.setSerialNo(tSerNo);
        tLPInsureAccTraceSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPInsureAccTraceSchema.setEdorType("ZT");
        tLPInsureAccTraceSchema.setPolNo(aLCInsureAccClassSchema.getPolNo());
        tLPInsureAccTraceSchema.setMoneyType("PG");
        tLPInsureAccTraceSchema.setRiskCode(aLCInsureAccClassSchema.getRiskCode());
        tLPInsureAccTraceSchema.setOtherNo(tPayNo);
        tLPInsureAccTraceSchema.setPayNo(tPayNo);
        tLPInsureAccTraceSchema.setOtherType("2");
        tLPInsureAccTraceSchema.setMoney(tInsuAccBala);
        tLPInsureAccTraceSchema.setContNo(aLCInsureAccClassSchema.getContNo());
        tLPInsureAccTraceSchema.setGrpPolNo(aLCInsureAccClassSchema.getGrpPolNo());
        tLPInsureAccTraceSchema.setInsuAccNo(aLCInsureAccClassSchema.
                                             getInsuAccNo());
        tLPInsureAccTraceSchema.setGrpContNo(aLCInsureAccClassSchema.
                                             getGrpContNo());
        tLPInsureAccTraceSchema.setManageCom(aLCInsureAccClassSchema.
                                             getManageCom());
        tLPInsureAccTraceSchema.setPayPlanCode(
                aLCInsureAccClassSchema.getPayPlanCode());
        tLPInsureAccTraceSchema.setState("0");
        tLPInsureAccTraceSchema.setAccAscription("1");
        tLPInsureAccTraceSchema.setPayDate(mLPEdorItemSchema.getEdorValiDate());

        //个人帐户对应的polno
        tLPInsureAccTraceSchema.setAccAlterNo(tPolNo);
        tLPInsureAccTraceSchema.setAccAlterType("1");
        tLPInsureAccTraceSchema.setMakeDate(mCurrDate);
        tLPInsureAccTraceSchema.setMakeTime(mCurrTime);
        tLPInsureAccTraceSchema.setModifyDate(mCurrDate);
        tLPInsureAccTraceSchema.setModifyTime(mCurrTime);
        tLPInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
        mLPInsureAccTraceSet.add(tLPInsureAccTraceSchema);

        map.put("delete from LPInsureAccTrace where edorno='"
                + tLPInsureAccTraceSchema.getEdorNo() +
                "' and edortype='ZT' and polno='"
                + aLCInsureAccClassSchema.getPolNo() + "' and AccAlterNo='"
                + tPolNo + "'", "DELETE");

        return true;
    }

    /**
     *
     **/
    private boolean dealSRiskMoney(LCPolSchema tLCPolSchema) {

        logger.debug(
                "=====This is PGrpEdorZTDetailBL->dealSRiskMoney=====\n");
        LCGrpContDB tLCGrpContDB = new LCGrpContDB();
        tLCGrpContDB.setGrpContNo(tLCPolSchema.getGrpContNo());
        LCGrpContSet tLCGrpContSet = tLCGrpContDB.query();
	String tMarketType=tLCGrpContSet.get(1).getMarketType();
	String tStandByflag1="";
	if(tMarketType==null||tMarketType.equals(""))
	{
	 tMarketType="0";
	}
	String tSQLe="select count(*) from lcpol where contno='"
		    +tLCPolSchema.getContNo()+"' and riskcode in ('144','148')";
       String tSQLo="select count(*) from lcpol where contno='"
		    +tLCPolSchema.getContNo()+"'";
        ExeSQL tExeSQL = new ExeSQL();
        int tEnum = Integer.parseInt(tExeSQL.getOneValue(tSQLe));
	int tOnum=Integer.parseInt(tExeSQL.getOneValue(tSQLo));
	if(tEnum==2&&tOnum==2)
	{
	   tStandByflag1="1";
	}
	else
	{
	   tStandByflag1="0";
	}
        double tPrem = 0.0;
        tPrem = tLCPolSchema.getSumPrem();
        //Date tPayDate = tD.getDate(mLPPolSchema.getCValiDate());
        BqCalBase tBqCalBase = new BqCalBase();
        tBqCalBase.setEdorValiDate(mLPEdorItemSchema.getEdorValiDate());
        tBqCalBase.setEdorTypeCal(mLPEdorItemSchema.getEdorTypeCal());
        tBqCalBase.setPolNo(mLPPolSchema.getPolNo());
        tBqCalBase.setPrem(tPrem);
	tBqCalBase.setCardFlag(tMarketType);
	tBqCalBase.setStandByFlag1(tStandByflag1);
        LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
        tLMEdorCalDB.setCalType("SurCalType");
        tLMEdorCalDB.setRiskCode(mLPPolSchema.getRiskCode());
        tLMEdorCalDB.setEdorType(mLPEdorItemSchema.getEdorType());
        LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query();
        if (tLMEdorCalDB.mErrors.needDealError()) {
            mErrors.copyAllErrors(tLMEdorCalDB.mErrors);
            mErrors.addOneError(new CError("查询险种责任保全计算信息失败！"));
            return false;
        }
        if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
            logger.debug("没有计算信息，不作计算");
            dPolZTMoney = 0.0;
        } else {
            BqCalBL tBqCalBL = new BqCalBL();
            // 计算短期费率
            double tValue = tBqCalBL.calChgMoney(tLMEdorCalSet.
                                                 get(1).getCalCode(),
                                                 tBqCalBase);
            if (tBqCalBL.mErrors.needDealError()) {
                mErrors.copyAllErrors(tBqCalBL.mErrors);
                mErrors.addOneError(new CError("计算短期费率失败！"));

                return false;
            }
	    if(tMarketType.equals("1")&&tStandByflag1.equals("1"))
	    {
		logger.debug("扣除的手续费为:" + tValue);
		dPolZTMoney=tPrem-tValue;
	    }
	    else{
                if (tValue < 0 || tValue > 1) {
                    logger.debug("短期费率计算异常，置为0！");
                    tValue = 0;
                }
                logger.debug(tPrem + "*" + tValue);
                dPolZTMoney = tPrem * tValue;
            }
        }

        return true;
    }

    /**
     *
     **/
    private boolean dealGetEndorse() {

        logger.debug(
                "=====This is PGrpEdorZTDetailBL->dealGetEndorse=====\n");

        //投保人退保，处理退费
        if (!addEndorseData()) {
            return false;
        }
        mLJSGetEndorseSchema.setGetMoney(mLPEdorItemSchema.getGetMoney());
        mLJSGetEndorseSchema.setFeeFinaType("TB");
        mLJSGetEndorseSchema.setSubFeeOperationType("TB");
        mLJSGetEndorseSet.add(mLJSGetEndorseSchema);
        map.put("delete from LJSGetEndorse where endorsementno='"
                + mLJSGetEndorseSchema.getEndorsementNo() +
                "' and FEEOPERATIONTYPE='ZT' and polno='"
                + mLJSGetEndorseSchema.getPolNo() + "'", "DELETE");

        //投保人转帐，处理缴费
//        if (mIfChecked.equals("") || !mIfChecked.equals("true")) {
//            return true;
//        }

        return true;
    }

    /**
     *
     **/
    private boolean addEndorseData() {

        logger.debug(
                "=====This is PGrpEdorZTDetailBL->addEndorseData=====\n");

        //设置批改补退费表
        mLJSGetEndorseSchema = new LJSGetEndorseSchema();
        ref.transFields(mLJSGetEndorseSchema, mLPPolSchema);
        mLJSGetEndorseSchema.setGetNoticeNo(mLPGrpEdorItemSchema.
                                            getEdorNo()); //给付通知书号码
        mLJSGetEndorseSchema.setEndorsementNo(mLPGrpEdorItemSchema.
                                              getEdorNo());
        mLJSGetEndorseSchema.setGrpContNo(mLPGrpEdorItemSchema.
                                          getGrpContNo());
        mLJSGetEndorseSchema.setGrpPolNo(mLPPolSchema.getGrpPolNo());
        mLJSGetEndorseSchema.setPolNo(mLPPolSchema.getPolNo());
        mLJSGetEndorseSchema.setInsuredNo(mLPPolSchema.getInsuredNo());
        mLJSGetEndorseSchema.setFeeOperationType(mLPGrpEdorItemSchema.
                                                 getEdorType());
        mLJSGetEndorseSchema.setGetDate(mLPGrpEdorItemSchema.
                                        getEdorValiDate());
        //mLJSGetEndorseSchema.setGetMoney( -dPolZTMoney);
        mLJSGetEndorseSchema.setFeeOperationType("ZT");
        mLJSGetEndorseSchema.setPayPlanCode("00000000");
        mLJSGetEndorseSchema.setDutyCode("0");
        mLJSGetEndorseSchema.setOtherNo(mLPGrpEdorItemSchema.getEdorNo());
        mLJSGetEndorseSchema.setOtherNoType("3");
        mLJSGetEndorseSchema.setGetFlag("0");
        mLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
        mLJSGetEndorseSchema.setManageCom(mGlobalInput.ManageCom);
        mLJSGetEndorseSchema.setMakeDate(mCurrDate);
        mLJSGetEndorseSchema.setMakeTime(mCurrTime);
        mLJSGetEndorseSchema.setModifyDate(mCurrDate);
        mLJSGetEndorseSchema.setModifyTime(mCurrTime);

        return true;
    }

    /**
     *
     **/
    private boolean dealAccTrace() {

        logger.debug(
                "=====This is PGrpEdorZTDetailBL->dealAccTrace=====\n");

        //归属企业部分转入公共帐户或法人帐户
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setGrpPolNo(mLPPolSchema.getGrpPolNo());

        //先查公共帐户，必须退到同一险种的公共帐户下
        tLCPolDB.setPolTypeFlag("2");
        LCPolSet tLCPolSet = tLCPolDB.query();
        if (tLCPolSet.size() - 1 < 0) {
            tLCPolDB = new LCPolDB();
            tLCPolDB.setGrpPolNo(mLPPolSchema.getGrpPolNo());
            tLCPolDB.setPolTypeFlag("3");
            tLCPolSet = new LCPolSet();
            tLCPolSet = tLCPolDB.query();
            if (tLCPolSet.size() - 1 < 0) {
                return true; //没有公共帐户和法人不报错，直接返回即可
            }
        }
        LCPolSchema tLCPolSchema = tLCPolSet.get(1);
        LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
        tLCInsureAccClassDB.setPolNo(tLCPolSchema.getPolNo());
        LCInsureAccClassSet tLCInsureAccClassSet =
                tLCInsureAccClassDB.query();
        LCInsureAccClassSchema tLCInsureAccClassSchema =
                tLCInsureAccClassSet.get(1);
        double tInsuAccBala = mLPEdorItemSchema.getGetMoney();

        if (tLCPolSchema.getPolTypeFlag() != null
            && !tLCPolSchema.getPolTypeFlag().equals("")
            && tLCPolSchema.getPolTypeFlag().equals("2")) { //转帐公共帐户
            if (!addPDataToTrace(tLCInsureAccClassSchema,
                                 mLPPolSchema.getPolNo(), "", -tInsuAccBala)) {
                return false;
            }
        } else {
            if (!addPDataToTrace2(tLCInsureAccClassSchema,
                                  mLPPolSchema.getPolNo(), "", -tInsuAccBala)) {
                return false;
            }
        }
        if (!addEndorseData()) {
            return false;
        }
        mLJSGetEndorseSchema.setGetMoney( -1 *
                                         mLPEdorItemSchema.getGetMoney());
        mLJSGetEndorseSchema.setFeeFinaType("TP");
        mLJSGetEndorseSchema.setSubFeeOperationType("TP");
        mLJSGetEndorseSet.add(mLJSGetEndorseSchema);

        return true;
    }

    /**
     *
     **/
    private boolean chgEdorState() {

        logger.debug(
                "=====This is PGrpEdorZTDetailBL->chgEdorState=====\n");

        //更新LPGrpEdorItem表
        mLPGrpEdorItemSchema.setGetMoney(mLPGrpEdorItemSchema.getGetMoney() +
                                         mZTMoney);
        mLPGrpEdorItemSchema.setEdorState("1");
        mLPGrpEdorItemSchema.setModifyDate(mCurrDate);
        mLPGrpEdorItemSchema.setModifyTime(mCurrTime);
        mLPGrpEdorItemSchema.setOperator(mOperator);
        mLPGrpEdorItemSchema.setManageCom(mManageCom);

        //更新LPGrpEdorMain表
        mLPGrpEdorMainSchema.setGetMoney(mLPGrpEdorMainSchema.getGetMoney() +
                                         mZTMoney);
        mLPGrpEdorMainSchema.setEdorState("1");
        mLPGrpEdorMainSchema.setModifyDate(mCurrDate);
        mLPGrpEdorMainSchema.setModifyTime(mCurrTime);
        mLPGrpEdorMainSchema.setOperator(mOperator);
        mLPGrpEdorMainSchema.setManageCom(mManageCom);

        //更新LPEdorApp表
        mLPEdorAppSchema.setGetMoney(mLPEdorAppSchema.getGetMoney() +
                                     mZTMoney);
        mLPEdorAppSchema.setEdorState("1");
        mLPEdorAppSchema.setModifyDate(mCurrDate);
        mLPEdorAppSchema.setModifyTime(mCurrTime);
        mLPEdorAppSchema.setOperator(mOperator);
        mLPEdorAppSchema.setManageCom(mManageCom);

        return true;
    }

    /**
     * 保存需要退保的数据（保单，被保人，险种）到P表
     * @return boolean
     */
    private boolean savePData() {

        logger.debug(
                "=====This is PGrpEdorZTDetailBL->savePData=====\n");
        Reflections ref = new Reflections();
        String tEdorNo = mLPGrpEdorItemSchema.getEdorNo();
        String tEdorType = mLPGrpEdorItemSchema.getEdorType();

        //判断如果保单下的所有险种被退保，则该保单也被退保
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
        tLCPolDB.setAppFlag("1");
//	tLCPolDB.setPolState("1");//新增保单状态标志
        int iAllPolCount = tLCPolDB.getCount();
        if (mLPPolSet.size() == iAllPolCount) {
            logger.debug("个单下的所有险种被退保，该个单也被退保");
            LPContBL tLPContBL = new LPContBL();
            tLPContBL.queryLPCont(mLPEdorItemSchema);
            if (tLPContBL.mErrors.needDealError()) {
                CError.buildErr(this, "查询个人保单失败!");
                return false;
            }
            map.put(tLPContBL.getSchema(), "DELETE&INSERT");
        } else {
            LCInsuredDB tLCInsuredDB = new LCInsuredDB();
            tLCInsuredDB.setContNo(mLPEdorItemSchema.getContNo());
            LCInsuredSet tLCInsuredSet = tLCInsuredDB.query();
            if (tLCInsuredDB.mErrors.needDealError()) {
                CError.buildErr(this, "查询被保人失败!");
                return false;
            }
            LCInsuredSchema tLCInsuredSchema;
            LPInsuredSchema tLPInsuredSchema;
            for (int i = 1; i <= tLCInsuredSet.size(); i++) {
                tLCInsuredSchema = tLCInsuredSet.get(i);

                //查询被保人下的所有险种个数
                LCPolDB tempLCPolDB = new LCPolDB();
                tempLCPolDB.setContNo(tLCInsuredSchema.getContNo());
                tempLCPolDB.setInsuredNo(tLCInsuredSchema.getInsuredNo());
                iAllPolCount = tempLCPolDB.getCount();
                if (tempLCPolDB.mErrors.needDealError()) {
                    CError.buildErr(this, "查询被保人险种出错!");
                    return false;
                }
                int iIsurPolCount = 0;
                for (int j = 1; j <= mLPPolSet.size(); j++) {
                    if (mLPPolSet.get(j).getInsuredNo().
                        equals(tLCInsuredSchema.getInsuredNo())) {
                        iIsurPolCount++;
                    }
                }

                //判断如果客户下的所有险种被退保，则该客户被退保
                if (iAllPolCount == iIsurPolCount) {
                    tLPInsuredSchema = new LPInsuredSchema();
                    ref.transFields(tLPInsuredSchema, tLCInsuredSchema);
                    tLPInsuredSchema.setEdorNo(tEdorNo);
                    tLPInsuredSchema.setEdorType(tEdorType);
                    mLPInsuredSet.add(tLPInsuredSchema);
                }
                map.put(mLPInsuredSet, "DELETE&INSERT");
            }
        }

        return true;
    }

    /**
     * 删除上次保存过的数据
     * @return boolean
     */
    private boolean delPData() {

        logger.debug(
                "=====This is PGrpEdorZTDetailBL->delPData=====\n");

        //清除P表中上次保存过的数据
        String tEdorNo = mLPEdorItemSchema.getEdorNo();
        String tEdorType = mLPEdorItemSchema.getEdorType();
        String tContNo = mLPEdorItemSchema.getContNo();
        String tPolNo = mLPEdorItemSchema.getPolNo();

        //验证数据
        if (tEdorNo.equals("") || tEdorType.equals("") ||
            tContNo.equals("") || tPolNo.equals("")) {

            CError.buildErr(this, "传输数据错误！");
            return false;
        }
        String sql = "";
        if (tPolNo.equals("000000")) {
            map.put(" delete from LJSGetEndorse " +
                    " where EndorsementNo = '" + tEdorNo +
                    " 'and ContNo = '" + tContNo +
                    "' and FeeOperationType = '" + tEdorType + "'",
                    "DELETE");
            sql = "delete from LPPol where ContNo = '" + tContNo +
                  "' and EdorNo = '" + tEdorNo + "'";
            map.put(sql, "DELETE");
            map.put("delete from LPInsureAccTrace " +
                    "where EdorNo = '" + tEdorNo +
                    "' and edortype = 'ZT' and " +
                    "ContNo = '" + tContNo + "'", "DELETE");
            map.put("delete from LPInsureAccClass " +
                    "where EdorNo = '" + tEdorNo +
                    "' and edortype = 'ZT' and " +
                    "ContNo = '" + tContNo + "'", "DELETE");
        } else {
            map.put(" delete from LJSGetEndorse " +
                    " where EndorsementNo = '" + tEdorNo +
                    " 'and ContNo = '" + tContNo +
                    " 'and PolNo = '" + tPolNo +
                    "' and FeeOperationType = '" + tEdorType + "'",
                    "DELETE");
            sql = "delete from LPPol where ContNo = '" + tContNo +
                  "' and EdorNo = '" + tEdorNo +
                  "' and PolNo = '" + tPolNo + "'";
            map.put(sql, "DELETE");
            map.put("delete from LPInsureAccTrace " +
                    "where EdorNo = '" + tEdorNo + "' and EdorType = 'ZT' " +
                    "and PolNo = '" + tPolNo + "'", "DELETE");
            map.put("delete from LPInsureAccClass " +
                    "where EdorNo = '" + tEdorNo + "' and EdorType = 'ZT' " +
                    "and PolNo = '" + tPolNo + "'", "DELETE");
            map.put("delete from LPInsureAccTrace " +
                    "where EdorNo = '" + tEdorNo + "' and edortype = 'ZT' " +
                    "and AccAlterNo = '" + tPolNo + "'", "DELETE");
            map.put("delete from LPInsureAccFeeTrace " +
                    "where EdorNo = '" + tEdorNo + "' and EdorType = 'ZT' " +
                    " and OtherNo = '" + tPolNo +
                    "' and OtherType = '1'", "DELETE");
            map.put("delete from LPInsureAccFeeTrace " +
                    "where EdorNo = '" + tEdorNo + "' and EdorType = 'ZT' " +
                    " and PolNo = '" + tPolNo + "'", "DELETE");

        }

        //清空容器
        mLPPolSet.clear();
        mLJSGetEndorseSet.clear();
        mLPInsureAccClassSet.clear();
        mLPInsureAccTraceSet.clear();

        return true;
    }

    /**
     *
     **/
    public VData getResult() {
        return mResult;
    }

    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean prepareOutputData() {

        logger.debug(
                "=====This is PGrpEdorZTDetailBL->prepareOutPutData=====\n");
        mLPEdorItemSet = new LPEdorItemSet();
        for (int i = 1; i <= mLPPolSet.size(); i++) {
            LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
            Reflections tReflections = new Reflections();
            tReflections.transFields(tLPEdorItemSchema, mLPEdorItemSchema);
            tLPEdorItemSchema.setPolNo(mLPPolSet.get(i).getPolNo());
            mLPEdorItemSet.add(tLPEdorItemSchema);
        }
        map.put("delete from LPEdorItem where EdorNo = '" +
                mLPGrpEdorItemSchema.getEdorNo() +
                "' and EdorType = 'ZT' and ContNo = '" +
                mLPEdorItemSchema.getContNo() +
                "' and PolNo = '000000'", "DELETE");
        map.put(mLPEdorItemSet, "DELETE&INSERT");
        map.put(mLPPolSet, "DELETE&INSERT");
        map.put(mLPInsureAccClassSet, "DELETE&INSERT");
        map.put(mLPInsureAccTraceSet, "DELETE&INSERT");
        if (mLPInsureAccFeeTraceSet.size() > 0) {
            map.put(mLPInsureAccFeeTraceSet, "DELETE&INSERT");
        }
        map.put(mLJSGetEndorseSet, "DELETE&INSERT");
        map.put(mLPGrpEdorItemSchema, "UPDATE");
        map.put(mLPGrpEdorMainSchema, "UPDATE");
        map.put(mLPEdorAppSchema, "UPDATE");
	String tSql = "update LPEdorItem set getmoney = nvl((" +
	      "select SUM(getmoney) from LjsGetEndorse " +
	      "where EndorsementNo = '" +
	      mLPGrpEdorItemSchema.getEdorNo() +
	      "' AND FEEOPERATIONTYPE = 'ZT' and " +
	      "PolNo = LPEdorItem.PolNo),0) where EdorNo = '" +
	      mLPGrpEdorItemSchema.getEdorNo() +
	      "' and EdorType = 'ZT' and contno='"+mLPEdorItemSchema.getContNo()+"'";
        map.put(tSql, "UPDATE");
        map.put("update LPInsureAccTrace set payno='" +
                mLPGrpEdorItemSchema.getEdorNo() + "' where edorno='" +
                mLPGrpEdorItemSchema.getEdorNo() +
                "'and contno='"+mLPEdorItemSchema.getContNo()+"' and edortype='ZT' and '1'=(select acckind from lmriskinsuacc where insuaccno=LPInsureAccTrace.insuaccno)",
                "UPDATE");

        mInputData.clear();
        mInputData.add(map);
        mInputData.add(mTransferData);

        mResult.clear();
        mResult.add(mTransferData);

        return true;
    }

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData() {
        try {
            mGlobalInput = (GlobalInput)
                           mInputData.getObjectByObjectName("GlobalInput", 0);
            mLPGrpEdorItemSchema = (LPGrpEdorItemSchema)
                                   mInputData.getObjectByObjectName(
                                           "LPGrpEdorItemSchema", 0);
            mLPEdorItemSchema = (LPEdorItemSchema)
                                mInputData.getObjectByObjectName(
                                        "LPEdorItemSchema", 0);
            mTransferData = (TransferData)
                            mInputData.getObjectByObjectName(
                                    "TransferData", 0);
            mIfChecked = String.valueOf(mTransferData.getValueByName(
                    "IfCheckd"));
            mOperator = mGlobalInput.Operator;
            mManageCom = mGlobalInput.ManageCom;
            logger.debug("#########mIfChecked#########" + mIfChecked);
        } catch (Exception e) {
            e.printStackTrace();
            CError tError = new CError();
            tError.moduleName = "PGrpEdorZTDetailBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接收数据失败!!";
            this.mErrors.addOneError(tError);

            return false;
        }
        if (mGlobalInput == null || mLPGrpEdorItemSchema == null ||
            mLPEdorItemSchema == null) {
            CError.buildErr(this, "传入数据有误!");
            return false;
        }

        return true;
    }

    /**
     * 校验传入的数据的合法性
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean checkData() {
        boolean flag = true;

        //团体保全项目
        LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
        tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
        if (!tLPGrpEdorItemDB.getInfo()) {

            //@@错误处理
            CError tError = new CError();
            tError.moduleName = "PGrpEdorZTDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "无团单保全项目数据!";
            this.mErrors.addOneError(tError);

            return false;
        }
        if (tLPGrpEdorItemDB.getEdorState().equals("2")) {

            //@@错误处理
            CError tError = new CError();
            tError.moduleName = "PGrpEdorZTDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "该保全项目已经申请确认不能修改!";
            this.mErrors.addOneError(tError);

            return false;
        }
        mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemDB.getSchema());

        //个单保全项目
//        LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
////        tLPEdorItemDB.setSchema(mLPEdorItemSchema);
//	tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
//	tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
//        if (!tLPEdorItemDB.getInfo()) {
//
//            //@@错误处理
//            CError tError = new CError();
//            tError.moduleName = "PGrpEdorZTDetailBL";
//            tError.functionName = "checkData";
//            tError.errorMessage = "无个单保全项目数据!";
//            this.mErrors.addOneError(tError);
//
//            return false;
//        }
//        mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());

        //团体批改表
        LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
        tLPGrpEdorMainDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
        tLPGrpEdorMainDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
        try {
            if (!tLPGrpEdorMainDB.getInfo()) {
                CError.buildErr(this, "没有团体保全批改表信息！");
                return false;
            }
        } catch (Exception ex) {
            CError.buildErr(this, ex.toString());
            return false;
        }
        mLPGrpEdorMainSchema.setSchema(tLPGrpEdorMainDB.getSchema());

        //申请主表
        LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
        tLPEdorAppDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
        try {
            if (!tLPEdorAppDB.getInfo()) {
                CError.buildErr(this, "没有团体保全批改表信息！");
                return false;
            }
        } catch (Exception ex) {
            CError.buildErr(this, ex.toString());
            return false;
        }
        mLPEdorAppSchema.setSchema(tLPEdorAppDB.getSchema());

        return flag;
    }


    /**
     * 计算现价函数
     */
    private double calNowPrize(double TMoney,
                               String tEdorValiDate) {
        if (mIfChecked != null && !mIfChecked.equals("") &&
            mIfChecked.equals("true")) { //转入公共帐户的话不扣费
            LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
            tLMRiskAppDB.setRiskCode(mLPPolSchema.getRiskCode());
            String tbonusflag = tLMRiskAppDB.query().get(1).getBonusFlag();
            if (tbonusflag.equals("1") || tbonusflag.equals("2")) {
                return TMoney;
            }
        }

        LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
        tLMEdorCalDB.setRiskCode(mLPPolSchema.getRiskCode());
        tLMEdorCalDB.setEdorType("ZT");
        tLMEdorCalDB.setCalType("CashValue");
        LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query();
        if (tLMEdorCalSet.size() != 1) {
            CError tError = new CError();
            tError.moduleName = "Calculator";
            tError.functionName = "executeSQL";
            tError.errorMessage = "查询现金价值算法表失败!";
            this.mErrors.addOneError(tError);
            return -1;
        }
        String tCalCode = tLMEdorCalSet.get(1).getCalCode();
        Calculator tCalculator = new Calculator();
        tCalculator.setCalCode(tCalCode);
        tCalculator.addBasicFactor("EdorNo", mLPGrpEdorItemSchema.getEdorNo());
        tCalculator.addBasicFactor("EdorValiDate", tEdorValiDate);
        tCalculator.addBasicFactor("PolNo", mLPPolSchema.getPolNo());
        tCalculator.addBasicFactor("EdorTypeCal",
                                   mLPGrpEdorItemSchema.getEdorTypeCal());
        tCalculator.addBasicFactor("CurrentMoney", String.valueOf(TMoney));
        String GAresult = tCalculator.calculate();
        logger.debug("sql:" + tCalculator.getCalSQL());
        logger.debug("result:" + GAresult);
        TMoney = Math.round((TMoney * Double.parseDouble(GAresult))*100)/100.0;
        logger.debug("TMoney=====" + TMoney);

        return TMoney;
    }

    /**
     * 计算162现价函数
     */
    private double calAccPrize(double TMoney,
                               String tEdorValiDate,
                               LPInsureAccClassSchema tLPInsureAccClassSchema) {
        if (mIfChecked != null && !mIfChecked.equals("") &&
            mIfChecked.equals("true")) { //转入公共帐户的话不扣费
            LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
            tLMRiskAppDB.setRiskCode(tLPInsureAccClassSchema.getRiskCode());
            String tbonusflag = tLMRiskAppDB.query().get(1).getBonusFlag();
            if (tbonusflag.equals("1") || tbonusflag.equals("2")) {
                return TMoney;
            }
        }
        if (TMoney == 0) {
            return TMoney; //帐户金额为0就不往下算了
        }

        LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
        String tSql = "Select * from LCInsureAccTrace where polno='"
                      + tLPInsureAccClassSchema.getPolNo() +
                      "' and payplancode='"
                      + tLPInsureAccClassSchema.getPayPlanCode() +
                      "' and payno='"
                      + tLPInsureAccClassSchema.getOtherNo() +
                      "' order by paydate";
        LCInsureAccTraceSet tLCInsureAccTraceSet = tLCInsureAccTraceDB.
                executeQuery(tSql);
        logger.debug(tSql);
        if (tLCInsureAccTraceSet.size() < 1) {
            mErrors.addOneError(new CError("查询帐户建立日期失败！"));
            return -1;
        }
        String tPayDate = tLCInsureAccTraceSet.get(1).getPayDate();
        String tGrpPolNo = tLCInsureAccTraceSet.get(1).getGrpPolNo();

        LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
        tLMEdorCalDB.setRiskCode(mLPPolSchema.getRiskCode());
        tLMEdorCalDB.setEdorType("ZT");
        tLMEdorCalDB.setCalType("BCashValue");
        LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query();
        if (tLMEdorCalSet.size() != 1) {
            CError tError = new CError();
            tError.moduleName = "Calculator";
            tError.functionName = "executeSQL";
            tError.errorMessage = "查询现金价值算法表失败!";
            this.mErrors.addOneError(tError);
            return -1;
        }
        String tCalCode = tLMEdorCalSet.get(1).getCalCode();
        Calculator tCalculator = new Calculator();
        tCalculator.setCalCode(tCalCode);
        tCalculator.addBasicFactor("EdorValiDate", tEdorValiDate);
        tCalculator.addBasicFactor("PayDate", tPayDate);
        tCalculator.addBasicFactor("GrpPolNo", tGrpPolNo);
        tCalculator.addBasicFactor("EdorTypeCal",
                                   mLPGrpEdorItemSchema.getEdorTypeCal());
        String GAresult = tCalculator.calculate();
        logger.debug("sql:" + tCalculator.getCalSQL());
        logger.debug("result:" + GAresult);
        TMoney =Math.round((TMoney * Double.parseDouble(GAresult))*100)/100.0;
        logger.debug("TMoney=====" + TMoney);

        return TMoney;
    }
    /**
     * 准备需要保存的数据
     */
    private boolean prepareData() {
	//for (int i = 1; i <= mLPGrpPolSet.size(); i++) {
	    double mInsuAccBonus = 0;
	    LOBonusPolHisDB tLOBonusPolHisDB = new LOBonusPolHisDB();
	    String sql = "select * from LOBonusPolHis a where PolNo = '"
			 + mLPPolSchema.getPolNo()
			 + "' and BonusGetMode = '2' and not exists (select 1 from LMRiskInsuAcc where InsuAccNo = a.InsuAccNo and Owner = '2') order by BonusDate";
	    LOBonusPolHisSet tLOBonusPolHisSet = tLOBonusPolHisDB.executeQuery(
		    sql);
	    logger.debug(sql);
	    if (tLOBonusPolHisSet.size() > 0) {
		for (int j = 1; j <= tLOBonusPolHisSet.size(); j++) {
		    LOBonusPolHisSchema tLOBonusPolHisSchema = new
			    LOBonusPolHisSchema();
		    tLOBonusPolHisSchema.setSchema(tLOBonusPolHisSet.get(j).
			    getSchema());
		    //调用红利结息函数
		    AccountManage tAccountManage = new AccountManage();
		    String tPeriod = "0";
		    String tRateType = "Y";
		    String tIntvType = "D";
		    double tInsuAccBonus = 0.0;
		    tInsuAccBonus = tAccountManage.getInsuAccBonus(
			    tLOBonusPolHisSchema,
			    mLPGrpEdorItemSchema.getEdorValiDate(), tPeriod,
			    tRateType, tIntvType, "F");
		    mInsuAccBonus += tInsuAccBonus;
		}
	    }
	    if (mInsuAccBonus > 0) { //团单下有未领的红利
		LJSGetEndorseSchema tLJSGetEndorseSchema = new
			LJSGetEndorseSchema();
		Reflections tRef = new Reflections();
		tRef.transFields(tLJSGetEndorseSchema, mLPPolSchema);
		tLJSGetEndorseSchema.setGetNoticeNo(mLPGrpEdorItemSchema.
			getEdorNo());
		tLJSGetEndorseSchema.setEndorsementNo(mLPGrpEdorItemSchema.
			getEdorNo());
		tLJSGetEndorseSchema.setGrpContNo(mLPGrpEdorItemSchema.
						  getGrpContNo());
		tLJSGetEndorseSchema.setGrpPolNo(mLPPolSchema.
						 getGrpPolNo());
		tLJSGetEndorseSchema.setPolNo(mLPPolSchema.getPolNo());
		tLJSGetEndorseSchema.setContNo(mLPPolSchema.getContNo());
		tLJSGetEndorseSchema.setInsuredNo(mLPPolSchema.getInsuredNo());
		tLJSGetEndorseSchema.setFeeOperationType("ZT");
		tLJSGetEndorseSchema.setGetDate(mLPGrpEdorItemSchema.
						getEdorValiDate());
		tLJSGetEndorseSchema.setPayPlanCode("00000000");
		tLJSGetEndorseSchema.setDutyCode("0");
		tLJSGetEndorseSchema.setOtherNo(mLPGrpEdorItemSchema.getEdorNo());
		tLJSGetEndorseSchema.setOtherNoType("3");
		tLJSGetEndorseSchema.setGetFlag("0");
		tLJSGetEndorseSchema.setRiskCode(mLPPolSchema.getRiskCode());
		tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
		tLJSGetEndorseSchema.setManageCom(mGlobalInput.ManageCom);
		tLJSGetEndorseSchema.setMakeDate(mCurrDate);
		tLJSGetEndorseSchema.setMakeTime(mCurrTime);
		tLJSGetEndorseSchema.setModifyDate(mCurrDate);
		tLJSGetEndorseSchema.setModifyTime(mCurrTime);
		tLJSGetEndorseSchema.setGetMoney( -1 * mInsuAccBonus);
		tLJSGetEndorseSchema.setFeeFinaType("HL");
		tLJSGetEndorseSchema.setSubFeeOperationType("HL");
		mLJSGetEndorseSet.add(tLJSGetEndorseSchema);
	    }
	return true;

    }
    private double calperfee(LPInsureAccClassSchema tLPInsureAccClassSchema) {
        double tper_fee = 0;
        String FeeCalCode;
        LCGrpFeeDB tLCGrpFeeDB = new LCGrpFeeDB();
        tLCGrpFeeDB.setGrpPolNo(tLPInsureAccClassSchema.getGrpPolNo());
        tLCGrpFeeDB.setFeeCode(tLPInsureAccClassSchema.getInsuAccNo());
//        tLCGrpFeeDB.setInsuAccNo(tLPInsureAccClassSchema.getInsuAccNo());
        tLCGrpFeeDB.setFeeCalMode("09");
        LCGrpFeeSet tLCGrpFeeSet = tLCGrpFeeDB.query();
        if (tLCGrpFeeSet.size() > 0) {
            tper_fee = tLCGrpFeeSet.get(1).getFeeValue();
            FeeCalCode = tLCGrpFeeSet.get(1).getFeeCalCode();
        } else {
            FeeCalCode = "01";
            tper_fee = 60.00; //暂定，防止没有定义时报空
        }
        String aBF = "";
        String dBF = "";
        ExeSQL tExeSQL = new ExeSQL();
        String aSql = "select to_date('" + mLPEdorItemSchema.getEdorValiDate() +
                      "','yyyy-mm-dd')-to_date('" + mLPPolSchema.getCValiDate() +
                      "','yyyy-mm-dd') from dual";
        aBF = tExeSQL.getOneValue(aSql);
        if (aBF == null || aBF.equals("")) {
            dBF = "0";
        }

        String bSql = "select to_date('" + mLPEdorItemSchema.getEdorValiDate() +
                      "','yyyy-mm-dd')-to_date((substr('" +
                      mLPEdorItemSchema.getEdorValiDate() +
                      "',1,4)||'-01-01'),'yyyy-mm-dd') from dual";
        dBF = tExeSQL.getOneValue(bSql);
        if (dBF == null || dBF.equals("")) {
            dBF = "0";
        }
        int mInterval = 0; //计算保单本年度经过几天
        if (Integer.parseInt(aBF) < Integer.parseInt(dBF)) {
            mInterval = Integer.parseInt(aBF);
        } else {
            mInterval = Integer.parseInt(dBF);
        }
        tper_fee = tper_fee * mInterval / 365;
        logger.debug("保单经过天数" + mInterval + "计算年费为" + tper_fee);
        if (tper_fee > 0 && FeeCalCode.equals("01")) { //从法人扣年费
            LCPolDB tLCPolDB = new LCPolDB();
            tLCPolDB.setGrpPolNo(tLPInsureAccClassSchema.getGrpPolNo());
            tLCPolDB.setPolTypeFlag("3");
            LCPolSet tLCPolSet = tLCPolDB.query();
            if (tLCPolSet.size() < 1) {
                FeeCalCode = "00"; //没有法人 就从个人扣
            } else {
                LCInsureAccTraceDB tLCInsureAccTraceDB = new
                        LCInsureAccTraceDB();
                tLCInsureAccTraceDB.setPolNo(tLCPolSet.get(1).getPolNo());
                LCInsureAccTraceSet tLCInsureAccTraceSet = tLCInsureAccTraceDB.
                        query();
                if (tLCInsureAccTraceSet.size() < 1) {
                    FeeCalCode = "00"; //法人下没钱就从个人扣
                } else {
                    LPInsureAccFeeTraceSchema tLPInsureAccFeeTraceSchema = new
                            LPInsureAccFeeTraceSchema();
                    Reflections tReflections = new Reflections();
                    tReflections.transFields(tLPInsureAccFeeTraceSchema,
                                             tLPInsureAccClassSchema);
                    String sLimit = PubFun.getNoLimit(mGlobalInput.
                            ManageCom);
                    String serNo = PubFun1.CreateMaxNo("SERIALNO", sLimit);
                    tLPInsureAccFeeTraceSchema.setSerialNo(serNo);
                    tLPInsureAccFeeTraceSchema.setEdorNo(mLPEdorItemSchema.
                            getEdorNo());
                    tLPInsureAccFeeTraceSchema.setEdorType(
                            mLPEdorItemSchema.getEdorType());
                    tLPInsureAccFeeTraceSchema.setContNo(tLCPolSet.get(1).
                            getContNo());
                    tLPInsureAccFeeTraceSchema.setPolNo(tLCPolSet.get(1).
                            getPolNo());
                    tLPInsureAccFeeTraceSchema.setOtherNo(
                            tLPInsureAccClassSchema.getPolNo()); //记录是那个polno产生的年费
                    tLPInsureAccFeeTraceSchema.setOtherType("1");
                    tLPInsureAccFeeTraceSchema.setMoneyType("NF");
                    tLPInsureAccFeeTraceSchema.setFee(tper_fee);
                    tLPInsureAccFeeTraceSchema.setPayDate(mLPEdorItemSchema.
                            getEdorValiDate());
                    tLPInsureAccFeeTraceSchema.setOperator(mGlobalInput.
                            Operator);
                    tLPInsureAccFeeTraceSchema.setMakeDate(mCurrDate);
                    tLPInsureAccFeeTraceSchema.setModifyDate(mCurrDate);
                    tLPInsureAccFeeTraceSchema.setMakeTime(mCurrTime);
                    tLPInsureAccFeeTraceSchema.setModifyTime(mCurrTime);
                    tLPInsureAccFeeTraceSchema.setPayNo(mLPEdorItemSchema.
                            getEdorNo());
                    mLPInsureAccFeeTraceSet.add(tLPInsureAccFeeTraceSchema);

                    LPInsureAccTraceSchema tLPInsureAccTraceSchema = new
                            LPInsureAccTraceSchema();
                    tReflections.transFields(tLPInsureAccTraceSchema,
                                             tLCInsureAccTraceSet.get(1));
                    serNo = PubFun1.CreateMaxNo("SERIALNO", sLimit);
                    tLPInsureAccTraceSchema.setSerialNo(serNo);
                    tLPInsureAccTraceSchema.setEdorNo(mLPEdorItemSchema.
                            getEdorNo());
                    tLPInsureAccTraceSchema.setEdorType(
                            mLPEdorItemSchema.getEdorType());
                    tLPInsureAccTraceSchema.setContNo(tLCPolSet.get(1).
                            getContNo());
                    tLPInsureAccTraceSchema.setPolNo(tLCPolSet.get(1).
                            getPolNo());
                    tLPInsureAccTraceSchema.setOtherNo(mLPEdorItemSchema.
                            getEdorNo());
                    tLPInsureAccTraceSchema.setOtherType("4");
                    tLPInsureAccTraceSchema.setMoneyType("NF");
                    tLPInsureAccTraceSchema.setMoney( -tper_fee);
                    tLPInsureAccTraceSchema.setPayDate(mLPEdorItemSchema.
                            getEdorValiDate());
                    tLPInsureAccTraceSchema.setOperator(mGlobalInput.
                            Operator);
                    tLPInsureAccTraceSchema.setMakeDate(mCurrDate);
                    tLPInsureAccTraceSchema.setModifyDate(mCurrDate);
                    tLPInsureAccTraceSchema.setMakeTime(mCurrTime);
                    tLPInsureAccTraceSchema.setModifyTime(mCurrTime);
                    tLPInsureAccTraceSchema.setPayNo(mLPEdorItemSchema.
                            getEdorNo());
                    tLPInsureAccTraceSchema.setAccAlterNo(
                            tLPInsureAccClassSchema.getPolNo()); //记录是那个polno产生的年费
                    tLPInsureAccTraceSchema.setAccAlterType("1");
                    mLPInsureAccTraceSet.add(tLPInsureAccTraceSchema);
                    tper_fee = 0;
                }
            }
        }
        if (tper_fee > 0 && FeeCalCode.equals("00")) { //从个人收取年费
            LPInsureAccFeeTraceSchema tLPInsureAccFeeTraceSchema = new
                    LPInsureAccFeeTraceSchema();
            Reflections tReflections = new Reflections();
            tReflections.transFields(tLPInsureAccFeeTraceSchema,
                                     tLPInsureAccClassSchema);
            String sLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
            String serNo = PubFun1.CreateMaxNo("SERIALNO", sLimit);
            tLPInsureAccFeeTraceSchema.setSerialNo(serNo);
            tLPInsureAccFeeTraceSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
            tLPInsureAccFeeTraceSchema.setEdorType(mLPEdorItemSchema.
                    getEdorType());
            tLPInsureAccFeeTraceSchema.setOtherNo(mLPEdorItemSchema.getEdorNo());
            tLPInsureAccFeeTraceSchema.setOtherType("4");
            tLPInsureAccFeeTraceSchema.setMoneyType("NF");
            tLPInsureAccFeeTraceSchema.setFee(tper_fee);
            tLPInsureAccFeeTraceSchema.setPayDate(mLPEdorItemSchema.
                                                  getEdorValiDate());
            tLPInsureAccFeeTraceSchema.setOperator(mGlobalInput.Operator);
            tLPInsureAccFeeTraceSchema.setMakeDate(mCurrDate);
            tLPInsureAccFeeTraceSchema.setModifyDate(mCurrDate);
            tLPInsureAccFeeTraceSchema.setMakeTime(mCurrTime);
            tLPInsureAccFeeTraceSchema.setModifyTime(mCurrTime);
            tLPInsureAccFeeTraceSchema.setPayNo(mLPEdorItemSchema.getEdorNo());
            mLPInsureAccFeeTraceSet.add(tLPInsureAccFeeTraceSchema);
        }

        return tper_fee;
    }
}
