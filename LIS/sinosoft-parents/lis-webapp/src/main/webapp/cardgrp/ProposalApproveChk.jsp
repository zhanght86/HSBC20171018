<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ManuUWAllChk.jsp
//程序功能：申请核保
//创建日期：2005-01-19 11:10:36
//创建人  ：HYQ
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.workflow.cardgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  if(tG == null) 
  {
		loggerDebug("ProposalApproveChk","session has expired");
		return;
  }
     
 	// 投保单列表

  //String sApplyType = request.getParameter("ApplyType");
  String sMissionID = request.getParameter("MissionID");
  String sSubMissionID = request.getParameter("SubMissionID");
  String sActivityID = request.getParameter("ActivityID");
  String sPrtNo = request.getParameter("PrtNo1");
  loggerDebug("ProposalApproveChk","== sMissionID == " + sMissionID);    	   
  loggerDebug("ProposalApproveChk","== sSubMissionID == " + sSubMissionID); 
  loggerDebug("ProposalApproveChk","== sActivityID == " + sActivityID); 
  TransferData mTransferData = new TransferData();
  //mTransferData.setNameAndValue("ApplyType", sApplyType);	
  mTransferData.setNameAndValue("MissionID", sMissionID);
  mTransferData.setNameAndValue("SubMissionID", sSubMissionID);
  mTransferData.setNameAndValue("ActivityID", sActivityID);
  mTransferData.setNameAndValue("PrtNo",sPrtNo);
  
  try{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( mTransferData );
		tVData.add( tG );
		
		// 数据传输
		UWApplyUI tUWApplyUI = new UWApplyUI();
		if (tUWApplyUI.submitData(tVData,"") == false)
		{
			int n = tUWApplyUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			{
			  loggerDebug("ProposalApproveChk","Error: "+tUWApplyUI.mErrors.getError(i).errorMessage);
			  Content = " 申请失败，原因是: " + tUWApplyUI.mErrors.getError(0).errorMessage;
			}
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tUWApplyUI.mErrors;
		    loggerDebug("ProposalApproveChk","tError.getErrorCount:"+tError.getErrorCount());
		    if (!tError.needDealError())
		    {                          
		    	Content = " 申请成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " 申请失败，原因是:";
		    	int n = tError.getErrorCount();
    			if (n > 0)
    			{
			      for(int i = 0;i < n;i++)
			      {
			        //tError = tErrors.getError(i);
			        Content = Content.trim() +i+". "+ tError.getError(i).errorMessage.trim()+".";
			      }
					}
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
