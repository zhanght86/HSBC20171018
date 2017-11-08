package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.db.*;

/**
 * <p>Title:LCInsureAccClassForAccBL.java </p>
 * <p>Description:用于帐户案件理赔结息，并修改 LCInsureAccClass记录</p>
 *<p>Company: SinoSoft Co. Ltd,</p>
 * @author 万泽辉
 * @version 1.0 2006-02-10
 */
public class AccMedBuyBL {
private static Logger logger = Logger.getLogger(AccMedBuyBL.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    private MMap map = new MMap();
    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    TransferData mTransferData = new TransferData(); //传入页面信息
    TransferData mReturnData = new TransferData(); //返回团体帐户金额

    /** 数据操作字符串 */
    private String mOperate;
    private String mCurrentDate = PubFun.getCurrentDate();
    private String mCurrentTime = PubFun.getCurrentTime();
    /** 业务处理相关变量 */
    private String mGrpContNo = "";
    private String mContNo = "";
    private String mBalaDate = "";

    LCInsureAccClassSchema mLCInsureAccClassSchema1 = new
            LCInsureAccClassSchema(); //团体帐户
    LCInsureAccClassSchema mLCInsureAccClassSchema2 = new
            LCInsureAccClassSchema(); //个人帐户
    public AccMedBuyBL() {
    }

    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData, String cOperate) {
        this.mOperate = cOperate;
        logger.debug("＝＝＝＝＝＝＝＝结息处理开始＝＝＝＝＝＝");
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData)) {
            CError tError = new CError();
            tError.moduleName = "AccMedBuyBL";
            tError.functionName = "submitData";
            tError.errorMessage =
                    "数据处理失败AccMedBuyBL-->getInputData()!";
            this.mErrors.addOneError(tError);
            return false;
        }
        //进行业务处理
        if (!dealData()) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "AccMedBuyBL";
            tError.functionName = "submitData";
            tError.errorMessage =
                    "数据处理失败AccMedBuyBL-->dealData()!";
            this.mErrors.addOneError(tError);
            return false;
        }
        //准备往后台的数据
        if (!prepareOutputData()) {
            CError tError = new CError();
            tError.moduleName = "AccMedBuyBL";
            tError.functionName = "submitData";
            tError.errorMessage =
                    "数据处理失败AccMedBuyBL-->prepareOutputData()!";
            this.mErrors.addOneError(tError);
            return false;
        }
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, cOperate)) {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "AccMedBuyBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        logger.debug("＝＝＝＝＝＝＝＝结息处理结束＝＝＝＝＝＝");
        mInputData = null;
        return true;
    }

    /**
     * 从输入数据中得到所有对象
     * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData) {
//        this.mGlobalInput.setSchema((GlobalInput) cInputData.
//                                    getObjectByObjectName("GlobalInput", 0));
        mTransferData = (TransferData) cInputData.getObjectByObjectName(
                "TransferData", 0);
        mGrpContNo = (String) mTransferData.getValueByName("GrpContNo"); //团体保单号
        mContNo = (String) mTransferData.getValueByName("ContNo"); //投保人号码
        mBalaDate = (String) mTransferData.getValueByName("AccDate"); //结息日期

        if (mGrpContNo==null||mGrpContNo.equals("")||mContNo==null||mContNo.equals("")||mBalaDate==null||mBalaDate.equals(""))
        {
            CError tError = new CError();
            tError.moduleName = "AccMedBuyBL";
            tError.functionName = "dealData";
            tError.errorMessage = "结息条件不满足!";
            this.mErrors.addOneError(tError);
            return false;
        }


        return true;
    }

    /**
     * 数据操作类业务处理
     * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData() {

        double tSumPay1 = 0.0; //团体帐户金额

        //String tBalaDate = PubFun.getCurrentDate(); //当前截息日期（系统日期）
        String tRateType = "Y"; //原始利率类型（）
        String tIntvType = "D"; //目标利率类型（日利率）
        int tPerio = 0; //银行利率期间
        String tType = "F"; //截息计算类型（单利还是复利）
        String tDepst = "D"; //贷存款标志（贷款还是存款）

        LCInsureAccClassSchema aLCInsureAccClassSchema = new
                LCInsureAccClassSchema();
        LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
        LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
        AccountManage tAccountManage = new AccountManage();

        String tPolNo = ""; //个人合同号
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setGrpContNo(mGrpContNo);
        tLCPolDB.setContNo(mContNo);
        LCPolSet tLCPolSet = tLCPolDB.query();
        if (tLCPolSet.size() > 0) {
            tPolNo = tLCPolSet.get(1).getPolNo();
        }
        //准备LCInsureAccClass表数据
        tLCInsureAccClassDB.setPolNo(tPolNo);
        tLCInsureAccClassSet = tLCInsureAccClassDB.query();
        if (tLCInsureAccClassSet == null ||
            tLCInsureAccClassSet.size() == 0) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "AccMedBuyBL";
            tError.functionName = "dealData";
            tError.errorMessage = "查询LCInsureAccClass表信息失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        aLCInsureAccClassSchema = tLCInsureAccClassSet.get(1);

        mReturnData = tAccountManage.getAccMedBuyBala(
                aLCInsureAccClassSchema,
                mBalaDate,
                tRateType,
                tIntvType,
                tPerio,
                tType,
                tDepst);
        if (mReturnData != null) {
            String tempmoney = String.valueOf(mReturnData.getValueByName(
                    "aAccClassSumPay"));
            tSumPay1 = Double.parseDouble(tempmoney);
            tSumPay1 = Arith.round(tSumPay1, 2);
            if (tSumPay1 < 0) {
                tSumPay1 = 0.00;
            }
        } else {
            tSumPay1 = 0.0;
        }

        return true;
    }

    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData() {
        mInputData.clear();
        mInputData.add(map);
        mResult.add(mReturnData);

        return true;
    }

    /**
     * 返回需要的结果
     * @return boolean
     */
    public VData getResult() {
        return mResult;
    }

    public MMap getMMap() {
        return this.map;
    }

    public static void main(String[] args) {

        TransferData tData = new TransferData();
        VData mData = new VData();
        GlobalInput mGlobal = new GlobalInput();
        mGlobal.ManageCom = "0101";
        mGlobal.Operator = "tk";
        String tGrpContNo = "280101001049";
        String tContNo = "2001011160867";
        String tAccDate = "2008-05-13";
        tData.setNameAndValue("GrpContNo", tGrpContNo);
        tData.setNameAndValue("ContNo", tContNo);
        tData.setNameAndValue("AccDate", tAccDate);

        mData.add(mGlobal);
        mData.add(tData);
        AccMedBuyBL tAccMedBuyBL = new AccMedBuyBL();

        if (!tAccMedBuyBL.submitData(mData, "")) {
            logger.debug("＝＝＝＝＝＝结息计算不成功＝＝＝＝＝＝");
        } else {
            VData tdd = tAccMedBuyBL.getResult();
            String tt = String.valueOf(((TransferData) tdd.
                                        getObjectByObjectName("TransferData", 0)).
                                       getValueByName("aAccClassSumPay"));

            logger.debug("===帐户余额===" + tt);

        }
        logger.debug("＝＝＝＝＝＝over＝＝＝＝＝＝");
    }
}
