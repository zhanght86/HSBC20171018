
<%
	//�������ƣ�TaskService.jsp
	//�����ܣ�
	//�������ڣ�2004-12-15 
	//������  ��ZhangRong
	//���¼�¼��  ������    ��������     ����ԭ��/����
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
		<td class=titleImg>���������Ϣ</td>
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
<!--<input CLASS=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPagePlan.firstPage();queryTaskMonitorInfo();"> 
      <input CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPagePlan.previousPage();queryTaskMonitorInfo();"> 					
      <input CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPagePlan.nextPage();queryTaskMonitorInfo();"> 
      <input CLASS=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPagePlan.lastPage();queryTaskMonitorInfo();">-->	    				
   </Div>
   <Div id="divTaskPlanButton" style="display: ''"><!--INPUT VALUE="�鿴������ϸ" class=cssButton TYPE=button name=delbutton onclick="taskManage();"-->
<INPUT VALUE="�鿴����������־" class=cssButton TYPE=button name=delbutton
	onclick="showRunLog();"> <INPUT VALUE="�鿴��������־"
	class=cssButton TYPE=button name=delbutton onclick="showMonitorLog();">
	<input type="button" name="OrderRss" class="cssButton" value="����RSS" onclick="orderRSS();" />
<!--INPUT VALUE="��������" class=cssButton TYPE=button name=addbutton onclick="activateOne();"-->
<!--INPUT VALUE="ֹͣ����" class=cssButton TYPE=button name=delbutton onclick="deactivateOne();"-->
<!--INPUT VALUE="�������" class=cssButton TYPE=button name=delbutton onclick="taskManage();"-->
<INPUT VALUE="����ˢ��" class=cssButton TYPE=button name=delbutton
	onclick="refreshTask();">
	<input class="cssButton" type="button" value="����ˢ��"
	onclick="queryTaskMonitorInfo()" /> �Զ�ˢ��<select
	onchange="setTaskMonitorDataInterval(this.options[this.options.selectedIndex].value);">
	<option value="0" selected>���Զ�ˢ��</option>
	<option value="10000">10 ��</option>
	<option value="30000">30 ��</option>
	<option value="60000">60 ��</option>
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
