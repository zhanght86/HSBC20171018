<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�PRnewUWManuRReportChk.jsp
//�����ܣ��б��˹��˱�������鱨��¼��
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
  	// Ͷ�����б�
	LCRReportSchema tLCRReportSchema = new LCRReportSchema();
  LCRReportItemSet tLCRReportItemSet = new LCRReportItemSet();
  LCRReportItemSchema tLCRReportItemSchema ;
  
	TransferData tTransferData = new TransferData();
	String tContNo = request.getParameter("ContNo");
	String tPrtNo = request.getParameter("PrtNo");
	String tCustomerNo = request.getParameter("CustomerNo");
	String tCustomerName = request.getParameter("CustomerName");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tOperator = request.getParameter("Operator");
	String tFlag = request.getParameter("Flag");
	//String tRemark = request.getParameter("Remark");
	String tContente = request.getParameter("Contente");
	String tRReportReason = request.getParameter("RReportReason");

 	//tongmeng 2007-12-19 modify
 	//�޸�ΪMS���������� 
 	/*
	String tSerialNo[] = request.getParameterValues("InvestigateGridNo");
	String tRReportItemCode[] = request.getParameterValues("InvestigateGrid1");
	String tRReportItemName[] = request.getParameterValues("InvestigateGrid2");
	String tRemark[] = request.getParameterValues("InvestigateGrid3");
		*/
	boolean flag = true;
	/*
	int ChkCount = 0;
	if(tSerialNo != null)
	{		
		ChkCount = tSerialNo.length;
	}
	loggerDebug("UWManuRReportChk","count:"+ChkCount);
	if (ChkCount == 0 )
	{
		Content = "����������Ϣ¼�벻����!";
		FlagStr = "Fail";
		flag = false;
	}
	else*/
	//{
		if (!tContNo.equals("")&& !tMissionID.equals(""))
		{
			tLCRReportSchema.setContNo(tContNo);
			tLCRReportSchema.setContente(tContente);
			//tLCRReportSchema.setRemark(tRemark); //add ln 2008-10-23 --��������Ҫ��
			tLCRReportSchema.setName(tCustomerName);
			tLCRReportSchema.setRReportReason(tRReportReason);
			
			//׼������������Ϣ
			tTransferData.setNameAndValue("ContNo",tContNo);
			tTransferData.setNameAndValue("PrtNo",tPrtNo) ;
			tTransferData.setNameAndValue("CustomerNo",tCustomerNo) ;
			tTransferData.setNameAndValue("MissionID",tMissionID);	
			tTransferData.setNameAndValue("SubMissionID",tSubMissionID);	
			tTransferData.setNameAndValue("LCRReportSchema",tLCRReportSchema);
			tTransferData.setNameAndValue("ProposalContNo",request.getParameter("ProposalContNo"));
			tTransferData.setNameAndValue("PrtSeq",request.getParameter("PrtSeq"));
			
			/*for(int i=0;i<ChkCount;i++)
			{
				tLCRReportItemSchema = new LCRReportItemSchema();
				
				tLCRReportItemSchema.setRReportItemCode(tRReportItemCode[i]);
				tLCRReportItemSchema.setRReportItemName(tRReportItemName[i]);
				tLCRReportItemSchema.setRemark(tRemark[i]);
				
				tLCRReportItemSet.add(tLCRReportItemSchema);
			}
			
			tTransferData.setNameAndValue("LCRReportItemSet",tLCRReportItemSet);
			loggerDebug("UWManuRReportChk","len="+tLCRReportItemSet.size());*/
		}
		else
		{
			flag = false;
			Content = "���ݲ�����!";
		}	
		loggerDebug("UWManuRReportChk","flag:"+flag);
		try
		{
		  	if (flag == true)
		  	{
				// ׼���������� VData
				VData tVData = new VData();
				tVData.add( tTransferData);
				tVData.add( tG );
				
				// ���ݴ���
				UWRReportChkUI tUWRReportChkUI   = new UWRReportChkUI();
				if (tUWRReportChkUI.submitData(tVData,"") == false)
				{
					int n = tUWRReportChkUI.mErrors.getErrorCount();
					for (int i = 0; i < n; i++)
					Content = " ����¼��ʧ�ܣ�ԭ����: " + tUWRReportChkUI.mErrors.getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
				if (FlagStr == "Fail")
				{
				    tError = tUWRReportChkUI.mErrors;
				    if (!tError.needDealError())
				    {                          
				    	Content = " ����¼��ɹ�! ";
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	Content = " ����¼��ʧ�ܣ�ԭ����:" + tError.getFirstError();
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
//	}

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
