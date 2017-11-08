<%
//**************************************************************************************************
//Name：LLMedicalFeeAdjSave.jsp
//Function：医疗费用调整显示信息
//Author：quyang
//Date: 2005-7-05 
//Desc: 
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
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
loggerDebug("LLMedicalFeeAdjSave","#########################LLMedicalFeeAdjSave.jsp start#################################");
//输入参数
LLClaimDutyFeeSchema tLLClaimDutyFeeSchema = new LLClaimDutyFeeSchema(); //责任费用统计


//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput();
tGI = (GlobalInput)session.getValue("GI");
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //理赔工作流引擎
//LLMedicalFeeAdjUI tLLMedicalFeeAdjUI = new LLMedicalFeeAdjUI(); //医疗费用调整显示信息修改提交类
//String busiName="ClaimWorkFlowUI";
//BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

String busiName="LLMedicalFeeAdjUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

//页面有效性判断
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLMedicalFeeAdjSave","页面失效,请重新登陆");
}
else
{
    //**********************************************************************************************
    //获取报案页面信息，表中字段用schema打包，String等零散数据使用TransferData打包
    //**********************************************************************************************

    //获取操作符（insert||first、insert||customer、update）
    String transact = request.getParameter("fmtransact");
    
    //工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
    String wFlag = request.getParameter("WorkFlowFlag");
    
	//费用类型
    String operationType = request.getParameter("OperationType");

	loggerDebug("LLMedicalFeeAdjSave","operationType"+operationType);

	if("A".equalsIgnoreCase(operationType) || "B".equalsIgnoreCase(operationType))
	{
		tLLClaimDutyFeeSchema.setAdjSum(request.getParameter("AdjSum")); //调整金额
		tLLClaimDutyFeeSchema.setAdjReason(request.getParameter("AdjReason")); //调整原因
		tLLClaimDutyFeeSchema.setAdjRemark(request.getParameter("AdjRemark")); //调整备注
		tLLClaimDutyFeeSchema.setClmNo(request.getParameter("ClmNo")); //赔案号
		tLLClaimDutyFeeSchema.setDutyFeeType(request.getParameter("DutyFeeType")); //费用类型
		tLLClaimDutyFeeSchema.setDutyFeeCode(request.getParameter("DutyFeeCode")); //费用代码
		tLLClaimDutyFeeSchema.setDutyFeeStaNo(request.getParameter("DutyFeeStaNo")); //序号
    
	}
	else if("C".equalsIgnoreCase(operationType))
	{

		tLLClaimDutyFeeSchema.setRealRate(request.getParameter("RealRate")); //实际给付比例
		tLLClaimDutyFeeSchema.setAdjReason(request.getParameter("AdjReason1")); //调整原因
		tLLClaimDutyFeeSchema.setAdjRemark(request.getParameter("AdjRemark1")); //调整备注
		tLLClaimDutyFeeSchema.setClmNo(request.getParameter("ClmNo")); //赔案号
		tLLClaimDutyFeeSchema.setDutyFeeType(request.getParameter("DutyFeeType")); //费用类型
		tLLClaimDutyFeeSchema.setDutyFeeCode(request.getParameter("DutyFeeCode")); //费用代码
		tLLClaimDutyFeeSchema.setDutyFeeStaNo(request.getParameter("DutyFeeStaNo")); //序号

	}
	else if("F".equalsIgnoreCase(operationType) || "D".equalsIgnoreCase(operationType) || "E".equalsIgnoreCase(operationType))
	{
		tLLClaimDutyFeeSchema.setAdjSum(request.getParameter("AdjSum1")); //调整金额
		tLLClaimDutyFeeSchema.setAdjReason(request.getParameter("AdjReason2")); //调整原因
		tLLClaimDutyFeeSchema.setAdjRemark(request.getParameter("AdjRemark2")); //调整备注
		tLLClaimDutyFeeSchema.setClmNo(request.getParameter("ClmNo")); //赔案号
		tLLClaimDutyFeeSchema.setDutyFeeType(request.getParameter("DutyFeeType")); //费用类型
		tLLClaimDutyFeeSchema.setDutyFeeCode(request.getParameter("DutyFeeCode")); //费用代码
		tLLClaimDutyFeeSchema.setDutyFeeStaNo(request.getParameter("DutyFeeStaNo")); //序号
	}

    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(tLLClaimDutyFeeSchema);


    // 数据传输
//	if (!tLLMedicalFeeAdjUI.submitData(tVData,""))
//	{
//
//        Content = " 保存失败，原因是: " + tLLMedicalFeeAdjUI.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//	}
	if(!tBusinessDelegate.submitData(tVData,"",busiName))
	{    
	    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	    { 
			Content = "保存失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else
		{
			Content = "保存失败";
			FlagStr = "Fail";				
		}
	}

	else
	{
	    Content = " 保存成功! ";
    	FlagStr = "Succ";
	}
}
%>


<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
