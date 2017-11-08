

package com.sinosoft.lis.reinsure.calculate.manage;

import com.sinosoft.utility.VData;
import com.sinosoft.utility.CErrors;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @zhangbin
 * @version 1.0
 */
public interface RICalMan {
	public CErrors mErrors = new CErrors();

	public boolean submitData(VData cInputData, String cOperate);
}
