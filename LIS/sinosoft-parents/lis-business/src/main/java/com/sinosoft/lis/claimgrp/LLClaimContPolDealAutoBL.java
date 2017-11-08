package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import java.text.*;
import java.util.*;


import com.sinosoft.lis.bq.EdorCalZT;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;



/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 合同险种终止处理,自动进行合同终止处理</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author 续涛，2005.05.24--2005.05.24
 * @version 1.0
 */
public class LLClaimContPolDealAutoBL
{
private static Logger logger = Logger.getLogger(LLClaimContPolDealAutoBL.class);


    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mVData = new VData();
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private String mOperate;                        /** 数据操作字符串 */
    private MMap mMMap = new MMap();

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    private GlobalInput mGlobalInput = new GlobalInput();    /** 全局数据 */
    private TransferData mTransferData = new TransferData();
    private Reflections mReflections = new Reflections();

    private LLClaimDetailSet mLLClaimDetailSet = new LLClaimDetailSet();
    private LLContStateSet mLLContStateSet = new LLContStateSet();

    private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
    private ExeSQL mExeSQL = new ExeSQL();

    private String mClmNo    = "";     //赔案号
//    private String mCustNo   = "";     //客户号
//    private String mContType = "";     //总单类型,1-个人总投保单,2-集体总单
//    private String mContNo = "";       //合同号
//    private String mPolNo  = "";       //险种号
//    private String mContStateReason="";//合同险种终止处理意见
//    private String mContEndDate="";    //合同险种终止时间
    private String mAccDate  = "";     //意外事故发生日期

    public LLClaimContPolDealAutoBL(){}

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *          cOperate 数据操作
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate)
    {

        logger.debug("----------赔案合同险种自动终止处理-----LLClaimContPolDealAutoBL测试-----开始----------");

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData,cOperate))
        {
            return false;
        }

        if (!checkBalAmnt())
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

        if (!pubSubmit())
        {
            return false;
        }

        logger.debug("----------赔案合同险种自动终止处理-----LLClaimContPolDealAutoBL测试-----结束----------");
        return true;
    }


    /**
     * 取传入参数信息
     * @param cInputData VData
     * @return boolean
     */
    private boolean getInputData(VData cInputData,String cOperate)
    {
        this.mInputData = (VData)cInputData.clone();
        this.mOperate = cOperate;
        mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);

        mTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);

        this.mClmNo   = (String) mTransferData.getValueByName("ClmNo");     //赔案号
