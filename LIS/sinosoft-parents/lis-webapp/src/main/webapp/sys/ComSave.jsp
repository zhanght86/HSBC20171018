<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ComInput.jsp
//程序功能：
//创建日期：2002-08-16 17:44:40
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.sys.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
//接收信息，并作校验处理。
//输入参数
LDComSchema tLDComSchema   = new LDComSchema();

//ComUI tComUI = new ComUI();
String busiName="ComUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

//输出参数
CErrors tError = null;

String tRela  = "";                
String FlagStr = "";
String Content = "";
String transact = "";

transact = request.getParameter("fmtransact");
loggerDebug("ComSave","------transact:"+transact);
tLDComSchema.setComCode(request.getParameter("ComCode"));
tLDComSchema.setOutComCode(request.getParameter("OutComCode"));
tLDComSchema.setName(request.getParameter("Name"));
tLDComSchema.setShortName(request.getParameter("ShortName"));
tLDComSchema.setComGrade(request.getParameter("ComGrade"));
tLDComSchema.setUpComCode(request.getParameter("UpComCode"));
//tLDComSchema.setComAreaType(request.getParameter("ComAreaType"));
tLDComSchema.setComCitySize(request.getParameter("ComCitySize"));
tLDComSchema.setAddress(request.getParameter("Address"));
tLDComSchema.setZipCode(request.getParameter("ZipCode"));
tLDComSchema.setPhone(request.getParameter("Phone"));
tLDComSchema.setFax(request.getParameter("Fax"));
tLDComSchema.setEMail(request.getParameter("EMail"));
tLDComSchema.setWebAddress(request.getParameter("WebAddress"));
tLDComSchema.setSatrapName(request.getParameter("SatrapName"));
tLDComSchema.setSign(request.getParameter("Sign"));
tLDComSchema.setIsDirUnder(request.getParameter("IsDirUnder"));

try {
	// 准备传输数据 VData
	VData tVData = new VData();
	tVData.addElement(tLDComSchema);
	//tComUI.submitData(tVData,transact);
	if(!tBusinessDelegate.submitData(tVData,transact,busiName))
	{    
	    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	    { 
			Content = "保存失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else
		{
			Content = "保存失败";
			FlagStr = "Fail";				
		}
	}
	else{
		Content = "保存成功";
		FlagStr = "Success";	
	}
	
}
catch(Exception ex) {
	Content = "保存失败，原因是:" + ex.toString();
	FlagStr = "Fail";
}
  
//如果在Catch中发现异常，则不从错误类中提取错误信息
if (FlagStr=="") {
	//tError = tComUI.mErrors;
	tError = tBusinessDelegate.getCErrors();
	if (!tError.needDealError()) {                          
		Content = " 保存成功! ";
		FlagStr = "Success";
	}
	else {
		Content = " 保存失败，原因是:" + tError.getFirstError();
		FlagStr = "Fail";
	}
}
%>                      
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
