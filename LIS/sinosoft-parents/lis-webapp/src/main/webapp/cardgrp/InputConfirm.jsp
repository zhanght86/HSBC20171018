<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.cardgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.cardgrp.*"%>
   <%@page import="com.sinosoft.service.*" %>
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
    mTransferData.setNameAndValue("ContNo", request.getParameter("ProposalContNo"));
    mTransferData.setNameAndValue("PrtNo", request.getParameter("PrtNo"));
    mTransferData.setNameAndValue("AppntNo", request.getParameter("AppntNo"));
    mTransferData.setNameAndValue("AppntName",request.getParameter("AppntName"));
    mTransferData.setNameAndValue("AgentCode",request.getParameter("AgentCode"));
    mTransferData.setNameAndValue("ManageCom", request.getParameter("ManageCom"));
    mTransferData.setNameAndValue("Operator",tG.Operator);
    mTransferData.setNameAndValue("MakeDate",PubFun.getCurrentDate());
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));  
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    
    loggerDebug("InputConfirm","ContNo="+request.getParameter("ProposalContNo"));
    loggerDebug("InputConfirm","PrtNo="+request.getParameter("PrtNo"));
    loggerDebug("InputConfirm","AppntNo="+request.getParameter("AppntNo"));
    loggerDebug("InputConfirm","AppntName="+request.getParameter("AppntName"));
    loggerDebug("InputConfirm","AgentCode="+request.getParameter("AgentCode"));
    loggerDebug("InputConfirm","ManageCom="+request.getParameter("ManageCom"));
    loggerDebug("InputConfirm","Operator="+tG.Operator);
    loggerDebug("InputConfirm","MakeDate="+PubFun.getCurrentDate());
    loggerDebug("InputConfirm","MissionID="+request.getParameter("MissionID"));
    loggerDebug("InputConfirm","SubMissionID="+request.getParameter("SubMissionID"));
    
    loggerDebug("InputConfirm","prtNo=="+request.getParameter("PrtNo"));
    tVData.add(mTransferData);
    tVData.add(tG);
    loggerDebug("InputConfirm","wFlag="+wFlag);
    loggerDebug("InputConfirm","-------------------start workflow---------------------");
    
    String busiName="tbTbWorkFlowUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
   // TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
  if( !tBusinessDelegate.submitData( tVData, wFlag,busiName ) ) {
      Content = " ¼��ȷ��ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
      FlagStr = "Fail";
  }else {
      Content =" ����ɹ���";
      FlagStr = "Succ";
  }
 loggerDebug("InputConfirm","-------------------end workflow---------------------");
%>
<html>
<script language="javascript">
	var contract='<%=request.getParameter("ProposalContNo")%>';
	//alert(contract);
	
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>",contract);
</script>
</html>
