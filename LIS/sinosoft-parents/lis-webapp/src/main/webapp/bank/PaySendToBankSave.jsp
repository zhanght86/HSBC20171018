<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�PaySendToBankSave.jsp
//�����ܣ�
//�������ڣ�2002-11-18 11:10:36
//������  ���� ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.bank.*"%>
  <%@page import="com.sinosoft.service.*"%>
  
<%
  loggerDebug("PaySendToBankSave","\n\n---PaySendToBankSave Start---");
  
  //PaySendToBankUI petSendToBankUI1 = new PaySendToBankUI();
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  TransferData transferData1 = new TransferData();
  transferData1.setNameAndValue("startDate", request.getParameter("StartDate"));
  transferData1.setNameAndValue("endDate", request.getParameter("EndDate"));
  transferData1.setNameAndValue("bankCode", request.getParameter("BankCode"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");

  VData tVData = new VData();
  tVData.add(transferData1);
  tVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";

  //if (!petSendToBankUI1.submitData(tVData, "PAYMONEY")) {
  if(!tBusinessDelegate.submitData(tVData,"PAYMONEY","PaySendToBankUI")){
    //VData rVData = petSendToBankUI1.getResult();
    VData rVData = tBusinessDelegate.getResult();
    Content = " ����ʧ�ܣ�ԭ����:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " ����ɹ�! ";
  	FlagStr = "Succ";
  }

	loggerDebug("PaySendToBankSave",Content + "\n" + FlagStr + "\n---PaySendToBankSave End---\n\n");
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
</script>
</html>
