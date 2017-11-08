package com.sinosoft.easyscan5.core.controller.clientupload;

import org.apache.log4j.Logger;

import com.sinosoft.easyscan5.base.controller.BaseAction;
import com.sinosoft.easyscan5.common.Constants;
import com.sinosoft.easyscan5.core.service.clientupload.impl.NewUploadIndexServiceImpl;
import com.sinosoft.easyscan5.core.service.dataconvert.impl.UpIndexDataConvertUtil;
import com.sinosoft.utility.VData;

public class NewUploadIndexAction extends BaseAction {

	private NewUploadIndexServiceImpl uploadIndexService = new NewUploadIndexServiceImpl();
	private UpIndexDataConvertUtil upIndexDataConvertUtil = new UpIndexDataConvertUtil();
	private Logger logger = Logger.getLogger(this.getClass());
	private String IndexXML;

	/**
	 * 新单上载索引
	 * 
	 * @return
	 * @throws Exception
	 */
	public String uploadIndex() throws Exception {
	
		StringBuffer bufXML = new StringBuffer(1024);
		String returnStr = null;
		try {
			VData nVData = new VData();
			returnStr = upIndexDataConvertUtil.xmlToVData(IndexXML, nVData);
			if (returnStr != null) {
				return this.outErrorString(returnStr);
			} else {
				
				uploadIndexService.submitData(nVData, Constants.UPLOAD_CLIENT);
			
				nVData = uploadIndexService.getResult();
				
				returnStr = upIndexDataConvertUtil.vdataToXml(nVData, bufXML);
				if (returnStr != null) {
					return this.outErrorString(returnStr);
				} else {
					return this.outString("<IndexXML>" + bufXML.toString() + "</IndexXML>");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		
			logger.error("NewUploadIndexAction--上载失败，出现异常：", e);
			String strErr = "NewUploadIndexAction--上载失败，出现异常\n" + e.toString();
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
