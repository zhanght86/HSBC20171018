<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�RReportReplyChk.jsp
//�����ܣ�������鱨��ظ�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  �����ˣ�ln    �������ڣ�2008-10-23   ����ԭ��/���ݣ������º˱�Ҫ������޸�
%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.xb.*"%>
<%
  //�������
  CErrors tError = null;
  //CErrors tErrors = new CErrors();
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
  loggerDebug("RnewRReportReplyChk","aaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	LCPolSet tLCPolSet = new LCPolSet();
	LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
	LCSpecSet tLCSpecSet = new LCSpecSet();
	LCPremSet tLCPremSet = new LCPremSet();
	RnewRReportSchema tRnewRReportSchema = new RnewRReportSchema();
	
	loggerDebug("RnewRReportReplyChk","bbbbbbbbbbbbbbbbbbbbbbbbbbbb");
	String tProposalNo = request.getParameter("ProposalNoHide");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tPrtSeq = request.getParameter("PrtSeqHide");
	//String tUse = request.getParameter("Operator");
	String tReporter = request.getParameter("Reporter");
	String tReportFee = request.getParameter("ReportFee");
	String tFlag = request.getParameter("Flag");
	String tContent = request.getParameter("Content");
	String tReplyContent = request.getParameter("ReplyResult");	
	loggerDebug("RnewRReportReplyChk","polno:"+tProposalNo);
	loggerDebug("RnewRReportReplyChk","content:"+tContent);
	loggerDebug("RnewRReportReplyChk","flag:"+tFlag);
	
	boolean flag = true;
	
	if (!tProposalNo.equals("")&&!tContent.equals(""))
	{
		tRnewRReportSchema.setContNo(tProposalNo);
		tRnewRReportSchema.setRemark(tReporter); //���������
		tRnewRReportSchema.setRReportFee(tReportFee); //��������
		tRnewRReportSchema.setReplyContente(tReplyContent);
		tRnewRReportSchema.setPrtSeq(tPrtSeq);
		
	}
	else
	{
		flag = false;
		Content = "���ݲ�����!";
	}
	
	loggerDebug("RnewRReportReplyChk","flag:"+flag);
try
{
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tRnewRReportSchema);
//*********************************add ln 2008-11-1**���빤����************************************//		
		
	  TransferData tTransferData = new TransferData();
	  tTransferData.setNameAndValue("MissionID",tMissionID);
	  tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
	  tTransferData.setNameAndValue("ContNo",tProposalNo);
	  tTransferData.setNameAndValue("BusiType", "1004"); 
	  ExeSQL yExeSQL = new ExeSQL();
	  String sqlstr="select activityid from lwactivity where functionid='10047014'";
	  SSRS ySSRS = yExeSQL.execSQL(sqlstr);
		String xActivityID="";
		if(ySSRS.MaxRow==0)
		{}
		else
		{
			 xActivityID = ySSRS.GetText(1,1);
		}
	  tTransferData.setNameAndValue("ActivityID", xActivityID);
	  tVData.add(tTransferData);
	  tVData.add(tG);

	  loggerDebug("RnewRReportReplyChk","---------------------------workflow start-------------------------");
	  
	//	RnewWorkFlowUI tRnewWorkFlowUI = new RnewWorkFlowUI();
			String busiName="WorkFlowUI";
            BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			loggerDebug("RnewRReportReplyChk","this will save the data!!!");
			if(!tBusinessDelegate.submitData(tVData,"execute",busiName)){
				Content = " ����ɹ�! ";
				FlagStr = "Succ";
			}else{
				Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
	//  if(tRnewWorkFlowUI.submitData(tVData,"0000007014")){
		
			//	Content = " ����ɹ�! ";
			//	FlagStr = "Succ";
		
		//}
		//else{
			//Content = "����ʧ�ܣ�ԭ����:" + tRnewWorkFlowUI.mErrors.getError(0).errorMessage;
			//FlagStr = "Fail";
		//}
////////////////////////////delete/////////////////////////////////////////////////////////////
		
		// ���ݴ���
		/*********************
		MSUWRReportReplyUI tUWRReportReplyUI   = new MSUWRReportReplyUI();
		if (tUWRReportReplyUI.submitData(tVData,"INSERT") == false)
		{
			int n = tUWRReportReplyUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " �����ظ�ʧ�ܣ�ԭ����: " + tUWRReportReplyUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tUWRReportReplyUI.mErrors;		    
		    if (!tError.needDealError())
		    {                          
		    	Content = " �����ظ��ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " �����ظ�ʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
		}
		*********************/
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
</script>
</html>
