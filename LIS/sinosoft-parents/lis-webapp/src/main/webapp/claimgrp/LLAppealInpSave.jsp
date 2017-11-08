<%
//**************************************************************************************************
//Name：LLAppealSave.jsp
//Function：申请申诉类信息保存提交
//Author：zhoulei
//Date: 2005-7-26 16:33
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>

<%

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
    loggerDebug("LLAppealInpSave","页面失效,请重新登陆");
}
else
{
    //准备提交的数据
    LLAppealSchema mLLAppealSchema = new LLAppealSchema();
    
    mLLAppealSchema.setAppealNo(request.getParameter("ClmNo"));
    mLLAppealSchema.setAppealType(request.getParameter("AppealType"));
    mLLAppealSchema.setAppealState(request.getParameter("AppealState"));
    mLLAppealSchema.setWaitDate(request.getParameter("WaitDate"));
    mLLAppealSchema.setAppealName(request.getParameter("AppealName"));
    mLLAppealSchema.setAppealSex(request.getParameter("AppealSex"));
    mLLAppealSchema.setAddress(request.getParameter("Address"));
    mLLAppealSchema.setPhone(request.getParameter("Phone"));
    mLLAppealSchema.setMobile(request.getParameter("Mobile"));
    mLLAppealSchema.setAppealMode(request.getParameter("AppealMode"));
    mLLAppealSchema.setPostCode(request.getParameter("PostCode"));
    mLLAppealSchema.setRelation(request.getParameter("Relation2"));
    mLLAppealSchema.setReturnMode(request.getParameter("ReturnMode"));
    mLLAppealSchema.setIDNo(request.getParameter("IDNo"));
    mLLAppealSchema.setIDType(request.getParameter("IDType"));
    mLLAppealSchema.setAppealRDesc(request.getParameter("AppealRDesc"));

    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mLLAppealSchema);

    try
    {
        //提交数据
        LLAppealUpdateUI tLLAppealUpdateUI = new LLAppealUpdateUI();
        if(!tLLAppealUpdateUI.submitData(tVData,""))
        {
            Content = " 数据提交失败，原因是: " + tLLAppealUpdateUI.mErrors.getError(0).errorMessage;
            FlagStr = "Fail";
        }
        else
        {
            Content = " 数据提交成功！";
            FlagStr = "Succ";
        }
    }
    catch(Exception ex)
    {
        Content = "数据提交失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit4("<%=FlagStr%>","<%=Content%>");
</script>
</html>
