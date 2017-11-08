<%@page import="java.text.*"%>
<%@page import="java.util.Vector"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.*"%>
<%!
	
	String busiNameLog = "UILog4j";
	BusinessDelegate tBusinessDelegateLog = BusinessDelegate.getBusinessDelegate();
	VData tData = new VData();
	
	public void loggerDebug(String tMsg)
	{
		tData.clear();
		tData.add(tMsg);
		tBusinessDelegateLog.submitData(tData, "debug",busiNameLog);
	}
	
	public void loggerDebug(String tPage,String tMsg)
	{
		tData.clear();
		tData.add(tMsg);
		tData.add(tPage);
		tBusinessDelegateLog.submitData(tData, "debug",busiNameLog);
	}
	
	
	public void loggerError(String tMsg)
	{
		tData.clear();
		tData.add(tMsg);
		tBusinessDelegateLog.submitData(tData, "error",busiNameLog);
	}
	
	public void loggerError(String tPage,String tMsg)
	{
		tData.clear();
		tData.add(tMsg);
		tData.add(tPage);
		tBusinessDelegateLog.submitData(tData, "error",busiNameLog);
	}
%>
