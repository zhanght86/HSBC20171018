<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ManuUWAllChk.jsp
//程序功能：申请核保
//创建日期：2005-10-09 11:10:36
//创建人  ：续涛
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  if(tG == null) 
  {
		loggerDebug("UWAutoApply","session has expired");
		return;
  }
     
 	// 投保单列表

  //String sApplyType = request.getParameter("ApplyType");
  String sMissionID = request.getParameter("MissionID");
  String sSubMissionID = request.getParameter("SubMissionID");
  String sActivityID = request.getParameter("ActivityID");
  loggerDebug("UWAutoApply","== sMissionID == " + sMissionID);    	   
  loggerDebug("UWAutoApply","== sSubMissionID == " + sSubMissionID); 
  loggerDebug("UWAutoApply","== sActivityID == " + sActivityID); 
  TransferData mTransferData = new TransferData();
  //mTransferData.setNameAndValue("ApplyType", sApplyType);	
  mTransferData.setNameAndValue("MissionID", sMissionID);
  mTransferData.setNameAndValue("SubMissionID", sSubMissionID);
  mTransferData.setNameAndValue("ActivityID", sActivityID);
  
  try{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( mTransferData );
		tVData.add( tG );
		
		// 数据传输
		//UWApplyUI tUWApplyUI = new UWApplyUI();
		String busiName="cbcheckUWApplyUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        loggerDebug("UWAutoApply","-----------自动核保申请---开始----------");
		if (tBusinessDelegate.submitData(tVData,"",busiName) == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			{
			  loggerDebug("UWAutoApply","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			  Content = " 申请失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			}
			FlagStr = "Fail";
		}
        loggerDebug("UWAutoApply","-----------自动核保申请---结束----------"+FlagStr);
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    loggerDebug("UWAutoApply","tError.getErrorCount:"+tError.getErrorCount());
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
parent.fraInterface.afterApplyUW("<%=FlagStr%>","<%=Content%>");
</script>
</html>
