<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWSendPrintChk.jsp
//�����ܣ��ʹ�ӡ����
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
  
<%
  //�������
  CErrors tError = null;
  //CErrors tErrors = null;
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  
  //У�鴦��
  //���ݴ����
  
  	//������Ϣ
  	// Ͷ�����б�	
	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

	String tProposalNo = request.getParameter("ProposalNoHide");
	String tOtherNoType = request.getParameter("OtherNoType");
	String tCode = request.getParameter("Code");
	
	boolean flag = false;
	
	if (!tProposalNo.equals("")&&!tOtherNoType.equals("")&&!tCode.equals(""))
	{
 	    tLOPRTManagerSchema.setOtherNo( tProposalNo);
	    tLOPRTManagerSchema.setOtherNoType(tOtherNoType);
	    tLOPRTManagerSchema.setCode(tCode);
	    
	    flag = true;
	}
	else
	{
	    FlagStr = "Fail";
	    Content = "���봫��ʧ��!";
	}
	
try
{
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tLOPRTManagerSchema);
		tVData.add( tG );
		
		// ���ݴ���
		GrpUWSendPrintUI tGrpUWSendPrintUI   = new GrpUWSendPrintUI();
		if (tGrpUWSendPrintUI.submitData(tVData,"INSERT") == false)
		{
			int n = tGrpUWSendPrintUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " ����ʧ��,ԭ����: " + tGrpUWSendPrintUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tGrpUWSendPrintUI.mErrors;		    
		    if (!tError.needDealError())
		    {                     
		    	Content = " �����ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                              
		    {
		    	Content = " ����ʧ��,ԭ����:";
		    	int n = tError.getErrorCount();
    			if (n > 0)
    			{
			      for(int i = 0;i < n;i++)
			      {			        
			        Content = Content.trim() +i+". "+ tError.getError(i).errorMessage.trim()+".";
			      }
			}
		    	FlagStr = "Fail";
		    }
		}
	}
}
catch(Exception e)
{
	e.printStackTrace();
	Content = Content.trim()+".��ʾ���쳣��ֹ!";
}
%>       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	parent.close();
	this.close();
</script>
</html>
