<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.service.*" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
//�������ƣ�NoScanContInputSave.jsp
//�����ܣ���������Լ��ɨ�������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.workflow.cardgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%
//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
String tFlag = request.getParameter("tFlag");
String ContType = request.getParameter("ContType");
loggerDebug("NoScanContInputSave","tFlag: "+tFlag);
if(tFlag.equals("1"))
 {
   if(ContType==null||ContType.equals(""))
   {
	  ContType = "TB01";
   }
 }
else
   ContType = "TB05" ; 
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");
if(tG == null)
{
	loggerDebug("NoScanContInputSave","session has expired");
	return;
}
//prepare data for workflow
VData tVData = new VData();
TransferData tTransferData = new TransferData();
tTransferData.setNameAndValue("PrtNo",request.getParameter("PrtNo"));
tTransferData.setNameAndValue("ManageCom",request.getParameter("ManageCom"));
tTransferData.setNameAndValue("InputDate",PubFun.getCurrentDate());
tTransferData.setNameAndValue("Operator",tG.Operator);
tTransferData.setNameAndValue("SubType",ContType);
tVData.add( tTransferData );
tVData.add( tG );
try
{
	if(tFlag.equals("2")) 
	{
		//�����ŵ��ĵ�һ���ڵ�����ɣ���ɨ��¼�룩
		//GrpTbWorkFlowUI tGrpTbWorkFlowUI = new GrpTbWorkFlowUI();
		String busiName="cardgrpGrpTbWorkFlowUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if (tBusinessDelegate.submitData(tVData,"7699999999",busiName) == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			loggerDebug("NoScanContInputSave","n=="+n);
			for (int j = 0; j < n; j++)
			loggerDebug("NoScanContInputSave","Error: "+tBusinessDelegate.getCErrors().getError(j).errorMessage);
			Content = " Ͷ��������ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr != "Fail")
		{
			tError = tBusinessDelegate.getCErrors();
			//tErrors = tTbWorkFlowUI.mErrors;
			Content = " Ͷ��������ɹ�! ";
			if (!tError.needDealError())
			{
				int n = tError.getErrorCount();
				if (n > 0)
				{
					for(int j = 0;j < n;j++)
					{
						//tError = tErrors.getError(j);
						Content = Content.trim() +j+". "+ tError.getError(j).errorMessage.trim()+".";
					}
				}
				FlagStr = "Succ";
			}
			else
			{
				int n = tError.getErrorCount();
				if (n > 0)
				{
					for(int j = 0;j < n;j++)
					{
						//tError = tErrors.getError(j);
						Content = Content.trim() +j+". "+ tError.getError(j).errorMessage.trim()+".";
					}
				}
				FlagStr = "Fail";
			}
		}
	}
	else if(tFlag.equals("1")||tFlag.equals("3"))
		{ 
			//GrpTbWorkFlowUI tGrpTbWorkFlowUI = new GrpTbWorkFlowUI();
			String busiName="cardgrpGrpTbWorkFlowUI";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			if (tBusinessDelegate.submitData(tVData,"7799999999",busiName) == false)
				{
					int n = tBusinessDelegate.getCErrors().getErrorCount();
					loggerDebug("NoScanContInputSave","n=="+n);
					for (int j = 0; j < n; j++)
					loggerDebug("NoScanContInputSave","Error: "+tBusinessDelegate.getCErrors().getError(j).errorMessage);
					Content = " Ͷ��������ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
		  				//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
				if (FlagStr != "Fail")
				{
				    tError = tBusinessDelegate.getCErrors();
				    //tErrors = tTbWorkFlowUI.mErrors;
				    Content = " Ͷ��������ɹ�! ";
				    if (!tError.needDealError())
				    {
				    	int n = tError.getErrorCount();
		    			if (n > 0)
		    			{
					      for(int j = 0;j < n;j++)
					      {
					        //tError = tErrors.getError(j);
					        Content = Content.trim() +j+". "+ tError.getError(j).errorMessage.trim()+".";
					      }
					    }

				    	FlagStr = "Succ";
				    }
				    else
				    {
				    	int n = tError.getErrorCount();
		    			if (n > 0)
		    			{
					      for(int j = 0;j < n;j++)
					      {
					        //tError = tErrors.getError(j);
					        Content = Content.trim() +j+". "+ tError.getError(j).errorMessage.trim()+".";
					      }
							}
				    	FlagStr = "Fail";
				    }
				}
		}else if(tFlag.equals("5"))
		{
			//GrpTbWorkFlowUI tGrpTbWorkFlowUI = new GrpTbWorkFlowUI();
			String busiName="cardgrpGrpTbWorkFlowUI";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  if (tBusinessDelegate.submitData(tVData,"7099999999",busiName) == false)
				{
					int n = tBusinessDelegate.getCErrors().getErrorCount();
					loggerDebug("NoScanContInputSave","n=="+n);
					for (int j = 0; j < n; j++)
					loggerDebug("NoScanContInputSave","Error: "+tBusinessDelegate.getCErrors().getError(j).errorMessage);
					Content = " Ͷ��������ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
		  				//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
				if (FlagStr != "Fail")
				{
				    tError = tBusinessDelegate.getCErrors();
				    //tErrors = tTbWorkFlowUI.mErrors;
				    Content = " Ͷ��������ɹ�! ";
				    if (!tError.needDealError())
				    {
				    	int n = tError.getErrorCount();
		    			if (n > 0)
		    			{
					      for(int j = 0;j < n;j++)
					      {
					        //tError = tErrors.getError(j);
					        Content = Content.trim() +j+". "+ tError.getError(j).errorMessage.trim()+".";
					      }
					    }

				    	FlagStr = "Succ";
				    }
				    else
				    {
				    	int n = tError.getErrorCount();
		    			if (n > 0)
		    			{
					      for(int j = 0;j < n;j++)
					      {
					        //tError = tErrors.getError(j);
					        Content = Content.trim() +j+". "+ tError.getError(j).errorMessage.trim()+".";
					      }
							}
				    	FlagStr = "Fail";
				    }
				}
		}

	}
	catch(Exception ex)
	{
			ex.printStackTrace();
			Content = Content.trim() +" ��ʾ:�쳣�˳�.";
	}
%>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
