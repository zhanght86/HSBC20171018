
<%
	//程序名称：TaskService.jsp
	//程序功能：
	//创建日期：2004-12-15 
	//创建人  ：ZhangRong
	//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.taskservice.*"%>

<%
	TaskServiceEngine tTaskServiceEngine = new TaskServiceEngine();
	String tType = "2";
	loggerDebug("TaskServiceManager","tTaskServiceEngine.getRunTaskPlan():"
			+ tTaskServiceEngine.getRunTaskPlan(tType));
%>
<html>
<script>
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<%@include file="TaskServiceManagerInit.jsp"%>
<SCRIPT src="TaskServiceManager.js"></SCRIPT>

</head>

<body onload="initForm();initMonitorData();">
<form action="./TaskServiceChk.jsp" method=post name=fm id=fm
	target="fraSubmit">

<Div id="divTaskPlan" style="display: ''">
<table>
	<tr class=common>
    	<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
		<td class=titleImg>任务服务信息</td>
	</tr>
</table><Div  id= "divPayPlan1" style= "display: ''">
<table class=common>
	<TR>
	</TR>
	<TR>
		<TD text-align: left colSpan=1><span id="spanTaskPlanGrid">
		</span></TD>
	</TR>
</table></Div>
<!--<input CLASS=cssButton VALUE="首  页" TYPE=button onclick="turnPagePlan.firstPage();queryTaskMonitorInfo();"> 
      <input CLASS=cssButton VALUE="上一页" TYPE=button onclick="turnPagePlan.previousPage();queryTaskMonitorInfo();"> 					
      <input CLASS=cssButton VALUE="下一页" TYPE=button onclick="turnPagePlan.nextPage();queryTaskMonitorInfo();"> 
      <input CLASS=cssButton VALUE="尾  页" TYPE=button onclick="turnPagePlan.lastPage();queryTaskMonitorInfo();">-->	    				
   </Div>
   <Div id="divTaskPlanButton" style="display: ''"><!--INPUT VALUE="查看任务明细" class=cssButton TYPE=button name=delbutton onclick="taskManage();"-->
<INPUT VALUE="查看任务运行日志" class=cssButton TYPE=button name=delbutton
	onclick="showRunLog();"> <INPUT VALUE="查看任务监控日志"
	class=cssButton TYPE=button name=delbutton onclick="showMonitorLog();">
	<input type="button" name="OrderRss" class="cssButton" value="订阅RSS" onclick="orderRSS();" />
<!--INPUT VALUE="启动任务" class=cssButton TYPE=button name=addbutton onclick="activateOne();"-->
<!--INPUT VALUE="停止任务" class=cssButton TYPE=button name=delbutton onclick="deactivateOne();"-->
<!--INPUT VALUE="解除挂起" class=cssButton TYPE=button name=delbutton onclick="taskManage();"-->
<INPUT VALUE="任务刷新" class=cssButton TYPE=button name=delbutton
	onclick="refreshTask();">
	<input class="cssButton" type="button" value="进度刷新"
	onclick="queryTaskMonitorInfo()" /> 自动刷新<select
	onchange="setTaskMonitorDataInterval(this.options[this.options.selectedIndex].value);">
	<option value="0" selected>不自动刷新</option>
	<option value="10000">10 秒</option>
	<option value="30000">30 秒</option>
	<option value="60000">60 秒</option>
</select> 
	</Div>
<INPUT class=common type=hidden name="RunString"
	value="<%=tTaskServiceEngine.getRunTaskPlan(tType)%>"> <input
	type="hidden" name="fmAction" value=""></form>
	<form action="" method="post" name="fmMonitor" target="fraSubmit">
<input type="hidden" name="TaskMonitorCode" /></form>
<form action="" method="post" name="fmRSS" target="fraSubmit">
<input value="AddressRSS" type="hidden" name="fmRSS" /></form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
