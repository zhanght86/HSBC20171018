package com.sinosoft.easyscan5.core.vo;

public class ImagePathVo {
	/**服务器IP地址**/
	private String serverIp;
	/**服务器端口号**/
	private String serverPort;
	/**服务器访问名,如：image **/
	private String serverAccName;
	/**文件固定存放路径,如：xerox/EasyScan/ **/
	private String serverPicPath;
	/**服务器绝对路径**/
	private String serverBasePath;
	/**文件存放路径 如：86/2012/10/11/**/
	private String pagePicPath;
	/**文件名**/
	private String pageName;
	/**文件后缀**/
	private String pageSuffix;
	/**服务器访问路径,如：http://10.100.11.111:8080/image/ **/
	private String serverUrl;
	/**文件相对路径,如：xerox/EasyScan/86/2012/10/11/F3343434.gif **/
	private String relativePath;
	/**filenet key**/
	private String fnDocKey;
	/**影像访问路径**/
	private String picRetrUrl;
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getServerPort() {
		return serverPort;
	}
	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}
	public String getServerAccName() {
		return serverAccName;
	}
	public void setServerAccName(String serverAccName) {
		this.serverAccName = serverAccName;
	}
	public String getServerUrl() {
		return serverUrl;
	}
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	public String getRelativePath() {
		return relativePath;
	}
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	public String getFnDocKey() {
		return fnDocKey;
	}
	public void setFnDocKey(String fnDocKey) {
		this.fnDocKey = fnDocKey;
	}
	public String getServerPicPath() {
		return serverPicPath;
	}
	public void setServerPicPath(String serverPicPath) {
		this.serverPicPath = serverPicPath;
	}
	public String getPagePicPath() {
		return pagePicPath;
	}
	public void setPagePicPath(String pagePicPath) {
		this.pagePicPath = pagePicPath;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getPageSuffix() {
		return pageSuffix;
	}
	public void setPageSuffix(String pageSuffix) {
		this.pageSuffix = pageSuffix;
	}
	public String getPicRetrUrl() {
		return picRetrUrl;
	}
	public void setPicRetrUrl(String picRetrUrl) {
		this.picRetrUrl = picRetrUrl;
	}
	public String getServerBasePath() {
		return serverBasePath;
	}
	public void setServerBasePath(String serverBasePath) {
		this.serverBasePath = serverBasePath;
	}
	
}
