<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLInqConfirmSave.jsp
//程序功能：用于提交调查结论信息、消亡该节点；然后判断是否可以生成下一个节点
//         判断条件：查询 调查状态==“为完成” and 调查序号<>本次序号 and 调查机构==本次机构 and 赔案号==本次赔案号，如存在记录则不生成下一节点
//创建日期：2005-6-22
//更新记录：  更新人:yuejw    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
//接收信息，并作校验处理
//输入参数
LLInqApplySchema tLLInqApplySchema = new LLInqApplySchema(); //调查申请
//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI");  
//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //理赔工作流引擎
//String busiName="ClaimWorkFlowUI";
String busiName="WorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLInqConfirmSave","页面失效,请重新登陆");    
}
else //页面有效
{
    String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码
    String transact = request.getParameter("fmtransact"); //获取操作
    loggerDebug("LLInqConfirmSave","-----transact= "+transact);
    //工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
    String wFlag = request.getParameter("WorkFlowFlag");
    //***************************************
    //获取页面信息，表中字段用schema打包，用于修改记录
     tLLInqApplySchema.setClmNo(request.getParameter("ClmNo")); //赔案号
     tLLInqApplySchema.setInqNo(request.getParameter("InqNo")); //调查序号
     tLLInqApplySchema.setBatNo(request.getParameter("BatNo")); //调查批次         
     tLLInqApplySchema.setInqState(request.getParameter("InqState")); //调查状态
     tLLInqApplySchema.setInqConclusion(request.getParameter("InqConclusion")); //调查结论
        
    //String等零散数据使用TransferData打包，用于准备 新建下一节点（调查结论）
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo")); //赔案号
    mTransferData.setNameAndValue("ConNo",request.getParameter("ConNo")); //结论序号
    mTransferData.setNameAndValue("BatNo",request.getParameter("BatNo")); //调查批次
    mTransferData.setNameAndValue("InitDept",request.getParameter("InitDept")); //发起机构
    mTransferData.setNameAndValue("InqDept",request.getParameter("InqDept")); //调查机构 
    mTransferData.setNameAndValue("ColFlag","2"); //汇总标志   
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("ActivityID",request.getParameter("Activityid"));    
    mTransferData.setNameAndValue("InqNo",request.getParameter("InqNo")); //调查序号  
	mTransferData.setNameAndValue("MngCom", request.getParameter("ManageCom"));  //机构      
    mTransferData.setNameAndValue("transact",request.getParameter("fmtransact"));
                        
    try
    {
        //准备传输数据 VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(mTransferData);
        tVData.add(tLLInqApplySchema);       
    	//wFlag = request.getParameter("Activityid"); //标识（自定义）
        try
        {
            loggerDebug("LLInqConfirmSave","---ClaimWorkFlowUI submitData and transact="+transact);
//            if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//            {
//                Content = " 提交工作流ClaimWorkFlowUI失败，原因是: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
			if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
			{    
			    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			    { 
					Content = "提交工作流WorkFlowUI失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "提交工作流WorkFlowUI失败";
					FlagStr = "Fail";				
				}
			}

            else
        	{
        		//从结果集中取出前台需要数据
                tVData.clear();
               // tVData = tClaimWorkFlowUI.getResult();
                tVData = tBusinessDelegate.getResult();
                loggerDebug("LLInqConfirmSave","tVData="+tVData);	    
                Content = "数据提交成功";
                FlagStr = "Succ";            
        	}
    	}
    	catch(Exception ex)
        {
        	Content = "数据提交WorkFlowUI失败，原因是:" + ex.toString();
        	FlagStr = "Fail";
        }	
    }  
    catch(Exception ex)
    {
        Content = "数据提交WorkFlowUI失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }
    
    loggerDebug("LLInqConfirmSave","------LLInqConfirmSave.jsp end------");
    loggerDebug("LLInqConfirmSave",FlagStr);
    loggerDebug("LLInqConfirmSave",Content);
    
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterComfirmSubmit("<%=FlagStr%>","<%=Content%>");
</script>  
