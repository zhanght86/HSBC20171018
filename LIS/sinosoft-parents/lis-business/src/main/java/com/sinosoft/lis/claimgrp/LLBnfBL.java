/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.claimgrp.*;
import com.sinosoft.lis.vschema.LLCaseSet;
import com.sinosoft.lis.vschema.LLBnfSet;
import com.sinosoft.lis.db.LLBalanceDB;
import com.sinosoft.lis.vschema.LLBalanceSet;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.lis.vschema.LJSGetClaimSet;
import com.sinosoft.lis.db.LDGrpDB;
import com.sinosoft.lis.db.LLCaseDB;
import com.sinosoft.lis.db.LDPersonDB;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 理赔立案结论逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author zl
 * @version 1.0
 */
public class LLBnfBL
{
private static Logger logger = Logger.getLogger(LLBnfBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    //全局变量
    private MMap map = new MMap();
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private GlobalInput mG = new GlobalInput();
    TransferData mTransferData = new TransferData();

    private LLBnfSet mLLBnfSet = new LLBnfSet();
    private LJSGetSet mLJSGetSet = new LJSGetSet();
    private LJSGetClaimSet mLJSGetClaimSet = new LJSGetClaimSet();

    private String mPolNo = "";
    private String mClmNo = "";
    private String mBnfKind = "";

    public LLBnfBL()
    {
    }

    public static void main(String[] args)
    {
    }

    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug(
                "----------LLBnfBL begin submitData----------");
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData())
        {
            return false;
        }
        logger.debug(
                "----------LLBnfBL after getInputData----------");

