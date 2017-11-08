<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@ page language="java" pageEncoding="GBK"%>

<%
//	RuleMapCached mRuleMapCached = RuleMapCached.getInstance();
	String busiName = "TaskTabMapInit";
	//String tUserCode = request.getParameter("UserCode");
	VData tVData = new VData();
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
	tVData.add(tG);
	String tOutJSON = "";
	BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
	if (!tBusinessDelegate.submitData(tVData, "",busiName)){
    }else{
    	tOutJSON = (String)tBusinessDelegate.getResult().get(0);
	}
	

	 loggerDebug("InitTaskTab",tOutJSON);
	 out.print(tOutJSON);

%>
