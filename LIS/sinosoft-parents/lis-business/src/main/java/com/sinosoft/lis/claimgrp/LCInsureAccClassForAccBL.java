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
public class LCInsureAccClassForAccBL {
private static Logger logger = Logger.getLogger(LCInsureAccClassForAccBL.class);
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
    TransferData mReturnData1 = new TransferData(); //返回个人帐户金额
    /** 数据操作字符串 */
    private String mOperate;
    private String mCurrentDate = PubFun.getCurrentDate();
    private String mCurrentTime = PubFun.getCurrentTime();
    /** 业务处理相关变量 */
    private String mGrpContNo = "";
    private String mInsuredNo = "";
    private String mRiskCode = "";
    private String mAccFlag = "";
    private String mRptNo="";
    LCInsureAccClassSchema mLCInsureAccClassSchema1 = new
            LCInsureAccClassSchema(); //团体帐户
    LCInsureAccClassSchema mLCInsureAccClassSchema2 = new
            LCInsureAccClassSchema(); //个人帐户
    public LCInsureAccClassForAccBL() {
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
            tError.moduleName = "LCInsureAccClassForAccBL";
            tError.functionName = "submitData";
            tError.errorMessage =
                    "数据处理失败LCInsureAccClassForAccBL-->getInputData()!";
            this.mErrors.addOneError(tError);
            return false;
        }
        //进行业务处理
        if (!dealData()) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LCInsureAccClassForAccBL";
            tError.functionName = "submitData";
            tError.errorMessage =
                    "数据处理失败LCInsureAccClassForAccBL-->dealData()!";
            this.mErrors.addOneError(tError);
            return false;
        }
        //准备往后台的数据
        if (!prepareOutputData()) {
            CError tError = new CError();
            tError.moduleName = "LCInsureAccClassForAccBL";
            tError.functionName = "submitData";
            tError.errorMessage =
                    "数据处理失败LCInsureAccClassForAccBL-->prepareOutputData()!";
            this.mErrors.addOneError(tError);
            return false;
        }
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, cOperate)) {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LCInsureAccClassForAccBL";
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
        this.mGlobalInput.setSchema((GlobalInput) cInputData.
                                    getObjectByObjectName("GlobalInput", 0));
        mTransferData = (TransferData) cInputData.getObjectByObjectName(
                "TransferData", 0);
        mGrpContNo = (String) mTransferData.getValueByName("GrpContNo"); //团体保单号
        mInsuredNo = (String) mTransferData.getValueByName("InsuredNo"); //投保人号码
        mRiskCode = (String) mTransferData.getValueByName("RiskCode"); //投保人号码
        mAccFlag = (String) mTransferData.getValueByName("AccFlag"); //投保人号码
        mRptNo = (String)mTransferData.getValueByName("RptNo");
        return true;
    }

    /**
     * 数据操作类业务处理
     * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData() {
        String tSql = "";
        double tSumPay1 = 0.0; //团体帐户金额
        double tSumPay2 = 0.0; //个人帐户金额
        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = new SSRS();
        String tBalaDate = PubFun.getCurrentDate(); //当前截息日期（系统日期）
        String tRateType = "Y"; //原始利率类型（）
        String tIntvType = "D"; //目标利率类型（日利率）
        int tPerio = 0; //银行利率期间
        String tType = "F"; //截息计算类型（单利还是复利）
        String tDepst = "D"; //贷存款标志（贷款还是存款）
        String pLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
        LCInsureAccClassSchema tLCInsureAccClassSchema = new
                LCInsureAccClassSchema();
        LCInsureAccClassSchema aLCInsureAccClassSchema = new
                LCInsureAccClassSchema();
        LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
        LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
        AccountManage tAccountManage = new AccountManage();
        AccountManage tAccountManage1 = new AccountManage();
        LCInsureAccClassDB tLCInsureAccClassDB1 = new LCInsureAccClassDB();
        LCInsureAccClassSet tLCInsureAccClassSet1 = new LCInsureAccClassSet();
        String tPolNo = ""; //个人合同号
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setGrpContNo(mGrpContNo);
        tLCPolDB.setPolTypeFlag("2");
        tLCPolDB.setRiskCode(mRiskCode);
        LCPolSet tLCPolSet = tLCPolDB.query();
        if (tLCPolSet.size() > 0) {
            tPolNo = tLCPolSet.get(1).getPolNo();
        }
        //准备LCInsureAccClass表数据
        /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No.1 查询团体帐户，如果是当前天的记录，那么不用结息
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
//         tSql = "select lastaccbala from lcinsureaccclass where grpcontno = '"+mGrpContNo+"' and "
//                        +" baladate = '"+tBalaDate+"' and contno = '"+tContNo+"'";
//         ExeSQL tExeSQL = new ExeSQL();
//         SSRS tSSRS = tExeSQL.execSQL(tSql);
//         if (tSSRS.getMaxRow() < 1)
//         {

        /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No.2 团体帐户截息
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        //tLCInsureAccClassDB.setGrpContNo(mGrpContNo);
        tLCInsureAccClassDB.setPolNo(tPolNo);
        //tLCInsureAccClassDB.setRiskCode(mRiskCode);
        logger.debug("结息险种：" + mRiskCode);
        tLCInsureAccClassSet = tLCInsureAccClassDB.query();
        if (tLCInsureAccClassSet == null ||
            tLCInsureAccClassSet.size() == 0) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LCInsureAccClassForAccBL";
            tError.functionName = "dealData";
            tError.errorMessage = "查询LCInsureAccClass表信息失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        aLCInsureAccClassSchema = tLCInsureAccClassSet.get(1);
        //增加一个临时表，来记录上次团体帐户class表的记录
        String pSerialNo = PubFun1.CreateMaxNo("SERIALNO", pLimit);
        tSql = "insert into lcclass_temp values('" +
               aLCInsureAccClassSchema.getGrpContNo() + "','" +
               aLCInsureAccClassSchema.getGrpPolNo() + "','" +
               aLCInsureAccClassSchema.getPolNo() + "','" +
               aLCInsureAccClassSchema.getContNo() + "','" +
               aLCInsureAccClassSchema.getInsuredNo() + "','" +
               aLCInsureAccClassSchema.getAccType() + "','" +
               aLCInsureAccClassSchema.getRiskCode() + "','" +
               aLCInsureAccClassSchema.getInsuAccNo() + "','" +
               aLCInsureAccClassSchema.getPayPlanCode() + "','" +
               aLCInsureAccClassSchema.getBalaDate() + "','" +
               aLCInsureAccClassSchema.getBalaTime() + "'," +
               aLCInsureAccClassSchema.getLastAccBala() + ",'" +
               aLCInsureAccClassSchema.getOperator() + "','" +
               mCurrentTime + "','" + mCurrentDate + "','" +
               mCurrentDate + "','" + mCurrentTime + "','" + pSerialNo +
               "')";
        logger.debug("======tSql==p=====" + tSql);
        map.put(tSql, "INSERT");
        mReturnData = tAccountManage.getAccClassInterestNew(
                aLCInsureAccClassSchema,
                tBalaDate,
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
            tempmoney = String.valueOf(mReturnData.getValueByName(
                    "aAccClassInterest"));
            double tInterest = Double.parseDouble(tempmoney);
            logger.debug("===团体帐户结息金额==" + tSumPay1);
            logger.debug("===团体帐户结息利息==" + tInterest);
        } else {
            tSumPay1 = 0.0;
        }
        /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No.3 更新团体帐户LCInsureAccClass表信息
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        mLCInsureAccClassSchema1.setSchema(aLCInsureAccClassSchema);
        mLCInsureAccClassSchema1.setBalaDate(tBalaDate);
        mLCInsureAccClassSchema1.setBalaTime(PubFun.getCurrentTime());
        mLCInsureAccClassSchema1.setLastAccBala(tSumPay1); //团体帐户金额
        mLCInsureAccClassSchema1.setInsuAccBala(tSumPay1); //团体帐户金额
        mLCInsureAccClassSchema1.setModifyDate(PubFun.getCurrentDate());
        mLCInsureAccClassSchema1.setModifyTime(PubFun.getCurrentTime());
        map.put(mLCInsureAccClassSchema1, "DELETE&INSERT");

//         }
//         else
//         {
//             mReturnData.setNameAndValue("aAccClassSumPay",tSSRS.GetText(1,1));
//         }

        /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No.4 查询个人帐户，如果是当前天的记录，那么不用结息
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        //         tSql = "select lastaccbala from lcinsureaccclass where grpcontno = '"+mGrpContNo+"' and "
        //                        +"insuredno = '"+mInsuredNo+"'";
        //         SSRS tSSRS2 = tExeSQL.execSQL(tSql);
        //         if(tSSRS2.getMaxRow() < 1)
        //         {
        //             CError tError = new CError();
        //             tError.moduleName = "LCInsureAccClassForAccBL";
        //             tError.functionName = "dealData";
        //             tError.errorMessage = "在LCInsureAccClass表没有个人帐户信息!";
        //             this.mErrors.addOneError(tError);
        //             return true;
        //         }
        //
        //         tSql = "select lastaccbala from lcinsureaccclass where grpcontno = '"+mGrpContNo+"' and "
        //                        +"insuredno = '"+mInsuredNo+"' and baladate = '"+tBalaDate+"'";
        //         SSRS tSSRS1 = tExeSQL.execSQL(tSql);
        //         if (tSSRS1.getMaxRow() < 1)
        //         {
        /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No.5 个人帐户截息
         －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String accSql = "select polno from llclaimaccount where clmno='" + mRptNo +
                        "' and othertype='S' ";
        tSSRS = tExeSQL.execSQL(accSql);
        LCInsureAccClassSet ttLCInsureAccClassSet = new LCInsureAccClassSet();
        for (int k = 1; k <= tSSRS.getMaxRow(); k++) {
//            tLCInsureAccClassDB1.setGrpContNo(mGrpContNo);
//            tLCInsureAccClassDB1.setInsuredNo(mInsuredNo);
//            tLCInsureAccClassDB1.setRiskCode(mRiskCode);
            mLCInsureAccClassSchema2 = new LCInsureAccClassSchema();
            tLCInsureAccClassDB1.setPolNo(tSSRS.GetText(k,1));
            tLCInsureAccClassSet1 = tLCInsureAccClassDB1.query();
            if (tLCInsureAccClassSet1.size() == 0 || tLCInsureAccClassSet1 == null) {
                // @@错误处理
                logger.debug("没有查到该出险人 " + mInsuredNo + " 在账户表中的信息!");
                CError tError = new CError();
                tError.moduleName = "LCInsureAccClassForAccBL";
                tError.functionName = "dealData";
                //tError.errorMessage = "查询LCInsureAccClass表信息失败!";
                tError.errorMessage = "没有查到该出险人 " + mInsuredNo + " 在账户表中的信息!";
                this.mErrors.addOneError(tError);
                return false;

            }
            tLCInsureAccClassSchema = tLCInsureAccClassSet1.get(1);
            //增加一个临时表，来记录上次个人帐户class表的记录
            String pSerialNo1 = PubFun1.CreateMaxNo("SERIALNO", pLimit);
            tSql = "insert into lcclass_temp values('" +
                   tLCInsureAccClassSchema.getGrpContNo() + "','" +
                   tLCInsureAccClassSchema.getGrpPolNo() + "','" +
                   tLCInsureAccClassSchema.getPolNo() + "','" +
                   tLCInsureAccClassSchema.getContNo() + "','" +
                   tLCInsureAccClassSchema.getInsuredNo() + "','" +
                   tLCInsureAccClassSchema.getAccType() + "','" +
                   tLCInsureAccClassSchema.getRiskCode() + "','" +
                   tLCInsureAccClassSchema.getInsuAccNo() + "','" +
                   tLCInsureAccClassSchema.getPayPlanCode() + "','" +
                   tLCInsureAccClassSchema.getBalaDate() + "','" +
                   tLCInsureAccClassSchema.getBalaTime() + "'," +
                   tLCInsureAccClassSchema.getLastAccBala() + ",'" +
                   tLCInsureAccClassSchema.getOperator() + "','" +
                   mCurrentTime + "','" + mCurrentDate + "','" +
                   mCurrentDate +
                   "','" + mCurrentTime + "','" + pSerialNo1 + "')";
            logger.debug("======tSql==p=====" + tSql);
            map.put(tSql, "INSERT");

            mReturnData1 = tAccountManage1.getAccClassInterestNew(
                    tLCInsureAccClassSchema,
                    tBalaDate,
                    tRateType,
                    tIntvType,
                    tPerio,
                    tType,
                    tDepst);
            if (!mReturnData1.equals("") || mReturnData1 != null) {
                String tempmoney = String.valueOf(mReturnData1.getValueByName(
                        "aAccClassSumPay"));

                tSumPay2 = Double.parseDouble(tempmoney);
                tSumPay2 = Arith.round(tSumPay2, 2);
                if (tSumPay2 < 0) {
                    tSumPay2 = 0.00;
                }
                tempmoney = String.valueOf(mReturnData1.getValueByName(
                        "aAccClassInterest"));
                double tInterest = Double.parseDouble(tempmoney);
                logger.debug("===个人帐户结息金额==" + tSumPay2);
                logger.debug("===个人帐户结息利息==" + tInterest);
            } else {
                tSumPay2 = 0.0;
            }
            /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No.6 更新个人帐户LCInsureAccClass表信息
             －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */

            mLCInsureAccClassSchema2.setSchema(tLCInsureAccClassSchema);
            mLCInsureAccClassSchema2.setBalaDate(tBalaDate);
            mLCInsureAccClassSchema2.setBalaTime(PubFun.getCurrentTime());
            mLCInsureAccClassSchema2.setLastAccBala(tSumPay2); //个人帐户金额
            mLCInsureAccClassSchema2.setInsuAccBala(tSumPay2); //个人帐户金额
            mLCInsureAccClassSchema2.setModifyDate(PubFun.getCurrentDate());
            mLCInsureAccClassSchema2.setModifyTime(PubFun.getCurrentTime());
            //map.put(mLCInsureAccClassSchema2, "DELETE&INSERT");
            ttLCInsureAccClassSet.add(mLCInsureAccClassSchema2);

            //         }
            //         else
            //         {
            //             mReturnData1.setNameAndValue("aAccClassSumPay",tSSRS1.GetText(1,1));
            //         }
        }
        map.put(ttLCInsureAccClassSet, "DELETE&INSERT");

        //---------jinsh20070522点结息时将llclaimaccount中以赔案号将kindcode置为1作为结息的标志--//
             String AccSql="update llclaimaccount set kindcode='1' where clmno='"+mRptNo+"'";
             map.put(AccSql,"UPDATE");
        //---------jinsh20070522点结息时将llclaimaccount中以赔案号将kindcode置为1作为结息的标志--//

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
        mResult.add(mReturnData1);
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
        String ttGrpContNo = "230101069217";
        String ttInsuredNo = "0210029233";
        tData.setNameAndValue("GrpContNo", ttGrpContNo);
        tData.setNameAndValue("InsuredNo", ttInsuredNo);
        mData.add(mGlobal);
        mData.add(tData);
        LCInsureAccClassForAccBL lcinsureaccclassforaccbl = new
                LCInsureAccClassForAccBL();

        if (!lcinsureaccclassforaccbl.submitData(mData, "")) {
            logger.debug("＝＝＝＝＝＝结息计算不成功＝＝＝＝＝＝");
        } else {
            VData tdd = lcinsureaccclassforaccbl.getResult();
            String tt = String.valueOf(((TransferData) tdd.
                                        getObjectByObjectName("TransferData", 0)).
                                       getValueByName("aAccClassSumPay"));
            String tt1 = String.valueOf(((TransferData) tdd.
                                         getObjectByObjectName("TransferData",
                    1)).getValueByName("aAccClassSumPay"));
            logger.debug("====tt==" + tt);
            logger.debug("====tt1==" + tt1);
        }
        logger.debug("＝＝＝＝＝＝over＝＝＝＝＝＝");
    }
}
