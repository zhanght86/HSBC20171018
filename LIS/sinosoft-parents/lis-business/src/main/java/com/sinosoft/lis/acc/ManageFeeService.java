package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
/**
 * <p>Title: </p>
 *
 * <p>Description:管理费接口 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company:sinosoft </p>
 *
 * @author:ck
 * @version 1.0
 */
public interface ManageFeeService {
    public boolean submitData(VData cInputData, String cOperate);

    public double getfeeValue();

    public CErrors getErrors();
}
