package com.sinosoft.easyscan5.core.service.getlicense.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sinosoft.easyscan5.base.service.impl.BaseServiceImpl;
import com.sinosoft.easyscan5.core.service.getlicense.IGetLicenseService;
import com.sinosoft.easyscan5.core.vo.easyscan.ProductVo;

public class GetLicenseServiceImpl extends BaseServiceImpl implements
		IGetLicenseService {
	private final Log logger = LogFactory.getLog(getClass());
	private String licenseFile;
	public String getLicense(ProductVo productVo,String path) {
		String returnStr = null;
		String productName = productVo.getProductName();
		try {
			if(productName !=null && productVo.isEasyScanProduct(productName)){
				String easyLicensePath = path + File.separatorChar + "WEB-INF" 
					+ File.separatorChar + "classes" + File.separatorChar +"easyscan5"+ File.separatorChar 
					+ "license.lic";
				readLicenseFile(easyLicensePath);
			}
			if(productName !=null && productVo.isEasyViewProduct(productName)){
				String easyViewPath = path + File.separatorChar + "WEB-INF" 
					+ File.separatorChar + "classes" + File.separatorChar
					+  "license.lic";
				readLicenseFile(easyViewPath);
			}
		} catch (Exception e) {
			returnStr = "获取license失败";
			logger.error("获取license失败" + e.getMessage());
		}
		return returnStr;
	}

	public String getLicensefile(){
		return licenseFile;
	}
	
	public void readLicenseFile(String filePath) throws Exception{
		StringBuffer sb = new StringBuffer();
		File file = new File(filePath);
		BufferedReader reader = null;
		try {
			InputStreamReader streamReader = new InputStreamReader(new FileInputStream(file), "UTF-8");  
			reader = new BufferedReader(streamReader);
			
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
		}finally {
			if (reader != null) {
				reader.close();
			}
		}
		licenseFile = sb.toString();
	}

}
