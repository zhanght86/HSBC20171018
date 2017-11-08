package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.bq.BqCalBL;
import com.sinosoft.lis.db.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import java.text.DecimalFormat;

/**
 * <p>Title: 保全项目报单复效明细</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Sinosoft</p>
 *
 * @author lizhuo
 * @version 1.0
 */
public class PEdorREDetailBL {
private static Logger logger = Logger.getLogger(PEdorREDetailBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();

    /** 往界面传输数据的容器 */
    private VData mResult = new VData();

    /** 数据操作字符串 */
    private String mOperate;

    private BqCalBase mBqCalBase = new BqCalBase();

    private Reflections mRef = new Reflections();

    /** 全局数据 */
    private MMap map = new MMap();
//    private LCContSchema mLCContSchema = new LCContSchema();
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
    private GlobalInput mGlobalInput = new GlobalInput();
    private LCPolSchema mLCPolSchema = new LCPolSchema();
    private LPPolSet mLPPolSet = new LPPolSet();
    private Reflections mReflections = new Reflections();
    private LCDutySet mLCDutySet = new LCDutySet();
    private LCPremSet mLCPremSet = new LCPremSet();
    private LCPremSet aLCPremSet = new LCPremSet();
    private LJSGetEndorseSchema mLJSGetEndorseSchema = new LJSGetEndorseSchema();
    private LJSGetEndorseSet aLJSGetEndorseSet = new LJSGetEndorseSet();
//    private LJSPaySchema mLJSPaySchema = new LJSPaySchema();
//    private LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
//    private LPCustomerImpartSet mLPCustomerImpartSet = new LPCustomerImpartSet();
//    private LPCustomerImpartParamsSet mLPCustomerImpartParamsSet = new
//            LPCustomerImpartParamsSet();
    private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
//    private LPContStateSet mLPContStateSet = new LPContStateSet();
    private LPDutySet mLPDutySet = new LPDutySet();
    private LPPremSet mLPPremSet = new LPPremSet();

    private String mPaytoDate;
    private BqCalBase tBqCalBase = new BqCalBase();
    private String mStartDate;


    //delete by JL at 2004-8-25,不走续期催收流程,走保全交费流程
    //private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
    private LJSPayPersonSet aLJSPayPersonSet = new LJSPayPersonSet();
    double mGetMoney = 0;
    double mTotalPrem = 0;
    double mPrem = 0;
    double mTotalInterest = 0;
    double mInterest = 0;
    double mAddJK = 0;
    double mAddZY = 0;
    double mAutoPay = 0;
    double mYearGet = 0;
    private String mPayToDate = "";
    private String mLastPayToDate = "";
    private String mLapseDate = "";
    private int mPayCount;
    double mRate = 0;
    private int tPaySTime;
    private String aunit = "";
    private String mPolNo;
    /** 计息类型 M-按月计 D-按天 */
    private String mIntervalType; //add by zhangtao 2006-06-26
    /** 利率取值类型 S-静态 D-动态 */
    private String mIntervalRateType; //add by zhangtao 2006-06-26

    /**
     * 数据提交的公共方法
     * @param: cInputData 传入的数据
     *		  cOperate 数据操作字符串
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate) {
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;
        logger.debug("===p=====RE=====start=====");
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData()) {
            return false;
        }

        //数据处理
        if (!dealData()) {
            return false;
        }

        //数据准备操作
        if (!prepareData()) {
            return false;
        }
        logger.debug("===p=====RE=====end=====");
        return true;
    }

    /**
     * 从输入数据中得到所有对象
     * @return boolean
     */
    private boolean getInputData() {
        try {
            mLPEdorItemSchema = (LPEdorItemSchema) mInputData
                                .getObjectByObjectName("LPEdorItemSchema", 0);
            mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
                    "GlobalInput", 0);
//            mLCCustomerImpartSet = (LCCustomerImpartSet) mInputData.
//                                   getObjectByObjectName(
//                                           "LCCustomerImpartSet", 0);
        } catch (Exception e) {
            CError.buildErr(this, "接收数据失败");
            mErrors.addOneError("接收数据失败!");
            return false;
        }
        if (mLPEdorItemSchema == null) {
            mErrors.addOneError("接受数据无效!");
            return false;
        }

        if (!"NOQUERY".equals(mOperate)) {
            LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
            LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
            tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
            tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
            tLPEdorItemSet = tLPEdorItemDB.query();
            if (tLPEdorItemSet == null || tLPEdorItemSet.size() == 0) {
                CError.buildErr(this, "查询保全项目信息失败!");
                return false;
            }
            mLPEdorItemSchema = tLPEdorItemSet.get(1);
        }

//        LCContDB tLCContDB = new LCContDB();
//        tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
//        if (!tLCContDB.getInfo()) {
//            mErrors.addOneError("查询保单信息失败!");
//            return false;
//        }
//        mLCContSchema = tLCContDB.getSchema();

//        mIntervalType = EdorVerifyBL.getInterestCalType(mLPEdorItemSchema.getEdorType());  //add by zhangtao 2006-06-26 获取利息计算类型 M-按月计 D-按天
//        mIntervalRateType = EdorVerifyBL.getInterestRateType(mLPEdorItemSchema.getEdorType());  //add by zhangtao 2006-06-26 获取利率取值方式 D-动态 S-静态

        return true;
    }

    public VData getResult() {
        return mResult;
    }

