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
 * <p>Description:理算步骤六，理算后续加减保计算</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author 续涛，2005.05.24--2005.05.24
 * @version 1.0
 */
public class LLClaimCalAutoAfterBL
{
private static Logger logger = Logger.getLogger(LLClaimCalAutoAfterBL.class);

    
    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */    
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */   
    private VData mResult = new VData();            /** 往界面传输数据的容器 */    
    private String mOperate;                        /** 数据操作字符串 */
    private MMap mMMap = new MMap();
    
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    private GlobalInput mGlobalInput = new GlobalInput();    /** 全局数据 */    
    private TransferData mTransferData = new TransferData();
    
      
//    private String mAccNo    = "";     //事件号
//    private String mAccDate  = "";     //意外事故发生日期
    private String mClmNo    = "";     //赔案号
//    private String mCusNo    = "";     //客户号
//    private String mContType = "";     //总单类型,1-个人总投保单,2-集体总单
//    private String mGetDutyKind;       //理赔类型
//    private String mGetDutyCode;       //责任编码
    
    
    //  理赔--赔案信息
    private LLClaimSchema mLLClaimSchema = new LLClaimSchema();
    private LLClaimDutyKindSet mLLClaimDutyKindSet = new LLClaimDutyKindSet();
    private LLClaimPolicySet   mLLClaimPolicySet   = new LLClaimPolicySet();
    
    
    
