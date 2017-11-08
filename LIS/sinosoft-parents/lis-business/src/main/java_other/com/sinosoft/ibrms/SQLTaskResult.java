package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;

public class SQLTaskResult {
private static Logger logger = Logger.getLogger(SQLTaskResult.class);
	private String UWLevel;
	private String ruleid;
	private String result;
	private String templateId;
	private String rulename;
	private int    version;
	private Map    mapUsedBoms = null;
	
	//tongmeng 2011-06-02 add
	//增加提示信息的返回
	private HashMap ResultMessage = new HashMap();
	
	private TransferData mSubCalDetail = new TransferData();
	
	private CErrors errors = new CErrors();
	
	public String getUWLevel() {
		return UWLevel;
	}
	public void setUWLevel(String level) {
		UWLevel = level;
	}
	
	public void setSubCalDetail(TransferData tTransferData) {
		this.mSubCalDetail = tTransferData;
	}
	public TransferData getSubCalDetail() {
		return this.mSubCalDetail;
	}
	
	
	public void setResultMessage(String tLanguage,String tMSg)
	{
		this.ResultMessage.put(tLanguage, tMSg);
	}
	
	public HashMap getResultMessage()
	{
		return this.ResultMessage;
	}
	
	public String getMessageByLanguage(String tLanguage)
	{
		return this.ResultMessage.get(tLanguage)==null?"":(String)this.ResultMessage.get(tLanguage);
	}
	public String getRuleid() {
		return ruleid;
	}
	public void setRuleid(String ruleid) {
		this.ruleid = ruleid;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Map getMapUsedBoms() {
		return mapUsedBoms;
	}
	public void setMapUsedBoms(Map mapUsedBoms) {
		this.mapUsedBoms = mapUsedBoms;
	}
	public SQLTaskResult(Map mapUsedBoms, String result, String ruleid) {
		super();
		this.mapUsedBoms = mapUsedBoms;
		this.result = result;
		this.ruleid = ruleid;
	}
	
	public SQLTaskResult (){
		
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getRulename() {
		return rulename;
	}
	public void setRulename(String rulename) {
		this.rulename = rulename;
	}
	public CErrors getErrors() {
		return errors;
	}
	
	public void addError(CError error){
		this.errors.addOneError(error);
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	

}
