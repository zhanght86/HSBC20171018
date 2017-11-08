package com.sinosoft.easyscan5.base.service.impl;

import com.sinosoft.easyscan5.base.service.BaseService;
import com.sinosoft.easyscan5.util.ServiceError;
import com.sinosoft.utility.VData;

public class BaseServiceImpl implements BaseService {
	/**输入参数 */
	protected VData inputData;
	/**输出参数 */
	protected VData outputData;
	/**操作类型*/
	protected String Operate;
	/**错误信息*/
	public ServiceError cError = new ServiceError();
	
	public void buildError(String moduleName, String methodName,
			String errorMessage) {
		cError.setModalName(moduleName);

		cError.setMethodName(methodName);

		cError.setMethodName(methodName);

		cError.setErrorMessage(errorMessage);
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean submitData(VData inputData, String operate) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public ServiceError getErrors() {
		// TODO Auto-generated method stub
		return cError;
	}

	

}
