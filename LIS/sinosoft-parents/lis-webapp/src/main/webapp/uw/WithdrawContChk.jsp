<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�WithdrawContChk.jsp
//�����ܣ�����
//�������ڣ�2008-09-28 11:10:36
//������  ��ln
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
<%
	    //�������
	    CErrors tError = null;
	    String FlagStr = "Fail";
	    String Content = "";

		GlobalInput tG = new GlobalInput();  
		tG=(GlobalInput)session.getValue("GI");
	
		String tPrtNo = request.getParameter("PrtNoH");
		String tContNo = request.getParameter("ContNoH");
		String tContent = request.getParameter("Content");
		String tUWWithDReasonCode = request.getParameter("UWWithDReasonCode");
		String tUWWithDReason = request.getParameter("UWWithDReason");
		
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("UWWithDReasonCode",tUWWithDReasonCode);
		tTransferData.setNameAndValue("UWWithDReason",tUWWithDReason);
		tTransferData.setNameAndValue("Content",tContent);
		tTransferData.setNameAndValue("ContNo",tContNo);
	
	
	  	//���丽���ձ�			    				
		LCApplyRecallPolSchema tLCApplyRecallPolSchema = new LCApplyRecallPolSchema();		
		tLCApplyRecallPolSchema.setRemark(tContent);
		tLCApplyRecallPolSchema.setPrtNo(tPrtNo);	
		tLCApplyRecallPolSchema.setApplyType("0"); //����	
			
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add( tLCApplyRecallPolSchema);
		tVData.add( tG );
		
		// ���ݴ���
		ApplyRecallPolUI tApplyRecallPolUI   = new ApplyRecallPolUI();
		if (tApplyRecallPolUI.submitData(tVData,"") == false)
		{
			Content = " ����ʧ�ܣ�ԭ����: " + tApplyRecallPolUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
        else     
		{                          
		    Content = " ����ɹ�! ";
		    FlagStr = "Succ";
		 }

%>                       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
