package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LLClaimDetailSchema;
import com.sinosoft.lis.schema.LLExemptSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
public class LLGrpClaimEdorTypeCAUI implements BusinessService
{
private static Logger logger = Logger.getLogger(LLGrpClaimEdorTypeCAUI.class);


	/**
	 * @param args
	 */
	

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    public LLGrpClaimEdorTypeCAUI()
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

        LLGrpClaimEdorTypeCABL tLLGrpClaimEdorTypeCABL = new LLGrpClaimEdorTypeCABL();

        logger.debug("----------UI BEGIN----------");
        if (!tLLGrpClaimEdorTypeCABL.submitData(mInputData,mOperate))
        {
                // @@错误处理
            this.mErrors.copyAllErrors(tLLGrpClaimEdorTypeCABL.mErrors);
            CError.buildErr(this, "数据提交失败");
            mResult.clear();
            return false;
        }
        else
        {
            mResult = tLLGrpClaimEdorTypeCABL.getResult();
        }
        mInputData = null;
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }


	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}



	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
