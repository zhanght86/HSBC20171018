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
import com.sinosoft.lis.claimgrp.SynLCLBPolBL;
import com.sinosoft.lis.claimgrp.SynLCLBDutyBL;
import com.sinosoft.lis.claimgrp.SynLCLBGetBL;
import com.sinosoft.lis.bq.EdorCalZT;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 合同终止处理的入口BLF</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author 续涛，2005.05.24--2005.05.24
 * @version 1.0
 */
public class LLClaimContDealBLF
{
private static Logger logger = Logger.getLogger(LLClaimContDealBLF.class);

    
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
    
    private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
    private ExeSQL mExeSQL = new ExeSQL();
    
    private LCPolSet mLCPolSet = null;
                  
    private String mClmNo    = "";     //赔案号
    private String mCustNo   = "";     //客户号   
    private String mContType = "";     //总单类型,1-个人总投保单,2-集体总单       
    private String mContNo = "";       //合同号
    private String mContStateReason="";//合同终止处理意见
    private String mContEndDate="";    //合同终止时间
    private String mAccDate  = "";     //意外事故发生日期    
       
    public LLClaimContDealBLF(){}

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *          cOperate 数据操作
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate)
    {
    
        logger.debug("----------合同终止处理-----LLClaimContDealBLF测试-----开始----------");
                
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData,cOperate))
        {
            return false;
        }

        if (!checkData())
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
        
        logger.debug("----------合同终止处理-----LLClaimContDealBLF测试-----结束----------");
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
        this.mCustNo  = (String) mTransferData.getValueByName("CustNo");    //客户号
        this.mContType= (String) mTransferData.getValueByName("ContType");  //总单类型,1-个人总投保单,2-集体总单
        this.mContNo  = (String) mTransferData.getValueByName("ContNo");    //合同号        
        this.mContStateReason  = (String) mTransferData.getValueByName("ContStateReason");//合同终止处理意见
        this.mContEndDate = (String) mTransferData.getValueByName("ContEndDate");//合同终止时间
                        
        return true;
    }
                    
    
    /**
     * 处理数据前做数据校验
     * @return
     */
    private boolean checkData()
    {
        String tSql = "select * from LLContState where 1 =1 "
            +" and ClmNo='" +this.mClmNo+ "'"
            +" and ContNo = '"+this.mContNo+"'"
            ;
        
        LLContStateDB tLLContStateDB = new LLContStateDB();           
        LLContStateSet tLLContStateSet = tLLContStateDB.executeQuery(tSql);
        
        if (tLLContStateDB.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tLLContStateDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getPolPay";
            tError.errorMessage = "查询合同状态暂存信息失败!";
            this.mErrors.addOneError(tError);
            logger.debug("------------------------------------------------------");
            logger.debug("--LLClaimContDealBLF.checkData()--查询合同状态暂存信息失败!"+tSql);
            logger.debug("------------------------------------------------------");
            return false;            
        }
                        
        if (tLLContStateSet.size()>0)
        {
            /*  2005-08-15应曾军要求,去掉此校验,目的是为了更改处理结论,因为审批不通过,可能回到
             *  审核作个解除合同XXX的的处理操作
             * 
             */
//            CError tError = new CError();
//            tError.moduleName = "LLClaimContDealBLF";
//            tError.functionName = "getLLContState";
//            tError.errorMessage = "合同号:["+this.mContNo+"]下的险种已经被系统自动终止,不能人工操作!";
//            this.mErrors.addOneError(tError);
//            return false;              
        }
        
        return true;
    }
    
    
    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData()    
    {          
                      
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No0.1 得到计算需要的信息
         * 
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */        
        if (!getPrepareCalInfo())
        {
            return false;
        }
        
        if (!getLLClaim())
        {
            return false;
        }
        
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 先删除相关数据,在进行后续操作
         * 
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        
        String tSql1 = "delete from LLBalance where ClmNo='" +this.mClmNo+ "' and substr(FeeOperationType,1,1) in ('C','D') and ContNo = '"+this.mContNo+"'";
        this.mMMap.put(tSql1, "DELETE");

        
        String tSql2 = "delete from LLBnf where ClmNo='" + this.mClmNo + "' and BnfKind = 'A'";
        mMMap.put(tSql2, "DELETE");
                
        String tSql3 = "delete from LJSGet where OtherNo='" + this.mClmNo + "'";
        mMMap.put(tSql3, "DELETE");
        
        String tSql4 = "delete from LJSGetClaim where OtherNo='" + this.mClmNo + "'";
        mMMap.put(tSql4, "DELETE");
        

        String tSql6 = "delete from LLContState where ClmNo='" +this.mClmNo+ "' and ContNo = '"+this.mContNo+"'";        
        this.mMMap.put(tSql6, "DELETE");
        
        String tSqlA = "delete from LLJSPay where ClmNo='"+this.mClmNo+"'";
        String tSqlB = "delete from LLJSPayPerson where ClmNo='"+this.mClmNo+"'";
        String tSqlC = "delete from LLJSGet where ClmNo='"+this.mClmNo+"'";
        String tSqlD = "delete from LLJSGetDraw where ClmNo='"+this.mClmNo+"'";
        
        mMMap.put(tSqlA,"DELETE");
        mMMap.put(tSqlB,"DELETE");
        mMMap.put(tSqlC,"DELETE");
        mMMap.put(tSqlD,"DELETE");
                
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 对合同进行终止操作,D99撤销终止结论
         * 
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (!mContStateReason.equals("D99"))
        {
            if (!getLLContState())
            {
                return false;
            }
            
            
            if (!getLLContDeal())
            {
                return false;
            }
        }
                    
                                                               

        return true;
    }
    
    
    /**
     * 得到结算需要的信息
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
        mTransferData.setNameAndValue("AccDate",mAccDate);
                                    
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
          tError.moduleName = "UrgeNoticeOverdueBL";
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
    
    
    
    
    
 
    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     * 
     * －－－－－－－－开始－－－－－－－－合同终止处理－－－－－－－－－－－－－－－－－－
     * 
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */ 
    /**
     * 根据终止结论,对理赔的合同状态表进行操作
     * @return boolean
     */    
    private boolean getLLContState()    
    {   
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 查询出合同状态表,状态为Available的所有信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */        
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setContNo(this.mContNo);
        tLCPolDB.setAppFlag("1");
        mLCPolSet = tLCPolDB.query();
        
        if (mLCPolSet == null || mLCPolSet.size() == 0 )
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLCPolDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimContDealBLF";
            tError.functionName = "getLLContState";
            tError.errorMessage = "没有查询到合同号为"+this.mContNo+"的险种数据!";
            this.mErrors.addOneError(tError);
            return false;              
        }
        
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 根据合同终止结论对合同进行操作
         * 
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        
        LLContStateSchema tLLContStateSchema = null;
        LLContStateSet tLLContStateSet = new LLContStateSet();
        
        for ( int i=1 ; i <= mLCPolSet.size() ; i++ )                
        {
            LCPolSchema tLCPolSchema = mLCPolSet.get(i);
                        
            if (mContStateReason.equals("D01") || mContStateReason.equals("D02") || mContStateReason.equals("D03") || mContStateReason.equals("D04") )
            {
                tLLContStateSchema = new LLContStateSchema();
                
                tLLContStateSchema.setClmNo(this.mClmNo);
                tLLContStateSchema.setContNo(tLCPolSchema.getContNo());
                tLLContStateSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
                tLLContStateSchema.setPolNo(tLCPolSchema.getPolNo());
                tLLContStateSchema.setStateType("Terminate");
                tLLContStateSchema.setState("1");
                tLLContStateSchema.setStateReason("04");
                tLLContStateSchema.setStartDate(this.mAccDate);                    
                tLLContStateSchema.setEndDate(this.CurrentDate);
                tLLContStateSchema.setRemark("理赔合同人工终止处理");
                tLLContStateSchema.setOperator(mGlobalInput.Operator);
                tLLContStateSchema.setMakeDate(this.CurrentDate);
                tLLContStateSchema.setMakeTime(this.CurrentTime);
                tLLContStateSchema.setModifyDate(this.CurrentDate);
                tLLContStateSchema.setModifyTime(this.CurrentTime);
                tLLContStateSchema.setDealState(this.mContStateReason);
                tLLContStateSchema.setClmState("0");    //0合同处理涉及的保单
            }
            
            tLLContStateSet.add(tLLContStateSchema);
            
        }
        
        this.mMMap.put(tLLContStateSet,"DELETE&INSERT");
        
        return true;
    }
    

    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     * 
     * －－－－－－－－开始－－－－－－－－合同终止费用计算处理－－－－－－－－－－－－－－
     * 
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */     
    
    /**
     * 根据终止结论,对理赔的合同状态表进行操作
     * @return boolean
     */    
    private boolean getLLContDeal()    
    {   
        
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No5.0 根据合同终止结论对合同上的险种进行结算操作
         * 
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LCPolSchema tLCPolSchema = null;        
        for ( int i=1 ; i <= mLCPolSet.size() ; i++ )                
        {
            tLCPolSchema = mLCPolSet.get(i);
            String tPolNo = tLCPolSchema.getPolNo();
            
            mVData.clear();
            mVData.add(mGlobalInput);
            mVData.add(tLCPolSchema);
            
            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No4.0 D01：判断附加险是否退还未满期保费
             * 
             *  对应的业务类型编码为C09,C0901,TF
             * 
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
            */        
            
            if (!this.mContStateReason.equals("D01"))
            {
                if (!getRSRiskBackFee(tLCPolSchema))
                {
                    return false;
                }                
            }


            
            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No5.1 D01：合同终止退还保费[客户所有已交保费，含出险前后的已交保费]
             * 
             *  目前在保单结算功能中会计算退出险以后的保费,对应的业务类型编码为C01,C0101
             *  而在合同处理中,该功能只处理退出险日期以前的全部保费
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */        
            
            if (this.mContStateReason.equals("D01"))
            {
                if (!getContDealD01(tLCPolSchema))
                {
                    return false;
                }
                
            }
                         
            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No5.2 D02：合同终止合同终止退还现值[出险日期调用保全的现金价值计算公式计算]
             *  
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */        
            if (this.mContStateReason.equals("D02"))
            {
                
                if (!getContDealD02(tLCPolSchema))
                {
                    return false;
                }
            }

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No5.3 D03：合同终止不退费
             *  对应保单结算退出险以后的保费
             *  先查看保单结算是否有数据,如果有,不用计算;如果没有,则需要进行计算
             *  对应的业务类型编码为C01,C0101
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */        
            if (this.mContStateReason.equals("D03"))
            {
                if (!getContDealD03(tLCPolSchema))
                {
                    return false;
                }
                
            }
            
            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No5.4 D04：合同终止处理
             *  根据条款规定,某些附加险需要退还手续费及未满期保费
             *  对应的业务类型编码为D04,D0401
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */        
            if (this.mContStateReason.equals("D04"))
            {
                if (!getContDealD04(tLCPolSchema))
                {
                    return false;
                }
                
            }
            
        }
                        
        return true;
    }
    
    
    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     * 
     * －－－－－－－－过程中－－－－开始－－－－附加险退还未满期保费－－－－－－－－－－－
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
        if (mLLClaimPubFunBL.getLMRiskSort(tRiskCode,"9") && !mLLClaimPubFunBL.getPolNoAddPay(this.mClmNo,"",pLCPolSchema) )
        {
            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No4.1 调用保全公式
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */        
                    
            EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);
            double tCal = tEdorCalZT.getCashValue(pLCPolSchema.getPolNo(),this.mAccDate);
            
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
        }
        
        return true;

    }
    


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     * 
     * －－－－－－－－过程中－－－－结束－－－－附加险退还未满期保费－－－－－－－－－－－
     * 
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */ 
    
    
    
    private boolean getContDealD01(LCPolSchema tLCPolSchema)
    {
        
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 D01：合同终止退还保费[客户所有已交保费，含出险前后的已交保费]
         * 
         *  目前在保单结算功能中会计算退出险以后的保费,对应的业务类型编码为C01,C0101
         *  而在合同处理中,该功能只处理退出险日期以前的全部保费
         * 
         *  退出险日期以前的保费--LLBalRecedeFeeBeforeBL.java
         *  退出险日期以后的保费--LLBalRecedeFeeAfterBL.java在保单结算中可能做过
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        
        
        mTransferData.setNameAndValue("FeeOperationType","D01");
        mTransferData.setNameAndValue("SubFeeOperationType","D0102");
        mVData.add(mTransferData);
        
        LLBalRecedeFeeBeforeBL tLLBalRecedeFeeBeforeBL = new LLBalRecedeFeeBeforeBL();
        if (tLLBalRecedeFeeBeforeBL.submitData(mVData,mOperate) == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLBalRecedeFeeBeforeBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimContDealBLF";
            tError.functionName = "getContDealD01";
            tError.errorMessage = "退费保单结算数据查询失败!";
            this.mErrors.addOneError(tError);                
            return false;
        }
        else
        {
            VData tempVData = tLLBalRecedeFeeBeforeBL.getResult();  
            MMap tMMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
            this.mMMap.add(tMMap);   
        }
        
        
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 D0102:先判断出险日期以后的保费是否已经结算过,
         *  如果有数据,不在进行结算;如果无,需要调用结算LLBalRecedeFeeAfterBL.java
         *  对应C01,C0101,
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */        
        LLBalanceDB tLLBalanceDB = new LLBalanceDB();
        tLLBalanceDB.setClmNo(this.mClmNo);
        tLLBalanceDB.setFeeOperationType("C01");
        tLLBalanceDB.setSubFeeOperationType("C0101");
        tLLBalanceDB.setPolNo(tLCPolSchema.getPolNo());        
        LLBalanceSet tLLBalanceSet = tLLBalanceDB.query();
        
        if (tLLBalanceSet == null || tLLBalanceSet.size() == 0)
        {
                        
            LLBalRecedeFeeAfterBL tLLBalRecedeFeeAfterBL = new LLBalRecedeFeeAfterBL();
            if (tLLBalRecedeFeeAfterBL.submitData(mVData,mOperate) == false)
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tLLBalRecedeFeeAfterBL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimContDealBLF";
                tError.functionName = "getContDealD01";
                tError.errorMessage = "退费保单结算数据查询失败!";
                this.mErrors.addOneError(tError);                
                return false;
            }
            else
            {
                VData tempVData = tLLBalRecedeFeeAfterBL.getResult();  
                MMap tMMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
                this.mMMap.add(tMMap);                                
            }
            
        }
                
        return true;
    }
    
    
    
    /**
     * 目的:对主险退现金价值
     *      对附加险不作任何处理
     * @param tLCPolSchema
     * @return
     */
    private boolean getContDealD02(LCPolSchema tLCPolSchema)
    {
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 对附加险长期险退现金价值
         * 
         * 11.长期附加险应退现金价值,277,280,332
         * 
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */        
        String tRiskCode = StrTool.cTrim(tLCPolSchema.getRiskCode());
        
        LMRiskAppSchema tLMRiskAppSchema = mLLClaimPubFunBL.getLMRiskApp(tRiskCode);
        String tRiskType = StrTool.cTrim(tLMRiskAppSchema.getSubRiskFlag());
        
        if (tRiskType.equals("S") && !mLLClaimPubFunBL.getLMRiskSort(tRiskCode,"11"))
        {
            return true;
        }
                
        
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 D02：基本保额应退金额[现金价值]
         * D0201：退出险日期以前的保费
         * 调用保全公式
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */        
        EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);

        double tCal = tEdorCalZT.getCashValue(tLCPolSchema.getPolNo(),this.mAccDate);
        
        if(tCal == -1)
        {
            this.mErrors.copyAllErrors(tEdorCalZT.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalMatchBL";
            tError.functionName = "getYearBonus";
            tError.errorMessage = "计算错误,调用保全公式计算现金价值失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        
        
        if ( tCal > 0 )
        {
            LLBalanceSchema tLLBalanceSchema = new LLBalanceSchema();
            tLLBalanceSchema = setLLBalance("D02","D0201","TB",tCal,tLCPolSchema);                          
            this.mMMap.put(tLLBalanceSchema,"INSERT");            
        }


                
        return true;
    }
    
       
    /**
     * 目的:合同终止不退费
     * @param tLCPolSchema
     * @return
     */
    private boolean getContDealD03(LCPolSchema tLCPolSchema)
    {
        
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 D03：合同终止不退费
         *  对应保单结算退出险以后的保费
         *  先查看保单结算是否有数据,如果有,不用计算;如果没有,则需要进行计算
         *  对应的业务类型编码为C01,C0101
         * 
         *－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
                
        mTransferData.setNameAndValue("FeeOperationType","D01");
        mTransferData.setNameAndValue("SubFeeOperationType","D0102");
        mVData.add(mTransferData);
                       
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 D0102:先判断出险日期以后的保费是否已经结算过,
         *  如果有数据,不在进行结算;如果无,需要调用结算LLBalRecedeFeeAfterBL.java
         *  对应C01,C0101,
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */        
        LLBalanceDB tLLBalanceDB = new LLBalanceDB();
        tLLBalanceDB.setClmNo(this.mClmNo);
        tLLBalanceDB.setFeeOperationType("C01");
        tLLBalanceDB.setSubFeeOperationType("C0101");
        tLLBalanceDB.setPolNo(tLCPolSchema.getPolNo());        
        LLBalanceSet tLLBalanceSet = tLLBalanceDB.query();
        
        if (tLLBalanceSet == null || tLLBalanceSet.size() == 0)
        {
                        
            LLBalRecedeFeeAfterBL tLLBalRecedeFeeAfterBL = new LLBalRecedeFeeAfterBL();
            if (tLLBalRecedeFeeAfterBL.submitData(mVData,mOperate) == false)
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tLLBalRecedeFeeAfterBL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimContDealBLF";
                tError.functionName = "getContDealD03";
                tError.errorMessage = "退费保单结算数据查询失败!";
                this.mErrors.addOneError(tError);                
                return false;
            }
            else
            {
                VData tempVData = tLLBalRecedeFeeAfterBL.getResult();  
                MMap tMMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
                this.mMMap.add(tMMap);    
            }
            
        }
                
        return true;
    }
    
  
    
    private boolean getContDealD04(LCPolSchema tLCPolSchema)
    {
        
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
     * －－－－－－－－结束－－－－－－－－合同终止处理－－－－－－－－－－－－－－－－－－
     * 
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */     

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

                     
        String tClaimFeeCode = "abcdefg";
        tClaimFeeCode = tClaimFeeCode.substring(1);
        
        
        GlobalInput tG = new GlobalInput();
        tG.Operator="001";
        tG.ManageCom = "86000000";
        tG.ComCode = "86110000";
        
                
        TransferData tTransferData = new TransferData(); 
        
        tTransferData.setNameAndValue("ClmNo","90000001640");
//        tTransferData.setNameAndValue("CustNo","");
        tTransferData.setNameAndValue("ConType","1");  
        tTransferData.setNameAndValue("ContNo","000000000082");    
        tTransferData.setNameAndValue("ContStateReason","D02");
                                        
        VData tVData = new VData();           
        tVData.addElement(tG);
        tVData.addElement(tTransferData);

        
        LLClaimContDealBLF tLLClaimContDealBLF = new LLClaimContDealBLF();
             
        
        if (tLLClaimContDealBLF.submitData(tVData,"") == false)
        {
            logger.debug("-------false-------");
        }
        
        int n = tLLClaimContDealBLF.mErrors.getErrorCount();
        for (int i = 0; i < n; i++)
        {
            String Content = "";
            Content = Content + "原因是: " + tLLClaimContDealBLF.mErrors.getError(i).errorMessage;
            logger.debug(Content);
        } 
    }    

    
}


