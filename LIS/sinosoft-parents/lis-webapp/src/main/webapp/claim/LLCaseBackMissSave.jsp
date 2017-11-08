<%
//**************************************************************************************************
//程序名称：LLCaseBackMissSave.jsp
//程序功能：
//创建日期：2005-10-20 17:29
//创建人  ：wanzh
//更新记录：
//**************************************************************************************************
%>

<!--用户校验类-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>

<%

//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tG = new GlobalInput();
tG = (GlobalInput)session.getValue("GI");

//页面有效性
if(tG == null)
{
    loggerDebug("LLCaseBackMissSave","页面失效,重新登陆!");
    return;
}

String tClaimNo = request.getParameter("ClmNo");

//打包数据
TransferData mTransferData = new TransferData();
mTransferData.setNameAndValue("ClaimNo", tClaimNo);

//准备传输数据 VData
VData tVData = new VData();
tVData.add(tG);
tVData.add(mTransferData);

// 数据传输
// LLCaseBackUpdateUI tLLCaseBackUpdateUI = new LLCaseBackUpdateUI();
//
//if (!tLLCaseBackUpdateUI.submitData(tVData,""))
//{
//    Content = " 申请失败，原因是: " + tLLCaseBackUpdateUI.mErrors.getError(0).errorMessage;
//    FlagStr = "Fail";
//}
	String busiName="LLCaseBackUpdateUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	   if(!tBusinessDelegate.submitData(tVData,"",busiName))
	   {    
	        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	        { 
					   Content = "申请失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
					   FlagStr = "Fail";
			}
			else
			{
					   Content = "申请失败";
					   FlagStr = "Fail";				
			}
	   }

else
{
    tVData.clear();
   // tVData = tLLCaseBackUpdateUI.getResult();
    tVData = tBusinessDelegate.getResult();
    TransferData tTransferData = new TransferData();
    tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData", 0);
    loggerDebug("LLCaseBackMissSave","tTransferData:" + tTransferData);
    String mClmNo = (String) tTransferData.getValueByName("clmno");
    loggerDebug("LLCaseBackMissSave","返回的赔案号为:" + mClmNo);
%>
<script language="javascript">
    parent.fraInterface.document.all("ClmNo").value="<%=mClmNo%>";
</script>
<%
    Content = " 申请成功! ";
    FlagStr = "Succ";
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