        //检查数据合法性
        if (!checkInputData())
        {
            return false;
        }
        logger.debug(
                "----------LLBnfBL after checkInputData----------");
        //进行业务处理
        if (!dealData(cOperate))
        {
            return false;
        }
        logger.debug(
                "----------LLBnfBL after dealData----------");
        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }
        logger.debug(
                "----------LLBnfBL after prepareOutputData----------");
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, cOperate))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLBnfBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mInputData = null;
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

    /**
     * 从输入数据中得到所有对象
     * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData()
    {
        logger.debug("---LLBnfBL start getInputData()...");
        //获取页面报案信息
        mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); //按类取值
        mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData",0);
        mLLBnfSet = (LLBnfSet) mInputData.
                               getObjectByObjectName("LLBnfSet", 0);
        mClmNo = (String) mTransferData.getValueByName("ClmNo");
        mPolNo = (String) mTransferData.getValueByName("PolNo");
        mBnfKind = (String) mTransferData.getValueByName("BnfKind");

        return true;
    }

    /**
     * 校验传入的报案信息是否合法
     * @return：如果发生错误则返回false,否则返回true
     */
    private boolean checkInputData()
    {
        return true;
    }

    /**
     * 数据操作类业务处理
     * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData(String cOperate)
     {
         String tpay = "";
         //更新立案结论
         if (cOperate.equals("INSERT")) {
             //删除受益人分配表
             String deleteSql1 = " delete from LLBnf where "
                                 + " ClmNo = '" + mClmNo + "'"
                                 //+ " and PolNo = '" + mPolNo + "'"
                                 + " and BnfKind = '" + mBnfKind + "'";
             //map.put(deleteSql1, "DELETE");

             //取到给付的总金额
             String strSql = " select sum(pay) from LLBalance where"
                             + " ClmNo = '" + mClmNo + "'";
             ExeSQL tExeSQL = new ExeSQL();
             SSRS tSSRS = tExeSQL.execSQL(strSql);
             if (tExeSQL.mErrors.needDealError()) {
                 this.mErrors.copyAllErrors(tExeSQL.mErrors);
                 CError tError = new CError();
                 tError.moduleName = "LLBnfBL";
                 tError.functionName = "dealData";
                 tError.errorMessage = "未查询到LLBalance表的信息!";
                 logger.debug(
                         "------------------------------------------------------");
                 logger.debug("--LLClaimCalAutoBL.dealData()--理赔计算时发生错误!" +
                                    strSql);
                 logger.debug(
                         "------------------------------------------------------");
                 this.mErrors.addOneError(tError);
                 return false;
             }
             if (tSSRS.getMaxRow() > 0) {
                 tpay = tSSRS.GetText(1, 1);
             }

             //取到立案信息
             LLRegisterDB tLLRegisterDB = new LLRegisterDB();
             LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
             tLLRegisterDB.setRgtNo(mClmNo);
             if (tLLRegisterDB.getInfo()) {
                 tLLRegisterSchema = tLLRegisterDB.getSchema();
             }else{
                 //@@错误处理
                 CError tError = new CError();
                 tError.moduleName = "LLBnfBL";
                 tError.functionName = "dealData";
                 tError.errorMessage = "查询理赔立案信息失败!";
                 this.mErrors.addOneError(tError);
                 return false;
             }

             LDGrpSchema tLDGrpSchema = new LDGrpSchema();
             LJSGetSchema tLJSGetSchema = new LJSGetSchema();
             if(tLLRegisterSchema.getRgtClass().equals("1")){//团转个

                 LLCaseDB mLLCaseDB = new LLCaseDB();
                 mLLCaseDB.setCaseNo(mClmNo);
                 LLCaseSet tLLCaseSet = mLLCaseDB.query();
                 if (tLLCaseSet.size() != 1) {
                     CError tError = new CError();
                     tError.moduleName = "LLClaimConfirmAfterInitService";
                     tError.functionName = "getLLBnf";
                     tError.errorMessage = "该赔案出险人信息有误!";
                     this.mErrors.addOneError(tError);
                     return false;
                 }

                 LDPersonSchema mLDPersonSchema = new LDPersonSchema();
                 LDPersonDB mLDPersonDB = new LDPersonDB();
                 String tCNo = tLLCaseSet.get(1).getCustomerNo();
                 mLDPersonDB.setCustomerNo(tCNo);
                 if (mLDPersonDB.getInfo()) {
                     mLDPersonSchema = mLDPersonDB.getSchema();
                 }
                 tLJSGetSchema.setDrawer(mLDPersonSchema.getName());
                 tLJSGetSchema.setDrawerID(mLDPersonSchema.getIDNo());//领取人身份证号
//                 tLJSGetSchema.setBankCode("");
//                 tLJSGetSchema.setBankAccNo("");
//                 tLJSGetSchema.setAccName("");
             }else{
                 //查询团体信息
                 LDGrpDB tLDGrpDB = new LDGrpDB();
                 if (tLLRegisterSchema.getAppntNo().equals("000000")) {
                     tLDGrpSchema.setGrpName("无名单");
                     tLDGrpSchema.setGrpGroupNo("000000");
                     tLDGrpSchema.setBankCode("000000");
                     tLDGrpSchema.setBankAccNo("000000");
                 } else {
                     tLDGrpDB.setCustomerNo(tLLRegisterSchema.getAppntNo());
                     if (tLDGrpDB.getInfo()) {
                         tLDGrpSchema = tLDGrpDB.getSchema();
                     } else {
                         //@@错误处理
                         CError tError = new CError();
                         tError.moduleName = "LLBnfBL";
                         tError.functionName = "dealData";
                         tError.errorMessage = "查询团体信息失败!";
                         this.mErrors.addOneError(tError);
                         return false;
                     }
                 }
                 tLJSGetSchema.setDrawer(tLDGrpSchema.getGrpName());
                 tLJSGetSchema.setDrawerID(tLDGrpSchema.getGrpGroupNo());//领取人身份证号
                 tLJSGetSchema.setBankCode(tLDGrpSchema.getBankCode());
                 tLJSGetSchema.setBankAccNo(tLDGrpSchema.getBankAccNo());
                 tLJSGetSchema.setAccName(tLDGrpSchema.getGrpName());
             }
                 /**-------------------------------------------------------------BEG
                  * No.1 生成财务数据
                  * 1) 应付总表LJSGet对应受益人表LLBnf
                  * 2) 赔付应付表LJSGetClaim对应赔付应付表LLBalance
                  * 3) 应付总表LJSGet与赔付应付表LJSGetClaim通过给付通知书号码GetNoticeNo
                  *    关联
                  * 另：管理机构应取自承包保单的管理结构
                  *--------------------------------------------------------------*/
                 //取得承保日期
                 String tManageCom = getPolManageCom(mPolNo);
                 if (tManageCom.equals("false"))
                 {
                     return false;
                 }
                 //生成给付通知书号码
                 //String tOtherNO2 = PubFun1.CreateMaxNo("GETNOTICENO", 10);
                 //退费直接生成收据　jixf
                 String tOtherNO2 = PubFun1.CreateMaxNo("GETNO",tManageCom);
                 //添加应付总表
                 tLJSGetSchema.setGetNoticeNo(tOtherNO2);
                 tLJSGetSchema.setOtherNo(mClmNo);
                 tLJSGetSchema.setOtherNoType("5");
                 tLJSGetSchema.setManageCom(tManageCom);
                 tLJSGetSchema.setSumGetMoney(tpay);
                 tLJSGetSchema.setOperator(mG.Operator);
                 tLJSGetSchema.setMakeDate(CurrentDate);
                 tLJSGetSchema.setMakeTime(CurrentTime);
                 tLJSGetSchema.setModifyDate(CurrentDate);
                 tLJSGetSchema.setModifyTime(CurrentTime);
                 //mLJSGetSet.add(tLJSGetSchema);
                 //更改受益人表，生成财务数据
                 for (int i = 1; i <= mLLBnfSet.size(); i++) {
                     LJSGetClaimSchema tLJSGetClaimSchema = new LJSGetClaimSchema();
                     LJSGetClaimSet tLJSGetClaimSet = new LJSGetClaimSet();
                     //删除应付总表
                     String deleteSql2 = " delete from LJSGet where "
                                         + " OtherNo = '" +
                                         mClmNo +
                                         "' and OtherNoType = '5'";
                     map.put(deleteSql2, "DELETE");
                     //删除赔付应付表
                     String deleteSql3 = " delete from LJSGetClaim where "
                                         + " OtherNo = '" +
                                         mClmNo +
                                         "' and OtherNoType = '5'";
                     map.put(deleteSql3, "DELETE");
                     //生成给付通知书号码
                     //String tOtherNO = PubFun1.CreateMaxNo("GETNOTICENO", 10);

                     //mLLBnfSet.get(i).setOtherNo(tOtherNO);
                     mLLBnfSet.get(i).setOtherNo(tOtherNO2);
                     mLLBnfSet.get(i).setOtherNoType("5");
                     mLLBnfSet.get(i).setOperator(mG.Operator);
                     mLLBnfSet.get(i).setMakeDate(CurrentDate);
                     mLLBnfSet.get(i).setMakeTime(CurrentTime);
                     mLLBnfSet.get(i).setModifyDate(CurrentDate);
                     mLLBnfSet.get(i).setModifyTime(CurrentTime);

                 //添加赔付应付表
                 LLBalanceDB tLLBalanceDB = new LLBalanceDB();
                 tLLBalanceDB.setClmNo(mLLBnfSet.get(i).getClmNo());
                 tLLBalanceDB.setPolNo(mLLBnfSet.get(i).getPolNo());
                 if (mBnfKind.equals("B")) //如果为预付则只处理预付
                 {
                     tLLBalanceDB.setFeeOperationType(mBnfKind);
                 }
                 LLBalanceSet tLLBalanceSet = new LLBalanceSet();
                 tLLBalanceSet = tLLBalanceDB.query();
                 if (tLLBalanceSet == null && tLLBalanceSet.size() == 0)
                 {
                     //@@错误处理
                     CError tError = new CError();
                     tError.moduleName = "LLBnfBL";
                     tError.functionName = "dealData";
                     tError.errorMessage = "查询理赔结算信息失败!";
                     this.mErrors.addOneError(tError);
                     return false;
                 }
                 else
                 {
                     for (int j = 1; j <= tLLBalanceSet.size(); j++)
                     {
                         /**-----------------------------------------------------
                          * 计算受益人赔付金额
                          * 注：是预付存正值,理算金额存原值
                          *----------------------------------------------------*/
                         Double tDouble1 = new Double(tLLBalanceSet.get(j).getPay());
                         double tPay = tDouble1.doubleValue();
                         Double tDouble2 = new Double(mLLBnfSet.get(i).getBnfLot());
                         double tBnfLot = tDouble2.doubleValue();
                         tPay = tBnfLot * tPay * 0.01;
                         if (mBnfKind.equals("B"))
                         {
                             tPay = Math.abs(tPay); //预付时为负，要取正值
                         }

                         tLJSGetClaimSchema = new LJSGetClaimSchema();
                         tLJSGetClaimSchema.setGetNoticeNo(mLLBnfSet.get(i).
                                 getOtherNo());
                         tLJSGetClaimSchema.setFeeOperationType(tLLBalanceSet.get(j).getFeeOperationType());
                         tLJSGetClaimSchema.setSubFeeOperationType(tLLBalanceSet.get(j).getSubFeeOperationType());
                         tLJSGetClaimSchema.setFeeFinaType(tLLBalanceSet.get(j).getFeeFinaType());
                         tLJSGetClaimSchema.setOtherNo(mClmNo);
                         tLJSGetClaimSchema.setOtherNoType("5");
                         tLJSGetClaimSchema.setPolNo(tLLBalanceSet.get(j).getPolNo());
                         tLJSGetClaimSchema.setGrpContNo(tLLBalanceSet.get(j).getGrpContNo());
                         tLJSGetClaimSchema.setGrpPolNo(tLLBalanceSet.get(j).getGrpPolNo());
                         tLJSGetClaimSchema.setContNo(tLLBalanceSet.get(j).getContNo());
                         tLJSGetClaimSchema.setPay(tPay);
                         tLJSGetClaimSchema.setAgentCode(tLLBalanceSet.get(j).getAgentCode());
                         tLJSGetClaimSchema.setAgentGroup(tLLBalanceSet.get(j).getAgentGroup());
                         tLJSGetClaimSchema.setAgentType(tLLBalanceSet.get(j).getAgentType());
                         tLJSGetClaimSchema.setAgentCom(tLLBalanceSet.get(j).getAgentCom());
                         tLJSGetClaimSchema.setDutyCode(tLLBalanceSet.get(j).getDutyCode());
                         tLJSGetClaimSchema.setGetDutyCode(tLLBalanceSet.get(j).getGetDutyCode());
                         tLJSGetClaimSchema.setGetDutyKind(tLLBalanceSet.get(j).getGetDutyKind());
                         tLJSGetClaimSchema.setRiskCode(tLLBalanceSet.get(j).getRiskCode());
                         tLJSGetClaimSchema.setRiskVersion(tLLBalanceSet.get(j).getRiskVersion());
                         tLJSGetClaimSchema.setKindCode(tLLBalanceSet.get(j).getKindCode());
                         tLJSGetClaimSchema.setOperator(mG.Operator);
                         tLJSGetClaimSchema.setMakeDate(CurrentDate);
                         tLJSGetClaimSchema.setMakeTime(CurrentTime);
                         tLJSGetClaimSchema.setModifyDate(CurrentDate);
                         tLJSGetClaimSchema.setModifyTime(CurrentTime);
                         //取得承保日期
                         String tMCom = getPolManageCom(tLLBalanceSet.get(j).getPolNo());
                         if (tMCom.equals("false"))
                         {
                             return false;
                         }
                         tLJSGetClaimSchema.setManageCom(tMCom);
                         //待添加其他字段信息----------------------------------------
                         mLJSGetClaimSet.add(tLJSGetClaimSchema);
                     }
                 }
                // mLJSGetClaimSet.add(tLJSGetClaimSet);
             }
             //map.put(mLLBnfSet, "INSERT");
             map.put(tLJSGetSchema, "INSERT");
             map.put(mLJSGetClaimSet, "INSERT");
             //------------------------------------------------------------------END
         }
         return true;
    }
