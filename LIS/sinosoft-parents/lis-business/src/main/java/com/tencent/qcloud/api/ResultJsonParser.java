package com.tencent.qcloud.api;

import org.json.JSONException;
import org.apache.log4j.Logger;

public class ResultJsonParser {
	private static Logger logger = Logger.getLogger(ResultJsonParser.class);

    public static UploadResult getUploadResultFromJsonString(String jsonStr) throws JSONException{
    	UploadResult result = null;
    	ResultData data = null;
    	org.json.JSONObject jo = null;
    	try{
    	    jo = new org.json.JSONObject(jsonStr);
    	}catch(Exception e){
    		logger.debug(e.getMessage());
    	}
    	if(jo == null){
    		return null;
    	}
    	Integer code = null;
    	if(!jo.isNull("code"))
    		code = jo.getInt("code");
    	String message = null;
    	if(!jo.isNull("message"))
    	    message = jo.getString("message");
    	org.json.JSONObject rd = null;
    	if(!jo.isNull("data"))
    		rd = jo.getJSONObject("data");
    	if(rd != null){
    		String access_url = null;
    		if(!rd.isNull("access_url"))
    		    access_url = rd.getString("access_url");
    		String resource_path = null;
    		if(!rd.isNull("resource_path")) 
    		    resource_path = rd.getString("resource_path");
    		String source_url = null;
    		if(!rd.isNull("source_url"))
    		    source_url = rd.getString("source_url");
    		String url = null;
    		if(!rd.isNull("url"))
    		    url = rd.getString("url");
    		data = new ResultData(access_url, resource_path, source_url, url);
    	}
    	if(code != null || message != null || data != null){
    		result = new UploadResult(code, message, data);
    	}
    	return result;
    }
}
