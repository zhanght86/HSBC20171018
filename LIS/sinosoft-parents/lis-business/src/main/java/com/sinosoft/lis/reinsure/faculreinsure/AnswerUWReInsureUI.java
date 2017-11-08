

package com.sinosoft.lis.reinsure.faculreinsure;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: AgentSystem</p>
 *
 * <p>Description: 新契约</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Sinosoft</p>
 *
 * @author
 * @version 1.0
 */
public class AnswerUWReInsureUI {
    public AnswerUWReInsureUI() {
    }

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
        AnswerUWReInsureBL tAnswerUWReInsureBL = new AnswerUWReInsureBL();
        if (!tAnswerUWReInsureBL.submitData(cInputData, cOperate)) {
            this.mErrors.copyAllErrors(tAnswerUWReInsureBL.mErrors);
            return false;
        }
        return true;
    }
}
