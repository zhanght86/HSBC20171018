package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import java.text.*;
import java.util.*;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;
import com.sinosoft.lis.claimgrp.SynLCLBPolBL;
import com.sinosoft.lis.claimgrp.SynLCLBDutyBL;
import com.sinosoft.lis.claimgrp.SynLCLBGetBL;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 计算步骤入口：整个理赔计算的入口BL</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author 续涛，2005.05.24--2005.05.24
 * @version 1.0
 */
public class LLClaimCalPortalBL implements BusinessService
{
private static Logger logger = Logger.getLogger(LLClaimCalPortalBL.class);



    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private String mOperate;                        /** 数据操作字符串 */
    private MMap mMMap = new MMap();

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    private GlobalInput mGlobalInput = new GlobalInput();    /** 全局数据 */
    private TransferData mTransferData = new TransferData();
    //private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();
    
    // 理赔公用方法类
    private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
    private ExeSQL mExeSQL = new ExeSQL();

    private String mAccNo    = "";     //事件号
    private String mAccDate  = "";     //意外事故发生日期
    private String mClmNo    = "";     //赔案号
    private String mCusNo    = "";     //客户号
    private String mContType = "";     //总单类型,1-个人总投保单,2-集体总单
    private String mClmState = "";     //赔案状态，20立案，30审核
    private String mAccFlag = "";      //账户案件对于公用账户判断

 

