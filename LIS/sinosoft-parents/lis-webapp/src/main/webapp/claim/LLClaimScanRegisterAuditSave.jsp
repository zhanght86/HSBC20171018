<%
//**************************************************************************************************
//Name：LLClaimScanRegisterAuditSave.jsp
//Function：初审结论保存
//Author：zhangyang
//Date: 2010-5-12 11:37
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

	//输入参数
	LLRegisterIssueSchema tLLRegisterIssueSchema = new LLRegisterIssueSchema(); //案件核赔表

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
    loggerDebug("LLClaimScanRegisterAuditSave","页面失效,请重新登陆");
	}
	else
	{

   	//获取操作符
   	String transact = request.getParameter("Operate");

    	//获取报案页面信息
    tLLRegisterIssueSchema.setRgtNo(request.getParameter("ClmNo")); //赔案号
    tLLRegisterIssueSchema.setIssueConclusion(request.getParameter("IssueConclusion")); //初审结论
    tLLRegisterIssueSchema.setIssueReason(request.getParameter("AccDesc")); 
    tLLRegisterIssueSchema.setIssueStage("1"); //退回阶段 1-初审 2-立案 
    
    

    String sMissionID = request.getParameter("MissionID");
    String sSubMissionID = request.getParameter("SubMissionID");
    String sActivityID = request.getParameter("ActivityID");
  //打包数据
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("MissionID", sMissionID);
    mTransferData.setNameAndValue("SubMissionID", sSubMissionID);
    mTransferData.setNameAndValue("ActivityID", sActivityID);

    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(tLLRegisterIssueSchema);
    tVData.add(mTransferData);

    try
    {
        //提交数据
        loggerDebug("LLClaimScanRegisterAuditSave","-------------------start LLClaimAuditConclusionSave.jsp---------------------");
//        LLClaimScanIssueConclusionUI tLLClaimScanIssueConclusionUI = new LLClaimScanIssueConclusionUI();
//        if(!tLLClaimScanIssueConclusionUI.submitData(tVData,transact))
//        {
//            Content = " 数据提交LLClaimAuditUI失败，原因是: " + tLLClaimScanIssueConclusionUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
        String busiName="LLClaimScanIssueConclusionUI";
		 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		   if(!tBusinessDelegate.submitData(tVData,transact,busiName))
		   {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
						   Content = "数据提交LLClaimAuditUI失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
						   FlagStr = "Fail";
				}
				else
				{
						   Content = "数据提交LLClaimAuditUI失败";
						   FlagStr = "Fail";				
				}
		   }
        
        else
        {
            Content = " 数据提交成功！";
            FlagStr = "Succ";
        }
        loggerDebug("LLClaimScanRegisterAuditSave","-------------------end LLClaimAuditConclusionSave.jsp---------------------");
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
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
