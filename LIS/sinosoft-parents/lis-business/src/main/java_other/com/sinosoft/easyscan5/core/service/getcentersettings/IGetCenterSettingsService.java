package com.sinosoft.easyscan5.core.service.getcentersettings;

import java.util.Map;

import com.sinosoft.easyscan5.base.service.BaseService;


public interface IGetCenterSettingsService extends BaseService {

	/**
	 * 获取中心配置信息
	 * @param manageCom
	 * @return 返回xml格式字符串
	 */
	public String  getCenterSettings(Map<String, String> param, String channel);
	/**
	 * 更新配置信息版本
	 * @param tableName
	 */
	public void updateVersion(String tableName,String userNo);
	/**
	 * 获取中心配置信息
	 * @return
	 */
	public Map getResultMap();
	/**
	 * 获取版本号
	 * @return
	 */
	public Map getVersionMap();

}
