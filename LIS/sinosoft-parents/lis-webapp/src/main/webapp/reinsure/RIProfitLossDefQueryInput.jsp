<%@include file="/i18n/language.jsp"%>
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�RIProfitLossDefQueryInput.jsp
 //�����ܣ�ӯ��Ӷ�����ѯ
 //�������ڣ�2011/8/20
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
 
  <SCRIPT src="RIProfitLossDefQuery.js"></SCRIPT>
  <%@include file="RIProfitLossDefQueryInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
<form action="./RIProfitLossDefQuerySave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >��ѯ����</td>
  </tr>
</table>
<table  class="common" >
  <tr class="common">
    <td class="title5">ӯ��Ӷ�����</td><td class="input5"><input class="common" name="RIProfitNo" ></td>  
    <td class="title5">ӯ��Ӷ������</td><td class="input5"><input class="common" name="RIProfitName" ></td>  
    <td class="title5">��������</td><td class="input5"><input class="codeno" name="RelaType"
    ondblclick="return showCodeList('riprorelatype',[this,RelaTypeName],[0,1]);"
    onkeyup="return showCodeListKey('riprorelatype',[this,RelaTypeName],[0,1]);" nextcasing=><input class="codename" name="RelaTypeName" ></td>  
  </tr>
</table>
<input value="��ѯ"  onclick="button138( )" class="cssButton" type="button" >
<input value="����"  onclick="button139( )" class="cssButton" type="button" >
<input value="�P�]"  onclick="button140( )" class="cssButton" type="button" >
<br>
<table>
  <tr>
    <td class="titleImg" >ӯ��Ӷ����Ϣ����</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulDefQueryGrid" >
     </span> 
      </td>
   </tr>
</table>

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
