<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDContDefiEntryInput.jsp
 //程序功能：契约信息定义入口
 //创建日期：2009-3-13
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
 
 <SCRIPT src="PDContDefiPay.js"></SCRIPT>
 <%@include file="PDContDefiPayInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDContDefiEntrySave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >产品基础信息：</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>产品险种代码</td>
		<td class=input5>
			<input class=common name="RiskCode" readonly="readonly" >
		</td>
		<td class=title5>申请日期</td>
		<td class=input5>
			<input class="multiDatePicker" dateFormat="short" name="RequDate" readonly="readonly" >
		</td>
	</tr>
</table>
<table>
  <tr>
    <td class="titleImg" >产品责任信息：</td>
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
<INPUT CLASS=cssbutton VALUE="首页" TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline9GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾页" TYPE=button onclick="Mulline9GridTurnPage.lastPage();">
</BR></BR>
<table>
  <tr>
    <td class="titleImg" >责任层级定义</td>
  </tr>
</table>
<input value="费率表定制" type=button  onclick="button100( )" class="cssButton" type="button" >
<!--  
<input value="定义责任缴费算法" type=button onclick="button101( )" class="cssButton" type="button" >
<input value="缴费字段控制定义" type=button id="btnPayFieldCtrl" onclick="button102( )" class="cssButton" type="button" >
<input value="给付字段控制定义" type=button id="btnGetFieldCtrl" onclick="button103( )" class="cssButton" type="button" >
<input value="险种责任加费定义" type=button  onclick="button104( )" class="cssButton" type="button" >
-->
<input value="责任录入要素定义" type=button  onclick="button105( )" class="cssButton" type="button" >
<br><br>
<input value="上一步" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>

<input type=hidden name="operator">
<input type=hidden name="IsDealWorkflow">
<input type=hidden name="tableName" value="LMRisk">
<input type=hidden name="SimpleContPara">
<input type=hidden name=MissionID>
<input type=hidden name=SubMissionID>
<input type=hidden name=ActivityID>
<input type=hidden name=IsReadOnly>
<input type=hidden name=PageNo value=101>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
