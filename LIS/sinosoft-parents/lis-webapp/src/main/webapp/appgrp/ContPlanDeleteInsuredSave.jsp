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
  
  
  LCInsuredSchema tmLCInsuredSchema =new LCInsuredSchema();
  LCDeleteContPlanInsuredBL tLCDeleteContPlanInsuredBL=new LCDeleteContPlanInsuredBL();
  VData tVData = new VData();
  loggerDebug("ContPlanDeleteInsuredSave","begin delete!");
  //�������
  String FlagStr = "";
  String Content = "";
   
  GlobalInput tGI = new GlobalInput(); 
  tGI=(GlobalInput)session.getValue("GI");  
  
  
        
  tmLCInsuredSchema.setGrpContNo(request.getParameter("GrpContNo"));
  tmLCInsuredSchema.setContPlanCode(request.getParameter("ContPlan"));  
 
 loggerDebug("ContPlanDeleteInsuredSave","^^^^^^^^^^^:"+request.getParameter("GrpContNo"));
 loggerDebug("ContPlanDeleteInsuredSave","^^^^^^^^^^^:"+request.getParameter("ContPlan")); 
  tVData.add(tmLCInsuredSchema);
  tVData.add(tGI);
  
  
  if (!tLCDeleteContPlanInsuredBL.submitData(tVData,"DELETE||MAIN"))
    {
     Content = "����ʧ�ܣ�ԭ����:" + tLCDeleteContPlanInsuredBL.mErrors.getFirstError();
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


