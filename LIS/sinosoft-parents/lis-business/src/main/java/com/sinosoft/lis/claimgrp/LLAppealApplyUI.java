/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.claimgrp.LLAppealApplyBL;
import com.sinosoft.lis.schema.LLClaimUWMainSchema;
import com.sinosoft.lis.pubfun.GlobalInput;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 申请申诉提交信息类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author: zl
 * @version 1.0
 */
public class LLAppealApplyUI
{
private static Logger logger = Logger.getLogger(LLAppealApplyUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    public LLAppealApplyUI()
    {
    }

    public static void main(String[] args)
    {

        GlobalInput tGI = new GlobalInput();
        LLAppealApplyUI tLLAppealApplyUI = new LLAppealApplyUI();

        tGI.ManageCom="86";
        tGI.Operator="001";

        //打包数据
        TransferData mTransferData = new TransferData();
        mTransferData.setNameAndValue("ClaimNo", "90000006020");

       //准备传输数据 VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(mTransferData);

        tLLAppealApplyUI.submitData(tVData,"");
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

        LLAppealApplyBL tLLAppealApplyBL = new LLAppealApplyBL();

        logger.debug("----------UI BEGIN----------");
        if (tLLAppealApplyBL.submitData(mInputData,mOperate) == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLAppealApplyBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLAppealApplyUI";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors .addOneError(tError) ;
            mResult.clear();
            return false;
        }
        else
        {
            mResult = tLLAppealApplyBL.getResult();
        }
        mInputData = null;
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

}
