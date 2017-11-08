<%
//**************************************************************************************************
//Name：LLCaseBackFeeSave.jsp
//Function：案件回退暂交费回销提交
//Author：zl
//Date: 2005-10-21 14:33
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
    //准备通用参数
    CErrors tError = null;
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");
    if(tG == null)
    {
        loggerDebug("LLCaseBackFeeSave","登录信息没有获取!");
        return;
    }

    //准备数据容器信息
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo", request.getParameter("ClmNo"));

    try
    {
       //准备传输数据 VData
       VData tVData = new VData();
       tVData.add(tG);
       tVData.add(mTransferData);
       
//       LLTempFeeConfUI tLLTempFeeConfUI = new LLTempFeeConfUI();
//       if (!tLLTempFeeConfUI.submitData(tVData,""))
//       {
//            Content = "数据提交失败，原因是: " + tLLTempFeeConfUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//       }
		String busiName="LLTempFeeConfUI";
		 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		   if(!tBusinessDelegate.submitData(tVData,"",busiName))
		   {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
						   Content = "数据提交失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
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
            Content = "保存成功! ";
            FlagStr = "SUCC";
       }
    }
    catch(Exception e)
    {
        e.printStackTrace();
        Content = Content.trim()+".提示：异常终止!";
    }

%>

<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
