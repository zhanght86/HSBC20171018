<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDLMDutyInput.jsp
 //�����ܣ����ֳб�����
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
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

 <SCRIPT src="PDLMDuty.js"></SCRIPT>
 <%@include file="PDLMDutyInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDLMDutySave.jsp" method=post name=fm target="fraSubmit">

<Table class=common>
	<TR class=common>
		<TD class=title5>���ֱ���</TD>
		<TD class=input5>
			<input class=common name=RiskCode readonly="readonly">
		</TD>
		<TD class=title5>���α���</TD>
		<TD class=input5>
			<input class=common name=DutyCode readonly="readonly">
		</TD>
	</TR>
</Table>	
<table>
  <tr>
    <td class="titleImg" width=85%>���ֳб����Զ���:</td>
    <td>
    	<div align=left>
    		<input value="�޸�" type=button  onclick="update()" class="cssButton" type="button" >
    	</div>
    </td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline9Grid" >
     </span> 
      </td>
   </tr>
</table>
</BR>
<input value="����" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>

<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_LMDuty">
<input type=hidden name=IsReadOnly>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
