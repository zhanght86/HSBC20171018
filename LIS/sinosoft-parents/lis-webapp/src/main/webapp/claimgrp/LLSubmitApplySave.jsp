<%
//Name：LLSubmitApplySave.jsp
//Function：发起呈报提交
//Author：zhoulei
//Date:
%>
<%@page contentType="text/html;charset=GBK" %> 
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>


<%
//************************
//接收信息，并作校验处理
//************************

//输入参数
LLSubmitApplySchema tLLSubmitApplySchema = new LLSubmitApplySchema(); //呈报

//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI");  
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //理赔工作流引擎
//LLSubmitApplyUI tLLSubmitApplyUI = new LLSubmitApplyUI();  //呈报申请表提交类
String busiName="grpClaimWorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//String busiName1="grpLLSubmitApplyUI";
//BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    System.out.println("页面失效,请重新登陆");    
}
else //页面有效
{
    String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码

    String transact = request.getParameter("fmtransact"); //获取操作insert||update
    System.out.println("-----transact= "+transact);
    //工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
    String wFlag = request.getParameter("WorkFlowFlag");

    //***************************************
    //获取报案页面信息，表中字段用schema打包，String等零散数据使用TransferData打包
    //***************************************
    tLLSubmitApplySchema.setClmNo(request.getParameter("ClmNo")); //赔案号
   // tLLSubmitApplySchema.setInqNo(request.getParameter("SubNo")); //呈报序号（自动生成）
    tLLSubmitApplySchema.setSubCount(request.getParameter("SubCount")); //呈报次数
    tLLSubmitApplySchema.setCustomerNo(request.getParameter("CustomerNo")); //出险人客户号
    tLLSubmitApplySchema.setCustomerName(request.getParameter("CustomerName")); //出险人名称
    tLLSubmitApplySchema.setVIPFlag(request.getParameter("VIPFlag")); //VIP客户标志
    tLLSubmitApplySchema.setInitPhase(request.getParameter("InitPhase")); //提起阶段    
    tLLSubmitApplySchema.setSubType(request.getParameter("SubType")); //呈报类型
    tLLSubmitApplySchema.setSubRCode(request.getParameter("SubRCode")); //呈报原因
    tLLSubmitApplySchema.setSubDesc(request.getParameter("SubDesc")); //呈报描述
    tLLSubmitApplySchema.setSubPer(request.getParameter("SubPer")); //呈报人
    tLLSubmitApplySchema.setSubDate(request.getParameter("SubDate")); //呈报日期
    tLLSubmitApplySchema.setSubDept(request.getParameter("SubDept")); //呈报机构
    tLLSubmitApplySchema.setSubState(request.getParameter("SubState")); //呈报状态
            
    //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo")); //赔案号
    mTransferData.setNameAndValue("SubNo",request.getParameter("SubNo")); //呈报序号
    mTransferData.setNameAndValue("SubCount",request.getParameter("SubCount")); //呈报次数
    mTransferData.setNameAndValue("CustomerNo",request.getParameter("CustomerNo")); //出险人客户号
    mTransferData.setNameAndValue("CustomerName",request.getParameter("CustomerName")); //出险人名称
    mTransferData.setNameAndValue("SubType",request.getParameter("SubType")); //呈报类型
    mTransferData.setNameAndValue("SubRCode",request.getParameter("SubRCode")); //呈报原因
    mTransferData.setNameAndValue("FilialeDirector",request.getParameter("FilialeDirector")); //分公司主任核赔员
    mTransferData.setNameAndValue("MngCom",request.getParameter("MngCom")); //机构
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
                    
    try
    {
        //准备传输数据 VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(mTransferData);
        tVData.add(tLLSubmitApplySchema);       
    	wFlag = "8999999999";
        try
        {
            System.out.println("---ClaimWorkFlowUI submitData and transact="+transact);
//            if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//            {
//                Content = " 提交工作流ClaimWorkFlowUI失败，原因是: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
			if(!tBusinessDelegate.submitData(tVData,wFlag,busiName))
			{    
			    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			    { 
					Content = "提交工作流ClaimWorkFlowUI失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "提交工作流ClaimWorkFlowUI失败";
					FlagStr = "Fail";				
				}
			}

            else
        	{
        		//从结果集中取出前台需要数据
                tVData.clear();
                //tVData = tClaimWorkFlowUI.getResult();
                 tVData = tBusinessDelegate.getResult();
                System.out.println("tVData="+tVData);	    
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
    
    System.out.println("------LLSubmitApplySave.jsp end------");
    System.out.println(FlagStr);
    System.out.println(Content);
    
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>