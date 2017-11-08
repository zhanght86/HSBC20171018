package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import java.text.*;
import java.util.*;


import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.claimgrp.SynLCLBPolBL;
import com.sinosoft.lis.claimgrp.SynLCLBDutyBL;
import com.sinosoft.lis.claimgrp.SynLCLBGetBL;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 险种终止处理</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author 续涛，2005.05.24--2005.05.24
 * @version 1.0
 */
public class LLClaimContPolDealBL
{
private static Logger logger = Logger.getLogger(LLClaimContPolDealBL.class);

    
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
    
                  
    private String mClmNo    = "";     //赔案号
    private String mCustNo   = "";     //客户号   
    private String mContType = "";     //总单类型,1-个人总投保单,2-集体总单       
    private String mContNo = "";       //合同号
    private String mPolNo  = "";       //险种号
    private String mContStateReason="";//合同险种终止处理意见
    private String mContEndDate="";    //合同险种终止时间
    private String mAccDate  = "";     //意外事故发生日期    
       
    public LLClaimContPolDealBL(){}

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *          cOperate 数据操作
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate)
    {
    
        logger.debug("----------险种终止处理-----LLClaimContDealBLF测试-----开始----------");
                
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
        
        logger.debug("----------险种终止处理-----LLClaimContDealBLF测试-----结束----------");
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
        this.mPolNo   = (String) mTransferData.getValueByName("PolNo");     //险种号
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
            +" and PolNo  ='" + this.mPolNo +"'"
            +" and ClmState='1'";   //1--赔案审批通过涉及处理的保单
        
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
            logger.debug("--LLClaimContPolDealBL.checkData()--查询合同状态暂存信息失败!"+tSql);
            logger.debug("------------------------------------------------------");
            return false;            
        }
                        
        if (tLLContStateSet.size()>0)
        {
            /*  2005-08-15应曾军要求,去掉此校验,目的是为了更改处理结论,因为审批不通过,可能回到
             *  审核作个解除合同XXX的结论
             * 
             */
//            CError tError = new CError();
//            tError.moduleName = "LLClaimContPolDealBL";
//            tError.functionName = "getLLContState";
//            tError.errorMessage = "保单险种号:["+this.mPolNo+"]已经被系统自动终止,不能人工操作!";
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
        String tSql1 = "delete from LLBalance where ClmNo='" +this.mClmNo+ "' and substr(FeeOperationType,1,1) in ('C','D') and  PolNo = '" + this.mPolNo +"'";
        this.mMMap.put(tSql1, "DELETE");                       
        String tSql2 = "delete from LLContState where 1=1 "
            +" and ClmNo  ='" + this.mClmNo +"'"
            +" and PolNo  ='" + this.mPolNo +"'"
            ;
        this.mMMap.put(tSql2, "DELETE");
        
        
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 对合同险种进行终止操作,P99撤销终止结论
         * 
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (!mContStateReason.equals("P99"))
        {
            if (!getLLContState())
            {
                return false;
            }
            
            if (!dealPolFee(mPolNo))
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
     * －－－－－－－－开始－－－－－－－－险种终止处理－－－－－－－－－－－－－－－－－－
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
        tLCPolDB.setPolNo(this.mPolNo);
        LCPolSet tLCPolSet = tLCPolDB.query();
        
        if (tLCPolSet.size() == 0 )
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLCPolDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimContDealBLF";
            tError.functionName = "getLLContState";
            tError.errorMessage = "没有查询到险种保单号:["+this.mPolNo+"]的数据!";
            this.mErrors.addOneError(tError);
            return false;              
        }
        else if (tLCPolSet.size() != 1 )
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLCPolDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimContDealBLF";
            tError.functionName = "getLLContState";
            tError.errorMessage = "查询到险种保单号:["+this.mPolNo+"]的数据不唯一!";
            this.mErrors.addOneError(tError);
            return false;               
        }
        
        if (tLCPolSet.get(1).getAppFlag().equals("4"))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLCPolDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimContDealBLF";
            tError.functionName = "getLLContState";
            tError.errorMessage = "险种保单号:["+this.mPolNo+"]的状态为终止,不用进行处理!";
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
        
        for ( int i=1 ; i <= tLCPolSet.size() ; i++ )                
        {
            LCPolSchema tLCPolSchema = tLCPolSet.get(i);
                        
            if (mContStateReason.equals("P01") || mContStateReason.equals("P02") )
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
                tLLContStateSchema.setClmState("0");    //0合同处理涉及的保单
            }
            
            tLLContStateSet.add(tLLContStateSchema);
            
        }
        
        this.mMMap.put(tLLContStateSet,"DELETE&INSERT");
        
        return true;
    }
    

    
    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     * 
     * －－－－－－－－开始－－－－－－－－附加险退未满期保费处理－－－－－－－－－－－－－
     * 
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */   
    
    /**
     * 退附加险的未满期保费
     * @return
     */
    private boolean dealPolFee(String pPolNo)
    {
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 取出保单所在险种,进行险种判断,是否需要退未满期保费
         * 
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */                                
        SynLCLBPolBL tSynLCLBPolBL = new SynLCLBPolBL();
        tSynLCLBPolBL.setPolNo(pPolNo);
        
        tSynLCLBPolBL.getInfo();
        if (tSynLCLBPolBL.mErrors.needDealError())
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tSynLCLBPolBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "calDutyPay";
            tError.errorMessage = "LCLBDuty表查询失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        
        LCPolSchema tLCPolSchema = new LCPolSchema();
        tLCPolSchema.setSchema(tSynLCLBPolBL.getSchema());
        String tRiskCode = StrTool.cTrim(tLCPolSchema.getRiskCode());
        
        if ( getLMRiskSort(tRiskCode,"9") )
        {            
            if (!dealPolCalFee(tLCPolSchema))
            {
                return false;
            }
            
        }
        
        return true;
    }
    
    
    /**
     * 对LMRiskSort进行判断
     * 7.理赔保额冲减
     * 8.理赔医疗类附加险单独理算
     * 9.退附加险的未满期保费
     * @return
     */        
    private boolean getLMRiskSort(String pRiskCode,String pRiskType)
    {
        LMRiskSortDB tLMRiskSortDB = new LMRiskSortDB();
        tLMRiskSortDB.setRiskCode(pRiskCode);
        tLMRiskSortDB.setRiskSortType(pRiskType);
        LMRiskSortSet tLMRiskSortSet = tLMRiskSortDB.query();
        
        //没有查到定义的数据
        if (tLMRiskSortSet.size()==1)
        {
            return true;
        }
        
        return false;
    }
                  
    
    
    /**
     * 调用保全计算未满期保费
     * @param pLCPolSchema
     * @return
     */
    private boolean dealPolCalFee(LCPolSchema pLCPolSchema)
    {
        
        
//        setLLBalance(pLCPolSchema,"D04","D0401",0);
        
        return true;
    }
        
    /**
     * 
     * @param pLCPolSchema:
     * @param pFeeOperationType:业务类型
     * @param pSubFeeOperationType:子业务类型
     * @param pPayCal:计算金额
     * @return
     */
    private boolean setLLBalance(LCPolSchema pLCPolSchema,String pFeeOperationType,String pSubFeeOperationType,double pPayCal)
    {
        //生成结算数据
        LLBalanceSchema tLLBalanceSchema = new LLBalanceSchema();
        tLLBalanceSchema.setClmNo(this.mClmNo);
        tLLBalanceSchema.setFeeOperationType(pFeeOperationType);           //业务类型
        tLLBalanceSchema.setSubFeeOperationType(pSubFeeOperationType);     //子业务类型
        tLLBalanceSchema.setFeeFinaType("TF");             //财务类型

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
        tLLBalanceSchema.setPay(pPayCal);                               //赔付金额

        tLLBalanceSchema.setPayFlag("0");                               //支付标志,0未支付,1已支付
        tLLBalanceSchema.setState("0");                                 //状态,0有效
        tLLBalanceSchema.setDealFlag("0");                              //处理标志,0未处理

        tLLBalanceSchema.setManageCom(mGlobalInput.ManageCom);          //管理机构
        tLLBalanceSchema.setAgentCom("");                               //代理机构
        tLLBalanceSchema.setAgentType("");                              //代理机构内部分类


        tLLBalanceSchema.setOperator(mGlobalInput.Operator);            //操作员
        tLLBalanceSchema.setMakeDate(PubFun.getCurrentDate());          //入机日期
        tLLBalanceSchema.setMakeTime(PubFun.getCurrentTime());          //入机时间
        tLLBalanceSchema.setModifyDate(PubFun.getCurrentDate());        //入机日期
        tLLBalanceSchema.setModifyTime(PubFun.getCurrentTime());        //入机时间

        mMMap.put(tLLBalanceSchema,"DELETE&INSERT");
        return true;
    }
    

    
    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     * 
     * －－－－－－－－结束－－－－－－－－附加险退未满期保费处理－－－－－－－－－－－－－－
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
        
        tTransferData.setNameAndValue("ClmNo","90000001326");
//        tTransferData.setNameAndValue("CustNo","");
        tTransferData.setNameAndValue("ConType","1");  
        tTransferData.setNameAndValue("ContNo","230110000003347");
        tTransferData.setNameAndValue("PolNo","230110000003347");
        tTransferData.setNameAndValue("ContStateReason","D05");
                                        
        VData tVData = new VData();           
        tVData.addElement(tG);
        tVData.addElement(tTransferData);

        
        LLClaimContPolDealBL tLLClaimContPolDealBL = new LLClaimContPolDealBL();
             
        
        if (tLLClaimContPolDealBL.submitData(tVData,"") == false)
        {
            logger.debug("-------false-------");
        }
        
        int n = tLLClaimContPolDealBL.mErrors.getErrorCount();
        for (int i = 0; i < n; i++)
        {
            String Content = "";
            Content = Content + "原因是: " + tLLClaimContPolDealBL.mErrors.getError(i).errorMessage;
            logger.debug(Content);
        } 
    }    

    
}


