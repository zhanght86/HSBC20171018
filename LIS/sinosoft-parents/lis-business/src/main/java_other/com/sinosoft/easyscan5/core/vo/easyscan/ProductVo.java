package com.sinosoft.easyscan5.core.vo.easyscan;

public class ProductVo {
	private String productName;
	private String productVersion;
	public String easyName = "EasyScan";
	public String easyViewName = "EasyView";
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductVersion() {
		return productVersion;
	}
	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}
	public boolean isEasyScanProduct(String productName){
		if(easyName.equals(productName)){
			return true;
		}
		return false;
	}
	public boolean isEasyViewProduct(String productName){
		if(easyViewName.equals(productName)){
			return true;
		}
		return false;
	}
}
