package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.bl.LPContBL;
import com.sinosoft.lis.bq.BqCalBL;
import com.sinosoft.lis.bq.BqCode;
import com.sinosoft.lis.bq.EdorCalZT;
import com.sinosoft.lis.bq.ReCalBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.db.LMDutyDB;
import com.sinosoft.lis.db.LMDutyPayRelaDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LMDutySchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpContSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vbl.LCPremBLSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LMDutySet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;


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
public class PGrpEdorAADetailBL implements BusinessService{
private static Logger logger = Logger.getLogger(PGrpEdorAADetailBL.class);
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


    /** 全局数据 */
    private LCPolSchema mLCPolSchema = new LCPolSchema();

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
    private LPGrpContSchema mLPGrpContSchema = new LPGrpContSchema();
    private LPGrpPolSchema mLPGrpPolSchema = new LPGrpPolSchema();
    private LPContSchema mLPContSchema = new LPContSchema();
    private LCContSchema mLCContSchema = new LCContSchema();
    private LPPolSet mLPPolSet = new LPPolSet();
    private LPGrpPolSet mLPGrpPolSet = new LPGrpPolSet();
    private LPDutySet mLPDutySet = new LPDutySet();
    private LPPremSet mLPPremSet = new LPPremSet();
    private LPGetSet mLPGetSet = new LPGetSet();
    private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
    private LJSGetEndorseSchema mLJSGetEndorseSchema = new LJSGetEndorseSchema();

    public PGrpEdorAADetailBL() {

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
                "----------------PGrpEdorAADetailBL------------------");

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData()) {
            return false;
        }
        logger.debug("-------end getInputData");
	if (mOperate.equals("INSERT||MAIN")) {
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
	}

        //使用挂帐
     if (mOperate.equals("INSERT||EY")) {
         if (!prepareDataEY()) {
             return false;
         }
     }
     //为什么要把他清空呢？在这里把mResult清空，前面类调用的返回信息全部被清空 Edit by ningyc 20110923 
     //mResult.clear();   
     return true;
    }

    private boolean prepareDataEY() {

        logger.debug(
            "=====This is GrpEdorAADetailBLF->prepareDataEY=====\n");

        if (!checkEdorData()){
            return false;
        }

        LCGrpContDB tLCGrpContDB = new LCGrpContDB();
        tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
        LCGrpContSchema tLCGrpContSchema = tLCGrpContDB.query().get(1);
        LPGrpContSchema tLPGrpContSchema = new LPGrpContSchema();
        mRef.transFields(tLPGrpContSchema, tLCGrpContSchema);
        tLPGrpContSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
        tLPGrpContSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());

        //团单挂帐的费用存储在LCGrpCont的Dif字段
        double tDif = tLCGrpContSchema.getDif();
        LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
        String get_sql =
            "select * from LJSGetEndorse where EndorsementNo = '" +
            tLPGrpContSchema.getEdorNo() +
            "' and FeeOperationType = 'AA' and FeeFinaType in ('BF','GL','LX')";
        LJSGetEndorseSet tLJSGetEndorseSet = tLJSGetEndorseDB.executeQuery(get_sql);
        double tGetMoney = 0.00; //AA需要的交费
        double mGetMoney = 0.00; //AA以为的可以用挂帐的保全项目的交费
        for (int i = 1; i <= tLJSGetEndorseSet.size(); i++) {
            tGetMoney += tLJSGetEndorseSet.get(i).getGetMoney();
        }
        tGetMoney = PubFun.round(tGetMoney, 2);
        logger.debug("AA需要交的钱：" + tGetMoney);
        if (tDif - tGetMoney < 0) {

            //@@错误处理
            CError tError = new CError();
            tError.moduleName = "GrpEdorAADetailBL";
            tError.functionName = "prepareDataEY";
            tError.errorMessage = "挂帐金额不足以支付本次保全缴费，请更换缴费方式!";
            this.mErrors.addOneError(tError);

            return false;
        }

        //查询使用挂帐的金额缴费的记录，因为只有增人和增险种才能使用挂帐，
        //并且，这两个保全项目可以同时作，所以这里是查NR是否有使用挂帐。
        get_sql = "select * from LJSGetEndorse where EndorsementNo = '" +
                  tLPGrpContSchema.getEdorNo() +
                  "' and FeeOperationType != 'AA' and FeeFinaType = 'EY'";
        tLJSGetEndorseSet = new LJSGetEndorseSet();
        try {
            tLJSGetEndorseSet = tLJSGetEndorseDB.executeQuery(get_sql);
        } catch (Exception ex){
            CError.buildErr(this, ex.toString());
            ex.printStackTrace();
            return false;
        }
        if (tLJSGetEndorseSet.size() > 0) {
	    for(int i=1;i<=tLJSGetEndorseSet.size();i++){
		mGetMoney=mGetMoney+tLJSGetEndorseSet.get(i).getGetMoney();
	    }
            double tMoney = tGetMoney - mGetMoney;
            tMoney = PubFun.round(tMoney, 2);
            logger.debug("NR + AA需要交的钱：" + tMoney);
            if (tDif - tMoney < 0) {
                CError tError = new CError();
                tError.moduleName = "GrpEdorAADetailBL";
                tError.functionName = "prepareDataEY";
                tError.errorMessage = "挂帐金额不足以支付本次保全，请更换缴费方式!";
                this.mErrors.addOneError(tError);

                return false;
            }
        }

