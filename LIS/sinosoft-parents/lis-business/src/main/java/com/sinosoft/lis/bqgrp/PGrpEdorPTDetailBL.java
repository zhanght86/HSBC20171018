package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.bq.*;
import java.text.DecimalFormat;


/**
 * <p>Title: </p>
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
public class PGrpEdorPTDetailBL {
private static Logger logger = Logger.getLogger(PGrpEdorPTDetailBL.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();


    /** 往后面传输数据的容器 */
    private VData mInputData;
    private MMap mMap = new MMap();


    /** 往界面传输数据的容器 */
    private VData mResult = new VData();


    /** 数据操作字符串 */
    private String mOperate;

    private Reflections mRef = new Reflections();

    private BqCalBase mBqCalBase = new BqCalBase();


    /** 传入数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private LPPolSchema mLPPolSchema = new LPPolSchema();
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
    private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();

    private GrpEdorCalZT mGrpEdorCalZT = new GrpEdorCalZT();
    
    /** 全局数据 */
    private LCPolSchema mLCPolSchema = new LCPolSchema();
    private LPGrpContSchema mLPGrpContSchema = new LPGrpContSchema();
    private LPGrpPolSchema mLPGrpPolSchema = new LPGrpPolSchema();
    private double mGetMoney = 0.0; //应退
    private double aGetMoney = 0.0; //累计应退
    private double mPayMoney = 0.0; //应付
    private double mOldPrem = 0.0; //原保费
    private double mChgAmnt = 0.0; //变动保额
    private double mChgPrem = 0.0; //变动保费
    private double tChgAmnt = 0.0; //变动保额
    private double tChgPrem = 0.0; //变动保费
    private EdorCalZT mEdorCalZT;
    private double mCashValue = 0.0; //现金价值
    private double mLoanCorpus = 0.0; //贷款本金
    private double mLoanInterest = 0.0; //贷款利息
    private double mAutoPayPrem = 0.0; //自垫本金
    private double mAutoPayInterest = 0.0; //自垫利息
    private double mFinaBonus = 0.0; //终了红利
    private double mAddupBonus = 0.0; //累计红利
    private double mAddZY = 0.0; //职业加费
    private double mAddJK = 0.0; //健康加费
    private double mOwePrem = 0.0; //欠交保费
    private double mOweInterest = 0.0; //欠交保费利息
    private double tValue = 0.0; //短期费率
    private double mRNewGetMoney = 0.0; //续期退费
    private boolean mFlag = false; //犹豫期标志
    private double mScale = 0.0; //减保比例
    private double tprem = 0;
    private double tamnt = 0;


    /** 修改数据 */
    private LPContSchema mLPContSchema = new LPContSchema();
    private LCContSchema mLCContSchema = new LCContSchema();
    private LPPolSet mLPPolSet = new LPPolSet();
    private LPDutySet mLPDutySet = new LPDutySet();
    private LPPremSet mLPPremSet = new LPPremSet();
    private LPGetSet mLPGetSet = new LPGetSet();
    private LJSGetEndorseSchema mLJSGetEndorseSchema = new LJSGetEndorseSchema();

    public PGrpEdorPTDetailBL() {
    }

    public VData getResult() {
        return mResult;
    }


    /**
     * 数据提交的公共方法
     * @param: cInputData 传入的数据
     * @param: cOperate 数据操作字符串
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate) {
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;
        logger.debug(
                "----------------PGrpEdorPTDetailBL------------------");

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData()) {
            return false;
        }
        logger.debug("-------end getInputData");

        //数据校验
        if (!checkData()) {
            return false;
        }
        logger.debug("-------end checkData");

        //数据准备操作
        if (!dealData()) {
            return false;
        }
        logger.debug("-------end dealData");
        return true;
    }


    /**
     * 从输入数据中得到所有对象
     * @return
     */
    private boolean getInputData() {
        try {
            mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData.
                                   getObjectByObjectName(
                                           "LPGrpEdorItemSchema", 0);
            mLPEdorItemSchema = (LPEdorItemSchema) mInputData.
                                getObjectByObjectName(
                                        "LPEdorItemSchema", 0);
            mLPPolSchema = (LPPolSchema) mInputData.getObjectByObjectName(
                    "LPPolSchema", 0);
            mLPDutySet = (LPDutySet) mInputData.getObjectByObjectName(
                    "LPDutySet", 0);
            mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
                    "GlobalInput", 0);
        } catch (Exception e) {
            mErrors.addOneError("接收数据失败");
            return false;
        }
