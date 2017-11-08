

package com.sinosoft.lis.reinsure.impdata.implclass;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @zhangbin
 * @version 1.0
 */

public interface RIDataImport {
    public CErrors mErrors = new CErrors();
    public boolean submitData(VData cInputData, String cOperate);
    public CErrors getCErrors();
}
