<%
//Name：LLInqConclusionSave.jsp
//Function：调查结论提交
//Author：zhoulei
//Date:  
//Remark: 2005-08-20 修改，用于保存机构调查结论，并判断是否生成  赔案层面的调查结论节点。
//		判断条件：查询 调查状态==“未完成” and 赔案号==本次赔案号 and 汇总标志=“2-调查机构结论”，如存在记录则不生成下一节点
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//************************
//接收信息，并作校验处理
//************************

//输入参数
LLInqConclusionSchema tLLInqConclusionSchema = new LLInqConclusionSchema(); //调查结论

//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI");  
ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //理赔工作流引擎

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLInqConclusionSave","页面失效,请重新登陆");    
}
else //页面有效
{
    String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码

    String transact = request.getParameter("fmtransact"); //获取操作insert||update
    loggerDebug("LLInqConclusionSave","-----transact= "+transact);
    //工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
    String wFlag = request.getParameter("WorkFlowFlag");
    //***************************************
    //获取页面信息 
    //***************************************   
    tLLInqConclusionSchema.setClmNo(request.getParameter("ClmNo")); //赔案号
    tLLInqConclusionSchema.setConNo(request.getParameter("ConNo")); //结论序号
    tLLInqConclusionSchema.setInqConclusion(request.getParameter("InqConclusion")); //调查结论
    tLLInqConclusionSchema.setRemark(request.getParameter("Remark")); //备注
    tLLInqConclusionSchema.setInitDept(request.getParameter("InitDept")); //发起机构
    tLLInqConclusionSchema.setInqDept(request.getParameter("InitDept")); //调查机构
     //String等零散数据使用TransferData打包，用于准备消亡节点（机构调查结论），生成 （赔案调查结论节点）
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo")); //赔案号
    mTransferData.setNameAndValue("ConNo",request.getParameter("ConNo")); //结论序号 
    mTransferData.setNameAndValue("BatNo",request.getParameter("BatNo")); //调查批次
    mTransferData.setNameAndValue("InitDept",request.getParameter("InitDept")); //发起机构
    mTransferData.setNameAndValue("InqDept",request.getParameter("InitDept")); //调查机构  
    mTransferData.setNameAndValue("ColFlag",request.getParameter("0")); //汇总标志 
    mTransferData.setNameAndValue("MngCom", request.getParameter("ManageCom"));  //机构  
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("Activityid",request.getParameter("Activityid"));    
    mTransferData.setNameAndValue("transact",request.getParameter("fmtransact"));
 
    try
    {
        //准备传输数据 VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(mTransferData);
        tVData.add(tLLInqConclusionSchema);       
    	wFlag = "0000005165"; //标识（自定义）
        try
        {
            loggerDebug("LLInqConclusionSave","---ClaimWorkFlowUI submitData and transact="+transact);
            if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
            {
                Content = " 提交工作流失败，原因是: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
                FlagStr = "Fail";
            }
            else
        	{
        		//从结果集中取出前台需要数据
//                tVData.clear();
//                tVData = tClaimWorkFlowUI.getResult();
//                loggerDebug("LLInqConclusionSave","tVData="+tVData);	    
                Content = "数据提交成功";
                FlagStr = "Succ";            
        	}
    	}
    	catch(Exception ex)
        {
        	Content = "数据提交失败，原因是:" + ex.toString();
        	FlagStr = "Fail";
        }	
    }  
    catch(Exception ex)
    {
        Content = "数据提交失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }
    
    loggerDebug("LLInqConclusionSave","------LLInqConclusionSave.jsp end------");
    loggerDebug("LLInqConclusionSave",FlagStr);
    loggerDebug("LLInqConclusionSave",Content);
    
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
