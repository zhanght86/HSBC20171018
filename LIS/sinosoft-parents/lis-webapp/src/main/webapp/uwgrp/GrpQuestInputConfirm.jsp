<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tbgrp.*"%>
<%
//�������
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
    //�����������ͱ𣬸��ݴ�ֵ�����ID��ȡ��������ִ�о���ҵ���߼�
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
      Content = " ����ʧ�ܣ�ԭ����: " + tGrpTbWorkFlowUI.mErrors.getError(0).errorMessage;
      FlagStr = "Fail";
  }else {
      Content = " ����ɹ���";
      FlagStr = "Succ";
  }
 loggerDebug("GrpQuestInputConfirm","-------------------end workflow---------------------");
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
