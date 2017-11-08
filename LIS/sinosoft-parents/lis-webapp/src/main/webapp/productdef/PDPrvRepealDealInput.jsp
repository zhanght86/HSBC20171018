<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDPrvRepealDealInput.jsp
 //程序功能：条款废止申请
 //创建日期：
 //创建人  ：
 //更新记录：  更新人    更新日期     更新原因/内容
%>

<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="../common/javascript/CustomDisplay.js"></SCRIPT>
  <script src="PDCommonJS.js"></script>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 <%@page import="com.sinosoft.lis.pubfun.*" %>
 <SCRIPT src="PDPrvRepealDeal.js"></SCRIPT>
  <%@include file="PDPrvRepealDealInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDPrvRepealDealSave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >条款信息：</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>条款编码</td>
		<td class=input5>
			<input class=common name="RiskCode" readonly="readonly" >
		</td>
		<td class=title5>申请日期</td>
		<td class=input5>
			<input class="multiDatePicker" dateFormat="short" name="RequDate" readonly="readonly" >
		</td>
		<td class=title5>可销售状态</td>
		<td class=input5>
			<Input class=codeno name=SealState CodeData="0|^0|正常销售^1|停止销售" 
				ondblclick="return showCodeListEx('SealState',[this,SealStateName],[0,1]);" onkeyup="return showCodeListKeyEx('SealState',[this,SealStateName],[0,1]);"
				><Input class="codename" name=SealStateName readonly="readonly" >
		</td>
	</tr>
</table>

<BR>
<hr>
<BR>

<input value="险种基础信息查看" type=button  onclick="button117()" class="cssButton" type="button" >
<input value="保存" type=button name=btnSave onclick="save()" class="cssButton" >
<input value="修改" type=button name=btnEdit onclick="update()" class="cssButton" >

<br><br>

<input value="返回" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>