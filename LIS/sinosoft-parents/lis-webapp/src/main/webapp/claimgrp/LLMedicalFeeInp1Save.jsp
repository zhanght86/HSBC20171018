<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLMedicalFeeInp1Save.jsp
//程序功能：门诊信息保存
//创建日期：2005-05-19 11:10:36
//创建人  ：续涛
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
//准备通用参数
CErrors tError = null;
String FlagStr = "FAIL";
String Content = "";
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

//页面有效性
if(tG == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLMedicalFeeInp1Save","页面失效,请重新登陆");
}
else
{
    String tOperate = request.getParameter("hideOperate");
    
    //准备数据容器信息
    LLFeeMainSchema tLLFeeMainSchema = new LLFeeMainSchema();
    LLCaseReceiptSchema tLLCaseReceiptSchema = new LLCaseReceiptSchema();

    //准备后台数据
    tLLFeeMainSchema.setClmNo(request.getParameter("claimNo"));
    tLLFeeMainSchema.setCaseNo(request.getParameter("caseNo"));
    tLLFeeMainSchema.setMainFeeNo(request.getParameter("ClinicMainFeeNo"));
    tLLFeeMainSchema.setCustomerNo(request.getParameter("custNo"));
    tLLFeeMainSchema.setHospitalCode(request.getParameter("ClinicHosID"));
    tLLFeeMainSchema.setFeeType("A");    //门诊
            
    tLLCaseReceiptSchema.setCustomerNo(request.getParameter("custNo"));
    tLLCaseReceiptSchema.setClmNo(request.getParameter("claimNo"));
    tLLCaseReceiptSchema.setCaseNo(request.getParameter("caseNo"));
    tLLCaseReceiptSchema.setMainFeeNo(request.getParameter("ClinicMainFeeNo"));
    tLLCaseReceiptSchema.setFeeDetailNo(request.getParameter("feeDetailNo"));
    tLLCaseReceiptSchema.setRgtNo(request.getParameter("claimNo"));
    tLLCaseReceiptSchema.setFeeItemType("A");    //门诊
    tLLCaseReceiptSchema.setFeeItemCode(request.getParameter("ClinicMedFeeType"));   //费用类型
    tLLCaseReceiptSchema.setFeeItemName(request.getParameter("ClinicMedFeeTypeName"));  //费用类型name
    tLLCaseReceiptSchema.setFee(request.getParameter("OriginFee"));  //费用金额
    tLLCaseReceiptSchema.setStartDate(request.getParameter("ClinicStartDate"));   //开始日期
    tLLCaseReceiptSchema.setEndDate(request.getParameter("ClinicStartDate"));   //结束日期
//    tLLCaseReceiptSchema.setEndDate(request.getParameter("ClinicEndDate"));   //结束日期
    tLLCaseReceiptSchema.setDayCount(request.getParameter("ClinicDayCount1"));   //天数
    tLLCaseReceiptSchema.setDealFlag(request.getParameter("DealFlag"));   //开始日期是否早于出险日期,0是1不是
    tLLCaseReceiptSchema.setAdjRemark(request.getParameter("MinusReasonName"));  //扣除原因
    tLLCaseReceiptSchema.setAdjReason(request.getParameter("MinusReason"));  //扣除代码
    //yaory add
    tLLCaseReceiptSchema.setAdjSum(request.getParameter("ClinicMedFeeSum"));
    loggerDebug("LLMedicalFeeInp1Save","***********************************");
    loggerDebug("LLMedicalFeeInp1Save",request.getParameter("ClinicMedFeeSum"));
    loggerDebug("LLMedicalFeeInp1Save",request.getParameter("SocietyFee"));
    loggerDebug("LLMedicalFeeInp1Save",request.getParameter("MinusReasonName"));
    loggerDebug("LLMedicalFeeInp1Save",request.getParameter("MinusReason"));
    tLLCaseReceiptSchema.setSecurityAmnt(request.getParameter("SocietyFee"));
    tLLCaseReceiptSchema.setCutApartAmnt(request.getParameter("MixFee"));
    tLLCaseReceiptSchema.setRefuseAmnt(request.getParameter("MinusFee"));
    try
    {
        // 准备传输数据 VData
        VData tVData = new VData();
        tVData.add( tG );
        tVData.add( tLLFeeMainSchema );
        tVData.add( tLLCaseReceiptSchema );

//        LLMedicalFeeInp1UI tLLMedicalFeeInp1UI = new LLMedicalFeeInp1UI();
//        if (tLLMedicalFeeInp1UI.submitData(tVData,tOperate) == false)
//        {
//            int n = tLLMedicalFeeInp1UI.mErrors.getErrorCount();
//            for (int i = 0; i < n; i++)
//            {
//                Content = Content + "信息提交保存，原因是: " + tLLMedicalFeeInp1UI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//            }
//        }
		String busiName="grpLLMedicalFeeInp1UI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
		    {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
		        	int n = tBusinessDelegate.getCErrors().getErrorCount();
			        for (int i = 0; i < n; i++)
			        {
			            //loggerDebug("LLMedicalFeeInp1Save","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			            Content =  Content + "信息提交保存，原因是: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
			        }
		       		FlagStr = "Fail";				   
				}
				else
				{
				   Content = "信息提交保存失败";
				   FlagStr = "Fail";				
				} 
		}

        else
        {
            Content = " 保存成功! ";
            FlagStr = "SUCC";
        }
    }
    catch(Exception ex)
    {
        Content = "数据提交失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
