

package com.sinosoft.lis.reinsure.calculate.param;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft</p>
 * @zhangbin
 * @version 1.0
 */
public interface RIInterParam {
    public CErrors mErrors = new CErrors();
    public boolean dealData();
    public VData getValue();
    public CErrors getCErrors();
}
