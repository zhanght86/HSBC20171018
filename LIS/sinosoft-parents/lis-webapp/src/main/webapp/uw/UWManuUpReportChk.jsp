<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�PRnewUWManuRReportChk.jsp
//�����ܣ��б��˹��˱�������鱨��¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��zhangxing
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
  	// Ͷ�����б�
	LCReinsureReportSchema tLCReinsureReportSchema = new LCReinsureReportSchema(); 
 
	TransferData tTransferData = new TransferData();
	String tContNo = request.getParameter("ContNo");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tReportReason = request.getParameter("ReportReasonDes");
	String tReportRemark = request.getParameter("ReportRemark");

		
	boolean flag = true;
	
	
		if (!tContNo.equals("")&& !tMissionID.equals(""))
		{
			tLCReinsureReportSchema.setContNo(tContNo);		
			tLCReinsureReportSchema.setReportReason(tReportReason); //add �ʱ�ԭ�� ln 2008-11-04
			tLCReinsureReportSchema.setReportRemark(tReportRemark);
			
			//׼������������Ϣ
			tTransferData.setNameAndValue("ContNo",tContNo);
		
			tTransferData.setNameAndValue("MissionID",tMissionID);	
			tTransferData.setNameAndValue("SubMissionID",tSubMissionID);	
			tTransferData.setNameAndValue("LCReinsureReportSchema",tLCReinsureReportSchema);	
			String ActivitySql="select activityid from lwmission where activityid in (select activityid from lwactivity where functionid = '10010039') and missionid = '"+tMissionID+"'";
			String ActivityID= new ExeSQL().getOneValue(ActivitySql);	
			tTransferData.setNameAndValue("ActivityID",ActivityID); 
			tTransferData.setNameAndValue("BusiType", "1001");
					
			
		}
		else
		{
			flag = false;
			Content = "���ݲ�����!";
		}	
		loggerDebug("UWManuUpReportChk","flag:"+flag);
		try
		{
		  	if (flag == true)
		  	{
				// ׼���������� VData
				VData tVData = new VData();
				tVData.add( tTransferData);
				tVData.add( tG );
				
				// ���ݴ���
				//TbWorkFlowUI tTbWorkFlowUI   = new TbWorkFlowUI();
				String busiName="tWorkFlowUI";
                BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				if (!tBusinessDelegate.submitData(tVData,"execute",busiName)) //ִ�гб��˱������������ڵ�0000001134
				{
					int n = tBusinessDelegate.getCErrors().getErrorCount();
					loggerDebug("UWManuUpReportChk","ErrorCount:"+n ) ;
					Content = " ����Լ�ٱ��ʱ�ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
				if (FlagStr == "Fail")
				{
				    tError = tBusinessDelegate.getCErrors();
				    if (!tError.needDealError())
				    {                          
				    	Content = " ����Լ�ٱ��ʱ��ɹ�! ";
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	Content = " ����Լ�ٱ��ʱ�ʧ�ܣ�ԭ����:" + tError.getFirstError();
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
