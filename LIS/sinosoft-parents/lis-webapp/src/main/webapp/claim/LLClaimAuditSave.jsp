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
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
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
    loggerDebug("LLClaimAuditSave","页面失效,请重新登陆");
}
else
{
	String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码

    //工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
    String wFlag = request.getParameter("WorkFlowFlag");

    //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("RptorState", "30");
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("customerNo"));
    mTransferData.setNameAndValue("CustomerName", request.getParameter("customerName"));
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate")); //事故日期
    mTransferData.setNameAndValue("RgtClass", request.getParameter("RgtClass"));  //申请类型
    mTransferData.setNameAndValue("RgtObj", request.getParameter("RgtObj"));  //号码类型
    mTransferData.setNameAndValue("RgtObjNo", request.getParameter("RgtObjNo"));  //其他号码,团险存储团体赔案号
    mTransferData.setNameAndValue("MngCom", tGI.ManageCom);  //机构
   // mTransferData.setNameAndValue("PrepayFlag", request.getParameter("BudgetFlag"));
    mTransferData.setNameAndValue("PrepayFlag", "0"); //是否是预付
    mTransferData.setNameAndValue("AuditPopedom", request.getParameter("AuditPopedom")); //审核权限
    mTransferData.setNameAndValue("Auditer", Operator); //审核人
    //------------------------------------------------------------------------------BEG
    //审批权限，在匹配计算完善后加入service类后去掉此行
    //------------------------------------------------------------------------------
//    mTransferData.setNameAndValue("Popedom","B01"); 
    //------------------------------------------------------------------------------END
    //转移条件参数
    mTransferData.setNameAndValue("BudgetFlag", "0");  //是否预付，传'0'进去是要工作流流转到审批阶段;
    mTransferData.setNameAndValue("IsRunBL", "1");  //是否运行BL
    mTransferData.setNameAndValue("PopedomPhase","B"); //权限阶段标示A:审核B:审批
    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("BusiType","1003");
    mTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));

    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);

    try
    {
        //提交数据
        loggerDebug("LLClaimAuditSave","-------------------start workflow---------------------");
        //ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI();
//        if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//        {
//            Content = " 数据提交ClaimWorkFlowUI失败，原因是: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
		String busiName="tWorkFlowUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
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
        loggerDebug("LLClaimAuditSave","-------------------end workflow---------------------");
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
    parent.fraInterface.afterSubmit1("<%=FlagStr%>","<%=Content%>");
</script>
</html>
