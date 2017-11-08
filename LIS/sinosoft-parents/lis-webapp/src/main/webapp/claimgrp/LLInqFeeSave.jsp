<%
//Name：LLInqFeeSave.jsp
//Function：调查费用录入提交
//Author：yuejinwei
//Date:
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>

<%
//************************
//接收信息，并作校验处理
//************************

//输入参数
LLInqFeeSchema tLLInqFeeSchema = new LLInqFeeSchema(); //案件核赔表
//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI");  
LLInqFeeUI tLLInqFeeUI = new LLInqFeeUI();

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLInqFeeSave","页面失效,请重新登陆");    
}
else //页面有效
{
	String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码
    loggerDebug("LLInqFeeSave","----ComCode= "+tGI.ComCode);
    loggerDebug("LLInqFeeSave","-----ManageCom= "+ManageCom);

    String transact = request.getParameter("fmtransact"); //获取操作insert||update
    loggerDebug("LLInqFeeSave","-----transact= "+transact);
    String isReportExist =request.getParameter("isReportExist"); //是否为新增事件，是true，否false
    //***************************************
    //需要根据isReportExist判断是否为新增事件\修改事件
    //***************************************
    //获取费用录入页面信息
        loggerDebug("LLInqFeeSave","------@@@@@@@@@@@@@@------"+request.getParameter("InqDept"));

    tLLInqFeeSchema.setClmNo(request.getParameter("ClmNo")); //赔案号
    tLLInqFeeSchema.setInqNo(request.getParameter("InqNo")); //调查序号
    tLLInqFeeSchema.setInqDept(request.getParameter("InqDept")); //调查机构
    tLLInqFeeSchema.setFeeType(request.getParameter("FeeType")); //费用类型
    tLLInqFeeSchema.setFeeDate(request.getParameter("FeeDate")); //发生时间
    tLLInqFeeSchema.setFeeSum(request.getParameter("FeeSum")); //金额
    tLLInqFeeSchema.setPayee(request.getParameter("Payee")); //领款人
    tLLInqFeeSchema.setPayeeType(request.getParameter("PayeeType")); //领款方式
    tLLInqFeeSchema.setRemark(request.getParameter("Remark")); //备注
      
    try
    {
        //准备传输数据 VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(isReportExist);
        tVData.add(tLLInqFeeSchema);    
        //数据传输
	      if (!tLLInqFeeUI.submitData(tVData,transact))
	      {
            Content = " 数据提交LLInqFeeUI失败，原因是: " + tLLInqFeeUI.mErrors.getError(0).errorMessage;
            FlagStr = "Fail";
	      }
	      else
	      {
	    	tVData.clear();
	    	tVData = tLLInqFeeUI.getResult();
        }
    }
    catch(Exception ex)
    {
        Content = "数据提交LLInqFeeUI失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }
    
    //如果在Catch中发现异常，则不从错误类中提取错误信息
    if (FlagStr == "Fail")
    {
        tError = tLLInqFeeUI.mErrors;
        if (!tError.needDealError())
        {
          	Content = " 数据提交成功! ";
    	      FlagStr = "Succ";
        }
        else
        {
      	    Content = " 数据提交LLInqFeeUI失败，原因是:" + tError.getFirstError();
    	      FlagStr = "Fail";
        }
    }    
    loggerDebug("LLInqFeeSave","------LLInqFeeSave.jsp end------");
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
