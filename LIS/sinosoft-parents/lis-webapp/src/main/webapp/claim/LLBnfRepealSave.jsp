<%
//**************************************************************************************************
//Name：LLBnfRepealSave.jsp
//Function：受益人撤销提交
//Author：zhangzheng
//Date: 2009-03-24 
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI");  
LLBnfSet mLLBnfSet = new LLBnfSet();

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    loggerDebug("LLBnfRepealSave","页面失效,请重新登陆");    
}
else //页面有效
{

    //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo", request.getParameter("ClmNo"));
    mTransferData.setNameAndValue("BnfKind", request.getParameter("BnfKind"));
    mTransferData.setNameAndValue("CustomerNo", request.getParameter("insuredno"));
    
    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);

    try
    {
//        LLBnfRepealBL tLLBnfRepealBL = new LLBnfRepealBL();
//        //数据提交
//        if (!tLLBnfRepealBL.submitData(tVData,"REPEAL"))
//        {
//            Content = " 撤销处理数据失败，原因是: " + tLLBnfRepealBL.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
String busiName="LLBnfRepealBL";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,"REPEAL",busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "撤销处理数据失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "撤销处理数据失败";
		FlagStr = "Fail";				
	}
}

        else
        {
            tVData.clear();
            Content = " 数据提交成功！";
            FlagStr = "Succ";
        }
    }
    catch(Exception ex)
    {
        Content = "数据提交撤销处理失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }

}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");
</script>
</html>
