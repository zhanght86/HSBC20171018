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
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tbgrp.*"%>
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
	String tReportRemark = request.getParameter("ReportRemark");

		
	boolean flag = true;
	
	

		if (!tContNo.equals("")&& !tMissionID.equals(""))
		{
			tLCReinsureReportSchema.setContNo(tContNo);			
			tLCReinsureReportSchema.setReportRemark(tReportRemark);
			
			//׼������������Ϣ
			tTransferData.setNameAndValue("ContNo",tContNo);
		
			tTransferData.setNameAndValue("MissionID",tMissionID);	
			tTransferData.setNameAndValue("SubMissionID",tSubMissionID);	
			tTransferData.setNameAndValue("LCReinsureReportSchema",tLCReinsureReportSchema);	
					
			
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
				String busiName="tbTbWorkFlowUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			//	TbWorkFlowUI tTbWorkFlowUI   = new TbWorkFlowUI();
				if (!tBusinessDelegate.submitData(tVData,"0000001134",busiName)) //ִ�гб��˱������������ڵ�0000001134
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
