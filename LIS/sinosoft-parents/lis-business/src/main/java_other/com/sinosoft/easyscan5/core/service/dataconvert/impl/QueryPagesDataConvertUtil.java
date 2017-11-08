package com.sinosoft.easyscan5.core.service.dataconvert.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.sinosoft.easyscan5.common.easyscanxml.QueryPagesReqXmlConstants;
import com.sinosoft.easyscan5.common.easyscanxml.QueryPagesResXmlConstants;
import com.sinosoft.easyscan5.core.service.dataconvert.INewDataConvertService;
import com.sinosoft.easyscan5.core.vo.easyscan.EsPageVo;
import com.sinosoft.easyscan5.core.vo.easyscan.QueryPageVo;
public class QueryPagesDataConvertUtil extends NewDataConvertServiceImpl
		implements INewDataConvertService {
	private Logger logger = Logger.getLogger(this.getClass());
	public String xmlToList(String indexXML, List queryList) {
		String returnStr = null;
		Map tempMap = new HashMap<String, String>();
		try {
			super.setDocument(indexXML);
			Document doc = super.getReqDocument();
			// 获取param节点
			List paramList = doc
					.selectNodes(QueryPagesReqXmlConstants.XML_ROOT + "/"
							+ QueryPagesReqXmlConstants.XML_BODY + "/"
							+ QueryPagesReqXmlConstants.XML_PARAM);
			if (paramList != null && paramList.size() > 0) {
				for (int i = 0; i < paramList.size(); i++) {
					Element paramNode = (Element) paramList.get(i);
					String paramName = paramNode
							.attributeValue(QueryPagesReqXmlConstants.XML_PARAM_NAME);
					String paramText = paramNode.getTextTrim();
					tempMap.put(paramName, paramText);
				}
				QueryPageVo queryPageVo = new QueryPageVo();
				mapToVo(tempMap, queryPageVo);
				queryList.add(queryPageVo);
			} else {
				logger.error("查询单证页请求参数为空");
				returnStr = "查询单证页请求参数为空";
			}
		} catch (Exception e) {
			logger.error("解析单证页请求报文出错", e);
			returnStr = "解析单证页请求报文出错：" + e.getMessage();
		}
		return returnStr;
	}
	private void mapToVo(Map paramMap, QueryPageVo queryPageVo) {
		String docId = (String) paramMap
				.get(QueryPagesReqXmlConstants.XML_NAME_DOCID);
		String startPage = (String) paramMap
				.get(QueryPagesReqXmlConstants.XML_NAME_STARTPAGE);
		String endPage = (String) paramMap
				.get(QueryPagesReqXmlConstants.XML_NAME_ENDPAGE);
		String haveMark = (String) paramMap
				.get(QueryPagesReqXmlConstants.XML_NAME_HAVEMARK);
		String dataSource = (String) paramMap
				.get(QueryPagesReqXmlConstants.XML_NAME_DATASOURCE);
		String type = (String) paramMap
				.get(QueryPagesReqXmlConstants.XML_NAME_QUERYTYPE);
		queryPageVo.setDocId(docId);
		queryPageVo.setStartPage(startPage);
		queryPageVo.setEndPage(endPage);
		queryPageVo.setHaveMark(haveMark);
		queryPageVo.setDataSource(dataSource);
		queryPageVo.setType(type);
	}
	public String listToXml(List pagesList,StringBuffer bufXML) {
		String returnStr = null;
		try {
			Element root = this.getHeadXml();
			Element body = root
					.addElement(QueryPagesResXmlConstants.XML_BODY);
			Element docEle = body.addElement(QueryPagesResXmlConstants.XML_DOC);
			Element pagesEle = docEle.addElement(QueryPagesResXmlConstants.XML_PAGES);
			for(int i = 0;i < pagesList.size();i++){
				EsPageVo esPageVo = (EsPageVo) pagesList.get(i);
				if(i == 0){
					docEle.addAttribute(QueryPagesResXmlConstants.XML_DOCID, esPageVo.getDocId());
				}
				Element pageEle = pagesEle.addElement(QueryPagesResXmlConstants.XML_PAGE);
				pageEle.addAttribute(QueryPagesResXmlConstants.XML_PAGE_PAGEID, esPageVo.getPageId());
				pageEle.addAttribute(QueryPagesResXmlConstants.XML_DOCID, esPageVo.getDocId());
				pageEle.addAttribute(QueryPagesResXmlConstants.XML_PAGE_PAGECODE, esPageVo.getPageCode());
				pageEle.addAttribute(QueryPagesResXmlConstants.XML_PAGE_PAGENAME, esPageVo.getPageName());
				pageEle.addAttribute(QueryPagesResXmlConstants.XML_PAGE_PAGESUFFIX, esPageVo.getPageSuffix());
				pageEle.addAttribute(QueryPagesResXmlConstants.XML_PAGE_PAGEFLAG, esPageVo.getPageFlag());
				pageEle.addAttribute(QueryPagesResXmlConstants.XML_PAGE_BOXNO, esPageVo.getScanNo());
				pageEle.addAttribute(QueryPagesResXmlConstants.XML_PAGE_PAGEURL, esPageVo.getPageUrl());
				pageEle.addAttribute(QueryPagesResXmlConstants.XML_PAGE_FILEKEY, esPageVo.getFileKey());
				pageEle.addAttribute(QueryPagesResXmlConstants.XML_PAGE_SCANCOM, esPageVo.getScanCom());
				pageEle.addAttribute(QueryPagesResXmlConstants.XML_PAGE_SCANUSER, esPageVo.getScanUser());
				pageEle.addAttribute(QueryPagesResXmlConstants.XML_PAGE_SCANDATE, esPageVo.getScanDate());
				pageEle.addAttribute(QueryPagesResXmlConstants.XML_PAGE_CREATEDATE, esPageVo.getCreateDate());
				pageEle.addAttribute(QueryPagesResXmlConstants.XML_PAGE_UPDATEDATE, esPageVo.getUpdateDate());
				pageEle.addAttribute(QueryPagesResXmlConstants.XML_PAGE_FORMAT, esPageVo.getFormat());
				if(esPageVo.getMarkLayer() != null && !"".equals(esPageVo.getMarkLayer())){
					Element marklaryerEle = pageEle.addElement(QueryPagesResXmlConstants.XML_PAGE_MARKLAYER);
					marklaryerEle.setText(esPageVo.getMarkLayer());
				}
				if(esPageVo.getContent() != null && !"".equals(esPageVo.getContent())){
					Element contentEle = pageEle.addElement(QueryPagesResXmlConstants.XML_PAGE_CONTENT);
					contentEle.setText(esPageVo.getContent());
				}
				if(esPageVo.getRemark() != null && !"".equals(esPageVo.getRemark())){
					Element remarkEle = pageEle.addElement(QueryPagesResXmlConstants.XML_PAGE_REMARK);
					remarkEle.setText(esPageVo.getRemark());
				}
			}
			addResult(root);
			super.getOutXml(bufXML);
		} catch (Exception e) {
			logger.error("查询问题件封装返回报文出错", e);
			returnStr = "查询问题件封装返回报文出错：" + e.getMessage();
		}
		return returnStr;
	}
}
