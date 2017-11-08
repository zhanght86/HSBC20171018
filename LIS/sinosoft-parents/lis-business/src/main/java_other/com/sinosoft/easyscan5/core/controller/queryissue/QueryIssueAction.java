package com.sinosoft.easyscan5.core.controller.queryissue;


import com.sinosoft.easyscan5.base.controller.BaseAction;
import com.sinosoft.easyscan5.core.service.dataconvert.impl.QueryIssueDataConvertUtil;
import com.sinosoft.easyscan5.core.service.queryissuedoc.impl.QueryIssueDocSerivceImpl;
import com.sinosoft.easyscan5.core.vo.easyscan.QueryIssueVo;

public class QueryIssueAction extends BaseAction {
	private String IndexXML;	
	private QueryIssueDocSerivceImpl queryIssueDocService = new QueryIssueDocSerivceImpl();
	private QueryIssueDataConvertUtil queryIssueDataConvertUtil = new QueryIssueDataConvertUtil();
	public String  queryIssue() throws Exception{ 
		String returnStr = null;
		StringBuffer bufXML = new StringBuffer();
		QueryIssueVo queryIssueVo = new QueryIssueVo();
		
		returnStr = queryIssueDataConvertUtil.xmlToQueryIssueVo(IndexXML, queryIssueVo);
		if(returnStr != null){
			return this.outString(clientUploadCommonFun.getNewReturnMessage(returnStr));
		}
		
		returnStr = queryIssueDocService.queryIssue(queryIssueVo);
		if(returnStr != null){
			return this.outString(clientUploadCommonFun.getNewReturnMessage(returnStr));
		}
		
		returnStr = queryIssueDataConvertUtil.mapToXml(queryIssueDocService.getReturnIssue(),
				queryIssueDocService.getReturnProp(),bufXML);
		if(returnStr != null){
			return this.outString(clientUploadCommonFun.getNewReturnMessage(returnStr));
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
