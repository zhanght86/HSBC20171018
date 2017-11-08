package com.sinosoft.lis.bq;
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

public interface EdorConfirm {
	public CErrors mErrors = new CErrors();

	public boolean submitData(VData cInputData, String cOperate);

	public VData getResult();
	// public TransferData getReturnTransferData();
	// public CErrors getErrors();
}