    /**
     * 数据计算处理
     * @return boolean
     */
    private boolean dealData() {
        // 取得保单信息(不处理保全变动带来的影响)
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
        LCPolSet tLCPolSet = tLCPolDB.query();
        if (tLCPolSet.size() < 1) {
            CError.buildErr(this, "查询个人险种信息失败!");
            return false;
        }

        //按险种层次进行处理
//        for (int j = 1; j <= tLCPolSet.size(); j++) {
        mLCPolSchema = tLCPolSet.get(1);
        mPolNo = mLPEdorItemSchema.getPolNo();
        //取得个人应收表信息
//        String str = "select min(paycount) from ljspayperson where PolNo='"
//                     + mLCPolSchema.getPolNo() +
//                     "' and edorno is null order by paycount";
//        ExeSQL ts = new ExeSQL();
//        LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
//        LJSPayPersonDB dLJSPayPersonDB = new LJSPayPersonDB();
//        LJSPayPersonSet dLJSPayPersonSet = new LJSPayPersonSet();
//        String sqlPay = "select * from LJSPayPerson where polno = '" +
//                        mLCPolSchema.getPolNo() + "' and PayCount = " +
//                        ts.getOneValue(str)
//                        + " and substr(trim(PayPlanCode),1,6) <> '000000'";
//        logger.debug(sqlPay);
//        String sqlPay2 = "select * from LJSPayPerson where polno = '" +
//                         mLCPolSchema.getPolNo() + "' and PayCount = " +
//                         ts.getOneValue(str)
//                         + " and substr(trim(PayPlanCode),1,6) = '000000'";
//        logger.debug(sqlPay2);
//        aLJSPayPersonSet = tLJSPayPersonDB.executeQuery(sqlPay +
//                " and paytype = 'ZC'");
//        if (aLJSPayPersonSet.size() < 1) {
//            aLJSPayPersonSet = tLJSPayPersonDB.executeQuery(sqlPay +
//                    " and paytype = 'RE'");
//        }
//        if (aLJSPayPersonSet.size() < 1) {
//            CError.buildErr(this, "该保单没有首期催收数据,不能复效!(LJSPayPerson)");
//            return false;
//        }
//        dLJSPayPersonSet = dLJSPayPersonDB.executeQuery(sqlPay2 +
//                " and paytype = 'ZC'");
//        if (dLJSPayPersonSet != null && dLJSPayPersonSet.size() > 0) {
//            aLJSPayPersonSet.add(dLJSPayPersonSet);
//        } else {
//            dLJSPayPersonSet = dLJSPayPersonDB.executeQuery(sqlPay2 +
//                    " and paytype = 'RE'");
//            if (dLJSPayPersonSet != null && dLJSPayPersonSet.size() > 0) {
//                aLJSPayPersonSet.add(dLJSPayPersonSet);
//            }
//        }
//        for (int i = 1; i <= aLJSPayPersonSet.size(); i++) {
//            aLJSPayPersonSet.get(i).setAgentCode(mLCPolSchema.getAgentCode());
//            aLJSPayPersonSet.get(i).setAgentCom(mLCPolSchema.getAgentCom());
//            aLJSPayPersonSet.get(i).setAgentGroup(mLCPolSchema.getAgentGroup());
//        }
        LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
        String sql = "select * from LJSPayPerson where polno='" +
                     mLCPolSchema.getPolNo() +
                     "' and paytype='ZC' and edorno is null order by paycount";
        logger.debug(sql);
        aLJSPayPersonSet = tLJSPayPersonDB.executeQuery(sql);
        if (aLJSPayPersonSet.size() < 1) {
            mErrors.addOneError("没有查到该失效保单下有未交的催收数据！");
            return false;
        }
        LJSPayPersonSchema tLJPayPersonSchema = new LJSPayPersonSchema();
        tLJPayPersonSchema = aLJSPayPersonSet.get(1);
        tLJPayPersonSchema.setPayType("RE");
        mPayCount = tLJPayPersonSchema.getPayCount(); //作为个人应收表标记使用

//        mLPEdorItemSchema.setStandbyFlag2(String.valueOf(mPayCount));
//        //供回退使用
//        mLPEdorItemSchema.setStandbyFlag3(tLJPayPersonSchema.getGetNoticeNo());

        // 取得保单责任信息
        LCDutyDB tLCDutyDB = new LCDutyDB();
        tLCDutyDB.setPolNo(mLCPolSchema.getPolNo());
        mLCDutySet = tLCDutyDB.query();
        if (mLCDutySet.size() < 1) {
            mErrors.addOneError("没有保单责任信息");
            return false;
        }

        // 取得当前所有保费项信息（每期总的交费金额）mGetMoney
        if (!setReceivablePrem(mLCPolSchema.getPolNo())) {
            return false;
        }
        logger.debug("每期总的交费金额" + mGetMoney);
        //校验首期催收数据是否和应交保费相等
//        double aGetMoney = 0;
//        for (int k = 1; k <= aLJSPayPersonSet.size(); k++) {
//            aGetMoney += aLJSPayPersonSet.get(k).getSumDuePayMoney();
//        }
//        if (mGetMoney != aGetMoney) {
//            mErrors.addOneError("催收数据与应收保费不符,不能进行复效!");
//            CError.buildErr(this, "催收数据与应收保费不符,不能进行复效!");
//            return false;
//        }

        tPaySTime = 0; //所欠的期数
        int tPayIntv = mLCPolSchema.getPayIntv();
        if (tPayIntv == 1) {
            aunit = "M";
        } else if (tPayIntv == 12) {
            aunit = "Y";
        } else {
            CError.buildErr(this, "交费间隔错误!");
            return false;
        }
        FDate aFDate = new FDate();
        if (aFDate.getDate(mLPEdorItemSchema.getEdorValiDate())
            .before(aFDate.getDate(mLCPolSchema.getPayEndDate()))) {
            logger.debug(mLCPolSchema.getPaytoDate());
            logger.debug(mLPEdorItemSchema.getEdorValiDate());
            tPaySTime += PubFun.calInterval2(mLCPolSchema.getPaytoDate(),
                                             mLPEdorItemSchema.
                                             getEdorValiDate(),
                                             aunit);
        } else {
            tPaySTime += PubFun.calInterval2(mLCPolSchema.getPaytoDate(),
                                             mLCPolSchema.getPayEndDate(),
                                             aunit);
        }
        logger.debug("所欠的期数" + tPaySTime);
        //先计算个人应收表的应收的第一期欠交的费用
        FDate tFDate = new FDate();
        mPayToDate = mLCPolSchema.getPaytoDate();
        //计算宽限期止期(交至日期的)——只有第一期欠费才有宽限期
//            String tLapseDate = EdorCalZT.CalLapseDate(mLCPolSchema.getRiskCode(),
//                    tPayToDate);

        String tLapseDate = PubFun.calDate(mLCPolSchema.getPaytoDate(), 60,
                                           "D", null);
        mStartDate = tLapseDate;

        mLapseDate = tLapseDate;
//        if (tFDate.getDate(tLapseDate).before(tFDate.getDate(
//                mLPEdorItemSchema.getEdorValiDate())) &&
//            tFDate.getDate(tLapseDate).before(tFDate.getDate(
//                    mLCPolSchema.getPayEndDate()))) {
//	    logger.debug("hhhhhhhhhhhhhjjjjjjjjjjjjjj");
//            mPrem = mGetMoney - mAddJK - mAddZY;
//            mTotalPrem += mPrem;
//            mInterest = calLJSGetInterest(mLCPolSchema.getPayIntv());
//            mTotalInterest += mInterest;
//            //产生批改补退费信息
//            if (!createLJSGetEndorseSchema()) {
//                mErrors.addOneError("产生批改补退项失败!");
//                CError.buildErr(this, "产生批改补退项失败!");
//                return false;
//            }
//            //首期个人应收表信息处理
//            for (int k = 1; k <= aLJSPayPersonSet.size(); k++) {
//                LJSPayPersonSchema tLJSPayPersonSchema = new
//                        LJSPayPersonSchema();
//                mRef.transFields(tLJSPayPersonSchema,
//                                 aLJSPayPersonSet.get(k));
//                tLJSPayPersonSchema.setPayDate(mLPEdorItemSchema.
//                                               getEdorValiDate());
//                tLJSPayPersonSchema.setPayType("RE");
//                tLJSPayPersonSchema.setGetNoticeNo(mLPEdorItemSchema.
//                        getEdorNo());
//                tLJSPayPersonSchema.setBankAccNo("");
//                tLJSPayPersonSchema.setBankCode("");
//                tLJSPayPersonSchema.setOperator(mGlobalInput.Operator);
//                tLJSPayPersonSchema.setModifyDate(PubFun.getCurrentDate());
//                tLJSPayPersonSchema.setModifyTime(PubFun.getCurrentTime());
//                mLJSPayPersonSet.add(tLJSPayPersonSchema);
//
//                if (!this.createLJSGetEndorseSchema(tLJSPayPersonSchema)) {
//                    return false;
//                }
//            }
//        } else {
//            CError.buildErr(this, "该保单不能复效!可能未超过宽限期止期或达到终交日期");
//            return false;
//        }
        //如果有多期欠交，计算各期的欠费信息
        if (tPaySTime > 0) {
            logger.debug("计算各期的欠费信息");
            mTotalPrem = mGetMoney * tPaySTime;
            for (int i = 1; i <= tPaySTime; i++) {
//                mTotalPrem += mPrem;
                mInterest = calTimeGetInterest(mLCPolSchema.getPayIntv(),
                                               i);
                mTotalInterest += mInterest;

                //产生个人应收表信息
                if (!createLJSPayPersonSchema(i)) {
                    mErrors.addOneError("产生个人应收表信息失败!");
                    return false;
                }
            }
            if (!this.createLJSGetEndorseSchema(mLJSPayPersonSet.get(1))) {
                return false;
            }

            //产生批改补退费信息LX
            if (!createLJSGetEndorseSchema()) {
                CError.buildErr(this, "产生批改补退项失败!");
                return false;
            }

        }

        //计算新的交至日期
        mPayToDate = PubFun.calDate(mLCPolSchema.getPaytoDate(), tPaySTime,
                                    aunit, null);
        logger.debug("新的交至日期" + mPayToDate);
        this.initDueLCPrem();
        this.initDueLCDuty();
        this.initDueLCPol();

        LPPolSchema tLPPolSchema = new LPPolSchema();
        mReflections.transFields(tLPPolSchema, mLCPolSchema);
        tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
        tLPPolSchema.setLastRevDate(mLPEdorItemSchema.getEdorValiDate());
        tLPPolSchema.setModifyDate(PubFun.getCurrentDate());
        tLPPolSchema.setModifyTime(PubFun.getCurrentTime());
        mLPPolSet.add(tLPPolSchema);

        for (int i = 1; i <= mLCDutySet.size(); i++) {
            LPDutySchema tLPDutySchema = new LPDutySchema();
            mReflections.transFields(tLPDutySchema, mLCDutySet.get(i));
            tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
            tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
            tLPDutySchema.setModifyDate(PubFun.getCurrentDate());
            tLPDutySchema.setModifyTime(PubFun.getCurrentTime());
            mLPDutySet.add(tLPDutySchema);
        }

        for (int i = 1; i <= aLCPremSet.size(); i++) {
            LPPremSchema tLPPremSchema = new LPPremSchema();
            mReflections.transFields(tLPPremSchema, aLCPremSet.get(i));
            tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
            tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
	    tLPPremSchema.setModifyDate(PubFun.getCurrentDate());
            tLPPremSchema.setModifyTime(PubFun.getCurrentTime());
            mLPPremSet.add(tLPPremSchema);
        }

        //删除此险种LASPayPerson中记录
//            String sDelete = "delete from laspayperson where 1=1 "
//                       + " and polno = '" + aLJSPayPersonSet.get(1).getPolNo() + "'"
//                       + " and paycount = '" + mLPEdorItemSchema.getStandbyFlag2() + "'"
//                       + " and paytype = 'ZC'";
//            map.put(sDelete,"DELETE");
//        }
        map.put(mLPPolSet, "DELETE&INSERT");
        map.put(mLPDutySet, "DELETE&INSERT");
        map.put(mLPPremSet, "DELETE&INSERT");

        //计算贷款
//        BqPolBalBL aBqPolBalBL = new BqPolBalBL();
////            aBqPolBalBL.calAutoPayPremAddInterest(mPolNo,
////                                                  mLPEdorItemSchema.
////                                                  getEdorValiDate());
//        aBqPolBalBL.calContLoanAndAutoPay(mLPEdorItemSchema.getContNo(),
//                                          mLPEdorItemSchema.getEdorValiDate());
//        mAutoPay += aBqPolBalBL.getCalResultRound();

        //健康告知处理
        /*       if (mLCCustomerImpartSet != null && mLCCustomerImpartSet.size() > 0) {
                   LCCustomerImpartSchema aLCCustomerImpartSchema = new
                           LCCustomerImpartSchema();
                   for (int k = 1; k <= mLCCustomerImpartSet.size(); k++) {
                       aLCCustomerImpartSchema = mLCCustomerImpartSet.get(k);
         aLCCustomerImpartSchema.setGrpContNo(mLCContSchema.getGrpContNo());
                       if (mLCContSchema.getGrpContNo() == null) {
         aLCCustomerImpartSchema.setGrpContNo("00000000000000000000");
                       }
         aLCCustomerImpartSchema.setPrtNo(mLCContSchema.getPrtNo());
                       if (mLCContSchema.getPrtNo() == null) {
                           mErrors.addOneError("个人保单印刷号码查询失败!");
                           return false;
                       }
                       mLCCustomerImpartSet.set(k, aLCCustomerImpartSchema);
                   }
               }

         if (mLCCustomerImpartSet != null && mLCCustomerImpartSet.size() > 0) {
                           VData cVData = new VData();
                           cVData.add(mLCCustomerImpartSet);
                           cVData.add(mGlobalInput);
                CustomerImpartBL tCustomerImpartBL = new CustomerImpartBL();
         tCustomerImpartBL.submitData(cVData, "IMPART||DEAL");
                           mErrors.copyAllErrors(tCustomerImpartBL.mErrors);
                           if (tCustomerImpartBL.mErrors.needDealError()) {

                               CError tError = new CError();
                               tError.moduleName = "ContInsuredBL";
                               tError.functionName = "dealData";
         tError.errorMessage = tCustomerImpartBL.mErrors.getFirstError().
                                                     toString();
                               this.mErrors.addOneError(tError);
                               return false;
                           }
                           VData tempVData = new VData();
                           tempVData = tCustomerImpartBL.getResult();
         LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
         LCCustomerImpartParamsSet tLCCustomerImpartParamsSet = new
                                   LCCustomerImpartParamsSet();
                           try {
         tLCCustomerImpartSet = (LCCustomerImpartSet) tempVData
                                                      .getObjectByObjectName(
                "LCCustomerImpartSet", 0);
                tLCCustomerImpartParamsSet = (LCCustomerImpartParamsSet)
                                                            tempVData
         .getObjectByObjectName(
                                       "LCCustomerImpartParamsSet", 0);
                           } catch (Exception e) {
                               CError.buildErr(this, "接受数据失败!");
                               return false;
                           }
                           Reflections tRef = new Reflections();
         LPCustomerImpartParamsSchema tLPCustomerImpartParamsSchema = new
                                   LPCustomerImpartParamsSchema();
                           LPCustomerImpartSchema tLPCustomerImpartSchema = new
                                   LPCustomerImpartSchema();
         for (int i = 1; i <= tLCCustomerImpartSet.size(); i++) {
         tLPCustomerImpartSchema = new LPCustomerImpartSchema();
                               tRef.transFields(tLPCustomerImpartSchema,
                                                tLCCustomerImpartSet.get(i));
         tLPCustomerImpartSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
         tLPCustomerImpartSchema.setEdorType(mLPEdorItemSchema.
                                       getEdorType());
                tLPCustomerImpartSchema.setGrpContNo("00000000000000000000");
                tLPCustomerImpartSchema.setPrtNo(mLCContSchema.getPrtNo());
                               if (mLCContSchema.getPrtNo() == null) {
                                   mErrors.addOneError("个人保单印刷号码查询失败!");
                                   return false;
                               }
         mLPCustomerImpartSet.add(tLPCustomerImpartSchema);
                           }

                for (int i = 1; i <= tLCCustomerImpartParamsSet.size(); i++) {
                               tLPCustomerImpartParamsSchema = new
                LPCustomerImpartParamsSchema();
                               tRef.transFields(tLPCustomerImpartParamsSchema,
         tLCCustomerImpartParamsSet.get(i));

                tLPCustomerImpartParamsSchema.setEdorNo(mLPEdorItemSchema.
                                       getEdorNo());
                tLPCustomerImpartParamsSchema.setEdorType(mLPEdorItemSchema.
                                       getEdorType());
                               tLPCustomerImpartParamsSchema.setGrpContNo(
                                       "00000000000000000000");
         tLPCustomerImpartParamsSchema.setPrtNo(mLCContSchema.getPrtNo());
                               if (mLCContSchema.getPrtNo() == null) {
                                   mErrors.addOneError("个人保单印刷号码查询失败!");
                                   return false;
                               }
                mLPCustomerImpartParamsSet.add(tLPCustomerImpartParamsSchema);
                           }
                           map.put(mLPCustomerImpartSet, "DELETE&INSERT");
         map.put(mLPCustomerImpartParamsSet, "DELETE&INSERT");
                       }
         */
        mResult.clear();
        mTotalPrem = Double.parseDouble(new DecimalFormat("0.00").format(
                mTotalPrem));
        mTotalInterest = Double.parseDouble(new DecimalFormat("0.00").format(
                mTotalInterest));

        TransferData tTransferData = new TransferData();
//        tTransferData.setNameAndValue("PayTime", tPaySTime);
//        tTransferData.setNameAndValue("Prem", mTotalPrem);
//        tTransferData.setNameAndValue("AddJK", mAddJK * tPaySTime);
//        tTransferData.setNameAndValue("AddZY", mAddZY * tPaySTime);
//        tTransferData.setNameAndValue("AutoPay", mAutoPay);
//        tTransferData.setNameAndValue("Interest", mTotalInterest);
//        tTransferData.setNameAndValue("YearGet", mYearGet);
//        tTransferData.setNameAndValue("Total",
//                                      mTotalPrem + mTotalInterest +
//                                      mAddJK * tPaySTime + mAddZY * tPaySTime);
        mResult.add(tTransferData);
        return true;
    }

