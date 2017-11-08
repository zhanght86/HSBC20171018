<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UnderwriteApply.jsp
//程序功能：人工核保投保单申请校验
//创建日期：2003-04-09 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.xbcheck.*"%>
  <%@page import="com.sinosoft.workflow.xb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
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
 	LCPolSet tLCPolSet = new LCPolSet();
	String tProposalNo = request.getParameter("PolNoHide");
	
	loggerDebug("ManuUWApply","ProposalNo:"+tProposalNo);
	
	boolean flag = false;
	
	if (!tProposalNo.equals(""))
	{	
 	    LCPolSchema tLCPolSchema = new LCPolSchema();
 	    tLCPolSchema.setPolNo( tProposalNo);
	    tLCPolSchema.setProposalNo( tProposalNo);	    
	    tLCPolSet.add( tLCPolSchema );	    
	    
	    flag = true;
	}
	else
	{
	    FlagStr = "Fail";
	    Content = "申请失败，号码传输失败!";
	}
	
try
{
  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tLCPolSet );		
		tVData.add( tG );
		
		// 数据传输
		PRnewUWManuApplyChkUI tPRnewUWManuApplyChkUI   = new PRnewUWManuApplyChkUI();
		if (tPRnewUWManuApplyChkUI.submitData(tVData,"INSERT") == false)
		{
			Content = " 续保人工核保申请失败，原因是: " + tPRnewUWManuApplyChkUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    
		    tError = tPRnewUWManuApplyChkUI.mErrors;
		    if (!tError.needDealError())
		    {                     
		    	Content = " 续保人工核保申请申请成功!";
		    	FlagStr = "Succ";
		    }
		    else                                                              
		    {
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
	parent.fraInterface.afterApply("<%=FlagStr%>","<%=Content%>");	
</script>
</html>
