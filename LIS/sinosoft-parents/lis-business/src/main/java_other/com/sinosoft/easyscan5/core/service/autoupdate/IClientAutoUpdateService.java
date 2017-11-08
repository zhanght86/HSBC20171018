package com.sinosoft.easyscan5.core.service.autoupdate;

import com.sinosoft.utility.VData;




public interface IClientAutoUpdateService {
	public VData getAutoUpdate(String Operator, String manageCom,
			String version, String path);

	public VData getUpdateResultInfo(String Operator, String manageCom,
			String version, String errorInfo);

}
