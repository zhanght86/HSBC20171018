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
    //wFlag = "0000001404";
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("PrtNo", request.getParameter("PrtNo"));
    mTransferData.setNameAndValue("ContNo", request.getParameter("ContNo"));
    mTransferData.setNameAndValue("AppntName",request.getParameter("AppntName"));
    mTransferData.setNameAndValue("AgentCode",request.getParameter("AgentCode"));
    mTransferData.setNameAndValue("ManageCom", request.getParameter("ManageCom"));
    mTransferData.setNameAndValue("Operator",tG.Operator);
    mTransferData.setNameAndValue("MakeDate",PubFun.getCurrentDate());
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));  
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    
    loggerDebug("UnionConfirmSave","tG.Operator: "+tG.Operator);
    //add by lzf
    mTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
    loggerDebug("UnionConfirmSave","ActivityID : "+ request.getParameter("ActivityID"));
    tVData.add(mTransferData);
    tVData.add(tG);
    loggerDebug("UnionConfirmSave","wFlag="+wFlag);
    loggerDebug("UnionConfirmSave","-------------------start workflow---------------------");
    //TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
     //String busiName="tbTbWorkFlowUI";
     String busiName = "WorkFlowUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  if( !tBusinessDelegate.submitData( tVData, "execute" ,busiName) ) {
      Content = " ¼��ȷ��ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
      FlagStr = "Fail";
  }else {
      Content =" ����ɹ���";
      FlagStr = "Succ";
  }
 loggerDebug("UnionConfirmSave","-------------------end workflow---------------------");
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");
</script>
</html>
