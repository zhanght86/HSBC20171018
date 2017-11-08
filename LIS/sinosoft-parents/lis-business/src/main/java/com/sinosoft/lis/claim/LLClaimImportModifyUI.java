package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;


import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;



/**
 * <p>Title: 重要信息修改</p>
 * <p>Description: 重要信息修改提交类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: sinosoft</p>
 * @author quyang
 * @version 1.0
 */


public class LLClaimImportModifyUI implements BusinessService
{
private static Logger logger = Logger.getLogger(LLClaimImportModifyUI.class);


    /** 传入数据的容器 */
    private VData mInputData = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    /** 错误处理类 */
    public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

    public LLClaimImportModifyUI()
    {}

    public static void main(String[] args)
    {
     /*   CErrors tError = null;
        String FlagStr = "Fail";
        String Content = "";

        boolean flag = true;
        GlobalInput tG = new GlobalInput();
        tG.Operator = "001";
        tG.ManageCom = "86110000";
        tG.ComCode = "86110000";

        //接收信息
        LLCUWBatchSet tLLCUWBatchSet = new LLCUWBatchSet();
        LLCUWBatchSchema tLLCUWBatchSchema = new LLCUWBatchSchema();

        tLLCUWBatchSchema.setCaseNo("90000000690");
        tLLCUWBatchSchema.setContNo("230110000000517");
        tLLCUWBatchSchema.setInsuredNo("0000497310");

        tLLCUWBatchSchema.setAppntName("理赔测试一");
        tLLCUWBatchSet.add(tLLCUWBatchSchema);


        // 准备传输数据 VData
        VData tVData = new VData();

        tVData.add(tLLCUWBatchSet);

        tVData.add(tG);

        // 数据传输
        LLClaimImportModifyUI tLLClaimImportModifyUI = new LLClaimImportModifyUI();

        if (!tLLClaimImportModifyUI.submitData(tVData, ""))
        {

            int n = tLLClaimImportModifyUI.mErrors.getErrorCount();
            Content = " 发起二核失败，原因是: " +
                      tLLClaimImportModifyUI.mErrors.getError(0).errorMessage;
            FlagStr = "Fail";
        }*/

    }


    // @Method
    /**
     * 数据提交方法
     * @param: cInputData 传入的数据
     * cOperate 数据操作字符串
     * @return: boolean
     **/

    public boolean submitData(VData cInputData, String cOperate)
    {
        // 数据操作字符串拷贝到本类中

        this.mOperate = cOperate;

        LLClaimImportModifyBL tLLClaimImportModifyBL = new LLClaimImportModifyBL();

        logger.debug("----UI BEGIN---");
        if (tLLClaimImportModifyBL.submitData(cInputData, mOperate) == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLClaimImportModifyBL.mErrors);
            return false;
        }
        else 
        {
			mResult = tLLClaimImportModifyBL.getResult();
		}
        
		logger.debug("----------UI---submitData--数据提交成功----------");
		mInputData = null;
		return true;
    }
    
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
