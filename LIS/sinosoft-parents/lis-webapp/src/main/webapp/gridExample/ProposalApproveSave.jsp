<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ProposalApproveSave.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��HST
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "";
  String Content = "";

	try
	{
		GlobalInput tG = new GlobalInput();
		tG=(GlobalInput)session.getValue("GI");
	  
	  	//������Ϣ
		LCPolSchema tLCPolSchema = new LCPolSchema();
		
		String polNo = request.getParameter("polNo");
		String approveFlag = request.getParameter("approveFlag");
		loggerDebug("ProposalApproveSave","ProposalNo:" + polNo + "\napproveFlag:"+ approveFlag); 
		
		tLCPolSchema.setProposalNo(polNo);
		tLCPolSchema.setApproveFlag(approveFlag);
	
		
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tLCPolSchema );
		tVData.add( tG );
		
		// ���ݴ���
		//ProposalApproveUI tProposalApproveUI = new ProposalApproveUI();
		String busiName="tbProposalApproveUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if (tBusinessDelegate.submitData( tVData, "INSERT" ,busiName) == false)
		{
			Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else
		{
	    	Content = " ���˳ɹ�! ";
	    	FlagStr = "Succ";
	    }
	} // end of try
	catch( Exception e1 )
	{
    	Content = " ����ʧ�ܣ�ԭ����:" + e1.toString().trim();
    	FlagStr = "Fail";
    }
	loggerDebug("ProposalApproveSave","---" + Content + "---\n");
%>                      
<html>
<script language="javascript">
try {
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
} catch(ex) { }
</script>
</html>
