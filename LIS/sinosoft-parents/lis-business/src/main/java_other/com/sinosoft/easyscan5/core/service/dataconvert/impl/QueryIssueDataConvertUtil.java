package com.sinosoft.easyscan5.core.service.dataconvert.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.sinosoft.easyscan5.common.easyscanxml.QueryHisScanNoReqXmlConstants;
import com.sinosoft.easyscan5.common.easyscanxml.QueryIssueReqXmlConstants;
import com.sinosoft.easyscan5.common.easyscanxml.QueryIssueResXmlConstants;
import com.sinosoft.easyscan5.core.service.dataconvert.INewDataConvertService;
import com.sinosoft.easyscan5.core.vo.EsIssueDocVO;
import com.sinosoft.easyscan5.core.vo.ExpandPropertyVo;
import com.sinosoft.easyscan5.core.vo.easyscan.QueryIssueVo;
import com.sinosoft.easyscan5.entity.EsQcMain;
import com.sinosoft.easyscan5.util.FDate;

public class QueryIssueDataConvertUtil extends NewDataConvertServiceImpl
		implements INewDataConvertService {
	private Logger logger = Logger.getLogger(this.getClass());

	public String xmlToQueryIssueVo(String indexXML, QueryIssueVo queryIssueVo) {
		String returnStr = null;
		Map tempMap = new HashMap<String, String>();
		try {
			super.setDocument(indexXML);
			Document doc = super.getReqDocument();
		
			List paramList = doc
					.selectNodes(QueryHisScanNoReqXmlConstants.XML_ROOT + "/"
							+ QueryHisScanNoReqXmlConstants.XML_BODY + "/"
							+ QueryHisScanNoReqXmlConstants.XML_PARAM);
			if (paramList != null && paramList.size() > 0) {
				for (int i = 0; i < paramList.size(); i++) {
					Element paramNode = (Element) paramList.get(i);
					String paramName = paramNode
							.attributeValue(QueryHisScanNoReqXmlConstants.XML_PARAM_NAME);
					String paramText = paramNode.getTextTrim();
					tempMap.put(paramName, paramText);
				}
				mapToVo(tempMap, queryIssueVo);
			} else {
				logger.error("解析请求报文失败，请求报文确认节点");
				returnStr = "解析请求报文失败，请求报文缺少节点";
			}
		} catch (Exception e) {
			logger.error("解析请求报文出现异常", e);
			returnStr = "解析请求报文出现异常：" + e.getMessage();
		}
		return returnStr;
	}

	private void mapToVo(Map paramMap, QueryIssueVo queryIssueVo) {
		String manageCom = (String) paramMap
				.get(QueryIssueReqXmlConstants.XML_NAME_MANAGECOM);
		String channel = (String) paramMap
				.get(QueryIssueReqXmlConstants.XML_NAME_CHANNEL);
		String groupNo = (String) paramMap
				.get(QueryIssueReqXmlConstants.XML_NAME_CASENO);
		String bussType = (String) paramMap
				.get(QueryIssueReqXmlConstants.XML_NAME_BUSSTYPE);
		String issueNo = (String) paramMap
				.get(QueryIssueReqXmlConstants.XML_NAME_ISSUENO);
		String issueStartDate = (String) paramMap
				.get(QueryIssueReqXmlConstants.XML_NAME_ISSUESTARTDATE);
		String issueEndDate = (String) paramMap
				.get(QueryIssueReqXmlConstants.XML_NAME_ISSUEENDDATE);
		String issueUser = (String) paramMap
				.get(QueryIssueReqXmlConstants.XML_NAME_ISSUEUSER);
		String dataSource = (String) paramMap
			.get(QueryIssueReqXmlConstants.XML_NAME_DATASOURCE);
		queryIssueVo.setManageCom(manageCom);
		queryIssueVo.setChannel(channel);
		queryIssueVo.setGroupNo(groupNo);
		queryIssueVo.setBussType(bussType);
		queryIssueVo.setIssueNo(issueNo);
		queryIssueVo.setIssueStartDate(issueStartDate);
		queryIssueVo.setIssueEndDate(issueEndDate);
		queryIssueVo.setIssueUser(issueUser);
		queryIssueVo.setDataSource(dataSource);
	}

	public String mapToXml(Map issueMap,Map propMap,StringBuffer bufXML) {
		String returnStr = null;
		try {
			Element root = this.getHeadXml();
			Element body = root
					.addElement(QueryIssueResXmlConstants.XML_BODY);
			Iterator iterator = issueMap.keySet().iterator();
			while (iterator.hasNext()) {
				Element issueEle = body
						.addElement(QueryIssueResXmlConstants.XML_ISSUE);
				String issueNo = (String) iterator.next();
				EsIssueDocVO esIssueDocVO = (EsIssueDocVO) issueMap.get(issueNo);
				issueEle.addAttribute(QueryIssueResXmlConstants.XML_ISSUE_ISSUENO, issueNo);
				issueEle.addAttribute(QueryIssueResXmlConstants.XML_ISSUE_ISSUEUSER, esIssueDocVO.getIssueUser());
				issueEle.addAttribute(QueryIssueResXmlConstants.XML_ISSUE_ISSUEDATE, esIssueDocVO.getIssueDate());
				issueEle.addAttribute(QueryIssueResXmlConstants.XML_ISSUE_ISSUETYPE,esIssueDocVO.getIssueType());
				Element issueRemarkEle = issueEle.addElement(QueryIssueResXmlConstants.XML_ISSUEREMARK);
				issueRemarkEle.setText(esIssueDocVO.getIssueDesc());
				setDocEle(issueEle,propMap,esIssueDocVO);
			}
			addResult(root);
			super.getOutXml(bufXML);
		} catch (Exception e) {
			logger.error("查询问题件出现异常", e);
			returnStr = "查询问题件出现异常：" + e.getMessage();
		}
		return returnStr;
	}
	/**
	 * 设置doc节点XML信息
	 * @param issueEle
	 * @param propMap
	 * @param esIssueDocVO
	 */
	public void setDocEle(Element issueEle,Map propMap, EsIssueDocVO esIssueDocVO){
		List<EsQcMain> qcList = esIssueDocVO.getEsQcList();
		Element groupEle = issueEle.addElement(QueryIssueResXmlConstants.XML_GROUP);
		for(int i = 0;i < qcList.size();i++){
			EsQcMain esQcMain = qcList.get(i);
			if(i == 0){
				groupEle.addAttribute(QueryIssueResXmlConstants.XML_CASENO, esQcMain.getGroupNo());
				esQcMain.getGroupNo();
			}
			Element docEle = groupEle.addElement(QueryIssueResXmlConstants.XML_DOC);
			docEle.addAttribute(QueryIssueResXmlConstants.XML_DOC_DOCID, esQcMain.getDocId());
			docEle.addAttribute(QueryIssueResXmlConstants.XML_DOC_DOCCODE, esQcMain.getDocCode());
			docEle.addAttribute(QueryIssueResXmlConstants.XML_DOC_CASENO, esQcMain.getGroupNo());
			docEle.addAttribute(QueryIssueResXmlConstants.XML_DOC_PRINTCODE, esQcMain.getPrintCode());
			docEle.addAttribute(QueryIssueResXmlConstants.XML_DOC_BUSSTYPE, esQcMain.getBussType());
			docEle.addAttribute(QueryIssueResXmlConstants.XML_DOC_SUBTYPE, esQcMain.getSubType());
			docEle.addAttribute(QueryIssueResXmlConstants.XML_DOC_BOXNO, esQcMain.getScanNo());
			docEle.addAttribute(QueryIssueResXmlConstants.XML_DOC_SCANTYPE, "");
			docEle.addAttribute(QueryIssueResXmlConstants.XML_DOC_PAGECOUNT, "" + esQcMain.getNumPages());
			docEle.addAttribute(QueryIssueResXmlConstants.XML_DOC_SCANDATE, 
					(esQcMain.getScanDate() == null)?"":FDate.formatDateTime(esQcMain.getScanDate()));
			docEle.addAttribute(QueryIssueResXmlConstants.XML_DOC_CREATEDATE,
					(esQcMain.getCreateDate() == null)?"":FDate.formatDateTime(esQcMain.getCreateDate()));
			docEle.addAttribute(QueryIssueResXmlConstants.XML_DOC_UPDATEDATE,
					(esQcMain.getUpdateDate() == null)?"":FDate.formatDateTime(esQcMain.getUpdateDate()));
			docEle.addAttribute(QueryIssueResXmlConstants.XML_DOC_SCANUSER, esQcMain.getScanOperator());
			docEle.addAttribute(QueryIssueResXmlConstants.XML_DOC_SCANCOM, esQcMain.getScanCom());
			docEle.addAttribute(QueryIssueResXmlConstants.XML_DOC_MANAGECOM, esQcMain.getManageCom());
			docEle.addAttribute(QueryIssueResXmlConstants.XML_PROP1, esQcMain.getFu1());
			docEle.addAttribute(QueryIssueResXmlConstants.XML_PROP2, esQcMain.getDocRemark());
			//��֤��Դ��0��ʾQC
			docEle.addAttribute(QueryIssueResXmlConstants.XML_DOC_DATASOURCE, "4");
			setPropEle(docEle,esQcMain.getDocId(),propMap);
		}
	}
	/**
	 * 设置扩展属性节点XML信息
	 * @param docEle
	 * @param docId 
	 * @param propMap
	 */
	public void setPropEle(Element docEle, String docId, Map propMap){
		Element propsEle = docEle.addElement(QueryIssueResXmlConstants.XML_PROPS);
		List propList = (List) propMap.get(docId);
		if(propList != null && propList.size() > 0){
			Element propEle = propsEle.addElement(QueryIssueResXmlConstants.XML_PROPS_PROP);
			for(int i = 0;i < propList.size();i++){
				ExpandPropertyVo esExpandPropertyVo = (ExpandPropertyVo) propList.get(i);
				propEle.addAttribute(QueryIssueResXmlConstants.XML_PROP_CODE, esExpandPropertyVo.getProName());
				propEle.addAttribute(QueryIssueResXmlConstants.XML_PROP_VALUE, esExpandPropertyVo.getProVaule());
			}
		}
	}
}
