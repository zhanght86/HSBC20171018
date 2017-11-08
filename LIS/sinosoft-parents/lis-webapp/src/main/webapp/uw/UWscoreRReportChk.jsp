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
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  <%@page import="com.sinosoft.service.*" %>
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
	LCScoreRReportSchema tLCScoreRReportSchema = new LCScoreRReportSchema();
	LCScoreRReportSubSchema tLCScoreRReportSubSchema ;
	LCScoreRReportSubSet tLCScoreRReportSubSet = new LCScoreRReportSubSet();
	
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
		tLCScoreRReportSchema.setContNo(tContNo);
		tLCScoreRReportSchema.setManageCom(tManageCom);
		tLCScoreRReportSchema.setCustomerNo(tCustomerNo);
		tLCScoreRReportSchema.setName(tName);
		tLCScoreRReportSchema.setSScore(tSScore);
		tLCScoreRReportSchema.setAScore(tAScore);
		tLCScoreRReportSchema.setScore(tScore);
		tLCScoreRReportSchema.setConclusion(tConclusion);
		tLCScoreRReportSchema.setAssessOperator(tAssessOperator);
		tLCScoreRReportSchema.setAssessDay(tAssessDay);
		tLCScoreRReportSchema.setAssessTime(tAssessTime);		
		
	}
	else
	{
		flag = false;
		Content = "数据不完整!";
	}	
	loggerDebug("UWscoreRReportChk","flag:"+flag);		
	
	//准备子表信息		
	String SSubtraction = "";
	String SScore = "";
	String Addition = "";
	String AScore = "";
	String tValue = "";
	String tScoreValue = "";
	
	for(int i=1; i<9; i++)
    {
		tLCScoreRReportSubSchema = new LCScoreRReportSubSchema();
		tLCScoreRReportSubSchema.setContNo(tContNo);
		tLCScoreRReportSubSchema.setManageCom(tManageCom);
		tLCScoreRReportSubSchema.setCustomerNo(tCustomerNo);
		tLCScoreRReportSubSchema.setName(tName);
		
    	SSubtraction = "Subtraction" + String.valueOf(i);
    	SScore = "SScore" + String.valueOf(i);
    	tValue = request.getParameter(SSubtraction);
    	loggerDebug("UWscoreRReportChk",SSubtraction);
    	loggerDebug("UWscoreRReportChk",SScore);
    	loggerDebug("UWscoreRReportChk",tValue);
    	if( tValue!=null && tValue.equals("1") )
    		tScoreValue = request.getParameter(SScore);
    	else 
    		tScoreValue ="0";
    	loggerDebug("UWscoreRReportChk",tScoreValue);
    	tLCScoreRReportSubSchema.setAssessItem(SScore);
    	tLCScoreRReportSubSchema.setScore(tScoreValue);
    	tLCScoreRReportSubSet.add(tLCScoreRReportSubSchema);
    }
	for(int i=1; i<7; i++)
    {
		tLCScoreRReportSubSchema = new LCScoreRReportSubSchema();
		tLCScoreRReportSubSchema.setContNo(tContNo);
		tLCScoreRReportSubSchema.setManageCom(tManageCom);
		tLCScoreRReportSubSchema.setCustomerNo(tCustomerNo);
		tLCScoreRReportSubSchema.setName(tName);
		
    	Addition = "Addition" + String.valueOf(i);
    	AScore = "AScore" + String.valueOf(i);   
    	tValue = request.getParameter(Addition);
    	if( tValue!=null && tValue.equals("1") )
    		tScoreValue = request.getParameter(AScore);
    	else 
    		tScoreValue ="0";
    	tLCScoreRReportSubSchema.setAssessItem(AScore);
    	tLCScoreRReportSubSchema.setScore(tScoreValue);
    	tLCScoreRReportSubSet.add(tLCScoreRReportSubSchema);
    }		
	
		try
		{
		  	if (flag == true)
		  	{
				// 准备传输数据 VData
				VData tVData = new VData();
				tVData.add( tLCScoreRReportSchema);
				tVData.add( tLCScoreRReportSubSet);
				tVData.add( tG );
				
				// 数据传输
//				UWScoreRReportUI tUWScoreRReportUI   = new UWScoreRReportUI();
//				if (!tUWScoreRReportUI.submitData(tVData,"")) //执行生调人员评审处理
//				{
//					int n = tUWScoreRReportUI.mErrors.getErrorCount();
//					loggerDebug("UWscoreRReportChk","ErrorCount:"+n ) ;
//					Content = " 新契约生调人员评审处理失败，原因是: " + tUWScoreRReportUI.mErrors.getError(0).errorMessage;
//					FlagStr = "Fail";
//				}
String busiName="UWScoreRReportUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,"",busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "新契约生调人员评审处理失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "新契约生调人员评审处理失败";
		FlagStr = "Fail";				
	}
}


				//如果在Catch中发现异常，则不从错误类中提取错误信息
				if (FlagStr == "Fail")
				{
				   // tError = tUWScoreRReportUI.mErrors;
				    tError = tBusinessDelegate.getCErrors();
				    if (!tError.needDealError())
				    {                          
				    	Content = " 新契约生调人员评审处理成功! ";
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	Content = " 新契约生调人员评审处理失败，原因是:" + tError.getFirstError();
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
