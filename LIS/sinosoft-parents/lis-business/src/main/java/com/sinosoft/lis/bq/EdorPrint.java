package com.sinosoft.lis.bq;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author lizhuo
 * @version 1.0
 */
public interface EdorPrint {
	public CErrors mErrors = new CErrors();

	public boolean submitData(VData cInputData, String cOperate);

	public VData getResult();
}
