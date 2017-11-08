<!---###打印预览页面上 [打印按钮]触发文件，用于打印完成后 修改“打印管理表”的打印状态 ##--->
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--用户校验类-->
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@page import="com.sinosoft.utility.*"%>

<%
	GlobalInput tGI = new GlobalInput();
	tGI=(GlobalInput)session.getValue("GI");
	String FlagStr = "";
	String Content = "";
	String PrintNo=(String)session.getValue("PrtSeq");
	loggerDebug("LLClaimoperPrintTable","################PrtSeq:#############" + PrintNo) ;
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("PrtSeq",PrintNo);

	VData tVData = new VData();
	tVData.add(tTransferData);
	tVData.add(tGI);
	ClaimPrintManageBL tClaimPrintManageBL = new ClaimPrintManageBL();
	if(!tClaimPrintManageBL.submitData(tVData,""))
	{
		FlagStr="Fail";
		Content="更新打印数据失败！";
		loggerDebug("LLClaimoperPrintTable","提交失败");
	}
	else
	{	
		FlagStr="Succ";
		Content="更新打印数据成功！";
		loggerDebug("LLClaimoperPrintTable","更新成功");
	}
%>

<html>
<script language="javascript">
	if("<%=FlagStr%>"=='Fail')
	{
		alert("更新打印管理表失败！");
	}
	window.returnValue="<%=FlagStr%>";
	window.close();
</script>
</html>

