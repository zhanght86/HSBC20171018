<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�ScanContInput.jsp
//�����ܣ���������Լɨ�������¼��
//�������ڣ�2004-12-22 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
//�����¸���
String tContNo="";
String tFlag="";
GlobalInput tGI=new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");
tFlag=request.getParameter("type");
%>
<script>
var contNo="<%=tContNo%>";          //���˵��Ĳ�ѯ����.
var operator="<%=tGI.Operator%>";   //��¼����Ա
var manageCom="<%=tGI.ManageCom%>"; //��¼��½����
var type="<%=tFlag%>";
var comcode="<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="NoScanContInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="NoScanContInit.jsp"%>
<title>ɨ��¼��</title>
</head>
<body  onload="initForm();initElementtype();" >
	<form action="./NoScanContInputSave.jsp" method=post name=fm id="fm" target="fraSubmit">
		<!-- ������Ϣ���� -->
		<table class=common border=0 width=100%>
			<tr>
				<td class=common>
    				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
				</td>
				<td class=titleImg align=center>������Ͷ�����ţ�</td>
			</tr>
		</table>
		<div class="maxbox1">
		<Div  id= "divFCDay" style= "display: ''">
		<table class=common>
			<TR class=common>
				<TD class=title>�������</TD>
				<TD class=input>
					<Input  style="background:url(../common/images/select--bg_03.png) 		no-repeat right center"  class=codeno name=ManageCom id="ManageCom"  verify="�������|code:comcode&notnull" onclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'#1#','1');" ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'#1#','1');" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'#1#','1');"><input class=codename name=ManageComName id="ManageComName" readonly=true elementtype=nacessary>
				</TD>
				<TD class=title>Ͷ������</TD>
				<TD class=input>
					<Input class="common wid" name=PrtNo id="PrtNo" elementtype=nacessary MAXLENGTH="14" verify="Ͷ������|num&len=14">
				</TD>		
				<TD class=title>��������</TD>
				<TD class=input>
					<Input class="coolDatePicker" onClick="laydate({elem: '#InputDate'});" verify="Ͷ����������|date" dateFormat="short" name=InputDate id="InputDate"><span class="icon"><a onClick="laydate({elem: '#InputDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>								
			<TR class=common  style="display:none">
				<TD class=title>ѯ�۱���</TD>
				<TD class=input>
					<Input class="common wid"  name=AskpriceNo id="AskpriceNo" verify="ѯ�۱���|num" >
				</TD>
				<TD class=title>ѯ�۰汾��</TD>
				<TD class=input>
					<Input class="common wid"  name=AskNo id="AskNo" verify="ѯ�۰汾��|num" >
				</TD>
				<TD class=title></TD>
				<TD class=input></TD>			
			</tr>
			<tr id='SubTitle' style="display:none">	
			</tr>
		</table>
	</Div>
	</div>
		<a href="javascript:void(0)" class=button onclick="easyQueryClick();">��  ѯ</a>
		<a href="javascript:void(0)" class=button onclick="ApplyInput();">��  ��</a>
		<!-- <INPUT VALUE="��  ѯ" class=cssButton TYPE=button onclick="easyQueryClick();">
		<INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="ApplyInput();"> -->
		<table>
			<tr>
				<td class=common>
					<IMG  src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCGrp1);">
				</td>
				<td class=titleImg>Ͷ������Ϣ</td>
			</tr>
			<INPUT type="hidden" class=Common name=MissionID    id="MissionID" value="">
			<INPUT type="hidden" class=Common name=SubMissionID id="SubMissionID" value="">
		</table>
		<Div id="divLCGrp1" style="display: ''" align=center>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanGrpGrid" ></span>
					</td>
				</tr>
			</table>
			<div style="display: none" >
			<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage1.firstPage();">
			<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage1.previousPage();">
			<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage1.nextPage();">
			<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage1.lastPage();">
			</div>
		</div>
			<a href="javascript:void(0)" class=button onclick="GoToInput();">��ʼ¼��</a>
			<!-- <INPUT VALUE="��ʼ¼��" class=cssButton TYPE=button onclick="GoToInput();"> -->
			<!--INPUT class=cssButton VALUE="���±��鿴" TYPE=button onclick="showNotePad();"-->
		<input class=common type=hidden name=tFlag  id="tFlag"value="<%=tFlag%>">
		<Input class=common type=hidden name=Operator id="Operator">
	</form>
	<br>
	<br>
	<br>
	<br>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
