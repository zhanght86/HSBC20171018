<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：PRnewUWManuRReportChk.jsp
//程序功能：承保人工核保核保等待原因录入
//创建日期：2008-09-28 11:10:36
//创建人  ：ln
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
  //CErrors tErrors = new CErrors();
  String FlagStr = "Fail";
  String Content = "";
  GlobalInput tG = new GlobalInput(); 
	tG=(GlobalInput)session.getValue("GI");  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}

  	//接收信息
  	// 投保单列表 
	TransferData tTransferData = new TransferData();
	String tContNo = request.getParameter("ContNoH"); //被等待通知书回复的合同号
	String tMissionID = request.getParameter("MissionIDH");
	String tSubMissionID = request.getParameter("SubMissionIDH");
	String tWaitReason = request.getParameter("WaitReason");
	String tContent = request.getParameter("Content");
	String tUniteNo = request.getParameter("UniteNo");
		
	boolean flag = true;	
	

		if (!tContNo.equals("")&& !tMissionID.equals(""))		{		
			
			//准备公共传输信息
			tTransferData.setNameAndValue("WaitReason",tWaitReason);
			tTransferData.setNameAndValue("Content",tContent);
			tTransferData.setNameAndValue("MissionID",tMissionID);	
			tTransferData.setNameAndValue("SubMissionID",tSubMissionID);	
			tTransferData.setNameAndValue("ContNo",tContNo);
			tTransferData.setNameAndValue("UniteNo",tUniteNo);
			
		}
		else
		{
			flag = false;
			Content = "数据不完整!";
		}	
		loggerDebug("WaitReasonInputChk","flag:"+flag);
		try
		{
		  	if (flag == true)
		  	{
				// 准备传输数据 VData
				VData tVData = new VData();
				tVData.add( tTransferData);
				tVData.add( tG );
				
				// 数据传输
				WaitReasonInputUI tWaitReasonInputUI   = new WaitReasonInputUI();
				if (!tWaitReasonInputUI.submitData(tVData,"")) //执行承保核保核保等待处理
				{
					int n = tWaitReasonInputUI.mErrors.getErrorCount();
					loggerDebug("WaitReasonInputChk","ErrorCount:"+n ) ;
					Content = " 新契约核保等待处理失败，原因是: " + tWaitReasonInputUI.mErrors.getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//如果在Catch中发现异常，则不从错误类中提取错误信息
				if (FlagStr == "Fail")
				{
				    tError = tWaitReasonInputUI.mErrors;
				    if (!tError.needDealError())
				    {                          
				    	Content = " 新契约核保等待处理成功! ";
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	Content = " 新契约核保等待处理失败，原因是:" + tError.getFirstError();
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
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
