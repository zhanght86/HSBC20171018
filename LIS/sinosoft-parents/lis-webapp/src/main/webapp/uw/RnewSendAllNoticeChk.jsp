<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWSendPrintChk.jsp
//程序功能：送打印队列
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.workflow.xb.*"%>
  <%@page import="com.sinosoft.lis.xbcheck.*"%>
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
  //tongmeng 2007-11-09 modify
  //修改为统一发放核保通知书,一期以新契约为主,二期再对其他复用此功能程序做修改.
  //校验处理
  //内容待填充	
			
  	//接收信息
  	// 投保单列表	
	String tContNo = request.getParameter("ContNo");
	String tOtherNoType = "02";
	String mtestRnewFlag = "1";
	String tCode = request.getParameter("hiddenNoticeType");
	
	loggerDebug("RnewSendAllNoticeChk","tCode="+ tCode);
	String tMissionID=request.getParameter("MissionID");
	String tSubMissionID=request.getParameter("SubMissionID");
  loggerDebug("RnewSendAllNoticeChk","@@@@@@@@@@@@@@@@tSubMissionID:"+tSubMissionID+"################");
	boolean flag = true;
try
{
  	if (flag == true)
  	{
  	
  	//tongmeng 2008-08-12 modify
  	//为支持多主险多被保人,同一时间可以对不同接收对象发送核保通知书,
  	//并且体检和生调通知书也在这里发放.
  	//String tSQL = "select missionid,SubMissionID from lwmission where missionid='"+tMissionID+"' and activityid='0000007002'";
  	String mSQL = "select missionid,SubMissionID from lwmission where missionid='"+tMissionID+"' and activityid=(select activityid from lwactivity  where functionId = '10047001')";
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(mSQL);
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
			mTransferData.setNameAndValue("ContNo",tContNo);
			mTransferData.setNameAndValue("NoticeType",tCode);
			mTransferData.setNameAndValue("MissionID",tMissionId);
   			mTransferData.setNameAndValue("SubMissionID",tSubMissionId); 
   		 	mTransferData.setNameAndValue("BusiType", "1004"); 
   		 	mTransferData.setNameAndValue("testRnewFlag", mtestRnewFlag); 
    		tVData.add(mTransferData);
    		//String busiName="RnewWorkFlowUI";
    		String busiName="WorkFlowUI";
            BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
            if(!tBusinessDelegate.submitData(tVData,"create",busiName))
            {
				int n = tBusinessDelegate.getCErrors().getErrorCount();
				if(tBusinessDelegate.getCErrors().getError(0).errorMessage.equals("没有录入问题件、没有承保计划变更、没有特约、没有加费，不能发核保通知书!"))
				{
					errorCount = errorCount + 1;
					if(errorCount == tSSRS.getMaxRow())
					{
						FlagStr = "Fail";
						Content = " 操作失败,原因是:";
					}
					else
					{
						tBusinessDelegate.getCErrors().clearErrors();
						continue;
					}					    
				}
				else
				{			
					FlagStr = "Fail";	
				}
			}
    		/*
			// 数据传输
			RnewWorkFlowUI tRnewWorkFlowUI   = new RnewWorkFlowUI();
			//tongmeng 2007-11-08 modify
			//统一发放核保通知书
			if (tRnewWorkFlowUI.submitData(tVData,"0000007002") == false)
			{
				int n = tRnewWorkFlowUI.mErrors.getErrorCount();
				if(tRnewWorkFlowUI.mErrors.getError(0).errorMessage.equals("没有录入问题件、没有承保计划变更、没有特约、没有加费，不能发核保通知书!"))
				{
					errorCount = errorCount + 1;
					if(errorCount == tSSRS.getMaxRow())
					{
						FlagStr = "Fail";
						Content = " 操作失败,原因是:";
					}
					else
					{
						tRnewWorkFlowUI.mErrors.clearErrors();
						continue;
					}					    
				}
				else
				{			
					FlagStr = "Fail";	
				}
			}
			*/
			//如果在Catch中发现异常，则不从错误类中提取错误信息
			if (FlagStr == "Fail")
			{
		    	tError = tBusinessDelegate.getCErrors();		    
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
	parent.fraInterface.afterSubmitSendNotice("<%=FlagStr%>","<%=Content%>");
	
</script>
</html>
