<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�RIAthCalItemInput.jsp
 //�����ܣ���ϸ�㷨���
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
 
  <SCRIPT src="RIAthCalItem.js"></SCRIPT>
  <%@include file="RIAthCalItemInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
<form action="./RIAthCalItemSave.jsp" method=post name=fm target="fraSubmit">

<div align=right>
<input value="��  ѯ"  onclick="button105( )" class="cssButton" type="button" >
<input value="��  ��"  onclick="button106( )" class="cssButton" type="button" >
<input value="��  ��"  onclick="button107( )" class="cssButton" type="button" >
<input value="ɾ  ��"  onclick="button108( )" class="cssButton" type="button" >
</div>
<br>
<table>
  <tr>
    <td class="titleImg" ><%="�㷨����"%></td>
  </tr>
</table>
<table  class="common" >
  <tr class="common">
    <td class="title5">�㷨����</td><td class="input5"><input class="readonly" name="" readonly="readonly" ></td>  
    <td class="title5">�㷨����</td><td class="input5"><input class="readonly" name="" readonly="readonly" ></td>  
    <td class="title5">�㷨����</td><td class="input5"><input class="codeno" name="RIAthType" ondblclick="return showCodeList('riathtype',[this,RIAthTypeName],[0,1]);" onkeyup="return showCodeListKey('riathtype',[this,RIAthTypeName],[0,1]);" nextcasing=><input class="codename" name="RIAthTypeName" ></td>  
  </tr>
	<tr class="common">
    <td class="title5">�¼�����</td><td class="input5"><input class="codeno" name="RIAthEvenType" ondblclick="return showCodeList('riatheventype',[this,RIAthEvenTypeName],[0,1]);" onkeyup="return showCodeListKey('riatheventype',[this,RIAthEvenTypeName],[0,1]);" nextcasing=><input class="codename" name="RIAthEvenTypeName" ></td>  
    <td class="title5">ҵ������</td><td class="input5"><input class="codeno" name="RIAthbsType" ondblclick="return showCodeList('riathbstype',[this,RIAthBSTypeName],[0,1]);" onkeyup="return showCodeListKey('riathbstype',[this,RIAthBSTypeName],[0,1]);" nextcasing=><input class="codename" name="RIAthBSTypeName" ></td>  
    <td class="title5">�㷨״̬</td><td class="input5"><input class="codeno" name="RIAthState" ondblclick="return showCodeList('ristate',[this,RIAthStateName],[0,1]);" onkeyup="return showCodeListKey('ristate',[this,RIAthStateName],[0,1]);" nextcasing=><input class="codename" name="RIAthStateName" ></td>  
  </tr>
</table>
�㷨����
<br><BR>
<textarea class="common" name="" cols="100%" rows="2" ></textarea>
<br><BR>
<table>
  <tr>
    <td class="titleImg" >�������</td>
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

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
