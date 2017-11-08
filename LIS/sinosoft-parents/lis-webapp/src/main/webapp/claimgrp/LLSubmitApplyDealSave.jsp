<%
//Name：LLSubmitApplyDealSave.jsp
//Function：呈报处理信息提交
%>
<!--用户校验类-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>

<%
//************************
//接收信息，并作校验处理
//************************
//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI");  
LLSubmitApplyDealUI tLLSubmitApplyDealUI = new LLSubmitApplyDealUI();
ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //理赔工作流引擎

if(tGI == null)   //页面无效
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    System.out.println("页面失效,请重新登陆");    
}
else //页面有效
{
    String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码
    System.out.println("----ComCode= "+tGI.ComCode);
    System.out.println("-----ManageCom= "+ManageCom);
    
    String transact = request.getParameter("fmtransact"); //获取操作类型insert||update
    System.out.println("-----transact= "+transact);
    String isReportExist = request.getParameter("isReportExist"); //是否为新增事件，是true，否false
    String wFlag = request.getParameter("WorkFlowFlag");

     //***************************************
    //获取呈报处理页面信息 
    //***************************************   
   //输入参数
    LLSubmitApplySchema tLLSubmitApplySchema = new LLSubmitApplySchema(); //呈报处理表
    tLLSubmitApplySchema.setClmNo(request.getParameter("ClmNo"));
    tLLSubmitApplySchema.setSubNo(request.getParameter("SubNO"));
    tLLSubmitApplySchema.setSubCount(request.getParameter("SubCount"));
    tLLSubmitApplySchema.setDispType(request.getParameter("DispType"));
    tLLSubmitApplySchema.setDispIdea(request.getParameter("DispIdea")); //呈报处理意见
    tLLSubmitApplySchema.setSubDesc(request.getParameter("ReportheadSubDesc")); //继续呈报描述
    
    //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo")); //赔案号
    mTransferData.setNameAndValue("SubNo",request.getParameter("SubNO")); //呈报序号
    mTransferData.setNameAndValue("SubCount",request.getParameter("SubCount")); //呈报次数
    mTransferData.setNameAndValue("CustomerNo",request.getParameter("CustomerNo")); //出险人客户号
    mTransferData.setNameAndValue("CustomerName",request.getParameter("CustomerName")); //出险人名称
    mTransferData.setNameAndValue("SubType",request.getParameter("SubType")); //呈报类型（1--从分公司到总公司）
    mTransferData.setNameAndValue("SubRCode",request.getParameter("SubRCode")); //呈报原因
	mTransferData.setNameAndValue("MngCom", request.getParameter("ManageCom"));  //机构    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("transact",request.getParameter("fmtransact"));

    try
    {
    	//准备传输数据 VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(isReportExist);        
        tVData.add(tLLSubmitApplySchema);
        tVData.add(mTransferData);
        System.out.println("---submitData and transact="+transact);
         //数据传输（继续呈报，直接处理）[此处用transact 替换mOperate]
        if (transact.equals("INSERT||Reporthead"))
        {
            System.out.println("---tLLSubmitApplyDealUI submitData and transact="+transact);
        	if (!tLLSubmitApplyDealUI.submitData(tVData,transact))
	        {
        		Content = " 数据提交失败，原因是: " + tLLSubmitApplyDealUI.mErrors.getError(0).errorMessage;
            	FlagStr = "Fail";
	        }
	        else
	       {
	        	tVData.clear();
	    	 	tVData = tLLSubmitApplyDealUI.getResult();
	    	 	Content = "继续呈报提交成功";
                FlagStr = "Succ";     
	       }
        }
         //如果选择“提起调查或回复意见”则走工作流  
        if(transact.equals("UPDATE||Replyport")|| transact.equals("UPDATE||Investgate")) //删除节点
        {
            System.out.println("---ClaimWorkFlowUI submitData and transact="+transact);
            wFlag="0000005105";           
            if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
            {
                Content = " 提交工作流失败，原因是: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
                FlagStr = "Fail";
            }   
            else
            {
            	//提交成功，从结果集中取出前台需要数据
                tVData.clear();
                tVData = tClaimWorkFlowUI.getResult();
                System.out.println("tVData="+tVData);	    
                Content = "数据提交成功";
                FlagStr = "Succ";            
           	}   
        } 
    }
    catch(Exception ex)
    {
    	Content = "提交失败，原因是:" + ex.toString();
        FlagStr = "Fail";
     }
    //如果在Catch中发现异常，则不从错误类中提取错误信息
    System.out.println("------LLSubmitApplyDealSave.jsp end------");
    System.out.println(FlagStr);
    System.out.println(Content);
}	
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>