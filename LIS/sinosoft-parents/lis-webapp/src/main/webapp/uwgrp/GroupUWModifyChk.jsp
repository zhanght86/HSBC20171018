<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GroupUWModifyChk.jsp
//�����ܣ�����˱���֤
//�������ڣ�2004-12-8 11:10:36
//������  ��heyq
//���¼�¼��  ������    ��������     ����ԭ��/����

%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.workflow.tbgrp.*"%>
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
	loggerDebug("GroupUWModifyChk","session has expired");
	return;
   }
   
  TransferData mTransferData = new TransferData();
  
  mTransferData.setNameAndValue("GrpContNo",request.getParameter("GrpContNo"));  
  mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));  
  mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));  
 	// Ͷ�����б�
	String tProposalGrpContNo = request.getParameter("GrpContNo");
//	String tPrtNo = request.getParameter("PrtNo");
	boolean flag = false;

	loggerDebug("GroupUWModifyChk","ready for UWCHECK ProposalGrpContNo: " + tProposalGrpContNo);
	//����prtno���Ʋ��� add by liuqh 2008-09-26
    String tRadio[] = request.getParameterValues("InpGrpGridSel");
	String tPrtNo[] = request.getParameterValues("GrpGrid1");
	for ( int i=0;i<tRadio.length;i++)
	{
		if(tRadio[i].equals("1"))
		mTransferData.setNameAndValue("PrtNo",tPrtNo[i]);
	}

	if (tProposalGrpContNo != null && tProposalGrpContNo != "")
		flag = true;
	
try{
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( mTransferData );
		tVData.add( tG );
		
		// ���ݴ���
		//GrpTbWorkFlowUI tGrpTbWorkFlowUI = new GrpTbWorkFlowUI();
		String busiName="tbgrpGrpTbWorkFlowUI";
		   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if (tBusinessDelegate.submitData(tVData,"0000002005",busiName) == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			{
			  loggerDebug("GroupUWModifyChk","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			  Content = "�˱�����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			}
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    loggerDebug("GroupUWModifyChk","tError.getErrorCount:"+tError.getErrorCount());
		    if (!tError.needDealError())
		    {                          
		    	Content = " �˱������ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " �˱�����ʧ�ܣ�ԭ����:";
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
    //top.close();
	//alert("<%=Content%>");
	//top.close();
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