//        mEdorCalZT = new EdorCalZT(mGlobalInput);
        if (mLPGrpEdorItemSchema == null || mLPDutySet == null ||
            mLPDutySet.size() == 0) {
            CError.buildErr(this, "输入数据有误!");
            return false;
        }

        return true;
    }

    private boolean checkData() {
        LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
        tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
        if (!tLPGrpEdorItemDB.getInfo()) {
            CError tError = new CError();
            tError.moduleName = "PGrpEdorCTDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "无保全项目数据!";
            logger.debug("---无保全项目数据!---");
            this.mErrors.addOneError(tError);
            return false;
        }
        if (tLPGrpEdorItemDB.getEdorState().trim().equals("2")) {
            CError tError = new CError();
            tError.moduleName = "PGrpEdorCTDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "该保全项目已经申请确认不能修改!";
            logger.debug("---该保全项目已经申请确认不能修改!---");
            this.mErrors.addOneError(tError);
            return false;
        }
        mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemDB.getSchema());

        LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
        tLPEdorItemSchema.setSchema(mLPEdorItemSchema);
        LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
        tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
        tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
        tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
        mLPEdorItemSchema = tLPEdorItemDB.query().get(1);
        mLPEdorItemSchema.setEdorReason(tLPEdorItemSchema.getEdorReason());
        mLPEdorItemSchema.setEdorReasonCode(tLPEdorItemSchema.getEdorReasonCode());

        //判断保单是否理赔,如果有理赔,不能作保全项目
        LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
        tLLClaimDetailDB.setPolNo(mLPPolSchema.getPolNo());
        LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB.query();
        if (tLLClaimDetailSet.size() > 0) {
            mErrors.addOneError("该保单发生过理赔，不能申请保全！");
            return false;
        }
        //end

        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(mLPPolSchema.getPolNo());
        mLCPolSchema = tLCPolDB.query().get(1);
        if (mLCPolSchema == null) {
            mErrors.addOneError("查询LCPol表失败!");
            return false;
        }
        mRef.transFields(mLPPolSchema, mLCPolSchema);
        mLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
        mLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
        mLPEdorItemSchema.setPolNo(mLPPolSchema.getPolNo());
        /*    if ((mLPPolSchema.getAmnt() > 0 &&
                 (mLCPolSchema.getAmnt() <= mLPPolSchema.getAmnt())) ||
                mLPPolSchema.getMult() > 0 &&
                mLCPolSchema.getMult() <= mLPPolSchema.getMult()) {
         logger.debug("Old Amnt ========>" + mLCPolSchema.getAmnt());
         logger.debug("New Amnt ========>" + mLPPolSchema.getAmnt());
              mErrors.addOneError("所减金额不符合规范，请重新输入!");
              return false;
            }
                try {
                  int a = (int) mLPPolSchema.getAmnt();
                  int b = (int) mLPPolSchema.getMult();
                  if (a - mLPPolSchema.getAmnt() != 0) {
                    mErrors.addOneError("所减金额非整数，请重新输入!");
                    return false;
                  }
                  if (b - mLPPolSchema.getMult() != 0) {
                    mErrors.addOneError("所减金额非整数，请重新输入!");
                    return false;
                  }
                } catch (Exception e) {
                  mErrors.addOneError("所减金额非整数，请重新输入!");
                  return false;
                }
         if (mLPPolSchema.getAmnt() == 0 && mLPPolSchema.getMult() == 0) {
                  mErrors.addOneError("所减金额为零，请重新输入!");
                  return false;
                }*/
        String strSQL = "select getmoney from ljsgetendorse where feeoperationtype = 'PT' and EndorsementNo = '"
                        + mLPEdorItemSchema.getEdorNo() + "' and polno <> '"
                        + mLPPolSchema.getPolNo() + "'";
        SSRS ts = new SSRS();
        ExeSQL tes = new ExeSQL();
        ts = tes.execSQL(strSQL);
        if (ts != null && ts.getMaxRow() > 0) {
            for (int i = 1; i <= ts.getMaxRow(); i++) {
                aGetMoney += Double.parseDouble(ts.GetText(i, 1));
            }
        }
        return true;
    }


    /**
     * TK减保业务处理
     * @return boolean
     */
    private boolean dealData() {
        LCContDB tLCContDB = new LCContDB();
        tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
        mLCContSchema = tLCContDB.query().get(1);
        if (mLCContSchema == null) {
            mErrors.addOneError("查询LCCont表失败!");
            return false;
        }
        Reflections mRef = new Reflections();
        LPContBL tLPContBL = new LPContBL();
        tLPContBL.queryLPCont(mLPEdorItemSchema);
        mLPContSchema = tLPContBL.getSchema();
        mRef.transFields(mLPContSchema, mLCContSchema);

        //续期退费 暂时注掉
        /*    LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
                LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
                String str =
         "select * from ljapayperson where lastpaytodate > to_date('" +
                    mLPEdorItemSchema.getEdorValiDate() +
         "','YYYY-MM-DD') and polno = '" + mLPPolSchema.getPolNo() + "'";
                logger.debug("续期＝＝＝" + str);
                tLJAPayPersonSet = tLJAPayPersonDB.executeQuery(str);
                if (tLJAPayPersonSet != null && tLJAPayPersonSet.size() > 0) {
                  mRNewGetMoney += mLPPolSet.get(1).getPrem() -
                      tLJAPayPersonSet.get(1).getSumActuPayMoney();
         tLJAPayPersonSet.get(1).setSumActuPayMoney(mLPPolSet.get(1).getPrem());
         tLJAPayPersonSet.get(1).setSumDuePayMoney(mLPPolSet.get(1).getPrem());
                  mMap.put(tLJAPayPersonSet, "UPDATE");
                }

                mOwePrem = 0;
                mOweInterest = 0;
                LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
                LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
                tLJSPayPersonDB.setPolNo(mLPPolSchema.getPolNo());
                tLJSPayPersonDB.setPayType("ZC");
                tLJSPayPersonSet = tLJSPayPersonDB.query();
                if (tLJSPayPersonSet != null && tLJSPayPersonSet.size() > 0) {
         String tPaytoDate = tLJSPayPersonSet.get(1).getLastPayToDate();
         String tLapseDate = PubFun.calDate(tPaytoDate, 60, "D", null);
                  FDate fd = new FDate();
         if (fd.getDate(tLapseDate).before(fd.getDate(mLPEdorItemSchema.
                      getEdorValiDate()))) {
                    //失效状态，进行复效
                    VData vd = new VData();
                    LCCustomerImpartSet tLCCustomerImpartSet = new
                        LCCustomerImpartSet();
                    vd.add(tLCCustomerImpartSet);
                    vd.add(mLPEdorItemSchema);
                    vd.add(mGlobalInput);
                    PEdorREDetailBL tPEdorREDetailBL = new PEdorREDetailBL();
                    tPEdorREDetailBL.submitData(vd, "");
                    if (tPEdorREDetailBL.mErrors.needDealError()) {
                      mErrors.copyAllErrors(tPEdorREDetailBL.mErrors);
                      mErrors.addOneError("险种复效失败!");
                      return false;
                    }
                    VData tRs = new VData();
                    tRs = tPEdorREDetailBL.getResult();
                    MMap tMap = (MMap) tRs.getObjectByObjectName("MMap", 0);
                    if (tMap == null) {
                      mErrors.addOneError("获取复效更新数据失败!");
                      return false;
                    }
                    try {
                      TransferData aTransferData = new TransferData();
                      aTransferData = (TransferData) tRs.getObjectByObjectName(
                          "TransferData", 0);
                      Double total = (Double) aTransferData.getValueByName(
                          "Total");
                      Double interest = (Double) aTransferData.getValueByName(
                          "Interest");
                      mOwePrem = total.doubleValue() - interest.doubleValue();
                      mOweInterest = interest.doubleValue();
                      mMap.add(tMap);
                    } catch (Exception e) {
                      CError.buildErr(this, "获取复效数据失败!");
                      return false;
                    }
                  } else {
                    mOwePrem = tLJSPayPersonSet.get(1).getSumActuPayMoney();
                    mOweInterest = 0;
                  }
                }
         */
        logger.debug("---减额处理 start---");
        if (!MainRisk()) {
            mErrors.addOneError("减保处理失败!");
            return false;
        }

        mGetMoney += mLoanCorpus + mLoanInterest + mAutoPayPrem +
                mAutoPayInterest + mFinaBonus + mOwePrem + mOweInterest;
        mPayMoney += mLoanCorpus + mLoanInterest + mAutoPayPrem +
                mAutoPayInterest + mOwePrem + mOweInterest;

        mGetMoney = PubFun.round(mGetMoney,2);
        mPayMoney = PubFun.round(mPayMoney,2);
//    LPEdorItem要实现按险种保存
        
        mLPEdorItemSchema.setChgAmnt(mLPPolSchema.getAmnt() -
                                     mLCPolSchema.getAmnt());
        mLPEdorItemSchema.setChgPrem(mLPPolSchema.getPrem() -
                                     mLCPolSchema.getPrem());
        mLPEdorItemSchema.setEdorState("1");
        logger.debug("#############################");
        logger.debug("#ChgAmnt#" + mLPEdorItemSchema.getChgAmnt());
        logger.debug("#ChgPrem#" + mLPPolSchema.getPrem() + "-" +
                           mLCPolSchema.getPrem() + "=" +
                           mLPEdorItemSchema.getChgPrem());
        logger.debug("#############################");
        mMap.put("delete from LPEdorItem where EdorNo='" +
                 mLPEdorItemSchema.getEdorNo() +
                 "' and EdorType='" + mLPEdorItemSchema.getEdorType() +
                 "' and ContNo='" + mLPEdorItemSchema.getContNo() +
                 "' and PolNo='000000'", "DELETE");
        mMap.put(mLPEdorItemSchema, "DELETE&INSERT");
        String tSql = "update LPEdorItem set getmoney = nvl((select SUM(getmoney) from ljsgetendorse where endorsementno='" +
                      mLPEdorItemSchema.getEdorNo() +
                      "' AND FEEOPERATIONTYPE='" +
                      mLPEdorItemSchema.getEdorType() +
                      "' and PolNo=LPEdorItem.PolNo),0) where EdorNo='" +
                      mLPEdorItemSchema.getEdorNo() +
                      "' and EdorType='" + mLPEdorItemSchema.getEdorType() +
                      "' and ContNo='" + mLPEdorItemSchema.getContNo() +
                      "' and PolNo='" + mLPPolSchema.getPolNo() + "'";
        mMap.put(tSql, "UPDATE");

        mLPGrpEdorItemSchema.setGetMoney(mGetMoney + aGetMoney);
        mLPGrpEdorItemSchema.setEdorState("1");
        mMap.put(mLPGrpEdorItemSchema, "UPDATE");

        /*        //增加LPGrp表数据
                LCGrpContDB tLCGrpContDB = new LCGrpContDB();
                tLCGrpContDB.setGrpContNo(mLPEdorItemSchema.getGrpContNo());
                LCGrpContSchema mLCGrpContSchema = tLCGrpContDB.query().get(1);
                if (mLCGrpContSchema == null) {
                    mErrors.addOneError("查询LCGrpCont表失败!");
                    return false;
                }
                mRef.transFields(mLPGrpContSchema, mLCGrpContSchema);
                mLPGrpContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                mLPGrpContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
                LPGrpContDB tLPGrpContDB = new LPGrpContDB();
                tLPGrpContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
                tLPGrpContDB.setEdorType(mLPEdorItemSchema.getEdorType());
                tLPGrpContDB.setGrpContNo(mLPEdorItemSchema.getGrpContNo());
                if (!tLPGrpContDB.getInfo()) {
                    tChgAmnt = 0.0;
                    tChgPrem = 0.0;
                } else {
                    LPGrpContSet tLPGrpContSet = tLPGrpContDB.query();
                    for (int c = 1; c <= tLPGrpContSet.size(); c++) {
         LPGrpContSchema tLPGrpContSchema = new LPGrpContSchema();
                        tLPGrpContSchema = tLPGrpContSet.get(c);
                        LCGrpContDB aLCGrpContDB = new LCGrpContDB();
         aLCGrpContDB.setGrpContNo(tLPGrpContSchema.getGrpContNo());
         LCGrpContSchema aLCGrpContSchema = new LCGrpContSchema();
                        aLCGrpContSchema = aLCGrpContDB.query().get(1);
                        tChgAmnt += -tLPGrpContSchema.getAmnt() +
                                aLCGrpContSchema.getAmnt();
                        tChgPrem += -tLPGrpContSchema.getPrem() +
                                aLCGrpContSchema.getPrem();
                    }
                }
         logger.debug("tChgAmnt:" + tChgAmnt + " tChgPrem:" + tChgPrem);
         mLPGrpContSchema.setAmnt(mLCGrpContSchema.getAmnt() - tChgAmnt - tamnt);
         mLPGrpContSchema.setPrem(mLCGrpContSchema.getPrem() - tChgPrem - tprem);
                mMap.put(mLPGrpContSchema, "DELETE&INSERT");

                LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
                tLCGrpPolDB.setGrpContNo(mLPEdorItemSchema.getGrpContNo());
                tLCGrpPolDB.setGrpPolNo(mLPPolSchema.getGrpPolNo());
                tLCGrpPolDB.setRiskCode(mLPPolSchema.getRiskCode());
                LCGrpPolSchema mLCGrpPolSchema = tLCGrpPolDB.query().get(1);
                if (mLCGrpPolSchema == null) {
                    mErrors.addOneError("查询LCGrpPol表失败!");
                    return false;
                }
                mRef.transFields(mLPGrpPolSchema, mLCGrpPolSchema);
                mLPGrpPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                mLPGrpPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
                LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();
                tLPGrpPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
                tLPGrpPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
                tLPGrpPolDB.setGrpContNo(mLPEdorItemSchema.getGrpContNo());
                tLPGrpPolDB.setGrpPolNo(mLPPolSchema.getGrpPolNo());
                tLPGrpPolDB.setRiskCode(mLPPolSchema.getRiskCode());
                tChgAmnt = 0.0;
                tChgPrem = 0.0;
                if (!tLPGrpPolDB.getInfo()) {
                    tChgAmnt = 0.0;
                    tChgPrem = 0.0;
                } else {
                    LPGrpPolSet tLPGrpPolSet = tLPGrpPolDB.query();
                    for (int i = 1; i <= tLPGrpPolSet.size(); i++) {
                        LPGrpPolSchema tLPGrpPolSchema = new LPGrpPolSchema();
                        tLPGrpPolSchema = tLPGrpPolSet.get(i);
                        LCGrpPolDB aLCGrpPolDB = new LCGrpPolDB();
         aLCGrpPolDB.setGrpContNo(tLPGrpPolSchema.getGrpContNo());
                        aLCGrpPolDB.setRiskCode(mLPPolSchema.getRiskCode());
                        LCGrpPolSchema aLCGrpPolSchema = new LCGrpPolSchema();
                        aLCGrpPolSchema = aLCGrpPolDB.query().get(1);
                        tChgAmnt += -tLPGrpPolSchema.getAmnt() +
                                aLCGrpPolSchema.getAmnt();
                        tChgPrem += -tLPGrpPolSchema.getPrem() +
                                aLCGrpPolSchema.getPrem();
                        logger.debug(tChgAmnt + "=========" + tChgPrem);
                    }
                }
         mLPGrpPolSchema.setAmnt(mLCGrpPolSchema.getAmnt() - tChgAmnt - tamnt);
         mLPGrpPolSchema.setPrem(mLCGrpPolSchema.getPrem() - tChgPrem - tprem);
                mMap.put(mLPGrpPolSchema, "DELETE&INSERT");
                //END
         */
        //   LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
//    LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
//    tLJSGetEndorseSchema = this.initLJSGetEndorse("TB");
//    tLJSGetEndorseSchema.setGetMoney(mGetMoney);
//    tLJSGetEndorseSchema.setSubFeeOperationType("TB");
//    tLJSGetEndorseSet.add(tLJSGetEndorseSchema);

//    if (mLoanCorpus != 0) {
//      LJSGetEndorseSchema aLJSGetEndorseSchema = new LJSGetEndorseSchema();
//      aLJSGetEndorseSchema = this.initLJSGetEndorse("DK");
//      aLJSGetEndorseSchema.setGetMoney(mLoanCorpus);
//      aLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_LoanCorpus);
//      tLJSGetEndorseSet.add(aLJSGetEndorseSchema);
//    }
//    if (mLoanInterest != 0) {
//      LJSGetEndorseSchema bLJSGetEndorseSchema = new LJSGetEndorseSchema();
//      bLJSGetEndorseSchema = this.initLJSGetEndorse("LX");
//      bLJSGetEndorseSchema.setGetMoney(mLoanInterest);
//      bLJSGetEndorseSchema.setSubFeeOperationType(BqCode.
//                                                  Pay_LoanCorpusInterest);
//      tLJSGetEndorseSet.add(bLJSGetEndorseSchema);
//    }
//    if (mAutoPayPrem != 0) {
//      LJSGetEndorseSchema cLJSGetEndorseSchema = new LJSGetEndorseSchema();
//      cLJSGetEndorseSchema = this.initLJSGetEndorse("BF");
//      cLJSGetEndorseSchema.setGetMoney(mAutoPayPrem);
//      cLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_AutoPayPrem);
//      tLJSGetEndorseSet.add(cLJSGetEndorseSchema);
//    }
//    if (mAutoPayInterest != 0) {
//      LJSGetEndorseSchema dLJSGetEndorseSchema = new LJSGetEndorseSchema();
//      dLJSGetEndorseSchema = this.initLJSGetEndorse("LX");
//      dLJSGetEndorseSchema.setGetMoney(mAutoPayInterest);
//      dLJSGetEndorseSchema.setSubFeeOperationType(BqCode.
//                                                  Pay_AutoPayPremInterest);
//      tLJSGetEndorseSet.add(dLJSGetEndorseSchema);
//    }
//    if (mFinaBonus != 0) {
//      LJSGetEndorseSchema eLJSGetEndorseSchema = new LJSGetEndorseSchema();
//      eLJSGetEndorseSchema = this.initLJSGetEndorse("TB");
//      eLJSGetEndorseSchema.setGetMoney(mFinaBonus);
//      eLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Get_FinaBonus);
//      tLJSGetEndorseSet.add(eLJSGetEndorseSchema);
//    }
//    if (mAddupBonus != 0) {
//      LJSGetEndorseSchema fLJSGetEndorseSchema = new LJSGetEndorseSchema();
//      fLJSGetEndorseSchema = this.initLJSGetEndorse("TB");
//      fLJSGetEndorseSchema.setGetMoney(mAddupBonus);
//      fLJSGetEndorseSchema.setSubFeeOperationType(BqCode.
//                                                  Get_BonusCashValue);
//      tLJSGetEndorseSet.add(fLJSGetEndorseSchema);
//    }
//
//    mAddZY = 0;
//    mAddJK = 0;
//
//    if (mOwePrem != 0) {
//      LJSGetEndorseSchema gLJSGetEndorseSchema = new LJSGetEndorseSchema();
//      gLJSGetEndorseSchema = this.initLJSGetEndorse("BF");
//      gLJSGetEndorseSchema.setGetMoney(mOwePrem);
//      gLJSGetEndorseSchema.setSubFeeOperationType(BqCode.
//                                                  Pay_Prem);
//      tLJSGetEndorseSet.add(gLJSGetEndorseSchema);
//    }
//    if (mOweInterest != 0) {
//      LJSGetEndorseSchema hLJSGetEndorseSchema = new LJSGetEndorseSchema();
//      hLJSGetEndorseSchema = this.initLJSGetEndorse("LX");
//      hLJSGetEndorseSchema.setGetMoney(mOweInterest);
//      hLJSGetEndorseSchema.setSubFeeOperationType(BqCode.
//                                                  Pay_PremInterest);
//      tLJSGetEndorseSet.add(hLJSGetEndorseSchema);
//    }

        //mMap.put(tLJSGetEndorseSet, "DELETE&INSERT");

        mResult.clear();
        mResult.add(mMap);

        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("GetMoney", mGetMoney);
        tTransferData.setNameAndValue("PayMoney", mPayMoney);
        tTransferData.setNameAndValue("CashValue", mCashValue);
        tTransferData.setNameAndValue("LoanCorpus", mLoanCorpus);
        tTransferData.setNameAndValue("LoanInterest", mLoanInterest);
        tTransferData.setNameAndValue("AutoPayPrem", mAutoPayPrem);
        tTransferData.setNameAndValue("AutoPayInterest", mAutoPayInterest);
        tTransferData.setNameAndValue("FinaBonus", mFinaBonus);
        tTransferData.setNameAndValue("AddupBonus", mAddupBonus);
        tTransferData.setNameAndValue("AddZY", mAddZY);
        tTransferData.setNameAndValue("AddJK", mAddJK);
        tTransferData.setNameAndValue("OwePrem", mOwePrem);
        tTransferData.setNameAndValue("OweInterest", mOweInterest);
        mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
        mBqCalBase.setEdorNo(mLPEdorItemSchema.getEdorNo());
        mBqCalBase.setEdorAppDate(mLPEdorItemSchema.getEdorAppDate());
        mBqCalBase.setEdorValiDate(mLPEdorItemSchema.getEdorValiDate());
        mBqCalBase.setPolNo(mLCPolSchema.getPolNo());
        mBqCalBase.setManageCom(mLPContSchema.getManageCom());
        mBqCalBase.setRiskCode(mLCPolSchema.getRiskCode());
        mResult.add(mBqCalBase);
        mResult.add(tTransferData);

        return true;
    }


    /**
     * 主险减保业务处理 【taikang不分主附险，所有险种都按主险处理】
     * @return boolean
     */
    private boolean MainRisk() {
    	LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
    	String mRiskCode = mLCPolSchema.getRiskCode();
    	String mRiskType = "S"; //S短险；L长险
    	LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
    	tLMRiskAppDB.setRiskCode(mRiskCode);
    	LMRiskAppSchema tLMRiskAppSchema = tLMRiskAppDB.query().get(1);
    	if (tLMRiskAppSchema.getRiskPeriod().equals("L")) {
    		mRiskType = "L";
    	}
    	if (mRiskType.equals("S")) {

    	}
    	/*    String i_sql = "select * from LCDuty where PolNo = '" +
                           mLCPolSchema.getPolNo() +
                           "' order by MakeDate desc,MakeTime desc";
            LCDutyDB tLCDutyDB = new LCDutyDB();
            LCDutySet tLCDutySet = new LCDutySet();

            LPDutySchema aLPDutySchema = new LPDutySchema();
            LCPremSet aLCPremSet = new LCPremSet();
            logger.debug(i_sql);
            tLCDutySet = tLCDutyDB.executeQuery(i_sql);
            if (tLCDutySet.size() < 1) {
              mErrors.addOneError("查询责任信息不符合规范!");
              return false;
            }
            logger.debug(mLCPolSchema.getRiskCode() + "险种对应责任的数目:" +
                               tLCDutySet.size());
//    for(int i=0;i<tLCDutySet.size();i++){
            LMDutyDB tLMDutyDB = new LMDutyDB();
            LMDutySet tLMDutySet = new LMDutySet();
            LMDutySchema tLMDutySchema = new LMDutySchema();
            tLMDutyDB.setDutyCode(tLCDutySet.get(1).getDutyCode().substring(0,
                6));
            tLMDutySet = tLMDutyDB.query();
            if (tLMDutySet.size() < 1) {
              mErrors.addOneError("查询责任定义表失败!");
              return false;
            }
            tLMDutySchema = tLMDutySet.get(1);

            //del掉了犹豫期处理
         if (tLMDutySchema.getCalMode().equals("G") && mLPPolSchema.getAmnt() > 0) {
              logger.debug("-------------按保额进行减保----------------");
              mRef.transFields(aLPDutySchema, tLCDutySet.get(1));
              aLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
              aLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
              aLPDutySchema.setModifyDate(PubFun.getCurrentDate());
              aLPDutySchema.setModifyTime(PubFun.getCurrentTime());
         aLPDutySchema.setAmnt(tLCDutySet.get(1).getAmnt() - mLPPolSchema.getAmnt());
              LCPremDB tLCPremDB = new LCPremDB();
              tLCPremDB.setPolNo(mLPPolSchema.getPolNo());
              tLCPremDB.setDutyCode(aLPDutySchema.getDutyCode());
              aLCPremSet = tLCPremDB.query();
              if (aLCPremSet.size() < 1) {
                mErrors.addOneError("查询保费项表失败!");
                return false;
              }
              //mChgAmnt = tLCDutySet.get(1).getAmnt() - mLPPolSchema.getAmnt();
              mChgAmnt = mLPPolSchema.getAmnt();
              logger.debug("减少的保额＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝" + mChgAmnt);
              mLPContSchema.setAmnt(mLCContSchema.getAmnt() - mChgAmnt);
              mLPPolSchema.setAmnt(mLCPolSchema.getAmnt() - mChgAmnt);
              mScale = mChgAmnt / mLCPolSchema.getAmnt();
              //mGetMoney = mChgPrem * tValue;//应退金额 保额怎么算？
            } else if (tLMDutySchema.getCalMode().equals("P") &&
                       mLPPolSchema.getPrem() > 0) {
              logger.debug("-------------按保费进行减保----------------");
              mRef.transFields(aLPDutySchema, tLCDutySet.get(1));
              aLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
              aLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
              aLPDutySchema.setModifyDate(PubFun.getCurrentDate());
              aLPDutySchema.setModifyTime(PubFun.getCurrentTime());
         aLPDutySchema.setPrem(tLCDutySet.get(1).getPrem() - mLPPolSchema.getPrem());
              LCPremDB tLCPremDB = new LCPremDB();
              tLCPremDB.setPolNo(mLPPolSchema.getPolNo());
              tLCPremDB.setDutyCode(aLPDutySchema.getDutyCode());
              aLCPremSet = tLCPremDB.query();
              if (aLCPremSet.size() < 1) {
                mErrors.addOneError("查询保费项表失败!");
                return false;
              }
              //mChgPrem = mLCPolSchema.getPrem() - aLPDutySchema.getPrem();
              mChgPrem = aLPDutySchema.getPrem();
         logger.debug("变动的保费~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + mChgPrem);
              mLPContSchema.setPrem(mLCContSchema.getPrem() - mChgPrem);
              mScale = mChgPrem / mLCPolSchema.getPrem();
              mGetMoney = mChgPrem * tValue; //应退金额
            } else {
              CError tCError = new CError();
              tCError.moduleName = "PGrpEdorPTDetailBL";
              tCError.functionName = "MainRisk";
              tCError.errorMessage = "该责任不能做PT操作!";
              mErrors.addOneError(tCError);
            }

            //计算应付金额 由于按险种录入，先考虑一种责任的情况。待后续改进。
            //LCDutySchema tLCDutySchema = new LCDutySchema();
            //tLCDutySchema = tLCDutySet.get(1);
            //tLCDutySchema.setAmnt(mChgAmnt);//?
            //tLCDutySchema.setMult(mLPPolSchema.getMult());

            //计算现金价值
            //mGetMoney +=mEdorCalZT.getCashValue(mLCPolSchema.getPolNo(),mLPEdorItemSchema.getEdorValiDate());
            //mCashValue += mEdorCalZT.getCashValue(mLCPolSchema,tLCDutySchema,mLPEdorItemSchema.getEdorValiDate());

//    if (mGetMoney <= 0) {
//      mErrors.copyAllErrors(mEdorCalZT.mErrors);
//      mErrors.addOneError("计算应退金额失败!");
//      return false;
//    }
    	 */
    	/*    mGetMoney = mGetMoney * mScale;
            mCashValue += mEdorCalZT.getBaseCashValue();
            mAddupBonus += mEdorCalZT.getBonusCashValue();

            mCashValue = mCashValue * mScale;
            mAddupBonus = mAddupBonus * mScale;

            mGetMoney = this.getRound(mGetMoney);
            mCashValue = this.getRound(mCashValue);
            mAddupBonus = this.getRound(mAddupBonus);

            if (mCashValue > 0) {
              mCashValue = 0 - mCashValue;
            }
            if (mAddupBonus > 0) {
              mAddupBonus = 0 - mAddupBonus;
            }
    	 */
    	logger.debug(mLPDutySet.size() + "~开始计算数据准备～～～～～～～");
    	LPDutySchema aLPDutySchema = new LPDutySchema();
    	LPDutySet aLPDutySet = new LPDutySet();
    	LCPremSet aLCPremSet = new LCPremSet();
    	for (int i = 1; i <= mLPDutySet.size(); i++) {
    		LCDutyDB tLCDutyDB = new LCDutyDB();
    		LCDutySet tLCDutySet = new LCDutySet();
    		tLCDutyDB.setPolNo(mLPDutySet.get(i).getPolNo());
    		tLCDutyDB.setDutyCode(mLPDutySet.get(i).getDutyCode());
    		tLCDutySet = tLCDutyDB.query();
    		if (tLCDutySet.size() < 1) {
    			mErrors.addOneError("查询责任定义表失败!");
    			return false;
    		}

    		aLPDutySchema = new LPDutySchema();
    		mRef.transFields(aLPDutySchema, tLCDutySet.get(1));
    		aLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
    		aLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());

    		LMDutyDB tLMDutyDB = new LMDutyDB();
    		LMDutySet tLMDutySet = new LMDutySet();
    		LMDutySchema tLMDutySchema = new LMDutySchema();
    		tLMDutyDB.setDutyCode(tLCDutySet.get(1).getDutyCode().substring(0,
    				6));
    		tLMDutySet = tLMDutyDB.query();
    		if (tLMDutySet.size() < 1) {
    			mErrors.addOneError("查询责任定义表失败!");
    			return false;
    		}
    		tLMDutySchema = tLMDutySet.get(1);
    		if (tLMDutySchema.getCalMode().equals("G") &&
    				mLPDutySet.get(i).getAmnt() > 0) {
    			logger.debug("-按保额----------" + mLPDutySet.get(i).getAmnt());
    			aLPDutySchema.setAmnt(mLPDutySet.get(i).getAmnt());
    			//aLPDutySchema.setPrem(mLPDutySet.get(i).getPrem());
    			mChgAmnt = tLCDutySet.get(1).getAmnt() -
    			mLPDutySet.get(i).getAmnt();
    			logger.debug("保额减少：" + mChgAmnt);
    			if (mChgAmnt < 0) {
    				mErrors.addOneError("输入的保额不符合规范!");
    				return false;
    			}
    			mLPPolSchema.setAmnt(mLPPolSchema.getAmnt() - mChgAmnt);
    			logger.debug("保单保额" + mLPPolSchema.getAmnt());
    		} else if (tLMDutySchema.getCalMode().equals("P") &&
    				mLPDutySet.get(i).getPrem() > 0) {
    			logger.debug("-按保费----------" + mLPDutySet.get(i).getPrem());
    			aLPDutySchema.setPrem(mLPDutySet.get(i).getPrem());
    			//aLPDutySchema.setAmnt(mLPDutySet.get(i).getAmnt());
    			mChgPrem = -mLPDutySet.get(i).getPrem() +
    			tLCDutySet.get(1).getPrem();
    			logger.debug(mChgPrem);
    			if (mChgPrem < 0) {
    				mErrors.addOneError("输入的保费不符合规范!");
    				return false;
    			}
    			mLPPolSchema.setPrem(mLPPolSchema.getPrem() - mChgPrem);
    		} else {
    			logger.debug("-保额保费都要输入----------");
    			aLPDutySchema.setAmnt(mLPDutySet.get(i).getAmnt());
    			aLPDutySchema.setPrem(mLPDutySet.get(i).getPrem());
    			mChgAmnt = -mLPDutySet.get(i).getAmnt() +
    			tLCDutySet.get(1).getAmnt();
    			mChgPrem = -mLPDutySet.get(i).getPrem() +
    			tLCDutySet.get(1).getPrem();
    			logger.debug("保额减少：" + mChgAmnt);
    			logger.debug("保费减少：" + mChgPrem);
    			if (mChgPrem < 0 && mChgAmnt < 0) {
    				mErrors.addOneError("输入的保额保费不符合规范!");
    				return false;
    			}
    			if (mLPDutySet.get(i).getAmnt() > 0 && mChgAmnt < 0) {
    				mErrors.addOneError("输入的保额不符合规范!");
    				return false;
    			}
    			if (mLPDutySet.get(i).getPrem() > 0 && mChgPrem < 0) {
    				mErrors.addOneError("输入的保费不符合规范!");
    				return false;
    			}
    			mLPPolSchema.setAmnt(mLPPolSchema.getAmnt() - mChgAmnt);
    			mLPPolSchema.setPrem(mLPPolSchema.getPrem() - mChgPrem);

    		}
    		if (aLPDutySchema.getCalRule() != null &&
    				aLPDutySchema.getCalRule().equals("3")) {
    			logger.debug("-约定费率----------");
    			aLPDutySchema.setAmnt(mLPDutySet.get(i).getAmnt());
    			aLPDutySchema.setPrem(mLPDutySet.get(i).getPrem());
    			mChgAmnt = -mLPDutySet.get(i).getAmnt() +
    			tLCDutySet.get(1).getAmnt();
    			mChgPrem = -mLPDutySet.get(i).getPrem() +
    			tLCDutySet.get(1).getPrem();
    			logger.debug("保额减少：" + mChgAmnt);
    			logger.debug("保费减少：" + mChgPrem);
    			if (mChgPrem < 0 || mChgAmnt < 0) {
    				mErrors.addOneError("输入的保额保费不符合规范!此责任的计算规则是约定费率，请输入保额保费！");
    				return false;
    			}
    			mLPPolSchema.setAmnt(mLPPolSchema.getAmnt() - mChgAmnt);
    			mLPPolSchema.setPrem(mLPPolSchema.getPrem() - mChgPrem);
    		}
    		if (aLPDutySchema.getCalRule() != null &&
    				aLPDutySchema.getCalRule().equals("1")) {
    			logger.debug("-统一费率----------");
    			aLPDutySchema.setCalRule("3");
    			aLPDutySchema.setAmnt(mLPDutySet.get(i).getAmnt());
    			aLPDutySchema.setFloatRate(tLCDutySet.get(1).getFloatRate());
    			logger.debug(aLPDutySchema.getAmnt() + "*" +
    					aLPDutySchema.getFloatRate());
    			aLPDutySchema.setPrem(aLPDutySchema.getAmnt() *
    					aLPDutySchema.getFloatRate());
    			logger.debug("先算出保费：" + aLPDutySchema.getPrem());
    			mChgAmnt = -mLPDutySet.get(i).getAmnt() +
    			tLCDutySet.get(1).getAmnt();
    			mChgPrem = -aLPDutySchema.getPrem() + tLCDutySet.get(1).getPrem();
    			logger.debug("保额减少：" + mChgAmnt);
    			logger.debug("保费减少：" + mChgPrem);
    			if (mChgAmnt < 0 || mChgAmnt < 0) {
    				mErrors.addOneError("输入的保额不符合规范!");
    				return false;
    			}
    			mLPPolSchema.setAmnt(mLPPolSchema.getAmnt() - mChgAmnt);
    			mLPPolSchema.setPrem(mLPPolSchema.getPrem() - mChgPrem);
    		}

    		aLPDutySet.add(aLPDutySchema);

    		LCPremDB tLCPremDB = new LCPremDB();
    		tLCPremDB.setPolNo(mLPPolSchema.getPolNo());
    		tLCPremDB.setDutyCode(aLPDutySchema.getDutyCode());
    		aLCPremSet = tLCPremDB.query();
    		if (aLCPremSet.size() < 1) {
    			mErrors.addOneError("查询保费项表失败!");
    			return false;
    		}
    	}
