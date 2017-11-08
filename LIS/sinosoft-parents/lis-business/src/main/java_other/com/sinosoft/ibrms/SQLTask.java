package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.sinosoft.lis.db.LRTemplateDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LRTemplateSchema;
import com.sinosoft.lis.vschema.LRTemplateSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;

public class SQLTask implements Task {
private static Logger logger = Logger.getLogger(SQLTask.class);
	
	protected String sql;
	protected String parameters;
	protected String boms;
	protected List listBoms;
	protected RuleTask ruleTask ;
	protected String templateId;
	protected String ruleName;
	
//	prote
	

	 public SQLTask(String sql, String parameters, String boms,String templateid, List listBoms, RuleTask ruleTask,
			 String rulename) {
		super();		
		this.sql = sql;
		this.parameters = parameters;
		this.boms = boms;
		this.listBoms = listBoms;
		this.ruleTask = ruleTask;
		this.templateId = templateid;
		this.ruleName = rulename;
	}



	public String getBoms() {
		return boms;
	}



	public void setBoms(String boms) {
		this.boms = boms;
	}






	public List getListBoms() {
		return listBoms;
	}



	public void setListBoms(List listBoms) {
		this.listBoms = listBoms;
	}



	public String getParameters() {
		return parameters;
	}



	public void setParameters(String parameters) {
		this.parameters = parameters;
	}



	public RuleTask getRuleTask() {
		return ruleTask;
	}



	public void setRuleTask(RuleTask ruleTask) {
		this.ruleTask = ruleTask;
	}



	public String getSql() {
		return sql;
	}



	public void setSql(String sql) {
		this.sql = sql;
	}

	//tongmeng 2010-12-27 add
	//为支持规则测试增加
	// 执行SQLTask
	public List executeForTest() {

		List results = null;
		try {

			Rule rule = new Rule();
			// 解析、执行
			// tongmeng 2010-12-24 modify
			// 先解析当前的SQL,判断是否需要递归调用.
			String tFinalSQL = "";
			// tFinalSQL = this.explainSQL(this.sql);
			TransferData tTransferData = new TransferData();
			// 先判断是否有子算法.如果有的话,先把子算法结果获取出来
			tFinalSQL = sql;
			if(sql.indexOf("BOMSubCal")!=-1)
			{
				calSubResult(this.ruleName, tTransferData, rule);

				tFinalSQL = replaceSQL(sql, tTransferData);
			}
			//tongmeng 
			if(tFinalSQL.indexOf("$RuleData$")!=-1)
	    	{
				tFinalSQL = RuleInfoPrepare.replaceRuleDataSQL(tFinalSQL,this.templateId);
	    	}
			results = rule.execRule(listBoms, tFinalSQL, this.parameters,
					this.boms,this.ruleName);
			for (int i = 0; i < results.size(); i++) {
				SQLTaskResult result = (SQLTaskResult) results.get(i);
				result.setTemplateId(templateId);
				result.setRulename(ruleName);
			}

		} catch (Exception e) {

		}
		return results;
	}


