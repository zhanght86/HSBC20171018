<%
//程序名称：LLClaimUserSave.jsp
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
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>

<%
    //准备通用参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI"); 
LLClaimUserUI tLLClaimUserUI = new LLClaimUserUI(); 

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLClaimUserSave","页面失效,请重新登陆");    
}
else //页面有效
{
    String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码
    String transact = request.getParameter("fmtransact"); //获取操作insert||update
    loggerDebug("LLClaimUserSave","-----transact= "+transact);	   
    
    //##########获取页面信息########### 
    LLClaimUserSchema tLLClaimUserSchema = new LLClaimUserSchema();
    
	//准备后台数据
	tLLClaimUserSchema.setUserCode(request.getParameter("UserCode")); //用户编码
	tLLClaimUserSchema.setUserName(request.getParameter("UserName")); //用户姓名
	
	tLLClaimUserSchema.setComCode(request.getParameter("ComCode")); //机构编码
	tLLClaimUserSchema.setJobCategory(request.getParameter("JobCategory")); //权限级别
	tLLClaimUserSchema.setUpUserCode(request.getParameter("UpUserCode")); //上级用户编码

	tLLClaimUserSchema.setReportFlag(request.getParameter("ReportFlag")); //报案权限
	tLLClaimUserSchema.setRegisterFlag(request.getParameter("RegisterFlag")); //立案权限
	tLLClaimUserSchema.setSubmitFlag(request.getParameter("SubmitFlag")); //呈报权限
	
	tLLClaimUserSchema.setSurveyFlag(request.getParameter("SurveyFlag")); //调查权限
	tLLClaimUserSchema.setPrepayFlag(request.getParameter("PrepayFlag")); //预付权限
	tLLClaimUserSchema.setSimpleFlag(request.getParameter("SimpleFlag")); //简易案件权限
	
	tLLClaimUserSchema.setCertifyScan(request.getParameter("CertifyScan")); //单证扫描权限
	tLLClaimUserSchema.setTaskAssign(request.getParameter("TaskAssign")); //任务分配权限
	tLLClaimUserSchema.setExemptFlag(request.getParameter("ExemptFlag")); //豁免权限

	tLLClaimUserSchema.setCheckFlag(request.getParameter("CheckFlag")); //审核权限
	tLLClaimUserSchema.setCheckLevel(request.getParameter("CheckLevel")); //审核级别
	tLLClaimUserSchema.setUWFlag(request.getParameter("UWFlag")); //审批权限
	tLLClaimUserSchema.setUWLevel(request.getParameter("UWLevel")); //审批级别
	
	tLLClaimUserSchema.setRgtFlag(request.getParameter("RgtFlag")); //在岗状态
	tLLClaimUserSchema.setRelPhone(request.getParameter("RelPhone")); //工作电话
	
	
    try
    {
        //准备传输数据 VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(transact);
        tVData.add(tLLClaimUserSchema);    	
		if(!tLLClaimUserUI.submitData(tVData,transact))
		{           
			Content = "提交失败，原因是: " + tLLClaimUserUI.mErrors.getError(0).errorMessage;
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
    loggerDebug("LLClaimUserSave","------LLClaimUserSave.jsp end------");
}
  
%>                     
<html>
<script language="javascript">
parent.fraInterface.afterUserSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
