<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ApplyRecallChk.jsp
//�����ܣ�
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	    //�������
	    CErrors tError = null;
	    String FlagStr = "Fail";
	    String Content = "";

		GlobalInput tG = new GlobalInput();  
		tG=(GlobalInput)session.getValue("GI");
	
		String tPrtNo = request.getParameter("PrtNo");
		String tContNo = request.getParameter("ContNoH");
		String tContent = request.getParameter("Content");
		//String tApplyType = request.getParameter("ApplyType");
		String tContType = request.getParameter("PolType");
		
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("UWWithDReasonCode","");
		tTransferData.setNameAndValue("UWWithDReason","");
		tTransferData.setNameAndValue("Content",tContent);
		tTransferData.setNameAndValue("ContNo",tContNo);	
	
	  	//���丽���ձ�			    				
		LCApplyRecallPolSchema tLCApplyRecallPolSchema = new LCApplyRecallPolSchema();		
		tLCApplyRecallPolSchema.setRemark(tContent);
		tLCApplyRecallPolSchema.setPrtNo(tPrtNo);	
		tLCApplyRecallPolSchema.setApplyType("0"); //����	
			
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tTransferData);
		tVData.add( tLCApplyRecallPolSchema);
		tVData.add( tG );
		
		// ���ݴ���
		if(tContType.equals("1"))
		{
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
		 }
		 else if(tContType.equals("2"))
		 {
		 GrpApplyRecallPolUI tGrpApplyRecallPolUI = new GrpApplyRecallPolUI();
		if (tGrpApplyRecallPolUI.submitData(tVData,"") == false)
		{
			Content = " ����ʧ�ܣ�ԭ����: " + tGrpApplyRecallPolUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
        else     
		{                          
		    Content = " ����ɹ�! ";
		    FlagStr = "Succ";
		 }
		 }

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