//        this.mCustNo  = (String) mTransferData.getValueByName("CustNo");    //客户号
//        this.mContType= (String) mTransferData.getValueByName("ContType");  //总单类型,1-个人总投保单,2-集体总单
//        this.mContNo  = (String) mTransferData.getValueByName("ContNo");    //合同号
//        this.mPolNo   = (String) mTransferData.getValueByName("PolNo");     //险种号
//        this.mContStateReason  = (String) mTransferData.getValueByName("ContStateReason");//合同终止处理意见
//        this.mContEndDate = (String) mTransferData.getValueByName("ContEndDate");//合同终止时间

        return true;
    }



    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－开始－－－－－－－－保额冲减校验－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    /**
     * 处理数据前做数据校验
     * @return
     */
    private boolean checkBalAmnt()
    {
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 判断该赔案是否有给付记录
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        logger.debug("-------------------------------保额冲减校验开始------------------------------------");

        LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
        String tQuerySql = "";
        tQuerySql = "select a.* from LLClaimDetail a,LLClaim b "
                  + "where a.clmno = b.clmno "
                  + "and a.givetype != '1' and (b.givetype = '0' or b.givetype = '4' )"
                  + "and a.clmno = '" + mClmNo + "'"
                  +" order by a.risktype "
                  ;

        logger.debug("------给付保项查询:"+tQuerySql);
        mLLClaimDetailSet = tLLClaimDetailDB.executeQuery(tQuerySql);
        if (tLLClaimDetailDB.mErrors.needDealError())
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLClaimDetailDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimConfirmPassBL";
            tError.functionName = "dealData";
            tError.errorMessage = "查询赔案给付赔付信息失败,不能进行此操作!";
            this.mErrors.addOneError(tError);
            return false;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 和LCGet表的有效保额进行比较
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        for (int i = 1; i <= mLLClaimDetailSet.size(); i++)
        {
            LLClaimDetailSchema tLLClaimDetailSchema = mLLClaimDetailSet.get(i);

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No2.2 查询领取项表
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            LCGetDB tLCGetDB = new LCGetDB();
            String tSql1 = "select * from LCGet where 1=1 "
                +" and PolNo = '" +tLLClaimDetailSchema.getPolNo() + "'"
                +" and DutyCode = '"+tLLClaimDetailSchema.getDutyCode() + "'"
                +" and GetDutyCode = '" +tLLClaimDetailSchema.getGetDutyCode()+"'";

            LCGetSet tLCGetSet = tLCGetDB.executeQuery(tSql1);
            if (tLCGetSet.size() != 1 || tLCGetDB.mErrors.needDealError())
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tLCGetDB.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimConfirmPassBL";
                tError.functionName = "dealData";
                tError.errorMessage = "查询承保领取项数据时出错!";
                this.mErrors.addOneError(tError);
                logger.debug("------------------------------------------------------");
                logger.debug("--LLClaimContPolDealAutoBL.checkData()--查询承保领取项数据时出错!"+tSql1);
                logger.debug("------------------------------------------------------");
                return false;
            }
            LCGetSchema tLCGetSchema = tLCGetSet.get(1);

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No2.3 取出各种标志位,进行保额冲减的判断
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            String tRiskCode = StrTool.cTrim(tLLClaimDetailSchema.getRiskCode());
            String tPosFlag  = StrTool.cTrim(tLLClaimDetailSchema.getPosFlag());//0未做保全,1做过保全
            String tPosPosEdorNo  = StrTool.cTrim(tLLClaimDetailSchema.getPosEdorNo());//保全批单号
            String tNBPolNo  = StrTool.cTrim(tLLClaimDetailSchema.getNBPolNo());//承保时的保单号

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No2.4 判断保项剩余的有效保额
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            if (mLLClaimPubFunBL.getLMRiskSort(tRiskCode,"7"))
            {
                double tRealPay = tLLClaimDetailSchema.getRealPay();//赔案给付金额
                double tBalAmnt = 0;

                String tGetDutyName = mLLClaimPubFunBL.getGetDutyName(tLCGetSchema);
                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 * No2.4 对做过保全的进行处理
                 *  0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化
                 *  如果tNBAmnt > tPosAmnt {[做保全前]--5万 > 减保后的保额[做保全后]--2万}
                 *
                 *      tRealPay > tBalAmnt {用给付金额 与 做保全前的有效保额相比较}
                 *
                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
                if (tPosFlag.equals("2"))
                {
                    LPGetSchema tLPGetSchema = null;
                    tLPGetSchema = mLLClaimPubFunBL.getLPGet(tPosPosEdorNo,tNBPolNo,tLCGetSchema);
                    if (tLPGetSchema == null)
                    {
                        return false;
                    }
                    double tNBAmnt = tLPGetSchema.getStandMoney(); //承保时的保额[做保全前]--5万
                    double tPosAmnt= tLCGetSchema.getStandMoney(); //减保后的保额[做保全后]--2万
                    tBalAmnt =  tLPGetSchema.getStandMoney() - tLPGetSchema.getSumMoney();//做保全前的有效保额

                    if ( (tNBAmnt > tPosAmnt) && (tRealPay > tBalAmnt) )
                    {
                        mErrors.addOneError("该合同做过保全减保,出险时点的有效保额不足以支付" + tGetDutyName + "的给付金额!");
                        return false;
                    }
                }
                else
                {
                    tBalAmnt =  tLCGetSchema.getStandMoney() - tLCGetSchema.getSumMoney();//做保全前的有效保额
                    if ( tRealPay > tBalAmnt )
                    {
                        mErrors.addOneError("有效保额不足以支付" + tGetDutyName + "的给付金额!");
                        return false;
                    }
                }


            }

        }

        logger.debug("-------------------------------保额冲减校验结束------------------------------------");
        return true;
    }


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－结束－－－－－－－－保额冲减校验－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */



    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－开始－－－－－－－－合同终止处理－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData()
    {
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 先删除附加险退还未满期保费,在进行后续操作
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

        String tSql1 = "delete from LLBalance where ClmNo='" +this.mClmNo+ "' and FeeOperationType in ('C09')";
        this.mMMap.put(tSql1, "DELETE");

        if (!getPrepareCalInfo())
        {
            return false;
        }

        if (!getLLClaim())
        {
            return false;
        }

        if (!getAfterGet())
        {
            return false;
        }
        return true;
    }



    /**
     * 将赔案表的合同处理状态置为1[已处理]
     */
    private boolean getLLClaim()
    {
        LLClaimDB tLLClaimDB = new LLClaimDB();
        tLLClaimDB.setClmNo(this.mClmNo);

        if (tLLClaimDB.getInfo() == false){
          // @@错误处理
          this.mErrors.copyAllErrors(tLLClaimDB.mErrors);
          CError tError = new CError();
          tError.moduleName = "LLClaimContPolDealAutoBL";
          tError.functionName = "getLLClaim";
          tError.errorMessage = "查询赔案信息失败!";
          this.mErrors.addOneError(tError);
          return false;
        }

        LLClaimSchema tLLClaimSchema = new LLClaimSchema();
        tLLClaimSchema.setSchema(tLLClaimDB.getSchema());
        tLLClaimSchema.setConDealFlag("1");
        this.mMMap.put(tLLClaimSchema, "UPDATE");

        return true;
    }



    /**
     * 得到处理需要的信息
     * @return boolean
     */
    private boolean getPrepareCalInfo()
    {
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 得到意外事故发生日期
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tSql = "";
        tSql = "select a.accDate from LLAccident a,LLCaseRela b where 1=1 "
            +" and a.AccNo = b.CaseRelaNo "
            +" and b.CaseNo in ('"+this.mClmNo+"')";

        ExeSQL tExeSQL = new ExeSQL();
        String tAccDate = tExeSQL.getOneValue(tSql);
        if (tAccDate == null || tAccDate.equals(""))
        {
            mErrors.addOneError("意外事故发生日期没有找到!");
            return false;
        }

        this.mAccDate = tAccDate.substring(0,10).trim();
        return true;
    }



    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－开始－－－－－－－－给付后动作处理－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */



    /**
     * 根据终止结论,对理赔的合同状态表进行操作
     * @return boolean
     */
    private boolean getAfterGet()
    {
        for (int i = 1; i <= mLLClaimDetailSet.size(); i++)
        {

            LLClaimDetailSchema tLLClaimDetailSchema = mLLClaimDetailSet.get(i);

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No1.0 查询LMDutyGetClm是否有记录
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            LMDutyGetClmDB tLMDutyGetClmDB = new LMDutyGetClmDB();
            LMDutyGetClmSet tLMDutyGetClmSet = new LMDutyGetClmSet();
            String tSql = "select * from LMDutyGetClm where 1=1 "
                +" and getdutycode = '"+ tLLClaimDetailSchema.getGetDutyCode() + "'"
                +" and GetDutyKind = '"+ tLLClaimDetailSchema.getGetDutyKind() + "'"
                ;
            tLMDutyGetClmSet = tLMDutyGetClmDB.executeQuery(tSql);

            if (tLMDutyGetClmSet == null && tLMDutyGetClmSet.size() != 1)
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LLClaimConfirmPassBL";
                tError.functionName = "dealData";
                tError.errorMessage = "责任给付赔付定义数据不唯一!"+tSql;
                this.mErrors.addOneError(tError);
                return false;
            }

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No2.0 获取险种定义信息
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            LMRiskAppSchema tLMRiskAppSchema = mLLClaimPubFunBL.getLMRiskApp(tLLClaimDetailSchema.getRiskCode());
            if (tLMRiskAppSchema == null)
            {
                return false;
            }


            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No5.0 查询LMDutyGetClm是否有记录
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            LMDutyGetClmSchema tLMDutyGetClmSchema = tLMDutyGetClmSet.get(1);
            String tAfterGet = StrTool.cTrim(tLMDutyGetClmSchema.getAfterGet());

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No5.1
             * AfterGet＝000 无动作
             * AfterGet＝001 [审批通过]保额递减，只冲减保额
             * AfterGet＝003 无条件销户时,终止该合同(包括所有的险种)
             * AfterGet＝004 最后一次给付销户,判断保额是否冲减完毕，如果冲减完毕执行003的动作
             * AfterGet＝005 [待定]
             * AfterGet＝006 [审批通过]终止该责任给付时,终止LCGet的数据
             * AfterGet＝007 [待定]终止该责任时：根据DutyCode的前六位，在LCDuty表中查找总数，如果与总数相等，
             *  则终止LCContState表中的状态,终止本险种.
             * AfterGet＝008  终止本险种
             *
             *
             * LMRiskApp.SubRiskFlag='S'[附险],
			 *   第一种情况:附加险只是扩展主险责任,赔付也是赔付主险责任的情况:如老系统的两个附加提前给付险种 LMDutyGetClm.EffectOnMainRisk='01',终止本身及主险;
			 *   第二种情况:附加险赔付自身,即附加险的给付责任在系统中存在描述:如新系统的MS附加提前给付重大疾病保险（2009）lmdutygetclm的EffectOnMainRisk需描述为'02',终止本身及主险;；
			
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            logger.debug("----------处理第["+i+"]个,险种["+tLLClaimDetailSchema.getRiskCode()+"],给付责任["+tLLClaimDetailSchema.getGetDutyCode()+"],给付后动作["+tAfterGet+"]");

            String tSubRiskFlag = StrTool.cTrim(tLMRiskAppSchema.getSubRiskFlag());
            String tEffectOnMainRisk = StrTool.cTrim(tLMDutyGetClmSchema.getEffectOnMainRisk());


            if (tSubRiskFlag.equals("S") && (tEffectOnMainRisk.equals("01")||tEffectOnMainRisk.equals("02")))
            {
                logger.debug("----------险类["+tSubRiskFlag+"],影响["+tEffectOnMainRisk+"]");
                if (!dealRisk(tLLClaimDetailSchema))
                {
                    return false;
                }
            }
            else
            {
                if (tAfterGet.equals("003"))
                {
                    if (!dealAfterGet003(tLLClaimDetailSchema))
                    {
                        return false;
                    }

                }
                else if (tAfterGet.equals("004"))
                {
                    if (!dealAfterGet004(tLLClaimDetailSchema))
                    {
                        return false;
                    }

                }
                else if (tAfterGet.equals("008"))
                {
                    if (!dealAfterGet008(tLLClaimDetailSchema))
                    {
                        return false;
                    }

                }
            }

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No7.0 判断本次赔案是否有
             *  9.附加险退未满期保费[225,264]的记录
             *  如果有,判断已经结案的赔案是否有过该记录,如果有,退费,存负值
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            String tRiskCode = StrTool.cTrim(tLLClaimDetailSchema.getRiskCode());
            String tDutyCoide= StrTool.cTrim(tLLClaimDetailSchema.getDutyCode());
            if ( mLLClaimPubFunBL.getLMRiskSort(tRiskCode,"9") && tDutyCoide.length()==6  )
            {

                String tSqlBal = "select a.* from LLBalance a,LLClaim b where 1=1"
                    +" and a.ClmNo = b.ClmNo "
                    +" and b.ClmState in ('50','60') "
                    +" and a.ClmNo not in ('"+this.mClmNo+"')"
                    +" and a.PolNo in ('"+tLLClaimDetailSchema.getPolNo()+"')"
                    +" and a.SubFeeOperationType ='C0901'"
                    +" and a.Pay > 0 "
                    ;


                LLBalanceDB tLLBalanceDB = new LLBalanceDB();
                LLBalanceSet tLLBalanceSet = tLLBalanceDB.executeQuery(tSqlBal);

                if (tLLBalanceDB.mErrors.needDealError())
                {
                    // @@错误处理
                    this.mErrors.copyAllErrors(tLLBalanceDB.mErrors);
                    CError tError = new CError();
                    tError.moduleName = "LLClaimContDealBLF";
                    tError.functionName = "getLLContState";
                    tError.errorMessage = "查询险种号为"+tLLClaimDetailSchema.getPolNo()+"未满期退费C0901数据失败!";
                    this.mErrors.addOneError(tError);
                    return false;
                }


                for (int j=1;j<=tLLBalanceSet.size();j++)
                {
                    LLBalanceSchema tLLBalanceSchema = tLLBalanceSet.get(j);
                    double tCal = tLLBalanceSchema.getPay();

                    tLLBalanceSchema.setClmNo(this.mClmNo);
                    tLLBalanceSchema.setGetDate(this.CurrentDate);                  //给付日期
                    tLLBalanceSchema.setPay(-tCal);                                 //赔付金额
                    tLLBalanceSchema.setOperator(mGlobalInput.Operator);            //操作员
                    tLLBalanceSchema.setMakeDate(this.CurrentDate);                //入机日期
                    tLLBalanceSchema.setMakeTime(this.CurrentTime);                //入机时间
                    tLLBalanceSchema.setModifyDate(this.CurrentDate);              //入机日期
                    tLLBalanceSchema.setModifyTime(this.CurrentTime);              //入机时间
                    this.mMMap.put(tLLBalanceSchema,"INSERT");
                }
            }


        }
        return true;
    }



    /**
     * 处理AfterGet＝003 无条件销户时,终止该合同(包括所有的险种)
     * @param pLLClaimDetailSchema
     * @return
     */
    private boolean dealAfterGet003(LLClaimDetailSchema pLLClaimDetailSchema)
    {


        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setContNo(pLLClaimDetailSchema.getContNo());
        LCPolSet tLCPolSet = tLCPolDB.query();
        if (tLCPolDB.mErrors.needDealError() || tLCPolSet.size()==0 )
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLCPolDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimContDealBLF";
            tError.functionName = "getLLContState";
            tError.errorMessage = "查询合同号为"+pLLClaimDetailSchema.getContNo()+"的数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        for (int i=1;i<=tLCPolSet.size();i++)
        {

            LCPolSchema tLCPolSchema = tLCPolSet.get(i);
            setLLContState(tLCPolSchema,"D00");
        }

        return true;
    }



    /**
     * AfterGet＝004 最后一次给付销户,判断保额是否冲减完毕，如果冲减完毕执行003的动作
     * @param pLLClaimDetailSchema
     * @return
     */
    private boolean dealAfterGet004(LLClaimDetailSchema pLLClaimDetailSchema)
    {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.1 查询领取项表
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

        LCGetDB tLCGetDB = new LCGetDB();
        String tSql1 = "select * from LCGet where 1=1 "
            +" and PolNo = '" +pLLClaimDetailSchema.getPolNo() + "'"
            +" and DutyCode = '"+pLLClaimDetailSchema.getDutyCode() + "'"
            +" and GetDutyCode = '" +pLLClaimDetailSchema.getGetDutyCode()+"'";

        LCGetSet tLCGetSet = tLCGetDB.executeQuery(tSql1);
        if (tLCGetDB.mErrors.needDealError() || tLCGetSet.size() == 0 )
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLCGetDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimConfirmPassBL";
            tError.functionName = "dealData";
            tError.errorMessage = "查询承保领取项数据时出错!";
            this.mErrors.addOneError(tError);
            logger.debug("------------------------------------------------------");
            logger.debug("--LLClaimContPolDealAutoBL.dealAfterGet004()--查询承保领取项数据时出错!"+tSql1);
            logger.debug("------------------------------------------------------");
            return false;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.2 计算保项的剩余保额
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        //因为主键查询,肯定至多一条,故不做循环
        double tRealPay = pLLClaimDetailSchema.getRealPay();
        double tStandM = tLCGetSet.get(1).getStandMoney();
        double tSumM = tLCGetSet.get(1).getSumMoney();
        double tMinus = tStandM - tSumM;

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.3 保项的剩余保额 = 给付总额
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (tMinus == tRealPay)
        {
            if (!dealAfterGet003(pLLClaimDetailSchema))
            {
                return false;
            }
        }
        return true;
    }



    /**
     * AfterGet＝008  终止本险种
     * @param pLLClaimDetailSchema
     * @return
     */
    private boolean dealAfterGet008(LLClaimDetailSchema pLLClaimDetailSchema)
    {
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(pLLClaimDetailSchema.getPolNo());
        LCPolSet tLCPolSet = tLCPolDB.query();
        if (tLCPolDB.mErrors.needDealError() || tLCPolSet.size()!=1 )
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLCPolDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimContDealBLF";
            tError.functionName = "getLLContState";
            tError.errorMessage = "查询险种号为"+pLLClaimDetailSchema.getPolNo()+"的数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        LCPolSchema tLCPolSchema = tLCPolSet.get(1);
        setLLContState(tLCPolSchema,"P00");

        return true;
    }



    /**
     * LMRiskApp.SubRiskFlag='S'[附险],LMDutyGetClm.EffectOnMainRisk='01',终止本身及主险
     * @param pLLClaimDetailSchema
     * @return
     */
    private boolean dealRisk(LLClaimDetailSchema pLLClaimDetailSchema)
    {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 获取该险种所在合同的所有信息
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setContNo(pLLClaimDetailSchema.getContNo());
        LCPolSet tLCPolSet = tLCPolDB.query();
        if (tLCPolDB.mErrors.needDealError() || tLCPolSet.size()==0 )
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLCPolDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimContDealBLF";
            tError.functionName = "getLLContState";
            tError.errorMessage = "查询合同号为"+pLLClaimDetailSchema.getContNo()+"的数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 循环该险种所在合同的所有险种定义信息
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        for (int i=1;i<=tLCPolSet.size();i++)
        {
            LCPolSchema tLCPolSchema = tLCPolSet.get(i);

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No2.1 获取险种定义信息
             *  终止本身及主险,其他附加险不终止
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            LMRiskAppSchema tLMRiskAppSchema = mLLClaimPubFunBL.getLMRiskApp(tLCPolSchema.getRiskCode());
            if (tLMRiskAppSchema == null)
            {
                return false;
            }

            //终止主险
            String tSubRiskFlag = tLMRiskAppSchema.getSubRiskFlag();
            if (tSubRiskFlag.equals("M"))
            {
                setLLContState(tLCPolSchema,"D00");
            }

            //终止本身
            if (pLLClaimDetailSchema.getPolNo().equals(tLCPolSchema.getPolNo()))
            {
                setLLContState(tLCPolSchema,"D00");
            }

        }

        return true;
    }


    /**
     * 根据保项所在的合同号到LCContState表中找到该合同所有数据,
     * 将StateType类型为Terminate的记录全部置为1终止，并填写终止时间
     * ClmState=1,赔案处理涉及的保单
     */
    private boolean setLLContState(LCPolSchema pLCPolSchema,String pDealState)
    {

        if (pLCPolSchema.getAppFlag().equals("1"))
        {
            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No1.0 防止重复终止险种,做个循环校验
             *  如果重复,不进行终止操作
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            for (int i=1;i<=mLLContStateSet.size();i++)
            {
                String tNewContNo = StrTool.cTrim(pLCPolSchema.getContNo());
                String tNewPolNo  = StrTool.cTrim(pLCPolSchema.getPolNo());

                LLContStateSchema tLLContStateSchema = mLLContStateSet.get(i);
                String tOldContNo = StrTool.cTrim(tLLContStateSchema.getContNo());
                String tOldPolNo  = StrTool.cTrim(tLLContStateSchema.getPolNo());

                if (tNewContNo.equals(tOldContNo) && tNewPolNo.equals(tOldPolNo) )
                {
                    return true;
                }
            }

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No2.0 退未满期保费
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            if (!getRSRiskBackFee(pLCPolSchema))
            {
                return false;
            }

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No3.0 判断是否是附加险,如果是附加险,判断是否是必须终止
             *  如果附加险不是立即终止,则退出
             *  10.主险终止附加险必须终止
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            String tRiskCode = pLCPolSchema.getRiskCode();
            LMRiskAppSchema tLMRiskAppSchema = mLLClaimPubFunBL.getLMRiskApp(tRiskCode);
            String tMSRisk = StrTool.cTrim(tLMRiskAppSchema.getSubRiskFlag());

            if ( tMSRisk.equals("S") && !mLLClaimPubFunBL.getLMRiskSort(tRiskCode,"10") )
            {
                return true;
            }


            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No3.0 进行终止处理
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            LLContStateSchema tLLContStateSchema = new LLContStateSchema();
            tLLContStateSchema.setClmNo(this.mClmNo);
            tLLContStateSchema.setContNo(pLCPolSchema.getContNo());
            tLLContStateSchema.setInsuredNo(pLCPolSchema.getInsuredNo());
            tLLContStateSchema.setPolNo(pLCPolSchema.getPolNo());
            tLLContStateSchema.setStateType("Terminate");
            tLLContStateSchema.setState("1");
            tLLContStateSchema.setStateReason("04");
            tLLContStateSchema.setStartDate(this.mAccDate);
            tLLContStateSchema.setEndDate("");
            tLLContStateSchema.setRemark("理赔合同自动终止处理");
            tLLContStateSchema.setOperator(mGlobalInput.Operator);
            tLLContStateSchema.setMakeDate(this.CurrentDate);
            tLLContStateSchema.setMakeTime(this.CurrentTime);
            tLLContStateSchema.setModifyDate(this.CurrentDate);
            tLLContStateSchema.setModifyTime(this.CurrentTime);
            tLLContStateSchema.setDealState(pDealState);
            tLLContStateSchema.setClmState("1");    //1赔案处理涉及的保单

            mLLContStateSet.add(tLLContStateSchema);
        }
        return true;
    }





    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－结束－－－－－－－－给付后动作处理－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */



    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－开始－－－－－－－－附加险退还未满期保费－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */

    /**
     * 目的:附加险退还未满期保费
     * @param tLCPolSchema
     * @return
     */
    private boolean getRSRiskBackFee(LCPolSchema pLCPolSchema)
    {



        String tRiskCode = StrTool.cTrim(pLCPolSchema.getRiskCode());
        if (mLLClaimPubFunBL.getLMRiskSort(tRiskCode,"9") && !mLLClaimPubFunBL.getPolNoAddPay(this.mClmNo,"a",pLCPolSchema) )
        {
            logger.debug("--------------------------------附加险退还未满期保费开始--------------------------------");

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No4.1 调用保全公式
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);
            String tPolNo = pLCPolSchema.getPolNo();
            double tCal = tEdorCalZT.getCashValue(tPolNo,this.mAccDate);

            logger.debug("-------合同号:["+pLCPolSchema.getContNo()+"],保险险种号:["+pLCPolSchema.getPolNo()+"],险种:["+pLCPolSchema.getRiskCode()+"],计算金额:["+tCal+"]");
            if(tCal == -1)
            {
                this.mErrors.copyAllErrors(tEdorCalZT.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimCalMatchBL";
                tError.functionName = "getYearBonus";
                tError.errorMessage = "计算错误,调用保全公式计算退还附加险未满期保费失败!";
                this.mErrors.addOneError(tError);
                return false;
            }
            if ( tCal > 0 )
            {
                LLBalanceSchema tLLBalanceSchema = new LLBalanceSchema();
                tLLBalanceSchema = setLLBalance("C09","C0901","TF",tCal,pLCPolSchema);
                this.mMMap.put(tLLBalanceSchema,"INSERT");
            }
            logger.debug("--------------------------------附加险退还未满期保费结束--------------------------------");
        }

        return true;

    }





    /**
     * 设置结算信息
     * @param pFeeOperationType:业务类型
     * @param pSubFeeOperationType:子业务类型
     * @param pFeeFinaType:财务类型
     * @param pCalValue:计算金额
     * @param pLCPolSchema:
     * @return
     */
    private LLBalanceSchema setLLBalance(String pFeeOperationType,
            String pSubFeeOperationType,String pFeeFinaType,
            double pCalValue,LCPolSchema pLCPolSchema)
    {


        LLBalanceSchema tLLBalanceSchema = new LLBalanceSchema();
        tLLBalanceSchema.setClmNo(this.mClmNo);
        tLLBalanceSchema.setFeeOperationType(pFeeOperationType);        //业务类型
        tLLBalanceSchema.setSubFeeOperationType(pSubFeeOperationType);  //子业务类型
        tLLBalanceSchema.setFeeFinaType(pFeeFinaType);                  //财务类型

        tLLBalanceSchema.setBatNo("0");             //批次号
        tLLBalanceSchema.setOtherNo("0");           //其它号码
        tLLBalanceSchema.setOtherNoType("0");       //财务类型
        tLLBalanceSchema.setSaleChnl("0");          //销售渠道

        tLLBalanceSchema.setGrpContNo(pLCPolSchema.getGrpContNo());      //集体合同号码
        tLLBalanceSchema.setContNo(pLCPolSchema.getContNo());            //合同号码
        tLLBalanceSchema.setGrpPolNo(pLCPolSchema.getGrpPolNo());        //集体保单号码
        tLLBalanceSchema.setPolNo(pLCPolSchema.getPolNo());              //保单号码

        tLLBalanceSchema.setDutyCode("0");                               //责任编码
        tLLBalanceSchema.setGetDutyKind("0");                            //给付责任类型
        tLLBalanceSchema.setGetDutyCode("0");                            //给付责任编码


        tLLBalanceSchema.setKindCode(pLCPolSchema.getKindCode());        //险类编码
        tLLBalanceSchema.setRiskCode(pLCPolSchema.getRiskCode());        //险种编码
        tLLBalanceSchema.setRiskVersion(pLCPolSchema.getRiskVersion());  //险种版本

        tLLBalanceSchema.setAgentCode(pLCPolSchema.getAgentCode());      //代理人编码
        tLLBalanceSchema.setAgentGroup(pLCPolSchema.getAgentGroup());    //代理人组别


        tLLBalanceSchema.setGetDate(this.CurrentDate);                  //给付日期
        tLLBalanceSchema.setPay(pCalValue);                              //赔付金额

        tLLBalanceSchema.setPayFlag("0");                               //支付标志,0未支付,1已支付
        tLLBalanceSchema.setState("0");                                 //状态,0有效
        tLLBalanceSchema.setDealFlag("0");                              //处理标志,0未处理

        tLLBalanceSchema.setManageCom(pLCPolSchema.getManageCom());     //管理机构
        tLLBalanceSchema.setAgentCom(pLCPolSchema.getAgentCom());       //代理机构
        tLLBalanceSchema.setAgentType(pLCPolSchema.getAgentType());     //代理机构内部分类


        tLLBalanceSchema.setOperator(mGlobalInput.Operator);            //操作员
        tLLBalanceSchema.setMakeDate(this.CurrentDate);                //入机日期
        tLLBalanceSchema.setMakeTime(this.CurrentTime);                //入机时间
        tLLBalanceSchema.setModifyDate(this.CurrentDate);              //入机日期
        tLLBalanceSchema.setModifyTime(this.CurrentTime);              //入机时间

        return tLLBalanceSchema;

    }




    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－结束－－－－－－－－附加险退还未满期保费－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */



    /**
     * 准备需要保存的数据
     * @return boolean
     */


    private boolean prepareOutputData()
    {
        String tSql = "delete from LLContState where 1=1"
            +" and ClmNo  ='" + this.mClmNo +"'"
            ;

        this.mMMap.put(tSql, "DELETE");
        this.mMMap.put(mLLContStateSet,"DELETE&INSERT");

        mInputData.clear();
        mInputData.add(mMMap);
        return true;
    }

    /**
     * 提交数据
     * @return
     */
    private boolean pubSubmit()
    {
        //  进行数据提交
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, ""))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimContDealBLF";
            tError.functionName = "PubSubmit.submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;

    }


    public VData getResult()
    {
        return mResult;
    }


    public static void main(String[] args)
    {


        String tClmNo = "90000001863";      /*赔案号*/

        LLRegisterDB tLLRegisterDB = new LLRegisterDB();
        tLLRegisterDB.setRgtNo(tClmNo);
        if (tLLRegisterDB.getInfo()==false)
        {
            logger.debug("------------------------------------------------------");
            logger.debug("--查询立案信息失败!");
            logger.debug("------------------------------------------------------");
        }

        String tClmState = tLLRegisterDB.getClmState();
        String tRgtClass = tLLRegisterDB.getRgtClass();

        GlobalInput tG = new GlobalInput();
        tG.Operator=tLLRegisterDB.getOperator();
        tG.ManageCom = tLLRegisterDB.getMngCom();
        tG.ComCode = tLLRegisterDB.getMngCom();


        TransferData tTransferData = new TransferData();

        tTransferData.setNameAndValue("ClmNo",tClmNo);

        VData tVData = new VData();
        tVData.addElement(tG);
        tVData.addElement(tTransferData);


        LLClaimContPolDealAutoBL tLLClaimContPolDealAutoBL = new LLClaimContPolDealAutoBL();


        if (tLLClaimContPolDealAutoBL.submitData(tVData,"") == false)
        {
            logger.debug("-------false-------");
        }

        int n = tLLClaimContPolDealAutoBL.mErrors.getErrorCount();
        for (int i = 0; i < n; i++)
        {
            String Content = "";
            Content = Content + "原因是: " + tLLClaimContPolDealAutoBL.mErrors.getError(i).errorMessage;
            logger.debug(Content);
        }
    }


}


