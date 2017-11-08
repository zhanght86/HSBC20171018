<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tbgrp.*"%>
<%
//输出参数
CErrors tError = null;
String tRela  = "";                
String FlagStr="";
String Content = "";
String tAction = "";
String tOperate = "";
String wFlag = "";

  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  
    VData tVData = new VData();
    //工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
    wFlag = request.getParameter("WorkFlowFlag");
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("GrpContNo", request.getParameter("GrpContNo"));
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));  
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    
    tVData.add(mTransferData);
    tVData.add(tG);
    loggerDebug("GrpQuestInputConfirm","wFlag="+wFlag);
    loggerDebug("GrpQuestInputConfirm","MissionID="+request.getParameter("MissionID"));
    loggerDebug("GrpQuestInputConfirm","SubM="+request.getParameter("SubMissionID"));
    loggerDebug("GrpQuestInputConfirm","GrpContNo="+request.getParameter("GrpContNo"));
    loggerDebug("GrpQuestInputConfirm","-------------------start workflow---------------------");
    GrpTbWorkFlowUI tGrpTbWorkFlowUI = new GrpTbWorkFlowUI();
  if( !tGrpTbWorkFlowUI.submitData( tVData, wFlag ) ) {
      Content = " 处理失败，原因是: " + tGrpTbWorkFlowUI.mErrors.getError(0).errorMessage;
      FlagStr = "Fail";
  }else {
      Content = " 处理成功！";
      FlagStr = "Succ";
  }
 loggerDebug("GrpQuestInputConfirm","-------------------end workflow---------------------");
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
