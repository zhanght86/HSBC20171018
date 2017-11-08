<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLMedicalFeeInp3Save.jsp
//程序功能：伤残信息保存
//创建日期：2005-05-19 11:10:36
//创建人  ：续涛
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
//准备通用参数
CErrors tError = null;
String FlagStr = "FAIL";
String Content = "";
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

//页面有效性
if(tG == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLMedicalFeeInp3Save","页面失效,请重新登陆");
}
else
{
    String tOperate = request.getParameter("hideOperate");
    loggerDebug("LLMedicalFeeInp3Save","$$$$$$$$$$$$$$"+request.getParameter("SerialNo3"));
    
    //准备数据容器信息
    LLCaseInfoSchema tLLCaseInfoSchema = new LLCaseInfoSchema();

    //准备后台数据
    tLLCaseInfoSchema.setClmNo(request.getParameter("claimNo"));
    tLLCaseInfoSchema.setCaseNo(request.getParameter("caseNo"));
    tLLCaseInfoSchema.setSerialNo(request.getParameter("SerialNo3"));
    tLLCaseInfoSchema.setCustomerNo(request.getParameter("custNo"));
    
    tLLCaseInfoSchema.setDefoType(request.getParameter("DefoType"));
    tLLCaseInfoSchema.setDefoGrade(request.getParameter("DefoGrade"));
    tLLCaseInfoSchema.setDefoCode(request.getParameter("DefoCode"));
    tLLCaseInfoSchema.setDefoName(request.getParameter("DefoGradeName"));
    tLLCaseInfoSchema.setDeformityReason(request.getParameter("DeformityReason"));
    tLLCaseInfoSchema.setDeformityRate(request.getParameter("DeformityRate"));
    tLLCaseInfoSchema.setAppDeformityRate(request.getParameter("AppDeformityRate"));
    tLLCaseInfoSchema.setRealRate(request.getParameter("RealRate"));
    tLLCaseInfoSchema.setJudgeOrganName(request.getParameter("JudgeOrganName"));
    tLLCaseInfoSchema.setJudgeDate(request.getParameter("JudgeDate"));

    try
    {
        // 准备传输数据 VData
        VData tVData = new VData();
        tVData.add( tG );
        tVData.add( tLLCaseInfoSchema );

//        LLMedicalFeeInp3UI tLLMedicalFeeInp3UI = new LLMedicalFeeInp3UI();
//        if (tLLMedicalFeeInp3UI.submitData(tVData,tOperate) == false)
//        {
//            int n = tLLMedicalFeeInp3UI.mErrors.getErrorCount();
//            for (int i = 0; i < n; i++)
//            {
//                Content = Content + "信息提交保存，原因是: " + tLLMedicalFeeInp3UI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//            }
//        }
String busiName="grpLLMedicalFeeInp3UI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
        	int n = tBusinessDelegate.getCErrors().getErrorCount();
	        for (int i = 0; i < n; i++)
	        {
	            //loggerDebug("LLMedicalFeeInp3Save","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
	            Content =Content + "信息提交保存，原因是: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
	        }
       		FlagStr = "Fail";				   
		}
		else
		{
		   Content = "信息提交保存失败";
		   FlagStr = "Fail";				
		} 
}

        else
        {
            Content = " 保存成功! ";
            FlagStr = "SUCC";
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
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
