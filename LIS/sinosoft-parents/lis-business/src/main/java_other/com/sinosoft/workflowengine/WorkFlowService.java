package com.sinosoft.workflowengine;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public interface WorkFlowService {
	public boolean submitData(VData cInputData, String cOperate);

	public VData getResult();

	public TransferData getReturnTransferData();

	public CErrors getErrors();
}
