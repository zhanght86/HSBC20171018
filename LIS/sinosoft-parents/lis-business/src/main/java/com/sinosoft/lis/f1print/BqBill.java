package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: 保全清单接口类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author liurx
 * @version 1.0
 */

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public interface BqBill {
	public boolean submitData(VData cInputData, String cOperate);

	public VData getResult();

	public CErrors getErrors();
}
