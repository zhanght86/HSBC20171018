<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDPrvRepealDealInput.jsp
 //�����ܣ������ֹ����
 //�������ڣ�
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
 <%@page import="com.sinosoft.lis.pubfun.*" %>
 <SCRIPT src="PDPrvRepealDeal.js"></SCRIPT>
  <%@include file="PDPrvRepealDealInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDPrvRepealDealSave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >������Ϣ��</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>�������</td>
		<td class=input5>
			<input class=common name="RiskCode" readonly="readonly" >
		</td>
		<td class=title5>��������</td>
		<td class=input5>
			<input class="multiDatePicker" dateFormat="short" name="RequDate" readonly="readonly" >
		</td>
		<td class=title5>������״̬</td>
		<td class=input5>
			<Input class=codeno name=SealState CodeData="0|^0|��������^1|ֹͣ����" 
				ondblclick="return showCodeListEx('SealState',[this,SealStateName],[0,1]);" onkeyup="return showCodeListKeyEx('SealState',[this,SealStateName],[0,1]);"
				><Input class="codename" name=SealStateName readonly="readonly" >
		</td>
	</tr>
</table>

<BR>
<hr>
<BR>

<input value="���ֻ�����Ϣ�鿴" type=button  onclick="button117()" class="cssButton" type="button" >
<input value="����" type=button name=btnSave onclick="save()" class="cssButton" >
<input value="�޸�" type=button name=btnEdit onclick="update()" class="cssButton" >

<br><br>

<input value="����" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>