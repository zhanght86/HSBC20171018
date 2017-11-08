/*
 * <p>ClassName: ReComManageUI </p>
 * <p>Description: ReComManageUI类文件 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: sinosoft </p>
 * @Database: Zhang Bin
 * @CreateDate：2009-11-14
 */
package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import java.util.Hashtable;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;

public class FinFeeUI   implements BusinessService
{
private static Logger logger = Logger.getLogger(FinFeeUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 数据操作字符串 */
    private String strOperate;

    //业务处理相关变量
    /** 全局数据 */

    public FinFeeUI()
    {
    }

    public boolean submitData(VData cInputData, String cOperate)
    {
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
            return false;

        //进行业务处理
        if (!dealData())
            return false;

        //准备往后台的数据
        if (!prepareOutputData())
            return false;

        FinFeeBL tFinFeeBL = new FinFeeBL();
        if (!tFinFeeBL.submitData(cInputData, cOperate))
        {
            if (tFinFeeBL.mErrors.needDealError())
            {
                this.mErrors.copyAllErrors(tFinFeeBL.mErrors);
            }
            else
            {
                buildError("submitData", "tCardPlanBL发生错误，但是没有提供详细信息！");
            }
            return false;
        }
        return true;
    }

    public static void main(String[] args)
    {
        GlobalInput globalInput = new GlobalInput();
        globalInput.ComCode = "8611";
        globalInput.Operator = "001";
        // 准备传输数据 VData
        VData vData = new VData();
        try
        {
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
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
        TransferData tTransferData = (TransferData)mResult.getObjectByObjectName("TransferData", 0);
        String tFinFeeNo = (String)tTransferData.getValueByName("FinFeeNo");

        return this.mResult;
    }
  
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
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
}
