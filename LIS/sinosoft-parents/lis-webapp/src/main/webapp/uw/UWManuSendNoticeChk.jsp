<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuRReportChk.jsp
//程序功能：新契约人工核保发送核保通知书报告录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
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
 String FlagStr = "Fail";
 String Content = "";
  GlobalInput tG = new GlobalInput(); 
	tG=(GlobalInput)session.getValue("GI");  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}

  	//接收信息
	TransferData tTransferData = new TransferData();
	String tContNo = request.getParameter("ContNo");
	String tPrtNo = request.getParameter("PrtNoHide");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubNoticeMissionID");	
	loggerDebug("UWManuSendNoticeChk","Contno:"+tContNo);
	loggerDebug("UWManuSendNoticeChk","flag:"+tPrtNo);
	loggerDebug("UWManuSendNoticeChk","flag:"+tMissionID);
	loggerDebug("UWManuSendNoticeChk","tSubMissionID:"+tSubMissionID);
	boolean flag = true;
	if (!tContNo.equals("")&&!tPrtNo.equals("")&& !tMissionID.equals(""))
	{
		//准备公共传输信息
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("PrtNo",tPrtNo) ;
		tTransferData.setNameAndValue("MissionID",tMissionID);	
		tTransferData.setNameAndValue("SubMissionID",tSubMissionID);	
	
	}
	else
	{
		flag = false;
		Content = "数据不完整!";
	}	
	loggerDebug("UWManuSendNoticeChk","flag:"+flag);
try
{
  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tTransferData);
		tVData.add( tG );
		
		// 数据传输
		TbWorkFlowUI tTbWorkFlowUI   = new TbWorkFlowUI();
		if (!tTbWorkFlowUI.submitData(tVData,"0000001105")) //执行保全核保生调工作流节点0000000004
		{
			int n = tTbWorkFlowUI.mErrors.getErrorCount();
			Content = " 新契约人工核发送核保通知书失败，原因是: " + tTbWorkFlowUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tTbWorkFlowUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " 新契约人工核保发送核保通知书成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " 新契约人工核保发送核保通知书失败，原因是:" + tError.getFirstError();
		    	FlagStr = "Fail";
		     }
		}
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
	parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");
</script>
</html>
