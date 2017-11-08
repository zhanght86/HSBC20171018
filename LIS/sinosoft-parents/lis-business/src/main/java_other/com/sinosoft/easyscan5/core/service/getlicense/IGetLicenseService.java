package com.sinosoft.easyscan5.core.service.getlicense;

import com.sinosoft.easyscan5.core.vo.easyscan.ProductVo;


public interface IGetLicenseService {
	public String getLicense(ProductVo productVo,String path);
	public String getLicensefile();
}
