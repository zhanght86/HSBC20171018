<%@page import="com.sinosoft.utility.SSRS"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@include file="../jsp/Log4jUI.jsp"%>  
<%@ page language="java" pageEncoding="GBK"%>

<%
	String subType = request.getParameter("subType");


	 String sql ="Select objCode,picIndex,x1,y1,x2,y2 from LDScanPosition where subType = '"+subType+"'";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	 TransferData sTransferData=new TransferData();
	 sTransferData.setNameAndValue("SQL", sql);
	 VData tVData = new VData();
	 tVData.add(sTransferData);
	 tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	 if(tBusinessDelegate.submitData(tVData, "execSQL", "ExeSQLUI")){
	      SSRS tSSRS=(SSRS)tBusinessDelegate.getResult().getObjectByObjectName("SSRS", 0);
	      String outjson = "[";
	      for(int i = 1;i<=tSSRS.MaxRow;i++){
		      String tObjCode = tSSRS.GetText(i,1);
		      String tPicIndex = tSSRS.GetText(i,2);
		      String x1 = tSSRS.GetText(i,3);
		      String y1 = tSSRS.GetText(i,4);
		      String x2 = tSSRS.GetText(i,5);
		      String y2 = tSSRS.GetText(i,6);
		      outjson += "{ \"objCode\":\""+tObjCode+"\",\"picIndex\":\""+tPicIndex+"\",\"x1\":\""+x1+"\",\"y1\":\""+y1+"\",\"x2\":\""+x2+"\",\"y2\":\""+y2+"\"}";
		      if(i<tSSRS.MaxRow){
		    	  outjson+=",";
		      }
	      }
	      outjson += "]";
	      
	      out.print(outjson);
	  }	



%>
