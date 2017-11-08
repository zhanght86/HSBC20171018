<%
//Name：LLClaimDescSave.jsp
//Function：事故描述提交
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
<%@page import="com.sinosoft.service.*" %>

<%
//************************
//接收信息，并作校验处理
//************************

//输入参数
LLClaimDescSchema tLLClaimDescSchema = new LLClaimDescSchema(); //事故描述

//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI");  
//LLClaimDescUI tLLClaimDescUI = new LLClaimDescUI();
String busiName="LLClaimDescUI";
 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLClaimDescSave","页面失效,请重新登陆");    
}
else //页面有效
{
    String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码

    String transact = request.getParameter("fmtransact"); //获取操作insert||update
    loggerDebug("LLClaimDescSave","-----transact= "+transact);
    
    //***************************************
    //获取页面信息 
    //***************************************
    tLLClaimDescSchema.setClmNo(request.getParameter("ClmNo")); //赔案号
//    tLLClaimDescSchema.setInqNo(request.getParameter("RptorName")); //事故描述序号
    tLLClaimDescSchema.setCustomerNo(request.getParameter("WhoNo")); //出险人客户号
    tLLClaimDescSchema.setStartPhase(request.getParameter("StartPhase")); //提起阶段    
//    tLLClaimDescSchema.setDescType(request.getParameter("DescType")); //事故描述类型
    tLLClaimDescSchema.setDescribe(request.getParameter("Describe")); //事故描述内容
            
    try
    {
        //准备传输数据 VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(tLLClaimDescSchema);

        //数据传输
//         if (!tLLClaimDescUI.submitData(tVData,transact))
//	      {
//            Content = " 数据提交LLClaimDescUI失败，原因是: " + tLLClaimDescUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//	      }
		 if(!tBusinessDelegate.submitData(tVData,transact,busiName))
		   {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
						   Content = "数据提交LLClaimDescUI失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
						   FlagStr = "Fail";
				}
				else
				{
						   Content = "数据提交LLClaimDescUI失败";
						   FlagStr = "Fail";				
				}
		   }


	      else
	      {
	    	tVData.clear();
	    	//tVData = tLLClaimDescUI.getResult();
	    	tVData = tBusinessDelegate.getResult();
//            LLClaimDescSet yLLClaimDescSet = new LLClaimDescSet();
//            yLLClaimDescSet.set((LLClaimDescSet)tVData.getObjectByObjectName("LLClaimDescSet",0));
//        int n = yLLClaimDescSet.size();
//        loggerDebug("LLClaimDescSave","get report "+n);
//    		String Strtest ="0|" + n + "^"+yLLClaimDescSet.encode();
//    		loggerDebug("LLClaimDescSave","Strtest==="+Strtest);
        }
    }
    catch(Exception ex)
    {
        Content = "数据提交LLClaimDescUI失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }
    
    //如果在Catch中发现异常，则不从错误类中提取错误信息
    if (FlagStr == "Fail")
    {
        //tError = tLLClaimDescUI.mErrors;
        tError = tBusinessDelegate.getCErrors();
        if (!tError.needDealError())
        {
          	Content = " 数据提交成功! ";
    	      FlagStr = "Succ";
        }
        else
        {
      	    Content = " 数据提交LLClaimDescUI失败，原因是:" + tError.getFirstError();
    	      FlagStr = "Fail";
        }
    }    
    loggerDebug("LLClaimDescSave","------LLClaimDescSave.jsp end------");
    loggerDebug("LLClaimDescSave",FlagStr);
    loggerDebug("LLClaimDescSave",Content);
    
} //end of else,页面有效区
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
