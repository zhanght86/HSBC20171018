<%@include file="../i18n/language.jsp"%>


<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.productdef.*" %>
<%
 //�������ƣ�PDDutyGetAliveInput.jsp
 //�����ܣ����θ�������
 //�������ڣ�2009-3-16
 //������  ��
 //���¼�¼��  ������    ��������     ����ԭ��/����
 
%>
<%
String tRiskCode = request.getParameter("riskcode");
String tStandbyFlag1 = "02";
PDSugIncomeConditionService tservice = new PDSugIncomeConditionService(tRiskCode,tStandbyFlag1);
String tRiskString = tservice.initRiskScript();
String tFuncString = tservice.initFuncScript();
%>
<html>
<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="DictionaryUtilities.js"></SCRIPT>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">

 <SCRIPT src="PDSugIncomeCondition.js"></SCRIPT>
<script type="text/javascript">
	var turnPage = new turnPageClass();
	<%=tFuncString%>
</script>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initElementtype();">
<form action="" method=post name=fm target="fraSubmit">
<hr>
<%=tRiskString%>
<div align=left>
	<input value="��  ��" name="btnSave" onClick="save()" class="cssButton" type="button" >
	<input value="ȡ  ��" name="btnEdit" onClick="cannelCondition()" class="cssButton" type="button" >

</div>
</form>
<span id="spanCode" style="position: absolute; display: none; top: 277px; left: 411px;"></span>
</body>
</html>

