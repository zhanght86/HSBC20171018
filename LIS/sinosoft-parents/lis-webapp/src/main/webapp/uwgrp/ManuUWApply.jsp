<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UnderwriteApply.jsp
//�����ܣ��˹��˱�Ͷ��������У��
//�������ڣ�2003-04-09 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.xbcheck.*"%>
    <%@page import="com.sinosoft.service.*" %>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  
  //У�鴦��
 	LCPolSet tLCPolSet = new LCPolSet();
	String tProposalNo = request.getParameter("PolNoHide");
	
	loggerDebug("ManuUWApply","ProposalNo:"+tProposalNo);
	
	boolean flag = false;
	
	if (!tProposalNo.equals(""))
	{	
 	    LCPolSchema tLCPolSchema = new LCPolSchema();
 	    tLCPolSchema.setPolNo( tProposalNo);
	    tLCPolSchema.setProposalNo( tProposalNo);	    
	    tLCPolSet.add( tLCPolSchema );	    
	    
	    flag = true;
	}
	else
	{
	    FlagStr = "Fail";
	    Content = "����ʧ�ܣ����봫��ʧ��!";
	}
	
try
{
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tLCPolSet );		
		tVData.add( tG );
		
		// ���ݴ���
		
		   String busiName="xbcheckPRnewUWManuApplyChkUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		//PRnewUWManuApplyChkUI tPRnewUWManuApplyChkUI   = new PRnewUWManuApplyChkUI();
		if (tBusinessDelegate.submitData(tVData,"INSERT",busiName) == false)
		{
			Content = " �����˹��˱�����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                     
		    	Content = " �����˹��˱���������ɹ�!";
		    	FlagStr = "Succ";
		    }
		    else                                                              
		    {
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
	parent.fraInterface.afterApply("<%=FlagStr%>","<%=Content%>");	
</script>
</html>
