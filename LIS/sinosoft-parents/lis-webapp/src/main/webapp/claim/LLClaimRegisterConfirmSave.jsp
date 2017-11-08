<%
//**************************************************************************************************
//Name：LLClaimRegisterConfirmSave.jsp
//Function：立案信息确认
//Author：zhoulei
//Date: 2005-6-20 11:34
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
//输出参数
CErrors tError = null;
String FlagStr="";
String Content = "";
String wFlag = "";
GlobalInput tGI = new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");

//页面有效性判断
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLClaimRegisterConfirmSave","页面失效,请重新登陆");
}
else 
{
    String RgtConclusion = request.getParameter("RgtConclusion");
    //add by liyan 2016-10-31
    String SimpleFlag = request.getParameter("rgtType");
    if(SimpleFlag == "11"){
    	SimpleFlag = "0";
    }
    if(SimpleFlag == "01"){
    	SimpleFlag = "1";
    }
    //**************************************************
    //工作流相关参数使用TransferData打包后提交
    //**************************************************
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("RptorState", "20");
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("customerNo"));
    mTransferData.setNameAndValue("CustomerName", request.getParameter("customerName"));
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate")); //事故日期   
    mTransferData.setNameAndValue("RgtClass", "1");  //申请类型：个险为1,团险为2
    mTransferData.setNameAndValue("RgtObj", "1");  //号码类型
    mTransferData.setNameAndValue("RgtObjNo", request.getParameter("RptNo"));  //其他号码
    mTransferData.setNameAndValue("MngCom", tGI.ManageCom);  //机构
    mTransferData.setNameAndValue("PrepayFlag", "0");  //预付标志
    mTransferData.setNameAndValue("ComeWhere", "A");  //来自:A立案进入审核，B审批进入审核因为不通过，C审批进入审核因为赔案为负数，D为预付进入审核
    //------------------------------------------------------------------------------BEG
    //审核权限，在匹配计算完善后加入service类后去掉此行
    //------------------------------------------------------------------------------
//    mTransferData.setNameAndValue("Popedom","A01"); 
    //------------------------------------------------------------------------------END
    //转移条件参数
    mTransferData.setNameAndValue("SimpleFlag", SimpleFlag);  //是否简易案件
    //业务类需要数据
    mTransferData.setNameAndValue("PopedomPhase","A"); //权限阶段标示A:审核B:审批
    mTransferData.setNameAndValue("standpay",request.getParameter("standpay")); //预估金额
    mTransferData.setNameAndValue("adjpay",request.getParameter("adjpay")); //调整后金额
    
    mTransferData.setNameAndValue("RgtConclusion",RgtConclusion);
    
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("BusiType", "1003");
    mTransferData.setNameAndValue("ActivityID", request.getParameter("ActivityID"));
    
    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);


    loggerDebug("LLClaimRegisterConfirmSave","-------------------start workflow---------------------");
    //wFlag = "0000005015";
//	ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI();
//    if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//    {
//        Content = " 数据提交ClaimWorkFlowUI失败，原因是: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//    }
//    else
//    {
//        Content = " 数据提交成功！";
//        FlagStr = "Succ";
//    }
   String busiName="tWorkFlowUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
   if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
   {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = "数据提交ClaimWorkFlowUI失败，原因是: " + tBusinessDelegate.getCErrors().getFirstError();
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
	    Content = " 数据提交成功！";
	    FlagStr = "Succ";
  }
    loggerDebug("LLClaimRegisterConfirmSave","-------------------end workflow---------------------");

}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");
</script>
</html>
