<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�HTPeopServManaInput.jsp
 //�����ܣ���Ա�������
 //�������ڣ�2009-10-28
 //������  ��
 //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <SCRIPT src="PrintSampleInput.js"></SCRIPT>
  
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./HTPeopServManaSave.jsp" method=post name=fm target="fraSubmit">

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
    	<input value="��ѯ"  onclick="QueryRisk()" class="cssButton" type="button" >
    </td>  
    <td class="title"></td><td class="input"></td><td class="title"></td>
  </tr>
</table>
<table>
  <tr>
    <td class="titleImg" >������Ϣ</td>
  </tr>
</table>

<table  class= common>
   <tr  class= common>
	<td style="text-align:left;" colSpan=1>
     <span id="spanRiskGrid" ></span> 
	</td>
   </tr>
</table>
<input class=cssbutton value="��ҳ" type=button onclick="turnPage.firstPage();"> 
<input class=cssbutton value="��һҳ" type=button onclick="turnPage.previousPage();">      
<input class=cssbutton value="��һҳ" type=button onclick="turnPage.nextPage();"> 
<input class=cssbutton value="βҳ" type=button onclick="turnPage.lastPage();">
<br><br>
<span id="RiskStateGrid"></span> 

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
pan>
</body>
</html>
