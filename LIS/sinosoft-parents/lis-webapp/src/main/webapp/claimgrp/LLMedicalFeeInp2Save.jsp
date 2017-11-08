<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLMedicalFeeInp2Save.jsp
//程序功能：住院信息保存
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
    loggerDebug("LLMedicalFeeInp2Save","页面失效,请重新登陆");
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
    tLLFeeMainSchema.setMainFeeNo(request.getParameter("HosMainFeeNo"));
    tLLFeeMainSchema.setCustomerNo(request.getParameter("custNo"));
    tLLFeeMainSchema.setHospitalCode(request.getParameter("InHosHosID"));
    tLLFeeMainSchema.setFeeType("B");    //住院
            
    tLLCaseReceiptSchema.setCustomerNo(request.getParameter("custNo"));
    tLLCaseReceiptSchema.setClmNo(request.getParameter("claimNo"));
    tLLCaseReceiptSchema.setCaseNo(request.getParameter("caseNo"));
    tLLCaseReceiptSchema.setMainFeeNo(request.getParameter("HosMainFeeNo"));
    tLLCaseReceiptSchema.setFeeDetailNo(request.getParameter("feeDetailNo"));
    tLLCaseReceiptSchema.setRgtNo(request.getParameter("claimNo"));
    tLLCaseReceiptSchema.setFeeItemType("B");  //住院
    tLLCaseReceiptSchema.setFeeItemCode(request.getParameter("InHosMedFeeType"));
    tLLCaseReceiptSchema.setFeeItemName(request.getParameter("InHosMedFeeTypeName"));
    tLLCaseReceiptSchema.setFee(request.getParameter("OriginFee1"));
    tLLCaseReceiptSchema.setStartDate(request.getParameter("InHosStartDate"));
    tLLCaseReceiptSchema.setEndDate(request.getParameter("InHosEndDate"));
    tLLCaseReceiptSchema.setDayCount(request.getParameter("InHosDayCount1"));
    tLLCaseReceiptSchema.setDealFlag(request.getParameter("DealFlag"));   //开始日期是否早于出险日期,0是1不是
    tLLCaseReceiptSchema.setAdjRemark(request.getParameter("MinusReasonName1"));  //扣除原因
    tLLCaseReceiptSchema.setAdjReason(request.getParameter("MinusReason1"));  //扣除代码
    //yaory
    tLLCaseReceiptSchema.setAdjSum(request.getParameter("InHosMedFeeSum1"));
    loggerDebug("LLMedicalFeeInp2Save","***********************************");
    loggerDebug("LLMedicalFeeInp2Save",request.getParameter("InHosMedFeeSum1"));
    loggerDebug("LLMedicalFeeInp2Save",request.getParameter("SocietyFee1"));
    loggerDebug("LLMedicalFeeInp2Save",request.getParameter("MixFee1"));
    loggerDebug("LLMedicalFeeInp2Save",request.getParameter("MinusFee1"));
    tLLCaseReceiptSchema.setSecurityAmnt(request.getParameter("SocietyFee1"));
    tLLCaseReceiptSchema.setCutApartAmnt(request.getParameter("MixFee1"));
    tLLCaseReceiptSchema.setRefuseAmnt(request.getParameter("MinusFee1"));
    //住院起付线
    tLLCaseReceiptSchema.setHospLineAmnt(request.getParameter("HospLineAmnt"));
//    loggerDebug("LLMedicalFeeInp2Save","*******************");
//    loggerDebug("LLMedicalFeeInp2Save","HospLineAmnt:"+request.getParameter("HospLineAmnt"));
//    loggerDebug("LLMedicalFeeInp2Save","*******************");
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
	            //loggerDebug("LLMedicalFeeInp2Save","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
	            Content = Content + "信息提交保存，原因是: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
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
