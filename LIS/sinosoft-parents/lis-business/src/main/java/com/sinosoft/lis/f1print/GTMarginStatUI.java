package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

public class GTMarginStatUI {
private static Logger logger = Logger.getLogger(GTMarginStatUI.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors=new CErrors();
    private VData mResult = new VData();

    /** 全局数据 */
    private GlobalInput mGlobalInput =new GlobalInput() ;

    public GTMarginStatUI() { }

    /**
     * 传输数据的公共方法
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        try
        {
            if( !cOperate.equals("PRINT"))
            {
                buildError("submitData", "不支持的操作字符串");
                return false;
            }

            // 得到外部传入的数据，将数据备份到本类中
            if( !getInputData(cInputData) ) {
                return false;
            }

            // 进行业务处理
            if( !dealData() ) {
                return false;
            }

            GTMarginStatBL tGTMarginStatBL = new GTMarginStatBL();
            logger.debug("Start GTMarginStatBL Submit ...");
            if( !tGTMarginStatBL.submitData(cInputData, cOperate) )
            {
                if( tGTMarginStatBL.mErrors.needDealError() )
                {
                    mErrors.copyAllErrors(tGTMarginStatBL.mErrors);
                    return false;
                }
                else
                {
                    buildError("submitData", "GTMarginStatBL发生错误，但是没有提供详细的出错信息");
                    return false;
                }
            }
            else
            {
                mResult = tGTMarginStatBL.getResult();
                return true;
            }
        }catch(Exception e)
        {
            e.printStackTrace();
            CError cError = new CError( );
            cError.moduleName = "GTMarginStatUI";
            cError.functionName = "submitData";
            cError.errorMessage = e.toString();
            mErrors.addOneError(cError);
            return false;
        }
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
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput",0));
        TransferData tTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData",0);
        if (tTransferData == null || mGlobalInput == null)
        {
            buildError("getInputData", "传入后台的参数缺少！");
            return false;
        }

        String tStartDate = (String)tTransferData.getValueByName("StartDate");
        String tEndDate = (String)tTransferData.getValueByName("EndDate");
        String tManageCom = (String)tTransferData.getValueByName("ManageCom");
        String tStatType = (String)tTransferData.getValueByName("StatType");
        if(tStartDate.equals("") || tEndDate.equals("") || tManageCom.equals("") || tStatType.equals(""))
        {
            buildError("getInputData", "没有得到足够的查询信息！");
            return false;
        }

        return true;
    }

    public VData getResult() {
        return this.mResult;
    }

    private void buildError(String szFunc, String szErrMsg){
        CError cError = new CError( );
        cError.moduleName = "GTMarginStatUI";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }
}
