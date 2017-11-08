package com.sinosoft.easyscan5.core.service.dataconvert.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.sinosoft.easyscan5.common.easyscanxml.QueryHisScanNoReqXmlConstants;
import com.sinosoft.easyscan5.common.easyscanxml.QueryHisScanNoResXmlConstants;
import com.sinosoft.easyscan5.core.service.dataconvert.INewDataConvertService;
import com.sinosoft.easyscan5.core.vo.easyscan.GetScanNoVo;
import com.sinosoft.easyscan5.core.vo.easyscan.ProductVo;
import com.sinosoft.easyscan5.core.vo.easyscan.QueryHisScanNoResVo;
import com.sinosoft.utility.VData;

public class QueryHisScanNoDataConvertUtil extends NewDataConvertServiceImpl implements
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
			List paramList = doc.selectNodes(QueryHisScanNoReqXmlConstants.XML_ROOT
					+ "/" + QueryHisScanNoReqXmlConstants.XML_BODY + "/"
					+ QueryHisScanNoReqXmlConstants.XML_PARAM);
			if(paramList != null && paramList.size() > 0){
				for(int i = 0;i < paramList.size();i++){
					Element paramNode = (Element) paramList.get(i);
					String paramName = paramNode.attributeValue(QueryHisScanNoReqXmlConstants.XML_PARAM_NAME);
					String paramText = paramNode.getTextTrim();
					tempMap.put(paramName, paramText);
				}
				mapToVo(tempMap,getScanNoVo);
			}else{
				logger.error("解析请求失败");
				returnStr = "解析请求失败";
			}
		} catch (Exception e) {
			logger.error("解析请求失败，异常：", e);
			returnStr = "获取历史箱号失败，服务器解析请求，原因：" + e.getMessage();
		}
		return returnStr;
	}
	public String  listToXml(List scanNoList, StringBuffer bufXML){
		String returnStr = null;
		try {
			Element root = this.getHeadXml();
			Element body = root.addElement(QueryHisScanNoResXmlConstants.XML_BODY);
			for(int i = 0;i < scanNoList.size();i++){
				QueryHisScanNoResVo queryHisScanNoResVo = (QueryHisScanNoResVo)scanNoList.get(i);
				Element scanNoEle = body.addElement(QueryHisScanNoResXmlConstants.XML_BOXNO);
				scanNoEle.addAttribute(QueryHisScanNoResXmlConstants.XML_SCANNO_VALUE,queryHisScanNoResVo.getScanNo());
				scanNoEle.addAttribute(QueryHisScanNoResXmlConstants.XML_SCANNO_DOCCOUNT, queryHisScanNoResVo.getDocCount());
				scanNoEle.addAttribute(QueryHisScanNoResXmlConstants.XML_SCANNO_PAGECOUNT, queryHisScanNoResVo.getPageCount());
				scanNoEle.addAttribute(QueryHisScanNoResXmlConstants.XML_SCANNO_REMARK, "");
			}
			addResult(root);
			super.getOutXml(bufXML);
		}catch (Exception e) {
			logger.error("查询历史箱号失败", e);
			returnStr = "查询历史箱号失败，原因：" + e.getMessage();
		}
		return returnStr;
	}
	public void mapToVo(Map paramMap,GetScanNoVo getScanNoVo){
		String manageCom = (String)paramMap.get
			(QueryHisScanNoReqXmlConstants.XML_NAME_MANAGECOM);
		String channel = (String)paramMap.get
			(QueryHisScanNoReqXmlConstants.XML_NAME_CHANNEL);
		String groupNo = (String)paramMap.get
			(QueryHisScanNoReqXmlConstants.XML_NAME_CASENO);
		String scanUser = (String)paramMap.get
			(QueryHisScanNoReqXmlConstants.XML_NAME_SCANUSER);
		String bussType = (String)paramMap.get
			(QueryHisScanNoReqXmlConstants.XML_NAME_BUSSTYPE);
		String subType = (String)paramMap.get
			(QueryHisScanNoReqXmlConstants.XML_NAME_SUBTYPE);
		String startDate = (String)paramMap.get
			(QueryHisScanNoReqXmlConstants.XML_NAME_STARTDATE);
		String endDate = (String)paramMap.get
			(QueryHisScanNoReqXmlConstants.XML_NAME_ENDDATE);
		String scanNo = (String)paramMap.get
		(QueryHisScanNoReqXmlConstants.XML_NAME_BOXNO);
		getScanNoVo.setManageCom(manageCom);
//		getScanNoVo.setChannel(channel);
		getScanNoVo.setGroupNo(groupNo);
		getScanNoVo.setScanUser(scanUser);
		getScanNoVo.setBussType(bussType);
		getScanNoVo.setSubType(subType);
		getScanNoVo.setStartDate(startDate);
		getScanNoVo.setEndDate(endDate);
		getScanNoVo.setScanNo(scanNo);
	}

}
