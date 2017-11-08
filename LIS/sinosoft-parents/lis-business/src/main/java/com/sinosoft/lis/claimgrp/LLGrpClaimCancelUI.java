/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LLCaseSchema;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.schema.LLAccidentSchema;
import com.sinosoft.lis.schema.LLAppClaimReasonSchema;
import com.sinosoft.lis.vschema.LLReportReasonSet;
import com.sinosoft.lis.vschema.LLAppClaimReasonSet;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description:提交理赔立案结论信息类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author
 * @version 1.0
 */
public class LLGrpClaimCancelUI
{
private static Logger logger = Logger.getLogger(LLGrpClaimCancelUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    public LLGrpClaimCancelUI()
    {
    }

    public static void main(String[] args)
    {
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

        LLGrpClaimCancelBL tLLGrpClaimCancelBL = new LLGrpClaimCancelBL();

        logger.debug("----------UI BEGIN----------");
        if (tLLGrpClaimCancelBL.submitData(mInputData,mOperate) == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLGrpClaimCancelBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimRegisterDealUI";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors .addOneError(tError) ;
            mResult.clear();
            return false;
        }
        else
        {
            mResult = tLLGrpClaimCancelBL.getResult();
        }
        mInputData = null;
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

}
