

package com.sinosoft.lis.reinsure;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.PubFun;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft</p>
 * @zhangbin
 * @version 1.0
 */
public class LRPreceptUI {
    public LRPreceptUI() {
    }
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();
    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     * cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate) {
        if (!getInputData(cInputData))
            return false;
        if (!dealData()) {
            return false;
        }
        LRPreceptBL tLRPreceptBL = new LRPreceptBL();
        if (!tLRPreceptBL.submitData(cInputData, cOperate))
        {
            if (tLRPreceptBL.mErrors.needDealError())
            {
                this.mErrors.copyAllErrors(tLRPreceptBL.mErrors);
            }
            else
            {
                buildError("submitData", "发生错误，但是没有提供详细信息！");
            }
            return false;
        }
        System.out.println(" cccccccc: 2");
        mResult = tLRPreceptBL.getResult();
        System.out.println(" cccccccc: 3 "+mResult);
        //准备往后台的数据
        if (!prepareOutputData())
            return false;

        return true;
    }

    private boolean dealData() {
        return true;
    }

    private boolean getInputData(VData cInputData) {

        return true;
    }

    private boolean prepareOutputData() {
        return true;
    }

    public VData getResult() {
        return mResult;
    }

    private void buildError(String szFunc, String szErrMsg) {
        CError cError = new CError();
        cError.moduleName = "CalItemDeal";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
        System.out.print(szErrMsg);
    }

    public static void main(String[] args) {
        LRPreceptUI tLRPreceptUI = new LRPreceptUI();
        VData tVData = new VData();
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput.ManageCom="86";
        tGlobalInput.Operator = "001";
        tGlobalInput.ComCode = "86";
    }

}
