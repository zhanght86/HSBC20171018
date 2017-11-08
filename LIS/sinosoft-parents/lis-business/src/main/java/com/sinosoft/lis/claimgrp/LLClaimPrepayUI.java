/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.claimgrp.LLClaimPrepayBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LLPrepayDetailSchema;
import com.sinosoft.lis.schema.LLPrepayClaimSchema;
import com.sinosoft.lis.schema.LLClaimDetailSchema;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.schema.LLBalanceSchema;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:预付管理提交信息类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author: yuejw
 * @version 1.0
 */
public class LLClaimPrepayUI
{
private static Logger logger = Logger.getLogger(LLClaimPrepayUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    public LLClaimPrepayUI()
    {
    }

    public static void main(String[] args)
    {
        CErrors tError = null;
        String FlagStr = "Fail";
        String Content = "";

        GlobalInput tGI = new GlobalInput();

        tGI.Operator="001";
        LLClaimPrepayUI tLLClaimPrepayUI = new LLClaimPrepayUI();
        String transact = "Prepay||Insert";//获取操作insert||update
        tGI.ManageCom="86";

        LLPrepayDetailSchema tLLPrepayDetailSchema=new LLPrepayDetailSchema();
        tLLPrepayDetailSchema.setClmNo("90000002290");
        tLLPrepayDetailSchema.setCaseNo("90000002290");
        tLLPrepayDetailSchema.setPolNo("210110000001115");
        tLLPrepayDetailSchema.setGetDutyKind("100");
        tLLPrepayDetailSchema.setGetDutyCode("238040");
        tLLPrepayDetailSchema.setPrepayNo("1000000010");
        tLLPrepayDetailSchema.setPrepaySum(100);

        LLPrepayClaimSchema tLLPrepayClaimSchema=new LLPrepayClaimSchema();
        tLLPrepayClaimSchema.setClmNo("90000002290");
        tLLPrepayClaimSchema.setCaseNo("90000002290");
        tLLPrepayClaimSchema.setRgtNo("90000002290");
        tLLPrepayClaimSchema.setPrepaySum(100);

        LLClaimDetailSchema tLLClaimDetailSchema=new LLClaimDetailSchema();
        tLLClaimDetailSchema.setClmNo("90000002290");
        tLLClaimDetailSchema.setCaseNo("90000002290");
        tLLClaimDetailSchema.setPolNo("210110000001115");
        tLLClaimDetailSchema.setGetDutyKind("100");
        tLLClaimDetailSchema.setGetDutyCode("238040");
        tLLClaimDetailSchema.setCaseRelaNo("80000002150");
        tLLClaimDetailSchema.setPrepayFlag("1");
        tLLClaimDetailSchema.setPrepaySum(100);

        LLClaimSchema tLLClaimSchema=new LLClaimSchema();
        tLLClaimSchema.setClmNo("90000002290");

        LLBalanceSchema tLLBalanceSchema=new LLBalanceSchema();
        tLLBalanceSchema.setClmNo("90000002290");
        tLLBalanceSchema.setFeeOperationType("B");
        tLLBalanceSchema.setSubFeeOperationType("B");
        tLLBalanceSchema.setFeeFinaType("B");
        tLLBalanceSchema.setBatNo("1000000010");
        tLLBalanceSchema.setPolNo("210110000001115");
        tLLBalanceSchema.setPayFlag("0");
        tLLBalanceSchema.setPay(100);


        try
        {
            //准备传输数据 VData
            VData tVData = new VData();
            tVData.add(tGI);
            tVData.add(transact);
            tVData.add(tLLPrepayDetailSchema);
            tVData.add(tLLPrepayClaimSchema);
            tVData.add(tLLClaimDetailSchema);
            tVData.add(tLLClaimSchema);
            tVData.add(tLLBalanceSchema);

            try
            {
                if (!tLLClaimPrepayUI.submitData(tVData, transact))
                {
                    Content = "提交失败，原因是: " +
                              tLLClaimPrepayUI.mErrors.getError(0).errorMessage;
                    FlagStr = "Fail";
                }
                else
                {
                    Content = "数据提交成功";
                    FlagStr = "Succ";
                }
            }
            catch (Exception ex)
            {
                Content = "数据提交失败，原因是:" + ex.toString();
                FlagStr = "Fail";
            }
        }
        catch (Exception ex)
        {
            Content = "数据提交失败，原因是:" + ex.toString();
            FlagStr = "Fail";
        }


    }

    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData,String cOperate)
    {
        //首先将数据在本类中做一个备份
        mInputData = (VData) cInputData.clone();
        this.mOperate = cOperate;

        LLClaimPrepayBL tLLClaimPrepayBL = new LLClaimPrepayBL();

        logger.debug("----------UI BEGIN----------");
        if (!tLLClaimPrepayBL.submitData(mInputData,mOperate))
        {

                // @@错误处理
            this.mErrors.copyAllErrors(tLLClaimPrepayBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimPrepayUI";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors .addOneError(tError) ;
            mResult.clear();
            return false;
        }
        else
        {
            mResult = tLLClaimPrepayBL.getResult();
        }
        mInputData = null;
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

}
