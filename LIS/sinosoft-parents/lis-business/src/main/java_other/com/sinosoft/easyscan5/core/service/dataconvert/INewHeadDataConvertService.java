package com.sinosoft.easyscan5.core.service.dataconvert;

import java.util.Map;

public interface INewHeadDataConvertService {
	/**
	 * 利用STAX解析报文头部信息
	 * @param strXML
	 * @param map
	 * @throws Exception
	 */
	public void xmlToHeadMap(String strXML, Map<String, String> map) throws Exception;	
	
}
