<%
//**************************************************************************************************
//Name：LLBnfToPensionSave.jsp
//Function：受益人提交
//Author：zhoulei
//Date: 2006-3-3 14:33
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
    loggerDebug("LLBnfToPensionSave","页面失效,请重新登陆");    
}
else //页面有效
{
    //获取主要信息
    String tClmNo = request.getParameter("ClmNo");
    String tPolNo = request.getParameter("polNo");
    String tBnfno = request.getParameter("bnfno");

    //String使用TransferData打包后提交
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo", tClmNo);
    mTransferData.setNameAndValue("PolNo", tPolNo);
    mTransferData.setNameAndValue("bnfno", tBnfno);
    
    //获取转年金方式
    LCGetSchema tLCGetSchema = new LCGetSchema();
    tLCGetSchema.setGetDutyCode(request.getParameter("PensionType"));
    
    //准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(mTransferData);
    tVData.add(tLCGetSchema);

    try
    {
        //LLClmToPensionBL tLLClmToPensionBL = new LLClmToPensionBL();
//        //数据提交
//        if (!tLLClmToPensionBL.submitData(tVData,""))
//        {
//            Content = " 处理数据失败，原因是: " + tLLClmToPensionBL.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//        }
String busiName="LLClmToPensionBL";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,"",busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "处理数据失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "处理数据失败";
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