//    double tdif_new = tdif - tGetMoney;
//    tLPGrpContSchema.setDif(tdif_new);
//    tLPGrpContSchema.setModifyDate(PubFun.getCurrentDate());
//    tLPGrpContSchema.setModifyTime(PubFun.getCurrentTime());

        LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
        mRef.transFields(tLJSGetEndorseSchema, tLPGrpContSchema);
        tLJSGetEndorseSchema.setGetNoticeNo(tLPGrpContSchema.getEdorNo());
        tLJSGetEndorseSchema.setEndorsementNo(tLPGrpContSchema.getEdorNo());
        tLJSGetEndorseSchema.setGrpPolNo("0000");
        tLJSGetEndorseSchema.setContNo("0000");
        tLJSGetEndorseSchema.setPolNo("000000");
        tLJSGetEndorseSchema.setFeeOperationType(tLPGrpContSchema.getEdorType());
        tLJSGetEndorseSchema.setGetDate(mLPGrpEdorItemSchema.getEdorValiDate());
        tLJSGetEndorseSchema.setGetMoney(-1 * tGetMoney);
        tLJSGetEndorseSchema.setFeeOperationType("AA");

        //挂帐缴费
        tLJSGetEndorseSchema.setFeeFinaType("EY");
        tLJSGetEndorseSchema.setPayPlanCode("00000000"); //无作用
        tLJSGetEndorseSchema.setDutyCode("0"); //无作用，但一定要，转ljagetendorse时非空
        tLJSGetEndorseSchema.setOtherNo(tLPGrpContSchema.getEdorNo()); //无作用

        //保全给付
        tLJSGetEndorseSchema.setOtherNoType("3");
        tLJSGetEndorseSchema.setGetFlag("0");
        tLJSGetEndorseSchema.setInsuredNo("0000");
        tLJSGetEndorseSchema.setKindCode("A");
        tLJSGetEndorseSchema.setRiskCode("000");
        tLJSGetEndorseSchema.setRiskVersion("2002");
        //tLJSGetEndorseSchema.setHandler("");
        tLJSGetEndorseSchema.setSubFeeOperationType("EY");
        tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
        tLJSGetEndorseSchema.setManageCom(mGlobalInput.ManageCom);
        tLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
        tLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
        tLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
        tLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
        try {
	    MMap tMap = new MMap();
	    VData tResult = new VData();
            tMap.put(tLJSGetEndorseSchema, "DELETE&INSERT");
            tResult.clear();
            tResult.add(tMap);
	    PubSubmit tSubmit = new PubSubmit();
	    if (!tSubmit.submitData(tResult, "")) {
	      // @@错误处理
	      this.mErrors.copyAllErrors(tSubmit.mErrors);
	      CError tError = new CError();
	      tError.moduleName = "PEdorPTDetailBL";
	      tError.functionName = "submitData";
	      tError.errorMessage = "数据提交失败!";
	      this.mErrors.addOneError(tError);
	      return false;
    }
        } catch (Exception e) {
            CError tError = new CError();
            tError.moduleName = "GrpEdorAADetailBL";
            tError.functionName = "prepareDataEY";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错! ";
            this.mErrors.addOneError(tError);

            return false;
        }

        return true;
}

    /**
        *
        **/
       private boolean checkEdorData(){

	   logger.debug(
	       "=====This is GrpEdorNIDetailBL->checkEdorData=====\n");
	   LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
	   tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
	   tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
	   try {
	       mLPGrpEdorItemSchema = tLPGrpEdorItemDB.query().get(1);
	   } catch (Exception ex){
	       CError.buildErr(this, ex.toString());
	       ex.printStackTrace();
	       return false;
	   }
	   if (mLPGrpEdorItemSchema == null){
	       CError.buildErr(this, "查询团体保全项目表失败！");
	       return false;
	   }
	   String tEdorState = mLPGrpEdorItemSchema.getEdorState();
	   if (tEdorState.equals("2")) {
	       CError tError = new CError();
	       tError.moduleName = "GrpEdorNIDetailBL";
	       tError.functionName = "prepareDataEY";
	       tError.errorMessage = "该保全已经申请确认不能修改!";
	       this.mErrors.addOneError(tError);

	       return false;
	   }
	   ExeSQL tExeSQL = new ExeSQL();

	   String tSql="select * from ljsgetendorse where endorsementno='"+
		      mLPGrpEdorItemSchema.getEdorNo()+
		       "' and feeoperationtype='"+mLPGrpEdorItemSchema.getEdorType()+
		       "' and feefinatype in ('PC','EY')";
	   logger.debug("======"+tSql);
	   SSRS tSSRS = tExeSQL.execSQL(tSql);
	   if (tSSRS.getMaxRow()>0)
	   {
	       MMap sMap = new MMap();
	       VData sResult = new VData();
	       sMap.put("DELETE from LJSGetEndorse where EndorsementNo = '" +
	         mLPGrpEdorItemSchema.getEdorNo() +
	          "' and FeeOperationType = 'AA' and feefinatype='EY'", "DELETE");
	 sResult.clear();
	 sResult.add(sMap);
	 PubSubmit tSubmit = new PubSubmit();
	 if (!tSubmit.submitData(sResult, "")) {
	   // @@错误处理
	   this.mErrors.copyAllErrors(tSubmit.mErrors);
	   CError tError = new CError();
	   tError.moduleName = "PEdorPTDetailBL";
	   tError.functionName = "submitData";
	   tError.errorMessage = "数据提交失败!";
	   this.mErrors.addOneError(tError);
	   return false;
    }
//	       CError tError = new CError();
//	       tError.moduleName = "GrpEdorNIDetailBL";
//	       tError.functionName = "prepareDataEY";
//	       tError.errorMessage = "该保全已经申请已经做过使用挂帐或法人帐户或公共帐户，不能再做类似操作!";
//	       this.mErrors.addOneError(tError);
//	       return false;
	   }

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
//    logger.debug(mLPDutySet.size());
//    mEdorCalZT = new EdorCalZT(mGlobalInput);
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
        /*    try {
              int a = (int) mLPPolSchema.getAmnt();
              int b = (int) mLPPolSchema.getMult();
              if (a - mLPPolSchema.getAmnt() != 0) {
                mErrors.addOneError("所加金额非整数，请重新输入!");
                return false;
              }
              if (b - mLPPolSchema.getMult() != 0) {
                mErrors.addOneError("所加金额非整数，请重新输入!");
                return false;
              }
            } catch (Exception e) {
              mErrors.addOneError("所加金额非整数，请重新输入!");
              return false;
            }
            if (mLPPolSchema.getAmnt() == 0 && mLPPolSchema.getMult() == 0) {
              mErrors.addOneError("所加金额为零，请重新输入!");
              return false;
            }*/
        String strSQL = "select getmoney from ljsgetendorse where feeoperationtype = 'AA' and EndorsementNo = '"
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
        logger.debug("之前变更费用合计＝＝＝＝＝＝＝＝＝＝＝" + aGetMoney);
        return true;
    }


    /**
     * TK加保业务处理
     * @return boolean
     */
    private boolean dealData() {
        tChgAmnt = 0.0;
        tChgPrem = 0.0;
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

        logger.debug("---增额处理 start---");
        if (!MainRisk()) {
            mErrors.addOneError("增额处理失败!");
            return false;
        }

        mGetMoney += mLoanCorpus + mLoanInterest + mAutoPayPrem +
                mAutoPayInterest + mFinaBonus + mOwePrem + mOweInterest;
        mPayMoney += mLoanCorpus + mLoanInterest + mAutoPayPrem +
                mAutoPayInterest + mOwePrem + mOweInterest;

        mGetMoney = PubFun.round(mGetMoney,2);
        mPayMoney = PubFun.round(mPayMoney,2);

//    LPEdorItem要实现按险种保存
        mLPEdorItemSchema.setPolNo(mLPPolSchema.getPolNo());
        mLPEdorItemSchema.setChgAmnt(mLPPolSchema.getAmnt() -
                                     mLCPolSchema.getAmnt());
        mLPEdorItemSchema.setChgPrem(mLPPolSchema.getPrem() -
                                     mLCPolSchema.getPrem());
        mLPEdorItemSchema.setGetMoney(mGetMoney + aGetMoney);
        mLPEdorItemSchema.setEdorState("1");
//    logger.debug("#############################");
//    logger.debug("#ChgAmnt#" + mLPEdorItemSchema.getChgAmnt());
//    logger.debug("#ChgPrem#" + mLPEdorItemSchema.getChgPrem());
//    logger.debug("#############################");
//    mMap.put(mLPEdorItemSchema, "UPDATE");
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
        /*    //增加LPGrp表数据
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
         tChgAmnt += tLPGrpContSchema.getAmnt() - aLCGrpContSchema.getAmnt();
         tChgPrem += tLPGrpContSchema.getPrem() - aLCGrpContSchema.getPrem();
              }
            }
//    logger.debug("tChgAmnt:" + tChgAmnt + " tChgPrem:" + tChgPrem);
         mLPGrpContSchema.setAmnt(mLCGrpContSchema.getAmnt() + tChgAmnt + tamnt);
         mLPGrpContSchema.setPrem(mLCGrpContSchema.getPrem() + tChgPrem + tprem);
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
         tChgAmnt += tLPGrpPolSchema.getAmnt() - aLCGrpPolSchema.getAmnt();
         tChgPrem += tLPGrpPolSchema.getPrem() - aLCGrpPolSchema.getPrem();
//        logger.debug(tChgAmnt + "=========" + tChgPrem);
              }
            }
         mLPGrpPolSchema.setAmnt(mLCGrpPolSchema.getAmnt() + tChgAmnt + tamnt);
         mLPGrpPolSchema.setPrem(mLCGrpPolSchema.getPrem() + tChgPrem + tprem);
            mMap.put(mLPGrpPolSchema, "DELETE&INSERT");
            //END
         */
        LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
//    LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
//    tLJSGetEndorseSchema = this.initLJSGetEndorse("BF");
//    tLJSGetEndorseSchema.setGetMoney(mGetMoney);
//    tLJSGetEndorseSchema.setSubFeeOperationType("BF");
//    tLJSGetEndorseSet.add(tLJSGetEndorseSchema);

        if (mLoanCorpus != 0) {
            LJSGetEndorseSchema aLJSGetEndorseSchema = new LJSGetEndorseSchema();
            aLJSGetEndorseSchema = this.initLJSGetEndorse("DK");
            aLJSGetEndorseSchema.setGetMoney(mLoanCorpus);
            aLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_LoanCorpus);
            tLJSGetEndorseSet.add(aLJSGetEndorseSchema);
        }
        if (mLoanInterest != 0) {
            LJSGetEndorseSchema bLJSGetEndorseSchema = new LJSGetEndorseSchema();
            bLJSGetEndorseSchema = this.initLJSGetEndorse("LX");
            bLJSGetEndorseSchema.setGetMoney(mLoanInterest);
            bLJSGetEndorseSchema.setSubFeeOperationType(BqCode.
                    Pay_LoanCorpusInterest);
            tLJSGetEndorseSet.add(bLJSGetEndorseSchema);
        }
        if (mAutoPayPrem != 0) {
            LJSGetEndorseSchema cLJSGetEndorseSchema = new LJSGetEndorseSchema();
            cLJSGetEndorseSchema = this.initLJSGetEndorse("BF");
            cLJSGetEndorseSchema.setGetMoney(mAutoPayPrem);
            cLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_AutoPayPrem);
            tLJSGetEndorseSet.add(cLJSGetEndorseSchema);
        }
        if (mAutoPayInterest != 0) {
            LJSGetEndorseSchema dLJSGetEndorseSchema = new LJSGetEndorseSchema();
            dLJSGetEndorseSchema = this.initLJSGetEndorse("LX");
            dLJSGetEndorseSchema.setGetMoney(mAutoPayInterest);
            dLJSGetEndorseSchema.setSubFeeOperationType(BqCode.
                    Pay_AutoPayPremInterest);
            tLJSGetEndorseSet.add(dLJSGetEndorseSchema);
        }
        if (mFinaBonus != 0) {
            LJSGetEndorseSchema eLJSGetEndorseSchema = new LJSGetEndorseSchema();
            eLJSGetEndorseSchema = this.initLJSGetEndorse("TB");
            eLJSGetEndorseSchema.setGetMoney(mFinaBonus);
            eLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Get_FinaBonus);
            tLJSGetEndorseSet.add(eLJSGetEndorseSchema);
        }
        if (mAddupBonus != 0) {
            LJSGetEndorseSchema fLJSGetEndorseSchema = new LJSGetEndorseSchema();
            fLJSGetEndorseSchema = this.initLJSGetEndorse("TB");
            fLJSGetEndorseSchema.setGetMoney(mAddupBonus);
            fLJSGetEndorseSchema.setSubFeeOperationType(BqCode.
                    Get_BonusCashValue);
            tLJSGetEndorseSet.add(fLJSGetEndorseSchema);
        }

        mAddZY = 0;
        mAddJK = 0;

        if (mOwePrem != 0) {
            LJSGetEndorseSchema gLJSGetEndorseSchema = new LJSGetEndorseSchema();
            gLJSGetEndorseSchema = this.initLJSGetEndorse("BF");
            gLJSGetEndorseSchema.setGetMoney(mOwePrem);
            gLJSGetEndorseSchema.setSubFeeOperationType(BqCode.
                    Pay_Prem);
            tLJSGetEndorseSet.add(gLJSGetEndorseSchema);
        }
        if (mOweInterest != 0) {
            LJSGetEndorseSchema hLJSGetEndorseSchema = new LJSGetEndorseSchema();
            hLJSGetEndorseSchema = this.initLJSGetEndorse("LX");
            hLJSGetEndorseSchema.setGetMoney(mOweInterest);
            hLJSGetEndorseSchema.setSubFeeOperationType(BqCode.
                    Pay_PremInterest);
            tLJSGetEndorseSet.add(hLJSGetEndorseSchema);
        }

