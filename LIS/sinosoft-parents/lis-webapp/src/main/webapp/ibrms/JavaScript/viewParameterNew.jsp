<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="../../common/jsp/Log4jUI.jsp"%>  
<%@ page import="com.sinosoft.ibrms.*"%>
<%@ page import="java.sql.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@ page import="java.util.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	String tableName = request.getParameter("tableName");
	String id = request.getParameter("id");
	
	String tLan = request.getParameter("lan");
	//QueryViewParameter queryViewParameter=new QueryViewParameter();
		TransferData sTransferData=new TransferData();
	      sTransferData.setNameAndValue("tableName", tableName);
	      sTransferData.setNameAndValue("id", id);
	      sTransferData.setNameAndValue("tLan", tLan);
	      
	      VData sVData = new VData();
	      sVData.add(sTransferData);
	      String viewParameter="";
	      BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	      if(tBusinessDelegate.submitData(sVData, "", "QueryViewParameterUI"))
	      {
	    	  viewParameter = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0);
	      }   
	//String viewParameter=queryViewParameter.queryForViewParameter(tableName,id,tLan);
	
	response.getWriter().write(viewParameter);
%>
