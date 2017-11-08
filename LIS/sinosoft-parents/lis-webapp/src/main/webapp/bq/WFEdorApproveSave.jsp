<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：WFEdorApproveSave.jsp
//程序功能：保全工作流复核申请校验
//创建日期：2005-12-17 15:10:36
//创建人  ：sinosoft
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
  <%@page import="com.sinosoft.lis.bq.*"%>
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
	System.out.println("session has expired");
	return;
  }

  String busiName="tPEdorPopedomCheckBL";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  String sMissionID = request.getParameter("MissionID");
  String sSubMissionID = request.getParameter("SubMissionID");
  String sActivityID = request.getParameter("ActivityID");
  String sEdorAcceptNo = request.getParameter("EdorAcceptNo");
  
  TransferData mTransferData = new TransferData();
  mTransferData.setNameAndValue("MissionID", sMissionID);
  mTransferData.setNameAndValue("SubMissionID", sSubMissionID);
  mTransferData.setNameAndValue("ActivityID", sActivityID);
  mTransferData.setNameAndValue("EdorAcceptNo", sEdorAcceptNo);
  
	// 准备传输数据 VData
	VData tVData = new VData();
	tVData.add( mTransferData );
	tVData.add( tG );
		
  
  try
  {		
//      PEdorPopedomCheckBL tPEdorPopedomCheckBL = new PEdorPopedomCheckBL();
//      if (!tPEdorPopedomCheckBL.submitData(tVData, "Approve"))
      if (!tBusinessDelegate.submitData(tVData, "Approve",busiName))
      {
      	tError = tBusinessDelegate.getCErrors();
        Content = "申请失败，原因是:" + tError.getFirstError();
        FlagStr = "Fail";
      }
      else
      {
		// 数据传输
		//UWApplyUI tUWApplyUI = new UWApplyUI();
		busiName="UWApplyUI";
 	    tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if (tBusinessDelegate.submitData(tVData,"",busiName) == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			{
			  System.out.println("Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			  Content = " 申请失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			}
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    System.out.println("tError.getErrorCount:"+tError.getErrorCount());
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