//    mMap.put(tLJSGetEndorseSet, "DELETE&INSERT");

        mResult.clear();
        mResult.add(mMap);

        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("GetMoney", mGetMoney);
//    tTransferData.setNameAndValue("PayMoney", mPayMoney);
//    tTransferData.setNameAndValue("CashValue", mCashValue);
//    tTransferData.setNameAndValue("LoanCorpus", mLoanCorpus);
//    tTransferData.setNameAndValue("LoanInterest", mLoanInterest);
//    tTransferData.setNameAndValue("AutoPayPrem", mAutoPayPrem);
//    tTransferData.setNameAndValue("AutoPayInterest", mAutoPayInterest);
//    tTransferData.setNameAndValue("FinaBonus", mFinaBonus);
//    tTransferData.setNameAndValue("AddupBonus", mAddupBonus);
//    tTransferData.setNameAndValue("AddZY", mAddZY);
//    tTransferData.setNameAndValue("AddJK", mAddJK);
//    tTransferData.setNameAndValue("OwePrem", mOwePrem);
//    tTransferData.setNameAndValue("OweInterest", mOweInterest);
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
     * 处理短期险的责任 add by sunsx 2008-09-25
     * @param tLPDutySchema
     * @return

    private boolean dealShortRiskDuty(LPDutySchema tLPDutySchema){
    	//TODO
    	double inputPrem = tLPDutySchema.getPrem();//界面传入的保费
    	double inputAmnt = tLPDutySchema.getAmnt();//界面传入的保额
    	
    	//查询C表的Duty 为P表
    	LCDutyDB tLCDutyDB = new LCDutyDB();
    	tLCDutyDB.setPolNo(tLPDutySchema.getPolNo());
    	tLCDutyDB.setDutyCode(tLPDutySchema.getDutyCode());
    	if(!tLCDutyDB.getInfo()){
    		mErrors.addOneError("查询责任定义表失败!");
            return false;
    	}
    	mRef.transFields(tLPDutySchema, tLCDutyDB.getSchema());
    	tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
    	tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
    	
    	double oldPrem = tLPDutySchema.getPrem();//原来的保费
    	double oldAmnt = tLPDutySchema.getAmnt();//原来的保额
    	
    	//查询险种定义表
    	LMDutyDB tLMDutyDB = new LMDutyDB();
    	tLMDutyDB.setDutyCode(tLPDutySchema.getDutyCode().substring(0,6));
    	if(!tLMDutyDB.getInfo()){
            mErrors.addOneError("查询责任定义表失败!");
            return false;
    	}
    	LMDutySchema tLMDutySchema = tLMDutyDB.getSchema();
    	
    	String tCalMode = tLMDutySchema.getCalMode();//保额保费的计算方法
    	
    	if(tCalMode.equals("G")){ //保额算保费 将界面传入的保额赋给PDUTY
    		
    		tLPDutySchema.setAmnt(inputAmnt);
    		
    		//将变动的值加到POL表上
    		double cAmnt = inputAmnt - oldAmnt;
    		mLPPolSchema.setAmnt(mLPPolSchema.getAmnt() + cAmnt);
    		
    	} else if (tCalMode.equals("P")) {//保费算保额 将界面传入的保费赋给PDUTY
    		
    		tLPDutySchema.setPrem(inputAmnt);
    		
    	}
    	
    	return true;
    }

     */
    
    /**
     * 主险减保业务处理 【taikang不分主附险，所有险种都按主险处理】
     * @return boolean
     */
    private boolean MainRisk() {
        LJSGetEndorseSet saveLJSGetEndorseSet = new LJSGetEndorseSet();
        String mRiskCode = mLCPolSchema.getRiskCode();
        String mRiskType = "S"; //S短险；L长险
        LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
        tLMRiskAppDB.setRiskCode(mRiskCode);
        LMRiskAppSchema tLMRiskAppSchema = tLMRiskAppDB.query().get(1);
        boolean isShortRisk = false;//是否是短险
        if (tLMRiskAppSchema.getRiskPeriod().equals("L")) {
            mRiskType = "L";
        }
        if (mRiskType.equals("S")) { //短险计算短期费率
        	isShortRisk = true;

            BqCalBase tBqCalBase = new BqCalBase();
            tBqCalBase.setEdorValiDate(mLPEdorItemSchema.getEdorValiDate());
            tBqCalBase.setCValiDate(mLCPolSchema.getCValiDate());
            tBqCalBase.setEndDate(mLCPolSchema.getPayEndDate());

            LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
            tLMEdorCalDB.setRiskCode("000000");
            tLMEdorCalDB.setCalType("GAddRate");
            tLMEdorCalDB.setEdorType(mLPEdorItemSchema.getEdorType());
            LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query(); //查找保费累计变动金额计算信息
            if (tLMEdorCalDB.mErrors.needDealError()) {
                mErrors.copyAllErrors(tLMEdorCalDB.mErrors);
                mErrors.addOneError(new CError("查询险种责任保全计算信息失败！"));
                return false;
            }
            if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
                logger.debug("没有计算信息，不作计算");
//      return true;
                tValue = 1;
            } else {
                BqCalBL tBqCalBL = new BqCalBL();
//                tValue = tBqCalBL.calChgMoney(tLMEdorCalSet.get(1).
//                                              getCalCode(), tBqCalBase); // 计算各险种保费补退费
    			tValue = tBqCalBL.calShortRate(tLMEdorCalSet.get(1)
    					.getCalCode(), tBqCalBase); // 计算各险种保费补退费
                logger.debug("==短期费率tValue==" + tValue);
                if (tBqCalBL.mErrors.needDealError()) {
                    mErrors.copyAllErrors(tBqCalBL.mErrors);
                    mErrors.addOneError(new CError("计算短期费率失败！"));
                    return false;
                }
            } //end add
        }
