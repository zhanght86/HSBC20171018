<%
//**************************************************************************************************
//Name：LLClaimConfirmReportBackSave.jsp
//Function：审批管理上报
//Author：zhangzheng
//Date:2007-11-28
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
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
    loggerDebug("LLClaimConfirmReportBackSave","页面失效,请重新登陆");    
}
else
{
    //获取操作insert||update
    String transact = request.getParameter("fmtransact");
    loggerDebug("LLClaimConfirmReportBackSave","LLClaimConfirmReportBackSave页面获得操作类型="+transact);
    //工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
    
    //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID","0000009055");
    
    //获取审批结论
    LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema(); //案件核赔表
    tLLClaimUWMainSchema.setClmNo(request.getParameter("RptNo")); //赔案号
    tLLClaimUWMainSchema.setExamConclusion(request.getParameter("DecisionSP")); //审批结论
    tLLClaimUWMainSchema.setExamIdea(request.getParameter("RemarkSP")); //审批意见
 

    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);  
    tVData.add(tLLClaimUWMainSchema); 
        
    try
    {  
        //提交数据
        loggerDebug("LLClaimConfirmReportBackSave","-------------------start workflow---------------------");

//        LLClaimConfirmReportBackBL tLLClaimConfirmReportBackBL = new LLClaimConfirmReportBackBL();
//        if(!tLLClaimConfirmReportBackBL.submitData(tVData,transact))
//        {
//            Content = " 案件上报失败，原因是: " + tLLClaimConfirmReportBackBL.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
		String busiName="grpLLClaimConfirmReportBackBL";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if(!tBusinessDelegate.submitData(tVData,transact,busiName))
		{    
		    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		    { 
				Content = "案件上报失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "案件上报失败";
				FlagStr = "Fail";				
			}
		}

        else
        {
            Content = " 案件上报成功！";
            FlagStr = "Succ";
        }
        loggerDebug("LLClaimConfirmReportBackSave","-------------------end workflow---------------------");
    }
    catch(Exception ex)
    {
        Content = "案件上报失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    } 
    
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
