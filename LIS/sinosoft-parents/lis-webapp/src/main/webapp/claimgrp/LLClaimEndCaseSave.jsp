<%
//Name：LLClaimEndCaseSave.jsp
//Function：结案
//Author：zhoulei
//Date:
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
//输入参数
//LLClaimSchema tLLClaimSchema = new LLClaimSchema(); //赔案表

//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI");  
//LLEndCaseUI tLLEndCaseUI = new LLEndCaseUI();
String busiName="grpLLEndCaseUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();


//页面有效
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLClaimEndCaseSave","页面失效,请重新登陆");    
}
else
{
    String transact = request.getParameter("fmtransact"); //获取操作insert||update

    //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo", request.getParameter("ClmNo"));
    mTransferData.setNameAndValue("RptNo", request.getParameter("ClmNo"));
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    
    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);

    //数据传输
//    if (!tLLEndCaseUI.submitData(tVData,transact))
//    {
//        Content = " 数据提交LLEndCaseUI失败，原因是: " + tLLEndCaseUI.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//    }
if(!tBusinessDelegate.submitData(tVData,transact,busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "数据提交LLEndCaseUI失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "数据提交LLEndCaseUI失败";
		FlagStr = "Fail";				
	}
}

    else
    {
    	Content = " 数据提交成功! ";
	    FlagStr = "Succ";
    }
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
