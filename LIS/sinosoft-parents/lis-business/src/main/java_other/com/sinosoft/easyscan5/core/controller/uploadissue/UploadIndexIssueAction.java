package com.sinosoft.easyscan5.core.controller.uploadissue;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.sinosoft.easyscan5.base.controller.BaseAction;
import com.sinosoft.easyscan5.core.service.clientupload.impl.UploadIndexIssueServiceImpl;
import com.sinosoft.easyscan5.core.service.dataconvert.impl.UpIndexIssueDataConvertUtil;
import com.sinosoft.easyscan5.core.vo.easyscan.UploadIssueIndexVo;


public class UploadIndexIssueAction extends BaseAction {
	private UploadIndexIssueServiceImpl uploadIndexIssueService = new UploadIndexIssueServiceImpl();
	private UpIndexIssueDataConvertUtil upIndexIssueDataConvertUtil = new UpIndexIssueDataConvertUtil();
	private Logger logger = Logger.getLogger(this.getClass());
	private String IndexXML;

	public String  uploadIndex() throws Exception{
		String returnStr = null;
		
		StringBuffer bufXML = new StringBuffer(1024);
		try{
			UploadIssueIndexVo uploadIssueIndexVo = new UploadIssueIndexVo();
			
			returnStr = upIndexIssueDataConvertUtil.xmlToUpIssueIndexVo(IndexXML, uploadIssueIndexVo);
			if(returnStr != null){
				return this.outErrorString(returnStr);
			}
			
			returnStr = uploadIndexIssueService.saveIssueIndex(uploadIssueIndexVo);
			if(returnStr != null){
				return this.outErrorString(returnStr);
			}
			returnStr = upIndexIssueDataConvertUtil.stringToXml("", bufXML);
			if(returnStr != null){
				return this.outErrorString(returnStr);
			}
			return this.outString("<IndexXML>" + bufXML.toString() + "</IndexXML>");
		} catch (Exception e) {
		
			e.printStackTrace();
			String strErr = "UploadPrepareIssueService--问题件上载索引失败：\n" + e.toString();
			logger.error("UploadPrepareIssueService,异常",e);
			return this.outErrorString(strErr);
		}
	}

	public String getIndexXML() {
		return IndexXML;
	}

	public void setIndexXML(String indexXML) {
		IndexXML = indexXML;
	}
}
