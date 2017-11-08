
<%
	//程序名称：AgentQueryInput.jsp
	//程序功能：
	//创建日期：2003-04-8
	//创建人  ：lh
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
	String tManageCom = "";
	try {
		tManageCom = request.getParameter("ManageCom");
		loggerDebug("AgentComQueryInput","---tManageCom:" + tManageCom);
	} catch (Exception e1) {
		loggerDebug("AgentComQueryInput","---Exception:" + e1);
	}
%>

<script>
	var ManageCom = "<%= tManageCom%>";
</script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="./AgentComQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="./AgentComQueryInit.jsp"%>
<title>代理机构查询</title>
</head>
<body onload="initForm();">
<form action="./AgentCommonQuerySubmit.jsp" method=post name=fm id="fm"
	target="fraSubmit">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divLAAgent);"></td>
		<td class=titleImg>代理机构查询条件</td>
	</tr>
</table>
<div class="maxbox1">
<Div id="divLAAgent" style="display: ''">
<table class=common>
	<TR class=common>
		<TD class=title>代理机构编码</TD>
		<TD class=input><Input class="common wid" name=AgentCom id="AgentCom"></TD>
		<TD class=title>代理机构名称</TD>
		<TD class=input><Input class="common wid" name=Name id="Name"></TD>
		<TD class=title>管理机构</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" name=ManageCom class='codeno'
			id="ManageCom"
			onclick="return showCodeList('station',[this,ManageComName],[0,1]);" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);"
			onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input
			name=ManageComName id="ManageComName" class=codename readonly=true></TD>
	</TR>
	<TR class=common>
		<TD class=title>地址</TD>
		<TD class=input><Input name=Address id="Address" class="common wid"></TD>
		<TD class=title>邮编</TD>
		<TD class=input><Input name=ZipCode id="ZipCode" class="common wid"></TD>
		<TD class=title>电话</TD>
		<TD class=input><Input name=Phone id="Phone" class="common wid"></TD>
	</TR>
</table>
</Div>
</div>
<a href="javascript:void(0)" class=button onclick="easyQueryClick();">查  询</a>

<!-- <INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClick()">
<INPUT class=cssButton VALUE="返  回" TYPE=button onclick="returnParent()"> -->


<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divAgentGrid);"></td>
		<td class=titleImg>代理机构结果</td>
	</tr>
</table>
<Div id="divAgentGrid" style="display: ''" align=center>
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanAgentGrid"
			align=center> </span></td>
	</tr>
</table>
		<INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();">
		<INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();">
		<INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();">
		<INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
</div>
<a href="javascript:void(0)" class=button onclick="returnParent();">返  回</a>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</form>
<br>
<br>
<br>
<br>
</body>
</html>
