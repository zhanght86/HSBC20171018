<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�QuestReplyChk.jsp
//�����ܣ�������ظ�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.lis.xbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
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
  //���ݴ����
  
  	//������Ϣ
  	// Ͷ�����б�
	RnewIssuePolSet tRnewIssuePolSet = new RnewIssuePolSet();

	String tContNo = request.getParameter("ContNo");
	String tFlag = request.getParameter("Flag");
	String tBackObj = request.getParameter("Quest");
	String treply = request.getParameter("ReplyResult");
	String tSerialNo = request.getParameter("SerialNo");
	String QuesFlag = request.getParameter("QuesFlag");   //����������ظ���ϱ�־
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tContent = "";
	loggerDebug("RnewQuestReplyChk","tMissionID="+tMissionID);
	loggerDebug("RnewQuestReplyChk","tSubMissionID="+tSubMissionID);
	
	String tcont[] = request.getParameterValues("QuestGrid3");
	String tChk[] = request.getParameterValues("InpQuestGridChk");
	
	loggerDebug("RnewQuestReplyChk","contno:"+tContNo);
	loggerDebug("RnewQuestReplyChk","flag:"+tFlag);
	loggerDebug("RnewQuestReplyChk","reply:"+treply);
	loggerDebug("RnewQuestReplyChk","issuetype:"+tBackObj);
	loggerDebug("RnewQuestReplyChk","tSerialNo:"+tSerialNo);
	boolean flag = true;
	if(QuesFlag.equals("allover"))           //���ִ�С��ظ���ϡ���������ִ�й������ڵ�
	{
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("MissionID",tMissionID);
		tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
		tTransferData.setNameAndValue("BusiType", "1004");
		 ExeSQL yExeSQL = new ExeSQL();
			SSRS ySSRS = new SSRS();
		  String sqlstr="select activityid from lwactivity where functionid='10047015'";
			ySSRS = yExeSQL.execSQL(sqlstr);
			String xActivityID="";
			if(ySSRS.MaxRow==0)
			{}
			else
			{
				 xActivityID = ySSRS.GetText(1,1);
			}
		  tTransferData.setNameAndValue("ActivityID", xActivityID);
		  RnewCommonDataPut tRnewCommonDataPut = new RnewCommonDataPut();
		  if(!tRnewCommonDataPut.submitData(tTransferData))
		  {
			  Content = "����ʧ�ܣ�ԭ����:" + tRnewCommonDataPut.getErrors().getFirstError();
		      FlagStr = "Fail";
		  }
		  tTransferData=tRnewCommonDataPut.getTransferData();
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add( tG );
		String busiName="WorkFlowUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if(!tBusinessDelegate.submitData(tVData,"execute",busiName)){
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " �������������ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
	/*	
		EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI();
				if (tEdorWorkFlowUI.submitData(tVData,"0000007015") == false)
				{
					int n = tEdorWorkFlowUI.mErrors.getErrorCount();
					for (int i = 0; i < n; i++)
					Content = " �������������ʧ�ܣ�ԭ����: " + tEdorWorkFlowUI.mErrors.getError(0).errorMessage;
					FlagStr = "Fail";
				}
	*/
				//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
				if (FlagStr == "Fail")
				{
				    tError = tBusinessDelegate.getCErrors();
				    if (!tError.needDealError())
				    {                          
				    	Content = " ����ɹ�! ";
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
				    	FlagStr = "Fail";
				    }		
				}
	}
	else
	{
			if (tSerialNo.equals("")||tContNo.equals("") || tFlag.equals("") || treply.equals("") || tBackObj.equals(""))
			{
				Content = "����¼�벻��ȫ����ʧ��!";
				FlagStr = "Fail";
				flag = false;
				loggerDebug("RnewQuestReplyChk","ʧ��");
			}
			else
			{
				loggerDebug("RnewQuestReplyChk","begin");	
  		
					
				RnewIssuePolSchema tRnewIssuePolSchema = new RnewIssuePolSchema();
				
				tRnewIssuePolSchema.setContNo(tContNo);
				tRnewIssuePolSchema.setIssueCont(tContent);			
				tRnewIssuePolSchema.setOperatePos(tFlag);
				tRnewIssuePolSchema.setIssueType(tBackObj);
				tRnewIssuePolSchema.setReplyResult(treply);
				tRnewIssuePolSchema.setSerialNo(tSerialNo);
				tRnewIssuePolSet.add(tRnewIssuePolSchema);			    
				
			}
			
			loggerDebug("RnewQuestReplyChk","flag:"+flag);
  			if (flag == true)
  			{
				// ׼���������� VData
				VData tVData = new VData();
				loggerDebug("RnewQuestReplyChk","ContNo="+tContNo);
				tVData.add( tRnewIssuePolSet);
				tVData.add( tG );
				
				// ���ݴ���
				RnewQuestReplyChkUI tRnewQuestReplyChkUI   = new RnewQuestReplyChkUI();
				if (tRnewQuestReplyChkUI.submitData(tVData,"INSERT") == false)
				{
					int n = tRnewQuestReplyChkUI.mErrors.getErrorCount();
					for (int i = 0; i < n; i++)
					Content = " ����������ظ�ʧ�ܣ�ԭ����: " + tRnewQuestReplyChkUI.mErrors.getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
				if (FlagStr == "Fail")
				{
				    tError = tRnewQuestReplyChkUI.mErrors;
				    if (!tError.needDealError())
				    {                          
				    	Content = " �˹��˱��ɹ�! ";
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	Content = " �˹��˱�ʧ�ܣ�ԭ����:" + tError.getFirstError();
				    	FlagStr = "Fail";
				    }
				}
			} 
	}
%>                      
<%@page import="com.sinosoft.lis.xb.RnewCommonDataPut"%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
