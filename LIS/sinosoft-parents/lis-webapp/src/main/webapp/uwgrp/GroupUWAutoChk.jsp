<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GroupUWAutoChk.jsp
//�����ܣ������Զ��˱�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
loggerDebug("GroupUWAutoChk","Auto-begin:");
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
	loggerDebug("GroupUWAutoChk","session has expired");
	return;
   }
   
  TransferData mTransferData = new TransferData();
  mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));  
  mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));  
 	// Ͷ�����б�
	String tProposalGrpContNo = request.getParameter("ProposalGrpContNoHidden");
//	String tPrtNo = request.getParameter("PrtNo");
	boolean flag = false;

	loggerDebug("GroupUWAutoChk","ready for UWCHECK ProposalGrpContNo: " + tProposalGrpContNo);
	loggerDebug("GroupUWAutoChk","MissionID="+request.getParameter("MissionID"));
	loggerDebug("GroupUWAutoChk","SubMissionID="+request.getParameter("SubMissionID"));
/*	if (tProposalGrpContNo != null && tProposalGrpContNo != "")
	{
		loggerDebug("GroupUWAutoChk","ProposalGrpContNo:"+":"+tProposalGrpContNo);
	  	LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
   		tLCGrpPolSchema.setGrpPolNo( tProposalGrpContNo );
		tLCGrpPolSet.add( tLCGrpPolSchema );
   		flag = true;
	}*/

	if (tProposalGrpContNo != null && tProposalGrpContNo != "")
		flag = true;

	LCGrpContSet tLCGrpContSet = null;
	LCGrpContDB tLCGrpContDB = new LCGrpContDB();
	tLCGrpContDB.setGrpContNo(tProposalGrpContNo);
	tLCGrpContSet = tLCGrpContDB.query();
	if (tLCGrpContSet == null)
	{
		loggerDebug("GroupUWAutoChk","���壨Ͷ��������Ϊ" + tProposalGrpContNo + "�ĺ�ͬ����ʧ�ܣ�");
		return;
	}
	LCGrpContSchema tLCGrpContSchema = tLCGrpContSet.get(1);

	
try{
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( mTransferData );
		tVData.add( tLCGrpContSchema );
		tVData.add( tG );
		
		// ���ݴ���
//		if (tGrpTbWorkFlowUI.submitData(tVData,"INSERT") == false)
		//GrpTbWorkFlowUI tGrpTbWorkFlowUI = new GrpTbWorkFlowUI();
		String busiName="tbgrpGrpTbWorkFlowUI";
		   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if (tBusinessDelegate.submitData(tVData,"0000002003",busiName) == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			{
			  loggerDebug("GroupUWAutoChk","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			  Content = " �Զ��˱�ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			}
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    loggerDebug("GroupUWAutoChk","tError.getErrorCount:"+tError.getErrorCount());
		    if (!tError.needDealError())
		    {                          
		    	Content = " �Զ��˱��ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " �Զ��˱�ʧ�ܣ�ԭ����:";
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
