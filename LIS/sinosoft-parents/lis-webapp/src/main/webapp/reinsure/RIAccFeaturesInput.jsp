<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�RIAccFeaturesInput.jsp
 //�����ܣ��ֳ����ζ���-�ֱ�����
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
  <SCRIPT src="RIAccFeatures.js"></SCRIPT>
  <%@include file="RIAccFeaturesInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
<form action="./RIAccFeaturesSave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >�ֱ����Զ���</td>
  </tr>
</table>
<table  class="common" >
  <tr class="common">
    <td class="title5">�ֳ����α���</td><td class="input5"><input class="readonly" name="AccDefNo"  readonly="readonly" ></td>  
    <td class="title5">�ֳ���������</td><td class="input5"><input class="readonly" name="AccDefName"  readonly="readonly" ></td>  
    <td class="title5">���ֱ���</td>
    <td class="input5"><input class="codeno" name="RIRiskCode" 
	    ondblclick="return showCodeList('riaccrisk',[this,RIRiskName],[0,1],null,fm.AccDefNo.value,'Accumulatedefno');" 
	    onkeyup="return showCodeListKey('riaccrisk',[this,RIRiskName],[0,1],null,fm.AccDefNo.value,'Accumulatedefno');" nextcasing=><input class="codename" name="RIRiskName" >
    </td>  
  </tr>
	<tr class="common">
    <td class="title5">���α���</td>
    <td class="input5"><input class="codeno" name="RIDutyCode"
	    ondblclick="return showCodeList('riaccduty',[this,RIDutyName],[0,1],null,fm.RIRiskCode.value,'Associatedcode');" 
	    onkeyup="return showCodeListKey('riaccduty',[this,RIDutyName],[0,1],null,fm.RIRiskCode.value,'Associatedcode');" nextcasing=><input class="codename" name="RIDutyName" >
	</td>  
    <td class="title5">�ֱ�����</td>
    <td class="input5"><input class="codeno" name="RIRiskFu" value = '01'
	    ondblclick="return showCodeList('ririskfeature',[this,RIRiskFuName],[0,1]);" 
	    onkeyup="return showCodeListKey('ririskfeature',[this,RIRiskFuName],[0,1]);" nextcasing=><input class="codename" name="RIRiskFuName" value = '�������շֱ�''>
	</td>  
    <td class="title5"></td><td class="input5"></td>  
  </tr>
</table>
<br>

<Div id= "divFeature02" style= "display:none;" >
	<input value="��  ��"  onclick="button102()" class="cssButton" type="button" >
</Div>

<br><br>

<Div id= "divFeature01" style= "display: ''" >
	<table>
	  <tr>
	    <td class="titleImg" >���շֱ�������Ϣ</td>
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
	<br>
	<input value="��������"  onclick="button103()" class="cssButton" type="button" >
</Div>

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