    /**
     * 得到应收保费项
     * @param aPolNo
     * @return boolean
     */
    private boolean setReceivablePrem(String aPolNo) {
        LCPremSet tLCPremSet = new LCPremSet();
        LCPremDB tLCPremDB = new LCPremDB();
        String i_sql = "select * from LCPrem where PolNo = '" + aPolNo +
                       "' and "
                       + "PayStartDate <= to_date('"
                       + mLCPolSchema.getPaytoDate() +
                       "','YYYY-MM-DD') and PayEndDate >= to_date('"
                       + mLCPolSchema.getPaytoDate() +
                       "','YYYY-MM-DD') and UrgePayFlag = 'Y'";
        logger.debug(i_sql);
        tLCPremSet = tLCPremDB.executeQuery(i_sql);
        if (tLCPremSet.size() < 1) {
            mErrors.addOneError("保费项表中没有对应催交信息!");
            return false;
        }
        mGetMoney = 0;
        mAddJK = 0;
        mAddZY = 0;
        aLCPremSet.clear();
        for (int i = 1; i <= tLCPremSet.size(); i++) {
            LCPremSchema tLCPremSchema = new LCPremSchema();
            tLCPremSchema = tLCPremSet.get(i);

            LCDutyDB tLCDutyDB = new LCDutyDB();
            tLCDutyDB.setPolNo(tLCPremSchema.getPolNo());
            tLCDutyDB.setDutyCode(tLCPremSchema.getDutyCode());
            if (!tLCDutyDB.getInfo()) {
                CError.buildErr(this, "查询责任表失败!");
                return false;
            } else {
                aLCPremSet.add(tLCPremSchema);
                if ((tLCDutyDB.getFreeFlag() != null) &&
                    !tLCDutyDB.getFreeFlag().equals("1") &&
                    (tLCDutyDB.getFreeRate() < 1) &&
                    (tLCDutyDB.getFreeRate() > 0)) {
                    FDate cFDate = new FDate();
                    if (cFDate.getDate(mLCPolSchema.getPaytoDate()).before(
                            cFDate.getDate(tLCDutyDB.getFreeEndDate()))) {
                        tLCPremSchema.setPrem(tLCPremSchema.getPrem() * (1 -
                                tLCDutyDB.getFreeRate()));
                    }
                }
                if (tLCPremSchema.getPrem() > 0) {
                    mGetMoney += tLCPremSchema.getPrem();
                    mLCPremSet.add(tLCPremSchema);
                }
            }
        }
        if (mLCPremSet.size() == 0) { //如果过滤后的保费项表纪录数=0
            this.mErrors.addOneError("查询保险责任表失败，原因是:都为免交");
            return false;
        }
        return true;
    }