//**************************************************************************//
        logger.debug(mLPDutySet.size() + "~开始增额计算数据准备～～～～～～～");
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
                aLPDutySchema.setAmnt(mLPDutySet.get(i).getAmnt());
                mChgAmnt = mLPDutySet.get(i).getAmnt() -
                           tLCDutySet.get(1).getAmnt();
                if (mChgAmnt < 0) {
                    mErrors.addOneError("输入的保额不符合规范!");
                    return false;
                }
                mLPPolSchema.setAmnt(mLPPolSchema.getAmnt() + mChgAmnt);
            } else if (tLMDutySchema.getCalMode().equals("P") &&
                       mLPDutySet.get(i).getPrem() > 0) {
                aLPDutySchema.setPrem(mLPDutySet.get(i).getPrem());
                mChgPrem = mLPDutySet.get(i).getPrem() -
                           tLCDutySet.get(1).getPrem();
                if (mChgPrem < 0) {
                    mErrors.addOneError("输入的保费不符合规范!");
                    return false;
                }
                mLPPolSchema.setPrem(mLPPolSchema.getPrem() + mChgPrem);
            } else {
                aLPDutySchema.setAmnt(mLPDutySet.get(i).getAmnt());
                aLPDutySchema.setPrem(mLPDutySet.get(i).getPrem());
                mChgAmnt = mLPDutySet.get(i).getAmnt() -
                           tLCDutySet.get(1).getAmnt();
                mChgPrem = mLPDutySet.get(i).getPrem() -
                           tLCDutySet.get(1).getPrem();
                logger.debug("保额增加：" + mChgAmnt);
                logger.debug("保费增加：" + mChgPrem);
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
                mLPPolSchema.setAmnt(mLPPolSchema.getAmnt() + mChgAmnt);
                mLPPolSchema.setPrem(mLPPolSchema.getPrem() + mChgPrem);
            }
            if (aLPDutySchema.getCalRule() != null &&
                aLPDutySchema.getCalRule().equals("3")) {
                logger.debug("-约定费率----------");
                aLPDutySchema.setAmnt(mLPDutySet.get(i).getAmnt());
                aLPDutySchema.setPrem(mLPDutySet.get(i).getPrem());
                mChgAmnt = mLPDutySet.get(i).getAmnt() -
                           tLCDutySet.get(1).getAmnt();
                mChgPrem = mLPDutySet.get(i).getPrem() -
                           tLCDutySet.get(1).getPrem();
                if (mChgPrem < 0 || mChgAmnt < 0) {
                    mErrors.addOneError("输入的保额保费不符合规范!此责任的计算规则是约定费率，请输入保额保费！");
                    return false;
                }
//        double rate_old = tLCDutySet.get(1).getPrem() /
//                          tLCDutySet.get(1).getAmnt();
//        double rate_new = mLPDutySet.get(i).getPrem() /
//                          mLPDutySet.get(i).getAmnt();
//        if (rate_old > rate_new) {
//          mErrors.addOneError("输入的保额保费比例小于承保，请重新输入保额保费！");
//          return false;
//        }
                mLPPolSchema.setAmnt(mLPPolSchema.getAmnt() + mChgAmnt);
                mLPPolSchema.setPrem(mLPPolSchema.getPrem() + mChgPrem);
            }
            if (aLPDutySchema.getCalRule() != null &&
                aLPDutySchema.getCalRule().equals("1")) {
                logger.debug("-统一费率----------");
                aLPDutySchema.setCalRule("3");
                aLPDutySchema.setAmnt(mLPDutySet.get(i).getAmnt());
                aLPDutySchema.setFloatRate(tLCDutySet.get(1).getFloatRate());
                aLPDutySchema.setPrem(aLPDutySchema.getAmnt() *
                                      aLPDutySchema.getFloatRate());
                mChgAmnt = mLPDutySet.get(i).getAmnt() -
                           tLCDutySet.get(1).getAmnt();
                mChgPrem = aLPDutySchema.getPrem() - tLCDutySet.get(1).getPrem();
                if (mChgAmnt < 0 || mChgAmnt < 0) {
                    mErrors.addOneError("输入的保额不符合规范!");
                    return false;
                }
                mLPPolSchema.setAmnt(mLPPolSchema.getAmnt() + mChgAmnt);
                mLPPolSchema.setPrem(mLPPolSchema.getPrem() + mChgPrem);
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

//****************************end*****************************************//



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

        if (!tReCalBL.recalWithData(cLCPolBL, cLCDutyBLSet, cLCPremBLSet,
                                    cLCGetBLSet, mLPEdorItemSchema)) {
            CError tError = new CError();
            tError.moduleName = "PEdorPTDetailBL";
            tError.functionName = "dealData";
            tError.errorMessage = "录入的信息不符合要求!";
            this.mErrors.addOneError(tError);
            return false;
        }

// 职业健康加费 暂时不考虑
        tprem = 0.0;
        tamnt = 0.0;
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
            mLPPremSet.get(j).setOperator(aLCPremSchema.getOperator());
            mLPPremSet.get(j).setMakeDate(aLCPremSchema.getMakeDate());
            mLPPremSet.get(j).setMakeTime(aLCPremSchema.getMakeTime());
            mLPPremSet.get(j).setStandPrem(aLCPremSchema.getStandPrem());
            mLPPremSet.get(j).setPaytoDate(aLCPremSchema.getPaytoDate());
            prem = mLPPremSet.get(j).getPrem() - aLCPremSchema.getPrem();
            mLPPremSet.get(j).setSumPrem(aLCPremSchema.getSumPrem() +
                                         prem * tValue + prem * paycount);
        }

        mLPDutySet = tReCalBL.aftLPDutySet;
        for (int j = 1; j <= mLPDutySet.size(); j++) {
            mLPDutySet.get(j).setOperator(mGlobalInput.Operator);
            mLPDutySet.get(j).setPrem(mLPDutySet.get(j).getPrem());
            LCDutyDB aLCDutyDB = new LCDutyDB();
            LCDutySchema aLCDutySchema = new LCDutySchema();
            aLCDutyDB.setPolNo(mLPDutySet.get(j).getPolNo());
            aLCDutyDB.setDutyCode(mLPDutySet.get(j).getDutyCode());
            aLCDutySchema = aLCDutyDB.query().get(1);
            mLPDutySet.get(j).setCalRule(aLCDutySchema.getCalRule());
            mLPDutySet.get(j).setStandPrem(aLCDutySchema.getStandPrem());
            prem = mLPDutySet.get(j).getPrem() - aLCDutySchema.getPrem();
            tprem = tprem + prem;
            amnt = mLPDutySet.get(j).getAmnt() - aLCDutySchema.getAmnt();
            tamnt = tamnt + amnt;
            mLPDutySet.get(j).setSumPrem(aLCDutySchema.getSumPrem() +
                                         prem * tValue + prem * paycount);
            LJSGetEndorseSchema saveLJSGetEndorseSchema = new
                    LJSGetEndorseSchema();
            saveLJSGetEndorseSchema = this.initLJSGetEndorse("BF");
            saveLJSGetEndorseSchema.setGetMoney(prem * tValue + prem * paycount);
            saveLJSGetEndorseSchema.setDutyCode(aLCDutySchema.getDutyCode());
            LMDutyPayRelaDB tLMDutyPayRelaDB = new LMDutyPayRelaDB();
            tLMDutyPayRelaDB.setDutyCode(aLCDutySchema.getDutyCode());
            saveLJSGetEndorseSchema.setPayPlanCode(tLMDutyPayRelaDB.query().get(
                    1).
                    getPayPlanCode());
            saveLJSGetEndorseSchema.setSubFeeOperationType("BF");
            saveLJSGetEndorseSet.add(saveLJSGetEndorseSchema);
        }
        mLPPolSchema.setPrem(mLCPolSchema.getPrem() + tprem);
        mLPPolSchema.setStandPrem(aLCPolSchema.getStandPrem());
        mLPPolSchema.setSumPrem(aLCPolSchema.getSumPrem() + tprem * tValue +
                                tprem * paycount);
        mLPPolSchema.setAmnt(mLCPolSchema.getAmnt() + tamnt);

        mLPGetSet = tReCalBL.aftLPGetSet;
        for (int j = 1; j <= mLPGetSet.size(); j++) {
            mLPGetSet.get(j).setOperator(mGlobalInput.Operator);
            mLPGetSet.get(j).setMakeDate(tLCGetSet.get(1).getMakeDate());
            mLPGetSet.get(j).setMakeTime(tLCGetSet.get(1).getMakeTime());
        }
        //生成个人应收表
        LJSPayPersonSchema dLJSPayPersonSchema = new LJSPayPersonSchema();
        for (int j = 1; j <= saveLJSGetEndorseSet.size(); j++) {
            dLJSPayPersonSchema = new LJSPayPersonSchema();
            mRef.transFields(dLJSPayPersonSchema, saveLJSGetEndorseSet.get(j));
            dLJSPayPersonSchema.setPolNo(mLPPolSchema.getPolNo());
            dLJSPayPersonSchema.setPayCount(0);
           // dLJSPayPersonSchema.setEdorNo(mLPEdorItemSchema.getEdorNo()); //新增
            dLJSPayPersonSchema.setGrpContNo(mLPEdorItemSchema.getGrpContNo());
            dLJSPayPersonSchema.setGrpPolNo(mLPPolSchema.getGrpPolNo());
            dLJSPayPersonSchema.setContNo(mLPPolSchema.getContNo());
            dLJSPayPersonSchema.setManageCom(mGlobalInput.ManageCom);
            dLJSPayPersonSchema.setOperator(mGlobalInput.Operator);
            dLJSPayPersonSchema.setRiskCode(mLPPolSchema.getRiskCode());
            dLJSPayPersonSchema.setAppntNo(mLPPolSchema.getAppntNo());
            dLJSPayPersonSchema.setPayAimClass("1");
            dLJSPayPersonSchema.setDutyCode(saveLJSGetEndorseSet.get(j).
                                            getDutyCode());
            dLJSPayPersonSchema.setPayPlanCode(saveLJSGetEndorseSet.get(j).
                                               getPayPlanCode());
            dLJSPayPersonSchema.setSumDuePayMoney(saveLJSGetEndorseSet.get(j).
                                                  getGetMoney());
            dLJSPayPersonSchema.setPayIntv("0");
            dLJSPayPersonSchema.setPayDate(mLPEdorItemSchema.getEdorValiDate());
            dLJSPayPersonSchema.setPayType(mLPEdorItemSchema.getEdorType());
            dLJSPayPersonSchema.setMakeDate(PubFun.getCurrentDate());
            dLJSPayPersonSchema.setMakeTime(PubFun.getCurrentTime());
            dLJSPayPersonSchema.setModifyDate(PubFun.getCurrentDate());
            dLJSPayPersonSchema.setModifyTime(PubFun.getCurrentTime());
            mLJSPayPersonSet.add(dLJSPayPersonSchema);
        }
        mLPContSchema.setPrem(mLPContSchema.getPrem() + tprem);
        // mLPContSchema.setSumPrem(mLPContSchema.getSumPrem()+tprem);
        mLPContSchema.setAmnt(mLPContSchema.getAmnt() + tamnt);
        mLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
        mLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());

