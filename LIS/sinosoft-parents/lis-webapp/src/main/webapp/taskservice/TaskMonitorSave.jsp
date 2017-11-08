<%--
    接受任务ID数组，返回任务进度 2011-11-28 HuangLiang
--%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<html>
<%
loggerDebug("TaskMonitorSave","##########TaskMonitor----START----#########");
%>
<script language="javascript">
var content = new Array();
<%
	String FlagStr="";      //操作结果
	String Content = "";    //控制台信息
	String tAction = "";    //操作类型：delete update insert
	String tOperate = "";   //操作代码
	String busiName = "";	//业务代码

	VData tData = new VData();
	GlobalInput tG = new GlobalInput();

	FlagStr = "Succ";
	busiName = "TaskMonitorUI";
	tAction = "action";
	BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();

	tG=(GlobalInput)session.getValue("GI");
	String[] tArrayStr = request.getParameter("TaskMonitorCode").split(",");
	VData tVData = new VData();
	for(int i=0;i<tArrayStr.length;i++)
	{
		tVData.add(tArrayStr[i]);
	}
	// 准备传输数据 VData
	tData.add( tG );
	tData.add( tVData );

	if (!tBusinessDelegate.submitData(tData, tAction,busiName))
	{
		Content = " 操作失败，原因是: " + tBusinessDelegate.getCErrors().getFirstError();
		FlagStr = "Fail";
	}
	else
	{
		Content = " 操作成功! ";
		FlagStr = "Succ";
		tData.clear();
	}
	//获得任务进度数据VData
	String tContent = "";
	tVData = tBusinessDelegate.getResult();

	tData = (VData) tVData.get(0);
	try{
	for (int i = 0; i < tData.size(); i++)
	{
		tContent = tData.get(i).toString();
%>
		content[<%=i%>]="<%=tContent%>";
<%
	}
	}catch(Exception ex){
		FlagStr = "Fail";
		ex.printStackTrace();
	}
	loggerDebug("TaskMonitorSave",tData.toString());
	loggerDebug("TaskMonitorSave","########TaskMonitor----END----#########");
%>
	parent.fraInterface.afterMonitor("<%=FlagStr%>",content);
</script>
</html>
