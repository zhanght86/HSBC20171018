package com.sinosoft.easyscan5.core.controller.querypages;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.easyscan5.base.controller.BaseAction;
import com.sinosoft.easyscan5.core.service.dataconvert.impl.QueryPagesDataConvertUtil;
import com.sinosoft.easyscan5.core.service.querydocpages.impl.QueryPagesServiceImpl;

public class QueryPagesAction extends BaseAction {
	private QueryPagesServiceImpl queryPagesService = new QueryPagesServiceImpl();
	private String IndexXML;	
	private QueryPagesDataConvertUtil queryPagesDataConvertUtil = new QueryPagesDataConvertUtil();
	public String queryPages() throws Exception{ 
		String returnStr = null;
		StringBuffer bufXML = new StringBuffer(1024);
		List queryPageList = new ArrayList();
		
		returnStr = queryPagesDataConvertUtil.xmlToList(IndexXML, queryPageList);
		if(returnStr != null){
			return this.outErrorString(returnStr);
		}
		
		returnStr = queryPagesService.queryPages(queryPageList);
		if(returnStr != null){
			return this.outErrorString(returnStr);
		}
	
		List pagevoList = queryPagesService.getResultList();
		returnStr = queryPagesDataConvertUtil.listToXml(pagevoList,bufXML);
		if(returnStr != null){
			return this.outErrorString(returnStr);
		}
		return this.outString("<IndexXML>" + bufXML.toString() + "</IndexXML>");
	}
	public String getIndexXML() {
		return IndexXML;
	}
	public void setIndexXML(String indexXML) {
		IndexXML = indexXML;
	}
}
