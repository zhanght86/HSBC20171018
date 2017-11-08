package com.tencent.qcloud.api;

public class UploadResult {
	private Integer code;
    private String message;
    private ResultData data;
    public UploadResult(Integer code, String message, ResultData data)
    {
    	this.code = code;
    	this.message = message;
    	if(data != null)
    	    this.data = new ResultData(data.getAccessUrl(), data.getResourcePath(), data.getSourceUrl(), data.getUrl());
    	else
    		this.data = null;
    }
    
    public Integer getCode() {
		return code;
	}
    
	public String getMessage() {
		return message;
	}
	
	public ResultData getData(){
		return this.data;
	}
	
}
