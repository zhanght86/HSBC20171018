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
  <%@page import="com.sinosoft.lis.tb.*"%>
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
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  //tongmeng 2009-03-26 add
  //增加计入误发按钮
  String tErrorFlagAction = request.getParameter("ErrorFlagAction");
  //校验处理
  //内容待填充
  loggerDebug("QuestReplyChk","tErrorFlagAction:"+tErrorFlagAction);
  	//接收信息
  	// 投保单列表
	LCContSet tLCContSet = new LCContSet();
	LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

	String tContNo = request.getParameter("ContNo");
	String tFlag = request.getParameter("HideOperatePos");
	String tBackObj = request.getParameter("Quest");
	String treply = request.getParameter("ReplyResult");
	String tSerialNo = request.getParameter("SerialNo");
	String QuesFlag = request.getParameter("QuesFlag");   //机构问题件回复完毕标志
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tErrFlag = request.getParameter("ErrorFlag");
	String tErrOperator =tG.Operator;
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
	
	if(tErrorFlagAction==null||tErrorFlagAction.equals("")
			||tErrorFlagAction.equals("null")||!tErrorFlagAction.equals("1"))
	{
	if(QuesFlag.equals("allover"))           //如果执行“回复完毕”操作，则执行工作流节点
	{
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("MissionID",tMissionID);
		tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
		
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add( tG );
		
		//TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
		
		String busiName="tbTbWorkFlowUI";
		   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
				if (tBusinessDelegate.submitData(tVData,"0000001020",busiName) == false)
				{
					int n = tBusinessDelegate.getCErrors().getErrorCount();
					for (int i = 0; i < n; i++)
					Content = " 问题件回复失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//如果在Catch中发现异常，则不从错误类中提取错误信息
				if (FlagStr == "Fail")
				{
				    tError = tBusinessDelegate.getCErrors();
				    if (!tError.needDealError())
				    {                          
				    	Content = " 问题件回复成功! ";
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	Content = " 问题件回复失败，原因是:" + tError.getFirstError();
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
				if("Y".equals(tErrFlag)){
				tLCIssuePolSchema.setErrOperator(tErrOperator);
				tLCIssuePolSchema.setErrMakeDate(PubFun.getCurrentDate());
				tLCIssuePolSchema.setErrMakeTime(PubFun.getCurrentTime());
				}
				
				tLCIssuePolSchema.setErrFlag(tErrFlag);
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
				//QuestReplyChkUI tQuestReplyChkUI   = new QuestReplyChkUI();
				
				String busiName="cbcheckQuestReplyChkUI";
				   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				
				if (tBusinessDelegate.submitData(tVData,"INSERT",busiName) == false)
				{
					int n = tBusinessDelegate.getCErrors().getErrorCount();
					for (int i = 0; i < n; i++)
					Content = " 问题件回复，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//如果在Catch中发现异常，则不从错误类中提取错误信息
				if (FlagStr == "Fail")
				{
				    tError = tBusinessDelegate.getCErrors();
				    if (!tError.needDealError())
				    {                          
				    	Content = " 问题件回复成功! ";
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
	}
	else
	{
		loggerDebug("QuestReplyChk","修改计入误发标记");
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
			if("Y".equals(tErrFlag)){
				tLCIssuePolSchema.setErrOperator(tErrOperator);
				tLCIssuePolSchema.setErrMakeDate(PubFun.getCurrentDate());
				tLCIssuePolSchema.setErrMakeTime(PubFun.getCurrentTime());
				}
			
			tLCIssuePolSchema.setErrFlag(tErrFlag);
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
			
			String busiName="cbcheckQuestErrFlagChkUI";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			
			//QuestErrFlagChkUI tQuestErrFlagChkUI   = new QuestErrFlagChkUI();
			if (tBusinessDelegate.submitData(tVData,"INSERT",busiName) == false)
			{
				int n = tBusinessDelegate.getCErrors().getErrorCount();
				for (int i = 0; i < n; i++)
				Content = " 问题件回复，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			//如果在Catch中发现异常，则不从错误类中提取错误信息
			if (FlagStr == "Fail")
			{
			    tError = tBusinessDelegate.getCErrors();
			    if (!tError.needDealError())
			    {                          
			    	Content = " 问题件回复成功! ";
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
