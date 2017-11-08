

/**
 * LRBsnsBillUI.java
 * com.sinosoft.lis.reinsure
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 Nov 4, 2010 		曹淑国
 *
 * Copyright (c) 2010, TNT All Rights Reserved.
*/

package com.sinosoft.lis.reinsure;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * ClassName:LRBsnsBillUI
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   曹淑国
 * @version  
 *
 */
public class LRBsnsBillUWUI implements BusinessService{
	
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    private VData mResult = new VData();
    
    private String result = "";
    
    private TransferData tTransferdata = new TransferData();
    
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();

    /** 数据操作字符串 */
    private String strOperate;
    
    //业务处理相关变量
    /** 全局数据 */

    public LRBsnsBillUWUI()
    {
    }
    
    
    public boolean submitData(VData cInputData, String cOperate)
    {
        System.out.println("come in UI........");
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
            return false;

        //进行业务处理
        if (!dealData())
            return false;

        //准备往后台的数据
        if (!prepareOutputData())
            return false;
        System.out.println("..UI..");
        LRBsnsBillUWBL tLRBsnsBillUWBL = new LRBsnsBillUWBL();
        if (!tLRBsnsBillUWBL.submitData(cInputData, cOperate))
        {
            if (tLRBsnsBillUWBL.mErrors.needDealError())
            {
                this.mErrors.copyAllErrors(tLRBsnsBillUWBL.mErrors);
            }
            else
            {
                buildError("submitData", "tCardPlanBL发生错误，但是没有提供详细信息！");
            }
            return false;
        }
        result = tLRBsnsBillUWBL.getResult();
        return true;   	    	
    }

    /**
     * 准备往后层输出所需要的数据
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     */
    private boolean prepareOutputData()
    {
        return true;
    }


    /**
     * 根据前面的输入数据，进行UI逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
    {
        return true;
    }
    
    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
        return true;
    }

    public VData getResult()
    {
    	tTransferdata.setNameAndValue("String", result);
    	mResult.add(tTransferdata);
    	return mResult;
    }    
    
    /*
     * add by kevin, 2002-10-14
     */
    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError();

        cError.moduleName = "tReContManageBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }    
    
	/**
	 * main:(这里用一句话描述这个方法的作用)
	 * @param  @param args    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */

	public static void main(String[] args) {

		// TODO Auto-generated method stub

	}


	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
