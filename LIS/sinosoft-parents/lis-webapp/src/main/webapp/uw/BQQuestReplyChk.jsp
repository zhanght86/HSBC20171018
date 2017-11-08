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
  
  //校验处理
  //内容待填充
  
  	//接收信息
  	// 投保单列表
	LPIssuePolSet tLPIssuePolSet = new LPIssuePolSet();

	String tEdorNo = request.getParameter("EdorNo");
	String tContNo = request.getParameter("ContNo");
	String tFlag = request.getParameter("Flag");
	String tBackObj = request.getParameter("Quest");
	String treply = request.getParameter("ReplyResult");
	String tSerialNo = request.getParameter("SerialNo");
	String QuesFlag = request.getParameter("QuesFlag");   //机构问题件回复完毕标志
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tContent = "";
	loggerDebug("BQQuestReplyChk","tMissionID="+tMissionID);
	loggerDebug("BQQuestReplyChk","tSubMissionID="+tSubMissionID);
	 String ErrorMessage = "";
	String tcont[] = request.getParameterValues("QuestGrid3");
	String tChk[] = request.getParameterValues("InpQuestGridChk");
	
	loggerDebug("BQQuestReplyChk","edorno:"+tEdorNo);
	loggerDebug("BQQuestReplyChk","contno:"+tContNo);
	loggerDebug("BQQuestReplyChk","flag:"+tFlag);
	loggerDebug("BQQuestReplyChk","reply:"+treply);
	loggerDebug("BQQuestReplyChk","issuetype:"+tBackObj);
	loggerDebug("BQQuestReplyChk","tSerialNo:"+tSerialNo);
	boolean flag = true;
	if(QuesFlag.equals("allover"))           //如果执行“回复完毕”操作，则执行工作流节点
	{
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("EdorNo",tEdorNo);
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("MissionID",tMissionID);
		tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
		tTransferData.setNameAndValue("ActivityID",tActivityID);
		tTransferData.setNameAndValue("BusiType", "1002");
		VData tVData = new VData();
	//问题件回复完毕之后聚合到人工核保结论节点	
		  EdorManuDataDeal tEdorManuDataDeal=new EdorManuDataDeal();
		  if(!tEdorManuDataDeal.submitData(tTransferData))
		  {
			  Content = "保存失败，原因是:" + tEdorManuDataDeal.getErrors().getFirstError();
		      FlagStr = "Fail";
		  }
		  tTransferData=tEdorManuDataDeal.getTransferData();
		
		tVData.add(tTransferData);
		tVData.add( tG );
  	//String busiName="tWorkFlowUI";
  	String busiName="WorkFlowUI";
  	  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
           ErrorMessage=tBusinessDelegate.getCErrors().getError(0).moduleName;
				   Content = "保存失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = "保存失败";
				   FlagStr = "Fail";				
				}		
	 
	 }
	else
	 {
				  Content ="保存成功！";
		    	FlagStr = "Succ";	
	 }	 

	 
	 
	 
	 }
	else
	{
			if (tSerialNo.equals("")||tEdorNo.equals("")||tContNo.equals("") || tFlag.equals("") || treply.equals("") || tBackObj.equals(""))
			{
				Content = "数据录入不完全或传输失败!";
				FlagStr = "Fail";
				flag = false;
				loggerDebug("BQQuestReplyChk","失败");
			}
			else
			{
				loggerDebug("BQQuestReplyChk","begin");	
  		
					
				LPIssuePolSchema tLPIssuePolSchema = new LPIssuePolSchema();
				
				//tLCIssuePolSchema.setIssueCont(tContent);
				tLPIssuePolSchema.setEdorNo(tEdorNo);				
				tLPIssuePolSchema.setOperatePos(tFlag);
				tLPIssuePolSchema.setIssueType(tBackObj);
				tLPIssuePolSchema.setReplyResult(treply);
				tLPIssuePolSchema.setSerialNo(tSerialNo);
				tLPIssuePolSet.add(tLPIssuePolSchema);			    
				
			}
			
			loggerDebug("BQQuestReplyChk","flag:"+flag);
  			if (flag == true)
  			{
				// 准备传输数据 VData
				VData tVData = new VData();
				loggerDebug("BQQuestReplyChk","ContNo="+tContNo);
				tVData.add( tLPIssuePolSet);
				tVData.add( tG );
				
				// 数据传输
				//BQQuestReplyChkUI tBQQuestReplyChkUI   = new BQQuestReplyChkUI();
				String busiName="BQQuestReplyChkUI";
  	 			 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				if (tBusinessDelegate.submitData(tVData,"INSERT",busiName) == false)
				{
					int n = tBusinessDelegate.getCErrors().getErrorCount();
					for (int i = 0; i < n; i++)
					Content = " 机构问题件回复失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
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
	}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