/**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
 * No2.1 D04,终止主险,附加险不终止
 *  D05,解除附加险合同
 * 
 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
 
if (this.mContStateReason.equals("D04") || this.mContStateReason.equals("D05") )
{
    String tSql = "select SubRiskFlag from LMRiskApp where 1=1 "
        +" and riskcode = "
        +"  (select riskcode from LCPol where PolNo='"+tLCPolSchema.getPolNo()+"')";
    ExeSQL tExeSQL = new ExeSQL();                
    String tSubRiskFlag = tExeSQL.getOneValue(tSql);
    if (tSubRiskFlag == null || tSubRiskFlag.length() == 0)
    {
        CError tError = new CError();
        tError.moduleName = "LLClaimContDealBLF";
        tError.functionName = "getLLContState";
        tError.errorMessage = "没有查询到险种编码为"+tSubRiskFlag+"的信息!";
        this.mErrors.addOneError(tError);
        return false; 
    }
    
    //判断如果为附加险,M为主险,S为附加险
    if ( this.mContStateReason.equals("D04") && tSubRiskFlag.equals("M"))
    {
        tLLContStateSchema = new LLContStateSchema();
        
        tLLContStateSchema.setClmNo(this.mClmNo);
        tLLContStateSchema.setContNo(tLCPolSchema.getContNo());
        tLLContStateSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
        tLLContStateSchema.setPolNo(tLCPolSchema.getPolNo());
        tLLContStateSchema.setState("Terminate");
        tLLContStateSchema.setState("1");
        tLLContStateSchema.setStateReason("04");
        tLLContStateSchema.setStartDate(tLCPolSchema.getCValiDate());                    
        tLLContStateSchema.setEndDate(this.CurrentDate);
        tLLContStateSchema.setRemark("合同终止处理");
        tLLContStateSchema.setOperator(mGlobalInput.Operator);
        tLLContStateSchema.setMakeDate(this.CurrentDate);
        tLLContStateSchema.setMakeTime(this.CurrentTime);
        tLLContStateSchema.setModifyDate(this.CurrentDate);
        tLLContStateSchema.setModifyTime(this.CurrentTime);
        tLLContStateSchema.setDealState(this.mContStateReason);
        tLLContStateSchema.setClmState("0");    //合同处理涉及的保单
    }
    else if ( this.mContStateReason.equals("D05") && tSubRiskFlag.equals("S"))                    
    {
        tLLContStateSchema = new LLContStateSchema();                    
        tLLContStateSchema.setClmNo(this.mClmNo);
        tLLContStateSchema.setContNo(tLCPolSchema.getContNo());
        tLLContStateSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
        tLLContStateSchema.setPolNo(tLCPolSchema.getPolNo());
        tLLContStateSchema.setStateType("Terminate");
        tLLContStateSchema.setState("1");
        tLLContStateSchema.setStateReason("04");
        tLLContStateSchema.setStartDate(tLCPolSchema.getCValiDate());                    
        tLLContStateSchema.setEndDate(this.CurrentDate);
        tLLContStateSchema.setRemark("合同终止处理");
        tLLContStateSchema.setOperator(mGlobalInput.Operator);
        tLLContStateSchema.setMakeDate(this.CurrentDate);
        tLLContStateSchema.setMakeTime(this.CurrentTime);
        tLLContStateSchema.setModifyDate(this.CurrentDate);
        tLLContStateSchema.setModifyTime(this.CurrentTime);
        tLLContStateSchema.setDealState(this.mContStateReason);
        tLLContStateSchema.setClmState("0");    //合同处理涉及的保单                    
    }
}
*/
