<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
	//程序名称：GrpIssueInfoQueryInput.jsp
	//程序功能：问题件报表
	//创建日期：20090318
	//创建人  ：袁亦方
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getAttribute("GI");
%>
<script>
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录管理机构
	var comcode = "<%=tGI.ComCode%>";     //记录登陆机构
</script>
<title>问题件报表</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="GrpIssueInfoQuery.js"></SCRIPT>
<%@include file="./GrpIssueInfoQueryInit.jsp"%>
</head>
<body onload="initForm()">
<form action="" method=post name=fm id=fm target="fraTitle">
<table class=common border=0 width=100%>
	<tr>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,vaver);"></td>
		<td class=titleImg align=center>&nbsp;请输入查询条件：</td>
	</tr>
</table>
<Div  id= "vaver" style= "display: ''" class="maxbox1">
<table class=common>
	<tr class=common>
		<td class=title5>管理机构</td>
		<td class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ManageCom id=ManageCom readonly=true
			verify="管理机构|notnull&code:station"
			ondblclick="return showCodeList('station',[this,sManageComName],[0,1]);"
            onclick="return showCodeList('station',[this,sManageComName],[0,1]);"
			onkeyup="return showCodeListKey('station',[this,sManageComName],[0,1]);"><input
			class=codename name=sManageComName id=sManageComName readonly=true></td>			
		<TD class=title5>起始日期</TD>
		<TD class=input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="起始日期|notnull" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>	</tr>
            <tr class=common>
		<TD class=title5>截止日期</TD>
		<TD class=input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="截止日期|notnull" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>	
            <TD class=title5></TD>
            <TD class=input5></TD>
	</tr>		
</table></Div>
<!--<INPUT VALUE="查询问题件报表" class="cssButton" TYPE="button" name="query"
	onclick="easyQuery();">-->
    <a href="javascript:void(0);" class="button" name="query" onClick="easyQuery();">查询问题件报表</a>
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divInvoice);"></td>
		<td class=titleImg>问题件报表</td>
	</tr>
</table>
<Div id="divInvoice" style="display: ''" align=center>
<table class=common>
	<tr class=common>
		<td style="text-align:left" colSpan=1><span id="spanIssueInfoGrid"></span>
		</td>
	</tr>
</table>
	
</div>
	<INPUT VALUE="" TYPE=hidden name="sql">
	<!--<INPUT VALUE="导出Excel文件" name="toExcel" class=cssButton TYPE=button	onclick="ToExcel()">--><br>
    <a href="javascript:void(0);" name="toExcel" class="button" onClick="ToExcel();">导出Excel文件</a>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</form><br><br><br><br>
</body>
</html>
