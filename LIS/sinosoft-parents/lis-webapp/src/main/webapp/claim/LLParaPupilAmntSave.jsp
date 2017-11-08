<%
//程序名称：LLParaPupilAmntSave.jsp
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
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>

<%
    //准备通用参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI"); 
LLParaPupilAmntUI tLLParaPupilAmntUI = new LLParaPupilAmntUI(); 

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLParaPupilAmntSave","页面失效,请重新登陆");    
}
else //页面有效
{
    String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码
    String transact = request.getParameter("fmtransact"); //获取操作insert||update
    loggerDebug("LLParaPupilAmntSave","-----transact= "+transact);	   
    //##########获取页面信息########### 
    LLParaPupilAmntSchema tLLParaPupilAmntSchema = new LLParaPupilAmntSchema();
	//准备后台数据

	tLLParaPupilAmntSchema.setComCode(request.getParameter("ComCode")); //管理机构代码
	tLLParaPupilAmntSchema.setComCodeName(request.getParameter("ComCodeName")); //管理机构名称
	//tLLParaPupilAmntSchema.setUpComCode(request.getParameter("UpComCode")); //上级机构
	tLLParaPupilAmntSchema.setUpComCode("86"); //上级机构
	tLLParaPupilAmntSchema.setBaseValue(request.getParameter("BaseValue")); //保额标准
	tLLParaPupilAmntSchema.setStartDate(request.getParameter("StartDate")); //启用日期
	tLLParaPupilAmntSchema.setEndDate(request.getParameter("EndDate")); //结束日期

    try
    {
        //准备传输数据 VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(transact);
        tVData.add(tLLParaPupilAmntSchema);    	
		if(!tLLParaPupilAmntUI.submitData(tVData,transact))
		{           
			Content = "提交失败，原因是: " + tLLParaPupilAmntUI.mErrors.getError(0).errorMessage;
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
    loggerDebug("LLParaPupilAmntSave","------LLParaPupilAmntSave.jsp end------");
}
  
%>                     
<html>
<script language="javascript">
parent.fraInterface.afterHospitalSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
