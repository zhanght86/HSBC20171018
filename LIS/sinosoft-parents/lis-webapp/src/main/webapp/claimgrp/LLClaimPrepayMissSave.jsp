<%
//**************************************************************************************************
//程序名称：LLClaimPrepayMissSave.jsp
//程序功能：
//创建日期：2005-07-06 10:33
//创建人  ：yuejw
//更新记录：
//**************************************************************************************************
%>

<!--用户校验类-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%

//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tG = new GlobalInput();
tG = (GlobalInput)session.getValue("GI");
ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //理赔工作流引擎
//页面有效性
if(tG == null)
{
    loggerDebug("LLClaimPrepayMissSave","session has expired");
    return;
}

//打包数据,用于生成“预付节点”
TransferData mTransferData = new TransferData();
mTransferData.setNameAndValue("RptNo",request.getParameter("tRptNo")); //"赔案号" 
mTransferData.setNameAndValue("RptorState",request.getParameter("tRptorState")); //"报案状态"
mTransferData.setNameAndValue("CustomerNo",request.getParameter("tCustomerNo")); //"出险人编码"
mTransferData.setNameAndValue("CustomerName",request.getParameter("tCustomerName")); //"出险人姓名 
mTransferData.setNameAndValue("CustomerSex",request.getParameter("tCustomerSex")); //"出险人性别"
mTransferData.setNameAndValue("AccDate",request.getParameter("tAccDate")); //"出险日期" 
mTransferData.setNameAndValue("RgtClass",request.getParameter("tRgtClass")); //"申请类型" 
mTransferData.setNameAndValue("RgtObj",request.getParameter("tRgtObj")); //"号码类型" 
mTransferData.setNameAndValue("RgtObjNo",request.getParameter("tRgtObjNo")); //"其他号码" 
mTransferData.setNameAndValue("Popedom",request.getParameter("tPopedom")); //"核赔师权限" 
//mTransferData.setNameAndValue("Popedom",request.getParameter("tPrepayFlag")); //"预付标志" 
mTransferData.setNameAndValue("ComeWhere",request.getParameter("tComeWhere")); //"来自" 
mTransferData.setNameAndValue("Auditer",request.getParameter("tAuditer")); //"审核操作人" 
mTransferData.setNameAndValue("MngCom",request.getParameter("tMngCom")); //"机构" 
mTransferData.setNameAndValue("Operator",request.getParameter("Operator")); //预付操作人
mTransferData.setNameAndValue("ComFlag",request.getParameter("tComFlag")); //权限跨级标志
mTransferData.setNameAndValue("BudgetFlag","1");
mTransferData.setNameAndValue("MissionID",request.getParameter("tMissionID"));
mTransferData.setNameAndValue("SubMissionID",request.getParameter("tSubMissionID"));
mTransferData.setNameAndValue("ActivityID",request.getParameter("tActivityID"));
mTransferData.setNameAndValue("IsRunBL","0"); //用于判断是否运行BL，0--不运行

try
{
    // 准备传输数据 VData
    VData tVData = new VData();
    tVData.add(mTransferData);
    tVData.add(tG);
    try
    {
        if(!tClaimWorkFlowUI.submitData(tVData,"0000005035"))
        {
            Content = " 提交工作流失败，原因是: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
            FlagStr = "Fail";
        }
        else
    	{
    		//从结果集中取出前台需要数据
            tVData.clear();
            tVData = tClaimWorkFlowUI.getResult();
            loggerDebug("LLClaimPrepayMissSave","tVData="+tVData);	    
            Content = "数据提交成功";
            FlagStr = "Succ";            
    	}
	}
	catch(Exception ex)
    {
    	Content = "数据提交ClaimWorkFlowUI失败，原因是:" + ex.toString();
    	FlagStr = "Fail";
    }	
}
catch(Exception ex)
{
    Content = "数据提交ClaimWorkFlowUI失败，原因是:" + ex.toString();
    FlagStr = "Fail";
}
loggerDebug("LLClaimPrepayMissSave","------LLClaimPrepayMissSave.jsp end------");

%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