    /**
     * 个人应收表中的应收费用复效利息计算
     * 只针对年交和月交
     * @param aCalType int 计算利息的类型
     * @return double
     */
    private double calLJSGetInterest(int iPayIntv) {
        double aGetInterest = 0;
        double aRate = 0.0;
        String sIntervalType = "";
        String sIntvFlag = "";
        if (iPayIntv == 1) {
            sIntvFlag = "M"; //月交
            sIntervalType = "D"; //原需求:月交保单按日计息
        } else if (iPayIntv == 12) {
            sIntvFlag = "Y"; //年交
            sIntervalType = "M"; //原需求:年交保单按月计息
        } else if (iPayIntv == 3) {
            sIntvFlag = "Y"; //季交与年交计算方式一致
            sIntervalType = "M"; //原需求:季交保单按月计息
        } else {
            CError.buildErr(this, "交费间隔错误!");
            return -2;
        }

//===add===zhangtao===2006-06-26===获取利息计算类型 M-按月计 D-按天=================BGN============
        if (mIntervalType != null && mIntervalType.equals("D")) {
            sIntervalType = "D"; //如果需要按日计息，所有缴费间隔都按日计息
        }
//===add===zhangtao===2006-06-26===获取利息计算类型 M-按月计 D-按天=================END============

//        LMEdorCalDB aLMEdorCalDB = new LMEdorCalDB();
//        LMEdorCalSet aLMEdorCalSet = new LMEdorCalSet();
//        aLMEdorCalDB.setRiskCode(mLCPolSchema.getRiskCode());
//        aLMEdorCalDB.setDutyCode(mLCDutySet.get(1).getDutyCode());
//        aLMEdorCalDB.setCalType("BonusRate");
//        aLMEdorCalSet = aLMEdorCalDB.query();
//        if (aLMEdorCalDB.mErrors.needDealError()) {
//            mErrors.copyAllErrors(aLMEdorCalDB.mErrors);
//            mErrors.addOneError("查询险种责任保全计算红利率信息失败");
//        }
//        double aBonusRate = 0;
//        if (aLMEdorCalSet.size() >= 1) {
//            BqCalBase aBqCalBase = new BqCalBase();
//            aBqCalBase.setEdorValiDate(mStartDate);
//            aBqCalBase.setPayToDate(mPaytoDate);
//            aBqCalBase.setForceDVFlag("1");
//            aBqCalBase.setPayEndYear(mLCPolSchema.getPayEndYear());
//            aBqCalBase.setPayIntv(mLCPolSchema.getPayIntv());
//            BqCalBL aBqCalBL = new BqCalBL();
//            aBonusRate = aBqCalBL.calChgMoney(aLMEdorCalSet.get(1).
//                                              getCalCode(),
//                                              aBqCalBase);
//        }

        LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
        tLMEdorCalDB.setRiskCode(mLCPolSchema.getRiskCode());
        tLMEdorCalDB.setEdorType("RE");
        tLMEdorCalDB.setCalType("LX");

        LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query(); //查找保费累计变动金额计算信息
        if (tLMEdorCalDB.mErrors.needDealError()) {
            mErrors.copyAllErrors(tLMEdorCalDB.mErrors);
            mErrors.addOneError(new CError("查询险种责任保全计算信息失败！"));
            return -1;
        }
        tBqCalBase.setStartDate(mStartDate);
//        tBqCalBase.setBonusRate(aBonusRate);
        if (tLMEdorCalSet == null ||
            tLMEdorCalSet.size() <= 0) {
            return -1; //没有计算信息，则不作计算
        } else {
            BqCalBL tBqCalBL = new BqCalBL();
            double tValue = tBqCalBL.calChgMoney(tLMEdorCalSet.get(1).
                                                 getCalCode(), tBqCalBase); // 计算各险种保费补退费
            if (tBqCalBL.mErrors.needDealError()) {
                mErrors.copyAllErrors(tBqCalBL.mErrors);
                mErrors.addOneError(new CError("计算利息失败!"));
                return -2;
            }
            aRate = tValue;
        }
        if (aRate == 0) { //未查到利率信息，可返回
            mErrors.addOneError("计算利率失败!");
            return -2;
        }
//        if (mIntervalRateType != null && mIntervalRateType.equals("D")) { //动态利率计息
            AccountManage tAccountManage = new AccountManage(String.valueOf(
                    mLCPolSchema.getPayEndYear()));
            if (!tAccountManage.calInterest(mLPEdorItemSchema.getEdorType(),
                                            mGetMoney,
                                            mLCPolSchema.getPaytoDate(),
                                            mLPEdorItemSchema.getEdorValiDate(),
                                            mLCPolSchema.getRiskCode(),
                                            sIntvFlag)
                    ) {
                mErrors.addOneError("计算利息失败!");
                return -2;
            }
            aGetInterest = tAccountManage.getCalResult();
//        } else { //静态利率计息
//            aGetInterest = AccountManage.getMultiAccInterest(aRate,
//                    mGetMoney,
//                    PubFun.calDate(mLapseDate, 1, "D", null),
//                    mLPEdorItemSchema.getEdorValiDate(), "C", sIntervalType);
//        }
        logger.debug("记利开始日期：" +
                           PubFun.calDate(mLapseDate, 1, "D", mLastPayToDate));
        logger.debug("本次利息为：" + aGetInterest);
        return aGetInterest;
    }

