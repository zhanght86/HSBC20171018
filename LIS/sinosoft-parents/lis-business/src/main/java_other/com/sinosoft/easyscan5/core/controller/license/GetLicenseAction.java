package com.sinosoft.easyscan5.core.controller.license;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.easyscan5.base.controller.BaseAction;
import com.sinosoft.easyscan5.core.service.dataconvert.impl.GetLicenseDataConvertUtil;
import com.sinosoft.easyscan5.core.service.getlicense.impl.GetLicenseServiceImpl;
import com.sinosoft.easyscan5.core.vo.easyscan.ProductVo;

public class GetLicenseAction extends BaseAction {

	private String IndexXML;
	private GetLicenseServiceImpl getLicenseService = new GetLicenseServiceImpl();
	private GetLicenseDataConvertUtil getLicenseDataConvertUtil = new GetLicenseDataConvertUtil();

	/**
	 * 获取license
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getLicense(HttpServletRequest request) throws Exception {
		ProductVo productVo = new ProductVo();
		String returnStr = null;
		
		returnStr = getLicenseDataConvertUtil.xmlToProductVo(IndexXML, productVo);
		if (returnStr != null) {
			
			return this.outString(clientUploadCommonFun.getNewReturnMessage(returnStr));
		} else {
		
			returnStr = getLicenseService.getLicense(productVo,
					request.getSession().getServletContext().getRealPath("/"));
			if (returnStr != null) {
				
				return returnStr;
			} else {
				return this.outString("<IndexXML>" + getLicenseService.getLicensefile() + "</IndexXML>");
			}
		}
	}

	public String getIndexXML() {
		return IndexXML;
	}

	public void setIndexXML(String indexXML) {
		IndexXML = indexXML;
	}
}