//        mCashValue = tprem * paycount;
        mGetMoney = tprem * tValue + tprem * paycount;
//    }

        logger.debug("=========mGetMoney==========" + mGetMoney);

        if (mGetMoney < 0) {
            mGetMoney = 0 - mGetMoney;
        }

        //长险计算利息
        double GetInterest = 0;
        if (mRiskType.equals("L")) {
            AccountManage tAccountManage = new AccountManage(String.valueOf(
                    mLCPolSchema.getPayEndYear()));
            
            //TODO
            if (!tAccountManage.calInterest(mLPEdorItemSchema.getEdorType(),
                                            mGetMoney,
                                            mLCPolSchema.getCValiDate(),
                                            mLPEdorItemSchema.getEdorValiDate(),
                                            mLCPolSchema.getRiskCode(),
                                            sIntvFlag)) {
                mErrors.addOneError("计算利息失败!");
                return false;
            }
            GetInterest = tAccountManage.getCalResult();
            logger.debug("利息利息利息利息利息========" + GetInterest);
            if (GetInterest > 0) {
                LJSGetEndorseSchema aLJSGetEndorseSchema = new
                        LJSGetEndorseSchema();
                mRef.transFields(aLJSGetEndorseSchema,
                                 saveLJSGetEndorseSet.get(1));
                aLJSGetEndorseSchema.setGetMoney(GetInterest);
                aLJSGetEndorseSchema.setDutyCode("0");
                aLJSGetEndorseSchema.setPayPlanCode("0");
                aLJSGetEndorseSchema.setSubFeeOperationType("LX");
                aLJSGetEndorseSchema.setFeeFinaType("LX");
                saveLJSGetEndorseSet.add(aLJSGetEndorseSchema);
            }

        }

//        mMap.put(mLPContSchema, "DELETE&INSERT");
        mMap.put(mLPPolSchema, "DELETE&INSERT");
        mMap.put(mLPDutySet, "DELETE&INSERT");
        mMap.put(mLPPremSet, "DELETE&INSERT");
        mMap.put(mLPGetSet, "DELETE&INSERT");
        mMap.put(mLJSPayPersonSet, "DELETE&INSERT");
        mMap.put(saveLJSGetEndorseSet, "DELETE&INSERT");
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
        mLJSGetEndorseSchema.setFeeFinaType("BF");
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
        mLJSGetEndorseSchema.setGetFlag("0");

        //mLJSGetEndorseSchema.setSerialNo(mLJSPaySchema.getSerialNo());
        //mLJSGetEndorseSchema.setGetMoney(mGetMoney);

        tLJSGetEndorseSchema.setSchema(mLJSGetEndorseSchema);

        return tLJSGetEndorseSchema;
    }



    //四舍六入五靠偶数，保留两位
    private double getRound(double tValue) {
        String t = "0.00";
        DecimalFormat tDF = new DecimalFormat(t);
        return Double.parseDouble(tDF.format(tValue));
    }

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
}
