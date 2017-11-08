/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:发起呈报提交信息类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author: zl
 * @version 1.0
 */
public class LLSubmitApplyUI
{
private static Logger logger = Logger.getLogger(LLSubmitApplyUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    public LLSubmitApplyUI()
    {
    }

    public static void main(String[] args)
    {
//
//        LLSubmitApplySchema tLLSubmitApplySchema = new LLSubmitApplySchema(); //案件核赔表
//
//        GlobalInput tGI = new GlobalInput();
//        LLSubmitApplyUI tLLSubmitApplyUI = new LLSubmitApplyUI();
//
//        String transact = "INSERT";//获取操作insert||update
//        tGI.ManageCom="86";
//        tGI.Operator="001";
//
//        //获取报案页面信息
//        tLLSubmitApplySchema.setClmNo("90000000130");//赔案号
//        tLLSubmitApplySchema.setInqNo("90000000130");//调查序号
//        tLLSubmitApplySchema.setBatNo("90000000130");//调查批次号
//        tLLSubmitApplySchema.setCustomerNo("1");//出险人客户号
//        tLLSubmitApplySchema.setCustomerName("1");//出险人名称
//
//        VData tVData = new VData();
//        tVData.add(tGI);
//        tVData.add(tLLSubmitApplySchema);
//
//        tLLSubmitApplyUI.submitData(tVData,transact);
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

        LLSubmitApplyBL tLLSubmitApplyBL = new LLSubmitApplyBL();

        logger.debug("----------UI BEGIN----------");
        if (tLLSubmitApplyBL.submitData(mInputData,mOperate) == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLSubmitApplyBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLSubmitApplyUI";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors .addOneError(tError) ;
            mResult.clear();
            return false;
        }
        else
        {
            mResult = tLLSubmitApplyBL.getResult();
        }
        mInputData = null;
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

}