//  	****************************end*****************************************//
    	double polMoney = 0.0;//保单总退费
    	double oldPolPrem = mLCPolSchema.getPrem();//保单总保费
    	try {
    		polMoney = mGrpEdorCalZT.calZTData(mLPEdorItemSchema);
    	} catch (Exception e) {
    		CError.buildErr(this, "计算客户号为："+mLCPolSchema.getInsuredNo()+"的减保金失败!");
    		return false; 
    	}
    	
    	String tClaimFlag = mGrpEdorCalZT.getClaimFlag();
    	if("Y".equals(tClaimFlag))
    	{
    		CError.buildErr(this, "该保单发生过理赔,不能做减保操作");
    		return false; 
    	}
    	//对保费项进行计算，包括已终止效力的保费项
    	logger.debug("********************保费重算 start***************");
    	ReCalBL tReCalBL = new ReCalBL(mLPPolSchema, mLPEdorItemSchema);

    	LCPolSchema aLCPolSchema = new LCPolSchema();
    	mRef.transFields(aLCPolSchema, mLPPolSchema);
    	LCPolSchema cLCPolSchema = new LCPolSchema();
    	cLCPolSchema.setSchema(aLCPolSchema);
    	LCPolBL cLCPolBL = new LCPolBL();
    	cLCPolBL.setSchema(cLCPolSchema);
    	tReCalBL.preLCPolSchema.setSchema(cLCPolSchema);

    	LCDutyBLSet cLCDutyBLSet = new LCDutyBLSet();
    	for (int i = 1; i <= aLPDutySet.size(); i++) {
    		LCDutySchema cLCDutySchema = new LCDutySchema();
    		mRef.transFields(cLCDutySchema, aLPDutySet.get(i));
    		cLCDutyBLSet.add(cLCDutySchema);
    	}
    	tReCalBL.preLCDutySet.add(cLCDutyBLSet);

    	//    LCDutyBLSet eLCDutyBLSet = new LCDutyBLSet();
    	//   LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
    	//   mRef.transFields(tLPEdorItemSchema, mLPEdorItemSchema);
    	//   tLPEdorItemSchema.setPolNo(mLPPolSchema.getPolNo());
    	//   eLCDutyBLSet = tReCalBL.getRecalDuty(tLPEdorItemSchema);

    	LCPremBLSet cLCPremBLSet = new LCPremBLSet();
    	cLCPremBLSet.clear();
    	cLCPremBLSet.add(aLCPremSet);
    	tReCalBL.preLCPremSet.clear();
    	tReCalBL.preLCPremSet.add(aLCPremSet);

    	LCGetBLSet cLCGetBLSet = new LCGetBLSet();
    	LCGetDB tLCGetDB = new LCGetDB();
    	LCGetSet tLCGetSet = new LCGetSet();
    	tLCGetDB.setPolNo(mLPPolSchema.getPolNo());
    	tLCGetSet = tLCGetDB.query();
    	cLCGetBLSet.clear();
    	cLCGetBLSet.add(tLCGetSet);
    	tReCalBL.preLCGetSet.clear();
    	tReCalBL.preLCGetSet.add(tLCGetSet);

    	if (!tReCalBL.recalWithData(cLCPolBL,
    			cLCDutyBLSet, cLCPremBLSet, cLCGetBLSet,
    			mLPEdorItemSchema)) {
    		CError tError = new CError();
    		tError.moduleName = "PEdorPTDetailBL";
    		tError.functionName = "dealData";
    		tError.errorMessage = tReCalBL.mErrors.getFirstError();
    		this.mErrors.addOneError(tError);
    		return false;
    	}
    	
    	LPPolSchema aftLPPolSchema = tReCalBL.aftLPPolSet.get(1);
    	double oldAmnt = mLCPolSchema.getAmnt();
    	double newAmnt = aftLPPolSchema.getAmnt();
    	
    	tValue = (1-newAmnt/oldAmnt)*(polMoney/oldPolPrem);

