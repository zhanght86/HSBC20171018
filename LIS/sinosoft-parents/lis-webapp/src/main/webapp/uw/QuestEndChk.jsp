<%@page contentType="text/html;charset=GBK"%> 
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�QuestEndChk.jsp
//�����ܣ��������ѯ
//�������ڣ�2005-03-25 11:10:36
//������  ��tuqiang
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
<%

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  
  
  String No = request.getParameter("HideSerialNo");
  String ReplyResult = request.getParameter("ReplyResult");
  String Contno = request.getParameter("ContNoHide");
  
  
  VData inVData = new VData();
  inVData.add(tGlobalInput);
  inVData.add(No);
  inVData.add(ReplyResult);
  inVData.add(Contno);
  String Content = "";
  String FlagStr = "";
  
  UWQuestionBackUI tUWRequestBackUI = new UWQuestionBackUI();

  if (!tUWRequestBackUI.submitData(inVData, "")) {
    VData rVData = tUWRequestBackUI.getResult();
    Content = " ����ʧ�ܣ�ԭ����:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " ����ɹ�! ";
  	FlagStr = "Succ";
  }
%> 
<html>
<script language="javascript">
  try { parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>"); } catch(e) {}
</script>
</html>
