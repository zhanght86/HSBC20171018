package com.sinosoft.easyscan5.core.controller.autoupdate;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sinosoft.easyscan5.base.controller.BaseAction;
import com.sinosoft.easyscan5.core.controller.clientupload.ClientUploadCommonFun;
import com.sinosoft.easyscan5.core.service.autoupdate.impl.ClientAutoUpdateServiceImpl;
import com.sinosoft.easyscan5.core.service.dataconvert.impl.AutoUpdateDataConvertUtil;
import com.sinosoft.easyscan5.core.service.dataconvert.impl.AutoUpdateLogDataConvertUtil;
import com.sinosoft.utility.VData;

public class AutoUpdateInfoAction extends BaseAction {

	private String IndexXML;
	private Logger logger = Logger.getLogger(this.getClass());
	private ClientAutoUpdateServiceImpl autoUpdateService = new ClientAutoUpdateServiceImpl();
	private AutoUpdateDataConvertUtil dataConvertService = new AutoUpdateDataConvertUtil();
	private AutoUpdateLogDataConvertUtil dataLogConvertService = new AutoUpdateLogDataConvertUtil();
	private ClientUploadCommonFun clientUploadCommonFun = new ClientUploadCommonFun();

	/**
	 * 获取自动更新信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getAutoUpdateInfo(HttpServletRequest request) throws Exception {
		StringBuffer result = new StringBuffer(1024);
		try {
			String path = request.getContextPath();
			
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path;
			VData vData = new VData();
			
			String resultStr = dataConvertService.xmlToVData(IndexXML, vData);
			if (resultStr == null || "".equals(resultStr)) {
				String version = (String) vData.get(0);
				String operator = (String) vData.get(1);
				String manageCom = (String) vData.get(2);
				
				vData = autoUpdateService.getAutoUpdate(operator, manageCom, version, basePath);
				
				dataConvertService.vdataToXml(vData, result);
			} else {
				result.append(clientUploadCommonFun.getNewReturnMessage(resultStr));
			}
		} catch (Exception e) {
			e.printStackTrace();
			String strErr = "获取自动更新失败：\n" + e.toString();
			logger.error("获取自动更新失败", e);
			result.delete(0, result.length());
			result.append(clientUploadCommonFun.getNewReturnMessage(strErr));
			logger.error("获取自动更新失败", e);
		} finally {
			return this.outString("<IndexXML>" + result.toString() + "</IndexXML>");
		}
	}

	/**
	 * ��ȡ�Զ����½��
	 * 
	 * @throws Exception
	 */
	public void getAutoUpdateResult() throws Exception {
		StringBuffer result = new StringBuffer(1024);
		try {
			VData vData = new VData();
			String resultStr = dataLogConvertService.xmlToVData(IndexXML, vData);
			if (resultStr == null || "".equals(resultStr)) {
				String errorInfo = (String) vData.get(0);
				String version = (String) vData.get(1);
				String operator = (String) vData.get(2);
				String manageCom = (String) vData.get(3);
				vData = new VData();
				dataLogConvertService.vdataToXml(vData, result);
				this.outString("<IndexXML>" + result.toString() + "</IndexXML>");
			} else {
				logger.error(clientUploadCommonFun.getNewReturnMessage(resultStr));
				this.outString(clientUploadCommonFun.getNewReturnMessage(resultStr));
			}
		} catch (Exception e) {
			e.printStackTrace();
			String strErr = "自动更新返回结果失败：\n" + e.toString();
			logger.error("自动更新返回结果失败", e);
			String returnXml = clientUploadCommonFun.getNewReturnMessage(strErr);

			this.outString(returnXml);
		}
	}

	public String getIndexXML() {
		return IndexXML;
	}

	public void setIndexXML(String indexXML) {
		IndexXML = indexXML;
	}

}
