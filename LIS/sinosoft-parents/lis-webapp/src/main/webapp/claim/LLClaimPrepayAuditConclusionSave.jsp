<%
//**************************************************************************************************
//Name：LLClaimPrepayAuditConclusionSave.jsp
//Function：预付审核结论提交
//Author：zhangzheng 
//Date: 2008-12-29
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%

//输入参数
LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema(); //案件核赔表

//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");

//页面有效性判断
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLClaimPrepayAuditConclusionSave","页面失效,请重新登陆");
}
else
{
    //获取操作符
    String transact = request.getParameter("fmtransact");

    //获取报案页面信息
    tLLClaimUWMainSchema.setClmNo(request.getParameter("RptNo")); //赔案号
    tLLClaimUWMainSchema.setCaseNo(tLLClaimUWMainSchema.getClmNo()); //分案号
    tLLClaimUWMainSchema.setRgtNo(tLLClaimUWMainSchema.getClmNo()); //报案号
    tLLClaimUWMainSchema.setAuditConclusion(request.getParameter("AuditConclusion")); //审核结论
    tLLClaimUWMainSchema.setAuditIdea(request.getParameter("AuditIdea")); //审核意见
    tLLClaimUWMainSchema.setAuditLevel(request.getParameter("AuditPopedom")); //案件审核级别
    tLLClaimUWMainSchema.setCheckType("1"); //审核类型: 0--审核结论,1--预付审核结论

    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(tLLClaimUWMainSchema);

    try
    {
        //提交数据
        loggerDebug("LLClaimPrepayAuditConclusionSave","-------------------start LLClaimAuditConclusionSave.jsp---------------------");
//        LLClaimPrepayAuditBL tLLClaimPrepayAuditBL = new LLClaimPrepayAuditBL();
//        if(!tLLClaimPrepayAuditBL.submitData(tVData,transact))
//        {
//            Content = " 数据提交LLClaimPrepayAuditUI失败，原因是: " + tLLClaimPrepayAuditBL.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
		String busiName="LLClaimPrepayAuditBL";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

		if(!tBusinessDelegate.submitData(tVData,transact,busiName))
		{    
		    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		    { 
				Content = "数据提交LLClaimPrepayAuditUI失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "数据提交LLClaimPrepayAuditUI失败";
				FlagStr = "Fail";				
			}
		}

        else
        {
            Content = " 数据提交成功！";
            FlagStr = "Succ";
        }
        loggerDebug("LLClaimPrepayAuditConclusionSave","-------------------end LLClaimAuditConclusionSave.jsp---------------------");
    }
    catch(Exception ex)
    {
        Content = "数据提交LLClaimAuditUI失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit3("<%=FlagStr%>","<%=Content%>");
</script>
</html>
