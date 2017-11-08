<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
GlobalInput tGI =new GlobalInput();
tGI =(GlobalInput)session.getValue("GI");
%>
<script>
var managecom ="<%=tGI.ManageCom%>"; //记录管理机构
var comcode ="<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
     <!-- modified by lzf -->
   <script src="../common/javascript/jquery.workpool.js"></script>
   <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<SCRIPT src="LLUWPBodyCheck.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="LLUWPBodyCheckInit.jsp"%>
<title>打印理赔核保体检通知书</title>
</head>
<body onLoad="initForm();initElementtype();">

<!--lzf  <form action="./BodyCheckPrintQuery.jsp" method=post name=fm target="fraSubmit">
	
		<!-- 投保单信息部分 -->
<!--lzf		<table class=common border=0 width=100%>
			<tr>
				<td class=titleImg align=center>请输入查询条件：</td>
			</tr>
		</table>
		<table  class=common align=center>
			<TR class=common>
				<TD class=title>保单号码</TD>
				<TD class=input><Input class=common name=ContNo></TD>
				<TD class=title>保单印刷号</TD>
				<TD class=input><Input class=common name=PrtNo></TD>
				<TD class=title> 管理机构</TD>
				<TD class=input><Input class="code" name=ManageCom ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);"></TD>
			</TR>
			<TR class=common>
				<TD class=title>代理人编码</TD>
				<TD class=input><Input class="code" name=AgentCode ondblclick="return showCodeList('AgentCode',[this]);" onkeyup="return showCodeListKey('AgentCode',[this]);"></TD>
				<TD class=title>代理人组别</TD>
				<TD class=input><Input class="code" name=AgentGroup ondblclick="return showCodeList('AgentGroup',[this]);" onkeyup="return showCodeListKey('AgentGroup',[this]);"></TD>
				<!-- <TD class=title>展业机构</TD>
				<TD class="input" nowrap="true">
					<Input class="common" name="BranchGroup">
					<input name="btnQueryBranch" class="common" type="button" value="?" onclick="queryBranch()" style="width:20">
				</TD> -->
<!--lzf			</TR>
		</table>
		<INPUT VALUE="查  询" class=cssButton TYPE=button onclick="easyQueryClick();">
	</form> -->
	<form action="./LLUWPBodyCheckSave.jsp" method=post name=fm  id="fm" target="fraSubmit">
	<div id ="UWPBodyPool"></div>
<!-- lzf 		<table>
			<tr>
				<td class=common>
					<IMG  src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCCont1);">
				</td>
				<td class=titleImg>体检信息</td>
			</tr>
		</table>
		<Div id="divLCCont1" style="display: ''" align =center>
			<table class=common>
				<TR class=common>
					<td text-align: left colSpan=1>
						<span id="spanPolGrid"></span>
					</td>
				</tr>
			</table>
			<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="getFirstPage();">
			<INPUT VALUE="上一页" class=cssButton TYPE=button onclick="getPreviousPage();">
			<INPUT VALUE="下一页" class=cssButton TYPE=button onclick="getNextPage();">
			<INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="getLastPage();">
		</div>-->
		<p>
			<INPUT VALUE="打印理赔体检通知书" class=cssButton TYPE=button onClick="printPol();">
		</p>
		<input type=hidden id="fmtransact" name="fmtransact">
		<input type=hidden id="ContNo" name="ContNo">
		<input type=hidden id="PrtSeq" name="PrtSeq">
		<input type=hidden id="MissionID" name="MissionID">
		<input type=hidden id="SubMissionID" name="SubMissionID">
		<input type=hidden id="ActivityID" name="ActivityID">
		<input type=hidden id="PrtNo" name="PrtNo">
		<input type=hidden id="ManageCom" name="ManageCom">
		<input type=hidden id="AgentCode" name="AgentCode">
		<input type=hidden id="AgentGroup" name="AgentGroup">
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
