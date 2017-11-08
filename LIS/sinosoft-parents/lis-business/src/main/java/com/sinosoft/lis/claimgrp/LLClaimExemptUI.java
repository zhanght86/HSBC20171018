/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.claimgrp.LLClaimExemptBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.*;
import com.sinosoft.service.BusinessService;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:豁免处理提交信息类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author: yuejw
 * @version 1.0
 */
public class LLClaimExemptUI implements BusinessService
{
private static Logger logger = Logger.getLogger(LLClaimExemptUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    public LLClaimExemptUI()
    {
    }

    public static void main(String[] args)
    {
        CErrors tError = null;
        String FlagStr = "Fail";
        String Content = "";

        GlobalInput tGI = new GlobalInput();

        tGI.Operator="001";
        LLClaimExemptUI tLLClaimExemptUI = new LLClaimExemptUI();
        //String transact = "LLExempt||Get";
        String transact = "LLExempt||Save";
        tGI.ManageCom="86";
        LLClaimDetailSchema tLLClaimDetailSchema=new LLClaimDetailSchema();
        LCPremSchema tLCPremSchema=new LCPremSchema();
        LLExemptSchema tLLExemptSchema=new LLExemptSchema();
        if(transact.equals("LLExempt||Get"))  //[提取豁免信息]
        {
            tLLClaimDetailSchema.setClmNo("90000003740");//传入赔案号
        }

        if(transact.equals("LLExempt||Save"))  //[修改保存豁免信息]
        {
            tLLExemptSchema.setClmNo("90000003740");//传入赔案号
            tLLExemptSchema.setPolNo("210110000001652");//保单险种号码
            tLLExemptSchema.setDutyCode("141000");//责任编码
            tLLExemptSchema.setPayPlanCode("141020");//交费计划编码
        }

        try
        {
            //准备传输数据 VData
            VData tVData = new VData();
            tVData.add(tGI);
            tVData.add(transact);
            tVData.add(tLLExemptSchema);
            tVData.add(tLLClaimDetailSchema);
            try
            {
                if (!tLLClaimExemptUI.submitData(tVData, transact))
                {
                    Content = "提交失败，原因是: " +
                              tLLClaimExemptUI.mErrors.getError(0).errorMessage;
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

        LLClaimExemptBL tLLClaimExemptBL = new LLClaimExemptBL();

        logger.debug("----------UI BEGIN----------");
        if (!tLLClaimExemptBL.submitData(mInputData,mOperate))
        {
                // @@错误处理
            this.mErrors.copyAllErrors(tLLClaimExemptBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimExemptUI";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors .addOneError(tError) ;
            mResult.clear();
            return false;
        }
        else
        {
            mResult = tLLClaimExemptBL.getResult();
        }
        mInputData = null;
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