    /**
     * 第几期复效利息计算
     * @param aCalType int 计算利息的类型
     * @param aSPayTime int 第几期(从第二期开始)
     * @return
     */
    private double calTimeGetInterest(int iPayIntv, int iPayTime) {
        double aGetInterest = 0;
        double aRate = 0.0;
        String tIntervalType = "";
        String sStartDate = "";
        String sPayintvFlag = "";
        if (iPayIntv == 12) {
            sPayintvFlag = "Y"; //代表年交
            tIntervalType = "M"; //原需求:年交保单按月计息
            sStartDate = PubFun.calDate(mLCPolSchema.getPaytoDate(), iPayTime,
                                        "Y", null);
            mStartDate = PubFun.calDate(mPayToDate, iPayTime, "Y", null);
        }
        if (iPayIntv == 3) {
            sPayintvFlag = "Y"; //代表年交  季交计算与年交一致
            tIntervalType = "M"; //原需求:季交保单按月计息
            sStartDate = PubFun.calDate(mLCPolSchema.getPaytoDate(),
                                        3 * iPayTime, "M", null);
            mStartDate = PubFun.calDate(mPayToDate, 3 * iPayTime, "M", null);
        } else if (iPayIntv == 1) {
            sPayintvFlag = "M"; //代表年交
            tIntervalType = "D"; //原需求:月交保单按日计息
            sStartDate = PubFun.calDate(mLCPolSchema.getPaytoDate(), iPayTime,
                                        "M", null);
            mStartDate = PubFun.calDate(mPayToDate, iPayTime, "M", null);
        }

//        LMEdorCalDB aLMEdorCalDB = new LMEdorCalDB();
//        LMEdorCalSet aLMEdorCalSet = new LMEdorCalSet();
//        aLMEdorCalDB.setRiskCode(mLCPolSchema.getRiskCode());
//        aLMEdorCalDB.setDutyCode(mLCDutySet.get(1).getDutyCode());
//        aLMEdorCalDB.setCalType("BonusRate");
//        aLMEdorCalSet = aLMEdorCalDB.query();
//        if (aLMEdorCalDB.mErrors.needDealError()) {
//            mErrors.copyAllErrors(aLMEdorCalDB.mErrors);
//            mErrors.addOneError("查询险种责任保全计算红利率信息失败");
//        }
//        double aBonusRate = 0;
//        if (aLMEdorCalSet.size() > 0) {
//            BqCalBase aBqCalBase = new BqCalBase();
//            aBqCalBase.setEdorValiDate(mStartDate);
//            aBqCalBase.setPayToDate(mPaytoDate);
//            aBqCalBase.setForceDVFlag("1");
//            aBqCalBase.setPayEndYear(mLCPolSchema.getPayEndYear());
//            aBqCalBase.setPayIntv(mLCPolSchema.getPayIntv());
//            BqCalBL aBqCalBL = new BqCalBL();
//            aBonusRate = aBqCalBL.calChgMoney(aLMEdorCalSet.get(1).
//                                              getCalCode(),
//                                              aBqCalBase);
//        }

//        LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
//        tLMEdorCalDB.setRiskCode(mLCPolSchema.getRiskCode());
//        tLMEdorCalDB.setEdorType("RE");
//        tLMEdorCalDB.setCalType("LX");
//        LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query(); //查找保费累计变动金额计算信息
//        if (tLMEdorCalDB.mErrors.needDealError()) {
//            mErrors.copyAllErrors(tLMEdorCalDB.mErrors);
//            mErrors.addOneError(new CError("查询险种责任保全计算信息失败！"));
//            return -1;
//        }
//
//        tBqCalBase.setStartDate(mStartDate);
////        tBqCalBase.setBonusRate(aBonusRate);
//        if (tLMEdorCalSet == null ||
//            tLMEdorCalSet.size() <= 0) {
//            return -1; //没有计算信息，则不作计算
//        } else {
//            BqCalBL tBqCalBL = new BqCalBL();
//            double tValue = tBqCalBL.calChgMoney(tLMEdorCalSet.get(1).
//                                                 getCalCode(), tBqCalBase); // 计算各险种保费补退费
//            if (tBqCalBL.mErrors.needDealError()) {
//                mErrors.copyAllErrors(tBqCalBL.mErrors);
//                mErrors.addOneError(new CError("计算利息失败!"));
//                return -2;
//            }
//            aRate = tValue;
//        }
//        if (aRate == 0) { //未查到利率信息，可返回
//            mErrors.addOneError("查询利率失败!");
//            return -2;
//        }

//===add===zhangtao===2006-06-26===获取利息计算类型 M-按月计 D-按天=================BGN============
//        if (mIntervalType != null && mIntervalType.equals("D")) {
            tIntervalType = "D"; //如果需要按日计息，所有缴费间隔都按日计息
//        }
//===add===zhangtao===2006-06-26===获取利息计算类型 M-按月计 D-按天=================END============
//logger.debug(mIntervalRateType);
//        if (mIntervalRateType != null && mIntervalRateType.equals("D")) { //动态利率计息
	    logger.debug("动态利率计息");
            AccountManage tAccountManage = new AccountManage(String.valueOf(
                    mLCPolSchema.getPayEndYear()));
            if (!tAccountManage.calInterest(mLPEdorItemSchema.getEdorType(),
                                            mGetMoney,
                                            mLCPolSchema.getPaytoDate(),
                                            mLPEdorItemSchema.getEdorValiDate(),
                                            mLCPolSchema.getRiskCode(),
                                            sPayintvFlag)
                    ) {
                mErrors.addOneError("计算利息失败!");
                return -2;
            }
            aGetInterest = tAccountManage.getCalResult();
//        } else { //静态利率计息
//            aGetInterest = AccountManage.getMultiAccInterest(aRate,
//                    mGetMoney,
//                    sStartDate,
//                    mLPEdorItemSchema.getEdorValiDate(), "C", tIntervalType);
//        }
        return Double.parseDouble(String.valueOf(aGetInterest));
    }

