
<%
	//�������ƣ�AgentQueryInput.jsp
	//�����ܣ�
	//�������ڣ�2003-04-8
	//������  ��lh
	//���¼�¼��  ������    ��������     ����ԭ��/����
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
<title>���������ѯ</title>
</head>
<body onload="initForm();">
<form action="./AgentCommonQuerySubmit.jsp" method=post name=fm id="fm"
	target="fraSubmit">
<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divLAAgent);"></td>
		<td class=titleImg>���������ѯ����</td>
	</tr>
</table>
<div class="maxbox1">
<Div id="divLAAgent" style="display: ''">
<table class=common>
	<TR class=common>
		<TD class=title>�����������</TD>
		<TD class=input><Input class="common wid" name=AgentCom id="AgentCom"></TD>
		<TD class=title>�����������</TD>
		<TD class=input><Input class="common wid" name=Name id="Name"></TD>
		<TD class=title>�������</TD>
		<TD class=input><Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" name=ManageCom class='codeno'
			id="ManageCom"
			onclick="return showCodeList('station',[this,ManageComName],[0,1]);" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);"
			onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input
			name=ManageComName id="ManageComName" class=codename readonly=true></TD>
	</TR>
	<TR class=common>
		<TD class=title>��ַ</TD>
		<TD class=input><Input name=Address id="Address" class="common wid"></TD>
		<TD class=title>�ʱ�</TD>
		<TD class=input><Input name=ZipCode id="ZipCode" class="common wid"></TD>
		<TD class=title>�绰</TD>
		<TD class=input><Input name=Phone id="Phone" class="common wid"></TD>
	</TR>
</table>
</Div>
</div>
<a href="javascript:void(0)" class=button onclick="easyQueryClick();">��  ѯ</a>

<!-- <INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="easyQueryClick()">
<INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="returnParent()"> -->


<table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor:hand;" OnClick="showPage(this,divAgentGrid);"></td>
		<td class=titleImg>����������</td>
	</tr>
</table>
<Div id="divAgentGrid" style="display: ''" align=center>
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanAgentGrid"
			align=center> </span></td>
	</tr>
</table>
		<INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();">
		<INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();">
		<INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();">
		<INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
</div>
<a href="javascript:void(0)" class=button onclick="returnParent();">��  ��</a>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</form>
<br>
<br>
<br>
<br>
</body>
</html>
