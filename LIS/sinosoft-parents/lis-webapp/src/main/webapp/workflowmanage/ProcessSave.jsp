<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�PlanWorkFlowSave.jsp
//�����ܣ�
//�������ڣ�
//������  ��
//���¼�¼��
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>

  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%

  CErrors tError = null;
  String Content = "";
  String FlagStr = "";
  String tOperate=request.getParameter("hideOperate");
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("ProcessID",request.getParameter("ProcessID"));
	tTransferData.setNameAndValue("Version",request.getParameter("Version"));
	

  VData tVData = new VData();
  tVData.add(tG);
  tVData.add(tTransferData);
  BusinessDelegate tBusinessDelegate; 
  tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if(!tBusinessDelegate.submitData(tVData, tOperate, "ProcessDefUI"))
  {
	  Content = " ɾ��ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getFirstError();
	 	FlagStr = "Fail";
	}
	else
	{
		Content = " ɾ���ɹ�! ";
	  FlagStr = "Succ";
  }  
 

  %>
  <html>
<script language="javascript">
 parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
</script>
</html>
