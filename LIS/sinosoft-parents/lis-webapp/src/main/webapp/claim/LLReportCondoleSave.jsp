<%
//**************************************************************************************************
//Name：LLReportCondoleSave.jsp
//Function：提起慰问
//Author：zhoulei
//Date: 2005-6-30 16:20
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
<%@page import="com.sinosoft.service.*" %>

<%
//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI");  

//页面有效
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLReportCondoleSave","页面失效,请重新登陆");
}
else
{
    
    //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("customerNo"));

    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);
        
    try
    {  
        //提交数据
        loggerDebug("LLReportCondoleSave","-------------------start 提起慰问---------------------");
//        LLReportCondoleUI tLLReportCondoleUI = new LLReportCondoleUI();
//        if(!tLLReportCondoleUI.submitData(tVData,"UPDATE"))
//        {
//            Content = " 数据提交LLReportCondoleUI失败，原因是: " + tLLReportCondoleUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
 String busiName="LLReportCondoleUI";
 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

		if(!tBusinessDelegate.submitData(tVData,"UPDATE",busiName))
		   {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
						   Content = " 数据提交LLReportCondoleUI失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
						   FlagStr = "Fail";
				}
				else
				{
						   Content = "数据提交LLReportCondoleUI失败";
						   FlagStr = "Fail";				
				}
		   }

        else
        {
            Content = " 数据提交成功！";
            FlagStr = "Succ";
        }
        loggerDebug("LLReportCondoleSave","-------------------end 提起慰问---------------------");
    }
    catch(Exception ex)
    {
        Content = "数据提交LLReportCondoleUI失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    } 
    
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit3("<%=FlagStr%>","<%=Content%>");
</script>
</html>
