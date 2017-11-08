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
    <%@page import="com.sinosoft.service.*" %>
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
	LCGrpContSet tLCGrpContSet = new LCGrpContSet();
	LCGrpIssuePolSet tLCGrpIssuePolSet = new LCGrpIssuePolSet();

	String tGrpContNo = request.getParameter("GrpContNo");
	String tFlag = request.getParameter("Flag");
	String tBackObj = request.getParameter("Quest");
	String treply = request.getParameter("ReplyResult");
	String tSerialNo = request.getParameter("SerialNo");
	String QuesFlag = request.getParameter("QuesFlag");   //机构问题件回复完毕标志
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tContent = "";
	loggerDebug("GrpQuestReplyChk","tMissionID="+tMissionID);
	loggerDebug("GrpQuestReplyChk","tSubMissionID="+tSubMissionID);
	
	String tcont[] = request.getParameterValues("QuestGrid3");
	String tChk[] = request.getParameterValues("InpQuestGridChk");
	
	loggerDebug("GrpQuestReplyChk","GrpContNo:"+tGrpContNo);
	loggerDebug("GrpQuestReplyChk","flag:"+tFlag);
	loggerDebug("GrpQuestReplyChk","reply:"+treply);
	loggerDebug("GrpQuestReplyChk","issuetype:"+tBackObj);
	loggerDebug("GrpQuestReplyChk","tSerialNo:"+tSerialNo);
	boolean flag = true;
/*	if(QuesFlag.equals("allover"))           //如果执行“回复完毕”操作，则执行工作流节点
	{
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("GrpContNo",tGrpContNo);
		tTransferData.setNameAndValue("MissionID",tMissionID);
		tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
		
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add( tG );
		 String busiName="tbTbWorkFlowUI";
		   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		//TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
				if (tBusinessDelegate.submitData(tVData,"0000001020",busiName) == false)
				{
					int n = tBusinessDelegate.getCErrors().getErrorCount();
					for (int i = 0; i < n; i++)
					Content = " 回收业务员通知书失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//如果在Catch中发现异常，则不从错误类中提取错误信息
				if (FlagStr == "Fail")
				{
				    tError = tBusinessDelegate.getCErrors();
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
*/	{
			if (tSerialNo.equals("")||tGrpContNo.equals("") || tFlag.equals("") || treply.equals("") || tBackObj.equals(""))
			{
				Content = "数据录入不完全或传输失败!";
				FlagStr = "Fail";
				flag = false;
				loggerDebug("GrpQuestReplyChk","失败");
			}
			else
			{
				loggerDebug("GrpQuestReplyChk","begin");
  		
				LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
 					
				tLCGrpContSchema.setGrpContNo( tGrpContNo);
  		
  		  		tLCGrpContSet.add( tLCGrpContSchema );
			    		
  		
					
				LCGrpIssuePolSchema tLCGrpIssuePolSchema = new LCGrpIssuePolSchema();
				
				//tLCGrpIssuePolSchema.setIssueCont(tContent);
				tLCGrpIssuePolSchema.setOperatePos(tFlag);
				tLCGrpIssuePolSchema.setIssueType(tBackObj);
				tLCGrpIssuePolSchema.setReplyResult(treply);
				tLCGrpIssuePolSchema.setSerialNo(tSerialNo);
					
				tLCGrpIssuePolSet.add(tLCGrpIssuePolSchema);			    
				
			}
			
			loggerDebug("GrpQuestReplyChk","flag:"+flag);
  			if (flag == true)
  			{
				// 准备传输数据 VData
				VData tVData = new VData();
				loggerDebug("GrpQuestReplyChk","GrpContNo="+tLCGrpContSet.get(1).getGrpContNo());
				tVData.add( tLCGrpContSet );
				tVData.add( tLCGrpIssuePolSet);
				tVData.add( tG );
				
				// 数据传输
				 String busiName="cbcheckgrpGrpQuestReplyChkUI";
		   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				//GrpQuestReplyChkUI tGrpQuestReplyChkUI   = new GrpQuestReplyChkUI();
				if (tBusinessDelegate.submitData(tVData,"UPDATE",busiName) == false)
				{
					int n = tBusinessDelegate.getCErrors().getErrorCount();
					for (int i = 0; i < n; i++)
					Content = " 问题件回复失败，原因是: " +tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//如果在Catch中发现异常，则不从错误类中提取错误信息
				if (FlagStr == "Fail")
				{
				    tError = tBusinessDelegate.getCErrors();
				    if (!tError.needDealError())
				    {                          
				    	Content = " 问题件回复人工核保成功! ";
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	Content = " 问题件回复人工核保失败，原因是:" + tError.getFirstError();
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
