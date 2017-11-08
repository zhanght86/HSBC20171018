package com.sinosoft.easyscan5.core.controller.getscanno;

import org.apache.log4j.Logger;

import com.sinosoft.easyscan5.base.controller.BaseAction;
import com.sinosoft.easyscan5.core.service.dataconvert.impl.GetNewScanNoDataConvertUtil;
import com.sinosoft.easyscan5.core.service.getscanno.impl.GetScanNoServiceImpl;
import com.sinosoft.easyscan5.core.vo.easyscan.GetScanNoVo;


public class GetNewScanNoAction extends BaseAction {

	private GetScanNoServiceImpl getScanNoService = new GetScanNoServiceImpl();
	private GetNewScanNoDataConvertUtil getNewScanNoDataConvertUtil = new GetNewScanNoDataConvertUtil();
	
	private String IndexXML;
	private Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 获取新箱号
	 * @throws Exception 
	 */
	public String getNewScanNo() throws Exception  {
		StringBuffer bufXML=new StringBuffer(1024);
		String returnStr = null;
		try {
			GetScanNoVo getScanNoVo = new GetScanNoVo();
			getNewScanNoDataConvertUtil.xmlToScanNoVo(IndexXML, getScanNoVo);
			returnStr = getScanNoService.getNewScanNo(getScanNoVo);
			if(returnStr == null){
				String newScanNo = getScanNoService.getNewScanNoResult();
				returnStr = getNewScanNoDataConvertUtil.stringToXml(newScanNo,bufXML);	
				if(returnStr == null){
					return this.outString("<IndexXML>" + bufXML.toString() + "</IndexXML>");
				}else{
					return this.outString(clientUploadCommonFun.getNewReturnMessage(returnStr));
				}
			}else{
				return this.outString(clientUploadCommonFun.getNewReturnMessage(returnStr));
			}
		}catch(Exception e){	  
			e.printStackTrace();
			returnStr = "获取新箱号失败，原因：\n" + e.toString();
			String returnXml = clientUploadCommonFun.getNewReturnMessage(returnStr);
			logger.error("获取新箱号失败，异常：",e);
			return this.outString(returnXml);
		}
	}
	public String getIndexXML() {
		return IndexXML;
	}

	public void setIndexXML(String indexXML) {
		IndexXML = indexXML;
	}


}