//团体保单 只需要一条总的 LJSGet 给付记录 所以把原先的方法注释掉 2005-12-30 P.D
//    private boolean dealData(String cOperate)
//    {
//        //更新立案结论
//        if (cOperate.equals("INSERT"))
//        {
//            //删除受益人分配表
//            String deleteSql1 = " delete from LLBnf where "
//                              + " ClmNo = '" + mClmNo + "'"
//                              + " and PolNo = '" + mPolNo + "'"
//                              + " and BnfKind = '" + mBnfKind + "'";
//            map.put(deleteSql1, "DELETE");
//
//            //更改受益人表，生成财务数据
//            for (int i = 1; i <= mLLBnfSet.size(); i++)
//            {
//                LJSGetSchema tLJSGetSchema = new LJSGetSchema();
//                LJSGetClaimSchema tLJSGetClaimSchema = new LJSGetClaimSchema();
//                LJSGetClaimSet tLJSGetClaimSet = new LJSGetClaimSet();
//                //删除应付总表
//                String deleteSql2 = " delete from LJSGet where "
//                                    + " GetNoticeNo = '" + mLLBnfSet.get(i).getOtherNo() +
//                                    "'";
//                map.put(deleteSql2, "DELETE");
//                //删除赔付应付表
//                String deleteSql3 = " delete from LJSGetClaim where "
//                                    + " GetNoticeNo = '" + mLLBnfSet.get(i).getOtherNo() +
//                                    "'"
//                                    + " and PolNo = '" + mPolNo + "'";
//                map.put(deleteSql3, "DELETE");
//                //生成给付通知书号码
//                String tOtherNO = PubFun1.CreateMaxNo("GETNOTICENO", 10);
//
//                mLLBnfSet.get(i).setOtherNo(tOtherNO);
//                mLLBnfSet.get(i).setOtherNoType("5");
//                mLLBnfSet.get(i).setOperator(mG.Operator);
//                mLLBnfSet.get(i).setMakeDate(CurrentDate);
//                mLLBnfSet.get(i).setMakeTime(CurrentTime);
//                mLLBnfSet.get(i).setModifyDate(CurrentDate);
//                mLLBnfSet.get(i).setModifyTime(CurrentTime);
//
//                /**-------------------------------------------------------------BEG
//                 * No.1 生成财务数据
//                 * 1) 应付总表LJSGet对应受益人表LLBnf
//                 * 2) 赔付应付表LJSGetClaim对应赔付应付表LLBalance
//                 * 3) 应付总表LJSGet与赔付应付表LJSGetClaim通过给付通知书号码GetNoticeNo
//                 *    关联
//                 * 另：管理机构应取自承包保单的管理结构
//                 *--------------------------------------------------------------*/
//                //取得承保日期
//                String tManageCom = getPolManageCom(mPolNo);
//                if (tManageCom.equals("false"))
//                {
//                    return false;
//                }
//                //添加应付总表
//                tLJSGetSchema.setGetNoticeNo(mLLBnfSet.get(i).getOtherNo());
//                tLJSGetSchema.setOtherNo(mClmNo);
//                tLJSGetSchema.setOtherNoType("5");
////                tLJSGetSchema.setPayMode();
//                tLJSGetSchema.setManageCom(tManageCom);
//                tLJSGetSchema.setSumGetMoney(mLLBnfSet.get(i).getGetMoney());
//                tLJSGetSchema.setDrawer(mLLBnfSet.get(i).getPayeeName());
////                tLJSGetSchema.setDrawerID(mLLBnfSet.get(i).getPayeeNo());
//                tLJSGetSchema.setDrawerID(mLLBnfSet.get(i).getPayeeIDNo());//领取人身份证号
//                tLJSGetSchema.setBankCode(mLLBnfSet.get(i).getBankCode());
//                tLJSGetSchema.setBankAccNo(mLLBnfSet.get(i).getBankAccNo());
//                tLJSGetSchema.setAccName(mLLBnfSet.get(i).getAccName());
//                tLJSGetSchema.setOperator(mG.Operator);
//                tLJSGetSchema.setMakeDate(CurrentDate);
//                tLJSGetSchema.setMakeTime(CurrentTime);
//                tLJSGetSchema.setModifyDate(CurrentDate);
//                tLJSGetSchema.setModifyTime(CurrentTime);
//                mLJSGetSet.add(tLJSGetSchema);
//                //添加赔付应付表
//                LLBalanceDB tLLBalanceDB = new LLBalanceDB();
//                tLLBalanceDB.setClmNo(mLLBnfSet.get(i).getClmNo());
//                tLLBalanceDB.setPolNo(mLLBnfSet.get(i).getPolNo());
//                if (mBnfKind.equals("B")) //如果为预付则只处理预付
//                {
//                    tLLBalanceDB.setFeeOperationType(mBnfKind);
//                }
//                LLBalanceSet tLLBalanceSet = new LLBalanceSet();
//                tLLBalanceSet = tLLBalanceDB.query();
//                if (tLLBalanceSet == null && tLLBalanceSet.size() == 0)
//                {
//                    //@@错误处理
//                    CError tError = new CError();
//                    tError.moduleName = "LLBnfBL";
//                    tError.functionName = "dealData";
//                    tError.errorMessage = "查询理赔结算信息失败!";
//                    this.mErrors.addOneError(tError);
//                    return false;
//                }
//                else
//                {
//                    for (int j = 1; j <= tLLBalanceSet.size(); j++)
//                    {
//                        /**-----------------------------------------------------
//                         * 计算受益人赔付金额
//                         * 注：是预付存正值,理算金额存原值
//                         *----------------------------------------------------*/
//                        Double tDouble1 = new Double(tLLBalanceSet.get(j).getPay());
//                        double tPay = tDouble1.doubleValue();
//                        Double tDouble2 = new Double(mLLBnfSet.get(i).getBnfLot());
//                        double tBnfLot = tDouble2.doubleValue();
//                        tPay = tBnfLot * tPay * 0.01;
//                        if (mBnfKind.equals("B"))
//                        {
//                            tPay = Math.abs(tPay); //预付时为负，要取正值
//                        }
//
//                        tLJSGetClaimSchema = new LJSGetClaimSchema();
//                        tLJSGetClaimSchema.setGetNoticeNo(mLLBnfSet.get(i).
//                                getOtherNo());
//                        tLJSGetClaimSchema.setFeeOperationType(tLLBalanceSet.get(j).getFeeOperationType());
//                        tLJSGetClaimSchema.setSubFeeOperationType(tLLBalanceSet.get(j).getSubFeeOperationType());
//                        tLJSGetClaimSchema.setFeeFinaType(tLLBalanceSet.get(j).getFeeFinaType());
//                        tLJSGetClaimSchema.setOtherNo(mClmNo);
//                        tLJSGetClaimSchema.setOtherNoType("5");
//                        tLJSGetClaimSchema.setPolNo(tLLBalanceSet.get(j).getPolNo());
//                        tLJSGetClaimSchema.setGrpContNo(tLLBalanceSet.get(j).getGrpContNo());
//                        tLJSGetClaimSchema.setGrpPolNo(tLLBalanceSet.get(j).getGrpPolNo());
//                        tLJSGetClaimSchema.setContNo(tLLBalanceSet.get(j).getContNo());
//                        tLJSGetClaimSchema.setPay(tPay);
//                        tLJSGetClaimSchema.setDutyCode(tLLBalanceSet.get(j).getDutyCode());
//                        tLJSGetClaimSchema.setGetDutyCode(tLLBalanceSet.get(j).getGetDutyCode());
//                        tLJSGetClaimSchema.setGetDutyKind(tLLBalanceSet.get(j).getGetDutyKind());
//                        tLJSGetClaimSchema.setRiskCode(tLLBalanceSet.get(j).getRiskCode());
//                        tLJSGetClaimSchema.setRiskVersion(tLLBalanceSet.get(j).getRiskVersion());
//                        tLJSGetClaimSchema.setKindCode(tLLBalanceSet.get(j).getKindCode());
//                        tLJSGetClaimSchema.setOperator(mG.Operator);
//                        tLJSGetClaimSchema.setMakeDate(CurrentDate);
//                        tLJSGetClaimSchema.setMakeTime(CurrentTime);
//                        tLJSGetClaimSchema.setModifyDate(CurrentDate);
//                        tLJSGetClaimSchema.setModifyTime(CurrentTime);
//                        //取得承保日期
//                        String tMCom = getPolManageCom(tLLBalanceSet.get(j).getPolNo());
//                        if (tMCom.equals("false"))
//                        {
//                            return false;
//                        }
//                        tLJSGetClaimSchema.setManageCom(tMCom);
//                        //待添加其他字段信息----------------------------------------
//                        tLJSGetClaimSet.add(tLJSGetClaimSchema);
//                    }
//                }
//                mLJSGetClaimSet.add(tLJSGetClaimSet);
//            }
//            map.put(mLLBnfSet, "INSERT");
//            map.put(mLJSGetSet, "INSERT");
//            map.put(mLJSGetClaimSet, "INSERT");
//            //------------------------------------------------------------------END
//        }
//        return true;
//    }

    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private String getPolManageCom(String PolNo)
    {
        //查询管理机构(来自承保)
        String tSql = " select managecom from lcpol where "
                      + " PolNo = '" + PolNo + "'";
        ExeSQL tExeSQL = new ExeSQL();
        String tManageCom = tExeSQL.getOneValue(tSql);
        if (tManageCom == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLBnfBL";
            tError.functionName = "dealData";
            tError.errorMessage = "查询保单管理机构失败!";
            this.mErrors.addOneError(tError);
            return "false";
        }
        return tManageCom;
    }
    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData()
    {
        try
        {
            mInputData.clear();
            mInputData.add(map);
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLBnfBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

}
