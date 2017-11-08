<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.service.*" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
//程序名称：NoScanContInputSave.jsp
//程序功能：个单新契约无扫描件保单录入
//创建日期：2002-06-19 11:10:36
//创建人  ：HYQ
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.workflow.cardgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%
//输出参数
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
		//对于团单的第一个节点的生成（无扫描录入）
		//GrpTbWorkFlowUI tGrpTbWorkFlowUI = new GrpTbWorkFlowUI();
		String busiName="cardgrpGrpTbWorkFlowUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if (tBusinessDelegate.submitData(tVData,"7699999999",busiName) == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			loggerDebug("NoScanContInputSave","n=="+n);
			for (int j = 0; j < n; j++)
			loggerDebug("NoScanContInputSave","Error: "+tBusinessDelegate.getCErrors().getError(j).errorMessage);
			Content = " 投保单申请失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr != "Fail")
		{
			tError = tBusinessDelegate.getCErrors();
			//tErrors = tTbWorkFlowUI.mErrors;
			Content = " 投保单申请成功! ";
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
					Content = " 投保单申请失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
		  				//如果在Catch中发现异常，则不从错误类中提取错误信息
				if (FlagStr != "Fail")
				{
				    tError = tBusinessDelegate.getCErrors();
				    //tErrors = tTbWorkFlowUI.mErrors;
				    Content = " 投保单申请成功! ";
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
					Content = " 投保单申请失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
		  				//如果在Catch中发现异常，则不从错误类中提取错误信息
				if (FlagStr != "Fail")
				{
				    tError = tBusinessDelegate.getCErrors();
				    //tErrors = tTbWorkFlowUI.mErrors;
				    Content = " 投保单申请成功! ";
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
			Content = Content.trim() +" 提示:异常退出.";
	}
%>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
