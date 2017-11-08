package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import java.text.*;
import java.util.*;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:理算步骤八，产生赔付应付数据</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author 续涛，2005.05.24--2005.05.24
 * @version 1.0
 */
public class LLClaimCalShouldPayBL
{
private static Logger logger = Logger.getLogger(LLClaimCalShouldPayBL.class);



    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private String mOperate;                        /** 数据操作字符串 */
    private MMap mMMap = new MMap();

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    private GlobalInput mGlobalInput = new GlobalInput();    /** 全局数据 */
    private TransferData mTransferData = new TransferData();

    private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
    private LLBalanceSet mLLBalanceSet = new LLBalanceSet();

    private String mClmNo    = "";     //赔案号


    public LLClaimCalShouldPayBL(){}

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate)
    {


        logger.debug("----------理算步骤八-----生成结算数据-----LLClaimCalShouldPayBL测试-----开始----------");
        if (!getInputData(cInputData,cOperate))
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


        logger.debug("----------理算步骤八-----生成结算数据-----LLClaimCalShouldPayBL测试-----结束----------");
        return true;
    }


    /**
     * 取传入参数信息
     * @param cInputData VData
     * @return boolean
     */
    private boolean getInputData(VData cInputData, String cOperate)
    {

        mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
        mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
//        this.mAccNo   = (String) mTransferData.getValueByName("AccNo");     //事件号
//        this.mAccDate = (String) mTransferData.getValueByName("AccDate");   //意外事故发生日期

        this.mClmNo   = (String) mTransferData.getValueByName("ClmNo");     //赔案号

        return true;
    }




    private boolean dealData()
    {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 先删除已经计算过的赔付应付数据信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tSql1 = "delete from LLBalance where ClmNo='" + this.mClmNo + "' and substr(FeeOperationType,1,1) in ( 'A','C','D')  and substr(FeeOperationType,1,3) not in ('C05','C30')";
        mMMap.put(tSql1, "DELETE");

        String tSql2 = "delete from LLBnf where ClmNo='" + this.mClmNo + "' and BnfKind = 'A'";
        mMMap.put(tSql2, "DELETE");

        String tSql3 = "delete from LJSGet where OtherNo='" + this.mClmNo + "' and  OtherNoType='5' ";
        mMMap.put(tSql3, "DELETE");

        String tSql4 = "delete from LJSGetClaim where OtherNo='" + this.mClmNo + "' and  OtherNoType='5' ";
        mMMap.put(tSql4, "DELETE");
        
        String tSql5 = "update LLClaim set ConBalFlag='0',ConDealFlag='0',CasePayType='0' where ClmNo='" + this.mClmNo + "'";
        mMMap.put(tSql5, "UPDATE");
        
        String tSql6 = "delete from LLBnfGather where ClmNo='" + this.mClmNo + "' and BnfKind = 'A'";
        mMMap.put(tSql6, "DELETE");
        
		String tSql7 = "update llregister set CasePayType='0' where RgtNo='"
			+ this.mClmNo + "'";
		mMMap.put(tSql7, "UPDATE");



        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 得到结算数据
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if ( !getBalanceData() )
        {
            return false;
        }


        return true;
    }


    /**
     * 判断该赔案是正常案件,还是申诉案件
     * @return boolean
     */
    private boolean getBalanceData()
    {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 判断该案件是否是申诉案件
         * 泰康目前没有申诉案件 2006-03-27 注释 P.D
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

//        String tSql = "";
//        tSql = "select * from LLAppeal where 1=1 "
//                + " and AppealNo = '"+this.mClmNo+"'";
//
//        LLAppealDB tLLAppealDB = new LLAppealDB();
//        LLAppealSet tLLAppealSet = tLLAppealDB.executeQuery(tSql);
//
//        if (tLLAppealDB.mErrors.needDealError()==true)
//        {
//            this.mErrors.addOneError("在申诉信息中查找到相关的数据发生错误，不能理算!"+tLLAppealDB.mErrors.getError(0).errorMessage);
//            return false;
//        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 如果是申诉案件,执行申诉案件的理赔结算数据的生成
         * 泰康目前没有申诉案件 2006-03-27 注释 P.D
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
//        if (tLLAppealSet != null && tLLAppealSet.size() == 1 )
//        {
//            if ( !getLLAppealBalance(tLLAppealSet.get(1)) )
//            {
//                return false;
//            }
//        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 如果是正常案件,执行正常案件的理赔结算数据的生成
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
//        else
//        {

            if ( !getLLBalance() )
            {
                return false;
            }
//        }

        return true;
    }



    /**
     * 进行申诉案件的理赔结算数据的生成
     * @return
     */
    private boolean getLLAppealBalance(LLAppealSchema pLLAppealSchema)
    {

        String tOldClmNo = pLLAppealSchema.getCaseNo();     /*得到旧的赔案号*/

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 根据申诉的赔案号进行保单明细的查询
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

        LLClaimPolicyDB tLLClaimPolicyNewDB = new LLClaimPolicyDB();
//        tLLClaimPolicyNewDB.setClmNo(this.mClmNo);
//        tLLClaimPolicyNewDB.setGiveType("0");
        String tlsql="select * from llclaimpolicy where ClmNo='"+this.mClmNo+"' and GiveType !='1' ";//赔付类型为给付
        LLClaimPolicySet tLLClaimPolicyNewSet = tLLClaimPolicyNewDB.executeQuery(tlsql);
        if (tLLClaimPolicyNewDB.mErrors.needDealError())
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLClaimPolicyNewDB.mErrors);
            CError tError =new CError();
            tError.moduleName="LLClaimCalShouldPayBL";
            tError.functionName="getLJSGetClaim";
            tError.errorMessage="没有查询到赔案保单信息";
            this.mErrors .addOneError(tError) ;
            return false;
        }


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 根据申诉的赔案号进行保单明细的循环处理
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LLClaimPolicySchema tLLClaimPolicyNewSchema = null; /*申诉案件的保单明细信息*/
        LLClaimPolicySchema tLLClaimPolicyOldSchema = null; /*原案件的保单明细信息*/
        for ( int i=1 ; i<=tLLClaimPolicyNewSet.size() ; i++ )
        {
            tLLClaimPolicyNewSchema = tLLClaimPolicyNewSet.get(i);

            String tGetDutyKind = StrTool.cTrim(tLLClaimPolicyNewSchema.getGetDutyKind());
            if (tGetDutyKind.substring(2,3).equals("9"))
            {
                continue;
            }

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No2.1 根据险种保单号得到LCPol信息
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            String tPol = tLLClaimPolicyNewSchema.getPolNo();
            LCPolSchema tLCPolSchema = mLLClaimPubFunBL.getLCPol(tPol);
            if ( tLCPolSchema == null )
            {
                return false;
            }

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No2.2 根据申诉的原赔案号进行原保项明细的查询
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */

            LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
//            tLLClaimDetailDB.setClmNo(tOldClmNo);
//            tLLClaimDetailDB.setPolNo(tPol);
//            tLLClaimDetailDB.setGetDutyKind(tGetDutyKind);
//            tLLClaimDetailDB.setGiveType("0");
            String tsql="select * from llclaimdetail where ClmNo='"+tOldClmNo+"' and PolNo='"+tPol+"' and GetDutyKind='"+tGetDutyKind+"' and GiveType !='1' ";
            //liuyu增加赔付类型2,3
            LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB.executeQuery(tsql);

            if (tLLClaimDetailSet.mErrors.needDealError())
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tLLClaimPolicyNewDB.mErrors);
                CError tError =new CError();
                tError.moduleName="LLClaimCalShouldPayBL";
                tError.functionName="getLLAppealBalance";
                tError.errorMessage="没有查询到原赔案保项信息,原赔案号为:"+tOldClmNo;
                this.mErrors .addOneError(tError) ;
                return false;
            }

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No2.3 循环原保项明细信息
             * 如果原保项为拒付，则将其金额置0
             * 如果原保项为给付，则将其金额不变
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            double tNewPay = tLLClaimPolicyNewSchema.getRealPay();
            double tOldPay = 0; //原赔案的赔付金额

            for ( int j=1 ; j<=tLLClaimDetailSet.size() ; j++ )
            {
                LLClaimDetailSchema tLLClaimDetailSchema = tLLClaimDetailSet.get(j);
                String tGiveType = StrTool.cTrim(tLLClaimDetailSchema.getGiveType());
                if (!tGiveType.equals("1"))
                {
                    tOldPay = tOldPay + tLLClaimDetailSchema.getRealPay();
                }
            }


            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No2.4 准备写入结算信息的金额，用于受益人分配
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            double tCalValue = tNewPay - tOldPay; //

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No3.1 进行申诉案件结算信息的添加
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            LLBalanceSchema tLLBalanceSchema = new LLBalanceSchema();
            tLLBalanceSchema = setLLBalance("A",tGetDutyKind,"PK",tCalValue,tLLClaimPolicyNewSchema.getCurrency(),tLCPolSchema);

            mLLBalanceSet.add(tLLBalanceSchema);
        }

        mMMap.put(mLLBalanceSet, "INSERT");
        return true;
    }




    /**
     * 进行正常案件的理赔结算数据的生成
     * @return
     */
    private boolean getLLBalance()
    {

        LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
        /**tLLClaimDetailDB.setClmNo(this.mClmNo);
         * 金述海20070423注...原因:以前只有0和1两种状态,现在还有2和3
         * tLLClaimDetailDB.setGiveType("0");          //赔付类型为给付
         * LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB.query();
         */
        //2009-04-21 zhangzheng 拒付案件也要生成结算信息,只是分配金额为0
        String tlsql="select * from llclaimdetail where  clmno='"+this.mClmNo+"' " ;
        LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB.executeQuery(tlsql);
        //金述海20070423
        if (tLLClaimDetailDB.mErrors.needDealError())
        {
            this.mErrors .addOneError("生成赔案的数据时,没有找到赔案明细信息!!!") ;
            return false;
        }


        for ( int i=1 ; i<=tLLClaimDetailSet.size() ; i++ )
        {
            LLClaimDetailSchema tLLClaimDetailSchema = tLLClaimDetailSet.get(i);

            String tPolNo = StrTool.cTrim(tLLClaimDetailSchema.getPolNo());
            String tDutyCode = StrTool.cTrim(tLLClaimDetailSchema.getDutyCode());
            String tGetDutyKind = StrTool.cTrim(tLLClaimDetailSchema.getGetDutyKind());
            String tGetDutyCode = StrTool.cTrim(tLLClaimDetailSchema.getGetDutyCode());
            double tDRealPay = tLLClaimDetailSchema.getRealPay();

            if (tGetDutyKind.substring(2,3).equals("9"))
            {
                continue;
            }

            LCPolSchema tLCPolSchema = mLLClaimPubFunBL.getLCPol(tPolNo);
            if ( tLCPolSchema==null )
            {
                mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
                return false;
            }
            
    		//获得对应的财务类型
			String tFeeFinaType=mLLClaimPubFunBL.getFeeFinaType(tLCPolSchema.getRiskCode(),
											tLLClaimDetailSet.get(i).getGetDutyKind());
			
			if(tFeeFinaType==null){
				CError.buildErr(this, "理算出错,无法确定财务类型!");
				return false;
			}

            LLBalanceSchema tLLBalanceSchema = new LLBalanceSchema();

            tLLBalanceSchema.setClmNo(this.mClmNo);                 //也就是赔案号
            tLLBalanceSchema.setFeeOperationType("A");              //补/退费业务类型
            tLLBalanceSchema.setSubFeeOperationType(tGetDutyKind);  //补/退费子业务类型,用来存放理赔类型
            tLLBalanceSchema.setFeeFinaType(tFeeFinaType);                  //补/退费财务类型

            tLLBalanceSchema.setBatNo("0");                  //批次号
            tLLBalanceSchema.setOtherNo("0");               //其它号码
            tLLBalanceSchema.setOtherNoType("0");           //其它号码类型



            tLLBalanceSchema.setGrpContNo(tLCPolSchema.getGrpContNo());   //集体合同号码
            tLLBalanceSchema.setContNo(tLCPolSchema.getContNo());         //合同号码
            tLLBalanceSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());     //集体保单号码
            tLLBalanceSchema.setPolNo(tLCPolSchema.getPolNo());           //保单号码


            tLLBalanceSchema.setDutyCode(tDutyCode);         //责任编码
            tLLBalanceSchema.setGetDutyKind(tGetDutyKind);   //给付责任类型
            tLLBalanceSchema.setGetDutyCode(tGetDutyCode);   //给付责任编码

            tLLBalanceSchema.setKindCode(tLCPolSchema.getKindCode());        //险类编码
            tLLBalanceSchema.setRiskCode(tLCPolSchema.getRiskCode());        //险种编码
            tLLBalanceSchema.setRiskVersion(tLCPolSchema.getRiskVersion());  //险种版本

            tLLBalanceSchema.setSaleChnl(tLCPolSchema.getSaleChnl());        //销售渠道
            tLLBalanceSchema.setAgentCode(tLCPolSchema.getAgentCode());      //代理人编码
            tLLBalanceSchema.setAgentGroup(tLCPolSchema.getAgentGroup());    //代理人组别

            tLLBalanceSchema.setGetDate(CurrentDate);                        //给付日期
            tLLBalanceSchema.setPay(tDRealPay);                              //赔付金额
            tLLBalanceSchema.setCurrency(tLLClaimDetailSchema.getCurrency());

            tLLBalanceSchema.setPayFlag("0");                                //支付标志,0未支付,1已支付
            tLLBalanceSchema.setState("0");                                  //状态,0有效,1无效
            tLLBalanceSchema.setDealFlag("0");                               //处理标志,0未处理,1已处理


            tLLBalanceSchema.setManageCom(tLCPolSchema.getManageCom());     //管理机构
            tLLBalanceSchema.setAgentCom(tLCPolSchema.getAgentCom());       //代理机构
            tLLBalanceSchema.setAgentType(tLCPolSchema.getAgentType());     //代理机构内部分类
            
            tLLBalanceSchema.setCustomerNo(tLLClaimDetailSchema.getCustomerNo());


            tLLBalanceSchema.setOperator(this.mGlobalInput.Operator);
            tLLBalanceSchema.setMakeDate(PubFun.getCurrentDate());
            tLLBalanceSchema.setMakeTime(PubFun.getCurrentTime());
            tLLBalanceSchema.setModifyDate(PubFun.getCurrentDate());
            tLLBalanceSchema.setModifyTime(PubFun.getCurrentTime());
            //tLLBalanceSchema.setOriPay(tDRealPay);                          //原始金额

            mLLBalanceSet.add(tLLBalanceSchema);
        }

        mMMap.put(mLLBalanceSet, "INSERT");
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
            double pCalValue, String pCurrency, LCPolSchema pLCPolSchema)
    {


        LLBalanceSchema tLLBalanceSchema = new LLBalanceSchema();
        tLLBalanceSchema.setClmNo(this.mClmNo);
        tLLBalanceSchema.setFeeOperationType(pFeeOperationType);        //业务类型
        tLLBalanceSchema.setSubFeeOperationType(pSubFeeOperationType);  //子业务类型
        tLLBalanceSchema.setFeeFinaType(pFeeFinaType);                  //财务类型

        tLLBalanceSchema.setBatNo("0");             //批次号
        tLLBalanceSchema.setOtherNo("0");           //其它号码
        tLLBalanceSchema.setOtherNoType("0");       //财务类型
        tLLBalanceSchema.setSaleChnl(pLCPolSchema.getSaleChnl());        //销售渠道


        tLLBalanceSchema.setGrpContNo(pLCPolSchema.getGrpContNo());      //集体合同号码
        tLLBalanceSchema.setContNo(pLCPolSchema.getContNo());            //合同号码
        tLLBalanceSchema.setGrpPolNo(pLCPolSchema.getGrpPolNo());        //集体保单号码
        tLLBalanceSchema.setPolNo(pLCPolSchema.getPolNo());              //保单号码

        tLLBalanceSchema.setDutyCode("0");                               //责任编码
        tLLBalanceSchema.setGetDutyKind(pSubFeeOperationType);           //给付责任类型
        tLLBalanceSchema.setGetDutyCode("0");                            //给付责任编码


        tLLBalanceSchema.setKindCode(pLCPolSchema.getKindCode());        //险类编码
        tLLBalanceSchema.setRiskCode(pLCPolSchema.getRiskCode());        //险种编码
        tLLBalanceSchema.setRiskVersion(pLCPolSchema.getRiskVersion());  //险种版本

        tLLBalanceSchema.setAgentCode(pLCPolSchema.getAgentCode());      //代理人编码
        tLLBalanceSchema.setAgentGroup(pLCPolSchema.getAgentGroup());    //代理人组别


        tLLBalanceSchema.setGetDate(this.CurrentDate);                  //给付日期
        tLLBalanceSchema.setPay(pCalValue);                              //赔付金额
        tLLBalanceSchema.setCurrency(pCurrency);

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

        //tLLBalanceSchema.setOriPay(pCalValue);                          //原始金额

        return tLLBalanceSchema;

    }



    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData()
    {

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
            tError.moduleName = "LLClaimCalDutyKindBL";
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



        LLReportSchema tLLReportSchema = new LLReportSchema();


        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         *
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tClmNo = "90000001898";


        String tSql = "select * from LLAccident where AccNo in "
            +" (select CaseRelaNo from LLCaseRela where CaseNo = '"+tClmNo+"')";

        LLAccidentDB tLLAccidentDB = new LLAccidentDB();
        LLAccidentSet tLLAccidentSet = tLLAccidentDB.executeQuery(tSql);

        if (tLLAccidentDB.mErrors.needDealError())
        {
            logger.debug("------------------------------------------------------");
            logger.debug("--LLClaimCalMatchBL.getPosEdorNo()--获取保全处理的批改生效日期发生错误!"+tSql);
            logger.debug("------------------------------------------------------");
        }
        if (tLLAccidentSet.size()==0)
        {
            logger.debug("----------------------无事故信息-----------------------");
            return;
        }


        String tAccNo = tLLAccidentSet.get(1).getAccNo();
        String tAccDate = tLLAccidentSet.get(1).getAccDate().substring(0,10);

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
        tTransferData.setNameAndValue("AccNo",tAccNo);
        tTransferData.setNameAndValue("ClmNo",tClmNo);
        tTransferData.setNameAndValue("AccDate",tAccDate);
        tTransferData.setNameAndValue("ContType",tRgtClass);  //总单类型,1-个人总投保单,2-集体总单
        tTransferData.setNameAndValue("ClmState",tClmState);  //赔案状态，20立案，30审核


        VData tVData = new VData();
        tVData.addElement(tG);
        tVData.addElement(tTransferData);


        LLClaimCalShouldPayBL tLLClaimCalShouldPayBL = new LLClaimCalShouldPayBL();
        tLLClaimCalShouldPayBL.submitData(tVData, "");
        int n = tLLClaimCalShouldPayBL.mErrors.getErrorCount();


    }



}


