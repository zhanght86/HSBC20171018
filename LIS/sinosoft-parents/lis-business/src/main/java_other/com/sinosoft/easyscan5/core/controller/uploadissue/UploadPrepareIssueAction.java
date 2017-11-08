package com.sinosoft.easyscan5.core.controller.uploadissue;

import org.apache.log4j.Logger;

import com.sinosoft.easyscan5.base.controller.BaseAction;
import com.sinosoft.easyscan5.core.service.clientupload.impl.UploadPrepareIssueServiceImpl;
import com.sinosoft.easyscan5.core.service.dataconvert.impl.UpPrepareIssueDataConvertUtil;
import com.sinosoft.easyscan5.core.vo.easyscan.UploadIssuePrepareVo;

public class UploadPrepareIssueAction extends BaseAction{
	private UploadPrepareIssueServiceImpl uploadPrepareIssueService = new UploadPrepareIssueServiceImpl();
	private UpPrepareIssueDataConvertUtil upPrepareIssueDataConvertUtil = new UpPrepareIssueDataConvertUtil();
	private Logger logger = Logger.getLogger(this.getClass());
	private String IndexXML;
	public String uploadPrepare() throws Exception {
		String returnStr = null;
	
		StringBuffer bufXML = new StringBuffer(1024);
		try{
			UploadIssuePrepareVo uploadIssuePrepareVo = new UploadIssuePrepareVo();
			returnStr = upPrepareIssueDataConvertUtil.xmlToUpIssuePrepareVo(IndexXML, uploadIssuePrepareVo);
			if(returnStr != null){
				return this.outErrorString(returnStr);
			}
			
			returnStr = uploadPrepareIssueService.uploadIssuePrepare(uploadIssuePrepareVo);
			if(returnStr != null){
				return this.outErrorString(returnStr);
			}
			
			String[] serverUrl = uploadPrepareIssueService.getServerUrl();
		
			returnStr = upPrepareIssueDataConvertUtil.stringToXml(serverUrl, bufXML);
			if(returnStr != null){
				return this.outErrorString(returnStr);
			} 
			return this.outString("<IndexXML>" + bufXML.toString() + "</IndexXML>");
		} catch (Exception e) {
			e.printStackTrace();
			String strErr = "UploadPrepareIssueService-问题件上载校验失败：\n" + e.toString();
			logger.error("UploadPrepareIssueService，问题件上载校验异常：",e);
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
