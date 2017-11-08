<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：RReportReplyChk.jsp
//程序功能：生存调查报告回复
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人：ln    更新日期：2008-10-23   更新原因/内容：根据新核保要求进行修改
%>

<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
<%
  //输出参数
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
  
  //校验处理
  //内容待填充
  
  	//接收信息
  	// 投保单列表
	LCPolSet tLCPolSet = new LCPolSet();
	LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
	LCSpecSet tLCSpecSet = new LCSpecSet();
	LCPremSet tLCPremSet = new LCPremSet();
	LCRReportSchema tLCRReportSchema = new LCRReportSchema();
	
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
	//add by lzf 
	String tActivityID = request.getParameter("ActivityID");
	loggerDebug("MSRReportReplyChk","tActivityID:"+tActivityID);
	loggerDebug("MSRReportReplyChk","tMissionID:"+tMissionID);
	loggerDebug("MSRReportReplyChk","tSubMissionID:"+tSubMissionID);
	
	loggerDebug("MSRReportReplyChk","polno:"+tProposalNo);
	loggerDebug("MSRReportReplyChk","content:"+tContent);
	loggerDebug("MSRReportReplyChk","flag:"+tFlag);
	
	boolean flag = true;
	
	if (!tProposalNo.equals("")&&!tContent.equals(""))
	{
		tLCRReportSchema.setContNo(tProposalNo);
		tLCRReportSchema.setRemark(tReporter); //生存调查人
		tLCRReportSchema.setRReportFee(tReportFee); //生调费用
		tLCRReportSchema.setReplyContente(tReplyContent);
		tLCRReportSchema.setPrtSeq(tPrtSeq);
		
	}
	else
	{
		flag = false;
		Content = "数据不完整!";
	}
	
	loggerDebug("MSRReportReplyChk","flag:"+flag);
try
{
  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tLCRReportSchema);
//*********************************add ln 2008-11-1**加入工作流************************************//		
		
	  TransferData tTransferData = new TransferData();
	  tTransferData.setNameAndValue("MissionID",tMissionID);
	  tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
	  tTransferData.setNameAndValue("ContNo",tProposalNo);
	  tTransferData.setNameAndValue("ActivityID",tActivityID);
	  tVData.add(tTransferData);
	  tVData.add(tG);

	  loggerDebug("MSRReportReplyChk","---------------------------workflow start-------------------------");
	  
		//TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
		WorkFlowUI tWorkFlowUI = new WorkFlowUI();

			loggerDebug("MSRReportReplyChk","this will save the data!!!");
	  if(tWorkFlowUI.submitData(tVData,"execute")){
		
				Content = " 保存成功! ";
				FlagStr = "Succ";
		
		}
		else{
			Content = "保存失败，原因是:" + tWorkFlowUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
////////////////////////////delete/////////////////////////////////////////////////////////////
		
		// 数据传输
		/*********************
		MSUWRReportReplyUI tUWRReportReplyUI   = new MSUWRReportReplyUI();
		if (tUWRReportReplyUI.submitData(tVData,"INSERT") == false)
		{
			int n = tUWRReportReplyUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " 生调回复失败，原因是: " + tUWRReportReplyUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tUWRReportReplyUI.mErrors;		    
		    if (!tError.needDealError())
		    {                          
		    	Content = " 生调回复成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " 生调回复失败，原因是:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
		}
		*********************/
	} 
}
catch(Exception e)
{
	e.printStackTrace();
	Content = Content.trim()+".提示：异常终止!";
}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
