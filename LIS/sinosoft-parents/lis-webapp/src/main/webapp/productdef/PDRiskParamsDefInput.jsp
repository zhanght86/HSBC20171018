<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDRiskParamsDefInput.jsp
 //�����ܣ��ɷ��ֶο��ƶ���
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
	  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	 <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="buttonshow.jsp"%> 
 <SCRIPT src="PDRiskParamsDef.js"></SCRIPT>

 <%@include file="PDRiskParamsDefInit.jsp"%>
 <%
 	String type;
 	if(((String)request.getParameter("type")).equals("1"))
 	type = ""+"�ɷ�"+"";
   else 
   	type = ""+"����"+"";
%>
</head>
<body  onload="initForm();initElementtype();">
<form action="./PDRiskParamsDefSave.jsp" method=post name=fm target="fraSubmit">


<table class=common>
	<tr class=common>
		<td class=title5>��Ʒ���ִ���</td>
		<td class=input5>
			<input class=common name="RiskCode" readonly="readonly" >
		</td>
		<td class=title5>����<%=type%>����</td>
		<td class=input5>
			<input class=common name="PayPlanCode" readonly="readonly" >
		</td>
	</tr>
</table>
<table>
  <tr>
    <td class="titleImg" >�ѱ���<%=type%>�����ֶ�</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline10Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button onclick="Mulline10GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline10GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline10GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button onclick="Mulline10GridTurnPage.lastPage();">

<table>
  <tr>
    <td class="titleImg" ><%=type%>�ֶο��ƶ���</td>
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
<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline9GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button onclick="Mulline9GridTurnPage.lastPage();">
<br><br>
<div align=left id=savebuttonid><input value="��  ��" type=button  onclick="save()" class="cssButton" type="button" >
<input value="��  ��" type=button  onclick="update()" class="cssButton" type="button" >
<input value="ɾ  ��" type=button  onclick="del()" class="cssButton" type="button" >
<input value="��  ��" type=button  onclick="returnParent( )" class="cssButton" type="button" >
</div>

<!-- <input id=definedparams value="�ֶβ�������" type=button  onclick="button127( )" class="cssButton" type="button" > -->
<br><br>
<input type=hidden name="operator">
<input type=hidden name="DutyCode">
<input type=hidden name="tableName" value="PD_LMDutyCtrl">
<input type=hidden name=IsReadOnly>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
