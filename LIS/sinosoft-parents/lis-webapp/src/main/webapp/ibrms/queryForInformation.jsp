<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@ page import="java.util.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>

<%
   String DTTableName=request.getParameter("DTTableName");
	 String LRTemplateID=request.getParameter("LRTemplateID");

	// String json= queryClass.queryDTData(DTTableName,LRTemplateID);
	 String json = "";
	 TransferData sTransferData=new TransferData();
     sTransferData.setNameAndValue("DTTableName", DTTableName);
     sTransferData.setNameAndValue("LRTemplateID", LRTemplateID);
     
     VData sVData = new VData();
     sVData.add(sTransferData);
     String viewParameter="";
     BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
     if(tBusinessDelegate.submitData(sVData, "", "QueryForDTDataUI"))
     {
    	 json = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
     }   
	 

//   loggerDebug("queryForInformation","准备好的JSON数据是："+json);

   out.clear();
   out.write(json);
%>
