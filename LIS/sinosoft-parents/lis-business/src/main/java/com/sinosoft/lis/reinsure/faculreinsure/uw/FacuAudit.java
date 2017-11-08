

package com.sinosoft.lis.reinsure.faculreinsure.uw;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @zhangbin
 * @version 1.0
 */
public interface FacuAudit {

	public boolean submitData(VData cInputData, String cOperate);

	public boolean getResult();

	public CErrors getCErrors();
}
