<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LLMedicalFeeInp4Save.jsp
//程序功能：手术和特疾信息保存
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
    loggerDebug("LLMedicalFeeInp4Save","页面失效,请重新登陆");
}
else
{
    String tOperate = request.getParameter("hideOperate");
    
    //准备数据容器信息
    LLOperationSchema tLLOperationSchema = new LLOperationSchema();

    //准备后台数据
    tLLOperationSchema.setClmNo(request.getParameter("claimNo"));
    tLLOperationSchema.setCaseNo(request.getParameter("caseNo"));
    tLLOperationSchema.setSerialNo(request.getParameter("SerialNo4"));
    tLLOperationSchema.setCustomerNo(request.getParameter("custNo"));
    
    tLLOperationSchema.setOperationType(request.getParameter("OperationType"));
    tLLOperationSchema.setOperationCode(request.getParameter("OperationCode"));
    tLLOperationSchema.setOperationName(request.getParameter("OperationName"));
    tLLOperationSchema.setOpLevel(request.getParameter("OpLevel"));
    tLLOperationSchema.setOpGrade(request.getParameter("OpGrade"));
    tLLOperationSchema.setOpFee(request.getParameter("OpFee"));
    tLLOperationSchema.setMainOp(request.getParameter("MainOp"));
    tLLOperationSchema.setAdjSum(request.getParameter("OpFee"));   //调整金额设为初始金额
    tLLOperationSchema.setUnitNo("0");     //机构代码置0
    tLLOperationSchema.setUnitName(request.getParameter("UnitName"));  //机构名称
    tLLOperationSchema.setDiagnoseDate(request.getParameter("DiagnoseDate"));  //确认日期

    try
    {
        // 准备传输数据 VData
        VData tVData = new VData();
        tVData.add( tG );
        tVData.add( tLLOperationSchema );

//        LLMedicalFeeInp4UI tLLMedicalFeeInp4UI = new LLMedicalFeeInp4UI();
//        if (tLLMedicalFeeInp4UI.submitData(tVData,tOperate) == false)
//        {
//            int n = tLLMedicalFeeInp4UI.mErrors.getErrorCount();
//            for (int i = 0; i < n; i++)
//            {
//                Content = Content + "信息提交保存，原因是: " + tLLMedicalFeeInp4UI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//            }
//        }
String busiName="grpLLMedicalFeeInp4UI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
        	int n = tBusinessDelegate.getCErrors().getErrorCount();
	        for (int i = 0; i < n; i++)
	        {
	            //loggerDebug("LLMedicalFeeInp4Save","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
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
