<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLInqDistributeSave.jsp
//程序功能：调查任务分配界面
//创建日期：
//创建人  ：
//更新记录：  更新人:yuejw    更新日期     更新原因/内容
%>

<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%
//输入参数
LLInqApplySchema tLLInqApplySchema = new LLInqApplySchema(); //调查申请
ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //理赔工作流引擎 

//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI"); 
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLInqDistributeSave","页面失效,请重新登陆");    
}
else //页面有效
{
    String Operator=tGI.Operator ; //保存登陆管理员账号
    String ManageCom=tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码
    String transact = request.getParameter("fmtransact"); //获取操作
    String wFlag = request.getParameter("WorkFlowFlag");
    String ActivityID = request.getParameter("ActivityID");

    //***************************************
    //获取页面信息 
    //***************************************   
    tLLInqApplySchema.setInqPer(request.getParameter("InqPer"));
	tLLInqApplySchema.setClmNo(request.getParameter("tClmNo"));
    tLLInqApplySchema.setInqNo(request.getParameter("tInqNo"));
    
        //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo",request.getParameter("tClmNo")); //赔案号
    mTransferData.setNameAndValue("InqNo",request.getParameter("tInqNo")); //调查序号
    mTransferData.setNameAndValue("CustomerNo",request.getParameter("tCustomerNo")); //出险人客户号
    mTransferData.setNameAndValue("CustomerName",request.getParameter("tCustomerName")); //出险人名称
    mTransferData.setNameAndValue("InqDept",request.getParameter("tInitDept")); //分配调查任务机构  
    mTransferData.setNameAndValue("InqStartDate",PubFun.getCurrentDate()); //任务分配日期  
    mTransferData.setNameAndValue("InqPer",request.getParameter("InqPer")); //指定的调查人(用于查询数据)  
	  //mTransferData.setNameAndValue("MngCom", request.getParameter("ManageCom"));  //机构    
	mTransferData.setNameAndValue("Operator",request.getParameter("InqPer"));  
    mTransferData.setNameAndValue("MngCom", request.getParameter("tManageCom"));  //机构    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));   
    try
    {
        //准备传输数据 VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(mTransferData);
        tVData.add(tLLInqApplySchema);
        wFlag=ActivityID;
        try
        {
            loggerDebug("LLInqDistributeSave","---ClaimWorkFlowUI submitData and transact="+transact);
            if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
            {
  	            loggerDebug("LLInqDistributeSave","---@@@@@@@@@@@@@@@");
                Content = "提交工作流失败，原因是: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
                FlagStr = "Fail";
            }
            else
        	{
        		//从结果集中取出前台需要数据
                tVData.clear();
                tVData = tClaimWorkFlowUI.getResult();
                loggerDebug("LLInqDistributeSave","tVData="+tVData);	    
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
    loggerDebug("LLInqDistributeSave","------LLInqDistributeSave.jsp end------");
    loggerDebug("LLInqDistributeSave",FlagStr);
    loggerDebug("LLInqDistributeSave",Content);
}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

