<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuHealthChk.jsp
//程序功能：承保人工核保体检资料录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  //输出参数
  loggerDebug("RReportDealEachSave","start..................................");
  CErrors tError = new CErrors( );
        boolean operFlag=true;
		String tRela  = "";
		String FlagStr = "";
		String Content = "";
		String strOperation = "";
		
 
  GlobalInput tGlobalInput = new GlobalInput();
  tGlobalInput=(GlobalInput)session.getValue("GI");
  if(tGlobalInput == null) {
	out.println("session has expired");
	return;
  }

  loggerDebug("RReportDealEachSave","step1..................................");
  	// 投保单列表
	
  LCRReportPrtSet tLCRReportPrtSet = new LCRReportPrtSet();
	String tContNo = request.getParameter("ContNo");
	
  String tCustomerNo = request.getParameter("CustomerNo"); 
  String tCode = request.getParameter("RReportType");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tPrtSeq = request.getParameter("PrtSeq");
	
	String tSerialNo[] = request.getParameterValues("QuestionTypeGridNo");
	String tLCRReportPrtCode[] = request.getParameterValues("QuestionTypeGrid1");
  int ChkCount = 0;
	if(tSerialNo != null)
	{		
		ChkCount = tSerialNo.length;
	}

  
		// 准备传输数据 VData
	   VData tVData = new VData();
	   	VData mResult = new VData();
	   TransferData tTransferData = new TransferData();
	   tTransferData.setNameAndValue("ContNo",tContNo);
	   tTransferData.setNameAndValue("Code",tCode);
	   tTransferData.setNameAndValue("CustomerNo",tCustomerNo);
	   tTransferData.setNameAndValue("MissionID",tMissionID );
	   tTransferData.setNameAndValue("SubMissionID",tSubMissionID );
	   tTransferData.setNameAndValue("PrtSeq",tPrtSeq);
	   
    
     loggerDebug("RReportDealEachSave","tCode"+tCode);
     loggerDebug("RReportDealEachSave","tSubMissionID:"+tSubMissionID);
    
     	for(int i=0;i<ChkCount;i++)
			{
			 	LCRReportPrtSchema tLCRReportPrtSchema = new LCRReportPrtSchema();
		   tLCRReportPrtSchema.setAskCode(tLCRReportPrtCode[i]);
							
				tLCRReportPrtSet.add(tLCRReportPrtSchema);
			}

     tTransferData.setNameAndValue("LCRReportPrtSet",tLCRReportPrtSet);
	   tVData.add(tGlobalInput);
	   tVData.add(tTransferData);
	
	   
		// 数据传输
		TbWorkFlowUI tTbWorkFlowUI   = new TbWorkFlowUI();
		if (tTbWorkFlowUI.submitData(tVData,"0000001130") == false)
		{
			int n = tTbWorkFlowUI.mErrors.getErrorCount();
			Content = " 承保生调通知书发送失败，原因是:";
			Content = Content + tTbWorkFlowUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
	   tError = tTbWorkFlowUI.mErrors;
		    if (!tError.needDealError())
		    {
		   	Content = " 承保生调通知书发送成功! ";
		   	FlagStr = "Succ";
		    }
		   else
		   {
		    	Content = " 承保生调通知书发送失败，原因是:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
		}		
	
	%>
<html>
	
<script language="javascript">
		
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