    private double m_Sum_ClaimFee = 0;          /*赔案给付金额*/
    private double m_Sum_JFFee = 0;             /*赔案拒付金额*/

    
    public LLClaimCalAutoAfterBL(){}

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate)
    {
    
        
        logger.debug("----------理算步骤六-----理算后续加减保计算-----LLClaimCalAutoAfterBL测试-----开始----------");
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
        
         
        logger.debug("----------理算步骤六-----理算后续加减保计算-----LLClaimCalAutoAfterBL测试-----结束----------");
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
//      this.mAccDate = (String) mTransferData.getValueByName("AccDate");   //意外事故发生日期        
        this.mClmNo   = (String) mTransferData.getValueByName("ClmNo");     //赔案号
                              
        return true;
    }
    

    
 
    private boolean dealData()
    {
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No5.0 处理加保数据
         *  先冲减加保的保项的保额
         *  
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (!getAddInsAmnt())
        {
            return false;
        }
                
        return true;
    }

    


    /**
     * 处理加保保额冲减的情况
     * 先冲减加保的保项的保额,再冲减未加保保项的保额
     * @return
     */
    private boolean getAddInsAmnt()
    {
        String tSql ="" ;
        
        LLClaimDetailSet tLLClaimDetailSaveSet = new LLClaimDetailSet();
        
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 查询该赔案下的所有保项信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */ 
        LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
        tLLClaimDetailDB.setClmNo(this.mClmNo);
        LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB.query();                
        if (tLLClaimDetailDB.mErrors.needDealError())
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLClaimDetailDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "calDutyPay";
            tError.errorMessage = "查询保项信息失败,理算失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        
        if(tLLClaimDetailSet == null || tLLClaimDetailSet.size() == 0 )
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLClaimCalMatchBL";
            tError.functionName = "getLLAppClaimReason";
            tError.errorMessage = "查询的保项信息为空,理算失败!";
            this.mErrors.addOneError(tError);            
            return false;
        } 
        
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 循环该赔案下的所有保项信息,
         * 
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */ 
        for (int i=1 ; i<=tLLClaimDetailSet.size() ; i++ )
        {
            LLClaimDetailSchema tLLClaimDetailSchema = tLLClaimDetailSet.get(i);
            
            String tPolNo = tLLClaimDetailSchema.getPolNo();
            String tDutyCode = tLLClaimDetailSchema.getDutyCode();
            String tGetDutyKind = tLLClaimDetailSchema.getGetDutyKind();
            String tGetDutyCode = tLLClaimDetailSchema.getGetDutyCode();
            
            double tAddAmnt = tLLClaimDetailSchema.getAddAmnt();//加保的保额
            double tRealPay = tLLClaimDetailSchema.getRealPay();//给付金额
            
            
            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No2.1 判断保项的加保保额是否大于0,
             * 如果大于0,则认为是加保的项,用赔付给付金先冲减加保保项的给付金
             * 
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */ 
            if ( tAddAmnt > 0 )
            {
                tSql = "select * from LLClaimDetail where 1=1"
                    +" and ClmNo='"+this.mClmNo+"'"
                    +" and PolNo='"+tPolNo+"'"
                    +" and GetDutyCode='"+tGetDutyCode+"'"
                    +" and GetDutyKind='"+tGetDutyKind+"'"
                    +" and DutyCode like '"+tDutyCode+"%'"
                    +" and DutyCode not in ('"+tDutyCode+"')"
                    ;
                
                LLClaimDetailDB tAMLLClaimDetailDB = new LLClaimDetailDB();
                LLClaimDetailSet tAMLLClaimDetailSet = tAMLLClaimDetailDB.executeQuery(tSql);
                
                if (tAMLLClaimDetailDB.mErrors.needDealError())
                {
                    this.mErrors.copyAllErrors(tAMLLClaimDetailDB.mErrors);
                    CError tError = new CError();
                    tError.moduleName = "LLClaimCalAutoBL";
                    tError.functionName = "getPolPay";
                    tError.errorMessage = "查询赔案加保保项信息出现错误!";
                    this.mErrors.addOneError(tError);
                    logger.debug("------------------------------------------------------");
                    logger.debug("--LLClaimCalDutyKindBL.getAddInsAmnt()--查询赔案加保保项信息出现错误!"+tSql);
                    logger.debug("------------------------------------------------------");
                    return false;
                }
                
                if(tAMLLClaimDetailSet == null || tAMLLClaimDetailSet.size() == 0 )
                {
                    // @@错误处理
                    CError tError = new CError();
                    tError.moduleName = "LLClaimCalMatchBL";
                    tError.functionName = "getLLAppClaimReason";
                    tError.errorMessage = "查询赔案加保保项信息出现错误!";
                    logger.debug("------------------------------------------------------");
                    logger.debug("--LLClaimCalDutyKindBL.getAddInsAmnt()--查询赔案加保保项信息出现错误!"+tSql);
                    logger.debug("------------------------------------------------------");                    
                    this.mErrors.addOneError(tError);            
                    return false;
                } 
                
                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 * No2.4 循环加保的保项,进行给付金冲减
                 * 
                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */ 
                              
                double tBalAmnt = tRealPay ;
                LLClaimDetailSchema tLLClaimDetailSaveSchema = new LLClaimDetailSchema();
                for (int m=1 ; m<=tAMLLClaimDetailSet.size() ; m++ )
                {
                    LLClaimDetailSchema tAMLLClaimDetailSchema = tAMLLClaimDetailSet.get(m);
                    
                    
                    ////加保保项的保额 - 给付的金额
//                    double tAMAmnt = tAMLLClaimDetailSchema.getAmnt() - tAMLLClaimDetailSchema.getRealPay() ;
                    double tAMAmnt = tAMLLClaimDetailSchema.getAmnt() - tAMLLClaimDetailSchema.getRealPay() ;
                    
                    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     * No2.6 
                     *  加保前保项的给付金 > 加保保项的保额 , 则加保保项的给付金 = 加保保项的保额
                     *  加保前保项的给付金 < 加保保项的保额 , 则加保保项的给付金 = 加保前保项的给付金
                     * 
                     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     */ 
                    if ( tBalAmnt > tAMAmnt )
                    {
                        tAMLLClaimDetailSchema.setRealPay(tAMAmnt);
                        tBalAmnt = tBalAmnt - tAMAmnt ;                   
                    }
                    else if ( tBalAmnt < tAMAmnt )
                    {
                        tAMLLClaimDetailSchema.setRealPay(tBalAmnt);
                        tBalAmnt = 0;
                    }
                    
                    tLLClaimDetailSaveSchema.setSchema(tAMLLClaimDetailSchema);  
                    tLLClaimDetailSaveSet.add(tLLClaimDetailSaveSchema);
                }
                
                tLLClaimDetailSchema.setRealPay(tBalAmnt);
                tLLClaimDetailSaveSet.add(tLLClaimDetailSchema);
                
            }                                     
            
        }
             
        mMMap.put(tLLClaimDetailSaveSet, "UPDATE");    
        return true;
    }
    
    
    
    /**
     * 对LMRiskSort进行判断
     * 7.理赔保额冲减
     * 8.理赔医疗类附加险单独理算
     * 9.附加险退未满期保费
     * 10.主险终止附加险必须终止
     * 11.长期附加险应退现金价值
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

                        
        GlobalInput tG = new GlobalInput();
        tG.Operator="001";
        tG.ManageCom = "86000000";
        tG.ComCode = "86110000";
        
        
        String tAccNo = "80000005570";
        String tClmNo = "90000001424";
        String tAccDate = "2005-09-30";
        String tCusNo = "0000497310";
        TransferData tTransferData = new TransferData(); 
        tTransferData.setNameAndValue("AccNo",tAccNo);
        tTransferData.setNameAndValue("ClmNo",tClmNo);
        tTransferData.setNameAndValue("AccDate",tAccDate);
        tTransferData.setNameAndValue("CusNo",tCusNo);
        
        LLCaseSchema tLLCaseSchema = new LLCaseSchema();
        tLLCaseSchema.setCaseNo(tClmNo);
        tLLCaseSchema.setRgtNo(tClmNo);
        tLLCaseSchema.setCustomerNo(tCusNo);
                
        
        VData tVData = new VData();           
        tVData.addElement(tG);
        tVData.addElement(tTransferData);
        tVData.addElement(tLLCaseSchema);

        
        LLClaimCalAutoBL tClaimCalBL = new LLClaimCalAutoBL();
        tClaimCalBL.submitData(tVData, "");
        
        //检录一下
        //CaseCheckBL ccb = new CaseCheckBL();
    }
}
    
 
