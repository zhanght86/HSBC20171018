<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�RIAthSchemeAssInput.jsp
 //�����ܣ������㷨����
 //�������ڣ�2011/6/17
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
 
  <SCRIPT src="RIAthSchemeAss.js"></SCRIPT>
  <%@include file="RIAthSchemeAssInit.jsp"%>
</head>

<body  onload="initForm();initElementtype();">
<form action="./RIAthSchemeAssSave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >��ѯ����</td>
  </tr>
</table>
<table  class="common" >
  <tr class="common">
    <td class="title5">��������</td><td class="input5"><input class="readonly" name="ArithmeticDefID"  readonly="readonly" ></td>  
    <td class="title5">��������</td><td class="input5"><input class="readonly" name="ArithmeticDefName"  readonly="readonly"></td>  
    <td class="title5">��������</td>
    <td class="input5">
    	<input class="codeno" name="RISolType" readonly="readonly" ondblclick="return showCodeList('risolutiontype',[this,RISolTypeName],[0,1]);" 
    		onkeyup="return showCodeListKey('risolutiontype',[this,RISolTypeName],[0,1]);" nextcasing=><input class="codename" name="RISolTypeName" readonly="readonly">
    </td>  
  </tr>
  <tr>
    <td class="title5">״          ̬</td>
    <td class="input5">
    	<input class="codeno" name="RIPreceptState" ondblclick="return showCodeList('ristate',[this,RIPreceptStateName],[0,1]);" onkeyup="return showCodeListKey('ristate',[this,RIPreceptStateName],[0,1]);" 
   	 		nextcasing=><input class="codename" name="RIPreceptStateName" >
   	</td>  
    <td class="title5">
    	<Div id= "divTitle1" style= "display:none;" >�ֳ����α���</Div>
    	<Div id= "divTitle2" style= "display:none;" >��ͬ��������</Div>
    </td>
    <td class="input5">
    	<Div id= "divInput1" style= "display:none;" >
    	    <input class="codeno" name="RIAccDefNo" ondblclick="return showCodeList('riaccmucode',[this,RIAccDefName],[0,1]);" onkeyup="return showCodeListKey('riaccmucode',[this,RIAccDefName],[0,1]);" 
    			nextcasing=><input class="codename" name="RIAccDefName" >
    	</Div>
    	<Div id= "divInput2" style= "display:none;" >
     	    <input class="codeno" name="RIRreceptNo" ondblclick="return showCodeList('riprecept',[this,RIRreceptName],[0,1]);" onkeyup="return showCodeListKey('riprecept',[this,RIRreceptName],[0,1]);" 
    			nextcasing=><input class="codename" name="RIRreceptName" >
		</Div>
    </td>   
  </tr>
</table>
<br>
<input value="��  ѯ"  onclick="button110( )" class="cssButton" type="button" >

<input class="cssButton" type="button" value="��  ��" onclick="ClosePage()" >
<br><br>
<table>
  <tr>
    <td class="titleImg" >��ϸ��Ϣ</td>
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
<br>
<input value="��������"  onclick="button109()" class="cssButton" type="button"  style="display:none;">
<br>

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
