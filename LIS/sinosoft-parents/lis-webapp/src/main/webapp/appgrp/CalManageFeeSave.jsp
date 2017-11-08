<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>

<%
  
  VData tVData = new VData();
  TransferData tTransferData = new TransferData();
  PreManageFeeCalBL tPreManageFeeCalBL=new PreManageFeeCalBL();
  //Êä³ö²ÎÊý
  String FlagStr = "";
  String Content = "";
   
  GlobalInput tGI = new GlobalInput(); 
  tGI=(GlobalInput)session.getValue("GI");  
  
  CErrors tError = null;
        
  
  tVData.add(tGI);
  String PrtNo=request.getParameter("PrtNo"); 
  String RiskCode=request.getParameter("RiskCode"); 
  String FeeCode=request.getParameter("FeeCode"); 
  String CalMode=request.getParameter("CalMode"); 
  String AccType=request.getParameter("AccType"); 
  String ContNo=request.getParameter("ContNo"); 
  
  loggerDebug("CalManageFeeSave",request.getParameter("PrtNo"));
  loggerDebug("CalManageFeeSave",request.getParameter("RiskCode"));
  tVData.addElement(tTransferData);
  
  if(!tPreManageFeeCalBL.submitData(PrtNo,RiskCode,CalMode,AccType,ContNo,FeeCode))
  {    
    	%>
    	<script language="javascript">
	parent.fraInterface.fm.Fee.value="0";
	
</script>
    	<%
    	}else{
    	%>
    	<script language="javascript">
	parent.fraInterface.fm.Fee.value="<%=tPreManageFeeCalBL.managefee%>";
	
</script>
    	<%
    	}
    
    
    
 %>                                 
<html>

</html>


