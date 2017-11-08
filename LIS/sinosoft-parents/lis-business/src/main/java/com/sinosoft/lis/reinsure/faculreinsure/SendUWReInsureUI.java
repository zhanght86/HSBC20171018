

package com.sinosoft.lis.reinsure.faculreinsure;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Sinosoft</p>
 * @ZhangBin
 * @version 1.0
 */
public class SendUWReInsureUI {
    public SendUWReInsureUI() {
    }

    private SendUWReInsureBL mSendUWReInsureBL = new SendUWReInsureBL();

    /** 错误处理类 */
    public CErrors mErrors = new CErrors();
    /**
     * 向BL传递的接口
     *
     * @param cInputData VData
     * @param cOperate String
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate) {
        if (!mSendUWReInsureBL.submitData(cInputData, cOperate)) {
            this.mErrors.copyAllErrors(mSendUWReInsureBL.mErrors);
            return false;
        }
        return true;
    }
    public String getResult(){
        return mSendUWReInsureBL.getResult();
    }
}
