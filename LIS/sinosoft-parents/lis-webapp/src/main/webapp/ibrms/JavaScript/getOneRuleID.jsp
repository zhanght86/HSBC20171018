<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="../../common/jsp/Log4jUI.jsp"%>  
<%@ page import="com.sinosoft.lis.pubfun.PubFun1"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@ page import="java.util.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	String tRuleName = request.getParameter("RuleName");
	
	//String RuleId = PubFun1.CreateMaxNo("ibrms" + tRuleName, 4);
	TransferData sTransferData=new TransferData();
    sTransferData.setNameAndValue("RuleName", tRuleName);
  
    
    VData sVData = new VData();
    sVData.add(sTransferData);
    String RuleId="";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    if(tBusinessDelegate.submitData(sVData, "", "PubFun1UI"))
    {
    	RuleId = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
    }   
	// out.print(RuleId);
	response.getWriter().write(RuleId);
%>