	// 执行SQLTask
	public List execute() {
		try{
		Rule rule = new Rule();
		// 解析、执行
		//tongmeng 2010-12-24 modify
		//先解析当前的SQL,判断是否需要递归调用.
		String tFinalSQL = "";
		//tFinalSQL = this.explainSQL(this.sql);
		TransferData tTransferData = new TransferData();
		//先判断是否有子算法.如果有的话,先把子算法结果获取出来
		calSubResult(this.ruleName,tTransferData,rule);

		tFinalSQL = replaceSQL(sql,tTransferData);
		//tongmeng 
		if(tFinalSQL.indexOf("$RuleData$")!=-1)
    	{
			tFinalSQL = RuleInfoPrepare.replaceRuleDataSQL(tFinalSQL,this.templateId);
    	}
		
		TransferData tNewTransferData = new TransferData();
		Vector tNames = tTransferData.getValueNames();
		for(int i=0;i<tNames.size();i++)
		{
			String tName = (String)tNames.get(i);
			String tValue = (String)tTransferData.getValueByName(tName);
			logger.debug("tName:"+tName+":tValue:"+tValue);
			tNewTransferData.setNameAndValue(tName.substring(tName.indexOf(".")+1), tValue);
			
		}
		
		List results = rule.execRule(listBoms, tFinalSQL,this.parameters, this.boms,this.ruleName);
		for(int i=0;i<results.size();i++){
		    SQLTaskResult result = (SQLTaskResult)results.get(i);
		    result.setTemplateId(templateId);
		    result.setRulename(ruleName);
		    result.setSubCalDetail(tNewTransferData);
		}
		logger.debug("sql=---"+this.sql);
		TaskContext context = new DefaultTaskContext();
		context.setAttribute("results", results);
		context.setAttribute("task", this);
		this.ruleTask.workDone(context);
		}catch(Exception ex){
			// Can't return information easily. Modified by zzm.
			ex.printStackTrace();
			TaskContext context = new DefaultTaskContext();
			context.setAttribute("results", new ArrayList());
			context.setAttribute("task", this);
			this.ruleTask.workDone(context);
		}
		return null;
		

	}

	
	
	private  String calSubResult(String tCalCode,TransferData tTransferData,Rule rule)
	{
		String tRes = "";
		//LRTemplateSet tempLRTemplateSet = new LRTemplateSet();
		//LRTemplateDB tempLRTemplateDB =  new LRTemplateDB();
		ExeSQL  tExeSQL = new ExeSQL();
		SSRS tempSSRS = new SSRS();
		
		//String tSQL_CurrentCal = "select * from lrtemplate where rulename='"+tCalCode+"' ";
		String tSQL_CurrentCal = "select rulename,sqlstatement,sqlparameter,boms,id from lrtemplate where rulename='?tCalCode?' "
		                       + " union "
		                       + " select rulename,sqlstatement,sqlparameter,boms,id from lrtemplatet where rulename='?tCalCode?' "
		                       ;
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQL_CurrentCal);
		sqlbv.put("tCalCode", tCalCode);
		//tempLRTemplateSet = tempLRTemplateDB.executeQuery(tSQL_CurrentCal);
		tempSSRS = tExeSQL.execSQL(sqlbv);
		
		for(int n=1;n<=tempSSRS.getMaxRow();n++)
		{
			String tRuleName = tempSSRS.GetText(n,1);
			String tSQLStatement = tempSSRS.GetText(n,2);
			String tSQLParameter = tempSSRS.GetText(n,3);
			String tBoms = tempSSRS.GetText(n,4);
			String tTemplateID =  tempSSRS.GetText(n,5);
			//判断有没有子算法,有的话就递归
			String[] tSubCalArray =  this.getSubCalArray(tSQLParameter);
			
			if(tSubCalArray!=null)
			{
				for(int i=0;i<tSubCalArray.length;i++)
				{
					String tCurrentCal = tSubCalArray[i];
					String tResult = calSubResult(tCurrentCal,tTransferData,rule);
					tTransferData.removeByName("BOMSubCal."+tCurrentCal);
					tTransferData.setNameAndValue("BOMSubCal."+tCurrentCal, tResult);
				}
			}
			
			//执行SQL之前先做替换
			String tSQL = this.replaceSQL(tSQLStatement, tTransferData);
			logger.debug("**********************************************************");
			
			logger.debug("::"+tRuleName);
	    	if(tSQL.indexOf("$RuleData$")!=-1)
	    	{
	    		tSQL = RuleInfoPrepare.replaceRuleDataSQL(tSQL,tTemplateID);
	    	}
	    	
			logger.debug("**********************************************************");
			List results = rule.execRule(listBoms,tSQL,tSQLParameter,tBoms,tRuleName);
			for(int i=0;i<results.size();i++){
			    SQLTaskResult result = (SQLTaskResult)results.get(i);
			    //result.setTemplateId(templateId);
			    //result.setRulename(ruleName);
			   // tTransferData.removeByName(tRuleName);
				//tTransferData.setNameAndValue(tRuleName, result.getResult());
			    logger.debug("Rulename:"+tRuleName+"$$Result:"+result.getResult());
			    tRes = result.getResult();
			    return result.getResult();
			}
		}
		
		
		//for(int n=1;n<=tempLRTemplateSet.size();n++)
		
