package com.sinosoft.easyscan5.core.service.dataconvert.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.sinosoft.easyscan5.common.easyscanxml.GetLicenseReqXmlConstants;
import com.sinosoft.easyscan5.core.service.dataconvert.INewDataConvertService;
import com.sinosoft.easyscan5.core.vo.easyscan.ProductVo;
public class GetLicenseDataConvertUtil extends NewDataConvertServiceImpl implements
	INewDataConvertService {
	private Logger logger = Logger.getLogger(this.getClass());
	/**
	 * XML转VO
	 * @param strXML
	 * @param productVo
	 */
	public String xmlToProductVo(String strXML,ProductVo productVo){
		String returnStr = null;
		try {
			super.setDocument(strXML);
			Document doc = super.getReqDocument();

			// 获取param节点
			List productNameList = doc.selectNodes(GetLicenseReqXmlConstants.XML_ROOT
					+ "/" + GetLicenseReqXmlConstants.XML_BODY + "/"
					+ GetLicenseReqXmlConstants.XML_PRODUCTNAME);
			List productVersionList = doc.selectNodes(GetLicenseReqXmlConstants.XML_ROOT
					+ "/" + GetLicenseReqXmlConstants.XML_BODY + "/"
					+ GetLicenseReqXmlConstants.XML_PRODUCTNAME);
			if(productNameList != null && productNameList.size() > 0){
				Element paramNode = (Element) productNameList.get(0);
				String paramName = paramNode.getTextTrim();
				productVo.setProductName(paramName);
			}
			if(productVersionList != null && productVersionList.size() > 0){
				Element paramNode = (Element) productVersionList.get(0);
				String paramName = paramNode.getTextTrim();
				productVo.setProductName(paramName);
			}
		} catch (Exception e) {
			logger.error("解析获取license请求报文出错", e);
			returnStr = "解析获取license请求报文出错：" + e.getMessage();
		}
		return returnStr;
	}
}
