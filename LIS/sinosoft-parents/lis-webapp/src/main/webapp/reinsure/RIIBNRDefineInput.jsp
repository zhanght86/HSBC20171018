<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�RIIBNRDefineInput.jsp
 //�����ܣ�IBNR����ά��
 //�������ڣ�2011-7-30
 //������  ��
 //���¼�¼��  ������    ��������     ����ԭ��/����
%>

<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 
  <SCRIPT src="RIIBNRDefine.js"></SCRIPT>
  <%@include file="RIIBNRDefineInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
<form action="./RIIBNRDefineSave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >��ѯ����</td>
  </tr>
</table>
<table  class="common" >
  <tr class="common">
    <td class="title5">�ٱ���ͬ</td><td class="input5"><input class="codeno" name="" ondblclick="return showCodeList(null,[,],[0,1]);" onkeyup="return showCodeListKey(null,[,],[0,1]);" nextcasing=><input class="codename" name="ManageComName" ></td>  
    <td class="title5">��������</td><td class="input5"><input class="codeno" name="" ondblclick="return showCodeList(null,[,],[0,1]);" onkeyup="return showCodeListKey(null,[,],[0,1]);" nextcasing=><input class="codename" name="ManageComName" ></td>  
  </tr>
</table>
<input value="��  ѯ"  onclick="button142( )" class="cssButton" type="button" >
<br>
<table>
  <tr>
    <td class="titleImg" >IBNR�����б�</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMul10Grid" >
     </span> 
      </td>
   </tr>
</table>
<input value="��  ��"  onclick="button143( )" class="cssButton" type="button" >
<br>

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>