     public LLClaimCalPortalBL(){}

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *          cOperate 数据操作
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate)
    {

        logger.debug("----------理算入口-----LLClaimCalPortal测试-----开始----------");

        //得到外部传入的数据,将数据备份到本类中
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

        logger.debug("----------理算入口-----LLClaimCalPortal测试-----结束----------");
        return true;
    }


    /**
     * 取传入参数信息
     * @param cInputData VData
     * @return boolean
     */
    private boolean getInputData(VData cInputData,String cOperate)
    {
        this.mInputData = cInputData;

        mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);

        mTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);
        this.mAccNo   = (String) mTransferData.getValueByName("AccNo");     //事件号
        this.mClmNo   = (String) mTransferData.getValueByName("ClmNo");     //赔案号
        this.mAccDate = (String) mTransferData.getValueByName("AccDate");   //意外事故发生日期
        this.mContType= (String) mTransferData.getValueByName("ContType");  //总单类型,1-个人总投保单,2-集体总单

        this.mClmState= (String) mTransferData.getValueByName("ClmState");  //赔案状态，20立案，30审核


        return true;
    }

    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData()
    {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 计算步骤一：理赔计算前的数据校验
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
//        CError.buildErr(this,"保单帐户查询失败!" + "保单号：");
//        return false;

        VData tResult = new VData();
        //VData cInputData = (VData)mInputData.clone();

        LLClaimCalCheckBL tLLClaimCalCheckBL = new LLClaimCalCheckBL();
        if (!tLLClaimCalCheckBL.submitData(mInputData, ""))
        {

            int n = tLLClaimCalCheckBL.mErrors.getErrorCount();
            String tContent = "";

            for (int i = 0; i < n; i++)
            {
                tContent = tContent + tLLClaimCalCheckBL.mErrors.getError(i).errorMessage;

            }
            this.mErrors.copyAllErrors(tLLClaimCalCheckBL.mErrors);
            logger.debug("-----理算步骤一-----数据校验-----LLClaimCalCheckBL测试错误提示信息-----" + tContent);
            return false;
        }

        this.mErrors.copyAllErrors(tLLClaimCalCheckBL.mErrors);

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 计算步骤二：找出匹配保项
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LLClaimCalMatchBL tLLClaimCalMatchBL = new LLClaimCalMatchBL();
        if (!tLLClaimCalMatchBL.submitData(mInputData, ""))
        {
            int n = tLLClaimCalCheckBL.mErrors.getErrorCount();
            String tContent = "";

            for (int i = 0; i < n; i++)
            {
                tContent = tContent + tLLClaimCalMatchBL.mErrors.getError(i).errorMessage;

            }
            this.mErrors.copyAllErrors(tLLClaimCalMatchBL.mErrors);
            logger.debug("-----理算步骤二-----匹配保项-----LLClaimCalMatchBL测试错误提示信息-----" + tContent);
            return false;
        }
        tResult = tLLClaimCalMatchBL.getResult();
        this.mErrors.copyAllErrors(tLLClaimCalMatchBL.mErrors);

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 计算步骤三：对匹配后的保项和费用进行过滤，并提交到数据库
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

        LLClaimCalMatchFilterBL tLLClaimCalMatchFilterBL = new LLClaimCalMatchFilterBL();
        if (tLLClaimCalMatchFilterBL.submitData(tResult,"") == false)
        {
            int n = tLLClaimCalMatchFilterBL.mErrors.getErrorCount();
            String tContent = "";

            for (int i = 0; i < n; i++)
            {
                tContent = tContent + tLLClaimCalMatchFilterBL.mErrors.getError(i).errorMessage;

            }
            this.mErrors.copyAllErrors(tLLClaimCalMatchFilterBL.mErrors);
            logger.debug("-----理算步骤三-----过滤匹配保项并保存-----LLClaimCalMatchFilterBL测试错误提示信息-----" + tContent);
            return false;
        }
        this.mErrors.copyAllErrors(tLLClaimCalMatchFilterBL.mErrors);
        

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
           * No4.0 计算步骤四：进行免赔计算
           * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
        */

        LLClaimCalFranchiseBL tLLClaimCalFranchiseBL = new LLClaimCalFranchiseBL();
        if (tLLClaimCalFranchiseBL.submitData(mInputData,"") == false)
        {
               int n = tLLClaimCalFranchiseBL.mErrors.getErrorCount();
             String tContent = "";

             for (int i = 0; i < n; i++)
             {
                  tContent = tContent + tLLClaimCalFranchiseBL.mErrors.getError(i).errorMessage;

             }
             this.mErrors.copyAllErrors(tLLClaimCalFranchiseBL.mErrors);
             logger.debug("-----理算步骤四-----免赔额计算-----LLClaimCalFranchiseBL测试错误提示信息-----" + tContent);
             return false;
        }
        this.mErrors.copyAllErrors(tLLClaimCalFranchiseBL.mErrors);

        
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
           * No5.0 计算步骤五：理赔正式计算
           * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
           */

        LLClaimCalAutoBL tLLClaimCalAutoBL = new LLClaimCalAutoBL();
        if (tLLClaimCalAutoBL.submitData(mInputData,"") == false)
        {
           int n = tLLClaimCalAutoBL.mErrors.getErrorCount();
           String tContent = "";

           for (int i = 0; i < n; i++)
           {
              tContent = tContent + tLLClaimCalAutoBL.mErrors.getError(i).errorMessage;

           }
           this.mErrors.copyAllErrors(tLLClaimCalAutoBL.mErrors);
           logger.debug("-----理算步骤五-----理赔正式计算-----LLClaimCalAutoBL测试错误提示信息-----" + tContent);
           return false;
        }
        this.mErrors.copyAllErrors(tLLClaimCalAutoBL.mErrors);

        tResult = tLLClaimCalAutoBL.getResult();
        TransferData tTransferData = new TransferData();
        tTransferData = (TransferData)tResult.getObjectByObjectName("TransferData", 0);
        String tReturn = (String) tTransferData.getValueByName("Return");

        
        //返回TRUE说明有计算结果，可以进行下一步操作
        //String
        if (tReturn.equals("TRUE"))
        {
            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No6.0 计算步骤六：理算后续加减保计算
             * 目前不需要这个处理 2003-03-27 P.D 注释
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
//            LLClaimCalAutoAfterBL tLLClaimAutoAfterBL = new LLClaimCalAutoAfterBL();
//            if (tLLClaimAutoAfterBL.submitData(mInputData,"") == false)
//            {
//                int n = tLLClaimAutoAfterBL.mErrors.getErrorCount();
//                String tContent = "";
//
//                for (int i = 0; i < n; i++)
//                {
//                    tContent = tContent + tLLClaimAutoAfterBL.mErrors.getError(i).errorMessage;
//
//                }
//                this.mErrors.copyAllErrors(tLLClaimAutoAfterBL.mErrors);
//                logger.debug("-----理算步骤六-----理算后续加减保计算-----LLClaimCalAutoAfterBL测试错误提示信息-----" + tContent);
//                return false;
//            }
//            this.mErrors.copyAllErrors(tLLClaimAutoAfterBL.mErrors);


            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No6.0 计算步骤七：计算理赔类型赔付
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            LLClaimCalDutyKindBL tLLClaimCalDutyKindBL = new LLClaimCalDutyKindBL();
            if (tLLClaimCalDutyKindBL.submitData(mInputData,"") == false)
            {
                int n = tLLClaimCalDutyKindBL.mErrors.getErrorCount();
                String tContent = "";

                for (int i = 0; i < n; i++)
                {
                    tContent = tContent + tLLClaimCalDutyKindBL.mErrors.getError(i).errorMessage;

                }
                this.mErrors.copyAllErrors(tLLClaimCalDutyKindBL.mErrors);
                logger.debug("-----理算步骤七-----计算理赔类型的赔付-----LLClaimCalDutyKindBL测试错误提示信息-----" + tContent);
                return false;
            }
            this.mErrors.copyAllErrors(tLLClaimCalDutyKindBL.mErrors);

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No7.0 计算步骤八：产生赔付应付数据
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */

            LLClaimCalShouldPayBL tLLClaimCalShouldPayBL = new LLClaimCalShouldPayBL();
            if (tLLClaimCalShouldPayBL.submitData(mInputData,"") == false)
            {
                int n = tLLClaimCalShouldPayBL.mErrors.getErrorCount();
                String tContent = "";

                for (int i = 0; i < n; i++)
                {
                    tContent = tContent + tLLClaimCalShouldPayBL.mErrors.getError(i).errorMessage;

                }
                this.mErrors.copyAllErrors(tLLClaimCalShouldPayBL.mErrors);
                logger.debug("-----理算步骤八-----产生赔付应付数据-----LLClaimCalShouldPayBL测试错误提示信息-----" + tContent);
                return false;
            }
            this.mErrors.copyAllErrors(tLLClaimCalShouldPayBL.mErrors);

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No99.0 计算步骤最终：产生赔付应付数据
             * 泰康没有 预付金额 保单结算金额 合同处理金额
             * 2003-03-27 P.D 注释
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */

//            LLClaimCalFinalBL tLLClaimCalFinalBL = new LLClaimCalFinalBL();
//            if (tLLClaimCalFinalBL.submitData(mInputData,"") == false)
//            {
//                int n = tLLClaimCalFinalBL.mErrors.getErrorCount();
//                String tContent = "";
//
//                for (int i = 0; i < n; i++)
//                {
//                    tContent = tContent + tLLClaimCalFinalBL.mErrors.getError(i).errorMessage;
//
//                }
//                this.mErrors.copyAllErrors(tLLClaimCalFinalBL.mErrors);
//                logger.debug("-----理算步骤最终-----产生赔付汇总数据-----LLClaimCalFinalBL测试错误提示信息-----" + tContent);
//                return false;
//            }
//            this.mErrors.copyAllErrors(tLLClaimCalFinalBL.mErrors);

        }
        else
        {

            String strSQL1 = "delete from LLClaimDutyKind where ClmNo='" + this.mClmNo + "'";
            String strSQL2 = "delete from LLBalance where ClmNo='" + this.mClmNo + "' and FeeOperationType = 'A'";
            String strSQL3 = "delete from LLClaim where ClmNo='" +this.mClmNo+ "'";

            mMMap.put(strSQL1, "DELETE");
            mMMap.put(strSQL2, "DELETE");
            mMMap.put(strSQL3, "DELETE");

        }

        logger.debug("--------------------------理算结束系统信息打印---------------------------");
        int n = this.mErrors.getErrorCount();
        for (int i = 0; i < n; i++)
        {
            String Content = "";
            Content = Content + "提示信息: " + this.mErrors.getError(i).errorMessage;
            logger.debug(Content);
        }

        return true;
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
            tError.moduleName = "LLClaimCalCheckBL";
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
        String tClmNo = "05010000000001";


        String tSql = "select * from LLAccident where AccNo in "
            +" (select CaseRelaNo from LLCaseRela where CaseNo = '"+tClmNo+"')";

        logger.debug("SQL："+tSql);
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


        LLClaimCalPortalBL tLLClaimCalPortalBL = new LLClaimCalPortalBL();
        tLLClaimCalPortalBL.submitData(tVData, "MATCH||INSERT");
        int n = tLLClaimCalPortalBL.mErrors.getErrorCount();


//        String tDealState = "D001".substring(0,1);

//      int tYear = PubFun.calInterval("2007-05-12","2006-05-14", "D");
//
//      String tSNewDate = PubFun.calDate("2004-02-03",tYear,"Y","2004-02-03");
//      logger.debug(tYear);
//      int tNewYear = PubFun.calInterval("1990-01-01",tSNewDate, "Y");
//      logger.debug(tDealState);
//
//
//
//      String tClaimFeeCode = "123456789ABC";
//      String tClaimFeeCode = "20081234  ";
//      tClaimFeeCode = tClaimFeeCode.substring(0,1);
//      logger.debug(tClaimFeeCode.length());
//        double t1= 0.011;
//        double t2= 0.15;
//        double t3= 0;
//        t3 = t1 + t2;
////        t3 = Arith.div(t2,t1);
//        logger.debug(t3);
//      LLClaimUWDetailSchema tLLClaimUWDetailSchema = new LLClaimUWDetailSchema ();
//        ES_InssueDocSchema tES_InsueDocSchema = new ES_InssueDocSchema();

//        LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
//        tLLRegisterSchema.getFeeInputFlag();

    }

	public CErrors getErrors() {
		// TODO 自动生成方法存根
		return null;
	}


}
