<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：PEdorReprintSava.jsp
//程序功能：保全人工核保补打通知书
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
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
	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	String tPrtSeq = request.getParameter("PrtSeq");
	String tCode = request.getParameter("Code");
	String tContNo = request.getParameter("ContNo");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	
	loggerDebug("RnewRePrintSave1","ContNo:"+tContNo);
	loggerDebug("RnewRePrintSave1","MissionID:"+tMissionID);
	loggerDebug("RnewRePrintSave1","SubMissionID:"+tSubMissionID);
	loggerDebug("RnewRePrintSave1","Code:"+tCode);
	boolean flag = true;
	if (!tContNo.equals("")&& !tMissionID.equals("") && !tSubMissionID.equals(""))
	{
		//准备公共传输信息
		tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("Code",tCode);
		tTransferData.setNameAndValue("MissionID",tMissionID);	
		tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
		tTransferData.setNameAndValue("ActivityID",tActivityID);
		tTransferData.setNameAndValue("LOPRTManagerSchema",tLOPRTManagerSchema);
	}
	else
	{
		flag = false;
		Content = "数据不完整!";
	}	
	loggerDebug("RnewRePrintSave1","flag:"+flag);
	loggerDebug("RnewRePrintSave1","tCode:"+tCode);
try
{
	String tOperate = "";
  	if(!tActivityID.equals(""))
	   {
		// 准备传输数据 VData				
	      tOperate = tActivityID;
	   }
	    loggerDebug("RnewRePrintSave1","tOperate"+tOperate);
	      
		VData tVData = new VData();
		tVData.add( tTransferData);
		tVData.add( tG );
		
		// 数据传输
		 String busiName="WorkFlowUI";
		 BusinessDelegate  tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
     	System.out.println("开始执行：tBusinessDelegate");
     	if(!tBusinessDelegate.submitData(tVData,"execute",busiName)){
     		int n = tBusinessDelegate.getCErrors().getErrorCount();
     		Content = " 补打通知书数据提交失败，原因是: " +tBusinessDelegate.getCErrors().getError(0).errorMessage;
     		FlagStr = "Fail";
     	}
		/*
		EdorWorkFlowUI tEdorWorkFlowUI   = new EdorWorkFlowUI();
		if (!tEdorWorkFlowUI.submitData(tVData,tOperate))
		{
			int n = tEdorWorkFlowUI.mErrors.getErrorCount();
			Content = " 补打通知书数据提交失败，原因是: " + tEdorWorkFlowUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		*/
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
		    	Content = "补打通知书数据提交成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " 补打通知书数据提交失败，原因是:" + tError.getFirstError();
		    	FlagStr = "Fail";
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
