<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.cardgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>

<%
  
  LCContSchema tLCContSchema = new LCContSchema();
  LCInsuredSchema tmLCInsuredSchema =new LCInsuredSchema();
  LCDeleteAllInsuredBL tLCDeleteAllInsuredBL=new LCDeleteAllInsuredBL();
  VData tVData = new VData();
  loggerDebug("DeleteAllInsured","begin delete!");
  //�������
  String FlagStr = "";
  String Content = "";
   
  GlobalInput tGI = new GlobalInput(); 
  tGI=(GlobalInput)session.getValue("GI");  
  
  
        
  tLCContSchema.setGrpContNo(request.getParameter("ProposalGrpContNo"));  
 
 
  
  tVData.add(tLCContSchema);
  tVData.add(tGI);
  
  
  if (!tLCDeleteAllInsuredBL.submitData(tVData,"DELETE||MAIN"))
    {
     Content = "����ʧ�ܣ�ԭ����:" + tLCDeleteAllInsuredBL.mErrors.getFirstError();
    	FlagStr = "Fail";
    }else{
      Content ="�����ɹ���";
    	FlagStr = "Succ";
    	
    }
    
    
 %>                                 
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	
</script>
</html>


