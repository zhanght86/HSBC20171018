
<%
//�������ƣ�PrtReplaceSave.jsp
//�����ܣ������޸�
//�������ڣ�2006-07-18 11:10:36
//������  ��ck
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  //�������
  	CErrors tError = null;
  	String FlagStr = "Fail";
  	String Content = "";
	TransferData tTransferData=new TransferData();
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");

  	if(tG == null) {
		out.println("session has expired");
		return;
	}		
	tTransferData.setNameAndValue("OldPrtNo",request.getParameter("OldPrtNoHide"));
	tTransferData.setNameAndValue("NewPrtNo",request.getParameter("NewPrtNo"));	     	     	     	

	// ׼���������� VData
	VData tVData = new VData();
	tVData.add( tTransferData );
	tVData.add( tG );

	// ���ݴ���
	PrtReplaceUI tPrtReplaceUI   = new PrtReplaceUI();	
	loggerDebug("PrtReplaceSave","before PrtReplaceUI");
	if (tPrtReplaceUI.submitData(tVData,"PrtReplace") == false)
	{
		int n = tPrtReplaceUI.mErrors.getErrorCount();
		for (int i = 0; i < n; i++)
		Content = " ӡˢ���滻ʧ�ܣ�ԭ����: " + tPrtReplaceUI.mErrors.getError(0).errorMessage;
		FlagStr = "Fail";
	}
	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	if (FlagStr == "Fail")
	{
	    tError = tPrtReplaceUI.mErrors;
		if (!tError.needDealError())
		{
			Content = " ӡˢ���滻�ɹ�! ";
		    	FlagStr = "Succ";
		}
		else                                                                           
		{
			Content = " ӡˢ���滻ʧ�ܣ�ԭ����:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
