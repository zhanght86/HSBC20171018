package com.sinosoft.easyscan5.core.controller.clientupload;

import org.apache.log4j.Logger;

import com.sinosoft.easyscan5.base.controller.BaseAction;
import com.sinosoft.easyscan5.common.Constants;
import com.sinosoft.easyscan5.core.service.clientupload.impl.NewUploadPrepareServiceImpl;
import com.sinosoft.easyscan5.core.service.dataconvert.impl.UpPrepareDataConvertUtil;
import com.sinosoft.utility.VData;

public class NewUploadPrepareAction extends BaseAction {
	private String IndexXML;
	private NewUploadPrepareServiceImpl uploadPrepareService = new NewUploadPrepareServiceImpl();
	private UpPrepareDataConvertUtil upPrepareDataConvertUtil = new UpPrepareDataConvertUtil();
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 新单上载校验
	 * 
	 * @return
	 * @throws Exception
	 */
	public String uploadPrepare() throws Exception {
		StringBuffer bufXML = new StringBuffer(1024);
		String returnStr = null;
		try {
			VData nVData = new VData();
			
			returnStr = upPrepareDataConvertUtil.xmlToVData(IndexXML, nVData);
			if (returnStr != null) {
				return this.outErrorString(returnStr);
			} else {
				
				uploadPrepareService.submitData(nVData, upPrepareDataConvertUtil.channel, Constants.UPLOAD_CLIENT);
				
				nVData = uploadPrepareService.getResult();
			
				returnStr = upPrepareDataConvertUtil.vdataToXml(nVData, bufXML);
				if (returnStr != null) {
					return this.outErrorString(returnStr);
				} else {
					return this.outString("<IndexXML>" + bufXML.toString() + "</IndexXML>");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			String strErr = "NewUploadPrepareAction--新单上载校验出现异常\n" + e.toString();
			logger.error("NewUploadPrepareAction--新单上载校验出现异常", e);
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
