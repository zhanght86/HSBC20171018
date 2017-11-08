<%@ page contentType="text/html; charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@ page import = "com.sinosoft.utility.*"%>
<%@ page import = "com.sinosoft.lis.vschema.*"%>
<%@ page import = "com.sinosoft.lis.schema.*"%>
<%@ page import = "com.sinosoft.lis.db.*"%>
<%@ page import = "com.sinosoft.lis.bqgrp.*"%>
<%@ page import = "com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>

<html>
<body>


<%
  String busiName="bqgrpBqSubAppBL";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
 
 GlobalInput tG = new GlobalInput();
 tG = ( GlobalInput )session.getValue( "GI" );
 String flag="succ"; 
 String Content="";
 boolean res = true;
 
       //******************提交到后台保存数据*****************//
       loggerDebug("BqSubmitApplyConfirmSave","begin prepore data !");
       
       String tDispType = request.getParameter("DispType");       
       String tDispIdea = request.getParameter("DispIdea");
       String tNewDispPer = request.getParameter("NewDispPer");
       String tNewDispDept = request.getParameter("NewDispDept");
       String tSubNo = request.getParameter("SubNo");
       
	   	       	
	   LPSubmitApplyTraceSchema tLPSubmitApplyTraceSchema = new LPSubmitApplyTraceSchema();
	   tLPSubmitApplyTraceSchema.setSubNo(tSubNo);
	   tLPSubmitApplyTraceSchema.setDealDate(PubFun.getCurrentDate());
	   tLPSubmitApplyTraceSchema.setDealType(tDispType);
	   tLPSubmitApplyTraceSchema.setDealIdea(tDispIdea);
	   tLPSubmitApplyTraceSchema.setDispDept(tNewDispDept);
	   tLPSubmitApplyTraceSchema.setDispPer(tNewDispPer);
	   
       

       VData tVData = new VData();
       tVData.addElement( tG );
       tVData.addElement(tLPSubmitApplyTraceSchema);
       
       //BqSubAppBL tBqSubAppBL = new BqSubAppBL();
       if(!tBusinessDelegate.submitData(tVData,"",busiName))
       {
          flag="Fail";
          Content="保存失败！";
          loggerDebug("BqSubmitApplyConfirmSave","save failed!");
       }else{
          flag="success";
          Content="保存成功！";
          loggerDebug("BqSubmitApplyConfirmSave","save success!");
       }
    
%>
  </body>
  <script language="javascript">
  parent.fraInterface.afterSubmit('<%=flag%>','<%=Content%>');
  </script>
</HTML>
