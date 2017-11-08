<%@page contentType="text/html;charset=GBK"%> 
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：QuestQueryUpdatePrintFlag.jsp
//程序功能：问题件打印标记更新
//创建日期：2002-09-24 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%> 
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
   <%@page import="com.sinosoft.service.*" %>
<%
//1-得到所有纪录，显示纪录值
  int index=0;
  int TempCount=0;
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "请选择保单";
  boolean flag = false;
  
  LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
  
  //得到radio列的数组
  loggerDebug("QuestQueryUpdatePrintFlag","---4----");
  
  GlobalInput tG = new GlobalInput();
  
  tG=(GlobalInput)session.getValue("GI");
  
  if(tG == null) {
	out.println("session has expired");
	return;
  }

  
  String tOperatePos = request.getParameter("OperatePos");
  
  String tRadio[] = request.getParameterValues("InpQuestGridSel");  
  String tTempClassNum[] = request.getParameterValues("QuestGridNo");
  String tContNo[] = request.getParameterValues("QuestGrid1");
  String tQuest[] = request.getParameterValues("QuestGrid2");
  String tReply[] = request.getParameterValues("QuestGrid3");
  String tOperate[] = request.getParameterValues("QuestGrid7");
  String tIfPrint[] = request.getParameterValues("QuestGrid9");
  String tSerialNo[] = request.getParameterValues("QuestGrid10");
  int temp = tTempClassNum.length;
  loggerDebug("QuestQueryUpdatePrintFlag","radiolength:"+temp);
  
  //保单表 
  
	if(tTempClassNum!=null) //如果不是空纪录	
	{
		TempCount = tTempClassNum.length; //记录数      
	 	loggerDebug("QuestQueryUpdatePrintFlag","Start query Count="+TempCount);   
		while(index<TempCount)
		{
			loggerDebug("QuestQueryUpdatePrintFlag","----3-----");
			loggerDebug("QuestQueryUpdatePrintFlag","polcode:"+tContNo[index]);  		
			if (!tContNo[index].equals("")&&!tQuest[index].equals("")&&!tIfPrint[index].equals("")&&!tOperate[index].equals(""))
			{	    					
				LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();
				
				tLCIssuePolSchema.setContNo(tContNo[index]);				
				tLCIssuePolSchema.setIssueType(tQuest[index]);
				tLCIssuePolSchema.setOperatePos(tOperate[index]);
				tLCIssuePolSchema.setNeedPrint(tIfPrint[index]);
				tLCIssuePolSchema.setSerialNo(tSerialNo[index]);
				loggerDebug("QuestQueryUpdatePrintFlag","index:"+index);
				loggerDebug("QuestQueryUpdatePrintFlag","Quest:"+tQuest[index]);
				loggerDebug("QuestQueryUpdatePrintFlag","printflag:"+tIfPrint[index]);
				
					
				tLCIssuePolSet.add(tLCIssuePolSchema);			    
				  			  			
				flag = true;    						
	    		}
	    		index = index + 1;
	    	}
	}
      		
	if (flag == true)
	{
		// 准备传输数据 VData
		VData tVData = new VData();		
		tVData.add( tLCIssuePolSet);
		tVData.add( tG );
		
		// 数据传输
		String busiName="cbcheckgrpQuestPrintFlagUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		//QuestPrintFlagUI tQuestPrintFlagUI   = new QuestPrintFlagUI();
		if (tBusinessDelegate.submitData(tVData,"INSERT",busiName) == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " 问题件打印标记修改失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
		    	Content = " 问题件打印标记修改成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " 问题件打印标记修改失败，原因是:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
		}
	} 

    		

   
%>  
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
