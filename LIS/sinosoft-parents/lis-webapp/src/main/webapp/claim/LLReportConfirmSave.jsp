<%
//**************************************************************************************************
//Name：LLReportConfirmSave.jsp
//Function：报案信息确认
//Author：zhoulei
//Date: 2005-6-14 8:42
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
//输出参数
CErrors tError = null;
String FlagStr="";
String Content = "";
String wFlag = "";
GlobalInput tGI = new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");

//页面有效性判断
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLReportConfirmSave","页面失效,请重新登陆");
}
else 
{
	String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码
    
    //**************************************************
    //工作流相关参数使用TransferData打包后提交
    //**************************************************
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("RptorState", "10");
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("customerNo"));
    mTransferData.setNameAndValue("CustomerName", request.getParameter("customerName"));
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate"));
    mTransferData.setNameAndValue("OtherOperator", "");
    mTransferData.setNameAndValue("OtherFlag", "0");
    mTransferData.setNameAndValue("MngCom", ManageCom);
    mTransferData.setNameAndValue("RgtNo", request.getParameter("RptNo"));
    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));

    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);
    //数据传输
    loggerDebug("LLReportConfirmSave","-------------------start workflow---------------------");
    wFlag = "reportconfirm";
//    ClaimReportConfirmBL tClaimReportConfirmBL = new ClaimReportConfirmBL();
//    if(!tClaimReportConfirmBL.submitData(tVData,wFlag))
//    {
//        Content = " 数据提交ClaimWorkFlowUI失败，原因是: " + tClaimReportConfirmBL.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//    }
 String busiName="ClaimReportConfirmBL";
 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

	if(!tBusinessDelegate.submitData(tVData,wFlag,busiName))
	   {    
	        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	        { 
					   Content = "数据提交ClaimWorkFlowUI失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
					   FlagStr = "Fail";
			}
			else
			{
					   Content = "数据提交ClaimWorkFlowUI失败";
					   FlagStr = "Fail";				
			}
	   }

    else
    {
        Content = " 数据提交成功！";
        FlagStr = "Succ";
    }
    loggerDebug("LLReportConfirmSave","-------------------end workflow---------------------");
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");
</script>
</html>
