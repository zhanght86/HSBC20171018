package com.sinosoft.easyscan5.base.service;

import com.sinosoft.easyscan5.util.ServiceError;
import com.sinosoft.utility.VData;

public interface BaseService {
	/**
	 * service
	 * @param cInputData
	 * @param cOperate 
	 * @return
	 * @throws Exception 
	 */
	public boolean submitData(VData cInputData, String cOperate) throws Exception;
	/**
	 * 获取结果
	 * @return
	 */
	public VData getResult();
	/**
	 * 生成错误信息
	 * @param moduleName
	 * @param methodName
	 * @param errorMessage
	 */
	public void buildError(String moduleName, String methodName,
			String errorMessage);
	/**
	 * 获取错误信息
	 * @return
	 */
	public ServiceError getErrors();
}