//  	职业健康加费 暂时不考虑
    	tprem = 0;
    	tamnt = 0;
    	double prem = 0.0;
    	double amnt = 0;
    	int paycount = 0; //定额养老型险种计算交了几期
    	String sIntvFlag = "M";
    	if (mRiskType.equals("L")) {
    		if (mLCPolSchema.getPayIntv() == 12) { //年缴
    			sIntvFlag = "Y";
    		}
    		paycount = PubFun.calInterval(mLCPolSchema.getCValiDate(),
    				mLCPolSchema.getPaytoDate(),
    				sIntvFlag) + 1;
    		if (mLCPolSchema.getPayIntv() == 3) { //季缴
    			paycount = paycount / 3;
    		}
    		if (mLCPolSchema.getPayIntv() == 6) { //半年缴
    			paycount = paycount / 6;
    		}
    		if (mLCPolSchema.getPayIntv() == 0 ||
    				mLCPolSchema.getPrem() == mLCPolSchema.getSumPrem()) { //承保未交续期
    			paycount = 1;
    		}
    		logger.debug("定额养老型险种计算交了几期:" + paycount);
    	}

    	mLPPremSet = tReCalBL.aftLPPremSet;
    	for (int j = 1; j <= mLPPremSet.size(); j++) {
    		LCPremDB aLCPremDB = new LCPremDB();
    		LCPremSchema aLCPremSchema = new LCPremSchema();
    		aLCPremDB.setPolNo(mLPPremSet.get(j).getPolNo());
    		aLCPremDB.setDutyCode(mLPPremSet.get(j).getDutyCode());
    		aLCPremDB.setPayPlanCode(mLPPremSet.get(j).getPayPlanCode());
    		aLCPremSchema = aLCPremDB.query().get(1);
    		mLPPremSet.get(j).setPaytoDate(aLCPremSet.get(1).getPaytoDate());
    		mLPPremSet.get(j).setOperator(aLCPremSet.get(1).getOperator());
    		mLPPremSet.get(j).setMakeDate(aLCPremSet.get(1).getMakeDate());
    		mLPPremSet.get(j).setMakeTime(aLCPremSet.get(1).getMakeTime());
    		mLPPremSet.get(j).setStandPrem(aLCPremSchema.getStandPrem());
    		mLPPremSet.get(j).setPaytoDate(aLCPremSchema.getPaytoDate());
    		prem = aLCPremSet.get(1).getPrem();
    		double sumprem = PubFun.round((prem * tValue + prem * paycount),2);
    		if (mRiskType.equals("L")) {
    			sumprem = calNowPrize(sumprem,
    					mLPEdorItemSchema.getEdorValiDate());
    		}
    		mLPPremSet.get(j).setSumPrem(aLCPremSchema.getSumPrem() -
    				sumprem);
    	}

    	mLPDutySet = tReCalBL.aftLPDutySet;
    	double totleTFMoney = 0.0;
    	for (int j = 1; j <= mLPDutySet.size(); j++) {
    		LCDutyDB aLCDutyDB = new LCDutyDB();
    		LCDutySchema aLCDutySchema = new LCDutySchema();
    		aLCDutyDB.setPolNo(mLPDutySet.get(j).getPolNo());
    		aLCDutyDB.setDutyCode(mLPDutySet.get(j).getDutyCode());
    		aLCDutySchema = aLCDutyDB.query().get(1);
    		mLPDutySet.get(j).setCalRule(aLCDutySchema.getCalRule());
    		mLPDutySet.get(j).setStandPrem(aLCDutySchema.getStandPrem());
    		mLPDutySet.get(j).setOperator(aLCDutySchema.getOperator());
    		mLPDutySet.get(j).setMakeDate(aLCDutySchema.getMakeDate());
    		mLPDutySet.get(j).setMakeTime(aLCDutySchema.getMakeTime());
    		mLPDutySet.get(j).setPaytoDate(aLCDutySchema.getPaytoDate());
    		logger.debug("计算规则要还原＝" + mLPDutySet.get(j).getCalRule());
    		prem = aLCDutySchema.getPrem();
    		tprem += (prem-mLPDutySet.get(j).getPrem());
    		amnt = -mLPDutySet.get(j).getAmnt() + aLCDutySchema.getAmnt();
    		tamnt = tamnt + amnt;
    		
    		logger.debug("tprem" + tprem + "#########tamnt" + tamnt);
    		double getmoney = PubFun.round((prem * tValue + prem * paycount),2);
    		if (mRiskType.equals("L")) {
    			getmoney = calNowPrize(getmoney,
    					mLPEdorItemSchema.getEdorValiDate());
    		}

    		totleTFMoney+=getmoney;
    		mLPDutySet.get(j).setSumPrem(mLPDutySet.get(j).getSumPrem() -
    				getmoney);
    		LJSGetEndorseSchema tLJSGetEndorseSchema = new
    		LJSGetEndorseSchema();
    		tLJSGetEndorseSchema = this.initLJSGetEndorse("TB");
    		tLJSGetEndorseSchema.setGetMoney(getmoney);
    		tLJSGetEndorseSchema.setDutyCode(aLCDutySchema.getDutyCode());
    		LMDutyPayRelaDB tLMDutyPayRelaDB = new LMDutyPayRelaDB();
    		tLMDutyPayRelaDB.setDutyCode(aLCDutySchema.getDutyCode());
    		tLJSGetEndorseSchema.setPayPlanCode(tLMDutyPayRelaDB.query().get(1).
    				getPayPlanCode());
    		tLJSGetEndorseSchema.setSubFeeOperationType("TB");
    		tLJSGetEndorseSet.add(tLJSGetEndorseSchema);
    	}
    	mLPPolSchema.setPrem(mLCPolSchema.getPrem() - tprem);
    	mLPPolSchema.setSumPrem(mLCPolSchema.getSumPrem() - totleTFMoney);
    	mLPPolSchema.setPaytoDate(mLCPolSchema.getPaytoDate());
    	mLPPolSchema.setStandPrem(aLCPolSchema.getStandPrem());
    	mLPPolSchema.setAmnt(mLCPolSchema.getAmnt() - tamnt);
    	mLPPolSchema.setOperator(mLCPolSchema.getOperator());
    	mLPPolSchema.setMakeDate(mLCPolSchema.getMakeDate());
    	mLPPolSchema.setMakeTime(mLCPolSchema.getMakeTime());

    	mLPGetSet = tReCalBL.aftLPGetSet;
    	for (int j = 1; j <= mLPGetSet.size(); j++) {
    		mLPGetSet.get(j).setOperator(tLCGetSet.get(1).getOperator());
    		mLPGetSet.get(j).setMakeDate(tLCGetSet.get(1).getMakeDate());
    		mLPGetSet.get(j).setMakeTime(tLCGetSet.get(1).getMakeTime());
    	}
