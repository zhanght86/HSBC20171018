<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDRequRiskInput.jsp
 //�����ܣ���Ʒ�������ѯ
 //�������ڣ�2009-3-12
 //������  ��
 //���¼�¼��  ������    ��������     ����ԭ��/����
%>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<SCRIPT src="SpeedProgressConvert.js"></SCRIPT>
<%@include file="buttonshow.jsp"%>
<SCRIPT src="PDRiskQuery.js"></SCRIPT>

<%@include file="PDRiskQueryInit.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
</head>
<body onload="initForm();initElementtype();">
<form action="" method=post name=fm target="fraSubmit">

<table>
	<tr>
		<td class="titleImg">�������Ʒ���ִ��룺</td>
	</tr>
</table>
<table class=common>
	<TR class=common>
		<TD class=title5>��Ʒ���ִ���</TD>
		<TD class=input5><input class=common name=RiskCode
			verify="��Ʒ���ִ���|len<=7"></TD>
		<TD class=title5>����״̬</td>
		<td class=input5><Input class=codeno name=RiskState
			readonly="readonly"
			ondblClick="showCodeList('pd_riskstate',[this,RiskStateS],[0,1]);"
			onkeyup="showCodeListKey('pd_riskstate',[this,RiskState],[0,1]);"><Input
			class=codename name=RiskStateS></TD>
	</tr>
	<tr class=common>
		<TD class=title5>������������</TD>
		<td class=input5><input class="coolDatePicker" dateFormat="short"
			id="beginDate" name="beginDate"
			onClick="laydate
({elem:'#beginDate'});"> <span class="icon"><a
			onClick="laydate({elem: '#beginDate'});"><img
			src="../common/laydate/skins/default/icon.png" /></a></span></td>
		<TD class=title5>��������ֹ��</TD>
		<td class=input5><input class="coolDatePicker" dateFormat="short"
			id="endDate" name="endDate" onClick="laydate
({elem:'#endDate'});">
		<span class="icon"><a onClick="laydate({elem: '#endDate'});"><img
			src="../common/laydate/skins/default/icon.png" /></a></span></td>
	</TR>
</table>
<input value="��  ѯ"
	onclick="riskQuery()" class="cssButton" type="button"> <br>
<br>

<table class=common>
	<tr class=common>
		<td style="text-align: left;" colSpan=1><span
			id="spanMulline9Grid"> </span></td>
	</tr>
</table>
<INPUT CLASS=cssbutton VALUE="��  ҳ"
	TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> <INPUT
	CLASS=cssbutton VALUE="��һҳ"
	TYPE=button onclick="Mulline9GridTurnPage.previousPage();"> <INPUT
	CLASS=cssbutton VALUE="��һҳ"
	TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> <INPUT
	CLASS=cssbutton VALUE="β  ҳ"
	TYPE=button onclick="Mulline9GridTurnPage.lastPage();"> </BR>
</BR>

<input value="������Ϣ��ѯ" type=button
	onclick="basicInfoQuery()" class="cssButton" type="button"> <input
	value="��Լ��Ϣ��ѯ" type=button
	onclick="ContInfoQuery()" class="cssButton" type="button"> <input
	value="��ȫ��Ϣ��ѯ" type=button
	onclick="EndorseInfoQuery()" class="cssButton" type="button"> <input
	value="������Ϣ��ѯ" type=button
	onclick="ClaimInfoQuery()" class="cssButton" type="button"> <!-- input value="�������Ϣ��ѯ" type=button onclick="LFRiskInfoQuery()" class="cssButton"
	type="button"--> <br>
</br>
<input value="�������ѯ" type=button
	onclick="issueQuery()" class="cssButton" type="button"> </br>
</br>
<input value="���Ȳ�ѯ" type=button
	onclick="ShowDetail()" class="cssButton" type="button"> </br>

<!-- 
<table>
  <tr>
    <td class="titleImg" >��ѯ����</td>
  </tr>
</table>
<table  class="common" >
  <tr class="common">
    <td class="title">���ֱ���</td>
    <td class="input" colspan=2>
    	<input class="common" name="RiskNo">&nbsp;
    	<input value="��  ѯ"  onclick="QueryRisk()" class="cssButton" type="button" >
    </td>  
    <td class="title"></td><td class="input"></td><td class="title"></td>
  </tr>
</table>
 -->
<hr>
<Div id="divQuery" style="display: none">
<table>
	<tr>
		<td class="titleImg">������ȣ�</td>
	</tr>
</table>
<table class=common>
	<tr class=common>
		<td style="text-align: left;" colSpan=1><span id="RiskStateGrid"></span>
		</td>
	</tr>
</table>
</Div>
<Input type=hidden name=queryRiskCode value=''></form>
<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
