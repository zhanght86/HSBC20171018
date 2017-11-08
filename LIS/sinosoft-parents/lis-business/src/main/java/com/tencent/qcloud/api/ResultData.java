package com.tencent.qcloud.api;

public class ResultData {
	private String accessUrl;
    private String resourcePath;
    private String sourceUrl;
    private String url;
    
    public ResultData(String accessUrl, String resourcePath, String sourceUrl, String url){
    	this.accessUrl = accessUrl;
    	this.resourcePath = resourcePath;
    	this.sourceUrl = sourceUrl;
    	this.url = url;
    }
    
    public String getAccessUrl() {
		return accessUrl;
	}
    
	public String getResourcePath() {
		return resourcePath;
	}
	
	public String getSourceUrl() {
		return sourceUrl;
	}
	
	public String getUrl() {
		return url;
	}
}
