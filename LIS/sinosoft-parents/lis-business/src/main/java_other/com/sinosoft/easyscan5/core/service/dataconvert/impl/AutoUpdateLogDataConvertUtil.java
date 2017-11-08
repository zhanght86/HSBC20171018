package com.sinosoft.easyscan5.core.service.dataconvert.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.sinosoft.easyscan5.common.Constants;
import com.sinosoft.easyscan5.common.easyscanxml.AutoUpdateLogReqXmlConstants;
import com.sinosoft.easyscan5.common.easyscanxml.UpPrepareResXmlConstants;
import com.sinosoft.easyscan5.core.service.dataconvert.INewDataConvertService;
import com.sinosoft.utility.VData;

public class AutoUpdateLogDataConvertUtil extends NewDataConvertServiceImpl
		implements INewDataConvertService {
	
	private Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 生产自动更新结果信息
	 */
	public String vdataToXml(VData data, StringBuffer bufXml) {
		String returnStr = null;
		try {
			Element root = this.getHeadXml();
			Element result = root
					.addElement(UpPrepareResXmlConstants.XML_RESULT);
			
			result.addAttribute(UpPrepareResXmlConstants.XML_RESULT_CODE,Constants.CLIENT_UPLOAD_SUCCESS);
			Element result_message = result
					.addElement(UpPrepareResXmlConstants.XML_RESULT_MESSAGE);
			result_message.setText(Constants.CLIENT_UPLOAD_SUCCESS_MESSAGE);
			Element body = root.addElement(UpPrepareResXmlConstants.XML_BODY);			
			super.getOutXml(bufXml);
		} catch (RuntimeException e) {
			logger.error("拼接返回报文出错", e);
			returnStr = "拼接返回报文出错" + e.getMessage();
		}
		return returnStr;
	}
/**
 *解析请求报文
 */
	public String xmlToVData(String xml, VData data) {
		String returnStr = null;
		data.clear();
		try {
			super.setDocument(xml);
			Document doc = super.getReqDocument();
			List codeList = doc
					.selectNodes(AutoUpdateLogReqXmlConstants.XML_ROOT + "/"
							+ AutoUpdateLogReqXmlConstants.XML_BODY + "/"
							+ AutoUpdateLogReqXmlConstants.XML_ERRORCODE);
			List infoList = doc
					.selectNodes(AutoUpdateLogReqXmlConstants.XML_ROOT + "/"
							+ AutoUpdateLogReqXmlConstants.XML_BODY + "/"
							+ AutoUpdateLogReqXmlConstants.XML_ERRORINFO);
			List verList = doc
					.selectNodes(AutoUpdateLogReqXmlConstants.XML_ROOT + "/"
							+ AutoUpdateLogReqXmlConstants.XML_BODY + "/"
							+ AutoUpdateLogReqXmlConstants.XML_VERSION);
			List operList = doc
					.selectNodes(AutoUpdateLogReqXmlConstants.XML_ROOT + "/"
							+ AutoUpdateLogReqXmlConstants.XML_BODY + "/"
							+ AutoUpdateLogReqXmlConstants.XML_USERCODE);
			List mngList = doc
					.selectNodes(AutoUpdateLogReqXmlConstants.XML_ROOT + "/"
							+ AutoUpdateLogReqXmlConstants.XML_BODY + "/"
							+ AutoUpdateLogReqXmlConstants.XML_MANAGECOM);

			Element infoNode = (Element) infoList.get(0);
			data.add(0, infoNode.getText());
			Element version = (Element) verList.get(0);
			data.add(1, version.getText());
			Element user = (Element) operList.get(0);
			data.add(2, user.getText());
			Element mng = (Element) mngList.get(0);
			data.add(3, mng.getText());
		} catch (Exception e) {
			logger.error("解析请求报文出错", e);
			returnStr = "解析请求报文出错";
		}
		return returnStr;
	}
}
