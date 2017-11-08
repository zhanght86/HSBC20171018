package com.sinosoft.lis.f1print;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public interface PrintService {
	public boolean submitData(VData cInputData, String cOperate);

	public VData getResult();

	public CErrors getErrors();
}