		/*for(int n=1;n<=tempLRTemplateSet.size();n++)
		
		{
			LRTemplateSchema tLRTemplateSchema = new LRTemplateSchema();
			tLRTemplateSchema.setSchema(tempLRTemplateSet.get(n));
			//判断有没有子算法,有的话就递归
			String[] tSubCalArray =  this.getSubCalArray(tLRTemplateSchema.getSQLParameter());
			
			if(tSubCalArray!=null)
			{
				for(int i=0;i<tSubCalArray.length;i++)
				{
					String tCurrentCal = tSubCalArray[i];
					String tResult = calSubResult(tCurrentCal,tTransferData,rule);
					tTransferData.removeByName("BOMSubCal."+tCurrentCal);
					tTransferData.setNameAndValue("BOMSubCal."+tCurrentCal, tResult);
				}
			}
			
			//执行SQL之前先做替换
			String tSQL = this.replaceSQL(tLRTemplateSchema.getSQLStatement(), tTransferData);
			logger.debug("**********************************************************");
			
			logger.debug("::"+tLRTemplateSchema.getRuleName());
			logger.debug("**********************************************************");
			List results = rule.execRule(listBoms,tSQL,tLRTemplateSchema.getSQLParameter(),tLRTemplateSchema.getBOMs());
			for(int i=0;i<results.size();i++){
			    SQLTaskResult result = (SQLTaskResult)results.get(i);
			    //result.setTemplateId(templateId);
			    //result.setRulename(ruleName);
			    tTransferData.removeByName(tLRTemplateSchema.getRuleName());
				tTransferData.setNameAndValue(tLRTemplateSchema.getRuleName(), result.getResult());
			    logger.debug("Rulename:"+tLRTemplateSchema.getRuleName()+"$$Result:"+result.getResult());
			    tRes = result.getResult();
			    return result.getResult();
			}
		}
		*/
		
		return tRes;
	}
	
	private String[] getSubCalArray(String tSQLParameters)
	{
		String[] tRes = null;
		//BOMCont.ContPrem,BOMSubCal.RU01,BOMSubCal.RU02
		ArrayList tArrayList = new ArrayList();
		String[] tSplitArr = tSQLParameters.split(","); 
		for(int i=0;i<tSplitArr.length;i++)
		{
			
			
			if(tSplitArr[i].indexOf("BOMSubCal")==-1)
			{
				continue;
			}
			
			//String[] tempArry = tSplitArr[i].split(".");
			String[] tempArry = PubFun.split(tSplitArr[i],".");
			for(int n=0;n<tempArry.length;n++)
			{
				if(tempArry[n].equals("BOMSubCal"))
				{
					continue;
				}
				else
				{
					tArrayList.add(tempArry[n]);
				}
			}
			
		}
		if(tArrayList.size()==0)
		{
			return null;
		}
		else
		{
			tRes = new String[tArrayList.size()];
			for(int i=0;i<tArrayList.size();i++)
			{
				tRes[i] = (String)tArrayList.get(i);
			}
			return tRes;
		}
		//
	}
	
	private String replaceSQL(String tSQL,TransferData tTransferData)
	{
		String tSQL_final = tSQL;
		Vector tNameVector = tTransferData.getValueNames();
		for(int i=0;i<tNameVector.size();i++)
		{
			String tName = (String)tNameVector.get(i);
			String tValue = (String)tTransferData.getValueByName(tName);
			logger.debug("before replace:"+tSQL_final);
			tSQL_final = StrTool.replaceEx(tSQL_final, "?"+tName+"?", tValue);
			logger.debug("after replace:"+tSQL_final);
			
		}
		
		return tSQL_final;
	}
	
	public String getTemplateId() {
		return templateId;
	}



	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	

}
