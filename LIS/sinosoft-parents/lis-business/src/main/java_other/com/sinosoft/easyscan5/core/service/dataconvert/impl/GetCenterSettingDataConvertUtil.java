package com.sinosoft.easyscan5.core.service.dataconvert.impl;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sinosoft.easyscan5.common.easyscanxml.GetCenterSettingReqXmlConstants;
import com.sinosoft.easyscan5.common.easyscanxml.GetCenterSettingResXmlConstants;
import com.sinosoft.easyscan5.common.easyscanxml.UpImageReqXmlConstants;
import com.sinosoft.easyscan5.core.service.dataconvert.INewDataConvertService;
import com.sinosoft.easyscan5.core.vo.ExpPropVo;
import com.sinosoft.easyscan5.entity.EsDocDef;
import com.sinosoft.easyscan5.entity.EsTwainDef;
import com.sinosoft.easyscan5.entity.SysCompany;

public class GetCenterSettingDataConvertUtil extends NewDataConvertServiceImpl implements
		INewDataConvertService {
	private Logger logger = Logger.getLogger(this.getClass());
	public String channel;
	/**
	 * 解析请求
	 */
	public String xmlToMap(String strXML, Map map) {
		String returnStr = null;
		try {
			super.setDocument(strXML);
			Document doc = super.getReqDocument();
			channel = getChannel(doc);
			
			List paramList = doc.selectNodes(GetCenterSettingReqXmlConstants.XML_ROOT
					+ "/" + GetCenterSettingReqXmlConstants.XML_BODY + "/"
					+ GetCenterSettingReqXmlConstants.XML_PARAMVERSION + "/"
					+ GetCenterSettingReqXmlConstants.XML_PARAM);
			if(paramList != null && paramList.size() > 0){
				for(int i = 0;i < paramList.size();i++){
					Element paramNode = (Element) paramList.get(i);
					String paramName = paramNode.attributeValue(GetCenterSettingReqXmlConstants.XML_PARAM_NAME);
					String paramVersion = paramNode.attributeValue(GetCenterSettingReqXmlConstants.XML_PARAM_VERSION);
					map.put(paramName, paramVersion);
				}
			}else{
				logger.error("获取中心配置请求参数为空");
				returnStr = "获取中心配置请求参数为空";
			}
		} catch (Exception e) {
			logger.error("解析中心配置请求报文出错", e);
			returnStr = "解析中心配置请求报文出错：" + e.getMessage();
		}
		return returnStr;
	}
	
	public String mapToXml(Map resultMap, Map versionMap, StringBuffer bufXml,String realPath) {
		String returnStr = null;
		try {
			Element root = this.getHeadXml();
			Element body = root.addElement(GetCenterSettingResXmlConstants.XML_BODY);
			//封装version
			versionToXml(body, versionMap);
			//封装docDefXml
			docDefMapToXml(body,resultMap);
			Element propDefEle = body.addElement(GetCenterSettingReqXmlConstants.XML_PARAM_NAME_PROPDEF);
			//封装propXml
			propDefToXml(propDefEle,resultMap);
			//封装codelistXml
			codeListToXml(propDefEle, resultMap);
			//封装managecomXml
			managecomListToXml(body,resultMap);
			//封装scandefToXml
			scandefToXml(body,resultMap);
			//封装easycan.xml配置
			easyScanToXml(body,realPath);
			addResult(root);
			super.getOutXml(bufXml);
		} catch (Exception e) {
			logger.error("获取中心配置封装报文出错", e);
			returnStr = "获取中心配置封装报文出错：" + e.getMessage();
		}
		return returnStr;
	}
	/**
	 * easyScanToXml
	 * @param body
	 * @param path
	 * @throws Exception 
	 */
	private void easyScanToXml(Element body,String path) throws Exception {
		SAXReader reader = new SAXReader();
		String configFile = "EasyScan5.xml";
		String xmlPath = path + File.separatorChar 
				+ "WEB-INF" + File.separatorChar 
				+ "classes" + File.separatorChar 
//				+ "resources" + File.separatorChar
				+ "easyscan5" + File.separatorChar
				+ configFile;
		xmlPath = "D:"+File.separatorChar+"EasyScan5.xml";
		logger.info("EasyScan5.xml路径"+xmlPath);
		reader.setEncoding("UTF-8");
		File t =new File(xmlPath);
		logger.info(t.exists());
		Document doc = reader.read(xmlPath);
		Element root = doc.getRootElement();
		List paramSettingList = root.selectNodes(GetCenterSettingResXmlConstants.XML_PARAMSETTING);
		if(paramSettingList != null && paramSettingList.size() > 0){
			Element paramSettingEle = (Element)paramSettingList.get(0);
			body.add((Element)paramSettingEle.clone());
		}
		List batchsettingsList = root.selectNodes(GetCenterSettingResXmlConstants.XML_BATCHSETTINGS);
		if(batchsettingsList != null && batchsettingsList.size() > 0){
			Element barcoderuleEle = (Element)batchsettingsList.get(0);
			body.add((Element)barcoderuleEle.clone());
		}
	}
	/**
	 * scandefToXml
	 * @param body
	 * @param resultMap
	 */
	public void scandefToXml(Element body,Map resultMap){
		String scanDefXml = GetCenterSettingReqXmlConstants.XML_PARAM_NAME_SCANDEF;
		List twainDefList = (List) resultMap.get(scanDefXml);
		Element scansettingEle = body.addElement(GetCenterSettingResXmlConstants.XML_SCANDEF);
		if (twainDefList != null && twainDefList.size() > 0) {
			EsTwainDef esTwainDef = null;
			 Element scan = null;
			for (Object obj : twainDefList) {
				esTwainDef = (EsTwainDef) obj;
				scan = scansettingEle.addElement(GetCenterSettingResXmlConstants.XML_SCANSETTING_SCAN);
				scan.addAttribute(GetCenterSettingResXmlConstants.XML_SCAN_CODE,
						DealNull(esTwainDef.getDefsettingcode()));
	            scan.addAttribute(GetCenterSettingResXmlConstants.XML_SCAN_NAME,
	            		DealNull(esTwainDef.getDefsettingname()));
	            scan.addAttribute(GetCenterSettingResXmlConstants.XML_SCAN_DPI,
	            		"" + esTwainDef.getDpi());
	            scan.addAttribute(GetCenterSettingResXmlConstants.XML_SCAN_BITDEPTH,
	            		DealNull(esTwainDef.getBitdepth()));
	            scan.addAttribute(GetCenterSettingResXmlConstants.XML_SCAN_PAPERMODE,
	            		DealNull(esTwainDef.getPagermode()));
	            scan.addAttribute(GetCenterSettingResXmlConstants.XML_SCAN_DUPLEX,
	            		DealNull(esTwainDef.getDuplex()));
	            
	            Element scanruleEle = scan.addElement(GetCenterSettingResXmlConstants.XML_SCAN_RULE);
	            String autoDeskew = "false";
	            String autoBorderDetection = "false";
	            if("0".equals(esTwainDef.getAutomaticdeskew())){
	            	autoDeskew = "true";
	            }
	            if("0".equals(esTwainDef.getIsblackedge())){
	            	autoBorderDetection = "true";
	            }
	            scanruleEle.setText("AutoDeskew=" + autoDeskew + ";AutoBorderDetection=" 
	            		+ autoBorderDetection +";");
//	            Element imageruleEle = scan.addElement(GetCenterSettingResXmlConstants.XML_IMAGE_RULE);
//	            imageruleEle.setText("");
//	            scan.setAttribute(XmlConstants.XML_NODE_SCANPAGES, DealNull(esTwainDef.getPages()));
//	            scan.setAttribute(XmlConstants.XML_NODE_SCANBRIGHTNESS,""+ esTwainDef.getBrightness());
//	            scan.setAttribute(XmlConstants.XML_NODE_SCANCONTRAST,""+esTwainDef.getContrast());
//	            scan.setAttribute(XmlConstants.XML_NODE_SCANAUTOBRIGHT, ""+esTwainDef.getAutobright());
//	            scan.setAttribute(XmlConstants.XML_NODE_SCANAUTOMATICDESKEW, DealNull(esTwainDef.getAutomaticdeskew()));
//	            scan.setAttribute(XmlConstants.XML_NODE_SCANISBLACKEDGE, DealNull(esTwainDef.getIsblackedge()));
//	            scan.setAttribute(XmlConstants.XML_NODE_SCANISBLANK, DealNull(esTwainDef.getIsblank()));
//	            scan.setAttribute(XmlConstants.XML_NODE_SCANBLANKVALUE, ""+esTwainDef.getBlankvalue());
			}
		}
	}
	/**
	 * managecomListToXml
	 * @param body
	 * @param resultMap
	 */
	public void managecomListToXml(Element body,Map resultMap){
		String companyXml = GetCenterSettingReqXmlConstants.XML_PARAM_NAME_MANAGECOMS;
		List companyList = (List) resultMap.get(companyXml);
		Element comEle = body.addElement(GetCenterSettingResXmlConstants.XML_MANAGECOMS);
		if (companyList != null && companyList.size() > 0) {
			Element manageEle = null;
			SysCompany sysCompany = null;
			for (Object obj : companyList) {
				sysCompany = (SysCompany) obj;
				manageEle = comEle.addElement(GetCenterSettingResXmlConstants.XML_MANAGECOM);
				manageEle.addAttribute(GetCenterSettingResXmlConstants.XML_MANAGECOM_CODE,
						sysCompany.getCompanyNo());
				manageEle.addAttribute(GetCenterSettingResXmlConstants.XML_MANAGECOM_NAME,
						sysCompany.getCompanyName());
				manageEle.addAttribute(GetCenterSettingResXmlConstants.XML_MANAGECOM_KEYWORD,
						"");
			}
		}
	}
	/**
	 * codeListToXml
	 * @param body
	 * @param resultMap
	 */
	public void codeListToXml(Element propdef,Map resultMap){
		String codeListXml = GetCenterSettingResXmlConstants.XML_CODELIST;
		Map codeListMap = (Map) resultMap.get(codeListXml);
		Element codeListEle = propdef.addElement(GetCenterSettingResXmlConstants.XML_CODELIST);
		if (codeListMap != null && codeListMap.keySet() != null && codeListMap.keySet().size() > 0) {
			List codeList = null;
			Object[] objarr = null;
			Element codeTypeEle = null;
			Element listEle = null;
			Iterator iterator = codeListMap.keySet().iterator();
			boolean flag = true;
			while (iterator.hasNext()) {
				codeTypeEle = codeListEle.addElement(GetCenterSettingResXmlConstants.XML_CODETYPE);
				codeList = (List) codeListMap.get(iterator.next());
				flag = true;
				for (Object obj : codeList) {
					objarr = (Object[]) obj;
					if (flag) {
						codeTypeEle.addAttribute(GetCenterSettingResXmlConstants.XML_CODETYPE_CODE,
								DealNull(objarr[0]));
						codeTypeEle.addAttribute(GetCenterSettingResXmlConstants.XML_CODETYPE_NAME,
								DealNull(objarr[1]));
						flag = false;
					}
					listEle = codeTypeEle.addElement(GetCenterSettingResXmlConstants.XML_CODETYPE_LIST);
					listEle.addAttribute(GetCenterSettingResXmlConstants.XML_CODETYPE_LIST_CODE,
							DealNull(objarr[2]));
					listEle.addAttribute(GetCenterSettingResXmlConstants.XML_CODETYPE_LIST_NAME,
							DealNull(objarr[3]));
					listEle.addAttribute(GetCenterSettingResXmlConstants.XML_CODETYPE_LIST_KEYWORD,
							"");
				}
			}
		}
	}
	/**
	 * propDefToXml
	 * @param propdef
	 * @param resultMap
	 */
	public void propDefToXml(Element propdef,Map resultMap){
		String propDefXml = GetCenterSettingReqXmlConstants.XML_PARAM_NAME_PROPDEF;
		List propList = (List) resultMap.get(propDefXml);
		Element propsEle = propdef.addElement(GetCenterSettingResXmlConstants.XML_PROPS);
		if (propList != null && propList.size() > 0) {
			ExpPropVo expPropVo = null;
			Element propField = null;
			int i = 1;
			for (Object obj : propList) {
				expPropVo = (ExpPropVo) obj;
				propField = propsEle.addElement(GetCenterSettingResXmlConstants.XML_PROPFIELD);
				propField.addAttribute(GetCenterSettingResXmlConstants.XML_PROPFIELD_CODE,
						DealNull(expPropVo.getPropColumn().toUpperCase()));
				propField.addAttribute(GetCenterSettingResXmlConstants.XML_PROPFIELD_NAME,
						DealNull(expPropVo.getPropName()));
				propField.addAttribute(GetCenterSettingResXmlConstants.XML_PROPFIELD_FIELDORDER,
						DealNull(""+(i++)));
				propField.addAttribute(GetCenterSettingResXmlConstants.XML_PROPFIELD_BUSSTYPE,
						DealNull(expPropVo.getBussType()));
				propField.addAttribute(GetCenterSettingResXmlConstants.XML_PROPFIELD_SUBTYPE,
						DealNull(expPropVo.getSubType()));
				propField.addAttribute(GetCenterSettingResXmlConstants.XML_PROPFIELD_CHANNEL,
						DealNull(expPropVo.getChannel()));
				propField.addAttribute(GetCenterSettingResXmlConstants.XML_PROPFIELD_CTRLTYPE,
						DealNull(expPropVo.getPropCtrlType()));
				propField.addAttribute(GetCenterSettingResXmlConstants.XML_PROPFIELD_DEFAULTVALUE,
						DealNull(expPropVo.getPropDefaultValue()));
				propField.addAttribute(GetCenterSettingResXmlConstants.XML_PROPFIELD_DEFAULTVALUE,
						DealNull(expPropVo.getPropDefaultValue()));
				propField.addAttribute(GetCenterSettingResXmlConstants.XML_PROPFIELD_CTRLCODE,
						DealNull(expPropVo.getPropDictType()));
			}
		}
	}
	/**
	 * ��װparamVersion
	 * @param body
	 * @param versionMap
	 */
	public void versionToXml(Element body,Map versionMap){
		if (versionMap.keySet() != null && versionMap.keySet().size() > 0) {
			Iterator iterator = versionMap.keySet().iterator();
			boolean flag = true;
			Element paramVersionEle = body.addElement(GetCenterSettingResXmlConstants.XML_PARAMVERSION);
			while (iterator.hasNext()) {
				String paramName = (String)iterator.next();
				String version = (String) versionMap.get(paramName);
				Element paramEle = paramVersionEle.addElement(GetCenterSettingResXmlConstants.XML_PARAM);
				paramEle.addAttribute(GetCenterSettingResXmlConstants.XML_PARAM_NAME,paramName);
				paramEle.addAttribute(GetCenterSettingResXmlConstants.XML_PARAM_VERSION, version);
			}
		}
	}
	/**
	 * docDefMapToXml
	 * @param body
	 * @param resultMap
	 * @param versionMap
	 */
	public void docDefMapToXml(Element body,Map resultMap){
		String docDefXml = GetCenterSettingReqXmlConstants.XML_PARAM_NAME_DOCDEF;
		Element docdefEle = body.addElement(GetCenterSettingReqXmlConstants.XML_PARAM_NAME_DOCDEF);
		Map docDefMap = (Map) resultMap.get(docDefXml);
		if (docDefMap != null && docDefMap.keySet() != null && docDefMap.keySet().size() > 0) {
			Element busstypeEle = null;
			Element subtypeEle = null;
			Iterator iterator = docDefMap.keySet().iterator();
			boolean flag = true;
			while (iterator.hasNext()) {
				busstypeEle = docdefEle.addElement(GetCenterSettingResXmlConstants.XML_BUSSTYPE);
				List subList = (List) docDefMap.get(iterator.next());
				flag = true;
				for (Object obj : subList) {
					EsDocDef esDocDef = (EsDocDef) obj;
					if (flag) {
						busstypeEle.addAttribute(GetCenterSettingResXmlConstants.XML_BUSSTYPE_CODE,
								esDocDef.getId().getBussType());
						busstypeEle.addAttribute(GetCenterSettingResXmlConstants.XML_BUSSTYPE_NAME,
								esDocDef.getBussTypeName());
						flag = false;
					}
					subtypeEle = busstypeEle.addElement(GetCenterSettingResXmlConstants.XML_SUBTYPE);
					subtypeEle.addAttribute(GetCenterSettingResXmlConstants.XML_SUBTYPE_CODE,
							esDocDef.getId().getSubType());
					subtypeEle.addAttribute(GetCenterSettingResXmlConstants.XML_SUBTYPE_NAME,
							DealNull(esDocDef.getSubtypeName()));
					subtypeEle.addAttribute(GetCenterSettingResXmlConstants.XML_SUBTYPE_KEYWORD,
							DealNull(esDocDef.getKeyword()));
					subtypeEle.addAttribute(GetCenterSettingResXmlConstants.XML_SUBTYPE_CHANNEL,
							DealNull(esDocDef.getId().getChannel()));
					subtypeEle.addAttribute(GetCenterSettingResXmlConstants.XML_SUBTYPE_ORDERNO,
							DealNull(""));
					subtypeEle.addAttribute(GetCenterSettingResXmlConstants.XML_SUBTYPE_BATCHMODE,
							DealNull(""));
					subtypeEle.addAttribute(GetCenterSettingResXmlConstants.XML_SUBTYPE_BATCHDETAILNAME,
							DealNull(""));
					String codeFlag = esDocDef.getCodeFlag();
					String newCaseFlag = esDocDef.getPassFlag();
					if("0".equals(codeFlag) && "0".equals(newCaseFlag)){
						subtypeEle.addAttribute(GetCenterSettingResXmlConstants.XML_SUBTYPE_CROSSTYPE, "NoCross");
					}else if("0".equals(codeFlag) && "1".equals(newCaseFlag)){
						subtypeEle.addAttribute(GetCenterSettingResXmlConstants.XML_SUBTYPE_CROSSTYPE, "CurPrior");
					}else if("1".equals(codeFlag) && "1".equals(newCaseFlag)){
						subtypeEle.addAttribute(GetCenterSettingResXmlConstants.XML_SUBTYPE_CROSSTYPE, "MustCross");
					}else{
						subtypeEle.addAttribute(GetCenterSettingResXmlConstants.XML_SUBTYPE_CROSSTYPE, "NoCross");
					}
					
					Element ruleEle = subtypeEle.addElement(GetCenterSettingResXmlConstants.XML_SUBTYPE_RULE); 
					ruleEle.setText(GetCenterSettingResXmlConstants.XML_SUBTYPE_CODELEN
							+ "=" + esDocDef.getCodeLen()  + ";" 
							+ GetCenterSettingResXmlConstants.XML_SUBTYPE_UPLOADFILETYPE
							+ "=" + esDocDef.getUpFileType() + ";" 
							+ GetCenterSettingResXmlConstants.XML_SUBTYPE_FILESAVETYPE
							+ "=" + esDocDef.getSaveColorMode() + ";" 
							+ GetCenterSettingResXmlConstants.XML_SUBTYPE_JPGQUALITY
							+ "=" + esDocDef.getJpgQuality() + ";" );
				}
			}
		}
	}
	
	/*
	 * 处理null和“null”值
	 */
	private String DealNull(Object str) {

		return (str == null || "null".equals(str.toString().toLowerCase())) ? ""
				: str.toString();
	}
	private void parseXmlToMap(List listFiles, Map<String,String> map)
			throws Exception {
		if (listFiles != null && listFiles.size() > 0) {
			for (int i = 0; i < listFiles.size(); i++) {
				Element files = (Element) listFiles.get(i);
				
				String filepath = files
						.attributeValue(UpImageReqXmlConstants.XML_FILES_FILEPATH);				
			}
		}
	}

}
