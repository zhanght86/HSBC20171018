<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//文件名称：LLPrtagainSave.jsp
//文件功能：单证补打原因录入页面
//建立人：yuejw
//建立日期: 2005-08-21
//页面描述: 用于在补打单证时录入补打原因
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.claimgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>

<%
    //输出参数
	CErrors tError = null;
    String FlagStr = "Fail";
    String Content = "";
	GlobalInput tG = new GlobalInput(); 
	tG=(GlobalInput)session.getValue("GI");
  	if(tG == null) 
  	{
		loggerDebug("LLPrtagainSave","session has expired");
		return;
	}
    LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
    tLOPRTManagerSchema.setPrtSeq(request.getParameter("PrtSeq"));
    tLOPRTManagerSchema.setRemark(request.getParameter("Remark"));
    
    // 准备传输数据 VData
    VData tVData = new VData();	
    tVData.addElement(tLOPRTManagerSchema);
    tVData.addElement(tG);
    // 数据传输
//    LLPrtagainReasonUI tLLPrtagainReasonUI   = new LLPrtagainReasonUI();
//	if (!tLLPrtagainReasonUI.submitData(tVData,""))
//	{
//     	Content = "补打原因保存失败，原因是: " + tLLPrtagainReasonUI.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//	} 
String busiName="grpLLPrtagainReasonUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,"",busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "补打原因保存失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "补打原因保存失败";
		FlagStr = "Fail";				
	}
}

    else
	{ 
        Content = "数据提交成功";
        FlagStr = "Succ";            
	}
%>                      
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