    //准备催收保费项
    private void initDueLCPrem() {
        for (int i = 1; i <= mLCPremSet.size(); i++) {
//            mLCPremSet.get(i).setUrgePayFlag("N");
            mLCPremSet.get(i).setPayTimes(mLCPremSet.get(i).getPayTimes() +
                                          tPaySTime);
            mLCPremSet.get(i).setPaytoDate(mPayToDate);
            mLCPremSet.get(i).setSumPrem(mLCPremSet.get(i).getSumPrem() +
                                         mLCPremSet.get(i).getPrem() *
                                         tPaySTime);
        }
    }

    /**
     * 准备催收保单信息
     */
    private void initDueLCPol() {
        mLCPolSchema.setPaytoDate(mPayToDate);
	mLCPolSchema.setPolState("1");
        mLCPolSchema.setSumPrem(mLCPolSchema.getSumPrem() +
                                mLCPolSchema.getPrem() * tPaySTime);
    }

    /**
     * 准备催收责任信息
     */
    private void initDueLCDuty() {
        for (int i = 1; i <= mLCDutySet.size(); i++) {
            mLCDutySet.get(i).setPaytoDate(mPayToDate);
            mLCDutySet.get(i).setSumPrem(mLCDutySet.get(i).getSumPrem() +
                                         mLCDutySet.get(i).getPrem() *
                                         tPaySTime);
        }
    }

