<%
//Name：LLInqApplySave.jsp
//Function：发起调查提交
//Author：zhoulei
//Date:
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page import="com.sinosoft.workflowengine.*"%>
<%
//************************
//接收信息，并作校验处理
//************************

//输入参数
LLInqApplySchema tLLInqApplySchema = new LLInqApplySchema(); //调查申请

//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI"); 
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //理赔工作流引擎 
//LLInqApplyUI tLLInqApplyUI = new LLInqApplyUI();
//String busiName="ClaimWorkFlowUI";
//BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//String busiName1="LLInqApplyUI";
//BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
WorkFlowUI tWorkFlowUI = new WorkFlowUI();//lzf
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLInqApplySave","页面失效,请重新登陆");    
}
else //页面有效
{
    String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码

    String transact = request.getParameter("fmtransact"); //获取操作insert||update
    loggerDebug("LLInqApplySave","-----transact= "+transact);
    String wFlag = request.getParameter("WorkFlowFlag");
    String tActivityID = new ExeSQL().getOneValue("select activityid from lwactivity where functionid ='10060001'");
    loggerDebug("LLInqApplySave","-----tActivityID= "+tActivityID);
    //***************************************
    //获取页面信息 
    //***************************************   
    tLLInqApplySchema.setClmNo(request.getParameter("ClmNo")); //赔案号
    tLLInqApplySchema.setInqNo(request.getParameter("InqNo")); //调查序号
    tLLInqApplySchema.setBatNo(request.getParameter("BatNo")); //调查批次号
    tLLInqApplySchema.setCustomerNo(request.getParameter("CustomerNo")); //出险人客户号
    tLLInqApplySchema.setCustomerName(request.getParameter("CustomerName")); //出险人名称
    tLLInqApplySchema.setVIPFlag(request.getParameter("VIPFlag")); //VIP客户标志
    tLLInqApplySchema.setInitPhase(request.getParameter("InitPhase")); //提起阶段
    tLLInqApplySchema.setInqRCode(request.getParameter("InqReason")); //调查原因
    tLLInqApplySchema.setInqItem(request.getParameter("InqItem")); //调查项目
    tLLInqApplySchema.setInqDesc(request.getParameter("InqDesc")); //调查描述
    tLLInqApplySchema.setInqDept(request.getParameter("InqOrg2")); //调查机构
    tLLInqApplySchema.setLocFlag(request.getParameter("LocFlag")); //本地标志
    
    //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo")); //赔案号
    mTransferData.setNameAndValue("InqNo",request.getParameter("InqNo")); //调查序号
    mTransferData.setNameAndValue("BatNo",request.getParameter("BatNo")); //调查批次号    
    mTransferData.setNameAndValue("CustomerNo",request.getParameter("CustomerNo")); //出险人客户号
    mTransferData.setNameAndValue("CustomerName",request.getParameter("CustomerName")); //出险人名称
    mTransferData.setNameAndValue("InitPhase",request.getParameter("InitPhase")); //提起阶段
    mTransferData.setNameAndValue("InqRCode",request.getParameter("InqReason")); //调查原因
    mTransferData.setNameAndValue("InqDept",request.getParameter("InqOrg2")); //调查机构  
	mTransferData.setNameAndValue("MngCom", request.getParameter("InqOrg2"));  //机构  -------显示为 “调查机构”      
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("CreateOperator",Operator);
    mTransferData.setNameAndValue("ActivityID",tActivityID);
    mTransferData.setNameAndValue("BusiType","1006");
    try
    {
        //准备传输数据 VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(transact);
        tVData.add(mTransferData);
        tVData.add(tLLInqApplySchema);
    	//wFlag = "8899999999"; 
        try
        {
           loggerDebug("LLInqApplySave","---WorkFlowUI submitData and transact="+transact);
           if(!tWorkFlowUI.submitData(tVData, "create")){
        	   Content = "提交tLLInqApplyUI失败，原因是: " + tWorkFlowUI.mErrors.getError(0).errorMessage;
               FlagStr = "Fail";
           }else{
        	 //从结果集中取出前台需要数据
               tVData.clear();
               tVData = tWorkFlowUI.getResult();
               loggerDebug("LLInqApplySave","tVData="+tVData);	    
               Content = "数据提交成功";
               FlagStr = "Succ"; 
           }
//            if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//            {           
//                Content = "提交工作流ClaimWorkFlowUI失败，原因是: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//    
//            }
/*			if(!tBusinessDelegate.submitData(tVData,wFlag,busiName))
			{    
			    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			    { 
					Content = "提交LLInqApplyUI失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "提交LLInqApplyUI失败";
					FlagStr = "Fail";				
				}
			}

            else
        	{
        		//从结果集中取出前台需要数据
                tVData.clear();
               // tVData = tClaimWorkFlowUI.getResult();
                tVData = tBusinessDelegate.getResult();
                loggerDebug("LLInqApplySave","tVData="+tVData);	    
                Content = "数据提交成功";
                FlagStr = "Succ";            
        	}
*/
    	}
    	catch(Exception ex)
        {
        	Content = "数据提交LLInqApplyUI失败，原因是:" + ex.toString();
        	FlagStr = "Fail";
        }	
    }    
    catch(Exception ex)
    {
        Content = "数据提交LLInqApplyUI失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }
    
    loggerDebug("LLInqApplySave","------LLInqApplySave.jsp end------");
    loggerDebug("LLInqApplySave",FlagStr);
    loggerDebug("LLInqApplySave",Content);
}    
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
