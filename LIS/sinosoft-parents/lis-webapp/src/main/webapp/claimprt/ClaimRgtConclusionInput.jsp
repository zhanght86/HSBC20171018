<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
 <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="ClaimRgtConclusion.js"></SCRIPT>
<link href="../common/css/Project.css" rel="stylesheet" type="text/css">
<link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
<link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
<%@include file="ClaimRgtConclusionInit.jsp"%>
<title>�����������۴�ӡ</title>
</head>

<body onLoad="initForm();">
<form action="./ClaimRgtConclusionSave.jsp" method=post name=fm id="fm"
	target="fraSubmit">
<table>
	<tr>
    
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divQuery);"></td>
		<td class=titleImg>�ŵ��ⰸ��ѯ����</td>
	</tr>
</table>

<Div id="divQuery" style="display: ''">
  <div class="maxbox1" >
<table class=common>
	<TR class=common>
		<TD class=title5>������</TD>
		<TD class=input5><Input class="common wid" name=RgtNo></TD>
		<TD class=title5>�������</TD>
		<TD class=input5><Input class="codeno" name=ManageCom  id=ManageCom
              style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('station',[this,ManageComName],[0,1]);"
			ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);"
			onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input
			class="codename" name="ManageComName" readonly></TD>
	</TR>
	<TR>
		<TD class=title5>ͳ������</TD>
		<TD class=input5>
            <input class=" multiDatePicker laydate-icon" dateFormat="short" name=StartDate  id=StartDate onClick="laydate({elem:'#StartDate'});" verify="��������|Date"> <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
		<TD class=title5>ͳ��ֹ��</TD>
		<TD class=input5>
            <input class=" multiDatePicker laydate-icon" dateFormat="short" name=EndDate  id=EndDate onClick="laydate({elem:'#EndDate'});" verify="��������|Date"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
            </TD>
	</TR>
</table>
</div>

<INPUT VALUE=" �� ѯ " class=cssButton TYPE=button onclick="easyQueryClick()"></Div>

<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this, divQueryResult)"></td>
		<td class=titleImg>�ⰸ��Ϣ</td>
	</tr>
</table>
<Div id="divQueryResult" style="display: ''">
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanQueryResultGrid">
		</span></td>
	</tr>
</table>
<center><input VALUE="��  ҳ" TYPE="button"
	onclick="turnPage.firstPage();" class="cssButton90"> <input
	VALUE="��һҳ" TYPE="button" onClick="turnPage.previousPage();"
	class="cssButton91"> <input VALUE="��һҳ" TYPE="button"
	onclick="turnPage.nextPage();" class="cssButton92"> <input
	VALUE="β  ҳ" TYPE="button" onClick="turnPage.lastPage();"
	class="cssButton93"></center>
</div>

<INPUT VALUE="�����������۴�ӡ" class=cssButton TYPE=button onclick="PrintClaimRgtPdf()">
    <Input class=common type=hidden
	name=CustomerNo></form>
<br><br><br><br>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>

