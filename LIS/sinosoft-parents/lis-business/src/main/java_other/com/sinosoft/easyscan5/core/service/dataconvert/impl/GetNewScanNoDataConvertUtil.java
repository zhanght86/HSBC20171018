package com.sinosoft.easyscan5.core.service.dataconvert.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.sinosoft.easyscan5.common.easyscanxml.GetNewScanNoReqXmlConstants;
import com.sinosoft.easyscan5.common.easyscanxml.GetNewScanNoResXmlConstants;
import com.sinosoft.easyscan5.core.service.dataconvert.INewDataConvertService;
import com.sinosoft.easyscan5.core.vo.easyscan.GetScanNoVo;
public class GetNewScanNoDataConvertUtil extends NewDataConvertServiceImpl implements
	INewDataConvertService {
	private Logger logger = Logger.getLogger(this.getClass());
	public String xmlToScanNoVo(String strXML, GetScanNoVo getScanNoVo) {
		String returnStr = null;
		Map tempMap = new HashMap<String, String>();
		try {
			super.setDocument(strXML);
			Document doc = super.getReqDocument();
			String channel = getChannel(doc);
			getScanNoVo.setChannel(channel);
			// 获取param节点
			List paramList = doc.selectNodes(GetNewScanNoReqXmlConstants.XML_ROOT
					+ "/" + GetNewScanNoReqXmlConstants.XML_BODY + "/"
					+ GetNewScanNoReqXmlConstants.XML_PARAM);
			if(paramList != null && paramList.size() > 0){
				for(int i = 0;i < paramList.size();i++){
					Element paramNode = (Element) paramList.get(i);
					String paramName = paramNode.attributeValue(GetNewScanNoReqXmlConstants.XML_PARAM_NAME);
					String paramText = paramNode.getTextTrim(); 
					tempMap.put(paramName, paramText);
				}
				mapToVo(tempMap,getScanNoVo);
			}else{
				logger.error("解析请求失败，缺少内容节点");
				returnStr = "解析请求失败，缺少内容节点";
			}
		} catch (Exception e) {
			logger.error("解析请求失败", e);
			returnStr = "解析请求失败，原因：" + e.getMessage();
		}
		return returnStr;
	}
	public String  stringToXml(String scanNO, StringBuffer bufXML){
		String returnStr = null;
		try {
			Element root = this.getHeadXml();
			Element body = root.addElement(GetNewScanNoResXmlConstants.XML_BODY);
			Element scanNoEle = body.addElement(GetNewScanNoResXmlConstants.XML_BOXNO);
			scanNoEle.addAttribute(GetNewScanNoResXmlConstants.XML_BOXNO_VALUE,scanNO);
			scanNoEle.addAttribute(GetNewScanNoResXmlConstants.XML_BOXNO_DOCCOUNT, "0");
			scanNoEle.addAttribute(GetNewScanNoResXmlConstants.XML_BOXNO_PAGECOUNT, "0");
			addResult(root);
			super.getOutXml(bufXML);
		}catch (Exception e) {
			logger.error("返回新箱号结果信息异常：", e);
			returnStr = "返回新箱号结果信息异常：?" + e.getMessage();
		}
		return returnStr;
	}
	public void mapToVo(Map tempMap,GetScanNoVo getScanNoVo){
		String manageCom = (String)tempMap.get
			(GetNewScanNoReqXmlConstants.XML_NAME_MANAGECOM);
		String channel = (String)tempMap.get
			(GetNewScanNoReqXmlConstants.XML_NAME_CHANNEL);
		String groupNo = (String)tempMap.get
			(GetNewScanNoReqXmlConstants.XML_NAME_CASENO);
		String scanUser = (String)tempMap.get
			(GetNewScanNoReqXmlConstants.XML_NAME_SCANUSER);
		String bussType = (String)tempMap.get
			(GetNewScanNoReqXmlConstants.XML_NAME_BUSSTYPE);
		String subType = (String)tempMap.get
			(GetNewScanNoReqXmlConstants.XML_NAME_SUBTYPE);
		getScanNoVo.setManageCom(manageCom);
//		getScanNoVo.setChannel(channel);
		getScanNoVo.setGroupNo(groupNo);
		getScanNoVo.setScanUser(scanUser);
		getScanNoVo.setBussType(bussType);
		getScanNoVo.setSubType(subType);
	}
}
