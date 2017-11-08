package com.sinosoft.easyscan5.core.service.dataconvert.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.sinosoft.easyscan5.common.Constants;
import com.sinosoft.easyscan5.common.easyscanxml.AutoUpdateReqXmlConstants;
import com.sinosoft.easyscan5.common.easyscanxml.AutoUpdateResXmlConstants;
import com.sinosoft.easyscan5.core.service.dataconvert.INewDataConvertService;
import com.sinosoft.easyscan5.core.vo.easyscan.ProductVo;
import com.sinosoft.utility.VData;

public class AutoUpdateDataConvertUtil extends NewDataConvertServiceImpl
		implements INewDataConvertService {

	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 生成返回结果
	 */
	public String vdataToXml(VData data, StringBuffer bufXml) {
		String returnStr = null;
		try {
			Element root = this.getHeadXml();
			Element result = root
					.addElement(AutoUpdateResXmlConstants.XML_RESULT);
			String version = (String) data.get(0);
			String isforce = (String) data.get(1);
			String url = (String) data.get(2);

			result.addAttribute(AutoUpdateResXmlConstants.XML_RESULT_CODE,
					Constants.CLIENT_UPLOAD_SUCCESS);
			Element result_message = result
					.addElement(AutoUpdateResXmlConstants.XML_RESULT_MESSAGE);
			result_message.setText(Constants.CLIENT_UPLOAD_SUCCESS_MESSAGE);
			Element body = root.addElement(AutoUpdateResXmlConstants.XML_BODY);

			Element versionEle = body
					.addElement(AutoUpdateResXmlConstants.XML_VERSION);
			versionEle.setText(version);
			Element forceEle = body
					.addElement(AutoUpdateResXmlConstants.XML_ISFORCE);
			forceEle.setText(isforce);
			Element urlEle = body
					.addElement(AutoUpdateResXmlConstants.XML_ZIPURL);
			urlEle.setText(url);

			super.getOutXml(bufXml);
		} catch (RuntimeException e) {
			logger.error("服务器生成自动更新结果信息失败！", e);
			returnStr = "服务器生成自动更新结果信息失败！" + e.getMessage();
		}
		return returnStr;
	}

	/**
	 * 解析自动更新请求
	 */
	public String xmlToVData(String xml, VData data) {
		String returnStr = null;
		data.clear();
		try {
			super.setDocument(xml);
			Document doc = super.getReqDocument();
			List versionList = doc
					.selectNodes(AutoUpdateReqXmlConstants.XML_ROOT + "/"
							+ AutoUpdateReqXmlConstants.XML_BODY + "/"
							+ AutoUpdateReqXmlConstants.XML_VERSION);
			List operList = doc.selectNodes(AutoUpdateReqXmlConstants.XML_ROOT
					+ "/" + AutoUpdateReqXmlConstants.XML_BODY + "/"
					+ AutoUpdateReqXmlConstants.XML_USERCODE);
			List mngList = doc.selectNodes(AutoUpdateReqXmlConstants.XML_ROOT
					+ "/" + AutoUpdateReqXmlConstants.XML_BODY + "/"
					+ AutoUpdateReqXmlConstants.XML_MANAGECOM);

			Element version = (Element) versionList.get(0);
			data.add(0, version.getText());
			Element user = (Element) operList.get(0);
			data.add(1, user.getText());
			Element mng = (Element) mngList.get(0);
			data.add(2, mng.getText());
		} catch (Exception e) {
			logger.error("服务器解析自动更新请求失败", e);
			returnStr = "服务器解析自动更新请求失败，原因:" + e.getMessage();
		}
		return returnStr;
	}

	public String xmlToProductVo(String indexXML, ProductVo productVo) {
		// TODO Auto-generated method stub
		return null;
	}
}
