<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：QuestReplyChk.jsp
//程序功能：问题件回复
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.lis.xbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
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
  
  //校验处理
  //内容待填充
  
  	//接收信息
  	// 投保单列表
	RnewIssuePolSet tRnewIssuePolSet = new RnewIssuePolSet();

	String tContNo = request.getParameter("ContNo");
	String tFlag = request.getParameter("Flag");
	String tBackObj = request.getParameter("Quest");
	String treply = request.getParameter("ReplyResult");
	String tSerialNo = request.getParameter("SerialNo");
	String QuesFlag = request.getParameter("QuesFlag");   //机构问题件回复完毕标志
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tContent = "";
	loggerDebug("RnewQuestReplyChk","tMissionID="+tMissionID);
	loggerDebug("RnewQuestReplyChk","tSubMissionID="+tSubMissionID);
	
	String tcont[] = request.getParameterValues("QuestGrid3");
	String tChk[] = request.getParameterValues("InpQuestGridChk");
	
	loggerDebug("RnewQuestReplyChk","contno:"+tContNo);
	loggerDebug("RnewQuestReplyChk","flag:"+tFlag);
	loggerDebug("RnewQuestReplyChk","reply:"+treply);
	loggerDebug("RnewQuestReplyChk","issuetype:"+tBackObj);
	loggerDebug("RnewQuestReplyChk","tSerialNo:"+tSerialNo);
	boolean flag = true;
	if(QuesFlag.equals("allover"))           //如果执行“回复完毕”操作，则执行工作流节点
	{
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("MissionID",tMissionID);
		tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
		tTransferData.setNameAndValue("BusiType", "1004");
		 ExeSQL yExeSQL = new ExeSQL();
			SSRS ySSRS = new SSRS();
		  String sqlstr="select activityid from lwactivity where functionid='10047015'";
			ySSRS = yExeSQL.execSQL(sqlstr);
			String xActivityID="";
			if(ySSRS.MaxRow==0)
			{}
			else
			{
				 xActivityID = ySSRS.GetText(1,1);
			}
		  tTransferData.setNameAndValue("ActivityID", xActivityID);
		  RnewCommonDataPut tRnewCommonDataPut = new RnewCommonDataPut();
		  if(!tRnewCommonDataPut.submitData(tTransferData))
		  {
			  Content = "保存失败，原因是:" + tRnewCommonDataPut.getErrors().getFirstError();
		      FlagStr = "Fail";
		  }
		  tTransferData=tRnewCommonDataPut.getTransferData();
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add( tG );
		String busiName="WorkFlowUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if(!tBusinessDelegate.submitData(tVData,"execute",busiName)){
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " 机构问题件处理失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
	/*	
		EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI();
				if (tEdorWorkFlowUI.submitData(tVData,"0000007015") == false)
				{
					int n = tEdorWorkFlowUI.mErrors.getErrorCount();
					for (int i = 0; i < n; i++)
					Content = " 机构问题件处理失败，原因是: " + tEdorWorkFlowUI.mErrors.getError(0).errorMessage;
					FlagStr = "Fail";
				}
	*/
				//如果在Catch中发现异常，则不从错误类中提取错误信息
				if (FlagStr == "Fail")
				{
				    tError = tBusinessDelegate.getCErrors();
				    if (!tError.needDealError())
				    {                          
				    	Content = " 处理成功! ";
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	Content = " 处理失败，原因是:" + tError.getFirstError();
				    	FlagStr = "Fail";
				    }		
				}
	}
	else
	{
			if (tSerialNo.equals("")||tContNo.equals("") || tFlag.equals("") || treply.equals("") || tBackObj.equals(""))
			{
				Content = "数据录入不完全或传输失败!";
				FlagStr = "Fail";
				flag = false;
				loggerDebug("RnewQuestReplyChk","失败");
			}
			else
			{
				loggerDebug("RnewQuestReplyChk","begin");	
  		
					
				RnewIssuePolSchema tRnewIssuePolSchema = new RnewIssuePolSchema();
				
				tRnewIssuePolSchema.setContNo(tContNo);
				tRnewIssuePolSchema.setIssueCont(tContent);			
				tRnewIssuePolSchema.setOperatePos(tFlag);
				tRnewIssuePolSchema.setIssueType(tBackObj);
				tRnewIssuePolSchema.setReplyResult(treply);
				tRnewIssuePolSchema.setSerialNo(tSerialNo);
				tRnewIssuePolSet.add(tRnewIssuePolSchema);			    
				
			}
			
			loggerDebug("RnewQuestReplyChk","flag:"+flag);
  			if (flag == true)
  			{
				// 准备传输数据 VData
				VData tVData = new VData();
				loggerDebug("RnewQuestReplyChk","ContNo="+tContNo);
				tVData.add( tRnewIssuePolSet);
				tVData.add( tG );
				
				// 数据传输
				RnewQuestReplyChkUI tRnewQuestReplyChkUI   = new RnewQuestReplyChkUI();
				if (tRnewQuestReplyChkUI.submitData(tVData,"INSERT") == false)
				{
					int n = tRnewQuestReplyChkUI.mErrors.getErrorCount();
					for (int i = 0; i < n; i++)
					Content = " 机构问题件回复失败，原因是: " + tRnewQuestReplyChkUI.mErrors.getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//如果在Catch中发现异常，则不从错误类中提取错误信息
				if (FlagStr == "Fail")
				{
				    tError = tRnewQuestReplyChkUI.mErrors;
				    if (!tError.needDealError())
				    {                          
				    	Content = " 人工核保成功! ";
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	Content = " 人工核保失败，原因是:" + tError.getFirstError();
				    	FlagStr = "Fail";
				    }
				}
			} 
	}
%>                      
<%@page import="com.sinosoft.lis.xb.RnewCommonDataPut"%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
