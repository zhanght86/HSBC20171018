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
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
  
<%
  //输出参数
  CErrors tError = null;
  //CErrors tErrors = null;
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
	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

	String tProposalNo = request.getParameter("ProposalNoHide");
	String tOtherNoType = request.getParameter("OtherNoType");
	String tCode = request.getParameter("Code");
	
	boolean flag = false;
	
	if (!tProposalNo.equals("")&&!tOtherNoType.equals("")&&!tCode.equals(""))
	{
 	    tLOPRTManagerSchema.setOtherNo( tProposalNo);
	    tLOPRTManagerSchema.setOtherNoType(tOtherNoType);
	    tLOPRTManagerSchema.setCode(tCode);
	    
	    flag = true;
	}
	else
	{
	    FlagStr = "Fail";
	    Content = "号码传输失败!";
	}
	
try
{
  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tLOPRTManagerSchema);
		tVData.add( tG );
		
		// 数据传输
		GrpUWSendPrintUI tGrpUWSendPrintUI   = new GrpUWSendPrintUI();
		if (tGrpUWSendPrintUI.submitData(tVData,"INSERT") == false)
		{
			int n = tGrpUWSendPrintUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " 操作失败,原因是: " + tGrpUWSendPrintUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tGrpUWSendPrintUI.mErrors;		    
		    if (!tError.needDealError())
		    {                     
		    	Content = " 操作成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                              
		    {
		    	Content = " 操作失败,原因是:";
		    	int n = tError.getErrorCount();
    			if (n > 0)
    			{
			      for(int i = 0;i < n;i++)
			      {			        
			        Content = Content.trim() +i+". "+ tError.getError(i).errorMessage.trim()+".";
			      }
			}
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
	parent.close();
	this.close();
</script>
</html>
