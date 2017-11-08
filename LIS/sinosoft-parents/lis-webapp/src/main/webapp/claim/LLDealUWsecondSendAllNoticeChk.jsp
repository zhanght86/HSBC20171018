<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLDealUWsecondSendAllNoticeChk.jsp
//程序功能：送打印队列
//创建日期：2009-01-15 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  
<%
  //输出参数
  CErrors tError = null;
  //CErrors tErrors = null;
  String FlagStr = "Fail";
  String Content = "部分通知书发送失败,原因是:";

	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}

			
  	//接收信息
  	// 投保单列表	
	String tCaseNo = request.getParameter("CaseNo");
	
	String tCode = request.getParameter("hiddenNoticeType");
	
	loggerDebug("LLDealUWsecondSendAllNoticeChk","tCode="+ tCode);
	String tMissionID=request.getParameter("MissionID");
	String tSubMissionID=request.getParameter("SubMissionID");
    loggerDebug("LLDealUWsecondSendAllNoticeChk","@@@@@@@@@@@@@@@@tSubMissionID:"+tSubMissionID+"################");
	boolean flag = true;
	try
	{
	  	if (flag == true)
	  	{
	  	
	  	//tongmeng 2008-08-12 modify
	  	//为支持多主险多被保人,同一时间可以对不同接收对象发送核保通知书,
	  	//并且体检和生调通知书也在这里发放.
	  	String tSQL = "select missionid,SubMissionID from lwmission where missionid='"+tMissionID+"' and activityid='0000000100' ";
	  	
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(tSQL);
		int errorCount = 0;
		for(int m=1;m<=tSSRS.getMaxRow();m++)
		{
			String tMissionId = tSSRS.GetText(m,1);
			String tSubMissionId = tSSRS.GetText(m,2);
			// 准备传输数据 VData
			VData tVData = new VData();
			//tVData.add( tLOPRTManagerSchema);
			tVData.add( tG );
			TransferData mTransferData = new TransferData();
			mTransferData.setNameAndValue("CaseNo",tCaseNo);
			mTransferData.setNameAndValue("NoticeType",tCode);
			mTransferData.setNameAndValue("MissionID",tMissionId);
   			mTransferData.setNameAndValue("SubMissionID",tSubMissionId); 
   			mTransferData.setNameAndValue("mSubMissionID",tSubMissionID); 
    
    		tVData.add(mTransferData);
			// 数据传输
			EdorWorkFlowUI tEdorWorkFlowUI   = new EdorWorkFlowUI();
			//tongmeng 2007-11-08 modify
			//统一发放核保通知书
			if (tEdorWorkFlowUI.submitData(tVData,"0000000100") == false) //1105
			{
				int n = tEdorWorkFlowUI.mErrors.getErrorCount();
				if(tEdorWorkFlowUI.mErrors.getError(0).errorMessage.equals("没有录入问题件、没有承保计划变更、没有特约、没有加费，不能发核保通知书!"))
				{
					errorCount = errorCount + 1;
					if(errorCount == tSSRS.getMaxRow())
					{
						FlagStr = "Fail";
						Content = " 操作失败,原因是:";
					}
					else
					{
						tEdorWorkFlowUI.mErrors.clearErrors();
						continue;
					}					    
				}
				else
				{			
					FlagStr = "Fail";	
				}
			}
			//如果在Catch中发现异常，则不从错误类中提取错误信息
			if (FlagStr == "Fail")
			{
		    	tError = tEdorWorkFlowUI.mErrors;		    
		    	if (!tError.needDealError())
		    	{                     
		    		if (m == tSSRS.getMaxRow())
			    	{                     
			    		Content = " 操作成功! ";
			    	}
		    		FlagStr = "Succ";
		    	}
		    	else                                                              
		   		{		    		
		    		int n = tError.getErrorCount();
    				if (n > 0)
    				{
			      	for(int i = 0;i < n;i++)
			      	{			        
			        	Content = Content.trim() +i+". "+ tError.getError(i).errorMessage.trim()+".";
			      	}
					}
		    			FlagStr = "Fail";
		    			break;
		   		}
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
