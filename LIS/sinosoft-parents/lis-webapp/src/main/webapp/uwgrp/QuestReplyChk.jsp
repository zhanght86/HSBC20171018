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
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.workflow.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
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
	LCContSet tLCContSet = new LCContSet();
	LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

	String tContNo = request.getParameter("ContNo");
	String tFlag = request.getParameter("Flag");
	String tBackObj = request.getParameter("Quest");
	String treply = request.getParameter("ReplyResult");
	String tSerialNo = request.getParameter("SerialNo");
	String QuesFlag = request.getParameter("QuesFlag");   //机构问题件回复完毕标志
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tContent = "";
	loggerDebug("QuestReplyChk","tMissionID="+tMissionID);
	loggerDebug("QuestReplyChk","tSubMissionID="+tSubMissionID);
	
	String tcont[] = request.getParameterValues("QuestGrid3");
	String tChk[] = request.getParameterValues("InpQuestGridChk");
	
	loggerDebug("QuestReplyChk","contno:"+tContNo);
	loggerDebug("QuestReplyChk","flag:"+tFlag);
	loggerDebug("QuestReplyChk","reply:"+treply);
	loggerDebug("QuestReplyChk","issuetype:"+tBackObj);
	loggerDebug("QuestReplyChk","tSerialNo:"+tSerialNo);
	boolean flag = true;
	if(QuesFlag.equals("allover"))           //如果执行“回复完毕”操作，则执行工作流节点
	{
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("MissionID",tMissionID);
		tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
		
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add( tG );
		
		TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
				if (tTbWorkFlowUI.submitData(tVData,"0000001020") == false)
				{
					int n = tTbWorkFlowUI.mErrors.getErrorCount();
					for (int i = 0; i < n; i++)
					Content = " 回收业务员通知书失败，原因是: " + tTbWorkFlowUI.mErrors.getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//如果在Catch中发现异常，则不从错误类中提取错误信息
				if (FlagStr == "Fail")
				{
				    tError = tTbWorkFlowUI.mErrors;
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
	else
	{
			if (tSerialNo.equals("")||tContNo.equals("") || tFlag.equals("") || treply.equals("") || tBackObj.equals(""))
			{
				Content = "数据录入不完全或传输失败!";
				FlagStr = "Fail";
				flag = false;
				loggerDebug("QuestReplyChk","失败");
			}
			else
			{
				loggerDebug("QuestReplyChk","begin");
  		
				LCContSchema tLCContSchema = new LCContSchema();
 					
				tLCContSchema.setContNo( tContNo);
  		
  		  		tLCContSet.add( tLCContSchema );
			    		
  		
					
				LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();
				
				//tLCIssuePolSchema.setIssueCont(tContent);
				tLCIssuePolSchema.setOperatePos(tFlag);
				tLCIssuePolSchema.setIssueType(tBackObj);
				tLCIssuePolSchema.setReplyResult(treply);
				tLCIssuePolSchema.setSerialNo(tSerialNo);
					
				tLCIssuePolSet.add(tLCIssuePolSchema);			    
				
			}
			
			loggerDebug("QuestReplyChk","flag:"+flag);
  			if (flag == true)
  			{
				// 准备传输数据 VData
				VData tVData = new VData();
				loggerDebug("QuestReplyChk","ContNo="+tLCContSet.get(1).getContNo());
				tVData.add( tLCContSet );
				tVData.add( tLCIssuePolSet);
				tVData.add( tG );
				
				// 数据传输
				QuestReplyChkUI tQuestReplyChkUI   = new QuestReplyChkUI();
				if (tQuestReplyChkUI.submitData(tVData,"INSERT") == false)
				{
					int n = tQuestReplyChkUI.mErrors.getErrorCount();
					for (int i = 0; i < n; i++)
					Content = " 自动核保失败，原因是: " + tQuestReplyChkUI.mErrors.getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//如果在Catch中发现异常，则不从错误类中提取错误信息
				if (FlagStr == "Fail")
				{
				    tError = tQuestReplyChkUI.mErrors;
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
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
