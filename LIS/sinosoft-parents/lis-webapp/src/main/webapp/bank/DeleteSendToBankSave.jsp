<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�DeleteSendToBankSave.jsp
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
  
<%
  loggerDebug("DeleteSendToBankSave","\n\n---DeleteSendToBankSave Start---");
  loggerDebug("DeleteSendToBankSave","TempFeeNo:" + request.getParameter("TempFeeNo"));
  loggerDebug("DeleteSendToBankSave","Action:" + request.getParameter("Action"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  
  LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
  tLJTempFeeClassSchema.setTempFeeNo(request.getParameter("TempFeeNo"));
  LJTempFeeClassSet inLJTempFeeClassSet = new LJTempFeeClassSet();
  inLJTempFeeClassSet.add(tLJTempFeeClassSchema);

  VData inVData = new VData();
  inVData.add(inLJTempFeeClassSet);
  inVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";
  
  DeleteSendToBankUI DeleteSendToBankUI1 = new DeleteSendToBankUI();

  if (!DeleteSendToBankUI1.submitData(inVData, request.getParameter("Action"))) {
    VData rVData = DeleteSendToBankUI1.getResult();
    Content = " ����ʧ�ܣ�ԭ����:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " ����ɹ�! ";
  	FlagStr = "Succ";
  }

	loggerDebug("DeleteSendToBankSave",Content + "\n" + FlagStr + "\n---DeleteSendToBankSave End---\n\n");
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
	parent.fraInterface.easyQueryClick();
</script>
</html>