    /**
     * 只生成当期利息的批改补退费信息
     * @return
     */
    private boolean createLJSGetEndorseSchema() {
        //生成保全RE保费复利记录
        if (mTotalInterest > 0) {
            LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
            tLJSGetEndorseSchema = this.initLJSGetEndorse("LX");
            tLJSGetEndorseSchema.setGetMoney(mTotalInterest);
            tLJSGetEndorseSchema.setSubFeeOperationType("RE");
            tLJSGetEndorseSchema.setPolNo(mPolNo);
            aLJSGetEndorseSet.add(tLJSGetEndorseSchema);
        }

        return true;
    }

    /**
     * 由LJSPayPerson信息生成当期保费的批改补退费信息
     * @param cLJSPayPersonSchema
     * @return
     */
    private boolean createLJSGetEndorseSchema(LJSPayPersonSchema
                                              cLJSPayPersonSchema) {
        LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
        tLJSGetEndorseSchema = this.initLJSGetEndorse("BF");
        mReflections.transFields(tLJSGetEndorseSchema, cLJSPayPersonSchema);
//        tLJSGetEndorseSchema.setGetMoney(cLJSPayPersonSchema.getSumDuePayMoney());
        tLJSGetEndorseSchema.setGetMoney(mTotalPrem);
        //处理SubFeeOperationType
//        if (cLJSPayPersonSchema.getPayPlanCode() != null &&
//            cLJSPayPersonSchema.getPayPlanCode().startsWith("000000")) {
//            LCPremDB tLCPremDB = new LCPremDB();
//            tLCPremDB.setPolNo(cLJSPayPersonSchema.getPolNo());
//            tLCPremDB.setDutyCode(cLJSPayPersonSchema.getDutyCode());
//            tLCPremDB.setPayPlanCode(cLJSPayPersonSchema.getPayPlanCode());
//            if (!tLCPremDB.getInfo()) {
//                CError.buildErr(this, "查询交费计划类型失败!");
//                return false;
//            }
//            if ("01".equals(tLCPremDB.getPayPlanType()) ||
//                "03".equals(tLCPremDB.getPayPlanType())) {
//                tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.
//                        Pay_InsurAddPremHealth +
//                        String.valueOf(cLJSPayPersonSchema.getPayCount()));
//            } else if ("02".equals(tLCPremDB.getPayPlanType()) ||
//                       "04".equals(tLCPremDB.getPayPlanType())) {
//                tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.
//                        Pay_InsurAddPremOccupation +
//                        String.valueOf(cLJSPayPersonSchema.getPayCount()));
//            } else {
//                CError.buildErr(this, "加费交费计划类型错误!");
//                return false;
//            }
//        } else {
        tLJSGetEndorseSchema.setSubFeeOperationType("RE");
//        }
        aLJSGetEndorseSet.add(tLJSGetEndorseSchema);
        return true;
    }

