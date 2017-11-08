package com.sinosoft.easyscan5.core.controller.getscanno;

import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.easyscan5.base.controller.BaseAction;
import com.sinosoft.easyscan5.core.service.dataconvert.impl.QueryHisScanNoDataConvertUtil;
import com.sinosoft.easyscan5.core.service.getscanno.impl.GetScanNoServiceImpl;
import com.sinosoft.easyscan5.core.vo.easyscan.GetScanNoVo;


public class QueryHisScanNoAction extends BaseAction {

	private GetScanNoServiceImpl getScanNoService = new GetScanNoServiceImpl();
	private QueryHisScanNoDataConvertUtil queryHisScanNoDataConvertUtil = new QueryHisScanNoDataConvertUtil();
	
	private String IndexXML;
	private Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 获取历史箱号
	 * @throws Exception 
	 */
	public String queryHisScanNo() throws Exception  {
		StringBuffer bufXML=new StringBuffer(1024);
		String returnStr = null;
		try {
			GetScanNoVo getScanNoVo = new GetScanNoVo();
			queryHisScanNoDataConvertUtil.xmlToScanNoVo(IndexXML, getScanNoVo);
			returnStr = getScanNoService.queryHisScanNo(getScanNoVo);
			if(returnStr == null){
				List hisScanNoList = getScanNoService.getHisScanNoResult();
				returnStr = queryHisScanNoDataConvertUtil.listToXml(hisScanNoList,bufXML);	
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
			returnStr = "查询历史箱号失败，原因：\n" + e.toString();
			String returnXml = clientUploadCommonFun.getNewReturnMessage(returnStr);
			logger.error("查询历史箱号失败，原因：",e);
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
