<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
<%
//�������
CErrors tError = null;
String tRela  = "";                
String FlagStr="";
String Content = "";
String tAction = "";
String tOperate = "";
String wFlag = "";
String frameType = "";

  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  
    VData tVData = new VData();
    //�����������ͱ𣬸��ݴ�ֵ�����ID��ȡ��������ִ�о���ҵ���߼�
    wFlag = request.getParameter("WorkFlowFlag");
    frameType = request.getParameter("FrameType");

    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ProposalGrpContNo", request.getParameter("ProposalGrpContNo"));
    mTransferData.setNameAndValue("GrpContNo", request.getParameter("ProposalGrpContNo"));
    mTransferData.setNameAndValue("PrtNo", request.getParameter("PrtNo"));
    mTransferData.setNameAndValue("SaleChnl", request.getParameter("SaleChnl"));
    mTransferData.setNameAndValue("AgentCode",request.getParameter("AgentCode"));    
    mTransferData.setNameAndValue("AgentGroup",request.getParameter("AgentGroup"));
    mTransferData.setNameAndValue("ManageCom", request.getParameter("ManageCom"));
    mTransferData.setNameAndValue("GrpName", request.getParameter("GrpName"));
    mTransferData.setNameAndValue("CValiDate", request.getParameter("CValiDate"));
    mTransferData.setNameAndValue("Operator",tG.Operator);
    mTransferData.setNameAndValue("MakeDate",PubFun.getCurrentDate());
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));  
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    
    loggerDebug("GrpInputConfirm",request.getParameter("PrtNo"));
    loggerDebug("GrpInputConfirm","fdljfldjfljdlfjdlfjzhangxing"+request.getParameter("ProposalGrpContNo"));
    tVData.add(mTransferData);
    tVData.add(tG);
    loggerDebug("GrpInputConfirm","wFlag="+wFlag);
    loggerDebug("GrpInputConfirm","-------------------start workflow---------------------");
    GrpTbWorkFlowUI tGrpTbWorkFlowUI = new GrpTbWorkFlowUI();
  if( !tGrpTbWorkFlowUI.submitData( tVData, wFlag ) ) {
      Content = " ����ʧ�ܣ�ԭ����: " + tGrpTbWorkFlowUI.mErrors.getError(0).errorMessage;
      FlagStr = "Fail";
  }else {
      Content = " ����ɹ���";
      FlagStr = "Succ";
  }
 loggerDebug("GrpInputConfirm","-------------------end workflow---------------------");
%>
<html>
<script language="javascript">
<%
/*if(frameType!=null&&frameType=="0"){
	out.println("parent.fraInterface.afterSubmit('"+FlagStr+"','"+Content+"')");
}else{
	out.println("parent.fraInterface.afterSubmit('"+FlagStr+"','"+Content+"')");
}*/
%>
parent.fraInterface.afterSubmit1("<%=FlagStr%>","<%=Content%>");
</script>
</html>
