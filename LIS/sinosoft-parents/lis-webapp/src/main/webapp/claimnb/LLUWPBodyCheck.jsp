<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
GlobalInput tGI =new GlobalInput();
tGI =(GlobalInput)session.getValue("GI");
%>
<script>
var managecom ="<%=tGI.ManageCom%>"; //��¼�������
var comcode ="<%=tGI.ComCode%>"; //��¼��½����
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
<title>��ӡ����˱����֪ͨ��</title>
</head>
<body onLoad="initForm();initElementtype();">

<!--lzf  <form action="./BodyCheckPrintQuery.jsp" method=post name=fm target="fraSubmit">
	
		<!-- Ͷ������Ϣ���� -->
<!--lzf		<table class=common border=0 width=100%>
			<tr>
				<td class=titleImg align=center>�������ѯ������</td>
			</tr>
		</table>
		<table  class=common align=center>
			<TR class=common>
				<TD class=title>��������</TD>
				<TD class=input><Input class=common name=ContNo></TD>
				<TD class=title>����ӡˢ��</TD>
				<TD class=input><Input class=common name=PrtNo></TD>
				<TD class=title> �������</TD>
				<TD class=input><Input class="code" name=ManageCom ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);"></TD>
			</TR>
			<TR class=common>
				<TD class=title>�����˱���</TD>
				<TD class=input><Input class="code" name=AgentCode ondblclick="return showCodeList('AgentCode',[this]);" onkeyup="return showCodeListKey('AgentCode',[this]);"></TD>
				<TD class=title>���������</TD>
				<TD class=input><Input class="code" name=AgentGroup ondblclick="return showCodeList('AgentGroup',[this]);" onkeyup="return showCodeListKey('AgentGroup',[this]);"></TD>
				<!-- <TD class=title>չҵ����</TD>
				<TD class="input" nowrap="true">
					<Input class="common" name="BranchGroup">
					<input name="btnQueryBranch" class="common" type="button" value="?" onclick="queryBranch()" style="width:20">
				</TD> -->
<!--lzf			</TR>
		</table>
		<INPUT VALUE="��  ѯ" class=cssButton TYPE=button onclick="easyQueryClick();">
	</form> -->
	<form action="./LLUWPBodyCheckSave.jsp" method=post name=fm  id="fm" target="fraSubmit">
	<div id ="UWPBodyPool"></div>
<!-- lzf 		<table>
			<tr>
				<td class=common>
					<IMG  src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCCont1);">
				</td>
				<td class=titleImg>�����Ϣ</td>
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
			<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="getFirstPage();">
			<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="getPreviousPage();">
			<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="getNextPage();">
			<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="getLastPage();">
		</div>-->
		<p>
			<INPUT VALUE="��ӡ�������֪ͨ��" class=cssButton TYPE=button onClick="printPol();">
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
