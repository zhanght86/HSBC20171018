<%
//**************************************************************************************************
//Name：LLClaimAuditConclusionSave.jsp
//Function：审核结论提交
//Author：zhoulei
//Date: 2005-7-2 11:37
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
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
String AuditConclusion = "";
//页面有效性判断
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLClaimAuditConclusionSave","页面失效,请重新登陆");
}
else
{
    //获取操作符
    String transact = request.getParameter("fmtransact");
    String mRptNo   = request.getParameter("RptNo");
    AuditConclusion = request.getParameter("AuditConclusion");
    
    //获取报案页面信息
    tLLClaimUWMainSchema.setClmNo(request.getParameter("RptNo")); //赔案号
    tLLClaimUWMainSchema.setCaseNo(request.getParameter("RptNo")); //分案号
    tLLClaimUWMainSchema.setRgtNo(request.getParameter("RptNo")); //报案号
    tLLClaimUWMainSchema.setAuditConclusion(request.getParameter("AuditConclusion")); //审核结论
    tLLClaimUWMainSchema.setAuditIdea(request.getParameter("AuditIdea")); //审核意见
    tLLClaimUWMainSchema.setSpecialRemark(request.getParameter("SpecialRemark1")); //特殊备注
    tLLClaimUWMainSchema.setAuditNoPassReason(request.getParameter("ProtestReason")); //审核不通过原因
    tLLClaimUWMainSchema.setAuditNoPassDesc(request.getParameter("ProtestReasonDesc")); //审核不通过依据

    //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("RptNo", request.getParameter("RptNo"));
    mTransferData.setNameAndValue("RptorState", "40");
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("customerNo"));
    mTransferData.setNameAndValue("CustomerName", request.getParameter("customerName"));
    mTransferData.setNameAndValue("CustomerSex", request.getParameter("customerSex"));
    mTransferData.setNameAndValue("AccDate", request.getParameter("AccidentDate2"));
    mTransferData.setNameAndValue("RgtClass", "1");  //申请类型
    mTransferData.setNameAndValue("RgtObj", "1");  //号码类型
    mTransferData.setNameAndValue("RgtObjNo", request.getParameter("RptNo"));  //其他号码
    mTransferData.setNameAndValue("MngCom", tGI.ManageCom);  //机构
    mTransferData.setNameAndValue("PrepayFlag", request.getParameter("BudgetFlag"));
    mTransferData.setNameAndValue("AuditPopedom", request.getParameter("AuditPopedom")); //审核权限
    mTransferData.setNameAndValue("Auditer", request.getParameter("Operator")); //审核人
    //转移条件参数
    mTransferData.setNameAndValue("BudgetFlag", "0");  //是否预付
    mTransferData.setNameAndValue("IsRunBL", "1");  //是否运行BL
    mTransferData.setNameAndValue("PopedomPhase","B"); //权限阶段标示A:审核B:审批
    //
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(tLLClaimUWMainSchema);
    tVData.add(mTransferData);

    try
    {
        //提交数据
        loggerDebug("LLClaimAuditConclusionSave","-------------------start LLClaimAuditConclusionSave.jsp---------------------");
//        LLClaimAuditUI tLLClaimAuditUI = new LLClaimAuditUI();
//        if(!tLLClaimAuditUI.submitData(tVData,transact))
//        {
//            Content = " 数据提交LLClaimAuditUI失败，原因是: " + tLLClaimAuditUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
String busiName="grpLLClaimAuditUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,transact,busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "数据提交LLClaimAuditUI失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "数据提交LLClaimAuditUI失败";
		FlagStr = "Fail";				
	}
}

        else
        {
            Content = " 数据提交成功！";
            FlagStr = "Succ";
            
            loggerDebug("LLClaimAuditConclusionSave","========加入再保险处理     sujd   2007-12-17=====");
            
            if (AuditConclusion.equals("0"))
            {
            	//tongmeng 2009-01-06 modify
            	//去掉再保处理
            	/*
                 //加入再保险处理     sujd   2007-12-17
                 ReLifeNeed tReLifeNeed=new ReLifeNeed();
                 VData tmpvdata=new VData();
                 
                 TransferData tmpTransferData = new TransferData();
                 tmpTransferData.setNameAndValue("ManageCom",tGI.ManageCom);
                 tmpTransferData.setNameAndValue("Operator",tGI.Operator);
                 tmpTransferData.setNameAndValue("OtherNo",request.getParameter("RptNo"));
                 
                 tmpvdata.add(tmpTransferData);
                 
                 if (!tReLifeNeed.submitdata(tmpvdata,"2"))
                 {
                      Content=Content+";再保险处理失败,请联系信息技术部人员(案件需要结案)!";
                      FlagStr = "Fail";
                 }
                 else
                 {
                     Content=Content+";再保险处理成功!";           
                 }
                 loggerDebug("LLClaimAuditConclusionSave","==================再保处理结束=================");
                 */
            }           
            
        }
           /*解锁，防止中间点2次结案按钮*/             
		         MMap tempMMap=new MMap();
		         ExeSQL ttExeSQL=new ExeSQL();
		         SSRS tSSRS=new SSRS();
		         String ttsql="select getdutykind from llclaim where clmno='"+mRptNo+"' and getdutykind is not null ";
		         tSSRS=ttExeSQL.execSQL(ttsql);
		         if(tSSRS.MaxRow>0 && tSSRS.GetText(1,1).equals("1"))
		         {
		           tempMMap.put("update llclaim set getdutykind='' where clmno='"+mRptNo+"' ","UPDATE");
		           PubSubmit tempSubmit = new PubSubmit();
               VData temVData = new VData();
               temVData.add(tempMMap);
               if (!tempSubmit.submitData(temVData, null)) 
               {
                 CError.buildErr(this, "理赔解锁失败！");                
               }
		         }          
        
        loggerDebug("LLClaimAuditConclusionSave","-------------------end LLClaimAuditConclusionSave.jsp---------------------");
    }
    catch(Exception ex)
    {
        Content = "数据提交LLClaimAuditUI失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit3("<%=FlagStr%>","<%=Content%>","<%=AuditConclusion%>");
</script>
</html>

