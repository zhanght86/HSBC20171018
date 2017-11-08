<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWscoreRReportChk.jsp
//程序功能：核保生调评分
//创建人  ：ln
//创建日期：2008-10-25
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.xb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
<%
  //输出参数
  CErrors tError = null;
  //CErrors tErrors = new CErrors();
  String FlagStr = "Fail";
  String Content = "";
  GlobalInput tG = new GlobalInput(); 
	tG=(GlobalInput)session.getValue("GI");  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}

  	//接收信息 
	TransferData tTransferData = new TransferData();
	RnewScoreRReportSchema tRnewScoreRReportSchema = new RnewScoreRReportSchema();
	RnewScoreRReportSubSchema tRnewScoreRReportSubSchema ;
	RnewScoreRReportSubSet tRnewScoreRReportSubSet = new RnewScoreRReportSubSet();
	
	String tContNo = request.getParameter("ContNoH"); //印刷号
	String tManageCom = request.getParameter("ManageCom");
	String tCustomerNo = request.getParameter("CustomerNo");
	String tName = request.getParameter("Name");
	String tSScore = request.getParameter("SScore");
	String tAScore = request.getParameter("AScore");
	String tScore = request.getParameter("Score");
	String tConclusion = request.getParameter("Conclusion");
	String tAssessOperator = request.getParameter("AssessOperator");
	String tAssessDay = request.getParameter("AssessDay");
	String tAssessTime = request.getParameter("AssessTimeH");
	
	boolean flag = true;	
	

	if (!tContNo.equals(""))		{		
		
		//准备主表信息
		tRnewScoreRReportSchema.setContNo(tContNo);
		tRnewScoreRReportSchema.setManageCom(tManageCom);
		tRnewScoreRReportSchema.setCustomerNo(tCustomerNo);
		tRnewScoreRReportSchema.setName(tName);
		tRnewScoreRReportSchema.setSScore(tSScore);
		tRnewScoreRReportSchema.setAScore(tAScore);
		tRnewScoreRReportSchema.setScore(tScore);
		tRnewScoreRReportSchema.setConclusion(tConclusion);
		tRnewScoreRReportSchema.setAssessOperator(tAssessOperator);
		tRnewScoreRReportSchema.setAssessDay(tAssessDay);
		tRnewScoreRReportSchema.setAssessTime(tAssessTime);		
		
	}
	else
	{
		flag = false;
		Content = "数据不完整!";
	}	
	loggerDebug("RnewUWscoreRReportChk","flag:"+flag);		
	
	//准备子表信息		
	String SSubtraction = "";
	String SScore = "";
	String Addition = "";
	String AScore = "";
	String tValue = "";
	String tScoreValue = "";
	
	for(int i=1; i<9; i++)
    {
		tRnewScoreRReportSubSchema = new RnewScoreRReportSubSchema();
		tRnewScoreRReportSubSchema.setContNo(tContNo);
		tRnewScoreRReportSubSchema.setManageCom(tManageCom);
		tRnewScoreRReportSubSchema.setCustomerNo(tCustomerNo);
		tRnewScoreRReportSubSchema.setName(tName);
		
    	SSubtraction = "Subtraction" + String.valueOf(i);
    	SScore = "SScore" + String.valueOf(i);
    	tValue = request.getParameter(SSubtraction);
    	loggerDebug("RnewUWscoreRReportChk",SSubtraction);
    	loggerDebug("RnewUWscoreRReportChk",SScore);
    	loggerDebug("RnewUWscoreRReportChk",tValue);
    	if( tValue!=null && tValue.equals("1") )
    		tScoreValue = request.getParameter(SScore);
    	else 
    		tScoreValue ="0";
    	loggerDebug("RnewUWscoreRReportChk",tScoreValue);
    	tRnewScoreRReportSubSchema.setAssessItem(SScore);
    	tRnewScoreRReportSubSchema.setScore(tScoreValue);
    	tRnewScoreRReportSubSet.add(tRnewScoreRReportSubSchema);
    }
	for(int i=1; i<7; i++)
    {
		tRnewScoreRReportSubSchema = new RnewScoreRReportSubSchema();
		tRnewScoreRReportSubSchema.setContNo(tContNo);
		tRnewScoreRReportSubSchema.setManageCom(tManageCom);
		tRnewScoreRReportSubSchema.setCustomerNo(tCustomerNo);
		tRnewScoreRReportSubSchema.setName(tName);
		
    	Addition = "Addition" + String.valueOf(i);
    	AScore = "AScore" + String.valueOf(i);   
    	tValue = request.getParameter(Addition);
    	if( tValue!=null && tValue.equals("1") )
    		tScoreValue = request.getParameter(AScore);
    	else 
    		tScoreValue ="0";
    	tRnewScoreRReportSubSchema.setAssessItem(AScore);
    	tRnewScoreRReportSubSchema.setScore(tScoreValue);
    	tRnewScoreRReportSubSet.add(tRnewScoreRReportSubSchema);
    }		
	
		try
		{
		  	if (flag == true)
		  	{
				// 准备传输数据 VData
				VData tVData = new VData();
				tVData.add( tRnewScoreRReportSchema);
				tVData.add( tRnewScoreRReportSubSet);
				tVData.add( tG );
				
				// 数据传输
				RnewUWScoreRReportUI tUWScoreRReportUI   = new RnewUWScoreRReportUI();
				if (!tUWScoreRReportUI.submitData(tVData,"")) //执行生调人员评审处理
				{
					int n = tUWScoreRReportUI.mErrors.getErrorCount();
					loggerDebug("RnewUWscoreRReportChk","ErrorCount:"+n ) ;
					Content = " 续保二核生调人员评审处理失败，原因是: " + tUWScoreRReportUI.mErrors.getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//如果在Catch中发现异常，则不从错误类中提取错误信息
				if (FlagStr == "Fail")
				{
				    tError = tUWScoreRReportUI.mErrors;
				    if (!tError.needDealError())
				    {                          
				    	Content = " 续保二核生调人员评审处理成功! ";
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	Content = " 续保二核生调人员评审处理失败，原因是:" + tError.getFirstError();
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
