<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDLFRiskInput.jsp
 //�����ܣ�����ᱨ������
 //�������ڣ�2009-3-16
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
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 
 <SCRIPT src="PDLFRisk.js"></SCRIPT>
 <%@include file="PDLFRiskInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDContDefiEntrySave.jsp" method=post name=fm target="fraSubmit">


<br>
<table class=common>
	<tr class=common>
		<td class=title5>
��Ʒ���ִ���
		</td>
		<td class=readonly>
			<input class="readonly" name="RiskCode" readonly="readonly" >
		</td>
		<td class=title5>
��������
		</td>
		<td class=readonly>
			<input class="readonly" name="RequDate" readonly="readonly" >
		</td>
	</tr>
</table>
<br>
<table>
<input	type=button class=cssbutton value="�鿴���ֻ�����Ϣ" onclick="queryRisk()"></table>

<br>

<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline9Grid" >
     </span> 
      </td>
   </tr>
</table>
<br>
<div align=center id=save><input value="����" type=button   onclick="save()" class="cssButton" type="button" >
<input value="�޸�" type=button  onclick="update()" class="cssButton" type="button" >
<input value="ɾ��" type=button  onclick="del()" class="cssButton" type="button" ></div>
<br>

<input value="���±�" type=hidden  onclick="button214( )" class="cssButton" type="button" >
<input value="�������ѯ" type=button  onclick="button215( )" class="cssButton" type="button" >
<input value="�����¼��" type=button  onclick="button217( )" class="cssButton" type="button" >
<input value="����ᱨ�������" type=button  onclick="btnLFRiskDone( )" class="cssButton" type="button" >
<br><br>
<input value="����" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>

<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_LFRisk">

<input type=hidden name=IsReadOnly>
<input type=hidden name=MissionID>
<input type=hidden name=SubMissionID>
<input type=hidden name=ActivityID>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
