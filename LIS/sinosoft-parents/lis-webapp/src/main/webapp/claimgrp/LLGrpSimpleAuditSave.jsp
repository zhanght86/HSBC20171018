<%
//**************************************************************************************************
//Name：LLClaimAuditSave.jsp
//Function：审核管理提交
//Author：zhoulei
//Date: 2005-6-20 15:40
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
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>

<%

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
    loggerDebug("LLGrpSimpleAuditSave","页面失效,请重新登陆");
}
else
{
	String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码

    //工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
    String wFlag = request.getParameter("WorkFlowFlag");
    String mRptNo = request.getParameter("RptNo");
    //审核结论
    String tSimpleConclusion2 = request.getParameter("SimpleConclusion2");
    //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    if(tSimpleConclusion2.equals("5")||tSimpleConclusion2.equals("0")){
    	if(tSimpleConclusion2.equals("0")){
    		mTransferData.setNameAndValue("RptorState", "50");
    	}else{
        mTransferData.setNameAndValue("RptorState", "20");
    		String MngCom=request.getParameter("mngNo");
    		mTransferData.setNameAndValue("MngCom",MngCom);  //立案机构
    		String operator=request.getParameter("operator");
    		loggerDebug("LLGrpSimpleAuditSave","operator:"+operator);
    		mTransferData.setNameAndValue("DefaultOperator",operator);  //立案操作员
    	}
    }else{
    mTransferData.setNameAndValue("RptorState", "40");
    }
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("customerNo"));
    mTransferData.setNameAndValue("CustomerName", request.getParameter("customerName"));
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate"));
    mTransferData.setNameAndValue("RgtClass", "1");  //申请类型
    mTransferData.setNameAndValue("RgtObj", "1");  //号码类型
    mTransferData.setNameAndValue("RgtObjNo", request.getParameter("RptNo"));  //其他号码
    mTransferData.setNameAndValue("MngCom", tGI.ManageCom);  //机构
    mTransferData.setNameAndValue("PrepayFlag", request.getParameter("BudgetFlag"));
    mTransferData.setNameAndValue("AuditPopedom", request.getParameter("AuditPopedom")); //审核权限
    mTransferData.setNameAndValue("Auditer", request.getParameter("Operator")); //审核人
    mTransferData.setNameAndValue("RgtNo", mRptNo); //审核人
 

    //转移条件参数
    if(tSimpleConclusion2.equals("5")){
    //原来（0000005035->0000005025） 现在改为（0000005035->0000005015）
    mTransferData.setNameAndValue("BudgetFlag", "1");  //案件回退条件 （1 立案）
    }
    else if(tSimpleConclusion2.equals("0")) //||tAuditConclusion.equals("1"))
    {
    	//（0000005035->0000005075）
    	mTransferData.setNameAndValue("Reject", "0");  //案件流转到理赔结案
    	mTransferData.setNameAndValue("BudgetFlag", "");  //案件回退条件 （1 立案）
    }
    else
    {
	    //（0000005035->0000005055）
	    mTransferData.setNameAndValue("BudgetFlag", "0");  //案件回退条件 （0 审批管理）
	}


    mTransferData.setNameAndValue("IsRunBL", "1");  //是否运行BL
    mTransferData.setNameAndValue("PopedomPhase","B"); //权限阶段标示A:审核B:审批
    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID","0000009035");
    mTransferData.setNameAndValue("AuditConclusion",tSimpleConclusion2); //审核结论
    mTransferData.setNameAndValue("RgtState","03"); //案件类型

    //团单个单判断回退后的标示
    if(request.getParameter("RgtClass").equals("1") && tSimpleConclusion2.equals("5")){
    mTransferData.setNameAndValue("OtherOperator", request.getParameter("RgtClass")); //外包操作人(存放个险标记)
    }

    String tsql="select rgtstate from llregister where rgtno='"+mRptNo+"' ";
    ExeSQL tExeSQL = new ExeSQL();
    String tRgtState=tExeSQL.getOneValue(tsql);
    loggerDebug("LLGrpSimpleAuditSave","tRgtState:"+tRgtState);
    mTransferData.setNameAndValue("RgtState","03");
    mTransferData.setNameAndValue("AuditConclusion",tSimpleConclusion2);

    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);

    try
    {
        //提交数据
        loggerDebug("LLGrpSimpleAuditSave","-------------------start workflow---------------------");
        wFlag = "0000009035";
//        ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI();
//        if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//        {
//            Content = " 数据提交ClaimWorkFlowUI失败，原因是: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
String busiName="grpClaimWorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,wFlag,busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "数据提交ClaimWorkFlowUI失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
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
        loggerDebug("LLGrpSimpleAuditSave","-------------------end workflow---------------------");
    }
    catch(Exception ex)
    {
        Content = "数据提交ClaimWorkFlowUI失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
