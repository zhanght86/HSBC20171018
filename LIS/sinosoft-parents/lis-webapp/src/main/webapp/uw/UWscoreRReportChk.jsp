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
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  <%@page import="com.sinosoft.service.*" %>
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
	LCScoreRReportSchema tLCScoreRReportSchema = new LCScoreRReportSchema();
	LCScoreRReportSubSchema tLCScoreRReportSubSchema ;
	LCScoreRReportSubSet tLCScoreRReportSubSet = new LCScoreRReportSubSet();
	
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
		tLCScoreRReportSchema.setContNo(tContNo);
		tLCScoreRReportSchema.setManageCom(tManageCom);
		tLCScoreRReportSchema.setCustomerNo(tCustomerNo);
		tLCScoreRReportSchema.setName(tName);
		tLCScoreRReportSchema.setSScore(tSScore);
		tLCScoreRReportSchema.setAScore(tAScore);
		tLCScoreRReportSchema.setScore(tScore);
		tLCScoreRReportSchema.setConclusion(tConclusion);
		tLCScoreRReportSchema.setAssessOperator(tAssessOperator);
		tLCScoreRReportSchema.setAssessDay(tAssessDay);
		tLCScoreRReportSchema.setAssessTime(tAssessTime);		
		
	}
	else
	{
		flag = false;
		Content = "���ݲ�����!";
	}	
	loggerDebug("UWscoreRReportChk","flag:"+flag);		
	
	//׼���ӱ���Ϣ		
	String SSubtraction = "";
	String SScore = "";
	String Addition = "";
	String AScore = "";
	String tValue = "";
	String tScoreValue = "";
	
	for(int i=1; i<9; i++)
    {
		tLCScoreRReportSubSchema = new LCScoreRReportSubSchema();
		tLCScoreRReportSubSchema.setContNo(tContNo);
		tLCScoreRReportSubSchema.setManageCom(tManageCom);
		tLCScoreRReportSubSchema.setCustomerNo(tCustomerNo);
		tLCScoreRReportSubSchema.setName(tName);
		
    	SSubtraction = "Subtraction" + String.valueOf(i);
    	SScore = "SScore" + String.valueOf(i);
    	tValue = request.getParameter(SSubtraction);
    	loggerDebug("UWscoreRReportChk",SSubtraction);
    	loggerDebug("UWscoreRReportChk",SScore);
    	loggerDebug("UWscoreRReportChk",tValue);
    	if( tValue!=null && tValue.equals("1") )
    		tScoreValue = request.getParameter(SScore);
    	else 
    		tScoreValue ="0";
    	loggerDebug("UWscoreRReportChk",tScoreValue);
    	tLCScoreRReportSubSchema.setAssessItem(SScore);
    	tLCScoreRReportSubSchema.setScore(tScoreValue);
    	tLCScoreRReportSubSet.add(tLCScoreRReportSubSchema);
    }
	for(int i=1; i<7; i++)
    {
		tLCScoreRReportSubSchema = new LCScoreRReportSubSchema();
		tLCScoreRReportSubSchema.setContNo(tContNo);
		tLCScoreRReportSubSchema.setManageCom(tManageCom);
		tLCScoreRReportSubSchema.setCustomerNo(tCustomerNo);
		tLCScoreRReportSubSchema.setName(tName);
		
    	Addition = "Addition" + String.valueOf(i);
    	AScore = "AScore" + String.valueOf(i);   
    	tValue = request.getParameter(Addition);
    	if( tValue!=null && tValue.equals("1") )
    		tScoreValue = request.getParameter(AScore);
    	else 
    		tScoreValue ="0";
    	tLCScoreRReportSubSchema.setAssessItem(AScore);
    	tLCScoreRReportSubSchema.setScore(tScoreValue);
    	tLCScoreRReportSubSet.add(tLCScoreRReportSubSchema);
    }		
	
		try
		{
		  	if (flag == true)
		  	{
				// ׼���������� VData
				VData tVData = new VData();
				tVData.add( tLCScoreRReportSchema);
				tVData.add( tLCScoreRReportSubSet);
				tVData.add( tG );
				
				// ���ݴ���
//				UWScoreRReportUI tUWScoreRReportUI   = new UWScoreRReportUI();
//				if (!tUWScoreRReportUI.submitData(tVData,"")) //ִ��������Ա������
//				{
//					int n = tUWScoreRReportUI.mErrors.getErrorCount();
//					loggerDebug("UWscoreRReportChk","ErrorCount:"+n ) ;
//					Content = " ����Լ������Ա������ʧ�ܣ�ԭ����: " + tUWScoreRReportUI.mErrors.getError(0).errorMessage;
//					FlagStr = "Fail";
//				}
String busiName="UWScoreRReportUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,"",busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "����Լ������Ա������ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "����Լ������Ա������ʧ��";
		FlagStr = "Fail";				
	}
}


				//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
				if (FlagStr == "Fail")
				{
				   // tError = tUWScoreRReportUI.mErrors;
				    tError = tBusinessDelegate.getCErrors();
				    if (!tError.needDealError())
				    {                          
				    	Content = " ����Լ������Ա������ɹ�! ";
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	Content = " ����Լ������Ա������ʧ�ܣ�ԭ����:" + tError.getFirstError();
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
