<%
//**************************************************************************************************
//Name：LLClaimConfirmSave.jsp
//Function：审批管理提交
//Author：zhoulei
//Date:
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

//输入参数
LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema(); //案件核赔表

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
    loggerDebug("LLClaimConfirmSave","页面失效,请重新登陆");    
}
else
{
	
	
    //获取操作insert||update
    String transact = request.getParameter("fmtransact");
    //工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
    String wFlag = request.getParameter("WorkFlowFlag");

    //获取报案页面信息
    tLLClaimUWMainSchema.setClmNo(request.getParameter("RptNo")); //赔案号
    tLLClaimUWMainSchema.setExamConclusion(request.getParameter("DecisionSP")); //审批结论
    tLLClaimUWMainSchema.setExamIdea(request.getParameter("RemarkSP")); //审批意见
    tLLClaimUWMainSchema.setExamNoPassReason(request.getParameter("ExamNoPassReason")); //不通过原因
    tLLClaimUWMainSchema.setExamNoPassDesc(request.getParameter("ExamNoPassDesc")); //不通过依据
    
    //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("RptorState", "40");
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("customerNo"));
    mTransferData.setNameAndValue("CustomerName", request.getParameter("customerName"));
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate"));
    mTransferData.setNameAndValue("RgtClass", request.getParameter("RgtClass"));  //申请类型
    mTransferData.setNameAndValue("RgtObj", request.getParameter("RgtObj"));  //号码类型
    mTransferData.setNameAndValue("RgtObjNo", request.getParameter("RgtObjNo"));  //其他号码,团险存储团体赔案号
    mTransferData.setNameAndValue("MngCom", tGI.ManageCom);  //机构
    mTransferData.setNameAndValue("RgtState", request.getParameter("RgtState"));  //案件类型
    
    mTransferData.setNameAndValue("Popedom",request.getParameter("AuditPopedom")); //审核权限
    mTransferData.setNameAndValue("PrepayFlag", request.getParameter("BudgetFlag")); //预付标志
    mTransferData.setNameAndValue("Auditer", request.getParameter("Auditer")); //审核人
    mTransferData.setNameAndValue("ModifyRgtState",request.getParameter("ModifyRgtState")); //案件类型修改Modify by zhaorx 2006-04-17
    
    //转移条件
    mTransferData.setNameAndValue("Reject", request.getParameter("DecisionSP"));  //审批是否通过
    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
    mTransferData.setNameAndValue("BusiType", "1003");
    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);  
    tVData.add(tLLClaimUWMainSchema); 
        
    
	    try
	    {  
	        //提交数据
	        loggerDebug("LLClaimConfirmSave","-------------------start workflow---------------------");
	        //wFlag = "0000005055";
//	        ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI();
//	        if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//	        {
//	            Content = " 审批确认失败，原因是: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//	            FlagStr = "Fail";
//	        }
			String busiName="tWorkFlowUI";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
			{    
			    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			    { 
					Content = "审批确认失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "审批确认失败";
					FlagStr = "Fail";				
				}
			}

	        else
	        {
	            Content = " 审批确认成功！";
	            FlagStr = "Succ";
	        }
	        loggerDebug("LLClaimConfirmSave","-------------------end workflow---------------------");
	    }
	    catch(Exception ex)
	    {
	        Content = "审批确认失败，原因是:" + ex.toString();
	        FlagStr = "Fail";
	    } 
	
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
