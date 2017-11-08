package com.sinosoft.lis.f1printgrp;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lh
 * @version 1.0
 */

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public interface PrintService {
	public boolean submitData(VData cInputData, String cOperate);

	public VData getResult();

	public CErrors getErrors();
}
