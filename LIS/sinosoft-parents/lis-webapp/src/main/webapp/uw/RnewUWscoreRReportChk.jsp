<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWscoreRReportChk.jsp
//�����ܣ��˱���������
//������  ��ln
//�������ڣ�2008-10-25
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.xb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
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

  	//������Ϣ 
	TransferData tTransferData = new TransferData();
	RnewScoreRReportSchema tRnewScoreRReportSchema = new RnewScoreRReportSchema();
	RnewScoreRReportSubSchema tRnewScoreRReportSubSchema ;
	RnewScoreRReportSubSet tRnewScoreRReportSubSet = new RnewScoreRReportSubSet();
	
	String tContNo = request.getParameter("ContNoH"); //ӡˢ��
	String tManageCom = request.getParameter("ManageCom");
	String tCustomerNo = request.getParameter("CustomerNo");
	String tName = request.getParameter("Name");
	String tSScore = request.getParameter("SScore");
	String tAScore = request.getParameter("AScore");
	String tScore = request.getParameter("Score");
	String tConclusion = request.getParameter("Conclusion");
	String tAssessOperator = request.getParameter("AssessOperator");
	String tAssessDay = request.getParameter("AssessDay");
	String tAssessTime = request.getParameter("AssessTimeH");
	
	boolean flag = true;	
	

	if (!tContNo.equals(""))		{		
		
		//׼��������Ϣ
		tRnewScoreRReportSchema.setContNo(tContNo);
		tRnewScoreRReportSchema.setManageCom(tManageCom);
		tRnewScoreRReportSchema.setCustomerNo(tCustomerNo);
		tRnewScoreRReportSchema.setName(tName);
		tRnewScoreRReportSchema.setSScore(tSScore);
		tRnewScoreRReportSchema.setAScore(tAScore);
		tRnewScoreRReportSchema.setScore(tScore);
		tRnewScoreRReportSchema.setConclusion(tConclusion);
		tRnewScoreRReportSchema.setAssessOperator(tAssessOperator);
		tRnewScoreRReportSchema.setAssessDay(tAssessDay);
		tRnewScoreRReportSchema.setAssessTime(tAssessTime);		
		
	}
	else
	{
		flag = false;
		Content = "���ݲ�����!";
	}	
	loggerDebug("RnewUWscoreRReportChk","flag:"+flag);		
	
	//׼���ӱ���Ϣ		
	String SSubtraction = "";
	String SScore = "";
	String Addition = "";
	String AScore = "";
	String tValue = "";
	String tScoreValue = "";
	
	for(int i=1; i<9; i++)
    {
		tRnewScoreRReportSubSchema = new RnewScoreRReportSubSchema();
		tRnewScoreRReportSubSchema.setContNo(tContNo);
		tRnewScoreRReportSubSchema.setManageCom(tManageCom);
		tRnewScoreRReportSubSchema.setCustomerNo(tCustomerNo);
		tRnewScoreRReportSubSchema.setName(tName);
		
    	SSubtraction = "Subtraction" + String.valueOf(i);
    	SScore = "SScore" + String.valueOf(i);
    	tValue = request.getParameter(SSubtraction);
    	loggerDebug("RnewUWscoreRReportChk",SSubtraction);
    	loggerDebug("RnewUWscoreRReportChk",SScore);
    	loggerDebug("RnewUWscoreRReportChk",tValue);
    	if( tValue!=null && tValue.equals("1") )
    		tScoreValue = request.getParameter(SScore);
    	else 
    		tScoreValue ="0";
    	loggerDebug("RnewUWscoreRReportChk",tScoreValue);
    	tRnewScoreRReportSubSchema.setAssessItem(SScore);
    	tRnewScoreRReportSubSchema.setScore(tScoreValue);
    	tRnewScoreRReportSubSet.add(tRnewScoreRReportSubSchema);
    }
	for(int i=1; i<7; i++)
    {
		tRnewScoreRReportSubSchema = new RnewScoreRReportSubSchema();
		tRnewScoreRReportSubSchema.setContNo(tContNo);
		tRnewScoreRReportSubSchema.setManageCom(tManageCom);
		tRnewScoreRReportSubSchema.setCustomerNo(tCustomerNo);
		tRnewScoreRReportSubSchema.setName(tName);
		
    	Addition = "Addition" + String.valueOf(i);
    	AScore = "AScore" + String.valueOf(i);   
    	tValue = request.getParameter(Addition);
    	if( tValue!=null && tValue.equals("1") )
    		tScoreValue = request.getParameter(AScore);
    	else 
    		tScoreValue ="0";
    	tRnewScoreRReportSubSchema.setAssessItem(AScore);
    	tRnewScoreRReportSubSchema.setScore(tScoreValue);
    	tRnewScoreRReportSubSet.add(tRnewScoreRReportSubSchema);
    }		
	
		try
		{
		  	if (flag == true)
		  	{
				// ׼���������� VData
				VData tVData = new VData();
				tVData.add( tRnewScoreRReportSchema);
				tVData.add( tRnewScoreRReportSubSet);
				tVData.add( tG );
				
				// ���ݴ���
				RnewUWScoreRReportUI tUWScoreRReportUI   = new RnewUWScoreRReportUI();
				if (!tUWScoreRReportUI.submitData(tVData,"")) //ִ��������Ա������
				{
					int n = tUWScoreRReportUI.mErrors.getErrorCount();
					loggerDebug("RnewUWscoreRReportChk","ErrorCount:"+n ) ;
					Content = " ��������������Ա������ʧ�ܣ�ԭ����: " + tUWScoreRReportUI.mErrors.getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
				if (FlagStr == "Fail")
				{
				    tError = tUWScoreRReportUI.mErrors;
				    if (!tError.needDealError())
				    {                          
				    	Content = " ��������������Ա������ɹ�! ";
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	Content = " ��������������Ա������ʧ�ܣ�ԭ����:" + tError.getFirstError();
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
</script>
</html>