    private boolean createLJSPayPersonSchema(int Pays) {
        //修改PayCount,LastPaytoDate,CurPaytoDate
        LJSPayPersonSchema tLJSPayPersonSchema;
        for (int i = 1; i <= aLJSPayPersonSet.size(); i++) {
            tLJSPayPersonSchema = new LJSPayPersonSchema();
            tLJSPayPersonSchema.setSchema(aLJSPayPersonSet.get(i));
            tLJSPayPersonSchema.setPayCount(mPayCount - 1);
            tLJSPayPersonSchema.setOperator(mGlobalInput.Operator);
            tLJSPayPersonSchema.setPayDate(mLPEdorItemSchema.
                                           getEdorValiDate());
            tLJSPayPersonSchema.setLastPayToDate(PubFun.calDate(
                    aLJSPayPersonSet.get(1).getLastPayToDate()
                    , Pays
                    , aunit, null));
            tLJSPayPersonSchema.setCurPayToDate(PubFun.calDate(
                    aLJSPayPersonSet.
                    get(1).getCurPayToDate()
                    , Pays
                    , aunit, null));
            tLJSPayPersonSchema.setPayType("RE");
            tLJSPayPersonSchema.setMakeDate(PubFun.getCurrentDate());
            tLJSPayPersonSchema.setMakeTime(PubFun.getCurrentTime());
            tLJSPayPersonSchema.setModifyDate(PubFun.getCurrentDate());
            tLJSPayPersonSchema.setModifyTime(PubFun.getCurrentTime());
            tLJSPayPersonSchema.setBankAccNo("");
            tLJSPayPersonSchema.setBankCode("");
            tLJSPayPersonSchema.setGetNoticeNo(mLPEdorItemSchema.
                                               getEdorNo());
            tLJSPayPersonSchema.setOperator(mGlobalInput.Operator);
            tLJSPayPersonSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
            mLJSPayPersonSet.add(tLJSPayPersonSchema);
            mPayCount++;

        }
        return true;
    }


    /**
     * 生成交退费记录
     * @return
     */
    private LJSGetEndorseSchema initLJSGetEndorse(String strfinType) {
        LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
        mLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
        mLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
        mLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema.getEdorType());
//        //从描述表中获取财务接口类型，modify by Minim at 2003-12-23
//        String finType = BqCalBL.getFinType(mLPEdorItemSchema.getEdorType(),
//                                            strfinType,
//                                            mLPEdorItemSchema.getPolNo());
//        if (finType.equals("")) {
//            // @@错误处理
//            CError.buildErr(this, "在LDCode1中缺少保全财务接口转换类型编码!");
//            return null;
//        }
        mLJSGetEndorseSchema.setFeeFinaType(strfinType);
        mLJSGetEndorseSchema.setDutyCode("0");
        mLJSGetEndorseSchema.setPayPlanCode("0");
        //走保全交费财务流程
        mLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo()); //保全给付用批单号
        mLJSGetEndorseSchema.setOtherNoType("3"); //保全给付
        Reflections tRef = new Reflections();

        mLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
        mLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
        mLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
        mLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
        mLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
        mLJSGetEndorseSchema.setGetDate(mLPEdorItemSchema.getEdorValiDate());
        mLJSGetEndorseSchema.setGetFlag("0");
//        tLJSGetEndorseSchema.setSchema(mLJSGetEndorseSchema);
        tRef.transFields(tLJSGetEndorseSchema, mLJSGetEndorseSchema);
        tRef.transFields(tLJSGetEndorseSchema, mLCPolSchema);
        logger.debug("gohomegohome");
        logger.debug(tLJSGetEndorseSchema.getPolNo());
        return tLJSGetEndorseSchema;
    }

    private boolean prepareData() {
        //准备LJSGetEndorse表
        double getMoney = 0;
        LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
        for (int i = 1; i <= aLJSGetEndorseSet.size(); i++) {
            LJSGetEndorseSchema tLJS = new LJSGetEndorseSchema();
            tLJS.setSchema(aLJSGetEndorseSet.get(i));
            getMoney += tLJS.getGetMoney();
            tLJSGetEndorseSet.add(tLJS);
        }
        map.put(tLJSGetEndorseSet, "DELETE&INSERT");

        map.put(mLJSPayPersonSet, "DELETE&INSERT");

//        LCContDB tLCContDB = new LCContDB();
//        LCContSchema tLCContSchema = new LCContSchema();
//        LPContSchema tLPContSchema = new LPContSchema();
//        tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
////        if (!tLCContDB.getInfo()) {
////            mErrors.addOneError("查询保单信息失败!");
////            return false;
////        }
//        tLCContSchema = tLCContDB.query().get(1);
//        Reflections tRef = new Reflections();
//        tRef.transFields(tLPContSchema, tLCContSchema);
//        tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//        tLPContSchema.setEdorType("RE");
//        tLPContSchema.setModifyDate(PubFun.getCurrentDate());
//        tLPContSchema.setModifyTime(PubFun.getCurrentTime());
//        map.put(tLPContSchema, "DELETE&INSERT");

        //准备LPEdorItem表
//        mLPEdorItemSchema.setEdorState("1");
//        mLPEdorItemSchema.setGetMoney(getMoney);
//        mLPEdorItemSchema.setGetInterest(mTotalInterest);
//        mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
//        mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
//        map.put(mLPEdorItemSchema, "UPDATE");

//        mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
//        mResult.add(mBqCalBase);
        mResult.add(map);
        return true;
    }

    public static void main(String[] args) {
        PEdorREDetailBL t = new PEdorREDetailBL();
        GlobalInput tG = new GlobalInput();
        tG.Operator = "001";
        tG.ManageCom = "86";

        LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
        LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
        LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
        tLPEdorItemDB.setEdorAcceptNo("6120050826000038");
        tLPEdorItemSet = tLPEdorItemDB.query();
        tLPEdorItemSchema = tLPEdorItemSet.get(1);
        LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
        VData tVData = new VData();
        tVData.add(tG);
        tVData.add(tLPEdorItemSchema);
        tVData.add(tLCCustomerImpartSet);
        t.submitData(tVData, "");
    }
}
