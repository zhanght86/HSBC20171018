<%
//Name：LLSubmitApplyHeadDealSave.jsp
//Function：呈报处理信息提交
%>
<!--用户校验类-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

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
//LLSubmitApplyHeadDealUI tLLSubmitApplyHeadDealUI = new LLSubmitApplyHeadDealUI();
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //理赔工作流引擎
//String busiName="LLSubmitApplyHeadDealUI";
String busiName ="WorkFlowUI";//工作流引擎 20130528
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(tGI == null)   //页面无效
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLSubmitApplyHeadDealSave","页面失效,请重新登陆");    
}
else //页面有效
{
    String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码    
    String transact = request.getParameter("fmtransact"); //获取操作类型
    loggerDebug("LLSubmitApplyHeadDealSave","-----transact= "+transact);
    String isReportExist = request.getParameter("isReportExist"); //是否为新增事件，是true，否false

     //***************************************
    //获取呈报处理页面信息 
    //***************************************   
   //输入参数
    LLSubmitApplySchema tLLSubmitApplySchema = new LLSubmitApplySchema(); //呈报处理表
    tLLSubmitApplySchema.setClmNo(request.getParameter("ClmNo"));
    tLLSubmitApplySchema.setSubNo(request.getParameter("SubNO"));
    tLLSubmitApplySchema.setSubCount(request.getParameter("SubCount"));
    tLLSubmitApplySchema.setSubType("2"); //呈报类型置“2--分公司到总公司”
    tLLSubmitApplySchema.setDispType(request.getParameter("DispType")); //处理类型
    tLLSubmitApplySchema.setDispIdea(request.getParameter("DispIdea")); //处理意见
    //tLLSubmitApplySchema.setSubState(request.getParameter("SubState")); //呈报状态
    
    //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo")); //赔案号
    mTransferData.setNameAndValue("SubNo",request.getParameter("SubNO")); //呈报序号
    mTransferData.setNameAndValue("SubCount",request.getParameter("SubCount")); //呈报次数
    mTransferData.setNameAndValue("CustomerNo",request.getParameter("CustomerNo")); //出险人客户号
    mTransferData.setNameAndValue("CustomerName",request.getParameter("CustomerName")); //出险人名称
    mTransferData.setNameAndValue("SubType","2"); //呈报类型（2--从分公司到总公司）
    mTransferData.setNameAndValue("SubRCode",request.getParameter("SubRCode")); //呈报原因
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
    mTransferData.setNameAndValue("DispType","1");
    mTransferData.setNameAndValue("DefaultOperator",Operator);
    mTransferData.setNameAndValue("transact",transact);
    try
    {
    	//准备传输数据 VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(isReportExist);    
        tVData.add(transact);          
        tVData.add(tLLSubmitApplySchema);
        tVData.add(mTransferData);
        loggerDebug("LLSubmitApplyHeadDealSave","---tLLSubmitApplyHeadDealUI submitData and transact="+transact);
//    	if (!tLLSubmitApplyHeadDealUI.submitData(tVData,transact))
//        {
//    		Content = " 数据提交失败，原因是: " + tLLSubmitApplyHeadDealUI.mErrors.getError(0).errorMessage;
//        	FlagStr = "Fail";
//        }
		//if(!tBusinessDelegate.submitData(tVData,transact,busiName))
	if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
		{    
		    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		    { 
				Content = "数据提交失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "数据提交失败";
				FlagStr = "Fail";				
			}
		}

        else
       {
        	tVData.clear();
    	 	//tVData = tLLSubmitApplyHeadDealUI.getResult();
    	 	tVData = tBusinessDelegate.getResult();
    	 	Content = "回复意见数据提交成功";
            FlagStr = "Succ";     
       }
    }
    catch(Exception ex)
    {
    	Content = "提交失败，原因是:" + ex.toString();
        FlagStr = "Fail";
     }
    //如果在Catch中发现异常，则不从错误类中提取错误信息
    loggerDebug("LLSubmitApplyHeadDealSave","------LLSubmitApplyHeadDealSave.jsp end------");
    loggerDebug("LLSubmitApplyHeadDealSave",FlagStr);
    loggerDebug("LLSubmitApplyHeadDealSave",Content);
}	
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