//  	mLPContSchema.setPrem(mLPContSchema.getPrem() - tprem);
//// mLPContSchema.setSumPrem(mLPContSchema.getSumPrem() - tprem);
//  	mLPContSchema.setAmnt(mLPContSchema.getAmnt() - tamnt);
//  	mLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//  	mLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());

    	logger.debug("NEW amnt＝" + mLPPolSchema.getAmnt());
    	logger.debug("new Prem＝" + mLPPolSchema.getPrem());
    	logger.debug("原保单保费" + mLCPolSchema.getPrem());
    	mCashValue = tprem;
    	mGetMoney = PubFun.round(tprem * tValue,2);
//  	}

    	logger.debug("=========mGetMoney==========" + mGetMoney);

    	if (mGetMoney > 0) {
    		mGetMoney = 0 - mGetMoney;
    	}

//  	mMap.put(mLPContSchema, "DELETE&INSERT");
    	mMap.put(mLPPolSchema, "DELETE&INSERT");
    	mMap.put(mLPDutySet, "DELETE&INSERT");
    	mMap.put(mLPPremSet, "DELETE&INSERT");
    	mMap.put(mLPGetSet, "DELETE&INSERT");
    	mMap.put(tLJSGetEndorseSet, "DELETE&INSERT");

    	return true;
    }


    /**
     * 生成交退费记录
     * @return
     */
    private LJSGetEndorseSchema initLJSGetEndorse(String strfinType) {
        LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();

        Reflections tReflections = new Reflections();
        //mPayCount++;
        mLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
        mLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
        mLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema.getEdorType());
        mLJSGetEndorseSchema.setFeeFinaType("TB");
        mLJSGetEndorseSchema.setDutyCode("0");
        mLJSGetEndorseSchema.setPayPlanCode("0");
        mLJSGetEndorseSchema.setPolNo(mLPPolSchema.getPolNo());

        //走保全交费财务流程
        mLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo()); //保全给付用批单号
        mLJSGetEndorseSchema.setOtherNoType("3"); //保全给付

        tReflections.transFields(mLJSGetEndorseSchema, mLCPolSchema);

        mLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
        mLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
        mLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
        mLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
        mLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);

        mLJSGetEndorseSchema.setGetDate(mLPEdorItemSchema.getEdorValiDate());
        mLJSGetEndorseSchema.setGetFlag("1");
        tLJSGetEndorseSchema.setSchema(mLJSGetEndorseSchema);

        return tLJSGetEndorseSchema;
    }


    //四舍六入五靠偶数，保留两位
    private double getRound(double tValue) {
        String t = "0.00";
        DecimalFormat tDF = new DecimalFormat(t);
        return Double.parseDouble(tDF.format(tValue));
    }


    /**
     * 更新附加险续期标准
     * @param aLPPolSchema LPPolSchema
     * @return boolean
     */
    private boolean dealRNewPol(LPPolSchema aLPPolSchema) {
        LCPolDB tLCPolDB = new LCPolDB();
        LCPolSet tLCPolSet = new LCPolSet();
        LCPolSchema tLCPolSchema = new LCPolSchema();
        LCPolSchema eLCPolSchema = new LCPolSchema();
        tLCPolDB.setRiskCode(aLPPolSchema.getRiskCode());
        tLCPolDB.setContNo(aLPPolSchema.getContNo());
        tLCPolDB.setAppFlag("9");
        tLCPolSet = tLCPolDB.query();
        if (tLCPolSet == null || tLCPolSet.size() < 1) {
            return true;
        }
        tLCPolSchema = tLCPolSet.get(1);
        eLCPolSchema.setSchema(mLCPolSchema);
        eLCPolSchema.setAmnt(mLCPolSchema.getAmnt() - mLPPolSchema.getAmnt());
        eLCPolSchema.setMult(mLCPolSchema.getMult() - mLPPolSchema.getMult());
        logger.debug("险种号：===========" + eLCPolSchema.getPolNo());
        logger.debug("续期险种号：===========" + tLCPolSchema.getPolNo());
        RNewPolCalBL tRNewPolCalBL = new RNewPolCalBL();
        VData tVData = new VData();
        tVData.add(eLCPolSchema);
        if (!tRNewPolCalBL.submitData(tVData, "")) {
            mErrors.copyAllErrors(tRNewPolCalBL.mErrors);
            mErrors.addOneError("处理续期险种失败!");
            return false;
        }
        VData tResult = new VData();
        tResult = tRNewPolCalBL.getResult();
        LCPolSchema aLCPolSchema = new LCPolSchema();
        LCPremSet aLCPremSet = new LCPremSet();
        LCGetSet aLCGetSet = new LCGetSet();
        LCDutySet aLCDutySet = new LCDutySet();
        aLCPolSchema = (LCPolSchema) tResult.getObjectByObjectName(
                "LCPolSchema", 0);
        //Pol
        aLCPolSchema.setPolNo(tLCPolSchema.getPolNo());
        //Duty
        aLCDutySet = (LCDutySet) tResult.getObjectByObjectName(
                "LCDutySet", 0);
        for (int k = 1; k <= aLCDutySet.size(); k++) {
            aLCDutySet.get(k).setPolNo(tLCPolSchema.getPolNo());
        }
        //Prem
        aLCPremSet = (LCPremSet) tResult.getObjectByObjectName(
                "LCPremSet", 0);
        for (int k = 1; k <= aLCPremSet.size(); k++) {
            aLCPremSet.get(k).setPolNo(tLCPolSchema.getPolNo());
        }
        //Get
        aLCGetSet = (LCGetSet) tResult.getObjectByObjectName(
                "LCGetSet", 0);
        for (int k = 1; k <= aLCGetSet.size(); k++) {
            aLCGetSet.get(k).setPolNo(tLCPolSchema.getPolNo());
        }

        mMap.put(aLCPolSchema, "DELETE&INSERT");
        mMap.put(aLCDutySet, "DELETE&INSERT");
        mMap.put(aLCPremSet, "DELETE&INSERT");
        mMap.put(aLCGetSet, "DELETE&INSERT");
        return true;
    }

    /**
     * 计算现价函数
     */
    private double calNowPrize(double TMoney,
                               String tEdorValiDate) {
	if (mLPGrpEdorItemSchema.getEdorTypeCal() == null ||
	   mLPGrpEdorItemSchema.getEdorTypeCal().equals("")) {
	   mLPGrpEdorItemSchema.setEdorTypeCal("000");
       }

        LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
        tLMEdorCalDB.setRiskCode(mLCPolSchema.getRiskCode());
        tLMEdorCalDB.setEdorType("PT");
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
        tCalculator.addBasicFactor("PolNo", mLCPolSchema.getPolNo());
	tCalculator.addBasicFactor("EdorTypeCal", mLPGrpEdorItemSchema.getEdorTypeCal());
        String GAresult = tCalculator.calculate();
        logger.debug("sql:" + tCalculator.getCalSQL());
        logger.debug("result:" + GAresult);
        TMoney = TMoney * Double.parseDouble(GAresult);
        logger.debug("TMoney=====" + TMoney);

        return TMoney;
    }


}
