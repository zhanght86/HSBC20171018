<%
//程序名称：LLCommendHospitalSave.jsp
//程序功能：医院信息维护
//创建日期：2005-7-13
//创建人  ：yuejw
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
LLCommendHospitalUI tLLCommendHospitalUI = new LLCommendHospitalUI(); 

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLCommendHospitalSave","页面失效,请重新登陆");    
}
else //页面有效
{
    String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码
    String transact = request.getParameter("fmtransact"); //获取操作insert||update
    loggerDebug("LLCommendHospitalSave","-----transact= "+transact);	   
    //##########获取页面信息########### 
    LLCommendHospitalSchema tLLCommendHospitalSchema = new LLCommendHospitalSchema();
	//准备后台数据
	tLLCommendHospitalSchema.setConsultNo(request.getParameter("ConsultNo")); //咨询通知号码==医院代码	
	tLLCommendHospitalSchema.setHospitalCode(request.getParameter("HospitalCode")); //医院代码
	tLLCommendHospitalSchema.setHospitalName(request.getParameter("HospitalName")); //医院名称
	tLLCommendHospitalSchema.setHosAtti(request.getParameter("HosAtti")); //医院等级
	tLLCommendHospitalSchema.setConFlag(request.getParameter("ConFlag")); //定点标志
	tLLCommendHospitalSchema.setAppFlag(request.getParameter("AppFlag")); //残疾鉴定资质标志
	tLLCommendHospitalSchema.setHosState(request.getParameter("HosState")); //医院状态

    try
    {
        //准备传输数据 VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(transact);
        tVData.add(tLLCommendHospitalSchema);    	
		if(!tLLCommendHospitalUI.submitData(tVData,transact))
		{           
			Content = "提交失败，原因是: " + tLLCommendHospitalUI.mErrors.getError(0).errorMessage;
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
    loggerDebug("LLCommendHospitalSave","------LLCommendHospitalSave.jsp end------");
}
  
%>                     
<html>
<script language="javascript">
parent.fraInterface.afterHospitalSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
