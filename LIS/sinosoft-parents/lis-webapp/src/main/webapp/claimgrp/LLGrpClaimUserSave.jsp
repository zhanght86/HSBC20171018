<%
//程序名称：LLGrpClaimUserSave.jsp
//程序功能：理赔用户管理保存
//创建日期：2009/04/17  
//创建人  ：zhangzheng
//更新记录：  更新人 yuejw    更新日期     更新原因/内容

%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>

<%
    //准备通用参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI"); 
LLGrpClaimUserUI tLLGrpClaimUserUI = new LLGrpClaimUserUI(); 

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLGrpClaimUserSave","页面失效,请重新登陆");    
}
else //页面有效
{
    String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码
    String transact = request.getParameter("fmtransact"); //获取操作insert||update
    loggerDebug("LLGrpClaimUserSave","-----transact= "+transact);	   
    
    //##########获取页面信息########### 
    LLGrpClaimUserSchema tLLGrpClaimUserSchema = new LLGrpClaimUserSchema();
    
	//准备后台数据
	tLLGrpClaimUserSchema.setUserCode(request.getParameter("UserCode")); //用户编码
	tLLGrpClaimUserSchema.setUserName(request.getParameter("UserName")); //用户姓名
	
	tLLGrpClaimUserSchema.setComCode(request.getParameter("ComCode")); //机构编码
	tLLGrpClaimUserSchema.setJobCategory(request.getParameter("JobCategory")); //权限级别
	tLLGrpClaimUserSchema.setUpUserCode(request.getParameter("UpUserCode")); //上级用户编码

	tLLGrpClaimUserSchema.setReportFlag(request.getParameter("ReportFlag")); //报案权限
	tLLGrpClaimUserSchema.setRegisterFlag(request.getParameter("RegisterFlag")); //立案权限
	tLLGrpClaimUserSchema.setSubmitFlag(request.getParameter("SubmitFlag")); //呈报权限
	
	tLLGrpClaimUserSchema.setSurveyFlag(request.getParameter("SurveyFlag")); //调查权限
	tLLGrpClaimUserSchema.setPrepayFlag(request.getParameter("PrepayFlag")); //预付权限
	tLLGrpClaimUserSchema.setSimpleFlag(request.getParameter("SimpleFlag")); //简易案件权限
	
	tLLGrpClaimUserSchema.setCertifyScan(request.getParameter("CertifyScan")); //单证扫描权限
	tLLGrpClaimUserSchema.setTaskAssign(request.getParameter("TaskAssign")); //任务分配权限
	tLLGrpClaimUserSchema.setExemptFlag(request.getParameter("ExemptFlag")); //豁免权限

	tLLGrpClaimUserSchema.setCheckFlag(request.getParameter("CheckFlag")); //审核权限
	tLLGrpClaimUserSchema.setCheckLevel(request.getParameter("CheckLevel")); //审核级别
	tLLGrpClaimUserSchema.setUWFlag(request.getParameter("UWFlag")); //审批权限
	tLLGrpClaimUserSchema.setUWLevel(request.getParameter("UWLevel")); //审批级别
	
	tLLGrpClaimUserSchema.setRgtFlag(request.getParameter("RgtFlag")); //在岗状态
	tLLGrpClaimUserSchema.setRelPhone(request.getParameter("RelPhone")); //工作电话
	
	
    try
    {
        //准备传输数据 VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(transact);
        tVData.add(tLLGrpClaimUserSchema);    	
		if(!tLLGrpClaimUserUI.submitData(tVData,transact))
		{           
			Content = "提交失败，原因是: " + tLLGrpClaimUserUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";    
		}
		else
		{	    
			Content = "数据提交成功";
			FlagStr = "Succ";            
		}
    }
    catch(Exception ex)
    {
        Content = "数据提交失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }
    loggerDebug("LLGrpClaimUserSave","------LLGrpClaimUserSave.jsp end------");
}
  
%>                     
<html>
<script language="javascript">
parent.fraInterface.afterUserSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
