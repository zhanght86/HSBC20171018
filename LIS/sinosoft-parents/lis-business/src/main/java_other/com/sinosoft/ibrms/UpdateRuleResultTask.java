package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

import java.util.List;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LRResultDetailSchema;
import com.sinosoft.lis.schema.LRResultMainSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class UpdateRuleResultTask implements Task {
private static Logger logger = Logger.getLogger(UpdateRuleResultTask.class);
	
	private String currentDate;
	private String currentTime;
	private List   results;
	private String business;
	private String contNo;
	private int    time;
	private String operator;
	private String manageCom;
	private String calcode;
	
	private StackTraceElement[] stackElements = null;

	public String getManageCom() {
		return manageCom;
	}


	public void setStack(StackTraceElement[] stack)
	{
		this.stackElements = stack;
	}
	
	public StackTraceElement[] getStack()
	{
		return this.stackElements;
	}

	public void setManageCom(String manageCom) {
		this.manageCom = manageCom;
	}




	public String getCurrentDate() {
		return currentDate;
	}




	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}




	public String getCurrentTime() {
		return currentTime;
	}




	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}




	public List getResults() {
		return results;
	}




	public void setResults(List results) {
		this.results = results;
	}




	public String getBusiness() {
		return business;
	}




	public void setBusiness(String business) {
		this.business = business;
	}




	public String getContNo() {
		return contNo;
	}




	public void setContNo(String contNo) {
		this.contNo = contNo;
	}




	public int getTime() {
		return time;
	}




	public void setTime(int time) {
		this.time = time;
	}




	public String getOperator() {
		return operator;
	}




	public void setOperator(String operator) {
		this.operator = operator;
	}




	public Object execute() {
		MMap map = new MMap();
		// TODO Auto-generated method stub
		LRResultMainSchema mainSchema = new LRResultMainSchema();
		String resultno = PubFun1.CreateMaxNo("ibrmsResultNo", 10);
		mainSchema.setContNo(contNo);
		mainSchema.setBusiness(this.business);
		String resultFlag = "0";
		if(results.size()==0)
			resultFlag = "1";
		mainSchema.setResultFlag(resultFlag);
		mainSchema.setTime(time);
		mainSchema.setSerialNo(resultno);
	    mainSchema.setMakeDate(currentDate);;
	    mainSchema.setMakeTime( currentTime);
	    mainSchema.setOperator(operator);
	    mainSchema.setManageCom(manageCom);
		
		//dump 
	    if(stackElements != null)
	    {
	        for(int i = 0; i < stackElements.length; i++)
	        {
//	        	 logger.debug(stackElements[i].getClassName());
//	  	        logger.debug(stackElements[i].getFileName());
//	  	        logger.debug(stackElements[i].getLineNumber());
//	  	        logger.debug(stackElements[i].getMethodName());
	  	        
	        	String tClasName = stackElements[i].getClassName();
	        	String tMethodName = stackElements[i].getMethodName();
	        	
	        	if(tClasName.equals("com.sinosoft.lis.pubfun.Calculator"))
	        	{
	        		if(i<stackElements.length-1)
	        		{
	        			tClasName = stackElements[i+1].getClassName();
	        			tMethodName = stackElements[i+1].getMethodName();
	        		}
	        		
	        		mainSchema.setCalClass(tClasName);
	        		mainSchema.setCalMethod(tMethodName);
	        		if(!tClasName.equals("com.sinosoft.lis.pubfun.Calculator"))
	        		{
	        			break;
	        		}
	        	}
	      
	//        logger.debug("-----------------------------------");
	        }
	    }
	    
	    
	    
	    map.put(mainSchema, "INSERT");
		
		
		
		
		
		for(int i=0;i<results.size();i++){
			SQLTaskResult result = (SQLTaskResult)results.get(i);
			LRResultDetailSchema detailSchema = new LRResultDetailSchema();
			detailSchema.setSerialNo(resultno);
			detailSchema.setVersion(result.getVersion());
			detailSchema.setNo(i);
			detailSchema.setRuleId(result.getRuleid());
			detailSchema.setTemplateId(result.getTemplateId());
			detailSchema.setUWLevel(result.getUWLevel());
			
			if (result.getErrors().getErrorCount() > 0)
			{
				detailSchema.setExceptionFlag("0");
				CError error = result.getErrors().getError(0);
				detailSchema.setResult( error.errorMessage);
				
			}else{
				detailSchema.setExceptionFlag("1");
				detailSchema.setResult(result.getResult());
			}
			String sql="select Version from LRTemplate where ID='?ID?'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("ID", result.getTemplateId());
		/*	ExeSQL exec = new ExeSQL();
			SSRSC ssrsc=exec.execSQL(sql);
			detailSchema.setVersion(result.g);*/
			
			map.put(detailSchema, "INSERT");
		}
		
		
		
	
		
		PubSubmit tPubSubmit = new PubSubmit();
		VData mInputData = new VData();
		mInputData.add(map);
		if (!tPubSubmit.submitData(mInputData, "INSERT")) {
			// @@错误处理
		   logger.debug("error");
		}
		
		


		
			
		return null;
	}




	public String getCalcode() {
		return calcode;
	}




	public void setCalcode(String calcode) {
		this.calcode = calcode;
	}

}
