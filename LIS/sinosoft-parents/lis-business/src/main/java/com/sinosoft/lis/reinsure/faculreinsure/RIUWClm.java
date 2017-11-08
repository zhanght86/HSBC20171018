

package com.sinosoft.lis.reinsure.faculreinsure;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public interface RIUWClm {
    public CErrors mErrors = new CErrors();
    public boolean submitData(VData cInputData, String cOperate);
    public String getResult();
    public CErrors getCErrors();
}
