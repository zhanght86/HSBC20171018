package com.sinosoft.easyscan5.base.controller;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.sinosoft.easyscan5.core.controller.clientupload.ClientUploadCommonFun;



public class BaseAction {
	Logger log = Logger.getLogger(BaseAction.class);
	public ClientUploadCommonFun clientUploadCommonFun = new ClientUploadCommonFun();

	/**
	 *输出非json格式字符串
	 * @param object
	 * @throws IOException
	 */
	public String outString(String string) throws IOException{
//		response.setContentType(Constants.CONTENTTYPE_UTF8);				
		log.info(string);
		return string;
	}
	/**
	 * 输出错误信息
	 * @param str
	 * @throws Exception 
	 */
	public String outErrorString(String strErr) throws Exception{
		return this.outString(clientUploadCommonFun.getReturnXmlMessage(strErr, "-500"));
	}
}
