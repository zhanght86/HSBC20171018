package com.sinosoft.easyscan5.core.controller.centerinfo;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sinosoft.easyscan5.base.controller.BaseAction;
import com.sinosoft.easyscan5.core.controller.clientupload.ClientUploadCommonFun;
import com.sinosoft.easyscan5.core.service.dataconvert.impl.GetCenterSettingDataConvertUtil;
import com.sinosoft.easyscan5.core.service.getcentersettings.impl.GetCenterSettingsServiceImpl;

public class GetCenterInfoAction extends BaseAction {

	private GetCenterSettingsServiceImpl getCenterSettingsService = new GetCenterSettingsServiceImpl();
	private GetCenterSettingDataConvertUtil getCenterSettingDataConvertUtil = new GetCenterSettingDataConvertUtil();
	private String IndexXML;
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 获取中心配置
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getCenterSetting(HttpServletRequest request) throws Exception {
		ClientUploadCommonFun clientUploadCommonFun = new ClientUploadCommonFun();
		StringBuffer bufXML = new StringBuffer();
		String returnStr = null;
		try {
			Map<String, String> param = new HashMap<String, String>();
			
			returnStr = getCenterSettingDataConvertUtil.xmlToMap(IndexXML, param);
			String realPath = request.getSession().getServletContext().getRealPath("/");
			if (returnStr != null) {
				return this.outString(clientUploadCommonFun.getNewReturnMessage(returnStr));
			} else {
				
				returnStr = getCenterSettingsService.getCenterSettings(param, getCenterSettingDataConvertUtil.channel);
				if (returnStr == null) {
					Map resultMap = getCenterSettingsService.getResultMap();
					Map versionMap = getCenterSettingsService.getVersionMap();
					returnStr = getCenterSettingDataConvertUtil.mapToXml(resultMap, versionMap, bufXML, realPath);
					if (returnStr == null) {
						return this.outString("<IndexXML>" + bufXML.toString() + "</IndexXML>");
					} else {
					
						return this.outString(clientUploadCommonFun.getNewReturnMessage(returnStr));
					}
				} else {
					return this.outString(clientUploadCommonFun.getNewReturnMessage(returnStr));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String strErr = "获取中心配置失败\n" + e.toString();
			String returnXml = clientUploadCommonFun.getNewReturnMessage(strErr);
			logger.error("getCenterSetting获取中心配置失败", e);
			return this.outString(returnXml);
		}
	}

	public String getIndexXML() {
		return IndexXML;
	}

	public void setIndexXML(String indexXML) {
		IndexXML = indexXML;
	}

}
