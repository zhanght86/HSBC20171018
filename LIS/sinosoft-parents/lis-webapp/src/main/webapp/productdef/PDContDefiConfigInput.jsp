<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDContDefiEntryInput.jsp
 //�����ܣ���Լ��Ϣ�������
 //�������ڣ�2009-3-13
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
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="../common/javascript/CustomDisplay.js"></SCRIPT>
  <script src="PDCommonJS.js"></script>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 
 <SCRIPT src="PDContDefiEntry.js"></SCRIPT>
 <%@include file="PDContDefiEntryInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDContDefiEntrySave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >��Ʒ������Ϣ��</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>��Ʒ���ִ���</td>
		<td class=input5>
			<input class=common name="RiskCode" readonly="readonly" >
		</td>
		<td class=title5>��������</td>
		<td class=input5>
			<input class="multiDatePicker" dateFormat="short" name="RequDate" readonly="readonly" >
		</td>
	</tr>
</table>
<input value="���ֻ�����Ϣ�鿴" type=button  onclick="button117( )" class="cssButton" type="button" >
<table>
  <tr>
    <td class="titleImg" >���ֲ㼶����</td>
  </tr>
</table>
<!-- <input value="���ָ���ɫ����" type=button  onclick="button106( )" class="cssButton" type="button" >-->
<input value="���������ӡ����" type=button  onclick="button107( )" class="cssButton" type="button" >
<input value="���ֽɷ����Զ���" type=button  onclick="button108( )" class="cssButton" type="button" >
<!--<input value="���ֽɷѼ������" type=button  onclick="button109( )" class="cssButton" type="button" >-->
<input value="����Ͷ������" type=button  onclick="button110( )" class="cssButton" type="button" >
<input value="����Ӌ��Ҫ��" type=button  onclick="button111( )" class="cssButton" type="button" >
<!--<input value="��������϶���" type=button  onclick="button115( )" class="cssButton" type="button" >-->
<!--<input value="�˻������ֶ���" type=button id="btnRiskAcc" onclick="button116( )" class="cssButton" type="hidden"> -->
<input value="�������ۿ��ƶ���" type=button  onclick="button114( )" class="cssButton" type="button" >
<br><br>
<input value="��һ��" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<hr>
<br><br>
<input type=hidden name="operator">
<input type=hidden name="IsDealWorkflow">
<input type=hidden name="tableName" value="LMRisk">
<input type=hidden name="SimpleContPara">
<input type=hidden name=MissionID>
<input type=hidden name=SubMissionID>
<input type=hidden name=ActivityID>
<input type=hidden name=IsReadOnly>
<input type=hidden name=PageNo value=101>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
