package com.sinosoft.lis.finfee.finpay;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public interface LJTempIF {
    public CErrors mErrors = new CErrors();
    public boolean submitData(VData cInputData, String cOperate);
    public CErrors getCErrors();
}
