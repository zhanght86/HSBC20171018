<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�RIAccDistillInput.jsp
 //�����ܣ��ֳ����ζ���-�������Ͷ���
 //�������ڣ�2011/6/16
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
 
  <SCRIPT src="RIAccDistill.js"></SCRIPT>
  <%@include file="RIAccDistillInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
<form action="./RIAccDistillSave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >�������Ͷ���</td>
  </tr>
</table>
<table  class="common" >
  <tr class="common">
    <td class="title5">�ֳ����α���</td><td class="input5"><input class="readonly" name="AccDefNo"  readonly="readonly" ></td>  
    <td class="title5">�ֳ���������</td><td class="input5"><input class="readonly" name="AccDefName"  readonly="readonly" ></td>  
    <td class="title5"></td><td class="input5"></td>  
  </tr>
</table>
<br>
<input value="ȷ  ��"  onclick="button100( )" class="cssButton" type="button" >
<br><br>
<table>
  <tr>
    <td class="titleImg" >��������ѡ��</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMul9Grid" >
     </span> 
      </td>
   </tr>
</table>

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
