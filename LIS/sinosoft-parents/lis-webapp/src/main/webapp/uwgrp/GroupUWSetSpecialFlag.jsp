<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GroupUWAutoChk.jsp
//�����ܣ������Զ��˱�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
loggerDebug("GroupUWSetSpecialFlag","Auto-begin:");
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
    <%@page import="com.sinosoft.service.*" %>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  if(tG == null) 
  {
	loggerDebug("GroupUWSetSpecialFlag","session has expired");
	return;
   }
   
 	// Ͷ�����б�
	LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
	String tGrpProposalNo = request.getParameter("GrpProposalNo");
	String tGrpPrtNo = request.getParameter("GrpPrtNo");
	boolean flag = false;
	
	if (tGrpProposalNo != null && tGrpProposalNo != "")
	{
		loggerDebug("GroupUWSetSpecialFlag","GrpProposalNo:"+":"+tGrpProposalNo);	  	
   		tLCGrpPolSchema.setPrtNo( tGrpPrtNo );
		flag = true;
	}
		
	
try{
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tLCGrpPolSchema );
		tVData.add( tG );
		
		// ���ݴ���
		//GrpUWSetSpecialFlagUI tGrpUWSetSpecialFlagUI   = new GrpUWSetSpecialFlagUI();
		
		String busiName="cbcheckgrpGrpUWSetSpecialFlagUI";
		   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if (tBusinessDelegate.submitData(tVData,"UPDATE",busiName) == false)
		{
			  //loggerDebug("GroupUWSetSpecialFlag","Error: "+tGrpUWSetSpecialFlagUI.mErrors.getError(0).errorMessage);
			  Content = " ����ʧ�ܣ�ԭ����: " ; //+ tGrpUWSetSpecialFlagUI.mErrors.getError(0).errorMessage;
		
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    loggerDebug("GroupUWSetSpecialFlag","tError.getErrorCount:"+tError.getErrorCount());
		    if (!tError.needDealError())
		    {                          
		    	Content = " �����ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " ����ʧ�ܣ�ԭ����:";
		    	int n = tError.getErrorCount();
    			if (n > 0)
    			{
			      for(int i = 0;i < n;i++)
			      {
			        //tError = tErrors.getError(i);
			        Content = Content.trim() +i+". "+ tError.getError(i).errorMessage.trim()+".";
			      }
			}
		    	FlagStr = "Fail";
		    }
		}
	}
	else
	{
		Content = "��ѡ�񱣵���";
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
    top.close();
	alert("<%=Content%>");
	top.close();
</script>
</html>
